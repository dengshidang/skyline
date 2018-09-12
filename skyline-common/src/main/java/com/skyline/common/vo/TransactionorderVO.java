package com.skyline.common.vo;

import java.io.Serializable;

public class TransactionorderVO implements Serializable {

	private static final long serialVersionUID = 1L;


	private Integer id;
	/** 交易订单号*/
	private String orderNo;
	/** 交易流水号*/
	private String transactionNo;
	/** 商户ID*/
	private Integer merchantId;
	/** 商户名称*/
	private String  merchantName;
	/** 用户ID*/
	private Integer userId;
	/**用户名称*/
	private String   userName;
	/**币种id*/
	private Integer coinId;
	/**币种名称*/
	 private String coinName;
	 /**交易类型id*/
	 private Integer transactionTypeId;
	 /**交易类型*/
	 private Integer transactionType;
	/** 数量*/
	private Double number;
	/**单价*/
	private Double price;
	/** 总价*/
	private Double totalPrice;
	/** 支付方式*/
	private String payWay;
	/** 备注码*/
	private String remarkCode;
	/** 状态:(0:待支付,1:已支付,2:完成,3:撤销,4:自动取消,5:申述中,6:处理完成)*/
	private Integer status;
	/** 创建时间*/
	private String createTime;
	/** 更新时间*/
	private String updateTime;
	/**已付款时间*/
	private String makeTime;
	
	private Integer time;
	/**
	 * 付款图片url
	 */
	private String imgUrl;
	
	private Integer isAppeal;

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return id;
	}

	public void setOrderNo(String orderNo){
		this.orderNo=orderNo;
	}

	public String getOrderNo(){
		return orderNo;
	}

	public void setTransactionNo(String transactionNo){
		this.transactionNo=transactionNo;
	}

	public String getTransactionNo(){
		return transactionNo;
	}

	public void setMerchantId(Integer merchantId){
		this.merchantId=merchantId;
	}

	public Integer getMerchantId(){
		return merchantId;
	}

	public void setUserId(Integer userId){
		this.userId=userId;
	}

	public Integer getUserId(){
		return userId;
	}

	public void setNumber(Double number){
		this.number=number;
	}

	public Double getNumber(){
		return number;
	}

	public void setTotalPrice(Double totalPrice){
		this.totalPrice=totalPrice;
	}

	public Double getTotalPrice(){
		return totalPrice;
	}

	public void setPayWay(String payWay){
		this.payWay=payWay;
	}

	public String getPayWay(){
		return payWay;
	}

	public void setRemarkCode(String remarkCode){
		this.remarkCode=remarkCode;
	}

	public String getRemarkCode(){
		return remarkCode;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return status;
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

	public String getMakeTime() {
		return makeTime;
	}

	public void setMakeTime(String makeTime) {
		this.makeTime = makeTime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Integer getTransactionTypeId() {
		return transactionTypeId;
	}

	public void setTransactionTypeId(Integer transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	public Integer getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Integer transactionType) {
		this.transactionType = transactionType;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getIsAppeal() {
		return isAppeal;
	}

	public void setIsAppeal(Integer isAppeal) {
		this.isAppeal = isAppeal;
	}

	
	
	
	
}
