package com.boomhope.tms.message.out;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *前置系统参数查询-前置QRY00
 * @author zjg
 *
 */
@XStreamAlias("ROOT")  
public class OutQRY00ResBean extends OutResBean{
	
	private OutQRY00ResBodyBean BODY;

	public OutQRY00ResBodyBean getBody() {
		return BODY;
	}

	public void setBody(OutQRY00ResBodyBean body) {
		BODY = body;
	}

}
