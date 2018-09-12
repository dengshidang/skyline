package com.skyline.wallet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.Userbalance;
import com.skyline.common.vo.UserbalanceVO;
import com.skyline.wallet.business.UserBalanceBusiness;

@RestController
public class UserBalanceService {
	
	@Autowired
	private UserBalanceBusiness userBalanceBusiness;
	

	
	/**
	 * 获取用户币种总额
	 * @param userId
	 * @return
	 */
	@GetMapping(value="userbalance/getUserBalanceList")
	public Result<List<UserbalanceVO>> getUserBalanceList() {
		return userBalanceBusiness.getUserBalanceList();
	}
	/**
	 * 
	* @Title: getUserBalance
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author xzj
	* @param @return    参数
	* @return UserbalanceVO    返回类型
	* @throws
	 */
	@GetMapping(value="userbalance/getUserBalance")
	public Result<Userbalance> getUserBalance(Integer coinId) {
		return userBalanceBusiness.getUserBalance(coinId);
	}
	
	
}
