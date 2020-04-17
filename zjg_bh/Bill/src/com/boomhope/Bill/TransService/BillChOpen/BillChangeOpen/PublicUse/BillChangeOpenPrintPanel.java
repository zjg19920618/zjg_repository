package com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.PublicUse;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Comm.HKPrintBill;
import com.boomhope.Bill.Comm.HKPrintPt;
import com.boomhope.Bill.Framework.BaseLoginFrame;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.BillChOpen.Bean.PublicBillChangeOpenBean;
import com.boomhope.Bill.TransService.BillChOpen.Bean.SearchProducRateInfo;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc.BillChangeOpenSuccessPanel;
import com.boomhope.Bill.TransService.BillChOpen.Interface.IntefaceSendMsg;
import com.boomhope.Bill.Util.DateTool;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.MoneyUtils;
import com.boomhope.Bill.Util.NumberZH;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 存单换开打印页面
 * 
 * @author 
 * 
 */
public class BillChangeOpenPrintPanel extends BaseTransPanelNew {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(BillChangeOpenPrintPanel.class);
	JLabel depoLum=null;
	JLabel label=null;
	JLabel label2=null;
	private boolean shjdBiao = true;//事后标识
	private boolean dayiShibai = true;//调用存单打印接口失败标识
	private boolean on_off=true;
	
	public BillChangeOpenPrintPanel() {
		logger.info("进入存单换开成功后，打印页面");
		
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);//关语音
				closeVoice();//关语音流
				excuteVoice("voice/zzdycd.wav");
				
			}
		});
		voiceTimer.start();
		
		
		this.showTimeText(delaySecondMaxTime);
		delayTimer = new Timer(delaySecondMaxTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {
            	logger.info("存单换开成功倒计时结束 ");
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 

		// 标题
	    depoLum = new JLabel("交易成功,正在打印存单,请稍候......");
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setBounds(0, 220, 1009, 60);
		this.showJpanel.add(depoLum);
		
		label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		label.setForeground(Color.decode("#333333"));
		label.setBounds(0, 200, 1009, 30);
		label.setVisible(false);
		this.showJpanel.add(label);
		
		label2 = new JLabel();
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		label2.setForeground(Color.decode("#ff0000"));
		label2.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		label2.setBounds(0, GlobalParameter.TRANS_HEIGHT - 200, 1009, 30);
		this.showJpanel.add(label2);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
			
				prossDialog.showDialog();
				//存单换开存期处理
				monthsChange();
				
				if(!jslx()){
					return;
				}
				//回收存单
				try {
					sleep(1000);
					hdProcess();
				} catch (Exception e1) {
					logger.error("回收存单失败"+e1);
				}
				//存单开关关闭
				GlobalParameter.ACC_STATUS = false;//已无存单
				
				
				
				// 打印存单,提示信息  
				logger.info("打印存单开始" + bcOpenBean);
				label.setText("正在打印存单，请耐心等待...");
				label2.setText("请检查印章是否打印完整，若有异议请联系大堂经理");
				label.setVisible(true);
				
				
				//打开led灯
				try {
					openLed("3");
				} catch (Exception e1) {
					logger.error("打开Led灯异常" + e1);
				}
				
				// 调存单打印机打印
				if(!printBill()){
					// 更新凭证
					updateBill();
					shjdBiao = false;
					//关闭led灯
					try {
						closeLed("3");
					} catch (Exception e1) {
						logger.error("关闭Led灯异常" + e1);
					}	
					return;
				};
				
				//关闭led灯
				try {
					closeLed("3");
				} catch (Exception e1) {
					logger.error("关闭Led灯异常" + e1);
				}	
				
				// 更新凭证
				updateBill();
				//调用事后监督
				upLoad();
				
				repaint();
				logger.info("打印存单结束");
				label.setText("存单打印成功");
				
				prossDialog.disposeDialog();//关闭弹框
				
				bcOpenBean.getReqMCM001().setCentertrjnno(bcOpenBean.getHKSvrJrnlNo());
				bcOpenBean.getReqMCM001().setAccount(bcOpenBean.getAccNo());
				bcOpenBean.getReqMCM001().setCustomername(bcOpenBean.getAccName());
				bcOpenBean.getReqMCM001().setTransamt(bcOpenBean.getTotalAmt());
				bcOpenBean.getReqMCM001().setRvouchertype("102");
				bcOpenBean.getReqMCM001().setRvoucherno(bcOpenBean.getCertNo());
				bcOpenBean.getReqMCM001().setUsevouchertype("102");
				bcOpenBean.getReqMCM001().setUsevoucherno(bcOpenBean.getNowNo());
				bcOpenBean.getReqMCM001().setTransresult("0");
				MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
				
				//是否打印凭条
				isPrintPt(bcOpenBean);
			}
		}).start();
	}
	
	/**
	 * 调用存单回收模块
	 */
	public void hdProcess()throws Exception{
		// 回收存单
		logger.debug("回收存单:BH_Store");
		Dispatch.call(BaseLoginFrame.dispath, "BH_Store",new Variant("1"));
		//清理资源
		Dispatch.call(BaseLoginFrame.dispath, "BH_CleanResource");
		logger.debug("睡200ms后开始执行退票到回收箱：BH_Retract");
		sleep(200);
		//退票到回收箱
		Dispatch.call(BaseLoginFrame.dispath, "BH_Retract");
	}
	

	/**存单换开存期转换*/
	public void monthsChange(){
		logger.info("存单换开存期转换");
		try{
			// 将金额转换为大写
			bcOpenBean.setMoneyUpper(NumberZH.change(Double.parseDouble(String
					.valueOf(bcOpenBean.getEndAmt()))));
			if(bcOpenBean.getProCode().startsWith("JX") || bcOpenBean.getProCode().startsWith("RJ")){
				/* 存期时间 */
				int date = 0;

				String openTime = bcOpenBean.getOpenDate().replaceAll("/","").trim();
				String endTime = bcOpenBean.getEndDate().replaceAll("/","").trim();
				
				SimpleDateFormat fommter = new SimpleDateFormat("yyyyMMdd");
				
				Date a1 = fommter.parse(openTime);
				Date b1 = fommter.parse(endTime);
				System.out.println(a1);
				System.out.println(b1);
				date = DateTool.differentsDays(a1, b1);
				bcOpenBean.setJxRyjDepTem(String.valueOf(date));//积享存、如意存存期(天数)
			}else{
				//将存期的数字变为中文
				String months="";
				// 当存期的单位是月时加“个”如：3个月，单位是年时则没有如1年
				String measure = "";
				if(bcOpenBean.getMonthsUpper().contains("M") || bcOpenBean.getMonthsUpper().contains("Y")){
					if("01".equals(bcOpenBean.getMonthsUpper().substring(0, 2))){
						months="一";
					}else if("02".equals(bcOpenBean.getMonthsUpper().substring(0, 2))){
						months="二";
					}else if("03".equals(bcOpenBean.getMonthsUpper().substring(0, 2))){
						months="三";
					}else if("05".equals(bcOpenBean.getMonthsUpper().substring(0, 2))){
						months="五";
					}else if("06".equals(bcOpenBean.getMonthsUpper().substring(0, 2))){
						months="六";
					}else{
						months=bcOpenBean.getMonthsUpper().substring(0, 2);
					}
					
					if (bcOpenBean.getMonthsUpper().indexOf("M") != -1) {
						measure = "个月";
					} else if (bcOpenBean.getMonthsUpper().indexOf("Y") != -1) {
						measure = "年";
					}
					
				}else{
					
					months=bcOpenBean.getMonthsUpper();
					measure="天";
			   }
				bcOpenBean.setMonthsUpperStr(months + measure);
			}
		}catch(Exception e){
			logger.error("存单换开存期获取失败"+e);
		}
	}
		
	/** 更新凭证号 */
	public void updateBill() {
		logger.info("进入更新凭证号方法");
		try {
			Map<String, String> updateMap = new HashMap<String, String>();
			updateMap.put("nowNo", String.format("%08d",
					(Long.parseLong(bcOpenBean.getNowNo()) + 1)));// 更新当前凭证号
			updateMap.put("updateDate", DateUtil.getNowDate("yyyyMMddHHmmss"));
			updateMap.put("isNo", "");
			updateMap.put("id", bcOpenBean.getCertNoId());
			updateMap.put("startNo", bcOpenBean.getCertStartNo());
			updateMap.put("endNo", bcOpenBean.getCertEndNo());
			updateMap.put("createDate", bcOpenBean.getCreateTime());
			updateMap.put("updateDate", DateUtil.getNowDate("yyyyMMddHHmmss"));

			Map<String, String> tms0006 = IntefaceSendMsg.Tms0006(updateMap);
			if (!"000000".equals(tms0006.get("resCode"))) {// 凭证更新失败依然打印
				logger.error("更新凭证号失败：" + tms0006.get("errMsg"));
				openMistakeDialog("更新凭证号失败");
			}
		} catch (Exception e) {
			logger.error("更新凭证号失败：" + e);
			openMistakeDialog("更新凭证号失败");
		}
	}


	/** 调存单打印机 */
	public boolean printBill() {
		logger.info("进入调用存单打印机的方法");
		boolean isPrint = true;
		try {
			// 存单打印，获取存单换开返回信息
			HKPrintBill hkPrintBill = new HKPrintBill();
			hkPrintBill.PrintBill(bcOpenBean);
		} catch (Exception e) {
			logger.error("打印机打印存单失败：" + e);
			bcOpenBean.getReqMCM001().setIntereturnmsg("打印存单失败");
			MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
			serverStop("抱歉，您的存单没有打印成功","", "调用存单打印机失败");
			isPrint = false;
		}
		return isPrint;
	}

	/**
	 * 是否打印凭条
	 */
	private void isPrintPt(final PublicBillChangeOpenBean transBean) {
		
		logger.info("进入是否打印凭条的方法");
		
		openConfirmDialog("是否打印凭条 是：打印凭条，否：不打印凭条。");
		
		label.setVisible(false);
		label2.setVisible(false);
		depoLum.setText("正在为您打印凭条，请稍候......");
		
		confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择否不打印凭条的按钮");
				confirmDialog.disposeDialog();
				logger.info("选择不打印凭条");
				openPanel(new BillChangeOpenSuccessPanel());
			}

		});
		confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择是打印凭条的按钮");
				/* 退出 */
				confirmDialog.disposeDialog();
				
				voiceTimer = new Timer(voiceSecond, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						stopTimer(voiceTimer);//关语音
						closeVoice();//关语音流
						excuteVoice("voice/printPt.wav");
																
					}
				});
				voiceTimer.start();
				
				billPrint();
			}	
			
		});
	}
	
	//打印凭条
	public void billPrint(){
		
		logger.info("进入打印凭条的方法");
		try {
			openLed("0");
			logger.info("凭条Led灯打开");
		} catch (Exception e2) {
			logger.error("凭条Led灯打开失败");
		}
		try{
			HKPrintPt pt = new HKPrintPt();
			if(!pt.print(bcOpenBean)){
				openMistakeDialog(bcOpenBean.getBillMsg());
				mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(mistakeDialog);
					}
				});
			}
		
		}catch(Exception e3){
			logger.error("打印凭条失败"+e3);
			openMistakeDialog("凭条打印失败");
			mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					closeDialog(mistakeDialog);
				}
			});
			try {
				closeLed("0");
				logger.info("凭条Led灯关闭");
			} catch (Exception e1) {
				logger.info("凭条Led灯关闭");
			}
		}
		
		try {
			closeLed("0");
			logger.info("凭条Led灯关闭");
		} catch (Exception e1) {
			logger.info("凭条Led灯关闭");
		}
		//睡眠
		try {
			sleep(1000);
		} catch (Exception e) {
			logger.error("等待异常"+e);
		}
		logger.info("打印凭条结束");
		
		clearTimeText();
		stopTimer(voiceTimer);//关语音
		closeVoice();//关语音流
		openPanel(new BillChangeOpenSuccessPanel());
		
	}
	
	/**
	 * 调用事后监督
	 */
	public void upLoad(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				bcOpenAction.uploadPhoto();
			}
		}).start();
	}
	/**
	 * button监听事件：利息试算，产品预计利息(24小时)-03510s
	 */
	public boolean jslx(){
		
		logger.info("进入利息试算的方法");
		        //安享存                                                                                                                         //约享存                                                                                                         
				if(bcOpenBean.getProCode().startsWith("A") || bcOpenBean.getProCode().startsWith("Y")){
					Map inter02864 = new HashMap<String,Object>();
					try {
						bcOpenBean.getReqMCM001().setReqBefor("02864");
						inter02864 = IntefaceSendMsg.inter02864(bcOpenBean);
						bcOpenBean.getReqMCM001().setReqAfter((String)inter02864.get("resCode"), (String)inter02864.get("errMsg"));
						if ("4444".equals(inter02864.get("resCode"))) {
							logger.error(inter02864.get("errMsg"));
							bcOpenBean.getReqMCM001().setIntereturnmsg("调用产品利率查询接口失败（02864）");
							MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
							serverStop("查询产品利率失败，请联系大堂经理。", "", (String)inter02864.get("errMsg"));
							return false;
						}
						if(!"000000".equals(inter02864.get("resCode"))){
							logger.info("查询产品利率失败："+inter02864.get("errMsg"));
							bcOpenBean.getReqMCM001().setIntereturnmsg("调用产品利率查询接口失败（02864）");
							MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
							serverStop("查询产品利率失败，请联系大堂经理。", (String)inter02864.get("errMsg"), "");
							return false;
						}
					} catch (Exception e) {
						logger.error("查询产品利率失败(02864)："+e);
						bcOpenBean.getReqMCM001().setIntereturnmsg("调用产品利率查询接口异常（02864）");
						MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
						serverStop("查询产品利率失败，请联系大堂经理。", "", "调用产品利率查询接口异常（02864）");
						return false;
					}
					bcOpenBean.getReqMCM001().setIntereturncode((String)inter02864.get("resCode"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String)inter02864.get("errMsg"));
					
					Double startRate = null;//起始利率
					Double endRate = null;//起始利率
					List<SearchProducRateInfo> list = (List<SearchProducRateInfo>)inter02864.get("KEY_PRODUCT_RATES");
					DecimalFormat df = new DecimalFormat("#.0000");
					SearchProducRateInfo start = list.get(0);
					SearchProducRateInfo end = list.get(list.size()-1);
					startRate = Double.parseDouble(start.getRate())+Double.parseDouble(bcOpenBean.getFloatSum());
					endRate = Double.parseDouble(end.getRate())+Double.parseDouble(bcOpenBean.getFloatSum());
					
				if(bcOpenBean.getProCode().startsWith("A")){
					//获取安享存结息利率提示
					String floats = (String) inter02864.get("float");
					BigDecimal b1 = new BigDecimal(floats.trim());
					String s = "";
					for (int i = 0; i < list.size(); i++) {
						SearchProducRateInfo producRateInfo = list.get(i);
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
					
					bcOpenBean.setPrinterL52Str(s.trim());
				}else if(bcOpenBean.getProCode().startsWith("Y")){
					//获取约享存结息利率提示
					StringBuffer str=new StringBuffer();
					 str.append("提前支取选择期：");
					String floats = (String) inter02864.get("float");
					BigDecimal b1 = new BigDecimal(floats.trim());
					for(int j=0;j<list.size();j++){
						SearchProducRateInfo producRateInfo=list.get(j);
						if(producRateInfo.getDrawMonth()==null||producRateInfo.getDrawMonth().trim().length()==0){
							continue;
						}	
						BigDecimal b2 = new BigDecimal(producRateInfo.getRate().trim());
						BigDecimal add = b1.add(b2);
						String rate = add + "%";
						String st=new String();
					    st=producRateInfo.getBenDateStr()+"-"+producRateInfo.getEndDateStr()+","+"利率"+rate+";";
					    
					    str.append(st);			  
					}
					 
					 bcOpenBean.setPrinterL51Str(str.toString());
					
				}
		}
				return true;
				
	}
	
}
