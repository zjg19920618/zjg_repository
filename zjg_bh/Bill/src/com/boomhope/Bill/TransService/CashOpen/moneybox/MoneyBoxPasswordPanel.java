package com.boomhope.Bill.TransService.CashOpen.moneybox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Driver.KeypadDriver;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
/**
 *手动输入密码
 * @author gyw
 * 
 */
public class MoneyBoxPasswordPanel extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(MoneyBoxPasswordPanel.class);
	private static final long serialVersionUID = 1L;
	//密码文本框
	/*提示信息*/
	private JLabel errLabel;
	
	public JTextField txtPassWord;
	public JTextField txtKnowPass;//明文密码
	
	private boolean on_off=true;//开关控制
	public MoneyBoxPasswordPanel(final PublicCashOpenBean transBean) {
		this.cashBean = transBean;
		
		txtKnowPass = new JTextField();
		final Component comp=this;
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	on_off=false;
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);
				closeVoice();
				excuteVoice("voice/enterPasswords.wav");
			}
		});
		voiceTimer.start();
		
		/*提示信息*/
		JLabel titleLabel = new JLabel("请输入密码");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD,40));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 60, GlobalParameter.TRANS_WIDTH-50, 60);
		this.showJpanel.add(titleLabel);
	
		/* 密码框 */
		txtPassWord = new JTextField();
		txtPassWord.setForeground(Color.BLACK);
		txtPassWord.setFont(new Font("微软雅黑", Font.PLAIN, 40));
	
		txtPassWord.setBounds(553, 176, 400, 50);
		txtPassWord.setEditable(false);
		
		txtPassWord.setColumns(10);
		this.showJpanel.add(txtPassWord);
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
		//确认
		JLabel lblNewLabel = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		lblNewLabel.setBounds(1258, 770, 150, 50);
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				/* 处理下一页 */
				logger.info("点击确认按钮");	
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				nextStep(transBean);
			}

		});
		this.add(lblNewLabel);
		createThread(transBean);
	}
	
	private void createThread(final PublicCashOpenBean transBean) {
		new Thread(){
			@Override
			public void run(){
				inputPassword();
				
			}
		}.start();
	}
	
	private void inputPassword() {
		try {
			new KeypadDriver().getKnowPassword(this, "txtPassWord", "txtKnowPass", "4", "20", "6");
		} catch (Exception e) {
			logger.error("密码键盘设备故障");
		}
	}

	public void nextStep(PublicCashOpenBean transBean) {
		String strPass = txtKnowPass.getText();

		if(StringUtils.isBlank(strPass) || strPass.length() < 6){
			errLabel.setVisible(true);
			on_off=true;
			return;
		}
		String res;
		try {
			res = closePassword();
			if(!res.equals("0")){
				logger.error("密码键盘设备故障!没有正常关闭");
			}
		} catch (Exception e) {
			logger.error("密码键盘设备故障!没有正常关闭");
		}
		// 传递参数值

		// 2.密码
		transBean.setInputOrderPwd(strPass);
		clearTimeText();
		openPanel(new MoneyBoxDeputySelectionPanel(transBean));
	}
	
	
	

	
	
	
	
	public JTextField getTxtPassWord() {
		return txtPassWord;
	}
	public void setTxtPassWord(JTextField txtPassWord) {
		this.txtPassWord = txtPassWord;
	}
	public JTextField getTxtKnowPass() {
		return txtKnowPass;
	}
	public void setTxtKnowPass(JTextField txtKnowPass) {
		this.txtKnowPass = txtKnowPass;
	}
	/**
	 * 密码键盘明文输入银行卡号，调用关闭密码键盘
	 */
	public String closePassword() throws Exception{
		if(!KeypadDriver.socket.isClosed()&&!KeypadDriver.socket.isInputShutdown()){
			KeypadDriver.socket.shutdownInput();
		}if(!KeypadDriver.socket.isClosed()&&!KeypadDriver.socket.isOutputShutdown()){
			KeypadDriver.socket.shutdownOutput();
		}
		Thread.sleep(100);
		String res = new KeypadDriver().closePwd("6");
		return res;
	}
}
