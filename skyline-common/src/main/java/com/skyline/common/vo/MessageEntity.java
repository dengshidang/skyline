package com.skyline.common.vo;

import java.io.Serializable;

import com.skyline.common.util.DateUtil;

/**
 * 币币交易消息队列信息
 * @author Yjian
 * @time 2018-7-10
 */
public class MessageEntity implements Comparable<MessageEntity>,Serializable {

	private static final long serialVersionUID = 4941380528955664887L;
	@Override
	public String toString() {
		return "MessageEntity [marketId=" + marketId + ", orderNo=" + orderNo + ", type=" + type + ", price=" + price
				+ ", number=" + number + ", sendTime=" + sendTime + "]";
	}

	/** 行情ID **/
	private Integer marketId;
	
	/** 订单编号 **/
	private String orderNo;
	
	/** 交易类型(0:买入 1:卖出) **/
	private int type;
	
	/** 价格 **/
	private double price;
	
	/** 数量 **/
	private double number;
	
	/** 推送时间 **/
	private String sendTime;
	
	

	public MessageEntity(Integer marketId, String orderNo, int type, double price, double number, String sendTime) {
		super();
		this.marketId = marketId;
		this.orderNo = orderNo;
		this.type = type;
		this.price = price;
		this.number = number;
		this.sendTime = sendTime;
	}
	
	public MessageEntity(){
		
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getNumber() {
		return number;
	}

	public void setNumber(double number) {
		this.number = number;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	@Override
	public int compareTo(MessageEntity o) {
		
		if(o.getType() == 0){
			if(this.price > o.getPrice()){
		        return -1;
			}else if(this.price<o.getPrice()){
		        return 1;
			}else if(this.price == o.getPrice()){
				
				boolean bl = DateUtil.compareDate(this.sendTime, o.getSendTime(), "yyyy-MM-dd HH:mm:ss");
				if(bl){
					 return -1;
				}else{
					 return 1;
				}
			}else{
		        return 0;
			}
		}else{
			if(this.price < o.getPrice()){
		        return -1;
			}else if(this.price >o.getPrice()){
		        return 1;
			}else if(this.price == o.getPrice()){
				
				boolean bl = DateUtil.compareDate(this.sendTime, o.getSendTime(), "yyyy-MM-dd HH:mm:ss");
				if(bl){
					 return -1;
				}else{
					 return 1;
				}
			}else{
		        return 0;
			}
		}
		
		

	}
	
	
	
	
}
