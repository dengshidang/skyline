package com.skyline.common.constant;

/**
 * 
* <p>Title: AdditionalFunctionStatusCode</p>  
* <p>Description:异常定义 </p>  
* @author kuangwenqiang  
* @date 2018年7月20日
 */
public enum AdditionalFunctionStatusCode {
	E301("E301","数据库操作异常"),
	E303("E303","该用户暂未设定交易密码，请先设置交易密码再进行绑卡!"),
	E304("E304","支付密码错误"),
	E305("E305","未查询到有效实名认证信息!"),
	E306("E306","银行卡的开户姓名和用户实名认证不一致"),
	E307("E307","未找到相应用户信息！"),
	E308("E308","绑卡类型暂时仅支持：微信、支付宝、银行卡！"),
	E309("E309","审核状态有误！"),
	E310("E310","审核不通过！没有说明原因！"),
	E311("E311","文件上传异常！"),
	E312("E312"," 邀请记录级别错误！"),
	E313("E313"," 邀请码不正确，未查询到对应用户信息！"),
	E314("E314"," 未通过实名认证的用户不能申请商户！"),
	E315("E315","未找到相应的绑卡信息！"),
	E316("E316","该用户已提交过实名认证信息！"),
	E317("E317","添加邀请记录发生错误！"),
	E318("E318","不能重复绑定微信！"),
	E319("E319","不能重复绑定支付宝！"),
	E320("E320","不能重复绑定银行卡！"),
	E321("E321","邀请码错误！"),
	E322("E322","实名认证通过的用户才能进行绑卡操作!"),
	SUCCESS("SUCCESS","成功"),
	FAILED("FAILED","失败");
	
	
	private AdditionalFunctionStatusCode(String code,String msg) {
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
