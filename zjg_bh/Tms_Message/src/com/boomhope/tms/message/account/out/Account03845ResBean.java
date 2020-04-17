package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:卡信息查询 
 * Description:前置返回
 * @author mouchunyue
 * @date 2016年9月9日 下午6:09:39
 */
@XStreamAlias("ROOT")
public class Account03845ResBean extends OutResBean {

	private Account03845ResBodyBean BODY;

	public Account03845ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account03845ResBodyBean bODY) {
		BODY = bODY;
	}

}
