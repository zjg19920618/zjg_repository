package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:唐豆收回
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午5:31:01
 */
@XStreamAlias("Root")
public class BCK0006ReqBean extends InReqBean{

	private BCK0006ReqBodyBean Body;

	public BCK0006ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0006ReqBodyBean body) {
		Body = body;
	}
	
}
