package com.boomhope.Bill.TransService.BillPrint;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.ICBankBean;

/***
 * 银行卡退出界面Panel
 * @author hao
 *
 */
public class OutputBankCardPanel extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(OutputBankCardPanel.class);
	private static final long serialVersionUID = 1L;
	
	/***
	 * 初始化
	 * @param bankBeans 
	 */
	public OutputBankCardPanel(final BillPrintBean transBean, final List<ICBankBean> bankBeans){
		logger.info("进入退出银行卡页面");
		this.billPrintBean=transBean;
		this.bankBeans=bankBeans;
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
	        public void actionPerformed(ActionEvent e) {  
	        	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
	        }      
	    });            
		delayTimer.start(); 
		

		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
	        public void actionPerformed(ActionEvent e) {  
	        	stopTimer(voiceTimer);//关闭语音
	        	closeVoice();
	        	try {
					closeLed("2");
				} catch (Exception e1) {
					logger.error("LED灯关闭失败"+e);
				}//关银行卡led灯
	        	excuteVoice("voice/quitCardgo.wav");//执行语音
	        }      
	    });            
		voiceTimer.start();
		
		
		/* 标题信息 */
		JLabel titleLabel = new JLabel("银行卡已退出，请取走您的卡继续操作");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 60, GlobalParameter.TRANS_WIDTH-50, 60);
		this.showJpanel.add(titleLabel);
		
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/output_bank.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(325, 190, 362, 320);
		this.showJpanel.add(billImage);
		//确认
		JLabel backButton = new JLabel();
		backButton.setIcon(new ImageIcon("pic/newPic/confirm.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				clearTimeText();
				openPanel(new TransInputBankCardPassPanel(transBean, bankBeans));
			}
		});
		add(backButton);				
		
		
		
	}

	
		
		
		
	
}
