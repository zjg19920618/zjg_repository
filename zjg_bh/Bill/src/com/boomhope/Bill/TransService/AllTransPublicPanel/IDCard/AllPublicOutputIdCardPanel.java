package com.boomhope.Bill.TransService.AllTransPublicPanel.IDCard;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.Util.UtilVoice;

/**
 * 证件退出
 * 
 * @author gyw
 * 
 */
public class AllPublicOutputIdCardPanel extends BaseTransPanelNew {
	final int voiceSecond = 500;
	UtilVoice utilVoice = null;
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AllPublicOutputIdCardPanel.class);
	private boolean on_off=true;

	@SuppressWarnings("static-access")
	public AllPublicOutputIdCardPanel() {
		logger.info("进入退出身份证页面");
		// 将当前页面传入流程控制进行操作
		baseTransBean.setThisComponent(this);
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
				try {
					closeLed("1");
				} catch (Exception e1) {
					logger.error("LED灯关闭失败" + e);
				}
				excuteVoice("voice/custody.wav");

			}
		});
		voiceTimer.start();
		/* 显示时间倒计时 */
		if(delayTimer!=null){
			clearTimeText();
		}
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000,
				new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("退出身份证页面倒计时结束 ");
				/* 倒计时结束退出交易 */
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		delayTimer.start();
		/* 标题信息 */
		JLabel t = new JLabel("证件已退出，请妥善保管");
		t.setHorizontalAlignment(JLabel.CENTER);
		t.setFont(new Font("微软雅黑", Font.BOLD, 40));
		t.setBounds(0, 129, GlobalParameter.TRANS_WIDTH - 50, 40);
		this.showJpanel.add(t);
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();
		billImage.setIcon(new ImageIcon("pic/output_idCard.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(324, 236, 362, 320);
		this.showJpanel.add(billImage);

		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 处理上一页 */
				preStep();
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
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 处理下一页 */
				nextStep();
			}
		});
		confirm.setSize( 150, 50);
		confirm.setLocation(890, 770);
		add(confirm);
		
		// 返回
		JLabel backButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
				clearTimeText();
				accCancelExit();
			}
		});
		add(backButton);
	}

	/**
	 * 下一步
	 */
	public void nextStep() {
		logger.info("进入确认方法");
		// 关闭语音
		try {
			closeLed("1");
		} catch (Exception e1) {
			logger.error("LED灯关闭失败" + e1);
		}
		
		closeVoice();
		clearTimeText();
		stopTimer(voiceTimer);
		allPubTransFlow.transFlow();
			
	}

	/**
	 * 上一步
	 */
	public void preStep() {
		logger.info("进入上一步方法");
		// 关闭语音
		closeVoice();
		clearTimeText();
		stopTimer(voiceTimer);
		backStepMethod();
		allPubTransFlow.transFlow();
	}

}
