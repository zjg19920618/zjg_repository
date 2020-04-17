package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:账号信息综合查询BODY
 * Description:
 * @author mouchunyue
 * @date 2016年9月12日 下午4:31:32
 */
@XStreamAlias("Root")
public class BCK0002ResBean extends InResBean{

	private BCK0002ResBodyBean Body;

	public BCK0002ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0002ResBodyBean body) {
		Body = body;
	}

}
