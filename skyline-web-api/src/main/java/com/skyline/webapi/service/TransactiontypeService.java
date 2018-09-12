package com.skyline.webapi.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.Transactiontype;

@FeignClient(value = "skyline-c2c")
public interface TransactiontypeService {
	/**
	 * 
	* @Title: getTransactiontypeList
	* @Description: TODO(查询c2c交易类型)
	* @param @param type
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@RequestMapping(value="transactiontype/getTransactiontypeList",method=RequestMethod.GET)
	public Result<List<Transactiontype>> getTransactiontypeList(@RequestParam("type")Integer type);
}
