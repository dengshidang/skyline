package com.skyline.wallet.business.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skyline.common.constant.CoinConstant;
import com.skyline.common.constant.Constants;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.constant.SystemConstants;
import com.skyline.common.entity.Billinfo;
import com.skyline.common.entity.CointransactionEntity;
import com.skyline.common.entity.SeTCoininfoEntity;
import com.skyline.common.entity.SeTWalletaddrEntity;
import com.skyline.common.entity.Userbalance;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.entity.WalletinfoEntity;
import com.skyline.common.util.CryptUtil;
import com.skyline.common.util.DateUtil;
import com.skyline.common.util.DoubleUtil;
import com.skyline.common.util.OrderNoUtil;
import com.skyline.common.vo.CointransactionVO;
import com.skyline.wallet.btc.CoinUtils;
import com.skyline.wallet.business.WalletBusiness;
import com.skyline.wallet.eth.EthUtil;
import com.skyline.wallet.exception.BusinessException;
import com.skyline.wallet.filter.RequestUtil;
import com.skyline.wallet.mapper.BillinfoMapper;
import com.skyline.wallet.mapper.CoinTransactionMapper;
import com.skyline.wallet.mapper.SeTCoininfoMapper;
import com.skyline.wallet.mapper.SeTUserinfoMapper;
import com.skyline.wallet.mapper.UserBalanceMapper;
import com.skyline.wallet.mapper.WalletaddrMapper;
import com.skyline.wallet.mapper.WalletinfoMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 钱包业务实现类
 * @author Yjian
 * @time
 */
@Service
public class WalletBusinessImpl implements WalletBusiness{
	
	Logger logger = Logger.getLogger(WalletBusinessImpl.class);

	@Autowired
	private WalletaddrMapper walletaddrMapper;
	
	@Autowired
	private SeTCoininfoMapper coininfoMapper;
	
	@Autowired
	private CoinTransactionMapper coinTransactionMapper;
	
	@Autowired
	private UserBalanceMapper userBalanceMapper;
	
	@Autowired
	private BillinfoMapper billinfoMapper;
	
	@Autowired
	private SeTUserinfoMapper userinfoMapper;
	
	@Autowired
	private WalletinfoMapper walletinfoMapper;
	
	/**
	 * 查询资产充值提现交易记录
	 * @param entity 充值提现实体信息
	 * @param pageNum 页数
	 * @param pageSize 页数大小
	 * @return
	 */
	public Result<?> queryCoinTransaction(CointransactionEntity entity,Integer pageNum,Integer pageSize){
		try{
			PageHelper.startPage(pageNum, pageSize);
			List<CointransactionVO> listData = coinTransactionMapper.queryCoinTransaction(entity);
			PageInfo<CointransactionVO> page = new PageInfo<CointransactionVO>(listData);
			return Result.successResult(page);
		} catch (Exception e) {
			throw new BusinessException(StatusCode.SYSTEMERROR,e.getMessage());
		}
	}
	
	
	/**
	 * 获取用户的币种钱包地址
	 * @param coinId 币种ID
	 * @return
	 */
	public Result<?> getWalletAddress(Integer coinId){

			Userinfo user=RequestUtil.getCurrentUser();
			//获取币种信息
			SeTCoininfoEntity coininfo = coininfoMapper.selectByPrimaryKey(coinId);
			
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("userId", user.getId());
			map.put("walletId", coininfo.getWalletId());
			//根据币种的钱包ID查询当前用户的钱包地址
			SeTWalletaddrEntity walletaddr = walletaddrMapper.queryUserWalletAddress(map);
			//获取钱包服务器信息
			WalletinfoEntity walletinfo = walletinfoMapper.queryWalletByCoin(coinId);
			
			if(walletaddr == null){//钱包地址为空
				
				walletaddr = new SeTWalletaddrEntity();
				
				String addressStr = null;
				if(CoinConstant.ETH.equals(walletinfo.getWalletEng())){
					//创建以太坊钱包地址
					addressStr = EthUtil.createAccount();
				}else if(CoinConstant.BTC.equals(walletinfo.getWalletEng())){
					//创建比特币钱包地址
					CoinUtils coinUtil = CoinUtils.getCoinUtil(walletinfo.getAccount(), walletinfo.getPwd(), walletinfo.getWalletIp(), walletinfo.getWalletPort());
					addressStr = coinUtil.getNewaddress();
				}
				
				JSONObject addressJson = JSONObject.fromObject(addressStr);
				if(addressJson.getInt("code") == 0){
					walletaddr.setAddress(addressJson.getString("address"));
					walletaddr.setAddresskey(addressJson.getString("key"));
					walletaddr.setWalletId(walletinfo.getId());
					walletaddr.setWalletName(walletinfo.getWalletName());
					walletaddr.setUserId(user.getId());
					walletaddr.setStatus(CoinConstant.ADDRESSSTATUS_0);
					walletaddr.setCreateTime(DateUtil.getCurTimeString());
					walletaddr.setUpdateTime(DateUtil.getCurTimeString());
					
					//保存钱包地址
					int i = walletaddrMapper.insert(walletaddr);
					if(i > 0){
						return Result.successResult(walletaddr.getAddress());
					}else{
						throw new BusinessException(StatusCode.E00106);
					}
				}else{
					//钱包地址生成失败
					throw new BusinessException(StatusCode.E00109);
				}
				
			}else{
				return Result.successResult(walletaddr.getAddress());
			}

	}
	
	/**
	 * 根据交易ID检索充值信息
	 * @param tradeHash
	 * @return
	 */
	public Result<?> paySearch(Integer coinId,String tradeHash){
		//获取钱包服务器信息
		WalletinfoEntity walletinfo = walletinfoMapper.queryWalletByCoin(coinId);	
		String tradeStr = null;
		if(CoinConstant.ETH.equals(walletinfo.getWalletEng())){//以太坊
			//查询交易信息(以太坊)
			EthUtil ethUtil = EthUtil.getEthUtil(walletinfo.getWalletIp(), walletinfo.getWalletPort());
			tradeStr = ethUtil.getTransaction(tradeHash);
		}else if(CoinConstant.BTC.equals(walletinfo.getWalletEng())){//比特币
			//查询交易信息(比特币)
			CoinUtils coinUtil = CoinUtils.getCoinUtil(walletinfo.getAccount(), walletinfo.getPwd(), walletinfo.getWalletIp(), walletinfo.getWalletPort());
			tradeStr = coinUtil.getTransaction(tradeHash);
		}
		JSONObject tradejson = JSONObject.fromObject(tradeStr);
		if(tradejson.getString("code").equals(StatusCode.SUCCESS.getCode())){
			StatusCode sc;
			if(CoinConstant.ETH.equals(walletinfo.getWalletEng())){//以太坊
				//ETH检索充值交易
				sc = ethTradeSearch(tradejson.getJSONObject("data"),coinId);
			}else if(CoinConstant.BTC.equals(walletinfo.getWalletEng())){//比特币
				//BTC检索充值交易
				sc = btcTradeSearch(tradejson.getJSONObject("data"),coinId);
			}else{
				sc = StatusCode.SYSTEMERROR;
			}
			
			if(StatusCode.SUCCESS.equals(sc)){
				return Result.successResult();
			}else{
				throw new BusinessException(sc);
			}
			
		}else{
			throw new BusinessException(tradejson.getString("code"),tradejson.getString("msg"));
		}
	}
	
	/**
	 * 以太坊充值交易检索
	 * @param tradeJson
	 * @return
	 */
	public StatusCode ethTradeSearch(JSONObject tradeJson,Integer coinId){
		
		//交易接收地址
		String toAddress = tradeJson.getString("to");
		//交易ID
		String hash = tradeJson.getString("hash");
		//根据接收地址获取对应钱包地址信息
		SeTWalletaddrEntity walletaddr = walletaddrMapper.queryWalletAddress(toAddress);
		
		//获取币种信息
		SeTCoininfoEntity coininfo = coininfoMapper.selectByPrimaryKey(coinId);

		if(walletaddr != null){
			logger.info("有效的充值收账地址:"+toAddress+"----------------------");
			//根据交易ID获取充值记录
			CointransactionEntity cointransaction = coinTransactionMapper.queryCoinTransactionByHash(hash);
			if(cointransaction == null){
				logger.info("有效的充值交易记录:"+hash+"----------------------");
				cointransaction = new CointransactionEntity();
				cointransaction.setOrderNo(OrderNoUtil.getTopupOdrderNo());//充值ID
				cointransaction.setUserId(walletaddr.getUserId());//用户id
				cointransaction.setTxId(hash);//交易ID
				cointransaction.setWalletaddress(toAddress);//接收地址
				cointransaction.setAmount(Convert.fromWei(tradeJson.getString("value"), Convert.Unit.ETHER).doubleValue());//交易金额
				cointransaction.setFee(Convert.fromWei(tradeJson.getString("gasPrice"), Unit.GWEI).doubleValue());//手续费
				cointransaction.setCoinId(coinId);//币种ID
				cointransaction.setCoinName(coininfo.getName());//币种名称
				cointransaction.setCategroy(CoinConstant.COINRECEIVE);//状态为接收
				cointransaction.setStatus(SystemConstants.COINTRANSACTION_STATUS_0);//待审核
				cointransaction.setCreateTime(DateUtil.getCurTimeString());
				cointransaction.setUpdateTime(DateUtil.getCurTimeString());
				//保存充值信息
				coinTransactionMapper.insert(cointransaction);
				return StatusCode.SUCCESS;
			}else{
				logger.info("已存在的充值交易记录:"+hash+"----------------------");
				return StatusCode.E00104;
			}
		}else{
			logger.info("无效的充值收账地址:"+toAddress+"----------------------");
			return StatusCode.E00105;
		}
	}
	
	/**
	 * 比特币类充值交易检索
	 * @param tradeJson
	 * @return
	 */
	public StatusCode btcTradeSearch(JSONObject tradeJson,Integer coinId){
		//交易ID
		String hash = tradeJson.getString("txid");
		JSONArray details = tradeJson.getJSONArray("details");
		
		//获取币种信息
		SeTCoininfoEntity coininfo = coininfoMapper.selectByPrimaryKey(coinId);
		
		int ent = 0;
		
		for(int i=0 ; i<details.size() ; i++){
			JSONObject detail = details.getJSONObject(i);
			if(detail.getString("category").equals("receive")){//交易类型为接收
				//交易接收地址
				String toAddress = tradeJson.getString("to");
				//根据接收地址获取对应钱包地址信息
				SeTWalletaddrEntity walletaddr = walletaddrMapper.queryWalletAddress(toAddress);
				
				if(walletaddr != null){
					//根据交易ID获取充值记录
					CointransactionEntity cointransaction = coinTransactionMapper.queryCoinTransactionByHash(hash);
					if(cointransaction == null){
						cointransaction = new CointransactionEntity();
						cointransaction.setOrderNo(OrderNoUtil.getTopupOdrderNo());//充值ID
						cointransaction.setUserId(walletaddr.getUserId());//用户id
						cointransaction.setTxId(hash);//交易ID
						cointransaction.setWalletaddress(toAddress);//接收地址
						cointransaction.setAmount(tradeJson.getDouble("amount"));//交易金额
						cointransaction.setFee(Double.parseDouble(tradeJson.getString("fee")));//手续费
						cointransaction.setCoinId(coinId);//币种ID
						cointransaction.setCoinName(coininfo.getName());//币种名称
						cointransaction.setCategroy(CoinConstant.COINRECEIVE);//状态为接收
						cointransaction.setStatus(SystemConstants.COINTRANSACTION_STATUS_0);//待审核
						cointransaction.setCreateTime(DateUtil.getCurTimeString());
						cointransaction.setUpdateTime(DateUtil.getCurTimeString());
						//保存充值信息
						coinTransactionMapper.insert(cointransaction);
						ent++;
						
					}
				}
				
			}
		}
		
		if(ent > 0){
			return StatusCode.SUCCESS;
		}else{
			return StatusCode.E00105;
		}

	}
	
	/**
	 * 资产提现申请
	 * @param entity{"coinId":"币种ID","amount":"金额","walletaddress":"钱包地址","remark":"可选备注"}
	 * @param pwd 交易密码
	 * @return
	 */
	public Result<?> appliyTransaction(CointransactionEntity entity,String pwd){
		//从请求里获取用户信息
		Userinfo user=RequestUtil.getCurrentUser();
		//获取用户信息
		user = userinfoMapper.selectByPrimaryKey(user.getId());
		if(CryptUtil.crypt(pwd, "MD5").equals(user.getPayPwd())){//判断用户交易密码
			Userbalance prebalance = new Userbalance();
			prebalance.setUserId(user.getId());
			prebalance.setCoinId(entity.getCoinId());//设置获取交易币余额
			//获取用户币种余额信息
			prebalance = userBalanceMapper.queryUserBalance(prebalance);
			//获取币种信息
			SeTCoininfoEntity coininfo = coininfoMapper.selectByPrimaryKey(entity.getCoinId());
			//判断余额
			if(DoubleUtil.add(entity.getAmount(),coininfo.getFee()).doubleValue() <= prebalance.getValidNum().doubleValue()){
				entity.setCoinName(coininfo.getName());
				entity.setFee(coininfo.getFee());//手续费
				entity.setOrderNo(OrderNoUtil.getTopupOdrderNo());
				entity.setConfirmations(CoinConstant.TRADENUMBER_6);
				entity.setCategroy(CoinConstant.COINSEND);
				entity.setStatus(SystemConstants.COINEXTRACT_STATUS_0);
				entity.setUserId(user.getId());
				entity.setCreateTime(DateUtil.getCurTimeString());
				entity.setUpdateTime(DateUtil.getCurTimeString());
				int i = coinTransactionMapper.insert(entity);
				if(i > 0){
					//保存成功更新
					changeBalance(entity,prebalance);
					return Result.successResult();
				}else{
					throw new BusinessException(StatusCode.E00106);
				}
					
			}else{
				throw new BusinessException(StatusCode.E00107);
			}
		}else{
			throw new BusinessException(StatusCode.E00108);
		}
		
	}
	
	/**
	 * 根据交易ID查询交易信息
	 * @param hash 交易ID
	 * @param coinId 币种ID
	 * @return
	 */
	public Result<?> getTransactionMsg(String hash,Integer coinId){
		
		
		//获取钱包服务器信息
		WalletinfoEntity walletinfo = walletinfoMapper.queryWalletByCoin(coinId);
			
		if(CoinConstant.ETH.equals(walletinfo.getWalletEng())){//以太坊
			//查询交易信息(以太坊)
			EthUtil ethUtil = EthUtil.getEthUtil(walletinfo.getWalletIp(), walletinfo.getWalletPort());
			String tradeStr = ethUtil.getTransaction(hash);
			return Result.successResult(tradeStr);
		}else if(CoinConstant.BTC.equals(walletinfo.getWalletEng())){//比特币
			//查询交易信息(比特币)
			CoinUtils coinUtil = CoinUtils.getCoinUtil(walletinfo.getAccount(), walletinfo.getPwd(), walletinfo.getWalletIp(), walletinfo.getWalletPort());
			String tradeStr = coinUtil.getTransaction(hash);
			return Result.successResult(tradeStr);
		}else{
			return Result.successResult("接口暂未对接");
		}

	}
	
	/**
	 * 充值提现审核
	 * @param entity
	 * @return
	 */
	public Result<?> auditCointransaction(CointransactionEntity entity){
		
		CointransactionEntity cointransaction = coinTransactionMapper.selectByPrimaryKey(entity.getId());
		//修改审核状态
		cointransaction.setStatus(entity.getStatus());
		cointransaction.setRemark(entity.getRemark());
		cointransaction.setUpdateTime(DateUtil.getCurTimeString());
		
		if(cointransaction.getStatus() == SystemConstants.COINTRANSACTION_STATUS_1){
			if(cointransaction.getCategroy() == CoinConstant.COINSEND){//提现
				//提现审核成功需要发起转账交易
				String result = sendTrade(cointransaction);
				JSONObject tradeJson = JSONObject.fromObject(result);
				if(tradeJson.getString("code").equals(StatusCode.SUCCESS.getCode())){//提现转币成功
					//添加交易ID信息
					cointransaction.setTxId(tradeJson.getString("data"));
				}else{
					throw new BusinessException(tradeJson.getString("code"),tradeJson.getString("msg"));
				}
			}
		}
		
		//更新充值信息
		int i = coinTransactionMapper.updateByPrimaryKey(cointransaction);
		if(i > 0){//保存成功
				
			if(cointransaction.getStatus() == SystemConstants.COINTRANSACTION_STATUS_1){
				//审核通过跟新用户对应的币种余额
				changeBalance(cointransaction,null);
			}
			return Result.successResult(StatusCode.SUCCESS);
		}else{
			throw new BusinessException(StatusCode.E00106);
		}
	}
	
	/**
	 * 发送转账交易
	 * @param cointransaction
	 */
	public String sendTrade(CointransactionEntity cointransaction){
		WalletinfoEntity walletinfo = walletinfoMapper.queryWalletByCoin(cointransaction.getCoinId());
		//发送交易返回结果
		String tradeStr;
		
		//判断收账地址是否是我们账号里面的信息
		/*SeTWalletaddrEntity Walletaddr = walletaddrMapper.queryWalletAddress(cointransaction.getWalletaddress());
		if(Walletaddr != null){
			
		}else*/ 
		if(CoinConstant.ETH.equals(walletinfo.getWalletEng())){//以太坊
			//获取钱包服务器信息
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("userId", cointransaction.getUserId());
			map.put("walletId", walletinfo.getId());
			SeTWalletaddrEntity walletaddr = walletaddrMapper.queryUserWalletAddress(map);
			
			//获取用户信息
			Userinfo user = userinfoMapper.selectByPrimaryKey(cointransaction.getUserId());
			
			//查询交易信息(以太坊)
			EthUtil ethUtil = EthUtil.getEthUtil(walletinfo.getWalletIp(), walletinfo.getWalletPort());
			tradeStr = ethUtil.transaction(cointransaction.getAmount(), walletaddr.getAddress(), 
					cointransaction.getWalletaddress(), walletaddr.getAddresskey());

		}else if(CoinConstant.BTC.equals(walletinfo.getWalletEng())){//比特币
			//查询交易信息(比特币)
			CoinUtils coinUtil = CoinUtils.getCoinUtil(walletinfo.getAccount(), walletinfo.getPwd(), walletinfo.getWalletIp(), walletinfo.getWalletPort());
			tradeStr = coinUtil.trade(cointransaction.getAmount(), cointransaction.getWalletaddress());

		}else{
			JSONObject result = new JSONObject();
			result.put("code", StatusCode.E00102.getCode());
			result.put("msg", StatusCode.E00102.getMsg());
			tradeStr = result.toString();
		}
		
		return tradeStr;
	}
	
	/**
	 * 余额更新
	 * @param cointransaction 充值提现记录
	 */
	public void changeBalance(CointransactionEntity cointransaction,Userbalance prebalance){
		
		if(prebalance ==null){
			prebalance = new Userbalance();
			prebalance.setUserId(cointransaction.getUserId());		
			prebalance.setCoinId(cointransaction.getCoinId());//设置获取交易币余额
			//获取用户币种余额信息
			prebalance = userBalanceMapper.queryUserBalance(prebalance);
		}
		//个人交易币余额日志
		Billinfo bill = new Billinfo();
		bill.setCoinId(cointransaction.getCoinId());//币种ID
		bill.setCoinName(cointransaction.getCoinName());//币种名称
		bill.setUserId(cointransaction.getUserId());//用户ID
		bill.setForNum(prebalance.getTotalNum());//交易前总额
		bill.setForFrozenNum(prebalance.getFrozenNum());//交易前冻结金额
		bill.setForValidNum(prebalance.getValidNum());//交易前有效金额
		
		if(cointransaction.getCategroy() == CoinConstant.COINRECEIVE){//充值
			//资产余额充值
			prebalance.setValidNum(DoubleUtil.add(prebalance.getValidNum(),cointransaction.getAmount()));
			prebalance.setTotalNum(DoubleUtil.add(prebalance.getTotalNum(),cointransaction.getAmount()));
			bill.setType(Constants.BILLINFO_TYPE_0);//收入
			bill.setBehavior(Constants.BILLINFO_BEHAVIOR_3);//日志行为:充值
		}else if(cointransaction.getCategroy() == CoinConstant.COINSEND){//提现
			//提现跟新余额=提现金额+手续费
			double amount = DoubleUtil.add(cointransaction.getAmount(),cointransaction.getFee());
			if(cointransaction.getStatus() == SystemConstants.COINEXTRACT_STATUS_0){//待审核
				
				prebalance.setFrozenNum(DoubleUtil.add(prebalance.getFrozenNum(),amount));
				prebalance.setValidNum(DoubleUtil.sub(prebalance.getValidNum(),amount));
				bill.setType(Constants.BILLINFO_TYPE_2);//冻结
				
			}else if(cointransaction.getStatus() == SystemConstants.COINEXTRACT_STATUS_1){//审核成功
				
				prebalance.setFrozenNum(DoubleUtil.sub(prebalance.getFrozenNum(),amount));
				prebalance.setTotalNum(DoubleUtil.sub(prebalance.getTotalNum(),amount));
				bill.setType(Constants.BILLINFO_TYPE_1);//支出
			}
			bill.setBehavior(Constants.BILLINFO_BEHAVIOR_4);//日志行为:提现
		}
		bill.setNumber(cointransaction.getAmount());//交易金额
		bill.setFee(0.0);//手续费默认为0
		bill.setNowNum(prebalance.getTotalNum());//交易后总额
		bill.setNowFrozenNum(prebalance.getFrozenNum());//交易后冻结金额
		bill.setNowValidNum(prebalance.getValidNum());//交易后有效金额
		bill.setStatus(Constants.BILLINFO_STATUS_0);//默认状态
		bill.setCreateTime(DateUtil.getCurTimeString());
		bill.setUpdateTime(DateUtil.getCurTimeString());
		
		prebalance.setUpdateTime(DateUtil.getCurTimeString());
		
		//保存用户余额信息
		userBalanceMapper.updateByPrimaryKey(prebalance);
		//用户余额变更记录
		billinfoMapper.insert(bill);
	}
	
	/**
	 * 内部提现转账实现
	 * @param Walletaddr 收账用户的地址信息
	 * @param cointransaction 提现信息
	 */
	private void topupInterior(SeTWalletaddrEntity Walletaddr,CointransactionEntity cointransaction){
		CointransactionEntity entity = new CointransactionEntity();
		entity.setOrderNo(OrderNoUtil.getTopupOdrderNo());//充值ID
		entity.setUserId(Walletaddr.getUserId());//用户id
		entity.setTxId("0x000000000000000000000000");//交易ID
		entity.setWalletaddress(Walletaddr.getAddress());//接收地址
		entity.setAmount(cointransaction.getAmount());//交易金额
		entity.setFee(cointransaction.getFee());//手续费
		entity.setCoinId(cointransaction.getCoinId());//币种ID
		entity.setCoinName(cointransaction.getCoinName());//币种名称
		entity.setCategroy(CoinConstant.COINRECEIVE);//状态为接收
		entity.setStatus(SystemConstants.COINTRANSACTION_STATUS_1);//审核成功
		entity.setCreateTime(DateUtil.getCurTimeString());
		entity.setUpdateTime(DateUtil.getCurTimeString());
		//保存充值信息
		coinTransactionMapper.insert(cointransaction);

	}

}
