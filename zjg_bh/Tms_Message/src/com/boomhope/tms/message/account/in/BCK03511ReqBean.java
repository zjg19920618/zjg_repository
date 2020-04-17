package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03511ReqBean 
* @Description   产品可开立额度信息查询-03511
* @author zhang.m 
* @date 2016年12月5日 上午11:36:19  
*/
@XStreamAlias("Root")
public class BCK03511ReqBean extends InReqBean {
	private BCK03511ReqBodyBean Body;

	public BCK03511ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03511ReqBodyBean body) {
		Body = body;
	}

}
