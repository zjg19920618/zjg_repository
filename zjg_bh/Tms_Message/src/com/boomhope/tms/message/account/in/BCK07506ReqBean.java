package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account07506ReqBean 
* @Description 派发规则查询(交易码07506)  
* @author zh.m 
* @date 2016年12月4日 上午9:50:09  
*/
@XStreamAlias("Root")
public class BCK07506ReqBean extends InReqBean {
	private BCK07506ReqBodyBean Body;

	public BCK07506ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07506ReqBodyBean body) {
		Body = body;
	}

}
