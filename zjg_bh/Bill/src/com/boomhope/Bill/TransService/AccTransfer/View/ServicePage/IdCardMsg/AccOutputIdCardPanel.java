package com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.IdCardMsg;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.companyView.TransferAccSelectPanel;
import com.boomhope.Bill.Util.UtilVoice;

/**
 * 证件退出
 * 
 * @author hk
 * 
 */
public class AccOutputIdCardPanel extends BaseTransPanelNew {
	final int voiceSecond = 500;
	UtilVoice utilVoice = null;
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccOutputIdCardPanel.class);
	private PublicAccTransferBean transferMoneyBean;
	private boolean on_off=true;//控制页面跳转

	public AccOutputIdCardPanel(final PublicAccTransferBean transferBean) {
		transferMoneyBean = transferBean;
		
		// 将当前页面传入流程控制进行操作
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				voiceTimer.stop();
				closeVoice();
				try {
					closeLed("1");
				} catch (Exception e1) {
					logger.error("LED灯关闭失败" + e);
				}
				excuteVoice("voice/custody.wav");

			}
		});
		voiceTimer.start();
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000,
				new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		delayTimer.start();
		/* 标题信息 */
		JLabel t = new JLabel("证件已退出，请妥善保管");
		t.setHorizontalAlignment(JLabel.CENTER);
		t.setFont(new Font("微软雅黑", Font.BOLD, 40));
		t.setBounds(0, 100, GlobalParameter.TRANS_WIDTH - 50, 42);
		this.showJpanel.add(t);
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();
		billImage.setIcon(new ImageIcon("pic/output_idCard.gif"));
		billImage.setHorizontalAlignment(SwingUtilities.CENTER);
		billImage.setIconTextGap(6);
		billImage.setBounds(275, 190, 460, 360);
		this.showJpanel.add(billImage);

		//上一步
		JLabel upPageButton = new JLabel();
		upPageButton.setIcon(new ImageIcon("pic/preStep.png"));
		upPageButton.setBounds(1075, 770, 150, 50);
		upPageButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//返回时跳转页面
				preStep();
			}
		});
		add(upPageButton);
		
		// 确认
		JLabel okBtn = new JLabel();
		okBtn.setIcon(new ImageIcon("pic/newPic/confirm.png"));
		okBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				nextStep();
			}
		});
		okBtn.setSize(150, 50);
		okBtn.setLocation(890, 770);
		add(okBtn);
		
		//退出
		JLabel backButton = new JLabel();
		backButton.setIcon(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//清空倒计时
				clearTimeText();
				//返回时跳转页面
				openPanel(new OutputBankCardPanel());
			}
		});
		add(backButton);

	}

	/**
	 * 下一步
	 */
	public void nextStep() {
		// 关闭语音
		closeVoice();
		clearTimeText();
		stopTimer(voiceTimer);
		//校验身份证
		if (checkIdCard()) {
			on_off=true;
			return;
		}
		openPanel(new TransferPrintCameraPanel(transferMoneyBean));
	}

	/**
	 * 上一步
	 */
	public void preStep() {
		// 关闭语音,清空倒计时
		clearTimeText();
		closeVoice();
		stopTimer(voiceTimer);
		
		transferMoneyBean.setNextDaySendFlag("");
		transferMoneyBean.setTransMarkNo("");
		transferMoneyBean.setZhangWay("");
		
		openPanel(new TransferAccSelectPanel(transferMoneyBean));
	}

	/**
	 * 检查身份证是否合格
	 * @return
	 */
	private boolean checkIdCard() {
		logger.info("进行身份证是否合格检查");
		boolean b = false;
		try {
			//判断银行信息，与身份证信息是否一致
			if(transferMoneyBean.getChuZhangCardNo().startsWith("623193") || transferMoneyBean.getChuZhangCardNo().startsWith("622167") ){
				if(!transferMoneyBean.getChuZhangIdCardNo().equals(transferMoneyBean.getIdCardNo()) || !transferMoneyBean.getChuZhangIdCardName().equals(transferMoneyBean.getIdCardName())){
					logger.info("个人卡证件信息比对");
					logger.info("银行卡客户证件号："+transferMoneyBean.getChuZhangIdCardNo());
					logger.info("银行卡客户姓名："+transferMoneyBean.getChuZhangIdCardName());
					logger.info("身份证证件号："+transferMoneyBean.getIdCardNo());
					logger.info("身份证姓名："+transferMoneyBean.getIdCardName());
					openMistakeDialog("非本人证件，请联系工作人员核查后点击上一步重新插入");
					return true;
				}
			}else{
				if(!transferMoneyBean.getChuZhangAcctName().equals(transferMoneyBean.getIdCardName())){
					logger.info("单位卡证件信息比对");
					logger.info("银行卡客户姓名："+transferMoneyBean.getChuZhangAcctName());
					logger.info("身份证姓名："+transferMoneyBean.getIdCardName());
					openMistakeDialog("非本人证件，请联系工作人员核查后点击上一步重新插入");
					return true;
				}
			}
			
			String bir = transferMoneyBean.getBirthday();
			String endDate = transferMoneyBean.getEndDate();
			// 判断证件是否长期
			if (endDate.contains("长期")) {
				return b;
			}
			Date bir1 = new SimpleDateFormat("yyyy-MM-dd").parse(bir);
			Date endDate1 = new SimpleDateFormat("yyyy.MM.dd").parse(endDate);
			Date date = new Date();
			int age = date.getYear() - bir1.getYear();
			if (endDate1.before(date)) {
				openMistakeDialog("此证件已过有效期");
				return true;
			}
		} catch (Exception e) {
			logger.error("身份证信息获取失败"+e);
			mistakeDialog.showDialog("身份证信息获取失败，请重新插入");
			return true;
		}
		return b;
	}
}
