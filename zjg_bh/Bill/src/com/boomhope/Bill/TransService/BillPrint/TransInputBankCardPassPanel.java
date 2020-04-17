package com.boomhope.Bill.TransService.BillPrint;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Driver.NewKeypadDriver;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalDriver;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.ICBankBean;

/**
 * 银行卡密码校验
 * @author ly
 *
 */
public class TransInputBankCardPassPanel extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(TransInputBankCardPassPanel.class);
	private static final long serialVersionUID = 1L;
	/*提示信息*/
	private JLabel errLabel;
	public JLabel getErrLabel() {
		return errLabel;
	}

	public void setErrLabel(JLabel errLabel) {
		this.errLabel = errLabel;
	}

	//密码文本框
	private JTextField passwordField;
	public JTextField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JTextField passwordField) {
		this.passwordField = passwordField;
	}

	public TransInputBankCardPassPanel(final BillPrintBean transBean, List<ICBankBean> bankBeans){
		final Component comp=this;
		this.billPrintBean = transBean;
		this.bankBeans = bankBeans;
		/* 倒计时打开语音 */
		logger.info("进入输入银行卡密码页面");
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);
            	excuteVoice("voice/inputbankpass.wav");
            	
            }      
        });            
		voiceTimer.start(); 
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            	try {
        			closePwd();
        		} catch (Exception e1) {
        			logger.error("关闭密码键盘socket进程异常");
        		}
            }      
        });   
		delayTimer.start(); 
		/*提示信息*/
		JLabel titleLabel = new JLabel("请录入银行卡密码");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD,40));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 60, GlobalParameter.TRANS_WIDTH-50, 60);
		this.showJpanel.add(titleLabel);
	
		/*输入密码框*/
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 35));
		passwordField.setBounds(553, 176, 400, 70);
		passwordField.setEditable(false);
		passwordField.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(passwordField);
		passwordField.setColumns(10);
		/*提示信息*/
		errLabel = new JLabel("密码不足6位，请重新输入！");
		errLabel.setHorizontalAlignment(JLabel.CENTER);
		errLabel.setFont(new Font("微软雅黑", Font.PLAIN,16));
		errLabel.setBounds(0, 150, GlobalParameter.TRANS_WIDTH, 40);
		errLabel.setVisible(false);
		this.showJpanel.add(titleLabel);
		/*输入密码操作提示*/
		String hintPicture="pic/newPic/hint.png";
		JLabel hint = new JLabel();
		hint.setIcon(new ImageIcon(hintPicture));
		hint.setBounds(65, 176, 415, 360);
		this.showJpanel.add(hint);
		
		/*提示信息*/
		JLabel information = new JLabel();
		information.setText("温馨提示");
		information.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		information.setForeground(Color.decode("#ff0101"));
		information.setBounds(553, 294, 200, 50);
		this.showJpanel.add(information);
		
		JLabel information1 = new JLabel();
		information1.setText("1.注意保护个人密码，防止被他人偷窥");
		information1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		information1.setForeground(Color.decode("#333333"));
		information1.setBounds(553, 355, 511, 50);
		this.showJpanel.add(information1);
		
		
		JLabel information2 = new JLabel();
		information2.setText("2.如需帮助请拨打唐山银行24小时客服");
		information2.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		information2.setForeground(Color.decode("#333333"));
		information2.setBounds(553, 415, 431, 50);
		this.showJpanel.add(information2);
		
		
		JLabel information3 = new JLabel();
		information3.setText("   热线0315-96368");
		information3.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		information3.setForeground(Color.decode("#333333"));
		information3.setBounds(553, 475, 228, 50);
		this.showJpanel.add(information3);
		inputPassword(comp,transBean,bankBeans);
	}
	
	/**
	 * 调用加密密码键盘
	 * @param comp
	 * @param propertyStr
	 * @param errLable
	 */
	private void inputPassword(final Component comp,final BillPrintBean transferBean, final List<ICBankBean> bankBeans) {
		Thread thread  = new Thread("调用密码键盘线程"){
            @Override
            public void run(){
            	Map<String, String> resMap;
				try {
					Map<String, String> reqMap = new HashMap<String, String>();
					reqMap.put("PassLength", GlobalDriver.KEYPAD_PASSLENGTH);	// 密码长度
					reqMap.put("EncType", GlobalDriver.KEYPAD_ENCTYPE);	// 加密方式
					reqMap.put("PinType", GlobalDriver.KEYPAD_PINTYPE);	// pin类型
					reqMap.put("PinFill", GlobalDriver.KEYPAD_PINFILL);	// pin补位方式
					reqMap.put("CardNo",transferBean.getAccNo());	//卡号
					resMap =new NewKeypadDriver().getInputPassword((BaseTransPanelNew)comp, "passwordField", "errLabel", reqMap);
					if (resMap.get("ResCode").endsWith("S")){
						
						//密码键盘加密成功
						closeVoice();
						stopTimer(voiceTimer);//关闭语音
						clearTimeText();//清空倒计时
						logger.info("密码获取成功!");
						transferBean.setInputOrderPwd(resMap.get("Password"));
						if("handInput".equals(transferBean.getImportMap().get("handPath"))){
							openPanel(new TransCheckBillInfoPanel(transferBean));
						}else{
							openPanel(new TransPassingPanel(transferBean, bankBeans));
						}
					
					}
				} catch (Exception e) {
					logger.error("密码键盘设备故障"+e);
					try {
						closePwd();
					} catch (Exception e2) {
						logger.error("密码键盘关闭失败");
					}
				}
				
            }
        };
        thread.start();
	}
}
