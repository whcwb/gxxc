package com.cwb.platform.util.commonUtil;

import java.util.UUID;

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
}
