package com.boomhope.Bill.peripheral.action;

import java.io.IOException;
import java.net.UnknownHostException;

import com.boomhope.Bill.peripheral.bean.PrintMachineCheckStateBean;

/**
 * 
 * Title:存单打印机
 * Description:
 * @author mouchunyue
 * @date 2016年9月19日 下午5:31:07
 */
public class PrintMachine {
	/**
	 * 状态检查
	 * send:设备号|序列号|4|优先级|超时时间|
	 * result:0|序列号|4|设备状态值|
	 * @param code 序列号
	 * @return
	 */
	public static PrintMachineCheckStateBean printMachineCheckStatus() throws Exception{
		String result = SocketClient.Msg("208|1|999|0|30|").trim();
		String[] data = result.split("\\|");
		PrintMachineCheckStateBean printMachineStatus = new PrintMachineCheckStateBean();
		printMachineStatus.setId(data[0]);
		printMachineStatus.setCode(data[1]);
		printMachineStatus.setNum(data[2]);
		printMachineStatus.setStatus(data[3]);
		if("0".equals(data[0])){
			String[] status = data[3].split("&");
			printMachineStatus.setStatus1(status[0]);//8纸将尽
			printMachineStatus.setStatus2(status[1]);//7卡纸 
			printMachineStatus.setStatus3(status[2]);//6切刀错误 
		}
		return printMachineStatus;
	} 
}
