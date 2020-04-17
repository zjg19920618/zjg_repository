package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 权限明细查询【77015】(前置交易码02956)
 * 02956响应报文Bean
 * @author wang.sk
 *
 */
@XStreamAlias("Root")
public class BCK02956ResBean extends InResBean {
	private BCK02956ResBodyBean body;

	public BCK02956ResBodyBean getBody() {
		return body;
	}

	public void setBody(BCK02956ResBodyBean body) {
		this.body = body;
	}
	
}
