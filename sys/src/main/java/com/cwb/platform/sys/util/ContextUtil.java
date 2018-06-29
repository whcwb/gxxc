package com.cwb.platform.sys.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.util.exception.RuntimeCheck;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class ContextUtil {
    public static SysYh getCurrentUser(boolean required){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SysYh user =  (SysYh)request.getAttribute("userInfo");
        if (required){
            RuntimeCheck.ifNull(user,"未找到登录用户");
        }
        return user;
    }
    public static SysYh getCurrentUser(){
        return getCurrentUser(false);
    }

    public static Map<String,String> getParamMap(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String,String[]> map = request.getParameterMap();
        Map<String,String> paramMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            StringBuilder val = new StringBuilder();
            String[] array = entry.getValue();
            if (array.length == 1){
                val = new StringBuilder(array[0]);
            }else if (array.length> 1){
                for (String s : array) {
                    val.append(s).append(",");
                }
            }
            paramMap.put(entry.getKey(),val.toString());
        }
        return paramMap;
    }
}
