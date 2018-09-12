package com.skyline.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.Coinmarketprice;
import com.skyline.wallet.business.CoinmarketpriceBusiness;



@RestController
public class CoinmarketpriceService {
	@Autowired
	private  CoinmarketpriceBusiness coinmarketpriceBusiness;
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
	public Result<Coinmarketprice> getCoinmarketprice(Integer coinId,String coinName){
		return coinmarketpriceBusiness.getCoinmarketprice(coinId, coinName);
	}
}
