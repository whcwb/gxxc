package com.cwb.platform.biz.wxpkg.service;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.util.List;
import java.util.Map;

public interface WechatService {
    String sendTemplateMsg(WxMpTemplateMessage var1,List<Map<String, String>> smsMap) throws WxErrorException;
}
