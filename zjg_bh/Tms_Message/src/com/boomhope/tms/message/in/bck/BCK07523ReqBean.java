package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *银行卡换开查询原卡号【79114】-07523
 * @author zjg
 *
 */
@XStreamAlias("Root")  
public class BCK07523ReqBean extends InReqBean{
	
	private BCK07523ReqBodyBean Body;

	public BCK07523ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07523ReqBodyBean body) {
		Body = body;
	}

}
