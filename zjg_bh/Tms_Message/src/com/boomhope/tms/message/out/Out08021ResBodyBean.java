package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:代理人身份信息核对
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午6:48:49
 */
@XStreamAlias("BODY")
public class Out08021ResBodyBean {

	private String CHECK_RESULT;//核对结果

	public String getCHECK_RESULT() {
		return CHECK_RESULT;
	}

	public void setCHECK_RESULT(String cHECK_RESULT) {
		CHECK_RESULT = cHECK_RESULT;
	}
	
}
