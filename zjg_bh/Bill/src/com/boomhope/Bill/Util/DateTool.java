package com.boomhope.Bill.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 处理日期和字符等格式化信息的工具类
 * 
 * @author lxl
 *
 */
public class DateTool {

	static Logger logger = Logger.getLogger(DateTool.class);
	private int x; // 日期属性：年
	private int y; // 日期属性：月
	private int z; // 日期属性：日
	private Calendar localTime; // 当前日期
	
	
	public DateTool() {
		localTime = Calendar.getInstance();
	}
	
	/**
	 * 校验年限是否达标
	 */
	public static boolean checkYearsIsOk(String beginDate,String endDate,int yearLength) throws Exception{
		int years = Integer.parseInt(beginDate.substring(0,4))+yearLength;
		beginDate=years+beginDate.substring(4);
		logger.info("截止日期："+endDate+"；起算日期："+beginDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		if(sdf.parse(endDate).before(sdf.parse(beginDate))){
			logger.info("年限还未达标");
			return false;
		}
		return true;
	}
	
	/**
	 * 功能：得到当前月份月初 格式为：xxxx-yy-zz (eg: 2010-12-01)
	 * 
	 */
	public String thisMonth() {
		String strY = null;
		x = localTime.get(Calendar.YEAR);
		y = localTime.get(Calendar.MONTH) + 1;
		strY = y >= 10 ? String.valueOf(y) : ("0" + y);
		return x + "-" + strY + "-01";
	}

	/**
	 * 功能：得到当前月份月底 格式为：xxxx-yy-zz (eg: 2010-12-31)
	 * 
	 * @return String
	 * @author wy
	 **/
	public String thisMonthEnd() {
		String strY = null;
		String strZ = null;
		boolean leap = false;
		x = localTime.get(Calendar.YEAR);
		y = localTime.get(Calendar.MONTH) + 1;
		if (y == 1 || y == 3 || y == 5 || y == 7 || y == 8 || y == 10
				|| y == 12) {
			strZ = "31";
		}
		if (y == 4 || y == 6 || y == 9 || y == 11) {
			strZ = "30";
		}
		if (y == 2) {
			leap = leapYear(x);
			if (leap) {
				strZ = "29";
			} else {
				strZ = "28";
			}
		}
		strY = y >= 10 ? String.valueOf(y) : ("0" + y);
		return x + "-" + strY + "-" + strZ;
	}

	/**
	 * 功能：判断输入年份是否为闰年<br>
	 * 
	 * @param year
	 * @return 是：true 否：false
	 * @author pure
	 */
	public boolean leapYear(int year) {
		boolean leap;
		if (year % 4 == 0) {
			if (year % 100 == 0) {
				if (year % 400 == 0)
					leap = true;
				else
					leap = false;
			} else
				leap = true;
		} else
			leap = false;
		return leap;
	}

	// 上月第一天
	public String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
		// lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 上个月的年份和月份
	public String getPreviousUpMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得上月最后一天的日期
	public String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 格式化日期为字符串 "yyyy-MM-dd hh:mm"
	public String formatDateTime(Date basicDate, String strFormat) {
		SimpleDateFormat df = new SimpleDateFormat(strFormat);
		return df.format(basicDate);
	}

	// 格式化日期为字符串 "yyyy-MM-dd hh:mm"
	public String formatDateTime(String basicDate, String strFormat) {
		SimpleDateFormat df = new SimpleDateFormat(strFormat);
		Date tmpDate = null;
		try {
			tmpDate = df.parse(basicDate);
		} catch (Exception e) {
			// 日期型字符串格式错误
		}
		return df.format(tmpDate);
	}

	// 当前日期加减n天后的日期，返回String (yyyy-mm-dd)
	public static String nDaysAftertoday(int n) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow = Calendar.getInstance();
		// rightNow.add(Calendar.DAY_OF_MONTH,-1);
		rightNow.add(Calendar.DAY_OF_MONTH, +n);
		return df.format(rightNow.getTime());
	}

	// 当前日期加减n天后的日期，返回Date
	public Date nDaysAfterNowDate(int n) {
		Calendar rightNow = Calendar.getInstance();
		// rightNow.add(Calendar.DAY_OF_MONTH,-1);
		rightNow.add(Calendar.DAY_OF_MONTH, +n);
		return rightNow.getTime();
	}

	// 给定一个日期型字符串，返回加减n天后的日期型字符串
	public static String nDaysAfterOneDateString(String basicDate, int n) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date tmpDate = null;
		try {
			tmpDate = df.parse(basicDate);
		} catch (Exception e) {
			// 日期型字符串格式错误
		}
		long nDay = (tmpDate.getTime() / (24 * 60 * 60 * 1000) + 1 + n)
				* (24 * 60 * 60 * 1000);
		tmpDate.setTime(nDay);

		return df.format(tmpDate);
	}

	// 给定一个日期型字符串，返回加减n天后的日期型字符串
	public static String getNextDay(String basicDate, int n) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date tmpDate = null;
		try {
			tmpDate = df.parse(basicDate);
		} catch (Exception e) {
			// 日期型字符串格式错误
		}
		long nDay = (tmpDate.getTime() / (24 * 60 * 60 * 1000) + 1 + n)
				* (24 * 60 * 60 * 1000);
		tmpDate.setTime(nDay);

		return df.format(tmpDate);
	}

	// 给定一个日期，返回加减n天后的日期，返回Date
	public Date nDaysAfterOneDate(Date basicDate, int n) {
		long nDay = (basicDate.getTime() / (24 * 60 * 60 * 1000) + 1 + n)
				* (24 * 60 * 60 * 1000);
		basicDate.setTime(nDay);

		return basicDate;
	}

	// 当前日期加减n个月后的日期，返回String (yyyy-mm-dd)
	public String nMonthsAftertoday(int n) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow = Calendar.getInstance();
		// rightNow.add(Calendar.DAY_OF_MONTH,-1);
		rightNow.add(Calendar.MONTH, +n);
		return df.format(rightNow.getTime());
	}

	// 当前日期加减n个月后的日期，返回Date
	public Date nMonthsAfterNowDate(int n) {
		Calendar rightNow = Calendar.getInstance();
		// rightNow.add(Calendar.DAY_OF_MONTH,-1);
		rightNow.add(Calendar.MONTH, +n);
		return rightNow.getTime();
	}

	// 当前日期加减n个月后的日期，返回Date
	public Date nMonthsAfterOneDate(Date basicDate, int n) {
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(basicDate);
		rightNow.add(Calendar.MONTH, +n);
		return rightNow.getTime();
	}

	// 当前日期加减n个月后的日期，返回String
	public static String nMonthsAfterOneDateString(Date basicDate, int n) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(basicDate);
		rightNow.add(Calendar.MONTH, +n);
		return df.format(rightNow.getTime());
	}
	// 当前日期加减n个月或n年后的日期，返回String
	public static String nMonthsAfterOneString(String basicDate, String n) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar rightNow = Calendar.getInstance();
		int i;
		if(n.substring(n.length()-1, n.length()).equals("Y")){
			i = Integer.parseInt(n.trim().replace("Y",""));
			try {
				Date dt = df.parse(basicDate);
				rightNow.setTime(dt);
				rightNow.add(Calendar.YEAR, +i);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			i = Integer.parseInt(n.trim().replace("M",""));
			try {
				Date dt = df.parse(basicDate);
				rightNow.setTime(dt);
				rightNow.add(Calendar.MONTH, +i);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return df.format(rightNow.getTime());
	}
	//计算日期相隔天数
	public static int differentsDays(Date firstDate, Date secondDate) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(firstDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(secondDate);
		
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);
		
		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		
		if(year1 != year2){//同一年
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if(i%4==0 && i%100!=0 || i%400==0){//闰年
					timeDistance += 366;
				}else{//不是闰年
					timeDistance += 365;
				}
			}
			return timeDistance+(day2-day1);
			
		}else{//不同年
			
			return day2-day1;
		}
	}	

	// 计算两个日期相隔的天数
	public static int nDaysBetweenTwoDate(Date firstDate, Date secondDate) {
		int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));
		return nDay;
	}

	// 计算两个日期相隔的天数
	public static int nDaysBetweenTwoDate(String firstString,
			String secondString) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date firstDate = null;
		Date secondDate = null;
		try {
			firstDate = df.parse(firstString);
			secondDate = df.parse(secondString);
		} catch (Exception e) {
			// 日期型字符串格式错误
		}

		int nDay = (int) ((secondDate.getTime() - firstDate.getTime()) / (24 * 60 * 60 * 1000));
		return nDay;
	}

	/**
	 * 将指定格式的日期/时间字符串转换成Date格式
	 * 
	 * @param strDate
	 *            String类型，日期字符
	 * @param strFormat
	 *            String类型，格式
	 * @return Date类型
	 */
	public static java.sql.Date stringToDate(String strDate, String strFormat) {
		try {
			if (strDate == null || strDate.equals("")) {
				return null;
			} else {
				SimpleDateFormat _formatdate = new SimpleDateFormat(strFormat,
						Locale.getDefault());
				java.sql.Date _date = new java.sql.Date(
						(_formatdate.parse(strDate)).getTime());
				return _date;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 将指定格式的日期/时间字符串转换成Date格式
	 * 
	 * @param strDate
	 *            String类型，日期字符
	 * @param strFormat
	 *            String类型，格式
	 * @return Date类型
	 */
	public static Date stringToUtilDate(String strDate, String strFormat) {
		try {
			if (strDate == null || strDate.equals("")) {
				return null;
			} else {
				SimpleDateFormat sf = new SimpleDateFormat(strFormat,
						Locale.getDefault());
				return sf.parse(strDate);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 把包含日期值转换为字符串
	 * 
	 * @param date
	 *            日期（日期+时间）
	 * @param type
	 *            输出类型
	 * @return 字符串
	 */
	public static String DateTimeToString(java.util.Date date, String type) {
		String DateString = "";
		if (date == null) {
			DateString = "";
		} else {
			SimpleDateFormat formatDate = new SimpleDateFormat(type,
					Locale.getDefault());
			DateString = formatDate.format(date);
		}
		return DateString;
	}

	/**
	 * 返回指定时间所在的区间 可能的区间包括 凌晨 早上 上午 中午 下午 傍晚 晚上
	 */
	public static String getTimeInterval(Date cdate) {
		if (cdate == null) {
			return null;
		}
		Calendar caler = Calendar.getInstance();
		caler.setTime(cdate);
		int hour = caler.get(Calendar.HOUR_OF_DAY);

		String intervalset = StringUtils.EMPTY;
		if (hour < 6) {
			intervalset = "凌晨";
		} else if (hour < 9) {
			intervalset = "早上";
		} else if (hour < 12) {
			intervalset = "上午";
		} else if (hour < 14) {
			intervalset = "中午";
		} else if (hour < 17) {
			intervalset = "下午";
		} else if (hour < 19) {
			intervalset = "傍晚";
		} else {
			intervalset = "晚上";
		}
		return intervalset;
	}

	/**
	 * 取当前时间
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getDateByPattern(String pattern) {
		Calendar caler = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(caler.getTime());
	}

	/**
	 * 取当前时间20140601
	 * 
	 * @param pattern
	 *            yyyyMMddHHmmss
	 * @return
	 */
	public static Long getLongByDate(String pattern) {
		Calendar caler = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Long data = Long.valueOf(sdf.format(caler.getTime()));
		return data;
	}

	/**
	 * 显示时间格式
	 * 
	 * @author 张涛
	 *
	 * @param source
	 *            原long类型时间
	 * @param pattern
	 *            原时间格式 "yyyyMMddHHmmss"
	 * @param pattern2
	 *            显示时间格式 "yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static String showDateFormat(Long source, String pattern,
			String pattern2) {
		try {
			if (source != null && source > 0) {
				StringBuffer str = new StringBuffer(source.toString());
				for (int i = str.length(); i < pattern.length(); i++) {
					str.append("0");
				}
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				SimpleDateFormat sdf2 = new SimpleDateFormat(pattern2);
				return sdf2.format(sdf.parseObject(str.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 取当前时间 年月日 格式:yyyyMMdd
	 * 
	 * @return
	 */
	public static Date getDateTime() {
		String strFormat = "yyyyMMdd";
		return stringToUtilDate(getDateByPattern(strFormat), strFormat);
	}
	/**
	 * 判断是否大于3个月
	 * @param endDate 到期日期
	 * @param strFormat 日期格式
	 * @return
	 */
	public boolean judgeDate(String endDate,String strFormat){
		try {
			Date enday = stringToUtilDate(endDate, strFormat);
			Date today=Calendar.getInstance().getTime();
			today=nMonthsAfterOneDate(today, 3);
			if(enday.getTime()> today.getTime()){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	/**
	 * 对日期的加减操作
	 * @param field 表示年月日  （1-年、2-月、3-周、5-天）
	 * @param amount 表示加减的数量（正数+ ，负数-）
	 * @param date 要加减的日期
	 * @param format 要加减的日期格式
	 * @return
	 * @throws ParseException 
	 */
	public static String jiajDate(int field,int amount,String date,String format){
		try {
			SimpleDateFormat dformat = new SimpleDateFormat(format);
			Date formatDate = dformat.parse(date);
			
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(formatDate);
			gc.add(field, amount);
			gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));
			return dformat.format(gc.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("加减日期计算失败"+e);
		}
		return "";
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	public static void main(String[] args) {
		String nMonthsAfterOneString = DateTool.nMonthsAfterOneString("20170725","50M");
		System.out.println(nMonthsAfterOneString);
	}
}
