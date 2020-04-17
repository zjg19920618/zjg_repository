package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:客户手机绑定信息查询【前置-07491】
 * Description:访问前置
 * @author mouchunyue
 * @date 2016年10月20日 下午14:39:38
 *
 */
@XStreamAlias("Root")
public class BCK0012ReqBean extends InReqBean {
	
	private BCK0012ReqBodyBean Body;

	public BCK0012ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0012ReqBodyBean body) {
		Body = body;
	}

}
