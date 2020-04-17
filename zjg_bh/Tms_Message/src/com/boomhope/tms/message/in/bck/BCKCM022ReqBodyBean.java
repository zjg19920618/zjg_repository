package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *定时发送交易信息查询-前置CM022
 * @author zjg
 *
 */
@XStreamAlias("Body")  
public class BCKCM022ReqBodyBean {
	
	private String TRAN_CHANNEL;//交易渠道
	private String START_DATE;//起始日期
	private String END_DATE;//终止日期
	private String QRY_INST_NO;//交易机构
	private String QRY_TRAN_CODE;//交易码
	private String SEND_STAT;//交易状态
	private String QRY_TASK_ID;//任务号
	private String MS_ACCT_NO;//付款账号
	private String TRAN_AMT;//交易金额
	
	public String getTRAN_CHANNEL() {
		return TRAN_CHANNEL;
	}
	public void setTRAN_CHANNEL(String tRAN_CHANNEL) {
		TRAN_CHANNEL = tRAN_CHANNEL;
	}
	public String getSTART_DATE() {
		return START_DATE;
	}
	public void setSTART_DATE(String sTART_DATE) {
		START_DATE = sTART_DATE;
	}
	public String getEND_DATE() {
		return END_DATE;
	}
	public void setEND_DATE(String eND_DATE) {
		END_DATE = eND_DATE;
	}
	public String getQRY_INST_NO() {
		return QRY_INST_NO;
	}
	public void setQRY_INST_NO(String qRY_INST_NO) {
		QRY_INST_NO = qRY_INST_NO;
	}
	public String getQRY_TRAN_CODE() {
		return QRY_TRAN_CODE;
	}
	public void setQRY_TRAN_CODE(String qRY_TRAN_CODE) {
		QRY_TRAN_CODE = qRY_TRAN_CODE;
	}
	public String getSEND_STAT() {
		return SEND_STAT;
	}
	public void setSEND_STAT(String sEND_STAT) {
		SEND_STAT = sEND_STAT;
	}
	public String getQRY_TASK_ID() {
		return QRY_TASK_ID;
	}
	public void setQRY_TASK_ID(String qRY_TASK_ID) {
		QRY_TASK_ID = qRY_TASK_ID;
	}
	public String getMS_ACCT_NO() {
		return MS_ACCT_NO;
	}
	public void setMS_ACCT_NO(String mS_ACCT_NO) {
		MS_ACCT_NO = mS_ACCT_NO;
	}
	public String getTRAN_AMT() {
		return TRAN_AMT;
	}
	public void setTRAN_AMT(String tRAN_AMT) {
		TRAN_AMT = tRAN_AMT;
	}
	
}
