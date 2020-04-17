package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author;

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
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.Bean.AccPublicBean;
import com.boomhope.Bill.TransService.AccOpen.Interface.IntefaceSendMsg;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.AccountConfirmPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccAgreementPagePanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccOkInputDepInfoPanel;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.Util.SoftKeyBoardPopups5;

/***
 * 录入授权柜员号界面
 * 
 * @author gyw
 *
 */
public class AccAuthorNoPanel extends BaseTransPanelNew {

	static Logger logger = Logger.getLogger(AccAuthorNoPanel.class);
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	JLabel errInfo = null;// 错误提示
	JLabel fingerprint = null;
	private JPanel passwordPanel;
	private SoftKeyBoardPopups5 keyPopup;
	private Map map;
	private boolean on_off=true;
	
	/***
	 * 初始化
	 */
	public AccAuthorNoPanel(final Map map) {
		logger.info("进入录入柜员号页面");
		this.map=map;
		final Component comp = this;
		
		if(!"1".equals(AccountTradeCodeAction.transBean.getIsCheckedPic())){
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
            	
            	//变更已经核查
            	AccountTradeCodeAction.transBean.setIsCheckedPic("1");
            	
				//点击进行客户信息查询跳转到联系授权页面 
            	if("0".equals(AccountTradeCodeAction.transBean.getHaveAgentFlag())){
            		openPanel(new AccCheckAgentPhotos(map));
            	}else{
            		openPanel(new AccCheckPhotos(map));
            	}
			}
		});
		this.showJpanel.add(jbutton);
		
		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				/* 处理上一页 */
				logger.info("点击确认按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				logger.info("点击上一步");
				scanBill1(comp,map);
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
				clearTimeText();
				openPanel(new OutputBankCardPanel());
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
//		passwordPanel.add(textField, BorderLayout.CENTER);
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
		clearTimeText();
		stopTimer(voiceTimer);
		// 业务处理标识判断
		String str = textField.getText();
		if (str == null || "".equals(str)) {
			errInfo.setVisible(true);
			on_off=true;
			return;
		}
		if(!"1".equals(AccountTradeCodeAction.transBean.getIsCheckedPic()) ||!"2".equals(AccountTradeCodeAction.transBean.getIsSign())){
			errInfo.setText("请先点击客户信息查询按钮核对客户信息");
			errInfo.setVisible(true);
			on_off=true;
			return;
		}
		// 调用柜员查询接口
		checkAuthorNo(str);
	}

	/***
	 * 上一步
	 */
	private void scanBill1(Component comp,Map params) {
		logger.info("进入上一步方法");
		// 关闭语音
		clearTimeText();
		closeVoice();
		stopTimer(voiceTimer);
		AccountTradeCodeAction.transBean.setIsCheckedPic("");
		AccountTradeCodeAction.transBean.setIsSign("1");
		AccountTradeCodeAction.transBean.setIsReCamera("");
		if("0010".equals(AccountTradeCodeAction.transBean.getProductCode())){
			openPanel(new AccountConfirmPanel(AccountTradeCodeAction.transBean,params));
		}else{
			openPanel(new AccAgreementPagePanel(params));
		}
		
	}

	/***
	 * 键盘
	 */
	private void scanBill12() {
		logger.info("调用软键盘");
		if(!"1".equals(AccountTradeCodeAction.transBean.getIsCheckedPic()) ||!"2".equals(AccountTradeCodeAction.transBean.getIsSign())){
			openMistakeDialog("请先检查客户身份信息是否正确！");
			return;
		}
		keyPopup.show(passwordPanel, 140, 350);
		textField.grabFocus();
	}

	/**
	 * 检查柜员号
	 */
	private void checkAuthorNo(final String authorNo) {
		logger.info("进入检查柜员号方法");
		new Thread() {
			@Override
			public void run() {
				AccPublicBean bean = new AccPublicBean();
				bean.setSupTellerNo(authorNo);
				AccountTradeCodeAction.transBean.setSupTellerNo(authorNo);
				AccountTradeCodeAction.transBean.setIsCheckInfos("1");
				AccountTradeCodeAction.transBean.getReqMCM001().setAuthemp1(authorNo);//第一授权柜员号
				try {
					AccountTradeCodeAction.transBean.getReqMCM001().setReqBefor("07659");
					Map params = IntefaceSendMsg.inter07659(bean);
					AccountTradeCodeAction.transBean.getReqMCM001().setReqAfter((String)params.get("resCode"), (String)params.get("errMsg"));
					String resCode = (String) params.get("resCode");
					String errMsg = (String) params.get("errMsg");
					if (!IntefaceSendMsg.SUCCESS.equals(resCode)) {
						logger.info("柜员查询异常：" + errMsg);
						openMistakeDialog(errMsg);
						on_off=true;
						textField.setText("");
						return;
					}
					String checkMod = bean.getCheckMod();
					map.put("authorNo", authorNo);
					authorDeal( checkMod);
				} catch (Exception e) {
					logger.error("柜员查询异常：" + e);
					openMistakeDialog("柜员查询异常");
					on_off=true;
				}
			}

		}.start();
	}
	//柜员授权方式选择
	private void authorDeal( final String checkMod) {
		logger.info("进入授权方法选择方法");
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if ("1".equals(checkMod)) {// 1 口令2 指纹
					logger.info("进入口令授权"+checkMod);
					AccountTradeCodeAction.transBean.getReqMCM001().setAuthmethod1("1");
					openPanel(new AccAuthorPassPanel(map));
				} else {
					logger.info("进入指纹授权"+checkMod);
					AccountTradeCodeAction.transBean.getReqMCM001().setAuthmethod1("2");
					openPanel(new AccAuthorFingerprintPanel(map));
				}
			}

		});
	}
}
