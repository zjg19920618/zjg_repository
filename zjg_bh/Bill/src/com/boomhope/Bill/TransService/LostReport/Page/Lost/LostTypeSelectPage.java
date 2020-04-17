package com.boomhope.Bill.TransService.LostReport.Page.Lost;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Util.UtilButton;

/**
 * 选择银行卡/存单/存折挂失
 * @author zjg
 *
 */
@SuppressWarnings("static-access")
public class LostTypeSelectPage extends BaseTransPanelNew {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(LostTypeSelectPage.class);
	private boolean on_off=true;//用于控制页面跳转的开关
	
	public LostTypeSelectPage(){
		
		baseTransBean.setThisComponent(this);
		
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondShortTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondShortTime * 1000,new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				/*倒计时结束退出交易*/
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易！","","");
			}
		});
		delayTimer.start();
		
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
            	excuteVoice("voice/lostSelect.wav");
            	
            }      
        });            
		voiceTimer.start();
		
		//银行卡挂失
		UtilButton Button1 = new UtilButton("pic/newPic/cardLost.jpg","pic/newPic/cardLost.jpg");
		Button1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择银行卡挂失");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
				
				card();
			}

		});
		Button1.setBounds(100, 174, 200, 300);
		this.showJpanel.add(Button1);
		
		//存单挂失
		UtilButton Button2 = new UtilButton("pic/newPic/accLost.jpg", "pic/newPic/accLost.jpg");
		Button2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择存单挂失");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
				
				accDeposit();
			}

		});
		Button2.setBounds(400, 174, 200, 300);
		this.showJpanel.add(Button2);	
		
		//存折挂失
		UtilButton Button3 = new UtilButton("pic/newPic/acc1Lost.jpg", "pic/newPic/acc1Lost.jpg");
		Button3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择存折挂失");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
				
            	accDeposit1();
			}

		});
		Button3.setBounds(700, 174, 200, 300);
		this.showJpanel.add(Button3);			
		
		//因补领/销户选择，而显示不同按钮
		if("0".equals(lostPubBean.getRecOrCan())){//补领
			Button1.setVisible(false);
			Button3.setVisible(false);
			
		}else{//销户
			
		}
		
		//上一步
		JLabel back = new JLabel(new ImageIcon("pic/preStep.png"));
		back.setBounds(1075, 770, 150, 50);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				clearTimeText();//清空倒计时
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	
            	back();
			}
		});
		add(back);	
		
		//退出
		JLabel exit= new JLabel(new ImageIcon("pic/newPic/exit.png"));
		exit.setBounds(1258, 770, 150, 50);
		exit.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				logger.info("点击退出");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	
            	exit();
			}
		});
		add(exit);				
	}
	
	/**
	 * 银行卡挂失
	 */
	public void card(){
		
		lostPubBean.setLostType("0");//银行卡挂失标识
		openPanel(new LostCheckAllAccProcessing());
	}
	
	/**
	 * 存单挂失
	 */
	public void accDeposit(){
		
		lostPubBean.setLostType("1");//存单挂失标识
		openPanel(new LostCheckAllAccProcessing());
	}
	
	/**
	 * 存折挂失
	 */
	public void accDeposit1(){
		
		lostPubBean.setLostType("2");//存折挂失标识
		openPanel(new LostCheckAllAccProcessing());
	}
	
	/**
	 * 返回上一步
	 */
	public void back(){
		lostPubBean.setLostType("");//存折挂失标识
		openPanel(new LostReceiveCancelSelectPage());
	}
	
	/**
	 * 退出交易
	 */
	public void exit(){
		
		returnHome();
	}
}
