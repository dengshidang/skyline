package com.skyline.common.util;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;


public class CommonUtil {
    
        /**
         * 获取UUID 去除"-"
         * 
         * @author cgx
         * @date 2017-6-14
         * @return
         */
        public static String getUUID() {
                return UUID.randomUUID().toString().replaceAll("-", "");
        }

        /**
         * 数据join方法
         * 
         * @param arr
         * @param split
         * @return
         */
        public static final <T> String ArrayJoin(T[] arr, String split) {
                if (arr == null) {
                        return null;
                }
                if (arr.length == 0) {
                        return "";
                }
                if (split == null) {
                        split = "";
                }
                StringBuilder sb = new StringBuilder();
                for (T t : arr) {
                        sb.append(t.toString()).append(split);
                }

                return sb.substring(0, sb.length() - split.length()).toString();
        }

        /**
         * integer 转 string
         * 
         * @param num 数值
         * @param digit 位数
         * @return
         */
        public static String integerToString(Integer num, Integer digit) {
                String str = "";
                if (num != null) {
                        str = num.toString();
                        if (num.toString().length() < digit) {
                                for (int i = digit; i > num.toString().length(); i--) {
                                        str = "0" + str;
                                }
                        }
                }
                return str;
        }

        /**
         * string转成integer数组
         * 
         * @param ids 有规律的字符串
         * @param split 分隔符
         * @return
         */
        public static Integer[] stringToIntegerArray(String ids, String split) {
                if (StringUtils.isBlank(ids)) {
                        return new Integer[0];
                }
                String[] idStrArr = ids.split(split);
                Integer[] idArr = new Integer[idStrArr.length];
                int i = 0;
                for (String id : idStrArr) {
                        idArr[i] = Integer.parseInt(id);
                        i++;
                }

                return idArr;
        }
        
        
        public static void main(String[] args) {
        	
       }
        

}
