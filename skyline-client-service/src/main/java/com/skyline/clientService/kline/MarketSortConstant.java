package com.skyline.clientService.kline;

public enum MarketSortConstant {

	SUFCOINNAME            (1,"sufcoinName"),
	NEWPRICE               (2,"newPrice"),
	GAINS                  (3,"gains");
	
	private MarketSortConstant(Integer type, String sortName) {
		this.type = type;
		this.sortName = sortName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	private Integer type;
	
	private String sortName;

}
