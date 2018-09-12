package com.skyline.c2c.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.skyline.common.entity.Transactionstate;
import com.skyline.common.util.MyMapper;
import com.skyline.common.vo.TransactionstateVO;

public interface TransactionstateMapper extends MyMapper<Transactionstate>{
		
		public int examineTransactionstate(@Param("transactionstateId")Integer transactionstateId,
				@Param("currStatus")Integer currStatus,@Param("status")Integer status,
				 @Param("updateTime")String updateTime,@Param("remark")String remark);
		
		public int getCountTransactionstateByOrderId(@Param("orderNo")String orderNo,@Param("uid")Integer uid);
				
		public List<TransactionstateVO> getListTransactionstate(
					@Param("orderNo")String orderNo,
					@Param("stateUserAccount")String  stateUserAccount,
					@Param("takeUserAccount")String takeUserAccount,
					@Param("payWayType")Integer payWayType,
					@Param("status")Integer status,
					@Param("startTime")String startTime,
					@Param("endTime")String  endTime
				);
		
		public Transactionstate getTransactionstateByOrderNo(@Param("orderNo") String orderNo,@Param("userId")Integer userId,@Param("status")Integer status);
		
}
