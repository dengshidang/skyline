package com.skyline.clientService.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.skyline.common.entity.SeTTradeinfoEntity;
import com.skyline.common.util.MyMapper;
import com.skyline.common.vo.MessageEntity;
import com.skyline.common.vo.TradeBuyAndSellVO;
import com.skyline.common.vo.TradeCordDepthVO;
import com.skyline.common.vo.TradeEntityVO;
import com.skyline.common.vo.TradeEntrustEntityVO;
import com.skyline.common.vo.TradeUserEntrustVO;

public interface SeTTradeinfoMapper extends MyMapper<SeTTradeinfoEntity> {

	/**
	 * 查询智能戳和交易的队列信息
	 * @param map
	 * @return
	 */
	public List<MessageEntity> queryTradeEnstrut();

	/**
	 * 根据订单编号查询币币交易信息
	 * @param orderNo
	 * @return
	 */
	public SeTTradeinfoEntity queryTradeByOrderNo(String orderNo);

	/**
	 * 查询用户交易委托记录
	 * @param map
	 * @return
	 */
	public List<TradeUserEntrustVO> getUserTradeEntrust(
			@Param("userId")Integer userId,
			@Param("userEntrust")TradeUserEntrustVO userEntrust);


	List<TradeCordDepthVO> queryTrade(@Param("marketId") Integer marketId,@Param("type")Integer type);
	/**
	 * 最新委托数据
	 * @param marketId
	 * @param type
	 * @return
	 */
	List<TradeEntrustEntityVO> getNewTradeEntrust(
			@Param("marketId") Integer marketId,
			@Param("type")Integer type);

	/**
	 * kLine 的trades数据 
	 * @param marketId
	 * @param type
	 * @param limit
	 * @return
	 */
	List<TradeEntityVO> getNewTrade(@Param("marketId") Integer marketId,@Param("type") Integer type,@Param("limit") Integer limit);
	/**
	 * 绘画深度图数据，（买方买方力量对比）
	 * @param marketId
	 * @return
	 */
	List<TradeBuyAndSellVO>drawDepths(@Param("marketId")Integer marketId,@Param("type")Integer type);

}
