package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 单位卡校验对手信息
 * 02954请求报文
 * @author zjg
 *
 */
@XStreamAlias("Root")
public class BCK02954ReqBean extends InReqBean {
	private BCK02954ReqBodyBean body;

	public BCK02954ReqBodyBean getBody() {
		return body;
	}

	public void setBody(BCK02954ReqBodyBean body) {
		this.body = body;
	}
	
}
