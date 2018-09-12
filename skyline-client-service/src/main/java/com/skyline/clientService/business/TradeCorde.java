package com.skyline.clientService.business;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.skyline.clientService.kline.KlineParam;
import com.skyline.common.constant.Result;
import com.skyline.common.vo.ResultDataVO;
import com.skyline.common.vo.TradeEntrustEntityVO;
import com.skyline.common.vo.TradePrecoindNodeVO;

/**
 * 
 * @author dengshidang
 *
 */
public interface TradeCorde{   
	/**
	 * k线
	 * @param kp 参数实体
	 * @return
	 */
	ResultDataVO tradeCord(KlineParam kp);
	/**
	 * 
	 * @param marketId 行情id
	 * @return 买卖数据对比
	 */
	Result<?> depatMap(Integer marketId);
	/**
	 * 
	 * @param marketId 行情id
	 * @return 最新委托数据
	 */
	Result<PageInfo<TradeEntrustEntityVO>> getNewTradeEntrust(Integer marketId,Integer type,Integer pageNum,Integer pageSize);

	/**
	 * 
	 * @return 交易币种
	 */
	
	Result<List<TradePrecoindNodeVO>> getPrecoind();
	
	/**
	 * 成交记录
	 * @param marketId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	Result<?> getNewTradeCord(Integer marketId,Integer pageNum,Integer pageSize);
	/**
	 * 用户添加自选行情
	 * @param marketId
	 * @param userId
	 * @return
	 */
	Result<?> addOptional(Integer marketId, Integer userId);
	/**
	 * 获取用户收藏行情
	 * @param userId
	 * @param type
	 * @param dec
	 * @param keyword
	 * @return
	 */
	Result<?> getOptinal(Integer userId,Integer type,Integer dec,String keyword);
	/**
	 * 用户取消行情收藏
	 * @param marketId
	 * @param userId
	 * @return
	 */
	Result<?> cancelOptional(Integer marketId, Integer userId);
	/**
	 * 用户成交记录详情
	 * @param id 委托id,
	 * @param userId 用户id
	 * @return
	 */
	Result<?> getUserTradeDetails(Integer id, Integer userId);
	
}
