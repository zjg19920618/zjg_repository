package com.boomhope.Bill.TransService.BillPrint;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.Util.UtilButton;

/**
 * 
 * @Description:选择子账户打印要办理的途径（身份证，银行卡）
 * @author zjg
 * @date 2016年11月4日 下午4:18:28
 */
public class TransPrintCheckTransPathPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransPrintCheckTransPathPanel.class);
	private static final long serialVersionUID = 1L;
	//初始化
	public TransPrintCheckTransPathPanel(final BillPrintBean transBean) {
		logger.info("进入选择子账户打印办理途径页面");
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
				excuteVoice("voice/serverPath.wav");

			}
		});
		voiceTimer.start();
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请选择办理途径");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(titleLabel);
		/* 身份证 */
		UtilButton sonAccountPrintList = new UtilButton("pic/idCard.png",
				"pic/idCard.png");
		sonAccountPrintList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				IdCard(transBean);
			}
		});
		sonAccountPrintList.setIconTextGap(6);
		sonAccountPrintList.setBounds(192, 174, 200, 300);
		this.showJpanel.add(sonAccountPrintList);

		/* 银行卡 */
		UtilButton listCloseAccount = new UtilButton("pic/bankcard.png",
				"pic/bankcard.png");
		listCloseAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				bankCard(transBean);
			}
		});
		listCloseAccount.setIconTextGap(6);
		listCloseAccount.setBounds(585, 174, 200,300);
		this.showJpanel.add(listCloseAccount);
		
		/*手动输入*/
		UtilButton inputAccNo = new UtilButton("pic/handInput.png",
				"pic/handInput.png");
		inputAccNo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				handInput(transBean);
			}
		});
		inputAccNo.setBounds(707, 174, 200, 300);
		inputAccNo.setIconTextGap(6);
		inputAccNo.setVisible(false);
		this.showJpanel.add(inputAccNo);
		
		//上一步
		JLabel label = new JLabel(new ImageIcon("pic/preStep.png"));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");				
				backTrans(transBean);
			}

		});
		label.setBounds(1075, 770, 150, 50);
		this.add(label);
		
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
		
		if("1".equals(transBean.getPrintChoice())){
			listCloseAccount.setLocation(405,174);
			sonAccountPrintList.setLocation(102, 174);
			inputAccNo.setVisible(true);
		}
	}
	
	/*
	 * 身份证
	 */
	private void IdCard(BillPrintBean transBean){
		//选择身份证办理方式办理
		transBean.getImportMap().put("idCardPath","idCard");
		transBean.getImportMap().put("Path","id");
		clearTimeText();
		openPanel(new TransPrintCheckProxyPanel(transBean));

	}
	/*
	 * 银行卡
	 */
	private void bankCard(BillPrintBean transBean){
		//选择银行卡办理
		transBean.getImportMap().put("bankCardPath","bankCard");
		transBean.getImportMap().put("Path","bank");
		clearTimeText();
		openPanel(new TransPrintBackInputBankCardPanel(transBean));
		
	}
	
	/*
	 * 手动输入
	 */
	private void handInput(BillPrintBean transBean){
		//选择手动输入
		transBean.getImportMap().put("handPath", "handInput");
		clearTimeText();
		openPanel(new TransHandInputAccInfoPanel());
	}
	
	/*
	 * 上一步
	 */
	public void backTrans(BillPrintBean transBean){
		clearTimeText();
		openPanel(new TransPrintAccOrAgrPanel(transBean));
	}
	

	

}
