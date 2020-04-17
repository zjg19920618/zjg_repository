package com.boomhope.tms.message.out;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *单位卡IC卡验证（核心77102）-前置03453
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out03453ResBean extends OutResBean{
	
	private Out03453ResBodyBean BODY;

	public Out03453ResBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out03453ResBodyBean body) {
		BODY = body;
	}

}
