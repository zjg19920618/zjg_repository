 package com.boomhope.Bill.TransService.BillAddPwd.ServicePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.BJ.AccCancelAuthorNoBJPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.HaveAcc.AccCancelCheckTFSecondPanel;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc.BillChangeOpenCheckTFSecondPanel;
import com.boomhope.Bill.Util.SoftKeyBoardPopups2;
import com.boomhope.Bill.Util.SoftKeyBoardPopups7;
/**
 * 手动输入存单页
 * @author Administrator
 *
 */
public class SetPwdEnterPanel extends BaseTransPanelNew{

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(SetPwdEnterPanel.class);
	public JTextField textField1;
	public JTextField textField2;
	public JTextField textField3;
	private JPanel passwordPanel1;
	private JPanel passwordPanel2;
	private SoftKeyBoardPopups2 keyPopup1;
	private SoftKeyBoardPopups7 keyPopup7;
	private SoftKeyBoardPopups2 keyPopup3;
	private JPanel passwordPanel3;
	private boolean on_off=true;//用于控制页面跳转的开关
	
	public SetPwdEnterPanel() {
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondMaxTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondMaxTime*1000,new ActionListener(){
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
				stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
				excuteVoice("voice/certificateinformation.wav");

			}
		});
		voiceTimer.start();
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请输入存单信息");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(titleLabel);	
		
		JLabel lblNewLabel = new JLabel("请输入凭证号：");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel.setBounds(40, 297, 192, 40);
	
		this.showJpanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("请输入存单账号：");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_1.setBounds(40, 204, 192, 40);
		this.showJpanel.add(lblNewLabel_1);
		
		// 输入框
		passwordPanel1 = new JPanel(new BorderLayout());
		passwordPanel1.setBackground(Color.WHITE);
		passwordPanel1.setPreferredSize(new Dimension(2024, 30));
		passwordPanel1.setLayout(new BorderLayout());
		passwordPanel1.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel1);
		/* 凭证号 */
		textField1 = new JTextField();
		textField1.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField1.setBounds(255, 297, 562, 50);
		textField1.setEditable(false);
		textField1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField1.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
				textField2.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GRAY));
				textField3.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GRAY));
				keyPopup1.show(passwordPanel1,250,350);
					
				
			}

		});
		textField1.setColumns(10);
		this.showJpanel.add(textField1);
		keyPopup1 = new SoftKeyBoardPopups2(textField1);

		
		// 输入框
		passwordPanel2 = new JPanel(new BorderLayout());
		passwordPanel2.setBackground(Color.WHITE);
		passwordPanel2.setPreferredSize(new Dimension(2024, 30));
		passwordPanel2.setLayout(new BorderLayout());
		passwordPanel2.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel2);
		
		/* 账号框 */
		textField2 = new JTextField();
		textField2.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField2.setBounds(255, 200, 564, 50);
		textField2.setEditable(false);
		textField2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField1.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GRAY));
				textField2.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
				textField3.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GRAY));
				keyPopup7.show(passwordPanel2, 250, 280);	
				
			}
		});
		textField2.setColumns(10);
		this.showJpanel.add(textField2);
		keyPopup7 = new SoftKeyBoardPopups7(textField2,21);
		
	
		JLabel lblNewLabel_2 = new JLabel("-");
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 30));
		lblNewLabel_2.setBounds(828, 200, 23, 50);
		this.showJpanel.add(lblNewLabel_2);
		// 输入框
		passwordPanel3 = new JPanel(new BorderLayout());
		passwordPanel3.setBackground(Color.WHITE);
		passwordPanel3.setPreferredSize(new Dimension(2024, 30));
		passwordPanel3.setLayout(new BorderLayout());
		passwordPanel3.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel3);
		/*子账号文本框*/
		textField3 = new JTextField();
		textField3.setBounds(858, 200, 93, 50);
		textField3.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField3.setEditable(false);
		textField3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField1.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GRAY));
				textField2.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GRAY));
				textField3.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
				keyPopup3.show(passwordPanel3, 600, 280);		
				
			}

		});
		this.showJpanel.add(textField3);
		textField3.setColumns(10);
		keyPopup3=new SoftKeyBoardPopups2(textField3);
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
	/**
	 * 下一步
	 */
	public void nextStep(){
		logger.info("凭证号输入值"+textField1.getText());
		logger.info("账号输入值"+textField2.getText());
		logger.info("子账号输入值"+textField3.getText());
		
		if((textField2.getText()==null || "".equals(textField2.getText()))&&  (textField1.getText()==null || "".equals(textField1.getText()))){//未输入账号
			openMistakeDialog("请输入账号或凭证号！");
			return;
		}
		
		
		if(!on_off){
			logger.info("开关当前状态"+on_off);
			return;
		}
		logger.info("开关当前状态"+on_off);
		on_off=false;
		

	 if((!"".equals(textField1.getText()) && textField1.getText()!=null )&&( !"".equals(textField2.getText()) && textField2.getText()!=null)){
		 logger.info("输入账号和凭证号查询存单信息");
		    addPwdBean.setBillType("");//凭证种类
			addPwdBean.setBillNo(textField1.getText());//凭证号
		 if(StringUtils.isNotBlank(textField3.getText())){
				if("0008".equals(textField2.getText().substring(6,10))){
					addPwdBean.setSubAccNoCancel("3");//电子账号标识
					logger.info("输入账号为电子子账号："+textField2.getText()+"-"+textField3.getText());
					logger.info("输入账号为电子子账号，匹配成功");
					addPwdBean.setAccNo(textField2.getText()+"-"+textField3.getText());//账号-子账号
					addPwdBean.setSubCardNo(textField2.getText());//账号
					addPwdBean.setSubAccNo(textField3.getText());//子账号
					
					
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关语音
			    	closeVoice();//关语音流
					//进入存单信息查询页
			    	openPanel(new SetPwdCheckCDSecondPanel());
					
				}else if(!"0008".equals(textField2.getText().substring(6,10))){
					
					logger.info("输入账号为卡下子账号，匹配成功");
					addPwdBean.setSubAccNoCancel("0");//卡下子账户标识
					addPwdBean.setBillType("102");//凭证种类
					addPwdBean.setAccNo(textField2.getText()+"-"+textField3.getText());//账号-子账号
					addPwdBean.setSubCardNo(textField2.getText());//账号
					addPwdBean.setSubAccNo(textField3.getText());//子账号
					
					
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关语音
			    	closeVoice();//关语音流
					
			    	//进入存单信息查询页
			    	openPanel(new SetPwdCheckCDSecondPanel());
					
				}else{
					
					logger.info("输入账号非电子账号、卡下子账号，匹配失败");
					on_off=true;
					openMistakeDialog("抱歉，输入账号非电子账号、卡下子账号，匹配失败");
					return;
				}
	    }else	if(!StringUtils.isNotBlank(textField3.getText())){//识别为普通账号
				
				logger.info("输入账号为普通账号，匹配成功");
				addPwdBean.setSubAccNoCancel("1");//普通账号标识
				addPwdBean.setAccNo(textField2.getText());
				
				clearTimeText();//清空倒计时
				stopTimer(voiceTimer);//关语音
		    	closeVoice();//关语音流
				
		    	//进入存单信息查询页
		    	openPanel(new SetPwdCheckCDSecondPanel());
				
			}else{
				
				logger.info("输入账号非普通账号，匹配失败");
				on_off=true;
				openMistakeDialog("抱歉，输入账号非普通账号，匹配失败");
				return;
			} 
		}else if(!"".equals(textField2.getText()) && textField2.getText()!=null){
			    logger.info("只输入账号查询存单信息");
			    addPwdBean.setBillType("");//凭证种类
				addPwdBean.setBillNo("");//凭证号
			 if(StringUtils.isNotBlank(textField3.getText())){
					if("0008".equals(textField2.getText().substring(6,10))){
						addPwdBean.setSubAccNoCancel("3");//电子账号标识
						logger.info("输入账号为电子子账号："+textField2.getText()+"-"+textField3.getText());
						logger.info("输入账号为电子子账号，匹配成功");
						addPwdBean.setAccNo(textField2.getText()+"-"+textField3.getText());//账号-子账号
						addPwdBean.setSubCardNo(textField2.getText());//账号
						addPwdBean.setSubAccNo(textField3.getText());//子账号
						
						
						clearTimeText();//清空倒计时
						stopTimer(voiceTimer);//关语音
				    	closeVoice();//关语音流
						//进入存单信息查询页
				    	openPanel(new SetPwdCheckCDSecondPanel());
						
					}else if(!"0008".equals(textField2.getText().substring(6,10))){
						
						logger.info("输入账号为卡下子账号，匹配成功");
						addPwdBean.setSubAccNoCancel("0");//卡下子账户标识
						addPwdBean.setBillType("102");//凭证种类
						addPwdBean.setAccNo(textField2.getText()+"-"+textField3.getText());//账号-子账号
						addPwdBean.setSubCardNo(textField2.getText());//账号
						addPwdBean.setSubAccNo(textField3.getText());//子账号
						
						
						clearTimeText();//清空倒计时
						stopTimer(voiceTimer);//关语音
				    	closeVoice();//关语音流
						
				    	//进入存单信息查询页
				    	openPanel(new SetPwdCheckCDSecondPanel());
						
					}else{
						
						logger.info("输入账号非电子账号、卡下子账号，匹配失败");
						on_off=true;
						openMistakeDialog("抱歉，输入账号非电子账号、卡下子账号，匹配失败");
						return;
					}
		    }else	if(!StringUtils.isNotBlank(textField3.getText())){//识别为普通账号
					
					logger.info("输入账号为普通账号，匹配成功");
					addPwdBean.setSubAccNoCancel("1");//普通账号标识
					addPwdBean.setAccNo(textField2.getText());
					
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关语音
			    	closeVoice();//关语音流
					
			    	//进入存单信息查询页
			    	openPanel(new SetPwdCheckCDSecondPanel());
					
				}else{
					
					logger.info("输入账号非普通账号，匹配失败");
					on_off=true;
					openMistakeDialog("抱歉，输入账号非普通账号，匹配失败");
					return;
				} 
		}else if(!"".equals(textField1.getText()) && textField1.getText()!=null){           
			
			logger.info("输入凭证号查询存单信息");
			addPwdBean.setBillType("102");//凭证种类
			addPwdBean.setBillNo(textField1.getText());//凭证号
    	
	    	//进入存单信息查询页
	    	openPanel(new SetPwdCheckCDSecondPanel());
    		
		}
	}
				

		
	}

