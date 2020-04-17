package com.boomhope.Bill.TransService.BudingJrnlNo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.BudingJrnlNo.Interface.InterfaceSendMsg;
import com.boomhope.Bill.TransService.BudingJrnlNo.bean.BulidingJrnNoBean;
import com.boomhope.Bill.Util.SoftKeyBoardPopups1;

/**
 * 
 * 推荐人奖励领取
 * @author ly
 */
public class GetRewardsPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(GetRewardsPanel.class);
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private SoftKeyBoardPopups1 keyPopup;
	private SoftKeyBoardPopups1 keyPopup1;
	private JPanel passwordPanel;
	JLabel label_2;
	//等待提示框
	/***
	 * 初始化
	 */
	public GetRewardsPanel(final BulidingJrnNoBean transBean) {

		this.jrnNoBean = transBean;
		/* 显示时间倒计时 */
		showTimeText(delaySecondMaxTime);
		delayTimer = new Timer(delaySecondMaxTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		//输入框
        passwordPanel = new JPanel(new BorderLayout());  
        passwordPanel.setBackground(Color.WHITE);  
        passwordPanel.setPreferredSize(new Dimension(2024, 30));  
        passwordPanel.setLayout(new BorderLayout());  
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));  
        this.showJpanel.add(passwordPanel); 
		/* 标题信息 */
		JLabel label = new JLabel("领取奖励");
		label.setBounds(0, 100, 1009, 60);
		label.setForeground(Color.decode("#412174"));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("微软雅黑", Font.BOLD, 40));
		this.showJpanel.add(label);
		
		/*JLabel label_1 = new JLabel("客户号：");
		label_1.setBounds(354, 247, 82, 37);
		label_1.setFont(new Font("微软雅黑", Font.BOLD, 18));
		add(label_1);*/
		
		JLabel lblNewLabel = new JLabel("领取码：");
		lblNewLabel.setBounds(354, 313, 82, 30);
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
		this.showJpanel.add(lblNewLabel);
		/*//工号
		textField = new JTextField();
		textField.setBounds(435, 245, 245, 41);
		textField.setFont(new Font("微软雅黑", Font.BOLD, 18));
		textField.setHorizontalAlignment(JLabel.CENTER);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				scanBill1();
			}

		});
		add(textField);
		textField.setColumns(10);*/
		keyPopup = new SoftKeyBoardPopups1(textField);
		//流水号
		textField_1 = new JTextField();
		textField_1.setBounds(435, 308, 245, 41);
		textField_1.setFont(new Font("微软雅黑", Font.BOLD, 20));
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
		
		/**
		 * 提示信息
		 */
	    label_2 = new JLabel("");
		label_2.setBounds(435, 371, 355, 58);
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_2.setForeground(Color.red);
		this.showJpanel.add(label_2);
		
		JLabel label_1 = new JLabel("推荐姓名："+transBean.getRecName());
		label_1.setFont(new Font("微软雅黑", Font.BOLD, 20));
		label_1.setBounds(291, 238, 227, 30);
		this.showJpanel.add(label_1);
		
		JLabel label_3 = new JLabel("推荐手机号："+transBean.getRecTelNo());
		label_3.setFont(new Font("微软雅黑", Font.BOLD, 20));
		label_3.setBounds(577, 238, 301, 30);
		this.showJpanel.add(label_3);
		//确认
		JLabel lblNewLabel1 = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		lblNewLabel1.setBounds(890, 770, 150, 50);
		lblNewLabel1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				/* 处理下一页 */
				logger.info("点击确认按钮");	
				valida(transBean);
			}
			
		});
		this.add(lblNewLabel1);
		//上一步
		JLabel label1 = new JLabel(new ImageIcon("pic/preStep.png"));
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");				
				backup();
			}
		
		});
		label1.setBounds(1075, 770, 150, 50);
		this.add(label1);
		//退出
		JLabel label_11 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");				
				returnHome();
			}

		});
		label_11.setBounds(1258, 770, 150, 50);
		this.add(label_11);
	}
	
	
	/**
	 * 验证输入的是否为空
	 */
	
	@SuppressWarnings({ "static-access", "rawtypes" })
	private void valida(BulidingJrnNoBean transBean){
       String b= keyPopup1.getSoftKeyBoardPanel().reset().getText();
       char[] password=b.toCharArray();
       String psw1 = new String(password);
//       logger.info("输入框一"+textField.getText());
//       logger.info("输入框2"+textField_1.getText());
//       int start= Integer.parseInt(psw);
//       int end = Integer.parseInt(psw1);
		/*if(psw.length()==0){
			label_2.setText("客户号不能为空！");
		}else*/ 
       if(psw1.length()==0){
			label_2.setText("领取码不能为空！");
		}else{
//			openProssDialog();
			transBean.setAccessCode(b);
			//提交后要跳转的页面
			InterfaceSendMsg inter = new InterfaceSendMsg();
			try {
				Map map = inter.inter03532(transBean);
				String resCode = (String) map.get("resCode");
				String errMsg = (String) map.get("errMsg");
				if(resCode=="000000"){
					//跳转成功页
					success(transBean);
				}else{
					//跳转错误页面
					fail(transBean, errMsg);
				}
			} catch (Exception e) {
				logger.error("调用接口03532失败"+e);
				//跳转错误页面
				fail(transBean, "调用接口03532失败"+e);
			}
			
		}
	}
	
	/***
	 * 键盘
	 */
	private void scanBill1(){
		keyPopup.show(passwordPanel, 355, 350);
		 String a= keyPopup.getSoftKeyBoardPanel().reset().getText();
      
         char[] password1=a.toCharArray();
         String start = new String(password1);
	}
	/***
	 * 键盘
	 */
	private void scanBill2(){
		keyPopup1.show(passwordPanel, 355, 350);
		 String a= keyPopup1.getSoftKeyBoardPanel().reset().getText();
         char[] password1=a.toCharArray();
         String end = new String(password1);
	}
	
	/**
	 * 成功处理
	 */
	private void success(BulidingJrnNoBean transBean){
		transBean.setSuccess("奖励领取成功");
		transBean.setCheckService("1");
		clearTimeText();
		openPanel(new SuccessPanle(transBean));
		
	}
	/**
	 * 上一步
	 */
	private void backup(){
		clearTimeText();
		openPanel(new TJServicePanel());
		
	}
	/**
	 * 
	 * 失败后处理
	 */
	private void fail(BulidingJrnNoBean transBean,String errmsg){
		transBean.setErrmsg(errmsg);
		transBean.setTag("getreward");
		clearTimeText();
		openPanel(new ErrorMsgPanel(transBean));			
		
		
	}
}
