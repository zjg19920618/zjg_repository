package com.boomhope.Bill.Util;

public class UtilString {
	
	/***
	 * 右补字符函数
	 * @param str
	 * @param length
	 * @param ch
	 */
	public static String rightFill(String str, int length, String ch){
		
		for (int i=str.getBytes().length; i<length; i++){
			str += ch;
		}
		
		return str;
	}

}
