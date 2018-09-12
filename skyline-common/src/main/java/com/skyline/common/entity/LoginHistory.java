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
 * 创建时间：2018-08-02 15:32:43
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_loginHistory")
public class LoginHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键（自增）*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 用户ID*/
	@Column(name = "userId")
	private Integer userId;

	/** 登录方式 0:PC端  1:手机端  2:其他*/
	@Column(name = "loginWay")
	private Integer loginWay;

	/** 登录方式 0:PC端  1:手机端  2:其他*/
	@Column(name = "ip")
	private String ip;

	/** 0 正常  1 异常*/
	@Column(name = "status")
	private Integer status;

	/** 登录时间*/
	@Column(name = "loginTime")
	private String loginTime;


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

	public void setLoginWay(Integer loginWay){
		this.loginWay=loginWay;
	}

	public Integer getLoginWay(){
		return loginWay;
	}

	public void setIp(String ip){
		this.ip=ip;
	}

	public String getIp(){
		return ip;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return status;
	}

	public void setLoginTime(String loginTime){
		this.loginTime=loginTime;
	}

	public String getLoginTime(){
		return loginTime;
	}

}
