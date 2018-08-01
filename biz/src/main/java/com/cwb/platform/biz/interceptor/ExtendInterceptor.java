package com.cwb.platform.biz.interceptor;

import com.cwb.platform.util.config.BaseWebConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class ExtendInterceptor extends BaseWebConfigure {

	@Autowired
	private StringRedisTemplate redisDao;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		(前台登录、用户注册、微信登录、下发手机验证码) 这些请求，都不需要进行验证操作
		//APP用户鉴权
		registry.addInterceptor(new AppInterceptor(redisDao))
		.addPathPatterns("/app/**")
				.excludePathPatterns("/app/login","/app/ptyh/save","/app/ptyh/wxlogin","/app/sendSMSzc"
						,"/app/sendSMScz"
						,"/app/yzyym"
						,"/app/hd/pager"
						,"/app/zjupload"
						,"/app/cd/xlcpager"
						,"/app/cd/kcpager"
						,"/app/cd/jxcpager"
						,"/app/zdxm/getzdxm"
						,"/app/validateSms"
						,"/app/resetpwd");//白名单  (前台登录、用户注册、微信登录、下发手机验证码)
		//SYS系统管理鉴权
		registry.addInterceptor(new AccessInterceptor(redisDao))
				.addPathPatterns("/api/**")
				.excludePathPatterns("/pub/**"
						,"/login"
						,"/upload");
		super.addInterceptors(registry);
	}
	
	/**
	 * 全局跨域处理方法
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		        .allowedOrigins("*")
		        .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS",  "TRACE")
		        .allowCredentials(true)
		        .maxAge(3600);
	}
}
