package com.skyline.wallet.btc.param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建原始交易参数
 * @author Administrator
 *
 */
public class TradeCreateParam {

	/** 未使用的交易信息 **/
	private List<Map<String, Object>> tradeArray;
	
	/** 转账信息 **/
	private Map<String, Object> tradeMap;
	
	/**
	 * 初始化
	 * @param tradeUnused 有效交易信息实体
	 * @param toAddress 接收地址
	 * @param amount 转账金额
	 */
	public TradeCreateParam(TradeUnused tradeUnused,String toAddress,String amount){
		
		tradeArray = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("txid", tradeUnused.getTxid());
		map.put("vout", tradeUnused.getVout());
		tradeArray.add(map);
		
		tradeMap = new HashMap<String, Object>();
		tradeMap.put(toAddress, amount);
		
	}
	
	/**
	 * 初始化
	 * @param tradeUnused 有效交易信息实体
	 * @param toAddress 接收地址
	 * @param amount 转账金额
	 */
	public TradeCreateParam(List<TradeUnused> tradeUnusedList,String toAddress,String amount){
		
		tradeArray = new ArrayList<Map<String, Object>>();
		for(TradeUnused tradeUnused :tradeUnusedList){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("txid", tradeUnused.getTxid());
			map.put("vout", tradeUnused.getVout());
			tradeArray.add(map);
		}
		tradeMap = new HashMap<String, Object>();
		tradeMap.put(toAddress, amount);
		
	}

	public List<Map<String, Object>> getTradeArray() {
		return tradeArray;
	}

	public void setTradeArray(List<Map<String, Object>> tradeArray) {
		this.tradeArray = tradeArray;
	}

	public Map<String, Object> getTradeMap() {
		return tradeMap;
	}

	public void setTradeMap(Map<String, Object> tradeMap) {
		this.tradeMap = tradeMap;
	}
	
	
	
}
