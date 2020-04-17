package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 账户信息验证(BCK_0001)请求报文Bean
 * @author shaopeng
 *
 */
@XStreamAlias("Root")  
public class BCK0001ReqBean extends InReqBean {
	
	private BCK0001ReqBodyBean Body;
	
	public BCK0001ReqBean(){
		this.Body = new BCK0001ReqBodyBean();
	}

	public BCK0001ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0001ReqBodyBean body) {
		Body = body;
	}
	
}
