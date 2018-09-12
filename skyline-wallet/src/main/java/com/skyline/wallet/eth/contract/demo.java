package com.skyline.wallet.eth.contract;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;

import com.skyline.wallet.eth.Web3JClient;

public class demo {
	private static String url = "http://172.16.2.247:8545";
	
	
	private volatile static Web3j web3j;

    public static Web3j getClient(String ip){
        if(web3j==null){
            synchronized (Web3JClient.class){
                if(web3j==null){
                    web3j = Web3j.build(new HttpService(ip));
                }
            }
        }
        return web3j;
    }
	
	
	public void load() throws Exception{
		Web3j web3j = demo.getClient(url);
		
		//获取用户凭证
		Credentials ALICE = WalletUtils.loadCredentials("密码","keystore文件路径");
		
		//矿工费
		BigInteger price = Convert.toWei("2", Unit.GWEI).toBigInteger();
		//最大限制
		BigInteger limit  = Convert.toWei("100", Unit.KWEI).toBigInteger();
		//加载合约
		ERC20 erc20 = ERC20.load("address", web3j, ALICE, price, limit);
		//部署合约
		//ERC20.deploy(web3j, credentials, gasPrice, gasLimit, initialSupply, tokenName, tokenSymbol);
		
		//交易数值转化
		BigInteger value = Convert.toWei("10", Convert.Unit.ETHER).toBigInteger();
		//转账
		erc20.transfer("toAddress", value).send();
	}
	
	/**
	 * 查询代币余额
	 * @param contractAddr 合约地址
	 * @param fromAddr 钱包地址
	 * @return
	 * @throws IOException
	 */
	public String getBalance(String contractAddr,String fromAddr) throws IOException{
		Web3j web3j = demo.getClient(url);
		
		Function function = new Function(
		        "balanceOf",//查询余额方法名称
		        Arrays.asList(new Address(fromAddr)),
		        Arrays.asList(new TypeReference<Address>(){})
		);
		//交易串
		String tradeStr= FunctionEncoder.encode(function);
		
		//获取代币余额
		String value = web3j.ethCall(Transaction.createEthCallTransaction(fromAddr,contractAddr, tradeStr),
				DefaultBlockParameterName.PENDING).send().getValue();
		
		return value;
	}
	
	/**
	 * 代币转账
	 * @param contractAddr 合约地址
	 * @param fromAddr 转账钱包地址
	 * @param toAddr 收账钱包地址
	 * @param value 转账金额
	 */
	public void trade(String contractAddr,String fromAddr,String toAddr,String value) throws IOException, InterruptedException, ExecutionException{
		Web3j web3j = demo.getClient(url);
		BigInteger nonce = null;
		
		try {
			//获取交易随机数
			EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(fromAddr,DefaultBlockParameterName.LATEST).sendAsync().get();
			nonce = ethGetTransactionCount.getTransactionCount(); 
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//转账金额
		BigInteger v = Convert.toWei(value, Convert.Unit.ETHER).toBigInteger();
		
		Function function = new Function(
		        "transfer",//合约转账方法名称
		        Arrays.asList(new Address(toAddr),new Uint256(v)),
		        Arrays.asList(new TypeReference<Address>(){},new TypeReference<Uint256>(){})
		);
		//交易串
		String tradeStr= FunctionEncoder.encode(function);
		
		//矿工费
		BigInteger price = Convert.toWei("2", Unit.GWEI).toBigInteger();
		//最大限制
		BigInteger limit  = Convert.toWei("100", Unit.KWEI).toBigInteger();
		
		//智能合约事物
		RawTransaction rawTransaction = RawTransaction.createTransaction(nonce,price,limit,"代币地址",tradeStr);
		//获取用户凭证
		Credentials credentials = Credentials.create("私钥");
		//credentials = WalletUtils.loadCredentials("密码","keystore文件路径");

		byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
		String hexValue = Numeric.toHexString(signedMessage);
		//发送事务
		EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
		//交易HASH
		String hash = ethSendTransaction.getTransactionHash();
	}
	
}
