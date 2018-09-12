package com.skyline.common.util;

/**
 * 
* <p>Title: RadomCode</p>  
* <p>Description:随机字符工具类 </p>  
* @author kuangwenqiang  
* @date 2018年8月3日
 */

public class RadomCode {

	/**
	 * 
	 * <p>Title: generateRandomStr</p>  
	 * <p>Description:生成不重复随机字符串包括字母数字 </p>  
	 * @param len
	 * @return
	 */
	public static String generateRandomStr(int len) {
	    //字符源，可以根据需要删减
	    String generateSource = "0123456789abcdefghigklmnopqrstuvwxyz";
	    String rtnStr = "";
	    for (int i = 0; i < len; i++) {
	        //循环随机获得当次字符，并移走选出的字符
	        String nowStr = String.valueOf(generateSource.charAt((int) Math.floor(Math.random() * generateSource.length())));
	        rtnStr += nowStr;
	        generateSource = generateSource.replaceAll(nowStr, "");
	    }
	    return rtnStr;
	}
	
	public static void main(String[] args) {
		System.out.println(generateRandomStr(7));
	}
}
