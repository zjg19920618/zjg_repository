package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03510ResBean 
* @Description 产品预计利息(24小时)-03510   
* @author zhang.m 
* @date 2016年12月5日 上午11:14:24  
*/
@XStreamAlias("Root")
public class BCK03510ResBean extends InResBean {
	private BCK03510ResBodyBean Body;

	public BCK03510ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03510ResBodyBean body) {
		Body = body;
	}

}
