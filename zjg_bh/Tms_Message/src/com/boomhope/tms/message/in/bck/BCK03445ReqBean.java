package com.boomhope.tms.message.in.bck;



import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 证件信息查询请求报文Bean
 * @author wang.sk
 *
 */
@XStreamAlias("Root")
public class BCK03445ReqBean extends InReqBean {
	private BCK03445ReqBodyBean BODY;//请求报文体

	public BCK03445ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(BCK03445ReqBodyBean bODY) {
		BODY = bODY;
	}

	
}
