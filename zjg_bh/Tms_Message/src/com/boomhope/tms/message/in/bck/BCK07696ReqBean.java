package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:普通定期存单预计利息
 * Description:
 * @author zjg
 * @date 2017年07月04日 下午2:21:44
 */
@XStreamAlias("Root")
public class BCK07696ReqBean extends InReqBean {

	private BCK07696ReqBodyBean Body;

	public BCK07696ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07696ReqBodyBean body) {
		Body = body;
	}
	
}
