package com.boomhope.Bill.Comm;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.peripheral.action.BillPrint;
import com.boomhope.Bill.peripheral.bean.BillCheckStateBean;

public class LostXHPrintPt {

	static Logger logger = Logger.getLogger(LostXHPrintPt.class);

	public Map<String,String> print(Map<String,String> map) {
		
		Map<String,String> returnMap = null;
		// 凭条打印处理
		logger.info("检测凭条打印机状态");
		returnMap = billPrintcheck();// 状态检测
		if (!"000000".equals(returnMap.get("resCode"))) {
			return returnMap;
		}
		
		//logo路径
		logger.info("检测logo路径");
		returnMap = logoPath();
		if (!"000000".equals(returnMap.get("resCode"))) {
			return returnMap;
		}
		
		// 打印内容
		logger.info("生成打印内容");
		returnMap = lostMsg(map);
		if (!"000000".equals(returnMap.get("resCode"))) {
			return  returnMap;
		}
		
		//打印凭条
		logger.info("打印凭条");
		returnMap = billPrint(returnMap.get("path"));
		if (!"000000".equals(returnMap.get("resCode"))) {
			return returnMap;
		}
		
		// 切纸
		returnMap = billCutting();
		returnMap.put("resCode", "000000");
		return returnMap;
	}

	/** 凭条打印机状态检测 */
	public Map<String,String> billPrintcheck() {
		
		Map<String,String> map = new HashMap<String,String>();
		// 凭条打印机状态检测
		BillCheckStateBean billPrintCheckStatus = null;
		try {
			billPrintCheckStatus = BillPrint.billPrintCheckStatus();
			logger.info("凭条打印机状态检测返回值" + billPrintCheckStatus);
			
		} catch (Exception e1) {
			
			logger.error("通讯异常，未找到凭条打印机"+e1);
			map.put("resCode", "999999");
			map.put("errMsg", "通讯异常，未找到凭条打印机");
			return map;
		}
		
		String id = billPrintCheckStatus.getId();
		String status = billPrintCheckStatus.getStatus();
		logger.info("id值:"+id);
		logger.info("status值:"+status);
		
		if ("0".equals(id)) {
			if ("0".equals(status)) {
				//状态检测正常
				logger.info("凭条打印机状态检测正常");
				map.put("resCode", "000000");
				map.put("errMsg", "凭条打印机正常");
				return map;
				
			} else if ("1".equals(status)) {
				logger.error("凭条打印机异常，打印机未连接或未上电");
				map.put("resCode", "999998");
				map.put("errMsg", "凭条打印机异常，打印机未连接或未上电");
				return map;
			} else if ("2".equals(status)) {
				logger.error("凭条打印机异常，打印机和查询状态控件不匹配");
				map.put("resCode", "999998");
				map.put("errMsg", "凭条打印机异常，打印机和查询状态控件不匹配");
				return map;
			} else if ("3".equals(status)) {
				logger.error("凭条打印机异常，容纸器错误");
				map.put("resCode", "999998");
				map.put("errMsg", "凭条打印机异常，容纸器错误");
				return map;
			} else if ("4".equals(status)) {
				logger.error("凭条打印机异常，打印机忙");
				map.put("resCode", "999998");
				map.put("errMsg", "凭条打印机异常，打印机忙");
				return map;
			} else if ("5".equals(status)) {
				logger.error("凭条打印机异常，打印头过热");
				map.put("resCode", "999998");
				map.put("errMsg", "凭条打印机异常，打印头过热");
				return map;
			} else if ("6".equals(status)) {
				logger.error("凭条打印机异常，切刀错误");
				map.put("resCode", "999998");
				map.put("errMsg", "凭条打印机异常，切刀错误");
				return map;
			} else if ("7".equals(status)) {
				logger.error("凭条打印机异常，卡纸");
				map.put("resCode", "999998");
				map.put("errMsg", "凭条打印机异常，卡纸");
				return map;
			} else if ("8".equals(status)) {
				logger.error("凭条打印机异常，纸将尽");
				map.put("resCode", "999998");
				map.put("errMsg", "凭条打印机异常，纸将尽");
				return map;
			} else if ("9".equals(status)) {
				logger.error("凭条打印机异常，纸尽");
				map.put("resCode", "999998");
				map.put("errMsg", "凭条打印机异常，纸尽");
				return map;
			} else if ("10".equals(status)) {
				logger.error("凭条打印机异常，主板系统错误");
				map.put("resCode", "999998");
				map.put("errMsg", "凭条打印机异常，主板系统错误");
				return map;
			} else if ("11".equals(status)) {
				logger.error("凭条打印机异常，出口纸检测");
				map.put("resCode", "999998");
				map.put("errMsg", "凭条打印机异常，出口纸检测");
				return map;
			} else if ("12".equals(status)) {
				logger.error("凭条打印机异常，打印头打开");
				map.put("resCode", "999998");
				map.put("errMsg", "凭条打印机异常，打印头打开");
				return map;
			} else if ("13".equals(status)) {
				logger.error("凭条打印机异常，黑标错误");
				map.put("resCode", "999998");
				map.put("errMsg", "凭条打印机异常，黑标错误");
				return map;
			} else if ("14".equals(status)) {
				logger.error("凭条打印机异常，打印机错误");
				map.put("resCode", "999998");
				map.put("errMsg", "凭条打印机异常，打印机错误");
				return map;
			} else if ("15".equals(status)) {
				logger.error("凭条打印机异常，检测状态失败");
				map.put("resCode", "999998");
				map.put("errMsg", "凭条打印机异常，检测状态失败");
				return map;
			} else {
				logger.error("凭条打印机异常，未知错误");
				map.put("resCode", "999998");
				map.put("errMsg", "凭条打印机异常，未知错误");
				return map;
			}
			
		} else {
			logger.error("凭条打印机错误,"+status);
			map.put("resCode", "999997");
			map.put("errMsg", "凭条打印机错误,"+status);
			return map;
		}
	}


	/** Logo路径 */
	public Map<String,String> logoPath() {
		Map<String,String> map = new HashMap<String,String>();
		String path = "";
		BillCheckStateBean billPrintLogo = null;
		//查找logo路径
		try {
			
			File file = new File("pic\\");
			path = file.getAbsolutePath();
			logger.info("凭条logo绝对路径" + path);
		} catch (Exception e) {
			logger.error("logo图片路径异常"+e);
			map.put("resCode", "999999");
			map.put("errMsg", "logo图片路径未找到");
			return map;
		}
		
		// 调用logo路径的方法
		try {
			billPrintLogo = BillPrint.billPrintLogo(path);
			logger.info("凭条logo返回值" + billPrintLogo);
		} catch (Exception e) {
			logger.error("凭条logo图片路径异常"+e);
			map.put("resCode", "999999");
			map.put("errMsg", "logo图片路径查找失败");
			return map;
		}
		
		String id = billPrintLogo.getId();
		String status = billPrintLogo.getStatus();
		logger.info("id值:"+id);
		logger.info("status值:"+status);
		
		if ("0".equals(id)) {
			
			logger.info("凭条logo路径查找成功:" + status);
			map.put("resCode", "000000");
			map.put("errMsg", "logo图片路径查找成功");
			return map;
		
		} else {
			
			logger.error("logo图片路径失败,"+status);
			map.put("resCode", "999997");
			map.put("errMsg", "logo图片路径失败,"+status);
			return map;
		}
	}
	
	/** 凭条打印 */
	public Map<String,String> billPrint(final String text) {
		Map<String,String> map = new HashMap<String,String>();
		// 凭条打印
		BillCheckStateBean billPrint = null;
		try {
			billPrint = BillPrint.billPrint(text);
			logger.info("凭条打印返回值" + billPrint);
			
		} catch (Exception e) {
			logger.error("凭条打印调用失败"+e);
			map.put("resCode", "999999");
			map.put("errMsg", "凭条打印调用失败");
			return map;
		}
		
		String id = billPrint.getId();
		String status = billPrint.getStatus();
		logger.info("id值:"+id);
		logger.info("status值:"+status);
		
		if ("0".equals(id)) {
			
			logger.info("凭条打印中");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				logger.info("睡眠失败"+e);
			}
			
			logger.info(status); // 打印成功信息
			map.put("resCode", "000000");
			map.put("errMsg", "凭条打印成功");
			return map;
			
		} else {
			
			logger.error("凭条打印失败，" + status);
			map.put("resCode", "999998");
			map.put("errMsg", "凭条打印失败，"+status);
			return map;
		}
	}

	/** 切纸 */
	public Map<String,String> billCutting() {
		Map<String,String> map = new HashMap<String,String>();
		// 凭条切刀
		BillCheckStateBean billCutting = null;
		try {
			billCutting = BillPrint.billCutting("0");
			logger.info("凭条切纸返回值" + billCutting);
			
		} catch (Exception e) {
			
			logger.error("凭条切刀调用失败"+e);
			map.put("resCode", "999999");
			map.put("errMsg", "凭条切刀调用失败");
			return map;
		}
		String id = billCutting.getId();
		String status = billCutting.getStatus();
		logger.info("id值:"+id);
		logger.info("status值:"+status);
		
		if ("0".equals(id)) {
			
			logger.info("凭条切纸成功");
			map.put("resCode", "000000");
			map.put("errMsg", "凭条切纸成功");
			return map;
			
		} else {
			
			logger.error("凭条切纸失败，" + status);
			map.put("resCode", "999998");
			map.put("errMsg", "凭条切纸失败，" + status);
			return map;
		}
	}

	/** 挂失销户凭条信息 */
	public Map<String,String> lostMsg(Map<String,String> lostMap) {
		Map<String,String> map = new HashMap<String,String>();
		try {
			//交易时间
			SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
			long timemillis1 = System.currentTimeMillis(); 
			String format1 = time.format(new Date(timemillis1));
			
			//转入账号
			int length=lostMap.get("selectCardNo").trim().length();
			String name = "";
			String cardNo = lostMap.get("selectCardNo").trim().substring(0, 6)+"******"+lostMap.get("selectCardNo").trim().substring(length-4);
			if("1_2".equals(lostMap.get("cardType"))){
				name = "转入电子账号 : "+cardNo;
			} else {
				name = "转入卡号 : " +cardNo;
			}
			
			//挂失账号
			int length1=lostMap.get("lostAccNo").trim().length();
			String accNo = "";
			String subCardNo = "";
			if(lostMap.get("lostAccNo").contains("-")){
				
				String[] split = lostMap.get("lostAccNo").split("-");
				subCardNo = split[0].substring(0, 6)+"******"+split[0].substring(length-4);
				accNo= subCardNo + "-" + split[1];
				
			}else{
				
				accNo=lostMap.get("lostAccNo").trim().substring(0, 6)+"******"+lostMap.get("lostAccNo").trim().substring(length1-4);
			}
			
			String text = "1#0|宋体|13|0|0|0|0|0|1|日    期 : "+lostMap.get("svrDate").trim()+"   时    间 : "+format1
					+ "#0|宋体|13|0|0|0|0|0|1| "
					+ "#0|宋体|13|0|0|0|0|0|1|交易名称 : "+lostMap.get("lostType")
					+ "#0|宋体|13|0|0|0|0|0|1| "
					+ "#0|宋体|13|0|0|0|0|0|1|挂失账户 : "+accNo
					+ "#0|宋体|13|0|0|0|0|0|1| "
					+ "#0|宋体|13|0|0|0|0|0|1|户    名 : *"+lostMap.get("custName").trim().substring(1)
					+ "#0|宋体|13|0|0|0|0|0|1| "
					+ "#0|宋体|13|0|0|0|0|0|1|本    金 : "+lostMap.get("realAmt").trim()+"元"
					+ "#0|宋体|13|0|0|0|0|0|1| "
					+ "#0|宋体|13|0|0|0|0|0|1|实付利息 : "+lostMap.get("realPri").trim()+"元"
					+ "#0|宋体|13|0|0|0|0|0|1| "
					+ "#0|宋体|13|0|0|0|0|0|1|"+name
					+ "#0|宋体|13|0|0|0|0|0|1| "
					+ "#0|宋体|13|0|0|0|0|0|1|挂失销户流水号 : "+lostMap.get("lostJrnlNo").trim()
					+ "#0|宋体|13|0|0|0|0|0|1| "
					+ "#0|宋体|13|0|0|0|0|0|1|交易机构 : "+GlobalParameter.branchNo
					+ "#0|宋体|13|0|0|0|0|0|1| "
					+ "#0|宋体|13|0|0|0|0|0|1|交易柜员 : "+GlobalParameter.tellerNo
					+ "#0|宋体|13|0|0|0|0|0|1| "
					+ "#0|宋体|13|0|0|0|0|0|1|客户须知 :"
					+ "#0|宋体|13|0|0|0|0|0|1|1.此核对单仅供客户核对使用."
					+ "#0|宋体|13|0|0|0|0|0|1|2.如有异议，请立即联系我行有关网点协商解决. #";
				;		
			map.put("resCode", "000000");
			map.put("path", text);
			return map;
		} catch (Exception e) {
			logger.error("凭条打印信息获取异常"+e);
			map.put("resCode", "999999");
			map.put("errMsg", "凭条打印信息获取失败");
			return map;
		}
	}
}
