package com.skyline.user.service;


import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skyline.user.redis.RedisUtil;




@RestController
@RequestMapping(value="redis")
public class RedisService {

	@Resource 
	RedisUtil redisUtil; 
	
	
	@PostMapping(value="save")
	public void save(String key ,String value,Long time){
		redisUtil.set(key, value, time);
	}
	@GetMapping(value="get")
	public String get(String key){
		return redisUtil.getString(key);
	}
}
