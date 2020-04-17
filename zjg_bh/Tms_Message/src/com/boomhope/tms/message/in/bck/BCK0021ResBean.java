package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 卡系统 子账户销户【75104】前置03517
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("Root")	
public class BCK0021ResBean extends InResBean{
	private BCK0021ResBodyBean Body;

	public BCK0021ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0021ResBodyBean body) {
		Body = body;
	}
	
}
