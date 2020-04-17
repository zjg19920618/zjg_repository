package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.Bean.AccPublicBean;
import com.boomhope.Bill.TransService.AccOpen.Bean.ProductRateInfo2;
import com.boomhope.Bill.TransService.AccOpen.Interface.IntefaceSendMsg;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTransFlow;
import com.boomhope.Bill.Util.DateTool;
import com.boomhope.Bill.Util.NumberZH;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.UtilVoice;

/***
 * 录入如意存+信息页面
 * 
 * @author zjg
 */
public class AccRyjInputPanel extends BaseTransPanelNew {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccRyjInputPanel.class);

	JLabel promptLabel = null;
	UtilVoice utilVoice = null;
	/* 标记是否点击了 */
	private boolean isauto = false;// 标记是自动存
	private boolean noauto = false;// 标记不是自动转存
	private boolean isMoney=false;//标记是否选择金额
	
	JLabel[] buttons = null;
	boolean isbutton = false;
	JButton button_6 = null;
	JButton button_7 = null;
	JLabel button10 = null;
	JButton button11 = null;
	private JLabel button11_1;
	private String productName = "";// 产品名称
	String startMoney = "";
	List<ProductRateInfo2> list = null;
	JLabel lblNewLabel_5;
	JLabel lblNewLabel_7;

	private Map<Object, Object> params;
	private AccPublicBean accBean;
	private Component comp;
	private boolean on_off=true;

	public AccRyjInputPanel(Map<Object, Object> params) {
		
		comp = this;
		this.params = params;
		accBean = AccountTradeCodeAction.transBean;
		list = (List<ProductRateInfo2>) params
				.get(IntefaceSendMsg.PRODUCT_INFOS);
		logger.info("进入如意+信息录入页");

		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	logger.info("如意+信息页面倒计时结束 ");
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();//清空倒计时
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 

		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond,
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						stopTimer(voiceTimer);// 关闭语音
						closeVoice();//关语音流
						excuteVoice("voice/inputdepinfo.wav");// 执行语音

					}
				});
		voiceTimer.start();

		// 产品名称
		JLabel productNAme = new JLabel(list.get(0).getProductCodeName().trim());
		productNAme.setHorizontalAlignment(SwingConstants.CENTER);
		productNAme.setFont(new Font("微软雅黑", Font.BOLD, 40));
		productNAme.setForeground(Color.decode("#412174"));
		productNAme.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(productNAme);

		/* 用户信息 */
		JLabel label = new JLabel("用户信息");
		label.setFont(new Font("微软雅黑", Font.BOLD, 24));
		label.setForeground(Color.decode("#412174"));
		label.setBounds(69, 128, 150, 40);
		this.showJpanel.add(label);
		// 户名
		JLabel lblNewLabel = new JLabel("用户名 :");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel.setForeground(Color.decode("#333333"));
		lblNewLabel.setBounds(69, 193, 94, 40);
		this.showJpanel.add(lblNewLabel);
		// 银行卡金额
		JLabel label_1 = new JLabel("账户余额 :");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_1.setForeground(Color.decode("#333333"));
		label_1.setBounds(337, 193, 116, 40);
		this.showJpanel.add(label_1);
		// 获取用户名
		JLabel label_5 = new JLabel(accBean.getIdCardName().trim());
		label_5.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_5.setForeground(Color.decode("#333333"));
		label_5.setBounds(173, 193, 135, 40);
		this.showJpanel.add(label_5);
		// 获取银行卡金额
		JLabel label_6 = new JLabel(accBean.getCardAmt().trim()+" 元");
		label_6.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_6.setForeground(Color.decode("#333333"));
		label_6.setBounds(465, 193, 253, 40);
		this.showJpanel.add(label_6);
		// 录入存款信息
		JLabel lblNewLabel_1 = new JLabel("录入存款信息");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.BOLD, 24));
		lblNewLabel_1.setForeground(Color.decode("#412174"));
		lblNewLabel_1.setBounds(69, 272, 150, 40);
		this.showJpanel.add(lblNewLabel_1);

		// 产品系列
		JLabel lblNewLabel_20 = new JLabel("产品系列:");
		lblNewLabel_20.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_20.setForeground(Color.decode("#333333"));
		lblNewLabel_20.setBounds(69, 329, 103, 40);
		this.showJpanel.add(lblNewLabel_20);
		// 产品按钮
		button10 = new JLabel(list.get(0).getProductCodeName().trim(),0);
		button10.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		button10.setHorizontalTextPosition(SwingConstants.CENTER);
		button10.setForeground(Color.decode("#333333"));
		button10.setBounds(183, 329, 365, 40);
		button10.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		button10.setBorder(null);// 设置边框
		this.showJpanel.add(button10);

		// 存期
		JLabel lblNewLabel_2 = new JLabel("存      期 :");
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_2.setForeground(Color.decode("#333333"));
		lblNewLabel_2.setBounds(69, 386, 103, 40);
		this.showJpanel.add(lblNewLabel_2);

		/* 存期时间 */
		int date = 0;
		try {
			String openTime = accBean.getSvrDate();
			String endTime = accBean.getEndTime().replace("/", "");
			
			SimpleDateFormat fommter = new SimpleDateFormat("yyyyMMdd");
			
			Date a1 = fommter.parse(openTime);
			Date b1 = fommter.parse(endTime);
			System.out.println(a1);
			System.out.println(b1);
			date = DateTool.differentsDays(a1, b1);
			accBean.setJxRyjDepTem(String.valueOf(date));
					
		} catch (Exception e) {
			logger.error("积享存存期获取失败"+e);
		}
			
		final JLabel time = new JLabel(accBean.getJxRyjDepTem()+"天",
				new ImageIcon("pic/newPic/inputinfo1.png"), 0);
		time.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		time.setForeground(Color.decode("#333333"));
		time.setHorizontalTextPosition(SwingConstants.CENTER);
		time.setBounds(183, 386, 150, 40);
		time.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		this.showJpanel.add(time);
		
		// 关联如意产品名称
		JLabel lblNewLabel_21 = new JLabel("关联产品:");
		lblNewLabel_21.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_21.setForeground(Color.decode("#333333"));
		lblNewLabel_21.setBounds(585, 326, 103, 40);
		this.showJpanel.add(lblNewLabel_21);
		button11_1 = new JLabel(accBean.getSubProductName().trim(),0);
		button11_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		button11_1.setHorizontalTextPosition(SwingConstants.CENTER);
		button11_1.setBounds(698, 329, 365, 40);
		button11_1.setForeground(Color.decode("#333333"));
		button11_1.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		button11_1.setBorder(null);// 设置边框
		this.showJpanel.add(button11_1);		

		// 存入
		JLabel label_2 = new JLabel("存入金额 :");
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_2.setForeground(Color.decode("#333333"));
		label_2.setBounds(69, 443, 122, 40);
		this.showJpanel.add(label_2);

		// 循环出金额和所对应的产品代码
		int x1 = 194;
		buttons = new JLabel[list.size()];
		for (int i = 0; i < list.size(); i++) {

			final int a = i;
			startMoney = list.get(i).getStartMoneyStr();
			// 循环按钮
			buttons[i] = new JLabel(startMoney, new ImageIcon(
					"pic/newPic/inputinfo.png"), 0);
			buttons[i].setBounds(x1, 443, 90, 40);
			buttons[i].setHorizontalTextPosition(SwingConstants.CENTER);
			buttons[i].setFont(new Font("微软雅黑", Font.PLAIN, 20));
			buttons[i].setForeground(Color.decode("#333333"));
			buttons[i].setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
			buttons[i].setBorder(null);// 设置边框
			this.showJpanel.add(buttons[i]);
			x1 = x1 + 110;
			
			buttons[i].addMouseListener(new MouseAdapter() {// 选中金额按钮事件
						@Override
						public void mouseReleased(MouseEvent e) {
							logger.info("点击选择金额按钮");
							for (int j = 0; j < buttons.length; j++) {

								if (buttons[a].equals(buttons[j])) {
									
									buttons[j].setIcon(new ImageIcon(
											"pic/newPic/inputinfo_1.png"));// 点击背景变颜色
									isbutton = true;
									button(list.get(a).getStartMoneyStr(),list.get(a).getProductCode(),list.get(a).getProductCodeName());
									jslx();
								} else {
									buttons[j].setIcon(new ImageIcon(
											"pic/inputinfo.png"));// 未点击的按钮背景还原
								}
							}
						}
					});
		}

		// 自动转存
		JLabel lblNewLabel_3 = new JLabel("转存方式 :");
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_3.setForeground(Color.decode("#333333"));
		lblNewLabel_3.setBounds(69, 500, 150, 40);
		this.showJpanel.add(lblNewLabel_3);

		// 非自动转存
		final JLabel no = new JLabel("非自动转存", 0);
		no.setIcon(new ImageIcon("pic/newPic/inputinfo2.png"));
		no.setHorizontalTextPosition(SwingConstants.CENTER);
		no.setForeground(Color.decode("#333333"));
		no.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		no.setBounds(194, 500, 150, 40);
		no.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		this.showJpanel.add(no);
		
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
				/* 处理上一页 */
				accBean.setDepUnit("");//清空存期单位
				backTrans();
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
				/* 处理下一页 */
				okTrans();
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
				stopTimer(voiceTimer);//关闭语音
		    	closeVoice();
				openPanel(new OutputBankCardPanel());
			}
		});
		add(backButton);
		

		/* 提示信息 */
		promptLabel = new JLabel("");
		promptLabel.setHorizontalAlignment(SwingUtilities.RIGHT);
		promptLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		promptLabel.setForeground(Color.decode("#333333"));
		promptLabel.setBounds(0, 560, 990, 30);
		this.showJpanel.add(promptLabel);

		JLabel lblNewLabel_4 = new JLabel("到期利率：");
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_4.setForeground(Color.decode("#333333"));
		lblNewLabel_4.setBounds(402, 386, 134, 40);
		this.showJpanel.add(lblNewLabel_4);

		lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lblNewLabel_5.setForeground(Color.decode("#333333"));
		lblNewLabel_5.setBounds(520, 386, 150, 40);
		this.showJpanel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("所得利息：");
		lblNewLabel_6.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_6.setForeground(Color.decode("#333333"));
		lblNewLabel_6.setBounds(700, 386, 135, 40);
		this.showJpanel.add(lblNewLabel_6);

		lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lblNewLabel_7.setForeground(Color.decode("#333333"));
		lblNewLabel_7.setBounds(820, 386, 150, 40);
		this.showJpanel.add(lblNewLabel_7);	
		
		if("1".equals(accBean.getTransChangeNo())){
			accBean.setTransChangeNo("0");
			accBean.setZzAmt(accBean.getBeiZzAmt());
			accBean.setIsAuthorize("");
			accBean.setIsCheckedPic("");
			accBean.setIsSign("");
		}

	}

	// 判断存入金额是否选中
	public boolean ismoney() {
		logger.info("进入判断金额是否选中的方法");
		// 判断存入金额是否选中，如果用户直接在输入框中输入值也可以，此处还未对输入框做判断
		if (!isbutton) {
			// 加入提示语
			promptLabel.setText("请选择存入金额");
			promptLabel.setForeground(Color.red);
			return false;
		} else {
			return true;
		}
	}

	
	/**
	 * button监听事件：利息试算，产品预计利息(24小时)-03510s
	 */
	public void jslx(){
		logger.info("进入利息试算的方法");
		new Thread(){
			@Override
			public void run() {
				Map inter03510 = new HashMap<String, Object>();
				try {
					prossDialog.showDialog();//交易处理框
					AccountTradeCodeAction.transBean.getReqMCM001().setReqBefor("03510");
					inter03510 = IntefaceSendMsg.inter03510(accBean);
					AccountTradeCodeAction.transBean.getReqMCM001().setReqAfter((String)inter03510.get("resCode"), (String)inter03510.get("errMsg"));
					if ("4444".equals(inter03510.get("resCode"))) {
						prossDialog.disposeDialog();//关闭处理框
						logger.error(inter03510.get("errMsg"));
						openMistakeDialog("利息试算失败!");//弹错误框
						return;
					}
					if(!"000000".equals(inter03510.get("resCode"))){
						prossDialog.disposeDialog();//关闭处理框
						logger.error(inter03510.get("errMsg"));
						openMistakeDialog("利息试算失败!");//弹错误框
						return;
					}
					prossDialog.disposeDialog();//关闭处理框	
				} catch (Exception e) {
					prossDialog.disposeDialog();//关闭处理框
					logger.error("调用03510接口异常!");
					openMistakeDialog("利息试算失败!");//弹错误框
					return;
				}
				lblNewLabel_5.setText(accBean.getRate()+"%");
				lblNewLabel_7.setText(accBean.getInte()+"元");
				button10.setText(accBean.getProductName());
				comp.repaint();
			}
		}.start();
	}
	
	/**button监听事件：获取数据*/
	public void button(String startMoneyStr,String productCode,String productCodeName){
		logger.info("进入获取数据的方法");
		if(startMoneyStr!=null&&productCode!=null&&productCodeName!=null){
			String money = startMoneyStr.substring(startMoneyStr.length()-1, startMoneyStr.length()) ;
			if(money.equals("万")){
				String money1=  startMoneyStr.substring(0, startMoneyStr.length()-1);
				accBean.setMoney(money1+"0000.00");//产品金额
			}else{
				accBean.setMoney(money);
			}
			accBean.setProductCode(productCode);//产品代码
			accBean.setProductName(productCodeName);//产品名称
			
			Float floatMoney = Float.valueOf(accBean.getMoney().trim());
			Float cardMoney = Float.valueOf(accBean.getCardAmt());
			if(promptLabel.getText()!= ""){
				promptLabel.setText("");
			}
			if(cardMoney<floatMoney){//选中金额小于银行卡金额
				isMoney = true;
				promptLabel.setText("您的账户余额不足");
				promptLabel.setForeground(Color.red);
			}
		}
	}
	
	/** 返回*/
	public void backTrans() {
		logger.info("进入返回上一步的方法");
		clearTimeText();//清空倒计时
		stopTimer(voiceTimer);//关语音
		closeVoice();//关语音流
		logger.info("返回关联如意产品选择页");
		prossDialog.disposeDialog();
		if(!accBean.getBeiZzAmt().trim().equals(accBean.getZzAmt().trim())){
			accBean.setZzAmt(accBean.getBeiZzAmt());
			if(new BigDecimal(accBean.getBeiZzAmt()).compareTo(new BigDecimal(Property.getProperties().getProperty("acc_open_ckeckIdAmt")))<0){
				accBean.setMoreThanAmt("1");
				accBean.setIsAuthorize("1");
			}else{
				accBean.setMoreThanAmt("0");
				accBean.setIsAuthorize("0");
			}
		}
		openPanel(new AccGuanLianRycProPanel(params));
	}
	
	
	
	/** 确认*/
	public void okTrans(){	
		logger.info("进入确认的方法");
		accBean.setAutoRedpFlag("0");//不自动转存
		// 当用户全部选择之后进入下一个页面
		if(ismoney()==true && poss() && !isMoney){
			clearTimeText();//清空倒计时
			stopTimer(voiceTimer);//关语音
			closeVoice();//关语音流
			if(accBean.getMoney()!=null && accBean.getMoney()!=""){
				BigDecimal money = new BigDecimal(accBean.getMoney().trim());
				BigDecimal zzAmt = new BigDecimal(accBean.getZzAmt().trim());
				BigDecimal zzAmtNew = money;
				accBean.setZzAmt(zzAmtNew.toString());
				int result =zzAmtNew.compareTo(new BigDecimal(Property.getProperties().getProperty("acc_open_ckeckIdAmt")));
				if("".equals(accBean.getTransChangeNo()) || accBean.getTransChangeNo()==null ||"0".equals(accBean.getTransChangeNo())){
					//累计金额是否大于5万
					if(result>=0){
						accBean.setTransChangeNo("1");
						accBean.setMoreThanAmt("0");
						accBean.setIsAuthorize("0");//授权标识
					}else{
						accBean.setTransChangeNo("0");
						accBean.setIsAuthorize("1");//不授权标识
						accBean.setMoreThanAmt("1");
					}
				}
				params.put("transCode", "0021");//跳转标识
				AccountTransFlow.startTransFlow(comp, params);
			}
		}else{
			on_off=true;
		}
	}
	
	/**数据处理*/
	public boolean poss(){
		logger.info("进入数据处理的方法");
		boolean bean = true;
		//开始时间改成核心时间
		try {
			if(accBean.getSvrDate()!=null && accBean.getSvrDate()!=""){
				
				accBean.setCreateTime(accBean.getSvrDate());
				logger.info("核心时间"+accBean.getSvrDate());
			}else{
				logger.error("核心时间获取失败");
				openMistakeDialog("核心时间获取失败");//弹错误框
				bean = false;
			}
		} catch (Exception e) {
			logger.error("核心时间获取失败");
			openMistakeDialog("核心时间获取失败");//弹错误框
			bean = false;
		}
		
		//到期时间计算
//		try {
//			if(accBean.getMonthsUpperXySh()!=null && accBean.getMonthsUpperXySh()!=""){
//				
//				DateTool dt = new DateTool();
//				if(accBean.getMonthsUpperXySh().substring(accBean.getMonthsUpperXySh().length()-1, accBean.getMonthsUpperXySh().length()).equals("D")){
//					String abc = dt.nDaysAfterOneDateString(accBean.getSvrDate(),Integer.parseInt(accBean.getMonthsUpperXySh().replace("D", "")));
//					accBean.setEndTime(abc);
//					logger.info("到期时间"+accBean.getEndTime());
//				}else{
//					String abc = dt.nMonthsAfterOneString(accBean.getSvrDate(), accBean.getMonthsUpperXySh());
//					accBean.setEndTime(abc);
//					logger.info("到期时间"+accBean.getEndTime());
//				}
//			}else{
//				logger.error("到期时间计算失败");
//				openMistakeDialog("到期时间计算失败");//弹错误框
//				bean = false;
//			}
//		} catch (Exception e) {
//			logger.error("到期时间计算失败");
//			openMistakeDialog("到期时间计算失败");//弹错误框
//			bean = false;
//		}
				
		//存期转换数字、单位
		try {
			String str=accBean.getMonthsUpperXySh().toUpperCase();
			Integer n=Integer.parseInt(str.replaceAll("\\D",""));
			String[] acc = new String[]{"","",""};
			if(str.indexOf("D")!=-1){
				acc = new String[] { "D", n+"", n+"天" };
			}else if(str.indexOf("M")!=-1){
				acc = new String[] { "M", n+"", n+"个月"};
			}else if(str.indexOf("Y")!=-1){
				acc = new String[] { "Y", n+"", n+"年" };
			}
			//存期数字
			if(acc[1].length()>1){
				accBean.setMonthsUpper(acc[1]);				
			}else{
				accBean.setMonthsUpper("0"+acc[1]);
			}
			accBean.setDepUnit(acc[0]);//存期单位	
		} catch (Exception e) {
			logger.error("存期转换失败");
			openMistakeDialog("存期转换失败");//弹错误框
			bean = false;
		}
			
		//金额大小写转换
		try {
			if(accBean.getMoney()!=null && accBean.getMoney()!=""){
				
				accBean.setMoneyUpper(NumberZH.change(Double.parseDouble(String.valueOf(accBean.getMoney()))));
			}else{
				logger.error("金额转换失败");
				openMistakeDialog("金额转换失败");//弹错误框
				bean = false;
			}
		} catch (Exception e) {
			logger.error("金额转换失败");
			openMistakeDialog("金额转换失败");//弹错误框
			bean = false;
		}
		return bean;
	}
}
