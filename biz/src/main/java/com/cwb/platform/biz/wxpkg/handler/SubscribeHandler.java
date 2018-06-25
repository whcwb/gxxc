package com.cwb.platform.biz.wxpkg.handler;

import com.cwb.platform.biz.wxpkg.budiler.ImageBuilder;
import com.cwb.platform.biz.wxpkg.budiler.TextBuilder;
import com.cwb.platform.sys.service.ZdxmService;
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
			wodefuwu.setUrl("http://www.520xclm.com/wx");
			wxButtons.add(wodefuwu);

			wxMenu.setButtons(wxButtons);
			// 设置菜单
			weixinService.getMenuService().menuCreate(wxMenu);

			WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
			item.setDescription("您已成为520学车联盟的学员，您的培训流程已启动，请注意接听客服电话。我们会及时安排您的培训流程！");
			item.setPicUrl("http://www.520xclm.com:8001/wechatImg.gif");
			item.setTitle("感谢关注");
			item.setUrl("http://www.520xclm.com/wx");

			WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
					.fromUser(wxMessage.getToUser())
					.toUser(wxMessage.getFromUser())
					.addArticle(item)
					.build();
			return m;
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
