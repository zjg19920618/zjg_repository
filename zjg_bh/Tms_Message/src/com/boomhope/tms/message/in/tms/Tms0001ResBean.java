package com.boomhope.tms.message.in.tms;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 设备登录(TMS_0001)响应报文Bean
 * @author shaopeng
 *
 */
@XStreamAlias("Root")  
public class Tms0001ResBean extends InResBean{
	
	private Tms0001ResBodyBean bodyBean;
	
	public Tms0001ResBean(){
		this.bodyBean = new Tms0001ResBodyBean();
	}
	
	public void setBodyBean(Tms0001ResBodyBean bodyBean){
		this.bodyBean = bodyBean;
	}
	
	public Tms0001ResBodyBean getBodyBean(){
		return this.bodyBean;
	}

}
