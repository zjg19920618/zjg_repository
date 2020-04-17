package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 存单打印【02949】前置03514
 * @author hk
 * @date 2017年3月13日 上午18:22:14
 */
@XStreamAlias("Root")
public class BCK03514ResBean extends InResBean{
	
	private BCK03514ResBodyBean Body;
	
	public BCK03514ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03514ResBodyBean body) {
		Body = body;
	}


}
