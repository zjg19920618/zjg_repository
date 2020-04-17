package com.boomhope.Bill.TransService.AccCancel.AccountCancel.BJ;

import java.awt.Color;
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
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.HaveAcc.AccCancelInputDepositPanel;
import com.boomhope.Bill.TransService.AccCancel.Bean.PublicAccCancelBean;
import com.boomhope.Bill.TransService.AccCancel.Interface.IntefaceSendMsg;
import com.boomhope.Bill.Util.UtilVoice;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.peripheral.action.FingerPrint;
import com.boomhope.tms.peripheral.bean.FingerPrintGet;

/**
 * 主管鉴别存单真伪,指纹授权
 * @author gyw
 *
 */
public class AccCancelAuthorFingerprintBJPanel extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(AccCancelAuthorFingerprintBJPanel.class);
	private static final long serialVersionUID = 1L;
	final int voiceSecond = 500;
	JLabel fingerprint = null;
	UtilVoice utilVoice = null;
	SourceDataLine line = null;
	String zwValue = null;
	/***
	 * 初始化
	 */
	public AccCancelAuthorFingerprintBJPanel() {
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondShortTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondShortTime * 1000,new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				/*倒计时结束退出交易*/
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		delayTimer.start();
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
            	excuteVoice("voice/fingerprint.wav");
            	
            }      
        });            
		voiceTimer.start(); 
		
		/* 标题信息 */
		JLabel t = new JLabel("请录入指纹:");
		t.setHorizontalAlignment(JLabel.CENTER);
		t.setFont(new Font("微软雅黑", Font.BOLD, 40));
		t.setBounds(0, 174, 1009, 40);
		this.showJpanel.add(t);
		
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/fingerprint.png"));
		billImage.setIconTextGap(6);
		billImage.setBounds(414, 270, 220, 200);
		this.showJpanel.add(billImage);
	
		fingerprint = new JLabel("指纹验证失败,请重新录入");
		fingerprint.setVisible(false);;
		fingerprint.setFont(new Font("微软雅黑", Font.BOLD, 20));
		fingerprint.setHorizontalAlignment(SwingConstants.CENTER);
		fingerprint.setForeground(Color.RED);
		fingerprint.setBounds(394, 557, 276, 20);
		this.showJpanel.add(fingerprint);
		
		openZw();
		
	}
	
	/**
	 * 打开指纹
	 */
	public void openZw(){
		Thread thread  = new Thread("退卡线程"){
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
	        	checkAuthorNo();
	        }
	    };
	    thread.start();
	}

	/**
	 * 调用口令授权
	 */
	private void checkAuthorNo(){
		new Thread(){
			@Override
			public void run() {
				openProssDialog();
				
				PublicAccCancelBean bean=new PublicAccCancelBean();
				bean.setSupTellerNo(accCancelBean.getSupTellerNo());
				bean.setFinPriVal(zwValue);
				
				try {
					logger.info("指纹校验开始");
					accCancelBean.getReqMCM001().setReqBefor("07660");
					Map params=IntefaceSendMsg.inter07660(bean);
					accCancelBean.getReqMCM001().setReqAfter((String)params.get("resCode"), (String)params.get("errMsg"));
					String resCode=(String)params.get("resCode");
					String errMsg=(String)params.get("errMsg");
					if(!IntefaceSendMsg.SUCCESS.equals(resCode)){
						
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
									//清空鉴伪次数标识
									accCancelBean.setJwState("");
									openPanel(new AccCancelAuthorNoBJPanel());
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
									accCancelBean.getReqMCM001().setLendirection("");
									accCancelBean.getReqMCM001().setToaccount("");
									accCancelBean.getReqMCM001().setCustomername("");
									accCancelBean.getReqMCM001().setTonouns("");
									accCancelBean.getReqMCM001().setAccount("");
									accCancelBean.getReqMCM001().setRvouchertype("");
									accCancelBean.getReqMCM001().setRvoucherno("");
									MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
									//返回时跳转页面
									returnHome();
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
								openPanel(new AccCancelAuthorFingerprintBJPanel());
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
								//清空鉴伪次数标识
								accCancelBean.setJwState("");
								//返回时跳转页面
								openPanel(new AccCancelAuthorNoBJPanel());
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
					
					//鉴伪通过后进行一次授权标识
					accCancelBean.setJwState("1");
					//跳入下一页
					openPanel(new AccCancelInputDepositPanel());
					
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
							openPanel(new AccCancelAuthorFingerprintBJPanel());
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
							//清空鉴伪次数标识
							accCancelBean.setJwState("");
							//返回时跳转页面
							openPanel(new AccCancelAuthorNoBJPanel());
						}
					});
				}
			}
		}.start();
	}
}
