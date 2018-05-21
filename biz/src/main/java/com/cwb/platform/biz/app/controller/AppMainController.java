package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.sys.bean.AccessToken;
import com.cwb.platform.sys.bean.UserPassCredential;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.Des;
import com.cwb.platform.util.commonUtil.JwtUtil;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 处理学员用户登陆、登出等相关访问接口
 * @author Lee
 * @date 2018年5月18日
 */
@RestController
@RequestMapping("/app")
public class AppMainController {


	@Autowired
	private PtyhService ptyhService;

    @Autowired
	private StringRedisTemplate redisDao;
 // 忽略当接收json字符串中没有bean结构中的字段时抛出异常问题
 	private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	/**
	 * 用户登陆接口
	 * @param userCred
	 * @return
	 */
//	@RequestMapping(value="/login", method={RequestMethod.POST})
	@RequestMapping(value="/login")
	public ApiResponse<Map<String,Object>> login(UserPassCredential userCred, HttpServletRequest request){
//		RuntimeCheck.ifBlank(userCred.getCodeID(),"验证码不正确！");
		RuntimeCheck.ifTrue((
				StringUtils.isEmpty(userCred.getUsername()) ||
				StringUtils.isEmpty(userCred.getPassword())),
//				StringUtils.isEmpty(userCred.getCaptcha())),
				"请提交登陆用户信息！");
//		String code = (String)request.getSession().getAttribute(userCred.getCodeID());
//		RuntimeCheck.ifTrue(!userCred.getCaptcha().equals(code),"验证码不正确！");
		return commonLogin(userCred);
	}

	/**
	 * 用户登录
	 * @param userCred
	 * @return
	 */
	private ApiResponse<Map<String,Object>> commonLogin(UserPassCredential userCred){
		//加密密码
		try {
			userCred.setPassword(Des.encrypt(userCred.getPassword()));
		} catch (Exception e1) {
			throw new RuntimeException("密码加密异常",e1);
		}

		Example condition = new Example(BizPtyh.class);
		condition.and()
				.andEqualTo(BizPtyh.InnerColumn.yhZh.name(), userCred.getUsername())
				.andEqualTo(BizPtyh.InnerColumn.yhMm.name(), userCred.getPassword());
		List<BizPtyh> existUser = this.ptyhService.findByCondition(condition);
		Map<String,Object> rMap = new HashMap<>(2);
		ApiResponse<Map<String,Object>> result = new ApiResponse<>();
		if (existUser != null && existUser.size() > 0){
			BizPtyh item = existUser.get(0);
			RuntimeCheck.ifTrue("1".equals(item.getYhSfsd()),"用户已禁用！");

			try {
				String token = JwtUtil.createToken(item.getId(),item.getYhXm());
				redisDao.boundValueOps(item.getId()).set(token, 1, TimeUnit.DAYS);
				redisDao.boundValueOps(item.getId()+"-appUserInfo").set(mapper.writeValueAsString(item), 1, TimeUnit.DAYS);
				AccessToken aToken = new AccessToken();
				aToken.setUserId(item.getId());
				aToken.setUsername(item.getYhBm());//用户别名
				aToken.setToken(token);

				rMap.put("accessToken", aToken);
				result.setResult(rMap);
			} catch (Exception e) {
				result.setCode(ApiResponse.FAILED);
				result.setMessage("用户登陆失败，请重试！");
			}
		}else{
			result.setCode(ApiResponse.FAILED);
			result.setMessage("用户名或密码不正确！");
			return result;
		}
		return result;
	}
	/**
	 * 用户退出接口
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/logout", method={RequestMethod.GET})
	public ApiResponse<AccessToken> logout(HttpServletRequest request){
		ApiResponse<AccessToken> result = new ApiResponse<>();
		String userId = request.getHeader("userid");
		redisDao.delete(userId);
		redisDao.delete(userId+"-appUserInfo");
		return result;
	}
}
