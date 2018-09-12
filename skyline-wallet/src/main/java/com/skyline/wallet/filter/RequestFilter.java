package com.skyline.wallet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.skyline.common.constant.Constants;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.util.JSONUtil;
import com.skyline.common.util.StringTool;

@Component
@WebFilter(urlPatterns={"/*"}, filterName="requestFilter")
public class RequestFilter implements Filter {
	private final static Logger log = LoggerFactory.getLogger(RequestFilter.class); 
	@Override
	public void destroy() {
 
	}
 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		 HttpServletRequest req = (HttpServletRequest)request;
		 String userJson = req.getHeader(Constants.CURRENT_USER_JSON);
		 if(!StringTool.isBlank(userJson)) {
			request.setAttribute(Constants.CURRENT_USER, JSONUtil.toBean(userJson, Userinfo.class));
			request.setAttribute(Constants.CURRENT_USER_JSON, userJson);
		 }
		 chain.doFilter(request, response);
	}
 
	@Override
	public void init(FilterConfig arg0) throws ServletException {
 
	}

}
