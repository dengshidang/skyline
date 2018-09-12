package com.skyline.common.constant;

/**
 * 定义日期格式与间隔单位时间
 * 后期优化考虑增加月k线参数
 * @author dengshidang
 *
 */

public enum BaseTimeForat {
	/*分时K线查询参数定义*/
	TEN_SECOND(10l,"%Y-%m-%d %H:%i:%S","分时"),
	/*1分钟K线查询参数定义*/
	MINUTE_K(60l,"%Y-%m-%d %H:%i","分钟"),
	/*6分钟K线查询参数定义*/
	FIVE_MINUTE_K(60*5l,"%Y-%m-%d %H:%i","5分钟"),
	/*15分钟K线查询参数定义*/
	FIFTEENf_MINUTE(60*15l,"%Y-%m-%d %H:%i","15分钟"),
	/*30分钟K线查询参数定义*/
	THIRTY_MINUTE(60*30l,"%Y-%m-%d %H:%i","30分钟"),
	/*一小时K线查询参数定义*/
	HOUR_K(60*60l,"%Y-%m-%d %H","1个小时"),
	/*一天K线查询参数定义*/
	DAY_K(60l*60*24,"%Y-%m-%d %H","1天"),
	/*一周K线查询参数定义*/
	WEEK_K(60l*60*24*7,"%Y-%m-%d","1周");	
  
	private Long baseTime;
	private String format;
	private String node;
	
	public String getNode() {
		return node;
	}

	/**
	 * 
	 * @param baseTime //单位时间秒
	 * @param format //时间格式
	 * 
	 */
	private BaseTimeForat(Long baseTime,String format,String node) {
		this.baseTime = baseTime;
		this.format = format;
		this.node=node;
	}

	public Long getBaseTime() {
		return baseTime;
	}

	public String getFormat() {
		return format;
	}

   public static BaseTimeForat getFormat(Integer type) {
	   
		return BaseTimeForat.values()[type];
		
	}
  
}
