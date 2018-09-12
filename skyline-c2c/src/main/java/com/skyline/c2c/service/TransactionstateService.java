package com.skyline.c2c.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.skyline.c2c.business.TransactionstateBusiness;
import com.skyline.common.constant.Result;
import com.skyline.common.entity.Transactionstate;
import com.skyline.common.vo.TransactionstateVO;

@RestController
@RequestMapping(value="transactionstate")
public class TransactionstateService {

	@Autowired 
	TransactionstateBusiness transactionstateBusiness;
	
	/**
	 * 
	* @Title: addTransactionstate
	* @Description: TODO(添加审诉)
	* @author xzj
	* @param @param transactionstate
	* @param @param user
	* @param @return    参数
	* @return Result    返回类型
	* @throws
	 */
	@PostMapping(value= "/save")
	public Result<?> save(String orderNo,String proveUrl,String content){
		return transactionstateBusiness.addTransactionstate( orderNo,proveUrl,content);
	}
	/**
	 * 
	* @Title: examineTransactionstate
	* @Description: TODO(进行审批)
	* @author xzj
	* @param @param transactionstateId
	*  @param @param status
	* @param @return    参数
	* @return Result    返回类型
	* @throws
	 */
	@PostMapping(value= "/examineTransactionstate")
	public Result<?> examineTransactionstate(Integer transactionstateId,Integer status,String remark){
		return transactionstateBusiness.examineTransactionstate(transactionstateId, status,remark);
	}
	
	/**
	 * 
	* @Title: getListTransactionstate
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author xzj
	* @param @return    参数
	* @return Result<?>    返回类型
	* @throws
	 */
	@GetMapping(value= "/getListTransactionstate")
	public Result<PageInfo<TransactionstateVO>> getListTransactionstate(Integer pageNum,Integer pageSize,
			String orderNo,String stateUserAccount,String takeUserAccount,Integer payWayType,
			Integer status,String startTime,String endTime){
		return transactionstateBusiness.getListTransactionstate(pageNum, pageSize, orderNo, stateUserAccount, takeUserAccount, payWayType,
				 status, startTime, endTime);
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
		return transactionstateBusiness.getTransactionstateByOrderNo(orderNo);
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
		return transactionstateBusiness.getTransactionstate(id);
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
	public Result<?> revokeTransactionstate(Integer id){
		return transactionstateBusiness.revokeTransactionstate(id);
	}
}
