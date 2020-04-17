package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 证件信息查询(BCK0001)响应报文体
 * @author shaopeng
 *
 */
@XStreamAlias("Root")  
public class BCK0001ResBean extends InResBean{
	
	private BCK0001ResBodyBean Body;
	
	public BCK0001ResBean(){
		this.Body = new BCK0001ResBodyBean();
	}

	public BCK0001ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0001ResBodyBean body) {
		Body = body;
	}
	
}
