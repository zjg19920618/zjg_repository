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
 * 银行卡密码失败页
 * 
 * @author zy
 *
 */
public class TransPrintCheckingInputBillFail extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransPrintCheckingInputBillFail.class);
	private static final long serialVersionUID = 1L;

	public TransPrintCheckingInputBillFail(final BillPrintBean transBean) {
		this.billPrintBean = transBean;
		/* 显示时间倒计时 */
		logger.info("进入银行卡密码失败页面");
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {
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
				excuteVoice("voice/accreditcw.wav");
			}
		});
		voiceTimer.start();

		/* 服务终止图标 */
		JLabel billImage = new JLabel();
		billImage.setIcon(new ImageIcon("pic/war_icon.png"));
		billImage.setIconTextGap(6);
		billImage.setBounds(220, 281, 47, 44);
		this.showJpanel.add(billImage);

		/* 标题信息 */
		JLabel titleLabel = new JLabel("银行卡密码错误，请点击上一步重新输入");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(Color.red);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
		titleLabel.setBounds(300, 240, 540, 123);
		this.showJpanel.add(titleLabel);

		//上一步
		JLabel label1 = new JLabel(new ImageIcon("pic/preStep.png"));
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");				
				scanBill1(transBean);
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
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);
	}

	/***
	 * 上一步
	 */
	private void scanBill1(BillPrintBean transBean) {
		clearTimeText();
		openPanel(new TransInputBankCardPassPanel(transBean, bankBeans));

	}

	

	

}
