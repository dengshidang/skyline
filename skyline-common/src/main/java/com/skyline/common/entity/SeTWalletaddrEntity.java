package com.skyline.common.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 表注释： 钱包地址信息表 <br/>
 * 类描述： 钱包地址信息表 <br/>
 * 创建人： 工具类初始创建 <br/>
 * 创建时间：2018-06-20 13:24:51
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_walletaddr")
public class SeTWalletaddrEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 钱包地址*/
	@Column(name = "address")
	private String address;

	/** 钱包公钥或私钥*/
	@Column(name = "addresskey")
	private String addresskey;

	/** 钱包名称*/
	@Column(name = "walletName")
	private String walletName;

	/** 钱包ID*/
	@Column(name = "walletId")
	private Integer walletId;

	/** 用户ID*/
	@Column(name = "userId")
	private Integer userId;

	/** 币种ID*/
	@Column(name = "coinId")
	private Integer coinId;

	/** 币种名称*/
	@Column(name = "coinName")
	private String coinName;

	/** 状态（0有效，1无效）*/
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

	public void setAddress(String address){
		this.address=address;
	}

	public String getAddress(){
		return address;
	}

	
	public String getAddresskey() {
		return addresskey;
	}

	public void setAddresskey(String addresskey) {
		this.addresskey = addresskey;
	}

	public void setWalletName(String walletName){
		this.walletName=walletName;
	}

	public String getWalletName(){
		return walletName;
	}

	public void setWalletId(Integer walletId){
		this.walletId=walletId;
	}

	public Integer getWalletId(){
		return walletId;
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

	public void setCoinName(String coinName){
		this.coinName=coinName;
	}

	public String getCoinName(){
		return coinName;
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
