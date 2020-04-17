package com.boomhope.tms.message.in.tms;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 设备登录(TMS_0001)请求报文Bean
 * @author shaopeng
 *
 */
@XStreamAlias("Root")  
public class Tms0001ReqBean extends InReqBean{
	
	Tms0001ReqBodyBean bodyBean;	// 报文体Bean
	
	public Tms0001ReqBean(){
		bodyBean = new Tms0001ReqBodyBean();
	}
	
	public void setBodyBean(Tms0001ReqBodyBean bodyBean){
		this.bodyBean = bodyBean;
	}
	
	public Tms0001ReqBodyBean getBodyBean(){
		return this.bodyBean;
	}

}
