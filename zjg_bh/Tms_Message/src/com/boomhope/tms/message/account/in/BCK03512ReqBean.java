package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03512ReqBean 
* @Description 如意存明细查询【55020】--前置03512  
* @author zhang.m 
* @date 2016年12月5日 下午12:00:49  
*/
@XStreamAlias("Root")
public class BCK03512ReqBean extends InReqBean {
	private BCK03512ReqBodyBean Body;

	public BCK03512ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03512ReqBodyBean body) {
		Body = body;
	}

}
