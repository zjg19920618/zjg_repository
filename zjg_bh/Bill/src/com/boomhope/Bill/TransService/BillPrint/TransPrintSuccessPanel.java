package com.boomhope.Bill.TransService.BillPrint;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.ICBankBean;
import com.boomhope.Bill.peripheral.action.MachineLed;
import com.boomhope.Bill.peripheral.bean.LedStateBean;

/**
 * 
 * title:存单打印成功页面
 * 
 * @author ly 2016年11月9日上午11:31:46
 */
public class TransPrintSuccessPanel extends BaseTransPanelNew {

	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(TransPrintSuccessPanel.class);
	Timer checkTimer = null;
	public TransPrintSuccessPanel(final BillPrintBean transBean) {
		logger.info("进入存单打印成功页面");
		this.billPrintBean = transBean;
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
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				voiceTimer.stop();
				closeLed();//关闭打印机LED灯
				excuteVoice("voice/billsuccess.wav");

			}
		});
		voiceTimer.start();

		/* 警告图标 */
		JLabel billImage = new JLabel();
		billImage.setIcon(new ImageIcon("pic/ok.png"));
		billImage.setIconTextGap(6);
		billImage.setBounds(256, 240, 66, 76);
		this.showJpanel.add(billImage);

		JLabel label = new JLabel("存单打印成功，请收好您的存单!");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("微软雅黑", Font.BOLD, 30));
		label.setForeground(Color.red);
		label.setBounds(350, 250, 540, 40);
		this.showJpanel.add(label);
		JLabel lblNewLabel = new JLabel("子账户打印流水号：");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel.setBounds(245, 393, 235, 38);
		this.showJpanel.add(lblNewLabel);
		String text="";
		for ( Object jurNo:transBean.getSelect4()) {
			if(jurNo!=null&&!"".equals(jurNo)){
				text=text+jurNo+";";
			}			
		}
		JLabel lblNewLabel_1 = new JLabel("<HTML>"+text+"</br></HTML>");
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(300, 441, 558, 172);
		this.showJpanel.add(lblNewLabel_1);

		
		/*
		 * 继续交易
		 */
		JLabel label1 = new JLabel(new ImageIcon("pic/jxjy.png"));
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击继续交易按钮");				
				upstep(transBean);
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
				nextStep(transBean);
			}

		});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);

	}

	private void upstep(BillPrintBean transBean) {
		
		if("bankCard".equals(transBean.getImportMap().get("bankCardPath"))){
			//选择银行卡
			billPrintBean=new BillPrintBean();
			billPrintBean.setPrintChoice(transBean.getPrintChoice());
			billPrintBean.getImportMap().put("bankCardPath","bankCard");
			billPrintBean.setAccNo(transBean.getAccNo());
			billPrintBean.setInputOrderPwd(transBean.getInputOrderPwd());
			billPrintBean.setCardName(transBean.getCardName());
		}else if("idCard".equals(transBean.getImportMap().get("idCardPath"))){
			//选择身份证
			billPrintBean=new BillPrintBean();
			billPrintBean.setPrintChoice(transBean.getPrintChoice());
			billPrintBean.getImportMap().put("idCardPath","idCard");
			if ( "yes".equals(transBean.getImportMap().get("agent_persion"))) {
				//代理人
				billPrintBean.setAgentqfjg(transBean.getAgentqfjg());// 代理人发证机关
				billPrintBean.setAgentsex(transBean.getAgentsex());// 性别
    			billPrintBean.setAgentIdCardNo(transBean.getAgentIdCardNo());// 证件号
    			billPrintBean.setAgentIdCardName(transBean.getAgentIdCardName());// 姓名
    			billPrintBean.setAgentIdCardtitle(transBean.getAgentIdCardtitle());// 姓名
    			billPrintBean.setAgentIdCardface(transBean.getAgentIdCardface());//正面
    			billPrintBean.setAgentIdCardback(transBean.getAgentIdCardback());// 背面
    			billPrintBean.setAgentBirthday(transBean.getAgentBirthday());//生日
    			billPrintBean.setAgentEndDate(transBean.getAgentEndDate());//有效日期
    			billPrintBean.setAgentinspect(transBean.getAgentinspect());
    			billPrintBean.getImportMap().put("agent", "true");
    			billPrintBean.setFaceCompareVal(transBean.getFaceCompareVal());
    			billPrintBean.setSvrDate(transBean.getSvrDate());
    			billPrintBean.setCardName(transBean.getIdCardName());
			}else{
				//本人
				billPrintBean.setQfjg(transBean.getQfjg());// 发证机关
				billPrintBean.setSex(transBean.getSex());// 性别
				billPrintBean.setReadIdcard(transBean.getReadIdcard());// 证件号
				billPrintBean.setIdCardName(transBean.getIdCardName());// 姓名
				billPrintBean.setIdCardtitle(transBean.getIdCardtitle());//头像照片
				billPrintBean.setIdCardface(transBean.getIdCardface());//正面
				billPrintBean.setIdCardback(transBean.getIdCardback());// 背面
				billPrintBean.setEndDate(transBean.getEndDate());//有效期结束
				billPrintBean.setBirthday(transBean.getBirthday());//生日
				billPrintBean.setFaceCompareVal(transBean.getFaceCompareVal());
				billPrintBean.setCardName(transBean.getIdCardName());
				billPrintBean.setSvrDate(transBean.getSvrDate());
			}
		}
		openPanel(new TransPrintOrStateChage(billPrintBean));
	}

	private void nextStep(BillPrintBean transBean) {
			GlobalParameter.OFF_ON=true;
			returnHome();
		
	}
	
	/**关闭led灯*/
	public void closeLed(){
		LedStateBean closeLed = new LedStateBean();
		try {
			closeLed = MachineLed.closeLed("3");
			logger.info("打印机Led灯关闭返回值："+closeLed);
		} catch (Exception e) {
			logger.error("打印机led灯通讯异常");
			return;
		}
		logger.info("打印机Led灯关闭："+closeLed.getStatus());
	}


}
