package com.skyline.webapi.filter;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.skyline.common.constant.Constants;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.util.JSONUtil;
import com.skyline.webapi.annotation.NotLogin;
import com.skyline.webapi.controller.UserinfoController;
import com.skyline.webapi.exception.BusinessException;

public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserinfoController userinfoController;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, OPTIONS, DELETE");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, token");
		// 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if(!method.getDeclaringClass().isAnnotationPresent(RestController.class)){
        	 return true;
        }
        // 判断接口是否需要登录
        NotLogin methodAnnotation = method.getAnnotation(NotLogin.class);
        String token = request.getHeader("token");  // 从 http 请求头中取出 token
        Userinfo    user =(Userinfo)userinfoController.getUserByToken(token).getResult();
        // 有 @NoLogin 注解，不需要认证
        if (methodAnnotation == null) {
            // 执行认证
            if (token == null) {
                throw new BusinessException(StatusCode.TOKEN_NULL.getCode(), StatusCode.TOKEN_NULL.getMsg());
            }
			if(user==null){
	    		  throw new BusinessException(StatusCode.TOKEN_ERROR.getCode(), StatusCode.TOKEN_ERROR.getMsg());
			}
        }
	     request.setAttribute(Constants.CURRENT_USER, user);
		 request.setAttribute(Constants.CURRENT_USER_JSON, JSONUtil.toJson(user));
		 request.setAttribute(Constants.CURRENT_TOKEN, token);
		 return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
