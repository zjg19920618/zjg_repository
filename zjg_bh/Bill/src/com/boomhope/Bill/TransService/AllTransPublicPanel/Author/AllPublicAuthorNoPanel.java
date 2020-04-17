package com.boomhope.Bill.TransService.AllTransPublicPanel.Author;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Util.SoftKeyBoardPopups5;

/***
 * 录入授权柜员号界面
 * 
 * @author hk
 *
 */
@SuppressWarnings("static-access")
public class AllPublicAuthorNoPanel extends BaseTransPanelNew {

	static Logger logger = Logger.getLogger(AllPublicAuthorNoPanel.class);
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	JLabel errInfo = null;// 错误提示
	JLabel fingerprint = null;
	private JPanel passwordPanel;
	private SoftKeyBoardPopups5 keyPopup;
	private boolean on_off=true;
	
	/***
	 * 初始化
	 */
	public AllPublicAuthorNoPanel() {
		logger.info("进入录入柜员号页面");
		baseTransBean.setThisComponent(this);
		
		if(!"2".equals(baseTransBean.getAllPubIsCheckAuthor())){
			voiceTimer = new Timer(voiceSecond, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stopTimer(voiceTimer);//关语音
					closeVoice();//关语音流
					excuteVoice("voice/checkInfo.wav");
					
				}
			});
		}else{
			/* 倒计时打开语音 */
			voiceTimer = new Timer(voiceSecond, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stopTimer(voiceTimer);//关语音
					closeVoice();//关语音流
					excuteVoice("voice/impowers.wav");
					
				}
			});
			
		}
		voiceTimer.start();

		/* 显示时间倒计时 */
		
		if(delayTimer!=null){
			clearTimeText();
		}
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000,
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						logger.info("录入柜员号页面倒计时结束");
						/* 倒计时结束退出交易 */ 
		            	clearTimeText();
		            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
					}
				});
		delayTimer.start();
		// 输入框
		passwordPanel = new JPanel(new BorderLayout());
		passwordPanel.setBackground(Color.WHITE);
		passwordPanel.setPreferredSize(new Dimension(2024, 30));
		passwordPanel.setLayout(new BorderLayout());
		passwordPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel);

		/* 标题信息 */
		JLabel titleLabel = new JLabel("请录入授权柜员号:");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
		titleLabel.setBounds(0, 100, 1009, 40);
		this.showJpanel.add(titleLabel);

		//客户信息查询 
		JLabel jbutton = new JLabel(new ImageIcon("pic/customerInfo.png"));
		jbutton.setHorizontalAlignment(0);
		jbutton.setBounds(305, 200, 400, 40);
		jbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击确认按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//跳转页面时清空倒计时
				clearTimeText();
				stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
            	
            	if("1".equals(baseTransBean.getAllPubIsDeputy())){//代理人
            		baseTransBean.setNextStepName("ALL_PUBLIC_CHECK_AGENT_PHOTOS_PANLE");
            	}else{
            		baseTransBean.setNextStepName("ALL_PUBLIC_CHECK_PHOTOS_PANLE");
            	}
            	if(baseTransBean.getAllPubtransAuthorNo()!=null || "".equals(baseTransBean.getAllPubtransAuthorNo())){
            		baseTransBean.setAllPubtransAuthorNo("1");
            	}
				//点击进行客户信息查询跳转到联系授权页面 
            	allPubTransFlow.transFlow();
			}
		});
		this.showJpanel.add(jbutton);
		
		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				/* 处理上一页 */
				logger.info("点击上一步");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				scanBill1();
			}
		});
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(1075, 770);
		add(submitBtn);

		// 确认
		JLabel confirm = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		confirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击确认按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 处理下一页 */
				scanBill();
			}
		});
		confirm.setSize( 150, 50);
		confirm.setLocation(890, 770);
		add(confirm);
		
		// 返回
		JLabel backButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				clearTimeText();
				accCancelExit();
			}
		});
		add(backButton);
		

		textField = new JTextField();
		textField.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField.setBounds(292, 290, 424, 40);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击输入框");
				scanBill12();
			}

		});
		textField.setColumns(10);
		this.showJpanel.add(textField);
		keyPopup = new SoftKeyBoardPopups5(textField,5);

		errInfo = new JLabel("提示：请录入授权柜员号!");
		errInfo.setVisible(false);
		errInfo.setForeground(Color.RED);
		errInfo.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		errInfo.setForeground(Color.decode("#ff0101"));
		errInfo.setBounds(292, 350, 258, 23);
		this.showJpanel.add(errInfo);

	}

	/***
	 * 下一步
	 */
	private void scanBill() {
		logger.info("进入确认方法");
		closeVoice();
		stopTimer(voiceTimer);
		// 业务处理标识判断
		String str = textField.getText();
		if(!"2".equals(baseTransBean.getAllPubIsCheckAuthor())){
			errInfo.setText("请先点击客户信息查询按钮核对客户信息");
			errInfo.setVisible(true);
			on_off=true;
			return;
		}
		if (str == null || "".equals(str)) {
			errInfo.setVisible(true);
			on_off=true;
			return;
		}
		clearTimeText();
		baseTransBean.setAllPubFristSupTellerNo(str);
		allPubTransFlow.transFlow();
	}

	/***
	 * 上一步
	 */
	private void scanBill1() {
		logger.info("进入上一步方法");
		// 关闭语音
		clearTimeText();
		closeVoice();
		stopTimer(voiceTimer);
		baseTransBean.setAllPubIsCheckAuthor("0");
		baseTransBean.setAllPubIsSign("1");
		baseTransBean.setAllPubReCamera("");
		backStepMethod();
		allPubTransFlow.transFlow();
		
	}

	/***
	 * 键盘
	 */
	private void scanBill12() {
		logger.info("调用软键盘");
		if(!"2".equals(baseTransBean.getAllPubIsCheckAuthor())){
			openMistakeDialog("请先检查客户身份信息是否正确！");
			return;
		}
		keyPopup.show(passwordPanel, 140, 350);
		textField.grabFocus();
	}

}
