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
import com.boomhope.Bill.TransService.AllTransPublicPanel.InputCardAndPwd.AllPublicInputPasswordPanel;
import com.boomhope.Bill.TransService.LostReport.Page.SelectLostOrSolveLostPanel;
import com.boomhope.Bill.Util.UtilButton;

/**
 * @Description:选择是否补领
 * @author zjg
 * @date 2018年3月23日 上午10:41:51
 */
@SuppressWarnings("static-access")
public class LostCheckIsReceivePage extends BaseTransPanelNew {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(SelectLostOrSolveLostPanel.class);
	private boolean on_off = true;// 用于控制页面跳转的开关

	public LostCheckIsReceivePage() {

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

		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);// 关语音
				closeVoice();// 关语音流
				excuteVoice("voice/lostSelect.wav");

			}
		});
		voiceTimer.start();

		// 标题
		JLabel depoLum = new JLabel("是否立即补领存单？");
		depoLum.setBounds(0, 30, 1009, 180);
		this.showJpanel.add(depoLum);
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setHorizontalAlignment(JLabel.CENTER);

		/* 是(补领) */
		UtilButton trueButton = new UtilButton("pic/newPic/selectYes.jpg",
				"pic/newPic/selectYes.jpg");
		trueButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击是按钮");
				if (!on_off) {
					logger.info("开关当前状态" + on_off);
					return;
				}
				logger.info("开关当前状态" + on_off);
				on_off = false;

				clearTimeText();
				stopTimer(voiceTimer);// 关闭语音
				closeVoice();// 关闭语音流

				yesRec();

			}
		});
		trueButton.setBounds(160, 280, 200, 90);
		this.showJpanel.add(trueButton);

		/* 否(不补领) */
		UtilButton falseButton = new UtilButton("pic/newPic/selectNo.jpg",
				"pic/newPic/selectNo.jpg");
		falseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击否按钮");
				if (!on_off) {
					logger.info("开关当前状态" + on_off);
					return;
				}
				logger.info("开关当前状态" + on_off);
				on_off = false;

				clearTimeText();
				stopTimer(voiceTimer);// 关闭语音
				closeVoice();// 关闭语音流

				noRec();
			}
		});
		falseButton.setBounds(640, 280, 200, 90);
		this.showJpanel.add(falseButton);

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
	 * 选择补领
	 */
	public void yesRec() {

		// 进入账户信息签字页面(挂失补发)
		lostPubBean.setLostOrSolve("1");
		openPanel(new LostConfirmPanel());

	}

	/**
	 * 选择不补领
	 */
	public void noRec() {

		// 进入确认不补领页面(只挂失)
		lostPubBean.setLostOrSolve("0");
		lostPubBean.getReqMCM001().setTranscode("11039");
		openPanel(new LostCheckNoReceivePage());

	}

	/**
	 * 返回上一步
	 */
	public void back() {
		
		//进入账户信息展示页面
		openPanel(new LostShowAccNoPage());
	}

	/**
	 * 退出交易
	 */
	public void exit() {

		returnHome();
	}
}
