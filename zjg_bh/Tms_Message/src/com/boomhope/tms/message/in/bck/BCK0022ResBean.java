package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 电子账户 子账户销户【35104】-前置03522
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("Root")
public class BCK0022ResBean extends InResBean{
	private BCK0022ResBodyBean Body;

	public BCK0022ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0022ResBodyBean body) {
		Body = body;
	}
	
}
