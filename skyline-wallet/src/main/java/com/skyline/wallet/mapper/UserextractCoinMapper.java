package com.skyline.wallet.mapper;

import java.util.List;

import com.skyline.common.entity.UserextractCoinEntity;
import com.skyline.common.util.MyMapper;

public interface UserextractCoinMapper extends MyMapper<UserextractCoinEntity>{
	
	public List<UserextractCoinEntity> getSeTUserextractCoinList(UserextractCoinEntity entity);
	
	public void addSeTUserextractCoin(UserextractCoinEntity entity);
}
