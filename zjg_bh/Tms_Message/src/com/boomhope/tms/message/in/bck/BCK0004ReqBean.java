package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:个人账户销户
 * Description:
 * @author mouchunyue
 * @date 2016年9月12日 下午6:16:26
 */
@XStreamAlias("Root")
public class BCK0004ReqBean extends InReqBean{
	
	private BCK0004ReqBodyBean Body;

	public BCK0004ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0004ReqBodyBean body) {
		Body = body;
	}
	
}
