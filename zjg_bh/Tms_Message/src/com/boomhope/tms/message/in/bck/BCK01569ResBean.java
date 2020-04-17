package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *机构关系查询交易【20102】--前置01569
 * @author hk
 *
 */
@XStreamAlias("Root")  
public class BCK01569ResBean extends InResBean{
	
	private BCK01569ResBodyBean Body;

	public BCK01569ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK01569ResBodyBean body) {
		Body = body;
	}

}
