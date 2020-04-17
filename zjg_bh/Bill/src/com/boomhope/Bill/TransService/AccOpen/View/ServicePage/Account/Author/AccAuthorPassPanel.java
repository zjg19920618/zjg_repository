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
import javax.swing.JPasswordField;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.Bean.AccPublicBean;
import com.boomhope.Bill.TransService.AccOpen.Interface.IntefaceSendMsg;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccAgreementPagePanel;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTransFlow;
import com.boomhope.Bill.Util.SoftKeyBoardPopup;
import com.boomhope.Bill.Util.SoftKeyBoardPopuppassword;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;

/**
 * 授权柜员密码
 * 
 * @author gyw
 *
 */
public class AccAuthorPassPanel extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(AccAuthorPassPanel.class);
	private static final long serialVersionUID = 1L;;
	final int voiceSecond = 500;
	private JPanel passwordPanel;
	JLabel errInfo = null;// 错误提示
	private SoftKeyBoardPopuppassword keyPopup;
	private JPasswordField textField;
	private Map map;
	private boolean on_off=true;//用于控制页面跳转的开关
	/***
	 * 初始化
	 */
	public AccAuthorPassPanel(final Map map) {
		logger.info("进入柜员密码输入页面");
		
		//将当前页面传入流程控制进行操作
		final Component comp=this;
		this.map = map;
		/* 显示时间倒计时 */
		if(delayTimer!=null){
			clearTimeText();
		}
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("柜员密码录入页面倒计时结束 ");
				/* 倒计时结束退出交易 */ 
				clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		delayTimer.start();

		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
				excuteVoice("voice/codes.wav");

			}
		});
		voiceTimer.start();

		/* 标题信息 */
		JLabel titleLabel = new JLabel("请录入授权密码:");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
		titleLabel.setBounds(0, 209, 1009, 40);
		this.showJpanel.add(titleLabel);

		// 键盘面板
		passwordPanel = new JPanel(new BorderLayout());
		passwordPanel.setBackground(Color.WHITE);
		passwordPanel.setPreferredSize(new Dimension(2024, 30));
		passwordPanel.setLayout(new BorderLayout());
		passwordPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel);
		/* 密码框 */
		textField = new JPasswordField();
		textField.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField.setBounds(292, 259, 424, 40);
		// add(textField);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				showKeyBord();
			}

		});
		textField.setColumns(10);
		//passwordPanel.add(textField, BorderLayout.CENTER);
		this.showJpanel.add(textField);
		keyPopup = new SoftKeyBoardPopuppassword(textField,8);

		errInfo = new JLabel("提示：请录入授权密码!");
		errInfo.setVisible(false);
		errInfo.setForeground(Color.RED);
		errInfo.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		errInfo.setBounds(349, 322, 258, 23);
		this.showJpanel.add(errInfo);
		
		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 处理上一页 */
				backStep(comp);
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
				nextStep(comp);
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
				openPanel(new OutputBankCardPanel());
			}
		});
		add(backButton);

	
		
	}

	/***
	 * 下一步
	 */
	@SuppressWarnings("unchecked")
	private void nextStep(final Component comp) {
		logger.info("进入确认方法");
		if(!on_off){
			logger.info("开关当前状态"+on_off);
			return;
		}
		logger.info("开关当前状态"+on_off);
		on_off=false;
		clearTimeText();
		stopTimer(voiceTimer);
		closeVoice();
        char[] cs = textField.getPassword();
        String pass = new String(cs);
		if(pass == null || pass.equals("")){
			errInfo.setVisible(true);
			on_off=true;
			return;
		}
		map.put("authorPass", pass);
		checkAuthorNo(comp);
	}

	/***
	 * 上一步
	 */
	private void backStep(final Component comp) {
		logger.info("进入上一步方法");
		clearTimeText();
		stopTimer(voiceTimer);
		closeVoice();
		openPanel(new AccAuthorNoPanel(map));
	}



	/***
	 * 键盘
	 */
	private void showKeyBord() {
		logger.info("调用软键盘");
		keyPopup.show(passwordPanel, 160, 310);
	}

	/**
	 * 调用口令授权
	 */
	private void checkAuthorNo(final Component comp){
		logger.info("调用密码授权方法");
		new Thread(){
			@Override
			public void run() {
				AccPublicBean bean=new AccPublicBean();
				bean.setSupTellerNo((String)map.get("authorNo"));
				bean.setSupTellerPass((String)map.get("authorPass"));
				try {
					logger.info("调用07660方法");
					AccountTradeCodeAction.transBean.getReqMCM001().setReqBefor("07660");
					Map params=IntefaceSendMsg.inter07660(bean);
					AccountTradeCodeAction.transBean.getReqMCM001().setReqAfter((String)params.get("resCode"), (String)params.get("errMsg"));
					String resCode=(String)params.get("resCode");
					String errMsg=(String)params.get("errMsg");
					if(!IntefaceSendMsg.SUCCESS.equals(resCode)){
						logger.info("柜员授权异常："+errMsg);
						openMistakeDialog(errMsg);
						textField.setText("");
						on_off=true;
						return;
					}
					if ("0010".equals(AccountTradeCodeAction.transBean.getProductCode())) {
					
						logger.info("进入是否打印存单确认框");
						String msg = "";
						if("0".equals(AccountTradeCodeAction.transBean.getAutoRedpFlag())){//非自动转存
							msg = "是否打印存单。是：打印。否：不打印，到期后本息自动转入银行卡。";
						}else{
							msg = "是否打印存单。是：打印；否：不打印。";
						}
						// 弹框提示是否打印存单
						openConfirmDialog(msg);
						getConfirmDialog().YseButten.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								AccountTradeCodeAction.transBean.setCertPrint("1");// 打印标识
								getConfirmDialog().disposeDialog();
								map.put("transCode", "0015");
								logger.info("同意打印并进入输入密码页面"+map.get("PRODUCT_CODE"));
								AccountTransFlow.startTransFlow(comp, map);
							}
						});
						getConfirmDialog().Nobutton.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								AccountTradeCodeAction.transBean.setCertPrint("0");// 不打印标识
								getConfirmDialog().disposeDialog();
								map.put("transCode", "0015");
								logger.info("同意打印并进入输入密码页面"+map.get("PRODUCT_CODE"));
								AccountTransFlow.startTransFlow(comp, map);
							}
						});
					} else {
						logger.info("授权后直接进行交易："+map.get("PRODUCT_CODE"));
						if(AccountTradeCodeAction.transBean.getProductCode().startsWith("JX")){
							clearTimeText();// 清空倒计时
							stopTimer(voiceTimer);// 关语音
							closeVoice();// 关语音流
							AccountTradeCodeAction.transBean.setCertPrint("0");
							params.put("transCode", "0015");
							AccountTransFlow.startTransFlow(comp, params);
						}else{
							
							openConfirmDialog("是否打印存单。是：打印。否：不打印，到期后本息自动转入银行卡。");
							//打印存单
							confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseReleased(MouseEvent e) {
									logger.info("选择打印存单按钮");
									clearTimeText();// 清空倒计时
									stopTimer(voiceTimer);// 关语音
									closeVoice();// 关语音流
									AccountTradeCodeAction.transBean.setCertPrint("1");
									confirmDialog.disposeDialog();
									map.put("transCode", "0015");
									AccountTransFlow.startTransFlow(comp, map);
								}
							});
							//不打印存单
							confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseReleased(MouseEvent e) {
									logger.info("选择否不打印存单");
									clearTimeText();// 清空倒计时
									stopTimer(voiceTimer);// 关语音
									closeVoice();// 关语音流
									AccountTradeCodeAction.transBean.setCertPrint("0");
									confirmDialog.disposeDialog();
									map.put("transCode", "0015");
									AccountTransFlow.startTransFlow(comp, map);
								}
							});
						}

					}
				} catch (Exception e) {
					logger.error("柜员授权异常："+e);
					openMistakeDialog("柜员授权异常");
					on_off=true;
				}
			}
		}.start();
	}
	
}
