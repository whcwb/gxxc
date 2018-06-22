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

}
