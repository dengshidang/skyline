package com.skyline.common.constant;

public enum DepatEnum {
	DEPAT_BUY_FAIL("买方数据正在跟新，请稍后!"),
	DEPAT_SELL_FAIL("卖方数据正在更新，请稍后!");
	private String msg;

	private DepatEnum(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
