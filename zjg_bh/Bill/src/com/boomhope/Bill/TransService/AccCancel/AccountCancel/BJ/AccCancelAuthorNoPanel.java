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
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.HaveAcc.AccCancelDepositAffirmSignaPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.checkInfo.AccCancelCheckAgentPhotosPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.checkInfo.AccCancelCheckPhotosPanel;
import com.boomhope.Bill.TransService.AccCancel.Interface.IntefaceSendMsg;
import com.boomhope.Bill.Util.SoftKeyBoardPopups;

/***
 * 
 * 电子签名确认,录入授权柜员号
 * @author zjg
 *
 */
public class AccCancelAuthorNoPanel extends BaseTransPanelNew {

	static Logger logger = Logger.getLogger(AccCancelAuthorNoPanel.class);
private static final long serialVersionUID = 1L;
	
	private JPanel passwordPanel;//软键盘面板
	JLabel errInfo = null;// 错误提示
	private SoftKeyBoardPopups keyPopup;//软键盘对象
	private JTextField text;//输入文本框
	private boolean on_off=true;//用于控制页面跳转的开关
	public AccCancelAuthorNoPanel(){
		
		//设置倒计时
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start();
		
		
		/* 倒计时打开语音 */
		if(accCancelBean.getTellerIsChecked()==null || "".equals(accCancelBean.getTellerIsChecked())){
			voiceTimer = new Timer(voiceSecond, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stopTimer(voiceTimer);//关语音
					closeVoice();//关语音流
					excuteVoice("voice/checkInfo.wav");
					
				}
			});
		}else{
			voiceTimer = new Timer(voiceSecond, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stopTimer(voiceTimer);//关语音
					closeVoice();//关语音流
					excuteVoice("voice/impowers.wav");
					
				}
			});
		}
		voiceTimer.start();
		
		//主管授权
		JLabel jlabel = new JLabel("请确认销户信息后授权");
		jlabel.setFont(new Font("微软雅黑",Font.BOLD,40));
		jlabel.setHorizontalAlignment(0);
		jlabel.setBounds(0,100,1009,45);
		this.showJpanel.add(jlabel);
		
		//客户信息查询 
		JLabel jbutton = new JLabel(new ImageIcon("pic/customerInfo.png"));
		jbutton.setHorizontalAlignment(0);
		jbutton.setBounds(305, 200, 400, 40);
		jbutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
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
            	
            	//表示已经进入授权检查
            	accCancelBean.setTellerIsChecked("1");
            	
            	//有代理人
        		if ("1".equals(accCancelBean.getHavAgentFlag())) {
        			
        			//进入代理人图片核查页
        			openPanel(new AccCancelCheckAgentPhotosPanel());
        			
        		} else {
        			
        			//进入本人图片核查页
        			openPanel(new AccCancelCheckPhotosPanel());
        			
        		}
			}
		});
		this.showJpanel.add(jbutton);
		
		//授权柜员号
		JLabel jlabel1 = new JLabel("授权柜员号：");
		jlabel1.setFont(new Font("微软雅黑",Font.PLAIN,24));
		jlabel1.setHorizontalTextPosition(SwingConstants.LEFT);
		jlabel1.setBounds(300, 290, 150, 40);
		this.showJpanel.add(jlabel1);
		
		//授权柜员号信息
		text = new JTextField();
		text.setBounds(450, 290, 250, 40);
		text.setFont(new Font("微软雅黑",Font.PLAIN,24));
		text.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//点击文本框输入柜员号
				showKeyBord();
			}
			
		});
		this.showJpanel.add(text);
		
		
		//提示信息
		errInfo = new JLabel("提示：请录入授权柜员号!");
		errInfo.setVisible(false);
		errInfo.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		errInfo.setForeground(Color.decode("#ff0101"));
		errInfo.setBounds(305, 370, 400, 40);
		this.showJpanel.add(errInfo);
		
		// 键盘面板
		passwordPanel = new JPanel(new BorderLayout());
		passwordPanel.setBackground(Color.WHITE);
		passwordPanel.setPreferredSize(new Dimension(2024, 30));
		passwordPanel.setLayout(new BorderLayout());
		passwordPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel);
		text.setColumns(10);
		keyPopup = new SoftKeyBoardPopups(text,5);
		
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
				accCancelBean.setTellerIsChecked("");//清空授权检查状态
				openPanel(new AccCancelDepositAffirmSignaPanel());
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
				accCancelExit();
			}
		});
		add(backButton);
		
	}
	
	/***
	 * 键盘
	 */
	private void showKeyBord() {
		if(!"2".equals(accCancelBean.getTellerIsChecked())){
			openMistakeDialog("请先检查客户身份以及账户信息是否正确！");
			return;
		}
		keyPopup.show(passwordPanel, 140, 350);
		text.grabFocus();
	}

	/**
	 * 查询授权柜员号
	 */
	private void nextStep() {
		new Thread() {
			@Override
			public void run() {
				openProssDialog();
				
				String str = text.getText();
		    	if (str == null || "".equals(str)) {
		    		errInfo.setVisible(true);
		    		closeDialog(prossDialog);
		    		return;
		    	}
		    	
		    	logger.info("录入的授权员:"+str);
		    	accCancelBean.setTellNo3(str);
		    	accCancelBean.setSupTellerNo(str);
		    	accCancelBean.getReqMCM001().setAuthemp1(str);
				GlobalParameter.supTellerNo = str;
				
				try {
					logger.info("插入身份证，进入查询授权员");
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
			logger.info("进入口令授权"+checkMod);
			openPanel(new AccCancelAuthorPassPanel());
		} else {
			logger.info("进入指纹授权"+checkMod);
			openPanel(new AccCancelAuthorFingerprintPanel());
		}
			
	}	
}
