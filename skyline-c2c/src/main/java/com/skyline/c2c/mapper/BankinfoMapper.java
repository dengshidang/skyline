package com.skyline.c2c.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.skyline.common.entity.Bankinfo;
import com.skyline.common.util.MyMapper;

public interface BankinfoMapper extends MyMapper<Bankinfo>{

	public List<Bankinfo> getBankinfoByUser(@Param("userId")Integer  userId);
	
	public Bankinfo getBankinfoByType(@Param("userId")Integer  userId,@Param("type") Integer type);
	
	public List<Bankinfo> getBankinfoByTransactionNo(@Param("transactionNo")String transactionNo);

}
