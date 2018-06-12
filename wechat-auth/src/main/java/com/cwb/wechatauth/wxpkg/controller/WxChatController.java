package com.cwb.wechatauth.wxpkg.controller;

import com.cwb.wechatauth.wxpkg.config.WxConfig;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *微信接口授权
 */
@RestController
@RequestMapping("wechat")
public class WxChatController {
	@Autowired
	private WxMpService wxService;
	private WxConfig config = WxConfig.getInstance();

	@GetMapping("test")
	public String test(){
		return config.toString();
	}

	/**
	 * 微信服务器认证
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	@GetMapping(produces = "text/plain;charset=utf-8", value="auth")
	public String authGet(@RequestParam(name = "signature", required = false) String signature,
			@RequestParam(name = "timestamp", required = false) String timestamp,
			@RequestParam(name = "nonce", required = false) String nonce,
			@RequestParam(name = "echostr", required = false) String echostr) {

		System.out.println("signature:"+signature);
		System.out.println("timestamp:"+timestamp);
		System.out.println("nonce:"+nonce);
		System.out.println("echostr:"+echostr);
		if (StringUtils.isNoneEmpty(signature, timestamp, nonce, echostr)){
			if (this.wxService.checkSignature(timestamp, nonce, signature)) {
				System.out.println("接收到来自微信服务器的认证消息：signature = ["+signature+"] timestamp = ["+timestamp+"] nonce=["+nonce+"] echostr=["+echostr+"]");

				return echostr;
			}
		}
		return "非法请求";
	}

}
