package com.skyline.wallet.mapper;

import java.util.List;

import com.skyline.common.entity.CointransactionEntity;
import com.skyline.common.util.MyMapper;
import com.skyline.common.vo.CointransactionVO;

public interface CoinTransactionMapper extends MyMapper<CointransactionEntity>{
	
	/**
	 * 根据交易hash获取充值提现记录
	 * @param tradeHash 交易hash
	 * @return
	 */
	public CointransactionEntity queryCoinTransactionByHash(String tradeHash);
	
	/**
	 * 查询交易信息
	 * @param entity
	 * @return
	 */
	public List<CointransactionVO> queryCoinTransaction(CointransactionEntity entity);
}