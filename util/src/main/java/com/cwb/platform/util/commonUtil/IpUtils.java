package com.cwb.platform.util.commonUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 
 * 
 * Module: 基础框架
 * 
 * IpUtils.java IP地址工具
 * 
 * @author
 * @since JDK 1.6
 * @version 1.0
 * @description: IP地址工具
 * @log:2013-12-14
 */
public final class IpUtils {
	private final static Log logger = LogFactory.getLog(IpUtils.class);

	/** 查询IP地址 */
	private static final String ipLookUp = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=";

	/** 省 */
	public static final String PROVINCE = "ip_province";

	/** 市 */
	public static final String CITY = "ip_city";

	/**
	 * 获得请求的真实IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getRealIp(HttpServletRequest request) {
		String ip = "";
		try {
			ip = request.getHeader("X-Forwarded-For");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}

			if (StringUtils.isNotBlank(ip)) {
				int dotIndex = ip.indexOf(",");
				if (-1 != dotIndex) {
					ip = ip.substring(0, dotIndex);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ip;
	}

}
