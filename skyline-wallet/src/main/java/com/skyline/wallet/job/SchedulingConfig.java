package com.skyline.wallet.job;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;

import com.skyline.common.constant.CoinConstant;
import com.skyline.common.constant.SystemConstants;
import com.skyline.common.entity.CointransactionEntity;
import com.skyline.common.entity.SeTCoininfoEntity;
import com.skyline.common.entity.SeTWalletaddrEntity;
import com.skyline.common.entity.WalletinfoEntity;
import com.skyline.common.util.DateUtil;
import com.skyline.common.util.OrderNoUtil;
import com.skyline.wallet.btc.CoinUtils;
import com.skyline.wallet.business.WalletBusiness;
import com.skyline.wallet.eth.Web3JClient;
import com.skyline.wallet.mapper.CoinTransactionMapper;
import com.skyline.wallet.mapper.SeTCoininfoMapper;
import com.skyline.wallet.mapper.WalletaddrMapper;
import com.skyline.wallet.mapper.WalletinfoMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@WebListener
public class SchedulingConfig implements ServletContextListener {
	
	Logger logger = Logger.getLogger(SchedulingConfig.class);

	
	@Autowired
	private WalletinfoMapper walletinfoMapper;
	
	@Autowired
	private WalletaddrMapper walletaddrMapper;
	
	@Autowired
	private CoinTransactionMapper coinTransactionMapper;
	
	@Autowired
	private SeTCoininfoMapper coininfoMapper;
	
	@Autowired
	private WalletBusiness walletBusiness;
	
	@Override
	@Async
	public void contextInitialized(ServletContextEvent sce) {
		
		new Thread(new Runnable() {
			public void run() {
				ETHSearch();
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				BTCSearch();
			}
		}).start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 以太坊充值检索入口
	 */
	private void ETHSearch(){
		//获取以太坊钱包服务器信息
		WalletinfoEntity walletinfo = walletinfoMapper.queryWalletByEng(CoinConstant.ETH);
		String url = walletinfo.getWalletIp()+(walletinfo.getWalletPort() == null || walletinfo.getWalletPort() == 0 ? "":":"+walletinfo.getWalletPort());
		Web3j web3j = Web3JClient.getClient(url);
		logger.info("ETH服务器初始地址:"+url+"------------------");
		web3j.blockObservable(true).subscribe(block -> {
					
			new Thread(new Runnable() {
				public void run() {
					List<TransactionResult> a = block.getResult().getTransactions();
					logger.info("ETH区块检索:"+block.getBlock().getNumber()+"------------------");
					for(TransactionResult tradeResult : a){
						JSONObject tradeJson = JSONObject.fromObject(tradeResult);
						//检索交易是否是充值信息
						walletBusiness.ethTradeSearch(tradeJson,walletinfo.getPropertyid());
					}
				}
			}).start();
		});
	}
	
	/**
	 * 比特币充值检索入口
	 */
	private void BTCSearch(){
		//获取比特币钱包服务器信息
		WalletinfoEntity walletinfo = walletinfoMapper.queryWalletByEng(CoinConstant.BTC);
		CoinUtils coinUtils = CoinUtils.getCoinUtil(walletinfo.getAccount(), walletinfo.getPwd(), walletinfo.getWalletIp(), walletinfo.getWalletPort());
		//CoinUtils coinUtils = CoinUtils.getCoinUtil("yjian", "yjian8531", "http://127.0.0.1", 61315);
		//获取最近交易条数
		int count = 1;
		//忽略数量
		int from = 0;
		while (true) {
			try {
				boolean bl = false;
				logger.info("BTC充值交易检索:"+count+"→"+from+"-----------------------");
				//获取最近交易数量
				JSONArray tradeArray = coinUtils.listtransactions(null, count, from);
				for(int i =0 ; i<tradeArray.size() ; i++){
					JSONObject tradeJson = tradeArray.getJSONObject(i);
					int code = btcTradeVty(tradeJson, walletinfo.getPropertyid(),walletinfo.getConfirm());
					if(code == 4){//充值交易已经记录
						bl = true;
						break;
					}
				}
						
				if(bl || tradeArray.size() == 0){
					from = 0;
					bl = false;
					Thread.sleep(30000);
				}else{
					from += count;
				}
						
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
		}
	}
	
	/**
	 * btc充值交易检索
	 * @param tradeJson 交易信息
	 * @param coinId 币种ID
	 * @param confirm 确认次数
	 * @return
	 */
	private int btcTradeVty(JSONObject tradeJson,Integer coinId,Integer confirm){
		
		if(tradeJson.getString("category").equals("receive")){//交易类型为接收
			//交易接收地址
			String toAddress = tradeJson.getString("address");
			//确认次数
			int confirmS = tradeJson.getInt("confirmations");
			if(confirmS >= confirm.intValue()){
				//根据接收地址获取对应钱包地址信息
				SeTWalletaddrEntity walletaddr = walletaddrMapper.queryWalletAddress(toAddress);
				
				if(walletaddr != null){
					String hash = tradeJson.getString("txid");
					//根据交易ID获取充值记录
					CointransactionEntity cointransaction = coinTransactionMapper.queryCoinTransactionByHash(hash);
					if(cointransaction == null){
						
						//获取币种信息
						SeTCoininfoEntity coininfo = coininfoMapper.selectByPrimaryKey(coinId);
						
						cointransaction = new CointransactionEntity();
						cointransaction.setOrderNo(OrderNoUtil.getTopupOdrderNo());//充值ID
						cointransaction.setUserId(walletaddr.getUserId());//用户id
						cointransaction.setTxId(hash);//交易ID
						cointransaction.setAmount(tradeJson.getDouble("amount"));//交易金额
						cointransaction.setWalletaddress(toAddress);//接收地址
						//cointransaction.setFee(Double.parseDouble(tradeJson.getString("fee")));//手续费
						cointransaction.setCoinId(coinId);//币种ID
						cointransaction.setCoinName(coininfo.getName());//币种名称
						cointransaction.setCategroy(CoinConstant.COINRECEIVE);//状态为接收
						cointransaction.setStatus(SystemConstants.COINTRANSACTION_STATUS_0);//待审核
						cointransaction.setCreateTime(DateUtil.getCurTimeString());
						cointransaction.setUpdateTime(DateUtil.getCurTimeString());
						//保存充值信息
						coinTransactionMapper.insert(cointransaction);
						logger.info("BTC充值交易检索SUCCESS:检索成功→"+tradeJson.getString("txid")+"-----------------------");
						return 0;//代表充值交易检索成功
					}else{
						logger.info("BTC充值交易检索ERROR:充值记录已存在→"+tradeJson.getString("txid")+"-----------------------");
						return 4;//代表充值交易已经记录
					}
				}else{
					logger.info("BTC充值交易检索ERROR:无效收账地址→"+tradeJson.getString("txid")+"-----------------------");
					return 3;//代表充值交易接收地址不是系统用户关联的地址
				}
			}else{
				logger.info("BTC充值交易检索ERROR:交易未被确认→"+tradeJson.getString("txid")+"-----------------------");
				return 2;//代表充值交易未被确认
			}
			
			
		}else{
			logger.info("BTC充值交易检索ERROR:提币交易→"+tradeJson.getString("txid")+"-----------------------");
			return 1;//代表交易类型为提币
		}
	}
	
	
}
