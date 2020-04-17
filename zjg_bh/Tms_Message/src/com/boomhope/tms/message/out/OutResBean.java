package com.boomhope.tms.message.out;

/***
 * 响应Bean
 * @author shaopeng
 *
 */
public class OutResBean {

	private OutHeadBean HEAD;	// 报文头
	private OutResponseBean RESPONSE;	// response报文域
	
	public OutResBean(){
		HEAD = new OutHeadBean();
		RESPONSE = new OutResponseBean();
	}
	
	public void setHeadBean(OutHeadBean headBean){
		this.HEAD = headBean; 
	}
	
	public OutHeadBean getHeadBean(){
		return this.HEAD;
	}
	
	public void setResponseBean(OutResponseBean responseBean){
		this.RESPONSE = responseBean;
	}
	
	public OutResponseBean getResponseBean(){
		return this.RESPONSE;
	}

}
