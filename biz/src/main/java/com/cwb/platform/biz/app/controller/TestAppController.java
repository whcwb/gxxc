package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.service.AppOrderService;
import com.cwb.platform.biz.service.AlipayBillService;
import com.cwb.platform.util.bean.ApiResponse;
import com.github.binarywang.wxpay.bean.entpay.EntPayRequest;
import com.github.binarywang.wxpay.bean.entpay.EntPayResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/pub")
public class TestAppController {
    @Autowired
    private AppOrderService service;

    @Resource(name = "wxPayService")
    private WxPayService wxService;

    @Autowired
    private WxMpService wxMpService;

//    @Autowired
//    private WxMpQrcodeService wxMpQrcodeService;

    @Autowired
    private AlipayBillService alipayBillService;


    /**
     * App支付 （测试）
     */
    @RequestMapping(value="/appPay", method={RequestMethod.POST,RequestMethod.GET})
    public ApiResponse<Map<String,String>> appPay(HttpServletRequest request){

        return service.appPay(request);
    }

    /**
     * APP支付回调接口 （测试）
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/test")
    public void app(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] tempBytes = new byte[1024];
        int count = -1;
        while ((count = inputStream.read(tempBytes, 0, 1024)) != -1) {
            outStream.write(tempBytes, 0, count);
        }
        outStream.flush();
        //将流转换成字符串
        String result = new String(outStream.toByteArray(), "UTF-8");
        System.out.println("result -----> " + result);
        /*ServletOutputStream outputStream = response.getOutputStream();
        response.getWriter().write("1");*/


    }
//    alipayBill
@RequestMapping("/zfb")
public ApiResponse<String> alipayBill(String billDate){
        if(StringUtils.isEmpty(billDate)){
            billDate="2018-08-10";
        }
    alipayBillService.alipayBill(billDate);
        return ApiResponse.success();
}
    /**
     * 微信-公司打款到余额
     * @return
     */
    @RequestMapping("/tests")
    public ApiResponse<String> pager(){
        EntPayRequest request=new EntPayRequest();
        request.setMchAppid("wxb01394ea85904296");
        request.setMchId("1506987921");
        request.setPartnerTradeNo("order201809140002");//订单ID
//        request.setOpenid("oRPNG0hya_mZyLqoS-mUPJEt3fV8");//用户的open_id  羊祥
        request.setOpenid("oRPNG0sHSA1wM4qcGucSGxiLAAhU");//用户的open_id  赵虎
        request.setCheckName("FORCE_CHECK");//校验用户姓名选项  FORCE_CHECK 强校验真实姓名  NO_CHECK：不校验真实姓名 
        request.setReUserName("李彬彬1");//收款用户姓名
        request.setAmount(100);//金额
        request.setDescription("公司付款Test20180809114859分发送");
        request.setSpbillCreateIp("27.16.192.155");//Ip地址   27.16.192.155
        try {
            EntPayResult aaa = wxService.getEntPayService().entPay(request);
            System.out.println("-------------------------");
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return ApiResponse.success();
    }
    //生成带参数的邀请码
    @RequestMapping("/wxQrcodes")
    public ApiResponse<String> wxQrcode(){
        String url="URL 地址为：";
        try {
            WxMpQrcodeService wx = wxMpService.getQrcodeService();
            WxMpQrCodeTicket wxMpQrCodeTicket = wx.qrCodeCreateTmpTicket("1234321", 2592000);
            String ticket=wxMpQrCodeTicket.getTicket();
            String qrcodesFile=wx.qrCodePictureUrl(ticket);

            url+=qrcodesFile;//
            long expireSeconds=wxMpQrCodeTicket.getExpireSeconds();//二维码过期时间

            url+="   过期时间为"+expireSeconds+"秒";
        }catch (WxErrorException e){
            e.printStackTrace();
        }

        return ApiResponse.success(url);
    }

//



}
