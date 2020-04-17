package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *机构关系查询交易【20102】--前置01569
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCK01596ResBean extends InResBean{
	
	private BCK01596ResBodyBean Body;

	public BCK01596ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK01596ResBodyBean body) {
		Body = body;
	}

}
