package com.skyline.c2c.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.TransactionMessage;
import com.skyline.common.vo.WebSocketVO;

@FeignClient(value = "skyline-websocket")
public interface WebSocketService {
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
	@PostMapping(value= "/send")
	public Result<?> send(@RequestBody WebSocketVO webSocketVO);

}
