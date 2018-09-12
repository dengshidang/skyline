package com.skyline.webapi.from;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;


public class TransactionFrom implements Serializable {

	private static final long serialVersionUID = 1L;
	/** id*/
	private Integer id;
	
	/** 最小交易数*/
	private Double min;

	/** 最大交易数*/
	private Double max;

	/** 价格*/
	private Double price;

	/** 总数量*/
	private Double totalNum;


	/** 支持支付方式 */
	private String payWay;
	
	/** 交易类型id */
	private Integer transactiontypeId;
	
	/**备注*/
	private String remark;
	
	

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Double totalNum) {
		this.totalNum = totalNum;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public Integer getTransactiontypeId() {
		return transactiontypeId;
	}

	public void setTransactiontypeId(Integer transactiontypeId) {
		this.transactiontypeId = transactiontypeId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	


}
