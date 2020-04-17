package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *大额普通汇兑往帐发送交易接口（通用）02013
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCK02013ReqBean extends InReqBean{
	
	private BCK02013ReqBodyBean Body;

	public BCK02013ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK02013ReqBodyBean body) {
		Body = body;
	}

}
