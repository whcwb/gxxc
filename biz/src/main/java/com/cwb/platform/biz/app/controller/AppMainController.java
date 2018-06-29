package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.biz.service.WjService;
import com.cwb.platform.biz.util.DloadImgUtil;
import com.cwb.platform.sys.bean.AccessToken;
import com.cwb.platform.sys.bean.UserPassCredential;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.*;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 处理学员用户登陆、登出等相关访问接口
 * @author Lee
 * @date 2018年5月18日
 */
@RestController
@RequestMapping("/app")
public class AppMainController extends AppUserBaseController {
	Logger log = LoggerFactory.getLogger("access_info");

	@Value("${staticPath:/}")
	private String staticPath;
	//证件上传地址
	@Value("${credentialsPath}")
	private String credentialsPath;

	@Value("${appSendSMSRegister:app_sendSMS_register}")
	private String appSendSMSRegister;

	@Autowired
	private PtyhService ptyhService;

	@Autowired
	private WjService wjService;



	@Autowired
	private WxMpService wxService;

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
	 * @param type	1、注册  2、重置密码
	 * @return
	 */
	@RequestMapping(value="/validateSms", method={RequestMethod.POST})
	public ApiResponse<String> validateSms(@RequestParam(name = "zh") String zh,@RequestParam(name = "yyyqm") String identifyingCode,@RequestParam(name = "type") String type){
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

	/**
	 * 从微信端下载图片到本地
	 * 微信在获取到图片地址后，要将serverId 这个值的内容传到code中去。openid也不能为空。让后台来
	 *
	 *  fileType    上传的文件属性  文件属性 ZDCLK0050 (10、 身份证正面 11、 身份证反面  20、 驾照正面 21、 驾照背面…………)  - 头像上传  30、银行卡上传识别
	 */
	@PostMapping("/getWxFile")
	public ApiResponse<Map<String,String>> downloadMedia(@RequestParam("code") String code,@RequestParam("fileType") String fileType,HttpServletRequest request){
		Map<String,String>retMap=new HashMap<>();
		boolean credentialsType=false;//是否是证件
		log.debug("getWxFile------------1、获取到微信："+code);
		RuntimeCheck.ifTrue(StringUtils.isEmpty(code),"微信文件ID不能为空");
		String savePath=staticPath;
		if (!savePath.endsWith("/")) {
			savePath += "/";
		}
		BizPtyh user = getAppCurrentUser();
//		//重新验证登录
//		String userid = request.getHeader("userid");
//		String token = request.getHeader("token");
//		String url = request.getHeader("url");
//		log.debug("openid=" + request.getHeader("openid")+"---------------------------------------------------");
//		if (token == null){
//			token = request.getParameter("token");
//		}
//		if (userid == null){
//			userid = request.getParameter("userid");
//		}
//		if (StringUtils.isEmpty(userid) || StringUtils.isEmpty(token)) {
//			user=null;
//		}else{
//			try {
//				//1、验证访问者是否合法
//				String userId = JwtUtil.getClaimAsString(token, "userId");
//				log.debug("userId=" + userId);
//				if (!userid.equals(userId)) {
//					user=null;
//				}else {
//					//2、验证用户状态
//					user = ptyhService.findByIdSelect(userid);
//					if ("1".equals(user.getYhSfsd())) {
//						user = null;
//					} else {
//						String value = redisDao.boundValueOps(userid).get();
//						log.debug("value=" + value);
//						log.debug("token=" + token);
//						if (StringUtils.isEmpty(value) || !value.equals(token)) {
//							user = null;
//						}else{
//							String userInfoJson = redisDao.boundValueOps(userid + "-appUserInfo").get();
//							log.debug("boundValueOps");
//							ObjectMapper mapper = new ObjectMapper();
//							user = mapper.readValue(userInfoJson, BizPtyh.class);
//							log.debug("userInfoJson:" + userInfoJson);
//							if (StringUtils.isEmpty(userInfoJson)) {
//								user = null;
//							}
//						}
//					}
//				}
//			} catch (Exception e) {
//				user=null;
//			}
//		}

		log.debug("getWxFile------------2、获取用户登录："+user==null?"未登录":"已登录");
		savePath+="temp";
		log.debug("getWxFile------------3、文件存放地址："+savePath);
		if(StringUtils.isNotEmpty(fileType)){
			if(StringUtils.indexOf("10 11 20 21 ", fileType)>-1){
				savePath = credentialsPath+DateUtils.getToday().replaceAll("-","")+"/";//生成新的文件路径
				credentialsType=true;
			}
		}
		log.debug("getWxFile------------4、文件新的存放地址："+savePath);
		String accessToken = null;
		try {
			accessToken = wxService.getAccessToken();
		} catch (WxErrorException e) {
			log.debug("getWxFile*******5、获取accesstoken：失败");
			ApiResponse<Map<String,String>> res = new ApiResponse<>();
			res.setCode(500);
			res.setMessage("获取accesstoken 失败");
			res.setResult(retMap);

			log.error("获取accesstoken 失败",e);
			return res;
		}
		log.debug("getWxFile------------5、获取accesstoken："+accessToken);
		String fileUrl=DloadImgUtil.downloadMedia(code,savePath,accessToken);
		log.debug("getWxFile------------6、完整的路径："+fileUrl);

		if(credentialsType){
			log.debug("getWxFile------------7、进入证件上传类型");
			if(fileUrl != null && StringUtils.isNotEmpty(fileUrl)){
	//			1、识别
				RuntimeCheck.ifNull(fileType,"您好，请确定图片类型");
				boolean retType=wjService.ocrRecognition (retMap,fileType,fileUrl,fileUrl.replaceAll(credentialsPath,""),user);
				log.debug("getWxFile------------7-1、处理状态"+retType);
				if(!retType){
					ApiResponse<Map<String,String>> res = new ApiResponse<>();
					res.setCode(500);
					res.setMessage(retMap.get("image_message"));
					res.setResult(retMap);
					return res;
				}
				log.debug("getWxFile------------7-2、截图操作"+fileUrl);
	//			2、  切图
				wjService.tailorSubjectImg(fileUrl);
			}
		}else{
			String yhYhkCode="";
			if(StringUtils.isNotEmpty(fileUrl)){
				fileUrl=fileUrl.replace(staticPath,"");
			}
			if(StringUtils.equals("-",fileType)){//头像上传
				//直接修改用户头像
				BizPtyh bizPtyh=new BizPtyh();
				bizPtyh.setYhTx(fileUrl);
				bizPtyh.setId(user.getId());
				ptyhService.update(bizPtyh);
			}else if(StringUtils.equals("30",fileType)){//驾驶证号码提取
				boolean retType=wjService.ocrRecognition (retMap,fileType,staticPath+fileUrl,fileUrl.replaceAll(staticPath,""),user);
				if(!retType){
					ApiResponse<Map<String,String>> res = new ApiResponse<>();
					res.setCode(500);

					retMap.put("bank_card_number","");
					retMap.put("bank_name","");
					retMap.put("bank_card_type","");
					res.setMessage(retMap.get("image_message"));
					res.setResult(retMap);
					return res;
				}
			}
			retMap.put("filePath",fileUrl);
			if(StringUtils.isNotEmpty(yhYhkCode)){
				retMap.put("userBankCard ",yhYhkCode);
			}
		}
		String filePath=retMap.get("filePath");
		if(StringUtils.isNotEmpty(filePath)){
//			if(!StringUtils.equals(filePath.substring(0,1),"/")){
//				filePath="/"+filePath;
//				retMap.put("filePath",filePath);
//			}
		}else{
			retMap.put("filePath","");
		}
		try {
			log.debug("getWxFile------------100、结束"+mapper.writeValueAsString(retMap));
		} catch (JsonProcessingException e) {
			log.debug("getWxFile------------100、结束");
			e.printStackTrace();
		}

		return ApiResponse.success(retMap);
	}

	//用户上传证件照片时，进行实名认证，并返回结果
	//处理文件上传

	/**
	 *
	 * @param file  上传的文件
	 * @param fileType	上传的文件属性  文件属性 ZDCLK0050 (10、 身份证正面 11、 身份证反面  20、 驾照正面 21、 驾照背面…………)
	 * @return
	 */
	@RequestMapping(value="/zjupload")
	@ResponseBody
		public ApiResponse<String> uploadImg(@RequestParam("file") MultipartFile file,@RequestParam("fileType") String fileType,@RequestParam("userId") String userId) {
		Map<String,String > retMap= new HashMap<String,String>();//返回值
		String targetPath= DateUtils.getToday().replaceAll("-","");//生成目录
		String fileName = file.getOriginalFilename();//获取上传的文件名
		String suffix = fileName.substring(fileName.lastIndexOf("."));//获取后缀名
		UUID uuid = UUID.randomUUID();
		fileName = uuid.toString().replaceAll("-","") + suffix;//生成新的文件名

		String filePath = credentialsPath+targetPath+"/";//生成新的文件路径
		String path = "/"+targetPath +"/"+ fileName;//返回前台的地址
		try {
			FileUtil.uploadFile(file.getBytes(), filePath, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		wjService.tailorSubjectImg(filePath+fileName);
		redisDao.boundValueOps("zjupload_"+userId+"_"+fileType).set(path, 1, TimeUnit.DAYS);
		return ApiResponse.success(path);
	}
//	/**
//	 * 证件识别
//	 * @param path  上传的文件地址
//	 * @param fileType	上传的文件属性  文件属性 ZDCLK0050 (10、 身份证正面 11、 身份证反面  20、 驾照正面 21、 驾照背面…………)
//	 * @return
//	 */
//	@RequestMapping(value="/zjsb")
//	@ResponseBody
//	public ApiResponse<Map<String,String>> ocrRecognition(@RequestParam("path") String path,@RequestParam("fileType") String fileType) {
//		Map<String,String > retMap= new HashMap<String,String>();//返回值
//		RuntimeCheck.ifNull(fileType,"您好，请确定图片类型");
//		boolean retType=wjService.ocrRecognition (retMap,fileType,credentialsPath+path,path, user);
//		if(retType){
//			return ApiResponse.success(retMap);
//		}else{
//			ApiResponse<Map<String,String>> res = new ApiResponse<>();
//			res.setCode(500);
//			res.setMessage(retMap.get("image_message"));
//			res.setResult(retMap);
//			return res;
//		}
//	}


}
