package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:卡信息查询
 * Description:访问前置
 * @author mouchunyue
 * @date 2016年9月9日 下午6:07:32
 */
@XStreamAlias("ROOT")
public class Out75010ReqBean extends OutReqBean {

	private Out75010ReqBodyBean BODY;

	public Out75010ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out75010ReqBodyBean bODY) {
		BODY = bODY;
	}

}
