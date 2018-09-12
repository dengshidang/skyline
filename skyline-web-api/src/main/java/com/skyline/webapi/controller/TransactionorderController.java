package com.skyline.webapi.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.skyline.common.constant.TransactionMessageConstant;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.entity.Bankinfo;
import com.skyline.common.entity.TransactionMessage;
import com.skyline.common.entity.Transactionorder;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.util.ObjectTool;
import com.skyline.common.vo.TransactionorderVO;
import com.skyline.common.vo.WebSocketVO;
import com.skyline.webapi.filter.RequestUtil;
import com.skyline.webapi.service.TransactionMessageService;
import com.skyline.webapi.service.TransactionorderService;
import com.skyline.webapi.service.UserinfoService;

@RestController
@RequestMapping("transactionorder")
public class TransactionorderController {
	@Autowired
	TransactionorderService  transactionorderService;
	@Autowired
	UserinfoService  userinfoService;
	@Autowired
	TransactionMessageService  transactionMessageService;


	/**
	@Description: TODO(生成法币交易订单)
	* @param @param transactionorder
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws 
	 */
	@PostMapping(value="/save")
 	public Result<?> save( String transactionNo,Double number){
		if(ObjectTool.isBlank(transactionNo,number)){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		Transactionorder transaction=new Transactionorder();
		transaction.setTransactionNo(transactionNo);
		transaction.setNumber(number);
 		return transactionorderService.save(transaction);
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
	@PostMapping(value="/makeMoney")
 	public Result<?> makeMoney( Integer transactionorderId,String imgUrl,Integer payWay){
		if(transactionorderId==null){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
 		return transactionorderService.makeMoney(transactionorderId,imgUrl,payWay);
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
	@PostMapping(value="/confirmMakeMoney")
 	public Result<?> confirmMakeMoney(Integer transactionorderId,String payPwd){
		if(ObjectTool.isBlank(transactionorderId,payPwd)){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		Result<?> result= userinfoService.checkPayPwd(payPwd);
		if(!StatusCode.SUCCESS.getCode().equals(result.getCode())) {
			return result;
		}
 		return transactionorderService.confirmMakeMoney(transactionorderId);
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
	@PostMapping(value="/cancelOrder")
 	public Result<?> cancelOrder(Integer transactionorderId){
		if(transactionorderId==null){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
 		return transactionorderService.cancelOrder(transactionorderId);  		 		
 	}
	/**
	 * 
	* @Title: send
	* @Description: TODO(c2c通讯对话)
	* @author xzj
	* @param @param msg
	* @param @param transactionorderId
	* @param @return    参数
	* @return Result    返回类型
	* @throws
	 */
	@PostMapping(value="/send")
	public Result<TransactionMessage> send(Integer transactionorderId,String msg){
		  if(ObjectTool.isBlank(msg,transactionorderId)){
				return Result.errorResult(StatusCode.PARAM_NULL);
		  }
		return  transactionMessageService.send(transactionorderId,msg);
	}
	/**
	 * 
	* @Title: getMessageList
	* @Description: TODO(获取信息列表)
	* @author xzj
	* @param @param orderId
	* @param @return    参数
	* @return Result    返回类型
	* @throws
	 */
	@GetMapping(value="/getMessageList")
	public Result<List<TransactionMessage>> getMessageList( Integer orderId){
		if(ObjectTool.isBlank(orderId)){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return transactionMessageService.getMessageList(orderId);
	}
	/**
	 * 
	* @Title: getTransactionorderList
	* @Description: TODO(获取c2c订单)
	* @author xzj
	* @param @param pageNum
	* @param @param pageSize
	* @param @return    参数
	* @return Result    返回类型
	* @throws
	 */
	@GetMapping(value= "/getTransactionorderList")
 	public Result<PageInfo<TransactionorderVO>> getTransactionorderList(Integer pageNum,Integer pageSize,
 			Integer status,Integer type,Integer coinId,String startTime,String endTime) {
		if(ObjectTool.isBlank(pageNum,pageSize)){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return transactionorderService.getTransactionorderList(pageNum, pageSize, status, type, coinId, startTime, endTime);
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
		if(ObjectTool.isBlank(transactionId,pageNum,pageSize)){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return transactionorderService.getTransactionorderListByTransaction(transactionId,pageNum, pageSize);
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
 	public Result<TransactionorderVO> getTransactionorder(@RequestParam("transactionorderId") Integer transactionorderId){
		if(ObjectTool.isBlank(transactionorderId)){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return transactionorderService.getTransactionorder(transactionorderId);
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
 	 public Result<List<Bankinfo>>  getBankinfoByOrder(@RequestParam("id")Integer id){
 		if(ObjectTool.isBlank(id)){
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
 		return transactionorderService.getBankinfoByOrder(id);
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
		  return transactionorderService.getIdsByUserId();
	  }
}
