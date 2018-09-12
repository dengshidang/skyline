package com.skyline.service.mapper;

import org.apache.ibatis.annotations.Param;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.Userbalance;
import com.skyline.common.util.MyMapper;

public interface SeTUserbalanceMapper extends MyMapper<Userbalance>{

	/**
	 * 查询用户的余额信息
	 * @param seTUserbalanceEntity
	 * @return
	 */
	public Userbalance queryUserBalance(Userbalance seTUserbalanceEntity);
   
	
}
