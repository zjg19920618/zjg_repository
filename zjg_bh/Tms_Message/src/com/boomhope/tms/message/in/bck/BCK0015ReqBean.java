package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 4.13待打印存单查询【02948】前置03518
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("Root")
public class BCK0015ReqBean extends InReqBean{
	
	private BCK0015ReqBodyBean Body;
	
	public BCK0015ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0015ReqBodyBean body) {
		Body = body;
	}


}
