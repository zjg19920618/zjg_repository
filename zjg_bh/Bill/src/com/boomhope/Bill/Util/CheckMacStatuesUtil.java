package com.boomhope.Bill.Util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Driver.KeypadDriver;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.peripheral.action.BillPrint;
import com.boomhope.Bill.peripheral.action.FingerPrint;
import com.boomhope.Bill.peripheral.action.ICBank;
import com.boomhope.Bill.peripheral.action.IdCard;
import com.boomhope.Bill.peripheral.action.PaperCutter;
import com.boomhope.Bill.peripheral.action.PrintMachine;
import com.boomhope.Bill.peripheral.bean.BillCheckStateBean;
import com.boomhope.Bill.peripheral.bean.FingerPrintCheckStatusBean;
import com.boomhope.Bill.peripheral.bean.ICBankCheckStatusBean;
import com.boomhope.Bill.peripheral.bean.ICBankQuitBean;
import com.boomhope.Bill.peripheral.bean.IdCartCheckStatusBean;
import com.boomhope.Bill.peripheral.bean.PaperCutterCheckStatusBean;
import com.boomhope.Bill.peripheral.bean.PrintMachineCheckStateBean;
import com.boomhope.tms.peripheral.action.Dimension;
import com.boomhope.tms.peripheral.bean.DimensionCheckStatusBean;

public class CheckMacStatuesUtil {
	private Logger logger = Logger.getLogger(CheckMacStatuesUtil.class);
	private Map primap;	
	
	public CheckMacStatuesUtil() {
		primap = new HashMap<String,String>();
	}
	
	
	public Map getPrimap() {
		return primap;
	}


	/**
	 * 检查凭条打印机状态
	 */
	public boolean checkBillState(){
		//检查凭条打印机状态
		BillCheckStateBean billPrintCheckStatus = null;
		boolean flag = true;
		try {
			billPrintCheckStatus = BillPrint.billPrintCheckStatus();
		} catch (Exception e) {
			flag = false;
			logger.error("凭条打印机连接异常:"+e);
			primap.put("PRI0007", "3");
			primap.put("PRI0007failMsg", "凭条打印机连接异常");
			return flag;
		}
		
		String id = billPrintCheckStatus.getId();
		String billPrinStatus = billPrintCheckStatus.getStatus();
		if("0".equals(id)){
			if(billPrinStatus != null){
				if("0".equals(billPrinStatus)){
					
					primap.put("PRI0007", "0");
					primap.put("PRI0007failMsg", "凭条打印机状态值:正常"+billPrinStatus);
					
				}else{
					
					flag = false;
					primap.put("PRI0007", "2");
					primap.put("PRI0007failMsg", "凭条打印机状态值:异常"+billPrinStatus);
					
				}
				
			}else{
				
				flag = false;
				primap.put("PRI0007", "2");
				primap.put("PRI0007failMsg", "凭条打印机状态值:获取状态失败，无返回值");
			}
			
		}else{
			
			flag = false;
			primap.put("PRI0007", "3");
			primap.put("PRI0007failMsg", "凭条打印机调用:异常"+id);
			
		}
		return flag;	
	}
	
	/**
	 * 检查存单打印机状态
	 */
	public boolean checkPrintMachineState(){
		//检查存单打印机状态
		boolean flag = true;
		PrintMachine printMachine = new PrintMachine();
		PrintMachineCheckStateBean printMachineCheckStatus =null;
		try {
			printMachineCheckStatus = printMachine.printMachineCheckStatus();
		} catch (Exception e) {
			flag = false;
			logger.error("存单打印机连接异常"+e);
			primap.put("PRI0006", "3");
			primap.put("PRI0006failMsg", "存单打印机连接异常");
			return flag;
		}
		
		String id = printMachineCheckStatus.getId();
		String printStatus = printMachineCheckStatus.getStatus();
		String status1 = printMachineCheckStatus.getStatus1();//8纸将尽
		String status2 = printMachineCheckStatus.getStatus2();//7卡纸 
		String status3 = printMachineCheckStatus.getStatus3();//6切刀错误 
		logger.info("返回状态值"+id);
		logger.info("返回状态值对应信息"+printStatus);
		
		if("0".equals(id)){
			
			primap.put("PRI0006", "0");
			primap.put("PRI0006failMsg", "存单打印机状态值:正常");
			
			if("7".equals(status2)){
				flag = false;
				logger.error("存单打印机状态7卡纸");
				primap.put("PRI0006", "2");
				primap.put("PRI0006failMsg", "存单打印机状态值:异常7");
			}
			if("6".equals(status3)){
				flag = false;
				logger.error("存单打印机状态6切刀错误");
				primap.put("PRI0006", "2");
				primap.put("PRI0006failMsg", "存单打印机状态值:异常6");
			}
			
		}else {
			
			flag = false;
			primap.put("PRI0006", "3");
			primap.put("PRI0006failMsg", "存单打印机调用:异常"+id);
		}	
		return flag;
	}
	
	/**
	 * 检查裁纸器状态
	 */
	public boolean checkPaperCutterState(){
		//检查裁纸器状态
		boolean flag = true;
		PaperCutterCheckStatusBean paperCutterCheckStatusBean = null;
		try {
			paperCutterCheckStatusBean = PaperCutter.checkStatus("5");
		} catch (Exception e) {
			flag = false;
			logger.error("裁纸器状态连接异常"+e);
			primap.put("PRI0005", "3");
			primap.put("PRI0005failMsg", "裁纸器状态连接异常");
			return flag;
		}
		String id = paperCutterCheckStatusBean.getId();
		String paperStatus = paperCutterCheckStatusBean.getStatus();
		if("0".equals(id)){
			if(paperStatus != null){
				if("0".equals(paperStatus)){
					
					primap.put("PRI0005", "0");
					primap.put("PRI0005failMsg", "裁纸器状态值:正常"+paperStatus);
					
				}else{
					
					flag = false;
					primap.put("PRI0005", "2");
					primap.put("PRI0005failMsg", "裁纸器状态值:异常"+paperStatus);
					
				}
				
			}else{
				
				flag = false;
				primap.put("PRI0005", "2");
				primap.put("PRI0005failMsg", "裁纸器状态值:获取状态失败，无返回值");
			}
			
		}else{
			
			flag = false;
			primap.put("PRI0005", "3");
			primap.put("PRI0005failMsg", "裁纸器调用:异常"+id);
			
		}
		return flag;
	}
	
	/**
	 * 检查身份证阅读器状态
	 */
	public boolean checkIdCardState(){
		// 检查身份证阅读器状态
		boolean flag = true;
		IdCartCheckStatusBean idcard = null;
		try {
			idcard = IdCard.checkStatus("1");
		} catch (Exception e) {
			flag = false;
			logger.error("身份证阅读器连接异常"+e);
			primap.put("PRI0003", "3");
			primap.put("PRI0003failMsg", "身份证阅读器连接异常");
			return flag;
		}
		String id = idcard.getId();
		String status_idcard = idcard.getStatus();
		if("0".equals(id)){
			if(status_idcard != null){
				if("0".equals(status_idcard)){
					
					primap.put("PRI0003", "0");
					primap.put("PRI0003failMsg", "身份证阅读器状态值:正常"+status_idcard);
					
				}else{
					
					flag = false;
					primap.put("PRI0003", "2");
					primap.put("PRI0003failMsg", "身份证阅读器状态值:异常"+status_idcard);
					
				}
				
			}else{
				
				flag = false;
				primap.put("PRI0003", "2");
				primap.put("PRI0003failMsg", "身份证阅读器状态值:获取状态失败，无返回值");
			}
			
		}else{
			
			flag = false;
			primap.put("PRI0003", "3");
			primap.put("PRI0003failMsg", "身份证阅读器调用:异常"+id);
			
		}
		return flag;
	}
	
	/**
	 * 检查银行卡读卡器状态
	 */
	public boolean checkICBankState(){
		// 检查银行卡状读卡器状态
		boolean flag = true;
		ICBankCheckStatusBean bank = null;
		try {
			bank = ICBank.checkStatus("2");
		} catch (Exception e) {
			flag = false;
			logger.error("银行卡读卡器连接异常"+e);
			primap.put("PRI0004", "3");
			primap.put("PRI0004failMsg", "银行卡读卡器连接异常");
			return flag;
		}
		String id = bank.getStatus();
		if("0".equals(id)){
			String touchStatus = bank.getTouchStatus();//接触式
			if("1".equals(touchStatus)){
				//卡座正常且卡座当前有卡
				//执行退卡
				ICBankQuitBean icBankQuit = null;
				try {
					icBankQuit = ICBank.ICBankQuit("1", "30");
				} catch (Exception e) {
					flag = false;
					logger.error("银行卡读卡器退卡异常"+e);
					primap.put("PRI0004", "2");
					primap.put("PRI0004failMsg", "银行卡读卡器退卡异常");
				}
				String status = icBankQuit.getStatus();
				if("0".equals(status)){
					//退卡成功
					primap.put("PRI0004", "0");
					primap.put("PRI0004failMsg", "银行卡读卡器退卡状态值:正常"+status);
				}else{
					flag = false;
					primap.put("PRI0004", "2");
					primap.put("PRI0004failMsg", "银行卡读卡器退卡状态值:异常"+status);
				}
			}else if("2".equals(touchStatus)){
				//卡座正常且卡座当前无卡
				//正常运行
				primap.put("PRI0004", "0");
				primap.put("PRI0004failMsg", "银行卡读卡器状态值:正常"+touchStatus);
			}else{
				flag = false;
				primap.put("PRI0004", "2");
				primap.put("PRI0004failMsg", "银行卡读卡器状态值:异常"+touchStatus);
			}
		}else{
			flag = false;
			primap.put("PRI0004", "3");
			primap.put("PRI0004failMsg", "银行卡读卡器调用:异常"+id);
		}		
		return flag;
	}
	
	/**
	 * 检查密码键盘状态
	 */
	public boolean checkKeypadState(){
		// 检查密码键盘状态
		boolean flag = true;
		KeypadDriver driver = new KeypadDriver();
		Map<String, String> driverMap = null;
		try {
			driverMap = driver.checkKeypadStatus();
		} catch (Exception e) {
			flag = false;
			logger.error("密码键盘连接异常"+e);
			primap.put("PRI0001", "3");
			primap.put("PRI0001failMsg", "密码键盘连接异常");
			return flag;
		}
		String resCode = driverMap.get("ResCode");
		if(resCode != null){
			if("S".equals(resCode)){
				primap.put("PRI0001", "0");
				primap.put("PRI0001failMsg", "密码键盘状态值:正常"+resCode);
			}else{
				flag = false;
				primap.put("PRI0001", "2");
				primap.put("PRI0001failMsg", "密码键盘状态值:异常"+resCode);
			}
		}else{
			flag = false;
			primap.put("PRI0001", "3");
			primap.put("PRI0001failMsg", "密码键盘状态值:"+"获取状态失败，无返回值");
		}
		return flag;
	}
	
	/**
	 * 检查指纹状态
	 */
	public boolean checkFingerPrintState(){
		// 检查指纹状态
		boolean flag = true;
		FingerPrint print = new FingerPrint();
		FingerPrintCheckStatusBean bean = null;
		try {
			bean = print.checkStatus("4");
		} catch (Exception e) {
			flag = false;
			logger.error("指纹仪连接异常"+e);
			primap.put("PRI0002", "3");
			primap.put("PRI0002failMsg", "指纹仪连接异常");
			return flag;
		}
		String id = bean.getId();
		String print_status = bean.getStatus();
		if("0".equals(id)){
			if(print_status != null){
				if("0".equals(print_status)){
					
					primap.put("PRI0002", "0");
					primap.put("PRI0002failMsg", "指纹仪状态值:正常"+print_status);
					
				}else{
					
					flag = false;
					primap.put("PRI0002", "2");
					primap.put("PRI0002failMsg", "指纹仪状态值:异常"+print_status);
					
				}
				
			}else{
				
				flag = false;
				primap.put("PRI0002", "2");
				primap.put("PRI0002failMsg", "指纹仪状态值:获取状态失败，无返回值");
			}
			
		}else{
			
			flag = false;
			primap.put("PRI0002", "3");
			primap.put("PRI0002failMsg", "指纹仪调用:异常"+id);
			
		}
		return flag;
	}	
	
	/**
	 * 检查二维码扫描仪状态
	 */
	public boolean checkDimensionState(){
		// 检查二维码扫描仪状态
		boolean flag = true;
		DimensionCheckStatusBean dcr = null;
		try {
			dcr = Dimension.checkStatus("2");
		} catch (Exception e) {
			flag = false;
			logger.error("二维码扫描仪连接异常"+e);
			primap.put("PRI0008", "3");
			primap.put("PRI0008failMsg", "二维码扫描仪连接异常");
			return flag;
		}
		String id = dcr.getId();
		String status = dcr.getStatus();
		if("0".equals(id)){
			if(status != null){
				if("0".equals(status)){
					
					primap.put("PRI0008", "0");
					primap.put("PRI0008failMsg", "二维码扫描仪状态值:正常"+status);
					
				}else{
					
					flag = false;
					primap.put("PRI0008", "2");
					primap.put("PRI0008failMsg", "二维码扫描仪状态值:异常"+status);
					
				}
				
			}else{
				
				flag = false;
				primap.put("PRI0008", "2");
				primap.put("PRI0008failMsg", "二维码扫描仪状态值:获取状态失败，无返回值");
			}
			
		}else{
			
			flag = false;
			primap.put("PRI0008", "3");
			primap.put("PRI0008failMsg", "二维码扫描仪调用:异常"+id);
			
		}
		return flag;
	}
}
