
package com.cwb.platform.biz.app;

import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * APP端，获取用户登录情况
 */
public abstract class AppUserBaseController {

	/**
	 * 获取当前学员登录用户信息
	 * @return
	 */
	public static BizPtyh getAppCurrentUser(Boolean require){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		BizPtyh userInfo = (BizPtyh)request.getAttribute("appUserInfo");
		RuntimeCheck.ifTrue(require && userInfo == null,"当前登录用户未空！");
		return userInfo;
	}
	/**
	 * 获取当前学员登录用户信息
	 * @return
	 */
	public static BizPtyh getAppCurrentUser(){
		return getAppCurrentUser(true);
	}

    @InitBinder
    public void initBinder(WebDataBinder binder) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		//true:允许输入空值，false:不能为空值
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
