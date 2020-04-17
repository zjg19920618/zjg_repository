package com.boomhope.Bill.peripheral.action;

import com.boomhope.Bill.peripheral.bean.PaperCutterCheckStatusBean;
import com.boomhope.Bill.peripheral.bean.PaperCutterCloseBean;
import com.boomhope.Bill.peripheral.bean.PaperCutterOpenBean;


public class PaperCutter {

	/**
	 * 打开裁纸器
	 * @return
	 */
	public static PaperCutterOpenBean openPaperCutter(String code) throws Exception{
		//设备号|序列号|1|优先级|超时时间|
		String result = SocketClient.Msg("229|"+code+"|1|0|120|").trim();
		String[] data = result.split("\\|");
		PaperCutterOpenBean cutterOpenBean = new PaperCutterOpenBean();
		cutterOpenBean.setId(data[0]);
		cutterOpenBean.setCode(data[1]);
		cutterOpenBean.setNum(data[2]);
		cutterOpenBean.setRemark(data[3]);
		return  cutterOpenBean;
	}
	
	/**
	 * 关闭裁纸器
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static PaperCutterCloseBean closePaperCutter(String code) throws Exception{
		//错误码|序列号|2|错误信息|
		String result = SocketClient.Msg("229|"+code+"|2|0|120|").trim();
		String[] data = result.split("\\|");
		PaperCutterCloseBean cutterCloseBean = new PaperCutterCloseBean();
		cutterCloseBean.setId(data[0]);
		cutterCloseBean.setCode(data[1]);
		cutterCloseBean.setNum(data[2]);
		cutterCloseBean.setRemark(data[3]);
		return cutterCloseBean;
	}
	
	public static PaperCutterCheckStatusBean checkStatus(String code) throws Exception{
		//设备号|序列号|999|优先级|超时时间|
		String result = SocketClient.Msg("229|"+code+"|999|0|120|").trim();
		String[] data = result.split("\\|");
		PaperCutterCheckStatusBean paperCutterCheckStatusBean = new PaperCutterCheckStatusBean();
		paperCutterCheckStatusBean.setId(data[0]);
		paperCutterCheckStatusBean.setCode(data[1]);
		paperCutterCheckStatusBean.setNum(data[2]);
		paperCutterCheckStatusBean.setStatus(data[3]);
		return paperCutterCheckStatusBean;
	}
}
