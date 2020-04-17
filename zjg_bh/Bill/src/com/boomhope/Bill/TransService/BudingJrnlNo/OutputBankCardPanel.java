package com.boomhope.Bill.TransService.BudingJrnlNo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.BudingJrnlNo.Interface.InterfaceSendMsg;
import com.boomhope.Bill.TransService.BudingJrnlNo.bean.BulidingJrnNoBean;

/***
 * 银行卡退出界面Panel
 * @author zy
 *
 */
public class OutputBankCardPanel extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(OutputBankCardPanel.class);
	private static final long serialVersionUID = 1L;
	/***
	 * 初始化
	 */
	public OutputBankCardPanel(final BulidingJrnNoBean transBean){
		this.jrnNoBean = transBean;
		/* 倒计时打开语音 */
		this.showTimeText(delaySecondMinTime);
		delayTimer = new Timer(delaySecondMinTime * 1000, new ActionListener() {          
	        public void actionPerformed(ActionEvent e) {  
	        	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	returnHome();
	        }      
	    });            
		delayTimer.start(); 
		

		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
	        public void actionPerformed(ActionEvent e) {  
	        	stopTimer(voiceTimer);//关闭语音
	        	closeVoice();
	        	try {
					closeLed("2");
				} catch (Exception e1) {
					logger.error("LED灯关闭失败"+e);
				}//关银行卡led灯
	        	excuteVoice("voice/quit.wav");//执行语音
	        }      
	    });            
		voiceTimer.start();
		
		
		/* 标题信息 */
		JLabel titleLabel = new JLabel("银行卡已退出，请取走您的卡继续操作");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 60, GlobalParameter.TRANS_WIDTH-50, 60);
		this.showJpanel.add(titleLabel);
		
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/output_bank.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(325, 190, 362, 320);
		this.showJpanel.add(billImage);
		//确认
		JLabel backButton = new JLabel();
		backButton.setIcon(new ImageIcon("pic/newPic/confirm.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					Map info = InterfaceSendMsg.inter03845(transBean);
					String resCode = (String) info.get("resCode");
					String errMsg = (String) info.get("errMsg");
					if("000000".equals(resCode)){
						transBean.setCustNos((String) info.get("CUST_NO"));
						Map map = InterfaceSendMsg.inter02906(transBean);
						String resCodes = (String) map.get("resCode");
						String errMsgs = (String) map.get("errMsg");
						if("000000".equals(resCodes)){
							transBean.setRecName( (String) map.get("CUST_NAME"));
							transBean.setRecTelNo( (String) map.get("TEL_NO"));
//							transBean.setCustNos((String) map.get("CUST_NO"));
							//跳转成功页
							success(transBean);
						}else{
							//跳转错误页面
							fail(transBean, errMsgs);
						}
					}else{
						//跳转错误页面
						fail(transBean, errMsg);
					}
					
				} catch (Exception e1) {
					logger.error("调用卡查询接口失败"+e1);
					//跳转错误页面
					fail(transBean, "调用卡查询接口失败"+e1);
				}
				
			}
		});
		add(backButton);				
		
		
		
	}
	/**
	 * 失败处理
	 * @param fail
	 */
	public void fail(BulidingJrnNoBean transBean,String fail){
		transBean.setErrmsg(fail);
		transBean.setTag("input");
		clearTimeText();
		openPanel(new ErrorMsgPanel(transBean));
		
		
	}
	/**
	 * 成功处理
	 */
	private void success(BulidingJrnNoBean transBean){
		if(transBean.getTag().equals("wytj")){
			clearTimeText();
			openPanel(new RecomPanel(transBean));
		}else{
			clearTimeText();
			openPanel(new GetRewardsPanel(transBean));
		
		}
		
	}
	
}
