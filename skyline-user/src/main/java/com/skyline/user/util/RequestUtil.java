package com.skyline.user.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.skyline.common.constant.Constants;
import com.skyline.common.entity.Userinfo;

public class RequestUtil {
	 public static Userinfo getCurrentUser(){
	      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	      return  (Userinfo)request.getAttribute(Constants.CURRENT_USER);
	 }
	 public static String getCurrentUserJSON(){
	      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	      return    String.valueOf(request.getAttribute(Constants.CURRENT_USER_JSON));
	 }
	 public static String getCurrentToken() {
		  HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	      return    String.valueOf(request.getAttribute(Constants.CURRENT_TOKEN));
	 }
}
