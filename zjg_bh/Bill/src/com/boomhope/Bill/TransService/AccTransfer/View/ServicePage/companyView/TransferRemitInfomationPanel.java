package com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.companyView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PaymentTermsMsgBean;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.Interface.InterfaceSendMsg;
import com.boomhope.Bill.Util.AccGetKeyBoredPanel;
import com.boomhope.Bill.Util.BankInfo;
import com.boomhope.Bill.Util.City;
import com.boomhope.Bill.Util.ComboBox;
import com.boomhope.Bill.Util.GetBankInfo;
import com.boomhope.Bill.Util.GetBankStress;
import com.boomhope.Bill.Util.Province;
import com.boomhope.Bill.Util.SoftKeyBoardPopups4;
import com.boomhope.Bill.Util.SoftKeyBoardPopups5;
import com.boomhope.Bill.Util.StringUtils;
import com.boomhope.Bill.Util.TextMyDocument;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;

/**
 * 汇款信息录入界面
 * 
 * @author hao
 *
 */
public class TransferRemitInfomationPanel extends BaseTransPanelNew {

	static Logger logger = Logger.getLogger(TransferRemitInfomationPanel.class);
	public static int nowpage = 1;// 银行信息页数
	private static final long serialVersionUID = 1L;
	private JTextField textField;// 金额输入框
	private JTextField textField_2;// 转入姓名框
	private JTextField textField_3;// 开户行框
	private JTextField textField_4;// 转入卡号输入框
	private AccGetKeyBoredPanel keyPopup;// 输入卡号键盘
	private SoftKeyBoardPopups5 keyPopup1;// 输入转出户名键盘
	private SoftKeyBoardPopups4 keyPopup2;// 输入汇款金额键盘
	private SoftKeyBoardPopups5 keyPopup3;// 输入地区键盘
	private JLabel submitBtn;//上一步
	private JLabel backBtn;//确认
	private JLabel utilButton;//返回
	private JPanel passwordPanel;
	private JLayeredPane jpanel;//信息展示底板
	private JList useList;//用途列表
	private JTextField comboBox;//用途输入框
	SoftKeyBoardPopups5 keyPop4;
	private GetBankStress stress = new GetBankStress();// 读取省市县其他
	private GetBankInfo info = new GetBankInfo();// 获取银行信息
	List<Map<String, Object>> provinces = new ArrayList<Map<String, Object>>();// 省份信息
	List<Map<String, Object>> citys = new ArrayList<Map<String, Object>>();// 城市信息
	DefaultListModel<String> model = new DefaultListModel<String>();
	List<Map<String, Object>> infos = new ArrayList<Map<String, Object>>();;// 银行信息
	private List<PaymentTermsMsgBean> PayList = null;
	public String [] OtherBank;// 跨行
	private String[] summText;// 资金用途
	JPanel panel_4;//地区面板
	private JTextField textField_1;//地区输入框
	boolean isCityCheck=false;
	boolean isProCheck=false;
	private PublicAccTransferBean transferMoneyBean;
	private JLabel jlabelNameInfo;//转账客户信息查询
	private JPanel jpanelInfo;//转账客户信息列表
	private JList jlist;//详细信息列表
	private int pageNo;//第几页
	private int pageCount;//页数
	private JLabel label;//汇款金额
	private JLabel label_1;//汇款用途
	private JLabel label_5;//开户户名
	private JLabel label_6;//开户行名
	private JScrollPane js;//名册展示滚动条
	private int count=10;//收款人名册每页条数
	private JList list;//开户行列表
	private JLabel uselabel;//用途选择方式
	private JPanel usejpanel;//用途列表面板 
	private Component comp;//当前页面
	private JLabel labelNames;//名册无信息提示
	private boolean on_off=true;//控制点击的开关
	
	/**
	 * 初始化
	 */
	@SuppressWarnings("unchecked")
	public TransferRemitInfomationPanel(final PublicAccTransferBean transferBean) {
		comp = this;
		
		PayList = (List<PaymentTermsMsgBean>) transferBean.getParams().get("PayList");
		
		logger.info("进入汇款信息页面");
		transferMoneyBean = transferBean;
		final Component comp = this;
		
		// 设置倒计时
		this.showTimeText(delaySecondLongestTime);
		delayTimer = new Timer(delaySecondLongestTime * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* 倒计时结束退出交易 */
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！", "","");
			}
		});
		delayTimer.start();
		// 键盘面板
		passwordPanel = new JPanel();
		passwordPanel.setBackground(Color.WHITE);
		passwordPanel.setPreferredSize(new Dimension(202, 30));
		passwordPanel.setLayout(new BorderLayout());
		passwordPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel);
		
		jpanel=new JLayeredPane();
		jpanel.setBounds(5,0,1000,550);
		jpanel.setOpaque(isOpaque());
		this.showJpanel.add(jpanel);
		jpanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("转出账号：");
		lblNewLabel.setBounds(30, 90, 130, 30);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
		jpanel.add(lblNewLabel);

		JLabel label_2 = new JLabel("转出户名：");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setFont(new Font("微软雅黑", Font.BOLD, 24));
		label_2.setBounds(30, 140, 130, 30);
		jpanel.add(label_2);

		JLabel label_3 = new JLabel("开 户 行 ：");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setFont(new Font("微软雅黑", Font.BOLD, 24));
		label_3.setBounds(30, 190, 130, 30);
		jpanel.add(label_3);
		
		
	
		// 转出账号
		JLabel lblNewLabel_1 = new JLabel(transferMoneyBean.getChuZhangCardNo());
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_1.setBounds(160, 91, 840, 30);
		jpanel.add(lblNewLabel_1);

		// 转出姓名
		JLabel lblsadad = new JLabel(transferMoneyBean.getChuZhangcardName());
		lblsadad.setHorizontalAlignment(SwingConstants.LEFT);
		lblsadad.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblsadad.setBounds(160, 141, 840, 30);
		jpanel.add(lblsadad);
		
		// 开户行
		JLabel label_8 = new JLabel(transferMoneyBean.getPayHbrInstName());
		label_8.setHorizontalAlignment(SwingConstants.LEFT);
		label_8.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_8.setBounds(160, 191, 840, 30);
		jpanel.add(label_8);

		JLabel labelLine_1=new JLabel("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		labelLine_1.setBounds(40,240,960,10);
		labelLine_1.setFont(new Font("", Font.PLAIN, 24));
		jpanel.add(labelLine_1);
		
		JLabel label_4 = new JLabel("转入账号：");
		label_4.setBounds(30, 290, 130, 30);
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setFont(new Font("微软雅黑", Font.BOLD, 24));
		jpanel.add(label_4);

		label_5 = new JLabel("转入户名：");
		label_5.setBounds(30, 340, 130, 30);
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setFont(new Font("微软雅黑", Font.BOLD, 24));
		jpanel.add(label_5);

		label_6 = new JLabel("开 户 行 ：");
		label_6.setBounds(30, 390, 130, 30);
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setFont(new Font("微软雅黑", Font.BOLD, 24));
		jpanel.add(label_6);
		
		//查询曾经转账客户信息
		jlabelNameInfo = new JLabel(new ImageIcon("pic/456.png"));
		jlabelNameInfo.setBounds(770, 290, 195, 40);
		jlabelNameInfo.setBackground(Color.BLUE);
		jlabelNameInfo.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		jpanel.add(jlabelNameInfo,JLayeredPane.DEFAULT_LAYER);
		jlabelNameInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(jpanelInfo.isVisible()){
					jpanelInfo.setVisible(false);
					
					jpanel.setVisible(true);
					submitBtn.setVisible(true);
					backBtn.setVisible(true);
					utilButton.setVisible(true);
					
				}else{
					ruZhangName();				
				}
			}
		});
		
		//展示客户信息的列表
		jpanelInfo = new JPanel();
		jpanelInfo.setBounds(15, 15, 980, 580);
		jpanelInfo.setBackground(Color.decode("#f7f2ff"));
		jpanelInfo.setVisible(false);
		jpanelInfo.setLayout(null);
		this.showJpanel.add(jpanelInfo);
		
		
		//信息展示格式
		JLabel infoWays = new JLabel("  账号--户名--开户行名");
		infoWays.setFont(new Font("微软雅黑", Font.BOLD, 24));
		infoWays.setHorizontalAlignment(SwingConstants.LEFT);
		infoWays.setOpaque(true);
		infoWays.setOpaque(isOpaque());
		infoWays.setBounds(30, 20, 980, 30);
		jpanelInfo.add(infoWays);
		
		//滚动条
		js = new JScrollPane();
		js.setBounds(30, 60, 940, 450);
		js.setVisible(true);
		jpanelInfo.add(js);
		
		//列表 
		jlist = new JList<>();
		jlist.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		jlist.setVisibleRowCount(count);
		jlist.setFixedCellHeight(45);
		js.getViewport().setView(jlist);
		
		//返回 
		JLabel exit = new JLabel(new ImageIcon("pic/enew.png"));
		exit.setBounds(850,530, 150, 50);
		jpanelInfo.add(exit);
		exit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				jpanelInfo.setVisible(false);
				labelNames.setVisible(false);
				
				jpanel.setVisible(true);
				submitBtn.setVisible(true);
				backBtn.setVisible(true);
				utilButton.setVisible(true);
			}
		});
		
		//当删除所有收款人信息后显示提示
		labelNames = new JLabel("已经没有收款人信息了");
		labelNames.setFont(new Font("微软雅黑",Font.BOLD,40));
		labelNames.setHorizontalAlignment(SwingConstants.CENTER);
		labelNames.setBounds(0,250,980,50);
		labelNames.setVisible(false);
		jpanelInfo.add(labelNames);
		
		//上一页
		JLabel upLabel = new JLabel(new ImageIcon("pic/up_new.png"));
		upLabel.setBounds(300, 530, 150,50);
		jpanelInfo.add(upLabel);
		upLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(pageNo>1){
					pageNo-=1;
					showInfo();
				}else{
					openMistakeDialog("已经没有更多信息了");
				}
			}
		});
		
		//下一页
		JLabel downLabel = new JLabel(new ImageIcon("pic/next_new.png"));
		downLabel.setBounds(550,530,150, 50);
		jpanelInfo.add(downLabel);
		downLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(pageNo<pageCount){
					pageNo+=1;
					showInfo();
				}else{
					openMistakeDialog("已经没有更多信息了");
				}
			}
		});	
		
		//删除
		JLabel deleteLabel = new JLabel(new ImageIcon("pic/delete.png"));
		deleteLabel.setBounds(750, 5, 150, 50);
		jpanelInfo.add(deleteLabel);
		deleteLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!js.isVisible()){
					openMistakeDialog("已经没有信息可选，请选择退出。");
					return;
				}
				if(jlist.getSelectedValue()!=null && !"".equals(jlist.getSelectedValue())){
					deleteInfo(transferBean);
				}else{
					openMistakeDialog("请先选择收款人信息");
				}
			}
		});
		
		//确定
		JLabel sureLabel = new JLabel(new ImageIcon("pic/sure.png"));
		sureLabel.setBounds(0, 530, 150, 50);
		jpanelInfo.add(sureLabel);
		sureLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(jlist.getSelectedValue()!=null && !"".equals(jlist.getSelectedValue())){
					chooseInfo(comp,transferBean);
				}else{
					openMistakeDialog("请先选择收款人信息");
				}
				
			}
		});
		
		//转账客户记录下拉列表事件
		jlist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.getClickCount()==2){
					chooseInfo(comp,transferBean);
				}
			}
		});
		
		
		// 卡号输入框
		textField_4 = new JTextField();
		textField_4.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		textField_4.setColumns(10);
		textField_4.setBounds(160, 290, 600, 40);
		jpanel.add(textField_4,JLayeredPane.DEFAULT_LAYER);
		textField_4.setBorder(BorderFactory.createLineBorder(Color.black));
		//判断收款人卡号值是否为空，不为空则直接赋值
		if(transferMoneyBean.getRuZhangCardNo()!=null && !"".equals(transferMoneyBean.getRuZhangCardNo())){
			textField_4.setText(transferMoneyBean.getRuZhangCardNo());
		}
		keyPopup=new AccGetKeyBoredPanel(textField_4);
		keyPopup.addPopupMenuListener(new PopupMenuListener() {
			
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				
			}
			
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				checkPayeeAcctNo(comp,transferBean);
			}
			
			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				
				
			}
		});
		this.showJpanel.add(keyPopup);
		
		//收款方户名
		textField_2 = new JTextField();
		textField_2.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		textField_2.setColumns(10);
		textField_2.setBorder(BorderFactory.createLineBorder(Color.black));
		textField_2.setBounds(160, 340, 810, 40);
		textField_2.setDocument(new TextMyDocument(50));
		jpanel.add(textField_2,JLayeredPane.DEFAULT_LAYER);
		//判断收款方户名是否为空，若来为空，则直接赋值
		if(transferMoneyBean.getRuZhangcardName()!=null && !"".equals(transferMoneyBean.getRuZhangcardName())){
			textField_2.setText(transferMoneyBean.getRuZhangcardName());
		}
		textField_2.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				
				extraName();
			}

		});
		keyPopup1=new SoftKeyBoardPopups5(textField_2,50);
		this.showJpanel.add(keyPopup1);

		
		//收款方行名
		textField_3 = new JTextField();
		textField_3.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		textField_3.setColumns(10);
		textField_3.setBorder(BorderFactory.createLineBorder(Color.black));
		textField_3.setBounds(160, 390, 810, 40);
		//若收款方第行名不为空，则直接赋值
		if(transferMoneyBean.getRecvBankName()!=null && !"".equals(transferMoneyBean.getRecvBankName())){
			textField_3.setText(transferMoneyBean.getRecvBankName());
		}
		
		jpanel.add(textField_3,JLayeredPane.DEFAULT_LAYER);
		
		JLabel labelLine_2=new JLabel("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		labelLine_2.setBounds(40,440,960,10);
		labelLine_2.setFont(new Font("", Font.PLAIN, 24));
		jpanel.add(labelLine_2);
		
		// 开户行面板
		panel_4 = new JPanel();
		panel_4.setBounds(15, 15, 980, 580);
		panel_4.setBackground(Color.decode("#f7f2ff"));
		this.showJpanel.add(panel_4);
		panel_4.setVisible(false);
		panel_4.setLayout(null);
		//省、直辖市
		JLabel pro=new JLabel("省/直辖市 :");
		pro.setBounds(36, 30,200, 44);
		pro.setFont(new Font("微软雅黑", Font.PLAIN,24));
		panel_4.add(pro);
		//城市
		
		JLabel city=new JLabel("城 市 :");
		city.setBounds(580, 30, 150, 44);
		city.setFont(new Font("微软雅黑", Font.PLAIN,24));
		panel_4.add(city);
		//关键字
		JLabel word=new JLabel("关  键  字 :");
		word.setBounds(36, 90, 200, 44);
		word.setFont(new Font("微软雅黑", Font.PLAIN,24));
		panel_4.add(word);
		// 省份下拉
		provinces = stress.getProvince();
		final ComboBox comboBox_1 = new ComboBox();
		comboBox_1.setElements(provinces);
		comboBox_1.setFont(new Font("微软雅黑", Font.PLAIN,24));
		comboBox_1.setBounds(170, 30, 300, 44);
		comboBox_1.setSelectedIndex(0);
		panel_4.add(comboBox_1);

		// 市下拉
		citys=stress.getCity("1");
		final ComboBox comboBox_2 = new ComboBox();
		comboBox_2.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		comboBox_2.setBounds(664, 30, 300, 44);
		comboBox_2.setElements(citys);
		comboBox_2.setSelectedIndex(0);
		panel_4.add(comboBox_2);

		// 监听省份下拉框
		comboBox_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				extract(comboBox_1, comboBox_2);
			}

			
		});

		//监听卡号输入框
		textField_4.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				extraNo();
			}
		});
		
		

		list = new JList();
		list.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		list.setBounds(36, 150, 930,370);
		list.setFixedCellWidth(900);
		list.setFixedCellHeight(30);
		panel_4.add(list);
		
		//上一页
		JLabel up = new JLabel(new ImageIcon("pic/up_new.png"));
		up.setForeground(Color.blue);
		up.setBounds(300, 530, 150,50);
		panel_4.add(up);
		up.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (nowpage <= 1) {
					openMistakeDialog("已经没有更多信息了");
					return;
				}
				nowpage--;
				putInfos(infos);
			}
		});
		
		//下一页
		JLabel next = new JLabel(new ImageIcon("pic/next_new.png"));
		next.setForeground(Color.blue);
		next.setBounds(550,530,150, 50);
		panel_4.add(next);
		next.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (nowpage >= (infos.size() %13 == 0 ? infos.size() / 13: infos.size() / 13 + 1)) {
					openMistakeDialog("已经没有更多信息了");
					return;
				}
				nowpage++;
				putInfos(infos);
			}
		});	
		//退出 
		JLabel exit1 = new JLabel(new ImageIcon("pic/enew.png"));
		exit1.setFont(new Font("微软雅黑", Font.BOLD, 25));
		exit1.setBounds(850,530, 150, 50);
		exit1.setForeground(Color.blue);
		exit1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				panel_4.setVisible(false);
				submitBtn.setVisible(true);
				backBtn.setVisible(true);
				utilButton.setVisible(true);
				jpanel.setVisible(true);
			}

		});
		panel_4.add(exit1);
		//确定
		JLabel sureLabel1 = new JLabel(new ImageIcon("pic/sure.png"));
		sureLabel1.setFont(new Font("微软雅黑", Font.BOLD, 25));
		sureLabel1.setBounds(0, 530, 150, 50);
		sureLabel1.setForeground(Color.blue);
		sureLabel1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(list.getSelectedValue()!=null && !"".equals(list.getSelectedValue())){
								extraBank(list);
						}else{
							openMistakeDialog("请先选择银行名称");
						}
						
					}
		});
		
		//添加开户行名称双击选择
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.getClickCount()==2){
					logger.info("双击选择开户行名称");
					extraBank(list);
				}
			}
		});
		
		panel_4.add(sureLabel1);
		textField_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				panel_4.setVisible(true);
				submitBtn.setVisible(false);
				backBtn.setVisible(false);
				utilButton.setVisible(false);
				extraArea(jpanel, comboBox_1, comboBox_2);
			}
			
		});
		label = new JLabel("汇款金额：");
		label.setBounds(30, 490, 130, 40);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		jpanel.add(label);

		
	
		//地区输入框
		textField_1 = new JTextField();
		textField_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		textField_1.setBounds(170, 90, 300, 44);
		textField_1.setColumns(10);
		textField_1.setBorder(BorderFactory.createLineBorder(Color.black));
		textField_1.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				scanBill4();
				textField_1.setBorder(BorderFactory.createLineBorder(Color.red));
			}
		});
		panel_4.add(textField_1);
		keyPopup3=new SoftKeyBoardPopups5(textField_1,50);
		this.showJpanel.add(keyPopup3);
		
		//搜索
		JLabel lblNewLabel_9 = new JLabel(new ImageIcon("pic/select_new.png"),0);
		lblNewLabel_9.setBounds(510, 90, 100, 44);
		lblNewLabel_9.setOpaque(true);
		lblNewLabel_9.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				select( comboBox_2,comboBox_1);
			}
		});
		panel_4.add(lblNewLabel_9);
		// 金额输入框
		textField = new JTextField();
		textField.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		textField.setBounds(160, 490, 300, 42);
		textField.setBorder(BorderFactory.createLineBorder(Color.black));
		jpanel.add(textField);
		textField.setColumns(10);
		//判断金额是否为空，如果不为空，则直接赋值
		if(transferMoneyBean.getRemitAmt()!=null && !"".equals(transferMoneyBean.getRemitAmt())){
			textField.setText(transferMoneyBean.getRemitAmt());
		}
		textField.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				scanBill3();
				textField.setBorder(BorderFactory.createLineBorder(Color.red));
				textField_2.setBorder(BorderFactory.createLineBorder(Color.black));
				textField_3.setBorder(BorderFactory.createLineBorder(Color.black));
				textField_4.setBorder(BorderFactory.createLineBorder(Color.black));
			}

			

		});	
		keyPopup2 = new SoftKeyBoardPopups4(textField);
		this.showJpanel.add(keyPopup2);
		keyPopup2.addPopupMenuListener(new PopupMenuListener() {
			
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
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
			}
			
			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		label_1 = new JLabel("汇款用途：");
		label_1.setBounds(500, 490, 130, 40);
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		jpanel.add(label_1);
		
		//用途下拉框
		OtherBank = new String[] {"工资", "津贴", "奖金", "劳务报酬",
				"划款","货款","工程款","自输"};
		//用途面板
		usejpanel = new JPanel();
		usejpanel.setBounds(630, 280, 300, 210);
		usejpanel.setBackground(Color.white);
		usejpanel.setVisible(false);
		jpanel.add(usejpanel,JLayeredPane.MODAL_LAYER);
		
		// 下拉列表
		useList = new JList(OtherBank);
		useList.setBounds(0, 0, 300, 200);
		useList.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		useList.setFixedCellHeight(25);
		useList.setFixedCellWidth(300);
		useList.setOpaque(false);
		useList.setBackground(new Color(0,0,0,0));
		usejpanel.add(useList);
		useList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if("自输".equals((String)useList.getSelectedValue())){
					comboBox.setText("");
					usejpanel.setVisible(false);
					scan();
				}else{
					comboBox.setText((String)useList.getSelectedValue());
					usejpanel.setVisible(false);
				}
			}
		});
		
		
		comboBox = new JTextField();
		comboBox.setBorder(new BevelBorder(BevelBorder.LOWERED, null,null,null,null));
		comboBox.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		comboBox.setForeground(Color.black);
		comboBox.setBounds(630, 490, 300, 42);
		comboBox.setDocument(new TextMyDocument(10));
		comboBox.setText("请点击选择汇款用途");
		// 获取选中的内容
		jpanel.add(comboBox);
		//判断用途是否为空，若不为空，则直接赋值
		if(transferMoneyBean.getPurpos()!=null && !"".equals(transferMoneyBean.getPurpos())){
			comboBox.setText(transferMoneyBean.getPurpos());
		}
		if(transferMoneyBean.getAppdText()!=null && !"".equals(transferMoneyBean.getAppdText())){
			comboBox.setText(transferMoneyBean.getAppdText());
		}
		
		//用途输入的键盘
		keyPop4=new SoftKeyBoardPopups5(comboBox,50);
		jpanel.add(keyPop4);
		
		comboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if("请点击选择汇款用途".equals(comboBox.getText().trim())){
					usejpanel.setVisible(true);
					keyPop4.setVisible(false);
				}else if("工资".equals(comboBox.getText().trim())||
						 "津贴".equals(comboBox.getText().trim())||
						 "奖金".equals(comboBox.getText().trim())||
						 "劳务报酬".equals(comboBox.getText().trim())||
						 "划款".equals(comboBox.getText().trim())||
						 "货款".equals(comboBox.getText().trim())||
				   		 "工程款".equals(comboBox.getText().trim())
						){
					usejpanel.setVisible(true);
					keyPop4.setVisible(false);
				}else{
					usejpanel.setVisible(false);
					scan();
				}
			}
		});
		
		//选择用途输入方式
		uselabel= new JLabel("选择");
		uselabel.setBounds(930, 490, 45, 40);
		uselabel.setOpaque(isOpaque());
		uselabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		jpanel.add(uselabel);
		uselabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				usejpanel.setVisible(true);
				keyPop4.setVisible(false);
				return;
			}
		});
		
		// 上一步
		submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(1075, 770);
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				// 清空倒计时
				clearTimeText();
				// 跳转回上一个页面
				openPanel(new TransferAccountInfo(transferBean));
			}
		});
		add(submitBtn);

		// 确认
		backBtn = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				// 清空倒计时
				confirm(comp,transferBean);
			}
		});
		backBtn.setSize(150, 50);
		backBtn.setLocation(890, 770);
		add(backBtn);
		// 返回
		utilButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		utilButton.setBounds(1258, 770, 150, 50);
		utilButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				// 清空倒计时
				clearTimeText();
				openPanel(new OutputBankCardPanel());
			}
		});
		add(utilButton);
	}
	
	/***
	 * 帐号键盘
	 */
	public void scanBill1() {
		
			keyPopup.show(passwordPanel, 200, 450);
			textField_4.grabFocus();
		

	}

	/**
	 * 姓名键盘
	 */
	void scanBill2() {
		
			keyPopup1.show(passwordPanel, 200, 80);
			textField_2.grabFocus();
			
	}

	/**
	 * 汇款金额键盘
	 */
	private void scanBill3() {
		
			keyPopup2.show(passwordPanel,450, 450);
			textField.grabFocus();
	}
	/***
	 * 地区
	 */
	public void scanBill4() {
		
			keyPopup3.show(passwordPanel, 300, 500);
			textField_1.grabFocus();
	}
	/**
	 * 汇款用途键盘
	 */
	public  void scan() {
		keyPop4.show(passwordPanel, 200, 80);
		comboBox.grabFocus();
		
	}
	/**
	 * 将银行信息放到Bean中
	 * @param infos
	 * @param bankName
	 */
	public void putBean(List<Map<String,Object>> infos,String bankName) {
		if(bankName==null||infos.size()==0){
			return;
		}
		for (int i = 0; i < infos.size(); i++) {
			BankInfo bankInfo=(BankInfo) infos.get(i).get("info");
			if(bankName.equals(bankInfo.getName())){
				System.out.println(bankInfo.getName());
				
				if("1".equals(transferMoneyBean.getIsCardBank())){//外行卡
					if(bankInfo.getName().trim().startsWith("唐山银行股份有限公司")){//所有唐山银行结算行行号一致，由此判断外行卡不可选择本行银行
						openMistakeDialog("您输入的账户或者选择的开户行有误");
						return;
					}
				}
				logger.info("收款开户行号:"+bankInfo.getBankId());
				logger.info("收款开户行名:"+bankInfo.getName());
				logger.info("收款清算行号:"+bankInfo.getZjBankCode());
				logger.info("收款清算行名:"+bankInfo.getNamej());
				
				transferMoneyBean.setRecvBankNo(bankInfo.getBankId());//收款账户开户行号
				transferMoneyBean.setRecvBankName(bankInfo.getName());//收款账户开户行名
				transferMoneyBean.setRecvClrBankNo(bankInfo.getZjBankCode());//收款账号清算行号
				transferMoneyBean.setRecvClrBankName(bankInfo.getNamej());//收款账号清算行名
				textField_3.setText(bankName);
				break;
			}
		}
	}

	/**
	 * 将查询到的银行信息放到model中
	 * @param infos
	 */
	public void putInfos(List<Map<String,Object>> infos){
		model.removeAllElements();
		if(infos==null||infos.size()==0){
			model.addElement("没有符合条件的银行请重新选择!");
		}
		for (int i = (nowpage-1)*12; i < infos.size()&&i<(nowpage*12); i++) {
			String bankName=(String) infos.get(i).get("text");
			model.addElement(bankName);
		}
		list.setModel(model);

	}
		
	/**
	 * 处理省份下拉框事件
	 * @param comboBox_1
	 * @param comboBox_2
	 * @param comboBox_3
	 */
	public void extract(final ComboBox comboBox_1,final ComboBox comboBox_2) {
			try {
				Map<String, Object> pro = (Map<String, Object>) comboBox_1.getSelectItem();
				logger.info("选中的省份的信息" + pro);
				String showName = ((String) pro.get("text")).trim();
				comboBox_2.removeAllItems();
				model.removeAllElements();
				String proId = ((Province) pro.get("pro")).getProId();
				logger.info("选中的省份的省号" + proId);
				comboBox_2.removeAllItems();
				citys = stress.getCity(proId);
				logger.info("对应的市为" + citys);
				comboBox_2.setElements(citys);
			} catch (Exception e) {
				logger.error("省份下拉框出现异常"+e);
			}
	}	
	/**
	 * 卡号框
	 */
	public void extraNo() {
		try {
			scanBill1();
			textField.setBorder(BorderFactory.createLineBorder(Color.black));
			textField_4.setBorder(BorderFactory.createLineBorder(Color.red));
			textField_2.setBorder(BorderFactory.createLineBorder(Color.black));
			textField_3.setBorder(BorderFactory.createLineBorder(Color.black));
		} catch (Exception e) {
			logger.error("卡号框异常"+e);
		}
	}
	
	/**
	 * 处理银行列表
	 * @param panel
	 * @param list
	 */
	public void extraBank( final JList list) {
		try {
			logger.info("选中的银行名称" +  list.getSelectedValue());
			String name=(String) list.getSelectedValue();
			//将银行信息放到Bean中
			panel_4.setVisible(false);
			submitBtn.setVisible(true);
			backBtn.setVisible(true);
			utilButton.setVisible(true);
			jpanel.setVisible(true);
			putBean(infos,name);
		} catch (Exception e) {
			logger.error("银行信息列表异常"+e);
		}
	}
	/**
	 * 处理开户行框事件
	 * @param panel
	 * @param comboBox_1
	 * @param comboBox_2
	 * @param comboBox_3
	 */
	public void extraArea(final JLayeredPane panel,
			final ComboBox comboBox_1, final ComboBox comboBox_2
			) {
		try {
			textField_3.setBorder(BorderFactory.createLineBorder(Color.red));
			textField.setBorder(BorderFactory.createLineBorder(Color.black));
			textField_4.setBorder(BorderFactory.createLineBorder(Color.black));
			textField_2.setBorder(BorderFactory.createLineBorder(Color.black));
			if(model!=null){
				model.removeAllElements();
			}
			panel_4.setVisible(true);
			panel.setVisible(false);
		} catch (Exception e) {
			logger.error("银行信息输入框异常"+e);
		}
	}
	/**
	 * 处理搜索按钮
	 * @param comboBox_1
	 * @param comboBox_2
	 * @param list
	 */
	public void select(final ComboBox comboBox_2,final ComboBox comboBox_1) {
		try {
			nowpage=1;
			model.removeAllElements();
			String text=textField_1.getText();
			if(text==null){
				openMistakeDialog("请输入关键字搜索");
				return;
			}
			if(infos.size()>0){
				infos= new ArrayList<Map<String, Object>>();
			}
			Map<String, Object> pro = (Map<String, Object>) comboBox_1.getSelectItem();
			logger.info("选中的省份的信息" + pro);
			String jdCode=((String)pro.get("jdCode")).trim();
			logger.info("节点代码为"+jdCode);
			Map city = (Map) comboBox_2.getSelectItem();
			String cityName = ((String) city.get("text")).trim();
			char[] password = cityName.toCharArray();
			if (password != null && password.length > 0) {
				char[] copyOf = Arrays.copyOf(password, 2);
				logger.info("修改后的城市名"+new String(copyOf));
				cityName=new String(copyOf);
				if(cityName.contains("深圳")){
					jdCode= ((City) (city.get("pro"))).getCityCode();
				}
			}
			final Map<String, String> params=new HashMap<String, String>();
			params.put("bankName", text);
			params.put("cityName", cityName);
			params.put("provinceCode", jdCode);
			extact(params);
			
		} catch (Exception e) {
			logger.error("搜索框异常"+e);
		}
	}
	/***
	 * 调用接口Tms0008查询行名行号
	 * @param params
	 */
	public void extact(final Map<String, String> params) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					logger.info("传入的搜索条件"+params);
					openProssDialog();
					String resInfos=InterfaceSendMsg.inter0008(params);
					closeDialog(prossDialog);
					if(resInfos==null||"".equals(resInfos)){
						return;
					}
					String[] bankInfos= resInfos.split("\\;");
					for (int i = 0; i < bankInfos.length; i++) {
						String[] banks=bankInfos[i].trim().split("\\,");
						BankInfo info=new BankInfo();
						info.setBankId(banks[0].trim());
						info.setName(banks[1].trim());
						info.setZjBankCode(banks[2].trim());
						info.setNamej(banks[3].trim());
						Map map=new HashMap();
						map.put("text", banks[1].trim());
						map.put("info", info);
						infos.add(map);
					} 
					putInfos(infos);
				} catch (Exception e) {
					closeDialog(prossDialog);
					logger.error("调用接口Tms0008失败"+e);
				}
				
			}
		}).start();
	}
	/**
	 * 处理转入户名框
	 */
	public void extraName() {
		scanBill2();
		textField.setBorder(BorderFactory.createLineBorder(Color.black));
		textField_4.setBorder(BorderFactory.createLineBorder(Color.black));
		textField_2.setBorder(BorderFactory.createLineBorder(Color.red));
		textField_3.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	
	
	/**
	 * 监听转入卡号的失焦事件
	 */
	public void checkPayeeAcctNo(final Component comp,final PublicAccTransferBean transferBean) {
		logger.info("输入的转入卡号:" + textField_4.getText());
		
		logger.info("历史转账账户列表总数:" + PayList.size());
		String text=textField_4.getText();
		text=StringUtils.full2Half(text);
		textField_4.setText(text);
		if (textField_4.getText() != null && textField_4.getText() != "") {

			// 本行卡信息自动带出
			new Thread() {
				@Override
				public void run() {
					try {
						openProssDialog();//打开处理等待框
						transferMoneyBean.setRuZhangCardNo(textField_4.getText());
						
						//查询卡账户信息
						logger.info("进入卡账户信息查询");
						if(!checkCardAcct(transferBean)){
							return;
						}
						
						closeDialog(prossDialog);//关闭处理等待框
						
						//查询账户信息成功自动带出转入的 账户信息
						if("0".equals(transferMoneyBean.getIsHaveAcctNo())){
							
							logger.info("判断为本行卡:"+transferMoneyBean.getRuZhangCardNo());
							
							textField_2.setText(transferMoneyBean.getRuZhangcardName());// 收款账户名
							textField_3.setText(transferMoneyBean.getRecvBankName());// 收款账户开户行名
							comp.repaint();
							
							transferBean.setIsCardBank("0");// 判定本行卡
							
							
						//查账户信息失败无此记录，表明为他行卡，再继续查询转账历史记录
						}else{
							
							//判断输入卡号的卡Bin，卡Bin如果为本行，是本行卡。但是可能因为卡号输入错误，查账户信息失败无此记录，提示核查输入的卡号信息
							if(textField_4.getText().trim().startsWith("623193") || textField_4.getText().trim().startsWith("622167") || textField_4.getText().trim().startsWith("62326558")){
								openMistakeDialog("卡信息查询失败，请核查您的卡号是否输入正确");
								return ;
							}
							
							logger.info("判断为他行卡:"+transferMoneyBean.getRuZhangCardNo());
							transferBean.setIsCardBank("1");// 他行卡
							
							if (PayList != null && PayList.size() != 0) {
								for (PaymentTermsMsgBean msgBean : PayList) {
									// 有转账客户信息直接自动带出，无则继续输入
									if (textField_4.getText().equals(
											msgBean.getPayeeAcctNo().trim())) {// 有匹配的历史转账记录
										logger.info("历史转账账户：" + textField_4.getText());
										textField_2.setText(msgBean.getPayeeName().trim());// 收款账户名
										textField_3.setText(msgBean.getPayeeHbrName().trim());// 收款账户开户行名
										
										transferBean.setRuZhangcardName(msgBean
												.getPayeeName().trim());// 收款账户名
										transferBean.setRecvBankNo(msgBean
												.getPayeeHbrNo().trim());// 收款账户开户行号
										transferBean.setRecvBankName(msgBean
												.getPayeeHbrName().trim());// 收款账户开户行名
										transferBean.setRecvClrBankNo(msgBean
												.getRecvClrBankNo().trim());// 收款账号清算行号
										transferBean.setRecvClrBankName(msgBean
												.getRecvClrBankName().trim());// 收款账号清算行名
										comp.repaint();
										break;
									} else {// 无相关转账记录
//										textField_2.setText("");// 收款账户名
//										textField_3.setText("");// 收款账户开户行名
//										comp.repaint();
									}
								}
							}
						}
					} catch (Exception e) {
						clearTimeText();
						logger.error("输入转入账户的监听异常"+e);
						serverStop("抱歉，转入账号信息查询失败,请核对转入账号的正确性后重新操作", "","调用转入账号信息查询接口异常");
					}
					
				}
			}.start();	
		}
	}

	/**
	 * 输入转入卡号的处理
	 */
	public boolean checkPayeeAcc(final PublicAccTransferBean transferBean) {
		logger.info("转入卡名：" + textField_4.getText());
		if ("".equals(textField_4.getText()) || null == textField_4.getText()) {
			openMistakeDialog("请输入汇款账号");
			return false;
		}
		if (!textField_4.getText().trim().matches("^[0-9]*[0-9][0-9]*$")) {
			openMistakeDialog("您输入的账号格式不正确,请重新输入");
			return false;
		}
		transferBean.setRuZhangCardNo(textField_4.getText());// 收款账户
		return true;
	}

	/**
	 * 输入转入户名的处理
	 */
	public boolean checkPayeeAcctName(final PublicAccTransferBean transferBean) {
		logger.info("转入户名：" + textField_2.getText());
		if ("".equals(textField_2.getText()) || null == textField_2.getText()) {
			openMistakeDialog("请输入汇款姓名");
			return false;
		}
		transferBean.setRuZhangcardName(textField_2.getText());// 收款账户名
		return true;
	}

	/**
	 * 输入转入开户行的处理
	 */
	public boolean checkPayeeRecvBank() {
		logger.info("转入开户行：" + textField_3.getText());
		if ("".equals(textField_3.getText()) || null == textField_3.getText()) {
			openMistakeDialog("请输入收款人开户行");
			return false;
		}
		return true;
	}

	/**
	 * 输入金额的处理
	 */
	public boolean checkRemitAmt(final PublicAccTransferBean transferBean){
		logger.info("转入金额："+textField.getText());
		if("".equals(textField.getText()) || null ==textField.getText()){
			openMistakeDialog("请输入汇款金额");
			return false;
		}
		//不为正整数或者小数位数不等于2位
		String money = textField.getText();
		
		if(money.contains(".")){
			String[] split = money.split("\\.");
			if(split.length==2){
				if(split[0].matches("^[0-9]*[0-9][0-9]*$") && (split[1].matches("^[0-9]*[0-9][0-9]*$"))){//整数、小数都为数字
					
				}else{
					openMistakeDialog("汇款金额不合法，请重新输入！");
					return false;
				}
				if(split[1].length()==1){
					money = textField.getText()+"0";
				}else if(split[1].length()!=2){
					openMistakeDialog("汇款金额只保留两位小数，请重新输入！");
					return false;
				}
			}else{
				openMistakeDialog("汇款金额不合法，请重新输入！");
				return false;
			}
		}else{
			if(money.matches("^[0-9]*[0-9][0-9]*$")){
				money = textField.getText()+".00";
			}else{
				openMistakeDialog("汇款金额不合法，请重新输入！");
				return false;
			}
		}
		
		//判断转账金额是否大于卡内余额
		if(!"".equals(transferBean.getCardAmt()) && null!=transferBean.getCardAmt()){
			Double floatAmt = Double.valueOf(transferBean.getCardAmt());//卡余额
			Double remitAmt = Double.valueOf(money);//汇款金额
			logger.info("卡余额"+floatAmt);
			logger.info("汇款金额"+remitAmt);
			if(remitAmt==0 ){
				openMistakeDialog("转账金额不能为0，请重新输入");
				return false;
			}else if(remitAmt>floatAmt){
				openMistakeDialog("卡内余额不足，请重新输入");
				return false;
			}
		}	
		openProssDialog();
		logger.info("进入节假日查询");
		if(checkHolidays(transferBean)==false){//查询节假日
			return false;
		}
		
		transferBean.setRemitAmt(money);//获取汇款金额
		
		//查询二类帐户限制金额是否超限
		if(!checkErInfo()){
			return false;
		}
		closeDialog(prossDialog);
		return true;
		
	}
	
	
	/**
	 * 查询节假日
	 */
	public boolean checkHolidays(final PublicAccTransferBean transferBean){
		try {
			transferBean.getReqMCM001().setReqBefor("07495");
			Map inter07495 = InterfaceSendMsg.inter07495(transferBean); 
			transferBean.getReqMCM001().setReqAfter((String)inter07495.get("resCode"), (String)inter07495.get("errMsg"));
			if(!"000000".equals(inter07495.get("resCode"))){
				
				closeDialog(prossDialog);
				logger.info(inter07495.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				clearTimeText();
				serverStop("查询节假日失败，请联系大堂经理", (String)inter07495.get("errMsg"),"");
				return false;
			}
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("查询节假日失败"+e);
			transferBean.getReqMCM001().setIntereturnmsg("调用07495接口异常");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			clearTimeText();
			serverStop("查询节假日失败，请联系大堂经理", "","调用07495接口异常");
			return false;
		}
		logger.info("查询节假日成功");
		return true;
	}
	
	/**
	 * 下拉框的处理
	 */
	public boolean checkComBox(final PublicAccTransferBean transferBean){
		logger.info("汇款用途："+(String) comboBox.getText());
		if("0".equals(transferBean.getIsCardBin())){//付款方为个人选输
			logger.info("个人转账资金用途");
			if("".equals((String) comboBox.getText())){
				
				if("0".equals(transferBean.getIsCardBank())){
					transferBean.setPurpos("");//获取个人资金用途
					transferBean.setSummText("行内汇划");//摘要
				}else{
					transferBean.setAppdText("");//获取个人资金用途
					transferBean.setSummText("跨行汇款");//摘要
				}
			
			}else{
			
				if("0".equals(transferBean.getIsCardBank())){
					transferBean.setPurpos((String) comboBox.getText());//获取个人资金用途
					transferBean.setSummText("行内汇划");//摘要
				}else{
					transferBean.setAppdText((String) comboBox.getText());//获取个人资金用途
					transferBean.setSummText("跨行汇款");//摘要
				}
			}
		}else if("1".equals(transferBean.getIsCardBin())){//付款方为单位必输
			if(("请点击选择汇款用途".equals(comboBox.getText().trim())||"".equals((String) comboBox.getText())) && Float.parseFloat(transferBean.getRemitAmt())>50000){
				openMistakeDialog("请选择汇款用途");
				return false;
			}else if("".equals((String) comboBox.getText()) && Float.parseFloat(transferBean.getRemitAmt())<=50000){
				if("0".equals(transferBean.getIsCardBank())){
					transferBean.setPurpos("");//获取单位资金用途
					transferBean.setSummText("行内汇划");//摘要
				}else{
					transferBean.setAppdText("");//用途
					transferBean.setSummText("跨行汇款");//摘要
				}
			}else{
				if("0".equals(transferBean.getIsCardBank())){
					transferBean.setPurpos((String) comboBox.getText());//获取单位资金用途
					transferBean.setSummText("行内汇划");//摘要
				}else{
					transferBean.setAppdText((String) comboBox.getText());//用途
					transferBean.setSummText("跨行汇款");//摘要
				}
			}
		}
		if("请点击选择汇款用途".equals(comboBox.getText().trim())){
			transferBean.setPurpos("");
			transferBean.setAppdText("");
		}
		return true;
	}
	
	/**
	 * 查询转入卡黑灰名单
	 */
	public boolean checkTelephoneFraud(final PublicAccTransferBean transferBean){
		try {
			transferBean.getReqMCM001().setReqBefor("01597");
			Map inter01597 = InterfaceSendMsg.inter01597(transferBean); 
			transferBean.getReqMCM001().setReqAfter((String)inter01597.get("resCode"), (String)inter01597.get("errMsg"));
			if(!"000000".equals(inter01597.get("resCode"))){
				
				if ("0010".equals(inter01597.get("resCode"))) {
					
					closeDialog(prossDialog);
					logger.info((String) inter01597.get("errMsg"));
					MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
					clearTimeText();
					serverStop("该收款账号为涉案客户，禁止交易", "","");
					return false;
				} else if ("0020".equals(inter01597.get("resCode"))) {
					
					closeDialog(prossDialog);
					logger.info((String) inter01597.get("errMsg"));
					MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
					clearTimeText();
					serverStop("该收款账号可疑，谨防诈骗，请到我行柜台办理", "","");
					return false;
				}
				closeDialog(prossDialog);
				logger.info(inter01597.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				clearTimeText();
				serverStop("查询黑灰名单信息失败", (String)inter01597.get("errMsg"),"");
				return false;
			}
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用01597查询黑灰名单信息失败"+e);
			transferBean.getReqMCM001().setIntereturnmsg("调用01597接口异常");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			clearTimeText();
			serverStop("查询黑灰名单信息失败","","调用01597接口异常!");
			return false;
		}
		return true;
	}
	
	/**
	 * 查询卡账户信息
	 */
	public boolean checkCardAcct(final PublicAccTransferBean transferBean){
		try {
			transferBean.getReqMCM001().setReqBefor("03521");
			Map ru03521 = InterfaceSendMsg.Ru03521(transferMoneyBean); 
			transferBean.getReqMCM001().setReqAfter((String)ru03521.get("resCode"), (String)ru03521.get("errMsg"));
			if(!"000000".equals(ru03521.get("resCode"))){
				String errMsg = (String)ru03521.get("errMsg");
				
				//查询失败无此账户
				closeDialog(prossDialog);
				logger.info((String)ru03521.get("errMsg"));
				logger.info("查询输入的转入账户没有此记录，判定为外行卡"+transferMoneyBean.getRuZhangCardNo());
				transferMoneyBean.setIsHaveAcctNo("1");//无此账户
				return true;		
						
			}
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("卡账户信息查询失败,接口调用异常"+e);
			openMistakeDialog("抱歉，转入卡信息查询失败");
			mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					closeDialog(prossDialog);
					clearTimeText();
					openPanel(new TransferRemitInfomationPanel(transferBean));
				}
			});
			return false;
		}
		
		logger.info("查询输入的转入账户成功，判定为本行卡"+transferMoneyBean.getRuZhangCardNo());
		//查询开户行机构
		logger.info("进入账号开户机构查询");
		if (!checkHbrInstNo(transferBean)) {
			return false;
		}
		logger.info("转入账号开户机构查询成功......");
		transferMoneyBean.setIsHaveAcctNo("0");//有此账户
		return true;
	}
	
	/**
	 * 查询开户机构信息
	 */
	public boolean checkHbrInstNo(final PublicAccTransferBean transferBean){
		try {
			transferBean.getReqMCM001().setReqBefor("01118");
			Map ru01118 = InterfaceSendMsg.ru01118(transferMoneyBean);
			transferBean.getReqMCM001().setReqAfter((String)ru01118.get("resCode"), (String)ru01118.get("errMsg"));
			if(!"000000".equals(ru01118.get("resCode"))){
				closeDialog(prossDialog);
				logger.info(ru01118.get("errMsg"));
				openMistakeDialog("抱歉，本行开户行查询失败");
				mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(prossDialog);
						clearTimeText();
						openPanel(new TransferRemitInfomationPanel(transferBean));
					}
					
				});
				return false;
			}
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.info("查询机构信息失败,调用01118接口异常"+e);
			openMistakeDialog("抱歉，本行开户行查询失败");
			mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					closeDialog(prossDialog);
					clearTimeText();
					openPanel(new TransferRemitInfomationPanel(transferBean));
				}
				
			});
			return false;
		}
		return true;
	}
	
	/**
	 * 确认处理
	 */
	public void confirm(Component comp,final PublicAccTransferBean transferBean){
		logger.info("点击确认存款信息页的处理");
		new Thread() {
			@Override
			public void run() {
				transferBean.setIsChuRu("1");//判断入账黑灰名单
				if(checkPayeeAcc(transferBean) && checkPayeeAcctName(transferBean)  && checkPayeeRecvBank() && checkRemitAmt(transferBean) && checkComBox(transferBean) && checkTelephoneFraud(transferBean) && checkOtherCardInfo()){
					clearTimeText();
					openPanel(new TransferAccSelectPanel(transferBean));
				}else{
					on_off=true;
				}
			}
		}.start();
	}
	
	/**
	 * 转账客户信息查询
	 */
	public void ruZhangName(){
		logger.info("转账客户信息展示");
		if(PayList != null && PayList.size()!=0){
			pageNo=1;
			if(PayList.size()%count>0){
				pageCount=PayList.size()/count+1;
			}else{
				pageCount=PayList.size()/count;
			}
			
			jpanelInfo.setVisible(true);
			js.setVisible(true);
			labelNames.setVisible(false);
			
			jpanel.setVisible(false);
			submitBtn.setVisible(false);
			backBtn.setVisible(false);
			utilButton.setVisible(false);
			
			showInfo();
			
		}else{
			openMistakeDialog("您的账户还没有转账记录");
		}
	}
	
	//转账客户信息展示 
	public void showInfo(){
		try {
			logger.info("展示客户信息");
			DefaultListModel<String> dlm = new DefaultListModel<String>();
			
			Vector<String> vStr = new Vector<String>();
			for(PaymentTermsMsgBean msgBean : PayList){
				String ruZhangInfo = "  "+msgBean.getPayeeAcctNo();
				if(msgBean.getPayeeName()!=null && !"".equals(msgBean.getPayeeName()) && msgBean.getPayeeHbrName()!=null && !"".equals(msgBean.getPayeeHbrName())){
					ruZhangInfo+="--"+msgBean.getPayeeName();
				}
				if(msgBean.getPayeeHbrName()!=null && !"".equals(msgBean.getPayeeHbrName())){
					ruZhangInfo+="--"+msgBean.getPayeeHbrName();
				}
				vStr.add(ruZhangInfo);
				logger.info("客户信息："+ruZhangInfo);
			}	
			
			if(pageNo*count<PayList.size()){
				for(int i=(pageNo-1)*count;i<pageNo*count;i++){
					dlm.addElement(vStr.get(i));
				}
			}else{
				for(int i=(pageNo-1)*count;i<PayList.size();i++){
					dlm.addElement(vStr.get(i));
				}
			}
			jlist.setModel(dlm);
		} catch (Exception e) {
			logger.error("展示转账客户记录失败"+e);
		}
	}
	
	//选择转账记录
	public void chooseInfo(final Component comp,final PublicAccTransferBean transferBean){
		String info = (String)jlist.getSelectedValue();
		String[] infoArr =info.split("--");
		textField_4.setText(infoArr[0].trim());
		if(infoArr.length==2){
			textField_2.setText(infoArr[1]);
		}
		if(infoArr.length==3){
			textField_3.setText(infoArr[2]);
		}
		checkPayeeAcctNo(comp,transferBean);
		
		jpanelInfo.setVisible(false);
		jpanel.setVisible(true);
		submitBtn.setVisible(true);
		backBtn.setVisible(true);
		utilButton.setVisible(true);
	}
	
	//校验对手信息
	public boolean checkOtherCardInfo(){
		try {
			if("1".equals(transferMoneyBean.getIsCardBin())){
				transferBean.getReqMCM001().setReqBefor("02954");
				Map inter02954=InterfaceSendMsg.inter02954(transferMoneyBean);
				transferBean.getReqMCM001().setReqAfter((String)inter02954.get("resCode"), (String)inter02954.get("errMsg"));
				if(!"000000".equals(inter02954.get("resCode"))){
					closeDialog(prossDialog);
					MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
					clearTimeText();
					serverStop("校验对手信息失败，请联系大堂经理", (String)inter02954.get("errMsg"),"");
					return false;
				}
			}
			
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("校验对手信息失败"+e);
			transferBean.getReqMCM001().setIntereturnmsg("调用02954接口异常");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			clearTimeText();
			serverStop("校验对手信息失败，请联系大堂经理","","调用校验对手信息接口异常");
			return false;
		}
		
		return true;
	}
	public static void main(String[] args) {
//		TransferRemitInfomationPanel s=new TransferRemitInfomationPanel();
		Map<String, String> params=new HashMap<String, String>();
		params.put("bankName", "机场路");
		params.put("cityName", "唐山市");
		params.put("provinceCode", "1210");
//		extact(params);
	}
	
	//查询二类帐户限制金额
	public boolean checkErInfo(){
		logger.info("进入二类帐户限额交易查询");
		try {
			transferBean.getReqMCM001().setReqBefor("02781");
			Map<String,String> map = InterfaceSendMsg.inter02781(transferMoneyBean);
			transferBean.getReqMCM001().setReqAfter((String)map.get("resCode"), (String)map.get("errMsg"));
			if(!"000000".equals(map.get("resCode"))){
				if(map.get("errMsg").startsWith("CU77")){
					logger.info("超出限额"+map.get("errMsg"));
					prossDialog.disposeDialog();
					openMistakeDialog("转账金额超出限制金额，请重新输入金额。");
				}else{
					logger.info("查询二类帐户限制金额失败"+map.get("errMsg"));
					prossDialog.disposeDialog();
					MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
					clearTimeText();
					serverStop("二类帐户限额交易查询失败，请联系大堂经理",map.get("errMsg"),"");
				}
				return false;
			}
			
		} catch (Exception e) {
			logger.error("二类帐户限额交易查询失败"+e);
			prossDialog.disposeDialog();
			transferBean.getReqMCM001().setIntereturnmsg("调用02781接口异常");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			clearTimeText();
			serverStop("二类帐户限额交易查询失败，请联系大堂经理","","调用查询二类账限额接口异常");
			return false;
		}
		return true;
	}
	
	//删除名册记录
	public void deleteInfo(final PublicAccTransferBean transferBean){
		new Thread(new Runnable() {
			public void run() {
				logger.info("进入删除名册记录方法");
				String info = (String)jlist.getSelectedValue();
				String[] infoArr =info.split("--");
				Map<String,String> deleteMap = new HashMap<String, String>();
				deleteMap.put("chuZhangCardNo", transferMoneyBean.getChuZhangCardNo());
				deleteMap.put("chuZhangCardName", transferMoneyBean.getChuZhangcardName());
				deleteMap.put("ruZhangCardNo", infoArr[0].trim());
				deleteMap.put("ruZhangCardName",infoArr[1].trim());
				
				try {
					logger.info("调用删除记录接口");
					openProssDialog();
					transferBean.getReqMCM001().setReqBefor("07496");
					Map<String,String> map = InterfaceSendMsg.inter07496(deleteMap);
					transferBean.getReqMCM001().setReqAfter((String)map.get("resCode"), (String)map.get("errMsg"));
					prossDialog.disposeDialog();
					if(!"000000".equals(map.get("resCode"))){
						logger.error("删除记录失败"+map.get("errMsg"));
						openMistakeDialog("删除记录失败"+map.get("errMsg"));
						return;
					}
					logger.info("重新查询名册信息");
					transferAction.checkCustMsg(comp,transferBean);
					PayList = (List<PaymentTermsMsgBean>) transferBean.getParams().get("PayList");
					logger.info("重新展示名册信息");
					jpanelInfo.repaint();
					if(PayList!=null&&PayList.size()!=0){
						ruZhangName();
					}else{
						pageNo=1;
						jpanelInfo.setVisible(true);
						js.setVisible(false);
						labelNames.setVisible(true);
						
						jpanel.setVisible(false);
						submitBtn.setVisible(false);
						backBtn.setVisible(false);
						utilButton.setVisible(false);
					}
					
				} catch (Exception e) {
					prossDialog.disposeDialog();
					logger.error("删除记录失败"+e);
					openMistakeDialog("删除记录失败");
				}
				
			}
		}).start();
	}
	
}
