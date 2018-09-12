package com.skyline.clientService.exception;
/**
 * 币币交易异常类
 * @author dengshidang
 *
 */
public class TradeException extends RuntimeException{
	 
	private static final long serialVersionUID = -6618453229409928086L;
	private  String msg;
	private String code;
    public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setCode(String code) {
		this.code = code;
	}
	

	public TradeException(String code, String msg) {
		super();
		this.msg = msg;
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public String getCode() {
		return code;
	}
 
    
}