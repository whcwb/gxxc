package com.cwb.platform.biz.wxpay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import org.springframework.beans.factory.annotation.Value;

/**
 * 支付宝
 * 参考 这是接口请求的 DEMO
 * Created by Administrator on 2018/7/25.
 */
public class AlipayDemoController {
    //  应用id(app_id)
    @Value("${alipay.app_id}")
    private static String appId;
    //APPA应用私钥(private_key)
    @Value("${alipay.app_privaie_key_pkcs8}")
    private static String appPrivaieKey;
    //编码格式(charset)
    @Value("${alipay.charset}")
    private static String charset;
    //    支付宝公钥
    @Value("${alipay.alipay_public_key}")
    private static String alipayPublicKey;
    //    网关(gateway)请求支付的接口
    @Value("${alipay.gateway}")
    private static String gateway;
    //  签名类型  默认为：RSA2
    @Value("${alipay.sign_type}")
    private static String signType;

    public static void main(String[] args) {


        applyForPay();
    }

    /**
     * 申请支付
     */
    private static void applyForPay() {
        appId= "2016091700534601";
        appPrivaieKey= "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQClyrBPpFqU2//2JbEwL2r/Hcnao7Dq/zlmb1o+h5ZxWRB2PMoANBmB6mzbdOrvxEL8K2MMdJdbss20XhUPWl21pORPI3/Thea9dnB0RZNivMBv3bUyTQZeYz4gYlvaCds5SZLMQFrlwXPDO0fl3u/PWxG5CQW28u8cGW2w66vyuaZkL48JS6NhJ5Kl8lBAGlN7C3cMRU3+pjFow8iUAc3n9LHpkIeqMiJmUqzcgpPEVFXRiz+hQNN0FXfIRHI2wltXqjp0Jr3aAATqt4p6DcM6npj2bptjHv6dfaGygWgLFZ+Tn+uYv3vFj7JoCnHBDc+RhN3WuhYlr9wTBA1d6NKXAgMBAAECggEAAojozS7sG56JeU/2N5BiCGiCL4+VIuKoFALb1FzqVe1QrR4c0If7Y9sbsvdEqEnN5GEdcJFPxx0ivAE8PTHagO52dydJywgfEQKAkaaGrFnKb2tqW8yFrmfCCWi3ksvXyDZxwiGxwhWFGZUMcl1IOVlhGf0jUzq/oSRaYvwXcuyRZeewk5EQFWE9yZ+b9ZH4ZXEc9bSZPyu18rZzrTCUZLraf+4OuZhQGHy4uPSZxjIVo6UTLNtH4UCSUeIoirpivXK1NhQ5E3Jff+QzdGQGqrmpWCB827nSQbUxfi9y/gWFURDBlxscN1hxzdIqduwyA3waRQImf8IhnLzj5M+kMQKBgQDgr8IQJtQY66Iwlk+dxrstNIzxkOjxxdyWyrTZ5TzeyNqZiZxXusuvlazJPXeKjLBqSf+PMmka4pymt9RPdAlw+w/8/p1DbLPPdeJ5k2jUBoqoAG2uj0SnWtc2WPjpXVq4fn643tafWYiceOFZXMYreUYwU7okXaof/OWMaevngwKBgQC85bb0Fq3LPXxl7Y3aikB/2MJg/kizJaL+qTKZ86+UWANMS19TSAuSzV+Teu/EivdAa8U4XuLGOn2GWF0e9RJ495HlXrAEebOyEUSgVajAgzISOoSBd7QFn8nhi15LNH9JjTDe9garwzxx16nJ7xjnHVMt/oyW2b7enGDE/U3oXQKBgQCL0RNEFcV6VHczIPYeSmKDuOXUFMbAXM3uD+oGhb4hLH/Ld3SQqy7A7rT87YEL+Ix3N+EN7OK21jmBDXfSkXSml7TVTGP0ZxpliQgLe42KEyAJd2rJa2HfeondyfPq1Cr2D9EvnYuU0ZRPOnGQuEihGPBydkepBZguG6OEgw1HnQKBgQCSWUs4q0V0/Csf8T+A/LHiGzbl3LzIhzVUJhR9ME5tGQ9dEdxF8sB8uT7+TTXsSVosgBKpjOIXgIN2DYoADRGFxRE06vpmTUAKitRngFYg6MRnWQ0GHY7M+LJ5WvfwPjkL7Cbb5zLd6oIvFU6HlAXwMUkbjmawDP2TDQbwN+wfRQKBgDjehADTmc05ZFGi82Bv/dG1zM8/YGpl26I4os0UOIicUnP+Z/cpIbk5y8rt2OmML0KNaPSBiKJ+zDhVJ/H01i84aL6Yg58PgXRu41w/VvXa7KvqliVxNDTkqrbHDHPIkfgy3xjOWbHWyh1BRJMBmv4ocIziYJUBKN3MnYCVRJnm";
        charset= "UTF-8";
        alipayPublicKey= "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApcqwT6RalNv/9iWxMC9q/x3J2qOw6v85Zm9aPoeWcVkQdjzKADQZgeps23Tq78RC/CtjDHSXW7LNtF4VD1pdtaTkTyN/04XmvXZwdEWTYrzAb921Mk0GXmM+IGJb2gnbOUmSzEBa5cFzwztH5d7vz1sRuQkFtvLvHBltsOur8rmmZC+PCUujYSeSpfJQQBpTewt3DEVN/qYxaMPIlAHN5/Sx6ZCHqjIiZlKs3IKTxFRV0Ys/oUDTdBV3yERyNsJbV6o6dCa92gAE6reKeg3DOp6Y9m6bYx7+nX2hsoFoCxWfk5/rmL97xY+yaApxwQ3PkYTd1roWJa/cEwQNXejSlwIDAQAB";
        gateway= "https://openapi.alipay.com/gateway.do";
        signType= "RSA2";

        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", appId, appPrivaieKey, "json", charset, alipayPublicKey, signType);
//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("body");//对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
        model.setSubject("subject");
        model.setOutTradeNo("1237456");//订单ID
        model.setTimeoutExpress("30m");
        model.setTotalAmount("0.01");//支付金额
        model.setProductCode("QUICK_MSECURITY_PAY");
        model.setTimeoutExpress("1c");//订单当天有效
        request.setBizModel(model);
        request.setNotifyUrl("http://my-domswain.tunnel.qydev.com/alipay/orderFulfil");//todo 支付回调
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println("结果："+response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }



}
