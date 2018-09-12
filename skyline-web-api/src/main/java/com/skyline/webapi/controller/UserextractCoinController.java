package com.skyline.webapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.entity.UserextractCoinEntity;
import com.skyline.common.util.ObjectTool;
import com.skyline.webapi.filter.RequestUtil;
import com.skyline.webapi.service.UserextractCoinService;

@RestController
@RequestMapping("userextractcoin")
public class UserextractCoinController {
	
	@Autowired
	private UserextractCoinService userextractcoinservice;
	
	/**
	 * @Title getSeTUserextractCoinList
	 * @Description 获取用户提币地址信息
	 * @param userId
	 * @return
	 */
	@PostMapping(value="getsetuserextractcoinlist")
	public Result getSeTUserextractCoinList() {
		return userextractcoinservice.getSeTUserextractCoinList(RequestUtil.getCurrentUserJSON());
	}
	
	/**
	 * @Title addSeTUserextractCoin
	 * @Description  添加提币地址
	 * @param entity 
	 * @param coinId 
	 * @param coinType 
	 * @param isdefault 
	 * @param ecAddress 
	 * @return
	 */
	@PostMapping(value="addsetuserextractcoin")
	public Result addSeTUserextractCoin(Integer coinId,String coinType,Integer isdefault,String ecAddress) {
		if(ObjectTool.isBlank(coinId,coinType,isdefault,ecAddress)) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		UserextractCoinEntity entity=new UserextractCoinEntity();
		entity.setCoinId(coinId);
		entity.setCoinType(coinType);
		entity.setIsdefault(isdefault);
		entity.setEcAddress(ecAddress);
		return userextractcoinservice.addSeTUserextractCoin(entity, RequestUtil.getCurrentUserJSON());
	}
}
