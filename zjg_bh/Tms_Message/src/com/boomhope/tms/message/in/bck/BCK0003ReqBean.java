package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:普通定期存单预计利息
 * Description:
 * @author mouchunyue
 * @date 2016年9月12日 下午2:09:14
 */
@XStreamAlias("Root")
public class BCK0003ReqBean extends InReqBean {

	private BCK0003ReqBodyBean Body;

	public BCK0003ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0003ReqBodyBean body) {
		Body = body;
	}
	
}
