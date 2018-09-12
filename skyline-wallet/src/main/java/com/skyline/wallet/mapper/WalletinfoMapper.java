package com.skyline.wallet.mapper;

import com.skyline.common.entity.WalletinfoEntity;
import com.skyline.common.util.MyMapper;

public interface WalletinfoMapper extends MyMapper<WalletinfoEntity>{
	
	/**
	 * 根据币种ID查询钱包服务器信息
	 * @param coinId
	 * @return
	 */
	public WalletinfoEntity queryWalletByCoin(Integer coinId);
	
	/**
	 * 跟代币类型获取钱包服务器信息
	 * @param eng
	 * @return
	 */
	public WalletinfoEntity queryWalletByEng(String eng) ;
	
	public void updateWalletInfo(WalletinfoEntity entity) ;
	
	
}
