package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:代理人身份信息核对
 * Description:访问前置
 * @author mouchunyue
 * @date 2016年9月9日 下午6:44:49
 */
@XStreamAlias("ROOT")
public class Out08021ReqBean extends OutReqBean{

	private Out08021ReqBodyBean BODY;

	public Out08021ReqBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out08021ReqBodyBean bODY) {
		BODY = bODY;
	}
	
}
