package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:个人账户销户
 * Description:访问前置
 * @author mouchunyue
 * @date 2016年9月9日 下午4:12:57
 */
@XStreamAlias("ROOT")
public class Out02026ReqBean extends OutReqBean{
	
	private Out02026ReqBodyBean BODY;

	public Out02026ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02026ReqBodyBean bODY) {
		BODY = bODY;
	}
	
	
}
