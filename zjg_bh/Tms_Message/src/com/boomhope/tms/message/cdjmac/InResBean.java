package com.boomhope.tms.message.cdjmac;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 存单机响应报文Bean
 * @author shaopeng
 *
 */
@XStreamAlias("Root")  
public class InResBean {
	
	private InResHeadBean Head;	// 响应报文头Bean
	
	public InResBean(){
		Head  = new InResHeadBean();
	}
	
	public void setHeadBean(InResHeadBean headBean){
		this.Head = headBean;
	}
	
	public InResHeadBean getHeadBean(){
		return this.Head;
	}
	

}
