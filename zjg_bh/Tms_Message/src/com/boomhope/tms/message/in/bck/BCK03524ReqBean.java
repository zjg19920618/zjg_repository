package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 客户评级查询03524
 * @author shaopeng
 *
 */
@XStreamAlias("Root")  
public class BCK03524ReqBean extends InReqBean{
	
	private BCK03524ReqBodyBean Body;

	public BCK03524ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03524ReqBodyBean body) {
		Body = body;
	}

}
