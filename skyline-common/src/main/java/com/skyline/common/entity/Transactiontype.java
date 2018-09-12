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
 * 创建时间：2018-07-05 11:10:51
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_transactiontype")
public class Transactiontype implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 法币交易类型*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 交易币种id*/
	@Column(name = "coinId")
	private Integer coinId;

	/** 交易币种名称*/
	@Column(name = "coinName")
	private String coinName;

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

	/** 类型（0买入 1卖出）*/
	@Column(name = "type")
	private Integer type;

	/** 状态（0：启用  1：禁用 ）*/
	@Column(name = "status")
	private Integer status;

	/** 创建时间*/
	@Column(name = "createTime")
	private String createTime;

	/** */
	@Column(name = "updateTime")
	private String updateTime;


	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return id;
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

	public void setType(Integer type){
		this.type=type;
	}

	public Integer getType(){
		return type;
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
