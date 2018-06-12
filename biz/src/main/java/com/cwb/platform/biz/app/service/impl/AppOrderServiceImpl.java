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
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.commonUtil.IpUtils;
import com.cwb.platform.util.commonUtil.MathUtil;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import java.math.BigDecimal;

@Service
public class AppOrderServiceImpl extends BaseServiceImpl<BizOrder,String> implements AppOrderService {
    @Value("${wechat.pay.trade_type}")
    private String tradeType;

    @Value("${wechat.pay.notify_url}")
    private String notifyUrl;

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
            RuntimeCheck.ifNull(StringUtils.isEmpty(openId),"OPEN_ID不能为空");
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
        RuntimeCheck.ifTrue(StringUtils.equals(userSelect.getDdSfjx(),"1"),"您已支付成功，无需再次支付！");//获取是否缴费(0无 1已缴费)
        RuntimeCheck.ifTrue(StringUtils.equals(userSelect.getYhSfsd(),"1"),"您已经锁定，无法支付。请联系管理人员进行解锁！");//用户是否锁定 ZDCLK0046 (0 否  1 是)  0是没有锁定 1是已锁定

        SimpleCondition condition = new SimpleCondition(BizOrder.class);
        condition.eq(BizOrder.InnerColumn.yhId.name(), userId);
        condition.eq(BizOrder.InnerColumn.ddZt.name(), "2");//订单状态(1、待缴费 2、已缴费 3、已退费)
        condition.eq(BizOrder.InnerColumn.ddZfzt.name(), "1");//支付状态（0,待支付 1、支付成功  2、支付失败）
        Integer count = this.countByCondition(condition);
        RuntimeCheck.ifTrue(count > 0,"您已支付成功，无需再次支付！");

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
        boolean payType=false;//就否完成支付。
        if(entity.getDdZftd().equals(("2"))){//微信支付

            Map<String,Object> wxMap= create(newEntity,bizCp,request);
            payMessage=wxMap.get("message");
            if(wxMap!=null&&payMessage==null){
                String prepayId="";//ddZfpz
                WxPayUnifiedOrderResult orderResult= (WxPayUnifiedOrderResult) wxMap.get("orderResult");
                if(orderResult!=null){
                    prepayId=orderResult.getPrepayId();
                    if(StringUtils.isNotEmpty(prepayId)){
                        payType=true;
                        ret.put("prepayId",prepayId);
                        entity.setDdZfpz(prepayId);//支付凭证ID
                    }
                }
            }

        }else if(entity.getDdZftd().equals(("1"))){//支付宝方式支付。

        }
        if(payType){
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
            WxPayUnifiedOrderRequest orderRequests= wxService.createOrder(orderRequest);

            WxPayUnifiedOrderResult orderResult = wxService.unifiedOrder(orderRequests);
            Map<String,Object>map =new HashedMap();
            map.put("orderRequest", orderRequest);
            map.put("orderResult", orderResult);
            map.put("timeStamp", System.currentTimeMillis());// 必填，生成签名的时间戳
            map.put("nonceStr", genId()); // 必填，生成签名的随机串

            return map;
        } catch (Exception e) {
//            log.error("【微信支付】支付失败 订单号={} 原因={}", orderDTO.getOrderId(), e.getMessage());
            e.printStackTrace();
            Map<String,Object>map =new HashedMap();
            map.put("message", e.getMessage());
            return map;
        }

    }
}
