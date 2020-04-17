package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.Bean.AccPublicBean;
import com.boomhope.Bill.TransService.AccOpen.Bean.PublicAccOpenBean;
import com.boomhope.Bill.TransService.AccOpen.Interface.IntefaceSendMsg;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccAgreementPagePanel;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTransFlow;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.Interface.InterfaceSendMsg;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.TransferBusinessDealPanel;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.IdCardMsg.TransferAuthorizationPanel;
import com.boomhope.Bill.Util.UtilVoice;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.peripheral.action.FingerPrint;
import com.boomhope.tms.peripheral.bean.FingerPrintGet;

/**
 * 指纹识别
 * @author gyw
 *
 */
public class AccAuthorFingerprintPanel extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(AccAuthorFingerprintPanel.class);
	private static final long serialVersionUID = 1L;
	final int voiceSecond = 500;
	JLabel fingerprint = null;
	UtilVoice utilVoice = null;
	SourceDataLine line = null;
	String zwValue = null;
	private Map map;
	/***
	 * 初始化
	 */
	public AccAuthorFingerprintPanel(final Map map) {
		logger.info("进入指纹识别页面");
		//将当前页面传入流程控制进行操作
		final Component comp=this;
		this.map = map;
		
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
            	excuteVoice("voice/fingerprint.wav");
            	
            }      
        });            
		voiceTimer.start(); 
		/* 显示时间倒计时 */
		if(delayTimer!=null){
			clearTimeText();
		}
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	logger.info("指纹页面倒计时结束");
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });  
		delayTimer.start(); 
		
		/* 标题信息 */
		JLabel t = new JLabel("请录入指纹:");
		t.setHorizontalAlignment(JLabel.CENTER);
		t.setFont(new Font("微软雅黑", Font.BOLD, 40));
		t.setBounds(0, 174, 1009, 40);
		this.showJpanel.add(t);
		
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/fingerprint.png"));
		billImage.setIconTextGap(6);
		billImage.setBounds(405, 300, 220, 200);
		this.showJpanel.add(billImage);
	
		fingerprint = new JLabel("指纹验证失败,请重新录入");
		fingerprint.setVisible(false);;
		fingerprint.setFont(new Font("微软雅黑", Font.BOLD, 20));
		fingerprint.setHorizontalAlignment(SwingConstants.CENTER);
		fingerprint.setForeground(Color.RED);
		fingerprint.setBounds(394, 557, 276, 20);
		this.showJpanel.add(fingerprint);
		
		openZw(comp);
	}
	
	/**
	 * 打开指纹
	 */
	public void openZw(final Component comp ){
		Thread thread  = new Thread("指纹线程"){
	        @Override
	        public void run(){
	        	try {
	        		
	        		logger.info("等待指纹录入.............");
	        		FingerPrint fp = new FingerPrint();
	        		FingerPrintGet fpg = fp.getFingerPrint("99");
	        		String status = fpg.getStatus();
	        		
	        		if("0".equals(status)){
	        			//成功指纹值
	        			zwValue = fpg.getFingerPrint();
	        			logger.info("指纹录入"+fpg.getStatusDesc());
	        			
	        		}else{
	        			// 其它错误
	        			logger.info("指纹录入失败"+fpg.getStatusDesc());
	        			if(fpg.getStatusDesc()!=null && !"".equals(fpg.getStatusDesc())){
	        				serverStop("指纹仪调用失败,请重新操作",fpg.getStatusDesc(),"");
	        			}else{
	        				serverStop("指纹仪调用失败,请重新操作","","");
	        			}
	        			return;
	        		}
	        		logger.info("指纹录入完成.............");
				} catch (Exception e) {
					logger.error("获取指纹异常"+e);
					serverStop("指纹仪调用失败,请重新操作","","");
					return;
				}
	        	checkAuthorNo(comp);
	        }
	    };
	    thread.start();
	}

	/**
	 * 调用口令授权
	 */
	private void checkAuthorNo(final Component comp){
		new Thread(){
			@Override
			public void run() {
				openProssDialog();
				
				AccPublicBean bean=new AccPublicBean();
				bean.setSupTellerNo(AccountTradeCodeAction.transBean.getSupTellerNo());
				bean.setFinPriVal(zwValue);
				
				try {
					logger.info("指纹校验开始");
					AccountTradeCodeAction.transBean.getReqMCM001().setReqBefor("07660");
					Map params=IntefaceSendMsg.inter07660(bean);
					AccountTradeCodeAction.transBean.getReqMCM001().setReqAfter((String)params.get("resCode"), (String)params.get("errMsg"));
					String resCode=(String)params.get("resCode");
					String errMsg=(String)params.get("errMsg");
					if(!InterfaceSendMsg.SUCCESS.equals(resCode)){
						
						// 指纹错误
						if("710099".equals(resCode)){
							logger.info("指纹校验失败："+errMsg);
							closeDialog(prossDialog);
							openConfirmDialog(errMsg+":是：请重新录入指纹。否：请重新录入柜员号。");
							
						}else if("700018".equals(resCode)){
							
							//700018：柜员角色权限不够
							logger.info("柜员角色权限不够："+errMsg);
							closeDialog(prossDialog);
							openConfirmDialog(errMsg+":是：请重新录入柜员号。否：退出。");
							confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseReleased(MouseEvent e) {
									//清空倒计时
									clearTimeText();
									stopTimer(voiceTimer);
									closeVoice();
									closeDialog(confirmDialog);
									openPanel(new AccAuthorNoPanel(map));
								}
							});
							confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseReleased(MouseEvent e) {
									//清空倒计时
									clearTimeText();
									stopTimer(voiceTimer);
									closeVoice();
									closeDialog(confirmDialog);
									openPanel(new OutputBankCardPanel());
								}
							});
							return;
							
						}else if("700066".equals(resCode)){
							
							//700066：指纹录入错误
							logger.info("指纹录入错误："+errMsg);
							closeDialog(prossDialog);
							openConfirmDialog(errMsg+":是：请重新录入指纹。否：请重新录入柜员号。");
							
						}else{
							
							logger.info("指纹校验异常："+errMsg);
							closeDialog(prossDialog);
							openConfirmDialog(errMsg+":是：请重新录入指纹。否：请重新录入柜员号。");
						}
						confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								//清空倒计时
								clearTimeText();
								stopTimer(voiceTimer);
								closeVoice();
								closeDialog(confirmDialog);
								openPanel(new AccAuthorFingerprintPanel(map));
							}
						});
						confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								//清空倒计时
								clearTimeText();
								stopTimer(voiceTimer);
								closeVoice();
								closeDialog(confirmDialog);
								openPanel(new AccAuthorNoPanel(map));
							}
						});
						return;
					}
					
					logger.info("指纹校验成功");
					//清空倒计时
					clearTimeText();
					stopTimer(voiceTimer);
					closeVoice();
					closeDialog(prossDialog);
					if ("0010".equals(AccountTradeCodeAction.transBean.getProductCode())) {
						
						logger.info("进入是否打印存单确认框");
						String msg = "";
						if("0".equals(AccountTradeCodeAction.transBean.getAutoRedpFlag())){//非自动转存
							msg = "是否打印存单。是：打印。否：不打印，到期后本息自动转入银行卡。";
						}else{
							msg = "是否打印存单。是：打印；否：不打印。";
						}
						// 弹框提示是否打印存单
						openConfirmDialog(msg);
						getConfirmDialog().YseButten.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								AccountTradeCodeAction.transBean.setCertPrint("1");// 打印标识
								getConfirmDialog().disposeDialog();
								map.put("transCode", "0015");
								logger.info("同意打印并进入输入密码页面"+map.get("PRODUCT_CODE"));
								AccountTransFlow.startTransFlow(comp, map);
							}
						});
						getConfirmDialog().Nobutton.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								AccountTradeCodeAction.transBean.setCertPrint("0");// 不打印标识
								getConfirmDialog().disposeDialog();
								map.put("transCode", "0015");
								logger.info("同意打印并进入输入密码页面"+map.get("PRODUCT_CODE"));
								AccountTransFlow.startTransFlow(comp, map);
							}
						});
					} else {
						logger.info("授权后直接进行交易："+map.get("PRODUCT_CODE"));
						if(AccountTradeCodeAction.transBean.getProductCode().startsWith("JX")){
							clearTimeText();// 清空倒计时
							stopTimer(voiceTimer);// 关语音
							closeVoice();// 关语音流
							AccountTradeCodeAction.transBean.setCertPrint("0");
							params.put("transCode", "0015");
							AccountTransFlow.startTransFlow(comp, params);
						}else{
							
							openConfirmDialog("是否打印存单。是：打印。否：不打印，到期后本息自动转入银行卡。");
							//打印存单
							confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseReleased(MouseEvent e) {
									logger.info("选择打印存单按钮");
									clearTimeText();// 清空倒计时
									stopTimer(voiceTimer);// 关语音
									closeVoice();// 关语音流
									AccountTradeCodeAction.transBean.setCertPrint("1");
									confirmDialog.disposeDialog();
									map.put("transCode", "0015");
									AccountTransFlow.startTransFlow(comp, map);
								}
							});
							//不打印存单
							confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseReleased(MouseEvent e) {
									logger.info("选择否不打印存单");
									clearTimeText();// 清空倒计时
									stopTimer(voiceTimer);// 关语音
									closeVoice();// 关语音流
									AccountTradeCodeAction.transBean.setCertPrint("0");
									confirmDialog.disposeDialog();
									map.put("transCode", "0015");
									AccountTransFlow.startTransFlow(comp, map);
								}
							});
						}

					}
				} catch (Exception e) {
					logger.error("指纹校验异常："+e);
					closeDialog(prossDialog);
					openConfirmDialog("指纹校验失败。是：请重新录入指纹。否：请重新录入柜员号。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							//清空倒计时
							clearTimeText();
							stopTimer(voiceTimer);
							closeVoice();
							closeDialog(confirmDialog);
							openPanel(new AccAuthorFingerprintPanel(map));
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							//清空倒计时
							clearTimeText();
							stopTimer(voiceTimer);
							closeVoice();
							closeDialog(confirmDialog);
							openPanel(new AccAuthorNoPanel(map));
						}
					});
				}
			}
		}.start();
	}
}
