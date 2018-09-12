
package com.skyline.common.util;

/**
 * @author chenzilong
 * @Description TODO(随机验证码的工具类)
 * @date 2018年7月13日下午3:39:22
 */
public class RandomUtil {

	private RandomUtil() {
		
	}

	// 获取6位随机验证码
	public static String getRandom() {
		String num = "";
		for (int i = 0 ; i < 6 ; i ++) {
			num = num + String.valueOf((int) Math.floor(Math.random() * 9 + 1));
		}
		return num;
	}
}
