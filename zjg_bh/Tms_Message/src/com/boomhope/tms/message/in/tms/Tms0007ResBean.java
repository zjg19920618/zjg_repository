package com.boomhope.tms.message.in.tms;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 设备登录(TMS_0001)响应报文Bean
 * @author shaopeng
 *
 */
@XStreamAlias("Root")  
public class Tms0007ResBean extends InResBean{
	
	private Tms0007ResBodyBean bodyBean;
	
	public Tms0007ResBean(){
		this.bodyBean = new Tms0007ResBodyBean();
	}
	
	public void setBodyBean(Tms0007ResBodyBean bodyBean){
		this.bodyBean = bodyBean;
	}
	
	public Tms0007ResBodyBean getBodyBean(){
		return this.bodyBean;
	}

}
