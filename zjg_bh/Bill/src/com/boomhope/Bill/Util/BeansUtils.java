package com.boomhope.Bill.Util;

import java.lang.reflect.Field;



/**
 * Title: 通过反射机制对bean中的所有属性清空
 *
 * @author: hk
 * 
 * @date: 2018年3月23日 上午10:26:19  
 * 
 */
public class BeansUtils {

	
	@SuppressWarnings("rawtypes")
	public static void beansSetNull(Object obj) throws Exception{
		Class cls = obj.getClass();
				
		Field[] files = cls.getDeclaredFields();
		for(int i=0;i<files.length;i++){
			Field f = files[i];
			f.setAccessible(true);
			f.set(obj, null);
		}
	}
}
