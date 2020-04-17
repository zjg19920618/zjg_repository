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
 * title: 身份证检查失败
 * @author ly
 * 2016年11月8日上午9:48:49
 */
public class TransPrintCheckIdcardFailPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransPrintCheckIdcardFailPanel.class);

	private static final long serialVersionUID = 1L;

	public TransPrintCheckIdcardFailPanel(final BillPrintBean transBean) {
		logger.info("进入身份证信息读取失败页面");
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
		billImage.setIcon(new ImageIcon("pic/war_icon.png"));
		billImage.setIconTextGap(6);
		billImage.setBounds(290, 281, 47, 44);
		this.showJpanel.add(billImage);

		JLabel label = new JLabel("身份证信息读取失败");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("微软雅黑", Font.BOLD, 30));
		label.setForeground(Color.red);
		label.setBounds(370, 240, 540, 123);
		this.showJpanel.add(label);

		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");				
				nextStep(transBean);
			}

		});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);
	}

	/**
	 * 下一步
	 */
	public void nextStep(BillPrintBean transBean) {
		transBean.getImportMap().put("idCard_check_result", "false");
		openPanel(new TransPrintOutputIdcardPanel(transBean));
	}
}
