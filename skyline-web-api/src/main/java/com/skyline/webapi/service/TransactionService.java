package com.skyline.webapi.service;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.skyline.common.constant.Constants;
import com.skyline.common.constant.Result;
import com.skyline.common.entity.Bankinfo;
import com.skyline.common.entity.Transaction;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.vo.TransactionVO;

@FeignClient(value = "skyline-c2c")
public interface TransactionService {
		
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
	@RequestMapping(value= "/transaction/release", method = RequestMethod.POST)
	public Result<?> release(@RequestBody Transaction  transaction);
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
	@PostMapping(value= "/transaction/updateTransaction")
	public Result<?> updateTransaction(@RequestBody Transaction  transaction);
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
	@RequestMapping(value="transaction/cancel",method=RequestMethod.POST)
	public Result<?> cancel(@RequestParam("transactionId")Integer transactionId);
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
	@RequestMapping(value="transaction/findById",method=RequestMethod.GET)
	public Result<TransactionVO> findById(@RequestParam("id")Integer id);
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
	@RequestMapping(value="transaction/getTransactionList",method=RequestMethod.GET)
	public Result<PageInfo<TransactionVO>> getTransactionList(@RequestParam("tansactiontypeId")Integer tansactiontypeId, 
			@RequestParam("pageNum")Integer pageNum,
			@RequestParam("pageSize")Integer pageSize,@RequestParam("payWay")String  payWay,@RequestParam("money")Double money);
	
	 /**
	  * 
	 * @Title: getTransactionListByUser
	 * @Description: TODO(查询各人发布的广告)
	 * @author xzj
	 * @param @return    参数
	 * @return Result    返回类型
	 * @throws
	  */
	@GetMapping(value= "transaction/getTransactionListByUser")
	 public Result<PageInfo<TransactionVO>> getTransactionListByUser(@RequestParam("pageNum")Integer pageNum, 
			 @RequestParam("pageSize")Integer pageSize,@RequestParam("startTime")String startTime,
			 @RequestParam("endTime")String endTime,@RequestParam("coinId")Integer coinId);

	 /**
	  * 
	 * @Title: getBankinfoByUser
	 * @Description: TODO(查询广告可支付方式)
	 * @author xzj
	 * @param @return    参数
	 * @return Result<Bankinfo>    返回类型
	 * @throws
	  */
	@GetMapping(value= "transaction/getBankinfoByUser")
	 public Result<List<Bankinfo>>  getBankinfoByUser();
	
	@GetMapping(value= "transaction/getBankinfoByTransactionNo")
	 public Result<List<Bankinfo>>  getBankinfoByTransactionNo(@RequestParam("transactionNo")String transactionNo);
	
	/**
	  * 
	 * @Title: saveBankinfo
	 * @Description: TODO(添加支付方式)
	 * @author xzj
	 * @param @return    参数
	 * @return Result<?>    返回类型
	 * @throws
	  */
	@PostMapping(value= "transaction/saveBankinfo")
	 public Result<?>  saveBankinfo(@RequestBody Bankinfo bank);
}
