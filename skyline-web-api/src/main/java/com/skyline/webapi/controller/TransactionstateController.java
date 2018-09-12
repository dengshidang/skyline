package com.skyline.webapi.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.entity.Transactionorder;
import com.skyline.common.entity.Transactionstate;
import com.skyline.common.util.ObjectTool;
import com.skyline.common.util.StringTool;
import com.skyline.webapi.service.TransactionstateService;


@RestController
@RequestMapping("transactionstate")
public class TransactionstateController {
	@Autowired
	TransactionstateService  transactionstateService;
	
	
	/**
	 * 
	* @Title: saveTransactionorder
	* @Description: TODO(生成申诉)
	* @param @param transactionorder
	* @param @return    参数
	* @return Result    返回类型
	* @author xiaozhijian
	* @throws
	 */
	@PostMapping(value= "/save")
 	public Result<?> save( String orderNo ,String proveUrl,String content){
		if(ObjectTool.isBlank(orderNo)) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return transactionstateService.save(orderNo,proveUrl,content);
	}
	
	/**
	 * 
	* @Title: getTransactionstateByOrderNo
	* @Description: TODO(查询当前订单有效的审诉订单)
	* @author xzj
	* @param @param orderNo
	* @param @param id
	* @param @return    参数
	* @return Result<Transactionstate>    返回类型
	* @throws
	 */
	@GetMapping(value= "/getTransactionstateByOrderNo")
	public Result<Transactionstate> getTransactionstateByOrderNo( String  orderNo){
		if(StringTool.isBlank(orderNo)) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return transactionstateService.getTransactionstateByOrderNo(orderNo);
	}
	/**
	 * 
	* @Title: getTransactionstate
	* @Description: TODO(查询单条信息)
	* @author xzj
	* @param @param orderNo
	* @param @param id
	* @param @return    参数
	* @return Result<Transactionstate>    返回类型
	* @throws
	 */
	@GetMapping(value= "/getTransactionstate")
	public Result<Transactionstate> getTransactionstate(Integer id){
		if(ObjectTool.isBlank(id)) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return transactionstateService.getTransactionstate(id);
	}
	
	/**
	 * 
	* @Title: revokeTransactionstate
	* @Description: TODO(撤消审诉)
	* @author xzj
	* @param @param id
	* @param @return    参数
	* @return Result<?>    返回类型
	* @throws
	 */
	@PostMapping(value= "/revokeTransactionstate")
	public Result<?> revokeTransactionstate(@RequestParam("id") Integer id){
		if(ObjectTool.isBlank(id)  ) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return transactionstateService.revokeTransactionstate(id);
	}
}
