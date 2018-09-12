package com.skyline.wallet.mapper;


import org.apache.ibatis.annotations.Param;

import com.skyline.common.entity.Coinmarketprice;
import com.skyline.common.util.MyMapper;

public interface CoinmarketpriceMapper extends MyMapper<Coinmarketprice>{
	
	public Coinmarketprice getCoinmarketprice(@Param("coinId")Integer coinId,@Param("coinName")String coinName);

}
