package com.boomhope.Bill.monitor.bean;

/**
 * 监控平台返回报文
 * @author hk
 *
 */
public class MonitorResBean {

	public String version;		//报文版本号
	public String type;			//自助设备接口代码
	public String deviceno;		//设备编号
	public String senddate;		//发送日期
	public String sendtime;		//发送时间
	public String retcode;		//返回码
	public String retdetail;	//错误描述
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
	public String getRetcode() {
		return retcode;
	}
	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}
	public String getRetdetail() {
		return retdetail;
	}
	public void setRetdetail(String retdetail) {
		this.retdetail = retdetail;
	}
	
	
}
