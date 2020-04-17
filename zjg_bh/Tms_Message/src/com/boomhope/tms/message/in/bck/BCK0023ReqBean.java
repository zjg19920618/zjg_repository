package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:产品利率信息查询【02077】-前置07666
 * Description:访问前置
 * @author mouchunyue
 * @date 2016年9月9日 下午6:44:49
 */
@XStreamAlias("Root")
public class BCK0023ReqBean extends InReqBean{
	
	private BCK0023ReqBodyBean Body;

	public BCK0023ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0023ReqBodyBean body) {
		Body = body;
	}

	
}
