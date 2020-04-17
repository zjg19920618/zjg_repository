package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account02864ReqBean 
* @Description  产品利率信息查询—02864 
* @author zhang.m 
* @date 2016年12月5日 上午11:50:56  
*/
@XStreamAlias("Root")
public class BCK02864ReqBean extends InReqBean{
	private BCK02864ReqBodyBean Body;

	public BCK02864ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK02864ReqBodyBean body) {
		Body = body;
	}

}
