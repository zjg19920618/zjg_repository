package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 证件信息查询响应报文Bean
 * @author wang.sk
 *
 */
@XStreamAlias("ROOT")
public class Out03445ResBean extends OutResBean {
	private Out03445ResBodyBean BODY;//响应报文体

	public Out03445ResBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out03445ResBodyBean body) {
		this.BODY = body;
	}
	
}
