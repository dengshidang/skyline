package com.skyline.user.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.skyline.common.entity.Newsinfo;
import com.skyline.common.util.MyMapper;

public interface NewsInfoMapper extends MyMapper<Newsinfo> {
	
	List<Newsinfo> queryNewsCarousel(@Param("type")Integer type,@Param("status")Integer status,@Param("startTime")String startTime,
			@Param("endTime")String endTime,@Param("content")String content);
	
}
