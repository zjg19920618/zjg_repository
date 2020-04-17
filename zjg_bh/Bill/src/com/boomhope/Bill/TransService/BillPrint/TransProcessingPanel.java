package com.boomhope.Bill.TransService.BillPrint;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Comm.PrintBill;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Framework.MainFrame;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.MistakeDialog;
import com.boomhope.Bill.Socket.SocketClient;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.ICBankBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.ICSubAccNo;
import com.boomhope.Bill.TransService.BillPrint.Interface.SearchProducRateInfo;
import com.boomhope.Bill.Util.DateTool;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.FtpFileUtils;
import com.boomhope.Bill.Util.JpgUtils;
import com.boomhope.Bill.Util.MoneyUtils;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.SFTPUtil;
import com.boomhope.Bill.Util.TextFileReader;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.Bill.peripheral.action.MachineLed;
import com.boomhope.Bill.peripheral.action.PrintMachine;
import com.boomhope.Bill.peripheral.bean.LedStateBean;
import com.boomhope.Bill.peripheral.bean.PrintMachineCheckStateBean;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK02864ResBean;
import com.boomhope.tms.message.in.InResBean;
import com.boomhope.tms.message.in.bck.BCK0016ResBean;
import com.boomhope.tms.message.in.bck.BCK0016ResBodyBean;
import com.boomhope.tms.message.in.bck.BCK0020ResBean;
import com.boomhope.tms.message.in.tms.Tms0005ResBean;
import com.boomhope.tms.message.in.tms.Tms0005ResBodyBean;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * title:打印交易处理中
 * @author ly
 * 2016年11月9日上午10:39:35
 */
public class TransProcessingPanel extends BaseTransPanelNew {

	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(TransProcessingPanel.class);
	Timer checkTimer = null;

	public TransProcessingPanel(final BillPrintBean transBean,List<ICBankBean> bankBeans) {
		logger.info("进入打印交易处理中");
		this.billPrintBean = transBean;
		this.bankBeans = bankBeans;
		
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 

		/* 警告图标 */
		JLabel billImage = new JLabel();
		billImage.setIcon(new ImageIcon("pic/processing.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(404, 227, 200, 200);
		this.showJpanel.add(billImage);

		JLabel label = new JLabel("交易正在进行中，请稍后...");
		label.setHorizontalAlignment(0);
		label.setForeground(Color.decode("#412174"));
		label.setFont(new Font("微软雅黑", Font.BOLD, 40));
		label.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(label);
		
		/* 倒计时进入接口调用 */
		checkTimer = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				checkTimer.stop();
				openLed();//打开打印机LED灯    
				print(transBean);

			}
		});
		checkTimer.start();
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);
				excuteVoice("voice/transpro.wav");
			}
		});
		voiceTimer.start();

	}
	

	/**
	 * 业务处理
	 * 
	 * @return
	 */
	public void print(BillPrintBean transBean){
		
		
		try {
			//存单打印机状态检测
			PrintMachine printMachine = new PrintMachine();
 			PrintMachineCheckStateBean printMachineCheckStatus = printMachine.printMachineCheckStatus();
 			String id = printMachineCheckStatus.getId();
 			String printStatus = printMachineCheckStatus.getStatus();
 			String status1 = printMachineCheckStatus.getStatus1();//8纸将尽
 			String status2 = printMachineCheckStatus.getStatus2();//7卡纸 
 			String status3 = printMachineCheckStatus.getStatus3();//6切刀错误 
 			logger.info(printMachineCheckStatus);
 			logger.info("返回状态值"+id);
 			logger.info("返回状态值对应信息"+printStatus);
 			
 			if("0".equals(id)){
 				logger.info("存单打印机状态：正常");
 				GlobalParameter.printStatus = "0";
 				if("7".equals(status2)){
 					logger.error("存单打印机状态：卡纸");
 					return;
 				}
 				if("6".equals(status3)){
 					logger.error("存单打印机状态：切刀错误");
 					serverStop("请联系大堂经理","","存单打印机状态：切刀错误");
 					return;
 				}
 			}else if("4".equals(id)){
 				logger.error("存单打印机错误：找不到打印机");
 				serverStop("请联系大堂经理","","存单打印机错误：找不到打印机");
					return;
 			}else {
 				logger.error("存单打印机错误：未知异常");
 				serverStop("请联系大堂经理","","存单打印机错误：未知异常");
				return;
 			}
		}catch (Exception e) {
 				logger.error(e);
 				serverStop("请联系大堂经理","","存单打印机错误：未知异常");
				return;
 		}
		try{
			PrintBill print = new PrintBill();
			//取银行卡
			ICBankBean icBankCard = new  ICBankBean();
			for (ICBankBean icBankBean : bankBeans) {
				if(transBean.getAccNo().equals(icBankBean.getICAccNo())){
					icBankCard = icBankBean;
				}
			}
			//未选中存单
			if(transBean.getSelect().size()==0){
				openMistakeDialog("请选择要打印的存单!");
				return;
			}
	   
			//循环打印存单
			for (Object select2 : transBean.getSelect()){
				//调用查询凭证号接口
				if(checkCertNo(transBean)==false){
					if("1".equals(transBean.getIsEnought())){
						mistakeDialog = new MistakeDialog(MainFrame.mainFrame,
								true, "凭证号不足,请联系大堂经理！");
						mistakeDialog.showDialog();
					}
					return;
				}
				logger.info("当前凭证号："+transBean.getCertNoAdd());
				//调用存单打印接口
				Map<String,String> map = new HashMap<String, String>();
				map.put("acctNo", transBean.getAccNo());//卡号
				map.put("subAcctNo", (String) select2);//子账号
				map.put("certNoAdd", transBean.getCertNoAdd());//凭证号
				transBean.getReqMCM001().setReqBefor("03514");
				BCK0016ResBean bck0016 = BCK0016(map);
				if(bck0016 == null){
					transBean.getReqMCM001().setIntereturnmsg("打印存单03514接口调用失败");
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
					serverStop("请联系大堂经理!","", "打印存单调用失败");
					return;
				}
				String resCode = bck0016.getHeadBean().getResCode();
				String resMsg = bck0016.getHeadBean().getResMsg();
				transBean.getReqMCM001().setReqAfter(resCode, resMsg);
				if(!"000000".equals(resCode)){
					if("44444".equals(resCode)){
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop("请联系大堂经理", "",resMsg );
					}else{
						logger.error("错误信息："+resMsg);
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop("请联系大堂经理", resMsg,"" );
						return;
					}
				}
				transBean.getReqMCM001().setIntereturncode(resCode);
				transBean.getReqMCM001().setIntereturnmsg(resMsg);
				transBean.getReqMCM001().setCentertrjnno(bck0016.getBody().getSVR_JRNL_NO().trim());
				transBean.getReqMCM001().setAccount(transBean.getAccNo()+"-"+(String) select2);
				transBean.getReqMCM001().setCustomername(bck0016.getBody().getACCT_NAME().trim());
				transBean.getReqMCM001().setTransamt(bck0016.getBody().getBALANCE().trim());
				transBean.getReqMCM001().setUsevouchertype("102");
				transBean.getReqMCM001().setUsevoucherno(bck0016.getBody().getCERT_NO().trim());
				
				//获取打印存单接口：前置返回的body实体bean
				BCK0016ResBodyBean bck0016ResBodyBean = bck0016.getBody();
				transBean.getSelect4().add((String)bck0016ResBodyBean.getSVR_JRNL_NO());
				
				//调用更新凭证号接口
				Map<String, String> map2 = new HashMap<String, String>();
				map2.put("isNo", "");
				map2.put("id", transBean.getCertNoId());
				map2.put("startNo", transBean.getCertStartNo());
				map2.put("endNo", transBean.getCertEndNo());
				long no=Long.valueOf(transBean.getCertNoAdd())+1;
				map2.put("nowNo",String.format("%08d", no) );
				transBean.setCertNoAdd(String.format("%08d", no));
				map2.put("createDate",transBean.getCreatTime());
				map2.put("updateDate", DateUtil.getNowDate("yyyyMMddHHmmss")); 
				Tms0005ResBean tms0006 = Tms0006(map2);
				if(null == tms0006 ){
					serverStop("请联系大堂经理!", "","更新凭证号失败");
					return;
				}
				resCode = tms0006.getHeadBean().getResCode();
				resMsg = tms0006.getHeadBean().getResMsg();
				if(!"000000".equals(resCode)){
					serverStop("请联系大堂经理!","",resMsg);
					return;
				}

				ICSubAccNo icSubAccNo = new ICSubAccNo();
				icSubAccNo.setSubAccNo((String)select2);
				icSubAccNo.setATM(bck0016ResBodyBean.getBALANCE().trim());
				icSubAccNo.setProductCode(bck0016ResBodyBean.getPRO_CODE().trim());
				icSubAccNo.setProductName(bck0016ResBodyBean.getPROD_NAME().trim());
				icSubAccNo.setOpenRate(bck0016ResBodyBean.getOPEN_RATE().trim());
				icSubAccNo.setDepTerm(bck0016ResBodyBean.getDEP_TERM().trim());
			    //开户日期转换格式
				icSubAccNo.setOpenDate(bck0016ResBodyBean.getOPEN_DATE());
				//起息日期转换格式
				icSubAccNo.setStartIntDate(bck0016ResBodyBean.getSTART_DATE());
				//到期日期转换格式
				icSubAccNo.setEndIntDate(bck0016ResBodyBean.getEND_DATE());
				icSubAccNo.setFserialno(bck0016ResBodyBean.getDATE()+bck0016ResBodyBean.getSVR_JRNL_NO());
				//如意+产品存期转换成    （天）
				if(bck0016ResBodyBean.getPRO_CODE().trim().startsWith("RJ")){
					String remotePath = bck0016ResBodyBean.getOPEN_DATE();
					logger.info("开户时间"+remotePath);
					
					int deTp = DateTool.nDaysBetweenTwoDate(remotePath, bck0016ResBodyBean.getEND_DATE());
					icSubAccNo.setDepTerm(String.valueOf(deTp));
				}
				//整存整取
				if(("0010").equals(bck0016ResBodyBean.getPRO_CODE().trim())){
					if(("0").equals(bck0016ResBodyBean.getEXCH_FLAG().trim())){
						icSubAccNo.setExchFlag("非自动转存  本行提示:非到期日支取的,请提供存款人有效身份证件。");
					}else if("1".equals(bck0016ResBodyBean.getEXCH_FLAG().trim()) || "2".equals(bck0016ResBodyBean.getEXCH_FLAG().trim())){
						icSubAccNo.setExchFlag("自动转存   本行提示:存款到期已自动转存,非到期日支取的,请提供存款人有效身份证件。");
					}
				}
				//约享存
				if(bck0016ResBodyBean.getPRO_CODE().trim().startsWith("Y")){
					//产品利率信息查询-
					Map<String, String> searchProducRateInfoMap=new HashMap<String, String>();
					searchProducRateInfoMap.put("PROD_CODE", bck0016ResBodyBean.getPRO_CODE());	//产品代码
					searchProducRateInfoMap.put("RATE_DATE", bck0016ResBodyBean.getSTART_DATE());//起息日
					searchProducRateInfoMap.put("FLOAT_FLAG","1");//非必输1-浮动，非1-不浮动
					searchProducRateInfoMap.put("CHL_ID","");//渠道模块标识
					searchProducRateInfoMap.put("CUST_NO", "");
					searchProducRateInfoMap.put("ACCT_NO", transBean.getAccNo());//账号
					searchProducRateInfoMap.put("SUB_ACCT_NO", (String)select2);//子账号
					transBean.getReqMCM001().setReqBefor("02864");
					BCK0020ResBean bck0020 = BCK0020(searchProducRateInfoMap);
					if(bck0020 == null){
						transBean.getReqMCM001().setTransresult("2");
						transBean.getReqMCM001().setIntereturnmsg("存单查询利率02864接口调用失败");
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop("请联系大堂经理","", "约享存存单查询利率失败");
						return;
					}
					resCode = bck0020.getHeadBean().getResCode();
					resMsg = bck0020.getHeadBean().getResMsg();
					transBean.getReqMCM001().setReqAfter(resCode, resMsg);
					if(!"000000".equals(resCode)){
						if("44444".equals(resCode)){
							transBean.getReqMCM001().setTransresult("2");
							MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
							serverStop("请联系大堂经理","",resMsg);
						}else{
							transBean.getReqMCM001().setTransresult("2");
							MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
							serverStop("请联系大堂经理",resMsg,"");
						}
						return;
					}
					//下载产品利率信息查询接口中的 文件
					String fileName = bck0020.getBody().getFILE_NAME();
					// 下载文件
					FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
					List<SearchProducRateInfo> productInfos = TextFileReader.paddingList(Property.FTP_LOCAL_PATH+fileName,SearchProducRateInfo.class);
					float floatRet=getFloatRet(bck0016ResBodyBean.getAREA_FLOAT_RET()
							,bck0016ResBodyBean.getCHL_FLOAT_RET()
							,bck0016ResBodyBean.getBIRTH_FLOAT_RET()
							,bck0016ResBodyBean.getTIME_FLOAT_RET(),bck0016ResBodyBean.getCOMB_FLOT_RET());
					StringBuffer str=new StringBuffer();
					
					for (int j = 0; j < productInfos.size(); j++)
					{
						SearchProducRateInfo producRateInfo=productInfos.get(j);
						if(producRateInfo.getDrawMonth()==null||producRateInfo.getDrawMonth().trim().length()==0){
							continue;
						}
						producRateInfo.setInteDate(bck0016ResBodyBean.getOPEN_DATE());
						StringBuffer l51Str;
						if("1".equals(GlobalParameter.printRateStatus)){
							l51Str=producRateInfo.getL51Str2();
						}else{
							l51Str=producRateInfo.getL51Str(floatRet);
						}
						if(l51Str.toString().trim().length()!=0){
							if(str.toString().length()==0){
								str.append("提前支取选择期：").append(l51Str);
							}else{
								str.append("；").append(l51Str);
							}
						}
					}
					icSubAccNo.setPrinterL51Str(str.toString()+"（详见协议）");
				}
				
				//安享存
				if(bck0016ResBodyBean.getPRO_CODE().trim().startsWith("A")){
					//产品利率信息查询-
					Map<String, String> searchProducRateInfoMap=new HashMap<String, String>();
					searchProducRateInfoMap.put("PROD_CODE", bck0016ResBodyBean.getPRO_CODE());	//产品代码
					searchProducRateInfoMap.put("RATE_DATE", bck0016ResBodyBean.getSTART_DATE());//起息日
					searchProducRateInfoMap.put("FLOAT_FLAG","1");//非必输1-浮动，非1-不浮动
					searchProducRateInfoMap.put("CHL_ID","");//渠道模块标识
					searchProducRateInfoMap.put("CUST_NO", "");
					searchProducRateInfoMap.put("ACCT_NO", transBean.getAccNo());
					searchProducRateInfoMap.put("SUB_ACCT_NO", (String)select2);
					transBean.getReqMCM001().setReqBefor("02864");
					BCK0020ResBean bck0020 = BCK0020(searchProducRateInfoMap);
					if(bck0020 == null){
						transBean.getReqMCM001().setTransresult("2");
						transBean.getReqMCM001().setIntereturnmsg("存单查询利率02864接口调用失败");
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop("请联系大堂经理!","", "安享存存单查询利率失败");
						return;
					}
					resCode = bck0020.getHeadBean().getResCode();
					resMsg = bck0020.getHeadBean().getResMsg();
					transBean.getReqMCM001().setReqAfter(resCode, resMsg);
					if(!"000000".equals(resCode)){
						if("44444".equals(resCode)){
							transBean.getReqMCM001().setTransresult("2");
							MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
							serverStop("请联系大堂经理","",resMsg);
						}else{
							transBean.getReqMCM001().setTransresult("2");
							MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
							serverStop("请联系大堂经理",resMsg,"");
						}
						return;
					}
					//下载产品利率信息查询接口中的 文件
					String fileName = bck0020.getBody().getFILE_NAME();
					// 下载文件
					FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
					List<SearchProducRateInfo> productInfos = TextFileReader.paddingList(Property.FTP_LOCAL_PATH+fileName,SearchProducRateInfo.class);
					float floatRet=getFloatRet(bck0016ResBodyBean.getAREA_FLOAT_RET()
							,bck0016ResBodyBean.getCHL_FLOAT_RET()
							,bck0016ResBodyBean.getBIRTH_FLOAT_RET()
							,bck0016ResBodyBean.getTIME_FLOAT_RET(),bck0016ResBodyBean.getCOMB_FLOT_RET());
					StringBuffer str=new StringBuffer();
					String floats = bck0020.getBody().getFLOAT_SUM();
					String s = "";
					BigDecimal b1 = new BigDecimal(floats.trim());
					for (int j = 0; j < productInfos.size(); j++)
					{
						SearchProducRateInfo producRateInfo=productInfos.get(j);
						String intUppercaseXYCK = MoneyUtils
								.intUppercaseXYCK(producRateInfo.getSavingCount());
						BigDecimal b2 = new BigDecimal(producRateInfo.getRate().trim());
						BigDecimal add = b1.add(b2);
						String rate = add + "%";
						s = s + intUppercaseXYCK + rate + ";";
						
					}
					if (s.toString().trim().length() != 0) {
						s = "结息利率提示：" + s;
						s = s.substring(0, s.length() - 1);
						s = s.replaceAll("年", "期");
					}
					if("1".equals(GlobalParameter.printRateStatus)){
						icSubAccNo.setPrinterL51Str("");
					}else{
						icSubAccNo.setPrinterL51Str(s.trim());
					}
				}
				
				//聚享存
				if(bck0016ResBodyBean.getPRO_CODE().trim().startsWith("F")){
					//产品利率信息查询-
					Map<String, String> searchProducRateInfoMap=new HashMap<String, String>();
					searchProducRateInfoMap.put("PROD_CODE", bck0016ResBodyBean.getPRO_CODE());	//产品代码
					searchProducRateInfoMap.put("RATE_DATE", bck0016ResBodyBean.getSTART_DATE());//起息日
					searchProducRateInfoMap.put("FLOAT_FLAG","1");//非必输1-浮动，非1-不浮动
					searchProducRateInfoMap.put("CHL_ID","");//渠道模块标识
					searchProducRateInfoMap.put("CUST_NO", "");
					searchProducRateInfoMap.put("ACCT_NO", transBean.getAccNo());//账号
					searchProducRateInfoMap.put("SUB_ACCT_NO", (String)select2);//子账号
					transBean.getReqMCM001().setReqBefor("02864");
					BCK0020ResBean bck0020 = BCK0020(searchProducRateInfoMap);
					if(bck0020 == null){
						transBean.getReqMCM001().setTransresult("2");
						transBean.getReqMCM001().setIntereturnmsg("存单查询利率02864接口调用失败");
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop("请联系大堂经理!","", "，聚享存存单查询利率失败");
						return;
					}
					resCode = bck0020.getHeadBean().getResCode();
					resMsg = bck0020.getHeadBean().getResMsg();
					transBean.getReqMCM001().setReqAfter(resCode, resMsg);
					if(!"000000".equals(resCode)){
						if("44444".equals(resCode)){
							transBean.getReqMCM001().setTransresult("2");
							MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
							serverStop("请联系大堂经理","",resMsg);
						}else{
							transBean.getReqMCM001().setTransresult("2");
							MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
							serverStop("请联系大堂经理",resMsg,"");
						}
						return;
					}
					//下载产品利率信息查询接口中的 文件
					String fileName = bck0020.getBody().getFILE_NAME();
					// 下载文件
					FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
					List<SearchProducRateInfo> productInfos = TextFileReader.paddingList(Property.FTP_LOCAL_PATH+fileName,SearchProducRateInfo.class);
					float floatRet=getFloatRet(bck0016ResBodyBean.getAREA_FLOAT_RET()
							,bck0016ResBodyBean.getCHL_FLOAT_RET()
							,bck0016ResBodyBean.getBIRTH_FLOAT_RET()
							,bck0016ResBodyBean.getTIME_FLOAT_RET(),bck0016ResBodyBean.getCOMB_FLOT_RET());
					String s = "";
					
					SearchProducRateInfo producRateInfo=productInfos.get(0);
					//获得存单的封闭期	
					String saveDate = producRateInfo.getSavingCount();//存期
					String closeDate="";
					if(saveDate.indexOf("D")!=-1){
						 closeDate = saveDate.substring(0,saveDate.indexOf("D")+1).toUpperCase();
					}else{
						closeDate = saveDate.substring(0,3).toUpperCase();
					}
					String rate = producRateInfo.getRate();//封闭期利率
					double b = Double.parseDouble(rate);
					BigDecimal a1 = new BigDecimal(floatRet);
					BigDecimal b2 = new BigDecimal(b);
					DecimalFormat df1 = new DecimalFormat("0.0000");
					String rate2 = df1.format(a1.add(b2).doubleValue());
					String startDate = bck0016ResBodyBean.getSTART_DATE();
					String startDate_1 = startDate.substring(0,4);
					String startDate_2 = startDate.substring(4,6);
					String startDate_3 = startDate.substring(6,8);
					String startDa = startDate_1+"-"+startDate_2+"-"+startDate_3;
					int in = Integer.parseInt(closeDate.replace("D", ""))-1;
					DateTool dt = new DateTool();//处理日期工具类
					Date date = DateTool.stringToDate(startDa,"yyyy-MM-dd");
					if(closeDate.indexOf("D")!=-1){
						if("1".equals(GlobalParameter.printRateStatus)){
							s = "锁定期："+startDate+"-"+DateTool.nDaysAfterOneDateString(startDate,in).replace("-", "");
						}else{
							s = "锁定期："+startDate+"-"+DateTool.nDaysAfterOneDateString(startDate,in).replace("-", "")+",锁定期利率："+rate2+"%(详见协议)";
						}
					}else if(closeDate.indexOf("M")!=-1){
						if("1".equals(GlobalParameter.printRateStatus)){
							s = "锁定期："+startDate+"-"+(dt.nMonthsAfterOneDateString(date,Integer.parseInt(closeDate.replace("M", ""))).replace("-", ""));
						}else{
							s = "锁定期："+startDate+"-"+(dt.nMonthsAfterOneDateString(date,Integer.parseInt(closeDate.replace("M", ""))).replace("-", ""))+",锁定期利率："+rate2+"%(详见协议)";
						}
					}
						
					if(s.toString().trim().length()!=0){
						s = "提示："+s;
						s.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
					}

					icSubAccNo.setPrinterL51Str(s.trim());
				}
				
				boolean printBills = print.PrintBills(transBean, icSubAccNo,bck0016ResBodyBean,icBankCard.getType());
				if(!printBills){
					transBean.getReqMCM001().setIntereturnmsg("打印存单失败");
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
					serverStop("请联系大堂经理", "", "存单打印失败");
					return;
				}
				
				transBean.getReqMCM001().setTransresult("0");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				//睡眠2秒等待上送交易流水
				Thread.sleep(2000);
				//清空交易流水上送字段
				transBean.getReqMCM001().setReqBefor("");
				transBean.getReqMCM001().setReqAfter("", "");
				transBean.getReqMCM001().setIntereturncode("");
				transBean.getReqMCM001().setIntereturnmsg("");
				transBean.getReqMCM001().setCentertrjnno("");
				transBean.getReqMCM001().setAccount("");
				transBean.getReqMCM001().setCustomername("");
				transBean.getReqMCM001().setTransamt("");
				transBean.getReqMCM001().setUsevouchertype("");
				transBean.getReqMCM001().setUsevoucherno("");
				transBean.getReqMCM001().setTransresult("");
				}
			
					
		Thread.sleep(3000);
		openPanel(new TransPrintSuccessPanel(transBean));
				
		} catch (Exception e) {
			logger.error("存单打印失败"+e);
			transBean.getReqMCM001().setIntereturnmsg("打印存单失败");
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
			serverStop("请联系大堂经理", "", "存单打印失败");
			return;
		}
		try {
			as.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
	//事后监管
	public boolean ftpupLoad(BillPrintBean transBean,BCK0016ResBodyBean bck0016ResBodyBean,ICSubAccNo icSubAccNo) {
		Map<String, String> map = new HashMap<String, String>();
		String flag = transBean.getImportMap().get("agent");
		if ("true".equals(flag)) {
			map.put("agentFlag", "1");// 1-存在代理人 2-不存在代理人
		} else {
			map.put("agentFlag", "2");
		}
		map.put("svrJrnlNo", bck0016ResBodyBean.getSVR_JRNL_NO());// 销户核心流水号
		map.put("proName", bck0016ResBodyBean.getPROD_NAME());// 产品名称
		map.put("accName", icSubAccNo.getCustName());// 户名
		map.put("branchNo", GlobalParameter.branchNo);// 机构号
		map.put("accNo", transBean.getAccNo()+"-"+icSubAccNo.getSubAccNo());// 账号
		map.put("billNo", bck0016ResBodyBean.getCERT_NO());// 凭证号
		map.put("macNo", GlobalParameter.machineNo);// 终端号
		map.put("idCardName", transBean.getIdCardName());// 本人姓名
		map.put("idCardNo", transBean.getReadIdcard());// 本人身份证号
		map.put("qfjg", transBean.getQfjg());// 签发机关
		map.put("teller", GlobalParameter.tellerNo);// 操作员
		map.put("busType", "存单打印");// 业务类型
		map.put("transType", "存单打印");// 业务类型
		map.put("agentIdCardName", transBean.getAgentIdCardName());// 代理人姓名
		map.put("agentIdCardNo", transBean.getAgentIdCardNo());// 代理人卡号
		map.put("agentqfjg", transBean.getAgentqfjg());// 代理人签发机关
		map.put("bill_face", transBean.getBillPath_fc());
		logger.info("生成事后监督内容："+map);
		boolean isupload = true;// 标记是否上传成功
		JpgUtils cg = new JpgUtils();
		String filePath = "";
		try {
			filePath = cg.graphicsGeneration(map);
		} catch (IOException e2) {
			logger.error("事后监管程序，生成事后监管图片异常！"+ e2);
			return false;
		}
		SFTPUtil sf = new SFTPUtil();
		ChannelSftp sftp = null;
		Session sshSession = null;
    	JSch jsch = new JSch();
    	try {
    		//连接SFTP
    		sshSession = jsch.getSession(Property.FTP_USER, Property.FTP_IP, Integer.parseInt(Property.FTP_PORT));
    		logger.info("Session created.");
    		sshSession.setPassword(Property.FTP_PWD);
    		Properties sshConfig = new Properties();
    		sshConfig.put("StrictHostKeyChecking", "no");
    		sshSession.setConfig(sshConfig);
    		sshSession.connect();
    		logger.info("Session connected.");
    		logger.info("Opening Channel.");
    		Channel channel = sshSession.openChannel("sftp");
    		channel.connect();
    		sftp = (ChannelSftp) channel;
    		logger.info("Connected to " + Property.FTP_IP + ".");
    		
    		String nowDate = DateUtil.getNowDate("yyyyMMdd");
    		// 上传的目录
    		String ftpPath = Property.FTP_SER_PATH + nowDate;
    		String[] ftpList = ftpPath.split("/");
    		String paths = "";
    		for (String path : ftpList) {
    			if(StringUtils.isNotBlank(path)){
    				paths += "/" + path;
    				try {
            			Vector content = sftp.ls(paths);
            			if (content == null) {
            				sftp.mkdir(paths);
            			}
    				} catch (Exception e) {
    					sftp.mkdir(paths);
    				}
    				sftp.cd(paths);
    			}
    		}
    		File file = new File(filePath);
    		boolean result = sf.upload(ftpPath, filePath, sftp);
    		if(!result){
    			logger.error("事后监管上传图片失败-->" + file.getName());
    			isupload = false;
    		}else{
    			logger.info("事后监管上传图片成功-->" + file.getName());
    			file.delete();
    		}
    		
		} catch (Exception e) {
			logger.error("事后监督上传图片，进入目录失败");
			isupload = false;
		}finally{
			if (sftp!= null && sftp.isConnected()){
				sftp.disconnect();
			}
			if (sshSession!= null && sshSession.isConnected()){
				sshSession.disconnect();
			}
		}
		return true;
    	
	}
	
	private float getFloatRet(String area,String chl,String birth,String time,String comb){
		return toFloat(area)+toFloat(chl)+toFloat(birth)+toFloat(time)+toFloat(comb);
	}
	private float toFloat(String f){
		if(f!=null){
			try
			{
				f=f.trim();
				return Float.parseFloat(f);
			}
			catch (Exception e)
			{
				logger.error(e);
			}
		}
		return 0.000f;
	}
	
	
	/*凭证信息修改*/
	public Tms0005ResBean Tms0006(Map<String,String> map){
		SocketClient sc = new SocketClient();
		Socket socket = null;
		try {
			socket = sc.createSocket();
			// 发送请求
			sc.sendRequest(socket,sc.Tms0006(map));
			// 响应
			String retMsg = sc.response(socket);
			
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", Tms0005ResBean.class);
			reqXs.alias("Head", InResBean.class);
			reqXs.alias("Body", Tms0005ResBodyBean.class);
			Tms0005ResBean tms0005ResBean = (Tms0005ResBean)reqXs.fromXML(retMsg);
			logger.info(tms0005ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
			return tms0005ResBean;
		} catch (UnknownHostException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			sc.closeSocket(socket);
		}
		return null;
	}
	
	/*存单打印接口*/
	public BCK0016ResBean BCK0016(Map<String,String> map){
		SocketClient sc = new SocketClient();
		Socket socket = null;
		try {
			socket = sc.createSocket();
			// 发送请求
			sc.sendRequest(socket,sc.BCK_0016(map));
			// 响应
			String retMsg = sc.response(socket);
			
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK0016ResBean.class);
			reqXs.alias("Head", InResBean.class);
			reqXs.alias("Body", BCK0016ResBodyBean.class);
			BCK0016ResBean bck0016ResBean = (BCK0016ResBean)reqXs.fromXML(retMsg);
			logger.info(bck0016ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
			return bck0016ResBean;
			
		} catch (UnknownHostException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			sc.closeSocket(socket);
		}
		return null;
	}
	
	public BCK0020ResBean BCK0020(Map<String,String> map)  {
		String retMsg = "";
		SocketClient sc = new SocketClient();
		Socket socket=null;
		InputStream is=null;
		OutputStream os=null;
		try {
			 socket = sc.createSocket();
            //构建IO  
             is = socket.getInputStream();  
             os = socket.getOutputStream();    
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
            //向服务器端发送一条消息  
            bw.write(sc.BCK_0020(map) + "\n");  
            bw.flush();  
            //读取服务器返回的消息  
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
			
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)){
					break;
				}
			}
			logger.info(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK0020ResBean.class);
			BCK0020ResBean bck0020ResBean = (BCK0020ResBean)reqXs.fromXML(retMsg);
			logger.info(bck0020ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
			return bck0020ResBean;
		} catch (UnknownHostException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}finally{
			try {
				if(os!=null){
					os.close();
				}
				if(is!=null){
					is.close();
				}
				if(socket!=null){
					socket.close();
				}
			} catch (Exception e2) {
				logger.info(e2);
			}
		}
		return null;

	}
	
	/**打开led灯*/
	public void openLed(){
		LedStateBean openLed = new LedStateBean();
		try {
			openLed = MachineLed.openLed("3");
			logger.info("打印机Led灯打开返回值："+openLed);
		} catch (Exception e) {
			logger.error("打印机led灯通讯异常");
			return;
		}
		logger.info("打印机Led灯打开："+openLed.getStatus());
	}
	
	/**关闭led灯*/
	public void closeLed(){
		LedStateBean closeLed = new LedStateBean();
		try {
			closeLed = MachineLed.closeLed("3");
			logger.info("打印机Led灯关闭返回值："+closeLed);
		} catch (Exception e) {
			logger.error("打印机led灯通讯异常");
			return;
		}
		logger.info("打印机Led灯关闭："+closeLed.getStatus());
	}
	
}