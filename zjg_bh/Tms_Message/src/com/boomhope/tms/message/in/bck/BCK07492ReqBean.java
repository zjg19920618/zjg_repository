package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 转账客户列表信息查询-前置07492
 * 07492请求报文
 * @author wang.sk
 *
 */
@XStreamAlias("Root")
public class BCK07492ReqBean extends InReqBean {
	private BCK07492ReqBodyBean body;

	public BCK07492ReqBodyBean getBody() {
		return body;
	}

	public void setBody(BCK07492ReqBodyBean body) {
		this.body = body;
	}
	
}
