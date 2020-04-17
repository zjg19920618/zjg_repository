package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:产品利率信息查询--02864
 * Description:
 * @author mouchunyue
 * @date 2016年11月26日 下午3:44:18
 */
@XStreamAlias("Root")
public class BCK0020ReqBean extends InReqBean {
	
	private BCK0020ReqBodyBean Body;

	public BCK0020ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0020ReqBodyBean body) {
		Body = body;
	}
	
}
