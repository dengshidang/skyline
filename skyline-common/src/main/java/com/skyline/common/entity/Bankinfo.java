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
 * 创建时间：2018-08-07 16:42:58
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_bankinfo")
public class Bankinfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键（自增）*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 用户ID*/
	@Column(name = "userId")
	private Integer userId;

	/** 账号*/
	@Column(name = "account")
	private String account;

	/** 名称*/
	@Column(name = "name")
	private String name;

	/** 类型（0:微信，1:支付宝，2:银行卡）*/
	@Column(name = "type")
	private Integer type;

	/** 收款二维码*/
	@Column(name = "imgUrl")
	private String imgUrl;

	/** 银行卡开户地址*/
	@Column(name = "address")
	private String address;

	/** 银行名称*/
	@Column(name = "bankName")
	private String bankName;

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

	public void setUserId(Integer userId){
		this.userId=userId;
	}

	public Integer getUserId(){
		return userId;
	}

	public void setAccount(String account){
		this.account=account;
	}

	public String getAccount(){
		return account;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}

	public void setType(Integer type){
		this.type=type;
	}

	public Integer getType(){
		return type;
	}

	public void setImgUrl(String imgUrl){
		this.imgUrl=imgUrl;
	}

	public String getImgUrl(){
		return imgUrl;
	}

	public void setAddress(String address){
		this.address=address;
	}

	public String getAddress(){
		return address;
	}

	public void setBankName(String bankName){
		this.bankName=bankName;
	}

	public String getBankName(){
		return bankName;
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
