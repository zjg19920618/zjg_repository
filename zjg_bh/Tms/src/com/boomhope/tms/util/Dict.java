package com.boomhope.tms.util;

/**
 * 数据字典
 * @author Administrator
 *
 */
public class Dict {
	
	/**
	 * 用户状态
	 */
	public static final String USER_STATUS_START = "0";
	public static final String USER_STATUS_STOP = "1";
	
	/**
	 * 预警处理状态:1-未处理 2-已处理
	 */
	public static final String MAC_WAR_ON  = "1";
	public static final String MAC_WAR_TWO = "2";
	
	/**
	 * 部署状态:0-初始、1-已投产、2-已下线
	 */
	public static final String DEPLOY_INI = "0";
	public static final String DEPLOY_USE = "1";
	public static final String DEPLOY_DEL = "2";
	
	/**
	 * 上报状态:1-正常 2-异常
	 */
	public static final String REP_STATUS_NORMAL = "1";
	public static final String REP_STATUS_ERROR = "2";
	
	/**
	 * 虚拟键盘
	 */
	public static final String PARAMETE_WAR_TYPE = "war_type";
}
