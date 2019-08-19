package com.cwb.platform.biz.wxpkg.handler;

import com.cwb.platform.biz.service.impl.PtyhServiceImpl;
import com.cwb.platform.biz.wxpkg.budiler.TextBuilder;
import lombok.extern.log4j.Log4j2;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * * @author Binary Wang(https://github.com/binarywang)
 */
@Component
@Log4j2
public class SubscribeHandler extends AbstractHandler {

	@Value("${wechat.appId}")
	private String appId;
	@Value("${wechat.domain}")
	private String domain;
	@Autowired
	private PtyhServiceImpl ptyhService;

//	@Autowired
//	private WechatService wechatService;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService,
			WxSessionManager sessionManager) throws WxErrorException {

		this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());
		log.info("新关注用户 OPENID: " + wxMessage.getFromUser());
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
		String ticket = wxMessage.getTicket();
		log.info("新关注用户 ticket: " + wxMessage.getFromUser());
		try {
			WxMenu wxMenu = new WxMenu();
			List<WxMenuButton> wxButtons = new ArrayList<>();

			WxMenuButton button1 = new WxMenuButton();
			button1.setKey("button1");
			button1.setName("进入平台");//张总要求修改
			button1.setType(WxConsts.MenuButtonType.VIEW);
			button1.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+domain+"/wx&response_type=code&scope=snsapi_userinfo&state=debug&connect_redirect=1#wechat_redirect");

			WxMenuButton button2 = new WxMenuButton();
			button2.setKey("button2");
			button2.setName("关于我们");
			button2.setType(WxConsts.MenuButtonType.VIEW);
			button2.setUrl("http://www.520xclm.com?t="+System.currentTimeMillis());

			WxMenuButton button3 = new WxMenuButton();
			button3.setKey("appdown");
			button3.setName("APP下载");
			button3.setType(WxConsts.MenuButtonType.CLICK);

			wxButtons.add(button1);
			wxButtons.add(button2);
			wxButtons.add(button3);

			wxMenu.setButtons(wxButtons);
			// 设置菜单

			this.logger.info("设置菜单: " + wxButtons.size());
			weixinService.getMenuService().menuCreate(wxMenu);

//			WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
//			item.setDescription("您已成为520学车联盟的学员，您的培训流程已启动，请注意接听客服电话。我们会及时安排您的培训流程！");//第一版本
//			item.setDescription("欢迎加入520学车联盟！");//第二版本
//			item.setDescription("吉驾无忧，您身边的学车管家，邀请好友学车，享优惠！");//20180807版本
//			item.setPicUrl("http://www.520xclm.com:8001/banner.png");
//			item.setTitle("感谢关注");
//			item.setUrl("http://www.520xclm.com/wx");
//			item.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+domain+"/wx&response_type=code&scope=snsapi_userinfo&state=debug&connect_redirect=1#wechat_redirect");

			String EventKey=wxMessage.getEventKey();
//			log.info(EventKey + " :::::::::::::::::::::: eventKey");
			if(StringUtils.isNotEmpty(EventKey)){
				ptyhService.sendRegisterInvite(EventKey.replace("qrscene_",""),wxMessage.getFromUser());
			}

			/*WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
					.fromUser(wxMessage.getToUser())
					.toUser(wxMessage.getFromUser())
					.addArticle(null)
					.build();*/


			this.logger.info("设置菜单:2 " + wxButtons.size());
			return null;
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
