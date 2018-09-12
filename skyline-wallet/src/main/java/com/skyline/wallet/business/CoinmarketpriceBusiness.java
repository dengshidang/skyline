package com.skyline.wallet.business;

import com.skyline.common.business.BaseBusiness;
import com.skyline.common.constant.Result;
import com.skyline.common.entity.Coinmarketprice;


public interface CoinmarketpriceBusiness extends BaseBusiness<Coinmarketprice, Integer>  {
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
		public Result<Coinmarketprice> getCoinmarketprice(Integer coinId,String coinName);
}
