package com.skyline.webapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skyline.common.constant.Result;
import com.skyline.common.constant.StatusCode;
import com.skyline.common.constant.TradeStatusCode;
import com.skyline.common.entity.Userinfo;
import com.skyline.common.util.ObjectTool;
import com.skyline.common.vo.DataVO;
import com.skyline.common.vo.DepthVO2;
import com.skyline.common.vo.ResultDataVO;
import com.skyline.common.vo.TradeCoinRateVO;
import com.skyline.common.vo.TradePrecoinEntityVO;
import com.skyline.common.vo.TradeUserEntrustVO;
import com.skyline.webapi.annotation.NotLogin;
import com.skyline.webapi.filter.RequestUtil;
import com.skyline.webapi.service.TradeBusinesService;

@RestController
public class TradeCordController {
	@Autowired
	private  TradeBusinesService   tradeBusinesService ;
	/**
	 * 获取可交易币的(业务的开始)
	 * @return list<{id（交易币id），name(交易币名称)}...>
	 */
	@NotLogin
	@GetMapping(value="/trade/getPrecoind")
	public Result getPrecoind(){
		return  tradeBusinesService.getPrecoind();
	}
	/**
	 * 获取交易币对应行情信息，
	 * @param precoinId 可交易币id
	 * @param type 排序类型 1：precoinName按交易币名称排序，2：newPrice 按价格排序，3：gains:按日涨幅排序
	 * @param dec 0;升序，1：降序
	 * @param keyword 搜索关键字
	 * @return  
	 */
	@NotLogin
	@PostMapping(value = "/trade/getMarket")
	public Result getMarket(Integer precoinId, Integer type, Integer dec,String keyword){
		if(null==precoinId || precoinId<0)
			/*参数无效*/
			return Result.errorResult(TradeStatusCode.E00401.getCode(),TradeStatusCode.E00401.getMsg());		
		return tradeBusinesService.getMarket(precoinId,type,dec,keyword);

	}
	/**
	 * 用户提交交易委托	
	 * @param  marketId 行情id
	 * @param price 委托价
	 * @param type 委托方向，0：买，1：卖
	 * @return  totalNum 委托总数，dealNum 已成交数量，number 成交金额，price 委托单价
	 */ 
	@PostMapping(value= "/trade/addTradeEntrust")
	public Result addTradeEntrust(Integer marketId, Double number, Double price, Integer type){	
		Userinfo currentUser = RequestUtil.getCurrentUser();
		if(null==currentUser)
			return Result.errorResult(TradeStatusCode.E00325.getCode(),TradeStatusCode.E00325.getMsg());
		//TODO 更新token失效时间
		return tradeBusinesService.addTradeEntrust(currentUser.getId(),marketId,number,price, type);
	}
	/**
	 * 获取用户的币币交易委托信息
	 * 筛选条件如下一个或多个：
	 *   例如： （1当前委托参数：marketId,status=1,
	 *          2 历史委托：marketId,status=2，
	 *          3更多信息： 根据行情，交易币id,买卖币id 开始时间，结束时间等一个或多个条件进行筛选）
	 *        
	 * @param marketId 行情
	 * @param isCurrent 1 当前委托，2(已成交) :历史委托
	 * @param status   0 挂起，1 处理，2完成，3 撤销
	 * @param precoinId 交易币id 
	 * @param sufcoinId 买卖币id
	 * @param type  方向
	 * @param startTime 筛选开始时间
	 * @param endTime 结束时间
	 */
	@PostMapping(value="/trade/getUserTradeEntrust")
	public Result getUserTrade(TradeUserEntrustVO tradeUserEntrust,Integer pageNum,Integer pageSize){	
		
		Userinfo currentUser = RequestUtil.getCurrentUser();
		if(null==currentUser)
			return Result.errorResult(TradeStatusCode.E00325.getCode(),TradeStatusCode.E00325.getMsg());
		//TODO 更新token失效时间
		return tradeBusinesService.getUserTrade(currentUser.getId(),pageNum,pageSize,tradeUserEntrust);

	}
	/**
	 * k线数据
	 * @param symbol 查询k线数据标识符，这里指行情id （必要参数）
	 * @param range k线类型：1-分钟；2-5分钟；3-15分钟；4-30分钟；5-1小时；6-天；7-周,单位毫秒（必要参数）
	 * @param  limit 初始数据量 
	 * @param  since 开始时间（秒）
	 * @param  prevTradeTime 结束时间（秒） 
	 * @return  
	 * 
	 */
	@NotLogin
	@GetMapping(value= "/trade/klineShow")
	public ResultDataVO getTrdeCordByMarketAndTimeType(Integer symbol, Long range,Integer limit,Long since,Long prevTradeTime){			
		if(null==symbol||null==range)
			return new ResultDataVO(new DataVO(),false);
		return tradeBusinesService.getTrdeCordByMarketAndTimeType(symbol,range,limit,since, prevTradeTime);
	}
	/**
	 * 获取买卖数据对比(深度图)
	 * @param marketId 行情
	 * @return
	 */
	@NotLogin
	@PostMapping(value= "/trade/depat")
	public Result getTradeCordDepat(Integer marketId){
		if(marketId==null)
			return Result.errorResult(TradeStatusCode.E00401.getCode(),TradeStatusCode.E00401.getMsg());
		return tradeBusinesService.getTradeCordDepat(marketId);
	}
	/**
	 * 撤销币币交易委托信息
	 * @param id 委托id
	 * @return
	 */
	@PostMapping(value= "/trade/cancel")
	public Result cancelTrade(Integer id){
		Userinfo currentUser = RequestUtil.getCurrentUser();
		if(null==currentUser)
			return Result.errorResult(TradeStatusCode.E00325.getCode(),TradeStatusCode.E00325.getMsg());
		//TODO:currentUser.参数验证
		return tradeBusinesService.cancelTrade(currentUser.getId(),id);
	}
	/**
	 * 实时委托信息  ，买，卖
	 * @param marketId 行情id
	 * @param limit 展示记录数
	 * @return
	 */
	@NotLogin
	@PostMapping(value ="/trade/getNewTradeEntrust")
	public Result getNewTradeEntrust( Integer marketId,Integer type, Integer pageNum, Integer pageSize){		
		if(ObjectTool.isBlank( marketId)) {
			return Result.errorResult(StatusCode.PARAM_NULL.getCode(), StatusCode.PARAM_NULL.getMsg());
		}
		return tradeBusinesService.getNewTradeEntrust(marketId,type,pageNum,pageSize);
	};

	/**
	 * 实时交易记录
	 * @param marketId 行情id
	 * @param pageNum 页码
	 * @param pageSize 当页数据量
	 * @return
	 */
	@NotLogin
	@PostMapping(value="/trade/getNewTradeCord")
	public Result getNewTradeCord( Integer marketId, Integer pageNum, Integer pageSize){
		if(null==marketId)
			return Result.errorResult(TradeStatusCode.E00401.getCode(),TradeStatusCode.E00401.getMsg());
		return tradeBusinesService.getNewTradeCord(marketId,pageNum, pageSize);

	}
	/**
	 * 用户添加自选行情
	 * @param marketId	行情id
	 * @return
	 */
	@PostMapping(value="/trade/addOptional")
	public Result addOptional( Integer marketId){
		Userinfo currentUser = RequestUtil.getCurrentUser();
		if(null==currentUser)
			return Result.errorResult(TradeStatusCode.E00325.getCode(),TradeStatusCode.E00325.getMsg());
		return tradeBusinesService.addOptional(marketId, currentUser.getId());

	}
	/**
	 * 获取用户自选行情	
	 * @param type 指定类型排序
	 * @param dec  0:升序默认，1：降序
	 * @param keyword 模糊查询，输入交易币名称或者买卖币名称
	 * @return
	 */
	@PostMapping(value="/trade/getOptinal")
	public Result<List<TradePrecoinEntityVO>> getOptinal(Integer type,Integer dec, String keyword) {	 
		Userinfo currentUser = RequestUtil.getCurrentUser();
		if(null==currentUser)
			return Result.errorResult(TradeStatusCode.E00325.getCode(),TradeStatusCode.E00325.getMsg());
		return tradeBusinesService.getOptinal(currentUser.getId(),type,dec,keyword);
	}
	/**
	 * 用户取消自选行情
	 * @param marketId 行情id
	 * @return
	 */
	@PostMapping(value="/trade/cancelOptional")
	public Result cancelOptional(@RequestParam Integer marketId){
		if(marketId==null||marketId<=0)
			return Result.errorResult(TradeStatusCode.E00401.getCode(),TradeStatusCode.E00401.getMsg());
		Userinfo currentUser = RequestUtil.getCurrentUser();
		if(null==currentUser)
			return Result.errorResult(TradeStatusCode.E00325.getCode(),TradeStatusCode.E00325.getMsg());
		return tradeBusinesService.cancelOptional(marketId,currentUser.getId());

	}
	/**
	 * 用户单笔委托成交详情
	 * @param id 委托id,
	 * @param userId 用户id
	 * @return
	 */
	@PostMapping(value="/trade/getUserTradeDetails")
	public Result<?> getUserTradeDetails(Integer id){
		Userinfo currentUser = RequestUtil.getCurrentUser();
		return tradeBusinesService.getUserTradeDetails(id,currentUser.getId());

	}
	
	/**
	 * 币币交易费率详情（分页）
	 * @param tradeCoinRate
	 * @param pageNum 页码
	 * @param pageSize 当前页数据量
	 * @return 
	 */
	@NotLogin
	@PostMapping(value="/trade/getTradeCoinRate")
	public Result<?> getTradeCoinRate(TradeCoinRateVO tradeCoinRate,Integer pageNum,Integer pageSize){
		return tradeBusinesService.getTradeCoinRate(tradeCoinRate, pageNum,pageSize);
		
	}
	/**
	 * 获取当前行情交易信息
	 *@param marketId
	 *@return 
	 */
	@NotLogin
	@PostMapping(value="/trade/getCurrentMarket")
	public Result<?> getCurrentMarket (Integer marketId){
		if(marketId==null||marketId<=0)
			return Result.errorResult(TradeStatusCode.E00401.getCode(),TradeStatusCode.E00401.getMsg());
		return tradeBusinesService.getCurrentMarket(marketId);
	}
	
	/**
	 * 绘画深度图数据
	 * @param marketId
	 * @return
	 */
	@NotLogin
	@PostMapping(value = "/trade/drawDepths")
	public Result<DepthVO2> drawDepths(Integer marketId){
		return tradeBusinesService.drawDepths(marketId);
		
		
	}
	
	
}
