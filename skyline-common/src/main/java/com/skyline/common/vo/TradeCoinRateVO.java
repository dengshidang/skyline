package com.skyline.common.vo;
/**
 * 币币交易费率详情实体
 * @author dengshidang
 *
 */
public class TradeCoinRateVO {
    /**行情id**/
	private Integer marketId;
	
	/**行情名称**/
	private String marketName;
	
	/**交易币名称**/
	private String precoinName;
	
	/**买卖币名称**/
	private String sufcoinName;		
	
	/**挂单费率**/
	private Double buyFee;
	
	/**吃单费率**/
	private Double sellFee;

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

	public String getPrecoinName() {
		return precoinName;
	}

	public void setPrecoinName(String precoinName) {
		this.precoinName = precoinName;
	}

	public String getSufcoinName() {
		return sufcoinName;
	}

	public void setSufcoinName(String sufcoinName) {
		this.sufcoinName = sufcoinName;
	}

	
	public Double getBuyFee() {
		return buyFee;
	}

	public void setBuyFee(Double buyFee) {
		this.buyFee = buyFee;
	}

	public Double getSellFee() {
		return sellFee;
	}

	public void setSellFee(Double sellFee) {
		this.sellFee = sellFee;
	}
	
}
