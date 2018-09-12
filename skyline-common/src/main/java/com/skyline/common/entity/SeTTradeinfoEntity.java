package com.skyline.common.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 表注释： 币币交易信息表 <br/>
 * 类描述： 币币交易信息表 <br/>
 * 创建人： 工具类初始创建 <br/>
 * 创建时间：2018-07-02 14:35:33
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_tradeinfo")
public class SeTTradeinfoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 订单编号*/
	@Column(name = "orderNo")
	private String orderNo;

	/** 行情ID*/
	@Column(name = "marketId")
	private Integer marketId;

	/** 类型（0:买入，1:卖出）*/
	@Column(name = "type")
	private Integer type;

	/** 用户ID */
	@Column(name = "userId")
	private Integer userId;

	/** 交易币ID*/
	@Column(name = "precoinId")
	private Integer precoinId;

	/** 交易币名称*/
	@Column(name = "precoinName")
	private String precoinName;

	/** 买卖币ID*/
	@Column(name = "sufcoinId")
	private Integer sufcoinId;

	/** 买卖币币名称*/
	@Column(name = "sufcoinName")
	private String sufcoinName;

	/** 交易总量*/
	@Column(name = "totalNum")
	private Double totalNum;

	/** 成交量*/
	@Column(name = "dealNum")
	private Double dealNum;

	/** 交易金额*/
	@Column(name = "number")
	private Double number;

	/** 成交金额*/
	@Column(name = "dealSum")
	private Double dealSum;

	/** 委托价格*/
	@Column(name = "price")
	private Double price;
	
	/** 手续费*/
	@Column(name = "fee")
	private Double fee;

	/** 状态:(0:挂起,1:处理中,2:完成,3:撤销)*/
	@Column(name = "status")
	private Integer status;

	/** 创建时间*/
	@Column(name = "createTime")
	private String createTime;

	/** 更新时间*/
	@Column(name = "updateTime")
	private String updateTime;


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

	public void setMarketId(Integer marketId){
		this.marketId=marketId;
	}

	public Integer getMarketId(){
		return marketId;
	}

	public void setType(Integer type){
		this.type=type;
	}

	public Integer getType(){
		return type;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setPrecoinId(Integer precoinId){
		this.precoinId=precoinId;
	}

	public Integer getPrecoinId(){
		return precoinId;
	}

	public void setPrecoinName(String precoinName){
		this.precoinName=precoinName;
	}

	public String getPrecoinName(){
		return precoinName;
	}

	public void setSufcoinId(Integer sufcoinId){
		this.sufcoinId=sufcoinId;
	}

	public Integer getSufcoinId(){
		return sufcoinId;
	}

	public void setSufcoinName(String sufcoinName){
		this.sufcoinName=sufcoinName;
	}

	public String getSufcoinName(){
		return sufcoinName;
	}

	public void setTotalNum(Double totalNum){
		this.totalNum=totalNum;
	}

	public Double getTotalNum(){
		return totalNum;
	}

	public void setDealNum(Double dealNum){
		this.dealNum=dealNum;
	}

	public Double getDealNum(){
		return dealNum;
	}

	public void setNumber(Double number){
		this.number=number;
	}

	public Double getNumber(){
		return number;
	}

	public Double getDealSum() {
		return dealSum;
	}

	public void setDealSum(Double dealSum) {
		this.dealSum = dealSum;
	}

	public void setPrice(Double price){
		this.price=price;
	}

	public Double getPrice(){
		return price;
	}

	public void setFee(Double fee){
		this.fee=fee;
	}

	public Double getFee(){
		return fee;
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

	
	@Override
	public String toString() {
		return "SeTTradeinfoEntity [id=" + id + ", orderNo=" + orderNo + ", marketId=" + marketId + ", type=" + type
				+ ", userId=" + userId + ", precoinId=" + precoinId + ", precoinName=" + precoinName + ", sufcoinId="
				+ sufcoinId + ", sufcoinName=" + sufcoinName + ", totalNum=" + totalNum + ", dealNum=" + dealNum
				+ ", number=" + number + ", dealSum=" + dealSum + ", price=" + price + ", fee=" + fee + ", status="
				+ status + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}

}
