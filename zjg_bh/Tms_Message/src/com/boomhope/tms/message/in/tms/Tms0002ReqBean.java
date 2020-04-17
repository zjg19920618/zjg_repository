package com.boomhope.tms.message.in.tms;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 外设状态上送(TMS_0001)请求报文Bean
 * @author shaopeng
 *
 */
@XStreamAlias("Root")  
public class Tms0002ReqBean extends InReqBean {
	
	private Tms0002ReqBodyBean Body;	// 报文体Bean
	
	public Tms0002ReqBean(){
		this.Body = new Tms0002ReqBodyBean();
	}
	
	public void setBodyBean(Tms0002ReqBodyBean bodyBean){
		this.Body = bodyBean;
	}
	
	public Tms0002ReqBodyBean getBodyBean(){
		return this.Body;
	}

}
