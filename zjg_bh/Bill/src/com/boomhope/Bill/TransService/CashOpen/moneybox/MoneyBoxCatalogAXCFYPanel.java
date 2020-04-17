package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
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
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

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
 * 安享存系列页面(分页)
 * @author gyw
 *
 */
public class MoneyBoxCatalogAXCFYPanel extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxCatalogAXCFYPanel.class);
	JLabel lblNewLabel_49 = null;
	JLabel lblNewLabel_51 = null;
	JLabel lblNewLabel_53 = null;
	JLabel lblNewLabel_55 = null;
	JLabel lblNewLabel_57 = null;
	JLabel lblNewLabel_59 = null;
	JLabel lblNewLabel_1 ;
	JLabel lblNewLabel_3;
	JLabel lblNewLabel_9;
	JLabel lblNewLabel_11;
	JLabel lblNewLabel_17;
	JLabel lblNewLabel_19;
	JLabel lblNewLabel_25;
	JLabel lblNewLabel_27;
	JLabel lblNewLabel_33;
	JLabel lblNewLabel_35;
	JLabel lblNewLabel_41;
	JLabel lblNewLabel_43;
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
	Object object5;
	Object object10;
	Object object15;
	Object object20;
	Object object25;
	Object object30;
	String MoneyButton="";
	String s = "";
	
	private boolean on_off=true;//用于控制页面跳转的开关

	public MoneyBoxCatalogAXCFYPanel(final PublicCashOpenBean transBean){
		this.cashBean = transBean;
//		 Object[][] data = { { "王尼玛1", "123456", "1122", "1122"},{"张全蛋2","555","245", "1122"},{"张全蛋3","555","245", "1122"},{"张全蛋4","555","245", "1122"},{"张全蛋5","555","245", "1122"},{ "王尼玛6", "123456", "1122", "1122"},{"张全蛋7","555","245", "1122"},{"张全蛋8","555","245", "1122"},{"张全蛋9","555","245", "1122"},{"张全蛋10","555","245", "1122"}};
		 List<ProductRateInfo1> list= (List<ProductRateInfo1>) transBean.getAccountList().get(AccountDepositApi.KEY_PRODUCT_RATES);
		 int count = 0;
			Object[][] data = new Object[list.size()][7]; 
			 for(ProductRateInfo1 productRateInfo1 : list){	
				 data[count][0] = productRateInfo1.getProductCodeName().trim();//产品名称
				 data[count][1] = productRateInfo1.getSavingCountMinStr();//存期(天，月，年)
				 data[count][2] = productRateInfo1.getInterestRateRangeStr();//年利率
				 data[count][3] = productRateInfo1.getStartMoneyStr();//期存金额 
				 data[count][4] = productRateInfo1.getProductCode();//产品代码
				 data[count][6] = productRateInfo1.getStartMoney();//期存金额（小写）
				 data[count][5] = productRateInfo1.getSavingCountMin();//存期(D,M,Y)
				 
				 count += 1;
			 }
			int a = data.length;
			int z = a % 6;
			int z1 = a / 6;
			if (z != 0) {
				transBean.setKhaxcpage(z1 + 1);
				logger.debug(z1 + 1);
			} else {
				transBean.setKhaxcpage(z1);
				logger.debug(z1);
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
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);
				excuteVoice("voice/select.wav");

			}
		});
		voiceTimer.start();
		if(a>6*transBean.getKhaxcn()){
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
				test(transBean,lblNewLabel_1.getText(),lblNewLabel_3.getText(),objecta1,objectb1);
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
		
		JLabel lblNewLabel_2 = new JLabel("存       期：");
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(10, 48, 79, 20);
		panel_1.add(lblNewLabel_2);
		
		for (int i = 0 + 6 * transBean.getN() - 6 * transBean.getX(); i < 1
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object2 = objects[1];
			lblNewLabel_3 = new JLabel(object2.toString());
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_3.setBounds(95, 48, 215, 20);
			panel_1.add(lblNewLabel_3);
		}
		JLabel lblNewLabel_4 = new JLabel("起存金额：");
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(10, 73, 75, 20);
		panel_1.add(lblNewLabel_4);
		
		for (int i = 0 + 6 * transBean.getN() - 6 * transBean.getX(); i < 1
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object3 = objects[3];
			JLabel lblNewLabel_5 = new JLabel(object3.toString());
			lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_5.setBounds(95, 73, 215, 15);
			panel_1.add(lblNewLabel_5);
		}
		
		JLabel lblNewLabel_48 = new JLabel("到 期 利 率：");
		lblNewLabel_48.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_48.setBounds(10, 98, 75, 20);
		panel_1.add(lblNewLabel_48);
		
		for (int i = 0 + 6 * transBean.getN() - 6 * transBean.getX(); i < 1
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object4 = objects[2];
			JLabel lblNewLabel_7 = new JLabel(object4.toString());
			lblNewLabel_7.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_7.setBounds(95, 99, 195, 15);
			panel_1.add(lblNewLabel_7);
		}
		}else{}
		if(a>6*transBean.getKhaxcn()+1){
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
				test(transBean,lblNewLabel_9.getText(),lblNewLabel_11.getText(),objecta2,objectb2);
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
			objecta2 = objects[5];
			objectb2 = objects[4];
			lblNewLabel_9 = new JLabel(object6.toString());
			lblNewLabel_9.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_9.setBounds(100, 23, 190, 20);
			panel_2.add(lblNewLabel_9);
		}
		
		JLabel lblNewLabel_10 = new JLabel("存       期：");
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
		
		JLabel lblNewLabel_12 = new JLabel("起存金额：");
		lblNewLabel_12.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_12.setBounds(10, 73, 80, 20);
		panel_2.add(lblNewLabel_12);
		
		for (int i = 1 + 6 * transBean.getN() - 6 * transBean.getX(); i < 2
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object8 = objects[3];
			JLabel lblNewLabel_13 = new JLabel(object8.toString());
			lblNewLabel_13.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_13.setBounds(100, 73, 190, 20);
			panel_2.add(lblNewLabel_13);
		}
		/*JLabel lblNewLabel_13 = new JLabel("New label");
		lblNewLabel_13.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_13.setBounds(100, 73, 190, 20);
		panel_2.add(lblNewLabel_13);*/
		
		/*JLabel lblNewLabel_14 = new JLabel("存款结息规则：");
		lblNewLabel_14.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_14.setBounds(0, 124, 105, 20);
		panel_2.add(lblNewLabel_14);
		
		JLabel lblNewLabel_15 = new JLabel("New label");
		lblNewLabel_15.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_15.setBounds(105, 120, 215, 40);
		panel_2.add(lblNewLabel_15);*/
		
		JLabel lblNewLabel_50 = new JLabel("到 期 利 率：");
		lblNewLabel_50.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_50.setBounds(10, 98, 80, 20);
		panel_2.add(lblNewLabel_50);
		
		for (int i = 1 + 6 * transBean.getN() - 6 * transBean.getX(); i < 2
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object9 = objects[2];
			JLabel lblNewLabel_15 = new JLabel(object9.toString());
			lblNewLabel_15.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_15.setBounds(100, 98, 190, 20);
			panel_2.add(lblNewLabel_15);
		}
		/*JLabel lblNewLabel_51 = new JLabel("New label");
		lblNewLabel_51.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_51.setBounds(100, 98, 190, 20);
		panel_2.add(lblNewLabel_51);*/
		}else{}
		if(a>6*transBean.getKhaxcn()+2){
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
				test(transBean,lblNewLabel_17.getText(),lblNewLabel_19.getText(),objecta3,objectb3);
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
			objecta3 = objects[5];
			objectb3 = objects[4];
			lblNewLabel_17 = new JLabel(object11.toString());
			lblNewLabel_17.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_17.setBounds(91, 22, 199, 20);
			panel_3.add(lblNewLabel_17);
		}
		/*JLabel lblNewLabel_17 = new JLabel("New label");
		lblNewLabel_17.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_17.setBounds(91, 22, 199, 20);
		panel_3.add(lblNewLabel_17);*/
		
		JLabel lblNewLabel_18 = new JLabel("存       期：");
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
		
		JLabel lblNewLabel_20 = new JLabel("起存金额：");
		lblNewLabel_20.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_20.setBounds(10, 72, 81, 20);
		panel_3.add(lblNewLabel_20);
		
		for (int i = 2 + 6 * transBean.getN() - 6 * transBean.getX(); i < 3
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object13 = objects[3];
			JLabel lblNewLabel_21 = new JLabel(object13.toString());
			lblNewLabel_21.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_21.setBounds(91, 72, 199, 20);
			panel_3.add(lblNewLabel_21);
		}
		/*JLabel lblNewLabel_21 = new JLabel("New label");
		lblNewLabel_21.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_21.setBounds(91, 72, 199, 20);
		panel_3.add(lblNewLabel_21);*/
		
		/*JLabel lblNewLabel_22 = new JLabel("存款结息规则：");
		lblNewLabel_22.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_22.setBounds(0, 124, 105, 20);
		panel_3.add(lblNewLabel_22);
		
		JLabel lblNewLabel_23 = new JLabel("New label");
		lblNewLabel_23.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_23.setBounds(105, 120, 215, 40);
		panel_3.add(lblNewLabel_23);*/
		
		JLabel lblNewLabel_52 = new JLabel("到 期 利 率：");
		lblNewLabel_52.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_52.setBounds(10, 97, 81, 20);
		panel_3.add(lblNewLabel_52);
		
		for (int i = 2 + 6 * transBean.getN() - 6 * transBean.getX(); i < 3
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object14 = objects[2];
			JLabel lblNewLabel_23 = new JLabel(object14.toString());
			lblNewLabel_23.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_23.setBounds(91, 97, 199, 20);
			panel_3.add(lblNewLabel_23);
		}
		}else{}
		if(a>6*transBean.getKhaxcn()+3){
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
				test(transBean,lblNewLabel_25.getText(),lblNewLabel_27.getText(),objecta4,objectb4);
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
			objecta4 = objects[5];
			objectb4 = objects[4];
			lblNewLabel_25 = new JLabel(object16.toString());
			lblNewLabel_25.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_25.setBounds(95, 23, 195, 20);
			panel_4.add(lblNewLabel_25);
		}
		
		JLabel lblNewLabel_26 = new JLabel("存       期：");
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
		
		JLabel lblNewLabel_28 = new JLabel("起存金额：");
		lblNewLabel_28.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_28.setBounds(10, 73, 75, 20);
		panel_4.add(lblNewLabel_28);
		
		for (int i = 3 + 6 * transBean.getN() - 6 * transBean.getX(); i < 4
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object18 = objects[3];
			JLabel lblNewLabel_29 = new JLabel(object18.toString());
			lblNewLabel_29.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_29.setBounds(95, 73, 195, 20);
			panel_4.add(lblNewLabel_29);
		}
		
		JLabel lblNewLabel_54 = new JLabel("到 期 利 率：");
		lblNewLabel_54.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_54.setBounds(10, 98, 75, 20);
		panel_4.add(lblNewLabel_54);
		
		for (int i = 3 + 6 * transBean.getN() - 6 * transBean.getX(); i < 4
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object19 = objects[2];
			JLabel lblNewLabel_31 = new JLabel(object19.toString());
			lblNewLabel_31.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_31.setBounds(95, 98, 195, 20);
			panel_4.add(lblNewLabel_31);
		}
		}else{}
		if(a>6*transBean.getKhaxcn()+4){
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
				test(transBean,lblNewLabel_33.getText(),lblNewLabel_35.getText(),objecta5,objectb5);
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
			objecta5 = objects[5];
			objectb5 = objects[4];
			lblNewLabel_33 = new JLabel(object21.toString());
			lblNewLabel_33.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_33.setBounds(100, 23, 190, 20);
			panel_5.add(lblNewLabel_33);
		}
		
		JLabel lblNewLabel_34 = new JLabel("存       期：");
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
		
		JLabel lblNewLabel_36 = new JLabel("起存金额：");
		lblNewLabel_36.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_36.setBounds(10, 74, 80, 20);
		panel_5.add(lblNewLabel_36);
		
		for (int i = 4 + 6 * transBean.getN() - 6 * transBean.getX(); i < 5
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object23 = objects[3];
			JLabel lblNewLabel_37 = new JLabel(object23.toString());
			lblNewLabel_37.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_37.setBounds(100, 74, 190, 20);
			panel_5.add(lblNewLabel_37);
		}
		
		JLabel lblNewLabel_56 = new JLabel("到 期 利 率：");
		lblNewLabel_56.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_56.setBounds(10, 99, 80, 20);
		panel_5.add(lblNewLabel_56);
		
		for (int i = 4 + 6 * transBean.getN() - 6 * transBean.getX(); i < 5
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object24 = objects[2];
			JLabel lblNewLabel_39 = new JLabel(object24.toString());
			lblNewLabel_39.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_39.setBounds(100, 99, 190, 20);
			panel_5.add(lblNewLabel_39);
		}
		}else{}
		if(a>6*transBean.getKhaxcn()+5){
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
				test(transBean,lblNewLabel_41.getText(),lblNewLabel_43.getText(),objecta6,objectb6);
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
			objecta6 = objects[5];
			objectb6 = objects[4];
			lblNewLabel_41 = new JLabel(object26.toString());
			lblNewLabel_41.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_41.setBounds(92, 22, 198, 20);
			panel_6.add(lblNewLabel_41);
		}
		
		JLabel lblNewLabel_42 = new JLabel("存       期：");
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
		
		JLabel lblNewLabel_44 = new JLabel("起存金额：");
		lblNewLabel_44.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_44.setBounds(10, 71, 81, 20);
		panel_6.add(lblNewLabel_44);
		
		for (int i = 5 + 6 * transBean.getN() - 6 * transBean.getX(); i < 6
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object28 = objects[3];
			JLabel lblNewLabel_45 = new JLabel(object28.toString());
			lblNewLabel_45.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_45.setBounds(92, 71, 198, 20);
			panel_6.add(lblNewLabel_45);
		}
		
		JLabel lblNewLabel_58 = new JLabel("到 期 利 率：");
		lblNewLabel_58.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_58.setBounds(10, 96, 81, 20);
		panel_6.add(lblNewLabel_58);
		
		for (int i = 5 + 6 * transBean.getN() - 6 * transBean.getX(); i < 6
				+ 6 * transBean.getN() - 6 * transBean.getX(); i++) {
			Object[] objects = data[i];
			Object object29 = objects[2];
			JLabel lblNewLabel_47 = new JLabel(object29.toString());
			lblNewLabel_47.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			lblNewLabel_47.setBounds(91, 96, 199, 20);
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

		
		
		int n =transBean.getKhaxcn()+1;
		
		String nn=String.valueOf(n);
		JLabel lblNewLabel_120 = new JLabel(nn);
		lblNewLabel_120.setFont(new Font("宋体", Font.PLAIN, 30));
		lblNewLabel_120.setBounds(783, 560, 41, 40);
		this.showJpanel.add(lblNewLabel_120);
		
		JLabel lblNewLabel_130 = new JLabel("/");
		lblNewLabel_130.setBounds(825, 560, 21, 40);
		lblNewLabel_130.setFont(new Font("宋体", Font.PLAIN, 30));
		this.showJpanel.add(lblNewLabel_130);
		
		int  w=transBean.getKhaxcpage();
		String w1=String.valueOf(w);
		JLabel lblNewLabel_140 = new JLabel(w1);
		lblNewLabel_140.setBounds(845, 560, 44, 40);
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
		backButton11.setBounds(684, 560, 60, 40);
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
		backButton12.setBounds(899, 560, 60, 40);
		backButton12.setIcon(new ImageIcon("pic/next.png"));
		this.showJpanel.add(backButton12);
		
	}
	
	
	/**
	 * 返回
	 * */
	public void backTrans(PublicCashOpenBean transBean) {

		clearTimeText();
		openPanel(new MoneyBoxCatalogAXCXLPanel(transBean));
		
	}
	
	/**
	 * 上一页
	 * @return 
	 */
	public void  upStep(PublicCashOpenBean transBean){
		 int n =transBean.getKhaxcn();
     if (n==0){
    	 transBean.setKhaxcn(0);
     }else{
    	 transBean.setKhaxcn(n-1);
     }
     openPanel(new MoneyBoxCatalogAXCFYPanel(transBean));
	}
	
	/**
	 * 下一页
	 */
	public void  nextStep(PublicCashOpenBean transBean){
		int  w=transBean.getKhaxcpage();
		int n =transBean.getKhaxcn();
		if(n+1<w){
			 transBean.setKhaxcn(n+1);
		}else{
			transBean.setKhaxcn(n);
		}
		clearTimeText();
		openPanel(new MoneyBoxCatalogAXCFYPanel(transBean));
	}
	// 选择对应账号跳转页面
	private void test(final PublicCashOpenBean  transBean,String a,String b,Object obj1,Object obj2) {
		transBean.setProductName(a);//产品名称
		transBean.setMonthsUpper(obj1.toString());//存期  (D，M，Y)
		transBean.setMonthsUpperStr(MoneyUtils.intUppercaseXYCK(obj1.toString()));
		transBean.setProductCode(obj2.toString());
		kled(transBean);
		delayTimer.stop();
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				openPanel(new MoneyBoxEnteringAXCPanel(transBean));
			}			
		});
	}
	/**
	 * 查询子产品信息
	 */
	public void kled(PublicCashOpenBean transBean){	
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("CUST_NO", transBean.getCustNo());
			map.put("PRODUCT_CODE",transBean.getProductCode());
			map.put("QRY_TYPE", "2");
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
				transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES, list);
				ProductRateInfo2 productRateInfo2 =null;
				if(transBean.getMoney()<50000){
					productRateInfo2 = list.get(0);
				}else if(transBean.getMoney()==50000){
					productRateInfo2 = list.get(1);
				}else if(transBean.getMoney()==150000){
					productRateInfo2 = list.get(2);
				}else if(transBean.getMoney()==300000){
					productRateInfo2 = list.get(3);
				}else{
					productRateInfo2 = list.get(3);
				}
				
				//接口调用成功后记录流水信息
				transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
				transBean.setProductName(productRateInfo2.getProductCodeName());//产品名称
				transBean.setProductCode(productRateInfo2.getProductCode());//产品代码
				transBean.setLdcType("2");		
		
	}
	
}


