package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *大小额系统参数查询CM021
 * @author hk
 *
 */
@XStreamAlias("Body")  
public class BCKCM021ResBodyBean {
	
	private String PARNAME;//参数名称
	private String PARVALUE;//参数值 
	
	public String getPARNAME() {
		return PARNAME;
	}
	public void setPARNAME(String pARNAME) {
		PARNAME = pARNAME;
	}
	public String getPARVALUE() {
		return PARVALUE;
	}
	public void setPARVALUE(String pARVALUE) {
		PARVALUE = pARVALUE;
	}
	
	

}
