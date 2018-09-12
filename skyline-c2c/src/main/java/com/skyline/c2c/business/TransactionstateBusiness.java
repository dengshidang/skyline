package com.skyline.c2c.business;
import com.github.pagehelper.PageInfo;
import com.skyline.common.business.BaseBusiness;
import com.skyline.common.constant.Result;
import com.skyline.common.entity.Transactionstate;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.vo.TransactionstateVO;
public interface TransactionstateBusiness extends BaseBusiness<Transactionstate, Integer> {
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
	public Result<?> addTransactionstate(String orderNo,String proveUrl,String content);
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
	public Result<?> revokeTransactionstate(Integer id);
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
	public Result<Transactionstate> getTransactionstateByOrderNo(String  orderNo);
	
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
	public Result<Transactionstate> getTransactionstate(Integer id);
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
	public Result<?> examineTransactionstate(Integer transactionstateId,Integer status,String remark);
	/**
	 * 
	* @Title: getListTransactionstate
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author xzj
	* @param @return    参数
	* @return Result<?>    返回类型
	* @throws
	 */
	public Result<PageInfo<TransactionstateVO>> getListTransactionstate(Integer pageNum,Integer pageSize,
			String orderNo,String stateUserAccount,String takeUserAccount,Integer payWayType,
			Integer status,String startTime,String endTime);
	
	
}
