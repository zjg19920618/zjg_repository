package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *定时发送交易信息查询-前置CM022
 * @author zjg
 *
 */
@XStreamAlias("Root")  
public class BCKCM022ResBean extends InResBean{
	
	private BCKCM022ResBodyBean Body;

	public BCKCM022ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCKCM022ResBodyBean body) {
		Body = body;
	}

}
