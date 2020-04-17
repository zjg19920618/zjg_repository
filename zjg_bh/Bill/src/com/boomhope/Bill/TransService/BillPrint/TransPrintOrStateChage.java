package com.boomhope.Bill.TransService.BillPrint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.Util.UtilButton;

/**
 * 
 * @Description:选择打印或者状态变更
 * @author zjg
 * @date 2016年11月4日 下午4:18:28
 */
public class TransPrintOrStateChage extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransPrintOrStateChage.class);
	private static final long serialVersionUID = 1L;
	//初始化
	public TransPrintOrStateChage(final BillPrintBean transBean) {
		this.billPrintBean = transBean;
		logger.info("进入选择打印或状态变更页");

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
				stopTimer(voiceTimer);
				excuteVoice("voice/printOrState.wav");

			}
		});
		voiceTimer.start();
		/* 打印 */
		UtilButton sonAccountPrintList = new UtilButton("pic/printing.png","pic/printing.png");
		sonAccountPrintList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击打印功能");
					nextStep(transBean);
				
			}
		});
		sonAccountPrintList.setIconTextGap(6);
		sonAccountPrintList.setBounds(130, 195, 300, 220);
		this.showJpanel.add(sonAccountPrintList);

		/* 状态变更 */
		UtilButton listCloseAccount = new UtilButton("pic/stateChage.png","pic/stateChage.png");
		listCloseAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击状态变更功能");
				accNoState(transBean);
			}
		});
		listCloseAccount.setIconTextGap(6);
		listCloseAccount.setBounds(580, 195, 300,220);
		this.showJpanel.add(listCloseAccount);
		//上一步
		JLabel label1 = new JLabel(new ImageIcon("pic/preStep.png"));
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");				
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
				returnHome();
			}

		});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);
	}
	//存单开户,当用户点击时先查询打印机状态和凭证号
	public void nextStep(BillPrintBean transBean){
		//银行卡查询打印
		if("bankCard".equals(transBean.getImportMap().get("bankCardPath"))){
			
			transBean.setChoose("0");
			clearTimeText();
			openPanel(new TransBankInfoProcessingPanel(transBean));
			
		}else if("idCard".equals(transBean.getImportMap().get("idCardPath"))){
			
			//身份证查询打印
			transBean.setChoose("0");
			clearTimeText();
			openPanel(new TransBankInfoProcessingPanel(transBean));
		}
	}
		
	
	/*
	 * 跳转子账户状态变更页
	 */
	private void accNoState(BillPrintBean transBean){
		//银行卡查询状态变更
		if("bankCard".equals(transBean.getImportMap().get("bankCardPath"))){
			
			transBean.setChoose("1");
			clearTimeText();
			openPanel(new TransBankInfoProcessingPanel(transBean));
			
		}else if("idCard".equals(transBean.getImportMap().get("idCardPath"))){
			
			//身份证查询状态变更
			transBean.setChoose("1");
			clearTimeText();
			openPanel(new TransBankInfoProcessingPanel(transBean));
		}
	}

	/*上一步*/
	private void backStep(BillPrintBean transBean){
		clearTimeText();
		if("id".equals(transBean.getImportMap().get("Path"))){
			openPanel(new TransferPrintCameraPanel(transBean));
		}else{
			openPanel(new TransPrintCheckTransPathPanel(transBean));
		}
	}
	
	
	
	
	
}