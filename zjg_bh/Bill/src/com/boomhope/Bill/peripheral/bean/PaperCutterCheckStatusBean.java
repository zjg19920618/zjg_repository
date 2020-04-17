package com.boomhope.Bill.peripheral.bean;

/**
 * 
 * Title:裁纸器检测状态实体Bean
 * Description:
 * @author mouchunyue
 * @date 2016年11月30日 上午11:38:00
 */
public class PaperCutterCheckStatusBean {
	//成功   0|序列号|999|设备状态|

	//失败  错误码|序列号|999|错误信息|

	private String id;//错误码
	private String code;//序列号
	private String num;
	private String status;//设备状态
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
