package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 权限明细查询【78010】(前置交易码03740)
 * 03740请求报文
 * @author zjg
 *
 */
@XStreamAlias("Root")
public class BCK03740ReqBean extends InReqBean {
	private BCK03740ReqBodyBean body;

	public BCK03740ReqBodyBean getBody() {
		return body;
	}

	public void setBody(BCK03740ReqBodyBean body) {
		this.body = body;
	}
	
}
