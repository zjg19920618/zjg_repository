package com.boomhope.tms.message.in.tms;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 开关状态查询
 * @author zjg
 *
 */
@XStreamAlias("Root")  
public class Tms0009ReqBean extends InReqBean{
	
	Tms0009ReqBodyBean bodyBean;	// 报文体Bean
	
	public Tms0009ReqBean(){
		bodyBean = new Tms0009ReqBodyBean();
	}
	
	public void setBodyBean(Tms0009ReqBodyBean bodyBean){
		this.bodyBean = bodyBean;
	}
	
	public Tms0009ReqBodyBean getBodyBean(){
		return this.bodyBean;
	}

}
