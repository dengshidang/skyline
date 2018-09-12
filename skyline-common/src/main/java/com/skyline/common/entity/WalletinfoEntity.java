package com.skyline.common.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 表注释： 钱包服务器信息表 <br/>
 * 类描述： 钱包服务器信息表 <br/>
 * 创建人： 工具类初始创建 <br/>
 * 创建时间：2018-06-20 13:22:42
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_walletinfo")
public class WalletinfoEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	public WalletinfoEntity() {}
	
	public WalletinfoEntity(String walletEng) {
		this.walletEng=walletEng;
	}

	/** */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 钱包IP*/
	@Column(name = "walletIp")
	private String walletIp;

	/** 钱包端口*/
	@Column(name = "walletPort")
	private Integer walletPort;

	/** 账号*/
	@Column(name = "account")
	private String account;

	/** 密码*/
	@Column(name = "pwd")
	private String pwd;
	
	/**区块高度*/
	@Column(name="blockheight")
	private Integer blockheight;
	
	/** 钱包名称*/
	@Column(name = "walletName")
	private String walletName;
	
	/**简称USDT|BTC|ETH*/
	@Column(name = "walletEng")
	private String walletEng;
	
	/**钱包加密密码*/
	@Column(name="walletPwd")
	private String walletPwd;
	
	/**代币主账号*/
	@Column(name="walletaccount")
	private String walletaccount;
	
	/**代币令牌ID*/
	@Column(name="propertyid")
	private Integer propertyid;
	
	/**确认次数*/
	@Column(name="confirm")
	private Integer confirm;
	
	/** 状态（0有效，1无效）*/
	@Column(name = "status")
	private Integer status;


	/** 创建时间*/
	@Column(name = "createTime")
	private String createTime;

	/** 更新时间*/
	@Column(name = "updateTime")
	private String updateTime;
	

	public String getWalletaccount() {
		return walletaccount;
	}

	public void setWalletaccount(String walletaccount) {
		this.walletaccount = walletaccount;
	}

	public Integer getPropertyid() {
		return propertyid;
	}

	public void setPropertyid(Integer propertyid) {
		this.propertyid = propertyid;
	}

	public String getWalletPwd() {
		return walletPwd;
	}

	public void setWalletPwd(String walletPwd) {
		this.walletPwd = walletPwd;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return id;
	}

	public void setWalletIp(String walletIp){
		this.walletIp=walletIp;
	}

	public String getWalletIp(){
		return walletIp;
	}

	public void setWalletPort(Integer walletPort){
		this.walletPort=walletPort;
	}

	public Integer getWalletPort(){
		return walletPort;
	}

	public void setAccount(String account){
		this.account=account;
	}

	public String getAccount(){
		return account;
	}

	public void setPwd(String pwd){
		this.pwd=pwd;
	}

	public String getPwd(){
		return pwd;
	}

	public void setWalletName(String walletName){
		this.walletName=walletName;
	}

	public String getWalletName(){
		return walletName;
	}
	
	public Integer getConfirm() {
		return confirm;
	}

	public void setConfirm(Integer confirm) {
		this.confirm = confirm;
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

	public String getWalletEng() {
		return walletEng;
	}

	public void setWalletEng(String walletEng) {
		this.walletEng = walletEng;
	}

	public Integer getBlockheight() {
		return blockheight;
	}

	public void setBlockheight(Integer blockheight) {
		this.blockheight = blockheight;
	}

	
}
