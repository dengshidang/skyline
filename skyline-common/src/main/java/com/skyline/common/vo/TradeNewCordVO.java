package com.skyline.common.vo;
/**
 * 实时成交实体
 * @author dengshidang
 *
 */
public class TradeNewCordVO {

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

	/**成交价**/
	private Double price;
	/**成交量**/
	private Double number;
	
	/**成交时间**/
	private  String time;
	
	/**方向**/
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	

}
