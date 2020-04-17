package com.boomhope.Bill.TransService.AccCancel.AccountCancel.HaveAcc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.PublicUse.AccCancelOutputDepositPanel;
import com.boomhope.Bill.Util.UtilButton;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.checkInfo.AccCancelCameraPanel;
/**
 * 插入银行卡或手输银行卡密码方式选择页面
 * @author wang.sk
 *
 */
public class AccCancelSelectMethodPanel extends BaseTransPanelNew {
	private static final long serialVersionUID = 1L;
	private boolean on_off=true;//用于控制页面跳转的开关
	static Logger logger = Logger.getLogger(AccCancelSelectMethodPanel.class);
	public AccCancelSelectMethodPanel() {
		/* 显示时间倒计时 */
		this.showTimeText(BaseTransPanelNew.delaySecondShortTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		/* 倒计时打开语音 */
		voiceTimer = new Timer(BaseTransPanelNew.voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) { 
            	stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	excuteVoice("voice/checkCancel.wav");
            	
            }      
        });            
		voiceTimer.start();
		
		//插入银行卡
		UtilButton haveAcc = new UtilButton("pic/newPic/inputbank.png",
				"pic/newPic/inputbank.png");
		haveAcc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				clearTimeText();//清空倒计时
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	
            	accCancelBean.setInCardOrHandCard("1");//插卡标识
				openPanel(new AccCancelInputBankCardPanel());
			}

		});
		haveAcc.setSize(200, 300);
		haveAcc.setLocation(217, 174);
		haveAcc.setIcon(new ImageIcon("pic/newPic/inputbank.png"));
		this.showJpanel.add(haveAcc);
		
		
		
		//手输银行卡卡号
		UtilButton noAcc = new UtilButton("pic/newPic/inputbankcard.png",
				"pic/newPic/inputbankcard.png");
		noAcc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				clearTimeText();//清空倒计时
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	
            	accCancelBean.setInCardOrHandCard("2");//手输标识
				openPanel(new AccCancelHandInputBankCardPanel());
			}

		});
		noAcc.setSize(200, 300);
		noAcc.setLocation(571, 174);
		noAcc.setIcon(new ImageIcon("pic/newPic/inputbankcard.png"));
		this.showJpanel.add(noAcc);

		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.setBounds(1075, 770, 150, 50);
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				clearTimeText();//清空倒计时
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	if("1".equals(accCancelBean.getJudgeState())){
            		//提前支取返回拍照页面
            		openPanel(new AccCancelCameraPanel());
            		
            	}else{
            		
            		if("1".equals(accCancelBean.getAmtState())){
            			
            			//到期金额大于等于5万返回拍照页面
            			openPanel(new AccCancelCameraPanel());
            			
            		}else{
            			
            			openPanel(new AccCancelCheckBillPanel());
            			
            		}
            	}
			}
		});
		add(submitBtn);					
				
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.setBounds(1258, 770, 150, 50);
		add(label_1);
		label_1.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				clearTimeText();//清空倒计时
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
				openPanel(new AccCancelOutputDepositPanel());
			}
		});
	}
}
