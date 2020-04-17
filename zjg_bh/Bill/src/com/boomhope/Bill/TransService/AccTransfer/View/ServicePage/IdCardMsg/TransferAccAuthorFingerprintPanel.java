package com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.IdCardMsg;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Map;

import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.Interface.InterfaceSendMsg;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.TransferBusinessDealPanel;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.UtilVoice;
import com.boomhope.tms.peripheral.action.FingerPrint;
import com.boomhope.tms.peripheral.bean.FingerPrintGet;

/**
 * 指纹识别
 * @author gyw
 *
 */
public class TransferAccAuthorFingerprintPanel extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(TransferAccAuthorFingerprintPanel.class);
	private static final long serialVersionUID = 1L;
	final int voiceSecond = 500;
	JLabel fingerprint = null;
	UtilVoice utilVoice = null;
	SourceDataLine line = null;
	String zwValue = null;
	private PublicAccTransferBean transferMoneyBean;//公共bean
	/***
	 * 初始化
	 */
	public TransferAccAuthorFingerprintPanel(final PublicAccTransferBean transferBean) {
		//将当前页面传入流程控制进行操作
		final Component comp=this;
		this.transferMoneyBean = transferBean;
		
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	voiceTimer.stop();
            	closeVoice();
            	excuteVoice("voice/fingerprint.wav");
            	
            }      
        });            
		voiceTimer.start(); 
		/* 显示时间倒计时 */
		this.showTimeText( delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
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
		t.setBounds(0, 100, 1009, 42);
		this.showJpanel.add(t);
		
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/fingerprint.png"));
		billImage.setHorizontalAlignment(SwingUtilities.CENTER);
		billImage.setIconTextGap(6);
		billImage.setBounds(395, 205, 220, 200);
		this.showJpanel.add(billImage);
	
		fingerprint = new JLabel("指纹验证失败,请重新录入");
		fingerprint.setVisible(false);;
		fingerprint.setFont(new Font("微软雅黑", Font.BOLD, 20));
		fingerprint.setHorizontalAlignment(SwingConstants.CENTER);
		fingerprint.setForeground(Color.RED);
		fingerprint.setBounds(0, 460, 1009, 24);
		this.showJpanel.add(fingerprint);
		
		openZw(comp);
	}

/**
 * 打开指纹仪(校验授权指纹)
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
			
			PublicAccTransferBean bean=new PublicAccTransferBean();
			if("1".equals(transferMoneyBean.getTellerIsChecked())){
				bean.setSupTellerNo(transferMoneyBean.getSupTellerNo());
			}else{
				bean.setSupTellerNo(transferMoneyBean.getSupTellerNo2());
			}
			bean.setFinPriVal(zwValue);
			
			try {
				logger.info("指纹校验开始");
				transferBean.getReqMCM001().setReqBefor("07660");
				Map params=InterfaceSendMsg.inter07660(bean);
				String resCode=(String)params.get("resCode");
				String errMsg=(String)params.get("errMsg");
				transferBean.getReqMCM001().setReqAfter(resCode,errMsg);
				if(!InterfaceSendMsg.SUCCESS.equals(resCode)){
					
					// 指纹错误
					if("710099".equals(resCode)){
						logger.info("指纹校验失败："+errMsg);
						closeDialog(prossDialog);
						openConfirmDialog(errMsg+"是：请重新授权。否：退出。");
						
					}else if("700018".equals(resCode)){
						
						//700018：柜员角色权限不够
						logger.info("柜员角色权限不够："+errMsg);
						closeDialog(prossDialog);
						openConfirmDialog(errMsg+"是：请重新授权。否：退出。");
						
					}else if("700066".equals(resCode)){
						
						//700066：指纹录入错误
						logger.info("指纹录入错误："+errMsg);
						closeDialog(prossDialog);
						openConfirmDialog(errMsg+"是：请重新授权。否：退出。");
						
					}else{
						
						logger.info("指纹校验异常："+errMsg);
						closeDialog(prossDialog);
						openConfirmDialog(errMsg+"是：请重新授权。否：退出。");
					}
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							//清空倒计时
							clearTimeText();
							stopTimer(voiceTimer);
							closeVoice();
							closeDialog(confirmDialog);
							openPanel(new TransferAuthorizationPanel(transferMoneyBean));
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
				}
				
				logger.info("指纹校验成功");
				//清空倒计时
				clearTimeText();
				stopTimer(voiceTimer);
				closeVoice();
				closeDialog(prossDialog);
				if(new BigDecimal(transferMoneyBean.getRemitAmt()).compareTo(new BigDecimal(Property.AUTH_TWO_ONCE_AMT))>=0){
					if("1".equals(transferMoneyBean.getTellerIsChecked().trim())){
						transferMoneyBean.setTellerIsChecked("2");
						openPanel(new TransferAuthorizationPanel(transferMoneyBean));
					}else{
						//跳入下一页
						openPanel(new TransferBusinessDealPanel(transferMoneyBean));
					}
				}else{
					//跳入下一页
					openPanel(new TransferBusinessDealPanel(transferMoneyBean));
				}
			} catch (Exception e) {
				logger.error("指纹校验异常："+e);
				closeDialog(prossDialog);
				openConfirmDialog("指纹校验失败。是：请重新授权。否：退出。");
				confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						//清空倒计时
						clearTimeText();
						stopTimer(voiceTimer);
						closeVoice();
						closeDialog(confirmDialog);
						openPanel(new TransferAuthorizationPanel(transferMoneyBean));
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
			}
		}
	}.start();
}
}
