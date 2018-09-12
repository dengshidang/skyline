package com.skyline.service.mapper;

import java.util.List;

import com.skyline.common.entity.SeTTradeinfoEntity;
import com.skyline.common.util.MyMapper;
import com.skyline.common.vo.MessageEntity;

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
	
	
	
}
