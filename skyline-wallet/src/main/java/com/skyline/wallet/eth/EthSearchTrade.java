package com.skyline.wallet.eth;

import java.math.BigInteger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;

import com.skyline.common.constant.CoinConstant;
import com.skyline.common.entity.WalletinfoEntity;
import com.skyline.wallet.business.WalletBusiness;
import com.skyline.wallet.mapper.WalletinfoMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@WebListener
public class EthSearchTrade implements ServletContextListener {

	//private String url = "https://mainnet.infura.io/v3/91c8b11435f4487b905d8d7cad6ec022";
	//private  String url = "http://172.16.2.247:8545";
	//String url = "http://39.105.26.249:9099";
	Logger logger = Logger.getLogger(EthSearchTrade.class);
	
	@Autowired
	private WalletinfoMapper walletinfoMapper;
	
	@Autowired
	private WalletBusiness walletBusiness;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	@Async
	public void contextInitialized(ServletContextEvent arg0) {
		/*//获取以太坊钱包服务器信息
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
		});*/
		
	}
	
	
	public void ethSearchTrade(){
		
		//获取以太坊钱包服务器信息
		WalletinfoEntity walletinfo = walletinfoMapper.queryWalletByEng(CoinConstant.ETH);
		String url = walletinfo.getWalletIp()+(walletinfo.getWalletPort() == null || walletinfo.getWalletPort() == 0 ? "":":"+walletinfo.getWalletPort());
		Web3j web3j = Web3JClient.getClient(url);
		try {
			//获取最高区块高度
			EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().send();
			BigInteger block = ethBlockNumber.getBlockNumber();
			
			//当前区块高度
			int height = walletinfo.getBlockheight();
			//计算区块高度差距
			int gap = block.intValue() - height;
			logger.info("ETH区块对比:"+height +"→"+block.intValue()+"------------------");
			
			if(gap > 0){
				//交易检索区块高度
				int newHeight = height;
				
				while (newHeight < block.intValue()){
					BigInteger execHeight = new BigInteger(newHeight+"");
					
					logger.info("ETH区块检索:"+newHeight+"------------------");
					//获取区块交易信息
					DefaultBlockParameter defaultBlockParameter = new DefaultBlockParameterNumber(execHeight);
					EthBlock ethBlock = web3j.ethGetBlockByNumber(defaultBlockParameter, true).send();
					JSONObject blockJson = JSONObject.fromObject(ethBlock.getBlock());
					//获取当前区块所以交易信息
					JSONArray transactionsArray = blockJson.getJSONArray("transactions");
					
					for(int j=0 ; j<transactionsArray.size() ; j++){
						JSONObject tradeJson = transactionsArray.getJSONObject(j);
						try {
							//检索交易是否是充值信息
							walletBusiness.ethTradeSearch(tradeJson,walletinfo.getPropertyid());
						} catch (Exception e) {
							logger.error("ETH自动检索交易异常",e);
						}
						
					}
					newHeight +=1;
				}
				//更新钱包区块高度
				walletinfo.setBlockheight(newHeight);
				walletinfoMapper.updateByPrimaryKey(walletinfo);
			}else{
				Thread.sleep(10000);
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
