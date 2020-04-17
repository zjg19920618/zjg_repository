package com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc;

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
import com.boomhope.Bill.Public.GlobalParameter;
/**
 * 存单换开成功
 */
public class BillChangeOpenSuccessPanel  extends BaseTransPanelNew{

	Logger logger = Logger.getLogger(BillChangeOpenSuccessPanel.class);
	private static final long serialVersionUID = 1L;
    public  BillChangeOpenSuccessPanel(){
               logger.info("进入存单换开成功页面");
		//设置倒计时
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime*1000, new ActionListener() {          
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
            	closeVoice();//关闭语音流
            	excuteVoice("voice/hkcg.wav");
            	
            }      
        });            
		voiceTimer.start();
 
         //提示信息
		JLabel label = new JLabel("请取回您的存单，妥善保管！");
		label.setFont(new Font("微软雅黑",Font.BOLD,40));
		label.setHorizontalAlignment(0);
		label.setBounds(0,260,1009,50);
		this.showJpanel.add(label);
		
		JLabel label3 = new JLabel("存单换开流水号:"+bcOpenBean.getHKSvrJrnlNo());
		label3.setHorizontalAlignment(SwingConstants.CENTER);
		label3.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		label3.setBounds(0, 320, 1009, 30);
		this.showJpanel.add(label3);
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.setBounds(1258, 770, 150, 50);
		add(label_1);
		label_1.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				logger.info("点击退出按钮");
				clearTimeText();
				billHKExit();
			}
		});
				
    }
    
}
