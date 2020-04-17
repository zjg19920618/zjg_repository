package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 单位校验对手信息
 * 02954请求报文体Bean
 * @author zjg
 *
 */
@XStreamAlias("ROOT")
public class Out02954ReqBean extends OutReqBean {
	private Out02954ReqBodyBean BODY;

	public Out02954ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02954ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
