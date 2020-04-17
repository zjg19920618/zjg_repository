package com.boomhope.Bill.TransService.LostReport.Page.Lost;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Util.SoftKeyBoardPopups2;
import com.boomhope.Bill.Util.SoftKeyBoardPopups5;

/**
 * 录入电话和地址
 * @author hk
 *
 */
@SuppressWarnings("static-access")
public class LostInputPhoneAndAdress extends BaseTransPanelNew{

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(LostPassSelectPage.class);
	private boolean on_off=true;//用于控制页面跳转的开关
	
	private JTextField textPhone;//电话录入框
	private JTextField textAdress;//地址录入框
	private  JPanel panelkeyBroad;//键盘面板
	public JPanel KeyBordJp;//软键盘窗口
	private SoftKeyBoardPopups2 keyPopup2;//软键盘类对象
	private SoftKeyBoardPopups5 keyPopup1;//输入键盘
	
	public LostInputPhoneAndAdress(){
		logger.info("进入录入代理人电话号码和地址信息页面");
		
		baseTransBean.setThisComponent(this);

		/* 显示时间倒计时 */
		this.showTimeText(BaseTransPanelNew.delaySecondLongestTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondLongestTime * 1000,
			new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					logger.info("录入超时");
					/* 倒计时结束退出交易 */
					clearTimeText();
					serverStop("温馨提示：服务已超时，请结束您的交易！", "", "");
				}
			});
		delayTimer.start();
		
		//标题
		JLabel labelTitle = new JLabel("代理人身份信息核对");
		labelTitle.setBounds(0, 50, 1009, 50);
		labelTitle.setFont(new Font("微软雅黑",Font.BOLD,40));
		labelTitle.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(labelTitle);
		
		//代理人姓名
		JLabel labelName = new JLabel("姓       名："+lostPubBean.getAllPubAgentIdCardName());
		labelName.setBounds(100, 150, 810, 30);
		labelName.setFont(new Font("微软雅黑",Font.PLAIN,24));
		labelName.setHorizontalAlignment(SwingUtilities.LEFT);
		this.showJpanel.add(labelName);
		
		//代理人证件类型
		JLabel labelType = new JLabel("证件类型：身份证");
		labelType.setBounds(100, 220, 810, 30);
		labelType.setFont(new Font("微软雅黑",Font.PLAIN,24));
		labelType.setHorizontalAlignment(SwingUtilities.LEFT);
		this.showJpanel.add(labelType);
		
		//代理人身份证号
		JLabel labelIDNo = new JLabel("身份证号："+lostPubBean.getAllPubAgentIDNo());
		labelIDNo.setBounds(100, 290, 810, 30);
		labelIDNo.setFont(new Font("微软雅黑",Font.PLAIN,24));
		labelIDNo.setHorizontalAlignment(SwingUtilities.LEFT);
		this.showJpanel.add(labelIDNo);
		
		//录入电话信息
		JLabel labelPhone = new JLabel("电话(必输)：");
		labelPhone.setBounds(100, 360, 200, 30);
		labelPhone.setFont(new Font("微软雅黑",Font.PLAIN,24));
		labelPhone.setForeground(Color.RED);
		labelPhone.setHorizontalAlignment(SwingUtilities.LEFT);
		this.showJpanel.add(labelPhone);
		
		//电话录入框
		textPhone = new JTextField(lostPubBean.getAllPubAgentPhone()==null?"":lostPubBean.getAllPubAgentPhone());
		textPhone.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		textPhone.setColumns(10);
		textPhone.setBorder(BorderFactory.createLineBorder(Color.black));
		textPhone.setForeground(Color.RED);
		textPhone.setBounds(230, 360, 700, 40);
		this.showJpanel.add(textPhone);
		textPhone.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击电话录入框");
				scanBill1();
			}
			
		});
		
		keyPopup2 = new SoftKeyBoardPopups2(textPhone);
		keyPopup2.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {

			}
		});
		
		/*软键盘面板*/
		KeyBordJp = new JPanel(new BorderLayout());
		KeyBordJp.setBackground(Color.WHITE);
		KeyBordJp.setPreferredSize(new Dimension(2024, 30));
		KeyBordJp.setLayout(new BorderLayout());
		KeyBordJp.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(KeyBordJp);
		
		//地址
		JLabel labelAdress = new JLabel("地址(必输)：");
		labelAdress.setBounds(100, 430, 200, 30);
		labelAdress.setFont(new Font("微软雅黑",Font.PLAIN,24));
		labelAdress.setForeground(Color.RED);
		labelAdress.setHorizontalAlignment(SwingUtilities.LEFT);
		this.showJpanel.add(labelAdress);
		
		//地址录入框
		textAdress = new JTextField(lostPubBean.getAllPubAgentAddress()==null?"":lostPubBean.getAllPubAgentAddress());
		textAdress.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		textAdress.setColumns(10);
		textAdress.setBorder(BorderFactory.createLineBorder(Color.black));
		textAdress.setForeground(Color.RED);
		textAdress.setBounds(230, 430, 700, 40);
		this.showJpanel.add(textAdress);
		textAdress.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击地址输入框");
				scanBill2();
			}
			
		});
		keyPopup1 = new SoftKeyBoardPopups5(textAdress,30);
		
		// 键盘面板
		panelkeyBroad = new JPanel();
		panelkeyBroad.setBackground(Color.WHITE);
		panelkeyBroad.setPreferredSize(new Dimension(202, 30));
		panelkeyBroad.setLayout(new BorderLayout());
		panelkeyBroad.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(panelkeyBroad);
		
		// 确认
		JLabel okButton = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		okButton.setBounds(892, 770, 150, 50);
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击确认按钮");
				if (!on_off) {
					logger.info("开关当前状态" + on_off);
					return;
				}
				logger.info("开关当前状态" + on_off);
				on_off = false;
	
				ok();
			}
		});
		add(okButton);
	
		// 上一步
		JLabel back = new JLabel(new ImageIcon("pic/preStep.png"));
		back.setBounds(1075, 770, 150, 50);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");
				if (!on_off) {
					logger.info("开关当前状态" + on_off);
					return;
				}
				logger.info("开关当前状态" + on_off);
				on_off = false;
	
				clearTimeText();// 清空倒计时
	
				back();
			}
		});
		add(back);
	
		// 退出
		JLabel exit = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		exit.setBounds(1258, 770, 150, 50);
		exit.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");
				if (!on_off) {
					logger.info("开关当前状态" + on_off);
					return;
				}
				logger.info("开关当前状态" + on_off);
				on_off = false;
	
				clearTimeText();
	
				exit();
			}
		});
		add(exit);
		
		
	}
	
	/**
	 * 退出
	 */
	public void exit(){
		returnHome();
	}
	
	/**
	 * 上一步
	 */
	public void back(){
		lostPubBean.setUpStepName("ALL_PUBLIC_DEPUTY_SELECTION_PANEL");
		backStepMethod();
		allPubTransFlow.transFlow();
	}
	
	/**
	 * 确认
	 */
	public void ok(){
		String phoneNo = textPhone.getText();
		//校验电话号码是否超出位数并且所有字符必须为数字
		if(phoneNo==null ||"".equals(phoneNo.trim())){
			logger.info("没有输入电话号码");
			on_off=true;
			openMistakeDialog("请输入电话号码");
			return;
		}
		Pattern pattern = Pattern.compile("[0-9]{1,}");
		Matcher matcher = pattern.matcher(phoneNo);
		if(phoneNo !=null && !"".equals(phoneNo.trim()) && (phoneNo.trim().length()>11 || !matcher.matches())){
			logger.info("输入了错误的电话号码:"+phoneNo+"||"+matcher.matches());
			on_off=true;
			openMistakeDialog("请输入正确的电话号码");
			return;
		}
		
		//校验地址是否为空
		String address = textAdress.getText();
		if(address==null || address.trim().length()==0){
			logger.info("没有输入地址");
			on_off = true;
			openMistakeDialog("请输入常用地址");
			return;
		}
		
		lostPubBean.setAllPubAgentPhone(phoneNo);
		lostPubBean.setAllPubAgentAddress(address);
		lostPubBean.setUpStepName("LOST_INPUT_PHONE_AND_ADRESS");
		logger.info("代理人电话："+phoneNo+";代理人地址："+address);
		clearTimeText();
		allPubTransFlow.transFlow();
		return;
	}
	
	/**
	 * 电话录入
	 */
	public void scanBill1() {
		logger.info("进入调用软键盘方法");
		try {
			keyPopup2.show(KeyBordJp, 330, 100);
			textPhone.grabFocus();
		} catch (Exception e) {
			logger.info("调用软键盘" + e);
		}
	}
	
	/**
	 * 地址录入
	 */
	public void scanBill2() {
		keyPopup1.show(panelkeyBroad, 200, 80);
		textAdress.grabFocus();
	}
}
