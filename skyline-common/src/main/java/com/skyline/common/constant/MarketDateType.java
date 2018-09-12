package com.skyline.common.constant;

/**
 * 
 * @author dengshidang
 *
 */
/*规定定时任务的间隔时间*/
public class MarketDateType {
	/*五秒*/
   public static final long FIVE_SECOND = 5000l;   
    /*30秒*/
   public static final long THIRTY_SECOND = 5000l*6;   
   /*一分钟*/
   public static final long MUNITE = 5000l*12;
   /*5分钟分钟k*/
   public static final long FIVE_MUNITE = 5000l*12*5;
   /*1小时k线*/
   public static final long HOUR = 5000l*12*60;
   /*周k线 一天跟新一次*/
   public static final long DAY= 5000l*12*60*24;
   /*月k线 一周跟新一次*/
  // public static final long MONTH = 5000l*12*60*24*7
}
