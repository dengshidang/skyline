package com.skyline.webapi.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.Transactionorder;
import com.skyline.common.entity.Transactionstate;


@FeignClient(value = "skyline-c2c")
public interface TransactionstateService {
	
	/**
	 * 
	* @Title: saveTransactionorder
	* @Description: TODO(生成申诉)
	* @param @param transactionorder
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@PostMapping(value= "transactionstate/save")
 	public Result<?> save(@RequestParam("orderNo")String orderNo,
 			@RequestParam("proveUrl")String proveUrl,
 			@RequestParam("content")String content);
	
	
	/**
	 * 
	* @Title: getTransactionstateByOrderNo
	* @Description: TODO(查询当前订单有效的审诉订单)
	* @author xzj
	* @param @param orderNo
	* @param @param id
	* @param @return    参数
	* @return Result<Transactionstate>    返回类型
	* @throws
	 */
	@GetMapping(value= "transactionstate/getTransactionstateByOrderNo")
	public Result<Transactionstate> getTransactionstateByOrderNo(@RequestParam("orderNo") String  orderNo);
	/**
	 * 
	* @Title: getTransactionstate
	* @Description: TODO(查询单条信息)
	* @author xzj
	* @param @param orderNo
	* @param @param id
	* @param @return    参数
	* @return Result<Transactionstate>    返回类型
	* @throws
	 */
	@GetMapping(value= "transactionstate/getTransactionstate")
	public Result<Transactionstate> getTransactionstate(@RequestParam("id")Integer id);
	
	
	/**
	 * 
	* @Title: revokeTransactionstate
	* @Description: TODO(撤消审诉)
	* @author xzj
	* @param @param id
	* @param @return    参数
	* @return Result<?>    返回类型
	* @throws
	 */
	@PostMapping(value= "transactionstate/revokeTransactionstate")
	public Result<?> revokeTransactionstate(@RequestParam("id") Integer id);
}
