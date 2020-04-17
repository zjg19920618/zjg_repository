package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:产品利率信息查询【02077】-前置07666
 * Description:访问前置
 * @author mouchunyue
 * @date 2016年9月9日 下午6:44:49
 */
@XStreamAlias("ROOT")
public class Out07666ReqBean extends OutReqBean{
	
	private Out07666ReqBodyBean BODY;

	public Out07666ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out07666ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
