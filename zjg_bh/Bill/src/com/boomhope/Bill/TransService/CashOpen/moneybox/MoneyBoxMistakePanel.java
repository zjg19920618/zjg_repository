package com.boomhope.Bill.TransService.CashOpen.moneybox;

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
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
/**
 * 现金开户统一错误页面
 * @author gyw
 *
 */
public class MoneyBoxMistakePanel extends BaseTransPanelNew{
	JLabel label = null;
	static Logger logger = Logger.getLogger(MoneyBoxMistakePanel.class);
	private static final long serialVersionUID = 1L;
	private boolean on_off=true;//开关控制
	public MoneyBoxMistakePanel(final PublicCashOpenBean transBean) {
		this.cashBean= transBean;
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	on_off=false;
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
		billImage.setBounds(300, 281, 47, 44);
		this.showJpanel.add(billImage);

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("微软雅黑", Font.BOLD, 30));
		label.setBounds(385, 280, 540, 123);
		this.showJpanel.add(label);			
		//上一步
		JLabel label1 = new JLabel(new ImageIcon("pic/preStep.png"));
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");	
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				backStep(transBean);
				}

		});
		label1.setBounds(1075, 770, 150, 50);
		this.add(label1);
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");	
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				returnHome();
			}

		});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);
		setErrmsg(transBean);
	
	}
	
	/**
	 * 上一步
	 */
	public void backStep(PublicCashOpenBean transBean){
		
		clearTimeText();
		if(transBean.getImportMap().get("CashJurisdiction")!=null&&transBean.getImportMap().get("CashJurisdiction").equals(GlobalPanelFlag.MONEYBOX_ADDITION_FINGERPRINT+"")){
			openPanel(new MoneyBoxAdditionFingerprintPanel(transBean));
		}else if(transBean.getImportMap().get("CashProcurator")!=null&&transBean.getImportMap().get("CashProcurator").equals(GlobalPanelFlag.DEPUTY_SELECTION+"")){
			openPanel(new MoneyBoxDeputySelectionPanel(transBean));
		}else if(transBean.getImportMap().get("CashCard")!=null&&transBean.getImportMap().get("CashCard").equals(GlobalPanelFlag.DEPUTY_SELECTION+"")){
			openPanel(new MoneyBoxDeputySelectionPanel(transBean));
		}else if(transBean.getImportMap().get("CashProcurator")!=null&&transBean.getImportMap().get("CashProcurator").equals(GlobalPanelFlag.DEPUTY_SELECTION+"")){
			openPanel(new MoneyBoxDeputySelectionPanel(transBean));
		}else if(transBean.getImportMap().get("CashQRCode")!=null&&transBean.getImportMap().get("CashQRCode").equals(GlobalPanelFlag.ENTRY_MODE+"")){
			openPanel(new MoneyBoxEntryModePanel(transBean));
		}else if(transBean.getImportMap().get("agent_idCard_check")!=null&&transBean.getImportMap().get("agent_idCard_check").equals(GlobalPanelFlag.DEPUTY_SELECTION+"")){
			openPanel(new MoneyBoxDeputySelectionPanel(transBean));
		}else if(transBean.getImportMap().get("idCard_check")!=null&&transBean.getImportMap().get("idCard_check").equals(GlobalPanelFlag.DEPUTY_SELECTION+"")){
			openPanel(new MoneyBoxDeputySelectionPanel(transBean));
		}else if(transBean.getImportMap().get("backStepbankcard")!=null&&transBean.getImportMap().get("backStepbankcard").equals(GlobalPanelFlag.MONEYBOX_INPUT_BANK_CARD_PANEL+"")){
			openPanel(new MoneyBoxInputBankCardPanel(transBean));
		}else if(transBean.getImportMap().get("Maxcard")!=null&&transBean.getImportMap().get("Maxcard").equals(GlobalPanelFlag.ENTRY_MODE+"")){
			openPanel(new MoneyBoxEntryModePanel(transBean));
		}else if(transBean.getImportMap().get("backStep1")!=null&&transBean.getImportMap().get("backStep1").equals(GlobalPanelFlag.MONEYBOX_PRINT_SUP_TELLER_PASS+"")){
			openPanel(new MoneyBoxSupTellerPasswordFailuer(transBean));
		}else if(transBean.getImportMap().get("backStep")!=null&&transBean.getImportMap().get("backStep").equals(GlobalPanelFlag.INPUT_IDCARD+"")){
			openPanel(new MoneyBoxIdentificationCardPanel(transBean));
		}
		
		
//		transBean.getImportMap().put("backStep", GlobalPanelFlag.ADDITION_NUMBER+"");		
//		if(transBean.getImportMap().get("backStep")!=null){
//			String backStep = transBean.getImportMap().get("backStep");
//			this.openPanel(Integer.parseInt(backStep));
//		}else{
//			
//		}		

		
	}
	/**
	 * 设置错误信息
	 */
	public void setErrmsg(PublicCashOpenBean transBean){
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
