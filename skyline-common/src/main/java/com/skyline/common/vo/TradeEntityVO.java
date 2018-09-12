package com.skyline.common.vo;

import java.io.Serializable;

/**
 * 币币交易最新信息实体
 * @author dengshidang
 *
 */
public class TradeEntityVO implements Serializable{
	
	private static final long serialVersionUID = 2528442665279657213L;
	private Double amount;//成交数量
	private Double price;//成交价格
	private Integer tid;//id
	private Long time;//成交时间
	private String type;//方向
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type =type;
	}
	
	

}
