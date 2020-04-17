package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *单位卡IC卡验证（核心77102）-前置03453
 * @author hk
 *
 */
@XStreamAlias("Body")  
public class BCK03453ResBodyBean {
	
	private String BALANCE;//账户余额
	private String AVL_BALANCE;//可用余额
	private String TRA_AMT;//本日可取现金额
	private String TFR_AMT;//本日可转账金额
	private String ARPC;//ARPC
	
	
	public String getBALANCE() {
		return BALANCE;
	}
	public void setBALANCE(String bALANCE) {
		BALANCE = bALANCE;
	}
	public String getAVL_BALANCE() {
		return AVL_BALANCE;
	}
	public void setAVL_BALANCE(String aVL_BALANCE) {
		AVL_BALANCE = aVL_BALANCE;
	}
	public String getTRA_AMT() {
		return TRA_AMT;
	}
	public void setTRA_AMT(String tRA_AMT) {
		TRA_AMT = tRA_AMT;
	}
	public String getTFR_AMT() {
		return TFR_AMT;
	}
	public void setTFR_AMT(String tFR_AMT) {
		TFR_AMT = tFR_AMT;
	}
	public String getARPC() {
		return ARPC;
	}
	public void setARPC(String aRPC) {
		ARPC = aRPC;
	}
	
	
}
