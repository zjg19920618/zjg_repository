/**
 * 
 */
package com.boomhope.Bill.TransService.LostReport.Page.Lost;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Util.UtilButton;

/**
 * @Description:选择输入卡/账号或者申请客服帮助
 * @author zjg
 * @date 2018年3月26日 上午9:33:47
 */
@SuppressWarnings("static-access")
public class LostSelectEnterAccOrCustServicePage extends BaseTransPanelNew {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger
			.getLogger(LostSelectEnterAccOrCustServicePage.class);
	private boolean on_off = true;// 用于控制页面跳转的开关

	public LostSelectEnterAccOrCustServicePage() {

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

		// 选择输入账号/卡号
		UtilButton Button1 = new UtilButton("pic/newPic/enterAccNo.jpg",
				"pic/newPic/enterAccNo.jpg");
		Button1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择输入账号/卡号");
				if (!on_off) {
					logger.info("开关当前状态" + on_off);
					return;
				}
				logger.info("开关当前状态" + on_off);
				on_off = false;

				clearTimeText();
				stopTimer(voiceTimer);// 关闭语音
				closeVoice();// 关闭语音流

				enterAcc();
			}

		});
		Button1.setBounds(400, 174, 200, 300);
		this.showJpanel.add(Button1);

		// 选择申请客服帮助
//		UtilButton Button2 = new UtilButton("pic/newPic/customService.jpg",
//				"pic/newPic/customService.jpg");
//		Button2.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				logger.info("选择申请客服帮助");
//				if (!on_off) {
//					logger.info("开关当前状态" + on_off);
//					return;
//				}
//				logger.info("开关当前状态" + on_off);
//				on_off = false;
//
//				clearTimeText();
//				stopTimer(voiceTimer);// 关闭语音
//				closeVoice();// 关闭语音流
//
//				custHelp();
//			}
//
//		});
//		Button2.setBounds(585, 174, 200, 300);
//		this.showJpanel.add(Button2);

		// 上一步
		JLabel back = new JLabel(new ImageIcon("pic/preStep.png"));
		back.setBounds(1075, 770, 150, 50);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
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
	 * 选择输入账号/卡号
	 */
	public void enterAcc() {
		
		//跳转输入账号/卡号页
		openPanel(new LostEnterAccNoMsgPage());

	}

	/**
	 * 选择申请客服帮助
	 */
//	public void custHelp() {
//
//
//	}

	/**
	 * 返回上一步
	 */
	public void back() {
		lostPubBean.setUpStepName("ALL_PUBLIC_PRINT_CAMERA_PANEL");
		openPanel(new LostPassSelectPage());
	}

	/**
	 * 退出交易
	 */
	public void exit() {

		returnHome();
	}

}
