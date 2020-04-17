package com.boomhope.Bill.TransService.AllTransPublicPanel.Author;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransBean;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Util.SoftKeyBoardPopuppassword;


/**
 * Title: 授权柜员密码
 *
 * @author: hk
 * 
 * @date: 2018年3月23日 上午10:50:09  
 * 
 */
@SuppressWarnings("static-access")
public class AllPublicAuthorPassPanel extends BaseTransPanelNew {

	static Logger logger = Logger.getLogger(AllPublicAuthorPassPanel.class);
	private static final long serialVersionUID = 1L;;
	final int voiceSecond = 500;
	private JPanel passwordPanel;
	JLabel errInfo = null;// 错误提示
	private SoftKeyBoardPopuppassword keyPopup;
	private JPasswordField textField;
	private boolean on_off = true;// 用于控制页面跳转的开关

	/***
	 * 初始化
	 */
	public AllPublicAuthorPassPanel() {
		logger.info("进入柜员密码输入页面");

		// 将当前页面传入流程控制进行操作
		baseTransBean.setThisComponent(this);

		/* 显示时间倒计时 */
		if (delayTimer != null) {
			clearTimeText();
		}
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000,
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						logger.info("柜员密码录入页面倒计时结束 ");
						/* 倒计时结束退出交易 */
						clearTimeText();
						serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！", "",
								"");
					}
				});
		delayTimer.start();

		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);// 关语音
				closeVoice();// 关语音流
				excuteVoice("voice/codes.wav");

			}
		});
		voiceTimer.start();

		/* 标题信息 */
		JLabel titleLabel = new JLabel("请录入授权密码:");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
		titleLabel.setBounds(0, 209, 1009, 40);
		this.showJpanel.add(titleLabel);

		// 键盘面板
		passwordPanel = new JPanel(new BorderLayout());
		passwordPanel.setBackground(Color.WHITE);
		passwordPanel.setPreferredSize(new Dimension(2024, 30));
		passwordPanel.setLayout(new BorderLayout());
		passwordPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel);
		/* 密码框 */
		textField = new JPasswordField();
		textField.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField.setBounds(292, 259, 424, 40);
		// add(textField);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				showKeyBord();
			}

		});
		textField.setColumns(10);
		this.showJpanel.add(textField);
		keyPopup = new SoftKeyBoardPopuppassword(textField, 8);

		errInfo = new JLabel("提示：请录入授权密码!");
		errInfo.setVisible(false);
		errInfo.setForeground(Color.RED);
		errInfo.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		errInfo.setBounds(349, 322, 258, 23);
		this.showJpanel.add(errInfo);

		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步");
				if (!on_off) {
					logger.info("开关当前状态" + on_off);
					return;
				}
				logger.info("开关当前状态" + on_off);
				on_off = false;
				/* 处理上一页 */
				backStep();
			}
		});
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(1075, 770);
		add(submitBtn);

		// 确认
		JLabel confirm = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		confirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击确认按钮");
				if (!on_off) {
					logger.info("开关当前状态" + on_off);
					return;
				}
				logger.info("开关当前状态" + on_off);
				on_off = false;
				nextStep();
			}
		});
		confirm.setSize(150, 50);
		confirm.setLocation(890, 770);
		add(confirm);

		// 返回
		JLabel backButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");
				if (!on_off) {
					logger.info("开关当前状态" + on_off);
					return;
				}
				logger.info("开关当前状态" + on_off);
				on_off = false;
				clearTimeText();
				accCancelExit();
			}
		});
		add(backButton);

	}

	/***
	 * 下一步
	 */
	private void nextStep() {
		logger.info("进入确认方法");
		
		clearTimeText();
		stopTimer(voiceTimer);
		closeVoice();
		char[] cs = textField.getPassword();
		String pass = new String(cs);
		if (pass == null || pass.equals("")) {
			errInfo.setVisible(true);
			on_off = true;
			return;
		}
		BaseTransBean.setAllPubFristPass(pass);
		allPubTransFlow.transFlow();
		return;
	}

	/***
	 * 上一步
	 */
	private void backStep() {
		logger.info("进入上一步方法");
		clearTimeText();
		stopTimer(voiceTimer);
		closeVoice();
		backStepMethod();
		allPubTransFlow.transFlow();
	}

	/***
	 * 键盘
	 */
	private void showKeyBord() {
		logger.info("调用软键盘");
		keyPopup.show(passwordPanel, 160, 310);
	}
}
