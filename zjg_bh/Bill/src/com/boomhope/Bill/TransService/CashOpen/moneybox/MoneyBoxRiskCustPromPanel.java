package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.UtilButton;

public class MoneyBoxRiskCustPromPanel extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	Timer checkTimer = null;
	static Logger logger = Logger.getLogger(MoneyBoxRiskCustPromPanel.class);
	private boolean on_off=true;//开关控制
	public MoneyBoxRiskCustPromPanel(PublicCashOpenBean transBean) {
		this.cashBean = transBean;
		JLabel label = new JLabel("尊敬的客户：您在我行尚未预留信息或尚未进行客户身份识别，请先联系大堂经理，");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label.setForeground(Color.red);
		label.setBounds(0, 300, 1009, 30);
		this.showJpanel.add(label);
		
		JLabel lblNewLabel = new JLabel("给您带来的不便敬请谅解。");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel.setBounds(0, 350, 1059, 30);
		lblNewLabel.setForeground(Color.red);
		this.showJpanel.add(lblNewLabel);
		
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
		
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	on_off=false;
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });   
		delayTimer.start(); 
	}
	
}
