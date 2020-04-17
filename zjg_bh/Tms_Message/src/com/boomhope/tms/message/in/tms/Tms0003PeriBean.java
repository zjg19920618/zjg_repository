package com.boomhope.tms.message.in.tms;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 证件信息查询片段Bean
 * @author mouchunyue
 *
 */
@XStreamAlias("Body")
public class Tms0003PeriBean {
	
	@XStreamAlias("ID_TYPE")
	private String ID_TYPE;
	
	@XStreamAlias("ID_TYPE_NO")
	private String ID_TYPE_NO;
	
	@XStreamAlias("CUST_NM")
	private String CUST_NM;
	
	
	public String getID_TYPE() {
		return ID_TYPE;
	}
	public void setID_TYPE(String iD_TYPE) {
		ID_TYPE = iD_TYPE;
	}
	public String getID_TYPE_NO() {
		return ID_TYPE_NO;
	}
	public void setID_TYPE_NO(String iD_TYPE_NO) {
		ID_TYPE_NO = iD_TYPE_NO;
	}
	public String getCUST_NM() {
		return CUST_NM;
	}
	public void setCUST_NM(String cUST_NM) {
		CUST_NM = cUST_NM;
	}
	
}
