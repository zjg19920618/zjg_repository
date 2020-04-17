package com.boomhope.tms.message.out;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *单位卡IC卡验证（核心77102）-前置03453
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out03453ReqBean extends OutReqBean{
	
	private Out03453ReqBodyBean BODY;

	public Out03453ReqBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out03453ReqBodyBean body) {
		BODY = body;
	}

}
