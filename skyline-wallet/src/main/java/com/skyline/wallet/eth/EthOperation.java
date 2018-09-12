package com.skyline.wallet.eth;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByHash;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;

import com.skyline.common.constant.StatusCode;

import net.sf.json.JSONObject;

/**
 * ETH工具类
 * @author Yjian
 * @time 2018-6-22
 */
public class EthOperation {

	private static Logger logger = Logger.getLogger(EthOperation.class);
	
	/**
	 * 创建钱包地址
	 * @return
	 */
	public static String newAccount(String pwd,String serviceIp,Integer port){
		Admin admin = AdminClient.getParity("http://"+serviceIp+":"+port);
		String address="";
		try {
			NewAccountIdentifier newAccountIdentifier = admin.personalNewAccount(pwd).send();
			address = newAccountIdentifier.getAccountId();
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally {
			//关节点资源
			admin.shutdown();
		}

		return address;
	}

	
	/**
	 * ETH转账操作
	 * @param address
	 * @param passsword
	 * @param toAddress
	 * @param number
	 * @return
	 */
	public static String trasfer(String address,String passsword,String toAddress, Double number,String serviceIp,Integer port){
		
		JSONObject result = new JSONObject();
		
		Admin admin = AdminClient.getParity("http://"+serviceIp+":"+port);
		Web3j web3 = Web3JClient.getClient("http://"+serviceIp+":"+port);
		
		//交易数值转化
		BigInteger value = Convert.toWei(number.toString(), Convert.Unit.ETHER).toBigInteger();
		//矿工费
		BigInteger price = Convert.toWei("2", Unit.GWEI).toBigInteger();
		//最大限制
		BigInteger limit  = Convert.toWei("100", Unit.KWEI).toBigInteger();
		BigInteger fee = price.multiply(limit).add(value);
		
		if(verifyBalancel(fee, address,web3)){
			BigInteger nonce = null;
			try {
				//获取交易数据数
				EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(address,DefaultBlockParameterName.LATEST).sendAsync().get();
				nonce = ethGetTransactionCount.getTransactionCount(); 
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
	        Transaction transaction = Transaction.createEtherTransaction(address,nonce,price,limit,toAddress,value);
	        try{
	            EthSendTransaction ethSendTransaction =admin.personalSendTransaction(transaction,passsword).send();
	            if(ethSendTransaction!=null){
	                String tradeHash = ethSendTransaction.getTransactionHash();
	                logger.info("账户:["+address+"]转账到账户:["+toAddress+"],交易hash:["+tradeHash+"]");
	                if(tradeHash != null){
	                	result.put("code", StatusCode.SUCCESS.getCode());
	                	result.put("data", tradeHash);
	                	result.put("msg", StatusCode.SUCCESS.getMsg());
	                }else{
	                	result.put("code", StatusCode.SYSTEMERROR.getCode());
	                	result.put("msg", StatusCode.SYSTEMERROR.getMsg());
	                }
	                
	            }else{
	            	result.put("code", StatusCode.E00100.getCode());
	            	result.put("msg", StatusCode.E00100.getMsg());
	            }
	        }catch (Exception e){
	            logger.error("账户:["+address+"]交易失败!",e);
	            result.put("code", StatusCode.SYSTEMERROR.getCode());
            	result.put("msg", StatusCode.SYSTEMERROR.getMsg());
	        }
	        
	        
			
			
		}else{
			result.put("code", StatusCode.E00102.getCode());
        	result.put("msg", StatusCode.E00102.getMsg());
		}
		//关闭节点资源
		web3.shutdown();
		admin.shutdown();
        return result.toString();
    }
	
	
	
	
	
	/**
	 * 获取ETH余额
	 * @param address
	 * @return
	 */
	public static String getBalancel(String address,String serviceIp,Integer port){
		String result = "";
		Web3j web3 = Web3JClient.getClient("http://"+serviceIp+":"+port);
		try {
			//获取余额  
			EthGetBalance ethGetBalance1 = web3.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send();
			BigInteger value = ethGetBalance1.getBalance();
			result = Convert.fromWei(value.toString(), Convert.Unit.ETHER).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//关闭节点资源
			web3.shutdown();
		}
		return result;
	}
	
	/**
	 * 转账余额判断
	 * @param value 转出金额+手续费
	 * @param address 转出账号地址
	 * @return
	 */
	public static boolean verifyBalancel(BigInteger value,String address,Web3j web3){

		boolean bl = false;
		try {
			EthGetBalance ethGetBalance1 = web3.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send();
			BigInteger number = ethGetBalance1.getBalance();
			float sum = Convert.fromWei(number.toString(), Convert.Unit.ETHER).floatValue();
			float fee = Convert.fromWei(value.toString(), Convert.Unit.ETHER).floatValue();
			bl = sum > fee;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bl;
	}
	
	/**
	 * 获取以太坊的交易信息
	 * @param hash 交易ID
	 * @return
	 */
	public static String getTransaction(String hash,String serviceIp,Integer port){
		
		JSONObject result = new JSONObject();
		//链接以太坊节点
		Web3j web3 = Web3JClient.getClient("http://"+serviceIp+":"+port);
		try {
			//执行获取交易信息
			EthTransaction ethTransaction = web3.ethGetTransactionByHash(hash).send();
			org.web3j.protocol.core.methods.response.Transaction transaction = ethTransaction.getResult();
			
			if(transaction != null){
				result.put("data", JSONObject.fromObject(transaction));
				result.put("code", 0);
				result.put("msg", "查询成功");
			}else{
				result.put("code", StatusCode.E00100.getCode());
	        	result.put("msg", StatusCode.E00100.getMsg());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			result.put("code", StatusCode.SYSTEMERROR.getCode());
        	result.put("msg", StatusCode.SYSTEMERROR.getMsg());
		}finally {
			//关闭节点资源
			web3.shutdown();
		}
		
		return result.toString();
	}
	
	public static void main(String[] args) {
		
		Web3j web3j = Web3JClient.getClient("https://mainnet.infura.io/v3/91c8b11435f4487b905d8d7cad6ec022");
		try {
			EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().send();
			BigInteger block = ethBlockNumber.getBlockNumber();
			DefaultBlockParameter defaultBlockParameter = new DefaultBlockParameterNumber(block);
			EthBlock ethBlock = web3j.ethGetBlockByNumber(defaultBlockParameter, true).send();
			
			System.out.println(block);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*try {
			EthAccounts ethAccounts = web3j.ethAccounts().sendAsync().get();
			List<String> accountList = ethAccounts.getAccounts();//返回当前节点持有的账户列表
			System.out.println(accountList.size());
			for(String addr :accountList ){
				System.out.println(addr);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		 
	}
}
