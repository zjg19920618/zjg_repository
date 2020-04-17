package com.boomhope.Bill.TransService.AllTransPublicPanel.Author;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Util.UtilVoice;
import com.boomhope.tms.peripheral.action.FingerPrint;
import com.boomhope.tms.peripheral.bean.FingerPrintGet;

/**
 * 指纹识别
 * @author hk
 *
 */
@SuppressWarnings("static-access")
public class AllPublicAuthorFingerprintPanel extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(AllPublicAuthorFingerprintPanel.class);
	private static final long serialVersionUID = 1L;
	final int voiceSecond = 500;
	JLabel fingerprint = null;
	UtilVoice utilVoice = null;
	SourceDataLine line = null;
	String zwValue = null;
	/***
	 * 初始化
	 */
	public AllPublicAuthorFingerprintPanel() {
		logger.info("进入指纹识别页面");
		//将当前页面传入流程控制进行操作
		baseTransBean.setThisComponent(this);
		
		
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
		
		openZw();
	}
	
	/**
	 * 打开指纹
	 */
	public void openZw(){
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
	        	
	        	baseTransBean.setAllPubFristFingerVal(zwValue);
	        	allPubTransFlow.transFlow();
	        	return;
	        }
	    };
	    thread.start();
	}

}
