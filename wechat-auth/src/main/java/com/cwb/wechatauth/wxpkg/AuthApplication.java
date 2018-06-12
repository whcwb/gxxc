package com.cwb.wechatauth.wxpkg;

import com.cwb.wechatauth.wxpkg.config.WxConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) throws IOException {
        Map<String,String> map = readConfig();
        WxConfig config = WxConfig.getInstance();
        if (map != null){
            config.setAesKey(map.get("aesKey"));
            config.setAppId(map.get("appId"));
            config.setDomain(map.get("domain"));
            config.setSecret(map.get("secret"));
            config.setToken(map.get("token"));
        }else{
            config.setAppId("test");
        }
        SpringApplication.run(AuthApplication.class, args);
    }

    private static Map<String,String> readConfig() throws IOException {
        File file = new File("config.txt");
        if (!file.exists())return new HashMap<>();
        Map<String,String> map = new HashMap<>();
        List<String> lines = FileUtils.readLines(file,Charset.forName("UTF-8"));
        for (String line : lines) {
            if (StringUtils.isEmpty(line))continue;
            line = line.trim();
            String[] data = line.split("=");
            if (data.length < 2)continue;
            map.put(data[0],data[1]);
        }
        return map;
    }
}
