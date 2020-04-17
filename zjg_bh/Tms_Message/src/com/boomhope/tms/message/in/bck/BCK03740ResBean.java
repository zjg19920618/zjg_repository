package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 权限明细查询【78010】(前置交易码03740)
 * 03740响应报文
 * @author zjg
 *
 */
@XStreamAlias("Root")
public class BCK03740ResBean extends InResBean {
	private BCK03740ResBodyBean body;

	public BCK03740ResBodyBean getBody() {
		return body;
	}

	public void setBody(BCK03740ResBodyBean body) {
		this.body = body;
	}
	
}
