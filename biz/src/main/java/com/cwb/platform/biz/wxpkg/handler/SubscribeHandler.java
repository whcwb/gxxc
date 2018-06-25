package com.cwb.platform.biz.wxpkg.handler;

import com.cwb.platform.biz.wxpkg.budiler.ImageBuilder;
import com.cwb.platform.biz.wxpkg.budiler.TextBuilder;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class SubscribeHandler extends AbstractHandler {

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService,
			WxSessionManager sessionManager) throws WxErrorException {

		this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

		// 获取微信用户基本信息
		WxMpUser userWxInfo = weixinService.getUserService().userInfo(wxMessage.getFromUser());

		if (userWxInfo != null) {}else{
			return new TextBuilder().build("操作失败，请重新关注本公众号！", wxMessage, weixinService);
		}

		WxMpXmlOutMessage responseResult = null;
		try {
			responseResult = handleSpecial(wxMessage);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}

		if (responseResult != null) {
			return responseResult;
		}

		try {
			WxMenu wxMenu = new WxMenu();
			List<WxMenuButton> wxButtons = new ArrayList<WxMenuButton>();
			WxMenuButton wodefuwu = new WxMenuButton();
			wodefuwu.setKey("wodefuwu");
			wodefuwu.setName("我要学车");
			wodefuwu.setType(WxConsts.MenuButtonType.VIEW);
			wodefuwu.setUrl("http://xclm.xxpt123.com/wx");
			wxButtons.add(wodefuwu);

			wxMenu.setButtons(wxButtons);
			// 设置菜单
			weixinService.getMenuService().menuCreate(wxMenu);
//
//			WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
//			item.setDescription("description");
//			item.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1529838597564&di=315b4a41d85819536f135758457c3ed2&imgtype=0&src=http%3A%2F%2Fpic1.16pic.com%2F00%2F07%2F66%2F16pic_766152_b.jpg");
//			item.setTitle("title");
//			item.setUrl("http://xclm.xxpt123.com/wx");
//
//			WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
//					.fromUser(wxMessage.getToUser())
//					.toUser(wxMessage.getFromUser())
//					.addArticle(item)
//					.build();


			return new TextBuilder().build("感谢关注", wxMessage, weixinService);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
	 */
	private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage) throws Exception {
		// TODO
		return null;
	}

}
