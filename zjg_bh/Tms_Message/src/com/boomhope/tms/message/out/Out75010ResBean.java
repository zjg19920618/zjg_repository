package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:卡信息查询 
 * Description:前置返回
 * @author mouchunyue
 * @date 2016年9月9日 下午6:09:39
 */
@XStreamAlias("ROOT")
public class Out75010ResBean extends OutResBean {

	private Out75010ResBodyBean BODY;

	public Out75010ResBodyBean getBODY() {
		return BODY;
	}

	public void setBODY(Out75010ResBodyBean bODY) {
		BODY = bODY;
	}

}
