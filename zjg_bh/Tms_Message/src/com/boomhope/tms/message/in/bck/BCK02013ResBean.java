package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *大额普通汇兑往帐发送交易接口（通用）02013
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCK02013ResBean extends InResBean{
	
	private BCK02013ResBodyBean Body;

	public BCK02013ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK02013ResBodyBean body) {
		Body = body;
	}

}
