package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:身份核查
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午6:33:47
 */
@XStreamAlias("BODY")
public class Out07670ReqBodyBean {

	private String ID;//证件号码
	private String NAME;//姓名
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	
	
}
