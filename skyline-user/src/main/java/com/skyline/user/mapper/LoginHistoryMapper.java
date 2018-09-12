package com.skyline.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.skyline.common.entity.LoginHistory;
import com.skyline.common.util.MyMapper;

public interface LoginHistoryMapper extends MyMapper<LoginHistory>{
		

	List<LoginHistory>  queryLoginHistory(@Param("userId")String userId);
}
