package com.boomhope.Bill.TransService.LostReport.Page.Lost;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Util.UtilButton;

/**
 * 选择是否知道密码页
 * @author zjg
 *
 */
@SuppressWarnings("static-access")
public class LostPassSelectPage extends BaseTransPanelNew {
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(LostPassSelectPage.class);
	private boolean on_off=true;//用于控制页面跳转的开关
	
	public LostPassSelectPage(){
		
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
		
		//知道密码
		UtilButton Button1 = new UtilButton("pic/newPic/yesPass.jpg","pic/newPic/yesPass.jpg");
		Button1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择知道密码");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
				
				yesPass();
			}

		});
		Button1.setBounds(217, 174, 200, 300);
		this.showJpanel.add(Button1);
		
		//忘记密码
		UtilButton Button2 = new UtilButton("pic/newPic/noPass.jpg", "pic/newPic/noPass.jpg");
		Button2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择忘记密码");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
				
				noPass();
			}

		});
		Button2.setBounds(585, 174, 200, 300);
		this.showJpanel.add(Button2);
		
		//温馨提示
		JLabel tishi = new JLabel("<html><p>温馨提示：选择“忘记密码/无密码”，须等待7天后办理补发或销户.</p></html>");
		tishi.setBounds(10, 510, 989, 55);
		tishi.setForeground(Color.RED);
		tishi.setFont(new Font("微软雅黑",Font.PLAIN,20));
		tishi.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(tishi);
		
		//上一步
		JLabel back = new JLabel(new ImageIcon("pic/preStep.png"));
		back.setBounds(1075, 770, 150, 50);
		back.addMouseListener(new MouseAdapter() {
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
            	
            	back();
			}
		});
		add(back);	
		
		//退出
		JLabel exit= new JLabel(new ImageIcon("pic/newPic/exit.png"));
		exit.setBounds(1258, 770, 150, 50);
		exit.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
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
	 * 知道密码
	 */
	public void yesPass(){
		
		lostPubBean.setYseNoPass("0");//已知密码标识
		if("0".equals(lostPubBean.getAllPubIsDeputy())){//本人
			//跳转补销选择页
			openPanel(new LostReceiveCancelSelectPage());
			
		}else{//代理人
			//跳转输入账号/卡号选择页
			lostPubBean.setLostOrSolve("0");//单独挂失
			lostPubBean.getReqMCM001().setTranscode("11041");
			openPanel(new LostSelectEnterAccOrCustServicePage());
			
		}
		
	}
	
	/**
	 * 忘记密码
	 */
	public void noPass(){
		
		lostPubBean.setYseNoPass("1");//未知密码标识
		lostPubBean.setLostOrSolve("0");//单独挂失
		lostPubBean.setAllPubAccPwd("");//清空密码
		if("0".equals(lostPubBean.getAllPubIsDeputy())){//本人
			//跳转账户信息存查询页
			lostPubBean.getReqMCM001().setTranscode("11040");
			openPanel(new LostCheckAllAccProcessing());
			
		}else{//代理人
			//跳转输入账号/卡号选择页
			lostPubBean.getReqMCM001().setTranscode("11042");
			openPanel(new LostSelectEnterAccOrCustServicePage());
			
		}
		
	}
	
	/**
	 * 返回上一步
	 */
	public void back(){
		backStepMethod();
		if("0".equals(lostPubBean.getAllPubIsDeputy())){
			lostPubBean.setUpStepName("ALL_PUBLIC_DEPUTY_SELECTION_PANEL");
		}else{
			lostPubBean.setUpStepName("LOST_INPUT_PHONE_AND_ADRESS");
		}
		allPubTransFlow.transFlow();
	}
	
	/**
	 * 退出交易
	 */
	public void exit(){
		
		returnHome();
	}
}
