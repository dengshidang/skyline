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
 * 类描述：  币种交易信息表<br/>
 * 创建人： 工具类初始创建 <br/>
 * 创建时间：2018-06-29 15:17:35
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_cointransaction")
public class CointransactionEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 订单编号*/
	@Column(name = "orderNo")
	private String orderNo;

	/** 用户ID*/
	@Column(name = "userId")
	private Integer userId;

	/** 币种名称*/
	@Column(name = "coinName")
	private String coinName;

	/** 交易ID*/
	@Column(name = "txId")
	private String txId;

	/**钱包地址*/
	@Column(name = "walletaddress")
	private String walletaddress;

	/** 金额*/
	@Column(name = "amount")
	private Double amount;

	/** 手续费*/
	@Column(name = "fee")
	private Double fee;

	/** 确认数量，值为-1表示失败*/
	@Column(name = "confirmations")
	private Integer confirmations;

	/** 充值币种ID*/
	@Column(name = "coinId")
	private Integer coinId;

	/** 交易类别，0接收|1发送*/
	@Column(name = "categroy")
	private Integer categroy;

	/** 状态:0待审核，1审核成功，2审核失败，3充值退回，4异常打款*/
	@Column(name = "status")
	private Integer status;
	
	/** 备注*/
	@Column(name = "remark")
	private String remark;

	/** 创建时间*/
	@Column(name = "createTime")
	private String createTime;

	/** 更新时间*/
	@Column(name = "updateTime")
	private String updateTime;


	public String getWalletaddress() {
		return walletaddress;
	}

	public void setWalletaddress(String walletaddress) {
		this.walletaddress = walletaddress;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return id;
	}

	public void setOrderNo(String orderNo){
		this.orderNo=orderNo;
	}

	public String getOrderNo(){
		return orderNo;
	}

	public void setUserId(Integer userId){
		this.userId=userId;
	}

	public Integer getUserId(){
		return userId;
	}

	public void setCoinName(String coinName){
		this.coinName=coinName;
	}

	public String getCoinName(){
		return coinName;
	}

	public void setTxId(String txId){
		this.txId=txId;
	}

	public String getTxId(){
		return txId;
	}

	public void setAmount(Double amount){
		this.amount=amount;
	}

	public Double getAmount(){
		return amount;
	}

	public void setFee(Double fee){
		this.fee=fee;
	}

	public Double getFee(){
		return fee;
	}

	public void setConfirmations(Integer confirmations){
		this.confirmations=confirmations;
	}

	public Integer getConfirmations(){
		return confirmations;
	}

	public void setCoinId(Integer coinId){
		this.coinId=coinId;
	}

	public Integer getCoinId(){
		return coinId;
	}

	public Integer getCategroy() {
		return categroy;
	}

	public void setCategroy(Integer categroy) {
		this.categroy = categroy;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
