package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.TransService.CashOpen.Interface.AccountDepositApi;
import com.boomhope.Bill.TransService.CashOpen.Interface.SearchInteInfo;
import com.boomhope.Bill.Util.DateTool;
import com.boomhope.Bill.Util.NumberZH;
import com.boomhope.Bill.Util.UtilVoice;

/***
 * 私行积享存 录入页面 
 * @author gyw
 *
 */
public class MoneyBoxEnteringSHJXCPanel extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxEnteringSHJXCPanel.class);
	JLabel promptLabel;
	UtilVoice utilVoice = null;
	SourceDataLine line = null;
	/* 标记是否点击了 */
	private boolean isbutton_1 = true;// 标记3个月存期的
	private boolean isopenButton = true;// 标记6个月存期的
	private boolean isoneYearButton = true;// 标记一年存期的
	private boolean istwoYearButton = true;// 标记二年存期的
	private boolean isthreeYearButton = true;// 标记三年存期的
	private boolean isfiveYearButton = true;// 标记五年存期的
	
	private boolean isonemillbutton = true;// 标记一万的存款
	private boolean istwomillbutton = true;// 标记二万的存款
	private boolean isthreemillbutton = true;// 标记三万的存款
	private boolean isfourmillbutton = true;// 标记四万的存款
	private boolean isfivemillbutton = true;// 标记五万的存款
	private boolean fivemillButtoon11 = true;// 标记6万的存款
	private boolean fivemillButtoon21 = true;// 标记7万的存款
	
	private boolean ispaykey = true;// 标记是凭密支付
	private boolean nopaykey = true;// 标记不是频密支付
	private boolean isauto = true;// 标记是自动存
	private boolean noauto = true;// 标记不是自动转存

	
	
	 JButton onemillButton =null;
	 JButton twomillButtoon=null;
	 JButton threemillButtoon=null;
	 JButton fourmillButtoon=null;
	 JButton fivemillButtoon=null;
	 JButton fivemillButtoon1=null;
	 JButton fivemillButtoon2=null;
	 
	 JButton button_6=null;
	 JButton button_7=null;
	 JLabel lblNewLabel_5;
	//测试金额
	double testingAmount =5000;
	//测试自动转存
	String a="1";
	
	private boolean on_off=true;//开关控制
	public MoneyBoxEnteringSHJXCPanel(final PublicCashOpenBean  transBean) {
		this.cashBean = transBean;

		// 流程
		JLabel billImage1 = new JLabel();
		billImage1.setIcon(new ImageIcon("pic/business.png"));
		billImage1.setIconTextGap(6);
		billImage1.setBounds(104, 76, 848, 61);
		this.showJpanel.add(billImage1);

		/* 用户信息 */
		JLabel label = new JLabel("用户信息");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label.setBounds(60, 118, 150, 40);
		label.setForeground(Color.decode("#412174"));
		this.showJpanel.add(label);
		// 户名
		JLabel lblNewLabel = new JLabel("户名 :");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel.setBounds(60, 168, 94, 40);
		this.showJpanel.add(lblNewLabel);
		// 银行卡金额
//		JLabel label_1 = new JLabel("银行卡金额 :");
//		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
//		label_1.setBounds(506, 178, 174, 50);
//		add(label_1);
		// 获取用户名
		JLabel label_5 = new JLabel(transBean.getCardName());
		label_5.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_5.setBounds(164, 168, 135, 40);
		this.showJpanel.add(label_5);
		// 获取银行卡金额
//		JLabel label_6 = new JLabel(transBean.getBalanceStr()+"元");
//		label_6.setFont(new Font("微软雅黑", Font.PLAIN, 20));
//		label_6.setBounds(651, 178, 301, 50);
//		add(label_6);
		// 录入存款信息
		JLabel lblNewLabel_1 = new JLabel("录入存款信息");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_1.setForeground(Color.decode("#412174"));
		lblNewLabel_1.setBounds(60, 242, 150, 40);
		this.showJpanel.add(lblNewLabel_1);

		// 产品系列
		JLabel lblNewLabel_20 = new JLabel("产品系列:");
		lblNewLabel_20.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_20.setBounds(60, 295, 103, 40);
		this.showJpanel.add(lblNewLabel_20);
		// 产品按钮
		final JButton button10 = new JButton(transBean.getProductName(), new ImageIcon("pic/bj.png"));
		button10.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		button10.setHorizontalTextPosition(SwingConstants.CENTER);
		button10.setBounds(173, 295, 365, 40);
		button10.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		button10.setContentAreaFilled(false);// 设置图片填满按钮所在的区域
		button10.setFocusPainted(true);// 设置这个按钮是不是获得焦点
		button10.setBorderPainted(false);// 设置是否绘制边框
		button10.setBorder(null);// 设置边框
		button10.setIcon(new ImageIcon("pic/bj.png"));
		button10.setForeground(Color.white);
		this.showJpanel.add(button10);

		// 存期
		JLabel lblNewLabel_2 = new JLabel("存      期 :");
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_2.setBounds(60, 361, 103, 40);
		this.showJpanel.add(lblNewLabel_2);
		/* 存期时间 */
		final JLabel button = new JLabel(transBean.getMonthsUpperStr(), new ImageIcon("pic/newPic/inputinfo_1.png"),0);
		button.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setBounds(182, 361, 90, 40);
		button.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		button.setBorder(null);// 设置边框
		button.setForeground(Color.white);
		this.showJpanel.add(button);
		// 存入
		JLabel label_2 = new JLabel("存入金额 :");
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_2.setBounds(60, 428, 111, 40);
		this.showJpanel.add(label_2);
		// 自动转存
		JLabel lblNewLabel_3 = new JLabel("自动转存 :");
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_3.setBounds(60, 505, 111, 40);
		this.showJpanel.add(lblNewLabel_3);
		if(a=="1"){
			 button_7 = new JButton("否", new ImageIcon(
						"pic/newPic/inputinfo_1.png"));
				button_7.setBounds(265, 505, 85, 50);
				button_7.setHorizontalTextPosition(SwingConstants.CENTER);
				button_7.setFont(new Font("微软雅黑", Font.PLAIN, 20));
				button_7.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
				button_7.setContentAreaFilled(false);// 设置图片填满按钮所在的区域
				button_7.setFocusPainted(true);// 设置这个按钮是不是获得焦点
				button_7.setBorderPainted(false);// 设置是否绘制边框
				button_7.setBorder(null);// 设置边框
				button_7.setForeground(Color.white);// 被点击的按钮的字体显示为白色
				noauto = false;
				this.showJpanel.add(button_7);
		}else{
			// 是否自动转存
		    button_6 = new JButton("是", new ImageIcon(
					"pic/newPic/inputinfo.png"));
			button_6.setBounds(265, 505, 85, 50);
			button_6.setHorizontalTextPosition(SwingConstants.CENTER);
			button_6.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			button_6.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
			button_6.setContentAreaFilled(false);// 设置图片填满按钮所在的区域
			button_6.setFocusPainted(true);// 设置这个按钮是不是获得焦点
			button_6.setBorderPainted(false);// 设置是否绘制边框
			button_6.setBorder(null);// 设置边框
			// 监听是否自动转存的按钮
			button_6.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					isauto = button_6.isSelected();
					noauto = true;
					button_6.setIcon(new ImageIcon("pic/newPic/inputinfo_1.png"));// 点击背景变颜色
					button_6.setForeground(Color.white);// 被点击的按钮的字体显示为白色
					button_7.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
					button_7.setForeground(Color.black);// 未点击的按钮字体还原
				}
			});
			this.showJpanel.add(button_6);

		    button_7 = new JButton("否", new ImageIcon(
					"pic/newPic/inputinfo.png"));
			button_7.setBounds(370, 505, 85, 50);
			button_7.setHorizontalTextPosition(SwingConstants.CENTER);
			button_7.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			button_7.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
			button_7.setContentAreaFilled(false);// 设置图片填满按钮所在的区域
			button_7.setFocusPainted(true);// 设置这个按钮是不是获得焦点
			button_7.setBorderPainted(false);// 设置是否绘制边框
			button_7.setBorder(null);// 设置边框
			button_7.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					noauto = button_7.isSelected();
					isauto = true;
					button_7.setIcon(new ImageIcon("pic/newPic/inputinfo_1.png"));// 点击背景变颜色
					button_7.setForeground(Color.white);// 被点击的按钮的字体显示为白色
					button_6.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
					button_6.setForeground(Color.black);// 未点击的按钮字体还原
				}
			});
			this.showJpanel.add(button_7);
		}
		
		

		//确认
		JLabel lblNewLabel1 = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		lblNewLabel1.setBounds(890, 770, 150, 50);
		lblNewLabel1.addMouseListener(new MouseAdapter() {
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
				okTrans(transBean);
			}

		});
		this.showJpanel.add(lblNewLabel1);
		//上一步
		JLabel label1 = new JLabel(new ImageIcon("pic/preStep.png"));
		label1.addMouseListener(new MouseAdapter() {
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
		label1.setBounds(1075, 770, 150, 50);
		this.showJpanel.add(label1);
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
				returnHome();
			}

		});
		label_1.setBounds(1258, 770, 150, 50);
		this.showJpanel.add(label_1);
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				voiceTimer.stop();
				excuteVoice();

			}
		});
		voiceTimer.start();
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* 倒计时结束退出交易 */
				on_off=false;
				transBean.setErrmsg("");
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		delayTimer.start();

		/* 提示信息 */
		promptLabel = new JLabel("");
		promptLabel.setHorizontalAlignment(JLabel.CENTER);
		promptLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		promptLabel.setBounds(0, 565, 1009, 30);
		this.showJpanel.add(promptLabel);

		this.showJpanel.add(button_7);

		jslx(transBean);
		JLabel lblNewLabel_4 = new JLabel("到期利率：");
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_4.setBounds(651, 361, 125, 40);
		this.showJpanel.add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel();
		lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_5.setBounds(780, 361, 212, 40);
		this.showJpanel.add(lblNewLabel_5);
		
//		JLabel lblNewLabel_6 = new JLabel("所得利息：");
//		lblNewLabel_6.setFont(new Font("微软雅黑", Font.PLAIN, 20));
//		lblNewLabel_6.setBounds(716, 370, 111, 50);
//		add(lblNewLabel_6);
		
//		JLabel lblNewLabel_7 = new JLabel("New label");
//		lblNewLabel_7.setFont(new Font("微软雅黑", Font.PLAIN, 20));
//		lblNewLabel_7.setBounds(840, 370, 168, 50);
//		add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("关联产品：");
		lblNewLabel_8.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_8.setBounds(651, 295, 125, 40);
		this.showJpanel.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel(transBean.getRelaAcctName());
		lblNewLabel_9.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_9.setBounds(780, 295, 212, 40);
		this.showJpanel.add(lblNewLabel_9);
		
		JLabel lblNewLabel_6 = new JLabel("New label");
		lblNewLabel_6.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_6.setBounds(188, 425, 300, 40);
		this.showJpanel.add(lblNewLabel_6);


	}

	/**
	 * 执行语音
	 */
	private void excuteVoice() {
		// 加载音频
		utilVoice = new UtilVoice();
		try {
			line = utilVoice.voice("voice/inputdepinfo.wav");
		} catch (UnsupportedAudioFileException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} catch (LineUnavailableException e) {
			logger.error(e);
		}
	}

	/**
	 * 返回
	 * */
	public void backTrans(PublicCashOpenBean transBean) {
		clearTimeText();		
		openPanel(new MoneyBoxCatalogSHJXCXLPanel(transBean));		
				
			
		
	}

	/*
	 * 判断存期
	 * */
	public boolean isOption(){
		//判断存期是否选中
		if(isopenButton==false||isbutton_1==false||isoneYearButton==false||istwoYearButton==false||isthreeYearButton==false||isfiveYearButton==false){
			return true;
		}else{
			//加入提示音
//			inputday();
			//加入提示语
			promptLabel.setText("请选择存期");
			promptLabel.setForeground(Color.red);
			//默认选中
			return true;
		}
	}
	
	//判断存入金额是否选中
	public boolean ismoney(){
		//判断存入金额是否选中，如果用户直接在输入框中输入值也可以，此处还未对输入框做判断
		 if(isonemillbutton==false||istwomillbutton==false||isthreemillbutton==false||isfourmillbutton==false||isfivemillbutton==false||fivemillButtoon11==false||fivemillButtoon21==false){
			return true;
		}else{
			//加入提示音
//			inputmoney();
			//加入提示语
			promptLabel.setText("请选择存入金额");
			promptLabel.setForeground(Color.red);
			return false;
		}
	}
	//判断凭密支付是否选中
	public  boolean ispaypass(){
		if(ispaykey==false||nopaykey==false){
			//如果选择凭密支付确认时要弹出输入支付密码的框
			if(ispaykey==false){
				//确认时要弹出密码框
//				openBean.setPopUpBox(1);
				
				}
			return true;
		}else{
//			//语音提示
//			optinputdep();
//			//加入提示语
//			promptLabel.setText("请选择是否凭密支付");
//			promptLabel.setForeground(Color.red);
			//不判断
		return true;
		}
	}
	
	//判断自动转存是否选中
	public  boolean isautos(){
		if(isauto==false||noauto==false){
			return true;
		}else{
			//语音提示
//			optatuodep();
//			//加入提示语
//			promptLabel.setText("请选择是否自动转存");
//			promptLabel.setForeground(Color.red);
			//不判断
		return true;
		}
	}
	/* 未选择存期时提示音 */
	public void inputday(){
//		playVoice("voice/optionday.wav");

		//加载音频
		utilVoice = new UtilVoice();
		try {
			 line = utilVoice.voice("voice/optionday.wav");
		} catch (UnsupportedAudioFileException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} catch (LineUnavailableException e) {
			logger.error(e);
		}
	
	}
	/* 未选择存入金额时提示音 */
	public void inputmoney(){
//		playVoice("voice/optinputmoney.wav");
		//加载音频
		utilVoice = new UtilVoice();
		try {
			 line = utilVoice.voice("voice/optinputmoney.wav");
		} catch (UnsupportedAudioFileException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} catch (LineUnavailableException e) {
			logger.error(e);
		}
	}
	/* 未选择凭密支付时提示音 */
	public void optinputdep(){
//		playVoice("voice/optdeppass.wav");
		//加载音频
		utilVoice = new UtilVoice();
		try {
			 line = utilVoice.voice("voice/optdeppass.wav");
		} catch (UnsupportedAudioFileException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} catch (LineUnavailableException e) {
			logger.error(e);
		}
	}
	/* 未选择自动转存时提示音 */
	public void optatuodep(){
//		playVoice("voice/optatuodep.wav");
		//加载音频
		utilVoice = new UtilVoice();
		try {
			 line = utilVoice.voice("voice/optatuodep.wav");
		} catch (UnsupportedAudioFileException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} catch (LineUnavailableException e) {
			logger.error(e);
		}
	}
	/*
	 * 判断选择的金额
	 */
	public void money(PublicCashOpenBean transBean){
		if(isonemillbutton==false){
			int money = Integer.parseInt("1000.00".replace(".00", ""));
			transBean.setMoney(money);
		}else if(istwomillbutton==false){
			int money = Integer.parseInt("2000.00".replace(".00", ""));
			transBean.setMoney(money);
		}else if(isthreemillbutton==false){
			int money = Integer.parseInt("3000.00".replace(".00", ""));
			transBean.setMoney(money);
		}else if(isfourmillbutton==false){
			int money = Integer.parseInt("4000.00".replace(".00", ""));
			transBean.setMoney(money);
		}else if(isfivemillbutton==false){
			int money = Integer.parseInt("5000.00".replace(".00", ""));
			transBean.setMoney(money);
		}else if(fivemillButtoon11==false){
			int money = Integer.parseInt("10000.00".replace(".00", ""));
			transBean.setMoney(money);
		}
		
	}
	
	/**
	 *   产品预计利息(24小时)-03510
	 */
	public void jslx(PublicCashOpenBean transBean){
		money(transBean);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PROD_CODE", transBean.getProductCode());		//产品代码	账号不输时必输
		map.put("AMT",transBean.getMoney());		//金额	账号不输时必输
		map.put("OPEN_DATE", transBean.getSvrDate());	//开户日	必输	
		map.put("CUST_NO", transBean.getCustNo());	//	客户号	必输
		map.put("MonthsUpper", transBean.getMonthsUpper());//取款期	必输
		map.put("ACCT_NO", "");//账号
		map.put("SUB_ACCT_NO", "");//子账号
		if("1".equals(transBean.getPrivateLine())){
			map.put("CHL_ID", "3");
		}
		AccountDepositApi accountDepositApi = new AccountDepositApi();
		accountDepositApi.searchInteInfo(map);
		List<SearchInteInfo> productInfos=(List<SearchInteInfo>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
		if(productInfos!=null&&productInfos.size()>0){
			SearchInteInfo info=productInfos.get(0);
			transBean.setInte(info.getRegularInte());	//定期利息
			transBean.setRate(info.getRate());          //利率				
		}  
		lblNewLabel_5.setText(transBean.getRate()+"%");
	}
	
	
	/**
	 * 确认
	 * */
	public void okTrans(PublicCashOpenBean transBean){
		transBean.setCreateTime(transBean.getSvrDate());//开始时间改成核心时间
		DateTool dt = new DateTool();
		if(transBean.getMonthsUpper().substring(transBean.getMonthsUpper().length()-1, transBean.getMonthsUpper().length()).equals("D")){
			String abc = dt.getNextDay(transBean.getSvrDate(),Integer.parseInt(transBean.getMonthsUpper().replace("D", "")));
			transBean.setEndTime(abc);
		}else{
			String abc = dt.nMonthsAfterOneString(transBean.getSvrDate(), transBean.getMonthsUpper());
			transBean.setEndTime(abc);
		}
		transBean.setMoneyUpper(NumberZH.change(Double.parseDouble(String.valueOf(transBean.getMoney()))));//金额大小写转换
		transBean.setAutoSaving("0");//不自动转存				
		/*
		 * 当用户全部选择之后进入下一个页面
		 * */
		if(ismoney()==true&&ispaypass()==true&&isautos()==true){
	
			//如果选择凭密支付确认时要弹出输入支付密码的框
			if(ispaykey==false){
				clearTimeText();
				openPanel(new MoneyBoxWithCloseTopayPanel(transBean));
						
				}else{
					//如果是新建客户或者金额大于等于5万走授权流程
					String customer_identification = transBean.getImportMap().get("customer_identification");
					if(transBean.getMoney()>=50000.00){
						//判断跳转如果是等于yes就是代理人代理 跳转代理人页面
						if("yes".equals(transBean.getImportMap().get("agent_persion"))){
							clearTimeText();
							//跳转代理人授权页面
							openPanel(new MoneyBoxExistProcuratorPanel(transBean));
								
						}else if ("no".equals(transBean.getImportMap().get("agent_persion"))){
							clearTimeText();
							//跳转本人授权页面
							openPanel(new MoneyBoxNegationProcuratorPanel(transBean));
							
						}							
					}
					//判断 金额如果小于5万走简单流程 
					if(transBean.getMoney()<50000.00){
						clearTimeText();
						//确认存单信息页面
						openPanel(new MoneyBoxOkInputDepinfoPanel(transBean));
						
					}

				}	
					
		

			
		}else{
			on_off=true;
		}
	}
	
}
