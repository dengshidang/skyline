package com.skyline.c2c.mapper;

import org.apache.ibatis.annotations.Param;

import com.skyline.common.entity.Transactiontype;
import com.skyline.common.util.MyMapper;

public interface TransactiontypeMapper extends MyMapper<Transactiontype>{
		/**
		 * 
		* @Title: selectTransactiontype
		* @Description: TODO(根据订单id查询类型)
		* @param @param transactionorderId
		* @param @return    参数
		* @return Transactiontype    返回类型
		* @author xiaozhijian
		* @throws
		 */
		public Transactiontype selectTransactiontype(@Param("transactionorderId")Integer transactionorderId);
}
