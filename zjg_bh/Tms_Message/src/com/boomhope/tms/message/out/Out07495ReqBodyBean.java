package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 核心节假日查询-前置07495
 * @author wang.sk
 *
 */
@XStreamAlias("BODY")
public class Out07495ReqBodyBean {
	private String QRY_DATE;	//查询日期

	public String getQRY_DATE() {
		return QRY_DATE;
	}

	public void setQRY_DATE(String qRY_DATE) {
		QRY_DATE = qRY_DATE;
	}
	
}
