package com.skyline.common.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 表注释：  <br/>
 * 类描述：  <br/>
 * 创建人： 工具类初始创建 <br/>
 * 创建时间：2018-07-16 13:42:36
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_transaction_message")
public class TransactionMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	/** id*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 消息内容*/
	@Column(name = "message")
	private String message;

	/** 类型*/
	@Column(name = "orderId")
	private Integer orderId;

	/** 发送者 -1代表是系统发送*/
	@Column(name = "senderId")
	private Integer senderId;

	/** 接收者*/
	@Column(name = "recipientId")
	private Integer recipientId;

	/** 创建时间*/
	@Column(name = "createTime")
	private String createTime;

	/** */
	@Column(name = "updateTime")
	private String updateTime;


	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return id;
	}

	public void setMessage(String message){
		this.message=message;
	}

	public String getMessage(){
		return message;
	}

	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public void setSenderId(Integer senderId){
		this.senderId=senderId;
	}

	public Integer getSenderId(){
		return senderId;
	}

	public void setRecipientId(Integer recipientId){
		this.recipientId=recipientId;
	}

	public Integer getRecipientId(){
		return recipientId;
	}



	public void setCreateTime(String createTime){
		this.createTime=createTime;
	}

	public String getCreateTime(){
		return createTime;
	}

	public void setUpdateTime(String updateTime){
		this.updateTime=updateTime;
	}

	public String getUpdateTime(){
		return updateTime;
	}


	
}
