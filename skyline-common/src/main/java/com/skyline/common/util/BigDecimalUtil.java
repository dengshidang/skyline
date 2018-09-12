package com.skyline.common.util;

import java.math.BigDecimal;

public class BigDecimalUtil {
	
	/**
	 ** 精确加法运算
	 * @param num1
	 * @param num2
	 * @return 
	 */
	public static Double add(String num1,String num2) {
		BigDecimal big1=new BigDecimal(num1);
		BigDecimal big2=new BigDecimal(num2);
		return big1.add(big2).doubleValue();
	}
	
	/**
	 ** 精确减法运算
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static Double subtract(String num1,String num2){   
		BigDecimal big1 = new BigDecimal(num1);   
		BigDecimal big2 = new BigDecimal(num2);   
		return big1.subtract(big2).doubleValue();   
	} 
	
	/**
	 **精确乘法运算
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static Double multiply(String num1,String num2){   
		BigDecimal big1 = new BigDecimal(num1);   
		BigDecimal big2 = new BigDecimal(num2);   
		return big1.multiply(big2).doubleValue();   
	}
	
	/**
	 ** 精确除法运算
	 * @param num1
	 * @param num2 
	 * @param scale	精确小数点位数
	 * @return
	 */
	public static Double divide(String num1,String num2,int scale){   
		if(scale<0){   
			throw new IllegalArgumentException("刻度必须是正整数或零");   
		}   
		BigDecimal big1 = new BigDecimal(num1);   
		BigDecimal big2 = new BigDecimal(num2);   
		return big1.divide(big2,scale,BigDecimal.ROUND_HALF_UP).doubleValue(); 
	}
}
