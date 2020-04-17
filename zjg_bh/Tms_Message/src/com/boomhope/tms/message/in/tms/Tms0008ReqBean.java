package com.boomhope.tms.message.in.tms;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 获取银行行号(TMS_0008)请求报文Bean
 * @author shaopeng
 *
 */
@XStreamAlias("Root")  
public class Tms0008ReqBean extends InReqBean{
	
	Tms0008ReqBodyBean bodyBean;	// 报文体Bean
	
	public Tms0008ReqBean(){
		bodyBean = new Tms0008ReqBodyBean();
	}
	
	public void setBodyBean(Tms0008ReqBodyBean bodyBean){
		this.bodyBean = bodyBean;
	}
	
	public Tms0008ReqBodyBean getBodyBean(){
		return this.bodyBean;
	}

}
