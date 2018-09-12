package com.skyline.common.vo;

import java.util.List;
/**
 * 绘画深度图买卖方数据集
 * @author dengshidang
 *
 */
public class DepthVO2 {
	/**买方数据集合*/
	private List<TradeBuyAndSellVO> buy;
	/**卖方数据集合*/
	private List<TradeBuyAndSellVO> sell;
	public List<TradeBuyAndSellVO> getBuy() {
		return buy;
	}
	public void setBuy(List<TradeBuyAndSellVO> buy) {
		this.buy = buy;
	}
	public List<TradeBuyAndSellVO> getSell() {
		return sell;
	}
	public void setSell(List<TradeBuyAndSellVO> sell) {
		this.sell = sell;
	}
	
	
}
