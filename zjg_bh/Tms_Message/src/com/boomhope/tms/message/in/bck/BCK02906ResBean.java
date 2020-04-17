package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *客户账户基本信息维护【17024】前置-02906
 * @author ly
 *
 */
@XStreamAlias("Root")  
public class BCK02906ResBean extends InResBean{
	
	private BCK02906ResBodyBean Body;

	public BCK02906ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK02906ResBodyBean body) {
		Body = body;
	}

}
