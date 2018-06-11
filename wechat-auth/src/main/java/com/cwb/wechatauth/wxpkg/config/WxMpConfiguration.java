package com.cwb.wechatauth.wxpkg.config;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.api.WxConsts.MenuButtonType;
import me.chanjar.weixin.common.api.WxConsts.XmlMsgType;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(WxMpService.class)
public class WxMpConfiguration {
	private WxConfig config = WxConfig.getInstance();

	@Bean
	@ConditionalOnMissingBean
	public WxMpConfigStorage configStorage() {
		WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
		configStorage.setAppId(this.config.getAppId());
		configStorage.setSecret(this.config.getSecret());
		configStorage.setToken(this.config.getToken());
		configStorage.setAesKey(this.config.getAesKey());
		return configStorage;
	}

	@Bean
	@ConditionalOnMissingBean
	public WxMpService WxMpService(WxMpConfigStorage configStorage) {
		// WxMpService WxMpService = new
		// me.chanjar.weixin.cp.api.impl.okhttp.WxMpServiceImpl();
		// WxMpService WxMpService = new
		// me.chanjar.weixin.cp.api.impl.jodd.WxMpServiceImpl();
		// WxMpService WxMpService = new
		// me.chanjar.weixin.cp.api.impl.apache.WxMpServiceImpl();
		WxMpService service = new WxMpServiceImpl();
		service.setWxMpConfigStorage(configStorage);
		return service;
	}

	@Bean
	public WxMpMessageRouter router(WxMpService WxMpService) {
		final WxMpMessageRouter newRouter = new WxMpMessageRouter(WxMpService);

		return newRouter;
	}

}
