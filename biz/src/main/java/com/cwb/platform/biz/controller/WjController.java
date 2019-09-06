package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizWj;
import com.cwb.platform.biz.service.WjService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.SnowflakeIdWorker;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/wj")
public class WjController extends QueryController<BizWj,String> {
//public class WjController{
    @Autowired
    private WjService service;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Override
    protected BaseService<BizWj, String> getBaseService() {
        return service;
    }


    @PostMapping("/uploadWj")
    public ApiResponse<String> uploadWj(@RequestParam("file") MultipartFile file, String  userId){
        RuntimeCheck.ifBlank(userId, "请上传用户id");
        //  上传学生证
        String fileName = DateTime.now().toString("yyyy-MM-dd") + File.separator  +  userId  + "-" + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("\\."));
        try {
            InputStream stream = file.getInputStream();
            FileUtils.copyInputStreamToFile(stream, new File(fileName));
            BizWj bizWj = new BizWj();
            bizWj.setId(snowflakeIdWorker.nextId() + "");
            bizWj.setCjsj(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            bizWj.setWjTpdz(fileName);
            bizWj.setWjSx("31");
            service.save(bizWj);
        } catch (IOException e) {
            RuntimeCheck.ifTrue(true, "文件上传失败");
        }
        return  ApiResponse.success();
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
