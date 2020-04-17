package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 存单打印【02949】前置03514
 * @author hk
 * @date 2017年3月13日 上午18:16:25
 */
@XStreamAlias("Root")
public class BCK03514ReqBean extends InReqBean{
	
	private BCK03514ReqBodyBean Body;
	
	public BCK03514ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03514ReqBodyBean body) {
		Body = body;
	}


}
