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
 * 创建时间：2018-07-09 10:33:06
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_admininfo")
public class Admininfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键(自增)*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 账号*/
	@Column(name = "account")
	private String account;

	/** 密码*/
	@Column(name = "pwd")
	private String pwd;

	/** 昵称*/
	@Column(name = "nick")
	private String nick;

	/** 最后登录时间*/
	@Column(name = "loginTime")
	private String loginTime;

	/** 登录session*/
	@Column(name = "token")
	private String token;

	/** 状态:(0:正常,1:禁用)*/
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

	public void setNick(String nick){
		this.nick=nick;
	}

	public String getNick(){
		return nick;
	}

	public void setLoginTime(String loginTime){
		this.loginTime=loginTime;
	}

	public String getLoginTime(){
		return loginTime;
	}

	public void setToken(String token){
		this.token=token;
	}

	public String getToken(){
		return token;
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
