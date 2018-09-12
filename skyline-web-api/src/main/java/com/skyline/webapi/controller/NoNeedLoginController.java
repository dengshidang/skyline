/*package com.skyline.webapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skyline.common.constant.Result;
import com.skyline.webapi.service.NoNeedLoginService;

@RestController
@RequestMapping(value="noNeedLogin")
public class NoNeedLoginController {
	@Autowired
	private NoNeedLoginService noNeedLoginService;
	
	@PostMapping(value="/uploadPicture")
	public Result<?> uploadPicture(){

		return noNeedLoginService.uploadPicture();
	}
}
*/