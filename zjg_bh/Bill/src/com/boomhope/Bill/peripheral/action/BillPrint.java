package com.boomhope.Bill.peripheral.action;

import com.boomhope.Bill.peripheral.bean.BillCheckStateBean;

/**
 * 
 * Title:凭条打印机
 * Description:
 * @author mouchunyue
 * @date 2016年9月19日 下午5:31:07
 */
public class BillPrint {
	/**
	 * 状态检查
	 * send:设备号|序列号|4|优先级|超时时间|
	 * result:0|序列号|4|设备状态值|
	 * @param code 序列号
	 * @return
	 */
	public static BillCheckStateBean billPrintCheckStatus() throws Exception{
		String result = SocketClient1.Msg("206|1|999|0|30|").trim();
		String[] data = result.split("\\|");
		BillCheckStateBean billCheckStatus = new BillCheckStateBean();
		billCheckStatus.setId(data[0]);
		billCheckStatus.setCode(data[1]);
		billCheckStatus.setNum(data[2]);
		billCheckStatus.setStatus(data[3]);
		return billCheckStatus;
	} 
	
	/**
	 * 图片路径
	 * send:设备号|序列号|操作码|优先级|超时时间|当前路径
	 * result:0|序列号|1|Logo的绝对路径|
	 * @param code 序列号
	 */
	public static BillCheckStateBean billPrintLogo(String path) throws Exception{
		String result = SocketClient1.Msg("206|2|1|0|30|"+path).trim();
		String[] data = result.split("\\|");
		BillCheckStateBean billCheckStatus = new BillCheckStateBean();
		billCheckStatus.setId(data[0]);
		billCheckStatus.setCode(data[1]);
		billCheckStatus.setNum(data[2]);
		billCheckStatus.setStatus(data[3]);
		return billCheckStatus;
	}
	
	/**
	 * 凭条打印
	 * send:设备号|序列号|1|优先级|超时时间|打印内容
	 * result:0|序列号|2|打印完成|
	 * @param code 序列号
	 */
	public static BillCheckStateBean billPrint(String text) throws Exception{
		String result = SocketClient1.Msg("206|2|2|0|30|"+text).trim();
		String[] data = result.split("\\|");
		BillCheckStateBean billCheckStatus = new BillCheckStateBean();
		billCheckStatus.setId(data[0]);
		billCheckStatus.setCode(data[1]);
		billCheckStatus.setNum(data[2]);
		billCheckStatus.setStatus(data[3]);
		return billCheckStatus;
	}
	
	/**
	 * 凭条切刀
	 * send:设备号|序列号|1|优先级|超时时间|切纸模式
	 * result:0|序列号|3|
	 * @param code 序列号
	 */
	public static BillCheckStateBean billCutting(String mode) throws Exception{
		String result = SocketClient1.Msg("206|3|3|0|30|"+mode).trim();
		String[] data = result.split("\\|");
		BillCheckStateBean billCheckStatus = new BillCheckStateBean();
		billCheckStatus.setId(data[0]);
		billCheckStatus.setCode(data[1]);
		billCheckStatus.setNum(data[2]);
		billCheckStatus.setStatus(data[3]);
		return billCheckStatus;
	}
	
	
	public static void main(String[] args) {
		String path = "D:\\zjg_Bill\\Bill2\\Bill\\pic\\tsBackLogo.png";
		
		try {
			BillCheckStateBean billPrintLogo = billPrintLogo(path);//图片路径
			System.out.println(billPrintLogo.getId());
			System.out.println(billPrintLogo.getStatus());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String text   = "1#0|宋体|13|0|0|0|0|0|1| #0|宋体|13|0|0|0|0|0|1| #0|宋体|13|0|0|0|0|0|1| #0|宋体|13|0|0|0|0|0|1| "
			     + "#1|宋体|13|1|1|0|0|0|1|销户成功信息#0|宋体|13|0|0|0|0|0|1| "
				 + "#0|宋体|13|0|0|0|0|0|1|本    金 : 1000元#0|宋体|13|0|0|0|0|0|1| "
				 + "#0|宋体|13|0|0|0|0|0|1|应付利息 : 1000元#0|宋体|13|0|0|0|0|0|1| "
				 + "#0|宋体|13|0|0|0|0|0|1|已支付利息：0.00元#0|宋体|13|0|0|0|0|0|1| "
				 + "#0|宋体|13|0|0|0|0|0|1|实 付 利 息：10000元#0|宋体|13|0|0|0|0|0|1| "
				 + "#0|宋体|13|0|0|0|0|0|1|扣回加息券：10000元#0|宋体|13|0|0|0|0|0|1| "
				 + "#0|宋体|13|0|0|0|0|0|1|扣 回 唐 豆：sadsds#0|宋体|13|0|0|0|0|0|1| "
				 + "#0|宋体|13|0|0|0|0|0|1|提 示 信 息：本息合计11111元已转入#0|宋体|13|0|0|0|0|0|1| "
				 + "#0|宋体|13|0|0|0|0|0|1|             您的卡号为6231930000000900092的卡中#0|宋体|13|0|0|0|0|0|1| "
				 + "#0|宋体|13|0|0|0|0|0|1| #0|宋体|13|0|0|0|0|0|1| #0|宋体|13|0|0|0|0|0|1| #0|宋体|13|0|0|0|0|0|1| #0|宋体|13|0|0|0|0|0|1| #0|宋体|13|0|0|0|0|0|1| #";
		try {
			BillCheckStateBean billPrint = billPrint(text);
			System.out.println(billPrint.getId());
			System.out.println(billPrint.getStatus());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
