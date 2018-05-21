package com.cwb.platform.biz.interceptor;

import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.commonUtil.JwtUtil;
import com.cwb.platform.util.spring.SpringContextUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  处理学员用户权限 访问接口控制
 *
 * @author
 *
 */
@Slf4j
public class AppInterceptor extends HandlerInterceptorAdapter {

//	private YhService yhService;
	private PtyhService ptyhService;

	private StringRedisTemplate redisDao;

	// 只要登录的用户都能访问
//	private List<String> whiteList = Arrays.asList("/app/login");

	public AppInterceptor() {
	}

	public AppInterceptor(StringRedisTemplate redisTemp) {
		this.ptyhService = SpringContextUtil.getBean(PtyhService.class);
		this.redisDao = redisTemp;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 查看请求类型
		String method = request.getMethod();
		if (method.equals("OPTIONS")) {
			// 如果收到的是跨域预请求消息，直接响应，返回true，以便后续跨域请求成功
			return true;
		}

		String userid = request.getHeader("userid");
		String token = request.getHeader("token");
		String url = request.getHeader("url");

		if (token == null){
			token = request.getParameter("token");
		}
		if (userid == null){
			userid = request.getParameter("userid");
		}
		if (StringUtils.isEmpty(userid) || StringUtils.isEmpty(token)) {
			request.getRequestDispatcher("/403").forward(request, response);
			return false;
		}
		log.debug("访问地址[{}], 请求openid[{}],请求token[{},header请求地址[{}]]", request.getRequestURI(), userid, token, url);

		try {
			//1、验证访问者是否合法
			String userId = JwtUtil.getClaimAsString(token, "userId");
			log.debug("userId=" + userId);
			if (!userid.equals(userId)) {
				request.getRequestDispatcher("/403").forward(request, response);
				log.debug("用户验证失败");
				return false;
			}

			//2、验证用户状态
			BizPtyh user = ptyhService.findByIdSelect(userid);
			if ("1".equals(user.getYhSfsd())) {
				request.getRequestDispatcher("/403").forward(request, response);
				log.debug("用户已禁用！");
				return false;
			}

			String value = redisDao.boundValueOps(userid).get();
			log.debug("value=" + value);
			log.debug("token=" + token);
			if (StringUtils.isEmpty(value) || !value.equals(token)) {
				request.getRequestDispatcher("/403").forward(request, response);
				log.debug("用户验证失败");
				return false;
			}

			request.setAttribute("appUserInfo", user);
			log.debug("boundValueOps");
			String userInfoJson = redisDao.boundValueOps(userid + "-appUserInfo").get();
			log.debug("boundValueOps");
			ObjectMapper mapper = new ObjectMapper();
			BizPtyh userInfo = mapper.readValue(userInfoJson, BizPtyh.class);
			log.debug("userInfoJson:" + userInfoJson);
			if (StringUtils.isEmpty(userInfoJson)) {
				request.getRequestDispatcher("/403").forward(request, response);
				return false;
			}
		} catch (Exception e) {
			request.getRequestDispatcher("/403").forward(request, response);
			return false;
		}
		return super.preHandle(request, response, handler);
	}
}
