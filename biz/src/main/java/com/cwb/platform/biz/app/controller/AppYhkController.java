package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.model.BizYhk;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.biz.service.YhkService;
import com.cwb.platform.biz.util.HttpUtils;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.exception.RuntimeCheck;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("acct_name", queryUser.getYhXm());//用户姓名
        querys.put("acct_pan", entity.getYhkKh());//卡号
        querys.put("cert_id", queryUser.getYhZjhm());//用户证件号码
        querys.put("phone_num", entity.getDn());//银行预留的手机号码
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == 200) {
                String str = "";
                /**读取服务器返回过来的json字符串数据**/
                str = EntityUtils.toString(response.getEntity());
                str=str.replaceAll("\\/","\\");
                /**把json字符串转换成json对象**/
                JSONObject jsonResult = JSONObject.fromObject(str);
                String code=jsonResult.getJSONObject("resp").getString("code");
                String desc=jsonResult.getJSONObject("resp").getString("desc");

                map.put("code",code);
                map.put("desc",desc);
                map.put("data",str);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String code=map.get("code");
        ApiResponse<Map<String,String>> res = new ApiResponse<Map<String,String>>();
        if(StringUtils.equals(code,"0")){
            res.setCode(200);
        }else{
            res.setCode(500);
        }
        res.setMessage(map.get("desc"));
        res.setResult(map);
        return res;
    }
}
