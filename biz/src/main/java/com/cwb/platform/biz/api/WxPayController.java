package com.cwb.platform.biz.api;

import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.model.BizZfrz;
import com.cwb.platform.biz.service.OrderService;
import com.cwb.platform.biz.service.ZfrzService;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.WeixinUtils;
import com.cwb.platform.util.commonUtil.XMLUtil;
import com.github.binarywang.wxpay.util.SignUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付
 * Created by Administrator on 2018/6/12.
 */
@Controller
@RequestMapping("/wxPay/")
public class WxPayController {
    Logger logger = LoggerFactory.getLogger("access_info");

    @Value("${wechat.pay.mchKey}")
    private String mchKey;
    /**
     * 支付日志
     */
    @Autowired
    private ZfrzService zfrzService;

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
                String resultCode = null;//回调支付是否成功状态吗
                String orderCode = null;//回调 支付订单号
                String totalFee = null;//支付金额
                ApiResponse<String> res=new ApiResponse<String>();


                BizZfrz payLog=new BizZfrz();
                payLog.setZfLx("2");


                InputStream inStream = request.getInputStream();
                int _buffer_size = 1024;
                if (inStream != null) {
                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                    byte[] tempBytes = new byte[_buffer_size];
                    int count = -1;
                    while ((count = inStream.read(tempBytes, 0, _buffer_size)) != -1) {
                        outStream.write(tempBytes, 0, count);
                    }
                    outStream.flush();
                    //将流转换成字符串
                    String result = new String(outStream.toByteArray(), "UTF-8");
                    payLog.setZfBw(result);// 支付原始报文

                    if(StringUtils.isNotEmpty(result)){//报文不为空
                        Map<String, String> kvm=new HashMap<String, String>();
                        try {
                                kvm = XMLUtil.parseXmlStringToMap(result);
                        }catch (Exception e){
                            payLog.setZfMs("报文转MAP出错");
                        }
                        logger.info("微信支付回调参数: " + kvm);
                        System.out.println("微信支付回调参数：");
                        System.out.println(kvm);
                        if (SignUtils.checkSign(kvm,"MD5", mchKey)) {
                            payLog.setZfYzzt("1");// 支付验证状态
                            orderCode =  kvm.get("out_trade_no");
                            resultCode = kvm.get("result_code");
                            totalFee = kvm.get("total_fee");

                            payLog.setDdId(orderCode);// 订单id
                            payLog.setZfSj(kvm.get("time_end"));// 支付时间

                            if ("SUCCESS".equals(resultCode)) {
                                BizOrder order=new BizOrder();
                                order.setDdId(orderCode);
                                order.setPayMoney(totalFee);//支付宝，实际支付的金额
                                order.setDdZftd("2");//设置支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
                                res= oracleService.updateOrderPayTpye(order);
                                if(res.isSuccess()){
                                    payLog.setZfClzt("1");// 支付处理状态
                                    payLog.setZfJe(totalFee);// 支付处理状态
                                    payLog.setZfMs(res.getMessage());//日志描述
                                    logger.info("out_trade_no: " + orderCode + " pay SUCCESS!");
                                    response.getWriter().write(WeixinUtils.WX_PAY_SUCCESS);
                                }else{
                                    payLog.setZfMs("业务处理失败"+"  订单ID: "  + orderCode );
                                    resultCode="error";
                                    this.logger.error("out_trade_no: "  + orderCode + " result_code is FAIL3");
                                    response.getWriter().write(WeixinUtils.WX_PAY_FAIL);
                                }
                            } else {
                                payLog.setZfMs("微信端返回本次处理失败"+"  订单ID: "  + orderCode + " result_code is FAIL");
                                this.logger.error("out_trade_no: "  + orderCode + " result_code is FAIL2");
                                response.getWriter().write(WeixinUtils.WX_PAY_FAIL);
                            }
                        } else {
                            payLog.setZfMs("验签失败"+"  订单ID: "  + orderCode + " result_code is FAIL");
                            this.logger.error("out_trade_no: " + orderCode + " check signature FAIL");
                            response.getWriter().write(WeixinUtils.WX_PAY_SIGN_FAIL);
                        }
                    }else{
                        payLog.setZfMs("平台接口到的报文为空" );
                    }
                }else{
                    payLog.setZfMs("平台接口到的报文为空" );
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
                //写入日志表，日志表中的任何异常，都不能影响主业务。所以要将所有的异常都捕捉
                try{
                    zfrzService.addObject(payLog);
                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
