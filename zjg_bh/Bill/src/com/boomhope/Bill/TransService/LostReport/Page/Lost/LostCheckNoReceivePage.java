package com.boomhope.Bill.TransService.LostReport.Page.Lost;

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

/**
 * @Description:确认不补领提示页
 * @author zjg
 * @date 2018年3月23日 上午10:41:51
 */
@SuppressWarnings("static-access")
public class LostCheckNoReceivePage extends BaseTransPanelNew {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(LostCheckNoReceivePage.class);
	private boolean on_off = true;// 用于控制页面跳转的开关

	public LostCheckNoReceivePage() {

		baseTransBean.setThisComponent(this);

		/* 显示时间倒计时 */
		this.showTimeText(BaseTransPanelNew.delaySecondShortTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondShortTime * 1000,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						/* 倒计时结束退出交易 */
						clearTimeText();
						serverStop("温馨提示：服务已超时，请结束您的交易！", "", "");
					}
				});
		delayTimer.start();

		// 标题
		JLabel depoLum = new JLabel("请确认仅对该存单进行挂失，不立即补领");
		depoLum.setBounds(0, 200, 1009, 180);
		this.showJpanel.add(depoLum);
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setHorizontalAlignment(JLabel.CENTER);

		// 确认
		JLabel okButton = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		okButton.setBounds(892, 770, 150, 50);
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击确认页面");
				if (!on_off) {
					logger.info("开关当前状态" + on_off);
					return;
				}
				logger.info("开关当前状态" + on_off);
				on_off = false;

				clearTimeText();// 清空倒计时
				stopTimer(voiceTimer);// 关闭语音
				closeVoice();// 关闭语音流

				ok();
			}
		});
		add(okButton);

		// 上一步
		JLabel back = new JLabel(new ImageIcon("pic/preStep.png"));
		back.setBounds(1075, 770, 150, 50);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");
				if (!on_off) {
					logger.info("开关当前状态" + on_off);
					return;
				}
				logger.info("开关当前状态" + on_off);
				on_off = false;

				clearTimeText();// 清空倒计时
				stopTimer(voiceTimer);// 关闭语音
				closeVoice();// 关闭语音流

				back();
			}
		});
		add(back);

		// 退出
		JLabel exit = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		exit.setBounds(1258, 770, 150, 50);
		exit.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");
				if (!on_off) {
					logger.info("开关当前状态" + on_off);
					return;
				}
				logger.info("开关当前状态" + on_off);
				on_off = false;

				clearTimeText();
				stopTimer(voiceTimer);// 关闭语音
				closeVoice();// 关闭语音流

				exit();
			}
		});
		add(exit);

	}

	/**
	 * 确认
	 */
	public void ok() {

		// 进入账户信息签字页面(只挂失)
		openPanel(new LostConfirmPanel());
	}

	/**
	 * 返回上一步
	 */
	public void back() {

		// 返回选择是否补领
		openPanel(new LostCheckIsReceivePage());

	}

	/**
	 * 退出交易
	 */
	public void exit() {

		returnHome();
	}
}
