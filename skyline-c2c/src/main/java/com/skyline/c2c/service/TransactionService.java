package com.skyline.c2c.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.skyline.c2c.business.TransactionBusiness;
import com.skyline.common.constant.Result;
import com.skyline.common.entity.Bankinfo;
import com.skyline.common.entity.Transaction;
import com.skyline.common.entity.Transactiontype;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.vo.TransactionVO;

@RestController
@RequestMapping(value="transaction")
public class TransactionService {
	private final static Logger log = LoggerFactory.getLogger(TransactionService.class); 
	@Autowired
	private TransactionBusiness transactionBusiness;
	/**
	 * 
	* @Title: release
	* @Description: TODO(发布c2c交易)
	* @param @param transaction
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@PostMapping(value= "/release")
	public Result<?> release(@RequestBody Transaction  transaction){
			return transactionBusiness.release(transaction);
	}
	/**
	 * 
	* @Title: uploadTransaction
	* @Description: TODO(修改订单)
	* @author xzj
	* @param @param transaction
	* @param @return    参数
	* @return Result<?>    返回类型
	* @throws
	 */
	@PostMapping(value= "/updateTransaction")
	public Result<?> updateTransaction(@RequestBody Transaction  transaction){
			return transactionBusiness.updateTransaction(transaction);
	}
	/**
	 * 
	* @Title: cancel
	* @Description: TODO(撤销c2c交易)
	* @param @param id
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@PostMapping(value= "/cancel")
	public Result<?> cancel(Integer transactionId){
			return transactionBusiness.cancel(transactionId);
	}
	/**
	 * 
	* @Title: findById
	* @Description: TODO(查询单条c2c交易信息)
	* @param @param id
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@GetMapping(value= "/findById")
	public Result<TransactionVO> findById(Integer id){
			return transactionBusiness.findById(id);
	}
	/**
	 * 
	* @Title: getTransactiontList
	* @Description: TODO(查询c2c列表)
	* @param @param tansactiontypeId
	* @param @param pageNum
	* @param @param pageSize
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@GetMapping(value= "/getTransactionList")
	public Result<PageInfo<TransactionVO>> getTransactionList(Integer tansactiontypeId,@RequestParam(required=true,defaultValue="1") Integer pageNum,
            	@RequestParam(required=false,defaultValue="10") Integer pageSize,String  payWay,Double money){
			return transactionBusiness.getTransactionList(tansactiontypeId, pageNum, pageSize, payWay,money);
	}

	 /**
	  * 
	 * @Title: getTransactionListByUser
	 * @Description: TODO(查询各人发布的广告)
	 * @author xzj
	 * @param @return    参数
	 * @return Result    返回类型
	 * @throws
	  */
	@GetMapping(value= "/getTransactionListByUser")
	 public  Result<PageInfo<TransactionVO>> getTransactionListByUser(Integer pageNum, Integer pageSize,String startTime,String endTime,Integer coinId) {
		 return transactionBusiness.getTransactionListByUser(pageNum, pageSize, startTime, endTime, coinId);
	 }
	
	 
	
	
	
	 /**
	  * 
	 * @Title: getBankinfoByUser
	 * @Description: TODO(查询广告可支付方式)
	 * @author xzj
	 * @param @return    参数
	 * @return Result<Bankinfo>    返回类型
	 * @throws
	  */
	@GetMapping(value= "/getBankinfoByUser")
	 public Result<List<Bankinfo>>  getBankinfoByUser( ){
		 return transactionBusiness.getBankinfoByUser();
	 }
	
	@GetMapping(value= "/getBankinfoByTransactionNo")
	 public Result<List<Bankinfo>>  getBankinfoByTransactionNo(String transactionNo){
		 return transactionBusiness.getBankinfoByTransactionNo(transactionNo);
	 }
	 /**
	  * 
	 * @Title: saveBankinfo
	 * @Description: TODO(添加支付方式)
	 * @author xzj
	 * @param @return    参数
	 * @return Result<?>    返回类型
	 * @throws
	  */
	@PostMapping(value= "/saveBankinfo")
	 public Result<?>  saveBankinfo(@RequestBody Bankinfo bank){
		 return transactionBusiness.saveBankinfo(bank);
	 }
	
	
	
	
}
