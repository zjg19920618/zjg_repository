package com.boomhope.tms.util;

public class StringUtil {
	
	/**
	 * 获取下拉框值
	 * @param sel
	 * @return
	 */
	public static String getSel(String sel){
		if(sel != null && !"".equals(sel)){
			if(sel.equals("ALL")){
				return null;
			}
		}
		return sel;
	}
	
	
	 
}
