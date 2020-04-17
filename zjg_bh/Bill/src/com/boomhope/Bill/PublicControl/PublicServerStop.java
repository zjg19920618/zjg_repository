package com.boomhope.Bill.PublicControl;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.PublicUse.AccCancelOutputDepositPanel;

/**
 * 公用服务终止页
 * @author wang.xm
 *
 */
public class PublicServerStop extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getLogger(PublicServerStop.class);
	
	private JLabel label_1;//错误提示按钮
	
	public PublicServerStop(String serStopMsg,String errMsg,String usMsg) {
		logger.info("服务终止页："+serStopMsg+":"+errMsg);
		//设置倒计时
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime* 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	logger.info("统一终止页面倒计时结束");
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	//退出
				accCancelExit();
            }      
        });            
		delayTimer.start();
		
		label_1 = new JLabel("错误提示：");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		label_1.setBounds(40, 302, 200, 30);
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setForeground(Color.red);
		this.showJpanel.add(label_1);
		if((errMsg == null ||("").equals(errMsg.trim()) )&&(usMsg==null || "".equals(usMsg.trim()))){
			label_1.setVisible(false);
		}
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		lblNewLabel.setBounds(230, 300, 650, 217);
		lblNewLabel.setText(setErrmsg(errMsg,usMsg));
		this.showJpanel.add(lblNewLabel);
		
		JLabel label_2 = new JLabel("<html><div style='text-align:center'>"+serStopMsg+"</div></html>");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		label_2.setBounds(205,120, 600, 173);
		this.showJpanel.add(label_2);
		
		//退出按钮
		JLabel exitImage = new JLabel();   
		exitImage.setIcon(new ImageIcon("pic/newPic/exit.png"));
		exitImage.setIconTextGap(6);
		exitImage.setBounds(1258, 770, 150, 50);
		exitImage.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");
				clearTimeText();
				//退出
				accCancelExit();
			}
			
		});
		add(exitImage);

	}

	/**
	 * 设置错误信息
	 */
	public String setErrmsg(String errMsg,String usMsg){
		int length = 0;
		if(errMsg!=null){
		if(errMsg.trim().length()>usMsg.trim().length()){
			length=errMsg.trim().length();
		}else{
			length=usMsg.trim().length();
		}
		}
		String msg = "";
		if((errMsg == null || "".equals(errMsg.trim()))&&(usMsg!=null || !"".equals(usMsg.trim()))){
			msg = "<html><p><font color=blue>"+usMsg+"</font></p></html>";
			if(length<=25){
				label_1.setLocation(555-length*25/2-200, 302);
			}
		}else if(errMsg.trim().length()<=25){
			msg = "<html><p><font color=red>"+errMsg.trim()+"</font></p>"
					+ "<p><font color=blue>"+usMsg+"</font></p></html>";
			if(length<=25){
				label_1.setLocation(555-length*25/2-200, 302);
			}
		}else if(errMsg.length()>25 && errMsg.length()<=50){
			msg = "<html><p><font color=red>"+errMsg.substring(0, 25)+"</font></p>"
					+ "<p><font color=red>"+errMsg.substring(25, errMsg.length())+"</font></p>"
					+ "<p><font color=blue>"+usMsg+"</font></p></html>";
		}else if(errMsg.length()>50 && errMsg.length()<=75){
			msg = "<html><p><font color=red>"+errMsg.substring(0, 25)+"</font></p>"
					+ "<p><font color=red>"+errMsg.substring(25, 50)+"</font></p>"
					+ "<p><font color=red>"+errMsg.substring(50, errMsg.length())+"</font></p>"
					+ "<p><font color=blue>"+usMsg+"</font></p></html>";
		}else if(errMsg.length()>75 && errMsg.length()<=100){
			msg = "<html><p><font color=red>"+errMsg.substring(0, 25)+"</font></p>"
					+ "<p><font color=red>"+errMsg.substring(25, 50)+"</font></p>"
					+ "<p><font color=red>"+errMsg.substring(50, 75)+"</font></p>"
					+ "<p><font color=red>"+errMsg.substring(75, errMsg.length())+"</font></p>"
					+ "<p><font color=blue>"+usMsg+"</font></p></html>";
		}else if(errMsg.length()>100 && errMsg.length()<=125){
			msg = "<html><p><font color=red>"+errMsg.substring(0, 25)+"</font></p>"
					+ "<p><font color=red>"+errMsg.substring(25, 50)+"</font></p>"
					+ "<p><font color=red>"+errMsg.substring(50, 75)+"</font></p>"
					+ "<p><font color=red>"+errMsg.substring(75, 100)+"</font></p>"
					+ "<p><font color=red>"+errMsg.substring(100, errMsg.length())+"</font></p>"
					+ "<p><font color=blue>"+usMsg+"</font></p></html>";
		}
        return msg;
	}
	
}
