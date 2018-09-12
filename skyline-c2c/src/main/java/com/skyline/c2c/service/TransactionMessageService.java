package com.skyline.c2c.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.skyline.c2c.business.TransactionMessageBusiness;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.WebSocketConstant;
import com.skyline.common.entity.TransactionMessage;
import com.skyline.common.vo.WebSocketVO;
@RestController
@RequestMapping(value="transactionMessage")
public class TransactionMessageService {
	@Autowired
	private TransactionMessageBusiness transactionMessageBusiness;
	@Autowired
	WebSocketService webSocketService;
	
	@GetMapping(value="/getMessageList")
	public Result<List<TransactionMessage>> getMessageList(Integer orderId){
      return transactionMessageBusiness.getMessageList(orderId);
	}
	
	@PostMapping(value= "/send")
	public Result<TransactionMessage> send(Integer transactionorderId,String msg){
        return transactionMessageBusiness.saveMessage( transactionorderId,msg);
	}
}
