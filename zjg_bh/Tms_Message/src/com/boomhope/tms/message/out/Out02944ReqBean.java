package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:普通定期存单预计利息
 * Description:访问前置
 * @author mouchunyue
 * @date 2016年9月9日 下午4:03:52
 */
@XStreamAlias("ROOT")
public class Out02944ReqBean extends OutReqBean{
	
	private Out02944ReqBodyBean BODY;

	public Out02944ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02944ReqBodyBean bODY) {
		BODY = bODY;
	}
	
	
}
