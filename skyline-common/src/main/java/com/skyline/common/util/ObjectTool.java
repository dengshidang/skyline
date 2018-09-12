package com.skyline.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.springframework.beans.BeanUtils;

public class ObjectTool {
	 public static boolean isBlank(Object...  param) {
	        if(param.length>0){
	        	for (Object object : param) {
	        		if(object==null){
						return true;
					}
	        		if(object instanceof String){
	        			String str=String.valueOf(object);
	        			if(str == null || str.trim().equals("") || str.trim().equals(" ")){
	        				return true;
	        			}
	        		}
				}
	        }
	        return false;
	 }
	  /**
	     * Map转成实体对象
	     * @param map map实体对象包含属性
	     * @param clazz 实体对象类型
	     * @return
	     */
	    public static <T>T mapToEntity(Object objMap, Class<T> clazz) {
	    	Map<String, Object> map=(Map<String,Object>)objMap;
	        if (map == null) {
	            return null;
	        }
	        T obj = null;
	        try {
	            obj = clazz.newInstance();

	            Field[] fields = obj.getClass().getDeclaredFields();
	            for (Field field : fields) {
	                int mod = field.getModifiers();
	                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
	                    continue;
	                }
	                field.setAccessible(true);
	                field.set(obj, map.get(field.getName()));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	        return obj;
	   }
	 
}
