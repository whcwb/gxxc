package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizWj;
import com.cwb.platform.biz.service.WjService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/wj")
public class WjController extends QueryController<BizWj,String> {
//public class WjController{
    @Autowired
    private WjService service;



    @Override
    protected BaseService<BizWj, String> getBaseService() {
        return service;
    }


    /**
     * 查看证件
     * @param response
     * @param userId
     * @param fileType
     */
    @RequestMapping("zjck")
    public void fileDownload(HttpServletResponse response, @RequestParam(name = "userId") String userId, @RequestParam(name = "fileType") String fileType){

       String path=service.getFilePath(userId,fileType);
        //获取网站部署路径(通过ServletContext对象)，用于确定下载文件位置
        if(StringUtils.isNotEmpty(path)){
            ServletOutputStream out=null;
            FileInputStream inputStream=null;
            //通过文件路径获得File对象(假如此路径中有一个 zms.jpg 文件)
            File file = new File(path );
            try {
                inputStream  = new FileInputStream(file);
                //3.通过response获取ServletOutputStream对象(out)
                out = response.getOutputStream();
                int b = 0;
                byte[] buffer = new byte[512];
                while (b != -1){
                    b = inputStream.read(buffer);
                    if(b != -1){
                        out.write(buffer,0,b);//4.写到输出流(out)中
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                try{
                    if(inputStream!=null){
                        inputStream.close();
                    }
                    if(out!=null){
                        out.close();
                        out.flush();
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
