package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *5.24存款关联账号查询【02947】-前置07518
 * @author zjg
 *
 */
@XStreamAlias("Root")  
public class BCK07518ResBean extends InResBean{
	
	private BCK07518ResBodyBean Body;

	public BCK07518ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07518ResBodyBean body) {
		Body = body;
	}

}
