package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.UtilButton;

/***
 * 授权密码错误页面
 *
 */
public class MoneyBoxSupTellerPasswordFailuer extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(MoneyBoxSupTellerPasswordFailuer.class);
	private static final long serialVersionUID = 1L;
	private boolean on_off=true;//开关控制
	public MoneyBoxSupTellerPasswordFailuer(final PublicCashOpenBean transBean) {
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
				excuteVoice("voice/accredits.wav");

			}
		});
		voiceTimer.start();
		/* 服务终止图标 */
		JLabel billImage = new JLabel();
		billImage.setIcon(new ImageIcon("pic/war_icon.png"));
		billImage.setIconTextGap(6);
		billImage.setBounds(223, 260, 47, 44);
		this.showJpanel.add(billImage);

		/* 标题信息 */
		JLabel titleLabel = new JLabel("授权密码错误，请点击上一步重新输入");
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
		titleLabel.setBounds(267, 253, 1009, 44);
		this.showJpanel.add(titleLabel);

		//上一步
		JLabel label = new JLabel(new ImageIcon("pic/preStep.png"));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");			
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				scanBill1(transBean);
			}

		});
		label.setBounds(1075, 770, 150, 50);
		this.add(label);
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

	/***
	 * 上一步
	 */
	private void scanBill1(PublicCashOpenBean transBean) {
		clearTimeText();
		// 授权密码
		openPanel(new MoneyBoxPrintSupTellerPassPanel(transBean));

	}


	
}
