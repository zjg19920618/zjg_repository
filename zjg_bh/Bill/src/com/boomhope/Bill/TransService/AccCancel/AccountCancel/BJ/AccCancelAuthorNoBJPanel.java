package com.boomhope.Bill.TransService.AccCancel.AccountCancel.BJ;


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
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.HaveAcc.AccCancelAmtOverrunPanel;
import com.boomhope.Bill.TransService.AccCancel.Interface.IntefaceSendMsg;
import com.boomhope.Bill.Util.SoftKeyBoardPopups;

/***
 * 
 * 鉴伪通过，金额大于等于10万,主管鉴别存单真伪,录入授权柜员号
 * @author zjg
 *
 */
public class AccCancelAuthorNoBJPanel extends BaseTransPanelNew {

	static Logger logger = Logger.getLogger(AccCancelAuthorNoBJPanel.class);
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	JLabel errInfo = null;// 信息提示
	JLabel fingerprint = null;
	private JPanel passwordPanel;
	private SoftKeyBoardPopups keyPopup;
	private JLabel lblNewLabel;
	private boolean on_off=true;//用于控制页面跳转的开关
	
	/***
	 * 初始化
	 */
	public AccCancelAuthorNoBJPanel() {
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondShortTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondShortTime*1000,new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		delayTimer.start();
		/* 倒计时打开语音 */
		voiceTimer = new Timer(BaseTransPanelNew.voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
				excuteVoice("voice/impowers.wav");

			}
		});
		voiceTimer.start();

		/* 标题信息 */
		JLabel titleLabel = new JLabel("请录入授权柜员号!");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 100, 1009, 40);
		this.showJpanel.add(titleLabel);
		
		// 输入框
		passwordPanel = new JPanel(new BorderLayout());
		passwordPanel.setBackground(Color.WHITE);
		passwordPanel.setPreferredSize(new Dimension(2024, 30));
		passwordPanel.setLayout(new BorderLayout());
		passwordPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel);

		textField = new JTextField();
		textField.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField.setBounds(354, 259, 424, 40);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				showKeyBord();
			}

		});
		textField.setColumns(10);
		this.showJpanel.add(textField);
		keyPopup = new SoftKeyBoardPopups(textField,5);

		errInfo = new JLabel("提示：请录入授权柜员号!");
		errInfo.setVisible(false);
		errInfo.setForeground(Color.RED);
		errInfo.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		errInfo.setForeground(Color.decode("#ff0101"));
		errInfo.setBounds(354, 309, 258, 23);
		this.showJpanel.add(errInfo);
		
		lblNewLabel = new JLabel("授权柜员号:");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel.setBounds(202, 259, 142, 40);
		this.showJpanel.add(lblNewLabel);
		
		
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
				//跳转页面
				openPanel(new AccCancelAmtOverrunPanel("金额超限，请手工对存单真伪进行鉴别"));
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
				//确认
				nextStep();
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
				returnHome();
			}
		});
		add(backButton);		
	}

	/***
	 * 键盘
	 */
	private void showKeyBord() {
		keyPopup.show(passwordPanel, 160, 310);
		textField.grabFocus();
	}

	/**
	 * 查询授权柜员号
	 */
	private void nextStep() {
		new Thread() {
			@Override
			public void run() {
				openProssDialog();
				
				String str = textField.getText();
		    	if (str == null || "".equals(str)) {
		    		errInfo.setVisible(true);
		    		closeDialog(prossDialog);
		    		return;
		    	}
		    	
		    	logger.info("录入的授权员:"+str);
		    	accCancelBean.setSupTellerNo(str);
		    	accCancelBean.setTellNo1(str);
		    	accCancelBean.getReqMCM001().setAuthemp1(str);
				GlobalParameter.supTellerNo = str;
				
				try {
					logger.info("主管鉴别存单真伪，进入查询授权员");
					accCancelBean.getReqMCM001().setReqBefor("07659");
					Map inter07659 = IntefaceSendMsg.inter07659(accCancelBean);
					accCancelBean.getReqMCM001().setReqAfter((String) inter07659.get("resCode"), (String) inter07659.get("errMsg"));
					String resCode = (String) inter07659.get("resCode");
					String errMsg = (String) inter07659.get("errMsg");
					if (!"000000".equals(resCode)) {
						logger.info("柜员查询异常：" + errMsg);
						closeDialog(prossDialog);
						openMistakeDialog(errMsg);
						return;
					}
					logger.info("查询授权柜员号成功");
					logger.info("授权方式:"+accCancelBean.getCheckMod());
					String checkMod = accCancelBean.getCheckMod();
					accCancelBean.getReqMCM001().setAuthmethod1(checkMod);
					
					//清空倒计时
					clearTimeText();
					stopTimer(voiceTimer);//关语音
			    	closeVoice();//关语音流
					closeDialog(prossDialog);
					authorDeal( checkMod);
				} catch (Exception e) {
					logger.error("柜员查询异常：" + e);
					closeDialog(prossDialog);
					openMistakeDialog("柜员查询异常");
				}
			}

		}.start();
	}
	
	//柜员授权方式选择
	private void authorDeal( final String checkMod) {
		
		if ("1".equals(checkMod)) {// 1 口令2 指纹
			logger.info("主管鉴别存单真伪，进入授权密码录入页");
			openPanel(new AccCancelAuthorPassBJPanel());
		} else {
			logger.info("主管鉴别存单真伪，进入指纹授权录入页");
			openPanel(new AccCancelAuthorFingerprintBJPanel());
		}		
			
	}	
}
