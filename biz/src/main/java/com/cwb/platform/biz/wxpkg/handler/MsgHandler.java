package com.cwb.platform.biz.wxpkg.handler;

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
//
//		WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
//		item.setDescription("description");
//		item.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1529838597564&di=315b4a41d85819536f135758457c3ed2&imgtype=0&src=http%3A%2F%2Fpic1.16pic.com%2F00%2F07%2F66%2F16pic_766152_b.jpg");
//		item.setTitle("title");
//		item.setUrl("http://xclm.xxpt123.com/wx");
//
//		WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
//				.fromUser(wxMessage.getFromUser())
//				.toUser(wxMessage.getToUser())
//				.addArticle(item)
//				.build();
//
//		return m;

		return new TextBuilder().build("敬请期待", wxMessage, weixinService);

	}

}
