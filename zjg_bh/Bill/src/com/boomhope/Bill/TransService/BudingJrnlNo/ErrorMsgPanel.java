package com.boomhope.Bill.TransService.BudingJrnlNo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.BudingJrnlNo.bean.BulidingJrnNoBean;
import com.boomhope.Bill.Util.UtilButton;

/**
 *交易辅助登记错误页面
 * @author ly
 *
 */
public class ErrorMsgPanel extends BaseTransPanelNew {
	JLabel label = null;
	UtilButton backButton=null;
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(ErrorMsgPanel.class);

	public ErrorMsgPanel(final BulidingJrnNoBean transBean) {
		this.jrnNoBean = transBean;
		/* 显示时间倒计时 */
		showTimeText( delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		/* 警告图标 */
		JLabel billImage = new JLabel();
		billImage.setIcon(new ImageIcon("pic/war_icon.png"));
		billImage.setIconTextGap(6);
		billImage.setBounds(345, 281, 47, 44);
		this.showJpanel.add(billImage);

		label = new JLabel("");
//		label.setForeground(java.awt.Color.RED);
		label.setForeground(Color.RED);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("微软雅黑", Font.BOLD, 30));
		label.setBounds(429, 280, 540, 323);
		this.showJpanel.add(label);

			
		//上一步
		JLabel label1 = new JLabel(new ImageIcon("pic/preStep.png"));
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");				
				backStep2(transBean);
			}
		
		});
		label1.setBounds(1075, 770, 150, 50);
		this.add(label1);
		//退出
		JLabel label_11 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");				
				returnHome();
			}

		});
		setErrmsg(transBean);
	}
	
	
	/**
	 * 上一步
	 */
	public void backStep2(BulidingJrnNoBean transBean){
		if("bulding".equals(transBean.getTag())){
			openPanel(new TransBuldingJrnlNo(transBean));
		}else if("getreward".equals(transBean.getTag())){
			openPanel(new GetRewardsPanel(transBean));
		}else if("recom".equals(transBean.getTag())){
			openPanel(new RecomPanel(transBean));
		}else if("input".equals(transBean.getTag())){
			openPanel(new TJServicePanel());
		}
		
	}
	
	/**
	 * 设置错误信息
	 */
	public void setErrmsg(BulidingJrnNoBean transBean){
		StringBuffer sbf = new StringBuffer();
		String s = transBean.getErrmsg();
		if(s == null ){
			label.setText("未知异常");
			return;
		}
		// 循环
		int cpCount = s.codePointCount(0, s.length());  
        for (int i = 0; i < cpCount; i++) {  
            int index = s.offsetByCodePoints(0, i);  
            int cp = s.codePointAt(index);  
            if (!Character.isSupplementaryCodePoint(cp)) {  
            	if(i == 0){
            		sbf.append("<html><p>");
            	}
            	sbf.append((char) cp);
            	if((i+1)%14 == 0){
            		sbf.append("</p><p>");
            	}
            	if(cpCount == (i+1)){
            		sbf.append("</p></html>");
            	}
            } else {  
            	if(i == 0){
            		sbf.append("<html><p>");
            	}
            	sbf.append((char) cp);
            	if((i+1)%10 == 0){
            		sbf.append("</p><p>");
            	}
            	if(cpCount == (i+1)){
            		sbf.append("</p></html>");
            	}
            }  
        }  
		label.setText(sbf.toString());
		
	}
}
