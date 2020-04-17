package com.boomhope.Bill.PublicControl.adminTrans;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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

import com.boomhope.Bill.Framework.BaseContentPanel;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.adminTrans.Bean.AdminBean;
import com.boomhope.Bill.Util.SoftKeyBoardPopups1;

/**
 * 
 * title:输入密码界面
 * @author ly
 * 2016年11月8日上午11:18:29
 */
public class TransPrintAdminLogin extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransPrintAdminLogin.class);
	private static final long serialVersionUID = 1L;
	//final int delaySecond = 20;
	public static JPasswordField passwordField;
	private SoftKeyBoardPopups1 keyPopup;
	private JPanel passwordPanel;
	JLabel label_1;
	
	/***
	 * 初始化
	 */
	public TransPrintAdminLogin() {

		GlobalPanelFlag.CurrentFlag = GlobalPanelFlag.PRINT_ADMIN_LOGIN;
		//输入框
        passwordPanel = new JPanel(new BorderLayout());  
        passwordPanel.setBackground(Color.WHITE);  
        passwordPanel.setPreferredSize(new Dimension(2024, 30));  
        passwordPanel.setLayout(new BorderLayout());  
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));  
        this.showJpanel.add(passwordPanel); 

		/* 标题信息 */
		JLabel label = new JLabel("管理员登录");
		label.setBounds(425, 150, 159, 28);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("微软雅黑", Font.BOLD, 24));
		this.showJpanel.add(label);
		JLabel titleLabel = new JLabel("密码:");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
		titleLabel.setBounds(330, 260, 90, 40);
		this.showJpanel.add(titleLabel);

		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(JLabel.CENTER);
		passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		passwordField.setBounds(415, 260, 242, 40);
		passwordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				scanBill12();
			}
		});
		this.showJpanel.add(passwordField);
		passwordField.setColumns(10);
		keyPopup = new SoftKeyBoardPopups1(passwordField);
		
		
		
		
		JButton backbtn = new JButton(new ImageIcon("pic/newPic/exit.png"));
		backbtn.setBounds(1258, 770, 150, 50);
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
		
		JButton backbtn1 = new JButton(new ImageIcon("pic/newPic/confirm.png"));
		backbtn1.setBounds(1075, 770, 150, 50);
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

		/**
		 * 提示信息
		 */
		label_1 = new JLabel("");
		label_1.setBounds(479, 343, 242, 50);
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_1.setForeground(Color.red);
		this.showJpanel.add(label_1);
		
		
	}
	
	
	/***
	 * 键盘
	 */
	private void scanBill12(){
//		System.out.println(1); 
//		keyPopup.show(passwordPanel, 2000, passwordPanel.getPreferredSize().height);
		keyPopup.show(passwordPanel, 360, 350);
		 String a= keyPopup.getSoftKeyBoardPanel().reset().getText();
//         char[] cs = a.getPassword();
      
         char[] password1=a.toCharArray();
         String psw = new String(password1);
//         System.out.println("页面" + psw);
      
     
	}
	
	/*
	 * 密码长度验证
	 */
	private void pass(){
		  String a= keyPopup.getSoftKeyBoardPanel().reset().getText();  
	      char[] password1=a.toCharArray();
	      String psw = new String(password1);
		  //System.out.println("页面" + psw);
		  if(psw.length()==0){
			label_1.setText("密码不能为空！！！");	
			}
		  else if(psw.length()!=6){
				label_1.setText("密码只能输入6位！！！");
			}
		  else{
			  AdminBean adminBean = new AdminBean();
			  adminBean.setAdminName(GlobalParameter.machineNo);
			  adminBean.setPaw(psw);
			  scanBill(adminBean);
		  }
	}
	

	/***
	 * 登录
	 */
	private void scanBill(AdminBean AdminBean) {
		// 跳到密码过渡页
		openPanel(new TransPrintCheckingAdminPassPanel(AdminBean));
	}

	/***
	 * 返回首页
	 */
	private void scanBill1() {
		returnHome();
	}
}
