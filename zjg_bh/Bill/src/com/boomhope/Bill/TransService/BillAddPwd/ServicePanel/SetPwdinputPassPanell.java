package com.boomhope.Bill.TransService.BillAddPwd.ServicePanel;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Driver.NewKeypadDriver;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalDriver;
import com.boomhope.Bill.TransService.BillAddPwd.Bean.AddPasswordBean;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
/**
 * 第二次输入密码 确认
 * @author hp
 *
 */
public class SetPwdinputPassPanell extends BaseTransPanelNew{

	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(SetPwdinputPassPanell.class);
	//密码文本框
		private JTextField passwordField;
		/*提示信息*/
		private JLabel errLabel;
		  public JTextField getPasswordField() {
			return passwordField;
		}

		public void setPasswordField(JTextField passwordField) {
			this.passwordField = passwordField;
		}

		public JLabel getErrLabel() {
			return errLabel;
		}

		public void setErrLabel(JLabel errLabel) {
			this.errLabel = errLabel;
		}
		final Component comp=this;//当前页面
		
		
		public SetPwdinputPassPanell() {
			  logger.info("进入再次输入新密码页面");
			  
			  this.showTimeText(delaySecondLongTime);
				delayTimer = new Timer(delaySecondLongTime * 1000,new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						/* 倒计时结束退出交易 */ 
						clearTimeText();//清空倒计时
		            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
					}
				});
				delayTimer.start();
				/* 倒计时打开语音 */
				voiceTimer = new Timer(voiceSecond, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						stopTimer(voiceTimer);//关语音
		            	closeVoice();//关语音流
						excuteVoice("voice/qzcsrmm.wav");

					}
				});
				voiceTimer.start();
				/* 标题信息 */
				JLabel titleLabel = new JLabel("请再次输入新密码");
				titleLabel.setHorizontalAlignment(JLabel.CENTER);
				titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));
				titleLabel.setBounds(0, 150, 1009, 40);
				this.showJpanel.add(titleLabel);
				
				/*输入密码框*/
				passwordField = new JPasswordField();
				passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 35));
				passwordField.setBounds(330, 210, 350, 60);
				passwordField.setEditable(false);
				passwordField.setHorizontalAlignment(SwingUtilities.CENTER);
				this.showJpanel.add(passwordField);
				passwordField.setColumns(10);
				/*提示信息*/
				errLabel = new JLabel("密码不足6位，请重新输入！");
				errLabel.setHorizontalAlignment(JLabel.CENTER);
				errLabel.setFont(new Font("微软雅黑", Font.PLAIN,16));
				errLabel.setBounds(385, 270, 250, 40);
				errLabel.setVisible(false);
				this.showJpanel.add(errLabel);
				inputPassword(comp,addPwdBean);
		  }
		/**
		 * 调用加密密码键盘
		 * @param comp
		 * @param propertyStr
		 * @param errLable
		 */
		private void inputPassword(final Component comp,final AddPasswordBean addPwdBean) {
			Thread thread  = new Thread("调用密码键盘线程"){
	            @Override
	            public void run(){
	            	
					try {
						Map<String, String> reqMap = new HashMap<String, String>();
						reqMap.put("PassLength", GlobalDriver.KEYPAD_PASSLENGTH);	// 密码长度
						reqMap.put("EncType", GlobalDriver.KEYPAD_ENCTYPE);	// 加密方式
						reqMap.put("PinType", GlobalDriver.KEYPAD_PINTYPE);	// pin类型
						reqMap.put("PinFill", GlobalDriver.KEYPAD_PINFILL);	// pin补位方式
						if("1".equals(addPwdBean.getSubAccNoCancel())){//普通账号标识
							reqMap.put("CardNo",addPwdBean.getAccNo());
						}else{
						    reqMap.put("CardNo",addPwdBean.getSubCardNo());	//卡号
						}
						Map<String, String> resMap;
						resMap =new NewKeypadDriver().getInputPassword((BaseTransPanelNew)comp, "passwordField", "errLabel", reqMap);
						if(!"S".equals(resMap.get("ResCode"))){
							
							String[] split = resMap.get("ResMsg").split("-");
							logger.info("密码键盘密文指令调用失败："+resMap.get("ResMsg"));
							
							if("3".equals(split[0])){//密码键盘调用超时
								logger.info("调用密码键盘超时："+split[0]);
							}else{//密码键盘调用其他异常
								try {
									logger.info("关闭密码键盘进程");
									closePwd();
								} catch (Exception e1) {
									logger.error("关闭密码键盘进程失败:"+e1);
								}
								closeVoice();
						    	stopTimer(voiceTimer);//关闭语音
						    	clearTimeText();//清空倒计时
								logger.info("密码键盘密文指令调用失败:进入服务终止页");
								serverStop("抱歉,密码键盘调用失败",resMap.get("ResMsg"),"");
							}
							return;
						}
						//密码键盘加密成功
						logger.info("密码获取成功!");
						addPwdBean.setPassword(resMap.get("Password"));
						closePwd();
						if(addPwdBean.getPassword().equals(addPwdBean.getFristPassword())){
							logger.info("两次输入密码一样");
							closeVoice();
					    	stopTimer(voiceTimer);//关闭语音
					    	clearTimeText();//清空倒计时
					    	openPanel(new SetPwdCheckBillQZPanel());
						}else{
							logger.info("两次输入密码不一样");
							//弹框提示较验弱密码失败
							
					    	clearTimeText();//清空倒计时
					    	openPanel(new SetPwdNoPassPanel());
													
						}
						
					} catch (Exception e) {
						closeVoice();
				    	stopTimer(voiceTimer);//关闭语音
				    	clearTimeText();//清空倒计时
				    	
						logger.error("密码键盘调用失败"+e);
						logger.error("密码键盘调用失败:进入服务终止页");
						addPwdBean.getReqMCM001().setIntereturnmsg("密码键盘调用失败");
						MakeMonitorInfo.makeInfos(addPwdBean.getReqMCM001());
						serverStop("抱歉,密码键盘调用失败","","");
						return;
					}
					
					
	            }
	        };
	        thread.start();
		}
}
