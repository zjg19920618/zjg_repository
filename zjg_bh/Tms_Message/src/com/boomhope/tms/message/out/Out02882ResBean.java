package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:凭证信息综合查询
 * Description:前置返回
 * @author mouchunyue
 * @date 2016年9月9日 下午3:35:02
 */
@XStreamAlias("ROOT")
public class Out02882ResBean extends OutResBean{

	private Out02882ResBodyBean BODY;

	public Out02882ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out02882ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
