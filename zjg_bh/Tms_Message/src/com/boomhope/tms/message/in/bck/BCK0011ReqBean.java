package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:代理人身份信息核对
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午6:44:49
 */
@XStreamAlias("Root")
public class BCK0011ReqBean extends InReqBean{

	private BCK0011ReqBodyBean Body;

	public BCK0011ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0011ReqBodyBean body) {
		Body = body;
	}

}
