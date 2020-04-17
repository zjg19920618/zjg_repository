package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:普通定期存单预计利息
 * Description:
 * @author mouchunyue
 * @date 2016年9月12日 下午2:21:44
 */
@XStreamAlias("Body")
public class BCK0003ResBodyBean {

	private String Inte;//利息
	private String SvrDate;//核心日期
	private String SvrJrnlNo;//流水号
	private String ADVN_INIT;//预付利息
	private String PAYOFF_INTEREST;//已付利息
	public String getSvrDate() {
		return SvrDate;
	}

	public void setSvrDate(String svrDate) {
		SvrDate = svrDate;
	}

	public String getSvrJrnlNo() {
		return SvrJrnlNo;
	}

	public void setSvrJrnlNo(String svrJrnlNo) {
		SvrJrnlNo = svrJrnlNo;
	}

	public String getInte() {
		return Inte;
	}

	public void setInte(String inte) {
		Inte = inte;
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
