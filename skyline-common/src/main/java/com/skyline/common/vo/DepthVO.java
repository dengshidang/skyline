package com.skyline.common.vo;

import java.util.List;

/**
 *深度数据，买卖对比情况
 * @author dengshidang
 * @param <T>
 *
 */
public class DepthVO<T> {	
	private List<T> asks;
	private List<T> bids;
	public List<T> getAsks() {
		return asks;
	}
	public void setAsks(List<T> asks) {
		this.asks = asks;
	}
	public List<T> getBids() {
		return bids;
	}
	public void setBids(List<T> bids) {
		this.bids = bids;
	}
	
   
}
