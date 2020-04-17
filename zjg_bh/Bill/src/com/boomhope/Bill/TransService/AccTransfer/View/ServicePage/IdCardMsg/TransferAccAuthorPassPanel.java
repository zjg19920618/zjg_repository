package com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.IdCardMsg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.Interface.InterfaceSendMsg;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.TransferBusinessDealPanel;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.SoftKeyBoardPopuppassword;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;

/**
 * 授权柜员密码
 * 
 * @author gyw
 *
 */
public class TransferAccAuthorPassPanel extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(TransferAccAuthorPassPanel.class);
	private static final long serialVersionUID = 1L;;
	final int voiceSecond = 500;
	private JPanel passwordPanel;
	JLabel errInfo = null;// 错误提示
	private SoftKeyBoardPopuppassword keyPopup;
	private JPasswordField textField;
	private PublicAccTransferBean transferMoneyBean;
	private boolean on_off=true;
	/***
	 * 初始化
	 */
	public TransferAccAuthorPassPanel(final PublicAccTransferBean transferBean) {
		transferMoneyBean=transferBean;
		
		//将当前页面传入流程控制进行操作
		final Component comp=this;
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		delayTimer.start();

		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				voiceTimer.stop();
				closeVoice();
				excuteVoice("voice/codes.wav");

			}
		});
		voiceTimer.start();

		/* 标题信息 */
		JLabel titleLabel = new JLabel("请录入授权密码:");
		titleLabel.setHorizontalAlignment(SwingUtilities.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
		titleLabel.setBounds(0, 100, 1009, 40);
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
		textField.setBounds(288, 200, 424, 40);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				showKeyBord();
			}

		});
		textField.setColumns(10);
		this.showJpanel.add(textField);
		keyPopup = new SoftKeyBoardPopuppassword(textField,8);

		errInfo = new JLabel("提示：请录入授权密码!");
		errInfo.setVisible(false);
		errInfo.setForeground(Color.RED);
		errInfo.setHorizontalAlignment(SwingUtilities.CENTER);
		errInfo.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		errInfo.setBounds(0, 280, 1009, 23);
		this.showJpanel.add(errInfo);
		
		//上一步
		JLabel upPageButton = new JLabel();
		upPageButton.setIcon(new ImageIcon("pic/preStep.png"));
		upPageButton.setBounds(1075, 770, 150, 50);
		upPageButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//清空倒计时
				clearTimeText();
				stopTimer(voiceTimer);
				closeVoice();
				//返回时跳转页面
				openPanel(new TransferAuthorizationPanel(transferMoneyBean));
			}
		});
		add(upPageButton);
		
		//确认
		JLabel okButton = new JLabel();
		okButton.setIcon(new ImageIcon("pic/newPic/confirm.png"));
		okButton.setBounds(890, 770, 150, 50);
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//下一步
				nextStep(comp);
			}
		});
		add(okButton);
		
		//返回
		JLabel backButton = new JLabel();
		backButton.setIcon(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//清空倒计时
				clearTimeText();
				stopTimer(voiceTimer);
				closeVoice();
				//返回时跳转页面
				openPanel(new OutputBankCardPanel());
			}
		});
		add(backButton);
	}

	/***
	 * 键盘
	 */
	private void showKeyBord() {
		keyPopup.show(passwordPanel, 140, 270);
	}

	/***
	 * 下一步(校验授权密码)
	 */
	private void nextStep(final Component comp){
		new Thread(){
			@Override
			public void run() {
				openProssDialog();
				
		        String pass = new String(textField.getPassword());
				if(pass == null || pass.equals("")){
					errInfo.setVisible(true);
					closeDialog(prossDialog);
					on_off=true;
					return;
				}
				
				//获取密码
				PublicAccTransferBean bean=new PublicAccTransferBean();
				if("1".equals(transferMoneyBean.getTellerIsChecked().trim())){
					transferMoneyBean.setSupTellerPass(pass);
					bean.setSupTellerNo(transferMoneyBean.getSupTellerNo());
					bean.setSupTellerPass(transferMoneyBean.getSupTellerPass());
					logger.info("第一次授权柜员号:"+transferMoneyBean.getSupTellerNo());
					logger.info("第一次授权密码:"+pass);
		    	}else{
		    		transferMoneyBean.setSupTellerPass2(pass);
					bean.setSupTellerNo(transferMoneyBean.getSupTellerNo2());
					bean.setSupTellerPass(transferMoneyBean.getSupTellerPass2());
					logger.info("第二次授权柜员号:"+transferMoneyBean.getSupTellerNo2());
					logger.info("第二次授权密码:"+pass);
		    	}
				
				
				try {
					logger.info("开始校验授权柜员密码");
					
					transferBean.getReqMCM001().setReqBefor("07660");
					Map params=InterfaceSendMsg.inter07660(bean);
					String resCode=(String)params.get("resCode");
					String errMsg=(String)params.get("errMsg");
					transferBean.getReqMCM001().setReqAfter(resCode,errMsg);
					if(!InterfaceSendMsg.SUCCESS.equals(resCode)){
						logger.info("柜员授权密码异常："+errMsg);
						closeDialog(prossDialog);
						on_off=true;
						openMistakeDialog(errMsg);
						return;
					}
					
					logger.info("授权柜员密码校验成功");
					//清空倒计时
					clearTimeText();
					stopTimer(voiceTimer);
					closeVoice();
					closeDialog(prossDialog);
					if(new BigDecimal(transferMoneyBean.getRemitAmt()).compareTo(new BigDecimal(Property.AUTH_TWO_ONCE_AMT))>=0){
						if("1".equals(transferMoneyBean.getTellerIsChecked().trim())){
							transferMoneyBean.setTellerIsChecked("2");
							openPanel(new TransferAuthorizationPanel(transferMoneyBean));
						}else{
							//跳入下一页
							openPanel(new TransferBusinessDealPanel(transferMoneyBean));
						}
					}else{
						//跳入下一页
						openPanel(new TransferBusinessDealPanel(transferMoneyBean));
					}
					
				} catch (Exception e) {
					//清空倒计时
					clearTimeText();
					stopTimer(voiceTimer);
					closeVoice();
					closeDialog(prossDialog);
					logger.error("柜员授权密码异常："+e);
					on_off=true;
					transferBean.getReqMCM001().setIntereturnmsg("调用07660接口异常");
					MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
					serverStop("抱歉，授权柜员密码异常,请重新操作", "","调用校验授权密码接口异常");
				}
			}
		}.start();
	}
	
}
