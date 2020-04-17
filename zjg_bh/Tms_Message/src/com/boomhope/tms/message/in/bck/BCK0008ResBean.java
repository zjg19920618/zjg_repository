package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:身份核查
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午6:34:02
 */
@XStreamAlias("Root")
public class BCK0008ResBean extends InResBean{

	private BCK0008ResBodyBean Body;

	public BCK0008ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0008ResBodyBean body) {
		Body = body;
	}

}
