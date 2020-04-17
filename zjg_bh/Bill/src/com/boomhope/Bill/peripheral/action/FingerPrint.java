package com.boomhope.Bill.peripheral.action;

import java.net.UnknownHostException;

import com.boomhope.Bill.peripheral.bean.FingerPrintCheckStatusBean;
import com.boomhope.Bill.peripheral.bean.FingerPrintGet;

/**
 * 
 * Title:指纹
 * Description:
 * @author mouchunyue
 * @date 2016年9月20日 上午11:41:26
 */
public class FingerPrint {
	
	/**
	 * send:设备号|序列号|999|优先级|超时时间|
	 * result:0|序列号|999|设备状态值|
	 * 设备状态值           含义
	 *  值为空	  检测不到状态
	 *	  0	            设备使用正常
	 *	  1	            设备故障
	 * 检查状态
	 * @param code 序列号
	 * @throws Exception 
	 */
	public FingerPrintCheckStatusBean checkStatus(String code) throws Exception{
		String result = SocketClient.Msg("203|"+code+"|999|0|120|").trim();
		String[] data = result.split("\\|");
		FingerPrintCheckStatusBean fingerPrintCheckStatusBean = new FingerPrintCheckStatusBean();
		fingerPrintCheckStatusBean.setId(data[0]);
		fingerPrintCheckStatusBean.setCode(data[1]);
		fingerPrintCheckStatusBean.setNum(data[2]);
		if(data.length>3){
			fingerPrintCheckStatusBean.setStatus(data[3]);
		}
		return fingerPrintCheckStatusBean;
	}
	/**
	 * 获取指纹
	 * @param code
	 * @throws Exception 
	 * @throws UnknownHostException 
	 */
	public FingerPrintGet getFingerPrint(String code) throws Exception{
		String result = SocketClient.Msg("203|"+code+"|1|0|120|").trim();
		System.out.println(result);
		FingerPrintGet fingerPrintGet = new FingerPrintGet();
		String[] data = result.split("\\|");
		fingerPrintGet.setStatus(data[0]);
		fingerPrintGet.setCode(data[1]);
		fingerPrintGet.setNum(data[2]);
		if(data[0].equals("0")){
			fingerPrintGet.setFingerPrint(data[3]);
		}else{
			fingerPrintGet.setStatusDesc(data[3]);
		}
		return fingerPrintGet;
	}
	public static void main(String[] args) throws Exception {
		FingerPrint fingerPrint = new FingerPrint();
		FingerPrintGet fr = fingerPrint.getFingerPrint("1");
		System.out.println(fr.getFingerPrint());
		
	}
}
