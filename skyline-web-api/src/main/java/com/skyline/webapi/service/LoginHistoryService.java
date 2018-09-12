package com.skyline.webapi.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.skyline.common.constant.Result;

@FeignClient(value = "skyline-user")
public interface LoginHistoryService {
	
	
	
	@PostMapping(value="/loginHistory/queryLoginHistory")
	public Result queryLoginHistory(@RequestParam("userId") String userId);
	

}
