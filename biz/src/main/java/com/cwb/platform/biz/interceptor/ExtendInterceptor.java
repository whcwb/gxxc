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
		//APP用户鉴权
		registry.addInterceptor(new AppInterceptor(redisDao))
		.addPathPatterns("/app/**")
				.excludePathPatterns("/app/login","/app/ptyh/save","/app/ptyh/wxlogin");//白名单
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
