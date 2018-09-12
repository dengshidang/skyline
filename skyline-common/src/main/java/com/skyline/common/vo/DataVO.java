package com.skyline.common.vo;

import java.util.List;


/**
 * k线数据实体：
 * @author dengshidang
 *
 */
public class DataVO{
	/*k线数据*/
	private List<List<Object>> lines;
	/*深度图数据*/
	private DepthVO depths;
	/*委托交易数据*/
	private List<TradeEntityVO> trades;

	public List<TradeEntityVO> getTrades() {
		return trades;
	}
	public void setTrades(List<TradeEntityVO> trades) {
		this.trades = trades;
	}
	public List<List<Object>> getLines() {
		return lines;
	}
	public void setLines(List<List<Object>> lines) {
		this.lines = lines;
	}
	public DepthVO getDepths() {
		return depths;
	}
	public void setDepths(DepthVO depths) {
		this.depths = depths;
	}

}
