package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 卡系统 子账户销户【75104】前置03517
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("Root")	
public class BCK0021ReqBean extends InReqBean{
	private BCK0021ReqBodyBean Body;

	public BCK0021ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0021ReqBodyBean body) {
		Body = body;
	}
	
}