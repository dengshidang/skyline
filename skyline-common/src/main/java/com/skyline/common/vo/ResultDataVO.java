package com.skyline.common.vo;

/**
 * k线居于时间单位的分组
 * @author dengshidang
 *
 */
public class ResultDataVO {
	/*状态*/
	private Boolean success;
	/*数据*/
	private DataVO data;
	public ResultDataVO(){}
	
	public ResultDataVO(DataVO data,Boolean success){
		this.data=data;
		this.success=success;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public DataVO getData() {
		return data;
	}
	public void setData(DataVO data) {
		this.data = data;
	}
	
}
