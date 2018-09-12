package com.skyline.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skyline.common.constant.Result;
import com.skyline.user.business.LoginHistoryBusiness;
@RestController
@RequestMapping(value="loginHistory")
public class LoginHistoryService {

	@Autowired
	private LoginHistoryBusiness loginHistoryBusiness;
	/**
	 * 
	 * <p>Title: queryLoginHistory</p>  
	 * <p>Description: 查询登录历史</p>  
	 * @param userId
	 * @return
	 */
	@PostMapping(value="queryLoginHistory")
	public Result queryLoginHistory(@RequestParam("userId") String userId){
		return loginHistoryBusiness.queryLoginHistory(userId);
	};
}
