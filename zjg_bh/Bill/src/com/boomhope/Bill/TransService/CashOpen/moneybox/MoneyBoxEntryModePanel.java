package com.boomhope.Bill.TransService.CashOpen.moneybox;

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
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.UtilButton;
/***
 * 请选择信息录入方式
 * @author gyw
 *
 */
public class MoneyBoxEntryModePanel extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxEntryModePanel.class);
	
	private boolean on_off=true;//开关控制
	public MoneyBoxEntryModePanel(final PublicCashOpenBean transBean){
		this.cashBean = transBean;
		
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	on_off=false;
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
            	excuteVoice("voice/Informationinputway.wav");
            	
            }      
        });            
		voiceTimer.start(); 
		
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请选择信息录入方式");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(titleLabel);

		/* 扫描二维码*/
		UtilButton openButton = new UtilButton("pic/QRcode.png","pic/QRcode.png");
		openButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 扫描二维码*/
				doOpenTrans(transBean);
			}

		});
		openButton.setSize(360, 280);
		openButton.setLocation(60, 230);
		openButton.setIcon(new ImageIcon("pic/QRcode.png"));
		this.showJpanel.add(openButton);
		
		
		
		/* 手动输入*/
		UtilButton backButton = new UtilButton("pic/sdsr.png","pic/sdsr.png");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 手动输入*/
				doBackTrans(transBean);
			}
		});
		backButton.setSize(360, 280);
		backButton.setLocation(600, 230);
		backButton.setIcon(new ImageIcon("pic/sdsr.png"));
		this.showJpanel.add(backButton);
		
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");			
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				returnHome();
			}

		});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);
		}
	
	/* 扫描二维码*/
	private void doOpenTrans(PublicCashOpenBean transBean){
		clearTimeText();
		openPanel(new MoneyBoxScanningPanel(transBean));
		
	}
	/* 手动输入*/
	private void doBackTrans(PublicCashOpenBean transBean){
		clearTimeText();
		openPanel(new MoneyBoxOrderNumberPanel(transBean));
		
	}
}
