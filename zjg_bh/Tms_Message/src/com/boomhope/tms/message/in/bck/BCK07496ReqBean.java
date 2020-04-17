package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *  转账客户列表信息删除-前置07496
 * @author hk
 *
 */
@XStreamAlias("Root")
public class BCK07496ReqBean extends InReqBean{
	private BCK07496ReqBodyBean Body;

	public BCK07496ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07496ReqBodyBean body) {
		Body = body;
	}

	
}
