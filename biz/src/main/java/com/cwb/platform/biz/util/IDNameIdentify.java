package com.cwb.platform.biz.util;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class IDNameIdentify {

    public static String indentifyIdCard(String idCard, String userName) {
        String host = "https://safrvcert.market.alicloudapi.com";
        String path = "/safrv_2meta_id_name/";
        String method = "GET";
        String appcode = "73851a79682e4a3f9ff9cc45c4c672c0";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("__userId", "1837927228696901");
//        querys.put("customerID", "customerID");
        querys.put("identifyNum", idCard);
        querys.put("userName", userName);
        querys.put("verifyKey", "IVQr0pYWlJwgEZ");


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            String string = EntityUtils.toString(response.getEntity(), "UTF-8");
            JSONObject object = JSONObject.fromObject(string);
            JSONObject value = object.getJSONObject("value");
//            String verifyUrl = value.getString("verifyUrl");

//            String s = HttpUtil.get(verifyUrl);
            int code = object.getInt("code");
            if (code == 200) {
                int bizCode = value.getInt("bizCode");
                if(bizCode == 0){
                    return "200";
                }else{
                    return "实名认证失败";
                }
            } else {
                return object.getString("message");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "身份验证失败";
    }

    public static String identify(String idCard, String name){
        String host = "https://idcardcert.market.alicloudapi.com";
        String path = "/idCardCert";
        String method = "GET";
        String appcode = "73851a79682e4a3f9ff9cc45c4c672c0";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139xxx
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("idCard", idCard);
        querys.put("name", name);
        //JDK 1.8示例代码请在这里下载：  http://code.fegine.com/java/cmapi029522.zip

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 或者直接下载：
             * http://code.fegine.com/HttpUtils.zip
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             * 相关jar包（非pom）直接下载：
             * http://code.fegine.com/aliyun-jar.zip
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            String string = EntityUtils.toString(response.getEntity(), "UTF-8");
            JSONObject object = JSONObject.fromObject(string);
            String status = object.getString("status");
            if(StringUtils.equals(status,"01")){
                return "200";
            }else{
                return object.getString("msg");
            }
            //System.out.println(response.toString());如不输出json, 请打开这行代码，打印调试头部状态码。
            //状态码: 200 正常；400 URL无效；401 appCode错误； 403 次数用完； 500 API网管错误
            //获取response的body
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "认证失败";
    }


}
