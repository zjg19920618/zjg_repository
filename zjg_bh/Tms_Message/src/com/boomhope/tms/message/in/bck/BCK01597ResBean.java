package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  BCK01597ResBean 
* @Description   电信诈骗黑灰名单查询-前置01597
* @author zhang.m 
* @date 2016年12月6日 下午8:21:20  
*/
@XStreamAlias("Root")
public class BCK01597ResBean extends InResBean {
	private BCK01597ResBodyBean Body;

	public BCK01597ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK01597ResBodyBean body) {
		Body = body;
	}
	
	
}
