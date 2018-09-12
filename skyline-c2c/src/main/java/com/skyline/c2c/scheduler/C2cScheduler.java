package com.skyline.c2c.scheduler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.skyline.c2c.business.TransactionorderBusiness;
import com.skyline.c2c.service.TransactionService;
@Component
public class C2cScheduler {
	private final static Logger log = LoggerFactory.getLogger(TransactionService.class); 
	
	@Resource
	TransactionorderBusiness transactionorderBusiness;
	@Scheduled(cron="0 0/1 * * * ?") //每分钟执行一次
	public void autoCancelOrder() {    
		log.info("每分钟执行一次。开始……");
		transactionorderBusiness.autoCancelOrder();
		log.info("每分钟执行一次。结束。");
	}  
	@Scheduled(cron="0 0/1 * * * ?") //每分钟执行一次
	public void autoUpdateOrderStatusStayState() {    
		log.info("每分钟执行一次。开始……");
		transactionorderBusiness.autoUpdateOrderStatusStayState();
		log.info("每分钟执行一次。结束。");
	}  
	
	@Scheduled(cron="0 0/1 * * * ?") //每分钟执行一次
	public void auotUpdateOrderStatusExceptionClose() {    
		log.info("每分钟执行一次。开始……");
		transactionorderBusiness.auotUpdateOrderStatusExceptionClose();
		log.info("每分钟执行一次。结束。");
	}  


}
