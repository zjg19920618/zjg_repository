package com.boomhope.Bill.monitor.bean;

/**
 * 监控平台外设状态检测上送请求报文
 * @author hk
 *
 */
public class ReqMCM002 extends MonitorReqHeadBean{

	public String servicesta;	//设备使用状态
	public String reader;		//读卡器状态|吞卡张数|吞卡状态
	public String idcard;		//身份证卡状态|吞卡状态
	public String pinpad;		//密码键盘状态|秘钥状态
	public String receipt;		//凭条打印机状态|纸余量
	public String jounal;		//打印机状态|纸余量
	public String fingerprint;	//指纹扫描
	public String barcodereader;//二维码扫描
	
	
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	public String getJounal() {
		return jounal;
	}
	public void setJounal(String jounal) {
		this.jounal = jounal;
	}
	public String getBarcodereader() {
		return barcodereader;
	}
	public void setBarcodereader(String barcodereader) {
		this.barcodereader = barcodereader;
	}
	public String getServicesta() {
		return servicesta;
	}
	public void setServicesta(String servicesta) {
		this.servicesta = servicesta;
	}
	public String getReader() {
		return reader;
	}
	public void setReader(String reader) {
		this.reader = reader;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getPinpad() {
		return pinpad;
	}
	public void setPinpad(String pinpad) {
		this.pinpad = pinpad;
	}
	public String getFingerprint() {
		return fingerprint;
	}
	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}
	
	
	
	
}
