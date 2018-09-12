package com.skyline.common.constant;

public enum UserStatusCode {
	E00301("E00301","该用户已经注册！"),
	E00302("E00302","该用户注册失败!"),
	E00303("E00303","用户名或密码错误！"),
	E00304("E00304","token不存在，或者过期"),
	E00305("E00305","图形验认证不正确或过期"),
	E00306("E00306","手机（邮箱）验认码证不正确或过期"),
	E00307("E00307","邮箱不合法"),
	E00308("E00308","手机号不合法"),
	E00309("E00309","资金密码有误"),
	E00310("E00310","旧密码有误，更新失败"),
	E00311("E00311","找回密码失败"),
	E00312("E00312","该邮箱地址或手机号码已被注册！"),
	E00313("E00313","手机号码与用户手机号码不一致"),
	E00314("E00314","邮箱地址与用户邮箱地址不一致"),
	E00315("E00315","用户未绑定邮箱，请选择其他方式"),
	E00316("E00316","用户未绑定手机号码，请选择其他方式"),
	E00317("E00317","没有足够的权限"),
	E00318("E00318","已设定资金密码"),
	E00319("E00319","未设定资金密码"),
	E00320("E00320","修改昵称失败"),
	E00321("E00321","用户不存在或已注销"),
	E00330("E00330","设置资金密码失败"),
	E00322("E00322","验证码不能为空");
	

	private UserStatusCode(String code,String msg) {
		this.code=code;
		this.msg=msg;
	}
	private String code;	//状态码
	private String msg;		//状态说明
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
