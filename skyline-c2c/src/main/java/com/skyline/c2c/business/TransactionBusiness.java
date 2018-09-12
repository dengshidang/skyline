package com.skyline.c2c.business;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.skyline.common.business.BaseBusiness;
import com.skyline.common.constant.Result;
import com.skyline.common.entity.Bankinfo;
import com.skyline.common.entity.Transaction;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.vo.TransactionVO;

public interface TransactionBusiness extends BaseBusiness<Transaction, Integer>{
	/**
	 * 
	* @Title: release
	* @Description: TODO(发布c2c交易)
	* @param     参数
	* @return void    返回类型
	* @author xiaozhijian
	* @throws
	 */
	public Result<?> release(Transaction  transaction);
	/**
	 * 
	* @Title: updateTransaction
	* @Description: TODO(更改c2c交易)
	* @author xzj
	* @param @param transaction
	* @param @return    参数
	* @return Result<?>    返回类型
	* @throws
	 */
	public Result<?>  updateTransaction(Transaction  transaction);
	
	/**
	 * 
	* @Title: cancel
	* @Description: TODO(撤消c2c交易)
	* @param @param transaction
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	public Result<?> cancel(Integer id);
	
	/**
	 * 
	* @Title: findById
	* @Description: TODO(查询单条数据)
	* @param @param id
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	public Result<TransactionVO> findById(Integer id);
	
	/**
	 * 
	* @Title: getList
	* @Description: TODO(查询 广告（所有人）)
	* @param @param tansactiontypeId
	* @param @param pageNum 当前页
	* @param @param pageSize 一页多少条
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	 public Result<PageInfo<TransactionVO>> getTransactionList(Integer tansactiontypeId,Integer pageNum,Integer pageSize,String  payWay,Double money);
	 
	 /**
	  * 
	 * @Title: getTransactionListByUser
	 * @Description: TODO(查询各人发布的广告)
	 * @author xzj
	 * @param @return    参数
	 * @return Result    返回类型
	 * @throws
	  */
	 public Result<PageInfo<TransactionVO>> getTransactionListByUser(Integer pageNum, Integer pageSize,String startTime,String endTime,Integer coinId);
	 
	
	 /**
	  * 
	 * @Title: getBankinfoByUser
	 * @Description: TODO(查询广告可支付方式)
	 * @author xzj
	 * @param @return    参数
	 * @return Result<Bankinfo>    返回类型
	 * @throws
	  */
	 public Result<List<Bankinfo>>  getBankinfoByUser();
	 /**
	  * 
	 * @Title: getBankinfoByTransactionNo
	 * @Description: TODO(广告查询支付方式)
	 * @author xzj
	 * @param @param transactionNo
	 * @param @return    参数
	 * @return Result<List<Bankinfo>>    返回类型
	 * @throws
	  */
	 public Result<List<Bankinfo>>  getBankinfoByTransactionNo(String transactionNo);
	 /**
	  * 
	 * @Title: saveBankinfo
	 * @Description: TODO(添加支付方式)
	 * @author xzj
	 * @param @return    参数
	 * @return Result<?>    返回类型
	 * @throws
	  */
	 public Result<?>  saveBankinfo(Bankinfo bank);

	 
	

}
