package com.cwb.platform.biz.api;

import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.service.OrderService;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.WeixinUtils;
import com.cwb.platform.util.commonUtil.XMLUtil;
import com.github.binarywang.wxpay.util.SignUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 微信支付
 * Created by Administrator on 2018/6/12.
 */
@Controller
@RequestMapping("/wxPay/")
public class WxPayController {
    Logger logger = LoggerFactory.getLogger("access_info");

    @Value("wechat.pay.mchKey")
    private String mchKey;

    @Autowired
    private OrderService oracleService;

    /**
     * 微信通知支付结果的回调地址，notifyCallback
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "notifyCallback")
    public void notifyCallback(HttpServletRequest request, HttpServletResponse response) {
        try {
            synchronized (this) {
                Map<String, String> kvm = XMLUtil.parseRequestXmlToMap(request);
                ApiResponse<String> res=new ApiResponse<String>();
                String orderCode = null;//回调 支付订单号
                String resultCode = null;//回调支付是否成功状态吗
                String totalFee = null;//支付金额
                logger.info("微信支付回调参数: " + kvm);
                System.out.println("微信支付回调参数：");
                System.out.println(kvm);
                if (SignUtils.checkSign(kvm,null, mchKey)) {
                    orderCode =  kvm.get("out_trade_no");
                    resultCode = kvm.get("result_code");
                    totalFee = kvm.get("total_fee");
                    if ("SUCCESS".equals(resultCode)) {

                        BizOrder order=new BizOrder();
                        order.setDdId(orderCode);
//                        order.setDdZfpz(ddZfpz);//支付凭证ID(保存支付通道返回的CODE)
                        order.setPayMoney(totalFee);//支付宝，实际支付的金额
                        order.setDdZftd("2");//设置支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
                        res= oracleService.updateOrderPayTpye(order);
                        if(res.isSuccess()){
                            logger.info("out_trade_no: " + orderCode + " pay SUCCESS!");
                            response.getWriter().write(WeixinUtils.WX_PAY_SUCCESS);
                        }else{
                            resultCode="error";
                            this.logger.error("out_trade_no: "  + orderCode + " result_code is FAIL");
                            response.getWriter().write(WeixinUtils.WX_PAY_FAIL);
                        }
                    } else {
                        this.logger.error("out_trade_no: "  + orderCode + " result_code is FAIL");
                        response.getWriter().write(WeixinUtils.WX_PAY_FAIL);
                    }
                } else {
                    this.logger.error("out_trade_no: " + orderCode + " check signature FAIL");
                    response.getWriter().write(WeixinUtils.WX_PAY_SIGN_FAIL);
                }

                if("SUCCESS".equals(resultCode)){
                    //支付成功的业务逻辑
                    //totalFee   要判断支付金额是否等于订单金额！！！
                    System.out.println("支付成功：订单号："+orderCode+",支付金额："+totalFee);

                    logger.info("支付成功：订单号："+orderCode+",支付金额："+totalFee);
                }else{
                    //支付失败的业务逻辑
                    System.out.println("微信支付 回调 ：*-************支付失败");
                    logger.info("微信支付 回调 ：*-************支付失败"+res.getMessage());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
