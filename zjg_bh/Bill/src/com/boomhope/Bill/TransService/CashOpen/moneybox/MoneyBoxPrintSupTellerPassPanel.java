package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.SoftKeyBoardPopup;
import com.boomhope.Bill.Util.UtilButton;

/**
 * 授权柜员密码
 * 
 * @author gyw
 *
 */
public class MoneyBoxPrintSupTellerPassPanel extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(MoneyBoxPrintSupTellerPassPanel.class);
	private static final long serialVersionUID = 1L;
	private JPanel passwordPanel;
	JLabel errInfo = null;// 错误提示
	private SoftKeyBoardPopup keyPopup;
	private JPasswordField textField;

	private boolean on_off=true;//开关控制
	/***
	 * 初始化
	 */
	public MoneyBoxPrintSupTellerPassPanel(final PublicCashOpenBean transBean) {

		this.cashBean = transBean;
		GlobalPanelFlag.CurrentFlag = GlobalPanelFlag.MONEYBOX_PRINT_SUP_TELLER_PASS;

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
				voiceTimer.stop();
				excuteVoice("voice/codes.wav");

			}
		});
		voiceTimer.start();

		/* 标题信息 */
		JLabel titleLabel = new JLabel("请录入授权密码:");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground((Color.decode("#412174")));
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setBounds(0, 200, 1009, 60);
		this.showJpanel.add(titleLabel);

		// textField = new JTextField();
		// textField.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		// textField.setBounds(357, 343, 424, 50);
		// add(textField);
		// textField.setColumns(10);
		// 输入框
		passwordPanel = new JPanel(new BorderLayout());
		passwordPanel.setBackground(Color.WHITE);
		passwordPanel.setPreferredSize(new Dimension(2024, 30));
		passwordPanel.setLayout(new BorderLayout());
		passwordPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel);
		/* 密码框 */
		textField = new JPasswordField();
		textField.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField.setBounds(325, 271, 360, 40);
		// add(textField);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				scanBill12();
			}

		});
		textField.setColumns(10);
		//passwordPanel.add(textField, BorderLayout.CENTER);
		this.showJpanel.add(textField);
		keyPopup = new SoftKeyBoardPopup(textField);

		errInfo = new JLabel("提示：请录入授权密码!");
		errInfo.setVisible(false);
		errInfo.setForeground(Color.RED);
		errInfo.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		errInfo.setBounds(325, 321, 258, 23);
		this.showJpanel.add(errInfo);


		//确认
		JLabel lblNewLabel = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		lblNewLabel.setBounds(890, 770, 150, 50);
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				/* 处理下一页 */
				logger.info("点击确认按钮");	
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				scanBill(transBean);
			}

		});
		this.add(lblNewLabel);
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
	 * 下一步
	 */
	private void scanBill(PublicCashOpenBean transBean) {
//		String pass = textField.getText();
        char[] cs = textField.getPassword();
        String pass = new String(cs);
        logger.debug("授权密码"+pass);
		if(pass == null || pass.equals("")){
			errInfo.setVisible(true);
			on_off=true;
			return;
		}
		transBean.setSupPass(pass);
		transBean.getImportMap().put("identifying", "6");
		clearTimeText();
		openPanel(new MoneyBoxSystemProcessingPanel(transBean));
	}

	/***
	 * 上一步
	 */
	private void scanBill1(PublicCashOpenBean transBean) {
		clearTimeText();
		openPanel(new MoneyBoxAdditionNumberPanel(transBean));
	}

	
	

	/***
	 * 键盘
	 */
	private void scanBill12() {
		logger.debug(1);
		keyPopup.show(passwordPanel, 160, 350);
		
	}

}
