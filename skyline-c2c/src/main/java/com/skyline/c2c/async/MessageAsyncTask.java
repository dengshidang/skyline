package com.skyline.c2c.async;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.skyline.c2c.business.TransactionMessageBusiness;
import com.skyline.c2c.service.TransactionMessageService;
import com.skyline.c2c.service.WebSocketService;
import com.skyline.common.entity.TransactionMessage;
import com.skyline.common.vo.WebSocketVO;

@Component
public class MessageAsyncTask {
		
		@Autowired
		TransactionMessageBusiness transactionMessageBusiness;
	
		@Autowired
		WebSocketService webSocketService;
		
		@Async
		public void send(TransactionMessage message,Integer  type){
			transactionMessageBusiness.saveMessage(message, type);
		}
}
