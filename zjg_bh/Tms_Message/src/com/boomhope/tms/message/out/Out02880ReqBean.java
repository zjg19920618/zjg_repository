package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:账号信息综合查询BODY
 * Description:访问前置
 * @author mouchunyue
 * @date 2016年9月9日 下午3:25:12
 */
@XStreamAlias("ROOT")
public class Out02880ReqBean extends OutReqBean{
	
	private Out02880ReqBodyBean BODY;

	public Out02880ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02880ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
