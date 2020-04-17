package com.boomhope.tms.message.out;

/***
 * 请求报文Bean
 * @author shaopeng
 *
 */
public class OutReqBean {
	private OutHeadBean HEAD;	// 报文头
	
	public OutReqBean(){
		HEAD = new OutHeadBean();
	}
	
	public void setHeadBean(OutHeadBean headBean){
		this.HEAD = headBean; 
	}
	
	public OutHeadBean getHeadBean(){
		return this.HEAD;
	}
	

}
