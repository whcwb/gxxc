package com.cwb.platform.biz.wxpkg.controller;

import com.cwb.platform.biz.util.WechatUtils;
import com.cwb.platform.util.bean.ApiResponse;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 微信服务
 * @author Lee
 *
 * 1.获取openid
 * 2.获取payId
 * 3.调取扫描二维码
 */
@Controller
@RequestMapping("wechat/message")
public class WxChatController {

	private final Logger logger = LoggerFactory.getLogger("access_info");


	@Autowired
	private WxMpService wxService;

	@Autowired
	private WxMpMessageRouter router;

	@Autowired
	private WechatUtils wechatUtils;

	@Value("${wechat.domain}")
	private String domainUrl;


	@RequestMapping("getOpenid")
	@ResponseBody
	public ApiResponse<String> getOpenid(String code){
		return ApiResponse.success(wechatUtils.getOpenid(code));
	}


	@RequestMapping("getJsApiSign")
	@ResponseBody
	public ApiResponse<String> getJsApiSign(String url,String timestamp,String nonceStr){
		try {
			String ticket = wxService.getJsapiTicket();
			String params = "jsapi_ticket=" +ticket +
					"&noncestr=" + nonceStr +
					"&timestamp="+ timestamp +
					"&url="+url;
			System.out.println(params);
			String sign = DigestUtils.shaHex(params);
			System.out.println(sign);
			return ApiResponse.success(sign);
		} catch (WxErrorException e) {
			logger.error("getJsApiSign error",e);
			e.printStackTrace();
		}
		return ApiResponse.fail("getJsApiSign error");
	}

	/**
	 * 微信服务器认证
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	@GetMapping(produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String authGet(@RequestParam(name = "signature", required = false) String signature,
			@RequestParam(name = "timestamp", required = false) String timestamp,
			@RequestParam(name = "nonce", required = false) String nonce,
			@RequestParam(name = "echostr", required = false) String echostr) {

		if (StringUtils.isNoneEmpty(signature, timestamp, nonce, echostr)){
			if (this.wxService.checkSignature(timestamp, nonce, signature)) {
				this.logger.info("\n接收到来自微信服务器的认证消息：signature = [{}], timestamp = [{}], nonce = [{}], echostr = [{}]", signature, timestamp, nonce, echostr);

				return echostr;
			}
		}

		return "非法请求";
	}


	@PostMapping(produces = "application/xml; charset=UTF-8")
	@ResponseBody
	public String post(@RequestBody String requestBody, @RequestParam("msg_signature") String signature,
			@RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce) {
		this.logger.info("\n接收微信请求：[signature=[{}], timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ", signature,
				timestamp, nonce, requestBody);

		WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, this.wxService.getWxMpConfigStorage(),
				timestamp, nonce, signature);
		this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
		WxMpXmlOutMessage outMessage = this.route(inMessage);
		if (outMessage == null) {
			return "";
		}

		this.logger.debug("\n发送内容明文：\n{} ", outMessage.toString());
		String out = outMessage.toEncryptedXml(this.wxService.getWxMpConfigStorage());
		this.logger.debug("\n组装回复信息：{}", out);
		return out;
	}

	private WxMpXmlOutMessage route(WxMpXmlMessage message) {
		try {
			return this.router.route(message);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}

		return null;
	}
}
