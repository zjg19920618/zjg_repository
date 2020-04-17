package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *银行卡换开查询原卡号【79114】-07523
 * @author zjg
 *
 */
@XStreamAlias("Root")  
public class BCK07523ResBean extends InResBean{
	
	private BCK07523ResBodyBean Body;

	public BCK07523ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK07523ResBodyBean body) {
		Body = body;
	}

}
