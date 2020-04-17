package com.boomhope.tms.message.in.tms;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 设备登录(TMS_0001)请求报文Bean
 * @author shaopeng
 *
 */
@XStreamAlias("Root")  
public class Tms0007ReqBean extends InReqBean{
	
	Tms0007ReqBodyBean bodyBean;	// 报文体Bean
	
	public Tms0007ReqBean(){
		bodyBean = new Tms0007ReqBodyBean();
	}
	
	public void setBodyBean(Tms0007ReqBodyBean bodyBean){
		this.bodyBean = bodyBean;
	}
	
	public Tms0007ReqBodyBean getBodyBean(){
		return this.bodyBean;
	}

}
