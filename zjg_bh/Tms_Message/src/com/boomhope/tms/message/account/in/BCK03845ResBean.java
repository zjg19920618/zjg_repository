package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:卡信息查询 
 * Description:前置返回
 * @author mouchunyue
 * @date 2016年9月9日 下午6:09:39
 */
@XStreamAlias("Root")
public class BCK03845ResBean extends InResBean {

	private BCK03845ResBodyBean Body;

	public BCK03845ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03845ResBodyBean body) {
		Body = body;
	}

}
