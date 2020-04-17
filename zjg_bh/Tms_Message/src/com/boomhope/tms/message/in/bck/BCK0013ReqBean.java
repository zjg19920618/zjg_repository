package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:单条短信发送 【前置-07190】
 * Description:访问前置
 * @author mouchunyue
 * @date 2016年10月20日 下午14:51:21
 *
 */
@XStreamAlias("Root")
public class BCK0013ReqBean extends InReqBean {
	private BCK0013ReqBodyBean Body;

	public BCK0013ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0013ReqBodyBean body) {
		Body = body;
	}

}
