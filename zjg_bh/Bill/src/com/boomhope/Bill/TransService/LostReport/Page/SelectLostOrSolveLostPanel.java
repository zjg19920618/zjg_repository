package com.boomhope.Bill.TransService.LostReport.Page;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.AllTransPublicPanel.IDCard.AllPublicDeputySelectionPanel;
import com.boomhope.Bill.TransService.LostReport.Page.SolveLost.SolveLostSelectPage;
import com.boomhope.Bill.Util.UtilButton;

/**
 * 选择挂失/解挂页
 * @author zjg
 *
 */
@SuppressWarnings("static-access")
public class SelectLostOrSolveLostPanel extends BaseTransPanelNew {
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(SelectLostOrSolveLostPanel.class);
	private boolean on_off=true;//用于控制页面跳转的开关

	public SelectLostOrSolveLostPanel(){
		
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
		
		//选择挂失
		UtilButton Button1 = new UtilButton("pic/newPic/lost.jpg","pic/newPic/lost.jpg");
		Button1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择挂失");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
				
				checkLost();
			}

		});
		Button1.setBounds(217, 174, 200, 300);
		this.showJpanel.add(Button1);
		
		//选择解挂
		UtilButton Button2 = new UtilButton("pic/newPic/solve.jpg", "pic/newPic/solve.jpg");
		Button2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择解挂");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
				
            	checkSolveLost();
			}

		});
		Button2.setBounds(585, 174, 200, 300);
		this.showJpanel.add(Button2);
		
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
	 * 选择挂失
	 */
	public void checkLost(){
		
		lostPubBean.setLOrS("0");//挂失
		lostPubBean.setNextStepName("ALL_PUBLIC_INPUT_ID_CARD_PANEL");// 下一步插入本人身份证
		lostPubBean.setUpStepName("SELECT_LOST_OR_PANEL");
		//是否代理人选择页
		openPanel(new AllPublicDeputySelectionPanel());
		
	}
	
	/**
	 * 选择解挂
	 */
	public void checkSolveLost(){//只能本人账户挂失
		
		lostPubBean.setLOrS("1");//解挂
		lostPubBean.setNextStepName("ALL_PUBLIC_INPUT_ID_CARD_PANEL");// 下一步插入本人身份证
		lostPubBean.setUpStepName("SELECT_LOST_OR_PANEL");
		//选择解挂类型
		openPanel(new SolveLostSelectPage());
	}
	
	/**
	 * 退出交易
	 */
	public void exit(){
		
		returnHome();
	}
}
