package com.skyline.webapi.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.skyline.common.constant.Constants;
import com.skyline.common.constant.Result;
import com.skyline.common.entity.UserextractCoinEntity;
@FeignClient("skyline-wallet")
public interface UserextractCoinService {
	/**
	 * 获取用户提币地址信息
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/user/getsetuserextractcoinlist",method=RequestMethod.POST)
	public Result getSeTUserextractCoinList(@RequestHeader(Constants.CURRENT_USER_JSON) String userJson);
	
	/**
	 * 添加提币地址
	 * @param entity
	 * @return
	 */
	@RequestMapping(value="/user/addsetuserextractcoin",method=RequestMethod.POST)
	public Result addSeTUserextractCoin(@RequestBody UserextractCoinEntity entity,
				@RequestHeader(Constants.CURRENT_USER_JSON) String userJson);
}
