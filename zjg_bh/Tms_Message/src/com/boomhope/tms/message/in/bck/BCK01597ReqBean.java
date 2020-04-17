package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  BCK01597ReqBean 
* @Description   电信诈骗黑灰名单查询-前置01597
* @author zhang.m 
* @date 2016年12月6日 下午8:13:44  
*/
@XStreamAlias("Root")
public class BCK01597ReqBean extends InReqBean{
	private BCK01597ReqBodyBean Body;

	public BCK01597ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK01597ReqBodyBean body) {
		Body = body;
	}
	
}
