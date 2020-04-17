package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *大小额系统参数查询CM021
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCKCM021ReqBean extends InReqBean{
	
	private BCKCM021ReqBodyBean Body;

	public BCKCM021ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCKCM021ReqBodyBean body) {
		Body = body;
	}

}
