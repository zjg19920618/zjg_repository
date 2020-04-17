package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.UtilImages1;

/***
 * 安享存系列页面
 * @author gyw
 *
 */
public class MoneyBoxCatalogAXCXLPanel extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxCatalogAXCXLPanel.class);
	
	private boolean on_off=true;//用于控制页面跳转的开关
	
	public MoneyBoxCatalogAXCXLPanel(final PublicCashOpenBean transBean){
		this.cashBean= transBean;
		
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
	
			//安享存
			UtilImages1 image = new UtilImages1("pic/xyax.png");
			image.setSize(250, 210);
			image.setLocation(380, 257);
			image.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					/* 处理上一页 */
					nextPage1(transBean);
				}
			});
			add(image);
			
			
		
		
		
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

	}
	

	
	/**
	 * 返回
	 * */
	public void backTrans(PublicCashOpenBean transBean) {
		//根据产品代码判断返回
		clearTimeText();
		openPanel(new MoneyBoxAgreementPanel(transBean));
		
	}
	
	/**
	 * 确认
	 * */
	public void okTrans(PublicCashOpenBean transBean) {
		clearTimeText();
		openPanel(new MoneyBoxInPutdepInfoPanel(transBean));
			
	}
	
	/**
	 * 安享存
	 * */
	public void nextPage1(PublicCashOpenBean transBean) {
		clearTimeText();
		openPanel(new MoneyBoxCatalogAXCFYPanel(transBean));
		
	}
	
	
	
}

