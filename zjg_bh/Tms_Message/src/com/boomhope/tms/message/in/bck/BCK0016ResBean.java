package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 存单打印【02949】前置03514
 * @author zjg
 * @date 2016年11月7日 上午10:37:51
 */
@XStreamAlias("Root")
public class BCK0016ResBean extends InResBean{
	
	private BCK0016ResBodyBean Body;
	
	public BCK0016ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0016ResBodyBean body) {
		Body = body;
	}


}
