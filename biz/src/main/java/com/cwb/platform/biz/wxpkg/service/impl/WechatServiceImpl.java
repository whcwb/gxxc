package com.cwb.platform.biz.wxpkg.service.impl;

import com.cwb.platform.biz.wxpkg.service.WechatService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WechatServiceImpl implements WechatService {

    @Autowired
    private WxMpService wxService;
    @Override
    public String sendTemplateMsg(WxMpTemplateMessage var1) throws WxErrorException {
        WxMpTemplateMsgService msgService = wxService.getTemplateMsgService();
        return msgService.sendTemplateMsg(var1);
    }
}
