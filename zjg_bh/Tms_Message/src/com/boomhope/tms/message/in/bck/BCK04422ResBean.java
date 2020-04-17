package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 客户基本信息查询 04422
 * @author hao
 *
 */
@XStreamAlias("Root")
public class BCK04422ResBean extends InResBean{
	private BCK04422ResBodyBean Body;

	public BCK04422ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK04422ResBodyBean body) {
		Body = body;
	}
}
