package com.skyline.c2c.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skyline.c2c.business.TransactionBusiness;
import com.skyline.c2c.business.TransactiontypeBusiness;
import com.skyline.common.constant.Result;
import com.skyline.common.entity.Transaction;
import com.skyline.common.entity.Transactiontype;

@RestController
@RequestMapping(value="transactiontype")
public class TransactiontypeService {
	private final static Logger log = LoggerFactory.getLogger(TransactiontypeService.class); 
	@Autowired
	private TransactiontypeBusiness transactiontypeBusiness;
	
	/**
	 * 
	* @Title: save
	* @Description: TODO(保存信息)
	* @param @param transactiontype
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@RequestMapping(value= "/save",method=RequestMethod.POST)
	public Result<?> save(Transactiontype transactiontype){
			return transactiontypeBusiness.saveTransactiontype(transactiontype);
	}
	/**
	 * 
	* @Title: updateStatus
	* @Description: TODO(更改状态（启用，禁用）)
	* @param @param transactiontypeId
	* @param @param status
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@RequestMapping(value= "/updateStatus",method=RequestMethod.POST)
	public Result<?> updateStatus(Integer transactiontypeId,Integer status){
			return transactiontypeBusiness.updateTransactiontypeStatus(status, transactiontypeId);
	}
	/**
	 * 
	* @Title: update
	* @Description: TODO(更改信息)
	* @param @param transactiontype
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@RequestMapping(value= "/update",method=RequestMethod.POST)
	public Result<?> update(Transactiontype transactiontype){
			return transactiontypeBusiness.updateTransactiontype(transactiontype);
	}
	/**
	 * 
	* @Title: delete
	* @Description: TODO(删除信息)
	* @param @param transactiontypeId
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@RequestMapping(value= "/delete",method=RequestMethod.GET)
	public Result<?> delete(Integer transactiontypeId){
			return transactiontypeBusiness.deleteTransactiontype(transactiontypeId);
	}
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
	@GetMapping(value= "/getTransactiontypeList")
	public Result<List<Transactiontype>> getTransactiontypeList(Integer type){
			return transactiontypeBusiness.getTransactiontypeList(type);
	}
	
	
	
}
