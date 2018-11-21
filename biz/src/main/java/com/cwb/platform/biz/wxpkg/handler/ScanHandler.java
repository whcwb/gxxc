package com.cwb.platform.biz.wxpkg.handler;

import com.cwb.platform.biz.service.PtyhService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class ScanHandler extends AbstractHandler {
	
	@Autowired
	private StringRedisTemplate stringRedisDao;

	@Autowired
	private PtyhService ptyhService;

//	@Autowired
//	private WechatService wechatService;


	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService,
			WxSessionManager sessionManager) {
		String ticket = wxMessage.getTicket();
		this.logger.info("新关注用户 ticket 扫码: " + wxMessage.getTicket());
		if(org.apache.commons.lang.StringUtils.isNotEmpty(ticket)){
			String EventKey=wxMessage.getEventKey();
			if(StringUtils.isNotEmpty(EventKey)){
				ptyhService.sendRegisterInvite(EventKey,wxMessage.getFromUser());
			}
		}

		//扫码进入公众号
		if (wxMessage.getEvent().equalsIgnoreCase(WxConsts.EventType.SCAN)){
			String imei = this.stringRedisDao.boundValueOps(ticket).get();
			if (StringUtils.isBlank(imei)){
				return WxMpXmlOutMessage.TEXT().content("设备IMEI号不正确").fromUser(wxMessage.getToUser())
						.toUser(wxMessage.getFromUser()).build();
			}else{}
		}
		
		return WxMpXmlOutMessage.TEXT().content("请求消息不正确").fromUser(wxMessage.getToUser())
				.toUser(wxMessage.getFromUser()).build();
	}
}
