package com.boomhope.Bill.TransService.CashOpen.moneybox;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.TransService.CashOpen.Interface.AccountDepositApi;
import com.boomhope.Bill.TransService.CashOpen.Interface.ProductRateInfo1;
import com.boomhope.Bill.TransService.CashOpen.Interface.ProductRateInfo2;
import com.boomhope.Bill.Util.MoneyUtils;
import com.boomhope.Bill.Util.UtilButton;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.message.account.in.BCK02867ResBean;
/***
 * 私行如意存系列页面(分页)
 * 
 * @author gyw
 *
 */
public class MoneyBoxSHRYCXLPanel extends BaseTransPanelNew{

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxSHRYCXLPanel.class);
	JLabel lblNewLabel_1 = null; 
	JLabel lblNewLabel_3 = null; 
	JLabel lblNewLabel_5 = null; 
	JLabel lblNewLabel_7 = null; 
	JLabel lblNewLabel_9 = null; 
	JLabel lblNewLabel_11 = null; 
	JLabel lblNewLabel_13 = null; 
	JLabel lblNewLabel_15 = null; 
	JLabel lblNewLabel_17 = null; 
	JLabel lblNewLabel_19 = null; 
	JLabel lblNewLabel_21 = null; 
	JLabel lblNewLabel_23 = null;
	JLabel lblNewLabel_25 = null;
	JLabel lblNewLabel_27 = null;
	JLabel lblNewLabel_29 = null;
	JLabel lblNewLabel_31 = null;
	JLabel lblNewLabel_33 = null;
	JLabel lblNewLabel_35 = null;
	JLabel lblNewLabel_37 = null;
	JLabel lblNewLabel_39 = null;
	JLabel lblNewLabel_41 = null;
	JLabel lblNewLabel_43 = null;
	JLabel lblNewLabel_45 = null;
	JLabel lblNewLabel_47 = null;
	
	Object objecta1;
	Object objecta2;
	Object objecta3;
	Object objecta4;
	Object objecta5;
	Object objecta6;
	Object objectb1;
	Object objectb2;
	Object objectb3;
	Object objectb4;
	Object objectb5;
	Object objectb6;
	
	private boolean on_off=true;//开关控制
	public MoneyBoxSHRYCXLPanel(final PublicCashOpenBean transBean) {
		this.cashBean = transBean;
		 
		List<ProductRateInfo1> list= (List<ProductRateInfo1>) transBean.getAccountList().get(AccountDepositApi.KEY_PRODUCT_RATES);
		int count = 0;
		Object[][] data = new Object[list.size()][7]; 
		 for(ProductRateInfo1 productRateInfo1 : list){
			 data[count][0] = productRateInfo1.getProductCodeName().trim();//产品名称
			 data[count][1] = productRateInfo1.getSavingCountMinStr().trim(); //存期(年/月/日)			 
			 data[count][2] = productRateInfo1.getStartMoney().trim();//起存金额
			 data[count][3] = productRateInfo1.getInterestRateRangeStr().trim();//年利率
			 data[count][4] = productRateInfo1.getProductCode().trim();//产品代码
			 data[count][5] = productRateInfo1.getSavingCountMin().trim();//存期(Y/M/D)
			 data[count][6] = productRateInfo1.getStartMoneyStr().trim();//金额()
			 
			 count += 1;
		 }
		
			int a = data.length;
			int z = a % 6;
			int z1 = a / 6;
			if (z != 0) {
				transBean.setSkhrypage(z1 + 1);
				System.out.println(z1 + 1);
			} else {
				transBean.setSkhrypage(z1);
				System.out.println(z1);
			}
			
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				on_off=false;
				/* 倒计时结束退出交易 */
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		delayTimer.start();
		if(a>6*transBean.getSkhryn()){
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(30, 160, 320, 160);
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(43, 174, 241));
		this.showJpanel.add(panel_1);
		panel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				test(transBean, lblNewLabel_1.getText(),lblNewLabel_3.getText(),objecta1,objectb1);
			}

		});
		JLabel lblNewLabel = new JLabel("产品名称：");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 23, 79, 20);
		panel_1.add(lblNewLabel);
		
		for (int i = 0 + 6 * transBean.getN() - 6 * transBean.getX(); i < 1
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object1 = objects[0];
			
			objecta1 = objects[5];
			objectb1 = objects[4];
			
			lblNewLabel_1 = new JLabel(object1.toString());
			lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_1.setBounds(95, 23, 215, 20);
			panel_1.add(lblNewLabel_1);
		}
		
		JLabel lblNewLabel_2 = new JLabel("存      期：");
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(10, 51, 79, 20);
		panel_1.add(lblNewLabel_2);
		
		for (int i = 0 + 6 * transBean.getN() - 6 * transBean.getX(); i < 1
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object2 = objects[1];
			lblNewLabel_3 = new JLabel(object2.toString());
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_3.setBounds(95, 51, 215, 20);
			panel_1.add(lblNewLabel_3);
		}
		
		JLabel lblNewLabel_4 = new JLabel("起存金额：");
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(10, 76, 75, 20);
		panel_1.add(lblNewLabel_4);
		
		for (int i = 0 + 6 * transBean.getN() - 6 * transBean.getX(); i < 1
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object3 = objects[2];
			lblNewLabel_5 = new JLabel(object3.toString());
			lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_5.setBounds(10, 101, 79, 20);
			panel_1.add(lblNewLabel_5);
		}
		
		JLabel lblNewLabel_6 = new JLabel("年利率：");
		lblNewLabel_6.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_6.setBounds(10, 124, 79, 20);
		panel_1.add(lblNewLabel_6);
		
		for (int i = 0 + 6 * transBean.getN() - 6 * transBean.getX(); i < 1
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object4 = objects[3];
			lblNewLabel_7 = new JLabel(object4.toString());
			lblNewLabel_7.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_7.setBounds(95, 101, 215, 20);
			panel_1.add(lblNewLabel_7);
		}
		}else{}
		if(a>6*transBean.getSkhryn()+1){
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(366, 160, 320, 160);
		panel_2.setBackground(new Color(43, 174, 241));
		panel_2.setLayout(null);
		this.showJpanel.add(panel_2);
		panel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				test(transBean, lblNewLabel_9.getText(),lblNewLabel_11.getText(),objecta2,objectb2);
			}

		});
		JLabel lblNewLabel_8 = new JLabel("产品名称：");
		lblNewLabel_8.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_8.setBounds(10, 23, 80, 20);
		panel_2.add(lblNewLabel_8);
		
		for (int i = 1 + 6 * transBean.getN() - 6 * transBean.getX(); i < 2
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object1 = objects[0];
			
			objecta2 = objects[5];
			objectb2 = objects[4];
			
			lblNewLabel_9 = new JLabel(object1.toString());
			lblNewLabel_9.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_9.setBounds(95, 23, 215, 20);
			panel_2.add(lblNewLabel_9);
		}
		
		JLabel lblNewLabel_10 = new JLabel("存      期：");
		lblNewLabel_10.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_10.setBounds(10, 48, 80, 20);
		panel_2.add(lblNewLabel_10);
		
		for (int i = 1 + 6 * transBean.getN() - 6 * transBean.getX(); i < 2
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object2 = objects[1];
			lblNewLabel_11 = new JLabel(object2.toString());
			lblNewLabel_11.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_11.setBounds(95, 51, 215, 20);
			panel_2.add(lblNewLabel_11);
		}
		
		JLabel lblNewLabel_12 = new JLabel("起存金额：");
		lblNewLabel_12.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_12.setBounds(10, 73, 80, 20);
		panel_2.add(lblNewLabel_12);
		
		for (int i = 0 + 6 * transBean.getN() - 6 * transBean.getX(); i < 2
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object3 = objects[2];
			lblNewLabel_13 = new JLabel(object3.toString());
			lblNewLabel_13.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_13.setBounds(95, 76, 215, 20);
			panel_2.add(lblNewLabel_13);
		}
		
		JLabel lblNewLabel_14 = new JLabel("年  利 率：");
		lblNewLabel_14.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_14.setBounds(10, 98, 80, 20);
		panel_2.add(lblNewLabel_14);
		
		for (int i = 1 + 6 * transBean.getN() - 6 * transBean.getX(); i < 2
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object4 = objects[3];
			lblNewLabel_15 = new JLabel(object4.toString());
			lblNewLabel_15.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_15.setBounds(95, 101, 215, 20);
			panel_2.add(lblNewLabel_15);
		}
		}else{}
		if(a>6*transBean.getSkhryn()+2){
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(703, 160, 320, 160);
		panel_3.setLayout(null);
		panel_3.setBackground(new Color(43, 174, 241));
		this.showJpanel.add(panel_3);
		panel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				test(transBean, lblNewLabel_17.getText(),lblNewLabel_19.getText(),objecta3,objectb3);
			}

		});
		JLabel lblNewLabel_16 = new JLabel("产品名称：");
		lblNewLabel_16.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_16.setBounds(10, 22, 81, 20);
		panel_3.add(lblNewLabel_16);
		
		for (int i = 2 + 6 * transBean.getN() - 6 * transBean.getX(); i < 3
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object1 = objects[0];
			
			objecta3 = objects[5];
			objectb3 = objects[4];
			
			lblNewLabel_17 = new JLabel(object1.toString());
			lblNewLabel_17.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_17.setBounds(95, 23, 215, 20);
			panel_3.add(lblNewLabel_17);
		}
		
		JLabel lblNewLabel_18 = new JLabel("存      期：");
		lblNewLabel_18.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_18.setBounds(10, 47, 81, 20);
		panel_3.add(lblNewLabel_18);
		
		for (int i = 2 + 6 * transBean.getN() - 6 * transBean.getX(); i < 3
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object2 = objects[1];
			lblNewLabel_19 = new JLabel(object2.toString());
			lblNewLabel_19.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_19.setBounds(95, 51, 215, 20);
			panel_3.add(lblNewLabel_19);
		}
		
		JLabel lblNewLabel_20 = new JLabel("起存金额：");
		lblNewLabel_20.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_20.setBounds(10, 72, 81, 20);
		panel_3.add(lblNewLabel_20);
		
		for (int i = 2 + 6 * transBean.getN() - 6 * transBean.getX(); i <3
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object3 = objects[2];
			lblNewLabel_21 = new JLabel(object3.toString());
			lblNewLabel_21.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_21.setBounds(95, 76, 215, 20);
			panel_3.add(lblNewLabel_21);
		}
		
		JLabel lblNewLabel_22 = new JLabel("年  利 率：");
		lblNewLabel_22.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_22.setBounds(10, 101, 81, 20);
		panel_3.add(lblNewLabel_22);
		
		for (int i = 2 + 6 * transBean.getN() - 6 * transBean.getX(); i < 3
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object4 = objects[3];
			lblNewLabel_23 = new JLabel(object4.toString());
			lblNewLabel_23.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_23.setBounds(95, 101, 215, 20);
			panel_3.add(lblNewLabel_23);
		}
		}else{}
		if(a>6*transBean.getSkhryn()+3){
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(30, 350, 320, 160);
		panel_4.setBackground(new Color(43, 174, 241));
		panel_4.setLayout(null);
		this.showJpanel.add(panel_4);
		panel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				test(transBean, lblNewLabel_25.getText(),lblNewLabel_27.getText(),objecta4,objectb4);
			}

		});
		JLabel lblNewLabel_24 = new JLabel("产品名称：");
		lblNewLabel_24.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_24.setBounds(10, 22, 81, 20);
		panel_4.add(lblNewLabel_24);
		
		for (int i = 3 + 6 * transBean.getN() - 6 * transBean.getX(); i < 4
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object1 = objects[0];
			
			objecta4 = objects[5];
			objectb4 = objects[4];
			
			lblNewLabel_25 = new JLabel(object1.toString());
			lblNewLabel_25.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_25.setBounds(95, 23, 215, 20);
			panel_4.add(lblNewLabel_25);
		}
		
		JLabel lblNewLabel_26 = new JLabel("存      期：");
		lblNewLabel_26.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_26.setBounds(10, 47, 81, 20);
		panel_4.add(lblNewLabel_26);
		
		for (int i = 3 + 6 * transBean.getN() - 6 * transBean.getX(); i < 4
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object2 = objects[1];
			lblNewLabel_27 = new JLabel(object2.toString());
			lblNewLabel_27.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_27.setBounds(95, 51, 215, 20);
			panel_4.add(lblNewLabel_27);
		}
		
		JLabel lblNewLabel_28 = new JLabel("起存金额：");
		lblNewLabel_28.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_28.setBounds(10, 72, 81, 20);
		panel_4.add(lblNewLabel_28);
		
		for (int i = 3 + 6 * transBean.getN() - 6 * transBean.getX(); i <4
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object3 = objects[2];
			lblNewLabel_29 = new JLabel(object3.toString());
			lblNewLabel_29.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_29.setBounds(95, 76, 215, 20);
			panel_4.add(lblNewLabel_29);
		}
		
		JLabel lblNewLabel_30 = new JLabel("年  利 率：");
		lblNewLabel_30.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_30.setBounds(10, 101, 81, 20);
		panel_4.add(lblNewLabel_30);
		
		for (int i = 3 + 6 * transBean.getN() - 6 * transBean.getX(); i < 4
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object4 = objects[3];
			lblNewLabel_31 = new JLabel(object4.toString());
			lblNewLabel_31.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_31.setBounds(95, 101, 215, 20);
			panel_4.add(lblNewLabel_31);
		}
		}else{}
		if(a>6*transBean.getSkhryn()+4){
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(366, 350, 320, 160);
		panel_5.setBackground(new Color(43, 174, 241));
		panel_5.setLayout(null);
		this.showJpanel.add(panel_5);
		panel_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				test(transBean, lblNewLabel_33.getText(),lblNewLabel_35.getText(),objecta5,objectb5);
			}

		});
		JLabel lblNewLabel_32 = new JLabel("产品名称：");
		lblNewLabel_32.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_32.setBounds(10, 22, 81, 20);
		panel_5.add(lblNewLabel_32);
		
		for (int i = 4 + 6 * transBean.getN() - 6 * transBean.getX(); i < 5
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object1 = objects[0];
			
			objecta5 = objects[5];
			objectb5 = objects[4];
			
			lblNewLabel_33 = new JLabel(object1.toString());
			lblNewLabel_33.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_33.setBounds(95, 23, 215, 20);
			panel_5.add(lblNewLabel_33);
		}
		
		JLabel lblNewLabel_34 = new JLabel("存      期：");
		lblNewLabel_34.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_34.setBounds(10, 47, 81, 20);
		panel_5.add(lblNewLabel_34);
		
		for (int i = 4 + 6 * transBean.getN() - 6 * transBean.getX(); i < 5
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object2 = objects[1];
			lblNewLabel_35 = new JLabel(object2.toString());
			lblNewLabel_35.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_35.setBounds(95, 51, 215, 20);
			panel_5.add(lblNewLabel_35);
		}
		
		JLabel lblNewLabel_36 = new JLabel("起存金额：");
		lblNewLabel_36.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_36.setBounds(10, 72, 81, 20);
		panel_5.add(lblNewLabel_36);
		
		for (int i = 4 + 6 * transBean.getN() - 6 * transBean.getX(); i <5
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object3 = objects[2];
			lblNewLabel_37 = new JLabel(object3.toString());
			lblNewLabel_37.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_37.setBounds(95, 76, 215, 20);
			panel_5.add(lblNewLabel_37);
		}
		
		JLabel lblNewLabel_38 = new JLabel("年  利 率：");
		lblNewLabel_38.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_38.setBounds(10, 101, 81, 20);
		panel_5.add(lblNewLabel_38);
		
		for (int i = 4 + 6 * transBean.getN() - 6 * transBean.getX(); i < 5
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object4 = objects[3];
			lblNewLabel_39 = new JLabel(object4.toString());
			lblNewLabel_39.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_39.setBounds(95, 101, 215, 20);
			panel_5.add(lblNewLabel_39);
		}
		}else{}
		if(a>6*transBean.getSkhryn()+5){
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(703, 350, 320, 160);
		panel_6.setBackground(new Color(43, 174, 241));
		panel_6.setLayout(null);
		this.showJpanel.add(panel_6);
		panel_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				test(transBean, lblNewLabel_41.getText(),lblNewLabel_43.getText(),objecta6,objectb6);
			}

		});
		JLabel lblNewLabel_40 = new JLabel("产品名称：");
		lblNewLabel_40.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_40.setBounds(10, 22, 81, 20);
		panel_6.add(lblNewLabel_40);
		
		for (int i = 5 + 6 * transBean.getN() - 6 * transBean.getX(); i < 6
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object1 = objects[0];
			
			objecta6 = objects[5];
			objectb6 = objects[4];
			
			lblNewLabel_41 = new JLabel(object1.toString());
			lblNewLabel_41.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_41.setBounds(95, 23, 215, 20);
			panel_6.add(lblNewLabel_41);
		}
		
		JLabel lblNewLabel_42 = new JLabel("存      期：");
		lblNewLabel_42.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_42.setBounds(10, 47, 81, 20);
		panel_6.add(lblNewLabel_42);
		
		for (int i = 5 + 6 * transBean.getN() - 6 * transBean.getX(); i < 6
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object2 = objects[1];
			lblNewLabel_43 = new JLabel(object2.toString());
			lblNewLabel_43.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_43.setBounds(95, 51, 215, 20);
			panel_6.add(lblNewLabel_43);
		}
		
		JLabel lblNewLabel_44 = new JLabel("起存金额：");
		lblNewLabel_44.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_44.setBounds(10, 72, 81, 20);
		panel_6.add(lblNewLabel_44);
		
		for (int i = 5 + 6 * transBean.getN() - 6 * transBean.getX(); i <6
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object3 = objects[2];
			lblNewLabel_45 = new JLabel(object3.toString());
			lblNewLabel_45.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_45.setBounds(95, 76, 215, 20);
			panel_6.add(lblNewLabel_45);
		}
		
		JLabel lblNewLabel_46 = new JLabel("年  利 率：");
		lblNewLabel_46.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_46.setBounds(10, 101, 81, 20);
		panel_6.add(lblNewLabel_46);
		
		for (int i = 5 + 6 * transBean.getN() - 6 * transBean.getX(); i < 6
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object4 = objects[3];
			lblNewLabel_47 = new JLabel(object4.toString());
			lblNewLabel_47.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_47.setBounds(95, 101, 215, 20);
			panel_6.add(lblNewLabel_47);
		}
		}else{}

		
		//上一步
		JLabel label = new JLabel(new ImageIcon("pic/preStep.png"));
		label.addMouseListener(new MouseAdapter() {
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
		label.setBounds(1075, 770, 150, 50);
		this.add(label);
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
		
		
		
		int n =transBean.getSkhryn()+1;
		
		String nn=String.valueOf(n);
		JLabel lblNewLabel_120 = new JLabel(nn);
		lblNewLabel_120.setFont(new Font("宋体", Font.PLAIN, 30));
		lblNewLabel_120.setBounds(783, 568, 41, 40);
		this.showJpanel.add(lblNewLabel_120);
		
		JLabel lblNewLabel_130 = new JLabel("/");
		lblNewLabel_130.setBounds(825, 568, 21, 40);
		lblNewLabel_130.setFont(new Font("宋体", Font.PLAIN, 30));
		this.showJpanel.add(lblNewLabel_130);
		
		int  w=transBean.getSkhrypage();
		String w1=String.valueOf(w);
		JLabel lblNewLabel_140 = new JLabel(w1);
		lblNewLabel_140.setBounds(845, 568, 44, 40);
		lblNewLabel_140.setFont(new Font("宋体", Font.PLAIN, 30));
		this.showJpanel.add(lblNewLabel_140);
		
		//上一页
		UtilButton backButton11 = new UtilButton("pic/up.png","pic/up.png");
		backButton11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		backButton11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				upStep(transBean);

			}

		});
//		backButton.setSize(200, 50);
		backButton11.setBounds(684, 568, 60, 40);
		backButton11.setIcon(new ImageIcon("pic/up.png"));
		this.showJpanel.add(backButton11);
		
		//下一页
		UtilButton backButton12 = new UtilButton("pic/next.png","pic/next.png");
		backButton12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		backButton12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				nextStep(transBean);				

			}

		});
//		backButton1.setSize(200, 50);
		backButton12.setBounds(899, 568, 60, 40);
		backButton12.setIcon(new ImageIcon("pic/next.png"));
		this.showJpanel.add(backButton12);
		
	}
	
	/**
	 * 返回
	 * */
	public void backTrans(PublicCashOpenBean transBean) {
		clearTimeText();
		openPanel(new MoneyBoxPrivateLineProductsNextPage(transBean));
		
	}
	
//	/**
//	 * 退卡
//	 * */
//	public void okTrans(PublicCashOpenBean transBean) {
//		clearTimeText();
//		openPanel(GlobalPanelFlag.MONEYBOX_TERMINATION_OF_SERVICE);
//		
//	}
	
	/**
	 * 上一页
	 * @return 
	 */
	public void  upStep(PublicCashOpenBean transBean){
		 int n =transBean.getSkhryn();
     if (n==0){
    	 transBean.setSkhryn(0);
     }else{
    	 transBean.setSkhryn(n-1);
     }
    clearTimeText();
 	openPanel(new MoneyBoxSHRYCXLPanel(transBean));
	}
	
	/**
	 * 下一页
	 */
	public void  nextStep(PublicCashOpenBean transBean){
		int  w=transBean.getSkhrypage();
		int n =transBean.getSkhryn();
		if(n+1<w){
			transBean.setSkhryn(n+1);
		}else{
			transBean.setSkhryn(n);
		}
		clearTimeText();
		openPanel(new MoneyBoxSHRYCXLPanel(transBean));
	}
	// 选择对应账号跳转页面
	private void test(PublicCashOpenBean transBean,String a,String b,Object c,Object d) {
		transBean.setProductName(a);//产品信息
		transBean.setMonthsUpperStr(MoneyUtils.intUppercaseXYCK(c.toString()));//存期(年/月/日)
		transBean.setProductCode(d.toString());//产品代码
		transBean.setMonthsUpper(c.toString());//存期(Y/M/D)
		//查询子产品信息
		kled(transBean);
		clearTimeText();
		openPanel(new MoneyBoxEnteringRYCPanel(transBean));
		
	}

	/**
	 * 查询子产品信息
	 */
	public void kled(PublicCashOpenBean transBean){	
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("CUST_NO", transBean.getCustNo());
			map.put("PRODUCT_CODE",transBean.getProductCode());
			map.put("QRY_TYPE", "2");
			if("2".equals(transBean.getPrivateLine())){
				map.put("DEP_AMT","0");//最低起存
				map.put("MAX_AMT","500000");//最高起存	
			}
			if("1".equals(transBean.getPrivateLine())){
				map.put("CHL_ID", "3");
				map.put("DEP_AMT","500000");
				map.put("MAX_AMT","");
			}
			
			transBean.getReqMCM001().setReqBefor("02867");//接口调用前流水信息记录
			
			AccountDepositApi accountDepositApi = new AccountDepositApi();
			BCK02867ResBean searchProduct=accountDepositApi.searchProduct(map);
			if(null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode())){
				//接口调用后流水信息记录
				if(null == searchProduct){
					transBean.getReqMCM001().setIntereturnmsg("查询子产品失败：调用02867接口异常");
				}else{
					transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
				}
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
				serverStop("请联系大堂经理","故障原因:"+searchProduct.getHeadBean().getResMsg(),"");
				return;
			}
			//接口调用成功后记录流水信息
			transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
			
			List<ProductRateInfo2> list = (List<ProductRateInfo2>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
			if(null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode()) || list.size() == 0 || "44444".equals(searchProduct.getHeadBean().getResCode())){
				//接口调用后流水信息记录
				if(null == searchProduct){
					transBean.getReqMCM001().setIntereturnmsg("查询子产品失败：调用02867接口异常");
				}else{
					transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
				}
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
				serverStop("请联系大堂经理","利率查询失败:子产品查询，"+searchProduct.getHeadBean().getResMsg(), "");
				return;
			}
			/*if("1".equals(transBean.getPrivateLine())){
				List<ProductRateInfo2> list1=new ArrayList<ProductRateInfo2>();
				for (ProductRateInfo2 productRateInfo2 : list) {
					float parseFloat = Float.parseFloat(productRateInfo2.getStartMoney());
					if (parseFloat>=500000) {
						list1.add(productRateInfo2);
					}
				}
				transBean.getAccountList().put(AccountDepositApi.PRODUCT_INFOS, list1);
			}*/
			//增加1-4万
			List<ProductRateInfo2> product = new ArrayList<ProductRateInfo2>();
			for(ProductRateInfo2 productrateinfo2 : list){
				if("10000.00".equals(productrateinfo2.getStartMoney())){
					for (int i = 0; i < 4; i++) {
						ProductRateInfo2 productinfo = new ProductRateInfo2();
						productinfo.setInterestRate(productrateinfo2.getInterestRate());
						productinfo.setIsPrint(productrateinfo2.getIsPrint());
						productinfo.setMinimumBalance(productrateinfo2.getMinimumBalance());
						productinfo.setProductCode(productrateinfo2.getProductCode());
						productinfo.setProductCodeName(productrateinfo2.getProductCodeName());
						productinfo.setSavingCount(productrateinfo2.getSavingCount());
//						productinfo.setStartMoney(productrateinfo2.getStartMoney());
						productinfo.setSubProductCode(productrateinfo2.getSubProductCode());
						productinfo.setSubProductName(productrateinfo2.getSubProductName());
						productinfo.setVoucherType(productrateinfo2.getVoucherType());
						float startMoney = Float.parseFloat(productrateinfo2.getStartMoney());
						productinfo.setStartMoney((String.valueOf(startMoney+i*10000)+"0").trim());
//						float endMoney = Float.parseFloat((productrateinfo2.getEndMoney()+"0"));
						productinfo.setEndMoney(String.valueOf(productrateinfo2.getEndMoney()));
						product.add(productinfo);
					}
				}else{
					product.add(productrateinfo2);
				}
			}
				transBean.getAccountList().put(AccountDepositApi.PRODUCT_INFOS, product);	
			
			
		
	}

	
}
