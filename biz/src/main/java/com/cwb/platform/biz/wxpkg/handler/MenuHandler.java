package com.cwb.platform.biz.wxpkg.handler;

import me.chanjar.weixin.common.api.WxConsts.MenuButtonType;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MenuHandler extends AbstractHandler {
	

	@Value("${wechat.domain}")
	private String domainUrl;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService,
			WxSessionManager sessionManager) {

		String msg = String.format("type:%s, event:%s, key:%s", wxMessage.getMsgType(), wxMessage.getEvent(),
				wxMessage.getEventKey());
		if (MenuButtonType.CLICK.equalsIgnoreCase(wxMessage.getEvent())) {
			//点击我的服务按钮

			return null;
		}

		return WxMpXmlOutMessage.TEXT().content(msg).fromUser(wxMessage.getToUser())
				.toUser(wxMessage.getFromUser()).build();
	}
	
	/**
	 * 创建菜单
	 * @param wxMessage
	 * @return
	 */
	public WxMpXmlOutNewsMessage getNewsMenu(WxMpXmlMessage wxMessage){
		WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();

		WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
		  .fromUser(wxMessage.getToUser())
		  .toUser(wxMessage.getFromUser())
		  .addArticle(item)
		  .build();
		
		return m;
	}

}
