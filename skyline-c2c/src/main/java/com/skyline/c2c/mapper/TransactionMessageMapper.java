package com.skyline.c2c.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.skyline.common.entity.TransactionMessage;
import com.skyline.common.util.MyMapper;

public interface TransactionMessageMapper extends MyMapper<TransactionMessage>{
	public List<TransactionMessage> getMessageList(@Param("userId")Integer userId,@Param("orderId")Integer orderId);
}
