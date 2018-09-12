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
 * 创建时间：2018-07-28 16:27:21
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_transactionstate")
public class Transactionstate implements Serializable {

	private static final long serialVersionUID = 1L;

	/** */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 订单号*/
	@Column(name = "orderNo")
	private String orderNo;

	/** 申述用户*/
	@Column(name = "stateUser")
	private Integer stateUser;

	/** 被控用户*/
	@Column(name = "takeUser")
	private Integer takeUser;

	/** 证据链接*/
	@Column(name = "proveUrl")
	private String proveUrl;

	/** 交易币种ID*/
	@Column(name = "coinId")
	private Integer coinId;

	/** 交易币种名称*/
	@Column(name = "coinName")
	private String coinName;

	/** */
	@Column(name = "number")
	private Double number;

	/** */
	@Column(name = "totalPrice")
	private Double totalPrice;

	/** 支付方式*/
	@Column(name = "payWay")
	private Integer payWay;

	/** 状态:(0:审核中,1:审核通过,2:驳回, 3撤消 )*/
	@Column(name = "status")
	private Integer status;

	/** 创建时间*/
	@Column(name = "createTime")
	private String createTime;

	/** 更新时间*/
	@Column(name = "updateTime")
	private String updateTime;
	/**备注*/
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "price")
	private Double price;

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

	public void setStateUser(Integer stateUser){
		this.stateUser=stateUser;
	}

	public Integer getStateUser(){
		return stateUser;
	}

	public void setTakeUser(Integer takeUser){
		this.takeUser=takeUser;
	}

	public Integer getTakeUser(){
		return takeUser;
	}

	public void setProveUrl(String proveUrl){
		this.proveUrl=proveUrl;
	}

	public String getProveUrl(){
		return proveUrl;
	}

	public void setCoinId(Integer coinId){
		this.coinId=coinId;
	}

	public Integer getCoinId(){
		return coinId;
	}

	public void setCoinName(String coinName){
		this.coinName=coinName;
	}

	public String getCoinName(){
		return coinName;
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



	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
