package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.UtilButton;
/***
 * 请选择糖豆兑换方式
 * @author gyw
 *
 */
public class MoneyBoxJellyBeanPanel extends BaseTransPanelNew{

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxJellyBeanPanel.class);
	
	private boolean on_off=true;//开关控制
	public MoneyBoxJellyBeanPanel(final PublicCashOpenBean transBean){
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
            	excuteVoice("voice/JellyBean.wav");
            	
            }      
        });            
		voiceTimer.start(); 
		
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请选择糖豆兑换方式");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setBounds(0, 60, 1009,60);
		this.showJpanel.add(titleLabel);

		/* 密码兑付*/
		UtilButton openButton = new UtilButton("pic/password1.png","pic/password1.png");
		openButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 密码兑付*/
//				doOpenTrans();
				openPanel(new MoneyBoxVerificationCodePanel(transBean));
					
				
			}

		});
		openButton.setSize(230, 230);
		openButton.setLocation(171, 292);
		openButton.setIcon(new ImageIcon("pic/password1.png"));
		this.showJpanel.add(openButton);
		
		
		
		/* 验证码兑付*/
		UtilButton backButton = new UtilButton("pic/authCode.png","pic/authCode.png");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 验证码兑付*/
//				doBackTrans();
				openPanel(new MoneyBoxVerificationCodePanel(transBean));
				
			}
		});
		backButton.setSize(230, 230);
		backButton.setLocation(616, 292);
		backButton.setIcon(new ImageIcon("pic/authCode.png"));
		this.showJpanel.add(backButton);
		
		
		}
	
	/* 密码兑付*/
	private void doOpenTrans(){
		try {
			as.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		delayTimer.stop();
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
//				openPanel(GlobalPanelFlag.PRIVATE_LINE_PRODUCTS);
			}
		});
	}
	/* 验证码兑付*/
//	private void doBackTrans(){
//		try {
//			as.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		delayTimer.stop();
//		SwingUtilities.invokeLater(new Runnable(){
//			@Override
//			public void run() {
//				openPanel(GlobalPanelFlag.ORDER_NUMBER);
//			}
//		});
//	}

}
