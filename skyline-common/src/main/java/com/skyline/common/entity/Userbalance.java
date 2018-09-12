package com.skyline.common.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 表注释： 用户币种余额信息表 <br/>
 * 类描述： 用户币种余额信息表 <br/>
 * 创建人： 工具类初始创建 <br/>
 * 创建时间：2018-07-10 13:26:07
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_userbalance")
public class Userbalance implements Serializable {

	
	private static final long serialVersionUID = 1L;

	/** */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 用户ID*/
	@Column(name = "userId")
	private Integer userId;

	/** 币种ID*/
	@Column(name = "coinId")
	private Integer coinId;

	/** 总金额*/
	@Column(name = "totalNum")
	private Double totalNum;

	/** 冻结金额*/
	@Column(name = "frozenNum")
	private Double frozenNum;

	/** 有效金额*/
	@Column(name = "validNum")
	private Double validNum;

	/** 状态:(0:有效,1无效)*/
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

	public void setTotalNum(Double totalNum){
		this.totalNum=totalNum;
	}

	public Double getTotalNum(){
		return totalNum;
	}

	public void setFrozenNum(Double frozenNum){
		this.frozenNum=frozenNum;
	}

	public Double getFrozenNum(){
		return frozenNum;
	}

	public void setValidNum(Double validNum){
		this.validNum=validNum;
	}

	public Double getValidNum(){
		return validNum;
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
	@Override
	public String toString() {
		return "Userbalance [id=" + id + ", userId=" + userId + ", coinId=" + coinId + ", totalNum=" + totalNum
				+ ", frozenNum=" + frozenNum + ", validNum=" + validNum + ", status=" + status + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}

}
