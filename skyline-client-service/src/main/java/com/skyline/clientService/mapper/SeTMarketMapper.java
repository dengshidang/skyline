package com.skyline.clientService.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.skyline.common.entity.SeTMarketEntity;
import com.skyline.common.util.MyMapper;
import com.skyline.common.vo.TradeCoinRateVO;
import com.skyline.common.vo.TradePrecoinEntityVO;
import com.skyline.common.vo.TradePrecoindNodeVO;

public interface SeTMarketMapper extends MyMapper<SeTMarketEntity> {


	/**
	 * 
	 * @return 获取可交易币种id,name
	 */

	List<TradePrecoindNodeVO> getPrecoind();

	/**
	 * 
	 * @param precoinId 可交易币种id
	 * @return
	 */
	List<TradePrecoinEntityVO> getMarket(
			@Param("precoinId")Integer precoinId,
			@Param("type")Integer type,
			@Param("sort")String sort,
			@Param("keyword") String keyword);

	/**
	 * 获取交易类型
	 * @return
	 */
	List<Map<String, Object>> queryType();
	/**
	 * 获取最新动态信息
	 * @param marketId
	 * @return 
	 */
	TradePrecoinEntityVO getNewPriceNadGains(Integer marketId);
	/**
	 * 获取用户收藏行情信息
	 * @param userId
	 * @param keyword
	 * @return
	 */
	List<TradePrecoinEntityVO> getOptinal(
			@Param("userId")Integer userId,
			@Param("type")Integer type,
			@Param("sort")String sort,
			@Param("keyword") String keyword);
	/**
	 * 获取币币费率详情
	 * @param tradeCoinRate
	 * @return
	 */
	List<TradeCoinRateVO> getTradeCoinRate(TradeCoinRateVO tradeCoinRate);
    /**
     * 获取当前行情信息
     * @param marketId
     * @return
     */
	TradePrecoinEntityVO getCurrentMarket(Integer marketId);

}
