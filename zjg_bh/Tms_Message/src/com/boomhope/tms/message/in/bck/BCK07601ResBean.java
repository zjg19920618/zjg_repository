package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:账号信息综合查询BODY
 * Description:
 * @author zjg
 * @date 2017年07月04日 下午4:31:16
 */
@XStreamAlias("Root")
public class BCK07601ResBean extends InResBean{

	private BCK07601ResBodyBean Body;

	public BCK07601ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07601ResBodyBean body) {
		Body = body;
	}

}
