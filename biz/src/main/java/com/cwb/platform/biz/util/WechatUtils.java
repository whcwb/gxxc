package com.cwb.platform.biz.util;

import com.cwb.platform.util.commonUtil.JsonUtil;
import com.cwb.platform.util.redis.RedisTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by chenwei on 16/9/5.
 */
@Slf4j
@Component
public class WechatUtils {

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.secret}")
    private String secret;

    @Value("${wechat.domain}")
    private String domain;
    @Autowired
    private RedisTemplateUtil redisTemplateUtil;


    public String getCode(){
        String redirectUrl = "";
        try {
            redirectUrl = "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri="
                    + domain +"&response_type=code&scope=snsapi_base&state=" + URLEncoder.encode("debug",Charset.defaultCharset().name()) + "#wechat_redirect?";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return redirectUrl;
    }

    public static void main(String[] args) {
        String s = URLEncoder.encode("http://mmm.lufengtech.com/wx");
        System.out.println(s);
    }

    public String getOpenid(String code){
        String result = HttpUtil
                .get("https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                        + appId
                        + "&secret="
                        + secret
                        + "&code="
                        + code
                        + "&grant_type=authorization_code");
        Map<?, ?> bean = JsonUtil.toBean(result, Map.class);
        if(bean.get("openid") == null){
            return null;
        }
        return bean.get("openid").toString();
    }

    public String getToken(String openid){
        String val = (String) redisTemplateUtil.opsForValue().get("accessToken-token");
        if (StringUtils.isNotEmpty(val)){
            return val;
        }
        String token = HttpUtil
                .get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                        + appId
                        + "&secret="
                        + secret + "");
        Map<?, ?> map = JsonUtil.toBean(token, Map.class);
        if (map == null)return "";
        String accessToken = map.get("access_token").toString();

            return accessToken;
    }

    public String getJsapiTicket(String token){
        String ticketRes = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token+"&type=jsapi");
        log.info("getticket: ",ticketRes);
        Map<?, ?> map = JsonUtil.toBean(ticketRes, Map.class);
        String ticket = map.get("ticket").toString();
        return ticket;
    }

    public String getUserInfo(String accessToken,String openid){
        return HttpUtil
                .get("https://api.weixin.qq.com/cgi-bin/user/info?access_token="
                        + accessToken + "&openid=" + openid);
    }


    public String getJsApiSign(String url,String token,String timestamp) {
        String ticket = getJsapiTicket(token);
        String params = "jsapi_ticket=" +ticket +
                "&noncestr=wechat123" +
                "&timestamp="+ timestamp +
                "&url="+url;
        System.out.println(params);
        String sign = DigestUtils.shaHex(params);
        System.out.println(sign);
        return sign;
    }

}
