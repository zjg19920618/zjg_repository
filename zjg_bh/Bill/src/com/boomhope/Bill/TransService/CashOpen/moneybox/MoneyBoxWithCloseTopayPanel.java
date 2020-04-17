package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.boomhope.Bill.Driver.KeypadDriver;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalDriver;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.Property;

/***
 * 输入凭密支付（1）
 * @author gyw
 *
 */
public class MoneyBoxWithCloseTopayPanel extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(MoneyBoxWithCloseTopayPanel.class);
	private static final long serialVersionUID = 1L;
	public JTextField passwordField;
	JLabel errInfo = null;// 错误提示
	JLabel errInfo1 = null;// 错误提示
	public MoneyBoxWithCloseTopayPanel(final PublicCashOpenBean transBean){
		this.cashBean = transBean;
		
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请输入存单密码");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(titleLabel);

		/* 显示时间倒计时 */
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
				excuteVoice("voice/qsrcdmm.wav");

			}
		});
		voiceTimer.start();
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 36));
		passwordField.setBounds(319, 300, 450, 40);
		this.showJpanel.add(passwordField);
		
		errInfo = new JLabel("提示：请输入存单密码!");
		errInfo.setVisible(false);
		errInfo.setForeground(Color.RED);
		errInfo.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		errInfo.setBounds(465, 346, 258, 23);
		this.showJpanel.add(errInfo);
		
		if("0".equals(transBean.getImportMap().get("pasError"))){
			errInfo1 = new JLabel("提示：两次录入的存单密码不相同，请重新录入!");
			errInfo1.setForeground(Color.RED);
			errInfo1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
			errInfo1.setBounds(400, 346, 558, 23);
			this.showJpanel.add(errInfo1);
			errInfo1.setVisible(true);
			transBean.getImportMap().put("pasError","");
		}
		
		
		
		/* 创建调用外设线程 */
		createThread(transBean);
	}
	/***
	 * 创建调用外设线程
	 */
	private void createThread(final PublicCashOpenBean transBean){
		
		Thread thread  = new Thread("InputPasswordThread"){
			@Override
			public void run(){
				inputPassword(transBean);
			}
		};
		thread.start();
	}
	/***
	 * 调用外设输入密码
	 */
	private void inputPassword(PublicCashOpenBean transBean){
		try {
			closePwd();
			Map<String, String> reqMap = new HashMap<String, String>();
			reqMap.put("PassLength", GlobalDriver.KEYPAD_PASSLENGTH);	// 密码长度
			reqMap.put("EncType", GlobalDriver.KEYPAD_ENCTYPE);	// 加密方式
			reqMap.put("PinType", GlobalDriver.KEYPAD_PINTYPE);	// pin类型
			reqMap.put("PinFill", GlobalDriver.KEYPAD_PINFILL);	// pin补位方式
			reqMap.put("CardNo", "0000000000000000");	// 存单号
			
			Map<String, String> resMap;
			try {
				resMap = new KeypadDriver().getInputPassword(this, "passwordField", "nextStep", reqMap);
				if (resMap.get("ResCode").endsWith("S")){
					logger.info("密码获取成功!");
					nextStep(transBean,resMap.get("Password"));
				}
			} catch (Exception e) {
				logger.error(e);
			}
		} catch (Exception e) {
			logger.error("密码键盘设备故障");
		}	
	}
	
	/**
	 * 下一步
	 */
	public void nextStep(PublicCashOpenBean transBean,String pass){
		logger.info(pass);
		transBean.setSubPwd(pass);
		transBean.setLimit("1");
		clearTimeText();
		openPanel(new MoneyBoxAgainWithCloseTopayPanel(transBean));
					
	}
	
	
	
	public JTextField getPasswordField() {
		return passwordField;
	}
	public void setPasswordField(JTextField passwordField) {
		this.passwordField = passwordField;
	}
	


}
