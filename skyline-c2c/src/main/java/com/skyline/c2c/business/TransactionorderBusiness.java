package com.skyline.c2c.business;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.skyline.common.business.BaseBusiness;
import com.skyline.common.constant.Result;
import com.skyline.common.entity.Bankinfo;
import com.skyline.common.entity.Transactionorder;
import com.skyline.common.vo.TransactionorderVO;
import com.skyline.common.vo.WebSocketVO;

public interface TransactionorderBusiness extends BaseBusiness<Transactionorder, Integer>{
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
     	public Result<?> saveTransactionorder(Transactionorder transactionorder);
     	
     	/**
     	 * 
     	* @Title: makeMoney
     	* @Description: TODO(打款)
     	* @param @return    参数
     	* @return Result    返回类型
     	* @author xiaozhijian
     	* @throws
     	 */
     	public Result<?> makeMoney(Integer transactionorderId,String msgUrl,Integer payWay);
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
     	public Result<?> confirmMakeMoney(Integer transactionorderId);
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
     	public Result<?> cancelOrder(Integer transactionorderId);
     	
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
     	public Result<TransactionorderVO> getTransactionorder(Integer transactionorderId);
     	
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
     	public Result<PageInfo<TransactionorderVO>> getTransactionorderList(Integer pageNum,Integer pageSize,
     			Integer status,Integer type,Integer coinId,String startTime,String endTime);
     	
     	/**
     	 * 
     	* @Title: getTransactionorderListByMerchantId
     	* @Description: TODO(商户查询广告下面的订单列表)
     	* @author xzj
     	* @param @return    参数
     	* @return Result<PageInfo<Transactionorder>>    返回类型
     	* @throws
     	 */
     	public Result<PageInfo<TransactionorderVO>> getTransactionorderListByTransaction(Integer transactionId,Integer pageNum,Integer pageSize);
     	
     	/**
     	 * 
     	* @Title: autoCancelOrder
     	* @Description: TODO(自动取消订单。定时器来调用)
     	* @param @return    参数
     	* @return Result    返回类型
     	* @author xiaozhijian
     	* @throws
     	 */
     	public void autoCancelOrder();
     	/**
     	 * 
     	* @Title: autoUpdateOrderStatusStayState
     	* @Description: TODO(自动更改订单为待审诉)
     	* @author xzj
     	* @param     参数
     	* @return void    返回类型
     	* @throws
     	 */
     	public void autoUpdateOrderStatusStayState();
     	/**
     	 * 
     	* @Title: auotUpdateOrderStatusExceptionClose
     	* @Description: TODO(自动更改订单为异常关闭)
     	* @author xzj
     	* @param     参数
     	* @return void    返回类型
     	* @throws
     	 */
     	public void auotUpdateOrderStatusExceptionClose();
     	
     	 /**
	   	  * 
	   	 * @Title: getBankinfoByUser
	   	 * @Description: TODO(查询订单的可支付类型)
	   	 * @author xzj
	   	 * @param @return    参数
	   	 * @return Result<Bankinfo>    返回类型
	   	 * @throws
	   	  */
	   	 public Result<List<Bankinfo>>  getBankinfoByOrder(Integer id); 
	   	 
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
		   public Result<List<WebSocketVO>> getIdsByUserId();
}
