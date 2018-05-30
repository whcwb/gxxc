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
		item.setDescription("我的设备");
		item.setPicUrl("http://"+domainUrl+"/weixin/wx_logo.jpg");
		item.setTitle("我的设备");
		item.setUrl("http://"+domainUrl+"/weixin/#/map?openid="+wxMessage.getFromUser());
		
		WxMpXmlOutNewsMessage.Item wfjb = new WxMpXmlOutNewsMessage.Item();
		wfjb.setDescription("违法举报");
		wfjb.setTitle("违法举报");
		wfjb.setUrl("http://"+domainUrl+"/weixin/#/ssp?openid="+wxMessage.getFromUser()+"&ret_url=ssp");
		
		WxMpXmlOutNewsMessage.Item ssp = new WxMpXmlOutNewsMessage.Item();
		ssp.setDescription("随手拍绑定");
		ssp.setTitle("随手拍绑定");
		ssp.setUrl("http://"+domainUrl+"/weixin/#/bindSsp?openid="+wxMessage.getFromUser()+"&ret_url=bindSsp");
		
		WxMpXmlOutNewsMessage.Item photo = new WxMpXmlOutNewsMessage.Item();
		photo.setDescription("云相册");
		photo.setTitle("云相册");
		photo.setUrl("http://"+domainUrl+"/weixin/#/photo?openid="+wxMessage.getFromUser()+"&ret_url=photo");
		
		WxMpXmlOutNewsMessage.Item video = new WxMpXmlOutNewsMessage.Item();
		video.setDescription("云视频");
		video.setTitle("云视频");
		video.setUrl("http://"+domainUrl+"/weixin/#/video?openid="+wxMessage.getFromUser()+"&ret_url=video");

		WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
		  .fromUser(wxMessage.getToUser())
		  .toUser(wxMessage.getFromUser())
		  .addArticle(item)
		  .addArticle(wfjb)
		  .addArticle(ssp)
		  .addArticle(photo)
		  .addArticle(video)
		  .build();
		
		return m;
	}

}
