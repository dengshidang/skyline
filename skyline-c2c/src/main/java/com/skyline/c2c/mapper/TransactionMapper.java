package com.skyline.c2c.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.skyline.common.entity.Transaction;
import com.skyline.common.util.MyMapper;
import com.skyline.common.vo.TransactionVO;

public interface TransactionMapper extends MyMapper<Transaction>{
		/**
		 * 
		* @Title: selectTransactionList
		* @Description: TODO(根据类型查找广告)
		* @param @param transactiontypeId
		* @param @return    参数
		* @return List<Transaction>    返回类型
		* @author xiaozhijian
		* @throws
		 */
		public List<TransactionVO>  selectTransactionList(@Param("transactiontypeId")Integer transactiontypeId,@Param("payWay")String payWay,@Param("money")Double money);
		/**
		 * 
		* @Title: getTransactionListByUser
		* @Description: TODO(查询个人广告列表)
		* @author xzj
		* @param @return    参数
		* @return List<Transaction>    返回类型
		* @throws
		 */
		public List<TransactionVO> getTransactionListByUser(@Param("userId") Integer userId,
				@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("coinId")Integer coinId);
		/**
		 * 
		* @Title: addTransactionFinishNum
		* @Description: TODO(添加成交数量)
		* @param @param transactionId
		* @param @param num
		* @param @return    参数
		* @return int    返回类型
		* @author xiaozhijian
		* @throws
		 */
		public int addTransactionFinishNum(@Param("transactionId")Integer transactionId,@Param("num")Double num,
				 @Param("updateTime")String updateTime);
		/**
		 * 
		* @Title: cancelTransaction
		* @Description: TODO(撤销)
		* @param @param transactionId
		* @param @param status
		* @param @return    参数
		* @return int    返回类型
		* @author xiaozhijian
		* @throws
		 */
		public int cancelTransaction(@Param("transactionId")Integer transactionId,@Param("status")Integer status,
				 @Param("updateTime")String updateTime);
		/**
		 * 
		* @Title: getTransactionByOrderId
		* @Description: TODO(根据订单id查交易信息)
		* @param @param transactionOrderId
		* @param @return    参数
		* @return Transaction    返回类型
		* @author xiaozhijian
		* @throws
		 */
		public Transaction getTransactionByOrderId(@Param("transactionorderId")Integer transactionorderId);
		/**
		 * 
		* @Title: findById
		* @Description: TODO(查一条数据)
		* @author xzj
		* @param @param id
		* @param @return    参数
		* @return TransactionVO    返回类型
		* @throws
		 */
		public TransactionVO findById(@Param("id") Integer id);
		/**
		 * 
		* @Title: getTransactionForUpdate
		* @Description: TODO(加锁查询一条记录)
		* @author xzj
		* @param @return    参数
		* @return Transaction    返回类型
		* @throws
		 */
		public Transaction getTransactionForUpdate(@Param("id")Integer id);
		
		
}
