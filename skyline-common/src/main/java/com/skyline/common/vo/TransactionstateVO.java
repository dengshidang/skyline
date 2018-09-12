package com.skyline.common.vo;




public class TransactionstateVO {
	
	/** */
	private Integer id;

	/** 订单号*/
	private String orderNo;

	/** 申述用户*/
	private Integer stateUser;
	
	/**申述用户名称*/
	private String  stateUserName;
	
	/**申述用户账号*/
	private String stateUserAccount;

	/** 被控用户*/
	private Integer takeUser;
	
	/**申述用户名称*/
	private String  takeUserName;
	
	/**申述用户账号*/
	private String takeUserAccount;

	/** 证据链接*/
	private String proveUrl;
	
	private String content;

	/** 交易币种ID*/
	private Integer coinId;

	/** 交易币种名称*/
	private String coinName;

	/** 总数量 */
	private Double number;

	private Double price;
	/** */
	private Double totalPrice;

	/** 支付方式 id*/
	private String payWayId;
	
	/** 支付方式*/
	private String payWayType; 
	
	/** 状态:(0:审核中,1:审核通过,2:驳回, 3撤消 )*/
	private Integer status;

	private String createTime;

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

	public Integer getStateUser() {
		return stateUser;
	}

	public void setStateUser(Integer stateUser) {
		this.stateUser = stateUser;
	}

	public String getStateUserName() {
		return stateUserName;
	}

	public void setStateUserName(String stateUserName) {
		this.stateUserName = stateUserName;
	}

	public String getStateUserAccount() {
		return stateUserAccount;
	}

	public void setStateUserAccount(String stateUserAccount) {
		this.stateUserAccount = stateUserAccount;
	}

	public Integer getTakeUser() {
		return takeUser;
	}

	public void setTakeUser(Integer takeUser) {
		this.takeUser = takeUser;
	}

	public String getProveUrl() {
		return proveUrl;
	}

	public void setProveUrl(String proveUrl) {
		this.proveUrl = proveUrl;
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

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getPayWayId() {
		return payWayId;
	}

	public void setPayWayId(String payWayId) {
		this.payWayId = payWayId;
	}

	public String getPayWayType() {
		return payWayType;
	}

	public void setPayWayType(String payWayType) {
		this.payWayType = payWayType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getTakeUserName() {
		return takeUserName;
	}

	public void setTakeUserName(String takeUserName) {
		this.takeUserName = takeUserName;
	}

	public String getTakeUserAccount() {
		return takeUserAccount;
	}

	public void setTakeUserAccount(String takeUserAccount) {
		this.takeUserAccount = takeUserAccount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	
	
}
