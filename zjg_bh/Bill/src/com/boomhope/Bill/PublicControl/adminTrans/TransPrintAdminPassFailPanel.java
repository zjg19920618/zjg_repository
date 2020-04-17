package com.boomhope.Bill.PublicControl.adminTrans;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.PublicControl.adminTrans.Bean.AdminBean;
import com.boomhope.Bill.Util.SoftKeyBoardPopups1;

/**
 * 
 * title:输入密码失败界面
 * @author ly
 * 2016年11月8日上午11:18:29
 */
public class TransPrintAdminPassFailPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransPrintAdminPassFailPanel.class);
	private static final long serialVersionUID = 1L;
	//final int delaySecond = 20;
	Timer delayTimer;
	public static JPasswordField passwordField;
	private SoftKeyBoardPopups1 keyPopup;
	private JPanel passwordPanel;
	JLabel label_1;
	JLabel label_2;
	private AdminBean adminBean;

	/***
	 * 初始化
	 */
	public TransPrintAdminPassFailPanel(AdminBean AdminBean) {

		this.adminBean = AdminBean;
		GlobalPanelFlag.CurrentFlag = GlobalPanelFlag.PRINT_ADMIN_PASS_FAIL;

		//输入框
        passwordPanel = new JPanel(new BorderLayout());  
        passwordPanel.setBackground(Color.WHITE);  
        passwordPanel.setPreferredSize(new Dimension(2024, 30));  
        passwordPanel.setLayout(new BorderLayout());  
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));  
        add(passwordPanel); 

		/* 标题信息 */
		JLabel label = new JLabel("管理员登录");
		label.setBounds(440, 182, 159, 24);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("微软雅黑", Font.BOLD, 24));
		add(label);
		JLabel titleLabel = new JLabel("密码:");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
		titleLabel.setBounds(354, 280, 90, 40);
		this.add(titleLabel);

		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(JLabel.CENTER);
		passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		passwordField.setBounds(440, 280, 242, 40);
		passwordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				scanBill12();
			}
		});
		add(passwordField);
		passwordField.setColumns(10);
		keyPopup = new SoftKeyBoardPopups1(passwordField);
		
		
		
		
		JButton backbtn = new JButton("返回",new ImageIcon("pic/111.png"));
		backbtn.setBounds(244, 638, 140, 50);
		backbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scanBill1();
			}
		});
		backbtn.setHorizontalTextPosition(SwingConstants.CENTER);
		backbtn.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		backbtn.setOpaque(false);//设置控件是否透明，true为不透明，false为透明  
		backbtn.setContentAreaFilled(false);//设置图片填满按钮所在的区域  
		backbtn.setFocusPainted(true);//设置这个按钮是不是获得焦点  
		backbtn.setBorderPainted(false);//设置是否绘制边框  
		backbtn.setBorder(null);//设置边框  
		add(backbtn);
		/* 返回首页 */
//		UtilButton backButton = new UtilButton("pic/111.png",
//				"pic/back_step.png");
//		backButton.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				scanBill1();
//			}
//
//		});		
//		backButton.setSize(200, 50);
//		backButton.setBounds(244, 638, 200, 50);
//		backButton.setIcon(new ImageIcon("pic/111.png"));
//		this.add(backButton);
		JButton backbtn1 = new JButton("登录",new ImageIcon("pic/111.png"));
		backbtn1.setBounds(676, 638, 140, 50);
		backbtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 pass();
			}
		});
		backbtn1.setHorizontalTextPosition(SwingConstants.CENTER);
		backbtn1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		backbtn1.setOpaque(false);//设置控件是否透明，true为不透明，false为透明  
		backbtn1.setContentAreaFilled(false);//设置图片填满按钮所在的区域  
		backbtn1.setFocusPainted(true);//设置这个按钮是不是获得焦点  
		backbtn1.setBorderPainted(false);//设置是否绘制边框  
		backbtn1.setBorder(null);//设置边框  
		add(backbtn1);
		/* 登录 */
//		UtilButton backButton1 = new UtilButton("pic/111.png", "pic/111.png");
//		backButton1.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				//scanBill();
//			}
//
//		});
//		backButton1.setSize(200, 50);
//		backButton1.setBounds(676, 638, 200, 50);
//		backButton1.setIcon(new ImageIcon("pic/111.png"));
//		this.add(backButton1);
//		
//		JButton button = new JButton("退出");
//		button.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				scanBill1();
//			}
//		});
//		button.setBounds(291, 570, 93, 23);
//		add(button);
//		
//		JButton button_1 = new JButton("登录");
//		button_1.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				 pass();
//			}
//		});
//		button_1.setBounds(739, 570, 93, 23);
//		add(button_1);
		/**
		 * 提示信息
		 */
		label_1 = new JLabel("");
		label_1.setBounds(479, 343, 242, 50);
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_1.setForeground(Color.red);
		add(label_1);
		
		label_2= new JLabel("提示:密码输入错误，请重新输入！");
		label_2.setBounds(425, 330, 311, 58);
		label_2.setFont(new Font("微软雅黑", Font.BOLD, 16));
		label_2.setForeground(Color.red);
		add(label_2);
		
	}
	
	
	/***
	 * 键盘
	 */
	private void scanBill12(){
//		keyPopup.show(passwordPanel, 2000, passwordPanel.getPreferredSize().height);
		keyPopup.show(passwordPanel, 360, 350);
		 String a= keyPopup.getSoftKeyBoardPanel().reset().getText();
//         char[] cs = a.getPassword();
      
         char[] password1=a.toCharArray();
         String psw = new String(password1);
         logger.info("页面" + psw);
      
     
	}
	
	/*
	 * 密码长度验证
	 */
	private void pass(){
		  String a= keyPopup.getSoftKeyBoardPanel().reset().getText();  
	      char[] password1=a.toCharArray();
	      String psw = new String(password1);
		  logger.info("页面" + psw);
		  if(psw.length()==0){
			  label_2.setVisible(false);
			label_1.setText("密码不能为空！！！");	
			}
		  else if(psw.length()!=6){
			  label_2.setVisible(false);
				label_1.setText("密码只能输入6位！！！");
			}
		  else{
			  adminBean.setAdminName("test");
			  adminBean.setPaw(psw);
			  scanBill();
		  }
	}
	/***
	 * 登录
	 */
	private void scanBill() {
		// 跳到密码过渡页
		
		openPanel(new TransPrintCheckingAdminPassPanel(adminBean));
	}

	/***
	 * 返回首页
	 */
	private void scanBill1() {

		// delayTimer.stop();

	}
}
