package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:普通定期存单预计利息
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午4:09:01
 */
@XStreamAlias("BODY")
public class Out02944ResBodyBean {

	private String SVR_DATE;//核心日期
	private String SVR_JRNL_NO;//流水号
	private String INTE;//利息
	private String ADVN_INIT;//预付利息
	private String PAYOFF_INTEREST;//已付利息
	public String getSVR_DATE() {
		return SVR_DATE;
	}
	public void setSVR_DATE(String sVR_DATE) {
		SVR_DATE = sVR_DATE;
	}
	public String getSVR_JRNL_NO() {
		return SVR_JRNL_NO;
	}
	public void setSVR_JRNL_NO(String sVR_JRNL_NO) {
		SVR_JRNL_NO = sVR_JRNL_NO;
	}
	public String getINTE() {
		return INTE;
	}
	public void setINTE(String iNTE) {
		INTE = iNTE;
	}
	public String getADVN_INIT() {
		return ADVN_INIT;
	}
	public void setADVN_INIT(String aDVN_INIT) {
		ADVN_INIT = aDVN_INIT;
	}
	public String getPAYOFF_INTEREST() {
		return PAYOFF_INTEREST;
	}
	public void setPAYOFF_INTEREST(String pAYOFF_INTEREST) {
		PAYOFF_INTEREST = pAYOFF_INTEREST;
	}
}
