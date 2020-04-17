package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *柜员认证方式查询-前置07659响应报文体Bean
 * @author shaopeng
 *
 */
@XStreamAlias("Body")  
public class BCK07659ResBodyBean {
	
	private String CHECK_MOD;

	public String getCHECK_MOD() {
		return CHECK_MOD;
	}

	public void setCHECK_MOD(String cHECK_MOD) {
		CHECK_MOD = cHECK_MOD;
	}

	@Override
	public String toString() {
		return "BCK07659ResBodyBean [CHECK_MOD=" + CHECK_MOD + "]";
	}
	

}
