package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:产品利率信息查询--02867
 * Description:
 * @author mouchunyue
 * @date 2016年12月3日 下午2:41:44
 */
@XStreamAlias("ROOT")
public class Account02867ResBean extends OutResBean {

	private Account02867ResBodyBean BODY;

	public Account02867ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account02867ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
