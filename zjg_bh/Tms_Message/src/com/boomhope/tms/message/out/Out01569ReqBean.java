package com.boomhope.tms.message.out;

import com.boomhope.tms.message.in.bck.BCK01569ReqBodyBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 机构关系查询交易【20102】--前置01569
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out01569ReqBean extends OutReqBean{
	
	private Out01569ReqBodyBean Body;

	public Out01569ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(Out01569ReqBodyBean outBody) {
		Body = outBody;
	}
	
	

}
