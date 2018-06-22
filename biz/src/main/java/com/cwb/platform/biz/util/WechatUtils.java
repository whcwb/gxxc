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
import java.util.concurrent.TimeUnit;

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

    private static final String WECHAT_TOKEN = "wechat_token";


    public static void main(String[] args) {
        String s = URLEncoder.encode("http://mmm.lufengtech.com/wx");
        System.out.println(s);
    }


    public String getToken(){
        String val = (String) redisTemplateUtil.opsForValue().get(WECHAT_TOKEN);
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
        redisTemplateUtil.opsForValue().set(WECHAT_TOKEN,accessToken,1,TimeUnit.HOURS);
        return accessToken;
    }



}
