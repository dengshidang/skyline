package com.skyline.webapi.from;

import javax.persistence.Column;

public class RegisterFrom {

	/** 手机号码*/
	private String phone;
	
	/** 邮箱账号*/
	private String email;

	/** 昵称*/
	private String nickName;

	/** 登入密码*/
	private String loginPwd;

	/**注册类型 0 手机 1 邮箱*/
	private Integer registerType;
	/**
	 * 图形验证码
	 */
	private String imgCode;
	/**
	 * 图形验证码id
	 */
	private String imgCodeId;
	/**
	 * 手机或邮箱验证码
	 */
	private String phoneOrEmailCode;
	
	/**
	 *  邀请码
	 */
	private String invit;
	
	
	

	public String getInvit() {
		return invit;
	}

	public void setInvit(String invit) {
		this.invit = invit;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public Integer getRegisterType() {
		return registerType;
	}

	public void setRegisterType(Integer registerType) {
		this.registerType = registerType;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	public String getImgCodeId() {
		return imgCodeId;
	}

	public void setImgCodeId(String imgCodeId) {
		this.imgCodeId = imgCodeId;
	}

	public String getPhoneOrEmailCode() {
		return phoneOrEmailCode;
	}

	public void setPhoneOrEmailCode(String phoneOrEmailCode) {
		this.phoneOrEmailCode = phoneOrEmailCode;
	}
	
	
	
}
