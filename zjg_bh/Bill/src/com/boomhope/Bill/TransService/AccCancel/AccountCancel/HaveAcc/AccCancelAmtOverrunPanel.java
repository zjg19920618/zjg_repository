package com.boomhope.Bill.TransService.AccCancel.AccountCancel.HaveAcc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.BJ.AccCancelAuthorNoBJPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.BJ.AccCancelAuthorNoJwPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.BJ.AccCancelAuthorNoJwTwoPanel;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.UtilButton;

/**
 * 销户金额超限提示页
 * @author Administrator
 *
 */
public class AccCancelAmtOverrunPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(AccCancelAmtOverrunPanel.class);
	private static final long serialVersionUID = 1L;
	private boolean on_off=true;//用于控制页面跳转的开关
	JLabel depoLum ;
	public AccCancelAmtOverrunPanel(String msg) {
		
		// 标题
		depoLum = new JLabel(setmsg(msg));
		depoLum.setBounds(0, 30, 1009, 180);
		this.showJpanel.add(depoLum);
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setHorizontalAlignment(JLabel.CENTER);	
		
		/* 人工识别通过 */
		UtilButton trueButton = new UtilButton("pic/jwOk.jpg","pic/jwOk.jpg");
		trueButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	
            	//存单金额
        		Float money = Float.valueOf(accCancelBean.getAmount());
        		
        		//销户限额10万
        		String amt = Property.getProperties().getProperty("acc_cancel_amt");
        		Float accCancelAmt = Float.valueOf(amt);
            	
        		//判断金额是否大于等于10万
				if(money>=accCancelAmt){
					
					logger.info("第一次鉴伪不通过，手动输入，金额大于等于10万:"+money+"元，进行二次授权，重新插入存单");
					//进入录入两个授权柜员页面
					openPanel(new AccCancelAuthorNoJwPanel());
					return;
					
				}else{
					
					logger.info("第一次鉴伪不通过，手动输入，金额小于10万:"+money+"元，进行一次授权，重新插入存单");
					//进入录入一个授权柜员页面
					openPanel(new AccCancelAuthorNoJwTwoPanel());
					return;
				}	
			}

		});
		trueButton.setBounds(160, 280, 200, 90);
		this.showJpanel.add(trueButton);
		
		/* 人工识别不通过*/
		UtilButton falseButton = new UtilButton("pic/jwNo.jpg", "pic/jwNo.jpg");
		falseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
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
		falseButton.setBounds(640, 280, 200, 90);
		this.showJpanel.add(falseButton);
		
		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.setBounds(1075, 770, 150, 50);
		submitBtn.addMouseListener(new MouseAdapter() {
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
            	
            	openPanel(new AccCancelCheckBillPanel());
			}
		});
		add(submitBtn);				
		
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.setBounds(1258, 770, 150, 50);
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
		add(label_1);
	}
	
	/**
	 * 设置信息
	 */
	public String setmsg(String msg){
		
		if((msg == null || "".equals(msg.trim()))){
			msg ="";
			
		}else if(msg.trim().length()<=23){
			msg = "<html><p><font color=#412174>"+msg+"</font></p></html>";
			
		}else if(msg.length()>23 && msg.length()<=48){
			msg = "<html><p><font color=#412174>"+msg.substring(0, 23)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(23, msg.length())+"</font></p></html>";
		}else if(msg.length()>48 && msg.length()<=73){
			msg = "<html><p><font color=#412174>"+msg.substring(0, 23)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(23, 48)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(48, msg.length())+"</font></p></html>";
		}else if(msg.length()>73 && msg.length()<=98){
			msg = "<html><p><font color=#412174>"+msg.substring(0, 23)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(23, 48)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(48, 73)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(73, msg.length())+"</font></p></html>";
		}else if(msg.length()>98 && msg.length()<=123){
			msg = "<html><p><font color=#412174>"+msg.substring(0, 23)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(23, 48)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(48, 73)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(73, 98)+"</font></p>"
					+ "<p><font color=#412174>"+msg.substring(98, msg.length())+"</font></p></html>";
		}
		return msg;
	}
}
