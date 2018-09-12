package com.skyline.common.vo;
/**
 * 深度图数据具体对象
 * @author dengshidang
 *
 */
public class TradeCordDepthVO {
	/*成交价位*/
	private Double price;
	/*成交量*/
	private Double totalNum;
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Double totalNum) {
		this.totalNum = totalNum;
	}
}
