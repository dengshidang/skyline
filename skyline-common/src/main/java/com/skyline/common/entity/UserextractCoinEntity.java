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
 * 创建人： 用户提币地址管理<br/>
 * 创建时间：2018-07-02 14:03:40
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_userextract_coin")
public class UserextractCoinEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public UserextractCoinEntity() {}
	public UserextractCoinEntity(Integer id) {
		this.id=id;
	}

	/** 主键（自增）*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 用户ID*/
	@Column(name = "userId")
	private Integer userId;

	/** 提币地址*/
	@Column(name = "ecAddress")
	private String ecAddress;

	/** 币种类型BTC|ETH|USDT*/
	@Column(name = "coinType")
	private String coinType;

	/** 是否默认0.是|1.否*/
	@Column(name = "isdefault")
	private Integer isdefault;

	/** 币种ID*/
	@Column(name = "coinId")
	private Integer coinId;

	/** 审核状态 0.有效|1.无效*/
	@Column(name = "status")
	private Integer status;

	/** 创建时间*/
	@Column(name = "createTime")
	private String createTime;

	/** 更新时间*/
	@Column(name = "updateTime")
	private String updateTime;


	public Integer getCoinId() {
		return coinId;
	}

	public void setCoinId(Integer coinId) {
		this.coinId = coinId;
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

	public void setEcAddress(String ecAddress){
		this.ecAddress=ecAddress;
	}

	public String getEcAddress(){
		return ecAddress;
	}

	public void setCoinType(String coinType){
		this.coinType=coinType;
	}

	public String getCoinType(){
		return coinType;
	}


	public Integer getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(Integer isdefault) {
		this.isdefault = isdefault;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
