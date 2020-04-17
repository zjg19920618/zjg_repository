package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 证件信息查询请求报文Bean
 * @author wang.sk
 *
 */
@XStreamAlias("ROOT")
public class Out03445ReqBean extends OutReqBean {
	private Out03445ReqBodyBean BODY;//请求报文体

	public Out03445ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out03445ReqBodyBean bODY) {
		BODY = bODY;
	}

}
