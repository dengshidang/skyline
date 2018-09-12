package com.skyline.webapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.entity.Userbalance;
import com.skyline.common.vo.UserbalanceVO;
import com.skyline.webapi.service.UserBalanceService;

@RestController
@RequestMapping("userbalance")
public class UserBalanceController {
	
	@Autowired
	private UserBalanceService userbalanceservice;
	
	/**
	 * @Title getUserBalanceList
	 * @Description 查询用户资产
	 * @return Result
	 */
	@GetMapping(value="/getUserBalanceList")
	public Result<List<UserbalanceVO>> getUserBalanceList() {
		return userbalanceservice.getUserBalanceList();
	}
	
	@GetMapping(value="/getUserBalance")
	public Result<Userbalance> getUserBalance(Integer coinId){
		if(coinId==null){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return userbalanceservice.getUserBalance(coinId);

	}
}
