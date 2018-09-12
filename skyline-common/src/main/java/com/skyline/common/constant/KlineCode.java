package com.skyline.common.constant;

public enum KlineCode {
	SUCCESS("SUCCESS","成功!"),
	SEARCH_FAIL("FAIL","后台数据维护中");
	private KlineCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	private String code;
	private String msg;
	public String getCode(){return this.code;}
	public String getMsg(){return this.msg;}
	public void setCode(String code){this.code=code;}
	public void setMsg(String msg){this.msg=msg;}

}
