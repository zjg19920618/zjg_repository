package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * *大小额系统参数查询CM021
 * @author hk
 *
 */
@XStreamAlias("BODY")  
public class OutCM021ReqBodyBean{
	
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
