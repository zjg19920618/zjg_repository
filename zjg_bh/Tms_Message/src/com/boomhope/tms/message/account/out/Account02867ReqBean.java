package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:产品利率信息查询--02867
 * Description:
 * @author mouchunyue
 * @date 2016年12月3日 下午2:41:14
 */
@XStreamAlias("ROOT")
public class Account02867ReqBean extends OutReqBean {

	private Account02867ReqBodyBean BODY;

	public Account02867ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account02867ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
