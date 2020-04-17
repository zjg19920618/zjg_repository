package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *机构关系查询交易【20102】--前置01569
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCK01596ReqBean extends InReqBean{
	
	private BCK01596ReqBodyBean Body;

	public BCK01596ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK01596ReqBodyBean body) {
		Body = body;
	}

}
