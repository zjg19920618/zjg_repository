package com.boomhope.Bill.TransService.BudingJrnlNo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.BudingJrnlNo.bean.BulidingJrnNoBean;
import com.boomhope.Bill.Util.UtilButton;

/**
 * 
 * @Description:选择要办理的业务
 * @author ly
 */
public class TJServicePanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TJServicePanel.class);
	AudioStream as = null;
	private static final long serialVersionUID = 1L;
	//初始化
	public TJServicePanel() {

		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* 倒计时结束退出交易 */
				clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");; 
			}

		});
		delayTimer.start();
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);
				excuteVoice("voice/checkService.wav");

			}
		});
		voiceTimer.start();

		
		/* 我要推荐 */
		UtilButton transferMoney = new UtilButton("pic/wytj.png", "pic/wytj.png");
		transferMoney.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				transfer(jrnNoBean);
			}
		});
		transferMoney.setIconTextGap(6);
		transferMoney.setBounds(622, 150, 200, 300);
		this.showJpanel.add(transferMoney);
		
		/* 领取奖励 */
		UtilButton transferCancel = new UtilButton("pic/lqjl.png", "pic/lqjl.png");
		transferCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				transferCancel(jrnNoBean);
			}
		});
		transferCancel.setIconTextGap(6);
		transferCancel.setBounds(197, 150, 200, 300);
		this.showJpanel.add(transferCancel);
		
		//上一步
		JLabel label1 = new JLabel(new ImageIcon("pic/preStep.png"));
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");				
				backTrans();
			}
		
		});
		label1.setBounds(1075, 770, 150, 50);
		this.add(label1);
		//退出
		JLabel label_11 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");				
				returnHome();
			}

		});
		label_11.setBounds(1258, 770, 150, 50);
		this.add(label_11);
		

	}
	

	/**
	 * 我要推荐
	 */
	private void transfer(BulidingJrnNoBean transBean){
		
		transBean.setTag("wytj");
		clearTimeText();
		openPanel(new InputBankCardPanel(transBean));
		
	}
	
	/**
	 * 领取奖励
	 */
	private void transferCancel(BulidingJrnNoBean transBean){
		
		clearTimeText();
		transBean.setTag("lqjl");
		openPanel(new InputBankCardPanel(transBean));
		
	}

	
	/**
	 * 返回
	 * */
	public void backTrans() {
		clearTimeText();
		returnHome();
			
	}

}
