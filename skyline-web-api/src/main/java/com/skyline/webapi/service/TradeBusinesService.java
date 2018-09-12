package com.skyline.webapi.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.skyline.common.constant.Result;
import com.skyline.common.vo.DepthVO2;
import com.skyline.common.vo.ResultDataVO;
import com.skyline.common.vo.TradeCoinRateVO;
import com.skyline.common.vo.TradePrecoinEntityVO;
import com.skyline.common.vo.TradeUserEntrustVO;
/**
 * 
 * @author dengshidang
 *
 */
@FeignClient(value="skyline-client-service")
public interface TradeBusinesService {	 
	/**
	 * 获取可交易币
	 * @return id,name
	 */
	@GetMapping(value="/trade/getPrecoind")
	public Result getPrecoind();
	/**
	 * 获取交易币对应行情信息，包括：最新行情价位、日涨幅、当日的行情最高点和最低点、当日行情累计交易量
	 * @param precoinId 可交易币id
	 * @param type 排序类型 1：precoinName按交易币名称排序，2：newPrice 按价格排序，3：gains:按日涨幅排序
	 * @param dec 0;升序，1：降序
	 * @param keyword 搜索关键字
	 * @return
	 */
	@PostMapping(value = "/trade/getMarket")
	public Result getMarket(@RequestParam("precoinId") Integer precoinId,@RequestParam("type") Integer type,@RequestParam("dec") Integer dec,@RequestParam("keyword")String keyword);
	/**
	 * 获取用户的币币交易委托信息
	 * @param userid
	 * @param pageNum
	 * @param pageSize
	 * @param tradeUserEntrust
	 * @return
	 */
	@PostMapping(value="/trade/getUserTradeEntrust")
	public Result getUserTrade(@RequestParam("userId") Integer userId,@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize") Integer pageSize,@RequestBody TradeUserEntrustVO tradeUserEntrust);
	/**
	 * 用户提交交易委托	
	 * @param  marketId 行情id
	 * @param price 委托价
	 * @param type 委托方向，0：买，1：卖
	 * @return  
	 */ 
	@PostMapping(value= "/trade/addTradeEntrust")
	public Result addTradeEntrust(@RequestParam("userId") Integer userId,@RequestParam("marketId") Integer marketId,@RequestParam("number") Double number,@RequestParam("price") Double price,@RequestParam("type") Integer type);
	/**
	 * 撤销币币交易委托信息
	 * @param userId
	 * @param id
	 * @return
	 */
	@PostMapping(value= "/trade/cancel")
	public Result cancelTrade(@RequestParam("userId")Integer userId, @RequestParam("id")Integer id);
	/**
	 * k线数据
	 * @param symbol
	 * @param range
	 * @param limit
	 * @param since
	 * @param prevTradeTime
	 * @return 
	 */
	@GetMapping(value= "/trade/klineShow")
	public ResultDataVO getTrdeCordByMarketAndTimeType(@RequestParam("symbol")Integer symbol,@RequestParam("range") Long range,@RequestParam("limit")Integer limit,@RequestParam("since")Long since,@RequestParam("prevTradeTime")Long prevTradeTime);
	/**
	 * 获取买卖数据对比(深度图)
	 * @param marketId
	 * @return
	 */
	@PostMapping(value= "/trade/depat")
	public Result getTradeCordDepat(@RequestParam("marketId") Integer marketId);
	/**
	 * 实时委托记录
	 * @param marketId
	 * @param type
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@PostMapping(value ="/trade/getNewTradeEntrust")
	public Result<?> getNewTradeEntrust(@RequestParam("marketId") Integer marketId,@RequestParam("type") Integer type,@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize);
	/**
	 * 获取用币种户余额
	 * @param id
	 * @param coinId
	 * @return
	 */
	@PostMapping("/trade/getUserBalance")
	public Result<?> getUserBalance(@RequestParam("id")Integer id, @RequestParam("coinId")Integer coinId);
	/**
	 * 实时交易
	 */
	@PostMapping(value="/trade/getNewTradeCord")
	public Result<?> getNewTradeCord(@RequestParam("marketId") Integer marketId,@RequestParam("pageNum")Integer pageNum,@RequestParam("pageSize") Integer pageSize);
	/**
	 * 用户添加自选行情
	 * @param marketId
	 * @param userId
	 * @return
	 */
	@PostMapping(value="/trade/addOptional")
	public Result<?> addOptional(@RequestParam("marketId") Integer marketId,@RequestParam("userId") Integer userId);
	/**
	 * 获取用户自选行情
	 * @param userId
	 * @param type
	 * @param dec
	 * @param keyword
	 * @return
	 */
	@PostMapping(value="/trade/getOptinal")
	public Result<List<TradePrecoinEntityVO>> getOptinal(@RequestParam("userId")Integer userId, @RequestParam("type")Integer type, @RequestParam("dec")Integer dec,@RequestParam("keyword") String keyword);	 
	/**
	 * 用户取消自选行情
	 * @param marketId
	 * @param userId
	 * @return
	 */
	@PostMapping(value="/trade/cancelOptional")
	public Result<?> cancelOptional(@RequestParam("marketId")Integer marketId,@RequestParam("userId")Integer userId);
		
	/**
	 * 用户单笔委托交易详情
	 * @param id 委托id
	 * @param  userId 
	 * @return
	 */
	@PostMapping(value="/trade/getUserTradeDetails")
	public Result<?> getUserTradeDetails(@RequestParam("id")Integer id, @RequestParam("userId")Integer userId);
	/**
	 * 获取币币交易费率详情
	 * @param tradeCoinRate
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@PostMapping(value="/trade/getTradeCoinRate")
	public Result<?> getTradeCoinRate(@RequestBody TradeCoinRateVO tradeCoinRate, @RequestParam("pageNum")Integer pageNum, @RequestParam("pageSize")Integer pageSize);
	
	/**
	 * 获取当前行情信息
	 * @param marketId 行情id
	 * @return
	 */
	@PostMapping(value="/trade/getCurrentMarket")
	public Result<?> getCurrentMarket(@RequestParam("marketId")Integer marketId);
	/**
	 * 绘画深度图数据
	 * @param marketId 行情id
	 * @return
	 */
	@PostMapping(value = "/trade/drawDepths")
	public Result<DepthVO2> drawDepths(@RequestParam("marketId")Integer marketId);
		
}	
