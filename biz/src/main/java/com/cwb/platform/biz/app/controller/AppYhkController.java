package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.model.BizYhk;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.biz.service.YhkService;
import com.cwb.platform.biz.util.HttpUtils;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.StringDivUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 银行卡管理
 *
 */
@RestController
@RequestMapping("/app/yhk")
public class AppYhkController extends AppUserBaseController {


    @Autowired
    private YhkService service;

    @Autowired
    private PtyhService ptyhService;

    @Autowired
    private StringRedisTemplate redisDao;
    // 忽略当接收json字符串中没有bean结构中的字段时抛出异常问题
    private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * 数据新增方法
     *
     * @param entity
     * @return
     */
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(BizYhk entity){
        BizPtyh user= getAppCurrentUser();
        RuntimeCheck.ifNull(entity,"入参不能为空");
        RuntimeCheck.ifNull(entity.getYhkKh(),"银行卡号不能为空");
        RuntimeCheck.ifNull(entity.getYhkXm(),"户主姓名不能为空");
        String validate=redisDao.boundValueOps("appUserYhkValidateError-"+user.getId()).get();
        if(StringUtils.isNotEmpty(validate)){
                return ApiResponse.fail(validate);
        }
        try {
            BizYhk bizYhk = mapper.readValue(redisDao.boundValueOps("appUserYhkValidate-"+user.getId()).get(), BizYhk.class);
            entity.setYhkLogo(bizYhk.getYhkLogo());
            entity.setYhkSsyh(bizYhk.getYhkSsyh());
        } catch (IOException e) {
            e.printStackTrace();
        }
        entity.setYhId(user.getId());
        return service.addUserCreditCard(entity);
    }


    /**
     * 数据修改方法
     *
     * @param entity
     * @return
     */
    @RequestMapping(value="/update", method={RequestMethod.POST})
    public ApiResponse<String> update(BizYhk entity){
        BizPtyh user= getAppCurrentUser();
        BizYhk obd=service.findById(entity.getId());
        RuntimeCheck.ifNull(obd,"该银行卡不存在");
        RuntimeCheck.ifFalse(StringUtils.equals(obd.getYhId(),user.getId()),"该卡查询异常，请刷新页面后重新重新尝试");

        return service.validAndUpdate(entity);
    }

    /**
     * 数据删除方法
     * 如果对数据要求高，请重写该方法或是不直接继承该类，防止数据泄露
     * @param id
     * @return
     */
    @RequestMapping(value="/remove/{pkid}", method={RequestMethod.POST})
    public ApiResponse<String> remove(@PathVariable("pkid")String id){
        BizPtyh user= getAppCurrentUser();
        BizYhk obd=service.findById(id);
        RuntimeCheck.ifNull(obd,"该银行卡不存在");
        RuntimeCheck.ifFalse(StringUtils.equals(obd.getYhId(),user.getId()),"该卡查询异常，请刷新页面后重新重新尝试");
        service.remove(id);
        return ApiResponse.success();
    }
    /**
     * 根据对象字段值查询数据
     * @param entity
     * @return
     */
    @RequestMapping(value="/getCondition", method={RequestMethod.POST})
    public ApiResponse<List<BizYhk>> getCondition(BizYhk entity){
        BizPtyh user= getAppCurrentUser();
        entity.setYhId(user.getId());
        List<BizYhk> list=service.getList(entity);
        if(list!=null&&list.size()>0){
           for(BizYhk l:list){
               String yhkId=l.getYhkKh();
                l.setYhkKh(StringDivUtils.hideCardNo(yhkId));
           }
        }
        return ApiResponse.success(list);
    }

    /**
     * 数据新增方法
     *
     * @param entity
     * @return
     */
    @RequestMapping(value="/yhkyz", method={RequestMethod.POST})
    public ApiResponse<Map<String,String>> validate(BizYhk entity){
        Map<String,String> map=new HashMap<>();
        BizPtyh user= getAppCurrentUser();
        RuntimeCheck.ifNull(entity,"入参不能为空");
        RuntimeCheck.ifNull(entity.getYhkKh(),"银行卡号不能为空");
        RuntimeCheck.ifNull(entity.getDn(),"手机号码不能为空");
        BizPtyh queryUser=ptyhService.findById(user.getId());

        String host = "http://lundroid.market.alicloudapi.com";
        String path = "/lianzhuo/verifi";
        String method = "GET";
        String appcode = "73851a79682e4a3f9ff9cc45c4c672c0";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        RuntimeCheck.ifNull(queryUser,"该用户不存在");
        Map<String, String> querys = new HashMap<>();
        querys.put("acct_name", queryUser.getYhXm());//用户姓名
        querys.put("acct_pan", entity.getYhkKh());//卡号
        querys.put("cert_id", queryUser.getYhZjhm());//用户证件号码
        querys.put("phone_num", entity.getDn());//银行预留的手机号码

//        querys.put("acct_name", entity.getYhkXm());//用户姓名
//        querys.put("acct_pan", entity.getYhkKh());//卡号
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
//            System.out.println(response.toString());
            //获取response的body
            String str = "";
//            System.out.println(EntityUtils.toString(response.getEntity()));
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == 200) {
                /**读取服务器返回过来的json字符串数据**/
                str = EntityUtils.toString(response.getEntity());
//                str=str.replaceAll("\\/","\\");
                /**把json字符串转换成json对象**/
                JSONObject jsonResult = JSONObject.fromObject(str);
                String code=jsonResult.getJSONObject("resp").getString("code");
                String desc=jsonResult.getJSONObject("resp").getString("desc");
                try {
                    map.put("bank_name",jsonResult.getJSONObject("data").getString("bank_name"));
                    map.put("card_name",jsonResult.getJSONObject("data").getString("card_name"));
                    map.put("card_type",jsonResult.getJSONObject("data").getString("card_type"));
                    map.put("bank_phone",jsonResult.getJSONObject("data").getString("bank_phone"));
                    map.put("bank_logo",jsonResult.getJSONObject("data").getString("bank_logo"));
                }catch (Exception E){}
                map.put("code",code);
                map.put("desc",desc);
                map.put("data",str);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        BizYhk yhk=new BizYhk();
        yhk.setYhkLogo(map.get("bank_logo"));
        yhk.setYhkSsyh(map.get("bank_name"));

        redisDao.delete("appUserYhkValidateError-"+user.getId());
        String code=map.get("code");
        ApiResponse<Map<String,String>> res = new ApiResponse<Map<String,String>>();
        if(StringUtils.equals(code,"0")){
            String card_type=map.get("card_type");
            if(StringUtils.isNotEmpty(card_type)){
                if(StringUtils.equals(card_type,"借记卡")||StringUtils.equals(card_type,"储蓄卡")){
                    res.setCode(200);
                    try {
                        redisDao.boundValueOps("appUserYhkValidate-"+user.getId()).set(mapper.writeValueAsString(yhk), 1, TimeUnit.DAYS);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }else{
                    res.setCode(500);
                    map.put("desc","提现卡，仅限：借记卡或储蓄卡");
                    redisDao.boundValueOps("appUserYhkValidateError-"+user.getId()).set(map.get("desc"), 1, TimeUnit.DAYS);
                }
            }else {
                redisDao.boundValueOps("appUserYhkValidate-"+user.getId()).set("200", 1, TimeUnit.DAYS);
                res.setCode(200);
            }
        }else{
            redisDao.boundValueOps("appUserYhkValidateError-"+user.getId()).set(map.get("desc"), 1, TimeUnit.DAYS);
            res.setCode(500);
        }
        res.setMessage(map.get("desc"));
        res.setResult(map);
        return res;
    }
}
