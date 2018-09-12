package com.skyline.webapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.entity.Transaction;
import com.skyline.common.entity.Transactiontype;
import com.skyline.webapi.annotation.NotLogin;
import com.skyline.webapi.service.TransactiontypeService;

@RestController
@RequestMapping("transactiontype")
public class TransactiontypeController {
	@Autowired
	TransactiontypeService transactiontypeService;
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
	@NotLogin
	@GetMapping(value= "/getTransactiontypeList")
	public Result<List<Transactiontype>> getTransactiontypeList(Integer type){
		if(type==null){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return transactiontypeService.getTransactiontypeList(type);
	}
}
