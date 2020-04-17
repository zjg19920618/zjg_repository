package com.boomhope.Bill.Comm;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.TransService.BillChOpen.Bean.BillChOpenInfoBean;
import com.boomhope.Bill.TransService.BillChOpen.Bean.PublicBillChangeOpenBean;
import com.boomhope.Bill.peripheral.action.BillPrint;
import com.boomhope.Bill.peripheral.bean.BillCheckStateBean;

public class HKPrintPt {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(HKPrintPt.class);
	Thread thread = null;
	String errmsg = "";

	public boolean print(PublicBillChangeOpenBean bean) {
		// 凭条打印处理
		boolean isPrint = true;
		
		logger.info("检测凭条打印机状态");
		boolean billPrintcheck = billPrintcheck();// 状态检测
		if (!billPrintcheck) {
			logger.error("凭条打印机异常");
			AccountTradeCodeAction.transBean.setBillMsg("凭条打印机异常");
			isPrint = false;
			return isPrint;
		}
		
		File file = new File("pic\\");// logo路径
		logger.info("检测logo路径");
		boolean logoPath = logoPath(file.getAbsolutePath());
		if (!logoPath) {
			logger.error("logo路径加载错误");
			AccountTradeCodeAction.transBean.setBillMsg("logo路径加载错误");
			isPrint = false;
			return isPrint;
		}
		
		logger.info("生成打印内容");
		String billMsg = billMsg(bean);// 打印内容
		
		logger.info("打印凭条");
		boolean billPrint = billPrint(billMsg);
		if (!billPrint) {
			logger.error("凭条打印异常");
			AccountTradeCodeAction.transBean.setBillMsg("凭条打印异常");
			isPrint = false;
			return isPrint;
		}
		return isPrint;

	}

	/** 凭条打印机状态检测 */
	public boolean billPrintcheck() {
		boolean flag = false;
		// 凭条打印机状态检测
		BillCheckStateBean billPrintCheckStatus = null;
		try {
			billPrintCheckStatus = BillPrint.billPrintCheckStatus();
			logger.info("凭条打印机状态检测返回值" + billPrintCheckStatus);
			System.out.println("凭条打印机状态检测返回值" + billPrintCheckStatus);
		} catch (Exception e1) {
			logger.error("通讯异常，未找到凭条打印机");
			return flag;
		}
		String id = billPrintCheckStatus.getId();
		String status = billPrintCheckStatus.getStatus();
		if ("0".equals(id)) {
			if ("0".equals(status)) {
				flag=true;
			} else if ("1".equals(status)) {
				logger.error("凭条打印机异常，打印机未连接或未上电");
			} else if ("2".equals(status)) {
				logger.error("凭条打印机异常，打印机和查状态控件不匹配");
			} else if ("3".equals(status)) {
				logger.error("凭条打印机异常，容纸器错误");
			} else if ("4".equals(status)) {
				logger.error("凭条打印机异常，打印机忙");
			} else if ("5".equals(status)) {
				logger.error("凭条打印机异常，打印头过热");
			} else if ("6".equals(status)) {
				logger.error("凭条打印机异常，切刀错误");
			} else if ("7".equals(status)) {
				logger.error("凭条打印机异常，卡纸");
			} else if ("8".equals(status)) {
				logger.error("凭条打印机异常，纸将尽");
			} else if ("9".equals(status)) {
				logger.error("凭条打印机异常，纸尽");
			} else if ("10".equals(status)) {
				logger.error("凭条打印机异常，主板系统错误");
			} else if ("11".equals(status)) {
				logger.error("凭条打印机异常，出口纸检测");
			} else if ("12".equals(status)) {
				logger.error("凭条打印机异常，打印头打开");
			} else if ("13".equals(status)) {
				logger.error("凭条打印机异常，黑标错误");
			} else if ("14".equals(status)) {
				logger.error("凭条打印机异常，打印机错误");
			} else if ("15".equals(status)) {
				logger.error("凭条打印机异常，检测状态失败");
			} else {
				logger.error("凭条打印机异常，未知错误");
			}
		} else if ("1".equals(id)) {
			logger.error("凭条打印机错误，" + status);
		} else if ("2".equals(id)) {
			logger.error("凭条打印机错误，" + status);
		} else if ("3".equals(id)) {
			logger.error("凭条打印机错误，" + status);
		} else if ("4".equals(id)) {
			logger.error("凭条打印机错误，" + status);
		} else if ("5".equals(id)) {
			logger.error("凭条打印机错误，" + status);
		} else if ("6".equals(id)) {
			logger.error("凭条打印机错误，" + status);
		} else if ("7".equals(id)) {
			logger.error("凭条打印机错误，" + status);
		} else if ("8".equals(id)) {
			logger.error("凭条打印机错误，" + status);
		} else if ("9".equals(id)) {
			logger.error("凭条打印机错误，" + status);
		} else if ("10".equals(id)) {
			logger.error("凭条打印机错误，" + status);
		} else {
			logger.error("凭条打印机错误，未知错误");
		}
		return flag;
	}

	/** 凭条打印 */
	public boolean billPrint(final String text) {
		// 凭条打印
		boolean flag = true;
		BillCheckStateBean billPrint = null;
		try {
			billPrint = BillPrint.billPrint(text);
			logger.info("凭条打印返回值" + billPrint);
			System.out.println("凭条打印返回值" + billPrint);
		} catch (Exception e) {
			logger.error("通讯异常，未找到凭条打印机");
			flag = false;
			return flag;
		}
		String id = billPrint.getId();
		String status = billPrint.getStatus();

		if ("0".equals(id)) {
			logger.info("凭条打印中");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logger.info(status); // 打印成功信息

			// 切纸
			 billCutting();
		} else if ("1".equals(id)) {
			logger.error("凭条打印失败，" + status);
			flag = false;
		} else if ("2".equals(id)) {
			logger.error("凭条打印失败，" + status);
			flag = false;
		} else if ("3".equals(id)) {
			logger.error("凭条打印失败，" + status);
			flag = false;
		} else if ("4".equals(id)) {
			logger.error("凭条打印失败，" + status);
			flag = false;
		} else if ("5".equals(id)) {
			logger.error("凭条打印失败，" + status);
			flag = false;
		} else if ("6".equals(id)) {
			logger.error("凭条打印失败，" + status);
			flag = false;
		} else if ("7".equals(id)) {
			logger.error("凭条打印失败，" + status);
			flag = false;
		} else if ("8".equals(id)) {
			logger.error("凭条打印失败，" + status);
			flag = false;
		} else if ("9".equals(id)) {
			logger.error("凭条打印失败，" + status);
			flag = false;
		} else if ("10".equals(id)) {
			logger.error("凭条打印失败，" + status);
			flag = false;
		} else {
			logger.error("凭条打印失败，未知错误");
			flag = false;
		}
		return flag;
	}

	/** Logo路径 */
	public boolean logoPath(final String path) {
		// logo路径
		boolean flag = true;
		BillCheckStateBean billPrintLogo = null;
		try {
			billPrintLogo = BillPrint.billPrintLogo(path);
			logger.info("凭条logo返回值" + billPrintLogo);
			System.out.println("凭条logo返回值" + billPrintLogo);
		} catch (Exception e) {
			logger.error("通讯异常，未找到凭条打印机");
			flag = false;
			return flag;
		}
		String id = billPrintLogo.getId();
		String status = billPrintLogo.getStatus();
		if ("0".equals(id)) {
			logger.info("凭条logo路径:" + status);
		} else if ("1".equals(id)) {
			logger.error("凭条logo路径失败，" + status);
			flag = false;
		} else if ("2".equals(id)) {
			logger.error("凭条logo路径失败，" + status);
			flag = false;
		} else if ("3".equals(id)) {
			logger.error("凭条logo路径失败，" + status);
			flag = false;
		} else if ("4".equals(id)) {
			logger.error("凭条logo路径失败，" + status);
			flag = false;
		} else if ("5".equals(id)) {
			logger.error("凭条logo路径失败，" + status);
			flag = false;
		} else if ("6".equals(id)) {
			logger.error("凭条logo路径失败，" + status);
			flag = false;
		} else if ("7".equals(id)) {
			logger.error("凭条logo路径失败，" + status);
			flag = false;
		} else if ("8".equals(id)) {
			logger.error("凭条logo路径失败，" + status);
			flag = false;
		} else if ("9".equals(id)) {
			logger.error("凭条logo路径失败，" + status);
			flag = false;
		} else if ("10".equals(id)) {
			logger.error("凭条logo路径失败，" + status);
			flag = false;
		} else {
			logger.error("凭条logo路径失败，未知错误");
			flag = false;
		}
		return flag;
	}

	/** 切纸 */
	public boolean billCutting() {
		// 凭条切刀
		boolean flag = true;
		BillCheckStateBean billCutting = null;
		try {
			billCutting = BillPrint.billCutting("0");
			logger.info("凭条切纸返回值" + billCutting);
			System.out.println("凭条切纸返回值" + billCutting);
		} catch (Exception e) {
			logger.error("通讯异常，未找到凭条打印机");
			flag = false;
			return flag;
		}
		String id = billCutting.getId();
		String status = billCutting.getStatus();
		if ("0".equals(id)) {
			// exitTrans();
			logger.info("凭条切纸成功");
		} else if ("1".equals(id)) {
			logger.error("凭条切纸失败，" + status);
			flag = false;
		} else if ("2".equals(id)) {
			logger.error("凭条切纸失败，" + status);
			flag = false;
		} else if ("3".equals(id)) {
			logger.error("凭条切纸失败，" + status);
			flag = false;
		} else if ("4".equals(id)) {
			logger.error("凭条切纸失败，" + status);
			flag = false;
		} else if ("5".equals(id)) {
			logger.error("凭条切纸失败，" + status);
			flag = false;
		} else if ("6".equals(id)) {
			logger.error("凭条切纸失败，" + status);
			flag = false;
		} else if ("7".equals(id)) {
			logger.error("凭条切纸失败，" + status);
			flag = false;
		} else if ("8".equals(id)) {
			logger.error("凭条切纸失败，" + status);
			flag = false;
		} else if ("9".equals(id)) {
			logger.error("凭条切纸失败，" + status);
			flag = false;
		} else if ("10".equals(id)) {
			logger.error("凭条切纸失败，" + status);
			flag = false;
		} else {
			logger.error("凭条切纸失败，未知错误");
			flag = false;
		}
		return flag;
	}

	/** 凭条信息 */
	public String billMsg(PublicBillChangeOpenBean bean) {

		// 结算信息
		String pri = bean.getEndAmt();// 金额
		NumberFormat fmt= NumberFormat.getCurrencyInstance();
		pri=fmt.format(Double.valueOf(pri));
		String advnInit = bean.getRouting();// 预付利息
		// 核心日期
		String date = bean.getSvrDate();
		SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
		long timemillis1 = System.currentTimeMillis();
		String format1 = time.format(new Date(timemillis1));
		
		String measure = "";
		if(bean.getProCode().startsWith("JX") || bean.getProCode().startsWith("RJ")){	
			measure = bean.getJxRyjDepTem()+"天";//存期

		}else{
			measure = bean.getMonthsUpperStr();//存期
		}
		String machineNo=GlobalParameter.branchNo;//机构号
		String name=bean.getAccName().trim() ;
		String proName = bean.getProName();//产品名称
		name="*"+name.substring(1);
		int length = bean.getAccNo().trim().length();
		String accNo = bean.getAccNo().trim().substring(0, 6) + "******"
				+ bean.getAccNo().trim().substring(length - 4);
		String text1 = "1#0|宋体|13|0|0|0|0|0|1|日    期 : " + date+ "    时    间 : " + format1 
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|交易类型 : 存单换开"
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|产品名称 : " + proName
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|存    期 : " + measure
				+ "#0|宋体|13|0|0|0|0|0|1| " 
				+ "#0|宋体|13|0|0|0|0|0|1|存单账户 : "+ accNo
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|户    名 : "+ name
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|金    额 : " + pri+"元"
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|预计利息 : "+ advnInit.trim()+"元" 
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|流 水 号 : "+ bean.getHKSvrJrnlNo().trim()
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|交易机构 : "+ machineNo
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|交易柜员 : " + GlobalParameter.tellerNo
				+ "#0|宋体|13|0|0|0|0|0|1| ";
		String text = null;
		text = text1
				+ "#0|宋体|13|0|0|0|0|0|1|客户须知 :"
				+ "#0|宋体|13|0|0|0|0|0|1|1.此核对单仅供客户核对使用."
				+ "#0|宋体|13|0|0|0|0|0|1|2.如有异议，请立即联系我行有关网点协商解决. #";

		return text;
	}

//	public static void main(String[] args) {
//		String path = "D:\\zjg_Bill\\Bill2\\Bill\\pic\\tsBackLogo.png";
//		try {
//			PublicBillChangeOpenBean transBean = new PublicBillChangeOpenBean();
//			transBean.setCardNo("6229000201899101965");
//			transBean.setSubAccNo("45");
//			transBean.setReadIdName("王晓明");
//			transBean.setAmount("20000");
//			//transBean.setInte("26.12");
//			transBean.setHKSvrJrnlNo("000525211");
//			transBean.setSvrDate("20261026");
//			HKPrintPt print = new HKPrintPt();
//			print.print(transBean,transBean);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//	}
}
