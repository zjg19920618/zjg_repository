package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
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

import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.AccZYDRulePanel;
import com.boomhope.Bill.Util.Property;

/***
 * 积享存存入成功页面
 * @author gyw
 *
 */
public class MoneyBoxJXCSuccessPage extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxJXCSuccessPage.class);
	private JTable table;
	private JTable table_1;
	private JTable table_2;
	private JTable table_3;
	
	private AccZYDRulePanel zydrp = null;//增益豆规则
	
	private JLabel billImage;//动画图片
	private JLabel promptLabel;//存入成功提示
	private JLabel accImage;//图片
	private JLabel lblNewLabel_1;//提示语
	private JPanel panel;//表格面板
	private JPanel panel1;//第二行表格面板
	private JLabel svrNoLabel;//流水号显示
	private JLabel zydrComfirm;//增益豆规则显示确认按钮
	private JLabel tdTotalCount;//唐豆赠送数量
	private JLabel tdinfoCount;//唐豆明细
	private JLabel rlueLabel;//规则提示
	private JLabel rlueButton;//规则按钮
	private JLabel labelTitle;//扣回规则标题
	private int y=350;
	
	private boolean on_off=true;//开关控制
	public MoneyBoxJXCSuccessPage(final PublicCashOpenBean transBean){
		this.cashBean= transBean;
		java.text.DecimalFormat  df1= new java.text.DecimalFormat("0.0");
		/* 加载凭证动画 */
		billImage = new JLabel("");   
		billImage.setIcon(new ImageIcon("pic/accomplish.png"));
		billImage.setIconTextGap(6);
		billImage.setBounds(392, 62, 50, 50);
		this.showJpanel.add(billImage);
		
		/* 提示信息 */
		promptLabel = new JLabel("存入成功");
		promptLabel.setHorizontalAlignment(JLabel.CENTER);
		promptLabel.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		promptLabel.setForeground(Color.decode("#412174"));
		promptLabel.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(promptLabel);
		//图片
		accImage = new JLabel(); 
		accImage.setIcon(new ImageIcon("pic/ico_wxts.png"));
		accImage.setIconTextGap(6);
		accImage.setBounds(205, 133, 36, 36);
		this.showJpanel.add(accImage);
		
		lblNewLabel_1 = new JLabel("温馨提示：如需纸质存款协议说明，您可到回单机上自助进行打印。");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lblNewLabel_1.setForeground(Color.red);
		lblNewLabel_1.setBounds(251, 129, 619, 50);
		this.showJpanel.add(lblNewLabel_1);
		
		
		String[] columnNames = { "子账户","存入日", "起息日", "到期日", "存期", "利率",  "柜员号" };
		String[] columnNames1 = { "卡号", "户名", "金额", "产品名称", "关联产品名称"};
		  Object[][] data = { { transBean.getSubAcctNo2(),transBean.getCreateTime(), transBean.getCreateTime(), transBean.getEndTime(), transBean.getMonthsUpperStr(), transBean.getRate(), GlobalParameter.tellerNo}};
		  Object[][] data1 = { { transBean.getCardNo(), transBean.getCardName(), transBean.getMoney(), transBean.getProductName(), transBean.getRelaAcctName()}};
			panel = new JPanel();
			panel.setBounds(97, 300, 883, 73);
			panel.setLayout(null);
			this.showJpanel.add(panel);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(0, 0, 883, 73);
			panel.add(scrollPane);
			
			table_2 = new JTable(data, columnNames);
			JTableHeader header = table_2.getTableHeader();
			header.setFont(new Font("宋体", Font.PLAIN, 14));
			header.setPreferredSize(new Dimension(header.getWidth(),27));
			table_2.setFont(new Font("宋体", Font.PLAIN, 16));//改动
			table_2.setRowHeight(43);//高度
			table_2.setEnabled(false);
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(JLabel.CENTER);
			table_2.setDefaultRenderer(Object.class, tcr);
			table_2.getColumnModel().getColumn(0).setPreferredWidth(78);//设置款度
			scrollPane.setViewportView(table_2);
			
			
			
			//第2行
			panel1 = new JPanel();
			panel1.setBounds(97, 200, 883, 73);
			panel1.setLayout(null);
			this.showJpanel.add(panel1);
			
			JScrollPane scrollPane1 = new JScrollPane();
			scrollPane1.setBounds(0, 0, 883, 73);
			panel1.add(scrollPane1);
			
			table_3 = new JTable(data1, columnNames1);
			JTableHeader header1 = table_3.getTableHeader();
			header1.setFont(new Font("宋体", Font.PLAIN, 14));
			header1.setPreferredSize(new Dimension(header.getWidth(),27));
			table_3.setFont(new Font("宋体", Font.PLAIN, 16));//改动
			table_3.setRowHeight(43);//高度
			table_3.setEnabled(false);
			DefaultTableCellRenderer tcr1 = new DefaultTableCellRenderer();
			tcr1.setHorizontalAlignment(JLabel.CENTER);
			table_3.setDefaultRenderer(Object.class, tcr1);
			table_3.getColumnModel().getColumn(0).setPreferredWidth(78);//设置款度
			scrollPane1.setViewportView(table_3);

			
			if(transBean.getTdTotalCount()!=null && !"".equals(transBean.getTdTotalCount())){
				if(Double.valueOf(transBean.getTdTotalCount())==0){
					logger.info("唐豆数量为0");
				}else{
					
					//加载增益豆规则
					zydrp = new AccZYDRulePanel(showJpanel,Property.acc_zyd_rules_path);
					zydrp.getJsp().setVisible(false);
					
					tdTotalCount = new JLabel("<HTML><font color='#333333'>您购买此产品共获赠</font><font color='#ff0000'>"
							//+ transBean.getTdTotalCount()
							+ df1.format(Double.valueOf(transBean.getTdTotalCount()))
							+ "</font><font color='#333333'>" + "个唐豆,"
							+ "</font></HTML>");
					tdTotalCount.setHorizontalAlignment(SwingConstants.CENTER);
					tdTotalCount.setFont(new Font("微软雅黑", Font.PLAIN, 30));
					tdTotalCount.setForeground(Color.decode("#333333"));
					tdTotalCount.setBounds(0, y+=50, 1009, 30);
					this.showJpanel.add(tdTotalCount);
					
					StringBuffer tdStr =new StringBuffer("含");
					if(transBean.getInterestCount()!=null && !"".equals(transBean.getInterestCount()) && Double.valueOf(transBean.getInterestCount())>0){
						//tdStr.append("幸运豆"+transBean.getInterestCount().trim()+"元、");
						tdStr.append("幸运豆"+df1.format(Double.valueOf(transBean.getInterestCount()))+"元、");
					}
					if(transBean.getZydCount()!=null && !"".equals(transBean.getZydCount()) && Double.valueOf(transBean.getZydCount())>0){
						//tdStr.append("增益豆"+transBean.getZydCount().trim()+"元、");
						tdStr.append("增益豆"+df1.format(Double.valueOf(transBean.getZydCount()))+"元、");
					}
					if(transBean.getXfdCount()!=null && !"".equals(transBean.getXfdCount()) && Double.valueOf(transBean.getXfdCount())>0){
						//tdStr.append("消费豆"+transBean.getXfdCount()+"个、");
						tdStr.append("消费豆"+df1.format(Double.valueOf(transBean.getXfdCount()))+"个、");
					}
					tdStr.deleteCharAt(tdStr.length()-1);
					tdStr.append(",");
					
					tdinfoCount =new JLabel(tdStr.toString());
					tdinfoCount.setBounds(0, y+=50, 1009, 30);
					tdinfoCount.setFont(new Font("微软雅黑",Font.PLAIN,30));
					tdinfoCount.setHorizontalAlignment(SwingUtilities.CENTER);
					this.showJpanel.add(tdinfoCount);
					
					labelTitle = new JLabel("");
					labelTitle.setBounds((showJpanel.getWidth()-250)/2, 20, 250, 40);
					labelTitle.setFont(new Font("微软雅黑",Font.BOLD,30));
					labelTitle.setHorizontalAlignment(SwingUtilities.CENTER);
					labelTitle.setVisible(false);
					this.showJpanel.add(labelTitle);
					
					rlueLabel =new JLabel("若该笔存款提前支取将扣回唐豆，具体标准见  ");
					rlueLabel.setBounds(0, y+=50, 690, 30);
					rlueLabel.setFont(new Font("微软雅黑",Font.PLAIN,30));
					rlueLabel.setHorizontalAlignment(SwingUtilities.RIGHT);
					this.showJpanel.add(rlueLabel);
					
					rlueButton = new JLabel("  唐豆扣回规则(链接)");
					rlueButton.setBounds(690, y, 300, 30);
					rlueButton.setFont(new Font("微软雅黑",Font.BOLD,30));
					rlueButton.setHorizontalAlignment(SwingUtilities.LEFT);
					rlueButton.setForeground(Color.red);
					rlueButton.setBackground(Color.BLUE);
					this.showJpanel.add(rlueButton);
					
					rlueButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							logger.info("点击扣回规则按钮");
							zydrp.getJsp().setVisible(true);
							showRule();
						};
					});
					
					/* 确认 */
					zydrComfirm = new JLabel(new ImageIcon("pic/affirm.png"));
					zydrComfirm.setBounds((showJpanel.getWidth()-200)/2, showJpanel.getHeight()-70, 200, 50);
					zydrComfirm.setVisible(false);
					this.showJpanel.add(zydrComfirm);
					zydrComfirm.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							logger.info("点击规则确认按钮");
							zydrp.getJsp().setVisible(false);
							notShowRule();
						}
					});
					
				}
			}
			
			/*开户流水号*/
			svrNoLabel = new JLabel("开户流水号："+transBean.getSvrJrnlNo());
			svrNoLabel.setBounds(0, y+=50, 1059, 30);
			svrNoLabel.setHorizontalAlignment(SwingUtilities.CENTER);
			svrNoLabel.setFont(new Font("微软雅黑",Font.BOLD,20));
			this.showJpanel.add(svrNoLabel);
			
			/* 倒计时打开语音 */
			voiceTimer = new Timer(voiceSecond, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stopTimer(voiceTimer);
					excuteVoice("voice/ok.wav");

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
					GlobalParameter.OFF_ON=true;
					returnHome();
				}

			});
			label_1.setBounds(1258, 770, 150, 50);
			this.add(label_1);
		
	}
	
	//点击扣回规则显示规则明细
	public void showRule(){
		zydrComfirm.setVisible(true);
		labelTitle.setVisible(true);
		
		billImage.setVisible(false);
		promptLabel.setVisible(false);
		accImage.setVisible(false);
		lblNewLabel_1.setVisible(false);
		panel.setVisible(false);
		panel1.setVisible(false);
		svrNoLabel.setVisible(false);
		tdTotalCount.setVisible(false);
		rlueLabel.setVisible(false);
		rlueButton.setVisible(false);
		tdinfoCount.setVisible(false);
	}
	
	//点击规则确认按钮退出规则明细
	public void notShowRule(){
		zydrComfirm.setVisible(false);
		labelTitle.setVisible(false);
		
		billImage.setVisible(true);
		promptLabel.setVisible(true);
		accImage.setVisible(true);
		lblNewLabel_1.setVisible(true);
		panel.setVisible(true);
		panel1.setVisible(true);
		svrNoLabel.setVisible(true);
		tdTotalCount.setVisible(true);
		rlueLabel.setVisible(true);
		rlueButton.setVisible(true);
		tdinfoCount.setVisible(true);
	}
	
	
	
}

