package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:客户手机绑定信息查询【前置-07491】
 * Description:前置返回
 * @author mouchunyue
 * @date 2016年10月20日 下午14:47:13
 */
@XStreamAlias("Root")
public class BCK0012ResBean extends InResBean{

	private BCK0012ResBodyBean Body;

	public BCK0012ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0012ResBodyBean body) {
		Body = body;
	}

}
