package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *客户账户基本信息维护【17024】前置-02906
 * @author ly
 *
 */
@XStreamAlias("BODY")  
public class Out02906ReqBodyBean {
	
	
	private String QRY_TYPE;//查询条件

	public String getQRY_TYPE() {
		return QRY_TYPE;
	}

	public void setQRY_TYPE(String qRY_TYPE) {
		QRY_TYPE = qRY_TYPE;
	}

	
}
