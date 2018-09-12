package com.skyline.service.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.skyline.common.constant.Constants;
import com.skyline.common.constant.TradeConstant;
import com.skyline.common.entity.Billinfo;
import com.skyline.common.entity.SeTMarketEntity;
import com.skyline.common.entity.SeTTradeinfoEntity;
import com.skyline.common.entity.SeTTraderecordEntity;
import com.skyline.common.entity.Userbalance;
import com.skyline.common.util.DateUtil;
import com.skyline.common.util.DoubleUtil;
import com.skyline.common.util.OrderNoUtil;
import com.skyline.common.vo.MessageEntity;
import com.skyline.common.vo.MessageList;
import com.skyline.service.mapper.BillinfoMapper;
import com.skyline.service.mapper.SeTMarketMapper;
import com.skyline.service.mapper.SeTTradeinfoMapper;
import com.skyline.service.mapper.SeTUserbalanceMapper;
import com.skyline.service.mapper.SetTraderecordMapper;
import com.skyline.service.util.ListSortUtil;
/**
 * 业务撮合入口
 * @author dengshidang
 *
 */
@Component
@RabbitListener(queues = RabbitConfig.queue)
@Transactional
public class RabbitInfo {


	Logger logger = Logger.getLogger(RabbitInfo.class);

	@Autowired
	private SeTMarketMapper seTMarketMapper;

	@Autowired
	private SeTTradeinfoMapper seTTradeinfoMapper;

	@Autowired
	private SeTUserbalanceMapper seTUserbalanceMapper;

	@Autowired
	private SetTraderecordMapper setTraderecordMapper;

	public static Map<Integer, MessageList> queueMap = new HashMap<Integer, MessageList>();

	volatile AtomicBoolean atomicBoolean = new AtomicBoolean(false);
	@Autowired
	private RabbitInfo rabbitInfo;
	@Autowired
	private BillinfoMapper billinfoMapper;
	@Autowired
	private AmqpTemplate  rabbitTemplate;/** rabbitMQ操作对象 **/

	/**
	 * 接收撮合消息
	 * @param entity 委托交易信息实体
	 */
	@RabbitHandler
	public void process(MessageEntity entity){
		logger.info("接收撮合消息："+entity.toString());
		queueMsgAdd(entity);

	}
	/**
	 * 添加信息到智能戳和队列
	 * @param entity 委托交易信息实体
	 */

	public void queueMsgAdd(MessageEntity entity){

		MessageList messageList = queueMap.get(entity.getMarketId());
		if(messageList == null){
			messageList = new MessageList();
			queueMap.put(entity.getMarketId(), messageList);
		}

		if(entity.getType() == TradeConstant.TRADETYPE_BUY){
			List<MessageEntity> buyList = messageList.getBuyList();
			if(buyList == null){
				buyList = new ArrayList<MessageEntity>();
			}
			//buyList = buyAdd(buyList, entity);
			buyList.add(entity);
			ListSortUtil.sort(buyList, false,"price","sendTime");
			messageList.setBuyList(buyList);

		}else if(entity.getType() == TradeConstant.TRADETYPE_SELL){
			List<MessageEntity> sellList = messageList.getSellList();
			if(sellList == null){
				sellList = new ArrayList<MessageEntity>();
			}
			//sellList = sellAdd(sellList, entity);
			sellList.add(entity);
			ListSortUtil.sort(sellList, true, "price","sendTime");
			messageList.setSellList(sellList);
		}

		//戳和交易触发
		calling(messageList);
		
	}

	/**
	 * 队列信息更新
	 * @param MarketId
	 * @param buyEntity
	 * @param sellEntity
	 */
	public void queueMsgUpdate(Integer MarketId,MessageEntity buyEntity,MessageEntity sellEntity){
		MessageList messageList = queueMap.get(MarketId);
		List<MessageEntity> buyList = messageList.getBuyList();
		List<MessageEntity> sellList = messageList.getSellList();
		buyList.remove(buyEntity);		
		if(buyEntity != null){
			buyList.add(0, buyEntity);
		}
		sellList.remove(sellEntity);
		if(sellEntity != null){
			sellList.add(0, sellEntity);
		}
		//戳和交易触发
		if((buyList != null && buyList.size() > 0) && (sellList != null && sellList.size() > 0))
			rabbitInfo.capacityTrade(buyList.get(0), sellList.get(0));
	}

	/**
	 * 智能戳和交易触发
	 * @param MessageList
	 */
	public void calling(MessageList messageList){
		
		List<MessageEntity> buyList = messageList.getBuyList();
		List<MessageEntity> sellList = messageList.getSellList();
		if((buyList != null && buyList.size() > 0) && (sellList != null && sellList.size() > 0)){
			rabbitInfo.capacityTrade(buyList.get(0), sellList.get(0));
		}
	}

	/**
	 * 撮合交易
	 * @param buyEntity 购买委托信息
	 * @param sellEntity 出售委托信息
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void capacityTrade(MessageEntity buyEntity,MessageEntity sellEntity){	
		if(sellEntity.getPrice()<= buyEntity.getPrice()){//判断买卖价格是否比配
			//获取交易行情信息
			SeTMarketEntity market = seTMarketMapper.selectByPrimaryKey(sellEntity.getMarketId());
			//获取购买的委托信息
			SeTTradeinfoEntity buyTradeinfo = seTTradeinfoMapper.queryTradeByOrderNo(buyEntity.getOrderNo());
			//获取出售的委托信息
			SeTTradeinfoEntity sellTradeinfo = seTTradeinfoMapper.queryTradeByOrderNo(sellEntity.getOrderNo());
			if(sellTradeinfo==null||buyTradeinfo==null)
				//清除无效订单
				handlerInvalidOrder(sellEntity,buyEntity,sellTradeinfo,buyTradeinfo,market);
			//声明交易成交记录
			else{
				SeTTraderecordEntity traderecord = new SeTTraderecordEntity();
				traderecord.setMarketId(sellEntity.getMarketId());//成交行情id
				traderecord.setOrderNo(OrderNoUtil.getMabOdrderNo());//成交订单号			
				traderecord.setEnterorderNo(buyEntity.getOrderNo());//买入交易订单编号
				traderecord.setOutorderNo(sellEntity.getOrderNo());//卖出交易订单编号
				traderecord.setOutcoinId(market.getPrecoinId());//卖出币ID
				traderecord.setOutcoinName(market.getPrecoinName());//卖出币名称			
				traderecord.setEntercoinId(market.getSufcoinId());//买入币ID
				traderecord.setEntercoinName(market.getSufcoinName());//买入币名称	
				System.out.println("sellEntity.getPrice():"+sellEntity.getPrice());
				traderecord.setPrice(sellEntity.getPrice());//设置交易价格
				traderecord.setStatus(TradeConstant.RECORDSTATUS_VALID);//默认有效状态
				traderecord.setCreateTime(DateUtil.getCurTimeString());
				traderecord.setUpdateTime(traderecord.getCreateTime());

				if(sellEntity.getNumber() > buyEntity.getNumber()){//出售数量大于购买数量
					//设置买入数量为交易数
					traderecord.setNumber(buyEntity.getNumber());

				}else{//出售数量等于购买数量

					//设置出售数量为交易数
					traderecord.setNumber(sellEntity.getNumber());
				}

				int round = market.getRound();//获取精准小数位
				//TODO:机器人不收手续费，
				//计算交易总金额
				double money = DoubleUtil.mul(traderecord.getPrice(),traderecord.getNumber());
				//计算卖出手续费
				double outFee = DoubleUtil.mul(traderecord.getNumber(),market.getSellFee());
				//计算买入手续费
				double enterFee = DoubleUtil.mul(money,market.getBuyFee());

				BigDecimal moneyBD = new BigDecimal(money);
				money = moneyBD.setScale(round, BigDecimal.ROUND_DOWN).doubleValue();
				traderecord.setMoney(money);//交易总金额

				BigDecimal outFeeBD = new BigDecimal(outFee);
				outFee = outFeeBD.setScale(round, BigDecimal.ROUND_DOWN).doubleValue();
				traderecord.setOutFee(outFee);//卖出手续费

				BigDecimal enterFeeBD = new BigDecimal(enterFee);
				enterFee = enterFeeBD.setScale(round, BigDecimal.ROUND_DOWN).doubleValue();
				traderecord.setEnterFee(enterFee);//买入手续费
				//保存币币交易成交记录
				setTraderecordMapper.insert(traderecord);
				//同步更新购买委托交易数据
				synTradeInfo(traderecord, buyTradeinfo);
				//同步更新出售委托交易数据
				synTradeInfo(traderecord, sellTradeinfo);

				logger.info("撮合达成: "+sellEntity.getOrderNo()+"---------→"+buyEntity.getOrderNo());
				//计算队列数据更新
				Double number = traderecord.getNumber();//成交数量
				//如果数量相同，则将其从队列中清除
				if(buyEntity.getNumber() == sellEntity.getNumber()){
					buyEntity = null;
					sellEntity = null;
				}
				//如果购买数量小于出售数量，则将订单从队列中清除，并将出售订单 重新撮合;
				else if(buyEntity.getNumber() < sellEntity.getNumber()){
					buyEntity = null;
					sellEntity.setNumber(DoubleUtil.sub(sellEntity.getNumber(),number));
				}
				//同上原理：
				else if(buyEntity.getNumber() > sellEntity.getNumber()){
					sellEntity = null;
					buyEntity.setNumber(DoubleUtil.sub(buyEntity.getNumber(),number));
				}
				//更新队列
				queueMsgUpdate(market.getId(), buyEntity, sellEntity);

			}
			
		}
		//价格不匹配，重新排序
		else{
			
			
		}

	}

	/**
	 * 处理无效撮合订单
	 * @param sellEntity 委托卖单信息
	 * @param sellTradeinfo 委托卖单详情
	 * @param buyEntity 委托买单信息
	 * @param buyTradeinfo  委托买单详情
	 * 
	 */
	private void handlerInvalidOrder(
			MessageEntity sellEntity,
			MessageEntity buyEntity,
			SeTTradeinfoEntity sellTradeinfo, 
			SeTTradeinfoEntity buyTradeinfo, 
			SeTMarketEntity market) {
		SeTTraderecordEntity traderecord = new SeTTraderecordEntity();
		traderecord.setMarketId(sellEntity.getMarketId());//成交行情id
		traderecord.setOrderNo(OrderNoUtil.getMabOdrderNo());//成交订单号			
		traderecord.setEnterorderNo(buyEntity.getOrderNo());//买入交易订单编号
		traderecord.setOutorderNo(sellEntity.getOrderNo());//卖出交易订单编号
		traderecord.setOutcoinId(market.getPrecoinId());//卖出币ID
		traderecord.setOutcoinName(market.getPrecoinName());//卖出币名称			
		traderecord.setEntercoinId(market.getSufcoinId());//买入币ID
		traderecord.setEntercoinName(market.getSufcoinName());//买入币名称			
		traderecord.setPrice(buyEntity.getPrice());//购买价格
		traderecord.setStatus(TradeConstant.RECORDSTATUS_VOID);//设置无效交易
		traderecord.setCreateTime(DateUtil.getCurTimeString());
		traderecord.setUpdateTime(traderecord.getCreateTime());

		String sell = sellTradeinfo==null?":订单号 <"+sellEntity.getOrderNo()+">无效或不存在！":"";
		String buy  = buyTradeinfo ==null?":订单号 <"+buyEntity.getOrderNo()+">  无效或不存在！":"";
		logger.info("撮合失败 ！"+DateUtil.getCurTimeString()+"撮合订单为："+sellEntity.getOrderNo()+"---------→"+buyEntity.getOrderNo());
		logger.info("其中  "+buy+":"+sell);
		
		//清除无效撮合消息，将有效撮合重新加入队列
		MessageList messageList = queueMap.get(market.getId());
		List<MessageEntity> buyList = messageList.getBuyList();
		List<MessageEntity> sellList = messageList.getSellList();
		buyList.remove(buyEntity);
		sellList.remove(sellEntity);
		if(buyTradeinfo != null)
			queueMsgAdd(buyEntity);
		if(sellTradeinfo != null)
			queueMsgAdd(sellEntity);
	}

	/**
	 * 同步委托交易数据
	 * @param traderecord 交易成交记录
	 * @param tradeinfo 委托交易信息
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	private void synTradeInfo(SeTTraderecordEntity traderecord,SeTTradeinfoEntity tradeinfo){
		//委托信息更新
		tradeinfo.setDealNum(DoubleUtil.add(tradeinfo.getDealNum(),traderecord.getNumber()));//成交数量
		tradeinfo.setDealSum(DoubleUtil.add(tradeinfo.getDealSum(),traderecord.getMoney()));//成交金额累计		
		tradeinfo.setFee(traderecord.getEnterFee());//手续费用
		tradeinfo.setUpdateTime(DateUtil.getCurTimeString());//更新
		if(tradeinfo.getDealNum().doubleValue() == tradeinfo.getTotalNum().doubleValue()){//成交量等于委托交易总量
			//订单状态为完成
			tradeinfo.setStatus(TradeConstant.TRADESTATUS_DONE);
		}else if(tradeinfo.getDealNum().doubleValue() < tradeinfo.getTotalNum().doubleValue()){
			//订单状态为处理中
			tradeinfo.setStatus(TradeConstant.TRADESTATUS_BEING);
		}
		//保存更新信息
		seTTradeinfoMapper.updateByPrimaryKey(tradeinfo);

		//同步更新用户余额信息
		changeUserBalance(traderecord, tradeinfo);

	}

	/**
	 * 个人账户余额更新
	 * @param traderecord
	 * @param tradeinfo
	 */

	public void changeUserBalance(SeTTraderecordEntity traderecord,SeTTradeinfoEntity tradeinfo){
		//获取用户的交易币余额信息
		Userbalance prebalance = new Userbalance();
		prebalance.setUserId(tradeinfo.getUserId());
		prebalance.setCoinId(tradeinfo.getPrecoinId());
		prebalance = seTUserbalanceMapper.queryUserBalance(prebalance);
		//个人交易币余额日志
		Billinfo preBill = new Billinfo();
		preBill.setOrderNo(traderecord.getOrderNo());//交易订单号
		preBill.setUserId(tradeinfo.getUserId());//用户ID
		preBill.setCoinId(tradeinfo.getPrecoinId());//币种ID
		preBill.setCoinName(tradeinfo.getPrecoinName());//币种名称
		preBill.setPayCoinId(tradeinfo.getSufcoinId());//支付币种ID
		preBill.setPayCoinName(tradeinfo.getSufcoinName());//支付币种名称
		preBill.setForNum(prebalance.getTotalNum());//交易前总额
		preBill.setForFrozenNum(prebalance.getFrozenNum());//交易前冻结金额
		preBill.setForValidNum(prebalance.getValidNum());//交易前有效金额

		//获取用户的买卖币余额信息
		Userbalance sufbalance = new Userbalance();
		sufbalance.setUserId(tradeinfo.getUserId());
		sufbalance.setCoinId(tradeinfo.getSufcoinId());
		sufbalance = seTUserbalanceMapper.queryUserBalance(sufbalance);
		//个人买卖币余额日志
		Billinfo sufBill = new Billinfo();
		sufBill.setOrderNo(traderecord.getOrderNo());//交易订单号
		sufBill.setUserId(tradeinfo.getUserId());//用户ID
		sufBill.setCoinId(tradeinfo.getSufcoinId());//币种ID
		sufBill.setCoinName(tradeinfo.getSufcoinName());//币种名称
		preBill.setPayCoinId(tradeinfo.getPrecoinId());//支付币种ID
		preBill.setPayCoinName(tradeinfo.getPrecoinName());//支付币种名称
		sufBill.setForNum(sufbalance.getTotalNum());//交易前总额
		sufBill.setForFrozenNum(sufbalance.getFrozenNum());//交易前冻结金额
		sufBill.setForValidNum(sufbalance.getValidNum());//交易前有效金额

		if(tradeinfo.getType() == TradeConstant.TRADETYPE_BUY){//交易类型为购买
			//交易币冻结金额 - 成交金额
			prebalance.setFrozenNum(DoubleUtil.sub(prebalance.getFrozenNum(),traderecord.getMoney()));
			//交易币总金额 - 成交金额
			prebalance.setTotalNum(DoubleUtil.sub(prebalance.getTotalNum(),traderecord.getMoney()));
			preBill.setType(Constants.BILLINFO_TYPE_1);//支出
			preBill.setNumber(traderecord.getMoney());//交易金额
			preBill.setFee(0.0);//手续费默认为0
			preBill.setNowNum(prebalance.getTotalNum());//交易后总额
			preBill.setNowFrozenNum(prebalance.getFrozenNum());//交易后冻结金额
			preBill.setNowValidNum(prebalance.getValidNum());//交易后有效金额
			preBill.setBehavior(Constants.BILLINFO_BEHAVIOR_6);//日志行为:币币交易成交
			preBill.setStatus(Constants.BILLINFO_STATUS_0);//默认状态
			preBill.setCreateTime(DateUtil.getCurTimeString());
			preBill.setUpdateTime(DateUtil.getCurTimeString());

			//用户成交数量-手续费=收益金额
			double number = DoubleUtil.sub(traderecord.getNumber(),traderecord.getEnterFee());
			//买卖币有效金额 + 成交数量
			sufbalance.setValidNum(DoubleUtil.add(sufbalance.getValidNum(),number));
			//买卖币总金额 + 成交数量
			sufbalance.setTotalNum(DoubleUtil.add(sufbalance.getTotalNum(),number));
			sufBill.setType(Constants.BILLINFO_TYPE_0);//收入
			sufBill.setNumber(traderecord.getNumber());//交易金额
			sufBill.setFee(traderecord.getEnterFee());//手续费
			sufBill.setNowNum(sufbalance.getTotalNum());//交易后总额
			sufBill.setNowFrozenNum(sufbalance.getFrozenNum());//交易后冻结金额
			sufBill.setNowValidNum(sufbalance.getValidNum());//交易后有效金额
			sufBill.setBehavior(Constants.BILLINFO_BEHAVIOR_6);//日志行为:币币交易成交
			sufBill.setStatus(Constants.BILLINFO_STATUS_0);//默认状态
			sufBill.setCreateTime(DateUtil.getCurTimeString());
			sufBill.setUpdateTime(DateUtil.getCurTimeString());
		}else if(tradeinfo.getType() == TradeConstant.TRADETYPE_SELL){
			//用户成交金额-手续费=收益金额
			double number = DoubleUtil.sub(traderecord.getMoney(),traderecord.getOutFee());
			//交易币有效金额 + 成交金额
			prebalance.setValidNum(DoubleUtil.add(prebalance.getValidNum(),number));
			//交易币总金额 + 成交金额
			prebalance.setTotalNum(DoubleUtil.add(prebalance.getTotalNum(),number));
			preBill.setType(Constants.BILLINFO_TYPE_0);//收入
			preBill.setNumber(traderecord.getMoney());//交易金额
			preBill.setFee(traderecord.getOutFee());//手续费
			preBill.setNowNum(prebalance.getTotalNum());//交易后总额
			preBill.setNowFrozenNum(prebalance.getFrozenNum());//交易后冻结金额
			preBill.setNowValidNum(prebalance.getValidNum());//交易后有效金额
			preBill.setBehavior(Constants.BILLINFO_BEHAVIOR_6);//日志行为:币币交易成交
			preBill.setStatus(Constants.BILLINFO_STATUS_0);//默认状态
			preBill.setCreateTime(DateUtil.getCurTimeString());
			preBill.setUpdateTime(DateUtil.getCurTimeString());

			//买卖币冻结金额 - 成交数量
			sufbalance.setFrozenNum(DoubleUtil.sub(sufbalance.getFrozenNum(),traderecord.getNumber()));
			//买卖币总金额 - 成交数量
			sufbalance.setTotalNum(DoubleUtil.sub(sufbalance.getTotalNum(),traderecord.getNumber()));
			sufBill.setType(Constants.BILLINFO_TYPE_1);//支出
			sufBill.setNumber(traderecord.getNumber());//交易金额
			sufBill.setFee(0.0);//手续费
			sufBill.setNowNum(sufbalance.getTotalNum());//交易后总额
			sufBill.setNowFrozenNum(sufbalance.getFrozenNum());//交易后冻结金额
			sufBill.setNowValidNum(sufbalance.getValidNum());//交易后有效金额
			sufBill.setBehavior(Constants.BILLINFO_BEHAVIOR_6);//日志行为:币币交易成交
			sufBill.setStatus(Constants.BILLINFO_STATUS_0);//默认状态
			sufBill.setCreateTime(DateUtil.getCurTimeString());
			sufBill.setUpdateTime(DateUtil.getCurTimeString());
		}

		prebalance.setUpdateTime(DateUtil.getCurTimeString());
		sufbalance.setUpdateTime(DateUtil.getCurTimeString());
		//保存用户余额信息
		seTUserbalanceMapper.updateByPrimaryKey(prebalance);
		seTUserbalanceMapper.updateByPrimaryKey(sufbalance);
		//用户余额变更记录
		billinfoMapper.insert(preBill);
		billinfoMapper.insert(sufBill);

	}


}
