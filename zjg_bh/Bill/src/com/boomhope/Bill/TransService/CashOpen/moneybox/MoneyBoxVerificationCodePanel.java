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

import javax.sound.sampled.SourceDataLine;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.boomhope.Bill.Driver.KeypadDriver;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.SoftKeyBoardPopups2;
import com.boomhope.Bill.Util.UtilVoice;

/**
 * 请输入验证码
 * @author gyw
 *
 */
public class MoneyBoxVerificationCodePanel extends BaseTransPanelNew{

	private static final long serialVersionUID = 1L;
	Timer checkTimer = null;
	static Logger logger = Logger.getLogger(MoneyBoxVerificationCodePanel.class);	
	UtilVoice utilVoice = null;
	SourceDataLine line = null;
	private JTextField textField;
	private JPanel passwordPanel1;
	private JPanel passwordPanel2;
	private SoftKeyBoardPopups2 keyPopup1;
	private SoftKeyBoardPopups2 keyPopup2;
	JLabel errInfo = null;// 错误提示
	JLabel errInfo1 = null;// 错误提示
	JLabel errInfo2 = null;// 错误提示
	JLabel lblNewLabel_11;
	JLabel lblNewLabel_13;
	//验证码测试
	String aa ="123456";
	private boolean on_off=true;//开关控制
	public MoneyBoxVerificationCodePanel(final PublicCashOpenBean transBean){
		
		this.cashBean = transBean;
		GlobalPanelFlag.CurrentFlag = GlobalPanelFlag.MONEYBOX_VERIFICATION_CODE;
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);
            	excuteVoice("voice/authCode.wav");
            	
            }      
        });            
		voiceTimer.start(); 
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
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请输入手机验证码");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
		titleLabel.setBounds(0, 100, 1059, 35);
		this.showJpanel.add(titleLabel);
		
		JLabel lblNewLabel_7 = new JLabel("手  机  号：");
		lblNewLabel_7.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		lblNewLabel_7.setBounds(368, 302, 130, 30);
		this.showJpanel.add(lblNewLabel_7);
		
//		JLabel lblNewLabel_8 = new JLabel("New label");
//		lblNewLabel_8.setFont(new Font("微软雅黑", Font.PLAIN, 25));
//		lblNewLabel_8.setBounds(383, 374, 284, 30);
//		add(lblNewLabel_8);
		
//		JButton btnNewButton = new JButton("获取验证码");
//		btnNewButton.setBounds(590, 425, 93, 31);
//		add(btnNewButton);
		
		JButton btnNewButton = new JButton(new ImageIcon("pic/verification.png"));
		btnNewButton.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnNewButton.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		btnNewButton.setContentAreaFilled(false);// 设置图片填满按钮所在的区域
		btnNewButton.setFocusPainted(true);// 设置这个按钮是不是获得焦点
		btnNewButton.setBorderPainted(false);// 设置是否绘制边框
		btnNewButton.setBorder(null);// 设置边框
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				/* 处理上一页 */
				nextStep2();
			}
		});
		btnNewButton.setSize(93, 31);
		btnNewButton.setLocation(576, 421);
		this.showJpanel.add(btnNewButton);
		
		// 输入框
		passwordPanel2 = new JPanel(new BorderLayout());
		passwordPanel2.setBackground(Color.WHITE);
		passwordPanel2.setPreferredSize(new Dimension(2024, 30));
		passwordPanel2.setLayout(new BorderLayout());
		passwordPanel2.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel2);
		
		
		textField = new JTextField();
		textField.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		textField.setBounds(368, 422, 130, 30);
//		add(textField);
//		textField.setColumns(10);
		
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					closePwd();
				} catch (Exception e1) {
					logger.error("密码键盘设备故障!没有正常关闭");
				}
				/* 创建调用外设线程 */
				createThread3();
				scanBill13();
			}

		});
		textField.setColumns(10);
		this.showJpanel.add(textField);
		keyPopup2 = new SoftKeyBoardPopups2(textField);
		
		
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
				nextStep1(transBean);
			}

		});
		label.setBounds(1075, 770, 150, 50);
		this.add(label);
		

		// 输入框
		passwordPanel1 = new JPanel(new BorderLayout());
		passwordPanel1.setBackground(Color.WHITE);
		passwordPanel1.setPreferredSize(new Dimension(2024, 30));
		passwordPanel1.setLayout(new BorderLayout());
		passwordPanel1.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel1);
		
	    lblNewLabel_11 = new JLabel("请输入验证码");
		lblNewLabel_11.setForeground(Color.RED);
		lblNewLabel_11.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_11.setBounds(389, 462, 79, 15);
		lblNewLabel_11.setVisible(false);
		this.showJpanel.add(lblNewLabel_11);
		
	    lblNewLabel_13 = new JLabel("验证码检验失败");
		lblNewLabel_13.setForeground(Color.RED);
		lblNewLabel_13.setBounds(389, 462, 93, 15);
		lblNewLabel_13.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_13.setVisible(false);
		this.showJpanel.add(lblNewLabel_13);
		
		JLabel lblNewLabel1 = new JLabel("New label");
		lblNewLabel1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lblNewLabel1.setBounds(576, 304, 289, 30);
		this.showJpanel.add(lblNewLabel1);
	}
	
	/**
	 * 上一步
	 * */
	public void nextStep1(PublicCashOpenBean transBean) {
		clearTimeText();
		openPanel(new MoneyBoxJellyBeanPanel(transBean));
		
	}
	
	
	/**
	 * 下一步
	 * */
	public void nextStep(PublicCashOpenBean transBean) {

		if( !textField.getText().equals("") && !textField.getText().equals("null")){
			  if(!textField.getText().equals(aa)){
					lblNewLabel_11.setVisible(false);
					lblNewLabel_13.setVisible(true);
			  }
			  if(textField.getText().equals(aa)){
				  lblNewLabel_11.setVisible(false);
					lblNewLabel_13.setVisible(false);
					clearTimeText();
					openPanel(new MoneyBoxWithCloseTopayPanel(transBean));
					return;
			  }
			  on_off=true;
		}else{	
			if(textField.getText().equals("") || textField.getText().equals("null") ){
				lblNewLabel_11.setVisible(true);
				lblNewLabel_13.setVisible(false);
			}else{				
				if(!textField.getText().equals(aa)){
					lblNewLabel_11.setVisible(false);
					lblNewLabel_13.setVisible(true);
				}else{
					lblNewLabel_11.setVisible(false);
					lblNewLabel_13.setVisible(false);
				} 
			}
			on_off=true;
		}
		

	}
	
	private void createThread2() {
		new Thread(){
			@Override
			public void run(){
				inputPassword2();
			}
		}.start();
	}
	private void inputPassword2() {
		try {
			new KeypadDriver().getKnowID(this, "","","4","8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/***
	 * 键盘
	 */
	private void scanBill12(){		
		logger.debug(1);  
		keyPopup1.show(passwordPanel1, 400, 490);
	
  
     
	}
	private void createThread3() {
		new Thread(){
			@Override
			public void run(){
				inputPassword3();
			}
		}.start();
	}
	private void inputPassword3() {
			try {
				new KeypadDriver().getKnowID(this," ","","4","21");
			} catch (Exception e) {
				logger.error("密码键盘故障"+e);
			}
		
	}
	/***
	 * 键盘
	 */
	private void scanBill13(){
		logger.debug(1); 
		keyPopup2.show(passwordPanel2, 400, 490);
	
     
	}
	/**
	 * 获取验证码按钮
	 * */
	public void nextStep2() {
		
		
	}
}
