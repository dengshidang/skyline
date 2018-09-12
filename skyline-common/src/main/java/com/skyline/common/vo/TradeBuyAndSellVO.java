package com.skyline.common.vo;
/**
 * 绘画深度图数据
 * @author dengshidang
 *
 */
public class TradeBuyAndSellVO {
    /**单价*/
	private Double price ;
	
	/**数量*/     
	private Double amount;

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
