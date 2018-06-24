package com.cwb.platform.biz.util;

import lombok.Getter;
import lombok.Setter;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@Getter
@Setter
public class SendWechatMsgEvent {
    private WxMpTemplateMessage data;

    public SendWechatMsgEvent(WxMpTemplateMessage data){
        this.data = data;
    }

}
