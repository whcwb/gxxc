package com.cwb.platform.biz.wxpkg.handler;

import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.sys.model.BizPtyh;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;
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
				String fromUser = wxMessage.getFromUser();
				List<BizPtyh> ptyhs = ptyhService.findEq(BizPtyh.InnerColumn.yhOpenId, fromUser);
				if(CollectionUtils.isEmpty(ptyhs)){
					try {
						ptyhService.sendRegisterInvite(EventKey,wxMessage.getFromUser());
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}

		//扫码进入公众号
		if (wxMessage.getEvent().equalsIgnoreCase(WxConsts.EventType.SCAN)){

//			String imei = this.stringRedisDao.boundValueOps(ticket).get();
			// 获取到用户的 openid  , 判断用户是否注册过账号 , 如果没有注册需要发送注册跳转链接
			/*String fromUser = wxMessage.getFromUser();
			List<BizPtyh> ptyhs = ptyhService.findEq(BizPtyh.InnerColumn.yhOpenId, fromUser);

			if (StringUtils.isBlank(imei)){
				return WxMpXmlOutMessage.TEXT().content("设备IMEI号不正确").fromUser(wxMessage.getToUser())
						.toUser(wxMessage.getFromUser()).build();
			}else if(CollectionUtils.isEmpty(ptyhs)){
				// 找到受邀请用户的邀请码
				BizPtyh ptyh = ptyhService.findById(ticket);
				if( ptyh == null ){
					return WxMpXmlOutMessage.TEXT().content("未找到用户信息,请确认二维码是否为平台二维码")
							.fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();
				}else{
					return WxMpXmlOutMessage.TEXT().content("<a herf='http://www.520xclm.com?qrCode="+ptyh.getYhZsyqm()+"'>注册页面</a>")
							.fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();
				}
			}*/
		}
		
		return null;
	}
}
