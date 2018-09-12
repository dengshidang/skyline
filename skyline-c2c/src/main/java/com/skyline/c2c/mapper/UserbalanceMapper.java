package com.skyline.c2c.mapper;

import org.apache.ibatis.annotations.Param;

import com.skyline.common.entity.Userbalance;
import com.skyline.common.util.MyMapper;

public interface UserbalanceMapper extends MyMapper<Userbalance>{
	
	
		/**
		 * 
		* @Title: getUserbalanceByUserIdForUpdate
		* @Description: TODO(加了排它锁查询用户资金)
		* @author xzj
		* @param @return    参数
		* @return Userbalance    返回类型
		* @throws
		 */
		public Userbalance getUserbalanceByUserIdForUpdate(@Param("userId")Integer userId,@Param("coinId") Integer coinId);
	
	
}
