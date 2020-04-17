package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:根据机构号查询支付行信息-前置01118
 * @author hk
 */
@XStreamAlias("BODY")  
public class Out01118ResBodyBean {
	
	private String BANK_NO;//支付行号
	private String EIS_NO;//电联行号
	private String CLR_CLS;//清算级别
	private String AUTH_AMT;//需授权金额
	private String BANK_NAME;//行名
	
	public String getBANK_NO() {
		return BANK_NO;
	}
	public void setBANK_NO(String bANK_NO) {
		BANK_NO = bANK_NO;
	}
	public String getEIS_NO() {
		return EIS_NO;
	}
	public void setEIS_NO(String eIS_NO) {
		EIS_NO = eIS_NO;
	}
	public String getCLR_CLS() {
		return CLR_CLS;
	}
	public void setCLR_CLS(String cLR_CLS) {
		CLR_CLS = cLR_CLS;
	}
	public String getAUTH_AMT() {
		return AUTH_AMT;
	}
	public void setAUTH_AMT(String aUTH_AMT) {
		AUTH_AMT = aUTH_AMT;
	}
	public String getBANK_NAME() {
		return BANK_NAME;
	}
	public void setBANK_NAME(String bANK_NAME) {
		BANK_NAME = bANK_NAME;
	}
	
	
}
