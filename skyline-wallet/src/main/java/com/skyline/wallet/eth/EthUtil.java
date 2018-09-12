package com.skyline.wallet.eth;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import org.apache.log4j.Logger;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;

import com.skyline.common.constant.StatusCode;

import net.sf.json.JSONObject;

/**
 * 以太坊钱包工具类
 * @author Yjian
 * @time 2018-8-6
 */
public class EthUtil {
	
	private static Logger logger = Logger.getLogger(EthUtil.class);
	
	private static String basePath = "E:/ETHtest/file";
	
	public static String basePwd = "8531";
	
	private Web3j web3;
	
	public static EthUtil getEthUtil(String ip,Integer port){
		return new EthUtil(ip, port);
	}
	
	private EthUtil(String ip,Integer port){
		//String url = "https://mainnet.infura.io/v3/91c8b11435f4487b905d8d7cad6ec022";
		//String url = "http://172.16.2.247:8545";
		String url = ip+(port == null || port == 0 ? "":":"+port);
		web3 = Web3JClient.getClient(url);
	}
	
	/**
	 * 创建钱包地址
	 * @param pwd 验签密码
	 * @return
	 */
	public static String createAccount(){
		Credentials credentials = null;
		String fileName = null;
		JSONObject json = new JSONObject();
		try {
			fileName = WalletUtils.generateNewWalletFile(basePwd,new File(basePath),false);
			credentials = WalletUtils.loadCredentials(basePwd, basePath+"/"+fileName);
			json.put("code", 0);
			json.put("key", basePath+"/"+fileName);
			json.put("address", credentials.getAddress());
		} catch (Exception e) {
			logger.error("以太坊钱包地址创建失败", e);
			json.put("code", -1);
		}
		return json.toString();
		
	}
	
	
	/**
	 * ETH交易(通过用户凭证签署交易)
	 * @param number 金额
	 * @param address 地址
	 * @param toAddress 接收地址
	 * @param key 签名文件路径
	 * @param pwd 签名密码
	 * @return
	 */
	public String transaction(Double number,String address, String toAddress,String key) {

		JSONObject result = new JSONObject();
		
		BigInteger nonce = null;
		
		//交易数值转化
		BigInteger value = Convert.toWei(number.toString(), Convert.Unit.ETHER).toBigInteger();
		//矿工费
		BigInteger price = Convert.toWei("2", Unit.GWEI).toBigInteger();
		//最大限制
		BigInteger limit  = Convert.toWei("100", Unit.KWEI).toBigInteger();
		if(verifyBalancel(value,address)){
			try {
				//获取交易随机数
				EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(address,DefaultBlockParameterName.LATEST).sendAsync().get();
				nonce = ethGetTransactionCount.getTransactionCount(); 
				
				//创建交易信息
				RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, price,limit , toAddress, value);
				Credentials ALICE;
				ALICE = WalletUtils.loadCredentials(basePwd,key);
				//交易进行签名和编码
				byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ALICE); 
				String hexValue =  Numeric.toHexString(signedMessage);
				//发送交易获取交易hash
				EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
				String transactionHash = ethSendTransaction.getTransactionHash();
				result.put("code", StatusCode.SUCCESS.getCode());
				result.put("msg", "转账成功");
				result.put("data", transactionHash);
				
			} catch (Exception e) {
				logger.error("以太坊离线交易异常", e);
				result.put("code", StatusCode.SYSTEMERROR.getCode());
				result.put("msg", e.getMessage());
			}finally {
				//关闭节点资源
				web3.shutdown();
			}
		}else{
			result.put("code", StatusCode.E00107.getCode());
			result.put("msg", StatusCode.E00107.getMsg());
		}

		 
		return result.toString();
	}
	
	/**
	 * 获取ETH余额
	 * @param address
	 * @return
	 */
	public String getBalancel(String address){
		String result = "";
		try {
			//获取余额  
			EthGetBalance ethGetBalance1 = web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
			BigInteger value = ethGetBalance1.getBalance();
			result = Convert.fromWei(value.toString(), Convert.Unit.ETHER).toString();
		} catch (Exception e) {
			logger.error("以太坊余额获取失败", e);
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
	public boolean verifyBalancel(BigInteger value,String address){

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
	public String getTransaction(String hash){
		
		JSONObject result = new JSONObject();
		try {
			
			//执行获取交易信息
			EthTransaction ethTransaction = web3.ethGetTransactionByHash(hash).send();
			org.web3j.protocol.core.methods.response.Transaction transaction = ethTransaction.getResult();
			
			if(transaction != null){
				result.put("code", StatusCode.SUCCESS.getCode());
				result.put("msg", "查询成功");
				result.put("data", transaction);
			}else{
				result.put("code", StatusCode.E00100.getCode());
				result.put("msg", StatusCode.E00100.getMsg());
			}
			
		} catch (IOException e) {
			logger.error("以太坊查询交易ID异常", e);
			result.put("code", StatusCode.SYSTEMERROR.getCode());
			result.put("msg", e.getMessage());
		}finally {
			//关闭节点资源
			web3.shutdown();
		}
		
		return result.toString();
	}
	
	
	public static void main(String[] args) throws IOException, CipherException {
		/*String key = "E:/ETHtest/file/UTC--2018-07-25T08-06-39.631000000Z--ffb3430762f92c24a684d634b5516975d7eb267d.json";
		String pwd = "8531";
		String address = "0xffb3430762f92c24a684d634b5516975d7eb267d";
		String toAddress = "0x6b79790e751c13ea9e1c344f0e2dceb683b57dce";

		
		EthUtil ethUtil = EthUtil.getEthUtil("172.16.2.247", 8545);
		
		//String hash = ethUtil.transaction(1.0, address, toAddress, key);
		String blan = ethUtil.getBalancel("0xe5eacd5ec9efd4f65e0689c395a337c0e8bbc206");
		
		System.out.println("交易hash:"+blan);*/
		createAccount();
		
	}
}
