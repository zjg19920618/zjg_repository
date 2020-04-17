package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:凭证信息综合查询BODY
 * Description:访问前置
 * @author mouchunyue
 * @date 2016年9月9日 下午3:25:12
 */
@XStreamAlias("ROOT")
public class Out02882ReqBean extends OutReqBean{
	
	private Out02882ReqBodyBean BODY;

	public Out02882ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02882ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
