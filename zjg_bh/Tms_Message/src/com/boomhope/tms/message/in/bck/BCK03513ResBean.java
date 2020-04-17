package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 待打印存单查询【02946】前置03513
 * @author zjg
 * @date 2016年11月7日 上午10:32:40
 */
@XStreamAlias("Root")
public class BCK03513ResBean {
	public BCK03513ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03513ResBodyBean body) {
		Body = body;
	}

	BCK03513ResBodyBean Body;

}
