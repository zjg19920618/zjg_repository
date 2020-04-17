package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *5.24存款关联账号查询【02947】-前置07518
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCK07518ReqBean extends InReqBean{
	
	private BCK07518ReqBodyBean Body;

	public BCK07518ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07518ReqBodyBean body) {
		Body = body;
	}

}
