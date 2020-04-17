package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.UtilButton;
import com.boomhope.Bill.Util.UtilVoice;

/**
 * 现金开户代理人选择页
 * @author gyw
 *
 */
public class MoneyBoxDeputySelectionPanel extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(MoneyBoxDeputySelectionPanel.class);
	private static final long serialVersionUID = 1L;
	
	private boolean on_off=true;//开关控制
	public MoneyBoxDeputySelectionPanel(final PublicCashOpenBean transBean){
		this.cashBean = transBean;

		// logger.debug("订单号："+this.transBean.getOrderNo());
		// logger.debug("订单密码："+this.transBean.getOrderPwd());
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);
            	excuteVoice("voice/others.wav");
            	
            }      
        });            
		voiceTimer.start(); 
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
		
		/* 标题信息 */
		JLabel titleLabel = new JLabel("您是否代理他人办理业务");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));
		titleLabel.setBounds(0, 80, 1009, 40);
		this.showJpanel.add(titleLabel);
		
		/* 是 */
		UtilButton trueButton = new UtilButton("pic/shi.png","pic/shi.png");
		trueButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				logger.info("选择有代理人");
				agent(transBean);
			}

		});
		trueButton.setBounds(160, 200, 200, 60);
		this.showJpanel.add(trueButton);
		
		/* 否 */
		UtilButton falseButton = new UtilButton("pic/fou.png", "pic/fou.png");
		falseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				logger.info("选择无代理人");
				me(transBean);
			}

		});
		falseButton.setBounds(640, 200, 200, 60);
		this.showJpanel.add(falseButton);
		
	}
	/**
	 * 代理人
	 */
	public void agent(PublicCashOpenBean transBean){
		clearTimeText();
		transBean.getImportMap().put("agent_persion", "yes");
		openPanel(new MoneyBoxIdentificationCardPanel(transBean));
		
	}
	
	/**
	 * 本人
	 */
	public void me(PublicCashOpenBean transBean){
		clearTimeText();
		transBean.getImportMap().put("agent_persion", "no");
		openPanel(new MoneyBoxIdentificationCardPanel(transBean));
		
		
		
		
	}
	
	
	
	
}
