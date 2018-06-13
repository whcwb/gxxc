package com.cwb.platform.util.commonUtil;

import java.util.*;

/**
 * 微信通用工具类
 * Created by Administrator on 2018/6/12.
 */
public class WeixinUtils {
    /** 微信支付回调支付成功，返回成功结果 */
    public static final String WX_PAY_SUCCESS = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[ok]]></return_msg></xml>";
    /** 微信支付回调支付失败，返回失败结果 */
    public static final String WX_PAY_FAIL = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[result_code is FAIL]]></return_msg></xml>";
    /** 微信支付回调支付sign验证失败，返回sign验证失败结果 */
    public static final String WX_PAY_SIGN_FAIL = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[check signature FAIL]]></return_msg></xml>";
    /** 微信支付回调地址路径 */
    public static final String NOTIFY_URL = "/wxPay/notifyCallback.do";
    /** 微信获取微信用户授权后用户信息 地址路径 */
    public static final String OAUTH2_USERINFO_URL = "/wx/getOAuth2UserInfo.do";
    /** 微信官方api接口 */
    public static final String URL_OAUTH2 = "https://open.weixin.qq.com/connect/oauth2/authorize?";
    /** 获取微信用户信息 */
    public static final String SCOPE = "snsapi_userinfo";
    /** 交易类型 ：jsapi代表微信公众号支付 */
    public static final String TRADE_TYPE = "JSAPI";

    /**
     * 获得微信支付随机码
     * @return
     * Date:2017年12月4日上午9:50:48
     * @author 吉文剑
     */
    public static String getNonceStr(){
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }


    //定义签名，微信根据参数字段的ASCII码值进行排序 加密签名,故使用SortMap进行参数排序
    public static String createSign(String characterEncoding,SortedMap<String,String> parameters,String mchKey){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + mchKey);//最后加密时添加商户密钥，由于key值放在最后，所以不用添加到SortMap里面去，单独处理，编码方式采用UTF-8
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }
}
