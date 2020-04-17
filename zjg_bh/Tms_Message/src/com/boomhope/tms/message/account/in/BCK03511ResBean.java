package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03511ResBean 
* @Description   产品可开立额度信息查询-03511
* @author zhang.m 
* @date 2016年12月5日 上午11:46:06  
*/
@XStreamAlias("Root")
public class BCK03511ResBean extends InResBean {
	private BCK03511ResBodyBean Body;

	public BCK03511ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03511ResBodyBean body) {
		Body = body;
	}

}
