package com.boomhope.Bill.monitor.bean;

/**
 * 监控平台请求报文头
 * @author hk
 *
 */
public class MonitorReqHeadBean {

	public String version;		//报文版本号
	public String type;			//自助设备接口代码
	public String deviceno;		//设备编号
	public String devicetype;	//设备类型
	public String senddate;		//发送日期
	public String sendtime;		//发送时间
	
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDeviceno() {
		return deviceno;
	}
	public void setDeviceno(String deviceno) {
		this.deviceno = deviceno;
	}
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
	public String getSenddate() {
		return senddate;
	}
	public void setSenddate(String senddate) {
		this.senddate = senddate;
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}
	
	
	
}
