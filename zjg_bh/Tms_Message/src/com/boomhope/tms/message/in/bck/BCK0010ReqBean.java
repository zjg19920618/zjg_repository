package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:柜员授权
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午6:39:10
 */
@XStreamAlias("Root")
public class BCK0010ReqBean extends InReqBean{

	private BCK0010ReqBodyBean Body;

	public BCK0010ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0010ReqBodyBean body) {
		Body = body;
	}
	
}
