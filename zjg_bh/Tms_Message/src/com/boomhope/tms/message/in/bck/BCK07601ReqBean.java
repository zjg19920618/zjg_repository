package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:账号信息综合查询BODY
 * Description:
 * @author zjg
 * @date 2017年07月04日 下午4:31:16
 */
@XStreamAlias("Root")
public class BCK07601ReqBean extends InReqBean{
	
	private BCK07601ReqBodyBean Body;

	public BCK07601ReqBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07601ReqBodyBean body) {
		Body = body;
	}

}
