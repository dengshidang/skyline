package com.skyline.clientService.kline;

import java.util.List;

/**
 * k线数据情况
 * @author dengshidang
 *
 */
public enum KlineStatus {	
	KLINE_SEARCH_FAIL("数据更新中，请稍后"),
	KLINE_PARAM_INVALID("无效的行情ID"),
	DEPAT_BUY_FAIL("买方数据正在跟新，请稍后!"),
	DEPAT_SELL_FAIL("卖方数据正在更新，请稍后!");
	private KlineStatus(String msg) {
		this.msg = msg;
	}




	public String getMsg() {
		return msg;
	}




	public void setMsg(String msg) {
		this.msg = msg;
	}




	private String msg;
	
 
	
	
}
