package com.cwb.wechatauth.wxpkg.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * auther chenwei
 * create at 2018/6/11
 */
@Getter
@Setter
@ToString
public class WxConfig {
    private String appId;
    private String secret;
    private String token;
    private String aesKey;
    private String domain;

    private static WxConfig instance = null;

    private WxConfig(){}

    public static WxConfig getInstance(){
        if (instance == null)instance = new WxConfig();
        return instance;
    }

}
