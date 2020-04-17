package com.boomhope.Bill.peripheral.bean;
/**
 * 
 * Title:二维码扫描仪扫描读取
 * Description:
 * @author mouchunyue
 * @date 2016年9月20日 上午11:12:19
 */
public class DimensionReadBean {
	//0|序列号|1|姓名&#性别&#民族&#生日&#地址&#身份证号&#发证机关&#有效期开始&#有效期结束|照片绝对路径|正面扫描图绝对路径|反面扫描图绝对路径|
	private String status;// 状态码
	private String code;//序列号
	private String num; //1
	private String info;//二维码信息
	private String statusDesc;//状态描述（失败时才有值）
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
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
}
