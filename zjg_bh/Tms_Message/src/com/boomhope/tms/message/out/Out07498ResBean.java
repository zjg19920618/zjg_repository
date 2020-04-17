package com.boomhope.tms.message.out;


import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 转入唐宝账户查询【55060】-前置07498
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out07498ResBean extends OutResBean{
	
	private Out07498ResBodyBean BODY;

	public Out07498ResBodyBean getBody() {
		return BODY;
	}

	public void setBody(Out07498ResBodyBean body) {
		BODY = body;
	}

	

}
