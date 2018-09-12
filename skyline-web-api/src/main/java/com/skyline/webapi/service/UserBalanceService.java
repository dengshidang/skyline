package com.skyline.webapi.service;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.Userbalance;
import com.skyline.common.vo.UserbalanceVO;
@FeignClient(value = "skyline-wallet")
public interface UserBalanceService {
	/**
	 * 获取用户币种总额
	 * @param userId
	 * @return
	 */
	@GetMapping(value="userbalance/getUserBalanceList")
	public Result<List<UserbalanceVO>> getUserBalanceList() ;
	
	@GetMapping(value="userbalance/getUserBalance")
	public Result<Userbalance> getUserBalance(@RequestParam("coinId")Integer coinId);
}
