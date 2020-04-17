package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:个人账户销户----代理人信息
 * Description:
 * @author zjg
 * @date 2017年07月04日 下午4:32:27
 */
@XStreamAlias("Root")
public class BCK07624ReqBean extends InReqBean{
	
	private BCK07624ReqBodyBean Body;

	public BCK07624ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07624ReqBodyBean body) {
		Body = body;
	}
	
}
