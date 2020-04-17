package com.boomhope.tms.message.in;

/***
 * 内联网关请求报文Bean
 * @author shaopeng
 *
 */
public class InReqBean {
	
	private InReqHeadBean Head;	// 报文头Bean

	public InReqBean(){
		Head = new InReqHeadBean();
	}

	public void setHeadBean(InReqHeadBean headBean){
		this.Head = headBean;
	}
	
	public InReqHeadBean getHeadBean(){
		return this.Head;
	}

}
