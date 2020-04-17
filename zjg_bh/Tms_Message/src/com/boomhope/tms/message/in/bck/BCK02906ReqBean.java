package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *客户账户基本信息维护【17024】前置-02906
 * @author ly
 *
 */
@XStreamAlias("Root")  
public class BCK02906ReqBean extends InReqBean{
	
	private BCK02906ReqBodyBean Body;

	public BCK02906ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK02906ReqBodyBean body) {
		Body = body;
	}

}
