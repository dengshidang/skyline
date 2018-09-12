package com.skyline.clientService.business.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skyline.clientService.business.TradeBusiness;
import com.skyline.clientService.exception.TradeException;
import com.skyline.clientService.mapper.BillinfoMapper;
import com.skyline.clientService.mapper.SeTMarketMapper;
import com.skyline.clientService.mapper.SeTTradeinfoMapper;
import com.skyline.clientService.mapper.SeTUserbalanceMapper;
import com.skyline.clientService.mapper.SeTUserinfoMapper;
import com.skyline.clientService.mapper.SetTraderecordMapper;
import com.skyline.clientService.queue.RabbitConfig;
import com.skyline.common.constant.Constants;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.TradeConstant;
import com.skyline.common.constant.TradeStatusCode;
import com.skyline.common.entity.Billinfo;
import com.skyline.common.entity.SeTMarketEntity;
import com.skyline.common.entity.SeTTradeinfoEntity;
import com.skyline.common.entity.Userbalance;
import com.skyline.common.util.DateUtil;
import com.skyline.common.util.DoubleUtil;
import com.skyline.common.util.OrderNoUtil;
import com.skyline.common.vo.DepthVO2;
import com.skyline.common.vo.MessageEntity;
import com.skyline.common.vo.TradeBuyAndSellVO;
import com.skyline.common.vo.TradeCoinRateVO;
import com.skyline.common.vo.TradeEntrustEntityVO;
import com.skyline.common.vo.TradePrecoinEntityVO;
import com.skyline.common.vo.TradeUserBalanceVO;
import com.skyline.common.vo.TradeUserEntrustVO;

/**
 * 币币交易业务处理类
 * @author yjian dengshidang
 * @time 2018-7-2 ,7.31
 */
@Service
public class TradeBusinessImpl implements TradeBusiness {

	Logger logger = Logger.getLogger(TradeBusinessImpl.class);

	@Autowired
	private SeTMarketMapper seTMarketMapper;

	@Autowired
	private SeTTradeinfoMapper seTTradeinfoMapper;

	@Autowired
	private SeTUserinfoMapper seTUserinfoMapper;

	@Autowired
	private SetTraderecordMapper setTraderecordMapper;

	@Autowired
	private SeTUserbalanceMapper seTUserbalanceMapper;

	@Autowired
	private BillinfoMapper billinfoMapper;

	@Autowired
	private AmqpTemplate  rabbitTemplate;/** rabbitMQ操作对象 **/

	/**
	 * 添加币币交易委托
	 * @param userId 用户id
	 * @param  marketId 行情id
	 * @param price 委托价
	 * @param type 委托方向，0：买，1：卖
	 * @param number 委托数量 
	 * @return 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public Result<?> tradeEntrust(Integer userId,Integer marketId,Double number,Double price,Integer type){

		if(null==userId||null==marketId||null==number||null==price||null==type){
			throw new TradeException(TradeStatusCode.E00401.getCode(), TradeStatusCode.E00401.getMsg());
		}		
		//获取交易行情信息
		SeTMarketEntity marketEntity = seTMarketMapper.selectByPrimaryKey(marketId);		

		if (null==marketEntity) {
			throw new TradeException(TradeStatusCode.E00323.getCode(), TradeStatusCode.E00323.getMsg());
		}
		//获取用户的币种余额信息
		Userbalance seTUserbalance = new Userbalance();
		seTUserbalance.setUserId(userId);
		if(type == TradeConstant.TRADETYPE_BUY){
			//如果是买入,委托设置查询交易币余额信息
			seTUserbalance.setCoinId(marketEntity.getPrecoinId());
		}else{
			//否则设置查询买卖币余额信息
			seTUserbalance.setCoinId(marketEntity.getSufcoinId());
		}

		seTUserbalance = seTUserbalanceMapper.queryUserBalance(seTUserbalance);
		if (null==seTUserbalance) {
			/*余额查询失败，提示重试操作*/
			throw new TradeException(TradeStatusCode.E00310.getCode(), TradeStatusCode.E00310.getMsg());
		}

		//创建委托交易信息
		SeTTradeinfoEntity seTTradeinfo = new SeTTradeinfoEntity();
		seTTradeinfo.setMarketId(marketId);//行情id
		seTTradeinfo.setTotalNum(number);//委托总量
		seTTradeinfo.setPrice(price);//单价		
		seTTradeinfo.setType(type);//方向
		seTTradeinfo.setUserId(userId);//用户id
		seTTradeinfo.setNumber(DoubleUtil.mul(price,number));//交易总金额
		//委托交易信息验证
		if(tradeVerify(seTTradeinfo, marketEntity, seTUserbalance)){
			seTTradeinfo.setOrderNo(OrderNoUtil.getCoinOdrderNo());
			seTTradeinfo.setPrecoinId(marketEntity.getPrecoinId());
			seTTradeinfo.setPrecoinName(marketEntity.getPrecoinName());
			seTTradeinfo.setSufcoinId(marketEntity.getSufcoinId());
			seTTradeinfo.setSufcoinName(marketEntity.getSufcoinName());
			seTTradeinfo.setDealNum(0.0);
			seTTradeinfo.setDealSum(0.0);
			seTTradeinfo.setStatus(TradeConstant.TRADESTATUS_HAN);//默认状态为挂起		
			seTTradeinfo.setCreateTime(DateUtil.getCurTimeString());
			seTTradeinfo.setUpdateTime(DateUtil.getCurTimeString());

			if(seTTradeinfo.getType() == TradeConstant.TRADETYPE_BUY){
				//设置买入手续费
				seTTradeinfo.setFee(DoubleUtil.mul(seTTradeinfo.getTotalNum(), marketEntity.getBuyFee()));
			}else if(seTTradeinfo.getType() == TradeConstant.TRADETYPE_SELL){
				//设置卖出手续费
				seTTradeinfo.setFee(DoubleUtil.mul((DoubleUtil.mul(seTTradeinfo.getTotalNum(),seTTradeinfo.getPrice())),marketEntity.getSellFee()));
			}

			//保存委托交易信息
			Integer i = seTTradeinfoMapper.insert(seTTradeinfo);
			if(i > 0){//保存信息成功
				//冻结用户余额
				changeBalance(seTTradeinfo,"F");
				MessageEntity entity = new MessageEntity();
				entity.setMarketId(seTTradeinfo.getMarketId());
				entity.setOrderNo(seTTradeinfo.getOrderNo());
				entity.setType(seTTradeinfo.getType());
				entity.setPrice(seTTradeinfo.getPrice());
				entity.setNumber(seTTradeinfo.getTotalNum());
				entity.setSendTime(seTTradeinfo.getCreateTime());

				//消息队列信息推送
				rabbitTemplate.convertAndSend(RabbitConfig.exchange,RabbitConfig.binding,entity);
				logger.info("消息推送成功："+entity.toString());
				return Result.successResult();
			}else{
				/*保存委托交易信息失败*/
				throw new TradeException(TradeStatusCode.E00311.getCode(),TradeStatusCode.E00311.getMsg());
			}

		}else{
			/*委托交易信息验证失败*/
			throw new TradeException(TradeStatusCode.E00312.getCode(),TradeStatusCode.E00312.getMsg());
		}

	}	
	/**
	 * 委托交易撤销
	 * @param id 委托交易ID
	 * @param userId
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public Result<?> tradeCancel(Integer userId,Integer id){

		SeTTradeinfoEntity tradeinfo = new SeTTradeinfoEntity();
		tradeinfo.setUserId(userId);
		tradeinfo.setId(id);
		//获取委托交易信息
		tradeinfo = seTTradeinfoMapper.selectOne(tradeinfo);
		//验证委托状态
		if(tradeinfo.getStatus() != TradeConstant.TRADESTATUS_DONE 
				&& tradeinfo.getStatus() != TradeConstant.TRADESTATUS_CALL){
			//设置状态为撤销
			tradeinfo.setStatus(TradeConstant.TRADESTATUS_CALL);			
			tradeinfo.setUpdateTime(DateUtil.getCurTimeString());
			int rs = seTTradeinfoMapper.updateByPrimaryKey(tradeinfo);				
			if(rs > 0){
				//解冻用户余额
				changeBalance(tradeinfo,"U");
			}else{Result.errorResult(TradeStatusCode.E00314.getCode(), TradeStatusCode.E00314.getMsg());}				
		}else{
			throw new TradeException(TradeStatusCode.E00315.getCode(),TradeStatusCode.E00315.getMsg());
		}
		/*返回当前用户委托信息
		TradeUserEntrust userEntrust = new TradeUserEntrust();
		userEntrust.setStatus(TradeConstant.TRADESTATUS_BEING);
		userEntrust.setMarketId(tradeinfo.getMarketId());
		List<TradeUserEntrust> userTradeEntrust = seTTradeinfoMapper.getUserTradeEntrust(userId,userEntrust);				
		if(userTradeEntrust.isEmpty())
			throw new TradeException(TradeStatusCode.E00331.getCode(),TradeStatusCode.E00331.getMsg());
		SeTMarketEntity market= seTMarketMapper.selectByPrimaryKey(tradeinfo.getMarketId());
		if(null==market)
			throw new TradeException(TradeStatusCode.E00309.getCode(),TradeStatusCode.E00309.getMsg());
		for (TradeUserEntrust tradeUser  : userTradeEntrust) {
			tradeUser.setMarketName(market.getMarketName());
		}*/
		return Result.successResult();

	}

	/**
	 * 冻结用户余额
	 * @param tradeinfo
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	public void changeBalance(SeTTradeinfoEntity tradeinfo,String sien){
		Billinfo bill = new Billinfo();
		Userbalance prebalance = new Userbalance();
		prebalance.setUserId(tradeinfo.getUserId());
		if(tradeinfo.getType() == TradeConstant.TRADETYPE_BUY){//交易为买入
			prebalance.setCoinId(tradeinfo.getPrecoinId());//设置获取交易币余额
			bill.setCoinId(tradeinfo.getPrecoinId());//币种ID
			bill.setCoinName(tradeinfo.getPrecoinName());//币种名称
		}else if(tradeinfo.getType() == TradeConstant.TRADETYPE_SELL){//交易为卖出
			prebalance.setCoinId(tradeinfo.getSufcoinId());//设置获取买卖币余额
			bill.setCoinId(tradeinfo.getSufcoinId());//币种ID
			bill.setCoinName(tradeinfo.getSufcoinName());//币种名称
		}
		//获取用户币种余额信息
		prebalance = seTUserbalanceMapper.queryUserBalance(prebalance);

		//个人交易币余额日志
		bill.setUserId(tradeinfo.getUserId());//用户ID
		bill.setForNum(prebalance.getTotalNum());//交易前总额
		bill.setForFrozenNum(prebalance.getFrozenNum());//交易前冻结金额
		bill.setForValidNum(prebalance.getValidNum());//交易前有效金额
		logger.info(tradeinfo.toString());
		if(tradeinfo.getType() == TradeConstant.TRADETYPE_BUY){//交易类型买入
			if(sien.equals("F")){//"F"标示为冻结金额
				prebalance.setFrozenNum(DoubleUtil.add(prebalance.getFrozenNum(),tradeinfo.getNumber()));
				prebalance.setValidNum(DoubleUtil.sub(prebalance.getValidNum(),tradeinfo.getNumber()));
			}else if(sien.equals("U")){//"U"标示为解冻金额
				//解冻金额 = 交易总额 - 以成交金额
				double number = DoubleUtil.sub(tradeinfo.getNumber(),tradeinfo.getDealSum());
				prebalance.setFrozenNum(DoubleUtil.sub(prebalance.getFrozenNum(),number));
				prebalance.setValidNum(DoubleUtil.add(prebalance.getValidNum(),number));
			}

			bill.setNumber(tradeinfo.getNumber());//交易金额
		}else if(tradeinfo.getType() == TradeConstant.TRADETYPE_SELL){//交易类型出售
			if(sien.equals("F")){//"F"标示为冻结金额
				prebalance.setFrozenNum(DoubleUtil.add(prebalance.getFrozenNum(),tradeinfo.getTotalNum()));
				prebalance.setValidNum(DoubleUtil.sub(prebalance.getValidNum(),tradeinfo.getTotalNum()));
			}else if(sien.equals("U")){//"U"标示为解冻金额
				//解冻金额 = 交易总量 - 成交量
				double number = DoubleUtil.sub(tradeinfo.getTotalNum(),tradeinfo.getDealNum());
				prebalance.setFrozenNum(DoubleUtil.add(prebalance.getFrozenNum(),number));
				prebalance.setValidNum(DoubleUtil.sub(prebalance.getValidNum(), number));
			}

			bill.setNumber(tradeinfo.getTotalNum());//交易金额
		}
		bill.setType(Constants.BILLINFO_TYPE_2);//冻结
		bill.setFee(0.0);//手续费默认为0
		bill.setNowNum(prebalance.getTotalNum());//交易后总额
		bill.setNowFrozenNum(prebalance.getFrozenNum());//交易后冻结金额
		bill.setNowValidNum(prebalance.getValidNum());//交易后有效金额
		bill.setBehavior(Constants.BILLINFO_BEHAVIOR_5);//日志行为:币币交易委托
		bill.setStatus(Constants.BILLINFO_STATUS_0);//默认状态
		bill.setCreateTime(DateUtil.getCurTimeString());
		bill.setUpdateTime(DateUtil.getCurTimeString());

		prebalance.setUpdateTime(DateUtil.getCurTimeString());

		//保存用户余额信息
		int updateByPrimaryKey = seTUserbalanceMapper.updateByPrimaryKey(prebalance);
		logger.info(prebalance.toString());
		if(updateByPrimaryKey<1)
			throw new TradeException(TradeStatusCode.E30017.getCode(),TradeStatusCode.E30017.getMsg());
		//用户余额变更记录
		int insert = billinfoMapper.insert(bill);
		if(insert<1)
			throw new TradeException(TradeStatusCode.E30017.getCode(),TradeStatusCode.E30017.getMsg());
	}

	/**
	 * 委托交易验证
	 * @param tradeinfo 交易信息
	 * @param market 行情信息
	 * @return
	 */	
	@Transactional(propagation=Propagation.SUPPORTS)
	private boolean tradeVerify(SeTTradeinfoEntity tradeinfo,SeTMarketEntity market,Userbalance userbalance){

		if(market.getTradeSign() != TradeConstant.MARKETSIGN_ON){//交易状态判断
			//交易已关盘
			throw new TradeException(TradeStatusCode.E00301.getCode(), TradeStatusCode.E00301.getMsg());
		}
		if(tradeinfo.getType() == TradeConstant.TRADETYPE_BUY){//交易类型为买入

			if(DoubleUtil.mul(tradeinfo.getPrice(),tradeinfo.getTotalNum()) > userbalance.getValidNum().doubleValue()){
				//余额不足
				throw new TradeException( TradeStatusCode.E00302.getCode(),  TradeStatusCode.E00302.getMsg());
			}

			if(tradeinfo.getTotalNum().doubleValue() > market.getBuyMax().doubleValue()){
				//超过最大买入数
				throw new TradeException(TradeStatusCode.E00303.getCode(), TradeStatusCode.E00303.getMsg());
			}else if(tradeinfo.getTotalNum().doubleValue() < market.getBuyMin().doubleValue()){
				//未达到最小买入数
				throw new TradeException(TradeStatusCode.E00304.getCode(), TradeStatusCode.E00304.getMsg());
			}

		}
		if(tradeinfo.getType() == TradeConstant.TRADETYPE_SELL){//交易类型为买入卖出

			if(tradeinfo.getTotalNum().doubleValue() > userbalance.getValidNum().doubleValue()){
				//余额不足
				throw new TradeException(TradeStatusCode.E00302.getCode(),TradeStatusCode.E00302.getMsg());
			}

			if(tradeinfo.getTotalNum().doubleValue() > market.getSellMax().doubleValue()){
				//超过最大卖出数
				throw new TradeException( TradeStatusCode.E00305.getCode(), TradeStatusCode.E00305.getMsg());
			}else if(tradeinfo.getTotalNum().doubleValue() < market.getSellMin().doubleValue()){
				//未达到最小卖出数
				throw new TradeException(TradeStatusCode.E00306.getCode(), TradeStatusCode.E00306.getMsg());
			}
		}
		//验证成功
		return true;
	}


	/**
	 * 添加币币交易行情信息
	 * @param seTMarketEntity
	 * @return
	 */
	@Transactional
	public Result<?> addMarket(SeTMarketEntity seTMarketEntity){			
		seTMarketEntity.setCreateTime(DateUtil.getTimeDayHours());
		seTMarketEntity.setUpdateTime(seTMarketEntity.getCreateTime());
		int rs = seTMarketMapper.insert(seTMarketEntity);
		if(rs <0)				
			throw new TradeException(TradeStatusCode.E00307.getCode(),TradeStatusCode.E00307.getMsg());		
		return Result.successResult();

	}


	/**
	 *  获取行情信息
	 * @param precoinId 可交易的币种id,
	 * @param type 按指定类型排序
	 * @param dec 
	 * @param keyword 关键字，搜索
	 * @return
	 */
	public Result<?> getMarket(Integer precoinId,Integer type,Integer dec,String keyword){	
		if(type==null)
			type=1;
		if(dec==null)
			dec=0;
		String sort = (dec==0?"asc":"desc");
		List<TradePrecoinEntityVO> markets = seTMarketMapper.getMarket(precoinId,type,sort,keyword);
		return Result.successResult(markets);	


	}

	/**
	 * 查询用户的交易委托信息
	 * @param userId 用户id
	 * @param marketId 行情ID	
	 * @param status 状态  1：处理中，2 已成交
	 * @return
	 */
	public Result<PageInfo<TradeUserEntrustVO>> getUserTradeEntrust(Integer userId,Integer pageNum,Integer pageSize,TradeUserEntrustVO userEntrust){
		PageHelper.startPage(pageNum, pageSize);
		List<TradeUserEntrustVO> tradeList = seTTradeinfoMapper.getUserTradeEntrust(userId,userEntrust);

		for (TradeUserEntrustVO tradeUserEntrust : tradeList) {
			if(tradeUserEntrust==null ||tradeUserEntrust.getStatus()==0 ||tradeUserEntrust.getStatus()==3)
				continue;
			SeTTradeinfoEntity tradeInfo= seTTradeinfoMapper.selectByPrimaryKey(tradeUserEntrust.getId());
			Map<String, Object> map = setTraderecordMapper.getavgPriceByOrderNo(tradeInfo.getType(),tradeInfo.getOrderNo());
			System.out.println(map);
			if(map!=null){
				BigDecimal big=(BigDecimal) map.get("avgPrice");
				if(big!=null)
					tradeUserEntrust.setAvgPrice(big.doubleValue());
			}
		}
		PageInfo<TradeUserEntrustVO> page = new PageInfo<TradeUserEntrustVO>(tradeList);
		return Result.successResult(page);		
	}

	/**
	 * 获取余额信息
	 * @param userId
	 * @param coinId
	 * @return
	 */
	@Override
	public Result<?> getUserBalance(Integer userId, Integer coinId) {
		if(null==userId)
			throw new TradeException(TradeStatusCode.E00325.getCode(),TradeStatusCode.E00325.getMsg());
		Userbalance userbalance = new Userbalance();
		userbalance.setUserId(userId);
		if(null!=coinId)
			userbalance.setCoinId(coinId);
		userbalance = seTUserbalanceMapper.queryUserBalance(userbalance);
		if(null==userbalance)
			throw new TradeException(TradeStatusCode.E00310.getCode(),TradeStatusCode.E00310.getMsg());
		SeTMarketEntity seTMarketEntity = new SeTMarketEntity();
		seTMarketEntity.setPrecoinId(coinId);
		SeTMarketEntity selectOne = seTMarketMapper.selectOne(seTMarketEntity);
		if(null==selectOne)
			throw new TradeException(TradeStatusCode.E00323.getCode(),TradeStatusCode.E00323.getMsg());
		TradeUserBalanceVO tradeUserBalance = new TradeUserBalanceVO();
		tradeUserBalance.setCoinName(selectOne.getPrecoinName());
		tradeUserBalance.setCoinId(coinId);
		tradeUserBalance.setValidNum(userbalance.getValidNum());
		return Result.successResult(tradeUserBalance);
	}
	/**
	 * 费率详情数据
	 * @param tradeCoinRate
	 * @return
	 */
	@Override
	public Result<PageInfo<TradeCoinRateVO>> getTradeCoinRate(TradeCoinRateVO tradeCoinRate,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TradeCoinRateVO> list = seTMarketMapper.getTradeCoinRate(tradeCoinRate);
		PageInfo<TradeCoinRateVO> page = new PageInfo<>(list );
		return Result.successResult(page) ;
	}
	/**
	 * 获取当前行情信息
	 * @param marketId
	 * @return
	 */
	@Override
	public Result<?> getCurrentMarket(Integer marketId) {
		return Result.successResult(seTMarketMapper.getCurrentMarket(marketId));
	}
	/**
	 * 绘画深度图数据
	 * @param marketId
	 * @return
	 */
	@Override
	public Result<DepthVO2> drawDepths(Integer marketId) {
		List<TradeBuyAndSellVO> buys = seTTradeinfoMapper.drawDepths(marketId,TradeConstant.TRADETYPE_BUY);
		List<TradeBuyAndSellVO> sells = seTTradeinfoMapper.drawDepths(marketId,TradeConstant.TRADETYPE_SELL);

		/*int index1 = 0;
		if(buys!=null&&!buys.isEmpty()){
			for (TradeBuyAndSellVO buy : buys) {
				buy = amount(buys,index1++);
			}
		}
		int index2 = 0;
		if(sells!=null&&!sells.isEmpty()){
			for (TradeBuyAndSellVO sell : sells) {
				sell = amount(sells,index2++);
			}
		}*/
		DepthVO2 depth = new DepthVO2();
		depth.setBuy(buys);
		depth.setSell(sells);
		return Result.successResult(depth);
	}
	//累计数量

	private TradeBuyAndSellVO amount(List<TradeBuyAndSellVO> list,int index) {
		if(list!=null&&!list.isEmpty()){
			double sum = 0.0;
			for(int i =0;i<=index;i++) {
				sum+=list.get(i).getAmount();	 
			}
			list.get(index).setAmount(sum);
		}
		return list.get(index);
	}
}
