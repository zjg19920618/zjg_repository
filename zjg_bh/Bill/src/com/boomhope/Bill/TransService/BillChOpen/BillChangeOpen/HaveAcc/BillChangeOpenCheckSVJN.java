package com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;

public class BillChangeOpenCheckSVJN extends BaseTransPanelNew{

	/**
	 * 查询当日流水信息
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(BillChangeOpenCheckSVJN.class);
	private Component comp;
	public BillChangeOpenCheckSVJN(){
		comp=this;
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondShortTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondShortTime*1000,new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
			
		});
		delayTimer.start();
		        // 标题
				JLabel depoLum = new JLabel("正在查询账户流水信息，请稍候......");
				depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
				depoLum.setHorizontalAlignment(0);
				depoLum.setForeground(Color.decode("#412174"));
				depoLum.setBounds(0, 60, 1009, 60);
				this.showJpanel.add(depoLum);
				/* 加载凭证动画 */
				JLabel billImage = new JLabel();   
				billImage.setIcon(new ImageIcon("pic/checking.gif"));
				billImage.setIconTextGap(6);
				billImage.setBounds(404,170, 340, 253);
				this.showJpanel.add(billImage);		
				check(comp);
	}
	/**
	 * 查询账户流水信息
	 */
	public void check(final Component comp){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				//查询账户流水信息
				bcOpenAction.checksvjn(comp);
			}
		}).start();
	}
}
