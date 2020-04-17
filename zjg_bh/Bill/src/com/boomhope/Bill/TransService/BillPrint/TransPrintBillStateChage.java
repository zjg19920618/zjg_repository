package com.boomhope.Bill.TransService.BillPrint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.JTableHeader;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Socket.SocketClient;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.ICBankBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.ICSubAccNo;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InResBean;
import com.boomhope.tms.message.in.bck.BCK0017ResBean;
import com.boomhope.tms.message.in.bck.BCK0017ResBodyBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @title 状态变更页
 */
public class TransPrintBillStateChage extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(TransPrintBillStateChage.class);
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTable table_1;
	private JTable table_2;
	JLabel chckbxNewCheckBox = null;
	JLabel chckbxNewCheckBox_1 =null;
	JLabel chckbxNewCheckBox_2 =null;
	Object[][] data = null;
	String str="";
	public TransPrintBillStateChage(final BillPrintBean transBean,final List<ICBankBean> bankBeans) {
		this.billPrintBean = transBean;
		this.bankBeans = bankBeans;
		logger.info("进入存单状态变更页");
		String[] columnNames = { "存入日", "起息日", "到期日", "存期", "利率", "支取方式", "柜员号" };
		int count = 0;
		for (ICBankBean ICAccNo :this.bankBeans) {
			//选中某张卡时
			if(ICAccNo.getICAccNo().equals(transBean.getAccNo())){
				List<ICSubAccNo> subAccNo = ICAccNo.getSubAccNo();
				data = new Object[subAccNo.size()][12];
				for (ICSubAccNo icSubAccNo : subAccNo) {
					String format = new DecimalFormat("###,###,###,###,###.00").format(Double.parseDouble(icSubAccNo.getATM()));

					String[] a = icSubAccNo.getOpenDate().split("\\/");
					data[count][0]=a[0]+a[1]+a[2];//存入日
					String[] b = icSubAccNo.getStartIntDate().split("\\/");
					data[count][1]=b[0]+b[1]+b[2];//起息日
					String[] c = icSubAccNo.getEndIntDate().split("\\/");
					data[count][2]=c[0]+c[1]+c[2];//到期日
					
					data[count][3]=icSubAccNo.getSavingCountMinStr();	
					if(icSubAccNo.getProductCode().trim().startsWith("A")){
						data[count][4]="详见提示"; //利率
					}else{
						data[count][4]=icSubAccNo.getOpenRate()+"%"; //利率
					}
					String accno=icSubAccNo.getDrawCond(); //支取方式
					if(accno=="0"){
						data[count][5]="无";
					}else if(accno.equals("1")){
						data[count][5]="密码";
					}
					data[count][6]="--"; //柜员号
					data[count][7]=icSubAccNo.getProductName(); ////储种
					data[count][8]=transBean.getCardName().trim();//户名
					data[count][9]=transBean.getAccNo()+"-"+icSubAccNo.getSubAccNo(); //账户
					data[count][10]="CNY"+format; //金额
					data[count][11]=icSubAccNo.getCheck(); //是否选中
					count+=1;
				}
			}
		}
		int a =data.length;
		int z = a%3;
		int z1= a/3;
		if(z!=0){
			transBean.setPage(z1+1);
			logger.info(z1+1);
		}else{
			transBean.setPage(z1);
			logger.info(z1);
		}
		
		// 字体
		JLabel	label28 = new JLabel("请选择要变更状态的存单");
		label28.setHorizontalAlignment(JLabel.CENTER);
		label28.setFont(new Font("微软雅黑", Font.BOLD, 40));
		label28.setForeground(Color.decode("#412174"));
		label28.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(label28);
		
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* 倒计时结束退出交易 */
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		/*
		 * 倒计时语音提示
		 */
		delayTimer.start();
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);
            	excuteVoice("voice/changState.wav");
            	
            }      
        });            
		voiceTimer.start(); 
		
		if(a>3*transBean.getN()){
			
		
			JPanel panel = new JPanel();
			panel.setBounds(107, 180, 775, 70);
			panel.setLayout(null);
			this.showJpanel.add(panel);		
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(0, 0, 775, 70);
			panel.add(scrollPane);	

			Object[] objects = data[3*transBean.getN()];
			Object[][] data21 ={objects};
			table = new JTable(data21, columnNames);
			JTableHeader header = table.getTableHeader();
			header.setFont(new Font("宋体", Font.PLAIN, 14));
			header.setPreferredSize(new Dimension(header.getWidth(),27));
			table.setFont(new Font("宋体", Font.PLAIN, 20));
			table.setBounds(107, 358, 600, 56);
			table.setRowHeight(40);//高度
			table.setEnabled(false);//设置只读的
			table.getColumnModel().getColumn(0).setPreferredWidth(78);//设置款度
			scrollPane.setViewportView(table);
			}else{}
		
		if(a>3*transBean.getN()+1){
			JPanel panel_1 = new JPanel();
			panel_1.setBounds(107, 326, 775, 70);
			panel_1.setLayout(null);
			this.showJpanel.add(panel_1);		
			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(0, 0, 775, 70);
			panel_1.add(scrollPane_1);
			
			Object[] objects1 = data[3*transBean.getN()+1];
			Object[][] data22 ={objects1};
			table_1 = new JTable(data22, columnNames);
			JTableHeader header = table_1.getTableHeader();
			header.setFont(new Font("宋体", Font.PLAIN, 14));
			header.setPreferredSize(new Dimension(header.getWidth(),27));
			table_1.setFont(new Font("宋体", Font.PLAIN, 20));
			table_1.setBounds(107, 350, 644, 56);
			table_1.setRowHeight(40);//高度
			table_1.setEnabled(false);//设置只读的
			table_1.getColumnModel().getColumn(0).setPreferredWidth(78);//设置款度
			scrollPane_1.setViewportView(table_1);
			}else{}
			if(a>3*transBean.getN()+2){
			JPanel panel_2 = new JPanel();
			panel_2.setBounds(107, 466, 775, 70);
			panel_2.setLayout(null);
			this.showJpanel.add(panel_2);		
			JScrollPane scrollPane_2 = new JScrollPane();
			scrollPane_2.setBounds(0, 0, 775, 70);
			panel_2.add(scrollPane_2);
			
			Object[] objects2 = data[3*transBean.getN()+2];
			Object[][] data23 ={objects2};
			table_2 = new JTable(data23, columnNames);
			JTableHeader header = table_2.getTableHeader();
			header.setFont(new Font("宋体", Font.PLAIN, 14));
			header.setPreferredSize(new Dimension(header.getWidth(),27));
			table_2.setFont(new Font("宋体", Font.PLAIN, 20));
			table_2.setBounds(57, 350, 644, 56);
			table_2.setRowHeight(40);//高度
			table_2.setEnabled(false);//设置只读的
			table_2.getColumnModel().getColumn(0).setPreferredWidth(78);//设置款度
			scrollPane_2.setViewportView(table_2);
			}else{}
			if(a>3*transBean.getN()){	
			JLabel lblNewLabel = new JLabel("储种:");
			lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
			lblNewLabel.setBounds(107, 125, 54, 30);
			this.showJpanel.add(lblNewLabel);
			
			JLabel lblNewLabel_1 = new JLabel("户名:");
			lblNewLabel_1.setFont(new Font("微软雅黑", Font.BOLD, 16));
			lblNewLabel_1.setBounds(107, 150, 54, 30);
			this.showJpanel.add(lblNewLabel_1);
			
			JLabel lblNewLabel_2 = new JLabel("账号:");
			lblNewLabel_2.setFont(new Font("微软雅黑", Font.BOLD, 16));
			lblNewLabel_2.setBounds(516, 125, 54, 30);
			this.showJpanel.add(lblNewLabel_2);
			
			JLabel lblNewLabel_3 = new JLabel("金额（小写）:");
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.BOLD, 16));
			lblNewLabel_3.setBounds(516, 150, 110, 30);
			this.showJpanel.add(lblNewLabel_3);
			}else{}
			if(a>3*transBean.getN()+1){	
			JLabel lblNewLabel_4 = new JLabel("储种:");
			lblNewLabel_4.setFont(new Font("微软雅黑", Font.BOLD, 16));
			lblNewLabel_4.setBounds(107, 267, 54, 31);
			this.showJpanel.add(lblNewLabel_4);
			
			JLabel lblNewLabel_6 = new JLabel("户名:");
			lblNewLabel_6.setFont(new Font("微软雅黑", Font.BOLD, 16));
			lblNewLabel_6.setBounds(107, 293, 54, 30);
			this.showJpanel.add(lblNewLabel_6);
			
			JLabel lblNewLabel_5 = new JLabel("账号:");
			lblNewLabel_5.setFont(new Font("微软雅黑", Font.BOLD, 16));
			lblNewLabel_5.setBounds(516, 267, 54, 31);
			this.showJpanel.add(lblNewLabel_5);
			
			JLabel lblNewLabel_7 = new JLabel("金额（小写）:");
			lblNewLabel_7.setFont(new Font("微软雅黑", Font.BOLD, 16));
			lblNewLabel_7.setBounds(516, 293, 110, 30);
			this.showJpanel.add(lblNewLabel_7);
			}else{}
			if(a>3*transBean.getN()+2){	
			JLabel lblNewLabel_8 = new JLabel("储种:");
			lblNewLabel_8.setFont(new Font("微软雅黑", Font.BOLD, 16));
			lblNewLabel_8.setBounds(107, 413, 54, 25);
			this.showJpanel.add(lblNewLabel_8);
			
			JLabel lblNewLabel_9 = new JLabel("户名:");
			lblNewLabel_9.setFont(new Font("微软雅黑", Font.BOLD, 16));
			lblNewLabel_9.setBounds(107, 435, 54, 30);
			this.showJpanel.add(lblNewLabel_9);
			
			JLabel lblNewLabel_10 = new JLabel("账号:");
			lblNewLabel_10.setFont(new Font("微软雅黑", Font.BOLD, 16));
			lblNewLabel_10.setBounds(516, 413, 54, 25);
			this.showJpanel.add(lblNewLabel_10);
			
			JLabel lblNewLabel_11 = new JLabel("金额（小写）:");
			lblNewLabel_11.setFont(new Font("微软雅黑", Font.BOLD, 16));
			lblNewLabel_11.setBounds(516, 438, 110, 25);
			this.showJpanel.add(lblNewLabel_11);
			}else{}
			if(a>3*transBean.getN()){	
			for (int i = 0+3*transBean.getN(); i < 1+3*transBean.getN(); i++) {
			Object[] objects41 = data[i];
			Object object110=objects41[7];
		JLabel lblNewLabel_16 = new JLabel(object110.toString());
		lblNewLabel_16.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lblNewLabel_16.setBounds(155, 125, 220, 30);
		this.showJpanel.add(lblNewLabel_16);
		}
		
			for (int i = 0+3*transBean.getN(); i < 1+3*transBean.getN(); i++) {
			Object[] objects42 = data[i];
			Object object111=objects42[8];
		JLabel lblNewLabel_17 = new JLabel(object111.toString());
		lblNewLabel_17.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lblNewLabel_17.setBounds(155, 150, 221, 30);
		this.showJpanel.add(lblNewLabel_17);
			}
			
			for (int i = 0+3*transBean.getN(); i < 1+3*transBean.getN(); i++) {
			Object[] objects42 = data[i];
			Object object112=objects42[9];
		JLabel lblNewLabel_18 = new JLabel(object112.toString());
		lblNewLabel_18.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lblNewLabel_18.setBounds(556, 125, 250, 30);
		this.showJpanel.add(lblNewLabel_18);
			}

			for (int i = 0+3*transBean.getN(); i < 1+3*transBean.getN(); i++) {
			Object[] objects43 = data[i];
			Object object113=objects43[10];
		JLabel lblNewLabel_19 = new JLabel(object113.toString());
		lblNewLabel_19.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lblNewLabel_19.setBounds(617, 150, 220, 30);
		this.showJpanel.add(lblNewLabel_19);
			}
			}else{}
			if(a>3*transBean.getN()+1){	
			for (int i = 1+3*transBean.getN(); i < 2+3*transBean.getN(); i++) {
			Object[] objects41 = data[i];
			Object object110=objects41[7];
		JLabel lblNewLabel_20 = new JLabel(object110.toString());
		lblNewLabel_20.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lblNewLabel_20.setBounds(155, 267, 220, 30);
		this.showJpanel.add(lblNewLabel_20);
			}
			for (int i = 1+3*transBean.getN(); i < 2+3*transBean.getN(); i++) {
			Object[] objects41 = data[i];
			Object object110=objects41[8];
		JLabel lblNewLabel_21 = new JLabel(object110.toString());
		lblNewLabel_21.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lblNewLabel_21.setBounds(155, 293, 220, 30);
		this.showJpanel.add(lblNewLabel_21);
			}
			for (int i = 1+3*transBean.getN(); i < 2+3*transBean.getN(); i++) {
			Object[] objects41 = data[i];
			Object object110=objects41[9];
		JLabel lblNewLabel_22 = new JLabel(object110.toString());
		lblNewLabel_22.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lblNewLabel_22.setBounds(556, 267, 250, 30);
		this.showJpanel.add(lblNewLabel_22);
			}
			for (int i = 1+3*transBean.getN(); i < 2+3*transBean.getN(); i++) {
			Object[] objects41 = data[i];
			Object object110=objects41[10];
		JLabel lblNewLabel_23 = new JLabel(object110.toString());
		lblNewLabel_23.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lblNewLabel_23.setBounds(619, 293, 216, 30);
		this.showJpanel.add(lblNewLabel_23);
			}
			}else{}
			if(a>3*transBean.getN()+2){	
			for (int i = 2+3*transBean.getN(); i < 3+3*transBean.getN(); i++) {
			Object[] objects41 = data[i];
			Object object110=objects41[7];
		JLabel lblNewLabel_24 = new JLabel(object110.toString());
		lblNewLabel_24.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lblNewLabel_24.setBounds(155, 413, 220, 25);
		this.showJpanel.add(lblNewLabel_24);
			}
			for (int i = 2+3*transBean.getN(); i < 3+3*transBean.getN(); i++) {
			Object[] objects41 = data[i];
			Object object110=objects41[8];
		JLabel lblNewLabel_25 = new JLabel(object110.toString());
		lblNewLabel_25.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lblNewLabel_25.setBounds(155, 438, 220, 25);
		this.showJpanel.add(lblNewLabel_25);
			}
			for (int i = 2+3*transBean.getN(); i < 3+3*transBean.getN(); i++) {
			Object[] objects41 = data[i];
			Object object110=objects41[9];
		JLabel lblNewLabel_26 = new JLabel(object110.toString());
		lblNewLabel_26.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lblNewLabel_26.setBounds(556, 413, 250, 25);
		this.showJpanel.add(lblNewLabel_26);
			}
			for (int i = 2+3*transBean.getN(); i < 3+3*transBean.getN(); i++) {
			Object[] objects41 = data[i];
			Object object110=objects41[10];
		JLabel lblNewLabel_27 = new JLabel(object110.toString());
		lblNewLabel_27.setFont(new Font("微软雅黑", Font.BOLD, 16));
		lblNewLabel_27.setBounds(617, 438, 220, 25);
		this.showJpanel.add(lblNewLabel_27);
			}
			}else{}
			
			if(a>3*transBean.getN()){	
			chckbxNewCheckBox = new JLabel();
			chckbxNewCheckBox.setBounds(890, 207, 30, 30);
			this.showJpanel.add(chckbxNewCheckBox);
			if((Boolean)data[3*transBean.getN()][11]){
				chckbxNewCheckBox.setIcon(new ImageIcon("pic/selected.png"));
			}else {
				chckbxNewCheckBox.setIcon(new ImageIcon("pic/wxz.png"));
			}
			chckbxNewCheckBox.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int i =(transBean.getN())*3;
					ICBankBean icBank = new ICBankBean();
					for ( ICBankBean icBankBean: bankBeans) {
						if(icBankBean.getICAccNo().equals(transBean.getAccNo())){
							icBank = icBankBean;
						}
					}
						
					if ((Boolean)data[3*transBean.getN()][11]){
						chckbxNewCheckBox.setIcon(new ImageIcon("pic/wxz.png"));
						data[3*transBean.getN()][11]=false;
						transBean.getSelect2().remove(icBank.getSubAccNo().get(i).getSubAccNo());
						icBank.getSubAccNo().get(i).setCheck(false);
						logger.debug("取消打印："+transBean.getSelect2());
						}else{
							chckbxNewCheckBox.setIcon(new ImageIcon("pic/selected.png"));
							data[3*transBean.getN()][11]=true;
							transBean.getSelect2().add(icBank.getSubAccNo().get(i).getSubAccNo());
							icBank.getSubAccNo().get(i).setCheck(true);
							logger.debug("增加打印："+transBean.getSelect2());
						}
					}
			});
			}else{}
			if(a>3*transBean.getN()+1){	
			chckbxNewCheckBox_1 = new JLabel();
			chckbxNewCheckBox_1.setBounds(890, 349, 30, 30);
			this.showJpanel.add(chckbxNewCheckBox_1);
			if((Boolean)data[3*transBean.getN()+1][11]){
				chckbxNewCheckBox_1.setIcon(new ImageIcon("pic/selected.png"));
			}else{
				chckbxNewCheckBox_1.setIcon(new ImageIcon("pic/wxz.png"));
			}
			chckbxNewCheckBox_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int i =(transBean.getN())*3+1;
					ICBankBean icBank = new ICBankBean();
					for ( ICBankBean icBankBean: bankBeans) {
						if(icBankBean.getICAccNo().equals(transBean.getAccNo())){
							icBank = icBankBean;
						}
					}
					if ((Boolean)data[3*transBean.getN()+1][11]){
						chckbxNewCheckBox_1.setIcon(new ImageIcon("pic/wxz.png"));
						data[3*transBean.getN()+1][11]=false;
						transBean.getSelect2().remove(icBank.getSubAccNo().get(i).getSubAccNo());
						icBank.getSubAccNo().get(i).setCheck(false);
						logger.debug("取消打印："+transBean.getSelect2());
						}else{
							chckbxNewCheckBox_1.setIcon(new ImageIcon("pic/selected.png"));
							data[3*transBean.getN()+1][11]=true;
							transBean.getSelect2().add(icBank.getSubAccNo().get(i).getSubAccNo());
							icBank.getSubAccNo().get(i).setCheck(true);
							logger.debug("选中打印："+transBean.getSelect2());
						}
				}
			});
			}else{}
			if(a>3*transBean.getN()+2){	
			chckbxNewCheckBox_2 = new JLabel();
			chckbxNewCheckBox_2.setBounds(890, 490, 30, 30);
			this.showJpanel.add(chckbxNewCheckBox_2);
			if((Boolean)data[3*transBean.getN()+2][11]){
				chckbxNewCheckBox_2.setIcon(new ImageIcon("pic/selected.png"));
			}else{
				chckbxNewCheckBox_2.setIcon(new ImageIcon("pic/wxz.png"));
			}
			chckbxNewCheckBox_2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int i =(transBean.getN())*3+2;
					ICBankBean icBank = new ICBankBean();
					for ( ICBankBean icBankBean: bankBeans) {
						if(icBankBean.getICAccNo().equals(transBean.getAccNo())){
							icBank = icBankBean;
						}
					}
					if ((Boolean)data[3*transBean.getN()+2][11]){
						chckbxNewCheckBox_2.setIcon(new ImageIcon("pic/wxz.png"));
						data[3*transBean.getN()+2][11]=false;
						transBean.getSelect2().remove(icBank.getSubAccNo().get(i).getSubAccNo());
						icBank.getSubAccNo().get(i).setCheck(false);
						logger.debug("取消打印："+transBean.getSelect2());
						}else{
							chckbxNewCheckBox_2.setIcon(new ImageIcon("pic/selected.png"));
							data[3*transBean.getN()+2][11]=true;
							transBean.getSelect2().add(icBank.getSubAccNo().get(i).getSubAccNo());
							icBank.getSubAccNo().get(i).setCheck(true);
							logger.debug("选中打印："+transBean.getSelect2());
						}
				}
			});
			}else{}
		
		
		//上一步
		JLabel label = new JLabel(new ImageIcon("pic/preStep.png"));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");				
				backTrans(transBean);
			}

		});
		label.setBounds(1075, 770, 150, 50);
		this.add(label);
			//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");				
				returnHome();
			}

		});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);

		
		
	
		
		/*添加修饰样式*/
		//向左
		JLabel left=new JLabel();
		left.setIcon(new ImageIcon("pic/newPic/left.png"));
		left.setBounds(30,265 ,57, 98);
		left.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击向左按钮");
				upStep(transBean);
			}
		});
		this.showJpanel.add(left);
	   
		//向右
		JLabel right=new JLabel();
		right.setIcon(new ImageIcon("pic/newPic/right.png"));
		right.setBounds(922,265,57, 98);
		right.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击向右按钮");
				nextStep(transBean);		
			}
		});
		this.showJpanel.add(right);
		
		//确认
				JLabel lblNewLabel1 = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
				lblNewLabel1.setBounds(890, 770, 150, 50);
				lblNewLabel1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						/* 处理下一页 */
						logger.info("点击确认按钮");	
						stateChage(transBean);
					}

				});
				this.add(lblNewLabel1);
		
		
	}
	
	
	
	
	/**
	 * 上一页
	 * @return 
	 */
	public void  upStep(BillPrintBean transBean){
		 int n =transBean.getN();
		 if (n==0){
			return ;
		 }else{
			 transBean.setN(n-1);
		 }
		 transBean.setVoice("1");
		 clearTimeText();
		 openPanel(new TransPrintBillStateChage(transBean, bankBeans));
	}
	
	/**
	 * 下一页
	 */
	public void  nextStep(BillPrintBean transBean){
		int w =transBean.getPage();
		int n =transBean.getN();
		if(n+1<w){
			 transBean.setN(n+1);
		}else{
			return;
		}
		transBean.setVoice("1");
		clearTimeText();
		openPanel(new TransPrintBillStateChage(transBean, bankBeans));
	}
	/**
	 * 上一步
	 */
	public void  backTrans(BillPrintBean transBean){
		transBean.setN(0);
		transBean.setX(0);
		transBean.setPage(0);
		transBean.getSelect2().clear();
		transBean.setVoice("0");
		openPanel(new TransPrintOrStateChage(transBean));
	}
	
	/**
	 * 状态变更
	 */
	public void  stateChage(BillPrintBean transBean){
		if(transBean.getSelect2().size() == 0){
			//未选中状态变更
			openMistakeDialog("请选择要变更状态的存单");
			return;
		}
		//调状态变更接口
		Map<String,String>map = new HashMap<String,String>();
		for (Object select2 : transBean.getSelect2()){
			map.put("acctNo",transBean.getAccNo() );//卡号
			map.put("subAcctNo",(String)select2); //子账号
			map.put("operChoose","0" ); //状态选择
			transBean.getReqMCM001().setReqBefor("03520");
			BCK0017ResBean bck0017 = BCK0017(map);
			transBean.getReqMCM001().setReqAfter((String)bck0017.getHeadBean().getResCode(),(String)bck0017.getHeadBean().getResMsg());
			if(!"000000".equals(bck0017.getHeadBean().getResCode())){
				//状态变更失败页
				String resMsg = bck0017.getHeadBean().getResMsg();
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop("请联系大堂经理",resMsg,"");
				logger.error("状态变更失败!");
				return;	
			}
		}
		//状态变更正确信息页
		success(transBean);
	}
	
	/*状态变更接口*/
	public BCK0017ResBean BCK0017(Map<String,String>map){
		SocketClient sc = new SocketClient();
		Socket socket = null;
		try {
			socket = sc.createSocket();
			// 发送请求
			sc.sendRequest(socket,sc.BCK_0017(map));
			// 响应
			String retMsg = sc.response(socket);
			
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK0017ResBean.class);
			reqXs.alias("Head", InResBean.class);
			reqXs.alias("Body", BCK0017ResBodyBean.class);
			BCK0017ResBean bck0017ResBean = (BCK0017ResBean)reqXs.fromXML(retMsg);
			logger.info(bck0017ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
			return bck0017ResBean;
		} catch (UnknownHostException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			sc.closeSocket(socket);
		}
		return null;
	}
	/**状态变更成功页*/
	public void success(BillPrintBean transBean){
		transBean.setN(0);
		transBean.setX(0);
		transBean.setPage(0);
		transBean.setVoice("0");
		transBean.getSelect2().clear();
		openPanel(new StateChangePanels(transBean));
		
	}

	
}
