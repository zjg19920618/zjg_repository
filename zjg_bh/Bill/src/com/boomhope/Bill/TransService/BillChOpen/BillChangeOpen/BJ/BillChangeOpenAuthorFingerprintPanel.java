package com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.BJ;

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
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.AccTransfer.Interface.InterfaceSendMsg;
import com.boomhope.Bill.TransService.BillChOpen.Bean.PublicBillChangeOpenBean;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.PublicUse.BillChangeOpenBusinessPanel;
import com.boomhope.Bill.TransService.BillChOpen.Interface.IntefaceSendMsg;
import com.boomhope.Bill.Util.UtilVoice;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.peripheral.action.FingerPrint;
import com.boomhope.tms.peripheral.bean.FingerPrintGet;

/**
 * 电子签名确认，录入指纹
 * @author zjg
 *
 */
public class BillChangeOpenAuthorFingerprintPanel extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(BillChangeOpenAuthorFingerprintPanel.class);
	private static final long serialVersionUID = 1L;
	final int voiceSecond = 500;
	JLabel fingerprint = null;
	UtilVoice utilVoice = null;
	SourceDataLine line = null;
	String zwValue = null;
	/***
	 * 初始化
	 */
	public BillChangeOpenAuthorFingerprintPanel() {
		//将当前页面传入流程控制进行操作
		final Component comp=this;
		
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
			
			PublicBillChangeOpenBean bean=new PublicBillChangeOpenBean();
			bean.setSupTellerNo(bcOpenBean.getSupTellerNo());
			bean.setFinPriVal(zwValue);
			
			try {
				logger.info("指纹校验开始");
				bcOpenBean.getReqMCM001().setReqBefor("07660");
				Map params=IntefaceSendMsg.inter07660(bean);
				String resCode=(String)params.get("resCode");
				String errMsg=(String)params.get("errMsg");
				bcOpenBean.getReqMCM001().setReqAfter(resCode, errMsg);
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
								openPanel(new BillChangeOpenAuthorNoPanel());
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
								billHKExit();
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
							openPanel(new BillChangeOpenAuthorFingerprintPanel());
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
							openPanel(new BillChangeOpenAuthorNoPanel());
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
				
				//进入换开业务处理页 
				openPanel(new BillChangeOpenBusinessPanel());	
				
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
						openPanel(new BillChangeOpenAuthorFingerprintPanel());
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
						openPanel(new BillChangeOpenAuthorNoPanel());
					}
				});
			}
		}
	}.start();
}
}
