package com.boomhope.tms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 
 * 日志工具类
 *	
 */
public class DateUtil {

	/**
	 * 接受YYYY-MM-DD的日期字符串参数,返回两个日期相差的天数
	 * @Title:getDistDates
	 * @param start	String类型	起始日期
	 * @param end	String类型	结束日期
	 * @return	long
	 * @throws ParseException
	 */
	public long getDistDates(String start, String end) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = sdf.parse(start);
		Date endDate = sdf.parse(end);
		return getDistDates(startDate, endDate);
	}

	/**
	 * 返回两个日期相差的天数
	 * @Title:getDistDates
	 * @param startDate 起始日期
	 * @param endDate	结束日期
	 * @return			long 天数
	 * @throws ParseException
	 */
	public long getDistDates(Date startDate, Date endDate) {
		long totalDate = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		long timestart = calendar.getTimeInMillis();
		calendar.setTime(endDate);
		long timeend = calendar.getTimeInMillis();
		totalDate = Math.abs((timeend - timestart)) / (1000 * 60 * 60 * 24);
		return totalDate;
	}

	/**
	 * 获取与_fromdate相差_monthCount个月的日期
	 * @Title：getDistMonths
	 * @param 	_fromdate	:YYYY-MM-DD           
	 * @param 	_monthCount
	 * @return	resultDate	:YYYY-MM-DD	
	 * @throws ParseException
	 */
	public String getDistMonths(String _fromdate, int _monthCount)
			throws ParseException {
		String resultDate = "";
		int year, month, date;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(_fromdate));
		year = calendar.get(Calendar.YEAR);
		date = calendar.get(Calendar.DAY_OF_MONTH);
		month = calendar.get(Calendar.MONTH) + 1 + _monthCount;
		int c = new Integer((month - 1) / 12);
		month = month % 12;
		if (month == 0)
			month = 12;
		year += c;
		resultDate = year + "-" + month + "-" + date;
		return resultDate;
	}

	/**
	 * 计算每个月的天数，以数组返回
	 * @Title：countMonthDates
	 * @param 	months 	int		月份数，即数组长度
	 * @param 	date	Date	起始日期
	 * @return	String[]	
	 * @throws ParseException
	 */
	public String[] countMonthDates(int months, Date date)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String _date = date == null ? sdf.format(new Date()) : sdf.format(date);// 如果不给定起算时间则从今天算起

		return countMonthDates(months, _date);
	}

	/**
	 * 计算每个月的天数，以数组返回
	 * @Title：countMonthDates
	 * @param 	months 	int		月份数，即数组长度
	 * @param 	date	String	起始日期
	 * @return	String[]	
	 * @throws ParseException
	 */
	public String[] countMonthDates(int months, String date)
			throws ParseException {
		String[] results = null;// 结果
		String _today = date == null ? new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date()) : date;// 如果不给定起算时间则从今天算起
		int _months = months > 0 ? months : 24;// 如果不给定计算的月数则计算未来两年里面的24月
		String startDate = getDistMonths(_today, 0);// 获得起算日期的YYYY-MM-DD格式的字符串日期
		results = new String[_months];
		for (int i = 1; i <= _months; i++) {
			String nextMonthDate = getDistMonths(_today, i);// 每个月的截至日期
			String desc = startDate + " 至 " + nextMonthDate;
			long dates = getDistDates(startDate, nextMonthDate);// 返回天数
			results[i - 1] = desc + " ：共 " + dates + " 天！";
			startDate = nextMonthDate;// 当前月的结束日期作为下一个月的起始日期
		}
		return results;
	}
	
	/**
	 * 返回当日日期
	 * @Title: getDateTime 
	 * @param @return    当天日期，以"yyyy-MM-dd HH:mm:ss"格式输出
	 * @return String    返回类型 
	 * @throws
	 */
	public static String getDateTime() {
		return getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss");
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
	
	/**
	 * 返回给定时间毫秒数指定的时间，起始时间为：1970-01-01 08:00:00。
	 * @Title: getDateTime 
	 * @param @param al_datetime	long类型		时间的毫秒数
	 * @param @return    对应毫秒数的日期
	 * @return String    返回类型 
	 * @throws
	 */
	public static String getDateTime(long al_datetime) {
	    java.util.Date date = new java.util.Date(al_datetime);
	    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
	        "yyyy-MM-dd HH:mm:ss");
	    return formatter.format(date);
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
	
	  /**
	   * 返回给定日期后数days天的日期
	   * @Title: getDateNDays 
	   * @param @param date	Date类型	给定时间
	   * @param @param days	int类型	天数
	   * @param @return    日期
	   * @return java.util.Date    返回类型 
	   * @throws
	   */
	public static java.util.Date getDateNDays(java.util.Date date, int days) {
	    if (days > 36500 || days < -36500) {
	      System.out.println("璇锋妸鏃ユ湡闄愬埗鍦�100骞村唴");
	      return null;
	    }
	    long l1 = 24, l2 = 60, l3 = 1000, l4 = days;
	    long lDays = l1 * l2 * l2 * l3 * l4; 
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    long lCurrentDate = calendar.getTimeInMillis(); 
	    long lUpdatedDate = lCurrentDate + lDays; 
	    java.util.Date dateNew = new java.util.Date(lUpdatedDate); 
	    return dateNew;
	}
	
	  /**
	   * 返回给定日期后数months月那天的日期
	   * @Title: getDateNMonths 
	   * @param @param date	Date类型，给定日期
	   * @param @param months	int类型，月份数
	   * @param @return    参数 
	   * @return Date    返回类型 
	   * @throws
	   */
	public static Date getDateNMonths(java.util.Date date, int months) {
	    if (months == 0) { 
	      return date;
	    }
	    if (months > 1200 || months < -1200) { 
	      System.out.println("璇锋妸鏃ユ湡闄愬埗鍦�100骞村唴");
	      return null;
	    }
	    GregorianCalendar gc = new GregorianCalendar();
	    gc.setTime(date);
	    gc.add(Calendar.MONTH, months);
	    java.util.Date date2 = gc.getTime();
	    return date2;
	}

	  /**
	   * 将给定的日期以给定的格式输出
	   * 如果给定格式为空，则日期格式以"yyyy-MM-dd HH:mm:ss"返回
	   * @Title: formatDate 
	   * @param @param date	Date类型
	   * @param @param format	String类型，日期格式
	   * @param @return    参数 
	   * @return String    返回类型 
	   * @throws
	   */
	public static String formatDate(Date date,String format){
	    if (date == null){
	      return "";
	    }
	    if (format == null || format.trim().equals("")){
	      format = "yyyy-MM-dd HH:mm:ss";
	    }
	    Locale locale = new Locale("en","US");
	    SimpleDateFormat formatter = new SimpleDateFormat(format.trim(),locale);
	    return formatter.format(date);
	}

	  /**
	   * 返回当前日期,格式为yyyy-MM-dd
	   * @Title: getDate 
	   * @param @return    参数 
	   * @return java.sql.Date    返回类型 
	   * @throws
	   */	
	public static java.sql.Date getDate() {
	    Calendar oneCalendar = Calendar.getInstance();
	    return getDate(oneCalendar.get(Calendar.YEAR),
	                   oneCalendar.get(Calendar.MONTH) + 1,
	                   oneCalendar.get(Calendar.DATE));
	}
	  /**
	   * 返回给定的两个日期相差的天数
	   * @Title: getIntervalDay 
	   * @param @param startDate
	   * @param @param endDate
	   * @param @return    参数 
	   * @return int    返回类型 
	   * @throws
	   */
	public static int getIntervalDay(java.sql.Date startDate,java.sql.Date endDate) {
	    long startdate = startDate.getTime();    
	    long enddate = endDate.getTime();
	    long interval = enddate - startdate;
	    int intervalday = (int) interval / (1000 * 60 * 60 * 24);
	    return intervalday;
	}
	  /**
	   * 
	   * @Title: getDate 
	   * @param @param yyyy
	   * @param @param MM
	   * @param @param dd
	   * @param @return    参数 
	   * @return java.sql.Date    返回类型 
	   * @throws
	   */
	public static java.sql.Date getDate(int yyyy, int MM, int dd) {
	    if (!verityDate(yyyy, MM, dd)) {
	      throw new IllegalArgumentException("This is illegimate date!");
	    }

	    Calendar oneCalendar = Calendar.getInstance();
	    oneCalendar.clear();
	    oneCalendar.set(yyyy, MM - 1, dd);
	    return new java.sql.Date(oneCalendar.getTime().getTime());
	}
	  /**
	   * 判断年，月，日对应的数值是否是正确的
	   * @Title: verityDate 
	   * @param @param yyyy	年的数值是否是正确的
	   * @param @param MM	月的数值是否是正确的
	   * @param @param dd	日的数值是否是正确的
	   * @param @return    参数 
	   * @return boolean    返回类型 
	   * @throws
	   */
	public static boolean verityDate(int yyyy, int MM, int dd) {
	    boolean flag = false;

	    if (MM >= 1 && MM <= 12 && dd >= 1 && dd <= 31) {
	      if (MM == 4 || MM == 6 || MM == 9 || MM == 11) {
	        if (dd <= 30) {
	          flag = true;
	        }
	      }  else if (MM == 2) {
	        if (yyyy % 100 != 0 && yyyy % 4 == 0 || yyyy % 400 == 0) {
	          if (dd <= 29) {
	            flag = true;
	          }
	        } else if (dd <= 28) {
	          flag = true;
	        }
	      } else {
	        flag = true;
	      }
	    }
	    return flag;
	}
	  /**
	   * 返回给定日期的下一天的日期
	   * @Title: nextDate 
	   * @param @param date
	   * @param @return    参数 
	   * @return String    返回类型 
	   * @throws
	   */	  
	public static String  nextDate(String date) {
		  
		  	int yyyy = Integer.valueOf(date.substring(0, 4));
			int MM = Integer.valueOf(date.substring(4,6));
			int dd = Integer.valueOf(date.substring(6,8));

	        if (MM == 4 || MM == 6 || MM == 9 || MM == 11) {
		        if (dd < 30) {
		          dd++;
		        }else{
		          if(MM < 12){
		        	  MM++;
		        	  dd = 1;
		          }else{
		        	  yyyy++;
		        	  MM = 1;
		        	  dd = 1;
		          }
		        }
	      }else if (MM == 2) {
		        if (yyyy % 100 != 0 && yyyy % 4 == 0 || yyyy % 400 == 0) {
		        	if (dd < 29) {
		  	          dd++;
		  	        }else{
		  	          if(MM < 12){
		  	        	  MM++;
		  	        	  dd = 1;
		  	          }else{
		  	        	  yyyy++;
		  	        	  MM = 1;
		  	        	  dd = 1;
		  	          }
		  	        }
		        }else{
		        	if (dd < 28) {
		  	          dd++;
		  	        }else{
		  	          if(MM < 12){
		  	        	  MM++;
		  	        	  dd = 1;
		  	          }else{
		  	        	  yyyy++;
		  	        	  MM = 1;
		  	        	  dd = 1;
		  	          }
		  	        }
		        }
	      }else{
	    	  if (dd < 31) {
	  	          dd++;
	  	        }else{
	  	          if(MM < 12){
	  	        	  MM++;
	  	        	  dd = 1;
	  	          }else{
	  	        	  yyyy++;
	  	        	  MM = 1;
	  	        	  dd = 1;
	  	          }
	  	        }
	      }
	      StringBuffer sb = new StringBuffer("");
	  	  sb.append(yyyy);
	  	  if(MM <10){
	  		  sb.append("0"+MM); 
	  	  }else{
	  		  sb.append(MM); 
	  	  }
	  	  if(dd < 10){
	  		  sb.append("0"+dd);
	  	  }else{
	  		  sb.append(dd);
	  	  }
	    
	    return sb.toString();
	}
	  /**
	   * date的格式为yyyyMMdd,返回给定日期的上一天的日期
	   * @Title: preDate 
	   * @param @param date
	   * @param @return    参数 
	   * @return String    返回类型 
	   * @throws
	   */	  
	public static String  preDate(String date) {//
		  int yyyy = Integer.valueOf(date.substring(0, 4));
		  int MM = Integer.valueOf(date.substring(4,6));
		  int dd = Integer.valueOf(date.substring(6,8));
		  
		  if(dd > 1){
			  dd--;
		  }else{
			  --MM;
			  if(MM > 0){
				  if(MM == 4 || MM == 6 || MM == 9 || MM == 11){
					  dd = 30;
				  }else if(MM == 2){
					  if (yyyy % 100 != 0 && yyyy % 4 == 0 || yyyy % 400 == 0) {
						  dd = 29;
					  }else{
						  dd = 28;
					  }
				  }else{
					  dd = 31;
				  }
		  	  }else{
		  		  --yyyy;
		  		  MM = 12;
		  		  dd = 31;
		  	  }
		  }
		  StringBuffer sb = new StringBuffer("");
		  sb.append(yyyy);
		  if(MM <10){
			  sb.append("0"+MM); 
		  }else{
			  sb.append(MM); 
		  }
		  if(dd < 10){
			  sb.append("0"+dd);
		  }else{
			  sb.append(dd);
		  }
	  
		  return sb.toString();
	}

	  /**
	   * 返回给定两个日期之间相差多少个月
	   * @Title: getIntervalMonth 
	   * @param @param startDate
	   * @param @param endDate
	   * @param @return    参数 
	   * @return int    返回类型 
	   * @throws
	   */
	public static int getIntervalMonth(java.util.Date startDate, java.util.Date endDate) {
	    java.text.SimpleDateFormat mmformatter = new java.text.SimpleDateFormat("MM");
	    int monthstart = Integer.parseInt(mmformatter.format(startDate));
	    int monthend = Integer.parseInt(mmformatter.format(endDate));
	    java.text.SimpleDateFormat yyyyformatter = new java.text.SimpleDateFormat("yyyy");
	    int yearstart = Integer.parseInt(yyyyformatter.format(startDate));
	    int yearend = Integer.parseInt(yyyyformatter.format(endDate));
	    return (yearend - yearstart) * 12 + (monthend - monthstart);
	}
	
	/**
	 * 获得日期是当年的第几周
	 * @param dateStr
	 * @param formatStr
	 * @return
	 * @throws Exception
	 */
	public static int getWeekOfYear(String dateStr, String formatStr) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = format.parse(dateStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
	
	 /**
	  * 将日期格式的字符串转换为长整型
	  * 
	  * @param date
	  * @param format
	  * @return
	  */
	public static long convertlong(String date, String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		try {
			return sf.parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
     * @Description: long类型转换成日期
     * 
     * @param lo 毫秒数
     * @return 
     */
    public static String longToDate(long lo,String format){
        Date date = new Date(lo);
        SimpleDateFormat sd = new SimpleDateFormat(format);
        return sd.format(date);
    }
	
}
