package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.Bean.AccPublicBean;
import com.boomhope.Bill.TransService.AccOpen.Bean.ProductRateInfo;
import com.boomhope.Bill.TransService.AccOpen.Bean.ProductRateInfo1;
import com.boomhope.Bill.TransService.AccOpen.Interface.IntefaceSendMsg;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTransFlow;
import com.boomhope.Bill.Util.AccGetKeyBoredPanel;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.SoftKeyBoardPopups4;
import com.boomhope.Bill.Util.StringUtils;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;

/**
 * 整存整取信息录入页面
 * 
 * @author hao
 * 
 */
public class AccDepoLumPanel extends BaseTransPanelNew {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccDepoLumPanel.class);
	private static JTextField textField;
	private SoftKeyBoardPopups4 keyPopup;
	private JPanel moneyPanel;
	private JLabel promptLabel;
	

	final int voiceSecond = 500;
	public static String SUCCESS = "0000";
	JLabel balanceLabel;
	final JLabel rateLabel;
	final JLabel interestLabel;
	private boolean on_off=true;
	
	/**
	 * 初始化
	 */
	public AccDepoLumPanel(final AccPublicBean transBean) {
		// 将当前页面传入流程控制进行操作
		final Component comp = this;
		logger.info("进入整存整取开户页面......"+transBean);
		if(delayTimer!=null){
			clearTimeText();
		}
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	logger.info("整存整取录入信息页面倒计时结束 ");
            	/* 倒计时结束退出交易 */ 
            	stopTimer(delayTimer);
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        }); 
		delayTimer.start(); // 当进入整存整取页面时初始化 存期、存款金额、存期单位、自动转存标识
		transBean.setMoney(null);
		transBean.setMonthsUpper(null);
		transBean.setDepUnit(null);
		transBean.setAutoRedpFlag(null);
		/* 提示信息 */
		promptLabel = new JLabel("");
		promptLabel.setHorizontalAlignment(JLabel.CENTER);
		promptLabel.setForeground(Color.decode("#333333"));
		promptLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		promptLabel.setBounds(600, 519, 400, 40);
		this.showJpanel.add(promptLabel);
		// 键盘面板
		moneyPanel = new JPanel(new BorderLayout());
		moneyPanel.setPreferredSize(new Dimension(2024, 30));
		moneyPanel.setLayout(new BorderLayout());
		moneyPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(moneyPanel);
		// 标题
		JLabel depoLum = new JLabel("整存整取储蓄存款");
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(depoLum);

		// 用户姓名
		JLabel custName = new JLabel("用户名 :");
		custName.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		custName.setBounds(69, 199, 94, 40);
		custName.setForeground(Color.decode("#333333"));
		this.showJpanel.add(custName);
		// 用户名字显示处
		final JLabel nameLabel = new JLabel();
		nameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		nameLabel.setBounds(173, 199, 135, 40);
		nameLabel.setForeground(Color.decode("#333333"));
		this.showJpanel.add(nameLabel);
		nameLabel.setText(transBean.getIdCardName());
		// 银行卡余额
		JLabel lblNewLabel_2 = new JLabel("账户余额 :");
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_2.setBounds(339, 199, 116, 40);
		lblNewLabel_2.setForeground(Color.decode("#333333"));
		this.showJpanel.add(lblNewLabel_2);
		// 卡余额显示
		balanceLabel = new JLabel();
		balanceLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		balanceLabel.setForeground(Color.decode("#333333"));
		balanceLabel.setBounds(465, 199, 253, 40);
		this.showJpanel.add(balanceLabel);
		balanceLabel.setText(transBean.getCardAmt() + "元");
		// 到期利率
		JLabel lblNewLabel_3 = new JLabel("到期利率 :");
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_3.setBounds(69, 322, 123, 40);
		lblNewLabel_3.setForeground(Color.decode("#333333"));
		this.showJpanel.add(lblNewLabel_3);
		// 利率显示
		rateLabel = new JLabel();
		rateLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		rateLabel.setBounds(194, 322, 135, 40);
		rateLabel.setForeground(Color.decode("#333333"));
		this.showJpanel.add(rateLabel);
		// 预计利息
		JLabel lblNewLabel_4 = new JLabel("预计利息 :");
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_4.setBounds(339, 322, 109, 40);
		lblNewLabel_4.setForeground(Color.decode("#333333"));
		this.showJpanel.add(lblNewLabel_4);
		// 利息显示
		interestLabel = new JLabel();
		interestLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		interestLabel.setBounds(465, 322, 300, 40);
		interestLabel.setForeground(Color.decode("#333333"));
		this.showJpanel.add(interestLabel);
		// 存款金额
		// 一万
		final JLabel btnOneWan = new JLabel("一万",0);
		btnOneWan.setForeground(Color.BLACK);
		btnOneWan.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
		btnOneWan.setForeground(Color.decode("#333333"));
		btnOneWan.setHorizontalTextPosition(SwingConstants.CENTER);
		btnOneWan.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnOneWan.setBounds(194, 451, 90, 40);
		btnOneWan.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		this.showJpanel.add(btnOneWan);
		// 2万
		final JLabel btnTwoWan = new JLabel("二万",0);
		btnTwoWan.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
		btnTwoWan.setForeground(Color.decode("#333333"));
		btnTwoWan.setHorizontalTextPosition(SwingConstants.CENTER);
		btnTwoWan.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnTwoWan.setBounds(320, 451, 90, 40);
		btnTwoWan.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		this.showJpanel.add(btnTwoWan);
		// 3万
		final JLabel btnThreeWan = new JLabel("三万",0);
		btnThreeWan.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
		btnThreeWan.setForeground(Color.decode("#333333"));
		btnThreeWan.setHorizontalTextPosition(SwingConstants.CENTER);
		btnThreeWan.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnThreeWan.setBounds(442, 451, 90, 40);
		btnThreeWan.setOpaque(false);// 设置控件是否透明，true不透明，false为透明
		this.showJpanel.add(btnThreeWan);
		// 4万
		final JLabel btnFourWan = new JLabel("四万",0);
		btnFourWan.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
		btnFourWan.setHorizontalTextPosition(SwingConstants.CENTER);
		btnFourWan.setForeground(Color.decode("#333333"));
		btnFourWan.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnFourWan.setBounds(557, 451, 90, 40);
		btnFourWan.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		this.showJpanel.add(btnFourWan);
		// 5万
		final JLabel btnFiveWan = new JLabel("五万",0);
		btnFiveWan.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
		btnFiveWan.setForeground(Color.BLACK);
		btnFiveWan.setForeground(Color.decode("#333333"));
		btnFiveWan.setHorizontalTextPosition(SwingConstants.CENTER);
		btnFiveWan.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnFiveWan.setBounds(675, 451, 90, 40);
		btnFiveWan.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		this.showJpanel.add(btnFiveWan);

		// 手动输入金额显示框
		textField = new JTextField();
		textField.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		textField.setBounds(787, 451, 150, 40);
		textField.setForeground(Color.decode("#333333"));
		textField.setColumns(10);
		textField.setText("输入金额");
		textField.setFocusable(false);
		textField.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				promptLabel.setText("");
				scanBill12();
				if ("输入金额".equals(textField.getText())) {
					textField.setText("");
				}
				btnFiveWan.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
				btnFiveWan.setBorder(BorderFactory.createLineBorder(Color.decode("#b691ec")));
				btnFourWan.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
				btnFourWan.setBorder(BorderFactory.createLineBorder(Color.decode("#b691ec")));
				btnThreeWan.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
				btnThreeWan.setBorder(BorderFactory.createLineBorder(Color.decode("#b691ec")));
				btnTwoWan.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
				btnTwoWan.setBorder(BorderFactory.createLineBorder(Color.decode("#b691ec")));
				btnOneWan.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
				btnOneWan.setBorder(BorderFactory.createLineBorder(Color.decode("#b691ec")));
			}

			

		});

		this.showJpanel.add(textField);
		keyPopup = new SoftKeyBoardPopups4(textField);
		keyPopup.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				logger.info("点击输入金额框");
				promptLabel.setText("");
				
				String money=textField.getText();
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
				textField.setText(money);
				
				transBean.setMoney(money);
				// 当输完金额时进行校验
				if (checkMoneyAndOption(transBean)) {
					// 当键盘消失后调用接口显示利率利息
					showRateAndMoney(transBean, comp);
				}

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {

			}
		});
		// 存期 
		JLabel lblNewLabel_5 = new JLabel("存       期 :");
		lblNewLabel_5.setForeground(Color.BLACK);
		lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_5.setForeground(Color.decode("#333333"));
		lblNewLabel_5.setBounds(69, 386, 123, 40);
		this.showJpanel.add(lblNewLabel_5);
		// 金额
		JLabel lblNewLabel_6 = new JLabel("存入金额 :");
		lblNewLabel_6.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_6.setForeground(Color.decode("#333333"));
		lblNewLabel_6.setBounds(69, 451, 123, 40);
		this.showJpanel.add(lblNewLabel_6);
		// 到期是否自动转存
		JLabel lblNewLabel_8 = new JLabel("转存方式 :");
		lblNewLabel_8.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_8.setBounds(69, 519, 123, 40);
		lblNewLabel_8.setForeground(Color.decode("#333333"));
		this.showJpanel.add(lblNewLabel_8);
		// 存期
		// 三个月
		final JLabel btnThreeMon = new JLabel("三个月",0);
		btnThreeMon.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
		btnThreeMon.setForeground(Color.decode("#333333"));
		btnThreeMon.setHorizontalTextPosition(SwingConstants.CENTER);
		btnThreeMon.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnThreeMon.setBounds(194, 386, 90, 40);
		btnThreeMon.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		this.showJpanel.add(btnThreeMon);
		// 6个月
		final JLabel btnSixMon = new JLabel("六个月",0);
		btnSixMon.setForeground(Color.decode("#333333"));
		btnSixMon.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
		btnSixMon.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSixMon.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnSixMon.setBounds(320, 386, 90, 40);
		btnSixMon.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		this.showJpanel.add(btnSixMon);
		// 1年
		final JLabel btnOneYear = new JLabel("一年",0);
		btnOneYear.setForeground(Color.decode("#333333"));
		btnOneYear.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
		btnOneYear.setHorizontalTextPosition(SwingConstants.CENTER);
		btnOneYear.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnOneYear.setBounds(442, 386, 90, 40);
		btnOneYear.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		this.showJpanel.add(btnOneYear);
		// 2年
		final JLabel btnTwoYear = new JLabel("二年",0);
		btnTwoYear.setForeground(Color.decode("#333333"));
		btnTwoYear.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
		btnTwoYear.setHorizontalTextPosition(SwingConstants.CENTER);
		btnTwoYear.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnTwoYear.setBounds(557, 386, 90, 40);
		btnTwoYear.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		this.showJpanel.add(btnTwoYear);
		// 3年
		final JLabel btnThreeYear = new JLabel("三年",0);
		btnThreeYear.setForeground(Color.decode("#333333"));
		btnThreeYear.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
		btnThreeYear.setHorizontalTextPosition(SwingConstants.CENTER);
		btnThreeYear.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnThreeYear.setBounds(675, 386, 90, 40);
		btnThreeYear.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		this.showJpanel.add(btnThreeYear);
		// 5年
		final JLabel btnFiveYear = new JLabel("五年",0);
		btnFiveYear.setForeground(Color.decode("#333333"));
		btnFiveYear.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
		btnFiveYear.setHorizontalTextPosition(SwingConstants.CENTER);
		btnFiveYear.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnFiveYear.setBounds(787, 386, 90, 40);
		btnFiveYear.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		this.showJpanel.add(btnFiveYear);

		// 自动转存 
		final JLabel yes = new JLabel("自动转存",0);
		yes.setForeground(Color.decode("#333333"));
		yes.setIcon(new ImageIcon("pic/newPic/inputinfo1_1.png"));
		yes.setHorizontalTextPosition(SwingConstants.CENTER);
		yes.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		yes.setBounds(194, 519, 130, 40);
		this.showJpanel.add(yes);
		// 非自动转存 
		final JLabel no = new JLabel("非自动转存",0);
		no.setForeground(Color.decode("#333333"));
		no.setIcon(new ImageIcon("pic/newPic/inputinfo2_1.png"));
		no.setHorizontalTextPosition(SwingConstants.CENTER);
		no.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		no.setBounds(407, 519, 150, 40);
		no.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		this.showJpanel.add(no);
		// 监听存期为3个月的按钮
		btnThreeMon.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择存期为3个月的按钮");
				monthChangeIconAndBorder(btnFiveYear, btnSixMon, btnOneYear, btnTwoYear, btnThreeYear, btnThreeMon);
				transBean.setMonthsUpper("03");
				transBean.setDepUnit("M");
				keyPopup.setVisible(false);
				if (checkMoneyAndOption(transBean)) {
					// 当键盘消失后调用接口显示利率利息
					showRateAndMoney(transBean, comp);
				}

			}

		});
		// 监听存期为6个月的按钮
		btnSixMon.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择存期为6个月的按钮");
				monthChangeIconAndBorder(btnThreeMon, btnFiveYear, btnOneYear, btnTwoYear, btnThreeYear, btnSixMon);
				transBean.setMonthsUpper("06");
				transBean.setDepUnit("M");
				keyPopup.setVisible(false);
				if (checkMoneyAndOption(transBean)) {
					// 当键盘消失后调用接口显示利率利息
					showRateAndMoney(transBean, comp);
				}

			}

		});
		// 监听存期为1年的按钮
		btnOneYear.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择存期为1年的按钮");
				monthChangeIconAndBorder(btnThreeMon, btnSixMon, btnFiveYear, btnTwoYear, btnThreeYear, btnOneYear);
				transBean.setMonthsUpper("01");
				transBean.setDepUnit("Y");
				keyPopup.setVisible(false);
				if (checkMoneyAndOption(transBean)) {
					// 当键盘消失后调用接口显示利率利息
					showRateAndMoney(transBean, comp);
				}
			}

		});
		// 监听存期为2年的按钮
		btnTwoYear.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择存期为2年的按钮");
				monthChangeIconAndBorder(btnThreeMon, btnSixMon, btnOneYear, btnFiveYear, btnThreeYear, btnTwoYear);
				transBean.setMonthsUpper("02");
				transBean.setDepUnit("Y");
				keyPopup.setVisible(false);
				if (checkMoneyAndOption(transBean)) {
					// 当键盘消失后调用接口显示利率利息
					showRateAndMoney(transBean, comp);
				}
			}

		});
		// 监听存期为3年的按钮
		btnThreeYear.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择存期为3年的按钮");
				monthChangeIconAndBorder(btnThreeMon, btnSixMon, btnOneYear, btnTwoYear, btnFiveYear,btnThreeYear);
				transBean.setMonthsUpper("03");
				transBean.setDepUnit("Y");
				keyPopup.setVisible(false);
				if (checkMoneyAndOption(transBean)) {
					// 当键盘消失后调用接口显示利率利息
					showRateAndMoney(transBean, comp);
				}
			}

		});
		// 监听存期为5年的按钮
		btnFiveYear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择存期为5年的按钮");
				monthChangeIconAndBorder(btnThreeMon, btnSixMon, btnOneYear,
						btnTwoYear, btnThreeYear, btnFiveYear);
				transBean.setMonthsUpper("05");
				transBean.setDepUnit("Y");
				keyPopup.setVisible(false);
				if (checkMoneyAndOption(transBean)) {
					// 当键盘消失后调用接口显示利率利息
					showRateAndMoney(transBean, comp);
				}
			}

			
		});
		// 监听存款金额为1万的按钮
		btnOneWan.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择金额为1万的按钮");
				moneyChangeIconAndBorder(btnThreeWan, btnTwoWan, btnOneWan, btnFourWan, btnFiveWan);
				transBean.setMoney("10000");
				textField.setText("");
				keyPopup.setVisible(false);
				if (checkMoneyAndOption(transBean)) {
					// 当键盘消失后调用接口显示利率利息
					showRateAndMoney(transBean, comp);
				}
			}
		});
		// 监听存款金额为2万的按钮
		btnTwoWan.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择金额为2万的按钮");
				moneyChangeIconAndBorder(btnOneWan, btnThreeWan, btnTwoWan, btnFourWan, btnFiveWan);
				transBean.setMoney("20000");
				keyPopup.setVisible(false);
				textField.setText("");
				if (checkMoneyAndOption(transBean)) {
					// 当键盘消失后调用接口显示利率利息
					showRateAndMoney(transBean, comp);
				}
			}
		});
		// 监听存款金额为3万的按钮
		btnThreeWan.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择金额为3万的按钮");
				moneyChangeIconAndBorder(btnOneWan, btnTwoWan, btnThreeWan, btnFourWan, btnFiveWan);
				transBean.setMoney("30000");
				textField.setText("");
				keyPopup.setVisible(false);
				if (checkMoneyAndOption(transBean)) {
					// 当键盘消失后调用接口显示利率利息
					showRateAndMoney(transBean, comp);
				}
			}
		});
		// 监听存款金额为4万的按钮
		btnFourWan.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择存款金额为4万的按钮");
				moneyChangeIconAndBorder(btnOneWan, btnTwoWan, btnFourWan, btnThreeWan, btnFiveWan);
				transBean.setMoney("40000");
				textField.setText("");
				keyPopup.setVisible(false);
				if (checkMoneyAndOption(transBean)) {
					// 当键盘消失后调用接口显示利率利息
					showRateAndMoney(transBean, comp);
				}
			}
		});
		// 监听存款金额为5万的按钮
		btnFiveWan.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				moneyChangeIconAndBorder(btnOneWan, btnTwoWan, btnFiveWan, btnFourWan, btnThreeWan);
				transBean.setMoney("50000");
				textField.setText("");
				keyPopup.setVisible(false);
				if (checkMoneyAndOption(transBean)) {
					logger.info("选择金额为5万的按钮");
					// 当键盘消失后调用接口显示利率利息
					showRateAndMoney(transBean, comp);
				}
			}
		});
		// 监听是否自动转存（是）的按钮
		yes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择自动转存是按钮");
				yes.setIcon(new ImageIcon("pic/newPic/inputinfo1.png"));
				no.setIcon(new ImageIcon("pic/newPic/inputinfo2_1.png"));
				transBean.setAutoRedpFlag("1");

			}
		});
		// 监听是否自动转存（否）的按钮
		no.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择自动转存否按钮");
				no.setIcon(new ImageIcon("pic/newPic/inputinfo2.png"));
				yes.setIcon(new ImageIcon("pic/newPic/inputinfo1_1.png"));
				transBean.setAutoRedpFlag("0");

			}
		});
		
		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				Map<String, Object> params = new HashMap<String, Object>();
				OpenDeposit(transBean, comp, params);
			}
		});
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(1075, 770);
		add(submitBtn);

		// 确认
		JLabel confirm = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		confirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击确认按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				scanBill(comp, transBean);
			}
		});
		confirm.setSize( 150, 50);
		confirm.setLocation(890, 770);
		add(confirm);
		
		// 返回
		JLabel backButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				clearTimeText();
				openPanel(new OutputBankCardPanel());
			}
		});
		add(backButton);

		JLabel lblNewLabel_7 = new JLabel("用户信息");
		lblNewLabel_7.setFont(new Font("微软雅黑", Font.BOLD, 24));
		lblNewLabel_7.setForeground(Color.decode("#333333"));
		lblNewLabel_7.setBounds(69, 140, 150, 40);
		lblNewLabel_7.setForeground(Color.decode("#412174"));
		this.showJpanel.add(lblNewLabel_7);

		JLabel lblNewLabel_9 = new JLabel("录入存款信息");
		lblNewLabel_9.setForeground(Color.decode("#333333"));
		lblNewLabel_9.setFont(new Font("微软雅黑", Font.BOLD, 24));
		lblNewLabel_9.setForeground(Color.decode("#412174"));
		lblNewLabel_9.setBounds(69, 262, 150, 40);
		this.showJpanel.add(lblNewLabel_9);
		// 元
		JLabel lblNewLabel_10 = new JLabel("元");
		lblNewLabel_10.setForeground(Color.decode("#333333"));
		lblNewLabel_10.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_10.setBounds(940, 451, 54, 40);
		this.showJpanel.add(lblNewLabel_10);

		if("1".equals(transBean.getTransChangeNo())){
			transBean.setTransChangeNo("0");
			transBean.setZzAmt(transBean.getBeiZzAmt());
			transBean.setIsAuthorize("");
			transBean.setIsCheckedPic("");
			transBean.setIsSign("");
		}
	}

	/**
	 * 上一步返回选择业务页面
	 * 
	 * @param params
	 */

	private void OpenDeposit(final AccPublicBean transBean,
			final Component comp, final Map<String, Object> params) {
		logger.info("上一步返回业务选择页");
		closeVoice();
		// 跳转
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				params.put("transCode", "0002");// 输入密码页面的交易码
				params.put("resultCode", "0000");//
				params.put("passWord", transBean.getCardPwd());// 设置卡密码
				params.put("cardNo", transBean.getCardNo());// 设置卡号
				logger.info("跳转页面-" + "卡号:" + transBean.getCardNo() + "密码:"
						+ transBean.getCardPwd());
				if(!transBean.getBeiZzAmt().trim().equals(transBean.getZzAmt().trim())){
					transBean.setZzAmt(transBean.getBeiZzAmt());
					if(new BigDecimal(transBean.getBeiZzAmt()).compareTo(new BigDecimal(Property.getProperties().getProperty("acc_open_ckeckIdAmt")))<0){
						transBean.setMoreThanAmt("1");
						transBean.setIsAuthorize("1");
					}else{
						transBean.setMoreThanAmt("0");
						transBean.setIsAuthorize("0");
					}
				}
				AccountTransFlow.startTransFlow(comp, params);
			}
		});
	}

	// 当有存款金额和存期时进行余额验证
	private boolean checkMoneyAndOption(final AccPublicBean transBean) {
		logger.info("进入金额和存期的余额验证");
		if (isMoneyCheck(transBean) && isOption(transBean) &&checkMoney(transBean)) {
			return true;
		}
		return false;
	}

	/***
	 * 键盘
	 */
	private void scanBill12() {
		logger.info("调用软键盘");
		keyPopup.show(moneyPanel, 737, 100);
		textField.setFocusable(true);
		textField.grabFocus();
	}

	// 余额校验
	private boolean checkMoney(AccPublicBean transBean) {
		logger.info("进入余额校验方法");
		// 余额数目

		double balance = Double.parseDouble(transBean.getCardAmt());
		// 输入金额的数目
		double money = Double.parseDouble(transBean.getMoney());

		System.out.println(money);
		if (balance < money) {
			promptLabel.setText("余额小于存款金额");
			promptLabel.setForeground(Color.red);
			on_off=true;
			return false;
		}
		if (money < 50) {
			promptLabel.setText("存款金额必须大于50元");
			promptLabel.setForeground(Color.red);
			on_off=true;
			return false;
		}
		if (balance < 50) {
			promptLabel.setText("余额必须大于50元");
			promptLabel.setForeground(Color.red);
			on_off=true;
			return false;
		}
		promptLabel.setText("");
		return true;
	}

	// 利率利息显示
	private void showRateAndMoney(final AccPublicBean transBean,
			final Component comp) {
		// 调用接口通过存期和存款金额得到利率和利息
		new Thread() {
			@Override
			public void run() {
				try {
					prossDialog.showDialog();
					AccountTradeCodeAction.transBean.getReqMCM001().setReqBefor("02867");
					Map inteMap = IntefaceSendMsg.inter02867(transBean);
					AccountTradeCodeAction.transBean.getReqMCM001().setReqAfter((String)inteMap.get("resCode"), (String)inteMap.get("errMsg"));
					prossDialog.disposeDialog();
					if (!"000000".equals(inteMap.get("resCode"))) {
						MakeMonitorInfo.makeInfos(AccountTradeCodeAction.transBean.getReqMCM001());
						serverStop(comp, "", (String) inteMap.get("errMsg"),"");
						return;
					}
					// 获取利率
					List<ProductRateInfo> list = (List<ProductRateInfo>) inteMap.get(IntefaceSendMsg.KEY_PRODUCT_RATES);
					for (ProductRateInfo productRateInfo : list) {
						if (productRateInfo.getSavingCount().equals(transBean.getMonthsUpper()+transBean.getDepUnit())) {
							transBean.setRate(productRateInfo.getInterestRate());
							break;
						}
					}
					Float rate=Float.parseFloat(transBean.getRate());
					rateLabel.setText(rate+"%");
					String monthsUpper = transBean.getMonthsUpper();
					Double time = null;
					int months = Integer.parseInt(monthsUpper);
					if("M".equals(transBean.getDepUnit())){
						time = Double.parseDouble(monthsUpper)/12;
					}else{
						time = Double.parseDouble(monthsUpper);
					}
					Calendar now = Calendar.getInstance();
					SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
					String svrDate = transBean.getSvrDate();
					transBean.setCreateTime(svrDate);//开始时间
					Date parse = new Date();
					try {
						parse = date.parse(svrDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					now.setTime(parse);
					if(transBean.getDepUnit().indexOf("D")!=-1){
						now.add(Calendar.DATE, months);	//一天
					}else if(transBean.getDepUnit().indexOf("M")!=-1){
						now.add(Calendar.MONTH,months);	//一天
					}else if(transBean.getDepUnit().indexOf("Y")!=-1){
						now.add(Calendar.YEAR, months);	//一年
					}
					transBean.setEndTime(date.format(now.getTime()));//结束时间
					Float money=Float.parseFloat(transBean.getMoney());
					transBean.setInte(String.format("%.2f", rate * money* 0.01 * time ));
					interestLabel.setText(transBean.getInte() + "元");
					logger.info("接口02867调用结束" + "金额:" + transBean.getMoney()
							+ "存期" + transBean.getMonthsUpper() + "利率:"
							+ transBean.getRate() + "利息:" + transBean.getInte());
				} catch (Exception e) {
					logger.error("调用接口02867失败" + e);
					prossDialog.disposeDialog();
					AccountTradeCodeAction.transBean.getReqMCM001().setIntereturnmsg("调用02867接口失败");
					MakeMonitorInfo.makeInfos(AccountTradeCodeAction.transBean.getReqMCM001());
					serverStop(comp, "", "调用接口02867失败","");
					return;
				}

				
			}
		}.start();

	}

	private boolean isMoneyCheck(AccPublicBean transBean) {
		// 判断存款金额是否选中
		logger.info("判断是否选中金额" + transBean.getMoney());
		if (transBean.getMoney() == null
				|| "输入金额".equals(transBean.getMoney())||"".equals(transBean.getMoney())) {
			on_off=true;
			return false;
		} else {
			return true;
		}
	}


	//当点击存期时改变背景和边框
	private void monthChangeIconAndBorder( JLabel btnThreeMon,
			 JLabel btnSixMon,  JLabel btnOneYear,
			 JLabel btnTwoYear,  JLabel btnThreeYear,
			 JLabel btnFiveYear) {
		btnFiveYear.setIcon(new ImageIcon("pic/newPic/inputinfo_1.png"));
		btnOneYear.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
		btnTwoYear.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
		btnThreeYear.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
		btnThreeMon.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
		btnSixMon.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
	}
	//当点击存入金额按钮后改变背景和边框
	private void moneyChangeIconAndBorder( JLabel l1,
			 JLabel l2,  JLabel l3,
			 JLabel l4,  JLabel l5) {
		l3.setIcon(new ImageIcon("pic/newPic/inputinfo_1.png"));
		l5.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
		l4.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
		l2.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
		l1.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
	}
	/*
	 * 判断存期
	 */
	public boolean isOption(AccPublicBean transBean) {
		// 判断存期是否选中
		logger.info("判断是否选中存期" + transBean.getMonthsUpper());
		if (transBean.getMonthsUpper() != null) {
			return true;
		} else {
			on_off=true;
			return false;
		}
	}

	// 判断自动转存是否选中
	public boolean isAutos(AccPublicBean transBean) {
		logger.info("判断是否选择自动转存" + transBean.getAutoRedpFlag());
		if (transBean.getAutoRedpFlag() != null) {
			return true;
		} else {
			// 语音提示
			excuteVoice("voice/optatuodep.wav");
			// 加入提示语
			promptLabel.setText("请选择是否自动转存");
			promptLabel.setForeground(Color.red);
			on_off=true;
			return false;
		}
	}



	// 确定
	private void scanBill(final Component comp, final AccPublicBean transBean) {
		/*
		 * 当用户全部选择之后判断进入哪个页面
		 */
		logger.info("跳转页面" + transBean);
		if (isOption(transBean) == true && isMoneyCheck(transBean) == true
				&& isAutos(transBean) == true && checkMoney(transBean)) {
			BigDecimal money = new BigDecimal(transBean.getMoney());
			BigDecimal zzAmt = new BigDecimal(transBean.getZzAmt());
			BigDecimal a = new BigDecimal(Property.getProperties().getProperty("acc_open_ckeckIdAmt"));
			BigDecimal totalMoney = money;
			AccountTradeCodeAction.transBean.setZzAmt(totalMoney.toString());
			int result = totalMoney.compareTo(a);
			
			if("".equals(AccountTradeCodeAction.transBean.getTransChangeNo()) || AccountTradeCodeAction.transBean.getTransChangeNo()==null ||"0".equals(AccountTradeCodeAction.transBean.getTransChangeNo())){
				//交易 金额大于20万
				if(result>=0){
					AccountTradeCodeAction.transBean.setTransChangeNo("1");
					AccountTradeCodeAction.transBean.setMoreThanAmt("0");
					AccountTradeCodeAction.transBean.setIsAuthorize("0");//授权标识
				}else{
					AccountTradeCodeAction.transBean.setTransChangeNo("0");
					AccountTradeCodeAction.transBean.setIsAuthorize("1");//不授权标识
					AccountTradeCodeAction.transBean.setMoreThanAmt("1");
				}
			}
			closeVoice();
			clearTimeText();
			
			promptLabel.setText("");
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					// 调流程
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("transCode", "0004");
					AccountTransFlow.startTransFlow(comp, map);
				}
			});

		} else if (!isOption(transBean)) {
			// 加入提示音
			excuteVoice("voice/optionday.wav");
			// 加入提示语
			promptLabel.setText("请选择存期");
			promptLabel.setForeground(Color.red);
			on_off=true;
		} else if (!isMoneyCheck(transBean)) {
			// 加入提示音
			excuteVoice("voice/optinputmoney.wav");
			// 加入提示语
			promptLabel.setText("请选择存储金额");
			promptLabel.setForeground(Color.red);
			on_off=true;
		}
	}
}
