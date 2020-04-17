package com.boomhope.tms.message.in.bck;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 电子账户 子账户销户【35104】-前置03522
 * @author zjg
 * @date 2017年07月04日 上午10:30:47
 */
@XStreamAlias("Root")
public class BCK03522ResBean extends InResBean{
	private BCK03522ResBodyBean Body;

	public BCK03522ResBodyBean getBody() {
		return Body;
	}

	public void setBody(BCK03522ResBodyBean body) {
		Body = body;
	}
	
}
