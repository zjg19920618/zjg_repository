/**
 * 
 */
package com.boomhope.Bill.TransService.LostReport.Page.Lost;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.LostReport.Bean.AccNoCheck08223;
import com.boomhope.Bill.TransService.LostReport.Bean.EAccInfoBean;
import com.boomhope.Bill.TransService.LostReport.Bean.LostPubBean;
import com.boomhope.Bill.TransService.LostReport.Bean.ShowAccNoMsgBean;
import com.boomhope.Bill.TransService.LostReport.Bean.SubAccInfoBean;
import com.boomhope.Bill.TransService.LostReport.Interface.InterfaceSendMsg;
import com.boomhope.Bill.Util.DateChooser;
import com.boomhope.Bill.Util.GuaLostAccKeyBoardPop;
import com.boomhope.Bill.Util.GuaLostAmtKeyBoardPop;
import com.boomhope.Bill.Util.GuaLostSubAccKeyBoardPop;
import com.boomhope.Bill.Util.StringUtils;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;

/**
 * @Description:输入卡/账号信息页
 * @author zjg
 * @date 2018年3月26日 上午9:29:03
 */
@SuppressWarnings("static-access")
public class LostEnterAccNoMsgPage extends BaseTransPanelNew {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(LostEnterAccNoMsgPage.class);
	private boolean on_off = true;// 用于控制页面跳转的开关
	public JTextField textField1;
	public JTextField textField2;
	public JTextField textField3;
	public JTextField textField4;
	private JPanel passwordPanel1;
	private JPanel passwordPanel2;
	private JPanel passwordPanel3;
	private JPanel passwordPanel4;
	private GuaLostAccKeyBoardPop keyPopup1;
	private GuaLostSubAccKeyBoardPop keyPopup2;
	private GuaLostAmtKeyBoardPop keyPopup4;
	public JTextField textField_4;

	public LostEnterAccNoMsgPage() {

		baseTransBean.setThisComponent(this);

		/* 显示时间倒计时 */
		this.showTimeText(BaseTransPanelNew.delaySecondLongestTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondLongestTime * 1000,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						/* 倒计时结束退出交易 */
						clearTimeText();
						serverStop("温馨提示：服务已超时，请结束您的交易！", "", "");
					}
				});
		delayTimer.start();

		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);// 关语音
				closeVoice();// 关语音流
				excuteVoice("voice/LostluRuAcc.wav");

			}
		});
		voiceTimer.start();

		// 账号/卡号
		JLabel label1 = new JLabel("账号/卡号：");
		label1.setBounds(60, 100, 300, 40);
		label1.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		label1.setHorizontalAlignment(SwingUtilities.LEFT);
		this.showJpanel.add(label1);

		// 输入框
		passwordPanel1 = new JPanel(new BorderLayout());
		passwordPanel1.setBackground(Color.WHITE);
		passwordPanel1.setPreferredSize(new Dimension(2024, 30));
		passwordPanel1.setLayout(new BorderLayout());
		passwordPanel1.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel1);
		/* 账号/卡号文本框 */
		textField1 = new JTextField();
		textField1.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField1.setBounds(280, 95, 562, 50);
		textField1.setEditable(false);
		textField1.setBackground(Color.WHITE);
		textField1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField1.setBorder(javax.swing.BorderFactory
						.createLineBorder(Color.RED));
				textField2.setBorder(javax.swing.BorderFactory
						.createLineBorder(Color.GRAY));
				textField3.setBorder(javax.swing.BorderFactory
						.createLineBorder(Color.GRAY));
				textField4.setBorder(javax.swing.BorderFactory
						.createLineBorder(Color.GRAY));
				keyPopup1.show(passwordPanel1, 280, 147);

			}

		});
		textField1.setColumns(10);
		this.showJpanel.add(textField1);
		keyPopup1 = new GuaLostAccKeyBoardPop(textField1,21);

		// 子账号
		JLabel label2 = new JLabel("子   账  号：");
		label2.setBounds(60, 170, 300, 40);
		label2.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		label2.setHorizontalAlignment(SwingUtilities.LEFT);
		this.showJpanel.add(label2);

		// 输入框
		passwordPanel2 = new JPanel(new BorderLayout());
		passwordPanel2.setBackground(Color.WHITE);
		passwordPanel2.setPreferredSize(new Dimension(2024, 30));
		passwordPanel2.setLayout(new BorderLayout());
		passwordPanel2.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel2);

		/* 子账号文本框 */
		textField2 = new JTextField();
		textField2.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField2.setBounds(280, 165, 93, 50);
		textField2.setEditable(false);
		textField2.setBackground(Color.WHITE);
		textField2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField1.setBorder(javax.swing.BorderFactory
						.createLineBorder(Color.GRAY));
				textField2.setBorder(javax.swing.BorderFactory
						.createLineBorder(Color.RED));
				textField3.setBorder(javax.swing.BorderFactory
						.createLineBorder(Color.GRAY));
				textField4.setBorder(javax.swing.BorderFactory
						.createLineBorder(Color.GRAY));
				keyPopup2.show(passwordPanel2, 280, 218);

			}
		});
		textField2.setColumns(10);
		this.showJpanel.add(textField2);
		keyPopup2 = new GuaLostSubAccKeyBoardPop(textField2,5);

		// 开户日期
		JLabel label3 = new JLabel("开户日期：");
		label3.setBounds(60, 240, 300, 40);
		label3.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		label3.setHorizontalAlignment(SwingUtilities.LEFT);
		this.showJpanel.add(label3);

		// 输入框
		DateChooser date = DateChooser.getInstance("yyyy-MM-dd");
		passwordPanel3 = new JPanel(new BorderLayout());
		passwordPanel3.setBackground(Color.WHITE);
		passwordPanel3.setPreferredSize(new Dimension(2024, 30));
		passwordPanel3.setLayout(new BorderLayout());
		passwordPanel3.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel3);
		/* 开户日期文本框 */
		textField3 = new JTextField("单击选择日期");
		textField3.setBounds(280, 235, 460, 50);
		textField3.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField3.setBackground(Color.WHITE);
		textField3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField1.setBorder(javax.swing.BorderFactory
						.createLineBorder(Color.GRAY));
				textField2.setBorder(javax.swing.BorderFactory
						.createLineBorder(Color.GRAY));
				textField3.setBorder(javax.swing.BorderFactory
						.createLineBorder(Color.RED));
				textField4.setBorder(javax.swing.BorderFactory
						.createLineBorder(Color.GRAY));

			}

		});
		textField3.setColumns(10);
		date.register(textField3);
		this.showJpanel.add(textField3);

		// 金额
		JLabel label4 = new JLabel("金       额：");
		label4.setBounds(60, 310, 300, 40);
		label4.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		label4.setHorizontalAlignment(SwingUtilities.LEFT);
		this.showJpanel.add(label4);

		// 输入框
		passwordPanel4 = new JPanel(new BorderLayout());
		passwordPanel4.setBackground(Color.WHITE);
		passwordPanel4.setPreferredSize(new Dimension(2024, 30));
		passwordPanel4.setLayout(new BorderLayout());
		passwordPanel4.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel4);
		/* 金额文本框 */
		textField4 = new JTextField();
		textField4.setBounds(280, 305, 460, 50);
		textField4.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField4.setEditable(false);
		textField4.setBackground(Color.WHITE);
		textField4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField1.setBorder(javax.swing.BorderFactory
						.createLineBorder(Color.GRAY));
				textField2.setBorder(javax.swing.BorderFactory
						.createLineBorder(Color.GRAY));
				textField3.setBorder(javax.swing.BorderFactory
						.createLineBorder(Color.GRAY));
				textField4.setBorder(javax.swing.BorderFactory
						.createLineBorder(Color.RED));
				keyPopup4.show(passwordPanel4, 280, 358);
				
			}

		});
		this.showJpanel.add(textField4);
		textField4.setColumns(10);
		keyPopup4 = new GuaLostAmtKeyBoardPop(textField4);
		keyPopup4.addPopupMenuListener(new PopupMenuListener() {
			
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				String money=textField4.getText();
				money=StringUtils.full2Half(money);
				if(money.matches("^[0-9]*[0-9][0-9]*$")){
					money = money+".00";
				}else if(money.startsWith(".")&&money.length()==3){
					money="0"+money;
				}else if(money.startsWith(".")&&money.length()==1){
					money="0"+money+"00";
				}else if(money.endsWith(".")){
					money = money+"00";
				}else if(money.startsWith(".")&&money.length()==2){
					money="0"+money+"0";
				}else if(money.contains(".")){
					String[] split = money.split("\\.");
					if(split[1].length()==1){
						money=money+"0";
					}
				}
				textField4.setText(money);
			}
			
			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//温馨提示
		if("1".equals(lostPubBean.getYseNoPass())){
			JLabel tishi = new JLabel("<html><p>温馨提示：银行卡子账号/电子账户子账号打印出存单的，不支持密码+存单挂失，</p>"
					+ "<p>请先使用银行卡/随身银行重置密码，再进行存单挂失.</p></html>");
			tishi.setBounds(0, 520, 989, 55);
			tishi.setForeground(Color.RED);
			tishi.setFont(new Font("微软雅黑",Font.PLAIN,20));
			tishi.setHorizontalAlignment(SwingUtilities.CENTER);
			this.showJpanel.add(tishi);
		}

		// 确认
		JLabel okButton = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		okButton.setBounds(892, 770, 150, 50);
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				ok();
			}
		});
		add(okButton);

		// 上一步
		JLabel back = new JLabel(new ImageIcon("pic/preStep.png"));
		back.setBounds(1075, 770, 150, 50);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (!on_off) {
					logger.info("开关当前状态" + on_off);
					return;
				}
				logger.info("开关当前状态" + on_off);
				on_off = false;

				clearTimeText();// 清空倒计时
				stopTimer(voiceTimer);// 关闭语音
				closeVoice();// 关闭语音流

				back();
			}
		});
		add(back);

		// 退出
		JLabel exit = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		exit.setBounds(1258, 770, 150, 50);
		exit.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (!on_off) {
					logger.info("开关当前状态" + on_off);
					return;
				}
				logger.info("开关当前状态" + on_off);
				on_off = false;

				clearTimeText();
				stopTimer(voiceTimer);// 关闭语音
				closeVoice();// 关闭语音流

				exit();
			}
		});
		add(exit);
	}

	/**
	 * 校验输入的文本框内容
	 */
	public boolean checkTextfiled() {
		logger.info("输入的账号/卡号：" + textField1.getText());
		if ("".equals(textField1.getText()) || null == textField1.getText()) {
			openMistakeDialog("请输入账号/卡号");
			return false;
		}
		logger.info("输入的子帐号：" + textField2.getText());

		logger.info("输入的开户日期：" + textField3.getText());
		if ("单击选择日期".equals(textField3.getText())) {
			openMistakeDialog("请选择开户日期");
			return false;
		}

		logger.info("输入的账户金额：" + textField4.getText());
		if ("".equals(textField4.getText()) || null == textField4.getText()) {
			openMistakeDialog("请输入账户金额");
			return false;
		}
		// 不为正整数或者小数位数不等于2位
		String money = textField4.getText();

		if (money.contains(".")) {
			String[] split = money.split("\\.");
			if (split.length == 2) {
				if (split[0].matches("^[0-9]*[0-9][0-9]*$")
						&& (split[1].matches("^[0-9]*[0-9][0-9]*$"))) {// 整数、小数都为数字

				} else {
					openMistakeDialog("账户金额不合法，请重新输入！");
					return false;
				}
				if (split[1].length() == 1) {
					money = textField4.getText() + "0";
				} else if (split[1].length() != 2) {
					openMistakeDialog("账户金额只保留两位小数，请重新输入！");
					return false;
				}
			} else {
				openMistakeDialog("账户金额不合法，请重新输入！");
				return false;
			}
		} else {
			if (money.matches("^[0-9]*[0-9][0-9]*$")) {
				money = textField4.getText() + ".00";
			} else {
				openMistakeDialog("账户金额不合法，请重新输入！");
				return false;
			}
		}
		return true;

	}
	
	/**
	 * 查询客户号
	 */
	public boolean checkCust(){
		
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("07519");
			Map inter07519 = InterfaceSendMsg.inter07519(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter07519.get("resCode"), (String)inter07519.get("errMsg"));
			if(!"000000".equals(inter07519.get("resCode"))){
				closeDialog(prossDialog);
				logger.info("调用个人客户查询( 01020)-前置07519接口失败："+inter07519.get("errMsg"));
				openMistakeDialog((String)inter07519.get("errMsg")+"，请重新输入");
				return false;
			}
		
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用个人客户查询( 01020)-前置07519接口异常："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用07519接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("查询客户信息失败，请联系大堂经理", "", "调用07519接口失败");
			return false;
		}
		return true;		 
	}
	
	/**
	 * 查询卡信息
	 */
	public boolean checkCardMsg(){
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("03845");
			Map inter03845 = InterfaceSendMsg.inter03845(lostPubBean); 
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter03845.get("resCode"), (String)inter03845.get("errMsg"));
			if(!"000000".equals(inter03845.get("resCode"))){
				closeDialog(prossDialog);
				logger.info("卡信息查询失败");
				openMistakeDialog((String)inter03845.get("errMsg")+"，请重新输入");
				return false;
			}
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("卡信息查询失败"+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用03845接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("卡信息查询失败，请联系大堂经理","", "调用03845接口异常");
			return false;
		}
		return true;
	}
	
	/**
	 * 查询卡账户信息
	 */
	public boolean checkCardAcct(){
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("03521");
			Map inter03521 = InterfaceSendMsg.inter03521(lostPubBean); 
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter03521.get("resCode"), (String)inter03521.get("errMsg"));
			if(!"000000".equals(inter03521.get("resCode"))){
				closeDialog(prossDialog);
				logger.info(inter03521.get("errMsg"));
				openMistakeDialog((String)inter03521.get("errMsg")+"，请重新输入");
				return false;
			}
			
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("卡账户信息查询失败"+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用03521接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("卡账户信息查询失败，请联系大堂经理","", "调用03521接口异常");
			return false;
		}
		return true;
	}
	
	/**
	 * 查询转入卡状态1
	 */
	public Map checkcard1(LostPubBean bean){
		Map card03521 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("03521");
			card03521 = InterfaceSendMsg.card03521(bean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)card03521.get("resCode"), (String)card03521.get("errMsg"));
			if(!"000000".equals(card03521.get("resCode"))){
				
				   logger.info("调用账户信息查询及密码验证-前置03521接口失败："+card03521.get("errMsg"));
				   closeDialog(prossDialog);
				   //上送流水信息
				   MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				   serverStop("查询银行卡信息失败，请联系大堂经理", (String) card03521.get("errMsg"), "");
				   return card03521;
			}
		
		} catch (Exception e) {
			
			logger.error("调用账户信息查询及密码验证-前置03521接口异常："+e);
			closeDialog(prossDialog);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用03521接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop( "查询银行卡信息失败，请联系大堂经理", "", "调用03521接口失败");
			card03521.put("resCode", "999999");
			return card03521;
		}
		return card03521;
	}					
			
	
	/**
	 * 查询转入卡状态2
	 */
	public Map checkcard2(LostPubBean bean){
		Map card07601 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("07601");
			card07601 = InterfaceSendMsg.card07601(bean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)card07601.get("resCode"), (String)card07601.get("errMsg"));
			if(!"000000".equals(card07601.get("resCode"))){
				
				   logger.info("调用账号信息综合查询【02880】-前置07601接口失败："+card07601.get("errMsg"));
				   closeDialog(prossDialog);
				   //上送流水信息
				   MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				   serverStop("查询银行卡信息失败，请联系大堂经理", (String) card07601.get("errMsg"), "");
				   return card07601;
			}
		
		} catch (Exception e) {
			
			logger.error("调用账号信息综合查询【02880】-前置07601接口异常："+e);
			closeDialog(prossDialog);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用07601接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("查询银行卡信息失败，请联系大堂经理", "", "调用07601接口失败");
			card07601.put("resCode", "999999");
			return card07601;
		}
		return card07601;
	}						
	
	/**
	 * 查询卡下子账户信息
	 */
	public Map checkCardSubAcc(){
		
		Map inter03519 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("03519");
			inter03519 = InterfaceSendMsg.inter03519(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter03519.get("resCode"), (String)inter03519.get("errMsg"));
			if(!"000000".equals(inter03519.get("resCode"))){
				closeDialog(prossDialog);
				logger.info("调用子账户列表查询-【75109】前置03519接口失败："+inter03519.get("errMsg"));
				openMistakeDialog((String) inter03519.get("errMsg")+"，请重新输入");
				return inter03519;
			}
		
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用子账户列表查询-【75109】前置03519接口异常："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用03519接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("查询卡下子账户信息失败，请联系大堂经理", "", "调用03519接口失败");
			inter03519.put("resCode","4444");
			return inter03519;
		}
		return inter03519;	
		
	}
	
	/**
	 * 查询电子子账户信息
	 */
	public Map checkECardSubAcc(){
		
		Map inter07819 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("07819");
			inter07819 = InterfaceSendMsg.inter07819(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter07819.get("resCode"), (String)inter07819.get("errMsg"));
			if(!"000000".equals(inter07819.get("resCode"))){
				closeDialog(prossDialog);
				logger.info("调用电子账户子账户列表查询【35109】（直连电子账户）-前置07819接口失败："+inter07819.get("errMsg"));
				openMistakeDialog((String) inter07819.get("errMsg")+"，请重新输入");
				return inter07819;
			}
		
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用电子账户子账户列表查询【35109】（直连电子账户）-前置07819接口异常："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用07819接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("查询电子子账户信息失败，请联系大堂经理", "", "调用07819接口失败");
			inter07819.put("resCode","4444");
			return inter07819;
		}
		return inter07819;
	}
	
	/**
	 * 账户信息查询
	 */
	public Map checkAcc(){
		
		Map inter07601 = null;
		try {
			lostPubBean.setIsPinPass("0");
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("07601");
			inter07601 = InterfaceSendMsg.inter07601(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter07601.get("resCode"), (String)inter07601.get("errMsg"));
			if(!"000000".equals(inter07601.get("resCode"))){
				closeDialog(prossDialog);
				logger.info("调用账号信息综合查询【02880】-前置07601接口失败："+inter07601.get("errMsg"));
				openMistakeDialog((String) inter07601.get("errMsg")+"，请重新输入");
				return inter07601;
			}
		
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用账号信息综合查询【02880】-前置07601接口异常："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用07601接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("查询账户信息失败，请联系大堂经理", "", "调用07601接口失败");
			inter07601.put("resCode","4444");
			return inter07601;
		}
		return inter07601;
		
	}
	
	/**
	 * 挂失销户限额查询（75209）-前置【08178】
	 */
	public boolean checkcardLimitAmt(){
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08178");
			Map inter08178 = InterfaceSendMsg.inter08178(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter08178.get("resCode"), (String)inter08178.get("errMsg"));
			if(!"000000".equals(inter08178.get("resCode"))){
				closeDialog(prossDialog);
				logger.info("挂失销户限额查询（75209）-前置【08178】调用失败："+inter08178.get("errMsg"));
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop("限额查询失败，请联系大堂经理。", (String)inter08178.get("errMsg"), "");
				return false;
			}
			
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用挂失销户限额查询（75209）-前置【08178】失败："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用08178接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("限额查询失败，请联系大堂经理。", "", "调用挂失销户限额查询（75209）-前置【08178】接口异常");
			return false;
		}
		return true;
	}
	
	/**
	 * 挂失销户限额查询（20097）-前置【08177】
	 */
	public boolean checkAccLimitAmt(){
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08177");
			Map inter08177 = InterfaceSendMsg.inter08177(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter08177.get("resCode"), (String)inter08177.get("errMsg"));
			if(!"000000".equals(inter08177.get("resCode"))){
				closeDialog(prossDialog);
				logger.info("挂失销户限额查询（20097）-前置【08177】调用失败："+inter08177.get("errMsg"));
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop("限额查询失败，请联系大堂经理。", (String)inter08177.get("errMsg"), "");
				return false;
			}
			
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用挂失销户限额查询（20097）-前置【08177】失败："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用08177接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("限额查询失败，请联系大堂经理。", "", "调用挂失销户限额查询（20097）-前置【08177】接口异常");
			return false;
		}
		return true;
	}
	/**
	 * 按账号查全面信息【02884】-前置08223
	 */
	public Map checkAcc08223(){
		Map inter08223=null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08223");
			inter08223 = InterfaceSendMsg.inter08223(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter08223.get("resCode"), (String)inter08223.get("errMsg"));
			if(!"000000".equals(inter08223.get("resCode"))){
				closeDialog(prossDialog);
				logger.info("按账号查全面信息【02884】-前置08223调用失败："+inter08223.get("errMsg"));
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop("按账号查全面信息查询失败，请联系大堂经理。", (String)inter08223.get("errMsg"), "");
				return inter08223;
			}
			
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("按账号查全面信息【02884】-前置08223调用失败："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用08223接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("按账号查全面信息查询失败，请联系大堂经理。", "", "调用按账号查全面信息【02884】-前置08223接口异常");
			inter08223.put("resCode","4444");
			return inter08223;
		}
		return inter08223;
	}
	/**
	 * 
	 * 黑名单查询【20115】-前置【08236】
	 */
	public boolean checkHMD(){
		// 黑名单查询
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08236");
			Map map08236 = InterfaceSendMsg.inter08236(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)map08236.get("resCode"), (String)map08236.get("errMsg"));
			if ("000000".equals(map08236.get("resCode"))) {
				String IDstata = (String) map08236.get("IDstata");
				logger.info("此账户状态：" + IDstata + "(2.买卖账户 3.失信账户)");
				if ("2".equals(IDstata) || "3".equals(IDstata)) {
					String type = null;
					if ("2".equals(IDstata)) {
						type = "买卖账户";
					} else if ("3".equals(IDstata)) {
						type = "失信账户";
					}		
					closeDialog(prossDialog);
					openMistakeDialog("此账户为"+type+"!");
					on_off=true;
					return false;
				}

			} else {
				logger.error((String) map08236.get("errMsg"));
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop("黑名单查询失败",(String) map08236.get("errMsg"), "");
				return false;
			}
		} catch (Exception e) {
			logger.error("调用08236黑名单查询接口失败：" + e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用08236接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("黑名单查询失败，请联系大堂经理","", "调用黑名单查询接口08236失败");
			return false;
		}

		return true;
		
	}
	/**
	 * 确认
	 */
	public void ok() {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				// 校验文本框内容
				if(!checkTextfiled()){
					return;
				}
				logger.info("输入的账/卡号"+textField1.getText());
				logger.info("子账号"+textField2.getText());
				logger.info("选择的开户日"+textField3.getText());
				logger.info("输入的开户金额"+textField4.getText());
				
				openProssDialog();//打开等待框

				if(!checkCust()){//查询客户号
					return;
				}
				
				ShowAccNoMsgBean acc = new ShowAccNoMsgBean();
				lostPubBean.setAllPubPassAccNo(textField1.getText());
				
				if(textField1.getText().startsWith("62326558")){
					
					closeDialog(prossDialog);
					openMistakeDialog("单位卡不支持挂失，请重新输入");
					return;
					
				}else if(textField1.getText().startsWith("623193") || textField1.getText().startsWith("622167")){
					
					if("".equals(textField2.getText()) || null == textField2.getText()){//查询银行卡账户
						
						if(textField1.getText().startsWith("6231930008")){
							
							closeDialog(prossDialog);
							openMistakeDialog("电子账号不支持挂失，请重新输入");
							return;
						}
						
						lostPubBean.setCardNo(textField1.getText());
						lostPubBean.setAllPubAccNo(textField1.getText());
						logger.info("查询输入的银行卡信息");
						
						//查询卡账户状态
						logger.info("进入卡账户信息查询");
						if(!checkCardAcct()){
							return;
						}
						
						//判断卡账户状态
						logger.info("卡账户状态（03521接口返回的）："+lostPubBean.getAccState());
						if("Q".equals(lostPubBean.getAccState())){//销户
							closeDialog(prossDialog);
							logger.info("卡账户状态:销户");
							openMistakeDialog("该银行卡已销户，无法进行挂失处理，请重新输入");
							return;
						}
						
						//查询卡信息
						logger.info("查询卡信息");
						if(!checkCardMsg()){
							return;
						}
						
						Map card1 = checkcard1(lostPubBean);
						if(!"000000".equals((String)card1.get("resCode"))){
							return;
						}
						Map card2 = checkcard2(lostPubBean);
						if(!"000000".equals((String)card2.get("resCode"))){
							return;
						}
						if(card1.get("cardState1")!=null){
							closeDialog(prossDialog);
							openMistakeDialog("该银行卡"+(String)card1.get("cardState1"));
							return;
						}
						if(card2.get("cardState2")!=null){
							closeDialog(prossDialog);
							openMistakeDialog("该银行卡"+(String)card2.get("cardState2"));
							return;
						}
						
						//判断卡是否正常
						logger.info("卡状态（03845接口返回的）："+lostPubBean.getCardState());
						if(!"0".equals(lostPubBean.getCardState())){
							String accState = "";
							if("N".equals(lostPubBean.getCardState())){
								if("0".equals(lostPubBean.getYseNoPass())){
									accState = "该银行卡未激活，请选择“代理人+忘记密码/无密码”申请挂失!";
								}
							}else if("Q".equals(lostPubBean.getCardState())){
								accState = "该银行卡已销卡，无法进行挂失处理，请重新输入";
							}else if("1".equals(lostPubBean.getCardState())){
								accState = "该银行卡未制卡，无法进行挂失处理，请重新输入";
							}else if("2".equals(lostPubBean.getCardState())){
								accState = "该银行卡已制卡,未发卡，无法进行挂失处理，请重新输入";
							}else if("3".equals(lostPubBean.getCardState())){
								accState = "该银行卡未领卡，无法进行挂失处理，请重新输入";
							}else if("4".equals(lostPubBean.getCardState())){
								accState = "该银行卡已没收，无法进行挂失处理，请重新输入";
							}else if("*".equals(lostPubBean.getCardState())){
								accState = "该银行卡已作废，无法进行挂失处理，请重新输入";
							}else{
								accState = "该银行卡状态异常，无法进行挂失处理，请重新输入";
							}
							if(!"".equals(accState)){
								closeDialog(prossDialog);
								openMistakeDialog(accState);
								return;
							}
						}
						
						//判断卡账户状态
						logger.info("卡账户状态（03845接口返回的）："+lostPubBean.getAccState());
						if("2".equals(String.valueOf(lostPubBean.getAccState().charAt(4)))){
							closeDialog(prossDialog);
							openMistakeDialog("您输入的卡已正式挂失，无法进行挂失处理，请重新输入");
							return;
						}else if("1".equals(String.valueOf(lostPubBean.getAccState().charAt(11))) || "2".equals(String.valueOf(lostPubBean.getAccState().charAt(11)))){
							closeDialog(prossDialog);
							openMistakeDialog("您输入的卡已密码挂失，无法进行挂失处理，请重新输入");
							return;
						}
						
						//判断卡查询出的客户证件类型、证件号码、客户名称是否与提供的证件信息一致
						logger.info("银行卡返回的客户姓名："+lostPubBean.getAccIdName());
						logger.info("银行卡返回的证件号码："+lostPubBean.getAccIdNo());
						if(!lostPubBean.getAllPubIDNo().equals(lostPubBean.getAccIdNo()) || !lostPubBean.getAllPubIdCardName().equals(lostPubBean.getAccIdName())){
							closeDialog(prossDialog);
							openMistakeDialog("您输入的卡号与存款人证件信息不一致，请检查与放入证件是否一致");
							return;
						}
						
						//判断开户日期
						logger.info("银行卡返回的开户日期："+lostPubBean.getOpenDate());
						String openDate = textField3.getText().replace("-", "");
						String accOpenDate = "";
						if(lostPubBean.getOpenDate().contains("/")){
							accOpenDate = lostPubBean.getOpenDate().trim().replaceAll("/", "");
							lostPubBean.setOpenDate(accOpenDate);
						}else{
							accOpenDate = lostPubBean.getOpenDate().trim();
						}
						if(!openDate.equals(accOpenDate)){
							closeDialog(prossDialog);
							openMistakeDialog("您输入的开户日期与账户的开户日期不一致，无法进行挂失处理，请重新输入");
							return;
						}
						
						//判断结存额
						logger.info("银行卡返回的结存额："+lostPubBean.getCardEndAmt());
						if(Double.parseDouble(textField4.getText()) != Double.parseDouble(lostPubBean.getCardEndAmt())){
							closeDialog(prossDialog);
							openMistakeDialog("您输入的金额与账户金额不一致，无法进行挂失处理，请重新输入");
							return;
						}
						
						//查询卡限额
						if(!checkcardLimitAmt()){
							return;
						}
						logger.info("查询出的卡限额："+lostPubBean.getLimitAmt()+"元");
						logger.info("输入的卡结存额："+textField4.getText()+"元");
						//查询出的卡限额
						Float limitAmt = Float.valueOf(lostPubBean.getLimitAmt().trim());
						//输入的卡结存额
						Float cardAmt = Float.valueOf(textField4.getText());
						if(cardAmt>limitAmt){
							closeDialog(prossDialog);
							openMistakeDialog("该银行卡已超出机具挂失限额，请于柜面办理.");
							return;
						}
						
						lostPubBean.setLostType("0");//银行卡挂失
						acc.setCardOrAccOrAcc1("0");//银行卡标识
						acc.setCardNo(lostPubBean.getCardNo());//银行卡号
						acc.setOpenDate(lostPubBean.getOpenDate());
						acc.setDepTerm(lostPubBean.getDepTerm());
						acc.setAccType(lostPubBean.getAccType());
						acc.setCertNo(lostPubBean.getCertNo());
						acc.setCustName(lostPubBean.getCustName());
						acc.setEndAmt(lostPubBean.getEndAmt());
						acc.setCardEndAmt(lostPubBean.getCardEndAmt());
						acc.setProName(lostPubBean.getProName());
						acc.setProCode(lostPubBean.getProCode());
						acc.setCardState(lostPubBean.getCardState());
						acc.setAccState(lostPubBean.getAccState());
						acc.setDrawCond(lostPubBean.getDrawCond());
						acc.setPrintState("");
						
					}else{
						
						lostPubBean.setCardNo(textField1.getText());
						lostPubBean.setAllPubAccNo(textField1.getText()+"-"+textField2.getText());
						
						if(textField1.getText().length()<10){
							
							closeDialog(prossDialog);
							openMistakeDialog("您输入的账号异常，请重新输入");
							return;
							
						}else{
							
							if(!"0008".equals(textField1.getText().substring(6,10))){//查询卡下子账号存单
								
								//子帐号列表查询
								Map checkCardSubAcc = checkCardSubAcc();
								if("000000".equals(checkCardSubAcc.get("resCode"))){
									
									List<SubAccInfoBean> list = (List<SubAccInfoBean>)checkCardSubAcc.get("cardSubAcc");
									
									if(list != null && list.size()>0){
										
										int sum = 0;
										
										for (SubAccInfoBean infoBean : list) {
											
											if(textField2.getText().equals(infoBean.getSubAccNo())){
												
												//判断账户是否允许挂失
												logger.info("查询对应输入的卡下子账号："+textField1.getText()+""+textField2.getText());
												logger.info("有效标志："+infoBean.getMark());
												logger.info("产品代码："+infoBean.getProductCode());
												logger.info("打印状态:"+infoBean.getPrintState());
												if("*".equals(infoBean.getMark())){
													closeDialog(prossDialog);
													openMistakeDialog("您输入的账户已作废，无法进行挂失处理，请重新输入");
													return;
												}else if("Q".equals(infoBean.getMark())){
													closeDialog(prossDialog);
													openMistakeDialog("您输入的账户已销户，无法进行挂失处理，请重新输入");
													return;
												}
												if(infoBean.getProductCode().startsWith("JX")){
													closeDialog(prossDialog);
													openMistakeDialog("您输入的账户为积享存账户，无法进行挂失处理，请重新输入");
													return;
												}else if(infoBean.getProductCode().startsWith("TB")){
													closeDialog(prossDialog);
													openMistakeDialog("您输入的账户为唐行宝账户，无法进行挂失处理，请重新输入");
													return;
												}
												if(!"2".equals(infoBean.getPrintState())){
													closeDialog(prossDialog);
													openMistakeDialog("您输入的账户未打印，无法进行挂失处理，请重新输入");
													return;
												}else if("1".equals(lostPubBean.getYseNoPass())){
													closeDialog(prossDialog);
													openMistakeDialog("银行卡子账号不支持密码+存单挂失，请先使用银行卡/随身银行重置密码，再进行存单挂失");
													return;
												}
												lostPubBean.setLostType("1");//存单挂失
												acc.setCardOrAccOrAcc1("1_1");//存单卡下子账户标识
												acc.setAccNo(textField1.getText()+"-"+textField2.getText());//卡下子账号
												acc.setPrintState(infoBean.getPrintState());
												acc.setAccType("卡下子账户");
												lostPubBean.setAccType("卡下子账户");
												sum++;
												break;
											}
										}
										if(sum==0){
											closeDialog(prossDialog);
											openMistakeDialog("未找到该账号，请重新输入");
											return;
										}
									}else{
										closeDialog(prossDialog);
										openMistakeDialog("未找到该账号，请重新输入");
										return;
									}
								}else{
									return;
								}
								
							}else{
								
								//电子子帐号列表查询
								Map checkECardSubAcc = checkECardSubAcc();
								if("000000".equals(checkECardSubAcc.get("resCode"))){
									
									List<EAccInfoBean> list = (List<EAccInfoBean>)checkECardSubAcc.get("eSubAcc");
									
									if(list != null && list.size()>0){
										
										int sum = 0;
										
										for (EAccInfoBean infoBean : list) {
											
											if(textField1.getText().equals(infoBean.geteCardNo().trim()) && textField2.getText().equals(infoBean.geteSubAccNo().trim())){
												
												//判断账户是否允许挂失
												logger.info("查询对应输入的电子子账号："+textField1.getText()+""+textField2.getText());
												logger.info("有效标志："+infoBean.geteMark());
												logger.info("产品代码："+infoBean.geteProductCode());
												logger.info("打印状态:"+infoBean.getePrintState());
												if("*".equals(infoBean.geteMark())){
													closeDialog(prossDialog);
													openMistakeDialog("您输入的账户已作废，无法进行挂失处理，请重新输入");
													return;
												}else if("Q".equals(infoBean.geteMark())){
													closeDialog(prossDialog);
													openMistakeDialog("您输入的账户已销户，无法进行挂失处理，请重新输入");
													return;
												}
												if(infoBean.geteProductCode().startsWith("JX")){
													closeDialog(prossDialog);
													openMistakeDialog("您输入的账户为积享存账户，无法进行挂失处理，请重新输入");
													return;
												}
												if(!"2".equals(infoBean.getePrintState())){
													closeDialog(prossDialog);
													openMistakeDialog("您输入的账户未打印，无法进行挂失处理，请重新输入");
													return;
												}else if("1".equals(lostPubBean.getYseNoPass())){
													closeDialog(prossDialog);
													openMistakeDialog("电子账户子账号不支持密码+存单挂失，请先使用银行卡/随身银行重置密码，再进行存单挂失");
													return;
												}
												lostPubBean.setLostType("1");//存单挂失
												acc.setCardOrAccOrAcc1("1_2");//存单电子子账户标识
												acc.setAccNo(textField1.getText()+"-"+textField2.getText());//电子子账号
												acc.setPrintState(infoBean.getePrintState());
												acc.setAccType("电子子账户");
												lostPubBean.setAccType("电子子账户");
												sum++;
												break;
											}
										}
										if(sum==0){
											closeDialog(prossDialog);
											openMistakeDialog("未找到该账号，请重新输入");
											return;
										}
									}else{
										closeDialog(prossDialog);
										openMistakeDialog("未找到该账号，请重新输入");
										return;
									}
								}else {
									return;
								}
							}
						}
						
						//查询账户状态
						Map checkAcc = checkAcc();
						if("000000".equals(checkAcc.get("resCode"))){
							//判断账户状态
							logger.info("卡下子账户/电子子账户状态（07601接口返回的）："+lostPubBean.getAccState());
							if("*".equals(String.valueOf(lostPubBean.getAccState().charAt(0)))){
								closeDialog(prossDialog);
								openMistakeDialog("您输入的账户已作废，无法进行挂失处理，请重新输入");
								return;
							}else if("1".equals(String.valueOf(lostPubBean.getAccState().charAt(0)))){
								closeDialog(prossDialog);
								openMistakeDialog("您输入的账户已停用，无法进行挂失处理，请重新输入");
								return;
							}else if("3".equals(String.valueOf(lostPubBean.getAccState().charAt(0)))){
								closeDialog(prossDialog);
								openMistakeDialog("您输入的账户渠道限制，无法进行挂失处理，请重新输入");
								return;
							}else if("Q".equals(String.valueOf(lostPubBean.getAccState().charAt(0)))){
								closeDialog(prossDialog);
								openMistakeDialog("您输入的账户已销户，无法进行挂失处理，请重新输入");
								return;
							}else if("2".equals(String.valueOf(lostPubBean.getAccState().charAt(7)))){
								closeDialog(prossDialog);
								openMistakeDialog("您输入的账户已挂失，无法进行挂失处理，请重新输入");
								return;
							}
						}else{
							return;
						}
						
						//判断账户查询出的客户证件类型、证件号码、客户名称是否与提供的证件信息一致
						logger.info("账户返回的客户姓名："+lostPubBean.getAccIdName());
						logger.info("账户返回的证件号码："+lostPubBean.getAccIdNo());
						if(!lostPubBean.getAllPubIDNo().equals(lostPubBean.getAccIdNo()) || !lostPubBean.getAllPubIdCardName().equals(lostPubBean.getAccIdName())){
							closeDialog(prossDialog);
							openMistakeDialog("您输入的卡号与存款人证件信息不一致，无法进行挂失处理，请重新输入");
							return;
						}
						
						//判断开户日期
						logger.info("账户返回的开户日期："+lostPubBean.getOpenDate());
						String openDate = textField3.getText().replace("-", "");
						String accOpenDate = "";
						if(lostPubBean.getOpenDate().contains("/")){
							accOpenDate = lostPubBean.getOpenDate().trim().replaceAll("/", "");
							lostPubBean.setOpenDate(accOpenDate);
						}else{
							accOpenDate = lostPubBean.getOpenDate().trim();
						}
						if(!openDate.equals(accOpenDate)){
							closeDialog(prossDialog);
							openMistakeDialog("您输入的开户日期与账户的开户日期不一致，无法进行挂失处理，请重新输入");
							return;
						}
						
						//判断存折余额
						logger.info("存单账户返回的存折余额："+lostPubBean.getTotalAmt());
						if(Double.parseDouble(textField4.getText()) != Double.parseDouble(lostPubBean.getTotalAmt()) && Double.parseDouble(textField4.getText()) != Double.parseDouble(lostPubBean.getEndAmt())){
							closeDialog(prossDialog);
							openMistakeDialog("您输入的金额与账户金额不一致，无法进行挂失处理，请重新输入");
							return;
						}
						
						//查询存单/存折限额
						if(!checkAccLimitAmt()){
							return;
						}
						logger.info("查询的存单账户限额："+lostPubBean.getLimitAmt()+"元");
						logger.info("存单存折余额："+lostPubBean.getTotalAmt()+"元");
						//查询出的账户限额
						Float limitAmt = Float.valueOf(lostPubBean.getLimitAmt().trim());
						//存折余额
						Float accAmt = Float.valueOf(lostPubBean.getTotalAmt());
						if(accAmt>limitAmt){
							closeDialog(prossDialog);
							openMistakeDialog("该账户已超出机具挂失限额，请于柜面办理.");
							return;
						}
						
						acc.setOpenDate(lostPubBean.getOpenDate());
						acc.setDepTerm(lostPubBean.getDepTerm());
						acc.setCertNo(lostPubBean.getCertNo());
						acc.setCustName(lostPubBean.getCustName());
						acc.setEndAmt(lostPubBean.getEndAmt());
						acc.setProName(lostPubBean.getProName());
						acc.setProCode(lostPubBean.getProCode());
						acc.setAccState(lostPubBean.getAccState());
						acc.setDrawCond(lostPubBean.getDrawCond());
						acc.setTotalAmt(lostPubBean.getTotalAmt());
					}
					
				}else{
					
					//查询存单个人和存折账户
					if("".equals(textField2.getText()) || null == textField2.getText()){
						
						lostPubBean.setAllPubAccNo(textField1.getText());
					}else{
						
						lostPubBean.setAllPubAccNo(textField1.getText()+"-"+textField2.getText());
					}
					
					//查询一本通外部账号
					Map inter08176 = null;
					try {
						inter08176 = InterfaceSendMsg.inter08176(lostPubBean);
						if(!"000000".equals(inter08176.get("resCode"))){
							
							logger.info("调用一本通账号查询【20098】-前置【08176】接口失败："+inter08176.get("errMsg"));
						}
					
					} catch (Exception e) {
						
						logger.error("调用一本通账号查询【20098】-前置【08176】接口异常："+e);
						
					}
					String accNo = (String)inter08176.get("SVR_JRNL_NO_R");
					String maxState = (String)inter08176.get("MX_STAT");
					if(accNo!=null && !"".equals(accNo)){
						if(!"2".equals(maxState)){
							lostPubBean.setAllPubAccNo(accNo);
						}
					}
					
					//查询账户状态
					logger.info("查询存单/存折账户信息");
					Map checkAcc = checkAcc();
					if("000000".equals(checkAcc.get("resCode"))){
						
						logger.info("账户类型（7601接口返回的）："+lostPubBean.getAccKind());
						logger.info("产品代码（7601接口返回的）："+lostPubBean.getProCode());
						String accstate=null;
	                    //查询账户全面信息
						Map checkacc08223=checkAcc08223();
						if("000000".equals(checkacc08223.get("resCode"))){
							List<AccNoCheck08223> list08223=(List<AccNoCheck08223>) checkacc08223.get("Acc08223");
							if(list08223 !=null && list08223.size()>0){
								for(AccNoCheck08223 acc08223:list08223){
									accstate=acc08223.getAcc_stata();
									//替换挂失状态值					
									char[] ch = lostPubBean.getAccState().toCharArray();
									ch[7] = accstate.charAt(7);
									lostPubBean.setAccState(String.valueOf(ch));
								}
							}
						}
						//判断账户类型
						if("8888".equals(lostPubBean.getAllPubAccNo().substring(9, 13)) || "9999".equals(lostPubBean.getAllPubAccNo().substring(9, 13))){
							closeDialog(prossDialog);
							openMistakeDialog("您输入的账户是一本通账户，无法进行挂失处理，请重新输入");
							return;
						}	
						
						if("1".equals(lostPubBean.getAccKind())){
							if(!"0000".equals(lostPubBean.getAllPubAccNo().substring(9, 13)) && !"0001".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) &&
									!"0011".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) && !"0012".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) &&
									!"0013".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) && !"0014".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) &&
									!"0015".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) && !"0018".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13))){
								//个人存单
								logger.info("查询个人存单账户信息");
								acc.setCardOrAccOrAcc1("1");
								lostPubBean.setLostType("1");//存单挂失
								acc.setAccType("个人存单");
								lostPubBean.setAccType("个人存单");
								if("".equals(textField2.getText()) || null == textField2.getText()){
									
									acc.setAccNo(textField1.getText());
								}else{
									
									acc.setAccNo(textField1.getText()+"-"+textField2.getText());//存单账号
								}
							}else if("0000".equals(lostPubBean.getAllPubAccNo().substring(9, 13)) || "0001".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) ||
										"0014".equals(lostPubBean.getAllPubAccNo().substring(9, 13)) || "0015".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13))){
								//存折
								logger.info("查询存折账户信息");
								acc.setCardOrAccOrAcc1("2");
								lostPubBean.setLostType("2");//存折挂失
								acc.setAccType("存折");
								lostPubBean.setAccType("存折");
								if("".equals(textField2.getText()) || null == textField2.getText()){
									
									acc.setAccNo1(textField1.getText());
								}else{
									
									acc.setAccNo1(textField1.getText()+"-"+textField2.getText());//存折账号
								}
							}else{
								closeDialog(prossDialog);
								openMistakeDialog("您输入的账户类型不支持挂失处理，请重新输入");
								return;
							}
						}else if("2".equals(lostPubBean.getAccKind())){
							closeDialog(prossDialog);
							openMistakeDialog("您输入的账户是一本通账户，无法进行挂失处理，请重新输入");
							return;
						}else{
							closeDialog(prossDialog);
							openMistakeDialog("您输入的账户类型不支持挂失处理，请重新输入");
							return;
						}
						
						if(lostPubBean.getDrawCond() != null){
							
							if(!"1".equals(lostPubBean.getDrawCond()) && "0".equals(lostPubBean.getYseNoPass())){
								closeDialog(prossDialog);
								String  type=null;
								if("0".equals(lostPubBean.getDrawCond())){
									type="无密码";
								}else if("2".equals(lostPubBean.getDrawCond())){
									type="凭证件";
								}else if("3".equals(lostPubBean.getDrawCond())){
									type="凭印鉴";
								}
								openMistakeDialog("该账户"+type+"，请选择“忘记密码/无密码”申请挂失!");
								return;
							}
						}
						
						//判断账户状态
						if("*".equals(String.valueOf(lostPubBean.getAccState().charAt(0)))){
							closeDialog(prossDialog);
							openMistakeDialog("您输入的账户已作废，无法进行挂失处理，请重新输入");
							return;
						}else if("1".equals(String.valueOf(lostPubBean.getAccState().charAt(0)))){
							closeDialog(prossDialog);
							openMistakeDialog("您输入的账户已停用，无法进行挂失处理，请重新输入");
							return;
						}else if("3".equals(String.valueOf(lostPubBean.getAccState().charAt(0)))){
							closeDialog(prossDialog);
							openMistakeDialog("您输入的账户渠道限制，无法进行挂失处理，请重新输入");
							return;
						}else if("Q".equals(String.valueOf(lostPubBean.getAccState().charAt(0)))){
							closeDialog(prossDialog);
							openMistakeDialog("您输入的账户已销户，无法进行挂失处理，请重新输入");
							return;
						}else if("2".equals(String.valueOf(lostPubBean.getAccState().charAt(7)))){
							closeDialog(prossDialog);
							openMistakeDialog("您输入的账户已挂失，无法进行挂失处理，请重新输入");
							return;
						}else if("1".equals(String.valueOf(lostPubBean.getAccState().charAt(15))) || "2".equals(String.valueOf(lostPubBean.getAccState().charAt(15)))){
							closeDialog(prossDialog);
							openMistakeDialog("您输入的账户密码已挂失，无法进行挂失处理，请重新输入");
							return;
						}
					}else{
						return;
					}
					
					if(lostPubBean.getProCode().startsWith("JX")){
						closeDialog(prossDialog);
						openMistakeDialog("您输入的账户为积享存账户，无法进行挂失处理，请重新输入");
						return;
					}
					
					//判断账户查询出的客户证件类型、证件号码、客户名称是否与提供的证件信息一致
					logger.info("账户返回的客户姓名："+lostPubBean.getAccIdName());
					logger.info("账户返回的证件号码："+lostPubBean.getAccIdNo());
					if(!lostPubBean.getAllPubIDNo().equals(lostPubBean.getAccIdNo()) || !lostPubBean.getAllPubIdCardName().equals(lostPubBean.getAccIdName())){
						closeDialog(prossDialog);
						openMistakeDialog("您输入的卡号与存款人证件信息不一致，无法进行挂失处理，请重新输入");
						return;
					}
					
					//判断开户日期
					logger.info("账户返回的开户日期："+lostPubBean.getOpenDate());
					String openDate = textField3.getText().replace("-", "");
					String accOpenDate = "";
					if(lostPubBean.getOpenDate().contains("/")){
						accOpenDate = lostPubBean.getOpenDate().trim().replaceAll("/", "");
						lostPubBean.setOpenDate(accOpenDate);
					}else{
						accOpenDate = lostPubBean.getOpenDate().trim();
					}
					if(!openDate.equals(accOpenDate)){
						closeDialog(prossDialog);
						openMistakeDialog("您输入的开户日期与账户的开户日期不一致，无法进行挂失处理，请重新输入");
						return;
					}
					
					//判断存单存折余额
					if(!"0000".equals(lostPubBean.getAllPubAccNo().substring(9, 13)) && !"0001".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) &&
							!"0011".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) && !"0012".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) &&
							!"0013".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) && !"0014".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) &&
							!"0015".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) && !"0018".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13))){//存单
						logger.info("存单账户返回的存折余额："+lostPubBean.getTotalAmt());
						if(Double.parseDouble(textField4.getText()) != Double.parseDouble(lostPubBean.getTotalAmt())){
							closeDialog(prossDialog);
							openMistakeDialog("您输入的金额与账户金额不一致，无法进行挂失处理，请重新输入");
							return;
						}
					}else if("0000".equals(lostPubBean.getAllPubAccNo().substring(9, 13)) || "0001".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) ||
								"0014".equals(lostPubBean.getAllPubAccNo().substring(9, 13)) || "0015".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13))){//判断存折结存额
						logger.info("存折账户返回的结存额："+lostPubBean.getEndAmt());
						if(Double.parseDouble(textField4.getText()) != Double.parseDouble(lostPubBean.getEndAmt())){
							closeDialog(prossDialog);
							openMistakeDialog("您输入的金额与账户金额不一致，无法进行挂失处理，请重新输入");
							return;
						}
					}
					
					//查询存单/存折限额
					if(!checkAccLimitAmt()){
						return;
					}
					logger.info("查询的账户限额："+lostPubBean.getLimitAmt()+"元");
					logger.info("输入的存单存折余额/存折结存额："+textField4.getText()+"元");
					//查询出的账户限额
					Float limitAmt = Float.valueOf(lostPubBean.getLimitAmt().trim());
					//输入的存单存折余额/存折结存额
					Float accAmt = Float.valueOf(textField4.getText());
					if(accAmt>limitAmt){
						closeDialog(prossDialog);
						openMistakeDialog("该账户已超出机具挂失限额，请于柜面办理.");
						return;
					}
				
					acc.setOpenDate(lostPubBean.getOpenDate());
					acc.setDepTerm(lostPubBean.getDepTerm());
					acc.setCertNo(lostPubBean.getCertNo());
					acc.setCustName(lostPubBean.getCustName());
					acc.setEndAmt(lostPubBean.getEndAmt());
					acc.setProName(lostPubBean.getProName());
					acc.setProCode(lostPubBean.getProCode());
					acc.setAccState(lostPubBean.getAccState());
					acc.setDrawCond(lostPubBean.getDrawCond());
					acc.setTotalAmt(lostPubBean.getTotalAmt());
					acc.setPrintState("");
				}
				
				//查询黑灰名单
				try {
					//上送流水调用接口前信息
					lostPubBean.getReqMCM001().setReqBefor("01597");
					Map map01597 = InterfaceSendMsg.inter01597(lostPubBean);
					//上送流水调用接口后信息
					lostPubBean.getReqMCM001().setReqAfter((String)map01597.get("resCode"), (String)map01597.get("errMsg"));
					String resCode = (String)map01597.get("resCode");
					if(!"000000".equals(resCode)){
						if("0010".equals(resCode)){
							logger.info("账户为涉案账户："+resCode+(String)map01597.get("errMsg")+"!");
							closeDialog(prossDialog);
							openMistakeDialog("此账户涉案，禁止办理业务");
							return;
						}else if("0020".equals(resCode)){
							logger.info("账户为可疑账户："+resCode+(String)map01597.get("errMsg")+"!");
							closeDialog(prossDialog);
							openMistakeDialog("此账户可疑，禁止办理业务");
							return;
						}else{
							closeDialog(prossDialog);
							//上送流水信息
							MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
							serverStop("黑灰名单查询失败，请联系大堂经理",(String)map01597.get("errMsg"),"");
							return;
						}
					}
				} catch (Exception e) {
					logger.error("调用01597黑灰名单查询接口失败："+e);
					closeDialog(prossDialog);
					//调用接口异常，上送流水信息
					lostPubBean.getReqMCM001().setIntereturnmsg("调用01597接口失败");
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop("黑灰名单查询失败，请联系大堂经理","","调用黑灰名单查询接口失败");
					return;
				}
				
				//查询账户黑名单
				if(!checkHMD()){
					return;
				}
				
				//存放账户信息
				lostPubBean.getAccMap().put("selectMsg", acc);
				
				closeDialog(prossDialog);
				
				//校验账户没问题，跳转下页
				confirm();
			}
		}).start();
	}
	
	/**
	 *  跳转下页
	 */
	public void confirm(){
		
		if(!on_off){
			logger.info("开关当前状态"+on_off);
			return;
		}
		logger.info("开关当前状态"+on_off);
		on_off=false;
		//存折账户增加提示框
		if( "1".equals(lostPubBean.getYseNoPass()) && "2".equals(lostPubBean.getLostType())){
			openConfirmDialog("存折类账户于机具申请双挂，后续仅支持销户处理，是否继续：是：继续。否：退出。");
			confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关闭语音
					closeVoice();//关闭语音流
					//签字确认页
		    		openPanel(new LostConfirmPanel());
				}
			});
			confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					
					on_off=true;
					closeDialog(confirmDialog);
				}
			});
			return;
		}
		
		clearTimeText();//清空倒计时
		stopTimer(voiceTimer);//关闭语音
		closeVoice();//关闭语音流
		
		//如果知道密码，则进入输入密码页面
		if(lostPubBean.getYseNoPass()!=null && "0".equals(lostPubBean.getYseNoPass())){
			lostPubBean.setNextStepName("ALL_PUBLIC_INPUT_PASSWORD_PANEL");
			allPubTransFlow.transFlow();
			return;
		}else{
			
			//进入账户信息签字页面(只挂失)
			openPanel(new LostConfirmPanel());
			return;
		}
	}

	/**
	 * 返回上一步
	 */
	public void back() {

		lostPubBean.setAllPubPassAccNo("");
		lostPubBean.setAllPubAccNo("");
		openPanel(new LostSelectEnterAccOrCustServicePage());
	}

	/**
	 * 退出交易
	 */
	public void exit() {

		returnHome();
	}
}
