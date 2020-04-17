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
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.LostReport.Page.SelectLostOrSolveLostPanel;
import com.boomhope.Bill.Util.UtilButton;

/**
 * 选择解挂补发/解挂销户/撤销挂失
 * @author hk
 *
 */
@SuppressWarnings("static-access")
public class SolveLostSelectPage extends BaseTransPanelNew {
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(SolveLostSelectPage.class);
	private boolean on_off=true;//用于控制页面跳转的开关

	public SolveLostSelectPage(){
		logger.info("进入解挂补发/解挂销户/撤销挂失页面");
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
            	excuteVoice("voice/lostSelect.wav");
            	stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
            }      
        });            
		voiceTimer.start();
		
		//选择解挂补发
		UtilButton Button1 = new UtilButton("pic/newPic/solveRePay.jpg","pic/newPic/solveRePay.jpg");
		Button1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择解挂补发");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
				
				checkRePay();
			}

		});
		Button1.setBounds(100, 174, 200, 300);
		this.showJpanel.add(Button1);
		
		//选择解挂销户
		UtilButton Button2 = new UtilButton("pic/newPic/solveCancel.jpg", "pic/newPic/solveCancel.jpg");
		Button2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择解挂销户");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
				
            	checkCancel();
			}

		});
		Button2.setBounds(400, 174, 200, 300);
		this.showJpanel.add(Button2);
		
		//选择撤销挂失
		UtilButton Button3 = new UtilButton("pic/newPic/solveReBack.jpg", "pic/newPic/solveReBack.jpg");
		Button3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择撤销挂失");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
				
            	checkLostBack();
			}

		});
		Button3.setBounds(700, 174, 200, 300);
		this.showJpanel.add(Button3);
		
		if("4".equals(GlobalParameter.printStatus)){
			Button1.setVisible(false);
			Button2.setBounds(217, 174, 200, 300);
			Button3.setBounds(585, 174, 200, 300);
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
				logger.info("选择退出");
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
	 * 选择解挂补发
	 */
	public void checkRePay(){
		//跳转插入本人身份证
		lostPubBean.setLostOrSolve("3");
		lostPubBean.getReqMCM001().setTranscode("11047");
		lostPubBean.setNextStepName("ALL_PUBLIC_INPUT_ID_CARD_PANEL");// 下一步插入本人身份证
		lostPubBean.setUpStepName("SOLVE_LOST_SELECT_PAGE");
		allPubTransFlow.transFlow();
	}
	
	/**
	 * 选择解挂销户
	 */
	public void checkCancel(){
		//跳转插入本人身份证
		lostPubBean.setLostOrSolve("4");
		lostPubBean.getReqMCM001().setTranscode("11046");
		lostPubBean.setNextStepName("ALL_PUBLIC_INPUT_ID_CARD_PANEL");// 下一步插入本人身份证
		lostPubBean.setUpStepName("SOLVE_LOST_SELECT_PAGE");
		allPubTransFlow.transFlow();
		
	}
	
	/**
	 * 选择撤销挂失
	 */
	public void checkLostBack(){
		lostPubBean.setLostOrSolve("5");
		lostPubBean.getReqMCM001().setTranscode("11045");
		lostPubBean.setNextStepName("ALL_PUBLIC_INPUT_ID_CARD_PANEL");// 下一步插入本人身份证
		lostPubBean.setUpStepName("SOLVE_LOST_SELECT_PAGE");
		allPubTransFlow.transFlow();
	}
	
	/**
	 * 退出交易
	 */
	public void exit(){
		returnHome();
	}
	
	/**
	 * 返回上一步
	 */
	public void back(){
		openPanel(new SelectLostOrSolveLostPanel());
	}
	
}
