package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:代理人身份信息核对
 * Description:
 * @author zjg
 * @date 2017年07月04日 下午6:44:49
 */
@XStreamAlias("Root")
public class BCK08021ReqBean extends InReqBean{

	private BCK08021ReqBodyBean Body;

	public BCK08021ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK08021ReqBodyBean body) {
		Body = body;
	}

}
