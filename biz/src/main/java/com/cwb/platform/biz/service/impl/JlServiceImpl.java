package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizJlMapper;
import com.cwb.platform.biz.mapper.BizPtyhMapper;
import com.cwb.platform.biz.mapper.BizUserMapper;
import com.cwb.platform.biz.mapper.BizWjMapper;
import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.biz.model.BizWj;
import com.cwb.platform.biz.model.BizYjmx;
import com.cwb.platform.biz.service.*;
import com.cwb.platform.biz.util.IDNameIdentify;
import com.cwb.platform.biz.util.ShoreCode;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.mapper.SysClkPtjsMapper;
import com.cwb.platform.sys.mapper.SysClkPtyhMapper;
import com.cwb.platform.sys.mapper.SysYhJsMapper;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.sys.model.SysJs;
import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.sys.model.SysYhJs;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.commonUtil.EncryptUtil;
import com.cwb.platform.util.exception.RuntimeCheck;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.common.Mapper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class JlServiceImpl extends BaseServiceImpl<BizJl,String> implements JlService {
    @Autowired
    private BizWjMapper wjMapper;

    @Autowired
    private BizJlMapper entityMapper;
    @Autowired
    private BizPtyhMapper bizPtyhMapper;
    @Autowired
    private BizUserMapper userMapper;

    @Autowired
    private PtyhService ptyhService;
    @Autowired
    private UserService userService;
    @Autowired
    private SysClkPtyhMapper sysClkPtyhMapper;
    @Autowired
    private SysClkPtjsMapper sysClkPtjsMapper;
    @Autowired
    private SysYhJsMapper sysYhJsMapper;

    @Autowired
    private WxMpService wxMpService;
    @Value("${qr_code_file_url}")
    private String qrCodeFileUrl;

    @Autowired
    private YjmxService yjmxService;
    @Autowired
    private ZhService zhService;






    @Override
    protected Mapper<BizJl> getBaseMapper() {
        return entityMapper;
    }
    /**
     * 更新教练认证状态
     *
     * yhJlsh 1成功  2 失败
     *
     * @return
     */
    @Override
    public ApiResponse<String> updateYhRz(BizPtyh obd){
        SysYh sysYh = getCurrentUser();
        BizPtyh user = bizPtyhMapper.selectByPrimaryKey(obd.getId());
        if (user == null) return ApiResponse.fail("用户不存在");

        RuntimeCheck.ifTrue(!StringUtils.equals(user.getYhJlsh(), "0"), "操作失败，只有提交教练资料才能进行认证操作");
        RuntimeCheck.ifTrue(StringUtils.equals(user.getYhJlsh(), "1"), "操作失败，该教练已认证无需再次认证");
        RuntimeCheck.ifTrue(StringUtils.equals(user.getYhSfsd(), "1"), "操作失败，该教练已锁定无法进行认证操作");
        RuntimeCheck.ifFalse(StringUtils.equals(user.getYhSfyjz(), "1"), "操作失败，该教练无驾照无法进行认证操作");

        RuntimeCheck.ifBlank(obd.getYhJlsh(), "审核状态不能为空");
        if (StringUtils.containsNone(obd.getYhJlsh(), new char[]{'1', '2'})) {
            return ApiResponse.fail("请输入正确审核状态");
        }

        BizJl jlInfo=this.findById(user.getId());
        if (jlInfo == null) return ApiResponse.fail("教练信息不存在");


        BizPtyh newEntity = new BizPtyh();
        newEntity.setId(user.getId());

        String yhZtMs="";
        yhZtMs=obd.getYhZtMs();
        if(StringUtils.equals("2",obd.getYhJlsh())){
            RuntimeCheck.ifBlank(yhZtMs, "请填写审核失败原因。");

            entityMapper.deleteByPrimaryKey(user.getId());

        } else if(StringUtils.equals("1",obd.getYhJlsh())){
            if(!StringUtils.equals(user.getYhZtMs(),"1")){//如果学员资料没有审核，就需要将学员资料进行同步审核
                //biz_ptyh平台用户表 字段拼装
                String yhXm=jlInfo.getYhXm();
                String yhZjhm=jlInfo.getYhZjhm();

                String sex;//获取性别 ZDCLK0042(1、男;2、女)
                if (Integer.parseInt(yhZjhm.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
                    sex = "2";
                } else {
                    sex = "1";
                }

                newEntity.setYhXm(yhXm);//用户姓名
                newEntity.setYhZjhm(yhZjhm);//用户证件号码
                newEntity.setYhXb(sex);//用户性别
                newEntity.setYhSfyjz("1");//用户驾照状态不能为空


                //更新用户实名表
                //      获取用户父级ID
                String yhSjid = "";//设置上级ID
                String yhSsjid = "";//上上级ID

                String yhYyyqm = user.getYhYyyqm();//该用户的父级ID
                SimpleCondition newCondition = new SimpleCondition(BizPtyh.class);
                newCondition.eq(BizPtyh.InnerColumn.yhZsyqm.name(), yhYyyqm);
                List<BizPtyh> bizPtyhsList = ptyhService.findByCondition(newCondition);
                if (bizPtyhsList == null) return ApiResponse.fail("用户资料存在异常，请联系管理处理!");
                if (bizPtyhsList.size() != 1) return ApiResponse.fail("用户资料存在异常，请联系管理处理!");
                String pUserId = bizPtyhsList.get(0).getId();//获取出父级ID
                yhSjid = pUserId;
                BizUser pBizUser = userMapper.selectByPrimaryKey(yhSjid);//获取出上上级ID
                if (pBizUser != null) {
                    yhSsjid = pBizUser.getYhSjid();
                }

                //修改用户实名表  biz_user
                BizUser bizUser = new BizUser();
                bizUser.setYhId(user.getId());//用户ID
                bizUser.setYhZjhm(jlInfo.getYhZjhm());//用户证件号码
                bizUser.setYhSjhm(user.getYhZh());//用户账户
                bizUser.setYhSfjsz(user.getYhSfyjz());//设置是否有驾驶证(1:有 2:没有)
                bizUser.setYhXm(jlInfo.getYhXm());//姓名
                bizUser.setCjsj(DateUtils.getNowTime());//创建时间
                bizUser.setYhSjid(yhSjid);//设置上级ID
                bizUser.setYhSsjid(yhSsjid);//上上级ID

                userMapper.deleteByPrimaryKey(user.getId());
                userMapper.insert(bizUser);

            }
            newEntity.setYhZt(obd.getYhJlsh());
//            newEntity.setYhLx("2");//设置类型 ZDCLK0041(2、教练、1、学员)
        }
        newEntity.setYhJlsh(obd.getYhJlsh());
        newEntity.setYhJlMs(yhZtMs);
       int i = ptyhService.update(newEntity);
       if(i>0){
           BizJl jl=new BizJl();
           jl.setYhId(newEntity.getId());
           jl.setJlShZt(obd.getYhJlsh());
           jl.setJlShMs(yhZtMs);
           this.update(jl);
       }
        return i==1?ApiResponse.success():ApiResponse.fail("审核失败");
    }



    @Override
    public ApiResponse<String> validAndSave(BizJl entity) {
        SysYh user=getCurrentUser();
        RuntimeCheck.ifBlank(entity.getYhXm(), "用户姓名不能为空");
        RuntimeCheck.ifBlank(entity.getYhZjhm(), "用户身份证号不能为空");
        RuntimeCheck.ifBlank(entity.getYhSjhm(), "用户手机号不能为空");
        RuntimeCheck.ifBlank(entity.getJlQu(), "用户教练所属区域不能为空");
        RuntimeCheck.ifBlank(entity.getJlZz(), "教练住地址不能为空");
        RuntimeCheck.ifBlank(entity.getJsId(), "角色id 不能为空");


        //设置用户密码 默认用户密码为123456
        String yhMmEncrypt = "4DA3BB20330A34F4";
        if(StringUtils.isNotEmpty(entity.getYhMm())){
            yhMmEncrypt = EncryptUtil.encryptUserPwd(entity.getYhMm());
            RuntimeCheck.ifBlank(yhMmEncrypt, "用户密码加密失败，用户注册失败");
        }
//        1、检查手机号码是否已经注册过
        BizPtyh newEntity = new BizPtyh();
        newEntity.setYhZh(entity.getYhSjhm());
        int dnCount=bizPtyhMapper.selectCount(newEntity);
        RuntimeCheck.ifTrue(dnCount>0,"该手机号码已经注册过，不能再次注册");
//        2、检查用户的证件号码是不是注册过
        newEntity = new BizPtyh();
        newEntity.setYhZjhm(entity.getYhZjhm());
        dnCount=bizPtyhMapper.selectCount(newEntity);
        RuntimeCheck.ifTrue(dnCount>0,"该证件号码已经注册过，不能再次注册");

//        3、组装平台用户对象进行入库操作
        newEntity = new BizPtyh();
        newEntity.setId(genId());
        newEntity.setYhZh(entity.getYhSjhm());//用户手机号码
        newEntity.setYhMm(yhMmEncrypt);//用户加密的密码
        newEntity.setYhZjhm(entity.getYhZjhm());//用户证件号码
        newEntity.setYhCjr(user.getYhid());//用户ID
        newEntity.setCjsj(DateUtils.getNowTime());//创建时间
        newEntity.setYhXm(entity.getYhXm());//用户姓名
        newEntity.setYhLx("zy");

// ------------新操作
        SysYh sysYh = new SysYh();
        // 用户密码
//        if(StringUtils.isNotBlank(entity.getYhMm())){
//            yhMmEncrypt = EncryptUtil.encryptUserPwd(entity.getYhMm());
//            RuntimeCheck.ifBlank(yhMmEncrypt, "用户密码加密失败，用户注册失败");
//        }
        // 检查手机号码是否已经注册过
        sysYh = new SysYh();
        sysYh.setZh(entity.getYhSjhm());
        int yhCount = sysClkPtyhMapper.selectCount(sysYh);
        RuntimeCheck.ifTrue(yhCount > 0 , "该手机号码已经注册过，不能再次注册" );
        // 检查用户的证件号码是不是注册过
        sysYh = new SysYh();
        sysYh.setZjhm(entity.getYhZjhm());
        yhCount = sysClkPtyhMapper.selectCount(sysYh);
        RuntimeCheck.ifTrue(yhCount > 0 , "该证件号码已经注册过，不能再次注册");
        // 检查角色信息是否存在
        SysJs sysJs = sysClkPtjsMapper.selectByPrimaryKey(entity.getJsId());
        RuntimeCheck.ifTrue(ObjectUtils.isEmpty(sysJs) , "用户角色信息不存在， 请重新选取角色");

//        String card = IDNameIdentify.identify(entity.getYhZjhm(), entity.getYhXm());
//        RuntimeCheck.ifFalse(StringUtils.equals(card,"200"), card);
        String yhZsyqm = "";
        boolean flag = true;
        while (flag) {
            yhZsyqm = ShoreCode.createShareCode();
            List<BizPtyh> ptyhs = ptyhService.findEq(BizPtyh.InnerColumn.yhZsyqm, yhZsyqm);
            if (CollectionUtils.isEmpty(ptyhs)) {
                flag = false;
            }
        }
        String filepath = "/QRCode/" + DateTime.now().toString("yyyyMMdd") + "/" + yhZsyqm + ".png";
        WxMpQrCodeTicket ticket = null;
        String pictureUrl = null;
        try {
            ticket = wxMpService.getQrcodeService().qrCodeCreateLastTicket(newEntity.getId());
           String qrCode = ticket.getTicket();
            pictureUrl = wxMpService.getQrcodeService().qrCodePictureUrl(qrCode);
            URL u = new URL(pictureUrl);
            newEntity.setQrcode(ticket.getUrl());
            FileUtils.copyURLToFile(u, new File(qrCodeFileUrl + filepath), 5000, 5000);
        } catch (Exception e) {
            RuntimeCheck.ifTrue(true, "邀请码生成异常");
        }
        newEntity.setYhZsyqm(yhZsyqm);
        newEntity.setYhZsyqmImg(filepath);
        newEntity.setYhYqmgqsj(DateTime.now().plusYears(1).toString("yyyy-MM-dd HH:mm:ss"));
        newEntity.setYhYqmkssj(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));


        //通过证件号码识别用户性别
        String CardCode=entity.getYhZjhm();
        String sex;//获取性别 ZDCLK0042(1、男;2、女)
        if (Integer.parseInt(CardCode.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
            sex = "2";
        } else {
            sex = "1";
        }


        // 组装平台用户
        sysYh = new SysYh();
        sysYh.setYhid(newEntity.getId());
        sysYh.setZh(entity.getYhSjhm());
        sysYh.setMm(yhMmEncrypt);
        sysYh.setSjh(entity.getYhSjhm());
        sysYh.setCjr(user.getYhid());
        sysYh.setCjsj(DateUtils.getNowTime());
        sysYh.setZt("01");
        sysYh.setXm(entity.getYhXm());
        sysYh.setLx("zy");
        sysYh.setXb(sex);
        sysYh.setZjhm(entity.getYhZjhm());

        int i = sysClkPtyhMapper.insertSelective(sysYh);
        if( i == 0){
            return ApiResponse.fail("保存失败，请重新操作");
        }


        // 保存用户角色

        SysYhJs sysYhJs = new SysYhJs();
        sysYhJs.setYhjsId(genId());
        sysYhJs.setYhId(sysYh.getYhid());
        sysYhJs.setJsId(entity.getJsId());
        sysYhJs.setCjr(user.getYhid());
        sysYhJs.setCjsj(DateUtils.getNowTime());
        sysYhJsMapper.insertSelective(sysYhJs);

// ------------------------------------------------------------------

        newEntity.setYhXb(sex);//性别 ZDCLK0042(1、男;2、女)
        newEntity.setYhZt("1");//认证状态 ZDCLK0043(0 未认证、1 已认证)
        newEntity.setDdSfjx("0");//是否缴费 ZDCLK0045 (0 未缴费 1 已缴费)
        newEntity.setYhTx(entity.getJlImg());//用户头像
        newEntity.setYhBm(entity.getYhXm());//用户别名
        newEntity.setYhIxySffp("0");//学员是否已分配 ZDCLK0046 (0 否  1 是)
        newEntity.setYhSfyjz("1");//是否有驾照 ZDCLK0046 (0 否  1 是)
        newEntity.setYhSfsd("0");//用户是否锁定 ZDCLK0046 (0 否  1 是)  0是没有锁定 1是已锁定
        newEntity.setYhJlsh("1");//教练认证状态 ZDCLK0043(0 未认证、1 已认证 2、认证失败)
//        newEntity.setYhLx("2");//设置类型 ZDCLK0041(2、教练、1、学员)

        i=ptyhService.save(newEntity);
        if(i==0){
            return ApiResponse.fail("保存失败，请重新操作");
        }
//        4、组装用户附属表
        BizUser bizUser = new BizUser();
        bizUser.setYhId(newEntity.getId());//用户ID
        bizUser.setYhZjhm(newEntity.getYhZjhm());//用户证件号码
        bizUser.setYhSjhm(newEntity.getYhZh());//用户账户
        bizUser.setYhSfjsz(newEntity.getYhSfyjz());//设置是否有驾驶证(1:有 2:没有)
        bizUser.setYhXm(newEntity.getYhXm());//姓名
        bizUser.setCjsj(DateUtils.getNowTime());//创建时间
        i=userService.save(bizUser);
        RuntimeCheck.ifTrue(i==0, "教练住地址不能为空");
//        5、教练表入库
        entity.setYhId(newEntity.getId());
        entity.setJlShZt("1");
        if(StringUtils.isBlank(entity.getJlImg())){
            entity.setJlImg("temp/192.png");
        }
        entityMapper.insertSelective(entity);

        BizYjmx yjmx = new BizYjmx();
        yjmx.setId(genId());
        yjmx.setZjFs("-1");
        yjmx.setZjZt("1");
        yjmx.setZjJe(20000d);
        yjmx.setYhId(newEntity.getId());
        yjmx.setTxShZt("1");
        yjmx.setMxlx("4");
        yjmx.setCjsj(DateUtils.getNowTime());
        yjmxService.save(yjmx);
        zhService.userAccountUpdate(Arrays.asList(newEntity.getId()));


        String[] imgList = StringUtils.split(entity.getImgList(), ",");
        String yhSfyjz="0";//设置是否有驾照 ZDCLK0046 (0 否  1 是)

        List<BizWj> wjList = new ArrayList<BizWj>();
        List<String> wjSxList=new ArrayList<String>();
        if (imgList != null&&imgList.length>0) {
            if(StringUtils.trimToNull(imgList[2])!=null  && !StringUtils.equals(imgList[2],"-")){
                yhSfyjz="1";
            }
            for (int k = 0; k < imgList.length; k++) {
                if(StringUtils.trimToNull(imgList[k])!=null  && !StringUtils.equals(imgList[k],"-")) {
                    BizWj wj = new BizWj();
                    wj.setId(genId());
                    wj.setYhId(newEntity.getId());//
                    wj.setWjTpdz(imgList[k]);//

                    //ZDCLK0050 (0 10、 身份证正面 1 11、 身份证反面  2 20、 驾照正面 3 21、 驾照背面…………)
                    switch (k) {
                        case 0:
                            wj.setWjSx("10");
                            break;
                        case 1:
                            wj.setWjSx("11");
                            break;
                        case 2:
                            wj.setWjSx("20");
                            break;
                        case 3:
                            wj.setWjSx("21");
                            break;
                    }
                    wj.setWjSbzt("0");
                    wj.setCjsj(DateUtils.getNowTime());
                    wj.setWjSfyx("1");
                    wjList.add(wj);
                    wjSxList.add(wj.getWjSx());
                }
            }
        }
        //
        if (wjList.size() > 0) {
            wjMapper.deleteBatch(newEntity.getId(),wjSxList);
            wjMapper.insertBatch(wjList);
        }

        return ApiResponse.success();
    }

   public ApiResponse<String> updateEntity(BizJl entity){
       entityMapper.updateByPrimaryKey(entity);
        return ApiResponse.success();
   }
}
