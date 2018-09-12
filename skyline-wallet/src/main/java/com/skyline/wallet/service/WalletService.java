package com.skyline.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.CointransactionEntity;
import com.skyline.wallet.business.WalletBusiness;

@RestController
public class WalletService {

	@Autowired
	private WalletBusiness walletBusiness;
	
	/**
	 * 查询资产充值提现交易记录
	 * @param entity 充值提现实体信息
	 * @return
	 */
	@PostMapping(value= "/wallet/record")
	public Result<?> queryCoinTransaction(@RequestBody CointransactionEntity entity,
			@RequestParam Integer pageNum,@RequestParam Integer pageSize){
		return walletBusiness.queryCoinTransaction(entity,pageNum,pageSize);
	}
	
	/**
	 * 获取用户的币种钱包地址
	 * @param coinId 币种ID
	 * @return
	 */
	@PostMapping(value= "/wallet/getAddress")
	public Result<?> getWalletAddress(@RequestParam Integer coinId){
		return walletBusiness.getWalletAddress(coinId);
	}
	
	/**
	 * 根据交易ID检索充值信息
	 * @param tradeHash
	 * @return
	 */
	@PostMapping(value= "/wallet/searchHash")
	public Result<?> paySearch(@RequestParam Integer coinId,@RequestParam String tradeHash){
		return walletBusiness.paySearch(coinId,tradeHash);
	}
	
	
	/**
	 * 资产提现申请
	 * @param entity
	 * @param pwd
	 * @return
	 */
	@PostMapping(value= "/wallet/appliy")
	public Result<?> appliyTransaction(@RequestBody CointransactionEntity entity,@RequestParam String pwd){
		return walletBusiness.appliyTransaction(entity,pwd);
	}
	
	/**
	 * 根据交易ID查询交易信息
	 * @param hash 交易ID
	 * @param walletId 钱包服务器ID
	 * @return
	 */
	@GetMapping(value= "/wallet/getHash")
	public Result<?> getTransactionMsg(@RequestParam String hash,@RequestParam Integer walletId){
		return walletBusiness.getTransactionMsg(hash,walletId);
	}
	
	/**
	 * 充值提现审核
	 * @param cointransaction
	 * @return
	 */
	@PostMapping(value= "/wallet/audit")
	public Result<?> auditCointransaction(@RequestBody CointransactionEntity entity){
		return walletBusiness.auditCointransaction(entity);
	}
}
