package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 权限明细查询【77015】(前置交易码02956)
 * 02956请求报文
 * @author wang.sk
 *
 */
@XStreamAlias("Root")
public class BCK02956ReqBean extends InReqBean {
	private BCK02956ReqBodyBean body;

	public BCK02956ReqBodyBean getBody() {
		return body;
	}

	public void setBody(BCK02956ReqBodyBean body) {
		this.body = body;
	}
	
}
