package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:代理人身份信息核对
 * Description:前置返回
 * @author mouchunyue
 * @date 2016年9月9日 下午6:47:35
 */
@XStreamAlias("ROOT")
public class Out08021ResBean extends OutResBean{

	private Out08021ResBodyBean BODY;

	public Out08021ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out08021ResBodyBean bODY) {
		BODY = bODY;
	}
	
}
