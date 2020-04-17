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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.boomhope.Bill.Driver.KeypadDriver;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.SoftKeyBoardPopups2;
import com.boomhope.Bill.Util.UtilButton;
/**
 *手动输入订单号
 * @author gyw
 *   
 */
public class MoneyBoxOrderNumberPanel extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(MoneyBoxOrderNumberPanel.class);
	JLabel label = null;
	private static final long serialVersionUID = 1L;
	public JPasswordField txtPassWord;
	public JTextField txtOrderNo;
	private JPanel JpPasswordPanel2;
	private UtilButton btnNext;
	private UtilButton btnPre;
	private SoftKeyBoardPopups2 keyPopup2;
	JLabel errInfo = null;// 错误提示
	JLabel errInfo1 = null;// 错误提示
	private JLabel jlOrderNo;
	JLabel errInfo2 = null;// 错误提示
	
	private boolean on_off=true;//开关控制
	public MoneyBoxOrderNumberPanel(final PublicCashOpenBean transBean) {
		this.cashBean = transBean;
		//设置开关
		GlobalParameter.OFF_ON = false;
		
		GlobalPanelFlag.CurrentFlag = GlobalPanelFlag.ORDER_NUMBER;
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
				excuteVoice("voice/orderNumbers.wav");

			}
		});
		voiceTimer.start();
		
		/* 标题信息 */
		JLabel lblTitle = new JLabel("请输入14位订单号");
		lblTitle.setHorizontalAlignment(JLabel.CENTER);
		lblTitle.setFont(new Font("微软雅黑", Font.BOLD, 40));
		lblTitle.setForeground(Color.decode("#412174"));
		lblTitle.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(lblTitle);	
		
		JLabel lblOrderNo = new JLabel("订单号：");
		lblOrderNo.setFont(new Font("宋体", Font.PLAIN, 30));
		lblOrderNo.setBounds(155, 326, 120, 50);
		this.showJpanel.add(lblOrderNo);

		/* 订单号 */
		txtOrderNo = new JTextField();
		txtOrderNo.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		txtOrderNo.setBounds(290, 326, 580, 50);
		txtOrderNo.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
		txtOrderNo.setEditable(false);

		// add(textField);
		txtOrderNo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				/*try {
					String res = closePwd();
					if(!res.equals("0")){
						logger.error("密码键盘设备故障!没有正常关闭");
					}
				} catch (Exception e1) {
					logger.error("密码键盘设备故障!没有正常关闭");
				}
				 创建调用外设线程 
				createThread3();*/
				//scanBill13();
			}

		});
		txtOrderNo.setColumns(10);
		this.showJpanel.add(txtOrderNo);
		
		// 输入框
		JpPasswordPanel2 = new JPanel(new BorderLayout());
		JpPasswordPanel2.setBackground(Color.WHITE);
		JpPasswordPanel2.setPreferredSize(new Dimension(2024, 30));
		JpPasswordPanel2.setLayout(new BorderLayout());
		JpPasswordPanel2.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(JpPasswordPanel2);
		//账号
		
		keyPopup2 = new SoftKeyBoardPopups2(txtOrderNo);


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
				nextStep(transBean);
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
				preStep(transBean);
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
		
		jlOrderNo = new JLabel("提示：请输入14位订单号!");
		jlOrderNo.setVisible(false);
		jlOrderNo.setForeground(Color.RED);
		jlOrderNo.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		jlOrderNo.setBounds(517, 386, 258, 23);
		this.showJpanel.add(jlOrderNo);				
		
		/* 创建调用外设线程 */
		createThread();
	}
	
	private void createThread() {
		new Thread(){
			@Override
			public void run(){
				inputPassword();
			}
		}.start();
	}
	
	private void inputPassword() {
		try {	
			new KeypadDriver().getKnowID(this, "txtOrderNo", "4", "40","20");
		} catch (Exception e) {
			logger.error("密码键盘设备故障");
		}
	}
	
	
	/**
	 * 下一步
	 */
	public void nextStep(PublicCashOpenBean transBean){
		
		String res;
		try {
			res = closePassword();
			if(!res.equals("0")){
				logger.error("密码键盘设备故障!没有正常关闭");
			}
		} catch (Exception e) {
			logger.error("密码键盘设备故障!没有正常关闭");
		}
		clearTimeText();
		String strOrderNo;	    
	    strOrderNo = txtOrderNo.getText();	
	    if( strOrderNo.length() != 14){
	    	jlOrderNo.setVisible(true);
	    	createThread();
	    	on_off=true;
			return;
			
	    }
	    if(StringUtils.isBlank(strOrderNo) ){
			jlOrderNo.setVisible(true);
			createThread();
			on_off=true;
			return;
		}	
		// 传递参数值
		// 1.订单号
		transBean.setOrderNo(strOrderNo);
		//二维码跳转标识
		transBean.setQrCode("2");
		openPanel(new MoneyBoxPasswordPanel(transBean));
		
	}
	/**
	 * 上一步
	 */
	public void preStep(PublicCashOpenBean transBean){
		
		
		String res;
		try {
			res = closePassword();
			if(!res.equals("0")){
				logger.error("密码键盘设备故障!没有正常关闭");
			}
		} catch (Exception e) {
			logger.error("密码键盘设备故障!没有正常关闭");
		}
		clearTimeText();
		openPanel(new MoneyBoxEntryModePanel(transBean));
				
	}
	
	
	
	
	
	
	/**
	 * 密码键盘明文输入银行卡号，调用关闭密码键盘
	 */
	public String closePassword() throws Exception{
		if(!KeypadDriver.socket.isClosed()&&!KeypadDriver.socket.isInputShutdown()){
			KeypadDriver.socket.shutdownInput();
		}if(!KeypadDriver.socket.isClosed()&&!KeypadDriver.socket.isOutputShutdown()){
			KeypadDriver.socket.shutdownOutput();
		}
		Thread.sleep(100);
		String res = new KeypadDriver().closePwd("6");
		return res;
	}
	public JTextField getTxtOrderNo() {
		return txtOrderNo;
	}

	public void setTxtOrderNo(JTextField txtOrderNo) {
		this.txtOrderNo = txtOrderNo;
	}
	
}
