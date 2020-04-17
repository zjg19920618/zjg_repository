package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 电子账户 子账户销户【35104】-前置03522
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("Root")
public class BCK0022ReqBean extends InReqBean{
	private BCK0022ReqBodyBean Body;

	public BCK0022ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0022ReqBodyBean body) {
		Body = body;
	}
	
}
