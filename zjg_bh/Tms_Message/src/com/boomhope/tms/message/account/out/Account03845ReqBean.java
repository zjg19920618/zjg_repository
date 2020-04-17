package com.boomhope.tms.message.account.out;

import com.boomhope.tms.message.out.OutReqBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:卡信息查询
 * Description:访问前置
 * @author mouchunyue
 * @date 2016年9月9日 下午6:07:32
 */
@XStreamAlias("ROOT")
public class Account03845ReqBean extends OutReqBean {

	private Account03845ReqBodyBean BODY;

	public Account03845ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Account03845ReqBodyBean bODY) {
		BODY = bODY;
	}

}
