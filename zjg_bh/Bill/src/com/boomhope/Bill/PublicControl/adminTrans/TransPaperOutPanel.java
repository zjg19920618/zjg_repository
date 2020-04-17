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
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseContentPanel;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.PublicControl.adminTrans.Bean.AdminBean;
import com.boomhope.Bill.Socket.SocketClient;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.SoftKeyBoardPopups1;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InResBean;
import com.boomhope.tms.message.in.tms.Tms0005ResBean;
import com.boomhope.tms.message.in.tms.Tms0005ResBodyBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * title:设置凭证信息页
 * @author ly
 * 2016年11月8日下午2:17:44
 */
public class TransPaperOutPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransPaperOutPanel.class);
	private static final long serialVersionUID = 1L;
	Timer delayTimer;
	private JTextField textField;
	private JTextField textField_1;
	private SoftKeyBoardPopups1 keyPopup;
	private SoftKeyBoardPopups1 keyPopup1;
	private JPanel passwordPanel;
	JLabel label_2;
	private AdminBean adminBeans;
	
	/***
	 * 初始化
	 */
	public TransPaperOutPanel(AdminBean adminBean) {

		this.adminBeans = adminBean;
		GlobalPanelFlag.CurrentFlag = GlobalPanelFlag.PRINT_PAPER_OUT;
		//输入框
        passwordPanel = new JPanel(new BorderLayout());  
        passwordPanel.setBackground(Color.WHITE);  
        passwordPanel.setPreferredSize(new Dimension(2024, 30));  
        passwordPanel.setLayout(new BorderLayout());  
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));  
        this.showJpanel.add(passwordPanel); 
		/* 标题信息 */
		JLabel label = new JLabel("设置凭证信息");
		label.setBounds(370, 150, 240, 37);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("微软雅黑", Font.BOLD, 24));
		this.showJpanel.add(label);
		
		JLabel label_1 = new JLabel("起始：");
		label_1.setBounds(370, 230, 54, 37);
		label_1.setFont(new Font("微软雅黑", Font.BOLD, 18));
		this.showJpanel.add(label_1);
		
		JLabel lblNewLabel = new JLabel("终止：");
		lblNewLabel.setBounds(370, 300, 54, 30);
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
		this.showJpanel.add(lblNewLabel);
		//起始位置
		textField = new JTextField();
		textField.setBounds(450, 230, 180, 41);
		textField.setFont(new Font("微软雅黑", Font.BOLD, 18));
		textField.setHorizontalAlignment(JLabel.CENTER);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				scanBill1();
			}

		});
		this.showJpanel.add(textField);
		textField.setColumns(10);
		keyPopup = new SoftKeyBoardPopups1(textField);
		//终止位置
		textField_1 = new JTextField();
		textField_1.setBounds(450, 300, 180, 41);
		textField_1.setFont(new Font("微软雅黑", Font.BOLD, 18));
		textField_1.setHorizontalAlignment(JLabel.CENTER);
		textField_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				scanBill2();
			}

		});
		this.showJpanel.add(textField_1);
		textField_1.setColumns(10);
		keyPopup1 = new SoftKeyBoardPopups1(textField_1);
		
		JButton button_1 = new JButton(new ImageIcon("pic/newPic/confirm.png"));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				valida();
			}
		});
		button_1.setBounds(890, 770, 150, 50);
		add(button_1);
		/**
		 * 提示信息
		 */
	    label_2 = new JLabel("");
		label_2.setBounds(415, 369, 355, 58);
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		label_2.setForeground(Color.red);
		this.showJpanel.add(label_2);
		
		JButton button_2 = new JButton(new ImageIcon("pic/newPic/preStep.png"));
		button_2.setBounds(1075, 770, 150, 50);
		add(button_2);
		button_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openPanel(new TransPrintManagePanel(adminBeans));
			}
		});
		
		//退出按钮
		JButton backbtn = new JButton(new ImageIcon("pic/newPic/exit.png"));
		backbtn.setBounds(1258, 770, 150, 50);
		add(backbtn);
		backbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				backStep();
			}
		});
	}
	
	
	/**
	 * 验证输入的是否为空
	 */
	
	private void valida(){
		//起始位置
	   String a= keyPopup.getSoftKeyBoardPanel().reset().getText();
       char[] password1=a.toCharArray();
       String psw = new String(password1);
       //终止位置
       String b= keyPopup1.getSoftKeyBoardPanel().reset().getText();
       char[] password=b.toCharArray();
       String psw1 = new String(password);
//       logger.info("输入框一"+textField.getText());
//       logger.info("输入框2"+textField_1.getText());
//       int start= Integer.parseInt(psw);
//       int end = Integer.parseInt(psw1);
		if(psw.length()==0){
			label_2.setText("起始位置不能为空！");
		}else if(psw1.length()==0){
			label_2.setText("终止位置不能为空！");
		}else if(psw.length()>8  || psw1.length()>8){
			label_2.setText("凭证号不能超过8位数！");
		}else if(Integer.parseInt(psw1)-Integer.parseInt(psw)<0){
			label_2.setText("终止位置不能小于起始位置！");
		}else{
			//提交后要跳转的页面
			Tms0006();
			adminBeans.setStartBo(String.format("%08d",(Long.parseLong(textField.getText()))));
			adminBeans.setEndBo(String.format("%08d",(Long.parseLong(textField_1.getText()))));
			openPanel(new TransOkPaperOutPanel(adminBeans));
		}
	}
	
	/***
	 * 键盘
	 */
	private void scanBill1(){
//		logger.info(1); 
//		keyPopup.show(passwordPanel, 2000, passwordPanel.getPreferredSize().height);
		keyPopup.show(passwordPanel, 335, 350);
		 String a= keyPopup.getSoftKeyBoardPanel().reset().getText();
//         char[] cs = a.getPassword();
      
         char[] password1=a.toCharArray();
         String start = new String(password1);
//         logger.info("页面" + start);
	}
	/***
	 * 键盘
	 */
	private void scanBill2(){
//		logger.info(1); 
//		keyPopup.show(passwordPanel, 2000, passwordPanel.getPreferredSize().height);
		keyPopup1.show(passwordPanel, 335, 350);
		 String a= keyPopup1.getSoftKeyBoardPanel().reset().getText();
//         char[] cs = a.getPassword();
      
         char[] password1=a.toCharArray();
         String end = new String(password1);
//         logger.info("页面" + end);
	}
	/*
	 * 修改凭证号
	 */
	public void Tms0006(){
		Map <String,String> map=new HashMap <String,String>();
		if("".equals(adminBeans.getNowBo()) && "".equals(adminBeans.getNowNumber())){
			//添加凭证信息
			map.put("isNo", "1");
			map.put("startNo", String.format("%08d",(Long.parseLong(textField.getText()))));
			map.put("endNo", String.format("%08d",(Long.parseLong(textField_1.getText()))));
			map.put("nowNo", String.format("%08d",(Long.parseLong(textField.getText()))));
			map.put("createDate", DateUtil.getNowDate("yyyyMMdd"));
			map.put("updateDate", DateUtil.getNowDate("yyyyMMdd"));
		}else{
			//修改凭证信息
			map.put("isNo", "0");
			map.put("id", adminBeans.getBusBillId());
			map.put("startNo",String.format("%08d",(Long.parseLong(textField.getText()))));
			map.put("endNo", String.format("%08d",(Long.parseLong(textField_1.getText()))));
			map.put("nowNo", String.format("%08d",(Long.parseLong(textField.getText()))));
			map.put("createDate", adminBeans.getCreateDate());
			map.put("updateDate", DateUtil.getNowDate("yyyyMMdd"));
		}
		SocketClient sc = new SocketClient();
		try {
			Socket socket =sc.createSocket();
			// 发送请求
			sc.sendRequest(socket,sc.Tms0006(map));
			// 响应
			String retMsg = sc.response(socket);
			
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", Tms0005ResBean.class);
			reqXs.alias("Head", InResBean.class);
			reqXs.alias("Body", Tms0005ResBodyBean.class);
			Tms0005ResBean tms0005ResBean = (Tms0005ResBean)reqXs.fromXML(retMsg);
			logger.info(tms0005ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
		
		} catch (UnknownHostException e) {
			e.printStackTrace();
			logger.error("更改凭证连接异常",e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("更改凭证io传输异常",e);
		}
	}
	
	//退出
	private void backStep(){
		BaseContentPanel comp=(BaseContentPanel)this.getParent();
		comp.getManagerButton().setVisible(true);
		returnHome();
	}
}
