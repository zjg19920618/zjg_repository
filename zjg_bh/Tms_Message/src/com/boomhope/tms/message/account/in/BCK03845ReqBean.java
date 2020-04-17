package com.boomhope.tms.message.account.in;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:卡信息查询
 * Description:访问前置
 * @author mouchunyue
 * @date 2016年9月9日 下午6:07:32
 */
@XStreamAlias("Root")
public class BCK03845ReqBean extends InReqBean {

	private BCK03845ReqBodyBean Body;

	public BCK03845ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03845ReqBodyBean body) {
		Body = body;
	}

}
