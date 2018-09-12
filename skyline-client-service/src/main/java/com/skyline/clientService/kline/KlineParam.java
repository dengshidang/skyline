package com.skyline.clientService.kline;
/**
 * k线请求参数
 * @author dengshidang
 *
 */
public class KlineParam {
	/**查询k线数据主体标识，这里指行情的id*/
	private Integer symbol;
	/**毫秒时间戳*/
	private Long range;
	/**开始时间,秒*/
	private Long since;

	/**结束时，秒*/
	private Long prevTradeTime;

	/**数据量*/
	private Integer limit;

	public Integer getSymbol() {
		return symbol;
	}

	public void setSymbol(Integer symbol) {
		this.symbol = symbol;
	}



	public Long getRange() {
		return range;
	}

	public void setRange(Long range) {
		this.range = range;
	}

	public Long getSince() {
		return since;
	}

	public void setSince(Long since) {
		this.since = since;
	}

	public Long getPrevTradeTime() {
		return prevTradeTime;
	}

	public void setPrevTradeTime(Long prevTradeTime) {
		this.prevTradeTime = prevTradeTime;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}
