package com.skyline.clientService.business;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.SeTMarketEntity;
import com.skyline.common.vo.DepthVO2;
import com.skyline.common.vo.TradeCoinRateVO;
import com.skyline.common.vo.TradeUserEntrustVO;

public interface TradeBusiness {

	/**
	 * 添加币币交易委托信息
	 * @param userId
	 * @param marketId
	 * @param number
	 * @param price
	 * @param type
	 * @return
	 */
	Result<?> tradeEntrust(Integer userId,Integer marketId,Double number,Double price,Integer type);

	/**
	 * 委托交易撤销
	 * @param id 交易ID
	 * @return
	 */
	Result<?> tradeCancel(Integer userId,Integer id);

	/**
	 * 添加币币交易行情信息
	 * @param seTMarketEntity
	 * @return
	 */
	Result<?> addMarket(SeTMarketEntity seTMarketEntity);

	/**
	 * 获取币币交易市场信息
	 * @param precoinId 
	 * @param dec 
	 * @param type 
	 * @return
	 */
	Result<?> getMarket(Integer precoinId, Integer type, Integer dec ,String keyword);

	/**
	 * 查询用户的交易委托信息
	 * @param userId 用户id
	 * @param marketId 行情ID
	 * @param type 交易类型
	 * @param status 状态
	 * @return
	 */
	Result<?> getUserTradeEntrust(Integer userid,Integer pageNum,Integer pageSize,TradeUserEntrustVO tradeUserEntrust);

	/**
	 * 获取余额信息
	 * @param userId
	 * @param coinId
	 * @return
	 */
	Result<?>  getUserBalance(Integer userId, Integer coinId);
	/**
	 * 获取对应费率
	 * 
	 * @return
	 */
	Result<?> getTradeCoinRate(TradeCoinRateVO tradeCoinRate,Integer pageNum,Integer pageSize);
	/**
	 * 获取当前行情信息
	 * @param marketId
	 * @return
	 */
	Result<?> getCurrentMarket(Integer marketId);
	/**
	 * 绘画深度图数据
	 * @param marketId
	 * @return
	 */
	Result<DepthVO2> drawDepths(Integer marketId);
}
