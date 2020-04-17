package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:卡信息查询
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午6:07:32
 */
@XStreamAlias("Root")
public class BCK0007ReqBean extends InReqBean {

	private BCK0007ReqBodyBean Body;

	public BCK0007ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0007ReqBodyBean body) {
		Body = body;
	}

}
