package com.skyline.wallet.btc;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bitcoinj.core.Coin;

import com.googlecode.jsonrpc4j.Base64;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.skyline.common.constant.StatusCode;
import com.skyline.wallet.btc.param.TradeCreateParam;
import com.skyline.wallet.btc.param.TradeSignParam;
import com.skyline.wallet.btc.param.TradeUnused;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 比特币访问工具类
 * @author Yjian
 * @time 2018-8-6
 */
public class CoinUtils {
 
	private static Logger logger = Logger.getLogger(CoinUtils.class);
	
	private JsonRpcHttpClient client;
	
	public static CoinUtils getCoinUtil(String user,String pwd,String ip,Integer port){
		CoinUtils result = null;
		try {
			result = new CoinUtils(user, pwd, ip, port);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
 
	
	private CoinUtils(String RPC_USER,String RPC_PASSWORD,String RPC_ALLOWIP,Integer RPC_PORT) throws Throwable{
		// 身份认证
		String cred = Base64.encodeBytes((RPC_USER + ":" + RPC_PASSWORD).getBytes());
		Map<String, String> headers = new HashMap<String, String>(1);
		headers.put("Authorization", "Basic " + cred);
		client = new JsonRpcHttpClient(new URL(RPC_ALLOWIP+":"+RPC_PORT), headers);
	}
 
 
	
	/**
	 * 验证地址是否存在
	 * @param address
	 * @return
	 * @throws Throwable
	 */
	public String validateaddress(String address)throws Throwable{
		return  (String) client.invoke("validateaddress", new Object[] {address}, Object.class).toString();
	}
	
 
	/**
	 * 如果钱包加密需要临时解锁钱包
	 * @param password
	 * @param time
	 * @return
	 * @throws Throwable
	 */
	public String walletpassphrase(String password,int time)throws Throwable{
		return  (String) client.invoke("walletpassphrase", new Object[] {password,time}, Object.class);
	}
	
	/**
	 * 转账到制定的账户中
	 * @param address
	 * @param amount
	 * @return
	 * @throws Throwable
	 */
	public String sendtoaddress(String address,double amount)throws Throwable{
		return  (String) client.invoke("sendtoaddress", new Object[] {address,amount}, Object.class).toString();
	}
	
	/**
	 * 查询账户下的交易记录
	 * @param account
	 * @param count
	 * @param offset
	 * @return
	 * @throws Throwable
	 */
	public JSONArray listtransactions(String account, int count ,int offset )throws Throwable{
		List<Map<String, Object>> map = (List<Map<String, Object>>)client.invoke("listtransactions", new Object[] {account,count,offset}, Object.class);
		return JSONArray.fromObject(map);
	}
	
	/**
	 * 获取地址下未花费的币量
	 * @param account
	 * @param count
	 * @param offset
	 * @return
	 * @throws Throwable
	 */
	public String listunspent( int minconf ,int maxconf ,String address)throws Throwable{
		String[] addresss= new String[]{address};
		return  (String) client.invoke("listunspent", new Object[] {minconf,maxconf,addresss}, Object.class).toString();
	}
	
	/**
	 * 生成新的接收地址
	 * @return
	 * @throws Throwable
	 */
	public String getNewaddress(){
		JSONObject result = new JSONObject();
		try {
			String address = client.invoke("getnewaddress", new Object[] {}, Object.class).toString();
			result.put("address", address);
			result.put("key", "");
			result.put("code", 0);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			logger.error("创建钱包接收地址失败", e);
			result.put("code", -1);
		}
		return  result.toString();
	}
	
	/**
	 * 获取钱包信息
	 * @return
	 * @throws Throwable
	 */
	public String getInfo() throws Throwable{
		return  client.invoke("getinfo", new Object[] {}, Object.class).toString();
	}
	
	/**
	 * 获取交易区块最高度
	 * @return
	 * @throws Throwable
	 */
	public String getBlockCount() throws Throwable{
		return client.invoke("getblockcount", new Object[] {}, Object.class).toString();
	}
	/**
	 * 获取区块hahe
	 * @param index
	 * @return
	 * @throws Throwable
	 */
	public String getBlockHash(int index) throws Throwable{
		return client.invoke("getblockhash", new Object[] {index}, Object.class).toString();
	}
	
	/**
	 * 获取区块信息
	 * @param hash
	 * @return
	 * @throws Throwable
	 */
	public String getBlock(String hash) throws Throwable{
		Map<String, Object> result = (Map<String, Object>)client.invoke("getblock", new Object[] {hash}, Object.class);
		return JSONObject.fromObject(result).toString();
	}
	
	/**
	 * 获取交易信息
	 * @param hash
	 * @return
	 * @throws Throwable
	 */
	public String getTransaction(String hash){
		JSONObject result = new JSONObject();
		try {
			Map<String, Object> map = (Map<String, Object>)client.invoke("gettransaction", new Object[] {hash}, Object.class);
			result.put("code", StatusCode.SUCCESS.getCode());
			result.put("msg", "查询成功");
			result.put("data", map);
		} catch (Throwable e) {
			logger.error("查询交易ID信息异常", e);
			result.put("code", StatusCode.E00100.getCode());
			result.put("msg", StatusCode.E00100.getMsg());
		}
		return JSONObject.fromObject(result).toString();
	}
	
	/**
	 * 获取钱包可用余额
	 * @return
	 * @throws Throwable
	 */
	public String getbalance() throws Throwable{
		return client.invoke("getbalance", new Object[] {}, Object.class).toString();
	}
	
	/**
	 * 获取未使用的交易信息
	 * @param minconf(1)
	 * @param maxconf(999999)
	 * @return
	 * @throws Throwable
	 */
	public List<TradeUnused> listunspent(int minconf,int maxconf) throws Throwable{
		List<Map<String, Object>> list = (List<Map<String, Object>>)client.invoke("listunspent", new Object[] {minconf,maxconf}, Object.class);
		List<TradeUnused> result = new ArrayList<TradeUnused>(); 
		for(Map<String, Object> map : list){
			JSONObject json = JSONObject.fromObject(map);
			TradeUnused tradeUnused = (TradeUnused)JSONObject.toBean(json, TradeUnused.class);
			result.add(tradeUnused);
		}
		
		return result;
	}
	
	/**
	 * 创建交易
	 * @param param [{\”txid\”:\”交易ID\”,\”vout\”:索引}]
	 * @param addrprice {\”address\”:0.01}
	 * @return
	 * @throws Throwable
	 */
	public String createrawtransaction(TradeCreateParam tradeCreateParam) throws Throwable{
		return client.invoke("createrawtransaction", new Object[] {tradeCreateParam.getTradeArray(),tradeCreateParam.getTradeMap()}, Object.class).toString();
	}
	
	/**
	 * 获取地址的私钥（有加密的得先解密）
	 * @param address
	 * @return
	 * @throws Throwable
	 */
	public String dumpprivkey(String address) throws Throwable{
		return client.invoke("dumpprivkey", new Object[] {address}, Object.class).toString();
	}
	
	/**
	 * 交易签名
	 * @param hex
	 * @param array
	 * @return
	 * @throws Throwable
	 */
	public String signrawtransaction(TradeSignParam param) throws Throwable{
		Map<String, Object> result = (Map<String, Object>)client.invoke("signrawtransaction", new Object[] {param.getHex(),param.getTradeArray(),param.getPrvKeyArray()}, Object.class);
		return JSONObject.fromObject(result).toString();
	}
	
	public String sendrawtransaction(String hex) throws Throwable{
		return client.invoke("sendrawtransaction", new Object[] {hex}, Object.class).toString();
	}
	
	
	/**
	 * 提现转账
	 * @param value 转账金额
	 * @param toAddress 收账地址
	 * @return
	 * @throws Throwable
	 */
	public String trade(Double value,String toAddress){
		
		JSONObject result = new JSONObject();

		double sumAmount = 0;
		String resultHash = null;
		
		try {
			//获取未使用的交易信息
			List<TradeUnused> tradeUnusedList = listunspent(1, 999999);
			//创建交易参数
			List<TradeUnused> paramList = new ArrayList<TradeUnused>();
			//遍历有效未使用交易信息
			for(TradeUnused tradeUnused :tradeUnusedList){
				String txStr = getTransaction(tradeUnused.getTxid());
				//获取未使用的交易余额
				double amount = JSONObject.fromObject(txStr).getJSONObject("data").getDouble("amount");
				if(amount > 0){//交易余额大于0代表当前交易是接收(充值)
					//未使用的交易余额叠加
					sumAmount += amount;
					//添加当前交易为创建新交易的参数
					paramList.add(tradeUnused);
					
					if(sumAmount > value.doubleValue()){//未使用的余额累计大于新交易的金额
						//创建交易
						TradeCreateParam tradeCreateParam = new TradeCreateParam(paramList, toAddress, value.toString());
						String hex = createrawtransaction(tradeCreateParam);
						//解锁获取钱包私钥
						walletpassphrase("yjian8531742", 1000);
						String prvKey = dumpprivkey(tradeUnused.getAddress());
						//交易签名
						TradeSignParam tradeSignParam = new TradeSignParam(hex, tradeUnused, prvKey);
						String signStr = signrawtransaction(tradeSignParam);
						String signHex = JSONObject.fromObject(signStr).getString("hex");
						//广播交易
						resultHash = sendrawtransaction(signHex);
						break;
					}
				}
			}
			
			if(sumAmount > value.doubleValue()){
				result.put("code", StatusCode.SUCCESS.getCode());
				result.put("msg", "转账成功");
				result.put("data", resultHash);
			}else{
				result.put("code", StatusCode.E00107.getCode());
				result.put("msg", StatusCode.E00107.getMsg());
			}
		} catch (Throwable e) {
			logger.error("离线签名交易异常", e);
			result.put("code", StatusCode.SYSTEMERROR.getCode());
			result.put("msg", e.getMessage());
		}
		
		return result.toString();
	}
	
	
	public static void main(String[] args) throws Throwable {
		
		Double sum = 0.0001;
		
		CoinUtils a = CoinUtils.getCoinUtil("yjian", "yjian8531", "http://127.0.0.1", 61315);
		
		JSONArray str = a.listtransactions(null, 1, 3);
		for(int i=0 ; i<str.size() ; i++){
			System.out.println(str.getJSONObject(i).toString());
		}

	}
	
	
}