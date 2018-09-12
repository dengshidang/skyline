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
 * 创建时间：2018-07-11 15:20:51
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_billinfo")
public class Billinfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** */
	@Column(name = "orderNo")
	private String orderNo;

	/** */
	@Column(name = "userId")
	private Integer userId;

	/** */
	@Column(name = "userName")
	private String userName;

	/** */
	@Column(name = "coinId")
	private Integer coinId;

	/**币种名称，英文简称（BTC） */
	@Column(name = "coinName")
	private String coinName;

	/** */
	@Column(name = "payCoinId")
	private Integer payCoinId;

	/** */
	@Column(name = "payCoinName")
	private String payCoinName;

	/** */
	@Column(name = "type")
	private Integer type;

	/** */
	@Column(name = "number")
	private Double number;

	/** */
	@Column(name = "fee")
	private Double fee;

	/** */
	@Column(name = "forNum")
	private Double forNum;

	/** */
	@Column(name = "forFrozenNum")
	private Double forFrozenNum;

	/** */
	@Column(name = "forValidNum")
	private Double forValidNum;

	/** */
	@Column(name = "nowNum")
	private Double nowNum;

	/** */
	@Column(name = "nowFrozenNum")
	private Double nowFrozenNum;

	/** */
	@Column(name = "nowValidNum")
	private Double nowValidNum;

	/** */
	@Column(name = "behavior")
	private Integer behavior;

	/** */
	@Column(name = "status")
	private Integer status;

	/** */
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

	public void setUserId(Integer userId){
		this.userId=userId;
	}

	public Integer getUserId(){
		return userId;
	}

	public void setUserName(String userName){
		this.userName=userName;
	}

	public String getUserName(){
		return userName;
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

	public void setPayCoinId(Integer payCoinId){
		this.payCoinId=payCoinId;
	}

	public Integer getPayCoinId(){
		return payCoinId;
	}

	public void setPayCoinName(String payCoinName){
		this.payCoinName=payCoinName;
	}

	public String getPayCoinName(){
		return payCoinName;
	}

	public void setType(Integer type){
		this.type=type;
	}

	public Integer getType(){
		return type;
	}

	public void setNumber(Double number){
		this.number=number;
	}

	public Double getNumber(){
		return number;
	}

	public void setFee(Double fee){
		this.fee=fee;
	}

	public Double getFee(){
		return fee;
	}

	public void setForNum(Double forNum){
		this.forNum=forNum;
	}

	public Double getForNum(){
		return forNum;
	}

	public void setForFrozenNum(Double forFrozenNum){
		this.forFrozenNum=forFrozenNum;
	}

	public Double getForFrozenNum(){
		return forFrozenNum;
	}

	public void setForValidNum(Double forValidNum){
		this.forValidNum=forValidNum;
	}

	public Double getForValidNum(){
		return forValidNum;
	}

	public void setNowNum(Double nowNum){
		this.nowNum=nowNum;
	}

	public Double getNowNum(){
		return nowNum;
	}

	public void setNowFrozenNum(Double nowFrozenNum){
		this.nowFrozenNum=nowFrozenNum;
	}

	public Double getNowFrozenNum(){
		return nowFrozenNum;
	}

	public void setNowValidNum(Double nowValidNum){
		this.nowValidNum=nowValidNum;
	}

	public Double getNowValidNum(){
		return nowValidNum;
	}

	public void setBehavior(Integer behavior){
		this.behavior=behavior;
	}

	public Integer getBehavior(){
		return behavior;
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

}
