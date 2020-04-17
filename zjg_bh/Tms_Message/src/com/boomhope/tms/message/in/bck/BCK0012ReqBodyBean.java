package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:客户手机绑定信息查询【前置-07491】
 * Description:访问前置
 * @author mouchunyue
 * @date 2016年10月20日 下午14:37:21
 *
 */
@XStreamAlias("Body")
public class BCK0012ReqBodyBean {
	private String ACCT_NO;//卡号

	public String getACCT_NO() {
		return ACCT_NO;
	}

	public void setACCT_NO(String aCCT_NO) {
		ACCT_NO = aCCT_NO;
	}
	
	
}
