package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03512ResBean 
* @Description 如意存明细查询【55020】--前置03512  
* @author zhang.m 
* @date 2016年12月5日 下午12:04:18  
*/
@XStreamAlias("Root")
public class BCK03512ResBean extends InResBean {
	private BCK03512ResBodyBean Body;

	public BCK03512ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03512ResBodyBean body) {
		Body = body;
	}

}
