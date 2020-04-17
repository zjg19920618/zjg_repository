package com.boomhope.Bill.TransService.BillPrint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.ICBankBean;

/*
 * 选择要打印存单的银行卡
 */
public class TransPrintCheckBankInfo extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransPrintCheckBankInfo.class);
	private static final long serialVersionUID = 1L;

	JLabel lblNewLabel_2;
	JLabel lblNewLabel_4;
	JLabel lblNewLabel_7;
	JLabel lblNewLabel_9;
	JLabel lblNewLabel_15;
	JLabel lblNewLabel_16;
	JLabel lblNewLabel_21;
	JLabel lblNewLabel_22;
	JLabel lblNewLabel_27;
	JLabel lblNewLabel_28;
	JLabel lblNewLabel_33;
	JLabel lblNewLabel_34;

	public TransPrintCheckBankInfo(final BillPrintBean transBean,
			List<ICBankBean> bankBeans) {
		this.billPrintBean = transBean;
		this.bankBeans = bankBeans;
		logger.info("进入选择银行卡页面");
		Object[][] data = new Object[this.bankBeans.size()][3];
		int count = 0;
		for (ICBankBean ICAccNo : this.bankBeans) {
			// 查身份证下的银行卡
			data[count][0] = transBean.getIdCardName();// 姓名
			data[count][1] = ICAccNo.getICAccNo(); // 账户
			if (ICAccNo.getType().equals("7")) {
				data[count][2] = "银行卡子账户"; // 类型
			}
			if (ICAccNo.getType().equals("11")) {
				data[count][2] = "电子账户"; // 类型
			}
			count += 1;
		}
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* 倒计时结束退出交易 */
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		delayTimer.start();
		// 字体
		JLabel label28 = new JLabel("请选择银行卡");
		label28.setHorizontalAlignment(JLabel.CENTER);
		label28.setForeground(Color.decode("#412174"));
		label28.setFont(new Font("微软雅黑", Font.BOLD, 40));
		label28.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(label28);

		logger.debug("N" + transBean.getN());
		logger.debug("X" + transBean.getX());
		// 测试对应值
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);
				excuteVoice("voice/checkBank2.wav");
			}
		});
		voiceTimer.start();

		int a = data.length;
		int z = a % 4;
		int z1 = a / 4;
		if (z != 0) {
			transBean.setPage(z1 + 1);
			logger.debug(z1 + 1);
		} else {
			transBean.setPage(z1);
			logger.debug(z1);
		}
		if (0 + 4 * transBean.getN() - 4 * transBean.getX() < a) {

			if (a >= 1 + 4 * transBean.getN() || transBean.getX() >= 1) {
				JPanel panel_1 = new JPanel();
				panel_1.setBounds(117, 140, 300, 160);
				panel_1.setLayout(null);
				this.showJpanel.add(panel_1);
				JLabel lblNewLabel_1 = new JLabel();
				lblNewLabel_1.setBounds(28, 35, 80, 20);
				lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 20));
				lblNewLabel_1.setHorizontalAlignment(JLabel.LEFT);
				lblNewLabel_1.setText("姓名：");
				panel_1.add(lblNewLabel_1);
				panel_1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						test(transBean, lblNewLabel_4.getText());
					}

				});

				for (int i = 0 + 4 * transBean.getN() - 4 * transBean.getX(); i < 1
						+ 4 * transBean.getN() - 4 * transBean.getX(); i++) {
					Object[] objects = data[i];
					Object object11 = objects[0];
					lblNewLabel_2 = new JLabel(object11.toString());
					lblNewLabel_2.setBounds(85,35, 200, 20);
					lblNewLabel_2.setFont(new Font("宋体", Font.BOLD, 20));
					lblNewLabel_2.setHorizontalAlignment(JLabel.LEFT);
					panel_1.add(lblNewLabel_2);
				}
				JLabel lblNewLabel_3 = new JLabel();
				lblNewLabel_3.setBounds(28, 75, 80, 20);
				lblNewLabel_3.setFont(new Font("宋体", Font.BOLD, 20));
				lblNewLabel_3.setHorizontalAlignment(JLabel.LEFT);
				lblNewLabel_3.setText("卡号：");
				panel_1.add(lblNewLabel_3);

				for (int i = 0 + 4 * transBean.getN() - 4 * transBean.getX(); i < 1
						+ 4 * transBean.getN() - 4 * transBean.getX(); i++) {
					Object[] objects = data[i];
					Object object12 = objects[1];
					lblNewLabel_4 = new JLabel(object12.toString());
					lblNewLabel_4.setBounds(85, 75, 250, 20);
					lblNewLabel_4.setFont(new Font("宋体", Font.BOLD, 20));
					lblNewLabel_4.setHorizontalAlignment(JLabel.LEFT);
					panel_1.add(lblNewLabel_4);
				}
				JLabel lblNewLabel_5 = new JLabel();
				lblNewLabel_5.setBounds(28, 115, 80, 20);
				lblNewLabel_5.setFont(new Font("宋体", Font.BOLD, 20));
				lblNewLabel_5.setHorizontalAlignment(JLabel.LEFT);
				lblNewLabel_5.setText("类型：");
				panel_1.add(lblNewLabel_5);
				for (int i = 0 + 4 * transBean.getN() - 4 * transBean.getX(); i < 1
						+ 4 * transBean.getN() - 4 * transBean.getX(); i++) {
					Object[] objects = data[i];
					Object object13 = objects[2];
					JLabel lblNewLabel_6 = new JLabel(object13.toString());
					lblNewLabel_6.setBounds(85, 115, 162, 20);
					lblNewLabel_6.setFont(new Font("宋体", Font.BOLD, 20));
					lblNewLabel_6.setHorizontalAlignment(JLabel.LEFT);
					panel_1.add(lblNewLabel_6);
				}
				JLabel jlb = new JLabel();
				jlb.setLocation(0, 0);
				ImageIcon image = new ImageIcon("pic/bck.png");
				image.setImage(image.getImage().getScaledInstance(300, 160,Image.SCALE_DEFAULT ));
				jlb.setIcon(image);
				jlb.setSize(300, 160);
				jlb.setVisible(true);
				panel_1.add(jlb);
			} else {
			}
		} else {
		}
		if (1 + 4 * transBean.getN() - 4 * transBean.getX() < a) {

			if (a >= 2 + 4 * transBean.getN() || transBean.getX() >= 1) {
				JPanel panel = new JPanel();
				panel.setBounds(592, 140, 300, 160);
				panel.setLayout(null);
				this.showJpanel.add(panel);
				panel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						test(transBean, lblNewLabel_9.getText());
					}

				});
				JLabel lblNewLabel = new JLabel();
				lblNewLabel.setBounds(25, 35, 80, 20);
				lblNewLabel.setFont(new Font("宋体", Font.BOLD, 20));
				lblNewLabel.setHorizontalAlignment(JLabel.LEFT);
				lblNewLabel.setText("姓名：");
				panel.add(lblNewLabel);
				for (int i = 1 + 4 * transBean.getN() - 4 * transBean.getX(); i < 2
						+ 4 * transBean.getN() - 4 * transBean.getX(); i++) {
					Object[] objects = data[i];
					Object object14 = objects[0];
					lblNewLabel_7 = new JLabel(object14.toString());
					lblNewLabel_7.setBounds(75, 35, 200, 20);
					lblNewLabel_7.setFont(new Font("宋体", Font.BOLD, 20));
					lblNewLabel_7.setHorizontalAlignment(JLabel.LEFT);
					panel.add(lblNewLabel_7);
				}
				JLabel lblNewLabel_8 = new JLabel();
				lblNewLabel_8.setBounds(25, 75, 80, 20);
				lblNewLabel_8.setFont(new Font("宋体", Font.BOLD, 20));
				lblNewLabel_8.setHorizontalAlignment(JLabel.LEFT);
				lblNewLabel_8.setText("卡号：");
				panel.add(lblNewLabel_8);
				for (int i = 1 + 4 * transBean.getN() - 4 * transBean.getX(); i < 2
						+ 4 * transBean.getN() - 4 * transBean.getX(); i++) {
					Object[] objects = data[i];
					Object object15 = objects[1];
					lblNewLabel_9 = new JLabel(object15.toString());
					lblNewLabel_9.setBounds(75, 75, 250, 20);
					lblNewLabel_9.setFont(new Font("宋体", Font.BOLD, 20));
					lblNewLabel_9.setHorizontalAlignment(JLabel.LEFT);
					panel.add(lblNewLabel_9);
				}
				JLabel lblNewLabel_10 = new JLabel();
				lblNewLabel_10.setBounds(25, 115, 80, 20);
				lblNewLabel_10.setFont(new Font("宋体", Font.BOLD, 20));
				lblNewLabel_10.setHorizontalAlignment(JLabel.LEFT);
				lblNewLabel_10.setText("类型：");
				panel.add(lblNewLabel_10);
				for (int i = 1 + 4 * transBean.getN() - 4 * transBean.getX(); i < 2
						+ 4 * transBean.getN() - 4 * transBean.getX(); i++) {
					Object[] objects = data[i];
					Object object16 = objects[2];
					JLabel lblNewLabel_11 = new JLabel(object16.toString());
					lblNewLabel_11.setBounds(75, 115, 164, 20);
					lblNewLabel_11.setFont(new Font("宋体", Font.BOLD, 20));
					lblNewLabel_11.setHorizontalAlignment(JLabel.LEFT);
					panel.add(lblNewLabel_11);
				}
				JLabel jlb = new JLabel();
				jlb.setLocation(0, 0);
				ImageIcon image = new ImageIcon("pic/bck.png");
				image.setImage(image.getImage().getScaledInstance(300, 160,Image.SCALE_DEFAULT ));
				jlb.setIcon(image);
				jlb.setSize(300, 160);
				jlb.setVisible(true);
				panel.add(jlb);
			} else {

			}
		} else {
		}
		if (2 + 4 * transBean.getN() - 4 * transBean.getX() < a) {

			if (a >= 3 + 4 * transBean.getN() || transBean.getX() >= 1) {
				JPanel panel_2 = new JPanel();
				panel_2.setBounds(117, 328, 300, 160);
				panel_2.setLayout(null);
				this.showJpanel.add(panel_2);
				panel_2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						test(transBean, lblNewLabel_16.getText());
					}

				});

				JLabel lblNewLabel_12 = new JLabel();
				lblNewLabel_12.setBounds(25, 35, 80, 20);
				lblNewLabel_12.setFont(new Font("宋体", Font.BOLD, 20));
				lblNewLabel_12.setHorizontalAlignment(JLabel.LEFT);
				lblNewLabel_12.setText("姓名：");
				panel_2.add(lblNewLabel_12);

				JLabel lblNewLabel_13 = new JLabel();
				lblNewLabel_13.setBounds(25, 75, 100, 20);
				lblNewLabel_13.setFont(new Font("宋体", Font.BOLD, 20));
				lblNewLabel_13.setHorizontalAlignment(JLabel.LEFT);
				lblNewLabel_13.setText("卡号：");
				panel_2.add(lblNewLabel_13);

				JLabel lblNewLabel_14 = new JLabel();
				lblNewLabel_14.setBounds(25, 115, 100, 20);
				lblNewLabel_14.setFont(new Font("宋体", Font.BOLD, 20));
				lblNewLabel_14.setHorizontalAlignment(JLabel.LEFT);
				lblNewLabel_14.setText("类型：");
				panel_2.add(lblNewLabel_14);
				for (int i = 2 + 4 * transBean.getN() - 4 * transBean.getX(); i < 3
						+ 4 * transBean.getN() - 4 * transBean.getX(); i++) {
					Object[] objects = data[i];
					Object object16 = objects[0];
					lblNewLabel_15 = new JLabel(object16.toString());
					lblNewLabel_15.setBounds(75, 35, 200, 20);
					lblNewLabel_15.setFont(new Font("宋体", Font.BOLD, 20));
					lblNewLabel_15.setHorizontalAlignment(JLabel.LEFT);
					panel_2.add(lblNewLabel_15);
				}
				for (int i = 2 + 4 * transBean.getN() - 4 * transBean.getX(); i < 3
						+ 4 * transBean.getN() - 4 * transBean.getX(); i++) {
					Object[] objects = data[i];
					Object object17 = objects[1];
					lblNewLabel_16 = new JLabel(object17.toString());
					lblNewLabel_16.setBounds(75, 75, 250, 20);
					lblNewLabel_16.setFont(new Font("宋体", Font.BOLD, 20));
					lblNewLabel_16.setHorizontalAlignment(JLabel.LEFT);
					panel_2.add(lblNewLabel_16);
				}
				for (int i = 2 + 4 * transBean.getN() - 4 * transBean.getX(); i < 3
						+ 4 * transBean.getN() - 4 * transBean.getX(); i++) {
					Object[] objects = data[i];
					Object object18 = objects[2];
					JLabel lblNewLabel_17 = new JLabel(object18.toString());
					lblNewLabel_17.setBounds(75, 115, 163, 20);
					lblNewLabel_17.setFont(new Font("宋体", Font.BOLD, 20));
					lblNewLabel_17.setHorizontalAlignment(JLabel.LEFT);
					panel_2.add(lblNewLabel_17);
				}
				JLabel jlb = new JLabel();
				jlb.setLocation(0, 0);
				ImageIcon image = new ImageIcon("pic/bck.png");
				image.setImage(image.getImage().getScaledInstance(300,160,Image.SCALE_DEFAULT ));
				jlb.setIcon(image);
				jlb.setSize(300, 160);
				jlb.setVisible(true);
				panel_2.add(jlb);
			} else {
			}
		} else {
		}
		if (3 + 4 * transBean.getN() - 4 * transBean.getX() < a) {

			if (a >= 4 + 4 * transBean.getN() || transBean.getX() >= 1) {
				JPanel panel_3 = new JPanel();
				panel_3.setBounds(592, 328, 300, 160);
				panel_3.setLayout(null);
				this.showJpanel.add(panel_3);
				panel_3.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						test(transBean, lblNewLabel_22.getText());
					}

				});

				JLabel lblNewLabel_18 = new JLabel();
				lblNewLabel_18.setBounds(25, 35, 80, 20);
				lblNewLabel_18.setFont(new Font("宋体", Font.BOLD, 20));
				lblNewLabel_18.setHorizontalAlignment(JLabel.LEFT);
				lblNewLabel_18.setText("姓名：");
				panel_3.add(lblNewLabel_18);

				JLabel lblNewLabel_19 = new JLabel();
				lblNewLabel_19.setBounds(25, 75, 80, 20);
				lblNewLabel_19.setFont(new Font("宋体", Font.BOLD, 20));
				lblNewLabel_19.setHorizontalAlignment(JLabel.LEFT);
				lblNewLabel_19.setText("卡号：");
				panel_3.add(lblNewLabel_19);

				JLabel lblNewLabel_20 = new JLabel();
				lblNewLabel_20.setBounds(25, 115, 80, 20);
				lblNewLabel_20.setFont(new Font("宋体", Font.BOLD, 20));
				lblNewLabel_20.setHorizontalAlignment(JLabel.LEFT);
				lblNewLabel_20.setText("类型：");
				panel_3.add(lblNewLabel_20);
				for (int i = 3 + 4 * transBean.getN() - 4 * transBean.getX(); i < 4
						+ 4 * transBean.getN() - 4 * transBean.getX(); i++) {
					Object[] objects = data[i];
					Object object19 = objects[0];
					lblNewLabel_21 = new JLabel(object19.toString());
					lblNewLabel_21.setBounds(75, 35, 200, 20);
					lblNewLabel_21.setFont(new Font("宋体", Font.BOLD, 20));
					lblNewLabel_21.setHorizontalAlignment(JLabel.LEFT);
					panel_3.add(lblNewLabel_21);
				}
				for (int i = 3 + 4 * transBean.getN() - 4 * transBean.getX(); i < 4
						+ 4 * transBean.getN() - 4 * transBean.getX(); i++) {
					Object[] objects = data[i];
					Object object20 = objects[1];
					lblNewLabel_22 = new JLabel(object20.toString());
					lblNewLabel_22.setBounds(75, 75, 250, 20);
					lblNewLabel_22.setFont(new Font("宋体", Font.BOLD, 20));
					lblNewLabel_22.setHorizontalAlignment(JLabel.LEFT);
					panel_3.add(lblNewLabel_22);
				}

				for (int i = 3 + 4 * transBean.getN() - 4 * transBean.getX(); i < 4
						+ 4 * transBean.getN() - 4 * transBean.getX(); i++) {
					Object[] objects = data[i];
					Object object20 = objects[2];
					JLabel lblNewLabel_23 = new JLabel(object20.toString());
					lblNewLabel_23.setBounds(75, 115, 160, 20);
					lblNewLabel_23.setFont(new Font("宋体", Font.BOLD, 20));
					lblNewLabel_23.setHorizontalAlignment(JLabel.LEFT);
					panel_3.add(lblNewLabel_23);
				}
				JLabel jlb = new JLabel();
				jlb.setLocation(0, 0);
				ImageIcon image = new ImageIcon("pic/bck.png");
				image.setImage(image.getImage().getScaledInstance(300, 160,Image.SCALE_DEFAULT ));
				jlb.setIcon(image);
				jlb.setSize(300, 160);
				jlb.setVisible(true);
				panel_3.add(jlb);
			} else {
			}
		} else {
		}
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

	}

	

	/**
	 * 上一页
	 * 
	 * @return
	 */
	public void upStep(BillPrintBean transBean) {
		int n = transBean.getN();
		if (n == 0) {
			return;
		} else {
			transBean.setN(n - 1);
		}
		clearTimeText();
		openPanel(new TransPrintCheckBankInfo(transBean, bankBeans));
	}

	/**
	 * 下一页
	 */
	public void nextStep(BillPrintBean transBean) {
		
		int w = transBean.getPage();
		int n = transBean.getN();
		if (n + 1 < w) {
			transBean.setN(n + 1);
		} else {
			return;
		}
		clearTimeText();
		openPanel(new TransPrintCheckBankInfo(transBean, bankBeans));
	}

	// 选择对应账号跳转页面
	private void test(BillPrintBean transBean,String bankCard) {
		transBean.setAccNo(bankCard);
		transBean.getImportMap().put("card1", "0");
		// 被点击的panel
		transBean.setN(0);
		transBean.setX(0);
		transBean.setPage(0);
		clearTimeText();
		openPanel(new TransInputBankCardPassPanel(transBean, bankBeans));
//		if("0".equals(transBean.getChoose())){
//			//打印页面
//			clearTimeText();
//			openPanel(new TransPrintBankMimeographs(transBean, bankBeans));
//		}else {
//			//状态变更页面
//			clearTimeText();
//			openPanel(new TransInputBankCardPassPanel(transBean, bankBeans));
//		}
	}


	
}
