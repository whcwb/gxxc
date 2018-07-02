package com.cwb.platform.biz.wxpkg.service.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.cwb.platform.biz.wxpkg.service.WechatService;
import com.cwb.platform.util.commonUtil.SendSmsUtil;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class WechatServiceImpl implements WechatService {
    Logger log = LoggerFactory.getLogger("access_info");

    @Autowired
    private WxMpService wxService;
    @Override
    public String sendTemplateMsg(WxMpTemplateMessage var1,List<Map<String, String>> smsMapList) throws WxErrorException {
        if(smsMapList!=null&&smsMapList.size()>0){
            for(Map<String,String> smsMap:smsMapList){
                Map<String, String> sendSmsMap=disposeSmsMessage(smsMap);
                if(sendSmsMap!=null){
                    sendSms(sendSmsMap);
                }
            }
        }
        if(var1!=null){
            WxMpTemplateMsgService msgService = wxService.getTemplateMsgService();
            return msgService.sendTemplateMsg(var1);
        }else{
            return "本次操作只进行短信下发";
        }
    }

    private Map<String,String> disposeSmsMessage(Map<String, String> smsMap) {

        Map<String, String> sendSmsMap=new HashMap<String, String>();

        Map<String,String> map=new HashMap<>();
        map.put("1","SMS_138068124");//学员缴费成功  恭喜您已缴费成功，成为学车联盟的学员。请您保持手机畅通，我们会跟您取得联系，以便安排您的学车流程！祝您学车愉快！
        map.put("2","SMS_138073086");//会员缴费成功  恭喜您成功加入学车联盟平台，平台客服电话${tel}，如果您有疑问可以与我们取得联系！
        map.put("3","SMS_138068443");//分配成功后，给专员下发短信  ${userName}专员，你好！平台为您分配了一位新学员（${studentUser}），电话号码是（${studentTel}），请您及时与他联系并安排培训！
        map.put("4","SMS_138078052");//尊敬的${userName}学员，您好！平台为您分配了${stage}的培训专员（${user}），电话号码是(${tel})，请保持手机畅通，以便专员与您联系！
        map.put("5","SMS_138063513");//尊敬的${userName},操作员:${operator}于${dates}修改产品名称：${cpName}类型：${cpType}金额：${cpMoney}属性${parameter},本次操作编号${code}及时授权

        String templateType=smsMap.get("templateType");
        if(StringUtils.isEmpty(templateType)){
            log.debug("未找到模板编号，下发短信失败");
            return null;
        }
        String phoneNumbers=smsMap.get("phoneNumbers");//手机号码
        if(StringUtils.isEmpty(phoneNumbers)){
            log.debug("没有手机号码，下发失败");
            return null;
        }
        String templateCode=map.get(templateType);//获取模板编号
        if(StringUtils.isEmpty(templateType)){
            log.debug("未找到模板编号，下发短信失败");
            return null;
        }

        sendSmsMap.put("phoneNumbers",phoneNumbers);//手机号码
        sendSmsMap.put("templateCode",templateCode);//模板编号
        String templateParam="";
        if(StringUtils.equals(templateType,"3")){
            String userName=smsMap.get("userName");//专员姓名
            String studentUser=smsMap.get("studentUser");//学员姓名
            String studentTel=smsMap.get("studentTel");//学员手机号码
            if(StringUtils.isEmpty(userName)){
                log.debug("专员姓名不能为空");
                return null;
            }
            if(StringUtils.isEmpty(studentUser)){
                log.debug("学员姓名不能为空");
                return null;
            }
            if(StringUtils.isEmpty(studentTel)){
                log.debug("学员手机号码不能为空");
                return null;
            }
            templateParam="{\"userName\":\"" + userName + "\",\"studentUser\":\"" + studentUser + "\",\"studentTel\":\"" + studentTel + "\"}";
            sendSmsMap.put("templateParam",templateParam);
        }else if(StringUtils.equals(templateType,"4")){
            //尊敬的${userName}学员，您好！平台为您分配了${stage}的培训专员（${user}），电话号码是(${tel})，请保持手机畅通，以便专员与您联系！
            String userName=smsMap.get("userName");//专员姓名
            String stage=smsMap.get("stage");//第几阶段
            String user=smsMap.get("user");//教练员姓名
            String tel=smsMap.get("tel");//教练员电话

            templateParam="{\"userName\":\"" + userName + "\",\"stage\":\"" + stage + "\",\"user\":\"" + user + "\",\"tel\":\"" + tel + "\"}";
            sendSmsMap.put("templateParam",templateParam);

        }else if(StringUtils.equals(templateType,"5")){
            //尊敬的${userName},操作员:${operator}于${dates}修改产品名称：${cpName}类型：${cpType}金额：${cpMoney}属性${parameter},本次操作编号${code}及时授权
            String userName=smsMap.get("userName");//专员姓名
            String operator=smsMap.get("operator");//专员姓名
            String dates=smsMap.get("dates");//专员姓名

            String cpName=smsMap.get("cpName");
            String cpType=smsMap.get("cpType");
            String cpMoney=smsMap.get("cpMoney");

            String parameter=smsMap.get("parameter");//专员姓名
            String code=smsMap.get("code");//专员姓名
            templateParam="{\"userName\":\"" + userName + "\",\"operator\":\"" + operator + "\",\"dates\":\"" + dates + "\",\"cpName\":\"" + cpName + "\",\"cpType\":\"" + cpType + "\",\"cpMoney\":\"" + cpMoney + "\",\"parameter\":\"" + parameter + "\",\"code\":\"" + code + "\"}";
            sendSmsMap.put("templateParam",templateParam);
        }
        return sendSmsMap;
    }

    public Boolean sendSms(Map<String,String> requestMap){
//        log.debug("专员姓名不能为空");
        Boolean ret=false;
        try {
            SendSmsResponse response= SendSmsUtil.sendSmsMap(requestMap);
            if(response.getCode() != null && response.getCode().equals("OK")) {
                ret=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
