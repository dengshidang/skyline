package com.skyline.c2c.business;

import java.util.List;

import com.skyline.common.business.BaseBusiness;
import com.skyline.common.constant.Result;
import com.skyline.common.entity.Transactiontype;

public interface TransactiontypeBusiness extends BaseBusiness<Transactiontype, Integer>{
	/**
	 * 
	* @Title: saveTransactiontype
	* @Description: TODO(新增c2c交易类型)
	* @param @param transactiontype
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	public Result<?> saveTransactiontype(Transactiontype transactiontype);
	/**
	 * 
	* @Title: updateTransactiontypeStatus
	* @Description: TODO(更改状态，启用，禁用)
	* @param @param status
	* @param @param transactiontypeId
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	public Result<?> updateTransactiontypeStatus(Integer status,Integer transactiontypeId);
	/**
	 * 
	* @Title: updateTransactiontype
	* @Description: TODO(更改基本信息)
	* @param @param transactiontype
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	public Result<?> updateTransactiontype(Transactiontype transactiontype);
	/**
	 * 
	* @Title: deleteTransactiontype
	* @Description: TODO(删除)
	* @param @param transactiontypeId
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	public Result<?> deleteTransactiontype(Integer transactiontypeId);
	
	 /**
	  * 
	 * @Title: getTransactiontypeList
	 * @Description: TODO(获取c2c交易类型)
	 * @param @param type
	 * @param @return    参数
	 * @return Result    返回类型
	 * @author xiaozhijian
	 * @throws
	  */
	 public Result<List<Transactiontype>> getTransactiontypeList(Integer type);
}
