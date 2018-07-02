package com.cwb.platform.biz.util;

import com.cwb.platform.biz.wxpkg.service.WechatService;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventHandler {
    @Autowired
    private WechatService wechatService;

    @Subscribe
    public void onSendWechatMsg(SendWechatMsgEvent event){
        try {
            String res = wechatService.sendTemplateMsg(event.getData(),null);
            log.info("sendMsg result :",res);
        } catch (WxErrorException e) {
            log.error("发送微信模板消息异常",e);
        }
    }
}
