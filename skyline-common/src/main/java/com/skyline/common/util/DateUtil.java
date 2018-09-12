package com.skyline.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public final class DateUtil {

	
		private static SimpleDateFormat synTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
		/**
		 * 时间戳转日期格式
		 * @param seconds
		 * @return
		 */
		public static String timeStampToDate(String timestamp) {
			if (timestamp == null || timestamp.isEmpty() || timestamp.equals("null")) {
				return "";
			}
			return synTime.format(new Date(Long.valueOf(timestamp + "000")));
		}
		
	
        /**
         * 计算两个日期之间相差的天数
         * 
         * @param smdate 较小的时间
         * @param bdate 较大的时间
         * @return 相差天数
         * @throws ParseException
         */
        public static int daysBetween(Date smdate, Date bdate) throws ParseException {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                smdate = sdf.parse(sdf.format(smdate));
                bdate = sdf.parse(sdf.format(bdate));
                Calendar cal = Calendar.getInstance();
                cal.setTime(smdate);
                long time1 = cal.getTimeInMillis();
                cal.setTime(bdate);
                long time2 = cal.getTimeInMillis();
                long between_days = (time2 - time1) / (1000 * 3600 * 24);
                return Integer.parseInt(String.valueOf(between_days));
        }

        /**
         * 计算两个日期之间相差的天数
         * @author cgx
         * @date 2017-6-14
         * @param smdate
         * @param bdate
         * @return
         * @throws ParseException
         */
        public static int daysBetween(String smdate, String bdate) throws ParseException {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                cal.setTime(sdf.parse(smdate));
                long time1 = cal.getTimeInMillis();
                cal.setTime(sdf.parse(bdate));
                long time2 = cal.getTimeInMillis();
                long between_days = (time2 - time1) / (1000 * 3600 * 24);
                return Integer.parseInt(String.valueOf(between_days));
        }

        public static Date getDiffDay(Date date, int days) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DAY_OF_MONTH, days);
                return c.getTime();
        }

        /**
         * 获取之前的日期列表
         * 
         * @param date
         * @param days
         * @return
         */
        public static List<Date> getBeforeDays(Date date, int days) {
                Calendar c = Calendar.getInstance();
                List<Date> dl = new ArrayList<Date>();
                if (days > 0) {
                        for (int i = days; i > 0; i--) {
                                c.setTime(date);
                                c.add(Calendar.DAY_OF_MONTH, -i);
                                dl.add(c.getTime());
                        }
                }
                return dl;
        }

        /**
         * 获取之后的日期列表
         * 
         * @param date
         * @param days
         * @return
         */
        public static List<Date> getAfterDays(Date date, int days) {
                Calendar c = Calendar.getInstance();
                List<Date> dl = new ArrayList<Date>();
                if (days > 0) {
                        for (int i = 1; i <= days; i++) {
                                c.setTime(date);
                                c.add(Calendar.DAY_OF_MONTH, i);
                                dl.add(c.getTime());
                        }
                }
                return dl;
        }

        public static Date getCurrentTime() {
                Calendar c = Calendar.getInstance();
                return c.getTime();
        }

        public static java.sql.Timestamp getCurrentTimestamp() {
                return new java.sql.Timestamp(getCurrentTime().getTime());
        }

        public static java.sql.Timestamp getCurrentTimestamp(Date date) {
                return new java.sql.Timestamp(date.getTime());
        }

        /**
         * 当前Calendar
         * 
         * @return
         *
         */
        public static Calendar currentCal() {
                return Calendar.getInstance();
        }

        /**
         * Calendar
         * 
         * @return
         *
         */
        public static Calendar calendar(Calendar cal) {
                return null != cal ? cal : currentCal();
        }

        /**
         * 当前日期
         * 
         * @param cal
         * @return
         *
         */
        public static Date currentDate() {
                return calendar(null).getTime();
        }

        /**
         * 当前日期
         * 
         * @param cal
         * @return
         *
         */
        public static Date currentDate(Calendar cal) {
                return calendar(cal).getTime();
        }

        /**
         * 年份
         * 
         * @param cal
         * @return
         *
         */
        public static Integer currentYear(Calendar cal) {
                return calendar(cal).get(Calendar.YEAR);
        }

        /**
         * 月份
         * 
         * @param cal
         * @return
         *
         */
        public static Integer currentMonth(Calendar cal) {
                return calendar(cal).get(Calendar.MONTH) + 1;
        }

        /**
         * 几号
         * 
         * @param cal
         * @return
         *
         */
        public static Integer currentDayOfMonth(Calendar cal) {
                return calendar(cal).get(Calendar.DAY_OF_MONTH);
        }

        /**
         * 小时
         * 
         * @param cal
         * @return
         *
         */
        public static Integer currentHour(Calendar cal) {
                return calendar(cal).get(Calendar.HOUR_OF_DAY);
        }

        /**
         * 分钟
         * 
         * @param cal
         * @return
         *
         */
        public static Integer currentMinute(Calendar cal) {
                return calendar(cal).get(Calendar.MINUTE);
        }

        /**
         * 秒数
         * 
         * @param cal
         * @return
         *
         */
        public static Integer currentSecond(Calendar cal) {
                return calendar(cal).get(Calendar.SECOND);
        }

        /**
         * 当前毫秒数
         * 
         * @param cal
         * @return
         *
         */
        public static Integer currentMilliSecond(Calendar cal) {
                return calendar(cal).get(Calendar.MILLISECOND);
        }

        /**
         * 长毫秒数
         * 
         * @param cal
         * @return
         *
         */
        public static Long currentMilliLong(Calendar cal) {
                return currentDate(cal).getTime();
        }

        /**
         * 获取开始时间和结束时间
         * @return yyyy-MM-dd 、yyyy-MM-dd+1
         * @throws ParseException 日期转换异常
         */
        public static List<String> getStartEnd(String static_date) throws ParseException {
                List<String> list = new ArrayList<String>();
                Date createData = new Date();
                createData = formatStringToDate(static_date, "yyyy-MM-dd");
                String formatDate1 = formatDate(createData, "yyyy-MM-dd");
                list.add(formatDate1);
                Calendar cal = Calendar.getInstance();
                cal.setTime(createData);
                cal.add(Calendar.DATE, 1);	
                String formatDate2 = (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());
                list.add(formatDate2);
                return list;
        }

        /**
         * 只针对前端传过来的时间格式 "2017-06-26T03:00:13.501Z"; + 8小时 本地时间
         * 
         * @throws ParseException 格式错误
         */
        public static Date getStringToDate(String dateTiem){
                String b = dateTiem.replaceAll("T", " ");
                Date date = null;
				try {
					date = formatStringToDate(b, "yyyy-MM-dd HH:mm:ss");
				} catch (ParseException e) {
					e.printStackTrace();
				}
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.HOUR, 8);
                return cal.getTime();
        }

        /**
         * 当天最后时间
         */
        public static Date dayEnd(Date date) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                // calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
                Calendar c = Calendar.getInstance();
                c.setTime(calendar.getTime());
                c.set(Calendar.HOUR_OF_DAY, 23);
                c.set(Calendar.MINUTE, 59);
                c.set(Calendar.SECOND, 59);
                c.set(Calendar.MILLISECOND, 999);
                return c.getTime();
        }

        /**
         * 获取上个月的今天
         * 
         * @param date
         * @return
         */
        public static Date getLastMonth(Date date) {
                Calendar now = Calendar.getInstance();
                now.setTime(date);
                Calendar cal = Calendar.getInstance();
                cal.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                cal.add(Calendar.MONTH, -1);// 取前一个月的同一天
                return cal.getTime();
        }

        /**
         * 转为 yyyy-MM-dd
         */
        public static String getTimeDay(Date date) {
                return formatDate(date, "yyyy-MM-dd");
        }

        /**
         * 转为 HH:mm
         */
        public static String getTimeHours(Date date) {
                return formatDate(date, "HH:mm");
        }

        /**
         * 转为yyyyMM
         */
        public static String getTimeMonth(Date date) {
                return formatDate(date, "yyyyMM");
        }

        /**
         * 转为 yyyy-MM-dd HH:mm:ss
         */
        public static String getTimeDayHours(Date date) {
                return formatDate(date, "yyyy-MM-dd HH:mm:ss");
        }
        
        /**
         * 转为 yyyy-MM-dd HH:mm:ss
         */
        public static String getTimeDayHours() {
                return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        }
        /**
         * 将时间按指定的格式转为字符串
         * @param date 时间
         * @param format 格式
         * @return
         * @author Z.CJ
         * @date 2018年3月5日
         */
        public static String formatDate(Date date, String format) {
                if (date != null && format != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(format);
                        return sdf.format(date);
                } else {
                        return null;
                }

        }

        /**
         * 将时间字符串转为日期格式
         * @param dateStr 时间字符串
         * @param format 格式
         * @return
         * @throws ParseException
         * @author Z.CJ
         * @date 2018年3月5日
         */
        public static Date formatStringToDate(String dateStr, String format) throws ParseException {
                if (dateStr != null && format != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(format);
                        return sdf.parse(dateStr);
                } else {
                        return null;
                }

        }
        
        /**
         * 时间字符串对比
         * @param dateStr1
         * @param dateStr2
         * @param format
         * @return
         */
        public static boolean compareDate(String dateStr1,String dateStr2,String format){
        	Date date1 = null;
        	Date date2 = null;
        	try {
				date1 = formatStringToDate(dateStr1, format);
				date2 = formatStringToDate(dateStr2, format);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	boolean result = date1.getTime() < date2.getTime();
        	return result;
        }
        
        /**
         * 时间对比
         * @param date1
         * @param date2
         * @param format
         * @return
         */
        public static boolean compareDate(Date date1,Date date2){
        	return date1.getTime() < date2.getTime();
        }

        
        public static String getCurTimeString() {
            return synTime.format(getCurrentTime());
        }
        /**
         * 
        * @Title: calLastedTime
        * @Description: TODO(与当前时间相差的秒数)
        * @author xzj
        * @param @param startDate
        * @param @return    参数
        * @return int    返回类型
        * @throws
         */
        public static  int calLastedTime(String startDate) {
        	   Date date = null;
				try {
					date = formatStringToDate(startDate, "yyyy-MM-dd HH:mm:ss");
				} catch (ParseException e) {
					e.printStackTrace();
				}
        	  long a = new Date().getTime();
        	  long b = date.getTime();
        	  int c = (int)((a - b) / 1000);
        	  return c;
       }
        /**
         * 
         * <p>Title: getNowDate</p>  
         * <p>Description:获取当前时间  </p>  
         * @return String  yyyy-MM-dd HH:mm:ss
         */
      public static String getNowDate(){
    	  Date currentTime = new Date();
    	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	  String dateString = formatter.format(currentTime);
    	  return dateString;
      };
      
      
       public static void main(String[] args) {
                System.out.println(DateUtil.getNowDate());

        }

}
