package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *根据客户号查询所有卡号信息【90001】-前置07520
 * @author zjg
 *
 */
@XStreamAlias("Root")  
public class BCK07520ResBean extends InResBean{
	
	private BCK07520ResBodyBean Body;

	public BCK07520ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07520ResBodyBean body) {
		Body = body;
	}

}
