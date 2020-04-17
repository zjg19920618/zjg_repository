package com.boomhope.Bill.peripheral.bean;
/**
 * 
 * Title:银行卡退出
 * Description:
 * @author mouchunyue
 * @date 2016年9月20日 上午11:10:37
 */
public class ICBankQuitBean {
	//成功：	0|序列号|9|成功
	//	失败:	错误码|序列号|9|错误信息
	private String status;//0 or 错误码
	private String code;
	private String num;
	private String statusDesc;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
}
