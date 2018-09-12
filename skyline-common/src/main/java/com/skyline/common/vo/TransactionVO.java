package com.skyline.common.vo;

import java.io.Serializable;




public class TransactionVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	/** 交易流水号*/
	private String transactionNo;

	/** 商户id*/
	private Integer merchantId;
	
	/** 商户昵称*/
	private String  merchantName;

	/** 交易币种id*/
	private Integer coinId;

	/** 交易币种名称*/
	private String coinName;

	/** 最小交易数*/
	private Double min;

	/** 最大交易数*/
	private Double max;
	
	/** 价格*/
	private Double price;

	/** 总数量*/
	private Double totalNum;

	/** 成交数量*/
	private Double finishNum;

	/** 剩余数量*/
	private Double surplusNum;

	/** 支持支付方式 */
	private String payWay;

	/** 状态 1 挂起 2完成 3 撤销 */
	private Integer status;

	/** 类型id*/
	private Integer transactiontypeId;
	
	
	/** 类型（0买入 1卖出）*/
	private Integer transactiontype;
	
	
	/**30成交数*/
	private Integer num;
	
	/**完成订单数*/
	private Integer completeOrderNum;
	
	/**订单总数*/
	private Integer orderNum;
	/**创建时间*/
	private String createTime;
	/**更改时间*/
	private String updateTime;
	/**备注*/
	private String remark;
	/**正在处理的订单数*/
	private Integer orderInNum;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
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
	public Double getMin() {
		return min;
	}
	public void setMin(Double min) {
		this.min = min;
	}
	public Double getMax() {
		return max;
	}
	public void setMax(Double max) {
		this.max = max;
	}
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
	public Double getFinishNum() {
		return finishNum;
	}
	public void setFinishNum(Double finishNum) {
		this.finishNum = finishNum;
	}
	public Double getSurplusNum() {
		return surplusNum;
	}
	public void setSurplusNum(Double surplusNum) {
		this.surplusNum = surplusNum;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getTransactiontypeId() {
		return transactiontypeId;
	}
	public void setTransactiontypeId(Integer transactiontypeId) {
		this.transactiontypeId = transactiontypeId;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getCompleteOrderNum() {
		return completeOrderNum;
	}
	public void setCompleteOrderNum(Integer completeOrderNum) {
		this.completeOrderNum = completeOrderNum;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getTransactiontype() {
		return transactiontype;
	}
	public void setTransactiontype(Integer transactiontype) {
		this.transactiontype = transactiontype;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public Integer getOrderInNum() {
		return orderInNum;
	}
	public void setOrderInNum(Integer orderInNum) {
		this.orderInNum = orderInNum;
	}

	
	
	
	
}
