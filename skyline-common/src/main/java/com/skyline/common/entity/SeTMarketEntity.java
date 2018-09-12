package com.skyline.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 表注释： 币币交易行情信息表 <br/>
 * 类描述： 币币交易行情信息表 <br/>
 * 创建人： 工具类初始创建 <br/>
 * 创建时间：2018-07-02 14:34:40
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_market")
public class SeTMarketEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

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

	/** 市场名称*/
	@Column(name = "marketName")
	private String marketName;

	/** 精准浮点位数*/
	@Column(name = "round")
	private Integer round;

	/** 可输入数量小位数*/
	@Column(name = "numDecimal")
	private Integer numDecimal;

	/** 可输入价格小位数*/
	@Column(name = "priDecimal")
	private Integer priDecimal;

	/** 买入手续费*/
	@Column(name = "buyFee")
	private Double buyFee;

	/** 买入手续费*/
	@Column(name = "sellFee")
	private Double sellFee;

	/** 最小买入数*/
	@Column(name = "buyMin")
	private Double buyMin;

	/** 最大买入数*/
	@Column(name = "buyMax")
	private Double buyMax;

	/** 最小卖出数*/
	@Column(name = "sellMin")
	private Double sellMin;

	/** 最大卖出数*/
	@Column(name = "sellMax")
	private Double sellMax;

	/** 最小交易额*/
	@Column(name = "tradeMin")
	private Double tradeMin;

	/** 最大交易额*/
	@Column(name = "tradeMax")
	private Double tradeMax;

	/** 涨浮限制*/
	@Column(name = "litreAstrict")
	private String litreAstrict;

	/** 跌浮限制*/
	@Column(name = "dropAstrict")
	private String dropAstrict;

	/** 前一天的最后交易价*/
	@Column(name = "houPrice")
	private Double houPrice;

	/** 走势图*/
	@Column(name = "tendency")
	private String tendency;

	/** 交易状态（0:正常,1:关闭）*/
	@Column(name = "tradeSign")
	private Integer tradeSign;

	/** 每日开盘时间*/
	@Column(name = "begintrade")
	private String begintrade;

	/** 每日关盘时间*/
	@Column(name = "enttrade")
	private String enttrade;

	/** 状态:（0:正常,1:禁用）*/
	@Column(name = "status")
	private Integer status;

	/** 创建时间*/
	@Column(name = "createTime")
	private String createTime;

	/** 更新时间*/
	@Column(name = "updateTime")
	
	private  String updateTime;
  
	

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return id;
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

	public void setMarketName(String marketName){
		this.marketName=marketName;
	}

	public String getMarketName(){
		return marketName;
	}

	public void setRound(Integer round){
		this.round=round;
	}

	public Integer getRound(){
		return round;
	}

	public void setNumDecimal(Integer numDecimal){
		this.numDecimal=numDecimal;
	}

	public Integer getNumDecimal(){
		return numDecimal;
	}

	public void setPriDecimal(Integer priDecimal){
		this.priDecimal=priDecimal;
	}

	public Integer getPriDecimal(){
		return priDecimal;
	}

	public void setBuyFee(Double buyFee){
		this.buyFee=buyFee;
	}

	public Double getBuyFee(){
		return buyFee;
	}

	public void setSellFee(Double sellFee){
		this.sellFee=sellFee;
	}

	public Double getSellFee(){
		return sellFee;
	}

	public void setBuyMin(Double buyMin){
		this.buyMin=buyMin;
	}

	public Double getBuyMin(){
		return buyMin;
	}

	public void setBuyMax(Double buyMax){
		this.buyMax=buyMax;
	}

	public Double getBuyMax(){
		return buyMax;
	}

	public void setSellMin(Double sellMin){
		this.sellMin=sellMin;
	}

	public Double getSellMin(){
		return sellMin;
	}

	public void setSellMax(Double sellMax){
		this.sellMax=sellMax;
	}

	public Double getSellMax(){
		return sellMax;
	}

	public void setTradeMin(Double tradeMin){
		this.tradeMin=tradeMin;
	}

	public Double getTradeMin(){
		return tradeMin;
	}

	public void setTradeMax(Double tradeMax){
		this.tradeMax=tradeMax;
	}

	public Double getTradeMax(){
		return tradeMax;
	}

	public void setLitreAstrict(String litreAstrict){
		this.litreAstrict=litreAstrict;
	}

	public String getLitreAstrict(){
		return litreAstrict;
	}

	public void setDropAstrict(String dropAstrict){
		this.dropAstrict=dropAstrict;
	}

	public String getDropAstrict(){
		return dropAstrict;
	}

	public void setHouPrice(Double houPrice){
		this.houPrice=houPrice;
	}

	public Double getHouPrice(){
		return houPrice;
	}

	public void setTendency(String tendency){
		this.tendency=tendency;
	}

	public String getTendency(){
		return tendency;
	}

	public void setTradeSign(Integer tradeSign){
		this.tradeSign=tradeSign;
	}

	public Integer getTradeSign(){
		return tradeSign;
	}

	public void setBegintrade(String begintrade){
		this.begintrade=begintrade;
	}

	public String getBegintrade(){
		return begintrade;
	}

	public void setEnttrade(String enttrade){
		this.enttrade=enttrade;
	}

	public String getEnttrade(){
		return enttrade;
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
