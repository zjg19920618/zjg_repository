package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:代理人身份信息核对
 * Description:
 * @author zjg
 * @date 2017年07月04日 下午6:44:49
 */
@XStreamAlias("Body")
public class BCK08021ResBodyBean {

	private String CHECK_RESULT;//核对结果

	public String getCHECK_RESULT() {
		return CHECK_RESULT;
	}

	public void setCHECK_RESULT(String cHECK_RESULT) {
		CHECK_RESULT = cHECK_RESULT;
	}
	
}
