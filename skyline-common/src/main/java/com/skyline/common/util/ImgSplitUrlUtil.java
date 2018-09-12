package com.skyline.common.util;

/**
 * 
* <p>Title: ImgSplitUrlUtil</p>  
* <p>Description：截取相对路径文件文件名 </p>  
* @author kuangwenqiang  
* @date 2018年8月3日
 */
public class ImgSplitUrlUtil {

	
	public static String splitUrl(String url){
		String result="";
		if(url!=null||url!=""){
			String[] str = url.split("/");
			if(str!=null&&str.length>0){
				int length = str.length;
				result = str[length-1];
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		String url = "http://localhost:9005/d8036e52-e9cf-4f2c-8028-4d243f702367fm.png";
		
			String[] str = url.split("/");
			int length = str.length;
			System.out.println(str[length-1]);
	}
	
}
