package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.TransService.CashOpen.Interface.AccountDepositApi;
import com.boomhope.Bill.TransService.CashOpen.Interface.ProductRateInfo2;
import com.boomhope.Bill.TransService.CashOpen.Interface.SearchRYCDetail;
import com.boomhope.Bill.Util.DateTool;
import com.boomhope.Bill.Util.MoneyUtils;
import com.boomhope.Bill.Util.UtilButton;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.message.account.in.BCK02867ResBean;

/***
 * 积享存系列页面(分页)
 * @author gyw
 *
 */
public class MoneyBoxCatalogJXCXLPanel extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxCatalogJXCXLPanel.class);

	JLabel lblNewLabel_1 ;
	JLabel lblNewLabel_3;
	JLabel lblNewLabel_5;
	JLabel lblNewLabel_9;
	JLabel lblNewLabel_11;
	JLabel lblNewLabel_13;
	JLabel lblNewLabel_17;
	JLabel lblNewLabel_19;
	JLabel lblNewLabel_21;
	JLabel lblNewLabel_25;
	JLabel lblNewLabel_27;
	JLabel lblNewLabel_29;
	JLabel lblNewLabel_33;
	JLabel lblNewLabel_35;
	JLabel lblNewLabel_37;
	JLabel lblNewLabel_41;
	JLabel lblNewLabel_43;
	JLabel lblNewLabel_45;
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
	Object objectc1;
	Object objectc2;
	Object objectc3;
	Object objectc4;
	Object objectc5;
	Object objectc6;
	Object objectd1;
	Object objectd2;
	Object objectd3;
	Object objectd4;
	Object objectd5;
	Object objectd6;
	Object objecte1;
	Object objecte2;
	Object objecte3;
	Object objecte4;
	Object objecte5;
	Object objecte6;
	
	private boolean on_off=true;//用于控制页面跳转的开关
	
	public MoneyBoxCatalogJXCXLPanel(final PublicCashOpenBean transBean){
		this.cashBean = transBean;
		 //Object[][] data = { { "王尼玛1", "123456", "1122", "1122"},{"张全蛋2","555","245", "1122"},{"张全蛋3","555","245", "1122"},{"张全蛋4","555","245", "1122"},{"张全蛋5","555","245", "1122"},{ "王尼玛6", "123456", "1122", "1122"},{"张全蛋7","555","245", "1122"},{"张全蛋8","555","245", "1122"},{"张全蛋9","555","245", "1122"},{"张全蛋10","555","245", "1122"}};
		List<SearchRYCDetail> list= (List<SearchRYCDetail>) transBean.getAccountList().get(AccountDepositApi.KEY_PRODUCT_RATES);
		 int count = 0;
			Object[][] data = new Object[list.size()][9]; 
			 for(SearchRYCDetail productRateInfo1 : list){	
				 data[count][0] = productRateInfo1.getProductCodeName().trim();//产品名称
				 data[count][1] = productRateInfo1.getOpenDate().trim();//开户日期
				 data[count][2] = productRateInfo1.getDepTermStr().trim();//存期(天，月，年)
				 data[count][3] = productRateInfo1.getBalance().trim();//期存金额 
				 data[count][4] = productRateInfo1.getProductCode().trim();//产品代码
				 data[count][5] = productRateInfo1.getDepTerm().trim();//存期(D,M,Y)
				 data[count][6] = productRateInfo1.getEndDate().trim();//结束日期
				 data[count][7] = productRateInfo1.getAcctNo().trim();//账号
				 data[count][8] = productRateInfo1.getSubAcctNo().trim();//关联子账号
				 count += 1;
			 }
			int a = data.length;
			int z = a % 6;
			int z1 = a / 6;
			if (z != 0) {
				transBean.setKhjxpage(z1 + 1);
				System.out.println(z1 + 1);
			} else {
				transBean.setKhjxpage(z1);
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
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请选择关联的如意存产品");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 35));
		titleLabel.setBounds(0, 80, GlobalParameter.TRANS_WIDTH, 40);
		this.showJpanel.add(titleLabel);
		delayTimer.start();
		if(a>6*transBean.getKhjxn()){
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 160, 320, 160);
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
				test(transBean, lblNewLabel_1.getText(),lblNewLabel_5.getText(),objecta1,objectb1,objectc1,objectd1,objecte1);
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
			objecta1 = objects[4];
			objectb1 = objects[5];
			objectc1 = objects[6];
			objectd1 = objects[7];
			objecte1 = objects[8];
			lblNewLabel_1 = new JLabel(object1.toString());
			lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_1.setBounds(95, 23, 215, 20);
			panel_1.add(lblNewLabel_1);
		}
		
		JLabel lblNewLabel_2 = new JLabel("开户日期：");
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(10, 48, 79, 20);
		panel_1.add(lblNewLabel_2);
		
		for (int i = 0 + 6 * transBean.getN() - 6 * transBean.getX(); i < 1
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object6 = objects[1];
			lblNewLabel_3 = new JLabel(object6.toString());
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_3.setBounds(95, 48, 215, 20);
			panel_1.add(lblNewLabel_3);
		}
		
		JLabel lblNewLabel_4 = new JLabel("存      期：");
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(10, 73, 75, 20);
		panel_1.add(lblNewLabel_4);
		
		for (int i = 0 + 6 * transBean.getN() - 6 * transBean.getX(); i < 1
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object3 = objects[2];
		    lblNewLabel_5 = new JLabel(object3.toString());
			lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_5.setBounds(95, 73, 215, 20);
			panel_1.add(lblNewLabel_5);
		}
		
		JLabel lblNewLabel_6 = new JLabel("金      额：");
		lblNewLabel_6.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_6.setBounds(10, 124, 79, 20);
		panel_1.add(lblNewLabel_6);
		
		for (int i = 0 + 6 * transBean.getN() - 6 * transBean.getX(); i < 1
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object4 = objects[3];
			JLabel lblNewLabel_7 = new JLabel(object4.toString());
			lblNewLabel_7.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_7.setBounds(95, 124, 215, 20);
			panel_1.add(lblNewLabel_7);
		}
		}else{}
		if(a>6*transBean.getKhjxn()+1){
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(344, 160, 320, 160);
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
				test(transBean, lblNewLabel_9.getText(),lblNewLabel_13.getText(),objecta2,objectb2,objectc2,objectd2,objecte2);
			}

		});
		JLabel lblNewLabel_8 = new JLabel("产品名称：");
		lblNewLabel_8.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_8.setBounds(10, 23, 80, 20);
		panel_2.add(lblNewLabel_8);
		
		for (int i = 1 + 6 * transBean.getN() - 6 * transBean.getX(); i < 2
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object6 = objects[0];
			objecta2 = objects[4];
			objectb2 = objects[5];
			objectc2 = objects[6];
			objectd2 = objects[7];
			objecte2 = objects[8];
			lblNewLabel_9 = new JLabel(object6.toString());
			lblNewLabel_9.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_9.setBounds(100, 23, 190, 20);
			panel_2.add(lblNewLabel_9);
		}
		
		JLabel lblNewLabel_10 = new JLabel("开户日期：");
		lblNewLabel_10.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_10.setBounds(10, 48, 80, 20);
		panel_2.add(lblNewLabel_10);
		
		for (int i = 1 + 6 * transBean.getN() - 6 * transBean.getX(); i < 2
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object7 = objects[1];
			lblNewLabel_11 = new JLabel(object7.toString());
			lblNewLabel_11.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_11.setBounds(100, 48, 190, 20);
			panel_2.add(lblNewLabel_11);
		}
		
		JLabel lblNewLabel_12 = new JLabel("存      期：");
		lblNewLabel_12.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_12.setBounds(10, 73, 80, 20);
		panel_2.add(lblNewLabel_12);
		
		for (int i = 1 + 6 * transBean.getN() - 6 * transBean.getX(); i < 2
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object8 = objects[2];
			lblNewLabel_13 = new JLabel(object8.toString());
			lblNewLabel_13.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_13.setBounds(100, 73, 190, 20);
			panel_2.add(lblNewLabel_13);
		}
		
		JLabel lblNewLabel_14 = new JLabel("金      额：");
		lblNewLabel_14.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_14.setBounds(10, 124, 80, 20);
		panel_2.add(lblNewLabel_14);
		
		for (int i = 1 + 6 * transBean.getN() - 6 * transBean.getX(); i < 2
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object9 = objects[3];
			JLabel lblNewLabel_15 = new JLabel(object9.toString());
			lblNewLabel_15.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_15.setBounds(100, 124, 190, 20);
			panel_2.add(lblNewLabel_15);
		}
		}else{}
		if(a>6*transBean.getKhjxn()+2){
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(676, 160, 320, 160);
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
				test(transBean, lblNewLabel_17.getText(),lblNewLabel_21.getText(),objecta3,objectb3,objectc3,objectd3,objecte3);
			}

		});
		JLabel lblNewLabel_16 = new JLabel("产品名称：");
		lblNewLabel_16.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_16.setBounds(10, 22, 81, 20);
		panel_3.add(lblNewLabel_16);
		
		for (int i = 2 + 6 * transBean.getN() - 6 * transBean.getX(); i < 3
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object11 = objects[0];
			objecta3 = objects[4];
			objectb3 = objects[5];
			objectc3 = objects[6];
			objectd3 = objects[7];
			objecte3 = objects[8];
			lblNewLabel_17 = new JLabel(object11.toString());
			lblNewLabel_17.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_17.setBounds(91, 22, 199, 20);
			panel_3.add(lblNewLabel_17);
		}
		
		JLabel lblNewLabel_18 = new JLabel("开户日期：");
		lblNewLabel_18.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_18.setBounds(10, 47, 81, 20);
		panel_3.add(lblNewLabel_18);
		
		for (int i = 2 + 6 * transBean.getN() - 6 * transBean.getX(); i < 3
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object12 = objects[1];
			lblNewLabel_19 = new JLabel(object12.toString());
			lblNewLabel_19.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_19.setBounds(91, 47, 199, 20);
			panel_3.add(lblNewLabel_19);
		}
		
		JLabel lblNewLabel_20 = new JLabel("存      期：");
		lblNewLabel_20.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_20.setBounds(10, 72, 81, 20);
		panel_3.add(lblNewLabel_20);
		
		for (int i = 2 + 6 * transBean.getN() - 6 * transBean.getX(); i < 3
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object13 = objects[2];
			lblNewLabel_21 = new JLabel(object13.toString());
			lblNewLabel_21.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_21.setBounds(91, 72, 199, 20);
			panel_3.add(lblNewLabel_21);
		}
		
		JLabel lblNewLabel_22 = new JLabel("金      额：");
		lblNewLabel_22.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_22.setBounds(10, 126, 81, 20);
		panel_3.add(lblNewLabel_22);
		
		for (int i = 2 + 6 * transBean.getN() - 6 * transBean.getX(); i < 3
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object14 = objects[3];
			JLabel lblNewLabel_23 = new JLabel(object14.toString());
			lblNewLabel_23.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_23.setBounds(91, 126, 199, 20);
			panel_3.add(lblNewLabel_23);
		}
		}else{}
		if(a>6*transBean.getKhjxn()+3){
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(12, 350, 320, 160);
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
				test(transBean, lblNewLabel_25.getText(),lblNewLabel_29.getText(),objecta4,objectb4,objectc4,objectd4,objecte4);
			}

		});
		JLabel lblNewLabel_24 = new JLabel("产品名称：");
		lblNewLabel_24.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_24.setBounds(10, 23, 75, 20);
		panel_4.add(lblNewLabel_24);
		
		for (int i = 3 + 6 * transBean.getN() - 6 * transBean.getX(); i < 4
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object16 = objects[0];
			objecta4 = objects[4];
			objectb4 = objects[5];
			objectc4 = objects[6];
			objectd4 = objects[7];
			objecte4 = objects[8];
			lblNewLabel_25 = new JLabel(object16.toString());
			lblNewLabel_25.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_25.setBounds(95, 23, 195, 20);
			panel_4.add(lblNewLabel_25);
		}
		
		JLabel lblNewLabel_26 = new JLabel("开户日期：");
		lblNewLabel_26.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_26.setBounds(10, 48, 75, 20);
		panel_4.add(lblNewLabel_26);
		
		for (int i = 3 + 6 * transBean.getN() - 6 * transBean.getX(); i < 4
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object17 = objects[1];
			lblNewLabel_27 = new JLabel(object17.toString());
			lblNewLabel_27.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_27.setBounds(95, 48, 195, 20);
			panel_4.add(lblNewLabel_27);
		}
		
		JLabel lblNewLabel_28 = new JLabel("存      期：");
		lblNewLabel_28.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_28.setBounds(10, 73, 75, 20);
		panel_4.add(lblNewLabel_28);
		
		for (int i = 3 + 6 * transBean.getN() - 6 * transBean.getX(); i < 4
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object18 = objects[2];
			lblNewLabel_29 = new JLabel(object18.toString());
			lblNewLabel_29.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_29.setBounds(95, 73, 195, 20);
			panel_4.add(lblNewLabel_29);
		}
		
		JLabel lblNewLabel_30 = new JLabel("金      额：");
		lblNewLabel_30.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_30.setBounds(10, 123, 75, 20);
		panel_4.add(lblNewLabel_30);
		
		for (int i = 3 + 6 * transBean.getN() - 6 * transBean.getX(); i < 4
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object19 = objects[3];
			JLabel lblNewLabel_31 = new JLabel(object19.toString());
			lblNewLabel_31.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_31.setBounds(95, 123, 195, 20);
			panel_4.add(lblNewLabel_31);
		}
		}else{}
		if(a>6*transBean.getKhjxn()+4){
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(344, 350, 320, 160);
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
				test(transBean, lblNewLabel_33.getText(),lblNewLabel_37.getText(),objecta5,objectb5,objectc5,objectd5,objecte5);
			}

		});
		JLabel lblNewLabel_32 = new JLabel("产品名称：");
		lblNewLabel_32.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_32.setBounds(10, 23, 80, 20);
		panel_5.add(lblNewLabel_32);
		
		for (int i = 4 + 6 * transBean.getN() - 6 * transBean.getX(); i < 5
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object21 = objects[0];
			objecta5 = objects[4];
			objectb5 = objects[5];
			objectc5 = objects[6];
			objectd5 = objects[7];
			objecte5 = objects[8];
			lblNewLabel_33 = new JLabel(object21.toString());
			lblNewLabel_33.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_33.setBounds(100, 23, 190, 20);
			panel_5.add(lblNewLabel_33);
		}
		
		JLabel lblNewLabel_34 = new JLabel("开户日期：");
		lblNewLabel_34.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_34.setBounds(10, 49, 80, 20);
		panel_5.add(lblNewLabel_34);
		
		for (int i = 4 + 6 * transBean.getN() - 6 * transBean.getX(); i < 5
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object22 = objects[1];
			lblNewLabel_35 = new JLabel(object22.toString());
			lblNewLabel_35.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_35.setBounds(100, 49, 190, 20);
			panel_5.add(lblNewLabel_35);
		}
		
		JLabel lblNewLabel_36 = new JLabel("存      期：");
		lblNewLabel_36.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_36.setBounds(10, 74, 80, 20);
		panel_5.add(lblNewLabel_36);
		
		for (int i = 4 + 6 * transBean.getN() - 6 * transBean.getX(); i < 5
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object23 = objects[2];
			lblNewLabel_37 = new JLabel(object23.toString());
			lblNewLabel_37.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_37.setBounds(100, 74, 190, 20);
			panel_5.add(lblNewLabel_37);
		}
		
		JLabel lblNewLabel_38 = new JLabel("金      额：");
		lblNewLabel_38.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_38.setBounds(10, 124, 80, 20);
		panel_5.add(lblNewLabel_38);
		
		for (int i = 4 + 6 * transBean.getN() - 6 * transBean.getX(); i < 5
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object24 = objects[3];
			JLabel lblNewLabel_39 = new JLabel(object24.toString());
			lblNewLabel_39.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_39.setBounds(100, 124, 190, 20);
			panel_5.add(lblNewLabel_39);
		}
		}else{}
		if(a>6*transBean.getKhjxn()+5){
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(676, 350, 320, 160);
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
				test(transBean, lblNewLabel_41.getText(),lblNewLabel_45.getText(),objecta6,objectb6,objectc6,objectd6,objecte6);
			}

		});
		JLabel lblNewLabel_40 = new JLabel("产品名称：");
		lblNewLabel_40.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_40.setBounds(10, 22, 81, 20);
		panel_6.add(lblNewLabel_40);
		
		for (int i = 5 + 6 * transBean.getN() - 6 * transBean.getX(); i < 6
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object26 = objects[0];
			objecta6 = objects[4];
			objectb6 = objects[5];
			objectc6 = objects[6];
			objectd6 = objects[7];
			objecte6 = objects[8];
			lblNewLabel_41 = new JLabel(object26.toString());
			lblNewLabel_41.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_41.setBounds(92, 22, 198, 20);
			panel_6.add(lblNewLabel_41);
		}
		
		JLabel lblNewLabel_42 = new JLabel("开户日期：");
		lblNewLabel_42.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_42.setBounds(10, 46, 81, 20);
		panel_6.add(lblNewLabel_42);
		
		for (int i = 5 + 6 * transBean.getN() - 6 * transBean.getX(); i < 6
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object27 = objects[1];
			lblNewLabel_43 = new JLabel(object27.toString());
			lblNewLabel_43.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_43.setBounds(92, 46, 198, 20);
			panel_6.add(lblNewLabel_43);
		}
		
		JLabel lblNewLabel_44 = new JLabel("存      期：");
		lblNewLabel_44.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_44.setBounds(10, 71, 81, 20);
		panel_6.add(lblNewLabel_44);
		
		for (int i = 5 + 6 * transBean.getN() - 6 * transBean.getX(); i < 6
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object28 = objects[2];
			lblNewLabel_45 = new JLabel(object28.toString());
			lblNewLabel_45.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_45.setBounds(92, 71, 198, 20);
			panel_6.add(lblNewLabel_45);
		}
		
		JLabel lblNewLabel_46 = new JLabel("金      额：");
		lblNewLabel_46.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_46.setBounds(10, 123, 81, 20);
		panel_6.add(lblNewLabel_46);
		
		for (int i = 5 + 6 * transBean.getN() - 6 * transBean.getX(); i < 6
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object29 = objects[3];
			JLabel lblNewLabel_47 = new JLabel(object29.toString());
			lblNewLabel_47.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_47.setBounds(92, 123, 198, 20);
			panel_6.add(lblNewLabel_47);
		}
		}else{}

		
		//上一步
		JLabel label = new JLabel(new ImageIcon("pic/preStep.png"));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
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
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				logger.info("点击退出按钮");				
				returnHome();
			}

		});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);

		
		
		int n =transBean.getKhjxn()+1;
		
		String nn=String.valueOf(n);
		JLabel lblNewLabel_120 = new JLabel(nn);
		lblNewLabel_120.setFont(new Font("宋体", Font.PLAIN, 30));
		lblNewLabel_120.setBounds(783, 568, 41, 40);
		this.showJpanel.add(lblNewLabel_120);
		
		JLabel lblNewLabel_130 = new JLabel("/");
		lblNewLabel_130.setBounds(825, 568, 21, 40);
		lblNewLabel_130.setFont(new Font("宋体", Font.PLAIN, 30));
		this.showJpanel.add(lblNewLabel_130);
		
		int  w=transBean.getKhjxpage();
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
		backButton12.setBounds(899, 568, 60, 40);
		backButton12.setIcon(new ImageIcon("pic/next.png"));
		this.showJpanel.add(backButton12);
		
	}
	
	
	/**
	 * 返回
	 * */
	public void backTrans(PublicCashOpenBean transBean) {

		clearTimeText();
		openPanel(new MoneyBoxAgreementPanel(transBean));
		
	}
	
	/**
	 * 上一页
	 * @return 
	 */
	public void  upStep(PublicCashOpenBean transBean){
		 int n =transBean.getKhjxn();
     if (n==0){
    	 transBean.setKhjxn(0);
     }else{
    	 transBean.setKhjxn(n-1);
     }
 	openPanel(new MoneyBoxCatalogJXCXLPanel(transBean));
	}
	
	/**
	 * 下一页
	 */
	public void  nextStep(PublicCashOpenBean transBean){
		int  w=transBean.getKhjxpage();
		int n =transBean.getKhjxn();
		if(n+1<w){
			 transBean.setKhjxn(n+1);
		}else{
			transBean.setKhjxn(n);
		}
		openPanel(new MoneyBoxCatalogJXCXLPanel(transBean));
	}
	// 选择对应账号跳转页面
	private void test(PublicCashOpenBean transBean,String a,String b,Object obj1,Object obj2,Object obj3,Object obj4,Object obj5) {
		//产品系列，存期，产品代码
		transBean.setRelaAcctName(a);
		transBean.setMonthsUpperStr(b);
		transBean.setMonthsUpper(obj2.toString());
		transBean.setProductCode(obj1.toString());
		transBean.setEndTime(obj3.toString());//结束日期
		transBean.setRelaAcctNo(obj5.toString());//关联子账号
		transBean.setAccinputNo(obj4.toString());//关联账号、收益账号
		kled(transBean);
		clearTimeText();
		openPanel(new MoneyBoxEnteringJXCPanel(transBean));
		
	}
	/**
	 * 查询如意存所对应的积享存产品
	 */
	public void kled(PublicCashOpenBean transBean){	
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("CUST_NO", transBean.getCustNo());
			String productCode = transBean.getProductCode().replace("RY", "JX");
			map.put("PRODUCT_CODE",productCode);
			map.put("QRY_TYPE", "3");
			map.put("DEP_AMT","0");//最低起存
			map.put("MAX_AMT","500000");//最高起存
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
			
			//接口调用成功后记录流水信息
			transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
			
			transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES, list);
			ProductRateInfo2 productRateInfo2 = list.get(0);
			transBean.setMonthsUpperStr(MoneyUtils.intUppercaseXYCK(productRateInfo2.getSavingCount()));
			transBean.setProductName(productRateInfo2.getProductCodeName());
			transBean.setProductCode(productRateInfo2.getProductCode());
			transBean.setMonthsUpper(productRateInfo2.getSavingCount().trim());
			SimpleDateFormat PRE_FORMAT = new SimpleDateFormat("yyyyMMdd");
			Date date =DateTool.stringToUtilDate(transBean.getEndTime(), "yyyyMMdd");
			int endDate = DateTool.nDaysBetweenTwoDate(transBean.getSvrDate(), PRE_FORMAT.format(date));
			transBean.setDayUpper(endDate+"天");
		
	}
	
}

