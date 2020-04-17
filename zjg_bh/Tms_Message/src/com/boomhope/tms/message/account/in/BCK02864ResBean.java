package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account02864ResBean 
* @Description 产品利率信息查询—02864   
* @author zhang.m 
* @date 2016年12月5日 上午11:54:40  
*/
@XStreamAlias("Root")
public class BCK02864ResBean extends InResBean {
	private BCK02864ResBodyBean Body;

	public BCK02864ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK02864ResBodyBean body) {
		Body = body;
	}
	
}
