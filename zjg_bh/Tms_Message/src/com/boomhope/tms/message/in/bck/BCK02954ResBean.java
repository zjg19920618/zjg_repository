package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 单位卡校验对手信息
 * 02954响应报文
 * @author zjg
 *
 */
@XStreamAlias("Root")
public class BCK02954ResBean extends InResBean {
	private BCK02954ResBodyBean body;

	public BCK02954ResBodyBean getBody() {
		return body;
	}

	public void setBody(BCK02954ResBodyBean body) {
		this.body = body;
	}
	
}
