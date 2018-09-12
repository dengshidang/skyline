package com.skyline.clientService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.skyline.clientService.business.TradeBusiness;
import com.skyline.clientService.business.TradeCorde;
import com.skyline.clientService.kline.KlineParam;
import com.skyline.common.constant.Result;
import com.skyline.common.constant.TradeStatusCode;
import com.skyline.common.entity.SeTMarketEntity;
import com.skyline.common.vo.DepthVO2;
import com.skyline.common.vo.ResultDataVO;
import com.skyline.common.vo.TradeCoinRateVO;
import com.skyline.common.vo.TradeEntrustEntityVO;
import com.skyline.common.vo.TradePrecoindNodeVO;
import com.skyline.common.vo.TradeUserEntrustVO;

/**
 * 币币交易业务入口
 * @author Yjian
 * @time 2018-7-2
 */
@RestController
public class TradeService {

	@Autowired
	private TradeBusiness tradeBusiness;
	@Autowired
	private TradeCorde tradeCord;
	/**
	 * 
	 * 获取交易币id,name 
	 * @return
	 */
	@GetMapping(value="/trade/getPrecoind")
	public Result<List<TradePrecoindNodeVO>> getPrecoind(){

		return tradeCord.getPrecoind();

	}
	/**
	 * 
	 * @param precoinId 可交易币id
	 * @param type 排序类型 1：precoinName按交易币名称排序，2：newPrice 按价格排序，3：gains:按日涨幅排序
	 * @param dec 0;升序，1：降序
	 * @return
	 */
	@PostMapping(value = "/trade/getMarket")
	public Result<?> getMarket(@RequestParam Integer precoinId,@RequestParam Integer type,@RequestParam Integer dec,String keyword ){
		return tradeBusiness.getMarket(precoinId,type,dec,keyword);
	}

	/**
	 * 添加币币交易行情信息
	 * @param seTMarketEntity 行情信息实体
	 * @return
	 */
	@PostMapping(value= "/trade/addMarket")
	public Result<?> saveMarketEntity(@RequestBody SeTMarketEntity seTMarketEntity){
		return tradeBusiness.addMarket(seTMarketEntity);
	}
	/**
	 * 添加币币交易委托
	 * @param userId 用户id
	 * @param  marketId 行情id
	 * @param price 委托价
	 * @param type 委托方向，0：买，1：卖
	 * @return 
	 */
	@PostMapping(value= "/trade/addTradeEntrust")
	public Result<?> addTradeEntrust(@RequestParam Integer userId,@RequestParam Integer marketId,@RequestParam Double number,@RequestParam Double price,@RequestParam Integer type){
		return tradeBusiness.tradeEntrust(userId,marketId,number,price,type);
	}

	/**
	 * 获取用户的币币交易委托信息
	 * @param userid
	 * @param marketId
	 * @param status
	 * @return
	 */
	@PostMapping(value= "/trade/getUserTradeEntrust")
	public Result<?> getUserTradeEntrust(@RequestParam Integer userId,@RequestParam Integer pageNum,@RequestParam Integer pageSize,@RequestBody TradeUserEntrustVO tradeUserEntrust){
		return tradeBusiness.getUserTradeEntrust(userId,pageNum,pageSize,tradeUserEntrust);
	}

	/**
	 * 撤销币币交易委托信息
	 * @param tradeUserEntrust 委托信息
	 * @param id 交易委托id
	 * @return
	 */
	@PostMapping(value= "/trade/cancel")
	public Result<?> cancelTrade(@RequestParam Integer userId,@RequestParam Integer id){

		return tradeBusiness.tradeCancel(userId,id);
	}

	/**
	 * k线请求参数
	 * @param symbol
	 * @param range
	 * @param limit
	 * @param since
	 * @param prevTradeTime
	 * @return Data data
	 */
	@GetMapping(value= "/trade/klineShow")
	public ResultDataVO getTrdeCordByMarketAndTimeType(@RequestParam Integer symbol,@RequestParam Long range,Integer limit,Long since,Long prevTradeTime){
		KlineParam kp = new KlineParam();	
		kp.setPrevTradeTime(prevTradeTime);
		kp.setLimit(limit);
		kp.setRange(range);;
		kp.setSymbol(symbol);
		kp.setSince(since);
		return tradeCord.tradeCord(kp);
	}
	/**
	 * 获取买卖数据对比
	 * @param market
	 * @param type
	 * @return
	 */
	@PostMapping(value= "/trade/depat")
	public Result<?> getTradeCordDepat(@RequestParam Integer marketId){
		if(null==marketId||marketId<0){Result.errorResult(TradeStatusCode.E00327.getCode(), TradeStatusCode.E00327.getMsg());}
		return tradeCord.depatMap(marketId);
	}
	/**
	 * 最新委托数据 
	 * @param marketId id
	 * @param limit 显示数量
	 * @return 
	 */
	@PostMapping(value ="/trade/getNewTradeEntrust")
	public Result<PageInfo<TradeEntrustEntityVO>> getNewTradeEntrust(@RequestParam Integer marketId,@RequestParam Integer type,@RequestParam Integer pageNum,@RequestParam Integer pageSize){	
		if(null==marketId||marketId<0){Result.errorResult(TradeStatusCode.E00327.getCode(), TradeStatusCode.E00327.getMsg());}
		return tradeCord.getNewTradeEntrust(marketId,type,pageNum,pageSize);
	}
	/**
	 * 
	 * 获取用户币种余额信息
	 * @param userId
	 * @param coinId
	 * @return
	 */
	@PostMapping(value ="/trade/getUserBalance")
	public Result<?> getUserBalance(@RequestParam Integer userId,@RequestParam Integer coinId){
		return tradeBusiness.getUserBalance(userId,coinId);

	}

	/**
	 * 实时交易
	 * @param marketId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@PostMapping(value="/trade/getNewTradeCord")
	public Result<?> getNewTradeCord(@RequestParam Integer marketId,Integer pageNum,Integer pageSize){
		return tradeCord.getNewTradeCord(marketId,pageNum, pageSize);

	}
	/**
	 * 用户添加自选行情
	 * @param marketId
	 * @param userId
	 * @return
	 */
	@PostMapping(value="/trade/addOptional")
	public Result<?> addOptional(@RequestParam Integer marketId,@RequestParam Integer userId){
		return tradeCord.addOptional(marketId,userId);

	}
	/**
	 * 获取用户自选行情
	 * @param userId
	 * @param type
	 * @param dec
	 * @param keyword
	 * @return
	 */
	@PostMapping(value="/trade/getOptinal")
	public Result<?> getOptinal(@RequestParam Integer userId, Integer type, Integer dec, String keyword) {	 
		return tradeCord.getOptinal(userId,type,dec,keyword);
	}

	/**
	 * 用户取消自选行情
	 * @param marketId
	 * @param userId
	 * @return
	 */
	@PostMapping(value="/trade/cancelOptional")
	public Result<?> cancelOptional(@RequestParam Integer marketId,Integer userId){
		return tradeCord.cancelOptional(marketId,userId);

	}
	/**
	 * 用户成交记录详情
	 * @param id 委托id,
	 * @param userId 用户id
	 * @return
	 */
	@PostMapping(value="/trade/getUserTradeDetails")
	public Result<?> getUserTradeDetails(@RequestParam Integer id,@RequestParam Integer userId){
		return tradeCord.getUserTradeDetails(id,userId);

	}
	/**
	 * 费率详情数据
	 * @param tradeCoinRate
	 * @return
	 */
	@PostMapping(value="/trade/getTradeCoinRate")
	public Result<?> getTradeCoinRate(@RequestBody TradeCoinRateVO tradeCoinRate,Integer pageNum,Integer pageSize){

		return tradeBusiness.getTradeCoinRate(tradeCoinRate, pageNum,pageSize);

	}
	/**
	 * 获取当前行情交易信息
	 *@param marketId
	 *@return 
	 */
	@PostMapping(value="/trade/getCurrentMarket")
	public Result<?> getCurrentMarket(@RequestParam Integer marketId){
		return tradeBusiness.getCurrentMarket(marketId);
	}
	/**
	 * 绘画深度图数据
	 * @param marketId
	 * @return
	 */
	@PostMapping(value = "/trade/drawDepths")
	public Result<DepthVO2> drawDepths(Integer marketId){
		return tradeBusiness.drawDepths(marketId);
		
		
	}
	
}
