package com.boomhope.tms.message.out;

import com.boomhope.tms.message.in.bck.BCK01596ReqBodyBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 机构关系查询交易【20102】--前置01569
 * @author hk
 *
 */
@XStreamAlias("ROOT")  
public class Out01596ReqBean extends OutReqBean{
	
	private Out01596ReqBodyBean Body;

	public Out01596ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(Out01596ReqBodyBean outBody) {
		Body = outBody;
	}
	
	

}
