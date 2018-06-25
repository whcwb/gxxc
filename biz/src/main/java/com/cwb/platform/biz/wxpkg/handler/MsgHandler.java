package com.cwb.platform.biz.wxpkg.handler;

import com.cwb.platform.biz.wxpkg.budiler.ImageBuilder;
import com.cwb.platform.biz.wxpkg.budiler.TextBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService,
			WxSessionManager sessionManager) {

		if (!wxMessage.getMsgType().equals(WxConsts.XmlMsgType.EVENT)) {
			// TODO 可以选择将消息保存到本地
		}

		ObjectMapper mapper = new ObjectMapper();
		// TODO 组装回复消息
		String content = "";
		try {
			content = "收到信息内容：" + mapper.writeValueAsString(wxMessage);
		} catch (JsonProcessingException e) {
			
		}
//		WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
//		item.setDescription("description");
//		item.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1529906813742&di=d79fc4654e119c6d2ce6422f8348932f&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01867c57fca5c3a84a0e282b666b41.gif");
//		item.setTitle("title");
//		item.setUrl("http://www.520xclm.com/wx");
//
//		WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
//				.fromUser(wxMessage.getToUser())
//				.toUser(wxMessage.getFromUser())
//				.addArticle(item)
//				.build();
//		return m;

//		return new ImageBuilder().build("http://xclm.xxpt123.com:8001/123456789.png",wxMessage,weixinService);
//
		return new TextBuilder().build("敬请期待", wxMessage, weixinService);

	}

}
