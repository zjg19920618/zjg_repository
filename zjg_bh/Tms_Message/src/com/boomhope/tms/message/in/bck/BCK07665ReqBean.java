package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:唐豆收回
 * Description:
 * @author zjg
 * @date 2017年07月04日 下午5:59:19
 */
@XStreamAlias("Root")
public class BCK07665ReqBean extends InReqBean{

	private BCK07665ReqBodyBean Body;

	public BCK07665ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07665ReqBodyBean body) {
		Body = body;
	}
	
}
