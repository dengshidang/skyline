package com.skyline.wallet.business.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.skyline.common.business.impl.BaseBusinessImpl;
import com.skyline.common.constant.Result;
import com.skyline.common.entity.Coinmarketprice;
import com.skyline.wallet.business.CoinmarketpriceBusiness;
import com.skyline.wallet.mapper.CoinmarketpriceMapper;

@Service
public class CoinmarketpriceBusiinessImpl extends BaseBusinessImpl<Coinmarketprice, Integer> implements CoinmarketpriceBusiness {
	@Autowired
	CoinmarketpriceMapper coinmarketpriceMapper;
	
	
	public Result<Coinmarketprice> getCoinmarketprice(Integer coinId,String coinName){
		return Result.successResult(coinmarketpriceMapper.getCoinmarketprice(coinId, coinName));
	}
}
