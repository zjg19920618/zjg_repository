package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:身份核查
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午6:35:13
 */
@XStreamAlias("body")
public class BCK07670ResBodyBean {

	private String CORE_RET_CODE;//核查返回码
	private String CORE_RET_MSG;//核查返回信息
	private String ISSUE_AUTH;//签发机构
	private String FILE_NAME;//文件名(身份证照片, 文件名是全路径, 到前置服务器下取文件)
	
	public String getCORE_RET_CODE() {
		return CORE_RET_CODE;
	}
	public void setCORE_RET_CODE(String cORE_RET_CODE) {
		CORE_RET_CODE = cORE_RET_CODE;
	}
	public String getCORE_RET_MSG() {
		return CORE_RET_MSG;
	}
	public void setCORE_RET_MSG(String cORE_RET_MSG) {
		CORE_RET_MSG = cORE_RET_MSG;
	}
	public String getISSUE_AUTH() {
		return ISSUE_AUTH;
	}
	public void setISSUE_AUTH(String iSSUE_AUTH) {
		ISSUE_AUTH = iSSUE_AUTH;
	}
	public String getFILE_NAME() {
		return FILE_NAME;
	}
	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}
	
	
}
