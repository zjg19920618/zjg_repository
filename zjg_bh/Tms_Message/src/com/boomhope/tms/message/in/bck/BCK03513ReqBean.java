package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 待打印存单查询【02946】前置03513
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("Root")
public class BCK03513ReqBean {
	public BCK03513ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03513ReqBodyBean body) {
		Body = body;
	}

	BCK03513ReqBodyBean Body;

}
