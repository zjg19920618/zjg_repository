package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03510ReqBean 
* @Description   产品预计利息(24小时)-03510
* @author zhang.m 
* @date 2016年12月5日 上午11:08:44  
*/
@XStreamAlias("Root")
public class BCK03510ReqBean extends InReqBean {
	private BCK03510ReqBodyBean Body;

	public BCK03510ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03510ReqBodyBean body) {
		Body = body;
	}
	
}
