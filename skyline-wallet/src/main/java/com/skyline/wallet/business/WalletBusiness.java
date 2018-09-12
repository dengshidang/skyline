package com.skyline.wallet.business;

import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.entity.CointransactionEntity;

import net.sf.json.JSONObject;

public interface WalletBusiness {

	
	/**
	 * 查询资产充值提现交易记录
	 * @param entity 充值提现实体信息
	 * @param pageNum 页数
	 * @param pageSize 页数大小
	 * @return
	 */
	public Result<?> queryCoinTransaction(CointransactionEntity entity,Integer pageNum,Integer pageSize);
	
	/**
	 * 获取用户的币种钱包地址
	 * @param coinId 币种ID
	 * @return
	 */
	public Result<?> getWalletAddress(Integer coinId);
	
	/**
	 * 根据交易ID检索充值信息
	 * @param tradeHash
	 * @return
	 */
	public Result<?> paySearch(Integer coinId,String tradeHash);
	
	/**
	 * 以太坊充值交易检索
	 * @param tradeJson
	 * @return
	 */
	public StatusCode ethTradeSearch(JSONObject tradeJson,Integer coinId);
	
	/**
	 * 比特币类充值交易检索
	 * @param tradeJson
	 * @return
	 */
	public StatusCode btcTradeSearch(JSONObject tradeJson,Integer coinId);
	
	
	/**
	 * 资产提现申请
	 * @param entity
	 * @param pwd
	 * @return
	 */
	public Result<?> appliyTransaction(CointransactionEntity entity,String pwd);
	
	/**
	 * 根据交易ID查询交易信息
	 * @param hash 交易ID
	 * @param walletId 钱包服务器ID
	 * @return
	 */
	public Result<?> getTransactionMsg(String hash,Integer walletId);
	
	/**
	 * 充值提现审核
	 * @param cointransaction
	 * @return
	 */
	public Result<?> auditCointransaction(CointransactionEntity entity);
}
