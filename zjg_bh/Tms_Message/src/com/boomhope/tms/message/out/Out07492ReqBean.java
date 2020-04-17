package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 转账客户列表信息查询-前置07492
 * 07492请求报文
 * @author wang.sk
 *
 */
@XStreamAlias("ROOT")
public class Out07492ReqBean extends OutReqBean {
	private Out07492ReqBodyBean BODY;

	public Out07492ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07492ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
