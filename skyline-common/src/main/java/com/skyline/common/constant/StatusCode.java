package com.skyline.common.constant;


/**
 * 状态
 * @author lilin
 *
 */
public enum StatusCode {
	SUCCESS("SUCCESS","成功"),
	SYSTEMERROR("SYSTEMERROR","系统异常"),
	SAVEERROR("SAVEERROR","添加失败"),
	UPDATEERROR("UPDATEERROR","更改失败"),
	DELETEERROR("DELETEERROR","删除失败"),
	USERERROR("USERERROR","用户无权操作"),
	E00100("E00100","数据信息不存在"),
	TOKEN_NULL("403","token不能为空"),
	TOKEN_ERROR("402","token无效"),
	PARAM_NULL("401","必填参数不能为空"),
	PARAM_INVALID("412","无效参数"),
	/*币种钱包*/
	E00101("E00101","钱包服务器数据获取出现异常"),
	E00102("E00102","系统未添加钱包服务器信息"),
	E00103("E00103","系统未添加钱包区块记录信息"),
	E00104("E00104","充值信息已经存在"),
	E00105("E00105","无效的充值交易信息"),
	E00106("E00106","数据保存失败"),
	E00107("E00107","用户余额不足"),
	E00108("E00108","交易密码错误"),
	E00109("E00109","钱包地址创建失败");
	
	private StatusCode(String code,String msg) {
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
