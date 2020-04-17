package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:凭证信息综合查询BODY
 * Description:
 * @author mouchunyue
 * @date 2016年9月12日 下午4:31:32
 */
@XStreamAlias("Root")
public class BCK0014ResBean extends InResBean{

	private BCK0014ResBodyBean Body;

	public BCK0014ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK0014ResBodyBean body) {
		Body = body;
	}

}
