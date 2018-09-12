package com.skyline.common.vo;

import java.util.ArrayList;
import java.util.List;
/**
 * 行情委托信息列表
 * @author dengshidang
 *
 */
public class MessageList {
	//委托买入列表
	private List<MessageEntity> buyList;
	//委托卖出列表
	private List<MessageEntity> sellList;
	
	public List<MessageEntity> getBuyList() {
		return buyList;
	}
	public void setBuyList(List<MessageEntity> buyList) {
		this.buyList = buyList;
	}
	public List<MessageEntity> getSellList() {
		return sellList;
	}
	public void setSellList(List<MessageEntity> sellList) {
		this.sellList = sellList;
	}


}
