package com.skyline.wallet.eth;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import org.web3j.utils.Convert.Unit;

import com.skyline.common.constant.StatusCode;

import net.sf.json.JSONObject;

public class ContractUtil {
	
	//private static String url = "https://mainnet.infura.io/v3/91c8b11435f4487b905d8d7cad6ec022";
	private static String url = "http://172.16.2.247:8531";
	//private static String url = "http://39.105.26.249:9099";
	
	private static String filePath = "E://ETHtest/file/";
	
	/**
	 * 获取ETH余额
	 * @param address
	 * @return
	 */
	public static String getBalancel(String address){
		String result = "";
		Web3j web3 = Web3JClient.getClient(url);
		try {
			//获取余额  
			EthGetBalance ethGetBalance1 = web3.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send();
			BigInteger value = ethGetBalance1.getBalance();
			result = Convert.fromWei(value.toString(), Convert.Unit.ETHER).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭节点资源
			web3.shutdown();
		}
		return result;
	}
	
	
	/**
	 * 保存文件
	 * @param filePath 路径
	 * @param content 内容
	 */
	private static void saveAsFileWriter(String filePath,String content) {  
		FileWriter fwriter = null;  
		try {  
		  fwriter = new FileWriter(filePath);  
		  fwriter.write(content);  
		} catch (IOException ex) {  
		  ex.printStackTrace();  
		} finally {  
			try {  
			  fwriter.flush();  
			  fwriter.close();  
			} catch (IOException ex) {  
			  ex.printStackTrace();  
			}	
		}  
	}
	
	/**
	 * 创建钱包地址
	 * @param pwd 验签密码
	 * @return
	 */
	public static String createAccount(String pwd){
		Credentials credentials = null;
		String fileName = null;
		JSONObject json = new JSONObject();
		try {
			fileName = WalletUtils.generateNewWalletFile(pwd,new File(filePath),false);
			credentials = WalletUtils.loadCredentials(pwd, filePath+"/"+fileName);
			json.put("code", 0);
			json.put("key", filePath+"/"+fileName);
			json.put("address", credentials.getAddress());
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", -1);
		}
		return json.toString();
		
	}
	
	/**
	 * 根据私钥加载钱包
	 * @param privateKey 私钥
	 * @return keystore路劲
	 */
	public static String toLead(String privateKey){
		String pwa = "8531";
		ECKeyPair my = ECKeyPair.create(new BigInteger(privateKey,16));
		try {
			WalletFile wf = Wallet.createLight(pwa, my);
			filePath += (wf.getAddress()+".json");
			saveAsFileWriter(filePath, JSONObject.fromObject(wf).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath;
	}
	
	/**
	 * 获取以太坊的交易信息
	 * @param hash 交易ID
	 * @return
	 */
	public static String getTransaction(String hash){
		Web3j web3 = Web3JClient.getClient(url);
		JSONObject result = new JSONObject();
		try {
			
			//执行获取交易信息
			EthTransaction ethTransaction = web3.ethGetTransactionByHash(hash).send();
			org.web3j.protocol.core.methods.response.Transaction transaction = ethTransaction.getResult();
			System.out.println("测试"+transaction);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			//关闭节点资源
			web3.shutdown();
		}
		
		return result.toString();
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
	public static String transaction(Double number,String address, String toAddress,String key,String pwd) {
		Web3j web3 = Web3JClient.getClient(url);
		BigInteger nonce = null;
		String transactionHash = null;
		
		//交易数值转化
		BigInteger value = Convert.toWei(number.toString(), Convert.Unit.ETHER).toBigInteger();
		//矿工费
		BigInteger price = Convert.toWei("20", Unit.GWEI).toBigInteger();
		//最大限制
		BigInteger limit  = Convert.toWei("100", Unit.KWEI).toBigInteger();
		
			try {
				//获取交易随机数
				EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(address,DefaultBlockParameterName.LATEST).sendAsync().get();
				nonce = ethGetTransactionCount.getTransactionCount(); 
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			//创建交易信息
			RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, price,limit , toAddress, value);
			Credentials ALICE;
			try {
				//获取用户凭证
				ALICE = WalletUtils.loadCredentials(pwd,key);
				//交易进行签名和编码
				byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, ALICE); 
				String hexValue =  Numeric.toHexString(signedMessage);
				//发送交易获取交易hash
				EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();
				transactionHash = ethSendTransaction.getTransactionHash();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				//关闭节点资源
				web3.shutdown();
			}
		

		 
		return transactionHash;
	}
	
	public static void main(String[] args) throws IOException, CipherException {
		//获取用户凭证
		Credentials ALICE = WalletUtils.loadCredentials("8531","E://ETHtest/file/8ced5329fb5b67309369ca3cad121067395818aa");
		String result = getBalancel(ALICE.getAddress());
		//String hashId =  transaction(0.001, ALICE.getAddress(), "0xc8562afa26bf6ea6d96170844200fd033ed54593",
		//		"E://ETHtest/file/8ced5329fb5b67309369ca3cad121067395818aa", "8531");
		//String result = getTransaction("0x98eebc5dd8148ebc9a8cdb3e51816b20e4734c3ff8fd69f861d97117231cf8b1");
		System.out.println(result);
	}
	
	/**
	 * 合约转账
	 */
	/*public void trade(){
		// 创建Web3J
        Web3j web3 = Web3j.build(new HttpService("http://127.0.0.1:8545"));
        // 加载钱包
        Credentials credentials = WalletUtils.loadCredentials("钱包密码", "钱包路径");
        // 加载合约
        Key keySCode = Key.load("合约地址", web3, credentials, BigInteger.valueOf(27000000000L), BigInteger.valueOf(250000));
        // 调用转账方法
        TransactionReceipt receipt = keySCode.transfer("目的账户", BigInteger.valueOf(1)).send();
        // 打印交易Hash
        System.out.println(receipt.getTransactionHash());

	}*/
	
	/*public static String aa(){
		
		String result = null;
		try {
			String fileName = WalletUtils.generateNewWalletFile("123456",new File(filePath), false);
            Credentials a = WalletUtils.loadCredentials("123456", filePath+"/"+fileName);
			System.out.println("地址:"+a.getAddress());
			System.out.println("私钥:"+a.getEcKeyPair().getPrivateKey().toString(16));
			result = a.getEcKeyPair().getPrivateKey().toString(16);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}*/
	
	
	
}
