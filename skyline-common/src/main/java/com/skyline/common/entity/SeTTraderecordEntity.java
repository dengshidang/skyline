package com.skyline.common.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 表注释： 币币交易成交记录表 <br/>
 * 类描述： 币币交易成交记录表 <br/>
 * 创建人： 工具类初始创建 <br/>
 * 创建时间：2018-07-27 22:48:43
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_traderecord")
public class SeTTraderecordEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 行情id*/
	@Column(name = "marketId")
	private Integer marketId;

	/** 订单编号*/
	@Column(name = "orderNo")
	private String orderNo;

	/** 买入交易订单编号*/
	@Column(name = "enterorderNo")
	private String enterorderNo;

	/** 买入交易订单编号*/
	@Column(name = "outorderNo")
	private String outorderNo;

	/** 卖出币ID*/
	@Column(name = "outcoinId")
	private Integer outcoinId;

	/** 卖出币名称*/
	@Column(name = "outcoinName")
	private String outcoinName;

	/** 卖出手续费*/
	@Column(name = "outFee")
	private Double outFee;

	/** 买入币ID*/
	@Column(name = "entercoinId")
	private Integer entercoinId;

	/** 买入币名称*/
	@Column(name = "entercoinName")
	private String entercoinName;

	/** 买入手续费*/
	@Column(name = "enterFee")
	private Double enterFee;

	/** 交易数量*/
	@Column(name = "number")
	private Double number;

	/** 成交价格*/
	@Column(name = "price")
	private Double price;

	/** 成交金额*/
	@Column(name = "money")
	private Double money;

	/** 状态:(0:有效,1无效)*/
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

	public void setMarketId(Integer marketId){
		this.marketId=marketId;
	}

	public Integer getMarketId(){
		return marketId;
	}

	public void setOrderNo(String orderNo){
		this.orderNo=orderNo;
	}

	public String getOrderNo(){
		return orderNo;
	}

	public void setEnterorderNo(String enterorderNo){
		this.enterorderNo=enterorderNo;
	}

	public String getEnterorderNo(){
		return enterorderNo;
	}

	public void setOutorderNo(String outorderNo){
		this.outorderNo=outorderNo;
	}

	public String getOutorderNo(){
		return outorderNo;
	}

	public void setOutcoinId(Integer outcoinId){
		this.outcoinId=outcoinId;
	}

	public Integer getOutcoinId(){
		return outcoinId;
	}

	public void setOutcoinName(String outcoinName){
		this.outcoinName=outcoinName;
	}

	public String getOutcoinName(){
		return outcoinName;
	}

	public void setOutFee(Double outFee){
		this.outFee=outFee;
	}

	public Double getOutFee(){
		return outFee;
	}

	public void setEntercoinId(Integer entercoinId){
		this.entercoinId=entercoinId;
	}

	public Integer getEntercoinId(){
		return entercoinId;
	}

	public void setEntercoinName(String entercoinName){
		this.entercoinName=entercoinName;
	}

	public String getEntercoinName(){
		return entercoinName;
	}

	public void setEnterFee(Double enterFee){
		this.enterFee=enterFee;
	}

	public Double getEnterFee(){
		return enterFee;
	}

	public void setNumber(Double number){
		this.number=number;
	}

	public Double getNumber(){
		return number;
	}

	public void setPrice(Double price){
		this.price=price;
	}

	public Double getPrice(){
		return price;
	}

	public void setMoney(Double money){
		this.money=money;
	}

	public Double getMoney(){
		return money;
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
