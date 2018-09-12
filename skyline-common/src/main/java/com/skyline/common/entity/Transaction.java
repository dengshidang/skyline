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
 * 创建时间：2018-07-05 13:31:00
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_transaction")
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 法币交易信息表*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 交易流水号*/
	@Column(name = "transactionNo")
	private String transactionNo;

	/** 商户id*/
	@Column(name = "merchantId")
	private Integer merchantId;

	/** 交易币种id*/
	@Column(name = "coinId")
	private Integer coinId;

	/** 交易币种名称*/
	@Column(name = "coinName")
	private String coinName;

	/** 最小交易数*/
	@Column(name = "min")
	private Double min;

	/** 最大交易数*/
	@Column(name = "max")
	private Double max;

	/** 价格*/
	@Column(name = "price")
	private Double price;

	/** 总数量*/
	@Column(name = "totalNum")
	private Double totalNum;

	/** 成交数量*/
	@Column(name = "finishNum")
	private Double finishNum;

	/** 剩余数量*/
	@Column(name = "surplusNum")
	private Double surplusNum;

	/** 支持支付方式 */
	@Column(name = "payWay")
	private String payWay;

	/** 状态 1 挂起 2完成 3 撤销 */
	@Column(name = "status")
	private Integer status;

	/** */
	@Column(name = "createTime")
	private String createTime;

	/** */
	@Column(name = "updateTime")
	private String updateTime;
	
	/** 类型（0买入 1卖出）*/
	@Column(name="transactiontypeId")
	private Integer transactiontypeId;
	/**备注*/
	@Column(name="remark")
	private String remark;


	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return id;
	}
	


	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public void setMerchantId(Integer merchantId){
		this.merchantId=merchantId;
	}

	public Integer getMerchantId(){
		return merchantId;
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

	public void setMin(Double min){
		this.min=min;
	}

	public Double getMin(){
		return min;
	}

	public void setMax(Double max){
		this.max=max;
	}

	public Double getMax(){
		return max;
	}

	public void setPrice(Double price){
		this.price=price;
	}

	public Double getPrice(){
		return price;
	}

	public void setTotalNum(Double totalNum){
		this.totalNum=totalNum;
	}

	public Double getTotalNum(){
		return totalNum;
	}

	public void setFinishNum(Double finishNum){
		this.finishNum=finishNum;
	}

	public Double getFinishNum(){
		return finishNum;
	}

	public void setSurplusNum(Double surplusNum){
		this.surplusNum=surplusNum;
	}

	public Double getSurplusNum(){
		return surplusNum;
	}

	public void setPayWay(String payWay){
		this.payWay=payWay;
	}

	public String getPayWay(){
		return payWay;
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

	public Integer getTransactiontypeId() {
		return transactiontypeId;
	}

	public void setTransactiontypeId(Integer transactiontypeId) {
		this.transactiontypeId = transactiontypeId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	


}
