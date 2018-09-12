package com.skyline.webapi.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.TransactionMessage;

@FeignClient(value = "skyline-c2c")
public interface TransactionMessageService {
	
	/**
	 * 
	* @Title: send
	* @Description: TODO(发送c2c通讯信息)
	* @author xzj
	* @param @param message
	* @param @return    参数
	* @return Result    返回类型
	* @throws
	 */
	@PostMapping(value= "transactionMessage/send")
	public Result<TransactionMessage> send(@RequestParam("transactionorderId")Integer transactionorderId,@RequestParam("msg")String msg);
	/**
	 * 
	* @Title: getMessageList
	* @Description: TODO(获取c2c交易信息列表)
	* @author xzj
	* @param @param orderId
	* @param @return    参数
	* @return Result    返回类型
	* @throws
	 */
	@GetMapping(value="transactionMessage/getMessageList")
	public Result<List<TransactionMessage>> getMessageList(@RequestParam("orderId") Integer orderId);
}
