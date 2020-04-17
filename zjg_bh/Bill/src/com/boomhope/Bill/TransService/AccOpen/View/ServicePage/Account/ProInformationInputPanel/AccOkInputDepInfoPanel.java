package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.Bean.AccPublicBean;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author.AccAuthorNoPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author.AccCheckAgentPhotos;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author.AccCheckPhotos;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author.AccPrintCameraPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author.AccSignaturePanel;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTransFlow;
import com.boomhope.Bill.Util.Property;

/***
 * 确认存款信息界面Panel
 * 
 * @author zjg
 *
 */
public class AccOkInputDepInfoPanel extends BaseTransPanelNew {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccOkInputDepInfoPanel.class);

	private JTable table_2;

	private Map<Object, Object> params;
	private AccPublicBean accBean;
	private Component comp;
	private JLabel lblNewLabel_3;
	private ImageIcon image;
	private JLabel label_cardNo;//
	private JLabel label_cardNo_1;//
	private JLabel label_1;
	private JLabel label_2;//
	private JLabel label_3;//
	private JLabel label_4;//
	private JLabel label_5;//
	private JPanel panel;//
	private JLabel lblNewLabel_1;//
	private JLabel lblNewLabel_2;//
	private JLabel lblNewLabel_4;//
	private JLabel label_9;
	private JPanel panelNew;//
	private boolean on_off=true;
	
	public AccOkInputDepInfoPanel(final Map<Object, Object> params) {
		logger.info("进入确认存款信息界面");
		
		comp = this;
		this.params = params;
		accBean = AccountTradeCodeAction.transBean;

		/* 显示时间倒计时 */
		if(delayTimer!=null){
			clearTimeText();
		}
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	logger.info("确认存款信息界面倒计时结束");
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
						excuteVoice("voice/okinputdepinfo.wav");// 执行语音

					}
				});
		voiceTimer.start();

		JLabel label = new JLabel(accBean.getProductName());
		label.setFont(new Font("微软雅黑", Font.BOLD, 40));
		label.setForeground(Color.decode("#412174"));
		label.setHorizontalAlignment(0);
		label.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(label);
		
		// 请确认存单信息
		label_1 = new JLabel("请确认您的存款信息：");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_1.setForeground(Color.decode("#412174"));
		label_1.setBounds(81, 120, 300, 30);
		this.showJpanel.add(label_1);

		// 卡号
		label_cardNo = new JLabel("卡号：");
		label_cardNo.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_cardNo.setBounds(81, 160, 72, 30);
		label_cardNo.setForeground(Color.decode("#333333"));
		this.showJpanel.add(label_cardNo);
		// 卡号展示框
		label_cardNo_1 = new JLabel(accBean.getCardNo());
		label_cardNo_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_cardNo_1.setBounds(163, 160, 329, 30);
		label_cardNo_1.setForeground(Color.decode("#333333"));
		this.showJpanel.add(label_cardNo_1);
		
		// 金额（大写）
		label_2 = new JLabel("金额（大写）：");
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_2.setBounds(81, 200, 176, 30);
		label_2.setForeground(Color.decode("#333333"));
		this.showJpanel.add(label_2);

		// 户名：
		label_3 = new JLabel("户名：");
		label_3.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_3.setBounds(594, 160, 83, 30);
		label_3.setForeground(Color.decode("#333333"));
		this.showJpanel.add(label_3);

		// 金额（小写）
		label_4 = new JLabel("金额（小写）：");
		label_4.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_4.setBounds(81, 240, 184, 30);
		label_4.setForeground(Color.decode("#333333"));
		this.showJpanel.add(label_4);
		
		//是否自动转存
		label_9 = new JLabel("转存方式：非自动转存");
		label_9.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_9.setBounds(594, 240, 500, 30);
		label_9.setForeground(Color.decode("#333333"));
		this.showJpanel.add(label_9);
		
		// 结息利率提示
		if(accBean.getProductCode().startsWith("AX")){
			
			label_5 = new JLabel(accBean.getPrinterL52Str());
			label_5.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			label_5.setBounds(115, 362, 600, 30);
			label_5.setForeground(Color.decode("#333333"));
			this.showJpanel.add(label_5);
		}
		
//			String[] columnNames = { "存入日", "起息日", "到期日", "存期", "利率", "到期利息", "柜员号" };
//			Object[][] data = { { accBean.getCreateTime(), accBean.getCreateTime(),
//					accBean.getEndTime(), accBean.getMonthsUpperStr(),
//					accBean.getRate() + "%", accBean.getInte() + "元",
//					GlobalParameter.tellerNo } };
		
		String[] columnNames ;
		Object[][] data ;
		if(accBean.getProductCode().startsWith("JX")){
			
			columnNames = new String[6];
			data = new Object[1][6];
			columnNames[0]="存入日";
			columnNames[1]="起息日";
			columnNames[2]="到期日";
			columnNames[3]="存期";
			columnNames[4]="利率";
			columnNames[5]="柜员号";
			data[0][0] = accBean.getCreateTime();
			data[0][1] = accBean.getCreateTime();
			data[0][2] = accBean.getEndTime();
			data[0][3] = accBean.getJxRyjDepTem()+"天";
			data[0][4] = accBean.getRate() + "%";
			data[0][5] = GlobalParameter.tellerNo;
			
		}else if(accBean.getProductCode().startsWith("RJ")){
			
			columnNames = new String[7];
			data = new Object[1][7];
			columnNames[0]="存入日";
			columnNames[1]="起息日";
			columnNames[2]="到期日";
			columnNames[3]="存期";
			columnNames[4]="利率";
			columnNames[5]="到期利息";
			columnNames[6]="柜员号";
			data[0][0] = accBean.getCreateTime();
			data[0][1] = accBean.getCreateTime();
			data[0][2] = accBean.getEndTime();
			data[0][3] = accBean.getJxRyjDepTem()+"天";
			data[0][4] = accBean.getRate() + "%";
			data[0][5] = accBean.getInte();
			data[0][6] = GlobalParameter.tellerNo;
			
		}else if(accBean.getProductCode().startsWith("AX")){
			
			columnNames = new String[7];
			data = new Object[1][7];
			columnNames[0]="存入日";
			columnNames[1]="起息日";
			columnNames[2]="到期日";
			columnNames[3]="存期";
			columnNames[4]="利率";
			columnNames[5]="到期利息";
			columnNames[6]="柜员号";
			data[0][0] = accBean.getCreateTime();
			data[0][1] = accBean.getCreateTime();
			data[0][2] = accBean.getEndTime();
			data[0][3] = accBean.getMonthsUpperStr();
			data[0][4] = "详见提示";
			data[0][5] = accBean.getInte();
			data[0][6] = GlobalParameter.tellerNo;
			
		}else{
			
			columnNames = new String[7];
			data = new Object[1][7];
			columnNames[0]="存入日";
			columnNames[1]="起息日";
			columnNames[2]="到期日";
			columnNames[3]="存期";
			columnNames[4]="利率";
			columnNames[5]="到期利息";
			columnNames[6]="柜员号";
			data[0][0] = accBean.getCreateTime();
			data[0][1] = accBean.getCreateTime();
			data[0][2] = accBean.getEndTime();
			data[0][3] = accBean.getMonthsUpperStr();
			data[0][4] = accBean.getRate() + "%";
			data[0][5] = accBean.getInte();
			data[0][6] = GlobalParameter.tellerNo;
		}
		
		panel = new JPanel();
		panel.setBounds(30, 280, 950, 88);
		panel.setLayout(null);
		this.showJpanel.add(panel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 950, 88);
		panel.add(scrollPane);

		table_2 = new JTable(data, columnNames);
		JTableHeader header = table_2.getTableHeader();
		header.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		header.setPreferredSize(new Dimension(header.getWidth(), 35));
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);
		table_2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		table_2.setRowHeight(50);// 高度
		table_2.setEnabled(false);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(JLabel.CENTER);
		table_2.setDefaultRenderer(Object.class, tcr);
		table_2.getColumnModel().getColumn(0).setPreferredWidth(85);// 设置款度
		table_2.getColumnModel().getColumn(1).setPreferredWidth(85);
		scrollPane.setViewportView(table_2);

		lblNewLabel_1 = new JLabel("人民币" + accBean.getMoneyUpper());
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_1.setBounds(243, 200, 500, 30);
		lblNewLabel_1.setForeground(Color.decode("#333333"));
		this.showJpanel.add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel(accBean.getIdCardName());
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_2.setBounds(680, 160, 138, 30);
		lblNewLabel_2.setForeground(Color.decode("#333333"));
		this.showJpanel.add(lblNewLabel_2);

		String strMoney="";
		if(accBean.getMoney().contains(".00")){
			strMoney=accBean.getMoney();
		}else{
			strMoney=accBean.getMoney()+".00";
		}
		lblNewLabel_4 = new JLabel("CNY" + strMoney);
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_4.setBounds(243, 240, 234, 30);
		lblNewLabel_4.setForeground(Color.decode("#333333"));
		this.showJpanel.add(lblNewLabel_4);
		

		//加载签名的图片
		lblNewLabel_3=new JLabel();
		image = new ImageIcon(Property.SIGNATURE_PATH);
		if(image != null){
			image.getImage().flush();
			image = new ImageIcon(Property.SIGNATURE_PATH);
		}
		if(!"1".equals(accBean.getIsSign()) && !"2".equals(accBean.getIsSign())){
			lblNewLabel_3.setText("客户签字区");
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 30));
			lblNewLabel_3.setForeground(Color.blue);
		}else{
			//使图片自适应窗格大小
			image.setImage(image.getImage().getScaledInstance(780, 200, Image.SCALE_DEFAULT));
			lblNewLabel_3.setIcon(image);
		}
		lblNewLabel_3.setBounds(115, 390, 780, 195);
		lblNewLabel_3.setBorder(BorderFactory.createLineBorder(Color.blue));
		this.showJpanel.add(lblNewLabel_3);
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!"1".equals(accBean.getIsSign()) && !"2".equals(accBean.getIsSign())){
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					signView();
				}else{
					askLook(accBean);
				}
				
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
				/* 处理上一页 */
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
				logger.info("点击确认");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				if("客户签字区".equals(lblNewLabel_3.getText()) || "请先签字后再确认".equals(lblNewLabel_3.getText())){
					lblNewLabel_3.setText("请先签字后再确认");
					on_off=true;
					return;
				}
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
				logger.info("点击返回");
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
	}

	/**
	 * 确认
	 * */
	public void okTrans() {
		logger.info("进入确认方法");
		if("1".equals(AccountTradeCodeAction.transBean.getIsCheckedPic())){
			accBean.setIsSign("2");
			logger.info("授权时直接进入输入授权号码页面");
			clearTimeText();//清空倒计时
			stopTimer(voiceTimer);//关语音
			closeVoice();//关语音流
			openPanel(new AccAuthorNoPanel(params));
		}else{
			logger.info("直接进入协议书页面");
			clearTimeText();//清空倒计时
			stopTimer(voiceTimer);//关语音
			closeVoice();//关语音流
			openPanel(new AccAgreementPagePanel(params));
		}

	}

	/** 返回 */
	public void backTrans() {
		logger.info("返回上一步的页面方法");
		stopTimer(voiceTimer);// 关语音
		closeVoice();// 关语音流
		
		if(!"1".equals(accBean.getIsCheckedPic())){
			accBean.setIsSign("0");
		}
		//通过是否有代理人返回上一页
		if("0".equals(accBean.getTransChangeNo().trim())||"2".equals(accBean.getTransChangeNo().trim())){
			if("1".equals(accBean.getIsCheckedPic())){
				if("LZ".equals(params.get("PRODUCT_CODE"))
						|| "LT".equals(params.get("PRODUCT_CODE"))
						|| "AX".equals(params.get("PRODUCT_CODE"))){
					logger.info("返回收益人录入信息页");
					openPanel(new AccSyrPagesPanel(params));
				}else{
					logger.info("返回联网核查页面");
					if("0".equals(AccountTradeCodeAction.transBean.getHaveAgentFlag())){
						openPanel(new AccCheckAgentPhotos(params));
					}else{
						openPanel(new AccCheckPhotos(params));
					}
				}
			}else{
				if("LZ".equals(params.get("PRODUCT_CODE"))
						|| "LT".equals(params.get("PRODUCT_CODE"))
						|| "AX".equals(params.get("PRODUCT_CODE"))){
					logger.info("返回收益人录入信息页");
					clearTimeText();//清空倒计时
					openPanel(new AccSyrPagesPanel(params));
				}else if ("RJ".equals(params.get("PRODUCT_CODE"))) {
					logger.info("返回如意+录入信息页");
					clearTimeText();//清空倒计时
					accBean.setZzAmt(accBean.getBeiZzAmt().trim());
					openPanel(new AccRyjInputPanel(params));
				} else if ("JX".equals(params.get("PRODUCT_CODE"))) {
					logger.info("返回积享存录入信息页");
					clearTimeText();//清空倒计时
					accBean.setZzAmt(accBean.getBeiZzAmt().trim());
					openPanel(new AccJxcInputPanel(params));
				}else{
					logger.info("返回信息录入页面");
					clearTimeText();//清空倒计时
					accBean.setZzAmt(accBean.getBeiZzAmt().trim());
					openPanel(new AccProInputPanel(params));
				}
			}
		}else if("1".equals(accBean.getTransChangeNo().trim())){
			clearTimeText();//清空倒计时
			if("LZ".equals(params.get("PRODUCT_CODE"))
					|| "LT".equals(params.get("PRODUCT_CODE"))
					|| "AX".equals(params.get("PRODUCT_CODE"))){
				logger.info("返回收益人录入信息页");
				openPanel(new AccSyrPagesPanel(params));
			}else{
				if("1".equals(accBean.getIsCheckedPic())){
					logger.info("返回联网核查页面");
					if("0".equals(AccountTradeCodeAction.transBean.getHaveAgentFlag())){
						openPanel(new AccCheckAgentPhotos(params));
					}else{
						openPanel(new AccCheckPhotos(params));
					}
				}else{
					openPanel(new AccPrintCameraPanel(params));
				}
			}
		}
	}

	//点击查看签名图片
	public void askLook(final AccPublicBean transBean){
		logger.info("进入查看签名图片方法");
		//设置信息内容消失
		panel.setVisible(false);
		lblNewLabel_1.setVisible(false);
		lblNewLabel_2.setVisible(false);
		label_2.setVisible(false);
		label_3.setVisible(false);
		label_4.setVisible(false);
		lblNewLabel_4.setVisible(false);
		lblNewLabel_3.setVisible(false);
		label_cardNo_1.setVisible(false);
		label_cardNo.setVisible(false);
		label_9.setVisible(false);
		label_1.setVisible(false);
		if(label_5!=null){
			label_5.setVisible(false);
		}
			
		//创建显示签名图片的面板
		panelNew = new JPanel();
		panelNew.setBounds(103, 120, 783, 420);
		panelNew.setLayout(null);
		panelNew.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		this.showJpanel.add(panelNew);
		panelNew.setVisible(true);
		
		
		//使图片自适应窗格大小
		image.setImage(image.getImage().getScaledInstance(780, 350, Image.SCALE_DEFAULT));
		//签名的图片
		JLabel imageLabel = new JLabel(image);
		imageLabel.setBounds(2, 1, 780, 350);
		imageLabel.setBorder(BorderFactory.createLineBorder(Color.decode("#f7f2ff")));
		panelNew.add(imageLabel);
		
		//重新签名的按钮
		JLabel reSignButton = new JLabel(new ImageIcon("pic/reSign.png"));
		panelNew.add(reSignButton);
		reSignButton.setBounds(0, 360, 200, 50);
		reSignButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				transBean.setIsSign("");
				signView();
			}
		});
		
		//取消按钮
		JLabel lookButton = new JLabel(new ImageIcon("pic/cancel_icon.png"));
		panelNew.add(lookButton);
		lookButton.setBounds(580, 360, 200, 50);
		lookButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				panelNew.setVisible(false);
				panel.setVisible(true);
				//使图片自适应窗格大小
				image.setImage(image.getImage().getScaledInstance(780, 200, Image.SCALE_DEFAULT));
				lblNewLabel_1.setVisible(true);
				lblNewLabel_2.setVisible(true);
				label_2.setVisible(true);
				label_3.setVisible(true);
				label_4.setVisible(true);
				lblNewLabel_4.setVisible(true);
				lblNewLabel_3.setVisible(true);
				label_cardNo_1.setVisible(true);
				label_cardNo.setVisible(true);
				label_9.setVisible(true);
				label_1.setVisible(true);
				if(label_5!=null){
					label_5.setVisible(true);
				}
			}
		});
		
	}
	
	//显示签字区
	public void signView(){
		//清空倒计时
		clearTimeText();
		//跳转到签字页面
		openPanel(new AccSignaturePanel(params));
	}
	
}