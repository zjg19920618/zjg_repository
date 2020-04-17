package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import com.boomhope.Bill.Util.NumberZH;
import com.boomhope.Bill.Util.Property;

/**
 * 存单确认信息页面
 * 
 * @author hk 2017-3-15
 */
public class AccountConfirmPanel extends BaseTransPanelNew {
	Logger logger = Logger.getLogger(this.getClass());

	private JTable table;
	private JLabel lblNewLabel_3;//显示图片的框
	private ImageIcon image;//加载签字的图片
	private JPanel panelNew;//加载显示图片的面板
	private JPanel panel;//显示存单信息的面板
	private JLabel label_9;//
	private JLabel label_8;//
	private JLabel label_7;//
	private JLabel label_6;//
	private JLabel label_4;//
	private JLabel label_3;//
	private JLabel label_2;//
	private JLabel label_5;//
	private JLabel label_1;//
	private JLabel label;//
	private Map<Object,Object> map;
	private boolean on_off=true;//用于控制页面跳转的开关
	
	public AccountConfirmPanel(final AccPublicBean transBean,final Map<Object,Object> map) {
		logger.info("存单确认信息页面。。。");
		final Component comp = this;
		this.map = map;
		
		/* 显示时间倒计时 */
		if(delayTimer!=null){
			clearTimeText();
		}
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	logger.info("在意确认信息页面倒计时结束 ");
            	/* 倒计时结束退出交易 */ 
            	delayTimer.stop();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
		// 标题
		JLabel depoLum = new JLabel("整存整取储蓄存款");
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setHorizontalAlignment(0);
		depoLum.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(depoLum);
		// 请确认存单信息
		label = new JLabel("请确认您的存款信息：");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label.setForeground(Color.decode("#412174"));
		label.setBounds(81, 120, 300, 30);
		this.showJpanel.add(label);

		// 卡号
		label_1 = new JLabel("卡号：");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_1.setBounds(81, 160, 72, 30);
		label_1.setForeground(Color.decode("#333333"));
		this.showJpanel.add(label_1);
		// 卡号展示框
		label_5 = new JLabel(transBean.getCardNo());
		label_5.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_5.setBounds(163, 160, 329, 30);
		label_5.setForeground(Color.decode("#333333"));
		this.showJpanel.add(label_5);

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


		// 大写金额展示
		// 将金额转换为大写
		transBean.setMoneyUpper(NumberZH.change(Double.parseDouble(String
				.valueOf(transBean.getMoney()))));
		label_6 = new JLabel(  transBean.getMoneyUpper());
		label_6.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_6.setBounds(243, 200, 500, 30);
		label_6.setForeground(Color.decode("#333333"));
		this.showJpanel.add(label_6);

		// 户名展示
		label_7 = new JLabel(transBean.getIdCardName());
		label_7.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_7.setBounds(680, 160, 138, 30);
		label_7.setForeground(Color.decode("#333333"));
		this.showJpanel.add(label_7);

		// 小写金额展示
		String strMoney="";
		if(transBean.getMoney().contains(".")){
			strMoney=transBean.getMoney();
		}else{
			strMoney=transBean.getMoney()+".00";
		}
		label_8 = new JLabel("CNY" + strMoney);
		label_8.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_8.setBounds(243, 240, 234, 30);
		label_8.setForeground(Color.decode("#333333"));
		this.showJpanel.add(label_8);
		
		String str = "";
		//转存方式
		if("0".equals(transBean.getAutoRedpFlag().trim())){
			str="非自动转存";
		}else{
			str="自动转存";
		}
		//是否自动转存
		label_9 = new JLabel("转存方式："+str);
		label_9.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_9.setBounds(594, 240, 500, 30);
		label_9.setForeground(Color.decode("#333333"));
		this.showJpanel.add(label_9);
		
		// 在意信息展示表
		String[] cloumnNames = { "存入日", "起息日", "到期日", "存期", "利率", "到期利息", "柜员号" };
		//将存期的数字变为中文
		String months="";
		if("01".equals(transBean.getMonthsUpper())){
			months="一";
		}else if("02".equals(transBean.getMonthsUpper())){
			months="二";
		}else if("03".equals(transBean.getMonthsUpper())){
			months="三";
		}else if("05".equals(transBean.getMonthsUpper())){
			months="五";
		}else if("06".equals(transBean.getMonthsUpper())){
			months="六";
		}
		// 当存期的单位是月时加“个”如：3个月，单位是年时则没有如1年
		String measure = "";
		if (transBean.getDepUnit().indexOf("M") != -1) {
			measure = "个月";
		} else if (transBean.getDepUnit().indexOf("Y") != -1) {
			measure = "年";
		}
		transBean.setMonthsUpperStr(months + measure);
		transBean.setCreateTime(transBean.getSvrDate());
		// 将对应的数值放到表格中
		Object[][] data = { { transBean.getCreateTime(),// 存入日
				transBean.getCreateTime(), // 起息日
				transBean.getEndTime(),// 到期日
				transBean.getMonthsUpperStr(),// 存期
				transBean.getRate().trim() + "%", // 利率
				transBean.getInte(),// 到期利息
				GlobalParameter.tellerNo // 柜员号
		} };
		
		panel = new JPanel();
		panel.setBounds(30, 280, 950, 88);
		panel.setLayout(null);
		this.showJpanel.add(panel);

		// 滚动条
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 950, 88);
		panel.add(scrollPane);

		// 将表头和数据添加到展示框中
		table = new JTable(data,cloumnNames);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		header.setPreferredSize(new Dimension(header.getWidth(), 35));
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);
		table.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		table.setRowHeight(50);// 高度
		table.setEnabled(false);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, tcr);
		table.getColumnModel().getColumn(0).setPreferredWidth(85);// 设置宽度
		table.getColumnModel().getColumn(1).setPreferredWidth(85);
		scrollPane.setViewportView(table);
		
		
		//加载签名的图片
		lblNewLabel_3=new JLabel();
		image = new ImageIcon(Property.SIGNATURE_PATH);
		if(image != null){
			image.getImage().flush();
			image = new ImageIcon(Property.SIGNATURE_PATH);
		}
		if(!"1".equals(transBean.getIsSign()) && !"2".equals(transBean.getIsSign())){
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
				if(!"1".equals(transBean.getIsSign()) && !"2".equals(transBean.getIsSign())){
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					signView(map);
				}else{
					askLook();
				}
				
			}
			
		});
		

		// 上一步按钮
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
				OpenDeposit(map);
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
				if("客户签字区".equals(lblNewLabel_3.getText()) || "请先签字再确认".equals(lblNewLabel_3.getText())){
					lblNewLabel_3.setText("请先签字再确认");
					on_off=true;
					return;
				}
				logger.info("确认存单信息后是否打印存单");
				openPrint(transBean, comp, map);
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
				//清空倒计时和语音
				clearTimeText();
				openPanel(new OutputBankCardPanel());
			}
		});
		add(backButton);
		
	}
	/**
	 * 弹出是否打印存单弹框
	 * @param transBean
	 * @param comp
	 * @param params
	 */
	private void openPrint(final AccPublicBean transBean,final Component comp, final Map<Object, Object> params) {
		//开户金额
		Float money = Float.valueOf(transBean.getMoney().trim());
		
		//开户授权超限金额20万
		String amt = Property.getProperties().getProperty("acc_open_tellNoAmt");
		Float accOpenAmt = Float.valueOf(amt);
		if(money>=accOpenAmt){
			logger.info("进入授权");
			clearTimeText();//清空倒计时
			stopTimer(voiceTimer);//关语音
			closeVoice();//关语音流
			if("1".equals(transBean.getIsCheckedPic())){
				transBean.setIsSign("2");
			}
			openPanel(new AccAuthorNoPanel(params));
		}else{
			
			String msg = "";
			if("0".equals(transBean.getAutoRedpFlag())){//非自动转存
				msg = "是否打印存单。是：打印。否：不打印，到期后本息自动转入银行卡。";
			}else{
				msg = "是否打印存单。是：打印；否：不打印。";
			}
			logger.info("是否打印存单确认框");
			openConfirmDialog(msg);
			confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("选择否不打印存单");
					transBean.setCertPrint("0");
					confirmDialog.disposeDialog();
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关语音
					closeVoice();//关语音流
					params.put("transCode", "0015");
					// 选好是否打印存单进入输入密码流程
					AccountTransFlow.startTransFlow(comp, params);
				}
				
			});
			confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("选择打印存单按钮");
					transBean.setCertPrint("1");
					confirmDialog.disposeDialog();
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关语音
					closeVoice();//关语音流
					params.put("transCode", "0015");
					// 选好是否打印存单进入输入密码流程
					AccountTransFlow.startTransFlow(comp, params);
				}
			});
		}
	}
	/**
	 * 上一步按钮，返回到整存整取页面,需要保留数据交易码和返回码
	 */
	private void OpenDeposit(final Map map) {
		// 跳转
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				clearTimeText();
				if(!"1".equals(AccountTradeCodeAction.transBean.getIsCheckedPic())){
					AccountTradeCodeAction.transBean.setIsSign("0");
				}
				if("0".equals(AccountTradeCodeAction.transBean.getTransChangeNo().trim()) ||"2".equals(AccountTradeCodeAction.transBean.getTransChangeNo().trim())){
					if("1".equals(AccountTradeCodeAction.transBean.getIsCheckedPic())){
						logger.info("返回联网核查页面");
						if("0".equals(AccountTradeCodeAction.transBean.getHaveAgentFlag())){
							openPanel(new AccCheckAgentPhotos(map));
						}else{
							openPanel(new AccCheckPhotos(map));
						}
					}else{
						AccountTradeCodeAction.transBean.setZzAmt(AccountTradeCodeAction.transBean.getBeiZzAmt());
						openPanel(new AccDepoLumPanel(AccountTradeCodeAction.transBean));
					}
				}else{
					if(!"1".equals(AccountTradeCodeAction.transBean.getIsCheckedPic())){
						AccountTradeCodeAction.transBean.setIsSign("0");
						AccountTradeCodeAction.transBean.setCameraCount("");
						openPanel(new AccPrintCameraPanel(map));
					}else{
						logger.info("返回联网核查页面");
						if("0".equals(AccountTradeCodeAction.transBean.getHaveAgentFlag())){
							openPanel(new AccCheckAgentPhotos(map));
						}else{
							openPanel(new AccCheckPhotos(map));
						}
					}
				}
			}
		});
	}

	
	//点击查看签名图片
	public void askLook(){
		logger.info("进入查看签名图片方法");
		//设置信息内容消失
		panel.setVisible(false);
		label.setVisible(false);
		label_1.setVisible(false);
		label_2.setVisible(false);
		label_3.setVisible(false);
		label_4.setVisible(false);
		label_5.setVisible(false);
		label_6.setVisible(false);
		label_7.setVisible(false);
		label_8.setVisible(false);
		label_9.setVisible(false);
		lblNewLabel_3.setVisible(false);
			
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
				AccountTradeCodeAction.transBean.setIsSign("");
				signView(map);
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
				label.setVisible(true);
				label_1.setVisible(true);
				label_2.setVisible(true);
				label_3.setVisible(true);
				label_4.setVisible(true);
				label_5.setVisible(true);
				label_6.setVisible(true);
				label_7.setVisible(true);
				label_8.setVisible(true);
				label_9.setVisible(true);
				lblNewLabel_3.setVisible(true);
				
			}
		});
		
	}
	
	//显示签字区
	public void signView(final Map map){
		//清空倒计时
		clearTimeText();
		//跳转到签字页面
		openPanel(new AccSignaturePanel(map));
	}
}
