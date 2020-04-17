package com.boomhope.tms.message.out;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *前置系统参数查询-前置QRY00
 * @author zjg
 *
 */
@XStreamAlias("ROOT")  
public class OutQRY00ReqBean extends OutReqBean{
	
	private OutQRY00ReqBodyBean BODY;

	public OutQRY00ReqBodyBean getBody() {
		return BODY;
	}

	public void setBody(OutQRY00ReqBodyBean body) {
		BODY = body;
	}

}
