package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.mapper.BizWjMapper;
import com.cwb.platform.biz.model.BizWj;
import com.cwb.platform.biz.service.WjService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class WjServiceImpl extends BaseServiceImpl<BizWj,java.lang.String> implements WjService{
    @Autowired
    private BizWjMapper entityMapper;

    //设置APPID/AK/SK
    public static final String APP_ID = "sE9HAgpK2XKsrKiuorzCoory";
    public static final String API_KEY = "sE9HAgpK2XKsrKiuorzCoory";
    public static final String SECRET_KEY = "HNyUWG5Od5docsq83l5l6hVgdKv6Etng";

    @Override
    protected Mapper<BizWj> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizWj.class;
    }

    /**
     * 图片识别
     * @param retMap
     * @param fileType  上传的文件属性  文件属性 ZDCLK0050 (10、 身份证正面 11、 身份证反面  20、 驾照正面 21、 驾照背面…………)
     * @param filePath  文件地址
     */
    @Override
    public void ocrRecognition(Map<String, String> retMap, String fileType,String filePath){
        String path=retMap.get("path");//文件地址
// TODO: 2018/6/19 这里需要重写
        // 初始化一个AipOcr
        //解析文件
        if(StringUtils.indexOf(fileType,"1")==0){//身份证识别
            HashMap<String, String> options = new HashMap<String, String>();
//            options.put("detect_direction", "true");//是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括: - true：检测朝向；  - false：不检测朝向。
            options.put("detect_risk", "false");//是否开启身份证风险类型(身份证复印件、临时身份证、身份证翻拍、修改过的身份证)功能，默认不开启，即：false。可选值:true-开启；false-不开启

            String idCardSide = "back";//front：身份证含照片的一面；back：身份证带国徽的一面
            //10、 身份证正面 11、 身份证反面
            if(StringUtils.equals(fileType,"10")){
                idCardSide = "front";
            }

        }else if(StringUtils.indexOf(fileType,"2")==0){//驾驶证识别

        }
    }
}
