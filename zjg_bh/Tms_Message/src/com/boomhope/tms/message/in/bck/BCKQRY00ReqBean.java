package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *前置系统参数查询-前置QRY00
 * @author zjg
 *
 */
@XStreamAlias("Root")  
public class BCKQRY00ReqBean extends InReqBean{
	
	private BCKQRY00ReqBodyBean Body;

	public BCKQRY00ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCKQRY00ReqBodyBean body) {
		Body = body;
	}

}
