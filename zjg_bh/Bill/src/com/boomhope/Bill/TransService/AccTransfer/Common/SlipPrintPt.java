package com.boomhope.Bill.TransService.AccTransfer.Common;

import java.io.File;
import java.util.Map;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.Interface.InterfaceSendMsg;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.Bill.peripheral.action.BillPrint;
import com.boomhope.Bill.peripheral.bean.BillCheckStateBean;

/**
 * 凭条打印
 * @author hk
 *
 */


public class SlipPrintPt {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(SlipPrintPt.class);
	Thread thread = null;
	String errmsg = "";
	private PublicAccTransferBean tfmb;

	public boolean print(PublicAccTransferBean transBean) {
		// 凭条打印处理
		boolean isPrint = false;
		logger.info("检测凭条打印机状态");
		boolean billPrintcheck = billPrintcheck();// 状态检测
		if (!billPrintcheck) {
			logger.error("凭条打印机异常");
			transBean.setBillMsg("凭条打印机异常");
			return isPrint;
		}
		
		//查询本机构信息
		boolean checkResult=checkBankInfo();
		if(!checkResult){
			logger.info("检测本机构信息异常");
			transBean.setBillMsg("检测本机构信息异常");
			return isPrint;
		}
		
		File file = new File("pic\\");// logo路径
		logger.info("检测logo路径");
		boolean logoPath = logoPath(file.getAbsolutePath());
		if (!logoPath) {
			logger.error("logo路径加载错误");
			transBean.setBillMsg("logo路径加载错误");
			return isPrint;
		}
		logger.info("生成打印内容");
		String billMsg = billMsg(transBean);// 打印内容
		logger.info("打印凭条");
		boolean billPrint = billPrint(billMsg);
		if (!billPrint) {
			logger.error("凭条打印异常");
			transBean.setBillMsg("凭条打印打印失败");
			return isPrint;
		}
		isPrint = true;
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
			logger.debug("凭条打印机状态检测返回值" + billPrintCheckStatus);
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
			logger.debug("凭条打印返回值" + billPrint);
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
			logger.debug("凭条logo返回值" + billPrintLogo);
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
			logger.debug("凭条切纸返回值" + billCutting);
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
	public String billMsg(PublicAccTransferBean transBean) {
		int length=transBean.getChuZhangCardNo().trim().length();
		String hcardNo=transBean.getChuZhangCardNo().trim().substring(0, 6)+"******"+transBean.getChuZhangCardNo().trim().substring(length-4);//汇款卡号
		String cardName="*"+transBean.getChuZhangcardName().trim().substring(1);//汇款户名
		String hkhh=transBean.getPayHbrInstName().trim();//汇款开户行名
		String scardNo=transBean.getRuZhangCardNo().trim();//收款卡号
		String scardName="*"+transBean.getRuZhangcardName().trim().substring(1);//收款户名
		String skhh=transBean.getRecvBankName().trim();//收款开户行
		String amt=transBean.getRemitAmt().trim();//汇款金额
		String use="";
		if("0".equals(transBean.getIsCardBank())){
			use = transBean.getPurpos().trim();//汇款用途
		}else{
			use = transBean.getAppdText().trim();//汇款用途
		}
		String method=transBean.getZhangWay().trim();//到账方式
		String svrJrnlNo=transBean.getSvrJrnlNo().trim();//核心流水号
		String transInstName=tfmb.getPayHbrInstName();//本机构名称
		String tellerNo = GlobalParameter.tellerNo;//交易柜员号
		String certDate = transBean.getSvrDate();//核心日期
		String transTime =transBean.getTransDoTime();//交易时间
		String text1 = 
				"1#0|宋体|13|0|0|0|0|0|1|交易日期："+certDate+" "+transTime
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|汇款账号 :"+hcardNo
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|汇款户名 : "+cardName
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|汇款开户行： "+hkhh
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|收款账号 : " +scardNo
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|收款户名： " +scardName
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|收款开户行 : "+skhh
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|汇款金额： "+amt
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|汇款用途： "+use
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|到账方式： "+method
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|流水号 : "+svrJrnlNo
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|交易柜员："+tellerNo
				+ "#0|宋体|13|0|0|0|0|0|1| "
				+ "#0|宋体|13|0|0|0|0|0|1|交易机构："+transInstName;
		String text = null;
		if("0".equals(transBean.getIsCardBank())){
			text = text1
				+ "#0|宋体|13|0|0|0|0|0|1|温馨提示 :"
				+ "#0|宋体|13|0|0|0|0|0|1|业务已受理，待后续账务处理。 #";
//				+ "#0|宋体|13|0|0|0|0|0|1|您可以次日到我行回单机打印正式回单。"
//				+ "#0|宋体|13|0|0|0|0|0|1| #0|宋体|13|0|0|0|0|0|1| #0|宋体|13|0|0|0|0|0|1|  #0|宋体|13|0|0|0|0|0|1|  #0|宋体|13|0|0|0|0|0|1| #";
		}else{
			text = text1
				+ "#0|宋体|13|0|0|0|0|0|1|温馨提示 :"
				+ "#0|宋体|13|0|0|0|0|0|1|业务已受理，待后续账务处理。"
				+ "#0|宋体|13|0|0|0|0|0|1|您的请求为跨行业务，若是因信息错误导致转账不成功,请收到信息后重新操作或到柜台办理。 #";
//				+ "#0|宋体|13|0|0|0|0|0|1| #0|宋体|13|0|0|0|0|0|1| #0|宋体|13|0|0|0|0|0|1|  #0|宋体|13|0|0|0|0|0|1|  #0|宋体|13|0|0|0|0|0|1| #";
		}

		return text;

		
	}

	public static void main(String[] args) {
		String path = "D:\\zjg_Bill\\Bill2\\Bill\\pic\\tsBackLogo.png";
		try {
			PublicAccTransferBean transBean = new PublicAccTransferBean();
		
			SlipPrintPt print = new SlipPrintPt();
			print.print(transBean);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public boolean checkBankInfo(){
		tfmb = new PublicAccTransferBean();
		tfmb.setPayHbrInstNo(GlobalParameter.branchNo);
		
		try {
			
			Map<String,String> map = InterfaceSendMsg.inter01118(tfmb);
			tfmb.getReqMCM001().setReqAfter((String)map.get("resCode"), (String)map.get("errMsg"));
			if("000000".equals(map.get("resCode"))){
				return true;				
			}else{
				return false;
			}
		} catch (Exception e) {
			logger.error("查询机构号失败："+e);
			return false;
		}
		
	}
	
}
