package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 存单打印状态变更【02950】前置03520
 * @author zjg
 * @date 2016年11月7日 上午10:32:40
 */
@XStreamAlias("Root")
public class BCK0017ResBean extends InResBean{
	
	private BCK0017ResBodyBean Body;
	
	public BCK0017ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0017ResBodyBean body) {
		Body = body;
	}


}
