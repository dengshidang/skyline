package com.skyline.common.constant;

/**
 * 币币交易常量
 * @author Yjian
 * @time 2018-07-13
 */
public class TradeConstant {

	/** 交易行情状态-开:0 **/
	public static int MARKETSIGN_ON = 0;
			
	/** 交易行情状态-关:1 **/
	public static int MARKETSIGN_OFF = 1;
	
	/** 交易类型-买入:0 **/
	public static int TRADETYPE_BUY = 0;
	
	/** 交易类型-卖出:1 **/
	public static int TRADETYPE_SELL = 1;
	
	/** 交易状态-挂起:0 **/
	public static int TRADESTATUS_HAN = 0;
	
	/** 交易状态-处理中:1 **/
	public static int TRADESTATUS_BEING = 1;
	
	/** 交易状态-完成 :2**/
	public static int TRADESTATUS_DONE = 2;
	
	/** 交易状态-撤销 :3**/
	public static int TRADESTATUS_CALL = 3;
	
	/** 成交状态-有效 :0**/
	public static int RECORDSTATUS_VALID = 0;
	
	/** 成交状态-无效 :1**/
	public static int RECORDSTATUS_VOID = 1;
	
	/**委托数据显示量。默认5条**/
	public static int RECORDSTATUS_COUNT = 5;

	/**成交量dealNum，dealSum 默认 0.0**/
	public static double RECORDSTATUS_DEAL_NUM_OR_SUM = 0.0;
}
