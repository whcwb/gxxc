package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.mapper.BizOrderMapper;
import com.cwb.platform.biz.model.BizCp;
import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.model.BizYjmx;
import com.cwb.platform.biz.service.CpService;
import com.cwb.platform.biz.service.OrderService;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.biz.service.YjmxService;
import com.cwb.platform.biz.wxpkg.service.WechatService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.commonUtil.MathUtil;
import com.cwb.platform.util.commonUtil.ZXingCode;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;

@Service
public class OrderServiceImpl extends BaseServiceImpl<BizOrder,java.lang.String> implements OrderService{
    Logger payInfo = LoggerFactory.getLogger("access_info");
    @Autowired
    private BizOrderMapper entityMapper;

    @Autowired
    private PtyhService ptyhService;

    @Autowired
    private YjmxService yjmxService;

    @Autowired
    private CpService cpService;
    @Autowired
    private WechatService wechatService;

    @Autowired
    private WxMpService wxMpService;

    AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(1));
    public OrderServiceImpl() {
        eventBus.register(this);
    }


    @Value("${logo_file_url}")
    private String logoFileUrl;
    @Value("${qr_code_file_url}")
    private String qrCodeFileUrl;

    @Value("${wxMsgTemplate.payBack}")
    private String examMsgId;

    @Value("${wxPayBackDomain}")
    private String wxDomain;


    @Override
    protected Mapper<BizOrder> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizOrder.class;
    }

    /**
     * 支付成功后，调用这个方法，修改数据库
     * oracle订单表      1、支付凭证     2、订单状态 2     3、支付状态 1成功 2 失败
     * user 用户表     DD_SFJX  是否缴费 ZDCLK0045 (0 未缴费 1 已缴费)
     * @param l
     * @return
     */
    public ApiResponse<String> updateOrderPayTpye(BizOrder l){
        payInfo.info("订单编号："+l.getDdId()+"*****************************************************************");
        BizOrder order=findById(l.getDdId());
        if(order==null){
            payInfo.info("订单编号："+l.getDdId()+"不存在，不需要操作数据");
            return ApiResponse.success("订单编号："+l.getDdId()+"不存在，不需要操作数据");
        }
        if(StringUtils.equals(order.getDdZt(),"2")||StringUtils.equals(order.getDdZfzt(),"1")){
            payInfo.info("订单编号："+l.getDdId()+"已完成支付，不需要操作数据");
            return ApiResponse.success("该订单已完成支付，不需要操作数据");
        }

        BizOrder newBizOrder=new BizOrder();
        newBizOrder.setDdId(l.getDdId());
        newBizOrder.setDdZt("2");//2、订单状态 2  设置订单状态(ZDCLK0037 1、待缴费 2、已缴费 3、已退费)
        newBizOrder.setDdZfzt("1");//支付状态 1成功 2 失败  ZDCLK0039  0,待支付 1、支付成功  2、支付失败
//        newBizOrder.setDdZftd(l.getDdZftd());//设置支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
        newBizOrder.setDdZfpz(l.getDdZfpz());//支付凭证ID(保存支付通道返回的CODE)
        newBizOrder.setPayMoney(l.getPayMoney());//订单支付成功后，将实际支付的金额回写到这里。用于验证订单支付是否异常
        newBizOrder.setDdZfsj(DateUtils.getNowTime());//设置支付时间

        this.update(newBizOrder);

        //支付成功后，生成二维码

        //插入两条支付信息插入流水表
        BizYjmx newBizYjmx1 = new BizYjmx();
        newBizYjmx1.setId(genId());
        newBizYjmx1.setZjId(l.getDdId());
        newBizYjmx1.setYhId(order.getYhId());//消费的用户
        newBizYjmx1.setZjJe(MathUtil.stringToDouble( l.getPayMoney()));//支付的金额
        newBizYjmx1.setZjFs("1");//费用方式 ZDCLK0053 (1 佣金 -1 提现)
        newBizYjmx1.setCjsj(DateUtils.getNowTime());
        newBizYjmx1.setZjZt("1");//提现状态 ZDCLK0054 (0、提现冻结  1、 处理成功 ) 提现操作默认0 佣金操作默认1
        newBizYjmx1.setMxlx("1");//明细类型  ZDCLK0066 1、付款 2、分佣 3、消费 4、提现
        yjmxService.save(newBizYjmx1);

        newBizYjmx1.setId(genId());
        newBizYjmx1.setZjFs("-1");//费用方式 ZDCLK0053 (1 佣金 -1 提现)
        newBizYjmx1.setMxlx("3");//明细类型  ZDCLK0066 1、付款 2、分佣 3、消费 4、提现
        yjmxService.save(newBizYjmx1);

        BizCp bizCp = cpService.findById(order.getCpId());
        String yhZsyqm ="";
        String yhZsyqmImg ="";


        order.setPayMoney(l.getPayMoney());
        order.setDdZftd(l.getDdZftd());

        BizPtyh queryUser=ptyhService.findById(order.getYhId());

        // 判断订单产品是否属于学费，只有学费才生成邀请码
        if(StringUtils.equals(bizCp.getCpType(),"1")||StringUtils.equals(bizCp.getCpType(),"3")) { // 产品类型为会员、学员时 ， 需要生成邀请码
            String queryYhZsyqm="";
            if(queryUser!=null){
                queryYhZsyqm=queryUser.getYhZsyqm();
            }
            //判断当前用户是否存在邀请码，如果存在邀请码将不会重新新的邀请码
            if(StringUtils.isEmpty(queryYhZsyqm)){
                yhZsyqm = genId();
                yhZsyqmImg = "QRCode/"+DateUtils.getToday("yyyyMMdd")+"/";
                String userName="";
                userName=order.getYhXm();
                Map<String,Object> sendMap=new HashMap<String,Object>();
                sendMap.put("type","1");
                sendMap.put("yhZsyqm",yhZsyqm);
                sendMap.put("yhZsyqmImg",yhZsyqmImg);
                sendMap.put("userName",userName);
                eventBus.post(sendMap);
            }
        }
        Map<String,Object> sendMap=new HashMap<String,Object>();
        sendMap.put("type","2");
        sendMap.put("order",order);
        sendMap.put("cpMc",bizCp.getCpMc());
        sendMap.put("cpType",bizCp.getCpType());//产品类型
        eventBus.post(sendMap);




        BizPtyh bizPtyh=new BizPtyh();
        bizPtyh.setId(order.getYhId());
        bizPtyh.setDdSfjx("1");
        //1、产品类型为3 会员  2、用户当前属性不等于1学员  3、用户当前属性等于1并且未邀费成功的
        if(StringUtils.equals(bizCp.getCpType(),"3")){
            bizPtyh.setYhLx("3");
            if(StringUtils.equals(queryUser.getYhLx(),"1")&&StringUtils.equals(queryUser.getDdSfjx(),"1")){
                bizPtyh.setYhLx("1");
            }
        }else  if(StringUtils.equals(bizCp.getCpType(),"1")){
            bizPtyh.setYhLx("1");
        }
        if(StringUtils.isNotEmpty(yhZsyqm)){
            bizPtyh.setYhZsyqm(yhZsyqm);//用户自己邀请码
            bizPtyh.setYhZsyqmImg("/"+yhZsyqmImg+yhZsyqm + ".png");//用户自己邀请码
        }
        // 增加二维码有效期 todo
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime years = null;
        if(StringUtils.isBlank(queryUser.getYhYqmgqsj())){ // 邀请码过期时间为空 , 添加过期时间为一年
            years = dateTime.plusYears(1L);
            bizPtyh.setYhYqmgqsj(years.format(formatter));
        }else {

            // 验证有效期是否已经过了
            if(queryUser.getYhYqmgqsj().compareTo(dateTime.format(formatter)) > 0){
                LocalDateTime localDateTime = LocalDateTime.parse(queryUser.getYhYqmgqsj(),formatter);
                 years = localDateTime.plusYears(1L);
                bizPtyh.setYhYqmgqsj(years.format(formatter));
            }else {
                 years = dateTime.plusYears(1L);
                bizPtyh.setYhYqmgqsj(years.format(formatter));
            }
        }

        ptyhService.update(bizPtyh);

        return ApiResponse.success();
    }


    @Subscribe
    public  void sendObject(Map<String,Object> map){
        payInfo.debug("进入异步通知开始 Async begin---");
        try {
            String type= (String) map.get("type");
            if("2".equals(type)){
                payInfo.debug("异步通知进入异步下发消息---");
                BizOrder order= (BizOrder) map.get("order");
                String cpMc = (String) map.get("cpMc");
                String cpType = (String) map.get("cpType");//产品类型
                this.asynchronousSendMessage(order,cpMc,cpType);
            }else  if("1".equals(type)){
                payInfo.debug("异步通知进入生成邀请号码---");
                String yhZsyqm= (String) map.get("yhZsyqm");
                String yhZsyqmImg= (String) map.get("yhZsyqmImg");
                String userName= (String) map.get("userName");
                this.asynchronousOperate(yhZsyqm,yhZsyqmImg,userName,null);
            }
        }catch (Exception e){}
        payInfo.debug("进入异步通知开始 Async END---");
    }
    /**
     * 异步下发消息
     * @param order
     */
    public void asynchronousSendMessage(BizOrder order,String cpMc,String cpType){
        payInfo.debug("下发微信通知---");
        String yhId=order.getYhId();
        BizPtyh ptyh=ptyhService.findById(yhId);
        try {
            String time =  DateUtils.getDateStr(new Date(), "yyyy年MM月dd日");
            Map<String,String > map=new HashMap<>();
            map.put("1","支付宝");
            map.put("2","微信");
            map.put("3","银联");
            map.put("4","快钱");
            String first  = "您于" + time +  "，通过" +map.get(order.getDdZftd())+ "，缴纳了"+cpMc+""+MathUtil.stringToDouble(order.getPayMoney())/100+"元";

            List<WxMpTemplateData> data = new ArrayList<>();
            data.add(new WxMpTemplateData("first", first));
            data.add(new WxMpTemplateData("keyword1", ptyh.getYhXm()));
            data.add(new WxMpTemplateData("keyword2", MathUtil.stringToDouble(order.getPayMoney())/100+"元","#ff0000"));
            data.add(new WxMpTemplateData("keyword3",time));
            data.add(new WxMpTemplateData("remark", "点击查看"));

            WxMpTemplateMessage msg = new WxMpTemplateMessage();
            msg.setToUser(ptyh.getYhOpenId());
            msg.setTemplateId(examMsgId);
            msg.setUrl(wxDomain);
            msg.setData(data);
            List<Map<String,String>> smsMapList=new ArrayList<>();
            Map<String, String> smsMap = new HashMap<String, String>();
            smsMap.put("phoneNumbers", ptyh.getYhZh());//电话号码
            if(StringUtils.equals(cpType,"1")){//学员
                smsMap.put("templateType", "1");//学员
            }else if(StringUtils.equals(cpType,"3")){//会员
                smsMap.put("templateType", "2");//会员
            }else {
                smsMap=null;
            }
            if(smsMap==null){
                smsMapList=null;
            }else {
                smsMapList.add(smsMap);
            }
            String res = wechatService.sendTemplateMsg(msg,smsMapList);
            payInfo.info("sendMsg result :", res);

        } catch (WxErrorException e) {
            payInfo.error("发送微信模板消息异常", e);
        }
        try {
            // 缴费成功发送微信消息
            WxMpKefuMessage message = WxMpKefuMessage .TEXT().toUser(ptyh.getYhOpenId()).content("您已缴费成功，请留意接听客服电话，关注您的培训流程！").build();
            wxMpService.getKefuService().sendKefuMessage(message);
        } catch (WxErrorException e) {
            payInfo.error("发送微信模板消息异常", e);
        }


    }

    /**
     * 异步生成邀请号码
     * @param yhZsyqm   邀请码
     * @param yhZsyqmImg    邀请码生成的图片地址
     * @param userName      用户名称
     * @param order         这个字段不需要，直接传null
     */
    public void asynchronousOperate(String yhZsyqm, String yhZsyqmImg, String userName,BizOrder order){
        try {

            payInfo.debug("支付成功，异步生成邀请图片---");
            File logoFile = new File(logoFileUrl);

            File file=new File(qrCodeFileUrl + yhZsyqmImg);
            if (!file.exists()  && !file.isDirectory()){
                file.mkdirs();
            }
            String note = "您的好友：" + userName + " 邀请您";
            note="";//经理说，生成的图片不需要增加文件。所以这行去掉
            ZXingCode.drawLogoQRCode(logoFile, new File(qrCodeFileUrl + yhZsyqmImg+yhZsyqm + ".png"), yhZsyqm, note);
            payInfo.debug("用户：" + userName + "。生成邀请码成功。异步---");
        }catch (Exception e){}

    }
}
