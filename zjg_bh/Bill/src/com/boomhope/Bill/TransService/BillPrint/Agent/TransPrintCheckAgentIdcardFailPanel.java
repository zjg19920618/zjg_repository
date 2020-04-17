package com.boomhope.Bill.TransService.BillPrint.Agent;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;

/**
 * 
 * title:身份证检查失败，证件退出
 * @author ly
 * 2016年11月7日下午10:00:53
 */
public class TransPrintCheckAgentIdcardFailPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransPrintCheckAgentIdcardFailPanel.class);
	private static final long serialVersionUID = 1L;
	public TransPrintCheckAgentIdcardFailPanel(final BillPrintBean transBean) {
		this.billPrintBean = transBean;
		logger.info("进入代理人身份信息核查失败页面");
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		delayTimer.start();
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);
            	excuteVoice("voice/custody.wav");
            	
            }      
        });            
		voiceTimer.start(); 
		/* 标题信息 */
		JLabel titleLabel = new JLabel("证件已退出，请妥善保管");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(titleLabel);
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/output_idCard.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(325, 236, 460, 360);
		this.showJpanel.add(billImage);
		
		//上一步
		JLabel label1 = new JLabel(new ImageIcon("pic/preStep.png"));
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");				
				canelTrans(transBean);
				}

		});
		label1.setBounds(1075, 770, 150, 50);
		this.add(label1);
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");				
				returnHome();
			}

		});

		
	}
	

	
	/**
	 * 上一步
	 */
	public void canelTrans(BillPrintBean transBean){
		clearTimeText();
		openPanel(new TransPrintInputAgentIdcardPanel(transBean));
	}
	
	
}
