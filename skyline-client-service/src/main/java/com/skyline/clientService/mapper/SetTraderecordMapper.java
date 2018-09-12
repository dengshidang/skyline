package com.skyline.clientService.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.skyline.clientService.kline.KlineParam;
import com.skyline.common.entity.SeTTraderecordEntity;
import com.skyline.common.util.MyMapper;
import com.skyline.common.vo.TradeNewCordVO;

public interface SetTraderecordMapper extends MyMapper<SeTTraderecordEntity> {
	/**
	 * 
	 * @param marketId  行情id
	 * @param format   时间格式
	 * @param baseTime  时间单位
	 * @return
	 */
	List<Map<String,Object>> selectTrdeCordByMarketAndTimeType(
			                 @Param("kp")KlineParam kp);

	List<Integer> getMarketIdlist();

   /**
    * 
    * 当天实时交易数据
    * @param marketId
    * @return
    */
	List<TradeNewCordVO>getNewTradeCord(@Param("marketId")Integer marketId);
	/**
	 * 获取交易均价
	 * @param type 挂单类型
	 * @param orderNo 订单号
	 * @return
	 */
	Map<String,Object>getavgPriceByOrderNo(@Param("type")Integer type,@Param("orderNo")String orderNo);



	
}
