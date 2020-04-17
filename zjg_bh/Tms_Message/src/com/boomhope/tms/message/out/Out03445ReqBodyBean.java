package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 证件信息查询请求报文体Bean
 * @author wang.sk
 *
 */
@XStreamAlias("BODY")
public class Out03445ReqBodyBean {
	private String ID_TYPE;//证件类型
	private String ID_NO;//证件号码
	private String CUST_NAME;//客户姓名
	public String getID_TYPE() {
		return ID_TYPE;
	}
	public void setID_TYPE(String iD_TYPE) {
		ID_TYPE = iD_TYPE;
	}
	public String getID_NO() {
		return ID_NO;
	}
	public void setID_NO(String iD_NO) {
		ID_NO = iD_NO;
	}
	public String getCUST_NAME() {
		return CUST_NAME;
	}
	public void setCUST_NAME(String cUST_NAME) {
		CUST_NAME = cUST_NAME;
	}
	
}
