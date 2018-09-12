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
import com.skyline.common.entity.Transactionorder;
import com.skyline.common.vo.TransactionorderVO;
import com.skyline.common.vo.WebSocketVO;


@FeignClient(value = "skyline-c2c")
public interface TransactionorderService {
	/**
	 * 
	* @Title: saveTransactionorder
	* @Description: TODO(生成法币交易订单)
	* @param @param transactionorder
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@RequestMapping(value="transactionorder/save",method=RequestMethod.POST)
 	public Result<?> save(@RequestBody Transactionorder transactionorder);
 	
 	/**
 	 * 
 	* @Title: makeMoney
 	* @Description: TODO(打款)
 	* @param @return    参数
 	* @return Result    返回类型
 	* @author xiaozhijian
 	* @throws
 	 */
	@RequestMapping(value="transactionorder/makeMoney",method=RequestMethod.POST)
 	public Result<?> makeMoney(@RequestParam("transactionorderId") Integer transactionorderId,
 			@RequestParam("imgUrl")String imgUrl,@RequestParam("payWay")Integer payWay);
 	/**
 	 * 
 	* @Title: confirmMakeMoney
 	* @Description: TODO(放行)
 	* @param @param transactionorderId
 	* @param @return    参数
 	* @return Result    返回类型
 	* @author xiaozhijian
 	* @throws
 	 */
	@RequestMapping(value="transactionorder/confirmMakeMoney",method=RequestMethod.POST)
 	public Result<?> confirmMakeMoney(@RequestParam("transactionorderId")Integer transactionorderId);
 	/**
 	 * 
 	* @Title: cancelOrder
 	* @Description: TODO(取消订单)
 	* @param @param transactionorderId
 	* @param @return    参数
 	* @return Result    返回类型
 	* @author xiaozhijian
 	* @throws
 	 */
	@RequestMapping(value="transactionorder/cancelOrder",method=RequestMethod.POST)
 	public Result<?> cancelOrder(@RequestParam("transactionorderId")Integer transactionorderId);
	
	/**
 	 * 
 	* @Title: getTransactionorder
 	* @Description: TODO(获取订单信息)
 	* @param @param transactionorderId
 	* @param @return    参数
 	* @return Result    返回类型
 	* @author xiaozhijian
 	* @throws
 	 */
	@GetMapping(value= "transactionorder/getTransactionorder")
 	public Result<TransactionorderVO> getTransactionorder(@RequestParam("transactionorderId") Integer transactionorderId);
	
	/**
 	 * 
 	* @Title: getTransactionorderList
 	* @Description: TODO(查个人c2c订单)
 	* @author xzj
 	* @param @param tansactiontypeId
 	* @param @param pageNum
 	* @param @param pageSize
 	* @param @return    参数
 	* @return Result    返回类型
 	* @throws
 	 */
	@GetMapping(value= "transactionorder/getTransactionorderList")
 	public Result<PageInfo<TransactionorderVO>> getTransactionorderList(@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize")Integer pageSize,
 			@RequestParam("status")Integer status,@RequestParam("type")Integer type,
 			@RequestParam("coinId")Integer coinId,@RequestParam("startTime")String startTime,
 			@RequestParam("endTime")String endTime);
	
 	/**
 	 * 
 	* @Title: getTransactionorderListByMerchantId
 	* @Description: TODO(商户查询广告下面的订单列表)
 	* @author xzj
 	* @param @return    参数
 	* @return Result<PageInfo<Transactionorder>>    返回类型
 	* @throws
 	 */
	@GetMapping(value= "transactionorder/getTransactionorderListByTransaction")
 	public Result<PageInfo<TransactionorderVO>> getTransactionorderListByTransaction(@RequestParam("transactionId")Integer transactionId,@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize")Integer pageSize);
 	

	/**
  	  * 
  	 * @Title: getBankinfoByUser
  	 * @Description: TODO(查询订单的可支付类型)
  	 * @author xzj
  	 * @param @return    参数
  	 * @return Result<Bankinfo>    返回类型
  	 * @throws
  	  */
	@GetMapping(value= "transactionorder/getBankinfoByOrder")
  	 public Result<List<Bankinfo>>  getBankinfoByOrder(@RequestParam("id")Integer id);
	
	/**
	 * 
	* @Title: getIdsByUserId
	* @Description: TODO(正在处理的订单id集合)
	* @author xzj
	* @param @param userId
	* @param @return    参数
	* @return List<Integer>    返回类型
	* @throws
	 */
  @GetMapping(value= "transactionorder/getIdsByUserId")
   public Result<List<WebSocketVO>> getIdsByUserId();
	
}
