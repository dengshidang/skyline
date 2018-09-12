package com.skyline.common.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 表注释： 用户信息表 <br/>
 * 类描述： 用户信息表 <br/>
 * 创建人： 工具类初始创建 <br/>
 * 创建时间：2018-07-10 11:06:53
 * 
 * @updateRemark 修改备注：
 */
@Entity
@Table(name = "se_t_userinfo")
public class Userinfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/** 手机号码*/
	@Column(name = "phone")
	private String phone;

	/** 邮箱账号*/
	@Column(name = "email")
	private String email;

	/** 昵称*/
	@Column(name = "nickName")
	private String nickName;

	/** 头像URL*/
	@Column(name = "portrait")
	private String portrait;

	/** 性别(0:男 1:女)*/
	@Column(name = "sex")
	private Integer sex;

	/** 登入密码*/
	@Column(name = "loginPwd")
	private String loginPwd;

	/** 交易密码*/
	@Column(name = "payPwd")
	private String payPwd;

	/** 记住密码(0:不 1:记)*/
	@Column(name = "tingPay")
	private Integer tingPay;

	/** 实名认证标记(0:未认证 1:已认证)*/
	@Column(name = "idSign")
	private Integer idSign;

	/** 商户认证标记(0:未认证 1:已认证)*/
	@Column(name = "mctSign")
	private Integer mctSign;

	/** 交易市场是否收取手术费（0：收取1：不收）*/
	@Column(name = "charge")
	private Integer charge;

	/** 专属邀请码*/
	@Column(name = "invit")
	private String invit;

	/** 登录标识*/
	@Column(name = "loginSession")
	private String loginSession;

	/** 等级*/
	@Column(name = "cla")
	private Integer cla;

	/** 国籍*/
	@Column(name = "nationality")
	private String nationality;

	/** 状态:0正常，1冻结*/
	@Column(name = "status")
	private Integer status;

	/** 最后登入时间*/
	@Column(name = "logTime")
	private String logTime;

	/** 创建时间*/
	@Column(name = "createTime")
	private String createTime;

	/** 更新时间*/
	@Column(name = "updateTime")
	private String updateTime;

	/** 是否删除 0 正常 1 删除*/
	@Column(name = "deleteSign")
	private Integer deleteSign;
	/**注册类型 0 手机 1 邮箱*/
	@Column(name = "registerType")
	private Integer registerType;
	
	/**isLogin*/
	@Column(name = "isLogin")
	private Integer isLogin;
	

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return id;
	}

	public void setPhone(String phone){
		this.phone=phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setEmail(String email){
		this.email=email;
	}

	public String getEmail(){
		return email;
	}

	public void setNickName(String nickName){
		this.nickName=nickName;
	}

	public String getNickName(){
		return nickName;
	}

	public void setPortrait(String portrait){
		this.portrait=portrait;
	}

	public String getPortrait(){
		return portrait;
	}

	public void setSex(Integer sex){
		this.sex=sex;
	}

	public Integer getSex(){
		return sex;
	}

	public void setLoginPwd(String loginPwd){
		this.loginPwd=loginPwd;
	}

	public String getLoginPwd(){
		return loginPwd;
	}

	public void setPayPwd(String payPwd){
		this.payPwd=payPwd;
	}

	public String getPayPwd(){
		return payPwd;
	}

	public void setTingPay(Integer tingPay){
		this.tingPay=tingPay;
	}

	public Integer getTingPay(){
		return tingPay;
	}

	public void setIdSign(Integer idSign){
		this.idSign=idSign;
	}

	public Integer getIdSign(){
		return idSign;
	}

	public void setMctSign(Integer mctSign){
		this.mctSign=mctSign;
	}

	public Integer getMctSign(){
		return mctSign;
	}

	public void setCharge(Integer charge){
		this.charge=charge;
	}

	public Integer getCharge(){
		return charge;
	}

	public void setInvit(String invit){
		this.invit=invit;
	}

	public String getInvit(){
		return invit;
	}

	public void setLoginSession(String loginSession){
		this.loginSession=loginSession;
	}

	public String getLoginSession(){
		return loginSession;
	}

	public void setCla(Integer cla){
		this.cla=cla;
	}

	public Integer getCla(){
		return cla;
	}

	public void setNationality(String nationality){
		this.nationality=nationality;
	}

	public String getNationality(){
		return nationality;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return status;
	}

	public void setLogTime(String logTime){
		this.logTime=logTime;
	}

	public String getLogTime(){
		return logTime;
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

	public Integer getDeleteSign() {
		return deleteSign;
	}

	public void setDeleteSign(Integer deleteSign) {
		this.deleteSign = deleteSign;
	}

	public Integer getRegisterType() {
		return registerType;
	}

	public void setRegisterType(Integer registerType) {
		this.registerType = registerType;
	}

	public Integer getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(Integer isLogin) {
		this.isLogin = isLogin;
	}
	

}
