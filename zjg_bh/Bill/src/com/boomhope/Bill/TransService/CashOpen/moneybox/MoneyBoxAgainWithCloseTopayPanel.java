package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Driver.KeypadDriver;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalDriver;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.Property;

/***
 * 输入凭密支付（2）
 * @author gyw
 *
 */
public class MoneyBoxAgainWithCloseTopayPanel extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(MoneyBoxAgainWithCloseTopayPanel.class);
	private static final long serialVersionUID = 1L;
	public JTextField passwordField;
	JLabel errInfo = null;// 错误提示
	JLabel errInfo1 = null;// 错误提示
	JLabel errInfo2 = null;// 错误提示
	public MoneyBoxAgainWithCloseTopayPanel(final PublicCashOpenBean transBean){
		this.cashBean = transBean;
		final PublicCashOpenBean transBean1=new PublicCashOpenBean();
		transBean1.setAccountCardNo(transBean.getAccountCardNo());
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请再次输入存单密码");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD,40));
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
				excuteVoice("voice/qzcsrcdmm.wav");

			}
		});
		voiceTimer.start();
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 36));
		passwordField.setBounds(319, 300, 450, 40);
		this.showJpanel.add(passwordField);
		errInfo = new JLabel("提示：请输入存单密码!");
		errInfo.setVisible(false);
		errInfo.setForeground(Color.decode("#ff0101"));
		errInfo.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		errInfo.setBounds(292, 310, 258, 23);
		this.showJpanel.add(errInfo);
		
		/* 下一步 */
//		UtilButton backButton = new UtilButton("pic/next_step.png","pic/next_step.png");
//		backButton.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				nextStep(transBean1);
//			}
//
//		});
//		backButton.setSize(200, 50);
//		backButton.setBounds(780, 638, 200, 50);
//		backButton.setIcon(new ImageIcon("pic/next_step.png"));
//		add(backButton);
//		JButton testButton = new JButton("测试按键");
//		testButton.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				openPanel(new MoneyBoxInputBankCardPanel(transBean));
//			}
//		});
//		testButton.setSize(150, 30);
//		testButton.setLocation(508, 660);
//		Property property = new Property();
//		if(property.getProperties().getProperty("push_button").equals("false")){
//			testButton.setVisible(false);
//		}else if (property.getProperties().getProperty("push_button").equals("true")){
//			testButton.setVisible(true);
//		}
//		this.showJpanel.add(testButton);
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
					logger.error("密码获取成功!");
					nextStep(transBean, resMap.get("Password"));
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
	public void nextStep(final PublicCashOpenBean transBean,String pass){
		clearTimeText();	
		if(pass.trim().equals(transBean.getSubPwd())){
			//判断是否是立得存
			if(transBean.getProductCode().subSequence(0, 2).equals("LT")||transBean.getProductCode().subSequence(0, 2).equals("LZ")){
				//暂时注释掉使用时打开
				openPanel(new MoneyBoxInputBankCardPanel(transBean));
					
			}else{
				//如果是新建客户或者金额大于等于5万走授权流程
				String customer_identification = transBean.getImportMap().get("customer_identification");
				if(transBean.getMoney()>=50000.00){
					//判断跳转如果是等于yes就是代理人代理 跳转代理人页面
					if("yes".equals(transBean.getImportMap().get("agent_persion"))){
						//跳转代理人授权页面
						openPanel(new MoneyBoxExistProcuratorPanel(transBean));
						
					}else if ("no".equals(transBean.getImportMap().get("agent_persion"))){
						//跳转本人授权页面
						openPanel(new MoneyBoxNegationProcuratorPanel(transBean));
						
					}							
				}
				//判断 金额如果小于5万走简单流程 
				if(transBean.getMoney()<50000.00){
					//确认存单信息页面
					openPanel(new MoneyBoxOkInputDepinfoPanel(transBean));
					
				}

			}
		}else{
			transBean.getImportMap().put("pasError", "0");
			//跳转第一次录入密码页重新输密码
			openPanel(new MoneyBoxWithCloseTopayPanel(transBean));
			
		
		}
	}

	public JTextField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JTextField passwordField) {
		this.passwordField = passwordField;
	}

}
