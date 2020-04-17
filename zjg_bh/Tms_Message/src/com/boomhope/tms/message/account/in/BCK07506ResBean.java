package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;


/** 
* @ClassName  Account07506ResBean 
* @Description 派发规则查询(交易码07506)  
* @author zh.m 
* @date 2016年12月4日 上午9:55:50  
*/
@XStreamAlias("Root")
public class BCK07506ResBean extends InResBean {
	private BCK07506ResBodyBean Body;

	public BCK07506ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07506ResBodyBean body) {
		Body = body;
	}

}
