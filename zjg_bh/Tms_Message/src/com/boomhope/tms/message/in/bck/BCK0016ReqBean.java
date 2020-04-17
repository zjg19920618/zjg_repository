package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 存单打印【02949】前置03514
 * @author zjg
 * @date 2016年11月7日 上午10:37:25
 */
@XStreamAlias("Root")
public class BCK0016ReqBean extends InReqBean{
	
	private BCK0016ReqBodyBean Body;
	
	public BCK0016ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0016ReqBodyBean body) {
		Body = body;
	}


}
