package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:根据机构号查询支付行信息-前置01118
 * @author hk
 */
@XStreamAlias("Root")
public class BCK01118ReqBean extends InReqBean{

	private BCK01118ReqBodyBean Body;

	public BCK01118ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK01118ReqBodyBean body) {
		Body = body;
	}
	
}
