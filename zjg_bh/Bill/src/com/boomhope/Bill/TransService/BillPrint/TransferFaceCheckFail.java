package com.boomhope.Bill.TransService.BillPrint;

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
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.Util.Property;

/**
 * 人脸识别失败
 * @author wang.xm
 *
 */
public class TransferFaceCheckFail extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getLogger(TransferFaceCheckFail.class);
	
	public TransferFaceCheckFail(String serStopMsg,String errMsg,final BillPrintBean transBean) {
		this.billPrintBean=transBean;
		/* 显示时间倒计时 */
		logger.info("进入人脸识别失败页面");
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
		transBean.setCameraCount(String.valueOf((Integer.parseInt(transBean.getCameraCount())+1)));
		
		JLabel label_1 = new JLabel("错误提示：");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_1.setBounds(226, 522, 283, 30);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		this.showJpanel.add(label_1);
		if(("").equals(errMsg) || errMsg == null){
			label_1.setVisible(false);
		}
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
		lblNewLabel.setBounds(416, 523, 540, 123);
		lblNewLabel.setText(setErrmsg(errMsg));
		this.showJpanel.add(lblNewLabel);
		
		JLabel label_2 = new JLabel(setSerStopMsg(serStopMsg+"或返回上一步重新拍照"));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		label_2.setBounds(246, 216, 540, 173);
		this.showJpanel.add(label_2);
		
		JLabel step = new JLabel(new ImageIcon("pic/preStep.png"));
		step.setBounds(1075, 770, 150, 50);
		step.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//清空倒计时和语音
				clearTimeText();
				openPanel(new TransferPrintCameraPanel(transBean));
			}
		});
		add(step);
		
		if(Property.CAMEAR_COUNT.equals(transBean.getCameraCount())){
			label_2.setText(setSerStopMsg("人脸识别操作已连续"+Property.CAMEAR_COUNT+"次失败，请结束交易"));
			step.setVisible(false);
		}
		
		// 退出交易
		JLabel backButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//清空倒计时和语音
				clearTimeText();
				returnHome();
			}
		});
		add(backButton);
	}

	/**
	 * 设置服务终止提示信息
	 */
	public String setSerStopMsg(String serStopMsg){
		String stopMsg = "";
		if(""==serStopMsg || null == serStopMsg){
			
		}else if(serStopMsg.length()<=15){
			stopMsg = "<html><p>"+serStopMsg+"</p></html>";
		}else if(serStopMsg.length()>15 && serStopMsg.length()<=30){
			stopMsg = "<html><p>"+serStopMsg.substring(0, 15)+"</p>"
					+ "<p>"+serStopMsg.substring(15, serStopMsg.length())+"</p></html>";
		}else if(serStopMsg.length()>30 && serStopMsg.length()<=45){
			stopMsg = "<html><p>"+serStopMsg.substring(0, 15)+"</p>"
					+ "<p>"+serStopMsg.substring(15, 30)+"</p>"
					+ "<p>"+serStopMsg.substring(30, serStopMsg.length())+"</p></html>";
		}else if(serStopMsg.length()>45 && serStopMsg.length()<=60){
			stopMsg = "<html><p>"+serStopMsg.substring(0, 15)+"</p>"
					+ "<p>"+serStopMsg.substring(15, 30)+"</p>"
					+ "<p>"+serStopMsg.substring(30, 45)+"</p>"
					+ "<p>"+serStopMsg.substring(45, serStopMsg.length())+"</p></html>";
		}
		return stopMsg;
	}
	/**
	 * 设置错误信息
	 */
	public String setErrmsg(String errMsg){
		String msg = "";
		if(errMsg == null || ""==errMsg){
			return msg;
			
		}else if(errMsg.length()<=15){
			msg = "<html><p>"+errMsg+"</p></html>";
		}else if(errMsg.length()>15 && errMsg.length()<=30){
			msg = "<html><p>"+errMsg.substring(0, 15)+"</p>"
					+ "<p>"+errMsg.substring(15, errMsg.length())+"</p></html>";
		}else if(errMsg.length()>30 && errMsg.length()<=45){
			msg = "<html><p>"+errMsg.substring(0, 15)+"</p>"
					+ "<p>"+errMsg.substring(15, 30)+"</p>"
					+ "<p>"+errMsg.substring(30, errMsg.length())+"</p></html>";
		}else if(errMsg.length()>45 && errMsg.length()<=60){
			msg = "<html><p>"+errMsg.substring(0, 15)+"</p>"
					+ "<p>"+errMsg.substring(15, 30)+"</p>"
					+ "<p>"+errMsg.substring(30, 45)+"</p>"
					+ "<p>"+errMsg.substring(45, errMsg.length())+"</p></html>";
		}
        return msg;
	}
}
