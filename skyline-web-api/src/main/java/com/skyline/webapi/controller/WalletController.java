package com.skyline.webapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.CointransactionEntity;
import com.skyline.common.entity.Userinfo;
import com.skyline.webapi.filter.RequestUtil;
import com.skyline.webapi.service.WalletService;

@RestController
public class WalletController {
	
	@Autowired
	private WalletService walletService;
	
	/**
	 * 查询资产充值提现交易记录
	 * @param entity 充值提现实体信息
	 * @return
	 */
	@PostMapping(value= "/wallet/record")
	public Result<?> queryCoinTransaction(CointransactionEntity entity,Integer pageNum, Integer pageSize){
		Userinfo user=RequestUtil.getCurrentUser();
		entity.setUserId(user.getId());
		return walletService.queryCoinTransaction(entity,pageNum,pageSize);
	}
	
	/**
	 * 获取用户的币种钱包地址
	 * @param coinId 币种ID
	 * @return
	 */
	@PostMapping(value= "/wallet/getAddress")
	public Result<?> getWalletAddress(Integer coinId){
		return walletService.getWalletAddress(coinId);
	}
	
	/**
	 * 根据交易ID检索充值信息
	 * @param tradeHash
	 * @return
	 */
	@PostMapping(value= "/wallet/searchHash")
	public Result<?> paySearch(Integer coinId,String tradeHash){
		return walletService.paySearch(coinId,tradeHash);
	}
	
	
	/**
	 * 资产提现申请
	 * @param entity
	 * @param pwd
	 * @return
	 */
	@PostMapping(value= "/wallet/appliy")
	public Result<?> appliyTransaction(CointransactionEntity entity,String pwd){
		return walletService.appliyTransaction(entity,pwd);
	}
	
	/**
	 * 根据交易ID查询交易信息
	 * @param hash 交易ID
	 * @param walletId 钱包服务器ID
	 * @return
	 */
	@GetMapping(value= "/wallet/getHash")
	public Result<?> getTransactionMsg( String hash,Integer walletId){
		return walletService.getTransactionMsg(hash,walletId);
	}
	
	/**
	 * 充值提现审核
	 * @param cointransaction
	 * @return
	 */
	@PostMapping(value= "/wallet/audit")
	public Result<?> auditCointransaction(CointransactionEntity entity){
		return walletService.auditCointransaction(entity);
	}
}
