package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03870ReqBean 
* @Description  "积享存"个人存款开户【75100】前置【03870】 
* @author zhang.m 
* @date 2016年12月5日 下午12:21:30  
*/
@XStreamAlias("Root")
public class BCK03870ReqBean extends InReqBean {
	private BCK03870ReqBodyBean Body;

	public BCK03870ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03870ReqBodyBean body) {
		Body = body;
	}

}
