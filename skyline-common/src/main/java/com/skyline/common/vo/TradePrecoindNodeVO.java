package com.skyline.common.vo;
/**
 * 币种
 * @author dengshidang
 *
 */
public class TradePrecoindNodeVO {
	/*交易币id*/
	private Integer precoinId;
	/*交易名称*/
	private String precoinName;
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
}
