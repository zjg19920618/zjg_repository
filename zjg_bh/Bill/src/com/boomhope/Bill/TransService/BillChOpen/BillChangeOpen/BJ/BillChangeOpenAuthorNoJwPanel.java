package com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.BJ;


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
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc.BillChangeOpenAmtOverrunPanel;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc.BillChangeOpenCheckBillPanel;
import com.boomhope.Bill.TransService.BillChOpen.Interface.IntefaceSendMsg;
import com.boomhope.Bill.Util.SoftKeyBoardPopups;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;

/***
 * 
 * 鉴伪不通过，金额大于等于10万,录入两个授权柜员号
 * @author zjg
 *
 */
public class BillChangeOpenAuthorNoJwPanel extends BaseTransPanelNew {

	static Logger logger = Logger.getLogger(BillChangeOpenAuthorNoJwPanel.class);
	private static final long serialVersionUID = 1L;
	private JPanel passwordPanel;
	private JTextField textField;
	private JLabel lblNewLabel;
	JLabel errInfo = null;// 信息提示
	private JPanel passwordPanel1;
	private JTextField textField1;
	private JLabel lblNewLabel1;
	JLabel errInfo1 = null;// 信息提示
	JLabel errInfo2 = null;// 信息提示
	JLabel errInfo3 = null;// 信息提示
	private SoftKeyBoardPopups keyPopup;
	private SoftKeyBoardPopups keyPopup1;
	private boolean on_off=true;//用于控制页面跳转的开关
	
	/***
	 * 初始化
	 */
	public BillChangeOpenAuthorNoJwPanel() {
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
		JLabel titleLabel = new JLabel("请两位授权柜员分别鉴伪!");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 100, 1009, 40);
		this.showJpanel.add(titleLabel);
		
		//第一组授权 输入框
		passwordPanel = new JPanel(new BorderLayout());
		passwordPanel.setBackground(Color.WHITE);
		passwordPanel.setPreferredSize(new Dimension(2024, 30));
		passwordPanel.setLayout(new BorderLayout());
		passwordPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel);

		textField = new JTextField();
		if("1".equals(bcOpenBean.getTellNoState())){
			textField.setText(bcOpenBean.getTellNo1());
		}
		textField.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField.setBounds(354, 259, 424, 40);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if("1".equals(bcOpenBean.getTellNoState())){
					
					logger.info("第一个柜员已授权");
					
				}else{
					
					showKeyBord();
					errInfo1.setVisible(false);
				}
			}

		});
		textField.setColumns(10);
		this.showJpanel.add(textField);
		keyPopup = new SoftKeyBoardPopups(textField,5);

		errInfo = new JLabel("提示：请录入授权柜员1!");
		errInfo.setVisible(false);
		errInfo.setForeground(Color.RED);
		errInfo.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		errInfo.setForeground(Color.decode("#ff0101"));
		errInfo.setBounds(354, 309, 258, 23);
		this.showJpanel.add(errInfo);
		
		errInfo2 = new JLabel("已鉴伪");
		errInfo2.setVisible(false);
		if("1".equals(bcOpenBean.getTellNoState())){
			errInfo2.setVisible(true);
		}
		errInfo2.setForeground(Color.RED);
		errInfo2.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		errInfo2.setForeground(Color.decode("#ff0101"));
		errInfo2.setBounds(810, 259, 258, 40);
		this.showJpanel.add(errInfo2);
		
		lblNewLabel = new JLabel("授权柜员1:");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel.setBounds(202, 259, 142, 40);
		this.showJpanel.add(lblNewLabel);
		
		//第二组授权 输入框
		passwordPanel1 = new JPanel(new BorderLayout());
		passwordPanel1.setBackground(Color.WHITE);
		passwordPanel1.setPreferredSize(new Dimension(2024, 30));
		passwordPanel1.setLayout(new BorderLayout());
		passwordPanel1.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel1);

		textField1 = new JTextField();
		textField1.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField1.setBounds(354, 340, 424, 40);
		textField1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if("1".equals(bcOpenBean.getTellNoState())){
					
					logger.info("第一个柜员交员已授权");
					showKeyBord1();
					errInfo1.setVisible(false);
					
				}else{
					errInfo1.setText("请先点击“确认”按钮，完成授权柜员1的操作!");
					errInfo1.setVisible(true);
				}
			}

		});
		textField1.setColumns(10);
		this.showJpanel.add(textField1);
		keyPopup1 = new SoftKeyBoardPopups(textField1,5);

		errInfo1 = new JLabel("提示：请录入授权柜号2!");
		errInfo1.setVisible(false);
		errInfo1.setForeground(Color.RED);
		errInfo1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		errInfo1.setForeground(Color.decode("#ff0101"));
		errInfo1.setBounds(354, 390, 500, 23);
		this.showJpanel.add(errInfo1);
		
		lblNewLabel1 = new JLabel("授权柜员2:");
		lblNewLabel1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel1.setBounds(202, 340, 142, 40);
		this.showJpanel.add(lblNewLabel1);		
		
		
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
				//清空授权标识
				if("1".equals(bcOpenBean.getTellNoState())){
					
					//清空授权标识
					bcOpenBean.setTellNoState("");
					bcOpenBean.setTellNo1("");
				}
				//跳转页面
				openPanel(new BillChangeOpenCheckBillPanel());
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
				billHKExit();
			}
		});
		add(backButton);		
	}

	/***
	 * 键盘
	 */
	private void showKeyBord() {
		keyPopup.show(passwordPanel, 160, 300);
		textField.grabFocus();
	}
	
	/***
	 * 键盘1
	 */
	private void showKeyBord1() {
		keyPopup1.show(passwordPanel1, 160, 380);
		textField1.grabFocus();
	}

	/**
	 * 查询授权柜员号
	 */
	private void nextStep() {
		new Thread() {
			@Override
			public void run() {
				openProssDialog();
				
				logger.info("第一次鉴伪不通过，手动输入，金额大于等于10万,进行二次授权");
				String str1 = textField.getText();
				String str2 = textField1.getText();
				bcOpenBean.setTellNo1(str1);
				bcOpenBean.setTellNo2(str2);
				
				if (str1 == null || "".equals(str1)) {//第一个柜员未录入提示
					
		    		errInfo.setVisible(true);
		    		closeDialog(prossDialog);
		    		return;
		    		
		    	}else{//第一个柜员已录入
		    		
		    		if(str2 == null || "".equals(str2)){//第二个柜员未录入
		    			
		    			logger.info("录入的授权柜员1:"+str1);
		    			bcOpenBean.setSupTellerNo(str1);
		    			GlobalParameter.supTellerNo = str1;
		    			
		    		}else{
		    			
		    			if(str1.equals(str2)){//两次柜员号不能一致
		    				errInfo1.setText("两次授权柜员不能一致，请重新录入!");
							errInfo1.setVisible(true);
							closeDialog(prossDialog);
							return;
		    				
		    			}else{
		    				
		    				logger.info("录入的授权柜员2:"+str2);
		    				bcOpenBean.setSupTellerNo(str2);
		    				GlobalParameter.supTellerNo = str2;
		    			}
		    		}
		    	}
		    	
				try {
					logger.info("鉴伪失败，进入查询授权员");
					bcOpenBean.getReqMCM001().setReqBefor("07659");
					Map inter07659 = IntefaceSendMsg.inter07659(bcOpenBean);
					String resCode = (String) inter07659.get("resCode");
					String errMsg = (String) inter07659.get("errMsg");
					bcOpenBean.getReqMCM001().setReqAfter(resCode, errMsg);
					if (!"000000".equals(resCode)) {
						logger.info("柜员查询异常：" + errMsg);
						bcOpenBean.getReqMCM001().setIntereturnmsg("柜员查询异常");
						MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
						closeDialog(prossDialog);
						openMistakeDialog(errMsg);
						return;
					}
					logger.info("查询授权柜员号成功");
					logger.info("授权方式:"+bcOpenBean.getCheckMod());
					String checkMod = bcOpenBean.getCheckMod();
					
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
	    	openPanel(new BillChangeOpenAuthorPassJwPanel());
		} else {
			logger.info("主管鉴别存单真伪，进入指纹授权录入页");
			openPanel(new BillChangeOpenAuthorFingerprintJwPanel());
		}		
			
	}	
}
