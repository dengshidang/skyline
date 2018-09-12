package com.skyline.webapi.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.Coinmarketprice;

@FeignClient(value = "skyline-wallet")
public interface CoinmarketpriceService {
	/**
	 * 
	* @Title: getCoinmarketprice
	* @Description: TODO(查询币种兑换比例)
	* @param @param coinId
	* @param @param coinName
	* @param @return    参数
	* @return Result<Coinmarketprice>    返回类型
	* @throws
	 */
	@GetMapping(value="/coinmarketprice/getCoinmarketprice")
	public Result<Coinmarketprice> getCoinmarketprice(@RequestParam("coinId")Integer coinId,@RequestParam("coinName")String coinName);
}
