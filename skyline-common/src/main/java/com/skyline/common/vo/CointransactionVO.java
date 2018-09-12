package com.skyline.common.vo;

import java.io.Serializable;

import javax.persistence.Column;

public class CointransactionVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;

	/** 订单编号*/
	private String orderNo;

	/** 用户ID*/
	private Integer userId;

	/** 币种名称*/
	private String coinName;
	
	/** 币种图标*/
	private String coinImg;

	/** 交易ID*/
	private String txId;

	/**钱包地址*/
	private String walletaddress;

	/** 金额*/
	private Double amount;

	/** 手续费*/
	private Double fee;

	/** 确认数量，值为-1表示失败*/
	private Integer confirmations;

	/** 充值币种ID*/
	private Integer coinId;

	/** 交易类别，0接收|1发送*/
	private Integer categroy;

	/** 状态:0待审核，1审核成功，2审核失败，3充值退回，4异常打款*/
	private Integer status;
	
	/** 备注*/
	private String remark;

	/** 创建时间*/
	private String createTime;

	/** 更新时间*/
	private String updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public String getCoinImg() {
		return coinImg;
	}

	public void setCoinImg(String coinImg) {
		this.coinImg = coinImg;
	}

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public String getWalletaddress() {
		return walletaddress;
	}

	public void setWalletaddress(String walletaddress) {
		this.walletaddress = walletaddress;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Integer getConfirmations() {
		return confirmations;
	}

	public void setConfirmations(Integer confirmations) {
		this.confirmations = confirmations;
	}

	public Integer getCoinId() {
		return coinId;
	}

	public void setCoinId(Integer coinId) {
		this.coinId = coinId;
	}

	public Integer getCategroy() {
		return categroy;
	}

	public void setCategroy(Integer categroy) {
		this.categroy = categroy;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}


	
}
