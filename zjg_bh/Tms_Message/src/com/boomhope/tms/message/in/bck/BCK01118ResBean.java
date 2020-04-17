package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:根据机构号查询支付行信息-前置01118
 * @author hk
 */
@XStreamAlias("Root")
public class BCK01118ResBean extends InResBean{

	private BCK01118ResBodyBean Body;

	public BCK01118ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK01118ResBodyBean body) {
		Body = body;
	}

}
