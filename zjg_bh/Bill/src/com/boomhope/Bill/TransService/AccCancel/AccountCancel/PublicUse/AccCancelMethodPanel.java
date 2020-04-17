package com.boomhope.Bill.TransService.AccCancel.AccountCancel.PublicUse;

import java.awt.Color;
import java.awt.Font;
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
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.HaveAcc.AccCancelInputDepositPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.NoAcc.AccCancelInputBankCardPanel;
import com.boomhope.Bill.Util.UtilButton;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
/**
 * 销户方式
 * @author hao
 *
 */
public class AccCancelMethodPanel extends BaseTransPanelNew {
	private static final long serialVersionUID = 1L;
	private boolean on_off=true;//用于控制页面跳转的开关
	static Logger logger = Logger.getLogger(AccCancelMethodPanel.class);
	public AccCancelMethodPanel() {
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
		// 标题
		JLabel depoLum = new JLabel("请选择存单销户种类");
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(depoLum);
		
		//有存单
		UtilButton haveAcc = new UtilButton("pic/newPic/haveAcc.png",
				"pic/newPic/haveAcc.png");
		haveAcc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				// 有存单
				clearTimeText();
				//设置标识
				accCancelBean.getReqMCM001().setLendirection("1");
				if("001".equals(accCancelBean.getAccCancelType())){
					accCancelBean.getReqMCM001().setTranscode("11025");
				}else if("002".equals(accCancelBean.getAccCancelType())){
					accCancelBean.getReqMCM001().setTranscode("11026");
				}else{
					accCancelBean.getReqMCM001().setTranscode("11027");
				}
				accCancelBean.setHaveAcc("0");
				openPanel(new AccCancelInputDepositPanel());
			}

		});
		haveAcc.setSize(200, 300);
		haveAcc.setLocation(217, 174);
		haveAcc.setIcon(new ImageIcon("pic/newPic/haveAcc.png"));
		this.showJpanel.add(haveAcc);
		//无存单
		UtilButton noAcc = new UtilButton("pic/newPic/noAcc.png",
				"pic/newPic/noAcc.png");
		noAcc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				// 无存单
				clearTimeText();
				//设置标识
				accCancelBean.getReqMCM001().setLendirection("1");
				if("001".equals(accCancelBean.getAccCancelType())){
					accCancelBean.getReqMCM001().setTranscode("11028");
				}else if("002".equals(accCancelBean.getAccCancelType())){
					accCancelBean.getReqMCM001().setTranscode("11029");
				}else{
					accCancelBean.getReqMCM001().setTranscode("11030");
				}
				accCancelBean.setHaveAcc("1");
				openPanel(new AccCancelInputBankCardPanel(null));
			}

		});
		noAcc.setSize(200, 300);
		noAcc.setLocation(571, 174);
		noAcc.setIcon(new ImageIcon("pic/newPic/noAcc.png"));
		this.showJpanel.add(noAcc);
		
		//上一步
		JLabel label = new JLabel(new ImageIcon("pic/newPic/preStep.png"));
		label.setBounds(1075, 770, 150, 50);
		add(label);
		label.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				clearTimeText();
				openPanel(new AccCancelDempMethodPanel());
			}
		});
		
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.setBounds(1258, 770, 150, 50);
		add(label_1);
		label_1.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				clearTimeText();
				returnHome();
			}
		});

	}
}
