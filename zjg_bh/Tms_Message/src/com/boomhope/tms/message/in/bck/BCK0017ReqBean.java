package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 存单打印状态变更【02950】前置03520
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("Root")
public class BCK0017ReqBean extends InReqBean{
	
	private BCK0017ReqBodyBean Body;
	
	public BCK0017ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0017ReqBodyBean body) {
		Body = body;
	}


}
