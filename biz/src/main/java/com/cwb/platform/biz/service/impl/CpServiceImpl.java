package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.app.service.impl.AppOrderServiceImpl;
import com.cwb.platform.biz.mapper.BizCpMapper;
import com.cwb.platform.biz.mapper.BizPtyhMapper;
import com.cwb.platform.biz.model.BizCp;
import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.service.CpService;
import com.cwb.platform.biz.wxpkg.service.WechatService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.commonUtil.MathUtil;
import com.cwb.platform.util.commonUtil.StringDivUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class CpServiceImpl extends BaseServiceImpl<BizCp,String> implements CpService {
    @Value("${order_money}")
    private String orderMoney;

    @Autowired
    private BizCpMapper entityMapper;
    @Autowired
    private StringRedisTemplate redisDao;

    @Autowired
    private BizPtyhMapper ptyhMapper;

    @Autowired
    private WechatService wechatService;

    @Autowired
    private AppOrderServiceImpl appOrderService;



    @Override
    protected Mapper<BizCp> getBaseMapper() {
        return entityMapper;
    }

    /**
     * 查询一个类型的产品
     * @param cpType    产品类型（1、学费  2、补考费）必填
     * @return
     */
    public ApiResponse<BizCp> getCpTyet(String cpType){
        BizCp bizCp=entityMapper.getCpTyetList(cpType);
        return ApiResponse.success(bizCp);
    }
    /**
     * 查询一个产品列表
     * @return
     */
    public ApiResponse<List<BizCp>> getCpTyetList(){
        BizPtyh user=getAppCurrentUser(false);
        BizCp queryBizCp=new BizCp();
        queryBizCp.setCpYx("1");

        SimpleCondition condition = new SimpleCondition(BizCp.class);
        condition.eq(BizCp.InnerColumn.cpYx, "1");
        condition.setOrderByClause(BizCp.InnerColumn.cpType.asc());
        List<BizCp> list=this.findByCondition(condition);

        List<BizCp> retCpList=list;
        if(user!=null&&list!=null&&list.size()>0){
            String userId=user.getId();
            BizPtyh userSelect = ptyhMapper.selectByPrimaryKey(userId);
            SimpleCondition orderCondition = new SimpleCondition(BizOrder.class);
            orderCondition.eq(BizOrder.InnerColumn.yhId.name(), userId);
            orderCondition.eq(BizOrder.InnerColumn.ddZt.name(), "2");//订单状态(1、待缴费 2、已缴费 3、已退费)
            orderCondition.eq(BizOrder.InnerColumn.ddZfzt.name(), "1");//支付状态（0,待支付 1、支付成功  2、支付失败）
            orderCondition.and().andCondition(" CP_ID IN (SELECT ID FROM biz_cp WHERE CP_TYPE='1') ");
            Integer count = appOrderService.countByCondition(orderCondition);
            String ykzt=userSelect.getYhXyYkType();//学员约考状态

            if(count>0&&(!StringUtils.equals(ykzt,"41"))){
                retCpList=new ArrayList<>();
//                移除所有会员的产品
                for(BizCp cp:list){
                    if(!StringUtils.equals(cp.getCpType(),"1")){
                        retCpList.add(cp);
                    }
                }
            }
        }
        return ApiResponse.success(retCpList);
    }
    /**
     * 后台-新增产品
     * @param entity
     * @return
     */
    public ApiResponse<String> saveCp(BizCp entity){
    //1、参数验证
        SysYh sysYh = getCurrentUser();
        RuntimeCheck.ifBlank(entity.getCpMc(),"费用名称不能为空");
        RuntimeCheck.ifBlank(entity.getCpType(),"收费类型不能为空");
        RuntimeCheck.ifBlank(entity.getCpYj(),"请确定该产品分佣状态");
        Double cpJl=  MathUtil.stringToDouble(entity.getCpJl());//产品金额
        RuntimeCheck.ifFalse(cpJl>0,"收费金额不能小于0");
        if(entity.getCpType().equals("1")) {
            RuntimeCheck.ifFalse((cpJl / 100) >= MathUtil.stringToDouble(orderMoney), "收费金额不能低于系统配置的最低价：" + orderMoney);
        }
        Double cpYjyjs=0.00;
        Double cpRjyjs=0.00;
        if(StringUtils.equals(entity.getCpYj(),"1")){
            Double cpYjyj= entity.getCpYjyj(); //new BigDecimal(entity.getCpYjyj()).doubleValue();//设置一级佣金
            Double cpRjyj=entity.getCpRjyj();//设置二级佣金
            RuntimeCheck.ifTrue(cpYjyj+cpRjyj>cpJl,"分佣金额不能大于总金额");//
            cpYjyjs=cpYjyj;
            cpRjyjs=cpRjyj;
        }
//    //2、将该类型所有产品设置为无效
//        entityMapper.updateTypeDelete(entity.getCpType());//更新类型为无效
    //3、将新记录插入表中
        BizCp newBizCp=new BizCp();
        newBizCp.setId(genId());
        newBizCp.setCpMc(entity.getCpMc());//设置产品名称
        newBizCp.setCpType(entity.getCpType());//设置产品类型（1、学费  2、补考费）必填
        newBizCp.setCpJl(cpJl.toString());//设置产品总金额
        newBizCp.setCpYj(entity.getCpYj());//设置是否分佣(0不分 1分佣)
        newBizCp.setCpYjyj(cpYjyjs);//设置一级佣金
        newBizCp.setCpRjyj(cpRjyjs);//设置二级佣金
        newBizCp.setCpYx("0");//设置产品是否有效(0、无效 1、生效)
        newBizCp.setCjsj(DateUtils.getNowTime());//设置创建时间
        newBizCp.setCjr(sysYh.getYhid());//设置创建人

//        将上一条产品JSON回写到新的里面
        SimpleCondition condition = new SimpleCondition(BizCp.class);
        condition.eq(BizCp.InnerColumn.cpYx, "1");
        condition.eq(BizCp.InnerColumn.cpType, entity.getCpType());
        List<BizCp> cpList=this.findByCondition(condition);
        if(cpList!=null&&cpList.size()>0){
            newBizCp.setCpXyJson(cpList.get(0).getCpXyJson());
        }


       int i= entityMapper.insert(newBizCp);
        if(i == 1){ // 保存成功 ， 生成验证码
            String code1 =  StringDivUtils.getSix();
            String code2 = StringDivUtils.getSix();
            redisDao.boundValueOps("cp-SMS-"+newBizCp.getId()).set(code1 + "," + code2,1,TimeUnit.DAYS);
            // 调用短信发送接口
           /* SendSmsUtil.sendSmsMap();*/

            //尊敬的${userName},操作员:${operator}于${dates}修改了${parameter},本次操作验证码是${code},请及时授权处理
            List<Map<String, String>> smsMapList=new ArrayList<Map<String, String>>();
            String dates=DateUtils.getDateStr(new Date(), "MM月dd日HH时mm分ss分");
            String cpTypes=entity.getCpType();
            String cpName="";
            if(StringUtils.equals(cpTypes,"1")){
                cpName="学费";
            }else if(StringUtils.equals(cpTypes,"3")){
                cpName="会员";
            }
            cpJl.toString();
            String fyString="无";
            if(StringUtils.equals(entity.getCpYj(),"1")){
                fyString+="分，一级"+(entity.getCpYjyj()/100)+"二级："+(entity.getCpRjyj()/100) ;
            }else {
                fyString+="不分";
            }
            fyString=""; // TODO: 2018/7/2 生产环境时需要去掉
            String parameter=fyString;
            Map<String, String> smsMap = new HashMap<String, String>();
            smsMap.put("phoneNumbers", "15827013808");//  李总电话 ‭15927183566‬ 电话号码  todo 李总的电话
            smsMap.put("templateType", "5");//给专员下发短信
            smsMap.put("userName", "李总");//姓名
            smsMap.put("operator", sysYh.getXm());
            smsMap.put("dates", dates);
            smsMap.put("cpName", entity.getCpMc());
            smsMap.put("cpType", cpName);
            smsMap.put("cpMoney", (cpJl/100)+"");
            smsMap.put("parameter", parameter);
            smsMap.put("code", code1);
            smsMapList.add(smsMap);
            smsMap = new HashMap<String, String>();
            smsMap.put("phoneNumbers", "15927183566");//  todo 刘总的电话
            smsMap.put("templateType", "5");//给专员下发短信
            smsMap.put("userName", "刘总");//姓名
            smsMap.put("operator", sysYh.getXm());
            smsMap.put("dates", dates);
            smsMap.put("parameter", parameter);
            smsMap.put("code", code2);
            smsMap.put("cpName", entity.getCpMc());
            smsMap.put("cpType", cpName);
            smsMap.put("cpMoney", (cpJl/100)+"");
            smsMapList.add(smsMap);
            try {
                wechatService.sendTemplateMsg(null,smsMapList);
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        }else{
            return ApiResponse.fail("修改失败，请重新操作");
        }
        ApiResponse<String> res = new ApiResponse<>();
        res.setMessage("操作成功");
        res.setResult(newBizCp.getId());
        return res;
    }

    /**
     * 验证短信验证号码
     * @param cpId
     * @return
     */
    @Override
    public ApiResponse<String> validateCSMS(String cpId, String code1, String code2) {
        SysYh user = getCurrentUser();
//        1、非空验证
        RuntimeCheck.ifBlank(cpId , "产品id不能为空");
        RuntimeCheck.ifNull(code1,"李总验证码不能为空");
        RuntimeCheck.ifNull(code2, "刘总验证码不能为空");
//2、验证号码验证
        String codes=redisDao.boundValueOps("cp-SMS-"+cpId).get();//获取验证号码。
        RuntimeCheck.ifTrue(StringUtils.isBlank(codes),"获取验证码失败，请重新尝试");//这里是没有获取到reis中的验证码
        String [] codeArray=codes.split(",");
        RuntimeCheck.ifTrue(codeArray==null||codeArray.length<1,"获取验证码失败，请重新尝试");//这里是没有获取到reis中的验证码

        RuntimeCheck.ifTrue(!StringUtils.equals(codeArray[0],code1) , "李总验证码不正确");
        RuntimeCheck.ifTrue(!StringUtils.equals(codeArray[1],code2) , "刘总验证码不正确");
//        3、该产品的验证
        BizCp cp = this.findById(cpId);
        RuntimeCheck.ifTrue(cp==null , "产品id不存在");
        RuntimeCheck.ifFalse(!StringUtils.equals(cp.getCpYx(),"1") , "该产品已经验证通过，暂不需要验证");//获取产品是否有效(0、无效 1、生效)



        entityMapper.updateTypeDelete(cp.getCpType());//更新类型为无效

        BizCp updateCp=new BizCp();
        updateCp.setId(cpId);
        updateCp.setCpYx("1");
//        updateCp.setCpYz("1");
        int i=this.update(updateCp);

        if(i == 1){ // 保存成功 ，清除redis
            redisDao.delete("cp-SMS-"+cpId);
        }else{
            return ApiResponse.fail("修改失败，请重新操作");
        }
        return ApiResponse.success();
    }

}
