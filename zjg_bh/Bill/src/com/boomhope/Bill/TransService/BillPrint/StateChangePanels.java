package com.boomhope.Bill.TransService.BillPrint;

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
 * 状态变更完成页面
 * @author gyw
 *
 */
public class StateChangePanels extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(StateChangePanels.class);
	public StateChangePanels(final BillPrintBean transBean) {
		logger.info("进入状态变更成功页");
		this.billPrintBean = transBean;
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
		/* 警告图标 */
		JLabel billImage = new JLabel();
		billImage.setIcon(new ImageIcon("pic/ok.png"));
		billImage.setIconTextGap(6);
		billImage.setBounds(380, 240, 66, 76);
		this.showJpanel.add(billImage);
		
		JLabel lblNewLabel = new JLabel("状态变更成功!");
		lblNewLabel.setForeground(Color.red);
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
		lblNewLabel.setBounds(494, 250, 540,40);
		this.showJpanel.add(lblNewLabel);
		
		/*
		 * 继续交易
		 */
		JLabel lblNewLabel1 = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		lblNewLabel1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				upstep(transBean);
			}

		});
		lblNewLabel1.setBounds(1075, 770, 150, 50);
		this.add(lblNewLabel1);
		
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
	}
	
	private void upstep(BillPrintBean transBean){
		clearTimeText();
		transBean.setSec(true);
		openPanel(new TransPrintOrStateChage(transBean));
	}
	
	
	
}
