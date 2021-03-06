package com.boomhope.Bill.TransService.BillPrint;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.peripheral.bean.LedStateBean;

/**
 * 
 * title:证件退出
 * @author ly
 * 2016年11月7日下午9:59:36
 */
public class TransPrintOutputIdcardPanel extends BaseTransPanelNew {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(TransPrintOutputIdcardPanel.class);

	public TransPrintOutputIdcardPanel(final BillPrintBean transBean) {
		logger.info("进入退出本人身份证页面");
		this.billPrintBean = transBean;
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);
            	closeLed(transBean);
            	excuteVoice("voice/custody.wav");
            	
            }      
        });            
		voiceTimer.start(); 
		/* 标题信息 */
		JLabel t = new JLabel("证件已退出，请妥善保管");
		t.setHorizontalAlignment(JLabel.CENTER);
		t.setForeground(Color.decode("#412174"));
		t.setFont(new Font("微软雅黑", Font.BOLD, 40));
		t.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(t);
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/output_idCard.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(325, 236, 460, 360);
		this.showJpanel.add(billImage);


		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });   
		delayTimer.start(); 
		//确认
		JLabel lblNewLabel = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		lblNewLabel.setBounds(1075, 770, 150, 50);
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				/* 处理下一页 */
				logger.info("点击确认按钮");	
				nextStep(transBean);
			}

		});
		this.add(lblNewLabel);
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");				
				returnHome();
			}

		});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);

	}
	/**
	 * 下一步
	 */
	public void nextStep(BillPrintBean transBean){
		//关闭语音
		if(checkIdCard(transBean)){
			serverStop("请联系大堂经理", "", transBean.getErrmsg());
			return;
		}
		clearTimeText();
		openPanel(new TransPrintCheckingIdcardPanel(transBean));
	}
	
	private boolean checkIdCard(BillPrintBean transBean){
		String bir=transBean.getBirthday();
		String endDate=transBean.getEndDate();
		boolean b=false;
		if(endDate.contains("长期")){
			return b;
		}
		try {
			Date bir1=new SimpleDateFormat("yyyy-MM-dd").parse(bir);
			Date endDate1=new SimpleDateFormat("yyyy.MM.dd").parse(endDate);
			Date date=new Date();
			int age=date.getYear()-bir1.getYear();
			if(age<16){
				b=true;
				transBean.setErrmsg("此用户不足16岁，无法办理业务");
			}else
			if(endDate1.before(date)){
				b=true;
				transBean.setErrmsg("证件已过有效期，无法办理业务");
			}
			
		} catch (Exception e) {
			logger.error(e);
			b=true;
			transBean.setErrmsg("证件信息读取失败，无法办理业务");
		}
		return b;
	}
	
	
	
	
	/**关闭led灯*/
	public void closeLed(BillPrintBean transBean){
		LedStateBean closeLed = new LedStateBean();
		try {
			this.closeLed("1");
		} catch (Exception e) {
			logger.error("身份证led灯关闭通讯异常");
			transBean.setErrmsg("未找到身份证Led灯设备");
			return;
		}
		logger.info("身份证Led灯关闭："+closeLed.getStatus());
	}
	
}
