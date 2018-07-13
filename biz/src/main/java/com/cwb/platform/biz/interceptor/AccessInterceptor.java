package com.cwb.platform.biz.interceptor;

import com.cwb.platform.sys.constant.Dict;
import com.cwb.platform.sys.model.SysGn;
import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.sys.service.GnService;
import com.cwb.platform.sys.service.YhService;
import com.cwb.platform.util.commonUtil.JwtUtil;
import com.cwb.platform.util.spring.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 访问接口控制
 *
 * @author 李彬彬
 *
 */
@Slf4j
public class AccessInterceptor extends HandlerInterceptorAdapter {

	private GnService gnService;

	private YhService yhService;

	private StringRedisTemplate redisDao;

	private List<String> excludeCtrls = Arrays.asList("exportCtrl","basicErrorController");

	private List<String> mappings;

	// 只要登录的用户都能访问
	private List<String> whiteList = Arrays.asList(
			"/api/gn/getMenuTree","/api/jg/pager","/api/zd/pager","/api/jg/getTree","/api/gn/getMenuTree","/api/jg/pager","/api/jg/getOrgTree","/api/jg/getOrgTree","/api/clsbyxsjjl/history","/api/clsbyxsjjl/history",
			"/biz/api/gn/getMenuTree","/biz/api/jg/pager","/biz/api/zd/pager","/biz/api/jg/getTree","/biz/api/gn/getMenuTree","/biz/api/jg/pager","/biz/api/jg/getOrgTree","/biz/api/jg/getOrgTree","/biz/api/clsbyxsjjl/history","/biz/api/clsbyxsjjl/history"
	);

	public AccessInterceptor() {
	}

	public AccessInterceptor(StringRedisTemplate redisTemp) {
		this.gnService = SpringContextUtil.getBean(GnService.class);
		this.yhService = SpringContextUtil.getBean(YhService.class);
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

		// todo 测试代码
//		if (true)return true;
		// 访问权限值
		// String userid = "1";
		// String token =
		// "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3Y3BtcyIsImF1ZCI6IndjcG1zIiwibG9naW5OYW1lIjoiYWRtaW5pIiwiaXNzIjoid2NwbXMiLCJ1c2VySWQiOiIxIn0.vok82zo-zveVlXrjKxgJiRRdXqKGpv1PFBngxhyR-Cg";
		String userid = request.getHeader("userid");
		String token = request.getHeader("token");

		if (token == null)
			token = request.getParameter("token");
		if (userid == null)
			userid = request.getParameter("userid");
		if (StringUtils.isEmpty(userid) || StringUtils.isEmpty(token)) {
			request.getRequestDispatcher("/403").forward(request, response);
			return false;
		}
		log.debug("访问地址[{}], 请求openid[{}],请求token[{},header请求地址[{}]]", request.getRequestURI(), userid, token);

		// 验证用户状态
		SysYh user = yhService.findById(userid);
		if (!Dict.UserStatus.VALID.getCode().equals(user.getZt())) {
			request.getRequestDispatcher("/403").forward(request, response);
			return false;
		}

		// 验证 JWT token
		try {
			String userId = JwtUtil.getClaimAsString(token, "userId");
			if (!userid.equals(userId)) {
				request.getRequestDispatcher("/403").forward(request, response);
				return false;
			}
		} catch (Exception e) {
			request.getRequestDispatcher("/403").forward(request, response);
			return false;
		}

		// 验证token是否过期
		String value = redisDao.boundValueOps(userid+"-token").get();
		if (StringUtils.isEmpty(value) || !value.equals(token)) {
			request.getRequestDispatcher("/403").forward(request, response);
			return false;
		}
		String apiPrefix = getApiQz(request.getRequestURI());
		if (!"su".equals(user.getLx())){ // su 用户可访问所有权限
			if (!whiteList.contains(apiPrefix)) {
				if (!checkPermission(user, apiPrefix)) {
					request.getRequestDispatcher("/403").forward(request, response);
					return false;
				}
			}
		}


		request.setAttribute("userInfo", user);
		request.setAttribute("orgCode", user.getJgdm());
		return super.preHandle(request, response, handler);
	}

	private boolean checkPermission(SysYh user, String apiPrefix) {
		return checkPermissionNew(user,apiPrefix);
	}
	private boolean checkPermissionNew(SysYh user, String apiPrefix) {
		String redisVal = redisDao.boundValueOps(user.getYhid()+"-apiPrefix").get();
		if (StringUtils.isEmpty(redisVal)) return false;
		return redisVal.contains(apiPrefix);
	}
	private boolean checkPermissionOld(SysYh user, String apiPrefix) {
		List<SysGn> functions = gnService.getUserFunctions(user);
		if (functions == null || functions.size() == 0)
			return false;

		for (SysGn function : functions) {
			if (StringUtils.isEmpty(function.getApiQz()))
				continue;
			if (function.getApiQz().contains(apiPrefix))
				return true;
		}
		return false;
	}

	private String getApiQz(String uri){
	    if (uri.startsWith("/biz/")){
	        uri = uri.substring(4);
        }
		List<String> mappings = getRequestMappings();
		for (String mapping : mappings) {
			if (uri.contains(mapping)){
				return mapping;
			}
		}
		return null;

//		String apiPrefix = uri.substring(0, uri.indexOf("/", 6) + 1);
//	    log.info("apiPrefix:"+apiPrefix);
//		return apiPrefix;
	}

	private List<String> getRequestMappings(){
		if (this.mappings == null){
			this.mappings = new ArrayList<>();
			Map<String,Object> requestMappings = SpringContextUtil.getByAnnotation(RequestMapping.class);
			for (Map.Entry<String, Object> entry : requestMappings.entrySet()) {
				if (excludeCtrls.contains(entry.getKey()))continue;
				Class<?> cls = entry.getValue().getClass();
				String clasName = cls.getSimpleName();
				RequestMapping requestMapping = entry.getValue().getClass().getAnnotation(RequestMapping.class);
				if (requestMapping == null){
					log.error("Not found requstmapping : "+clasName);
					continue;
				}
				String[] value = requestMapping.value();
				if (value.length == 0 || StringUtils.isEmpty(value[0]))continue;
				this.mappings.add(value[0]);
			}
		}
		return mappings;
	}
}
