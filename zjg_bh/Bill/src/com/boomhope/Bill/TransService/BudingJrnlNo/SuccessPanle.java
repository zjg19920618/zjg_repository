package com.boomhope.Bill.TransService.BudingJrnlNo;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.TransService.BudingJrnlNo.bean.BulidingJrnNoBean;
import com.boomhope.Bill.Util.UtilButton;

/**
 * 绑定流水成功
 * @author ly
 *
 */
public class SuccessPanle  extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(SuccessPanle.class);
	private static final long serialVersionUID = 1L;
	JLabel label;// 提示信息
	JLabel label_1;// 提示信息
	JLabel tdLable;//唐豆个数
	AudioStream as = null;
	JLabel titleLabel = null;
	JLabel confirm;
	/***
	 * 初始化
	 */
	public SuccessPanle(final BulidingJrnNoBean transBean){
		
		this.jrnNoBean = transBean;
		GlobalPanelFlag.CurrentFlag = GlobalPanelFlag.ACCOUNT_CANCELLATION;
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer( delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });   
		delayTimer.start(); 
		
		
		/* 标题信息 */
		titleLabel = new JLabel();
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));
		titleLabel.setBounds(271, 243, 524, 40);
		this.showJpanel.add(titleLabel);
		
		
		/* 继续交易 */
		confirm = new JLabel(new ImageIcon("pic/jxjy.png"));
		confirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击继续交易按钮");
				//继续交易
				clearTimeText();
				openPanel(new TransBuldingJrnlNo(transBean));
			}
		});
		confirm.setSize( 150, 50);
		confirm.setLocation(1075, 770);
		this.add(confirm);
		if(!"0".equals(transBean.getCheckService())){
			confirm.setVisible(false);
		}
		
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");				
				nextStep(transBean);
			}

		});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);
		setSuccess(transBean);
	}
	private void nextStep(BulidingJrnNoBean transBean) {
		GlobalParameter.OFF_ON=true;
		returnHome();
	
}
	/**
	 * 设置错误信息
	 */
	public void setSuccess(BulidingJrnNoBean transBean){
		StringBuffer sbf = new StringBuffer();
		String s = transBean.getSuccess();
		if(s == null ){
			titleLabel.setText("未知异常");
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
        titleLabel.setText(sbf.toString());
		
	}
	
}
