package com.boomhope.tms.message.in.tms;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 证件信息查询上送(TMS_0003)请求报文Bean
 * @author mouchunyue
 *
 */
@XStreamAlias("Root")
public class Tms0003ReqBean extends InReqBean{

	private Tms0003PeriBean Body;

	public Tms0003PeriBean getBody() {
		return Body;
	}

	public void setBody(Tms0003PeriBean body) {
		Body = body;
	}

}
