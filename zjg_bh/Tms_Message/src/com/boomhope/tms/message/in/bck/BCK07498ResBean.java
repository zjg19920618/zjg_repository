package com.boomhope.tms.message.in.bck;


import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 转入唐宝账户查询【55060】-前置07498
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCK07498ResBean extends InResBean{
	
	private BCK07498ResBodyBean Body;

	public BCK07498ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07498ResBodyBean body) {
		Body = body;
	}

	

}
