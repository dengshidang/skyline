package com.skyline.c2c.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.Transactionorder;
import com.skyline.common.util.MyMapper;
import com.skyline.common.vo.TransactionorderVO;

public interface TransactionorderMapper extends MyMapper<Transactionorder>{
		/**
		 * 
		* @Title: updateStatus
		* @Description: TODO(更改订单状态)
		* @param @param transactionorderId  
		* @param @param currStatus  当前状态
		* @param @param status 
		* @param @return    参数
		* @return int    返回类型
		* @author xiaozhijian
		* @throws
		 */
		public int updateStatus(@Param("transactionorderId")Integer transactionorderId,
				@Param("currStatus")Integer currStatus,@Param("status")Integer status,
				@Param("updateTime")String updateTime,@Param("imgUrl") String imgUrl,@Param("payWay")Integer  payWay);
		/**
		 * 
		* @Title: updateIsAppeal
		* @Description: TODO(更改为可审诉订单)
		* @author xzj
		* @param @param transactionorderId
		* @param @param currIsAppeal
		* @param @param isAppeal
		* @param @param updateTime
		* @param @return    参数
		* @return int    返回类型
		* @throws
		 */
		public int updateIsAppeal(@Param("transactionorderId")Integer transactionorderId,
				@Param("currIsAppeal")Integer currIsAppeal,@Param("isAppeal")Integer isAppeal,@Param("updateTime")String updateTime);
	
		/**
		 * 
		* @Title: getCountOrderNumber
		* @Description: TODO(正在交易的订单数)
		* @param @return    参数
		* @return int    返回类型
		* @author xiaozhijian
		* @throws
		 */
		public Double getCountOrderNumber(@Param("transactionNo")String transactionNo);
		/**
		 * 
		* @Title: getTransactionorderByStateId
		* @Description: TODO(根据申诉id获取订单信息)
		* @author xzj
		* @param @param id
		* @param @return    参数
		* @return Transactionorder    返回类型
		* @throws
		 */
		public Transactionorder getTransactionorderByStateId(@Param("transactionstateId")Integer id);
		
		/**
	 	 * 
	 	* @Title: getTransactionorderList
	 	* @Description: TODO(查个人c2c订单)
	 	* @author xzj
	 	* @param @param userId
	 	* @param @return    参数
	 	* @return Result    返回类型
	 	* @throws
	 	 */
	 	public List<TransactionorderVO> getTransactionorderList(@Param("userId")Integer userId,@Param("status")Integer status,
	 			@Param("type")Integer type,@Param("coinId")Integer coinId,
	 			@Param("startTime")String startTime,@Param("endTime")String endTime);
	 	/**
	 	 * 
	 	* @Title: getTransactionorderListByTransaction
	 	* @Description: TODO(查询商户某广告的交易订单)
	 	* @author xzj
	 	* @param @param transactionId
	 	* @param @return    参数
	 	* @return List<Transactionorder>    返回类型
	 	* @throws
	 	 */
	 	public List<TransactionorderVO> getTransactionorderListByTransaction(@Param("transactionId")Integer transactionId);
	 	/**
	 	 * 
	 	* @Title: getTransactionorderInCountByUser
	 	* @Description: TODO(查询个人未结束的订单数量)
	 	* @author xzj
	 	* @param @return    参数
	 	* @return int    返回类型
	 	* @throws
	 	 */
	 	public int getTransactionorderInCountByUser(@Param("userId")Integer userId);
	 	
		/**
		 * 
		* @Title: autoCancelOrder
		* @Description: TODO(获取把创建时间超过15分钟的)
		* @param @return    参数
		* @return int    返回类型
		* @author xiaozhijian
		* @throws
		 */
		public List<Transactionorder> getAutoCancelOrder(@Param("currStatus")Integer currStatus,@Param("time") Integer time);
		/**
		 * 
		* @Title: autoCancelOrder
		* @Description: TODO(获取把已付款时间超过15分钟的)
		* @param @return    参数
		* @return int    返回类型
		* @author xiaozhijian
		* @throws
		 */
		public List<Transactionorder> autoUpdateOrderStatusStayState(@Param("currStatus")Integer currStatus,@Param("time") Integer time);
		/**
		 * 
		* @Title: autoCancelOrder
		* @Description: TODO(获取把已付款时间超过6小时的)
		* @param @return    参数
		* @return int    返回类型
		* @author xiaozhijian
		* @throws
		 */
		public List<Transactionorder> auotUpdateOrderStatusExceptionClose(@Param("currStatus")Integer currStatus,@Param("time") Integer time);
		/**
		 * 
		* @Title: getSumNumberByIn
		* @Description: TODO(正在交易的币总数)
		* @author xzj
		* @param @param transactionNo
		* @param @return    参数
		* @return Double    返回类型
		* @throws
		 */
		public Double getSumNumberByIn(@Param("transactionNo")String transactionNo);
		/**
		 * 
		* @Title: getTransactionorderById
		* @Description: TODO(查询订单信息)
		* @author xzj
		* @param @param id
		* @param @return    参数
		* @return TransactionorderVO    返回类型
		* @throws
		 */
		public TransactionorderVO getTransactionorderById(@Param("id") Integer id);
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
		public List<Integer> getIdsByUserId(@Param("userId")Integer userId);
}
