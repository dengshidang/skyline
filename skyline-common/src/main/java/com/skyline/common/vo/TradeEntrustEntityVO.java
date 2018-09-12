package com.skyline.common.vo;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 最新委托数据实体
 * @author dengshidang
 *
 */
public class TradeEntrustEntityVO implements Serializable{
	
	private static final long serialVersionUID = -334656241093979036L;
	/*交易币名称*/
	private String  precoinName;
	/*买卖币名称*/
	private String sufcoinName;
	/*交易总量*/
	private Double totalNum;
	/*数量*/
	private Double dealNum;
	/*价位*/
	private Double price;
	/*没卖类型 0:买，1卖*/
	private Integer type;
	 
	/*委托动态累计数*/	
	private  Double sum;
	
	public Double getSum() {
		return sum;
	}
	public void setSum(Double sum) {
		this.sum = sum;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public Double getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Double totalNum) {
		this.totalNum = totalNum;
	}
	public Double getDealNum() {
		return dealNum;
	}
	public void setDealNum(Double dealNum) {
		this.dealNum = dealNum;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
}
