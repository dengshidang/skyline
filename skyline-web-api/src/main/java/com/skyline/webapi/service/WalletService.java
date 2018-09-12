package com.skyline.webapi.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.skyline.common.constant.Result;
import com.skyline.common.entity.CointransactionEntity;

@FeignClient(value = "skyline-wallet")
public interface WalletService {

	/**
	 * 查询资产充值提现交易记录
	 * @param entity 充值提现实体信息
	 * @return
	 */
	@PostMapping(value= "/wallet/record")
	public Result<?> queryCoinTransaction(@RequestBody CointransactionEntity entity,
			@RequestParam("pageNum") Integer pageNum,
			@RequestParam("pageSize") Integer pageSize);
	
	/**
	 * 获取用户的币种钱包地址
	 * @param coinId 币种ID
	 * @return
	 */
	@PostMapping(value= "/wallet/getAddress")
	public Result<?> getWalletAddress(@RequestParam("coinId") Integer coinId);
	
	/**
	 * 根据交易ID检索充值信息
	 * @param tradeHash
	 * @return
	 */
	@PostMapping(value= "/wallet/searchHash")
	public Result<?> paySearch(@RequestParam("coinId") Integer coinId,@RequestParam("tradeHash") String tradeHash);
	
	
	/**
	 * 资产提现申请
	 * @param entity
	 * @param pwd
	 * @return
	 */
	@PostMapping(value= "/wallet/appliy")
	public Result<?> appliyTransaction(@RequestBody CointransactionEntity entity,@RequestParam("pwd") String pwd);
	
	/**
	 * 根据交易ID查询交易信息
	 * @param hash 交易ID
	 * @param walletId 钱包服务器ID
	 * @return
	 */
	@GetMapping(value= "/wallet/getHash")
	public Result<?> getTransactionMsg(@RequestParam("hash") String hash,@RequestParam("walletId") Integer walletId);
	
	/**
	 * 充值提现审核
	 * @param cointransaction
	 * @return
	 */
	@PostMapping(value= "/wallet/audit")
	public Result<?> auditCointransaction(@RequestBody CointransactionEntity entity);
}
