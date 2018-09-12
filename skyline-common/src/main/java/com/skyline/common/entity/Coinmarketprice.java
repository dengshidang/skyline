package com.skyline.common.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 表注释： 币种市价信息表 <br/>
 * 类描述： 币种市价信息表 <br/>
 * 创建人： 工具类初始创建 <br/>
 * 创建时间：2018-07-18 09:38:02
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_coinmarketprice")
public class Coinmarketprice implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Coinmarketprice() {}
	
	public Coinmarketprice(Integer coinId) {
		this.coinId=coinId;
	}

	/** */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 币种ID*/
	@Column(name = "coinId")
	private Integer coinId;

	/** 美元比例*/
	@Column(name = "usdRatio")
	private Double usdRatio;

	/** 人民币比例*/
	@Column(name = "cnyRatio")
	private Double cnyRatio;

	/** 欧元比例*/
	@Column(name = "euroRatio")
	private Double euroRatio;

	/** 状态（预留）*/
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

	public void setCoinId(Integer coinId){
		this.coinId=coinId;
	}

	public Integer getCoinId(){
		return coinId;
	}

	public void setUsdRatio(Double usdRatio){
		this.usdRatio=usdRatio;
	}

	public Double getUsdRatio(){
		return usdRatio;
	}

	public void setCnyRatio(Double cnyRatio){
		this.cnyRatio=cnyRatio;
	}

	public Double getCnyRatio(){
		return cnyRatio;
	}

	public void setEuroRatio(Double euroRatio){
		this.euroRatio=euroRatio;
	}

	public Double getEuroRatio(){
		return euroRatio;
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
