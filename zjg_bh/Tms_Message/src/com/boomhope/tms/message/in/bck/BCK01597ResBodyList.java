package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ESB_LIST")
public class BCK01597ResBodyList {
	private String SOURCE_ID;//名单类型
	private String STTN;//查验状态
	public String getSOURCE_ID() {
		return SOURCE_ID;
	}
	public void setSOURCE_ID(String sOURCE_ID) {
		SOURCE_ID = sOURCE_ID;
	}
	public String getSTTN() {
		return STTN;
	}
	public void setSTTN(String sTTN) {
		STTN = sTTN;
	}
	
}
