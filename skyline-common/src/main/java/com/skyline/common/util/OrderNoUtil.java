package com.skyline.common.util;

import java.util.Date;

public class OrderNoUtil {

	/** 充值提币订单前缀 **/
	private static String topupPR = "TP";
	
	/** 币币交易委托订单前缀 **/
	private static String coinPR = "CP";
	
	/** 币币交易成交订单前缀 **/
	private static String mabPR = "MP";
	
	/** C2C交易流水前缀 **/
	private static String transaPR = "RP";
	
	/** C2C交易订单前缀 **/
	private static String torderPR = "OP";
	
	private static String production(){
		String a = DateUtil.formatDate(new Date(), "yyMMddHHmm");
    	int sum = (int)((Math.random()*9+1)*100000);
    	return a+sum;
	}
	
	/**
	 * 获取充值提币订单编号
	 * @return
	 */
	public static String getTopupOdrderNo(){
		return topupPR+production();
	}
	
	/**
	 * 获取币币交易委托订单编号
	 * @return
	 */
	public static String getCoinOdrderNo(){
		return coinPR+production();
	}
	
	/**
	 * 获取币币交易成交订单编号
	 * @return
	 */
	public static String getMabOdrderNo(){
		return mabPR+production();
	}
	
	/**
	 * 获取C2C交易流水编号
	 * @return
	 */
	public static String getTransaOdrderNo(){
		return transaPR+production();
	}
	
	/**
	 * 获取C2C交易订单编号
	 * @return
	 */
	public static String getTorderOdrderNo(){
		return torderPR+production();
	}
	/**
	 * 
	* @Title: getRemarkCode
	* @Description: TODO(获取备注码)
	* @param @return    参数
	* @return String    返回类型
	* @author xiaozhijian
	* @throws
	 */
	public static String getRemarkCode(){
		int sum = (int)((Math.random()*9+1)*100000);
		return String.valueOf(sum);
	}
	
}
