package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.mapper.BizOrderMapper;
import com.cwb.platform.biz.mapper.BizPtyhMapper;
import com.cwb.platform.biz.mapper.BizUserMapper;
import com.cwb.platform.biz.mapper.BizWjMapper;
import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.biz.model.BizWj;
import com.cwb.platform.biz.service.JlService;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.biz.service.UserService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.bean.AccessToken;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.*;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class PtyhServiceImpl extends BaseServiceImpl<BizPtyh, java.lang.String> implements PtyhService {

    @Autowired
    private StringRedisTemplate redisDao;

    @Value("${img_url}")
    private String imgUrl;

    @Value("${debug_test}")
    private String debugTest;

    // 忽略当接收json字符串中没有bean结构中的字段时抛出异常问题
    private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    @Autowired
    private PtyhService ptyhService;
    @Autowired
    private BizPtyhMapper entityMapper;
    @Autowired
    private BizWjMapper wjMapper;
    @Autowired
    private JlService jlService;
    @Autowired
    private UserService userService;
    @Autowired
    private BizUserMapper userMapper;

    @Autowired
    private BizOrderMapper orderMapper;


    //
    @Value("${appSendSMSRegister:app_sendSMS_register}")
    private String appSendSMSRegister;

    @Value("${appSendSMSResetting:app_sendSMS_resetting}")
    private String appSendSMSResetting ;




    @Override
    protected Mapper<BizPtyh> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls() {
        return BizPtyh.class;
    }


    @Override
    public List<String> getSpecialCols() {
        return Arrays.asList("ddSfjx", "yhSfyjz", "yhZt");
    }

    @Override
    public List<Map<String, String>> getSpecialVals(List<BizPtyh> list) {
        List<Map<String, String>> data = new ArrayList<>(list.size());
        for (BizPtyh row : list) {
            Map<String, String> map = new HashMap<>();
            map.put("ddSfjx", "1".equals(row.getDdSfjx()) ? "已缴费" : "未缴费");
            map.put("yhSfyjz", "1".equals(row.getYhSfyjz()) ? "有" : "无");
            map.put("yhZt", "1".equals(row.getYhZt()) ? "已认证" : "未认证");
            data.add(map);
        }
        return data;
    }


    @Override
    protected void afterPager(PageInfo<BizPtyh> resultPage) {
        List<BizPtyh> list = resultPage.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(bizPtyh -> afterReturn(bizPtyh));
        }

        return;
    }

    /**
     * 重置学员部分信息 ， 不对外展示
     *
     * @param bizPtyh
     */
    private void afterReturn(BizPtyh bizPtyh) {
        if (bizPtyh != null) {
            bizPtyh.setYhMm("");
            bizPtyh.setYhOpenId("");
            bizPtyh.setYhAlipayId("");
            bizPtyh.setYhZsyqm("");
            bizPtyh.setYhYyyqm("");
            if (StringUtils.isNotBlank(bizPtyh.getYhZjhm())) {
                bizPtyh.setYhZjhm(bizPtyh.getYhZjhm().replaceAll("(\\d{3})\\d*(\\d{4})", "$1******$2"));
            }
            if (StringUtils.isNotBlank(bizPtyh.getYhTx()) && StringUtils.containsNone(bizPtyh.getYhTx(), "http")) {
                bizPtyh.setYhTx(imgUrl + bizPtyh.getYhTx());
            }

            if (StringUtils.isNotBlank(bizPtyh.getYhZsyqmImg()) && StringUtils.containsNone(bizPtyh.getYhZsyqmImg(), "http")) {
                bizPtyh.setYhZsyqmImg(imgUrl + bizPtyh.getYhZsyqmImg());
            }
        }
    }

    public BizPtyh afterReturns(BizPtyh bizPtyh) {
        this.afterReturn(bizPtyh);
        return bizPtyh;
    }

    /**
     * 更新用户是否锁定状态 0 否 1 是
     *
     * @param bizPtyh
     * @return
     */
    @Override
    public ApiResponse<String> updateSfsd(BizPtyh bizPtyh) {
        SysYh sysYh = getCurrentUser();
        RuntimeCheck.ifBlank(bizPtyh.getId(), "用户Id不能为空");
        BizPtyh ptyh = findById(bizPtyh.getId());
        if (ObjectUtils.isEmpty(ptyh)) {
            return ApiResponse.fail("用户不存在");
        }
        RuntimeCheck.ifBlank(bizPtyh.getYhSfsd(), "用户锁定状态不能为空");
        if (StringUtils.containsNone(bizPtyh.getYhSfsd(), new char[]{'0', '1'})) {
            return ApiResponse.fail("请输入正确的状态");
        }
        BizPtyh newEntity = new BizPtyh();
        newEntity.setId(bizPtyh.getId());
        newEntity.setYhSfsd(bizPtyh.getYhSfsd());//用户是否锁定 ZDCLK0046 (0 否  1 是)
        newEntity.setXgsj(DateUtils.getNowTime());
        newEntity.setYhXgr(sysYh.getYhid());
        int i = update(newEntity);
        return i == 1 ? ApiResponse.success() : ApiResponse.fail();
    }

    /**
     * 更新用户是否分配信息
     *
     * @param bizPtyh
     * @return
     */
    @Override
    public ApiResponse<String> updateSffp(BizPtyh bizPtyh) {
        SysYh sysYh = getCurrentUser();
        RuntimeCheck.ifBlank(bizPtyh.getId(), "用户Id不能为空");
        BizPtyh ptyh = findById(bizPtyh.getId());
        if (ObjectUtils.isEmpty(ptyh)) {
            return ApiResponse.fail("用户不存在");
        }
        RuntimeCheck.ifBlank(bizPtyh.getYhIxySffp(), "用户是否分配不能为空");
        if (StringUtils.equals(bizPtyh.getYhIxySffp(), "0")) {
            bizPtyh.setYhFpms("");
        } else if (StringUtils.equals(bizPtyh.getYhIxySffp(), "1")) {
            if (StringUtils.isBlank(bizPtyh.getYhFpms())) {
                return ApiResponse.fail("用户分配描述不能为空");
            }
        }
        BizPtyh newEntity = new BizPtyh();
        newEntity.setId(bizPtyh.getId());
        newEntity.setYhIxySffp(bizPtyh.getYhIxySffp());
        if (StringUtils.equals(bizPtyh.getYhIxySffp(), "0")) {
            newEntity.setYhFpms("");
        } else if (StringUtils.equals(bizPtyh.getYhIxySffp(), "1")) {
            if (StringUtils.isBlank(bizPtyh.getYhFpms())) {
                return ApiResponse.fail("用户分配描述不能为空");
            }
            newEntity.setYhFpms(bizPtyh.getYhFpms());
        }
        newEntity.setXgsj(DateUtils.getNowTime());
        newEntity.setYhXgr(sysYh.getYhid());
        int i = update(newEntity);
        return i == 1 ? ApiResponse.success() : ApiResponse.fail();
    }

    /**
     * 更新学员认证状态
     *
     * @param bizPtyh
     * @return
     */
    @Override
    public ApiResponse<String> updateYhRz(BizPtyh bizPtyh) {
        SysYh sysYh = getCurrentUser();
        BizPtyh user = entityMapper.selectByPrimaryKey(bizPtyh.getId());
        if (user == null) return ApiResponse.fail("用户不存在");
        RuntimeCheck.ifTrue(!StringUtils.equals(user.getYhLx(), "1"), "操作失败，只有学员才能进行认证操作");
        RuntimeCheck.ifTrue(StringUtils.equals(user.getYhZt(), "1"), "操作失败，该学员已认证无需再次认证");
        RuntimeCheck.ifTrue(StringUtils.equals(user.getYhSfsd(), "1"), "操作失败，该学员已锁定无法进行认证操作");

        RuntimeCheck.ifBlank(bizPtyh.getYhZt(), "审核状态不能为空");
        if (StringUtils.containsNone(bizPtyh.getYhZt(), new char[]{'1', '2'})) {
            return ApiResponse.fail("请输入正确审核状态");
        }
        BizPtyh newEntity = new BizPtyh();
        newEntity.setId(user.getId());


        if(StringUtils.equals(bizPtyh.getYhZt(),"1")){//认证成功
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
                yhSsjid = pBizUser.getYhId();
            }

            //插入用户实名表  biz_user
            BizUser bizUser = new BizUser();
            bizUser.setYhId(user.getId());//用户ID
            bizUser.setYhZjhm(user.getYhZjhm());//用户证件号码
            bizUser.setYhSjhm(user.getYhZh());//用户账户
            bizUser.setYhSfjsz(user.getYhSfyjz());//设置是否有驾驶证(1:有 2:没有)
            bizUser.setYhXm(user.getYhXm());//姓名
            bizUser.setCjsj(DateUtils.getNowTime());//创建时间
            bizUser.setYhSjid(yhSjid);//设置上级ID
            bizUser.setYhSsjid(yhSsjid);//上上级ID
            int i = userMapper.insert(bizUser);
            RuntimeCheck.ifTrue(i != 1, "操作失败，请重新尝试");
            newEntity.setYhZt("1");
            newEntity.setYhZtMs(" ");
        }else{
            String yhZtMs=bizPtyh.getYhZtMs();
            RuntimeCheck.ifBlank(yhZtMs, "请填写审核失败原因。");
            newEntity.setYhZt("2");
            newEntity.setYhZtMs(yhZtMs);
        }

        int k = update(newEntity);
        return k == 1 ? ApiResponse.success() : ApiResponse.fail();

    }

    @Override
    public BizPtyh findByIdSelect(String userid) {
        BizPtyh obj = this.findById(userid);
        this.afterReturn(obj);
        return obj;
    }

//==============================================================APP端  开始===========

    /**
     * 用户注册操作
     *
     * @param entity
     * @return
     */
    @Override
    public ApiResponse<String> userEnroll(BizPtyh entity) {
        RuntimeCheck.ifBlank(entity.getYhZh(), "用户账户不能为空");

        String telIdentifying = entity.getTelIdentifying();//短信验证码
        RuntimeCheck.ifBlank(telIdentifying, "短信验证码不能为空");
        RuntimeCheck.ifBlank(entity.getYhYyyqm(), "用户应邀邀请码不能为空");

        String yhZh = entity.getYhZh();
        ApiResponse<String> validate= this.validateSms(yhZh, telIdentifying,"1");
        RuntimeCheck.ifTrue(validate.getCode()!=200,validate.getMessage());

//      用户应邀邀请码存在造假的可能。是否需要验证,这里的验证是注册下发短信时，已经查了数据库
        String app_sendSMS_yyyqm = redisDao.boundValueOps("app_sendSMS_yyyqm" + yhZh).get();
        RuntimeCheck.ifFalse(StringUtils.equals(entity.getYhYyyqm(), app_sendSMS_yyyqm), "邀请码错误，请重新注册");

        RuntimeCheck.ifBlank(entity.getYhMm(), "用户密码不能为空");
//        RuntimeCheck.ifBlank(entity.getYhXm(),"用户姓名不能为空");
//        RuntimeCheck.ifBlank(entity.getYhZjhm(),"用户证件号码不能为空");

        RuntimeCheck.ifBlank(entity.getYhLx(), "用户类型不能为空");//类型 ZDCLK0041(2、教练、1、学员)
        if (StringUtils.containsNone(entity.getYhLx(), new char[]{'1', '2'})) {
            return ApiResponse.fail("请输入正确用户类型");
        }

//        RuntimeCheck.ifBlank(entity.getYhXb(),"用户性别不能为空");
//        if(StringUtils.containsNone(entity.getYhXb(), new char[]{'1', '2'})){
//            return ApiResponse.fail("请输入正确用户性别");
//        }

//        RuntimeCheck.ifBlank(entity.getYhSfyjz(),"用户驾照状态不能为空");
//        if(StringUtils.containsNone(entity.getYhSfyjz(), new char[]{'1', '0'})){
//            return ApiResponse.fail("请输入正确用户驾照状态");
//        }

        String yhEncrypt = "";
        yhEncrypt = EncryptUtil.encryptUserPwd(entity.getYhMm());
        RuntimeCheck.ifBlank(yhEncrypt, "用户密码加密失败，用户注册失败");

        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        condition.eq(BizPtyh.InnerColumn.yhZh.name(), entity.getYhZh());
        Integer count = this.countByCondition(condition);
        RuntimeCheck.ifTrue(count > 0, "账号已存在，请更换别的登陆账号！");

//        注册类型  1、微信注册  2、支付宝注册 3、web页面注册
        String addType = entity.getAddType();
        RuntimeCheck.ifBlank(addType, "注册类型不能为空");//注册类型  1、微信注册  2、支付宝注册 3、web页面注册
        if (StringUtils.containsNone(addType, new char[]{'1', '2', '3'})) {
            return ApiResponse.fail("请输入正确注册类型");
        }
        // TODO: 2018/5/19 一定要确定，注册来源
        String yhOpenId = "";//微信OPEN_ID
        String yhAlipayId = "";//支付宝ID
        if (StringUtils.equals(addType, "1")) {
            yhOpenId = ""; // TODO: 2018/5/19 请求微信的OPEN_ID
            RuntimeCheck.ifBlank(yhOpenId, "微信唯一编号不能为空");
        } else if (StringUtils.equals(addType, "2")) {
            yhAlipayId = ""; // TODO: 2018/5/19 请求支付宝的ID
            RuntimeCheck.ifBlank(yhAlipayId, "支付宝唯一编号不能为空");
        }

        BizPtyh newEntity = new BizPtyh();
        newEntity.setId(genId());//获取ID
        newEntity.setYhZh(entity.getYhZh());//用户账户
        newEntity.setYhMm(yhEncrypt);//用户密码
        newEntity.setCjsj(DateUtils.getNowTime());//创建时间
        newEntity.setYhXm(entity.getYhXm());//姓名
        newEntity.setYhLx(entity.getYhLx());//用户类型
        newEntity.setYhXb(entity.getYhXb());//用户性别
        newEntity.setYhZjhm(entity.getYhZjhm());//用户证件号码
        newEntity.setYhZt("0");//认证状态 ZDCLK0043(0 未认证、1 已认证)
        newEntity.setDdSfjx("0");//是否缴费 ZDCLK0045 (0 未缴费 1 已缴费)
        newEntity.setYhOpenId(yhOpenId);//微信OPEN_ID
        newEntity.setYhAlipayId(yhAlipayId);//支付宝ID
        newEntity.setYhTx(entity.getYhTx());//用户头像
        newEntity.setYhBm(entity.getYhBm());//用户别名
        newEntity.setYhYyyqm(entity.getYhYyyqm());//用户应邀邀请码
        newEntity.setYhIxySffp("0");//学员是否已分配
        newEntity.setYhSfyjz(entity.getYhSfyjz());//学员是否有驾照
        newEntity.setYhSfsd("0");//用户是否锁定 ZDCLK0046 (0 否  1 是)

        int i = getBaseMapper().insertSelective(newEntity);

        return i == 1 ? ApiResponse.success() : ApiResponse.fail();
    }

    /**
     * 修改登录密码
     *
     * @param userId 用户id
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return 操作结果
     */
    @Override
    public ApiResponse<String> mdfPwd(String userId, String oldPwd, String newPwd) {

        BizPtyh user = entityMapper.selectByPrimaryKey(userId);
        String newEncrypt;
        if (user == null) return ApiResponse.fail("用户不存在");
        try {
            String encrypt = Des.encrypt(oldPwd);
            if (!encrypt.equals(user.getYhMm())) {
                return ApiResponse.fail("密码错误");
            }
            newEncrypt = Des.encrypt(newPwd);
        } catch (Exception e) {
            return ApiResponse.fail("加密失败");
        }
        user.setYhMm(newEncrypt);
        entityMapper.updateByPrimaryKeySelective(user);
        return ApiResponse.success();
    }

    /**
     * 微信登录
     *
     * @param openId 获得用户的OPEN_ID
     */
    @Override
    public ApiResponse<Map<String, Object>> wxlogin(String openId) {
        // TODO: 2018/5/19 调试模式。
        if (debugTest != null) {//调试
            openId = "aaaaaaa";
        }

        Example condition = new Example(BizPtyh.class);
        condition.and().andEqualTo(BizPtyh.InnerColumn.yhOpenId.name(), openId);
        List<BizPtyh> existUser = this.findByCondition(condition);
        Map<String, Object> rMap = new HashMap<>(2);
        ApiResponse<Map<String, Object>> result = new ApiResponse<>();
        if (existUser != null && existUser.size() > 0) {
            BizPtyh item = existUser.get(0);
            RuntimeCheck.ifTrue(!"1".equals(item.getYhSfsd()), "用户已禁用！");
            try {
                String token = JwtUtil.createToken(item.getId(), item.getYhXm());
                redisDao.boundValueOps(item.getId()).set(token, 1, TimeUnit.DAYS);
                redisDao.boundValueOps(item.getId() + "-appUserInfo").set(mapper.writeValueAsString(item), 1, TimeUnit.DAYS);
                AccessToken aToken = new AccessToken();
                aToken.setUserId(item.getId());
                aToken.setUsername(item.getYhBm());//用户别名
                aToken.setToken(token);

                rMap.put("accessToken", aToken);
                result.setResult(rMap);
            } catch (Exception e) {
                result.setCode(ApiResponse.FAILED);
                result.setMessage("用户登陆失败，请重试！");
            }
        } else {
            result.setCode(203);
            result.setMessage("该微信用户未注册，请您注册后使用系统！");
        }

        return result;
    }

    /**
     * 用户头像，别名 修改
     *
     * @param entity
     * @return
     */
    @Override
    public ApiResponse<String> updateUserInfo(BizPtyh entity) {
        BizPtyh user = getAppCurrentUser();
        if (user == null) return ApiResponse.fail("用户不存在");
        BizPtyh newEntity = new BizPtyh();
        newEntity.setId(user.getId());
        if (StringUtils.isNotEmpty(entity.getYhTx())) {
            newEntity.setYhTx(entity.getYhTx());
        }
        if (StringUtils.isNotEmpty(entity.getYhBm())) {
            newEntity.setYhBm(entity.getYhBm());
        }
        int i = update(newEntity);
        return i == 1 ? ApiResponse.success() : ApiResponse.fail();
    }

    /**
     * 用户实名操作
     *
     * @param entity yhXm     用户姓名
     *               yhZjhm     用户证件号码
     *               yhXb     用户性别
     *               yhSfyjz     用户驾照状态不能为空
     *               imgList 以,进行分隔
     *               imgTypeList 以,进行分隔
     *               <p>
     *               1、证件照片上传文件表  biz_wj
     *               2、修改用户表 biz_ptyh
     *               <p>
     *               下面两步是在后台审核时操作的。
     *               3、上传实名表 biz_user
     *               4、上传定单表  biz_order
     * @return
     */
    @Override
    public ApiResponse<String> updateUserReal(BizPtyh entity) {
        BizPtyh userRequest = getAppCurrentUser();
        if (userRequest == null) return ApiResponse.fail("用户不存在");

        RuntimeCheck.ifBlank(entity.getYhXm(), "用户姓名不能为空");
        RuntimeCheck.ifBlank(entity.getYhZjhm(), "用户证件号码不能为空");
        RuntimeCheck.ifBlank(entity.getYhXb(), "用户性别不能为空");
        if (StringUtils.containsNone(entity.getYhXb(), new char[]{'1', '2'})) {
            return ApiResponse.fail("请输入正确用户性别");
        }

        RuntimeCheck.ifBlank(entity.getYhSfyjz(), "用户驾照状态不能为空");
        if (StringUtils.containsNone(entity.getYhSfyjz(), new char[]{'1', '0'})) {
            return ApiResponse.fail("请输入正确用户驾照状态");
        }


        BizPtyh user = entityMapper.selectByPrimaryKey(userRequest.getId());
        if (user == null) {
            return ApiResponse.fail("用户不存在");
        }
        if (StringUtils.equals(user.getYhZt(),"1")) {
            return ApiResponse.fail("用户已实名认证成功，无需此操作");
        }
        if (StringUtils.equals(user.getYhSfsd(), "1")) {
            return ApiResponse.fail("用户已锁定，无法进行操作");
        }
        if (StringUtils.isEmpty(entity.getImgList())) {
            return ApiResponse.fail("请上传证件照片");
        }
        if (StringUtils.isEmpty(entity.getImgTypeList())) {
            return ApiResponse.fail("请上传证件照片属性");
        }

        String yhzjhm = entity.getYhZjhm();
        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        condition.eq(BizPtyh.InnerColumn.yhZjhm.name(), yhzjhm);
        List<BizPtyh> listCount = this.findByCondition(condition);
        if (listCount != null && listCount.size() > 0) {
            RuntimeCheck.ifTrue(true, "该证件号已与手机号" + listCount.get(0).getYhZh() + "关联，请更换新的证件号！");
        }

        String[] imgList = StringUtils.split(entity.getImgList(), ",");
        String[] imgTypeList = StringUtils.split(entity.getImgTypeList(), ",");

        List<BizWj> wjList = new ArrayList<BizWj>();
        if (imgList != null) {
            if (imgList.length != imgTypeList.length && imgList.length>0) {
                return ApiResponse.fail("证件数据和证件属性数据不同");
            }
            for (int i = 0; i < imgList.length; i++) {
                BizWj wj = new BizWj();
                wj.setId(genId());
                wj.setYhId(user.getId());//
                wj.setWjTpdz(imgList[i]);//
                wj.setWjSx(imgTypeList[i]);
                wj.setWjSbzt("0");
                wj.setCjsj(DateUtils.getNowTime());
                wj.setWjSfyx("1");
                wjList.add(wj);
            }
        }
        //TODO
        if (wjList.size() > 0) {
            wjMapper.deleteBatch(user.getId());
            wjMapper.insertBatch(wjList);
        }

        BizPtyh newEntity = new BizPtyh();
        newEntity.setId(user.getId());
        newEntity.setYhXm(entity.getYhXm());//用户姓名
        newEntity.setYhZjhm(entity.getYhZjhm());//用户证件号码
        newEntity.setYhXb(entity.getYhXb());//用户性别
        newEntity.setYhSfyjz(entity.getYhSfyjz());//用户驾照状态不能为空

        int i = update(newEntity);
        return i == 1 ? ApiResponse.success() : ApiResponse.fail();
    }

    /**
     * 我的邀请码
     * 用户缴费成功后，为用户生成邀请码，未缴费引导用户缴费。
     *
     * @return
     */
    public BizPtyh getUserInvitationCode(String id) {
        BizPtyh user = this.findByIdSelect(id);
        RuntimeCheck.ifTrue(user == null, "用户资料有误！");
        RuntimeCheck.ifTrue(StringUtils.equals(user.getYhZt(), "0"), "用户还未认证，请您认证！");//认证状态 ZDCLK0043(0 未认证、1 已认证)
//        RuntimeCheck.ifTrue(StringUtils.equals(user.getDdSfjx(),"0"),"用户还未缴费，请您缴费！");//是否缴费 ZDCLK0045 (0 未缴费 1 已缴费)
        return user;
    }

    /**
     * 用户申请成为教练
     * @param bizJl
     * @return
     */
    @Override
    public ApiResponse<String> updatelx(BizJl bizJl) {
        BizPtyh bizPtyh = getAppCurrentUser();

        BizJl b = jlService.findById(bizPtyh.getId());
        RuntimeCheck.ifTrue(b !=null , "该用户已经提交申请");

        RuntimeCheck.ifTrue(StringUtils.equals(bizPtyh.getYhLx(), "2"), "该用户已经是教练");

        RuntimeCheck.ifBlank(bizJl.getYhXm(), "用户姓名不能为空");
        RuntimeCheck.ifBlank(bizJl.getYhZjhm(), "用户身份号码不能为空");

        SimpleCondition condition = new SimpleCondition(BizJl.class);
        condition.eq(BizJl.InnerColumn.yhZjhm.name(),bizJl.getYhZjhm());
        List<BizJl> bizJls = jlService.findByCondition(condition);
        if(CollectionUtils.isNotEmpty(bizJls)){
            return ApiResponse.fail("该身份证已经与其他用户关联");
        }

        RuntimeCheck.ifBlank(bizJl.getYhSjhm(), "手机号码不能为空");
        RuntimeCheck.ifBlank(bizJl.getJlJl(), "教练驾龄不能为空");
        RuntimeCheck.ifBlank(bizJl.getJlQu(), "教练所属区域不能为空");
        RuntimeCheck.ifBlank(bizJl.getJlZml(), "教练证明人不能为空");
        RuntimeCheck.ifBlank(bizJl.getJlJjlxr(), "教练紧急联系人不能为空");
        RuntimeCheck.ifBlank(bizJl.getJlJjlxrdh(), "教练紧急联系人电话不能为空");
        RuntimeCheck.ifBlank(bizJl.getJlZz(), "住址不能为空");

        // 更新用户信息为教练 ，未认证
        BizPtyh ptyh = new BizPtyh();
        ptyh.setId(bizPtyh.getId());
        ptyh.setYhLx("2"); // 2 为教练 1 为学员
        ptyh.setYhZt("0"); // 0 为 未认证  1 为已认证
        ptyh.setYhSfyjz("1"); // 0 没有驾照 1 有驾照
        update(ptyh);

        bizJl.setYhId(bizPtyh.getId());

        jlService.save(bizJl);



        return ApiResponse.success();
    }

    /**
     * 根据条件分页搜索已经认证的教练
     * @param name
     * @param phone
     * @param area
     * @param
     * @return
     */
    @Override
    public ApiResponse<List<BizPtyh>> getCoaches(String name, String phone, String area, int pageNum, int pageSize) {
        List<BizPtyh> list = new ArrayList<>();
        // 若三个条件都为空 分页查询所有已经认证的教练
        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        condition.eq(BizPtyh.InnerColumn.yhZt.name(), "1"); // 0 未认证 1 已认证
        condition.eq(BizPtyh.InnerColumn.yhLx.name(), "2"); // 1 学员 2 教练
        PageHelper.startPage(pageNum, pageSize);

        if (StringUtils.isEmpty(name) && StringUtils.isEmpty(phone) && StringUtils.isEmpty(area)) {

            List<BizPtyh> bizPtyhs = this.findByCondition(condition);
            bizPtyhs.stream().forEach(bizPtyh -> {
                list.add(afterReturns(bizPtyh));
            });
            return ApiResponse.success(list);
        }

        if (StringUtils.isNotBlank(name)) {
            condition.like(BizPtyh.InnerColumn.yhXm.name(), "%" + name + "%");
        }
        if (StringUtils.isNotBlank(phone)) {
            condition.like(BizPtyh.InnerColumn.yhZh.name(), "%" + phone + "%");
        }
        if(StringUtils.isNotBlank(area)){

            SimpleCondition jlCondition = new SimpleCondition(BizJl.class);
            //  根据区域查出用户 id
            jlCondition.like(BizJl.InnerColumn.jlQu.name(), "%" + area + "");
            List<BizJl> bizJls = jlService.findByCondition(jlCondition);
            // 拿到所在区域的所有教练的 id
            List<String> jlIds = bizJls.stream().map(BizJl::getYhId).collect(Collectors.toList());
           List<BizPtyh> bizPtyhList =  entityMapper.getJls(name, phone, jlIds);
            bizPtyhList.stream().forEach(bizPtyh -> {
                list.add(afterReturns(bizPtyh));
            });
           return ApiResponse.success(list);
        }

        List<BizPtyh> ptyhs =   this.findByCondition(condition);
        ptyhs.stream().forEach(bizPtyh -> {
            list.add(afterReturns(bizPtyh));
        });
        return ApiResponse.success(list);
    }

    @Override
    public ApiResponse<List<String>> assignStudents(String yhId, String jlId) {

        BizPtyh users=this.findById(jlId);
        RuntimeCheck.ifTrue(ObjectUtils.isEmpty(users), "该用户不存在");

        RuntimeCheck.ifFalse(StringUtils.equals(users.getYhLx(),"2"),"教练信息有误，请核实后再操作");
        RuntimeCheck.ifFalse(StringUtils.equals(users.getYhZt(),"1"),"该教练未进行实名认证");

        // 将多个学员id 分开
        List<String> sIds = Arrays.asList(yhId.split(","));
        // 可以分配的用户 id
        List<String> ids = userService.getYhIds(sIds);

        // 进行分配操作
        if(CollectionUtils.isNotEmpty(ids)) {
            userService.updateJlId(ids, jlId);
            entityMapper.updateJlFp(ids,"该学员于："+DateUtils.getNowTime()+" 分配给教练员："+users.getYhXm()+"");
        }

        return ApiResponse.success(ids);
    }
    /**
     * 下发短信
     * @param tel    手机号码
     * @param type        1、注册  2、重置密码
     * @param identifyingCode     验证码
     *  @return
     */
    @Override
   public boolean sendSMS(String tel, int type,  String identifyingCode) {
        boolean ret = false;
        if (StringUtils.isEmpty(identifyingCode)) {
            identifyingCode = StringDivUtils.getSix();//获取验证码
        }

        String redisKey = "";
        if (type == 1) {
            redisKey = appSendSMSRegister;
            //使用注册模板下发
        } else if (type == 2) {
            redisKey = appSendSMSResetting;
            //使用重置密码模板进行下发
        } else {
            //类型不存在，不能下发
            return false;
        }
//        查询当前KEY过期时间还有多少秒 超过120秒后，可以再次下发短信
        long identifying = redisDao.getExpire(redisKey + tel, TimeUnit.SECONDS);
        if (identifying != -1 && 24 * 60 * 60 - identifying < 120) {
            return true;
        }
        redisDao.boundValueOps(redisKey+tel).set(identifyingCode, 1, TimeUnit.DAYS);//设备验证码，为一天过期
        ret=true;
        return  ret;
    }


    /**
     * 根据当前用户显示相关人员的列表
     * @return
     */
    @Override
    public ApiResponse<List<BizPtyh>> getBizPtyhList(int pageNum, int pageSize) {
        List<BizPtyh> list = new ArrayList<>();
        // 获取当前登录用户
        BizPtyh user = getAppCurrentUser();
        // 鉴定该用户为 教练还是学员
        if(StringUtils.equals(user.getYhLx(), "1")){ // 用户为学员 展示其教练信息

            BizUser bizUser = userService.findById(user.getId());
            RuntimeCheck.ifTrue(ObjectUtils.isEmpty(bizUser),"学员信息不存在");
            SimpleCondition condition = new SimpleCondition(BizPtyh.class);
            condition.eq(BizPtyh.InnerColumn.id.name(), bizUser.getYhJlid());
            List<BizPtyh> bizPtyhs = findByCondition(condition);

            BizJl bizJl = jlService.findById(bizUser.getYhJlid());
            RuntimeCheck.ifTrue(ObjectUtils.isEmpty(bizJl), "该用户的教练未进行认证");
            if(CollectionUtils.isNotEmpty(bizPtyhs)) {
                bizPtyhs.stream().forEach(
                        bizPtyh -> {
                            BizPtyh ptyh = afterReturns(bizPtyh);
                            ptyh.setYhMm(bizJl.getJlMs()); // 用户表中没有教练简介 ， 将 密码字段设置为 教练的简介
                            list.add(ptyh);
                        }
                );
            }
            return ApiResponse.success(list);

        }else if(StringUtils.equals(user.getYhLx(), "2")) { // 用户为教练 ， 需要展示其学员列表

            SimpleCondition condition = new SimpleCondition(BizUser.class);
            condition.eq(BizUser.InnerColumn.yhJlid.name(), user.getId());
            List<BizUser> bizUsers = userService.findByCondition(condition);

            // 获取所有学员的 id
            List<String> yhIds = bizUsers.stream().map(BizUser::getYhId).collect(Collectors.toList());
            SimpleCondition yhCondition = new SimpleCondition(BizPtyh.class);
            yhCondition.in(BizPtyh.InnerColumn.id.name(), yhIds);
            yhCondition.eq(BizPtyh.InnerColumn.yhSfyjz.name(), "0"); // 查询学员中无驾照的
            PageHelper.startPage(pageNum,pageSize);
            List<BizPtyh> ptyhs = findByCondition(yhCondition);
            if(CollectionUtils.isNotEmpty(ptyhs)){
                ptyhs.stream().forEach(bizPtyh -> {
                    list.add(afterReturns(bizPtyh));
                });
            }

            return ApiResponse.success(list);
        }

        return ApiResponse.success(list);
    }

    /**
     * 用户重置密码
     * @param tel
     * @param code
     * @param newPwd
     * @return
     */
    @Override
    public ApiResponse<String> resetPwd(String tel, String code, String newPwd) {

        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        condition.eq(BizPtyh.InnerColumn.yhZh.name(), tel);
        List<BizPtyh> ptyhList = findByCondition(condition);
        RuntimeCheck.ifEmpty(ptyhList, "该账号尚未注册");

        // 验证短信验证码是否正确
        ApiResponse<String> sms = validateSms(tel, code, "2");

        RuntimeCheck.ifTrue(sms.getCode()!=200,sms.getMessage());



            BizPtyh newEntity = new BizPtyh();
            newEntity.setId(ptyhList.get(0).getId());

            // 重置密码加密
            String userPwd = EncryptUtil.encryptUserPwd(newPwd);
            RuntimeCheck.ifBlank(userPwd , "重置密码出错，请重新申请");
            newEntity.setYhMm(userPwd);
            int i = update(newEntity);
            return i==1?ApiResponse.success():ApiResponse.fail("重置失败");


    }

    /**
     * 当前登录学员修改驾照信息
     * @return
     */
    @Override
    public ApiResponse<String> updateJz(BizPtyh ptyh) {

        BizPtyh bizPtyh = getAppCurrentUser();
        RuntimeCheck.ifTrue(StringUtils.containsNone(ptyh.getYhSfyjz(),new char[]{'0','1'}), "学员驾照信息输入有误");
        RuntimeCheck.ifTrue(StringUtils.equals(bizPtyh.getYhSfyjz(),ptyh.getYhSfyjz()), "没有修改信息");
        BizPtyh entity = new BizPtyh();
        entity.setId(bizPtyh.getId());
        entity.setYhSfyjz(ptyh.getYhSfyjz());
        int i = update(entity);

        return i==1?ApiResponse.success():ApiResponse.fail("更新失败");
    }


    /**
     * 短信验证
     * @param tel    手机号码
     *@param identifyingCode    验证码
     *@param type     1、注册  2、重置密码
     *
     * @return
     */
    @Override
    public ApiResponse<String> validateSms(String tel, String identifyingCode,String type) {
        if(StringUtils.isEmpty(identifyingCode)){
            return ApiResponse.fail("验证码不能为空");
        }
        if(StringUtils.isEmpty(tel)){
            return ApiResponse.fail("手机号码不能为空");
        }
        String redisKey="";
        if(StringUtils.equals(type,"1")){//
            redisKey=appSendSMSRegister;
        }else if(StringUtils.equals(type,"2")){//重置密码
            redisKey=appSendSMSResetting;
        }else{
            return ApiResponse.fail("验证失败");
        }

        String identifying = redisDao.boundValueOps(redisKey + tel).get();
        if(StringUtils.equals(identifying,identifyingCode)){
            return ApiResponse.success();
        }else{
            return ApiResponse.fail("验证码验证失败");
        }
    }
//
    /*public static void main(String[] args) {
        List<String> sids = new ArrayList<>();
        sids.add("1");
        sids.remove("1");
    }*/
    @Override
    public ApiResponse<String> validateCode(String code){
        SimpleCondition newCondition = new SimpleCondition(BizPtyh.class);
        newCondition.eq(BizPtyh.InnerColumn.yhZsyqm.name(), code);
        List<BizPtyh> bizPtyhsList = ptyhService.findByCondition(newCondition);
        if (bizPtyhsList == null) return ApiResponse.fail("邀请码不存在!");
        if (bizPtyhsList.size() != 1) return ApiResponse.fail("邀请码不存在!");
        if(!StringUtils.equals(bizPtyhsList.get(0).getYhZt(),"1")) return ApiResponse.fail("邀请码不存在!");
        if(!StringUtils.equals(bizPtyhsList.get(0).getDdSfjx(),"1")) return ApiResponse.fail("邀请码不存在!");
        if(!StringUtils.equals(bizPtyhsList.get(0).getYhSfsd(),"0")) return ApiResponse.fail("邀请码已经锁定，不能邀请您!");
        return ApiResponse.success();
    }
}
