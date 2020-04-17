package com.boomhope.tms.message.cdjmac;

/***
 * 存单机请求报文Bean
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
