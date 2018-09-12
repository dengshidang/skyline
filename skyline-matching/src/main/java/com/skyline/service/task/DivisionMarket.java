package com.skyline.service.task;

import java.util.List;

import com.skyline.common.vo.MessageEntity;

public class DivisionMarket implements Runnable{
   private Integer marketId;
   
   List<MessageEntity> buys;
   
   List<MessageEntity> sells;
   
	public DivisionMarket(Integer marketId) {
		this.marketId = marketId;
	}
	
	@Override
	public void run() {
	   
	}
	

}
