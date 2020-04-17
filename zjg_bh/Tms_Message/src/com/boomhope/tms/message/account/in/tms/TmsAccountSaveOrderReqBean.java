package com.boomhope.tms.message.account.in.tms;

import com.boomhope.tms.message.in.InReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:开户功能请求入库
 * Description:
 * @author mouchunyue
 * @date 2016年12月20日 上午10:04:56
 */
@XStreamAlias("Root")
public class TmsAccountSaveOrderReqBean extends InReqBean{

	private TmsAccountSaveOrderReqBodyBean Body;

	public TmsAccountSaveOrderReqBodyBean getBody() {
		return Body;
	}

	public void setBody(TmsAccountSaveOrderReqBodyBean body) {
		Body = body;
	}
	
}
