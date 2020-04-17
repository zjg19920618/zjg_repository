package com.boomhope.Bill.TransService.BillAddPwd.ServicePanel;

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
/**
 * 增设密码成功并退出存单
 */
public class SetPasswordSuccessPanel  extends BaseTransPanelNew{

	Logger logger = Logger.getLogger(SetPasswordSuccessPanel.class);
	private static final long serialVersionUID = 1L;
    public  SetPasswordSuccessPanel(){
               logger.info("进入退出存单提示页面");
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
            	excuteVoice("voice/zsmmcg.wav");
            	
            }      
        });            
		voiceTimer.start();
		//退出存单
		try {
			outBill();
			
			//成功退存单、退卡 后可执行子业务跳转
			if(!GlobalParameter.ACC_STATUS && !GlobalParameter.CARD_STATUS){
				GlobalParameter.OFF_ON=true;
			}
			
			//存单退出，变更存单状态
			GlobalParameter.ACC_STATUS=false;
		} catch (Exception e1) {
			logger.error("回单模块异常，退存单失败"+e1);
			serverStop("存单未退出，请联系工作人员解决","","");//退存单失败页面
		}	
		         //提示信息
				JLabel label = new JLabel("存单增设密码成功，请取走您的存单，");
				label.setFont(new Font("微软雅黑",Font.BOLD,40));
				label.setHorizontalAlignment(0);
				label.setBounds(0,230,1009,50);
				this.showJpanel.add(label);
				 //提示信息
				JLabel label2 = new JLabel("妥善保管！");
				label2.setFont(new Font("微软雅黑",Font.BOLD,40));
				label2.setHorizontalAlignment(0);
				label2.setBounds(0,280,1009,50);
				this.showJpanel.add(label2);
				 //提示信息
				JLabel label3 = new JLabel("增设密码流水号:"+addPwdBean.getZMSvrJrnlNo().trim());
				label3.setFont(new Font("微软雅黑",Font.BOLD,30));
				label3.setHorizontalAlignment(0);
				label3.setBounds(0,350,1009,50);
				this.showJpanel.add(label3);
				
				//退出
				JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
				label_1.setBounds(1258, 770, 150, 50);
				add(label_1);
				label_1.addMouseListener(new MouseAdapter(){
					public void mouseReleased(MouseEvent e){
						logger.info("点击退出按钮");
						closeVoice();
				    	stopTimer(voiceTimer);//关闭语音
				    	clearTimeText();//清空倒计时
						returnHome();
					}
				});
				
    }
    
}
