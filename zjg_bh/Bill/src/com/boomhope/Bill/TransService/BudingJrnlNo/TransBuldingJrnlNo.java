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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.BudingJrnlNo.Interface.InterfaceSendMsg;
import com.boomhope.Bill.TransService.BudingJrnlNo.bean.BulidingJrnNoBean;
import com.boomhope.Bill.Util.SoftKeyBoardPopups;
import com.boomhope.Bill.Util.SoftKeyBoardPopups1;

/**
 * 
 * 卡系统-交易辅助登记
 * @author ly
 */
public class TransBuldingJrnlNo extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransBuldingJrnlNo.class);
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private SoftKeyBoardPopups1 keyPopup;
	private SoftKeyBoardPopups keyPopup1;
	private JPanel passwordPanel;
	JLabel label_2;
	//等待提示框
	/***
	 * 初始化
	 */
	public TransBuldingJrnlNo(final BulidingJrnNoBean transBean) {

		this.jrnNoBean = transBean;
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
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
		JLabel label = new JLabel("绑定流水号");
		label.setBounds(396, 174, 240, 37);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("微软雅黑", Font.BOLD, 24));
		this.showJpanel.add(label);
		
		JLabel label_1 = new JLabel("工号：");
		label_1.setBounds(371, 247, 54, 37);
		label_1.setFont(new Font("微软雅黑", Font.BOLD, 18));
		this.showJpanel.add(label_1);
		
		JLabel lblNewLabel = new JLabel("流水号：");
		lblNewLabel.setBounds(354, 313, 82, 30);
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
		this.showJpanel.add(lblNewLabel);
		//工号
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
		this.showJpanel.add(textField);
		textField.setColumns(10);
		keyPopup = new SoftKeyBoardPopups1(textField);
		//流水号
		textField_1 = new JTextField();
		textField_1.setBounds(435, 308, 245, 41);
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
		keyPopup1 = new SoftKeyBoardPopups(textField_1,50);
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
		//确认
		JLabel lblNewLabel1 = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		lblNewLabel1.setBounds(1075, 770, 150, 50);
		lblNewLabel1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				/* 处理下一页 */
				logger.info("点击确认按钮");	
				valida(transBean);
			}

		});
		this.add(lblNewLabel1);
		/**
		 * 提示信息
		 */
	    label_2 = new JLabel("");
		label_2.setBounds(435, 387, 355, 58);
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_2.setForeground(Color.red);
		this.showJpanel.add(label_2);
		
	}
	
	
	/**
	 * 验证输入的是否为空
	 */
	
	@SuppressWarnings({ "static-access", "rawtypes" })
	private void valida(BulidingJrnNoBean transBean){
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
		if(psw.length()==0 || psw.length()!=5){
			label_2.setText("工号不能为空或者不是5位！");
		}else if(psw1.length()==0){
			label_2.setText("流水号不能为空！");
		}else{
//			openProssDialog();
			transBean.setWorkNo(a);
			transBean.setJrnlNo(b);
			//提交后要跳转的页面
			InterfaceSendMsg inter = new InterfaceSendMsg();
			try {
				Map map = inter.inter03531(transBean);
				String resCode = (String) map.get("resCode");
				String errMsg = (String) map.get("errMsg");
				String  RETURN_CODE = (String) map.get("RETURN_CODE");
				//不是渠道整合的，返回码结果为空
				if("000000".equals(resCode) && "".equals(RETURN_CODE)){
					//跳转成功页
					success(transBean);
					return;
				}else{
					//是渠道整合，要判断返回码
					if("000000".equals(resCode) && "0".equals(RETURN_CODE)){
						//跳转成功页
						success(transBean);
						return;
					}else if("000000".equals(resCode) && "1".equals(RETURN_CODE)){
						//跳转错误页面
						fail(transBean, "非发卡机流水");
						return;
					}else if("000000".equals(resCode) && "2".equals(RETURN_CODE)){
						//跳转错误页面
						fail(transBean, "非当日流水 ");
						return;
					}else if("000000".equals(resCode) && "3".equals(RETURN_CODE)){
						//跳转错误页面
						fail(transBean, "非成功交易流水");
						return;
					}else if("000000".equals(resCode) && "4".equals(RETURN_CODE)){
						//跳转错误页面
						fail(transBean, "未查询到流水");
						return;
					}
					//跳转错误页面
					fail( transBean, errMsg);
					return;
				}
			} catch (Exception e) {
				logger.error("调用接口03531失败"+e);
				//跳转错误页面
				fail(transBean, "调用接口03531失败"+e);
				return;
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
		keyPopup1.show(passwordPanel, 200, 350);
		 String a= keyPopup1.getSoftKeyBoardPanel().reset().getText();
         char[] password1=a.toCharArray();
         String end = new String(password1);
	}
	/**
	 * 成功处理
	 */
	private void success(BulidingJrnNoBean transBean){
		transBean.setSuccess("绑定流水成功");
		transBean.setCheckService("0");
		clearTimeText();
		openPanel(new SuccessPanle(transBean));
		
	}
	/**
	 * 
	 * 失败后处理
	 */
	private void fail(BulidingJrnNoBean transBean,String errmsg){
		transBean.setErrmsg(errmsg);
		transBean.setTag("bulding");
		openPanel(new ErrorMsgPanel(transBean));			
				
		
		
	}
	/**
	 * 上一步
	 */
	public void  backTrans(BulidingJrnNoBean transBean){
		logger.debug("上一步");
		clearTimeText();
		returnHome();
	}
}
