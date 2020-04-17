package com.boomhope.Bill.PublicControl.adminTrans;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.PublicControl.adminTrans.Bean.AdminBean;
import com.boomhope.Bill.Socket.SocketClient;
import com.boomhope.Bill.Util.SoftKeyBoardPopups1;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InResBean;
import com.boomhope.tms.message.in.tms.Tms0004ResBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * title:修改密码
 * @author ly
 * 2016年11月8日下午3:31:58
 */
public class TransPrintChangePassPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransPrintChangePassPanel.class);
	private static final long serialVersionUID = 1L;
	//final int delaySecond = 20;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;
	private SoftKeyBoardPopups1 keyPopup;
	private SoftKeyBoardPopups1 keyPopup1;
	private SoftKeyBoardPopups1 keyPopup2;
	private JPanel passwordPanel;
	JLabel lblNewLabel;
	private AdminBean adminBean;

	/***
	 * 初始化
	 */
	public TransPrintChangePassPanel(AdminBean AdminBean) {

		this.adminBean = AdminBean;
		GlobalPanelFlag.CurrentFlag = GlobalPanelFlag.PRINT_CHANGE_PASS;
		//输入框
        passwordPanel = new JPanel(new BorderLayout());  
        passwordPanel.setBackground(Color.WHITE);  
        passwordPanel.setPreferredSize(new Dimension(2024, 30));  
        passwordPanel.setLayout(new BorderLayout());  
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));  
        this.showJpanel.add(passwordPanel); 
		/* 标题信息 */
		JLabel label = new JLabel("修改密码");
		label.setBounds(410, 110, 159, 33);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("微软雅黑", Font.BOLD, 24));
		this.showJpanel.add(label);
		
		JLabel label_1 = new JLabel("原密码：");
		label_1.setBounds(360, 190, 107, 33);
		label_1.setHorizontalAlignment(JLabel.CENTER);
		label_1.setFont(new Font("微软雅黑", Font.BOLD, 18));
		this.showJpanel.add(label_1);
		
		JLabel label_2 = new JLabel("新密码：");
		label_2.setBounds(360, 250, 107, 35);
		label_2.setHorizontalAlignment(JLabel.CENTER);
		label_2.setFont(new Font("微软雅黑", Font.BOLD, 18));
		this.showJpanel.add(label_2);
		
		JLabel label_3 = new JLabel("确认密码：");
		label_3.setBounds(360, 310, 116, 33);
		label_3.setHorizontalAlignment(JLabel.CENTER);
		label_3.setFont(new Font("微软雅黑", Font.BOLD, 18));
		this.showJpanel.add(label_3);
		/*
		 * 原密码输入框
		 */
		passwordField = new JPasswordField();
		passwordField.setBounds(480, 190, 165, 40);
		passwordField.setHorizontalAlignment(JLabel.CENTER);
		passwordField.setFont(new Font("微软雅黑", Font.BOLD, 24));
		passwordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				scanBill1();
			}
		});
		this.showJpanel.add(passwordField);
		keyPopup = new SoftKeyBoardPopups1(passwordField);
		/*
		 * 新密码输入框
		 */
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(480, 250, 165, 40);
		passwordField_1.setHorizontalAlignment(JLabel.CENTER);
		passwordField_1.setFont(new Font("微软雅黑", Font.BOLD, 24));
		passwordField_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				scanBill2();
			}
		});
		this.showJpanel.add(passwordField_1);
		keyPopup1 = new SoftKeyBoardPopups1(passwordField_1);
		/*
		 * 确认新密码输入框
		 */
		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(480, 310, 165, 40);
		passwordField_2.setHorizontalAlignment(JLabel.CENTER);
		passwordField_2.setFont(new Font("微软雅黑", Font.BOLD, 24));
		passwordField_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				scanBill3();
			}
		});
		this.showJpanel.add(passwordField_2);
		keyPopup2 = new SoftKeyBoardPopups1(passwordField_2);
		
		JButton button = new JButton(new ImageIcon("pic/newPic/exit.png"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openPanel(new TransPrintManagePanel(adminBean));
			}
		});
		button.setBounds(1258, 770, 150, 50);
		add(button);
		
		JButton button_1 = new JButton(new ImageIcon("pic/newPic/confirm.png"));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				valida();
			}
		});
		button_1.setBounds(1075, 770, 150, 50);
		add(button_1);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(430, 419, 190, 40);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblNewLabel.setForeground(Color.red);
		this.showJpanel.add(lblNewLabel);
	}
	/*
	 * 校验输入的信息
	 */
	private void valida(){
		//原密码输入框
		   String a= keyPopup.getSoftKeyBoardPanel().reset().getText();
	       char[] password1=a.toCharArray();
	       String psw = new String(password1);
	       //新密码输入框
	       String b= keyPopup1.getSoftKeyBoardPanel().reset().getText();
	       char[] password2=b.toCharArray();
	       String psw1 = new String(password2);
	       //确认新密码输入框
	       String c= keyPopup2.getSoftKeyBoardPanel().reset().getText();
	       char[] password3=c.toCharArray();
	       String psw2 = new String(password3);
		if(psw.length()==0){
			lblNewLabel.setText("原密码不能为空!");
		}
		else if(psw1.length()==0){
			lblNewLabel.setText("新密码不能为空!");
		}
		else if(psw1.length()!=6){
			lblNewLabel.setText("新密码只能输入六位数!");
		}
		else if(psw2.length()==0){
			lblNewLabel.setText("确认密码不能为空!");
		}
		else if(!psw1.equals(psw2)){
			lblNewLabel.setText("两次输入的密码不一致!");
		}
		else{
			//查询
			Map<String,String> map =new HashMap<String,String>();
			map.put("isXiu","0"); //查询
			map.put("username",adminBean.getAdminName()); //管理员
			map.put("password",psw); //密码
			Tms0004ResBean tms0004 = Tms0004(map);
			if(!"000000".equals(tms0004.getHeadBean().getResCode())){
				lblNewLabel.setText(tms0004.getHeadBean().getResMsg());
				return;
			}
			//修改
			map.put("isXiu","1"); //修改
			map.put("username",adminBean.getAdminName()); //管理员
			map.put("password",psw2); //密码
			Tms0004ResBean tms00004 = Tms0004(map);
			if(!"000000".equals(tms00004.getHeadBean().getResCode())){
				lblNewLabel.setText(tms00004.getHeadBean().getResMsg());
				return;
			}
			//跳到下个页面
			this.openPanel(new PasUpdateSuccessPanel(adminBean));
		}
	}
	
	/***
	 * 键盘一
	 */
	private void scanBill1(){
		keyPopup.show(passwordPanel, 335, 250);
		 String a= keyPopup.getSoftKeyBoardPanel().reset().getText();
         char[] password1=a.toCharArray();
         String psw = new String(password1);
         logger.info("页面" + psw);
	}
	/***
	 * 键盘二
	 */
	private void scanBill2(){
		keyPopup1.show(passwordPanel, 335, 300);
		 String a= keyPopup1.getSoftKeyBoardPanel().reset().getText();
         char[] password1=a.toCharArray();
         String psw = new String(password1);
         logger.info("页面" + psw);
	}
	/***
	 * 键盘三
	 */
	private void scanBill3(){
		keyPopup2.show(passwordPanel, 335, 360);
		 String a= keyPopup2.getSoftKeyBoardPanel().reset().getText();
         char[] password1=a.toCharArray();
         String psw = new String(password1);
         logger.info("页面" + psw);
	}
	/**
	 * 查询密码
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Tms0004ResBean Tms0004(Map<String,String>map){
	
		SocketClient sc = new SocketClient();
		try {
			Socket socket =sc.createSocket();
			// 发送请求
			sc.sendRequest(socket,sc.Tms0004(map));
			// 响应
			String retMsg = sc.response(socket);
		
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", Tms0004ResBean.class);
			reqXs.alias("Head", InResBean.class);
			Tms0004ResBean tms0004ResBean = (Tms0004ResBean)reqXs.fromXML(retMsg);
			logger.info(tms0004ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
			return tms0004ResBean;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			logger.error("管理员校验连接异常",e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("管理员校验io传输异常",e);
		}
		return null;
	}
}
