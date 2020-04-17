package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:卡信息查询 
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午6:09:39
 */
@XStreamAlias("Root")
public class BCK0007ResBean extends InResBean {

	private BCK0007ResBodyBean Body;

	public BCK0007ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0007ResBodyBean body) {
		Body = body;
	}

}
