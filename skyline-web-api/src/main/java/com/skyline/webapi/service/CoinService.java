package com.skyline.webapi.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.skyline.common.constant.Constants;
import com.skyline.common.constant.Result;
import com.skyline.common.entity.CointransactionEntity;
import com.skyline.common.entity.SeTWalletaddrEntity;

@FeignClient(value = "skyline-wallet")
public interface CoinService {
	/**
	 * 获取新的钱包地址
	 * @return
	 * @throws Throwable 
	 */
	@RequestMapping(value="/coin/getnewaddress",method=RequestMethod.POST)
	public Result getNewAddress(@RequestBody SeTWalletaddrEntity entity,
				@RequestHeader(Constants.CURRENT_USER_JSON) String userJson) ;
	
	/**
	 * 添加提币申请
	 * @param userId 用户ID
	 * @param coinName	币种简称,格式：BTC
	 * @param walletaddress	提币地址
	 * @param amount 提币数量
	 * @param coinId 币种ID
	 * @return
	 */
	@RequestMapping(value="/coin/addsetuserextractrecord",method=RequestMethod.POST)
	public Result addSeTUserextractRecord(@RequestBody CointransactionEntity entity,
				@RequestHeader(Constants.CURRENT_USER_JSON) String userJson);
	
	/**
	 * 转账
	 * @param userId 用户ID
	 * @param coinName	币种简称,格式：BTC
	 * @param walletaddress	提币地址
	 * @param amount 提币数量
	 * @param coinId 币种ID   
	 * @param id  
	 * @param orderNo 订单编号
	 * @return
	 * @throws Throwable 
	 */
	@RequestMapping(value = "/coin/sendtoaddress", method = RequestMethod.POST)
	public Result sendToAddress(@RequestBody CointransactionEntity entity) ;
	
	/**
	 * 查询钱包总额
	 * @param coinType 
	 * @return
	 * @throws Throwable 
	 */
	@RequestMapping(value="/coin/getaddressbalances",method=RequestMethod.POST)
	public Result getAddressBalances(@RequestParam("coinType") String coinType);

	/**
	 * 校验钱包地址
	 * @param address
	 * @return
	 * @throws Throwable 
	 */
	@RequestMapping(value="/btc/validateaddress",method=RequestMethod.POST)
	public Result validateAddress(@RequestParam("address") String address) ;
	
	/**
	 * 查询指定交易ID交易信息
	 * @param txid
	 * @param coinType 币种类型
	 * @return json对象
	 * @throws Throwable 
	 */
	@RequestMapping(value="/coin/gettransaction",method=RequestMethod.POST)
	public Result getTransaction(@RequestParam("txid") String txid, @RequestParam("coinType") String coinType);
}
