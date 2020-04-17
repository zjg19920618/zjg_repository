package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 柜员认证方式查询-前置07659请求报文体Bean
 * @author Yangtao
 *
 */
@XStreamAlias("Body")  
public class BCK07659ReqBodyBean {
	private String QRY_TELLER_NO;//查询柜员号

	public String getQRY_TELLER_NO() {
		return QRY_TELLER_NO;
	}

	public void setQRY_TELLER_NO(String qRY_TELLER_NO) {
		QRY_TELLER_NO = qRY_TELLER_NO;
	}

	@Override
	public String toString() {
		return "BCK07659ReqBodyBean [QRY_TELLER_NO=" + QRY_TELLER_NO + "]";
	}
	
	

}
