package com.skyline.webapi.filter;

import org.springframework.context.annotation.Configuration;

import com.skyline.common.constant.Constants;

import feign.RequestInterceptor;
import feign.RequestTemplate;
@Configuration
public class FeignInterceptor  implements RequestInterceptor{

	@Override
	public void apply(RequestTemplate template) {
		// TODO Auto-generated method stub
		template.header(Constants.CURRENT_USER_JSON, RequestUtil.getCurrentUserJSON());
		template.header(Constants.CURRENT_TOKEN,RequestUtil.getCurrentToken());
	}

}
