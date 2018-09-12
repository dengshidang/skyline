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
 * 创建时间：2018-08-08 16:15:35
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_useridentitie")
public class Useridentitie implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键（自增）*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 用户ID*/
	@Column(name = "userId")
	private Integer userId;

	/** 1 身份证 2 其他证件*/
	@Column(name = "type")
	private Integer type;

	/** 证件号码*/
	@Column(name = "idCard")
	private String idCard;

	/** 真实姓名*/
	@Column(name = "name")
	private String name;

	/** 身份证证件照（正）*/
	@Column(name = "IdcardImg1")
	private String IdcardImg1;

	/** 身份证证件照（反）*/
	@Column(name = "IdcardImg2")
	private String IdcardImg2;

	/** 身份证证件照（人）*/
	@Column(name = "IdcardImg3")
	private String IdcardImg3;

	/** 状态（0:未审核，1:通过，2:拒绝）*/
	@Column(name = "status")
	private Integer status;

	/** 描述*/
	@Column(name = "remark")
	private String remark;

	/** 创建时间*/
	@Column(name = "createTime")
	private String createTime;

	/** 更新时间*/
	@Column(name = "updateTime")
	private String updateTime;

	/** 标记 0失效 1有效*/
	@Column(name = "sign")
	private Integer sign;


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

	public void setType(Integer type){
		this.type=type;
	}

	public Integer getType(){
		return type;
	}

	public void setIdCard(String idCard){
		this.idCard=idCard;
	}

	public String getIdCard(){
		return idCard;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}

	public void setIdcardImg1(String IdcardImg1){
		this.IdcardImg1=IdcardImg1;
	}

	public String getIdcardImg1(){
		return IdcardImg1;
	}

	public void setIdcardImg2(String IdcardImg2){
		this.IdcardImg2=IdcardImg2;
	}

	public String getIdcardImg2(){
		return IdcardImg2;
	}

	public void setIdcardImg3(String IdcardImg3){
		this.IdcardImg3=IdcardImg3;
	}

	public String getIdcardImg3(){
		return IdcardImg3;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return status;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}

	public String getRemark(){
		return remark;
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

	public void setSign(Integer sign){
		this.sign=sign;
	}

	public Integer getSign(){
		return sign;
	}

}
