package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;

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
 * 确认存款信息界面Panel(积享存)
 * @author ly
 *
 */
public class MoneyBoxOkJXCInfoPanel extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxOkJXCInfoPanel.class);
	private JTable table_2;
	
	private boolean on_off=true;//开关控制
	public MoneyBoxOkJXCInfoPanel(final PublicCashOpenBean transBean){
		this.cashBean = transBean;
        JLabel label = new JLabel(transBean.getProductName());

		
		label.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label.setBounds(114, 151, 392, 50);
		this.showJpanel.add(label);
		
		JLabel label_1 = new JLabel("账             号：");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_1.setBounds(234, 282, 140, 50);
		this.showJpanel.add(label_1);
		
		JLabel label_2 = new JLabel("户             名：");
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_2.setBounds(689, 282, 140, 50);
		this.showJpanel.add(label_2);
		
		JLabel label_3 = new JLabel("每月存入固定金额（大写）：");
		label_3.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_3.setBounds(114, 342, 260, 50);
		this.showJpanel.add(label_3);
		
		JLabel label_4 = new JLabel("金额（小写）：");
		label_4.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_4.setBounds(689, 342, 140, 50);
		this.showJpanel.add(label_4);
		
		String[] columnNames = { "存入日", "起息日", "到期日", "存期", "利率",  "柜员号" };
		String[] columnNames1 = { "支取日", "本金支取金额", "利息", "本息合计", "任务号", "柜员号"};
		  Object[][] data = { { transBean.getCreateTime(), transBean.getCreateTime(), transBean.getEndTime(), transBean.getMonthsUpperStr(), transBean.getRate()+"%", GlobalParameter.tellerNo}};
		  Object[][] data1 = { { "", "", "", "", "",	""}};
			JPanel panel = new JPanel();
			panel.setBounds(97, 450, 883, 73);
			panel.setLayout(null);
			this.showJpanel.add(panel);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(0, 0, 883, 73);
			panel.add(scrollPane);
			
//			table_2 = new JTable(data, columnNames);
//			table_2.setRowHeight(50);//高度
//			//设置表格不可修改
//			table_2.setEnabled(false);
//			//字体居中
//			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
//			tcr.setHorizontalAlignment(JLabel.CENTER);
//			table_2.setDefaultRenderer(Object.class, tcr);
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
			table_2.getColumnModel().getColumn(0).setPreferredWidth(78);//设置款度
			scrollPane.setViewportView(table_2);
			
			

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
					okTrans(transBean);
				}

			});
			this.add(lblNewLabel);
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
			JLabel label_11 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
			label_11.addMouseListener(new MouseAdapter() {
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
			label_11.setBounds(1258, 770, 150, 50);
			this.add(label_11);
			JLabel lblNewLabel1 = new JLabel(transBean.getCardNo());
			lblNewLabel1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			lblNewLabel1.setBounds(374, 282, 309, 50);
			this.showJpanel.add(lblNewLabel1);
			
			JLabel lblNewLabel_1 = new JLabel("人民币"+transBean.getMoneyUpper());
			lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			lblNewLabel_1.setBounds(374, 342, 309, 50);
			this.showJpanel.add(lblNewLabel_1);
			
			JLabel lblNewLabel_2 = new JLabel(transBean.getCardName());
			lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			lblNewLabel_2.setBounds(828, 282, 231, 50);
			this.showJpanel.add(lblNewLabel_2);
			
			JLabel lblNewLabel_3 = new JLabel("CNY"+transBean.getMoney()+".00");
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			lblNewLabel_3.setBounds(828, 342, 231, 50);
			this.showJpanel.add(lblNewLabel_3);
			
			JLabel label_5 = new JLabel(transBean.getProductName());
			label_5.setFont(new Font("微软雅黑", Font.PLAIN, 24));
			label_5.setBounds(469, 222, 392, 50);
			this.showJpanel.add(label_5);

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
					transBean.setErrmsg("");
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
		//暂时跳转协议存款 积享存 录入页面  跳转私行需要判断
		openPanel(new MoneyBoxEnteringJXCPanel(transBean));
		
	}
	
	/**
	 * 确认
	 * */
	public void okTrans(PublicCashOpenBean transBean) {
		clearTimeText();
		openPanel(new MoneyBoxAgreementPage(transBean));
				
		
		
	}
	
	

}
