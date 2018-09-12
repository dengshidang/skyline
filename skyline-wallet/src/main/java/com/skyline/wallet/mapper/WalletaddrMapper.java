package com.skyline.wallet.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.skyline.common.entity.SeTWalletaddrEntity;
import com.skyline.common.util.MyMapper;

public interface WalletaddrMapper extends MyMapper<SeTWalletaddrEntity>{

	/**
	 * 根据地址查询钱包地址信息
	 * @param address 钱包地址
	 * @return
	 */
	public SeTWalletaddrEntity queryWalletAddress(@Param("address")String address);
	
	public SeTWalletaddrEntity queryUserWalletAddress(Map<String, Object> map);
	
	public void addUserWalletAddress(SeTWalletaddrEntity entity);
	
	public void updateWalletAddress(SeTWalletaddrEntity entity);
}
