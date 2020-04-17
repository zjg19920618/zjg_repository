package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.UtilButton;

/***
 * 私行立得存存系列页面(分页)
 * @author gyw
 *
 */
public class MoneyBoxCatalogSHLDCXLZXTXPanel extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxCatalogSHLDCXLZXTXPanel.class);
	
	private boolean on_off=true;//开关控制
	
	public MoneyBoxCatalogSHLDCXLZXTXPanel(final PublicCashOpenBean transBean){
		this.cashBean = transBean;
		 Object[][] data = { { "王尼玛1", "123456", "1122", "1122"},{"张全蛋2","555","245", "1122"},{"张全蛋3","555","245", "1122"},{"张全蛋4","555","245", "1122"},{"张全蛋5","555","245", "1122"},{ "王尼玛6", "123456", "1122", "1122"},{"张全蛋7","555","245", "1122"},{"张全蛋8","555","245", "1122"},{"张全蛋9","555","245", "1122"},{"张全蛋10","555","245", "1122"}};
		 
			int a = data.length;
			int z = a % 6;
			int z1 = a / 6;
			if (z != 0) {
				transBean.setSkhldpage(z1 + 1);
				System.out.println(z1 + 1);
			} else {
				transBean.setSkhldpage(z1);
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
		
		if(a>6*transBean.getSkhldn()){
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
				test(transBean);
			}

		});
		JLabel lblNewLabel = new JLabel("产品名称：");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 23, 79, 20);
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(95, 23, 215, 20);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("存       期：");
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(10, 48, 79, 20);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(95, 48, 215, 20);
		panel_1.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("起存金额：");
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(10, 73, 75, 20);
		panel_1.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_5.setBounds(95, 73, 215, 20);
		panel_1.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("存款结息规则：");
		lblNewLabel_6.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_6.setBounds(0, 124, 105, 20);
		panel_1.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("New label");
		lblNewLabel_7.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_7.setBounds(105, 120, 215, 40);
		panel_1.add(lblNewLabel_7);
		
		JLabel lblNewLabel_48 = new JLabel("止存金额：");
		lblNewLabel_48.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_48.setBounds(10, 98, 75, 20);
		panel_1.add(lblNewLabel_48);
		
		JLabel lblNewLabel_49 = new JLabel("New label");
		lblNewLabel_49.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_49.setBounds(95, 99, 215, 20);
		panel_1.add(lblNewLabel_49);
		}else{}
		if(a>6*transBean.getSkhldn()+1){
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
				test(transBean);
			}

		});
		JLabel lblNewLabel_8 = new JLabel("产品名称：");
		lblNewLabel_8.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_8.setBounds(10, 23, 80, 20);
		panel_2.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("New label");
		lblNewLabel_9.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_9.setBounds(100, 23, 190, 20);
		panel_2.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("存       期：");
		lblNewLabel_10.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_10.setBounds(10, 48, 80, 20);
		panel_2.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("New label");
		lblNewLabel_11.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_11.setBounds(100, 48, 190, 20);
		panel_2.add(lblNewLabel_11);
		
		JLabel lblNewLabel_12 = new JLabel("起存金额：");
		lblNewLabel_12.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_12.setBounds(10, 73, 80, 20);
		panel_2.add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("New label");
		lblNewLabel_13.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_13.setBounds(100, 73, 190, 20);
		panel_2.add(lblNewLabel_13);
		
		JLabel lblNewLabel_14 = new JLabel("存款结息规则：");
		lblNewLabel_14.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_14.setBounds(0, 124, 105, 20);
		panel_2.add(lblNewLabel_14);
		
		JLabel lblNewLabel_15 = new JLabel("New label");
		lblNewLabel_15.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_15.setBounds(105, 120, 215, 40);
		panel_2.add(lblNewLabel_15);
		
		JLabel lblNewLabel_50 = new JLabel("止存金额：");
		lblNewLabel_50.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_50.setBounds(10, 98, 80, 20);
		panel_2.add(lblNewLabel_50);
		
		JLabel lblNewLabel_51 = new JLabel("New label");
		lblNewLabel_51.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_51.setBounds(100, 98, 190, 20);
		panel_2.add(lblNewLabel_51);
		}else{}
		if(a>6*transBean.getSkhldn()+2){
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
				test(transBean);
			}

		});
		JLabel lblNewLabel_16 = new JLabel("产品名称：");
		lblNewLabel_16.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_16.setBounds(10, 22, 81, 20);
		panel_3.add(lblNewLabel_16);
		
		JLabel lblNewLabel_17 = new JLabel("New label");
		lblNewLabel_17.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_17.setBounds(91, 22, 199, 20);
		panel_3.add(lblNewLabel_17);
		
		JLabel lblNewLabel_18 = new JLabel("存       期：");
		lblNewLabel_18.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_18.setBounds(10, 47, 81, 20);
		panel_3.add(lblNewLabel_18);
		
		JLabel lblNewLabel_19 = new JLabel("New label");
		lblNewLabel_19.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_19.setBounds(91, 47, 199, 20);
		panel_3.add(lblNewLabel_19);
		
		JLabel lblNewLabel_20 = new JLabel("起存金额：");
		lblNewLabel_20.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_20.setBounds(10, 72, 81, 20);
		panel_3.add(lblNewLabel_20);
		
		JLabel lblNewLabel_21 = new JLabel("New label");
		lblNewLabel_21.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_21.setBounds(91, 72, 199, 20);
		panel_3.add(lblNewLabel_21);
		
		JLabel lblNewLabel_22 = new JLabel("存款结息规则：");
		lblNewLabel_22.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_22.setBounds(0, 124, 105, 20);
		panel_3.add(lblNewLabel_22);
		
		JLabel lblNewLabel_23 = new JLabel("New label");
		lblNewLabel_23.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_23.setBounds(105, 120, 215, 40);
		panel_3.add(lblNewLabel_23);
		
		JLabel lblNewLabel_52 = new JLabel("止存金额：");
		lblNewLabel_52.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_52.setBounds(10, 97, 81, 20);
		panel_3.add(lblNewLabel_52);
		
		JLabel lblNewLabel_53 = new JLabel("New label");
		lblNewLabel_53.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_53.setBounds(91, 97, 199, 20);
		panel_3.add(lblNewLabel_53);
		}else{}
		if(a>6*transBean.getSkhldn()+3){
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
				test(transBean);
			}

		});
		JLabel lblNewLabel_24 = new JLabel("产品名称：");
		lblNewLabel_24.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_24.setBounds(10, 23, 75, 20);
		panel_4.add(lblNewLabel_24);
		
		JLabel lblNewLabel_25 = new JLabel("New label");
		lblNewLabel_25.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_25.setBounds(95, 23, 195, 20);
		panel_4.add(lblNewLabel_25);
		
		JLabel lblNewLabel_26 = new JLabel("存       期：");
		lblNewLabel_26.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_26.setBounds(10, 48, 75, 20);
		panel_4.add(lblNewLabel_26);
		
		JLabel lblNewLabel_27 = new JLabel("New label");
		lblNewLabel_27.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_27.setBounds(95, 48, 195, 20);
		panel_4.add(lblNewLabel_27);
		
		JLabel lblNewLabel_28 = new JLabel("起存金额：");
		lblNewLabel_28.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_28.setBounds(10, 73, 75, 20);
		panel_4.add(lblNewLabel_28);
		
		JLabel lblNewLabel_29 = new JLabel("New label");
		lblNewLabel_29.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_29.setBounds(95, 73, 195, 20);
		panel_4.add(lblNewLabel_29);
		
		JLabel lblNewLabel_30 = new JLabel("存款结息规则：");
		lblNewLabel_30.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_30.setBounds(0, 124, 105, 20);
		panel_4.add(lblNewLabel_30);
		
		JLabel lblNewLabel_31 = new JLabel("New label");
		lblNewLabel_31.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_31.setBounds(105, 120, 195, 40);
		panel_4.add(lblNewLabel_31);
		
		JLabel lblNewLabel_54 = new JLabel("止存金额：");
		lblNewLabel_54.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_54.setBounds(10, 98, 75, 20);
		panel_4.add(lblNewLabel_54);
		
		JLabel lblNewLabel_55 = new JLabel("New label");
		lblNewLabel_55.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_55.setBounds(95, 98, 195, 20);
		panel_4.add(lblNewLabel_55);
		}else{}
		if(a>6*transBean.getSkhldn()+4){
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
				test(transBean);
			}

		});
		JLabel lblNewLabel_32 = new JLabel("产品名称：");
		lblNewLabel_32.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_32.setBounds(10, 23, 80, 20);
		panel_5.add(lblNewLabel_32);
		
		JLabel lblNewLabel_33 = new JLabel("New label");
		lblNewLabel_33.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_33.setBounds(100, 23, 190, 20);
		panel_5.add(lblNewLabel_33);
		
		JLabel lblNewLabel_34 = new JLabel("存       期：");
		lblNewLabel_34.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_34.setBounds(10, 49, 80, 20);
		panel_5.add(lblNewLabel_34);
		
		JLabel lblNewLabel_35 = new JLabel("New label");
		lblNewLabel_35.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_35.setBounds(100, 49, 190, 20);
		panel_5.add(lblNewLabel_35);
		
		JLabel lblNewLabel_36 = new JLabel("起存金额：");
		lblNewLabel_36.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_36.setBounds(10, 74, 80, 20);
		panel_5.add(lblNewLabel_36);
		
		JLabel lblNewLabel_37 = new JLabel("New label");
		lblNewLabel_37.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_37.setBounds(100, 74, 190, 20);
		panel_5.add(lblNewLabel_37);
		
		JLabel lblNewLabel_38 = new JLabel("存款结息规则：");
		lblNewLabel_38.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_38.setBounds(0, 124, 105, 20);
		panel_5.add(lblNewLabel_38);
		
		JLabel lblNewLabel_39 = new JLabel("New label");
		lblNewLabel_39.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_39.setBounds(105, 120, 190, 40);
		panel_5.add(lblNewLabel_39);
		
		JLabel lblNewLabel_56 = new JLabel("止存金额：");
		lblNewLabel_56.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_56.setBounds(10, 99, 80, 20);
		panel_5.add(lblNewLabel_56);
		
		JLabel lblNewLabel_57 = new JLabel("New label");
		lblNewLabel_57.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_57.setBounds(100, 99, 190, 20);
		panel_5.add(lblNewLabel_57);
		}else{}
		if(a>6*transBean.getSkhldn()+5){
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
				test(transBean);
			}

		});
		JLabel lblNewLabel_40 = new JLabel("产品名称：");
		lblNewLabel_40.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_40.setBounds(10, 22, 81, 20);
		panel_6.add(lblNewLabel_40);
		
		JLabel lblNewLabel_41 = new JLabel("New label");
		lblNewLabel_41.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_41.setBounds(92, 22, 198, 20);
		panel_6.add(lblNewLabel_41);
		
		JLabel lblNewLabel_42 = new JLabel("存       期：");
		lblNewLabel_42.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_42.setBounds(10, 46, 81, 20);
		panel_6.add(lblNewLabel_42);
		
		JLabel lblNewLabel_43 = new JLabel("New label");
		lblNewLabel_43.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_43.setBounds(92, 46, 198, 20);
		panel_6.add(lblNewLabel_43);
		
		JLabel lblNewLabel_44 = new JLabel("起存金额：");
		lblNewLabel_44.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_44.setBounds(10, 71, 81, 20);
		panel_6.add(lblNewLabel_44);
		
		JLabel lblNewLabel_45 = new JLabel("New label");
		lblNewLabel_45.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_45.setBounds(92, 71, 198, 20);
		panel_6.add(lblNewLabel_45);
		
		JLabel lblNewLabel_46 = new JLabel("存款结息规则：");
		lblNewLabel_46.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_46.setBounds(0, 120, 105, 20);
		panel_6.add(lblNewLabel_46);
		
		JLabel lblNewLabel_47 = new JLabel("New label");
		lblNewLabel_47.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_47.setBounds(105, 120, 198, 40);
		panel_6.add(lblNewLabel_47);
		
		JLabel lblNewLabel_58 = new JLabel("止存金额：");
		lblNewLabel_58.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_58.setBounds(10, 96, 81, 20);
		panel_6.add(lblNewLabel_58);
		
		JLabel lblNewLabel_59 = new JLabel("New label");
		lblNewLabel_59.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_59.setBounds(91, 96, 199, 20);
		panel_6.add(lblNewLabel_59);
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

//		/* 下一页 */
//		JButton okButton = new JButton(new ImageIcon("pic/next_step.png"));
//
//		okButton.setHorizontalTextPosition(SwingConstants.CENTER);
//		okButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
//		okButton.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
//		okButton.setContentAreaFilled(false);// 设置图片填满按钮所在的区域
//		// okButton.setMargin(new Insets(0, 0, 0, 0));//设置按钮边框和标签文字之间的距离
//		// okButton.setIcon(new ImageIcon("pic/inputinfo_1.png"));
//		okButton.setFocusPainted(true);// 设置这个按钮是不是获得焦点
//		okButton.setBorderPainted(false);// 设置是否绘制边框
//		okButton.setBorder(null);// 设置边框
//		okButton.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				/* 处理下一页 */
//				okTrans();
//			}
//
//		});
//		okButton.setSize(200, 50);
//		okButton.setLocation(780, 638);
//		this.add(okButton);
		
		
		int n =transBean.getSkhldn()+1;
		
		String nn=String.valueOf(n);
		JLabel lblNewLabel_120 = new JLabel(nn);
		lblNewLabel_120.setFont(new Font("宋体", Font.PLAIN, 30));
		lblNewLabel_120.setBounds(783, 568, 41, 40);
		this.showJpanel.add(lblNewLabel_120);
		
		JLabel lblNewLabel_130 = new JLabel("/");
		lblNewLabel_130.setBounds(825, 568, 21, 40);
		lblNewLabel_130.setFont(new Font("宋体", Font.PLAIN, 30));
		this.showJpanel.add(lblNewLabel_130);
		
		int  w=transBean.getSkhldpage();
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
		openPanel(new MoneyBoxCatalogSHLDCTPPanel(transBean));
		
	}
	
	/**
	 * 确认
	 * */
	public void okTrans() {

		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
//				openPanel(GlobalPanelFlag.INPUT_DEPINFO_PANEL);
			}
			
		});
	}
	
	/**
	 * 上一页
	 * @return 
	 */
	public void  upStep(PublicCashOpenBean transBean){
		 int n =transBean.getSkhldn();
     if (n==0){
    	 transBean.setSkhldn(0);
     }else{
    	 transBean.setSkhldn(n-1);
     }
 		openPanel(new MoneyBoxCatalogSHLDCXLZXTXPanel(transBean));
	}
	
	/**
	 * 下一页
	 */
	public void  nextStep(PublicCashOpenBean transBean){
		int  w=transBean.getSkhldpage();
		int n =transBean.getSkhldn();
		if(n+1<w){
			 transBean.setSkhldn(n+1);
		}else{
			transBean.setSkhldn(n);
		}
		openPanel(new MoneyBoxCatalogSHLDCXLZXTXPanel(transBean));
	}
	// 选择对应账号跳转页面
	private void test(PublicCashOpenBean transBean) {
		clearTimeText();
		openPanel(new MoneyBoxEnteringSHLDCPanel(transBean));
		
	}
}

