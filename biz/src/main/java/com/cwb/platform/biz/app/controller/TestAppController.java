package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.service.AppOrderService;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/pub")
public class TestAppController {
    @Autowired
    private AppOrderService service;


    /**
     * App支付 （测试）
     */
    @GetMapping("/appPay")
    public ApiResponse<Map<String,String>> appPay(HttpServletRequest request){
        return service.appPay(request);
    }

    /**
     * APP支付回调接口 （测试）
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/test")
    public void app(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] tempBytes = new byte[1024];
        int count = -1;
        while ((count = inputStream.read(tempBytes, 0, 1024)) != -1) {
            outStream.write(tempBytes, 0, count);
        }
        outStream.flush();
        //将流转换成字符串
        String result = new String(outStream.toByteArray(), "UTF-8");
        System.out.println("result -----> " + result);
        /*ServletOutputStream outputStream = response.getOutputStream();
        response.getWriter().write("1");*/


    }

}
