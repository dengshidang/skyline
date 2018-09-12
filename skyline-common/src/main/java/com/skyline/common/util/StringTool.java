package com.skyline.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串处理工具类
 * 
 * @author yangyang
 * @since 2015-01-09
 */
public class StringTool {


    private final static Pattern wipeExtraSpacesRegex = Pattern.compile("\\s{2,}|\\t|\\n|\\r");

    /**
     * 去除多余的空格，即连续多空格处替换为一个空格,并且去除所有换行或回车或制表符，用空格替换
     * 
     * @param str
     * @return
     * @author yangyang
     * @since 2015-01-09
     */
    public static String wipeExtraSpaces(String str) {
        if (str == null || "".equals(str)) {
            return null;
        }
        return wipeExtraSpacesRegex.matcher(str).replaceAll(Matcher.quoteReplacement(" "));
    }

    /**
     * 获取数据源凭证
     * 
     * @param str
     * @return
     * @author maji
     * @since 2015-12-02
     */
    public static String getDataSourceKey() {
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        return "DIC" + id.toUpperCase();
    }

    /**
     * 字符串是否为null或空
     * 
     * @param str
     * @return
     * @author yangyang
     * @since 2015-01-09
     */
    public static boolean isBlank(String str) {
        if (str == null || str.trim().equals("") || str.trim().equals(" ")) {
            return true;
        }
        return false;
    }

    /**
     * 字符串是否为非null且非空
     * 
     * @param str
     * @return
     * @author yangyang
     * @since 2015-01-09
     */
    public static boolean isNotBlank(String str) {

        return !isBlank(str);
    }

    /**
     * 字符串是否为非null且非空
     * 
     * @param str
     * @return
     * @author maji
     * @since 2015-07-13
     */
    public static boolean isExits(String str, List<String> list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        int len = list.size();
        for (int i = 0; i < len; i++) {
            if (list.get(i).equals(str)) {
                return true;
            }
        }
        return false;
    }

  
    /**
     * 得到一个唯一的标示符
     * 
     * @return
     */
    public synchronized static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 得到一个唯一的标示符数组
     * 
     * @param number
     * @return
     */
    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] uList = new String[number];
        for (int i = 0; i < number; i++) {
            uList[i] = getUUID();
        }
        return uList;
    }

    /**
     * 去掉数组里为null的值
     * 
     * @param number
     * @return
     */
    public static String[] removeNull(String[] str) {
        ArrayList<String> list = new ArrayList<String>();
        for (String s : str) {
            if (s != null) {
                list.add(s);
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * 得到一个唯一的标示符数组
     * 
     * @param number
     * @return
     */
    public static Integer[] removeNull(Integer[] str) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (Integer s : str) {
            if (s != null) {
                list.add(s);
            }
        }
        return list.toArray(new Integer[list.size()]);
    }

    public static boolean isExist(List<String> list, String str) {
        if (list == null) {
            return false;
        }
        int len = list.size();
        for (int i = 0; i < len; i++) {
            if (list.get(i).equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static List<Long> changeTypeStringsToLongs(String[] List) {
        List us = new ArrayList();
        for (int j = 0; j < List.length; ++j)
            if ((List[j] != null) && (List[j].length() > 0))
                us.add(Long.valueOf(List[j]));

        return us;
    }

    public static String getValue(String value, String deflaut) {
        return ((isBlank(value)) ? deflaut : value);
    }

    public static Integer getIntValue(String value, Integer deflaut) {
        return ((isBlank(value)) ? deflaut : Integer.valueOf(value));
    }

    public static Long getLongValue(String value, Long deflaut) {
        return ((isBlank(value)) ? deflaut : Long.valueOf(value));
    }

    public static String subNSTring(String value, int n) {
        if (value.length() > n)
            return value.substring(0, n);

        return value;
    }

    public static String getDatasourceSequence(String sequence, int sum) {
        if (sequence == null)
            return null;

        String temp = "0000000000";
        if (sum > temp.length())
            sum = 9;

        int index = sequence.indexOf(",");
        if ((index == 0) || (index == sequence.length() - 1))
            return null;

        int len = sequence.length() - index - 1;
        if (len >= 5)
            return sequence.replace(",", "");

        temp = temp.substring(0, sum - len);
        return sequence.replace(",", temp);
    }

    /**
     * 获得12个长度的十六进制的UUID
     * @return UUID
     */
    public static String get12UUID(){
        UUID id=UUID.randomUUID();
        String[] idd=id.toString().split("-");
        return idd[0]+idd[1];
    }
    
    /**
     * 获得16个长度的十六进制的UUID
     * @return UUID
     */
    public static String get16UUID(){
        UUID id=UUID.randomUUID();
        String[] idd=id.toString().split("-");
        return idd[0]+idd[1]+idd[2];
    }
    /***
     * 科学计数法转换
     * @param amount
     * @return
     */
    public static String bigDecimalToString(String amount) {
		BigDecimal big=new BigDecimal(amount);
		return big.toPlainString();
	}
   
    
    public static void main(String[] args) {
      /*  String s = StringTool.getDatasourceSequence("Mysql,10", 5);
        System.out.println(s);*/
       /* String[] split = "Oysql,10".split(",");
        String str = split[0];
        char charAt = str.charAt(0);
        for(int i = 0;i<59;i++){
           System.out.println(charAt +"_"+get16UUID());
        }*/
    	System.out.println(get16UUID());
    }

}
