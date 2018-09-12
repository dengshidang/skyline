package com.skyline.clientService.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.skyline.clientService.business.TradeCorde;
import com.skyline.clientService.exception.TradeException;
import com.skyline.clientService.kline.KlineParam;
import com.skyline.clientService.mapper.SeTMarketMapper;
import com.skyline.clientService.mapper.SeTTradeinfoMapper;
import com.skyline.clientService.mapper.SetTraderecordMapper;
import com.skyline.clientService.mapper.TradeOptinalMapper;
import com.skyline.clientService.redis.RedisUtil;
import com.skyline.common.constant.BaseTimeForat;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.TradeConstant;
import com.skyline.common.constant.TradeStatusCode;
import com.skyline.common.entity.SeTMarketEntity;
import com.skyline.common.entity.SeTTradeinfoEntity;
import com.skyline.common.entity.SeTTraderecordEntity;
import com.skyline.common.entity.TradeOptinalEntity;
import com.skyline.common.util.DateUtil;
import com.skyline.common.vo.DataVO;
import com.skyline.common.vo.DepthVO;
import com.skyline.common.vo.ResultDataVO;
import com.skyline.common.vo.TradeCordDepthVO;
import com.skyline.common.vo.TradeEntityVO;
import com.skyline.common.vo.TradeEntrustEntityVO;
import com.skyline.common.vo.TradeNewCordVO;
import com.skyline.common.vo.TradePrecoinEntityVO;
import com.skyline.common.vo.TradePrecoindNodeVO;
import com.skyline.common.vo.TradeUserEntrustVO;
/**
 * 
 * @author dengshidang
 *
 */
@Service
public class TradeCordeImpl implements TradeCorde{
	private Logger logger = Logger.getLogger(TradeCordeImpl.class);
	@Autowired
	private SetTraderecordMapper setTraderecordMapper;
	@Autowired
	private SeTTradeinfoMapper seTTradeinfoMapper;
	@Autowired
	private  SeTMarketMapper  seTMarketMapper ;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private TradeOptinalMapper tradeOptinalMapper;
	/**异步获取k线数据
	 * @param kp k线数据的请求参数实体
	 * @return kline,depth,trades
	 */

	@Override
	public ResultDataVO tradeCord(KlineParam kp) {
		/*返回数据的实体*/	
		Integer marketId = kp.getSymbol();
		DataVO data = new DataVO(); 	
		if(null!=marketId){
			Long basetime=kp.getRange()/1000;
			kp.setRange(basetime);
			String format ="";
			BaseTimeForat[] values = BaseTimeForat.values();
			for (BaseTimeForat btf : values) {
				Long bt = btf.getBaseTime();
				if (bt==basetime) {
					format= btf.getFormat();
				}
			}			
			Future<List<Map<String, Object>>> f1 = kline(kp);
			List<List<Object>> klines = null;
			try {
				klines = toList2(f1.get());
			} catch (Exception e) {
				logger.info(e.getMessage());
			}        	

			if (!klines.isEmpty()) data.setLines(klines);

			DepthVO<List<Double>> depth = null;
			try {
				depth = depth(kp.getSymbol()).get();
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
			data.setDepths(depth);
			try {
				data.setTrades(trade(kp).get());
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
			return new ResultDataVO(data,true);
		}else
			return new ResultDataVO(data,false);

	}
	/**
	 * kline 数据
	 * @param kp
	 * @return
	 */
	@Async
	private Future<List<Map<String, Object>>> kline(KlineParam kp){
		List<Map<String, Object>> selectTrdeCordByMarketAndTimeType = 
				setTraderecordMapper.selectTrdeCordByMarketAndTimeType(kp);
		return new AsyncResult<List<Map<String,Object>>>(selectTrdeCordByMarketAndTimeType);
	}
	/**
	 * depth 数据
	 * @param marketId
	 * @return
	 */
	@Async
	private Future<DepthVO<List<Double>>> depth(Integer marketId){
		DepthVO<List<Double>> dp = new DepthVO<>();
		/*获取卖方数据*/
		List<TradeCordDepthVO> askslist = seTTradeinfoMapper.queryTrade(marketId,TradeConstant.TRADETYPE_SELL);
		if (askslist.isEmpty())
			logger.info(TradeStatusCode.E00328.getCode()+":"+TradeStatusCode.E00328.getMsg());
		/*获取买方数据*/
		List<TradeCordDepthVO> bidslist = seTTradeinfoMapper. queryTrade(marketId,TradeConstant.TRADETYPE_BUY);	
		if ( bidslist.isEmpty())
			logger.info(TradeStatusCode.E00329.getCode()+":"+TradeStatusCode.E00329.getMsg());
		/*封装*/		
		dp.setAsks(toList(askslist));
		dp.setBids(toList(bidslist));	
		return new AsyncResult<DepthVO<List<Double>>>(dp);

	}
	/**
	 * trdae 数据
	 * @param kp
	 * @return
	 */
	@Async
	private Future<List<TradeEntityVO>> trade(KlineParam kp){
		/*3. 获取trades数据,展示5条,委托卖方*/			        
		List<TradeEntityVO> newTradesellmaps = seTTradeinfoMapper.getNewTrade(kp.getSymbol(),TradeConstant.TRADETYPE_SELL,TradeConstant.RECORDSTATUS_COUNT);

		/*4. 获取trades数据,展示5条,委托买方*/		
		List<TradeEntityVO> newTradebuymaps = seTTradeinfoMapper.getNewTrade(kp.getSymbol(),TradeConstant.TRADETYPE_BUY,TradeConstant.RECORDSTATUS_COUNT);
		if(!newTradesellmaps.isEmpty()&&!newTradebuymaps.isEmpty()){				
			newTradesellmaps.addAll(newTradebuymaps);		
		}
		return new AsyncResult<List<TradeEntityVO>>(newTradesellmaps);
	}

	/**
	 * 买卖委托数据对比，深度图
	 * 可选数据，查不到则不显示，记录日志即可。
	 */
	@Override
	public Result<?> depatMap(Integer marketId) {		

		DepthVO<List<Double>> dp = new DepthVO<>();
		/*获取卖方数据*/
		List<TradeCordDepthVO> askslist = seTTradeinfoMapper.queryTrade(marketId,TradeConstant.TRADETYPE_SELL);
		if (askslist.isEmpty())
			logger.info(TradeStatusCode.E00328.getCode()+":"+TradeStatusCode.E00328.getMsg());
		/*获取买方数据*/
		List<TradeCordDepthVO> bidslist = seTTradeinfoMapper. queryTrade(marketId,TradeConstant.TRADETYPE_BUY);	
		if ( bidslist.isEmpty())
			logger.info(TradeStatusCode.E00329.getCode()+":"+TradeStatusCode.E00329.getMsg());
		/*封装*/		
		dp.setAsks(toList(askslist));
		dp.setBids(toList(bidslist));		
		return Result.successResult(dp);
	}
	/**
	 * @param tradeCord List<TradeCordDepth> 
	 * return list<Double>
	 */
	private List<List<Double>>toList(List<TradeCordDepthVO> tradeCord) {//price,totalNum 			
		List<List<Double>>  outlist = new ArrayList<>();
		if(tradeCord.isEmpty())return null;;
		for (TradeCordDepthVO tcd : tradeCord) {
			List<Double> list = new ArrayList<>();
			list.add(tcd.getPrice());
			list.add(tcd.getTotalNum());
			outlist.add(list);
		}
		return outlist;
	}
	/**
	 * 最新委托记录
	 */
	@Override
	public Result<PageInfo<TradeEntrustEntityVO>> getNewTradeEntrust(Integer marketId,Integer type,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TradeEntrustEntityVO> list = seTTradeinfoMapper.getNewTradeEntrust(marketId,type);	
        int index = 0;
		for (TradeEntrustEntityVO entity : list) {
			entity = sumTotal(list,index++);
		}
		PageInfo<TradeEntrustEntityVO> page=new PageInfo<TradeEntrustEntityVO>(list);
		return Result.successResult(page);
	}
	//累计数量

	private TradeEntrustEntityVO sumTotal(List<TradeEntrustEntityVO> list,int index) {
		if(list!=null&&!list.isEmpty()){
			double sum = 0.0;
			for(int i =0;i<=index;i++) {
				sum+=list.get(i).getTotalNum();	 
			}
			list.get(index).setSum(sum);
		}
		return list.get(index);
	}

	/** 
	 * 获取可交易币信息（id,name）
	 */
	@Override
	public Result<List<TradePrecoindNodeVO>> getPrecoind() {
		List<TradePrecoindNodeVO> precoinds = seTMarketMapper.getPrecoind();	
		if(precoinds.isEmpty())
			throw new TradeException(TradeStatusCode.E00330.getCode(),TradeStatusCode.E00330.getMsg());
		return Result.successResult(precoinds);
	}
	/**
	 * 当天实时交易数据
	 */
	@Override
	public Result<PageInfo<TradeNewCordVO>> getNewTradeCord(Integer marketId, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TradeNewCordVO> newTradeCord = setTraderecordMapper.getNewTradeCord(marketId);
		PageInfo<TradeNewCordVO> page = new PageInfo<>(newTradeCord);		
		return Result.successResult(page);
	}
	/**
	 * 添加自选行情
	 * @param marketId
	 * @param userId
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public Result<?> addOptional(Integer marketId, Integer userId) {
		SeTMarketEntity market = seTMarketMapper.selectByPrimaryKey(marketId);
		if(market!=null){
			//用户自选行情实体对象
			TradeOptinalEntity o = new TradeOptinalEntity();
			o.setMarketId(market.getId());
			o.setUserId(userId);
			//检查是否存在
			TradeOptinalEntity selectOne = tradeOptinalMapper.selectOne(o);
			//不存在，在更新
			if(selectOne==null){
				o.setPrecoinName(market.getPrecoinName());
				o.setSufcoinName(market.getSufcoinName());
				o.setPrecoinId(market.getPrecoinId());
				o.setSufcoinId(market.getSufcoinId());
				o.setStatus(1);//用户添加
				o.setCreateTime(DateUtil.getCurTimeString());
				o.setUpdateTime(o.getCreateTime());			
				int insert = tradeOptinalMapper.insert(o);
				if(insert<1)
					throw new  TradeException(TradeStatusCode.E00337.getCode(),TradeStatusCode.E00337.getMsg());
				return Result.successResult();
			}
			return Result.errorResult(TradeStatusCode.E00338.getCode(),TradeStatusCode.E00338.getMsg());
		}
		else
			return Result.errorResult(TradeStatusCode.E00401.getCode(),TradeStatusCode.E00401.getMsg());
	}
	/**
	 * 获取用户自选行情
	 * @param userId
	 * @param type
	 * @param dec
	 * @param keyword
	 * @return
	 */
	@Override
	public Result<List<TradePrecoinEntityVO>> getOptinal(Integer userId, Integer type, Integer dec, String keyword) {		
		//获取用户收藏的行情
		String sort = (dec==0?"asc":"desc");
		List<TradePrecoinEntityVO> optionals = seTMarketMapper.getOptinal(userId,type,sort,keyword);
		return Result.successResult(optionals);	
	}


	/**
	 * 用户取消行情收藏
	 * @param marketId
	 * @param userId
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public Result<?> cancelOptional(Integer marketId, Integer userId) {
		TradeOptinalEntity o = new TradeOptinalEntity();
		o.setMarketId(marketId);
		o.setUserId(userId);
		int insert = tradeOptinalMapper.delete(o);
		if(insert<1)
			throw new  TradeException(TradeStatusCode.E00336.getCode(),TradeStatusCode.E00336.getMsg());
		return Result.successResult();


	}
	/**
	 * 用户成交记录详情
	 * @param id 委托id,
	 * @param userId 用户id
	 * @return
	 */
	@Override
	public Result<?> getUserTradeDetails(Integer id, Integer userId) {
		SeTTraderecordEntity entity = new SeTTraderecordEntity();
		SeTTradeinfoEntity info = new SeTTradeinfoEntity();
		info.setId(id);
		info.setUserId(userId);
		info= seTTradeinfoMapper.selectOne(info);
		List<TradeUserEntrustVO> list = new ArrayList<>();
		if(info!=null){
			if( info.getType()==0)
				entity.setEnterorderNo(info.getOrderNo());
			else 
				entity.setOutorderNo(info.getOrderNo());
			List<SeTTraderecordEntity> select = setTraderecordMapper.select(entity);
			for (SeTTraderecordEntity seTTraderecordEntity : select) {
				TradeUserEntrustVO userEntrust = new TradeUserEntrustVO();
				userEntrust.setType(info.getType());//方向
				userEntrust.setCreateTime(info.getCreateTime());//交易时间
				userEntrust.setFee(info.getType()==0?(seTTraderecordEntity.getEnterFee()):seTTraderecordEntity.getOutFee());//费用
				userEntrust.setDealNum(seTTraderecordEntity.getNumber());//交易量
				userEntrust.setMarketName(seTMarketMapper.selectByPrimaryKey(info.getMarketId()).getMarketName());//行情，交易对
				userEntrust.setPrice(seTTraderecordEntity.getPrice());//价格
				list.add(userEntrust);
			}
		}
		return Result.successResult(list);
	}


	/**
	 * 
	 * @param fiveKlist k线数据
	 * @return
	 */
	public List toList2(List<Map<String, Object>> fiveKlist){   	
		List<List<Object>> outlist = new ArrayList<>();  
		for (Map<String, Object> map : fiveKlist) {
			List<Object> innerlist = new ArrayList<>();  
			innerlist.add((Long)map.get("tradingTime"));
			innerlist.add(map.get("open"));
			innerlist.add(map.get("high"));
			innerlist.add(map.get("low"));
			innerlist.add(map.get("close"));
			innerlist.add(map.get("volume"));
			outlist.add(innerlist);
		}           
		return outlist;

	}

}
