package com.skyline.common.vo;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * 交易行情信息
 * @author dengshidang
 *
 */
public class TradePrecoinEntityVO implements Serializable{

	private static final long serialVersionUID = 8335470407323118487L;

	/** 交易币ID*/
	private Integer precoinId;

	/** 交易币名称*/
	private String precoinName;

	/** 买卖币ID*/
	private Integer sufcoinId;

	/** 买卖币币名称*/
	private String sufcoinName;
	

	/**行情id**/
	private Integer marketId;

	/** 行情名称*/
	private String marketName;
   
	/**最新成交时间新时间**/
	private  String newTime;
	
	/**最新价位**/
	private transient Double newPrice;
	
	/**日涨幅**/
	private transient Double gains;
	
	/**当天交易总量*/
	private Double tradeTotal;
	
	/**当天最高成交价*/
	private Double high;
	
	/**当天最低价*/
	private Double low;
	
	/**兑换人民币后值  交易币 1 ：cny**/
	private Double cny;

	/**交易币log**/
	public String coinLog;
	

	public String getCoinLog() {
		return coinLog;
	}

	public void setCoinLog(String coinLog) {
		this.coinLog = coinLog;
	}

	public Double getCny() {
		return cny;
	}

	public void setCny(Double cny) {
		this.cny = cny;
	}

	
	public Double getTradeTotal() {
		return tradeTotal;
	}

	public void setTradeTotal(Double tradeTotal) {
		this.tradeTotal = tradeTotal;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Integer getPrecoinId() {
		return precoinId;
	}

	public void setPrecoinId(Integer precoinId) {
		this.precoinId = precoinId;
	}

	public String getPrecoinName() {
		return precoinName;
	}

	public void setPrecoinName(String precoinName) {
		this.precoinName = precoinName;
	}

	public Integer getSufcoinId() {
		return sufcoinId;
	}

	public void setSufcoinId(Integer sufcoinId) {
		this.sufcoinId = sufcoinId;
	}

	public String getSufcoinName() {
		return sufcoinName;
	}

	public void setSufcoinName(String sufcoinName) {
		this.sufcoinName = sufcoinName;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}


	public String getNewTime() {
		return newTime;
	}

	public void setNewTime(String newTime) {
		this.newTime = newTime;
	}

	public Double getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(Double newPrice) {
		this.newPrice = newPrice;
	}

	public Double getGains() {
		return gains;
	}

	public void setGains(Double gains) {
		this.gains = gains;
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
