package com.skyline.common.vo;

import com.skyline.common.util.DateUtil;

public class WebSocketVO {
	/** 发送者 -1代表是系统发送*/
	private Integer senderId;
	/** 接收者 */
	private Integer recipientId;
	/** 消息类型*/
	private Integer type;
	/** 消息内容*/
	private String   msg;
	/** 时间*/
	private String  createTime;
	
	/**订单id*/
	private Integer orderId;
	
	
	public Integer getSenderId() {
		return senderId;
	}
	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}
	public Integer getRecipientId() {
		return recipientId;
	}
	public void setRecipientId(Integer recipientId) {
		this.recipientId = recipientId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCreateTime() {
		return this.createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	
	
}
