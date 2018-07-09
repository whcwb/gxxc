package com.cwb.platform.biz.app.service.impl;


import com.cwb.platform.biz.app.service.AppOrderService;
import com.cwb.platform.biz.mapper.BizOrderMapper;
import com.cwb.platform.biz.mapper.BizPtyhMapper;
import com.cwb.platform.biz.model.BizCp;
import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.biz.service.impl.CpServiceImpl;
import com.cwb.platform.biz.service.impl.PtyhServiceImpl;
import com.cwb.platform.biz.service.impl.UserServiceImpl;
import com.cwb.platform.biz.util.ImgUtil;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.*;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.pagehelper.PageInfo;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

//import java.math.BigDecimal;

@Service
public class AppOrderServiceImpl extends BaseServiceImpl<BizOrder,String> implements AppOrderService {

    Logger payInfo = LoggerFactory.getLogger("access_info");
    @Value("${wechat.pay.trade_type}")
    private String tradeType;
    @Value("${wechat.pay.appId}")
    private String appid;

    @Value("${wechat.pay.mchKey}")
    private String mchKey;

    @Value("${wechat.pay.notify_url}")
    private String notifyUrl;

    @Value("${userAgreementPath}")
    private String userAgreementPath;
    @Value("${staticPath}")
    private String staticPath;

    @Autowired
    private BizOrderMapper entityMapper;

    @Autowired
    private BizPtyhMapper ptyhMapper;

    @Autowired
    private PtyhServiceImpl ptyhService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CpServiceImpl cpService;

    @Resource(name = "wxPayService")
    private WxPayService wxService;

    AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(1));
    public AppOrderServiceImpl() {
        eventBus.register(this);
    }

    @Override
    protected Mapper<BizOrder> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizOrder.class;
    }

    /**
     * 分页补充   按全部、已付款、待付款 来进行查询
     * @param condition
     *
     * @return
     */
    public boolean fillPagerCondition(LimitedCondition condition){
        BizPtyh user=getAppCurrentUser();
        RuntimeCheck.ifNull(user,"用户不存在");
        String userId=user.getId();

//        condition.and().andEqualTo(BizOrder.InnerColumn.yhSjid.name(),userId);
//        condition.or().andEqualTo(BizOrder.InnerColumn.yhSsjid.name(),userId);
        condition.and().andCondition(" ( YH_SJID='"+userId+"' OR YH_SSJID='"+userId+"') ");
        return true;
    }

    @Override
    protected void afterPager(PageInfo<BizOrder> resultPage){
        BizPtyh user=getAppCurrentUser();
        RuntimeCheck.ifNull(user,"用户不存在");
        String userId=user.getId();

        List<BizOrder> list = resultPage.getList();
        if(CollectionUtils.isNotEmpty(list)){
            List<String > ids = list.stream().map(BizOrder::getYhId).collect(Collectors.toList());

            SimpleCondition condition = new SimpleCondition(BizPtyh.class);
            condition.in(BizPtyh.InnerColumn.id.name(),ids);
            List<BizPtyh> userwjs = ptyhMapper.selectByExample(condition);

            Map<String,BizPtyh> userMap = userwjs.stream().collect(Collectors.toMap(BizPtyh::getId,p->p));

            for(BizOrder order:list){
                String orderUserId=order.getYhId();
                if (!userMap.containsKey(orderUserId))continue;
                BizPtyh zdlm = userMap.get(orderUserId);
                order.setUserDetail(ptyhService.afterReturns(zdlm));

                if(StringUtils.equals(userId,order.getYhSjid())){
                    order.setUserGrade("1");
                }else{
                    order.setUserGrade("2");
                }

            }
        }
        return;
    }
    public ApiResponse<Map<String,String>> saveAddOrder(BizOrder entity,HttpServletRequest request){
        BizPtyh user=getAppCurrentUser();
        RuntimeCheck.ifNull(user,"用户不存在");
        Object payMessage=null;//支付明细

        Map<String,String> ret=new HashMap<String,String>();
        //获取支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
        RuntimeCheck.ifNull(entity.getDdZftd(),"您好，请确定支付方式");
        if(StringUtils.containsNone(entity.getDdZftd(), new char[]{'1', '2','3','4'})){
            RuntimeCheck.ifTrue(true,"您好，请输入确定支付方式");
        }
        if(entity.getDdZftd().equals(("2"))){
            String openId=request.getHeader("openid");
            RuntimeCheck.ifTrue(StringUtils.isEmpty(openId),"支付创建失败，请稍后尝试");
            entity.setOpenId(openId);
        }
        //验证产品Id，是否有效
        String cpId=entity.getCpId();//产品id(BIZ_CP)
        RuntimeCheck.ifNull(cpId,"您好，请确定支付产品");
        BizCp bizCp=cpService.findById(cpId);
        RuntimeCheck.ifNull(bizCp,"您好，产品信息有误，请重新尝试");
        String cpYx=bizCp.getCpYx();//获取产品是否有效(0、无效 1、生效)
        RuntimeCheck.ifFalse(StringUtils.equals("1",cpYx),"您好，产品信息无效，请重新尝试");
//        String cpSh=bizCp.getCpSh();//产品审核(0待审核 1、审核通过 2、审核驳回)
//        RuntimeCheck.ifFalse(StringUtils.equals("1",cpSh),"您好，产品未审核，请重新尝试");

        String userId=user.getId();
        BizPtyh userSelect = ptyhMapper.selectByPrimaryKey(userId);
        RuntimeCheck.ifNull(userSelect,"用户不存在");
        RuntimeCheck.ifFalse(StringUtils.equals(userSelect.getYhZt(),"1"),"您好，请您上传证件或等待管理员对您资料进行认证！");//认证状态 ZDCLK0043(0 未认证、1 已认证)
//        RuntimeCheck.ifTrue(StringUtils.equals(userSelect.getDdSfjx(),"1"),"您已支付成功，无需再次支付！");//获取是否缴费(0无 1已缴费) todo 让用户可以重复支付
        RuntimeCheck.ifTrue(StringUtils.equals(userSelect.getYhSfsd(),"1"),"您已经锁定，无法支付。请联系管理人员进行解锁！");//用户是否锁定 ZDCLK0046 (0 否  1 是)  0是没有锁定 1是已锁定
        // TODO: 2018/7/2  让用户可以重复支付
//        SimpleCondition condition = new SimpleCondition(BizOrder.class);
//        condition.eq(BizOrder.InnerColumn.yhId.name(), userId);
//        condition.eq(BizOrder.InnerColumn.ddZt.name(), "2");//订单状态(1、待缴费 2、已缴费 3、已退费)
//        condition.eq(BizOrder.InnerColumn.ddZfzt.name(), "1");//支付状态（0,待支付 1、支付成功  2、支付失败）
//        Integer count = this.countByCondition(condition);
//        RuntimeCheck.ifTrue(count > 0,"您已支付成功，无需再次支付！");

        BizUser bizUser=userService.findById(userId);
        RuntimeCheck.ifNull(bizUser,"您好，请您上传证件资料进行认证！");

        BizOrder newEntity=new BizOrder();
        newEntity.setDdId(genId());
        newEntity.setYhId(user.getId());//用户id
        newEntity.setCjsj(DateUtils.getNowTime());//创建时间
        newEntity.setYhCjr(userId);//创建人
        newEntity.setDdZt("1");//订单状态(ZDCLK0037 1、待缴费 2、已缴费 3、已退费)
        newEntity.setDdZftd(entity.getDdZftd());//支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
//            newEntity.setDdZfsj();//支付时间
        newEntity.setDdZfzt("0");//支付状态（0,待支付 1、支付成功  2、支付失败）

        newEntity.setDdZfje(MathUtil.stringToDouble(bizCp.getCpJl()));//支付金额(单位 分)
//            newEntity.setDdZfpz();//支付凭证ID(保存支付通道返回的CODE)
//            newEntity.setDdZfjg();//支付响应结果(1:成功 2:失败)
        newEntity.setYhXm(user.getYhXm());//姓名
//            newEntity.setDdBz();//订单备注
        newEntity.setYhSjid(bizUser.getYhSjid());//上级ID
        newEntity.setYhSsjid(bizUser.getYhSsjid());//上上级ID
        newEntity.setJobType("0");//定时任务处理状态(0、待处理 1、处理成功 2、处理失败 )
        newEntity.setCpId(cpId);
        newEntity.setOpenId(entity.getOpenId());
        newEntity.setUserAutograph(entity.getUserAutograph());
        boolean payType=false;//就否完成支付。
        if(entity.getDdZftd().equals(("2"))){//微信支付

            payInfo.debug("openId="+newEntity.getOpenId()+"---------------------------------------------------");

            Map<String,Object> wxMap= create(newEntity,bizCp,request);
            payMessage=wxMap.get("message");
            if(wxMap!=null&&payMessage==null){
                String prepayId="";//ddZfpz
                WxPayUnifiedOrderResult orderResult= (WxPayUnifiedOrderResult) wxMap.get("orderResult");
                if(orderResult!=null){
                    prepayId=orderResult.getPrepayId();
                    if(StringUtils.isNotEmpty(prepayId)){
                        payType=true;
                        entity.setDdZfpz(prepayId);//支付凭证ID

                        //拼装给前台的报文
                        SortedMap retSorteMap=new TreeMap<String, String>();
                        retSorteMap.put("appId", appid);
                        retSorteMap.put("timeStamp", String.valueOf(new Date().getTime()));
                        retSorteMap.put("nonceStr", genId()); // 必填，生成签名的随机串
                        retSorteMap.put("package", "prepay_id="+prepayId);
                        retSorteMap.put("signType", "MD5");
                        String paySign = WeixinUtils.createSign("UTF-8",retSorteMap,mchKey);//签名，微信根据参数字段的ASCII码值进行排序 加密签名,故使用SortMap进行参数排序
                        ret.putAll(retSorteMap);
                        ret.put("paySign", paySign);
                    }
                }
            }

        }else if(entity.getDdZftd().equals(("1"))){//支付宝方式支付。

        }
        if(payType){
//            异步下发，生成签名
            Map<String, Object> map = new HashMap<>();
            map.put("ptyh", userSelect);//BizPtyh userSelect
            map.put("bizCp", bizCp);//BizCp bizCp
            map.put("order",newEntity);//BizOrder
            eventBus.post(map);

            entityMapper.insert(newEntity);
            return ApiResponse.success(ret);
        }else{
            ApiResponse<Map<String,String>> res = new ApiResponse<Map<String,String>>();
            res.setCode(500);
            res.setMessage("请求支付通道失败，请稍后尝试。");
            if(payMessage!=null){
                res.setMessage((String) payMessage);
            }
            return res;
        }
    }

    /**
     * 微信创建预支付
     * @param order
     * @param bizCp
     * @param request
     * @return
     */
    public Map<String,Object> create(BizOrder order, BizCp bizCp,HttpServletRequest request) {
        try {
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();   //商户订单类
            orderRequest.setBody(bizCp.getCpMc());
            orderRequest.setOpenid(order.getOpenId());//用户的openID
//            //元转成分
            orderRequest.setTotalFee((int) Math.ceil(MathUtil.stringToDouble(bizCp.getCpJl())));   //注意：传入的金额参数单位为分
            //outTradeNo  订单号
            orderRequest.setOutTradeNo(order.getDdId());
            //tradeType 支付方式
            orderRequest.setTradeType(tradeType);//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
            orderRequest.setNotifyUrl(notifyUrl);//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数

            //用户IP地址
            orderRequest.setSpbillCreateIp(IpUtils.getRealIp(request));

            WxPayUnifiedOrderResult orderResult = wxService.unifiedOrder(orderRequest);
            Map<String,Object>map =new HashedMap();
            map.put("orderResult", orderResult);
            return map;
        } catch (Exception e) {
//            log.error("【微信支付】支付失败 订单号={} 原因={}", orderDTO.getOrderId(), e.getMessage());
            e.printStackTrace();
            Map<String,Object>map =new HashedMap();
            map.put("message", e.getMessage());
            return map;
        }

    }


    @Subscribe
    public void sendObject(Map<String, Object> map) {
        payInfo.debug("进入异步通知开始 addOracle 生成协议 begin---");
        try {
//            map.put("ptyh", userSelect);//BizPtyh userSelect
//            map.put("bizCp", bizCp);//BizCp bizCp
//            map.put("order",newEntity);//BizOrder
            BizPtyh user= (BizPtyh) map.get("ptyh");
            BizCp bizCp= (BizCp) map.get("bizCp");
            BizOrder order= (BizOrder) map.get("order");
            String cpType=bizCp.getCpType();//获取产品类型（1、学费  2、补考费）必填
            String cpXyJson=bizCp.getCpXyJson();
            if(StringUtils.isNotEmpty(cpXyJson)){
                String agreementImgList="";
                String userQm=staticPath;
                if (!userQm.endsWith("/")) {
                    userQm += "/";
                }

                cpXyJson=cpXyJson.replaceAll("#oracleId#",order.getDdId());//订单ID
                cpXyJson=cpXyJson.replaceAll("#userName#",user.getYhXm());//学员姓名
                cpXyJson=cpXyJson.replaceAll("#userDn#",user.getYhZh());//演员手机
                cpXyJson=cpXyJson.replaceAll("#userZJH#",user.getYhZjhm());//学员证件号
                cpXyJson=cpXyJson.replaceAll("#addyyyy#",DateUtils.getToday("yyyy"));//订单创建的年   -MM-dd
                cpXyJson=cpXyJson.replaceAll("#addmm#",DateUtils.getToday("MM"));//订单月
                cpXyJson=cpXyJson.replaceAll("#adddd#",DateUtils.getToday("dd"));//订单日
                cpXyJson=cpXyJson.replaceAll("#userQm#",userQm+order.getUserAutograph());//用户签字的图片


                payInfo.debug("生成出来的JSON报文是："+cpXyJson);

                List<Map<String,Object>> list=JsonUtil.toList(cpXyJson, Map.class);
                for(Map<String,Object> m:list){
                    List<Map<String, String>> parameterlist=new ArrayList<>();
                    parameterlist= (List<Map<String, String>>) m.get("parameterlist");
                    // TODO: 2018/7/10 这里为了安全，需要将所有用户有关的信息，全部做隐藏。 staticPath生成的图片，需要将用户证件号码给隐藏
//                    Map<String,String> retMap=ImgUtil.generateCode(staticPath,parameterlist,(String) m.get("backdropImg"),(String) m.get("oracleId"));//"oracleId" -> "order2003"
//                    ImgUtil.generateCode(userAgreementPath,parameterlist,(String) m.get("backdropImg"),(String) m.get("oracleId"));//"oracleId" -> "order2003"
                    Map<String,String> retMap=ImgUtil.generateCode(userAgreementPath,parameterlist,(String) m.get("backdropImg"),(String) m.get("oracleId"));//"oracleId" -> "order2003"
                    if(StringUtils.equals(retMap.get("code"),"200")){
                        agreementImgList+=retMap.get("fileUrl")+";";
                    }
                }
                if(StringUtils.isNotEmpty(agreementImgList)){
                    String[] imgFileUrl=agreementImgList.split(";");
                    if(imgFileUrl!=null&&imgFileUrl.length>0){
                        String imgURL=userAgreementPath;
                        if (!imgURL.endsWith("/")) {
                            imgURL += "/";
                        }
                        File imgFile=new File(imgURL+imgFileUrl[0]);
                        String filePath=imgFile.getPath();
                        String fileNames=FileUtil.getNamePart(filePath);
                        filePath=filePath.replaceAll(fileNames,"");
                        String pdfPate=(!filePath.endsWith("/")?filePath+="/":filePath)+order.getDdId()+".pdf";
                        pdfPate=pdfPate.replaceAll(userAgreementPath,"");
                        BizOrder newOrder=new BizOrder();
                        newOrder.setDdId(order.getDdId());
                        newOrder.setAgreementImgList(agreementImgList);
                        newOrder.setAgreementPdfList(pdfPate);
                        this.update(newOrder);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        payInfo.debug("进入异步通知开始 addOracle 生成协议 END---");
    }
}
