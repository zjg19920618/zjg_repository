package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 电子账户 子账户销户【35104】-前置03522
 * @author zjg
 * @date 2017年07月04日 上午10:30:47
 */
@XStreamAlias("Root")
public class BCK03522ReqBean extends InReqBean{
	private BCK03522ReqBodyBean Body;

	public BCK03522ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03522ReqBodyBean body) {
		Body = body;
	}
	
}
