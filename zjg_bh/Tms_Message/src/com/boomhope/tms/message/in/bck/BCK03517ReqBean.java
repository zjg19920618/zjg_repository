package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 卡系统 子账户销户【75104】前置03517
 * @author zjg
 * @date 2017年07月04日 上午10:30:47
 */
@XStreamAlias("Root")	
public class BCK03517ReqBean extends InReqBean{
	private BCK03517ReqBodyBean Body;

	public BCK03517ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03517ReqBodyBean body) {
		Body = body;
	}
	
}