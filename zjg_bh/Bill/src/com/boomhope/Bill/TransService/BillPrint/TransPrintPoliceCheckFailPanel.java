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

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.BillPrint.Agent.TransPrintInputAgentIdcardPanel;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;

/**
 * 
 * title:联网核查失败页
 * @author ly
 * 2016年11月8日上午10:44:54
 */
public class TransPrintPoliceCheckFailPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransPrintPoliceCheckFailPanel.class);

	JLabel label = null;
	private static final long serialVersionUID = 1L;

	public TransPrintPoliceCheckFailPanel(final BillPrintBean transBean) {
		logger.info("进入联网核查失败页面");
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
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("微软雅黑", Font.BOLD, 30));
		label.setForeground(Color.red);
		label.setBounds(385, 280, 540, 123);
		this.showJpanel.add(label);

		//上一步
		JLabel label1 = new JLabel(new ImageIcon("pic/preStep.png"));
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");				
				nextStep(transBean);
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
		// 设置错误信息；
		setErrmsg(transBean);
	}

	/**
	 *上一步
	 */
	public void nextStep(BillPrintBean transBean) {
		String idCardCheck =transBean.getImportMap().get("idCard_check");
		// 本人联网核查失败
		if (idCardCheck != null) {
			clearTimeText();
			openPanel(new BackTransInputIdcard1Panel(transBean));
			return;
		}
		String agentIdCardCheck = transBean.getImportMap().get("agent_idCard_check");
		// 代理人联网核查失败
		if (agentIdCardCheck != null) {
			clearTimeText();
			openPanel(new TransPrintInputAgentIdcardPanel(transBean));
		}

	}

	/**
	 * 设置错误信息
	 */
	public void setErrmsg(BillPrintBean transBean) {
		StringBuffer sbf = new StringBuffer();

		String s = transBean.getErrmsg();
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
				if ((i + 1) % 10 == 0) {
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
