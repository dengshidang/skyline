package com.skyline.c2c.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.skyline.c2c.business.TransactionorderBusiness;
import com.skyline.c2c.filter.RequestUtil;
import com.skyline.common.constant.Result;
import com.skyline.common.entity.Bankinfo;
import com.skyline.common.entity.Transactionorder;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.vo.TransactionorderVO;
import com.skyline.common.vo.WebSocketVO;

@RestController
@RequestMapping(value="transactionorder")
public class TransactionorderService {
	private final static Logger log = LoggerFactory.getLogger(TransactionorderService.class); 
	@Autowired
	private TransactionorderBusiness transactionorderBusiness;
	
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
	@PostMapping(value= "/save")
 	public Result<?> save(@RequestBody Transactionorder transactionorder ){
 		return transactionorderBusiness.saveTransactionorder(transactionorder);
 	}
 	
 	/**
 	 * 
 	* @Title: makeMoney
 	* @Description: TODO(打款)
 	* @param @return    参数
 	* @return Result    返回类型
 	* @author xiaozhijian
 	* @throws
 	 */
	@PostMapping(value= "/makeMoney")
 	public Result<?> makeMoney(Integer transactionorderId,String imgUrl,Integer payWay){
 		return transactionorderBusiness.makeMoney(transactionorderId, imgUrl,payWay);
 	}
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
	@PostMapping(value= "/confirmMakeMoney")
 	public Result<?> confirmMakeMoney(Integer transactionorderId){
 		return transactionorderBusiness.confirmMakeMoney(transactionorderId);
 	}
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
	@PostMapping(value= "/cancelOrder")
 	public Result<?> cancelOrder(Integer transactionorderId){
 		return transactionorderBusiness.cancelOrder(transactionorderId);
 	}
	
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
	@GetMapping(value= "/getTransactionorder")
 	public Result<TransactionorderVO> getTransactionorder(Integer transactionorderId){
		return transactionorderBusiness.getTransactionorder(transactionorderId);
 	}
	
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
	@GetMapping(value= "/getTransactionorderList")
 	public Result<PageInfo<TransactionorderVO>> getTransactionorderList(Integer pageNum,Integer pageSize,
 			Integer status,Integer type,Integer coinId,String startTime,String endTime) {
 		return transactionorderBusiness.getTransactionorderList(pageNum, pageSize, status, type, coinId, startTime, endTime);
 	}
 	/**
 	 * 
 	* @Title: getTransactionorderListByMerchantId
 	* @Description: TODO(商户查询广告下面的订单列表)
 	* @author xzj
 	* @param @return    参数
 	* @return Result<PageInfo<Transactionorder>>    返回类型
 	* @throws
 	 */
	@GetMapping(value= "/getTransactionorderListByTransaction")
 	public Result<PageInfo<TransactionorderVO>> getTransactionorderListByTransaction(Integer transactionId,Integer pageNum,Integer pageSize){
 		return transactionorderBusiness.getTransactionorderListByTransaction(transactionId, pageNum, pageSize);
 	}
 	
	
	/**
  	  * 
  	 * @Title: getBankinfoByUser
  	 * @Description: TODO(查询订单的可支付类型)
  	 * @author xzj
  	 * @param @return    参数
  	 * @return Result<Bankinfo>    返回类型
  	 * @throws
  	  */
	@GetMapping(value= "/getBankinfoByOrder")
  	 public Result<List<Bankinfo>>  getBankinfoByOrder(Integer id){
  		 return transactionorderBusiness.getBankinfoByOrder(id);
  	 }
	
	
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
  @GetMapping(value= "/getIdsByUserId")
   public Result<List<WebSocketVO>> getIdsByUserId(){
	  return  transactionorderBusiness.getIdsByUserId();
   }
}
