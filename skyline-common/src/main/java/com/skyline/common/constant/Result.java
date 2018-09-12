package com.skyline.common.constant;

/**
 * 结果
 * @author lilin
 *
 */
public class Result<T>{
	private T result;	//结果
	private String code;	//状态码
	private String msg;		//状态说明
	
	
	public static<T> Result<T> successResult(){
		return new Result<T>(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMsg());
	}
	public static<T> Result<T> successResult(T result){
		return new Result<T>(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMsg(),result);
	}
	
	public static<T> Result<T> successResult(String code,String msg){
		return new Result<T>(code, msg);
	}
	public static<T> Result<T> systemErrorResult(){
		return new Result<T>(StatusCode.SYSTEMERROR.getCode(),StatusCode.SYSTEMERROR.getMsg());
	}
	public static<T> Result<T> saveErrorResult(){
		return new Result<T>(StatusCode.SAVEERROR.getCode(),StatusCode.SAVEERROR.getMsg());
	}
	public static<T> Result<T> updateErrorResult(){
		return new Result<T>(StatusCode.UPDATEERROR.getCode(),StatusCode.UPDATEERROR.getMsg());
	}
	public static<T> Result<T> deleteErrorResult(){
		return new Result<T>(StatusCode.DELETEERROR.getCode(), StatusCode.DELETEERROR.getMsg());
	}
	public static<T> Result<T> errorResult(String code, String msg){
		return new Result<T>( code, msg);
	}
	public static<T> Result<T> errorResult(String code, String msg,T result){
		return new Result<T>( code, msg,result);
	}
	public static<T> Result<T> errorResult(C2cStatusCode c2cStatusCode){
		return new Result<T>( c2cStatusCode.getCode(), c2cStatusCode.getMsg());
	}
	public static<T> Result<T> errorResult(StatusCode statusCode){
		return new Result<T>( statusCode.getCode(), statusCode.getMsg());
	}
	public static<T> Result<T> errorResult(C2cStatusCode c2cStatusCode,T result){
		return new Result<T>(c2cStatusCode.getCode(), c2cStatusCode.getMsg(),result);
	}
	
	
	
	public T getResult() {
		return result;
	}
	public Result() {}
	
	public Result( String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public Result(String code, String msg,T result) {
		super();
		this.result = result;
		this.code = code;
		this.msg = msg;
	}

	public void setResult(T result) {
		this.result = result;
	}
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
