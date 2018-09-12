package com.skyline.common.vo;

import java.io.Serializable;

import com.skyline.common.entity.Userbalance;


public class UserbalanceVO implements Serializable {

	private static final long serialVersionUID = 1L;


	private Integer id;

	/** 用户ID*/
	private Integer userId;

	/** 币种ID*/
	private Integer coinId;
	
	private String coinName;
	/** 总金额*/
	private Double totalNum;
	/** 冻结金额*/
	private Double frozenNum;
	/** 有效金额*/
	private Double validNum;
	/**美元比例*/
	private Double usdRatio;
	/**人民币比例*/
	private Double cnyRatio;
	/**欧元比例*/
	private Double euroRatio;

	
	/** 最小转出金额*/
	private Double outMin;

	/** 最大转出金额*/
	private Double outMax;

	
	public Double getOutMin() {
		return outMin;
	}

	public void setOutMin(Double outMin) {
		this.outMin = outMin;
	}

	public Double getOutMax() {
		return outMax;
	}

	public void setOutMax(Double outMax) {
		this.outMax = outMax;
	}
	
	
	




	public UserbalanceVO() {
		super();
	}
	
	public UserbalanceVO(Userbalance userbalance,String coinName,Double usdRatio,Double cnyRatio,Double euroRatio) {
		super();
		this.id=userbalance.getId();
		this.userId=userbalance.getUserId();
		this.coinId=userbalance.getCoinId();
		this.coinName=coinName;
		this.totalNum=userbalance.getTotalNum();
		this.frozenNum=userbalance.getFrozenNum();
		this.validNum=userbalance.getValidNum();
		this.coinName=coinName;
		this.usdRatio=usdRatio;
		this.euroRatio=euroRatio;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return id;
	}

	public void setUserId(Integer userId){
		this.userId=userId;
	}

	public Integer getUserId(){
		return userId;
	}

	public void setCoinId(Integer coinId){
		this.coinId=coinId;
	}

	public Integer getCoinId(){
		return coinId;
	}

	public void setTotalNum(Double totalNum){
		this.totalNum=totalNum;
	}

	public Double getTotalNum(){
		return totalNum;
	}

	public void setFrozenNum(Double frozenNum){
		this.frozenNum=frozenNum;
	}

	public Double getFrozenNum(){
		return frozenNum;
	}

	public void setValidNum(Double validNum){
		this.validNum=validNum;
	}

	public Double getValidNum(){
		return validNum;
	}

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public Double getUsdRatio() {
		return usdRatio;
	}

	public void setUsdRatio(Double usdRatio) {
		this.usdRatio = usdRatio;
	}

	public Double getCnyRatio() {
		return cnyRatio;
	}

	public void setCnyRatio(Double cnyRatio) {
		this.cnyRatio = cnyRatio;
	}

	public Double getEuroRatio() {
		return euroRatio;
	}

	public void setEuroRatio(Double euroRatio) {
		this.euroRatio = euroRatio;
	}

}
