package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *根据客户号查询所有卡号信息【90001】-前置07520
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCK07520ReqBean extends InReqBean{
	
	private BCK07520ReqBodyBean Body;

	public BCK07520ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07520ReqBodyBean body) {
		Body = body;
	}

}
