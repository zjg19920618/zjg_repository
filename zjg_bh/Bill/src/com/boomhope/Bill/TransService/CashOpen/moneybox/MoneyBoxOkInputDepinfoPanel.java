package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;

/***
 * 确认存款信息界面Panel
 * @author gyw
 *
 */
public class MoneyBoxOkInputDepinfoPanel  extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxOkInputDepinfoPanel.class);

	private JTable table_2;
	private JTable table_3;
	
	private boolean on_off=true;//开关控制
	public MoneyBoxOkInputDepinfoPanel(final PublicCashOpenBean transBean){

		this.cashBean = transBean;
        JLabel label = new JLabel(transBean.getProductName());
		label.setForeground(Color.decode("#412174"));
		label.setHorizontalAlignment(0);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		label.setBounds(0, 60, 1009, 60);
		label.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(label);
		
		/*JLabel label_1 = new JLabel("账             号：");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_1.setBounds(114, 211, 140, 50);
		add(label_1);*/
		
		JLabel label_2 = new JLabel("户             名：");
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_2.setBounds(114, 180, 140, 50);
		this.showJpanel.add(label_2);
		
		JLabel label_3 = new JLabel("金额（大写）：");
		label_3.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_3.setBounds(114, 246, 140, 50);
		this.showJpanel.add(label_3);
		
		JLabel label_4 = new JLabel("金额（小写）：");
		label_4.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_4.setBounds(599, 246, 140, 50);
		this.showJpanel.add(label_4);
		
		String[] columnNames = { "存入日", "起息日", "到期日", "存期", "利率", "到期利息","支取方式", "柜员号" };
		String[] columnNames1 = { "支取日", "本金支取金额", "利息", "本息合计", "任务号", "柜员号"};
		  Object[][] data = { { transBean.getCreateTime(), transBean.getCreateTime(), transBean.getEndTime(), transBean.getMonthsUpperStr(), transBean.getRate().trim()+"%",	transBean.getInte(), transBean.getSubPwd().length()>5?"密码":"无", GlobalParameter.tellerNo}};
		  //测试
//		  Object[][] data = { {"存入日", "起息日", "到期日", "存期", "利率", "到期利息","支取方式", "柜员号"}};
		  Object[][] data1 = { { "", "", "", "", "",	""}};
			JPanel panel = new JPanel();
			panel.setBounds(65, 351, 883, 73);
			panel.setLayout(null);
			this.showJpanel.add(panel);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(0, 0, 883, 73);
			panel.add(scrollPane);
			
//			table_2 = new JTable(data, columnNames);
//			table_2.setRowHeight(50);//高度
//			table_2.getColumnModel().getColumn(0).setPreferredWidth(78);//设置款度
//			scrollPane.setViewportView(table_2);
			table_2 = new JTable(data, columnNames);
			JTableHeader header = table_2.getTableHeader();
			header.setFont(new Font("宋体", Font.PLAIN, 14));
			header.setPreferredSize(new Dimension(header.getWidth(),27));
			table_2.setFont(new Font("宋体", Font.PLAIN, 20));
			table_2.setRowHeight(43);//高度
			table_2.setEnabled(false);
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(JLabel.CENTER);
			table_2.setDefaultRenderer(Object.class, tcr);
			table_2.getColumnModel().getColumn(5).setPreferredWidth(100);//设置款度
			scrollPane.setViewportView(table_2);

			
			
			
			//第2行
			JPanel panel1 = new JPanel();
			panel1.setBounds(65, 451, 883, 73);
			panel1.setLayout(null);
			this.showJpanel.add(panel1);
			
			JScrollPane scrollPane1 = new JScrollPane();
			scrollPane1.setBounds(0, 0, 883, 73);
			panel1.add(scrollPane1);
//			
//			table_3 = new JTable(data1, columnNames1);
//			table_3.setRowHeight(50);//高度
//			table_3.getColumnModel().getColumn(0).setPreferredWidth(78);//设置款度
//			scrollPane1.setViewportView(table_3);
			table_3 = new JTable(data1, columnNames1);
			JTableHeader header1 = table_3.getTableHeader();
			header1.setFont(new Font("宋体", Font.PLAIN, 14));
			header1.setPreferredSize(new Dimension(header1.getWidth(),27));
			table_3.setFont(new Font("宋体", Font.PLAIN, 20));
			table_3.setRowHeight(43);//高度
			table_3.setEnabled(false);
			DefaultTableCellRenderer tcr1 = new DefaultTableCellRenderer();
			tcr1.setHorizontalAlignment(JLabel.CENTER);
			table_3.setDefaultRenderer(Object.class, tcr1);
			table_3.getColumnModel().getColumn(0).setPreferredWidth(85);//设置款度
			scrollPane1.setViewportView(table_3);
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
			this.add(lblNewLabel1);
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
			this.add(label1);
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
			this.add(label_1);
			
			JLabel lblNewLabel_1 = new JLabel("人民币"+transBean.getMoneyUpper());
			lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			lblNewLabel_1.setBounds(254, 249, 309, 50);
			this.showJpanel.add(lblNewLabel_1);
			
			JLabel lblNewLabel_2 = new JLabel(transBean.getCardName());
			lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			lblNewLabel_2.setBounds(254, 180, 231, 50);
			this.showJpanel.add(lblNewLabel_2);
			
			JLabel lblNewLabel_3 = new JLabel("CNY"+transBean.getMoney()+".00");
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			lblNewLabel_3.setBounds(749, 249, 231, 50);
			this.showJpanel.add(lblNewLabel_3);

			/* 倒计时打开语音 */
			voiceTimer = new Timer(voiceSecond, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					voiceTimer.stop();
					excuteVoice("voice/okinputdepinfo.wav");

				}
			});
			voiceTimer.start();
			/* 显示时间倒计时 */
			this.showTimeText(delaySecondShortTime);
			delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					on_off=false;
					/* 倒计时结束退出交易 */
					clearTimeText();
					serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
				}
			});
			delayTimer.start();
		
	
	}
	/**
	 * 返回
	 * */
	public void backTrans(PublicCashOpenBean transBean) {
		
		clearTimeText();
		//根据产品代码跳转不同的页面
		if(transBean.getProductCode().substring(0, 2).equals("QX")){
			openPanel(new MoneyBoxEnteringQXXLPanel(transBean));
				
		}else if(transBean.getProductCode().substring(0, 2).equals("YA") ||transBean.getProductCode().substring(0, 2).equals("YB")||transBean.getProductCode().substring(0, 2).equals("YC") ){
			
			openPanel(new MoneyBoxEnteringYXCPanel(transBean));
			
		}else if(transBean.getProductCode().substring(0, 2).equals("XF")){
			
			openPanel(new MoneyBoxEnteringXFYJYXLPanel(transBean));
			
		}else if(transBean.getProductCode().substring(0, 2).equals("RY")){
			openPanel(new MoneyBoxEnteringRYCPanel(transBean));
			
		}else if(transBean.getProductCode().substring(0, 2).equals("RJ")){
			openPanel(new MoneyBoxEnteringRYCJPanel(transBean));
			
		}else if(transBean.getProductCode().substring(0, 2).equals("LT") || transBean.getProductCode().substring(0, 2).equals("LZ")){
			openPanel(new MoneyBoxEnteringLDCPanel(transBean));
				
		}else if(transBean.getProductCode().substring(0, 2).equals("JJ")){
			openPanel(new MoneyBoxEnteringLDCPanel(transBean));
				
		}else{
			
			openPanel(new MoneyBoxInPutdepInfoPanel(transBean));
			
		}
		
	}
	
	/**
	 * 确认
	 * */
	public void okTrans(PublicCashOpenBean transBean) {
		clearTimeText();
		
		if(transBean.getProductCode().substring(0, 2).equals("JX")){
			transBean.setAgreementIdentification("1");//积享存
			openPanel(new MoneyBoxAgreementPage(transBean));
			
		}else if(transBean.getProductCode().substring(0, 2).equals("LZ")){
			transBean.setAgreementIdentification("2");//立得存
			openPanel(new MoneyBoxAgreementPage(transBean));
			
		}else if(transBean.getProductCode().substring(0, 2).equals("LT")){
			transBean.setAgreementIdentification("2");//立得存
			openPanel(new MoneyBoxAgreementPage(transBean));
			
		}else if(transBean.getProductCode().substring(0, 2).equals("RY")){
			transBean.setAgreementIdentification("3");//如意存
			openPanel(new MoneyBoxAgreementPage(transBean));
				
		}else if(transBean.getProductCode().substring(0, 2).equals("RJ")){
			transBean.setAgreementIdentification("4");//如意存+
			openPanel(new MoneyBoxAgreementPage(transBean));
			
		}else if(transBean.getProductCode().substring(0, 2).equals("YA")){
			transBean.setAgreementIdentification("5");//约享存A
			openPanel(new MoneyBoxAgreementPage(transBean));
			
		}else if(transBean.getProductCode().substring(0, 2).equals("YB")){
			transBean.setAgreementIdentification("6");//约享存B
			openPanel(new MoneyBoxAgreementPage(transBean));
				
		}else if(transBean.getProductCode().substring(0, 2).equals("YC")){
			transBean.setAgreementIdentification("7");//约享存C
			openPanel(new MoneyBoxAgreementPage(transBean));
				
		}else{
			//整存整取
			//交易处理中
			openPanel(new MoneyBoxDepChecingPanel(transBean));
			
		}
	}
	
	
}
