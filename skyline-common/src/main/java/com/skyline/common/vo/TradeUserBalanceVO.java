package com.skyline.common.vo;
/**
 * 可交易的币种有效余额
 * @author dengshidang
 *
 */
public class TradeUserBalanceVO {
	/**交易id*/
	private Integer coinId;
	/**交易币名称*/
	private String coinName;
	/**有效金额*/
    private Double validNum;
	public Integer getCoinId() {
		return coinId;
	}
	public void setCoinId(Integer coinId) {
		this.coinId = coinId;
	}
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	public Double getValidNum() {
		return validNum;
	}
	public void setValidNum(Double validNum) {
		this.validNum = validNum;
	}
  

}
