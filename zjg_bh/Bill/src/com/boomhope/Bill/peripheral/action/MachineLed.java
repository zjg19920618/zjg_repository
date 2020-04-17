package com.boomhope.Bill.peripheral.action;

import com.boomhope.Bill.peripheral.bean.BillCheckStateBean;
import com.boomhope.Bill.peripheral.bean.LedStateBean;

/**
 * 
 * Title:外设Led灯
 * Description:
 * @author zjg
 * @date 2016年9月19日 下午5:31:07
 */
public class MachineLed {
	/**
	 * 状态检查
	 * send:设备号|序列号|4|优先级|超时时间|
	 * result:0|序列号|4|设备状态值|
	 * @param code 序列号
	 * @return
	 */
	public static LedStateBean ledCheckStatus() throws Exception{
		String result = SocketClient.Msg("207|1|999|0|30|").trim();
		String[] data = result.split("\\|");
		LedStateBean ledStatus = new LedStateBean();
		ledStatus.setId(data[0]);
		ledStatus.setCode(data[1]);
		ledStatus.setNum(data[2]);
		ledStatus.setStatus(data[3]);
		return ledStatus;
	} 
	
	/**
	 * 灯亮
	 * send:设备号|序列号|1|优先级|超时时间| 灯色|灯号|
	 * result:0|序列号|成功|
	 *		  0|序列号|失败|
	 * @param code 对应机器序号
	 */
	public static LedStateBean openLed(String code) throws Exception{
		String result = SocketClient.Msg("207|2|1|0|30|0|"+code+"|").trim();
		String[] data = result.split("\\|");
		LedStateBean openLed = new LedStateBean();
		openLed.setId(data[0]);
		openLed.setCode(data[1]);
		openLed.setNum(data[2]);
		openLed.setNum(data[3]);
		return openLed;
	}
	
	/**
	 * 灯灭
	 * send:设备号|序列号|2|优先级|超时时间|灯色|灯号|
	 * result:0|序列号|成功|
	 *        0|序列号|失败|	
	 * @param code 对应机器序号
	 */
	public static LedStateBean closeLed(String code) throws Exception{
		String result = SocketClient.Msg("207|2|2|0|30|0|"+code+"|").trim();
		String[] data = result.split("\\|");
		LedStateBean closeLed = new LedStateBean();
		closeLed.setId(data[0]);
		closeLed.setCode(data[1]);
		closeLed.setNum(data[2]);
		closeLed.setNum(data[3]);
		return closeLed;
	}
	
	/**
	 * 灯闪
	 * send:设备号|序列号|3|优先级|超时时间|灯色|灯号|闪烁时间TT
	 * result:0|序列号|成功|
	 *        0|序列号|失败|
	 * @param code 对应机器序号
	 */
	public static LedStateBean ledFlash(String code) throws Exception{
		String result = SocketClient.Msg("207|3|3|0|30|0|"+code+"|").trim();
		String[] data = result.split("\\|");
		LedStateBean ledFlash = new LedStateBean();
		ledFlash.setId(data[0]);
		ledFlash.setCode(data[1]);
		ledFlash.setNum(data[2]);
		ledFlash.setNum(data[3]);
		return ledFlash;
	}
	
	
	public static void main(String[] args) {
		String code = "0"; 
		try {
			LedStateBean led = ledCheckStatus();//检查状态
			System.out.println("led灯状态检查"+led);
//			LedStateBean led = openLed(code);//打开led灯
//			System.out.println("打开led灯"+led);
//			LedStateBean led = closeLed(code);//关闭led灯
//			System.out.println("关闭led灯"+led);
//			LedStateBean led = ledFlash(code);//led灯闪
//			System.out.println("led灯闪烁"+led);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
