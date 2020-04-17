package com.boomhope.Bill.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 
 * 日期工具类
 *	
 */
public class DateUtil {
	
	/**
	 * 日期转换
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String dateString) throws ParseException{
		DateFormat fmt =new SimpleDateFormat("yyyyMMdd"); 
	    Date date = fmt.parse(dateString); 
	    return date;
	}
	
	/**
	 * 日期转换
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static String getDate(String dateString,String format) throws ParseException{
		DateFormat fmt =new SimpleDateFormat(format); 
	    Date date = fmt.parse(dateString); 
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	    return sdf.format(date);
	}
	
	/**
	 * 日期转换
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static String getDate(String dateString,String oldformat,String newformat) throws ParseException{
		DateFormat fmt =new SimpleDateFormat(oldformat); 
	    Date date = fmt.parse(dateString); 
	    SimpleDateFormat sdf = new SimpleDateFormat(newformat);
	    return sdf.format(date);
	}
	
	/**
	 * 获取当前日期
	 * @param format
	 * @return
	 */
	public static String getNowDate(String format){
		Date currentTime = new Date();
		   SimpleDateFormat formatter = new SimpleDateFormat(format);
		   String dateString = formatter.format(currentTime);
		   return dateString;
	}
	/**
	 * 将当前日期以字符串形式返回
	 * @Title: getDateString 
	 * @param @param inDate	Date类型
	 * @param @return    参数 
	 * @return String    返回类型 
	 * @throws
	 */
	public static String getDateString(java.util.Date inDate) {
	    return inDate.toString();
	}
	public static void main(String args[]){
		
			System.out.println(getNowDate("yyyyMMddhhmmss"));

		
	}
	
	/**
	 * 返回当日日期
	 * @Title: getDateTime 
	 * @param @return    当天日期，以"传入格式输出"格式输出
	 * @return String    返回类型 
	 * @throws
	 */
	public static String getDateTime(String format) {
		return getCurrentDateByFormat(format);
	}
	
	/**
	  * 将当前时间的显示格式用给定的格式显示
	  * @Title: getCurrentDateByFormat 
	  * @param @param formatStr	String	日期格式
	  * @param @return    返回当日日期
	  * @return String    返回类型 
	  * @throws
	  */   
	public static String getCurrentDateByFormat(String formatStr){
		  java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(formatStr);
		  return formatter.format(new Date());
	}
}
