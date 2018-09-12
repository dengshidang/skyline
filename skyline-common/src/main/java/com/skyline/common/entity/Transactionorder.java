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
 * 创建时间：2018-07-10 15:06:50
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_transactionorder")
public class Transactionorder implements Serializable {

	private static final long serialVersionUID = 1L;

	/** */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 交易订单号*/
	@Column(name = "orderNo")
	private String orderNo;

	/** 交易流水号*/
	@Column(name = "transactionNo")
	private String transactionNo;

	/** 商户ID*/
	@Column(name = "merchantId")
	private Integer merchantId;

	/** 用户ID*/
	@Column(name = "userId")
	private Integer userId;

	/** 数量*/
	@Column(name = "number")
	private Double number;

	/**单价*/
	@Column(name = "price")
	private Double price;
	
	/** 总价*/
	@Column(name = "totalPrice")
	private Double totalPrice;

	/** 支付方式*/
	@Column(name = "payWay")
	private Integer payWay;

	/** 备注码*/
	@Column(name = "remarkCode")
	private String remarkCode;

	/** 状态:(0:待支付,1:已支付,2:完成,3:撤销,4:自动取消,5:申述中,6:处理完成)*/
	@Column(name = "status")
	private Integer status;

	/** 创建时间*/
	@Column(name = "createTime")
	private String createTime;

	/** 更新时间*/
	@Column(name = "updateTime")
	private String updateTime;
	
	/**已付款时间*/
	@Column(name = "makeTime")
	private String makeTime;
	
	/**付款图片url*/
	@Column(name = "imgUrl")
	private String imgUrl;
	
	
	/** 是否可以申诉*/
	@Column(name = "isAppeal")
	private Integer isAppeal;


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

	public void setTransactionNo(String transactionNo){
		this.transactionNo=transactionNo;
	}

	public String getTransactionNo(){
		return transactionNo;
	}

	public void setMerchantId(Integer merchantId){
		this.merchantId=merchantId;
	}

	public Integer getMerchantId(){
		return merchantId;
	}

	public void setUserId(Integer userId){
		this.userId=userId;
	}

	public Integer getUserId(){
		return userId;
	}

	public void setNumber(Double number){
		this.number=number;
	}

	public Double getNumber(){
		return number;
	}

	public void setTotalPrice(Double totalPrice){
		this.totalPrice=totalPrice;
	}

	public Double getTotalPrice(){
		return totalPrice;
	}



	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public void setRemarkCode(String remarkCode){
		this.remarkCode=remarkCode;
	}

	public String getRemarkCode(){
		return remarkCode;
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

	public String getMakeTime() {
		return makeTime;
	}

	public void setMakeTime(String makeTime) {
		this.makeTime = makeTime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getIsAppeal() {
		return isAppeal;
	}

	public void setIsAppeal(Integer isAppeal) {
		this.isAppeal = isAppeal;
	}
	
	
	
	
}
