package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:账号信息综合查询BODY
 * Description:
 * @author mouchunyue
 * @date 2016年9月12日 下午4:31:16
 */
@XStreamAlias("Root")
public class BCK0002ReqBean extends InReqBean{
	
	private BCK0002ReqBodyBean Body;

	public BCK0002ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0002ReqBodyBean body) {
		Body = body;
	}

}
