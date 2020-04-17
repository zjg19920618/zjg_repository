package com.boomhope.Bill.TransService.AccCancel.AccountCancel.HaveAcc;

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

/**
 * 鉴别真伪失败页
 * @author Administrator
 *
 */
public class AccCancelCheckTFFailPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(AccCancelCheckTFFailPanel.class);
	private static final long serialVersionUID = 1L;
	private boolean on_off=true;//用于控制页面跳转的开关
	JLabel depoLum ;
	public AccCancelCheckTFFailPanel(String msg) {
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondShortTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondShortTime*1000,new ActionListener(){

			@Override
 			public void actionPerformed(ActionEvent e) {
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
            	excuteVoice("voice/tfFail.wav");
            	
            }      
        });            
		voiceTimer.start();
		
		// 标题
		depoLum = new JLabel(setmsg(msg));
		depoLum.setBounds(10, 205, 1009, 180);
		showJpanel.add(depoLum);
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setHorizontalAlignment(JLabel.CENTER);	
		
		//手动输入存单号
		JLabel label_2 = new JLabel(new ImageIcon("pic/import.png"));
		label_2.setBounds(1035, 770, 150, 50);
		label_2.addMouseListener(new MouseAdapter(){
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
            	openPanel(new AccCancelEnterDepositMsgPanel());
			}
		});
		add(label_2);
		
		//重新插入存单
		JLabel label_3 = new JLabel(new ImageIcon("pic/anew.png"));
		label_3.setBounds(777, 770, 150, 50);
		label_3.addMouseListener(new MouseAdapter(){
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
				openPanel(new AccCancelInputDepositPanel());
			}
		});
		add(label_3);
		
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
				stopTimer(voiceTimer);//关闭语音
				closeVoice();//关闭语音流
				returnHome();
			}
		});
	}
	
	/**
	 * 设置信息
	 */
	public String setmsg(String msg){
		
		if((msg == null || "".equals(msg.trim()))){
			msg ="";
			
		}else if(msg.trim().length()<=22){
			msg = "<html><p><font color=#412174>"+msg+"</font></p></html>";
			
		}else if(msg.length()>22 && msg.length()<=47){
			msg = "<html><p><font color=#412174>"+msg.substring(0, 22)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(22, msg.length())+"</font></p></html>";
		}else if(msg.length()>47 && msg.length()<=72){
			msg = "<html><p><font color=#412174>"+msg.substring(0, 22)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(22, 47)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(47, msg.length())+"</font></p></html>";
		}else if(msg.length()>72 && msg.length()<=97){
			msg = "<html><p><font color=#412174>"+msg.substring(0, 22)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(22, 47)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(47, 72)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(72, msg.length())+"</font></p></html>";
		}else if(msg.length()>97 && msg.length()<=122){
			msg = "<html><p><font color=#412174>"+msg.substring(0, 22)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(22, 47)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(47, 72)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(72, 97)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(97, msg.length())+"</font></p></html>";
		}
		return msg;
	}
}
