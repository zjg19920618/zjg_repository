package com.boomhope.tms.message.in.bck;



import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 证件信息查询响应报文Bean
 * @author wang.sk
 *
 */
@XStreamAlias("Root")
public class BCK03445ResBean extends InResBean {
	private BCK03445ResBodyBean BODY;//响应报文体

	public BCK03445ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(BCK03445ResBodyBean bODY) {
		BODY = bODY;
	}

	

	
	
}
