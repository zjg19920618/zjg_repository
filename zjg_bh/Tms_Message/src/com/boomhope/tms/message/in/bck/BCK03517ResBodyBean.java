package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 卡系统 子账户销户【75104】前置03517
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("Body")	
public class BCK03517ResBodyBean {
	private String SVR_DATE;//核心日期
	private String CARD_DATE;//卡日期
	private String CARD_JRNL_NO;//卡流水号
	private String SVR_JRNL_NO;//核心流水号
	private String RATE;//利率
	private String RED_INTEREST;//应付利息
	private String TAX_RATE;//计税税率
	private String INTEREST_TAX;//利息税
	private String INVEST_INCOME;//保值贴补
	private String PAID_PRINCIPAL;//实付本金
	private String PAID_INTEREST;//实付利息
	private String PAYOFF_INTEREST;//已支付利息
	private String ADVN_INIT;//预付利息
	private String XFD_COUNT;//扣回消费豆数量
	private String XFD_AMT;//扣回消费豆金额
	
	public String getADVN_INIT() {
		return ADVN_INIT;
	}
	public void setADVN_INIT(String aDVN_INIT) {
		ADVN_INIT = aDVN_INIT;
	}
	public String getSVR_DATE() {
		return SVR_DATE;
	}
	public void setSVR_DATE(String sVR_DATE) {
		SVR_DATE = sVR_DATE;
	}
	public String getCARD_DATE() {
		return CARD_DATE;
	}
	public void setCARD_DATE(String cARD_DATE) {
		CARD_DATE = cARD_DATE;
	}
	public String getCARD_JRNL_NO() {
		return CARD_JRNL_NO;
	}
	public void setCARD_JRNL_NO(String cARD_JRNL_NO) {
		CARD_JRNL_NO = cARD_JRNL_NO;
	}
	public String getSVR_JRNL_NO() {
		return SVR_JRNL_NO;
	}
	public void setSVR_JRNL_NO(String sVR_JRNL_NO) {
		SVR_JRNL_NO = sVR_JRNL_NO;
	}
	public String getRATE() {
		return RATE;
	}
	public void setRATE(String rATE) {
		RATE = rATE;
	}
	public String getRED_INTEREST() {
		return RED_INTEREST;
	}
	public void setRED_INTEREST(String rED_INTEREST) {
		RED_INTEREST = rED_INTEREST;
	}
	public String getTAX_RATE() {
		return TAX_RATE;
	}
	public void setTAX_RATE(String tAX_RATE) {
		TAX_RATE = tAX_RATE;
	}
	public String getINTEREST_TAX() {
		return INTEREST_TAX;
	}
	public void setINTEREST_TAX(String iNTEREST_TAX) {
		INTEREST_TAX = iNTEREST_TAX;
	}
	public String getINVEST_INCOME() {
		return INVEST_INCOME;
	}
	public void setINVEST_INCOME(String iNVEST_INCOME) {
		INVEST_INCOME = iNVEST_INCOME;
	}
	public String getPAID_PRINCIPAL() {
		return PAID_PRINCIPAL;
	}
	public void setPAID_PRINCIPAL(String pAID_PRINCIPAL) {
		PAID_PRINCIPAL = pAID_PRINCIPAL;
	}
	public String getPAID_INTEREST() {
		return PAID_INTEREST;
	}
	public void setPAID_INTEREST(String pAID_INTEREST) {
		PAID_INTEREST = pAID_INTEREST;
	}
	public String getPAYOFF_INTEREST() {
		return PAYOFF_INTEREST;
	}
	public void setPAYOFF_INTEREST(String pAYOFF_INTEREST) {
		PAYOFF_INTEREST = pAYOFF_INTEREST;
	}
	public String getXFD_COUNT() {
		return XFD_COUNT;
	}
	public void setXFD_COUNT(String xFD_COUNT) {
		XFD_COUNT = xFD_COUNT;
	}
	public String getXFD_AMT() {
		return XFD_AMT;
	}
	public void setXFD_AMT(String xFD_AMT) {
		XFD_AMT = xFD_AMT;
	}
	

}
