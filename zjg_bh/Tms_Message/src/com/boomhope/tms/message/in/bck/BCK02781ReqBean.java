package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *  账户限额查询【02879】-前置02781
 * @author hk
 *
 */
@XStreamAlias("Root")
public class BCK02781ReqBean extends InReqBean{
	private BCK02781ReqBodyBean Body;

	public BCK02781ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK02781ReqBodyBean body) {
		Body = body;
	}

	
}
