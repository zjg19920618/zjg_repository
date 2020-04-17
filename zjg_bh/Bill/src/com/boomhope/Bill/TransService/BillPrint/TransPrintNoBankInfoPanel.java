package com.boomhope.Bill.TransService.BillPrint;

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
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;

/**
 * 
 * title:无银行卡信息页面
 * @author ly
 * 2016年11月9日下午5:26:40
 */
public class TransPrintNoBankInfoPanel  extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(TransPrintNoBankInfoPanel.class);
	private static final long serialVersionUID = 1L;
	public TransPrintNoBankInfoPanel(final BillPrintBean transBean) {
		logger.info("进入无信息页");
		this.billPrintBean = transBean;
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		/* 服务终止图标 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/war_icon.png"));
		billImage.setIconTextGap(6);
		billImage.setBounds(300, 281, 47, 44);
		this.showJpanel.add(billImage);
		
		JLabel label = new JLabel("没有可打印或可变更状态的存单！");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setForeground(Color.red);
		label.setFont(new Font("微软雅黑", Font.BOLD, 30));
		label.setBounds(385, 240, 540, 123);
		this.showJpanel.add(label);
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");				
				returnHome();
			}

		});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);
		//上一步
		JLabel label1 = new JLabel(new ImageIcon("pic/preStep.png"));
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");				
				clearTimeText();
				openPanel(new TransPrintOrStateChage(transBean));
			}

		});
		label1.setBounds(1075, 770, 150, 50);
		this.add(label1);
	}
}
