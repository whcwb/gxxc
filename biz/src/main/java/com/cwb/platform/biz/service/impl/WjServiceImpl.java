package com.cwb.platform.biz.service.impl;


import com.baidu.aip.ocr.AipOcr;
import com.cwb.platform.biz.mapper.BizWjMapper;
import com.cwb.platform.biz.model.BizWj;
import com.cwb.platform.biz.service.WjService;
import com.cwb.platform.biz.util.SampleDemo;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.mapper.SysYhJsMapper;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.sys.model.SysYhJs;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class WjServiceImpl extends BaseServiceImpl<BizWj,java.lang.String> implements WjService{
    @Autowired
    private BizWjMapper entityMapper;
    @Autowired
    private SysYhJsMapper userRoleMapper;

    //证件上传地址
    @Value("${credentialsPath}")
    private String credentialsPath;


    @Autowired
    private StringRedisTemplate redisDao;
    // 忽略当接收json字符串中没有bean结构中的字段时抛出异常问题
    private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(1));
    public WjServiceImpl() {
        eventBus.register(this);
    }

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
     * @param user
     */
    @Override
    public boolean ocrRecognition(Map<String, String> retMap, String fileType, String filePath, String paths, BizPtyh user){
        boolean retType=true;
        if(user==null){
            user= getAppCurrentUser();
        }
// TODO: 2018/6/19 这里需要重写
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        //解析文件
        if(StringUtils.indexOf(fileType,"1")==0){//身份证识别
            HashMap<String, String> options = new HashMap<String, String>();
//            options.put("detect_direction", "true");//是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括: - true：检测朝向；  - false：不检测朝向。
            options.put("detect_risk", "false");//是否开启身份证风险类型(身份证复印件、临时身份证、身份证翻拍、修改过的身份证)功能，默认不开启，即：false。可选值:true-开启；false-不开启

            String idCardSide = "back";//front：身份证含照片的一面；back：身份证带国徽的一面
            //10、 身份证正面 11、 身份证反面
            if(StringUtils.equals(fileType,"10")){
                idCardSide = "front";
            }else {
                retMap.put("filePath",paths);//文件地址
                return true;
            }

            JSONObject res = client.idcard(filePath, idCardSide, options);
            try {
                String image_message="";
                String error_code="";
                try {
                    Object object = res.get("error_code");
                    if(object!=null){
                        if (object instanceof String) {
                            error_code =(String) object;
                        }else if(object instanceof Integer ){
                            error_code =object+"";
                        }else {
                            error_code="未知错误";
                        }
                    }
                }catch (Exception e){}
                if(StringUtils.equals(error_code,"216633")){
                    retType=false;
                    image_message="识别身份证错误-您上传了非身份证图片或您上传的身份证图片不完整";
                }else if(StringUtils.isNotEmpty(error_code)){
                    retType=false;
                    image_message="识别身份证错误";
                }

                if(retType){
                    String image_status=res.getString("image_status");
                    if(StringUtils.equals(image_status,"reversed_side")){//
                        image_message="未摆正身份证";
                    }else if(StringUtils.equals(image_status,"non_idcard")){//
                        image_message="上传的图片中不包含身份证";
                    }else if(StringUtils.equals(image_status,"blurred")){//
                        image_message="身份证模糊";
                    }else if(StringUtils.equals(image_status,"over_exposure")){//
                        image_message="身份证关键字段反光或过曝";
                    }else if(StringUtils.equals(image_status,"unknown")){//
                        image_message="证件识别失败-未知状态";
                    }
                    if(idCardSide.equals("front")){//身份证正面
                        String xm="",xb="",mz="",csrq="",cfzh="",zz="";
                        try {
                            xm=res.getJSONObject("words_result").getJSONObject("姓名").getString("words");
                        }catch (Exception e){}
                        try {
                            xb=res.getJSONObject("words_result").getJSONObject("性别").getString("words");
                        }catch (Exception e){}
                        try {
                            mz=res.getJSONObject("words_result").getJSONObject("民族").getString("words");
                        }catch (Exception e){}
                        try {
                            csrq=res.getJSONObject("words_result").getJSONObject("出生").getString("words");
                        }catch (Exception e){}
                        try {
                            cfzh=res.getJSONObject("words_result").getJSONObject("公民身份号码").getString("words");
                        }catch (Exception e){}
                        try {
                            zz=res.getJSONObject("words_result").getJSONObject("住址").getString("words");
                        }catch (Exception e){}
                        if(StringUtils.isEmpty(xm)||StringUtils.isEmpty(cfzh)){
                            retType=false;
                            retMap.put("image_message","未能识别出信息");
                        }
                        Map<String, String> redisMap=new HashMap<String, String>();
                        redisMap.put("xm",xm);//姓名
                        redisMap.put("xb",xb);//性别
                        redisMap.put("mz",mz);//民族
                        redisMap.put("csrq",csrq);//出生日期
                        redisMap.put("cfzh",cfzh);//公民身份号码
                        redisMap.put("zz",zz);//住址
                        redisMap.put("filePath",paths);//文件地址

                        retMap.put("xm",xm);//姓名
                        retMap.put("cfzh",cfzh.replaceAll("(\\d{3})\\d*(\\d{4})", "$1******$2"));//公民身份号码
                        retMap.put("filePath",paths);//公民身份号码

                        redisDao.delete(user.getId()+"-ocrRecognition-map");
                        //将解析报文保存到redis中进行组缓存
                        redisDao.boundValueOps(user.getId()+"-ocrRecognition-map").set(mapper.writeValueAsString(redisMap), 1, TimeUnit.DAYS);
                    }else{//身份证反面

                    }

                }
                if(StringUtils.isNotEmpty(image_message)){
                    retMap.put("image_message",image_message);
                }
                redisDao.delete(user.getId()+"-ocrRecognition-string");
                redisDao.boundValueOps(user.getId()+"-ocrRecognition-string").set(mapper.writeValueAsString(res.toString(2)), 1, TimeUnit.DAYS);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
                retType=false;
            }

        }else if(StringUtils.indexOf(fileType,"2")==0){//驾驶证识别

        }
        return retType;
    }

    public void tailorSubjectImg(String imgUrl){

        if (StringUtils.isNotEmpty(imgUrl)){
            eventBus.post(imgUrl);
        }
    }
    @Subscribe
    public  void sendGps(String imgUrl){
        SampleDemo.tailorSubjectImg(imgUrl);
    }


    public String getFilePath(String userId, String fileType){
        SysYh sysUser=getCurrentUser();
        if(!StringUtils.equals(sysUser.getLx(),"su")){
            //检查本人是否有权限操作此接口
            SysYhJs yhJs=new SysYhJs();
            yhJs.setYhId(sysUser.getYhid());
            List<SysYhJs> userJsList=userRoleMapper.select(yhJs);
            RuntimeCheck.ifNull(userJsList,"本人无限制进行此操作");
            List<String> userJsLis = userJsList.stream().map(SysYhJs::getJsId).collect(Collectors.toList());
            RuntimeCheck.ifFalse(userJsLis.contains("000000"),"本人无限制进行此操作");
        }

        BizWj bizWj=new BizWj();
        bizWj.setYhId(userId);
        bizWj.setWjSx(fileType);
        List<BizWj> list=this.findByEntity(bizWj);
        String path = "";
        if(list!=null&&list.size()>0){
            path=credentialsPath+list.get(0).getWjTpdz();
        }
        return path;
    }
}
