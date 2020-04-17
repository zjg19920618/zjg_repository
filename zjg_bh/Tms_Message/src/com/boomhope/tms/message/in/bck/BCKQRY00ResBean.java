package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *前置系统参数查询-前置QRY00
 * @author zjg
 *
 */
@XStreamAlias("Root")  
public class BCKQRY00ResBean extends InResBean{
	
	private BCKQRY00ResBodyBean Body;

	public BCKQRY00ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCKQRY00ResBodyBean body) {
		Body = body;
	}

}
