package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:唐豆收回查询
 * Description:访问前置
 * @author mouchunyue
 * @date 2016年9月9日 下午4:53:16
 */
@XStreamAlias("ROOT")
public class Out02210ReqBean extends OutReqBean{

	private Out02210ReqBodyBean BODY;

	public Out02210ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02210ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
