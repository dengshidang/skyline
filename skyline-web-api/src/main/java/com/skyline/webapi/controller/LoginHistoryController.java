package com.skyline.webapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.util.ObjectTool;
import com.skyline.webapi.service.LoginHistoryService;


@RestController
@RequestMapping(value="userLoginHistory")
public class LoginHistoryController {
	
	@Autowired
	private LoginHistoryService loginHistoryService;
	
	/**
	 * 
	 * <p>Title: queryLoginHistory</p>  
	 * <p>Description:查询用户登录历史记录 </p>  
	 * @param userId
	 * @return
	 */
	@PostMapping(value="/queryLoginHistory")
	public Result queryLoginHistory(@RequestParam("userId")String userId){
		return loginHistoryService.queryLoginHistory(userId);
	}
	

}
