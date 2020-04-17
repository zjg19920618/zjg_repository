package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *大小额系统参数查询CM021
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCKCM021ResBean extends InResBean{
	
	private BCKCM021ResBodyBean Body;

	public BCKCM021ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCKCM021ResBodyBean body) {
		Body = body;
	}

}
