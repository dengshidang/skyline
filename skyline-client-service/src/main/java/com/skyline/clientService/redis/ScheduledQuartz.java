package com.skyline.clientService.redis;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import com.skyline.clientService.mapper.SetTraderecordMapper;
/**
 * 定时刷新数据到redis
 * @author dengshidang
 * W.mar, W.volume,W.avgP,W.high,W.low,tradingTime,low,close,open
 */
@Component
@EnableCaching
public class ScheduledQuartz{
	@Autowired
	private RedisUtil redisUtil; 
	@Autowired
	private SetTraderecordMapper setTraderecordMapper;
	Logger log = Logger.getLogger(ScheduledQuartz.class);
	
	/**
	 * 获取所有行情id存入redis;
	 */

	/*@Scheduled(fixedDelay = MarketDateType.DAY)
	public void changeX(){		
		List<Integer> allList= setTraderecordMapper.getMarketIdlist();
		if(null!=allList){
			redisUtil.set("MARKET_ID", allList);
			log.info("amrketId 存入redis成功："+allList);
		}

	}

	
	@Async
	@Scheduled(fixedDelay = MarketDateType.FIVE_SECOND)
	public void fiveSecondDo() {
		@SuppressWarnings("unchecked")
		List<Integer> markets = (List<Integer>)redisUtil.get("MARKET_ID");
		BaseTimeForat btf = BaseTimeForat.values()[0];
		for(Integer id:markets){			
			//居于行情id,时间格式，单位时间，获取成交数据，k 线数据
			List<Map<String, Object>> allKlist = 
					setTraderecordMapper.selectTrdeCordByMarketAndTimeType(id,btf.getFormat(),Integer.valueOf(btf.getBaseTime()+""),1000);
			if (allKlist!=null) {
				try {
					redisUtil.set(id.toString()+btf.getFormat(),RedisUtil.toList(allKlist));	
					log.info(allKlist);
				}catch(RuntimeException e) {
					log.info("缓存失败时间："+DateUtil.getCurTimeString()+"，失败的数据节点时："+btf.getNode()+"k线数据");
					 e.printStackTrace();
				}
			}
		}


	}

	@Async
	@Scheduled(fixedDelay = MarketDateType.FIVE_SECOND)
	public void minuteTimerDo() {	
		@SuppressWarnings("unchecked")
		List<Integer> markets = (List<Integer>)redisUtil.get("MARKET_ID");
		BaseTimeForat btf = BaseTimeForat.values()[1];
		for(Integer id:markets){			
			//居于行情id,时间格式，单位时间，获取成交数据，k 线数据
			List<Map<String, Object>> allKlist = 
					setTraderecordMapper.selectTrdeCordByMarketAndTimeType(id,btf.getFormat(),Integer.valueOf(btf.getBaseTime()+""),1000);
			if (allKlist!=null) {
				try {
					redisUtil.set(id.toString()+btf.getFormat(),RedisUtil.toList(allKlist));	
					log.info(allKlist);
				}catch(RuntimeException e) {
					log.info("缓存失败时间："+DateUtil.getCurTimeString()+"，失败的数据节点时："+btf.getNode()+"k线数据");
					 e.printStackTrace();
				}
			}
		}

	}

	@Async
	@Scheduled(fixedDelay = MarketDateType.THIRTY_SECOND)
	public void minute5TimerDo() {	
		@SuppressWarnings("unchecked")
		List<Integer> markets = (List<Integer>)redisUtil.get("MARKET_ID");
		BaseTimeForat btf = BaseTimeForat.values()[2];
		for(Integer id:markets){			
			//居于行情id,时间格式，单位时间，获取成交数据，k 线数据
			List<Map<String, Object>> allKlist = 
					setTraderecordMapper.selectTrdeCordByMarketAndTimeType(id,btf.getFormat(),Integer.valueOf(btf.getBaseTime()+""),1000);
			if (allKlist!=null) {
				try {
					redisUtil.set(id.toString()+btf.getFormat(),RedisUtil.toList(allKlist));	
					log.info(allKlist);
				}catch(RuntimeException e) {
					log.info("缓存失败时间："+DateUtil.getCurTimeString()+"，失败的数据节点时："+btf.getNode()+"k线数据");
					 e.printStackTrace();
				}
			}
		}

	}

	@Async
	@Scheduled(fixedDelay = MarketDateType.MUNITE)
	public void minute15TimerDo() {	
		@SuppressWarnings("unchecked")
		List<Integer> markets = (List<Integer>)redisUtil.get("MARKET_ID");
		BaseTimeForat btf = BaseTimeForat.values()[3];
		for(Integer id:markets){			
			//居于行情id,时间格式，单位时间，获取成交数据，k 线数据
			List<Map<String, Object>> allKlist = 
					setTraderecordMapper.selectTrdeCordByMarketAndTimeType(id,btf.getFormat(),Integer.valueOf(btf.getBaseTime()+""),1000);
			if (allKlist!=null) {
				try {
					redisUtil.set(id.toString()+btf.getFormat(),RedisUtil.toList(allKlist));	
					log.info(allKlist);
				}catch(RuntimeException e) {
					log.info("缓存失败时间："+DateUtil.getCurTimeString()+"，失败的数据节点时："+btf.getNode()+"k线数据");
					 e.printStackTrace();
				}
			}
		}

	}
	
	@Async
	@Scheduled(fixedDelay = MarketDateType.MUNITE)
	public void minute30TimerDo() {	
		@SuppressWarnings("unchecked")
		List<Integer> markets = (List<Integer>)redisUtil.get("MARKET_ID");
		BaseTimeForat btf = BaseTimeForat.values()[4];
		for(Integer id:markets){			
			//居于行情id,时间格式，单位时间，获取成交数据，k 线数据
			List<Map<String, Object>> allKlist = 
					setTraderecordMapper.selectTrdeCordByMarketAndTimeType(id,btf.getFormat(),Integer.valueOf(btf.getBaseTime()+""),1000);
			if (allKlist!=null) {
				try {
					redisUtil.set(id.toString()+btf.getFormat(),RedisUtil.toList(allKlist));	
					log.info(allKlist);
				}catch(RuntimeException e) {
					log.info("缓存失败时间："+DateUtil.getCurTimeString()+"，失败的数据节点时："+btf.getNode()+"k线数据");
					 e.printStackTrace();
				}
			}
		}

	}
	
	@Async
	@Scheduled(fixedDelay = MarketDateType.FIVE_MUNITE)
	public void hourTimerDo() {	
		@SuppressWarnings("unchecked")
		List<Integer> markets = (List<Integer>)redisUtil.get("MARKET_ID");
		BaseTimeForat btf = BaseTimeForat.values()[5];
		for(Integer id:markets){			
			//居于行情id,时间格式，单位时间，获取成交数据，k 线数据
			List<Map<String, Object>> allKlist = 
					setTraderecordMapper.selectTrdeCordByMarketAndTimeType(id,btf.getFormat(),Integer.valueOf(btf.getBaseTime()+""),1000);
			if (allKlist!=null) {
				try {
					redisUtil.set(id.toString()+btf.getFormat(),RedisUtil.toList(allKlist));	
					log.info(allKlist);
				}catch(RuntimeException e) {
					log.info("缓存失败时间："+DateUtil.getCurTimeString()+"，失败的数据节点时："+btf.getNode()+"k线数据");
					 e.printStackTrace();
				}
			}
		}

	}

	@Async
	@Scheduled(fixedDelay = MarketDateType.HOUR)
	public void dayTimerDo() {	
		@SuppressWarnings("unchecked")
		List<Integer> markets = (List<Integer>)redisUtil.get("MARKET_ID");
		BaseTimeForat btf = BaseTimeForat.values()[6];
		for(Integer id:markets){			
			//居于行情id,时间格式，单位时间，获取成交数据，k 线数据
			List<Map<String, Object>> allKlist = 
					setTraderecordMapper.selectTrdeCordByMarketAndTimeType(id,btf.getFormat(),Integer.valueOf(btf.getBaseTime()+""),1000);
			if (allKlist!=null) {
				try {
					redisUtil.set(id.toString()+btf.getFormat(),RedisUtil.toList(allKlist));	
					log.info(allKlist);
				}catch(RuntimeException e) {
					log.info("缓存失败时间："+DateUtil.getCurTimeString()+"，失败的数据节点时："+btf.getNode()+"k线数据");
					 e.printStackTrace();
				}
			}
		}

	}

	@Async
	@Scheduled(fixedDelay = MarketDateType.DAY)
	public void weekTimerDo() {	
		@SuppressWarnings("unchecked")
		List<Integer> markets = (List<Integer>)redisUtil.get("MARKET_ID");
		BaseTimeForat btf = BaseTimeForat.values()[7];
		for(Integer id:markets){			
			//居于行情id,时间格式，单位时间，获取成交数据，k 线数据
			List<Map<String, Object>> allKlist = 
					setTraderecordMapper.selectTrdeCordByMarketAndTimeType(id,btf.getFormat(),Integer.valueOf(btf.getBaseTime()+""),1000);
			if (allKlist!=null) {
				try {
					redisUtil.set(id.toString()+btf.getFormat(),RedisUtil.toList(allKlist));	
					log.info(allKlist);
				}catch(RuntimeException e) {
					log.info("缓存失败时间："+DateUtil.getCurTimeString()+"，失败的数据节点时："+btf.getNode()+"k线数据");
					 e.printStackTrace();
				}
			}
		}

	}

	*/

}