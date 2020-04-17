package com.boomhope.Bill.TransService.CashOpen.moneybox;

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
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.UtilButton;

public class MoneyBoxNotCompleteCustPromPanel extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxNotCompleteCustPromPanel.class);
	
	private boolean on_off=true;//开关控制
	public MoneyBoxNotCompleteCustPromPanel(final PublicCashOpenBean transBean) {
		setLayout(null);
		this.cashBean = transBean;
		
		JLabel label = new JLabel("尊敬的客户：您在我行预留信息不完整，请尽快补充完善信息，");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label.setForeground(Color.red);
		label.setBounds(0, 300, 1009, 30);
		this.showJpanel.add(label);
		
		JLabel lblNewLabel = new JLabel("谢谢您的配合。");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel.setBounds(0, 350, 1009, 30);
		lblNewLabel.setForeground(Color.red);
		this.showJpanel.add(lblNewLabel);
		
		//确认
		JLabel lblNewLabel1 = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		lblNewLabel1.setBounds(1075, 770, 150, 50);
		lblNewLabel1.addMouseListener(new MouseAdapter() {
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
				success(transBean);
			}

		});
		this.add(lblNewLabel1);
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
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	on_off=false;
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });   
		delayTimer.start(); 
	}
	/**
	 * 成功
	 */
	private void success(PublicCashOpenBean transBean){
		
		String idCard_check_result = transBean.getImportMap().get("agent_persion");
		if("yes".equals(idCard_check_result)){
			//代理人
			openPanel(new MoneyBoxInputAgentIdcardPanel(transBean));
		}else{
			transBean.setJijvOrPuhui("");//清空普惠标示
			openPanel(new MoneyBoxPageSelectlPanel(transBean));
		}
	}

}
