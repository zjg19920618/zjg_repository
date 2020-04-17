package com.boomhope.Bill.TransService.LostReport.Page.SolveLost;

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
 * 选择银行卡/存单/存折解挂补发/销户
 * @author zjg
 *
 */
@SuppressWarnings("static-access")
public class SolveLostTypeSelectPage extends BaseTransPanelNew {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(SolveLostTypeSelectPage.class);
	private boolean on_off=true;//用于控制页面跳转的开关
	
	public SolveLostTypeSelectPage(){
		
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
		
		//银行卡解挂销户
		UtilButton Button1 = new UtilButton("pic/newPic/cardSolveCancel.png","pic/newPic/cardSolveCancel.png");
		Button1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择银行卡解挂销户");
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
		
		//存折解挂销户
		UtilButton Button3 = new UtilButton("pic/newPic/zheSolveCancel.png", "pic/newPic/zheSolveCancel.png");
		Button3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择存折解挂销户");
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
		
		
		//因补发/销户选择，而显示不同按钮
		if("3".equals(lostPubBean.getLostOrSolve())){//补发
			
			//存单解挂补发
			UtilButton Button2 = new UtilButton("pic/newPic/danSolvebu.png", "pic/newPic/danSolvebu.png");
			Button2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("选择存单解挂补发");
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
			
			Button1.setVisible(false);
			Button3.setVisible(false);
			
		}else if("4".equals(lostPubBean.getLostOrSolve())){//销户
			
			//存单解挂销户
			UtilButton Button2 = new UtilButton("pic/newPic/danSolvecancel.png", "pic/newPic/danSolvecancel.png");
			Button2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("选择存单解挂销户");
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
	 * 银行卡解挂
	 */
	public void card(){
		
		lostPubBean.setSolveLostType("0");//银行卡解挂标识
		lostPubBean.setNextStepName("SOLVE_LOST_INPUT_APPLY_NO_PAGE");// 下一步录入挂失申请书页
		lostPubBean.setUpStepName("SOLVE_LOST_TYPE_SELECT_PAGE");
		allPubTransFlow.transFlow();
	}
	
	/**
	 * 存单解挂
	 */
	public void accDeposit(){
		
		lostPubBean.setSolveLostType("1");//个人存单解挂标识
		lostPubBean.setNextStepName("SOLVE_LOST_INPUT_APPLY_NO_PAGE");// 下一步录入挂失申请书页
		lostPubBean.setUpStepName("SOLVE_LOST_TYPE_SELECT_PAGE");
		allPubTransFlow.transFlow();
	}
	
	/**
	 * 存折解挂
	 */
	public void accDeposit1(){
		
		lostPubBean.setSolveLostType("2");//存折解挂标识
		lostPubBean.setNextStepName("SOLVE_LOST_INPUT_APPLY_NO_PAGE");// 下一步录入挂失申请书页
		lostPubBean.setUpStepName("SOLVE_LOST_TYPE_SELECT_PAGE");
		allPubTransFlow.transFlow();
	}
	
	/**
	 * 返回上一步
	 */
	public void back(){
		lostPubBean.setSolveLostType("");//存折解挂标识
		lostPubBean.setNextStepName("ALL_PUBLIC_PRINT_CAMERA_PANEL");//上一步返回拍照页面
		lostPubBean.setUpStepName("SOLVE_LOST_SELECT_PAGE");//拍照页面的上一步
		allPubTransFlow.transFlow();
	}
	
	/**
	 * 退出交易
	 */
	public void exit(){
		
		returnHome();
	}
}
