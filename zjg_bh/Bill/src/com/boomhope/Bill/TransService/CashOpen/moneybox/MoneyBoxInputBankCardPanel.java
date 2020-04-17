package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Driver.KeypadDriver;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.TransService.CashOpen.Interface.AccountDepositApi;
import com.boomhope.Bill.Util.CardStat;
import com.boomhope.Bill.Util.SoftKeyBoardPopups2;
import com.boomhope.Bill.Util.UtilButton;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.message.account.in.BCK03845ResBean;

/**
 * 输入受益人账号
 * @author gyw
 *
 */
public class MoneyBoxInputBankCardPanel extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(MoneyBoxInputBankCardPanel.class);
	
	JLabel label = null;
	private static final long serialVersionUID = 1L;
	public JTextField textField2;
	private JPanel passwordPanel1;
	private JPanel passwordPanel2;
	private SoftKeyBoardPopups2 keyPopup1;
	private SoftKeyBoardPopups2 keyPopup2;
	JLabel errInfo = null;// 错误提示
	JLabel errInfo1 = null;// 错误提示
	
	private boolean on_off=true;//开关控制
	public MoneyBoxInputBankCardPanel(final PublicCashOpenBean transBean) {
		this.cashBean = transBean;
		
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	on_off=false;
            	/* 倒计时结束退出交易 */ 
            	String res;
        		try {
        			res = closePassword();
        			if(!res.equals("0")){
        				logger.error("密码键盘设备故障!没有正常关闭");
        			}
        		} catch (Exception e1) {
        			logger.error("密码键盘设备故障!没有正常关闭"+e1);
        		}
            	clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
		
		/* 标题信息 */
		JLabel titleLabel = new JLabel("受益人信息");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(titleLabel);
		
		JLabel lblNewLabel_1 = new JLabel("受益人卡号：");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(146, 233, 120, 50);
		this.showJpanel.add(lblNewLabel_1);
		
		// 输入框
		passwordPanel1 = new JPanel(new BorderLayout());
		passwordPanel1.setBackground(Color.WHITE);
		passwordPanel1.setPreferredSize(new Dimension(2024, 30));
		passwordPanel1.setLayout(new BorderLayout());
		passwordPanel1.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel1);

		
		// 输入框
		passwordPanel2 = new JPanel(new BorderLayout());
		passwordPanel2.setBackground(Color.WHITE);
		passwordPanel2.setPreferredSize(new Dimension(2024, 30));
		passwordPanel2.setLayout(new BorderLayout());
		passwordPanel2.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel2);
		//账号	
		textField2 = new JTextField();
		textField2.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField2.setBounds(265, 233, 511, 50);
		textField2.setColumns(10);
		this.showJpanel.add(textField2);
		keyPopup2 = new SoftKeyBoardPopups2(textField2);
		
		//上一步
		JLabel label = new JLabel(new ImageIcon("pic/preStep.png"));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");		
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				backTrans(transBean);
			}

		});
		label.setBounds(1075, 770, 150, 50);
		this.add(label);
		//确认
		JLabel lblNewLabel = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		lblNewLabel.setBounds(890, 770, 150, 50);
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
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");	
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				String res;
				try {
					res = closePassword();
					if(!res.equals("0")){
						logger.error("密码键盘设备故障!没有正常关闭");
					}
				} catch (Exception e1) {
					logger.error("密码键盘设备故障!没有正常关闭"+e1);
				}
				returnHome();
			}

		});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);
	
		errInfo1 = new JLabel();
		errInfo1.setForeground(Color.RED);
		errInfo1.setFont(new Font("微软雅黑", Font.PLAIN, 20));		
		errInfo1.setBounds(409, 317, 400, 23);
		this.showJpanel.add(errInfo1);
		
		JLabel label_11 = new JLabel("提示：");
		label_11.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_11.setBounds(202, 350, 155, 32);
		this.showJpanel.add(label_11);
		
		JLabel label_2 = new JLabel();
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_2.setBounds(259, 305, 489, 236);
		this.showJpanel.add(label_2);
		
		//清空按钮
		UtilButton utilButton = new UtilButton("pic/qk.png", "pic/qk.png");
		utilButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField2.setText("");
				//调密码键盘
				createThread3();
			}

		});
		utilButton.setBounds(800, 233, 100, 50);
		this.showJpanel.add(utilButton);
		if(transBean.getLdcType().equals("0")){
			label_2.setText("<html>1、立得存-自享，收益人卡号默认本卡号。<p>2、页面“清空”按钮，清空全部卡号。<p>3、密码键盘“*/清除”键，删除最后一位卡号。<p>4、该产品需客户指定本人名下的一张唐山银行的银行卡为收益账户。</html>");
		}
		if(transBean.getLdcType().equals("1")){
			label_2.setText("<html>1、该产品需客户指定除本人以外的第三人名下的一张唐山银行的银行卡账户为收益账户。<p>2、页面“清空”按钮，清空全部卡号。<p>3、密码键盘“*/清除”键，删除最后一位卡号。</html>");
		}
		if(transBean.getLdcType().equals("2")){
			label_2.setText("<html>1、安享存，收益人卡号默认本卡号。<p>2、页面“清空”按钮，清空全部卡号。<p>3、密码键盘“*/清除”键，删除最后一位卡号。<p>4、该产品需客户指定本人名下的一张唐山银行的银行卡为收益账户。</html>");
		}
		createThread3();//调用密码键盘
		
	}
	
	
	private void createThread3() {
		String res;
		try {
			res = closePassword();
			if(!res.equals("0")){
				logger.error("密码键盘设备故障!没有正常关闭");
			}
		} catch (Exception e) {
			logger.error("密码键盘设备故障!没有正常关闭");
		}
		new Thread(){
			@Override
			public void run(){
				inputPassword3();
			}
		}.start();
	}
	private void inputPassword3() {
		try {
			try {
				new KeypadDriver().getKnowID(this, "textField2", "4", "25","19");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		} catch (Exception e) {
			logger.error("密码键盘设备故障");
		}
	}
	/**
	 * 返回
	 * */
	public void backTrans(PublicCashOpenBean transBean) {
		String res;
		try {
			res = closePassword();
			if(!res.equals("0")){
				logger.error("密码键盘设备故障!没有正常关闭");
			}
		} catch (Exception e) {
			logger.error("密码键盘设备故障!没有正常关闭");
		}
		clearTimeText();
		//输入柜员号
		openPanel(new MoneyBoxEnteringLDCPanel(transBean));
	
	}
	/**
	 * 下一步
	 */
	public void nextStep(PublicCashOpenBean transBean){
		
				
	String a=textField2.getText();
	
		if(textField2.getText() == null || textField2.getText().equals("")){
			errInfo1.setText("提示：请输入帐号!");
			createThread3();
			on_off=true;
			return;
		}else if(a.length()<19){		
			errInfo1.setText("输入的卡号不正确，请输入19位卡号");
			createThread3();
			on_off=true;
			return;
		}else if(a.length()>=19){
			transBean.setInputNo(textField2.getText());
		}
		else{
			transBean.setInputNo(textField2.getText());
			logger.debug("账号输入值"+textField2.getText());
		}	
		//判断自享还是他享或者安享
		if("0".equals(transBean.getLdcType())){
			//自享，查询客户号，姓名，账号
			try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("CARD_NO", transBean.getInputNo());
			map.put("PASSWD", "");
			map.put("ISPIN", "0");
			
			transBean.getReqMCM001().setReqBefor("03845");//接口调用前流水信息记录
			
			AccountDepositApi accountDepositApi = new AccountDepositApi();
			BCK03845ResBean  searchcard=accountDepositApi.searchCard(map);
			if(null == searchcard||!"000000".equals(searchcard.getHeadBean().getResCode())||"44444".equals(searchcard.getHeadBean().getResCode())){
				logger.error("自享，查询姓名，账号。卡查询失败:" + searchcard.getHeadBean().getResMsg()+"==="+map);
				//接口调用后流水信息记录
				if(null == searchcard){
					transBean.getReqMCM001().setIntereturnmsg("查询卡信息失败：调用03845接口异常");
				}else{
					transBean.getReqMCM001().setReqAfter(searchcard.getHeadBean().getResCode(), searchcard.getHeadBean().getResMsg());
				}
				fail( transBean, "该卡号不存在或不是本行银行卡,请输入本行的银行卡号。");//此处要有错误页面
				return;
			}
			//接口调用成功后记录流水信息
			transBean.getReqMCM001().setReqAfter(searchcard.getHeadBean().getResCode(), searchcard.getHeadBean().getResMsg());
			
			CardStat cardStat=new CardStat(searchcard.getBody().getACCT_STAT(), searchcard.getBody().getCARD_STAT());
			String statStr=cardStat.existsPutIntAcct();
			if(statStr.length()>0){
				transBean.getReqMCM001().setIntereturnmsg("该卡状态异常");
				
				fail(transBean, "该卡号状态不正常，不能为收益卡号。");//此处要有错误页面
				
				return;
			}
			
			if(!(searchcard.getBody().getCUST_NO().trim()).equals(transBean.getCustNo().trim())){
				transBean.getReqMCM001().setIntereturnmsg("受益人与开户人必须为同一人");
				fail(transBean,"收益卡号和开户卡号必须为同一人");//此处要有错误页面
				
				return;
			}
			} catch (Exception e) {
				logger.error(e);
				transBean.getReqMCM001().setIntereturnmsg("查询卡信息失败：调用03845接口异常");
				fail(transBean, "系统异常，请返回重新输入收益人卡号");//此处要有错误页面
				
				return;
			}
		}else if("1".equals(transBean.getLdcType())){
			//他享，查询姓名，账号
			try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("CARD_NO", transBean.getInputNo());
			map.put("PASSWD", "");
			map.put("ISPIN", "0");
			
			transBean.getReqMCM001().setReqBefor("03845");//接口调用前流水信息记录
			
			AccountDepositApi accountDepositApi = new AccountDepositApi();
			BCK03845ResBean  searchcard=accountDepositApi.searchCard(map);
			if(null == searchcard||!"000000".equals(searchcard.getHeadBean().getResCode())||"44444".equals(searchcard.getHeadBean().getResCode())){
				logger.error("他享，查询姓名，账号。卡查询失败:" + searchcard.getHeadBean().getResMsg()+"==="+map);
				
				//接口调用后流水信息记录
				if(null == searchcard){
					transBean.getReqMCM001().setIntereturnmsg("查询卡信息失败：调用03845接口异常");
				}else{
					transBean.getReqMCM001().setReqAfter(searchcard.getHeadBean().getResCode(), searchcard.getHeadBean().getResMsg());
				}
				
				fail( transBean, "该卡号不存在或不是本行银行卡,请输入本行的银行卡号。");//此处要有错误页面
				
				return;
			}						
			CardStat cardStat=new CardStat(searchcard.getBody().getACCT_STAT(), searchcard.getBody().getCARD_STAT());
			String statStr=cardStat.existsPutIntAcct();
			if(statStr.length()>0){
				transBean.getReqMCM001().setIntereturnmsg("该卡状态异常");
				fail(transBean, "该卡号状态不正常，不能为收益卡号。");//此处要有错误页面
				
				return;
			}
			
			if((searchcard.getBody().getCUST_NO().trim()).equals(transBean.getCustNo().trim())){
				transBean.getReqMCM001().setIntereturnmsg("收益人和开户人不能为同一人");
				fail(transBean, "收益卡号和开户卡号不能为同一人");//此处要有错误页面
				
				return;
			}
			transBean.setLdcTXacctName(searchcard.getBody().getACCT_NAME().trim());//存入他享的客户名字
		} catch (Exception e) {
			logger.error(e);
			transBean.getReqMCM001().setIntereturnmsg("查询卡信息失败：调用03845接口异常");
			fail(transBean, "系统异常，请返回重新输入收益人卡号");//此处要有错误页面
			
			return;
		}			
		}else if("2".equals(transBean.getLdcType())){
			//安享，查询客户号，姓名，账号
			try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("CARD_NO", transBean.getInputNo());
			map.put("PASSWD", "");
			map.put("ISPIN", "0");
			
			transBean.getReqMCM001().setReqBefor("03845");//接口调用前流水信息记录
			
			AccountDepositApi accountDepositApi = new AccountDepositApi();
			BCK03845ResBean  searchcard=accountDepositApi.searchCard(map);
			if(null == searchcard||!"000000".equals(searchcard.getHeadBean().getResCode())||"44444".equals(searchcard.getHeadBean().getResCode())){
				logger.error("安享，查询姓名，账号。卡查询失败:" + searchcard.getHeadBean().getResMsg()+"==="+map);
				
				//接口调用后流水信息记录
				if(null == searchcard){
					transBean.getReqMCM001().setIntereturnmsg("查询卡信息失败：调用03845接口异常");
				}else{
					transBean.getReqMCM001().setReqAfter(searchcard.getHeadBean().getResCode(), searchcard.getHeadBean().getResMsg());
				}
				
				fail( transBean, "该卡号不存在或不是本行银行卡,请输入本行的银行卡号。");//此处要有错误页面
				
				return;
			}						
			CardStat cardStat=new CardStat(searchcard.getBody().getACCT_STAT(), searchcard.getBody().getCARD_STAT());
			String statStr=cardStat.existsPutIntAcct();
			if(statStr.length()>0){
				transBean.getReqMCM001().setIntereturnmsg("该卡状态异常");
				
				fail(transBean, "该卡号状态不正常，不能为收益卡号。");//此处要有错误页面
				
				return;
			}
			
			if(!(searchcard.getBody().getCUST_NO().trim()).equals(transBean.getCustNo().trim())){
				transBean.getReqMCM001().setIntereturnmsg("收益人和开户人必须为同一人");
				
				fail(transBean, "收益卡号和开户卡号必须为同一人");//此处要有错误页面
				
				return;
			}
			} catch (Exception e) {
				logger.error(e);
				
				transBean.getReqMCM001().setIntereturnmsg("查询卡信息失败：调用03845接口异常");
				
				fail(transBean, "系统异常，请返回重新输入收益人卡号");//此处要有错误页面
				
				return;
			}
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
		clearTimeText();
		
		//跳转到信息确认页
		openPanel(new MoneyBoxSYRPage(transBean));
			
		
		
	}
	
	
	
	/**
	 * 失败
	 * @param fail
	 */
	public void fail(PublicCashOpenBean transBean,String fail){
		transBean.setErrmsg(fail);
		transBean.getImportMap().put("backStepbankcard", GlobalPanelFlag.MONEYBOX_INPUT_BANK_CARD_PANEL+"");
		openPanel(new MoneyBoxMistakePanel(transBean));
			
		
	}
	

	public JTextField getTextField2() {
		return textField2;
	}


	public void setTextField2(JTextField textField2) {
		this.textField2 = textField2;
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
