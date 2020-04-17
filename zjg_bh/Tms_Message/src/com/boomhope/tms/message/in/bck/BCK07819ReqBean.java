package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *电子账户子账户列表查询【35109】（直连电子账户）-前置07819
 * @author zjg
 *
 */
@XStreamAlias("Root")  
public class BCK07819ReqBean extends InReqBean{
	
	private BCK07819ReqBodyBean Body;

	public BCK07819ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07819ReqBodyBean body) {
		Body = body;
	}

}
