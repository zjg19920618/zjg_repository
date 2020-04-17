package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:身份核查
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午6:31:18
 */
@XStreamAlias("Root")
public class BCK0008ReqBean extends InReqBean{

	private BCK0008ReqBodyBean Body;

	public BCK0008ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0008ReqBodyBean body) {
		Body = body;
	}
	
}
