package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 4.13待打印存单查询【02948】前置03518
 * @author zjg
 * @date 2016年11月7日 上午10:32:40
 */
@XStreamAlias("Root")
public class BCK0015ResBean extends InResBean{
	
	private BCK0015ResBodyBean Body;
	
	public BCK0015ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0015ResBodyBean body) {
		Body = body;
	}


}
