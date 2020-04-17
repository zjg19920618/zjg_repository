package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 前置证件信息查询(86002)请求报文体Bean
 * @author shaopeng
 *
 */
@XStreamAlias("BODY")  
public class Out86022ReqBodyBean{
	
	private String ID_TYPE;	// 证件类型
	private String ID_NO;	// 证件号码
	private String CUST_NAME;	// 客户名称
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
