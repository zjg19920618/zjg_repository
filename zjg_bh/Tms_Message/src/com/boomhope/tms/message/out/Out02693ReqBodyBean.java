package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *定时发送交易撤销-前置02693
 * @author zjg
 *
 */
@XStreamAlias("BODY")  
public class Out02693ReqBodyBean {
	
	private String ORIG_CHANNEL;//原交易渠道
	private String ORIG_SYS_DATE;//原交易前置日期
	private String ORIG_TASK_ID;//原交易任务号
	public String getORIG_CHANNEL() {
		return ORIG_CHANNEL;
	}
	public void setORIG_CHANNEL(String oRIG_CHANNEL) {
		ORIG_CHANNEL = oRIG_CHANNEL;
	}
	public String getORIG_SYS_DATE() {
		return ORIG_SYS_DATE;
	}
	public void setORIG_SYS_DATE(String oRIG_SYS_DATE) {
		ORIG_SYS_DATE = oRIG_SYS_DATE;
	}
	public String getORIG_TASK_ID() {
		return ORIG_TASK_ID;
	}
	public void setORIG_TASK_ID(String oRIG_TASK_ID) {
		ORIG_TASK_ID = oRIG_TASK_ID;
	}
	
}
