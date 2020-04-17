package com.boomhope.tms.message.in.tms;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 设备登录(TMS_0001)响应报文Bean
 * @author shaopeng
 *
 */
@XStreamAlias("Root")  
public class Tms0008ResBean extends InResBean{
	
	private Tms0008ResBodyBean bodyBean;
	
	public Tms0008ResBean(){
		this.bodyBean = new Tms0008ResBodyBean();
	}
	
	public void setBodyBean(Tms0008ResBodyBean bodyBean){
		this.bodyBean = bodyBean;
	}
	
	public Tms0008ResBodyBean getBodyBean(){
		return this.bodyBean;
	}

}
