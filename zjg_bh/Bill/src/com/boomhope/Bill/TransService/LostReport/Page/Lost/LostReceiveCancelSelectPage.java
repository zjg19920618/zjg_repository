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
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.Util.UtilButton;

/**
 * 选择挂失补领或销户
 * @author zjg
 *
 */
@SuppressWarnings("static-access")
public class LostReceiveCancelSelectPage extends BaseTransPanelNew {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(LostReceiveCancelSelectPage.class);
	private boolean on_off=true;//用于控制页面跳转的开关
	
	public LostReceiveCancelSelectPage(){
		
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
		
		//挂失补领
		UtilButton Button1 = new UtilButton("pic/newPic/lostReceive.jpg","pic/newPic/lostReceive.jpg");
		Button1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择挂失补领");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
				
				receive();
			}

		});
		Button1.setBounds(217, 174, 200, 300);
		this.showJpanel.add(Button1);
		
		//挂失销户
		UtilButton Button2 = new UtilButton("pic/newPic/lostCancel.jpg", "pic/newPic/lostCancel.jpg");
		Button2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择挂失销户");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
				
				cancel();
			}

		});
		Button2.setBounds(585, 174, 200, 300);
		this.showJpanel.add(Button2);	
		
		if("4".equals(GlobalParameter.printStatus)){
			Button1.setVisible(false);
			Button2.setBounds(400, 174, 200, 300);
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
				logger.info("点击退出按钮");
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
	 * 挂失补领
	 */
	public void receive(){
		
		lostPubBean.setRecOrCan("0");//选择补领标识
		lostPubBean.setLostOrSolve("1");//挂失补领
		lostPubBean.getReqMCM001().setTranscode("11044");
		//跳转存单挂失选择页
		openPanel(new LostTypeSelectPage());
	}
	
	/**
	 * 挂失销户
	 */
	public void cancel(){
		
		lostPubBean.setRecOrCan("1");//选择销户标识
		lostPubBean.setLostOrSolve("2");//挂失销户
		lostPubBean.getReqMCM001().setTranscode("11043");
		openPanel(new LostTypeSelectPage());
	}
	
	/**
	 * 返回上一步
	 */
	public void back(){
		lostPubBean.setRecOrCan("");//选择补领标识
		lostPubBean.setLostOrSolve("");//挂失补领
		//在选择是否知道密码页面返回上一步是进入到拍照页面
		lostPubBean.setUpStepName("ALL_PUBLIC_PRINT_CAMERA_PANEL");
		openPanel(new LostPassSelectPage());
	}
	
	/**
	 * 退出交易
	 */
	public void exit(){
		
		returnHome();
	}
}
