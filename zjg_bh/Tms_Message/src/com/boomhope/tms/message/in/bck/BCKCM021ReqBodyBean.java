package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *大小额系统参数查询CM021
 * @author hk
 *
 */
@XStreamAlias("Body")  
public class BCKCM021ReqBodyBean {
	
	private String SYSCODE;//系统代码
	private String PARCODE;//参数代码
	
	public String getSYSCODE() {
		return SYSCODE;
	}
	public void setSYSCODE(String sYSCODE) {
		SYSCODE = sYSCODE;
	}
	public String getPARCODE() {
		return PARCODE;
	}
	public void setPARCODE(String pARCODE) {
		PARCODE = pARCODE;
	}
	
	

}
