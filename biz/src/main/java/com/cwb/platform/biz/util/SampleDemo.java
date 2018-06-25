package com.cwb.platform.biz.util;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.cwb.platform.util.commonUtil.FileUtil;
import com.cwb.platform.util.commonUtil.ImgUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片物体检测识别接口  demo
 */
public class SampleDemo {
    //设置APPID/AK/SK
    public static final String APP_ID = "11437782";
    public static final String API_KEY = "LWlbEbioIv9aWIve9ZOZ583W";
    public static final String SECRET_KEY = "vcoxuovajxrtxj0MhhSRZGWxz3Iy3g9E";

    public static void main(String[] args) {
        // 初始化一个AipImageClassifyClient
//        AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);
//
//        // 可选：设置网络连接参数
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);
//
//        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
////        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
////        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
//
////        AppID：11437782
////        API Key：LWlbEbioIv9aWIve9ZOZ583W
////        Secret Key：vcoxuovajxrtxj0MhhSRZGWxz3Iy3g9E
//
//        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
//        // 也可以直接通过jvm启动参数设置此环境变量
////        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
//
//
////        {
////            "result": {
////            "top": 881,
////                    "left": 1591,
////                    "width": 1238,
////                    "height": 1846
////        },
////            "log_id": 350067576481702495
////        }
//        // 调用接口
//        String path = "/Users/yangx/Desktop/ccc.jpg";

//        JSONObject res = client.objectDetect(path, new HashMap<String, String>());
//        System.out.println(res.toString(2));

//        BufferedImage bufferedimage= null;
//        try {
//            bufferedimage = ImageIO.read(new File(path));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////     * @param startX 裁剪开始x坐标
////     * @param startY 裁剪开始y坐标
////     * @param endX 裁剪结束x坐标
////     * @param endY 裁剪结束y坐标
//        bufferedimage=ImgUtils.cropImage(bufferedimage,1591,881,(int) 1591+1238,881+1846);
//
//        try {
//            ImageIO.write(bufferedimage, "jpg", new File(path));    //输出裁剪图片
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



        Boolean type=tailorSubjectImg( "/Users/yangx/Desktop/ccc.jpg");
        System.out.printf("type="+type);
    }

    /**
     * 识别文件主体
     *
     * @param imgUrl
     * @return
     * 距上: 881px    top
     * 距左: 1591px   left
     * 宽度: 1238px   width
     * 高度: 1846px   height
     */
    public static Map<String,Long> FileImgDetects(String imgUrl){
        Map<String,Long> map=new HashMap<>();
        // 初始化一个AipImageClassifyClient
        AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        JSONObject res = client.objectDetect(imgUrl, new HashMap<String, String>());
        try {
            long top=res.getJSONObject("result").getInt("top");
            long left=res.getJSONObject("result").getInt("left");
            long width=res.getJSONObject("result").getInt("width");
            long height=res.getJSONObject("result").getInt("height");
            map.put("top",top);
            map.put("left",left);
            map.put("width",width);
            map.put("height",height);
        }catch (Exception e){
            return null;
        }
        return map;
    }
    /**
     * 截取主体文件照片
     * @param imgUrl 需要处理的图片
     * @return
     */
    public static Boolean tailorSubjectImg(String imgUrl){

        Boolean ret=false;
        if(StringUtils.isEmpty(imgUrl)){
            return ret;
        }
        //判断文件是否存在
        File file = new File(imgUrl);
        if(!file.isFile()){
            return ret;
        }
        String fileName=file.getName();//获取文件名
        String fileTypePart=FileUtil.getTypePart(fileName);//生成文件后缀名
        String filePart=FileUtil.trimType(imgUrl);
        String filePartCopy=filePart+"-backups";
        Boolean copyFileType=FileUtil.CopyFile(imgUrl,filePartCopy+"."+fileTypePart);
        if(!copyFileType){
            return ret;
        }

        BufferedImage bufferedimage= null;
        try {
            bufferedimage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bufferedimage==null){
            return ret;
        }
        int width = bufferedimage.getWidth();
        int height = bufferedimage.getHeight();
        Map<String,Long > map=SampleDemo.FileImgDetects(imgUrl);
        if(map==null){
            return ret;
        }

        if(map.get("left")+map.get("width")>width){
            return ret;
        }

        bufferedimage=ImgUtils.cropImage(bufferedimage, Math.toIntExact(map.get("left")),Math.toIntExact(map.get("top")),
                Math.toIntExact(map.get("left"))+Math.toIntExact(map.get("width")),Math.toIntExact(map.get("top"))+Math.toIntExact(map.get("height")));

        try {
            ImageIO.write(bufferedimage, "jpg", new File(imgUrl));    //输出裁剪图片
        } catch (IOException e) {
            return ret;
        }

        return true;

    }
}