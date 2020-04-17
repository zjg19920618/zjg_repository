package com.boomhope.Bill.peripheral.bean;

/**
 * 
 * Title:关闭裁纸器实体Bean
 * Description:
 * @author mouchunyue
 * @date 2016年11月30日 上午11:38:00
 */
public class PaperCutterCloseBean {
	//正确：    0|序列号|2|
	//错误：     错误码|序列号|2|错误信息|
	private String id;//错误码
	private String code;//序列号
	private String num;//2
	private String remark;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
