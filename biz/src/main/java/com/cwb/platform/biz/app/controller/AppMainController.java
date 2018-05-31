package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.sys.bean.AccessToken;
import com.cwb.platform.sys.bean.UserPassCredential;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.Des;
import com.cwb.platform.util.commonUtil.JwtUtil;
import com.cwb.platform.util.commonUtil.StringDivUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
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
	@Value("${appSendSMSRegister:app_sendSMS_register}")
	private String appSendSMSRegister;

	@Autowired
	private PtyhService ptyhService;

    @Autowired
	private StringRedisTemplate redisDao;
 // 忽略当接收json字符串中没有bean结构中的字段时抛出异常问题
 	private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	@Value("${debug_test}")
	private String debugTest;

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
				org.apache.commons.lang.StringUtils.isEmpty(userCred.getUsername()) ||
				org.apache.commons.lang.StringUtils.isEmpty(userCred.getPassword())),
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

	/**
	 * 注册短信验证码下发
	 * @param zh		手机号码
	 * @param yyyqm	用户应邀邀请码
	 * @param key		申请验证码KEY-必填
	 * @param codeID	验证码
	 * @return
	 */
	@RequestMapping(value="/sendSMSzc", method={RequestMethod.POST})
	public ApiResponse<String> sendSMSRegister(@RequestParam(name = "zh") String zh,@RequestParam(name = "yyyqm") String yyyqm){
//		1、验证参数不能为空
		RuntimeCheck.ifTrue(org.apache.commons.lang.StringUtils.isEmpty(zh),"请填写正确的手机号");
		RuntimeCheck.ifTrue(org.apache.commons.lang.StringUtils.isEmpty(yyyqm),"邀请码不能为空");
		RuntimeCheck.ifFalse(StringDivUtils.isPhoneValid(zh),"请填写正确的手机号");
//		RuntimeCheck.ifTrue(org.apache.commons.lang.StringUtils.isEmpty(codeID),"验证码不能为空");
//		if(debugTest==null) {
//	//		验证码校验
//			String code = (String)request.getSession().getAttribute(key);
//			RuntimeCheck.ifTrue(!codeID.equals(code),"验证码不正确！");
//			request.getSession().removeAttribute(key);
//		}


//		2、验证登录账户不能重复
		SimpleCondition condition = new SimpleCondition(BizPtyh.class);
		condition.eq(BizPtyh.InnerColumn.yhZh.name(),zh);
		int count = ptyhService.countByCondition(condition);
		RuntimeCheck.ifTrue(count > 0,"该手机号已注册，请使用其它手机号码");

//		3、验证邀请码是否存在
		SimpleCondition newCondition = new SimpleCondition(BizPtyh.class);
		newCondition.eq(BizPtyh.InnerColumn.yhZsyqm.name(),yyyqm);
		newCondition.eq(BizPtyh.InnerColumn.yhSfsd.name(),"0");//用户没有锁定
		newCondition.eq(BizPtyh.InnerColumn.ddSfjx.name(),"1");//是否缴费 ZDCLK0045 (0 未缴费 1 已缴费)
		count = ptyhService.countByCondition(newCondition);
		RuntimeCheck.ifTrue(count == 0,"邀请码有误");
//		4、生成手机验证码
		String identifyingCode= StringDivUtils.getSix();//获取验证码
		boolean sendType=ptyhService.sendSMS(zh,1,identifyingCode);
		if(sendType){
			redisDao.boundValueOps(appSendSMSRegister+"yyyqm"+zh).set(yyyqm, 1, TimeUnit.DAYS);//设备邀请码，为10分钟过期
			return  ApiResponse.success();
		}else{
			return  ApiResponse.fail("短信下发失败");
		}
	}

	/**
	 * 短信验证码验证
	 * @param zh		手机号码
	 * @param identifyingCode	验证码
	 * @param type	1、注册  2、重置密码
	 * @return
	 */
	@RequestMapping(value="/validateSms", method={RequestMethod.POST})
	public ApiResponse<String> validateSms(@RequestParam(name = "zh") String zh,@RequestParam(name = "identifyingCode") String identifyingCode,@RequestParam(name = "type") String type){
		//		1、验证参数不能为空
		RuntimeCheck.ifTrue(StringUtils.isEmpty(zh),"请填写正确的手机号");
		RuntimeCheck.ifTrue(StringUtils.isEmpty(type),"请填写正确的类型");
		RuntimeCheck.ifFalse(StringDivUtils.isPhoneValid(zh),"请填写正确的手机号");

		return ptyhService.validateSms(zh, identifyingCode,type);
	}



	/**
	 * 重置密码短信验证码下发
	 * @param zh		手机号码
	 * @return
	 */
	@RequestMapping(value="/sendSMScz", method={RequestMethod.POST})
	public ApiResponse<String> sendSMSResetting(@RequestParam(name = "zh") String zh){
//		1、验证参数不能为空
		RuntimeCheck.ifTrue(org.apache.commons.lang.StringUtils.isEmpty(zh),"请填写正确的手机号");
		RuntimeCheck.ifFalse(StringDivUtils.isPhoneValid(zh),"请填写正确的手机号");

//		2、验证登录账户不能重复
		SimpleCondition condition = new SimpleCondition(BizPtyh.class);
		condition.eq(BizPtyh.InnerColumn.yhZh.name(),zh);
		int count = ptyhService.countByCondition(condition);
		RuntimeCheck.ifTrue(count == 0,"该手机号不存在，请注册后使用");

//		3、生成手机验证码
		String identifyingCode= StringDivUtils.getSix();//获取验证码
		boolean sendType=ptyhService.sendSMS(zh,2,identifyingCode);
		if(sendType){
			return  ApiResponse.success();
		}else{
			return  ApiResponse.fail("短信下发失败");
		}
	}



	/**
	 * 用户重置密码接口
	 *
	 */
	@PostMapping("/resetpwd")
	public ApiResponse<String> resetPassword(String tel, String code, String newPwd){
		return ptyhService.resetPwd(tel, code, newPwd);
	}

	/**
	 * 验证邀请码
	 */
	@PostMapping("/yzyym")
	public ApiResponse<String> validateCode( String code){
		return ptyhService.validateCode(code);
	}

}
