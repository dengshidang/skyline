package com.skyline.webapi.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.entity.Coinmarketprice;
import com.skyline.common.util.StringTool;
import com.skyline.webapi.annotation.NotLogin;
import com.skyline.webapi.service.CoinmarketpriceService;
@RestController
public class CoinmarketpriceController {
	
	@Autowired 
	CoinmarketpriceService  coinmarketpriceService;
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
	@NotLogin
	@GetMapping(value="/coinmarketprice/getCoinmarketprice")
	public Result<Coinmarketprice> getCoinmarketprice(Integer coinId,String coinName){
		if(coinId==null && StringTool.isBlank(coinName)) {
				return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return coinmarketpriceService.getCoinmarketprice(coinId, coinName);
	}
}
