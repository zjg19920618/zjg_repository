package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *查询账户利率浮动信息【55030】-前置03523
 * @author shaopeng
 *
 */
@XStreamAlias("Root")  
public class BCK03523ResBean extends InResBean{
	
	private BCK03523ResBodyBean Body;

	public BCK03523ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03523ResBodyBean body) {
		Body = body;
	}

}
