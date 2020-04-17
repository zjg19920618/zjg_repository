package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 客户基本信息查询 04422
 * @author hao
 *
 */
@XStreamAlias("Root") 
public class BCK04422ReqBean extends InReqBean{
	private BCK04422ReqBodyBean Body;

	public BCK04422ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK04422ReqBodyBean body) {
		Body = body;
	}
}
