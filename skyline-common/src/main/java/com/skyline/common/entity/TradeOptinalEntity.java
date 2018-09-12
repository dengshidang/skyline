package com.skyline.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "se_t_optionalcord")
public class TradeOptinalEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键（自增）*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 用户ID*/
	@Column(name = "userId")
	private Integer userId;

	/** 行情ID*/
	@Column(name = "marketId")
	private Integer marketId;

	/**交易币id*/
	@Column(name = "precoinId")
	private Integer precoinId;

	/** 交易币名称*/
	@Column(name = "precoinName")
	private String precoinName;
	
	/**买卖币id*/
	@Column(name = "sufcoinId")
	private Integer sufcoinId;
	
	/** 买卖币名称*/
	@Column(name = "sufcoinName")
	private String sufcoinName;

	/** 是否默认0.游客|1.用户*/
	@Column(name = "status")
	private Integer status;

	/** 创建时间*/
	@Column(name = "createTime")
	private String createTime;

	/** 更新时间*/
	@Column(name = "updateTime")
	private String updateTime;

	public Integer getPrecoinId() {
		return precoinId;
	}

	public void setPrecoinId(Integer precoinId) {
		this.precoinId = precoinId;
	}

	public Integer getSufcoinId() {
		return sufcoinId;
	}

	public void setSufcoinId(Integer sufcoinId) {
		this.sufcoinId = sufcoinId;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer  marketId) {
		this.marketId = marketId;
	}

	public String getPrecoinName() {
		return precoinName;
	}

	public void setPrecoinName(String precoinName) {
		this.precoinName = precoinName;
	}

	public String getSufcoinName() {
		return sufcoinName;
	}

	public void setSufcoinName(String sufcoinName) {
		this.sufcoinName = sufcoinName;
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

}
