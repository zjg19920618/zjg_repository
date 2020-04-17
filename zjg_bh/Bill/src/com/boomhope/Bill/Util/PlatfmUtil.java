package com.boomhope.Bill.Util;

import java.text.SimpleDateFormat;

public class PlatfmUtil {
	/**
	 * 
	 * <DL>
	 * <DT><B>15位身份证号转18位 </B></DT>
	 * <p>
	 * <DD>详细介绍</DD>
	 * </DL>
	 * <p>
	 * 
	 * @param trade
	 * @param fiftCardNo
	 *            15位身份证号
	 * @return 返回18位身份证号
	 * @throws Exception
	 * @author 
	 */
	public static String getEighteenSfzNo(String fiftCardNo) throws Exception {
		if (fiftCardNo.trim().length() == 18) {
			return fiftCardNo;
		}

		if (fiftCardNo.length() != 15) {
			throw new Exception("身份证号有误！");
		} else {
			if (!fiftCardNo.matches("[0-9]*")) {
				throw new Exception("身份证号有误！");
			}
		}

		final int[] W = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
		final String[] A = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
		int i, j, s = 0;
		String newNo18;
		newNo18 = fiftCardNo;
		newNo18 = newNo18.substring(0, 6) + "19" + newNo18.substring(6, fiftCardNo.length());

		for (i = 0; i < newNo18.length(); i++) {
			j = Integer.parseInt(newNo18.substring(i, i + 1)) * W[i];
			s = s + j;
		}

		s = s % 11;
		newNo18 = newNo18 + A[s];
		return newNo18;

	}

	/**
	 * 
	 * <DL>
	 * <DT><B>校验身份证号是否合法 </B></DT>
	 * <p>
	 * <DD>详细介绍</DD>
	 * </DL>
	 * <p>
	 * 
	 * @param trade
	 * @param sfzNo
	 *            身份证号
	 * @return true 合法， false 不合法
	 * @throws Exception
	 * @author 
	 */
	public static boolean checkSFZNo(String sfzNo) throws Exception {
		if (checkSFZCard(sfzNo) == 0)
			return true;
		else
			return false;

	}

	/**
	 * 
	 * <DL>
	 * <DT><B>检查校验位 </B></DT>
	 * <p>
	 * <DD>详细介绍</DD>
	 * </DL>
	 * <p>
	 * 
	 * @param sfzNo
	 *            身份证号
	 * @return true 校验位正确， false 校验位不正确
	 * @throws Exception
	 * @author 
	 */
	private static boolean checkSFZID(String sfzNo) {
		boolean flag = false;
		if (sfzNo == null || "".equals(sfzNo))
			return false;

		int xishu[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
		if (sfzNo.length() == 18) {
			int i = 0;
			for (int k = 0; k < 18; k++) {
				char c = sfzNo.charAt(k);
				int j;
				if (c == 'X')
					j = 10;
				else if (c <= '9' || c >= '0')
					j = c - 48;
				else
					return flag;

				i += j * xishu[k];
			}

			if (i % 11 == 1)
				flag = true;
		}
		return flag;
	}


	/**
	 * 
	 * <DL>
	 * <DT><B>校验身份证</B></DT>
	 * <p>
	 * <DD>详细介绍</DD>
	 * </DL>
	 * <p>
	 * 
	 * @param sfzNo
	 *            身份证号
	 * @return 0--校验成功; 1--位数不对; 2--生日格式不对 ; 3--校验位不对 ; 4--其他异常;5--字符异常;
	 * @throws Exception
	 * @author 
	 */
	public static int checkSFZCard(String sfzNo) {
		try {

			if (sfzNo == null || sfzNo.length() != 15 && sfzNo.length() != 18)
				return 1;
			sfzNo = sfzNo.toUpperCase();
			String s1;
			String s2;
			String s3;

			if (sfzNo.length() == 15) {

				if (!isNumber(sfzNo)) {
					return 5;
				}

				s1 = "19" + sfzNo.substring(6, 8);
				s2 = sfzNo.substring(8, 10);
				s3 = sfzNo.substring(10, 12);

				if (!checkDate(s1, s2, s3))
					return 2;
			}

			if (sfzNo.length() == 18) {
				if (!isNumber(sfzNo.substring(0, 17))) {
					return 5;
				}

				s1 = sfzNo.substring(6, 10);
				s2 = sfzNo.substring(10, 12);
				s3 = sfzNo.substring(12, 14);

				if (!checkDate(s1, s2, s3))
					return 2;

				if (!checkSFZID(sfzNo))
					return 3;
			}
		} catch (Exception exception) {
			return 4;
		}
		return 0;
	}

	/**
	 * 
	 * <DL>
	 * <DT><B>检验日期格式</B></DT>
	 * <p>
	 * <DD>详细介绍</DD>
	 * </DL>
	 * <p>
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return true 格式正确， false 格式不正确
	 * @throws Exception
	 * @author 
	 */
	private static boolean checkDate(String year, String month, String day) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
		try {
			String ymd = year + month + day;
			simpledateformat.setLenient(false);
			simpledateformat.parse(ymd);
		} catch (java.text.ParseException parseexception) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * <DL>
	 * <DT><B>检查字符串是否全为数字 </B></DT>
	 * <p>
	 * <DD>详细介绍</DD>
	 * </DL>
	 * <p>
	 * 
	 * @param str
	 *            身份证号
	 * @return true 是数字， false 不全是数字
	 * @throws Exception
	 * @author 
	 */
	public static boolean isNumber(String str) {
		if (str.matches("[0-9]+")) {
			return true;
		}
		return false;
	}
}
