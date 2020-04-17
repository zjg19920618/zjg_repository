package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:产品利率信息查询--02867
 * Description:
 * @author mouchunyue
 * @date 2016年12月3日 下午2:41:44
 */
@XStreamAlias("Root")
public class BCK02867ResBean extends InResBean {

	private BCK02867ResBodyBean Body;

	public BCK02867ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK02867ResBodyBean body) {
		Body = body;
	}

}
