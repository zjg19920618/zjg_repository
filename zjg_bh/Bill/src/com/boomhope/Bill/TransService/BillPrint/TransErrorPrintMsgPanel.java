package com.boomhope.Bill.TransService.BillPrint;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseLoginFrame;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.BillPrint.Agent.TransPrintInputAgentIdcardPanel;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.peripheral.action.MachineLed;
import com.boomhope.Bill.peripheral.bean.LedStateBean;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 
 * title:统一错误页面
 * @author ly
 * 2016年11月7日下午9:55:03
 */
public class TransErrorPrintMsgPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransErrorPrintMsgPanel.class);
	JLabel label = null;
	private static final long serialVersionUID = 1L;

	public TransErrorPrintMsgPanel(final BillPrintBean transBean) {
		logger.info("进入统一错误页");
		this.billPrintBean = transBean;
		/* 显示时间倒计时 */
		
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		delayTimer.start();
		/* 警告图标 */
		JLabel billImage = new JLabel();
		billImage.setIcon(new ImageIcon("pic/war_icon.png"));
		billImage.setIconTextGap(6);
		billImage.setBounds(300, 281, 47, 44);
		this.showJpanel.add(billImage);

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("微软雅黑", Font.BOLD, 30));
		label.setBounds(385, 280, 540, 123);
		this.showJpanel.add(label);			
		//上一步
		JLabel label1 = new JLabel(new ImageIcon("pic/preStep.png"));
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");				
				backStep(transBean);
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
				returnHome();
			}

		});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);
		setErrmsg(transBean);
		outBile(transBean);
	}

	public void outBile(BillPrintBean transBean) {
		String backstep = transBean.getImportMap().get("backStep");
		if (String.valueOf(GlobalPanelFlag.PRINT_CHECK_SERVICE).equals(backstep)) {
			Thread thread = new Thread() {
				public void run() {
					closeBill();
				}
			};
			thread.start();
		}
	}
	/**
	 * 关闭
	 */
	public void closeBill(){
		new Thread(){
			public void run(){
				try {
					LedStateBean closeLed = MachineLed.openLed("6");//开白灯
					logger.info("6号Led灯打开返回值："+closeLed);
					LedStateBean closeLed1 = MachineLed.openLed("7");//开Led灯
					logger.info("7号Led灯打开返回值："+closeLed1);
				} catch (Exception e) {
					logger.error("led灯通讯异常");
				}
				closeLed();//关灯
				// 1.退票给用户
				Dispatch.call(BaseLoginFrame.dispath, "BH_Eject",new Variant("60000"));
				// 2.关闭
				//Dispatch.call(BaseLoginFrame.dispath, "BH_Close");
				// 3.清理资源
				Dispatch.call(BaseLoginFrame.dispath, "BH_CleanResource");
			}
		}.start();
	}
	
	/**
	 * 定时关闭led灯
	 */
	public void closeLed(){
		new Thread(){
			Timer voiceTimer =null;
			public void run(){
				// 倒计时结束关灯
				voiceTimer = new Timer(0*1000, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							LedStateBean closeLed = MachineLed.closeLed("6");//关白灯
							logger.info("6号Led灯关闭返回值："+closeLed);
							LedStateBean closeLed1 = MachineLed.closeLed("7");//关Led灯
							logger.info("7号Led灯关闭返回值："+closeLed1);
						} catch (Exception e1) {
							logger.error("led灯通讯异常");
						}
						voiceTimer.stop();
					}
				});
				voiceTimer.start();
			}
		}.start();
	}
	/**
	 * 上一步
	 */
	public void backStep(BillPrintBean transBean) {
		if(transBean.getImportMap().get("backStep")!=null&&transBean.getImportMap().get("backStep").equals(GlobalPanelFlag.INPUT_IDCARD+"")){
			clearTimeText();
			openPanel(new BackTransInputIdcard1Panel(transBean));
		}else if(transBean.getImportMap().get("backStep")!=null&&transBean.getImportMap().get("backStep").equals(GlobalPanelFlag.INPUT_AGENT_IDCARD+"")){
			clearTimeText();
			openPanel(new TransPrintInputAgentIdcardPanel(transBean));
		}else if(transBean.getImportMap().get("backStep")!=null&&transBean.getImportMap().get("backStep").equals(GlobalPanelFlag.INPUT_BANK_CARD+"")){
			clearTimeText();
			openPanel(new TransPrintBackInputBankCardPanel(transBean));
		}
	}

	/**
	 * 设置错误信息
	 */
	public void setErrmsg(BillPrintBean transBean) {
		StringBuffer sbf = new StringBuffer();
		String s = transBean.getErrmsg();
		if (s == null) {
			label.setText("未知异常");
			return;
		}
		// 循环
		int cpCount = s.codePointCount(0, s.length());
		for (int i = 0; i < cpCount; i++) {
			int index = s.offsetByCodePoints(0, i);
			int cp = s.codePointAt(index);
			if (!Character.isSupplementaryCodePoint(cp)) {
				if (i == 0) {
					sbf.append("<html><p>");
				}
				sbf.append((char) cp);
				if ((i + 1) % 14 == 0) {
					sbf.append("</p><p>");
				}
				if (cpCount == (i + 1)) {
					sbf.append("</p></html>");
				}
			} else {
				if (i == 0) {
					sbf.append("<html><p>");
				}
				sbf.append((char) cp);
				if ((i + 1) % 10 == 0) {
					sbf.append("</p><p>");
				}
				if (cpCount == (i + 1)) {
					sbf.append("</p></html>");
				}
			}
		}
		label.setText(sbf.toString());
	}
}
