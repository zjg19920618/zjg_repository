package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:产品利率信息查询--02867
 * Description:
 * @author mouchunyue
 * @date 2016年12月3日 下午2:41:14
 */
@XStreamAlias("Root")
public class BCK02867ReqBean extends InReqBean {

	private BCK02867ReqBodyBean body;

	public BCK02867ReqBodyBean getBody() {
		return body;
	}

	public void setBody(BCK02867ReqBodyBean body) {
		this.body = body;
	}

}
