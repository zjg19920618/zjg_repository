package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTransFlow;
import com.boomhope.Bill.Util.UtilVoice;
import com.boomhope.Bill.peripheral.bean.IdCardReadBean;

/**
 * 证件退出
 * 
 * @author gyw
 * 
 */
public class AccOutputIdCardPanel extends BaseTransPanelNew {
	final int voiceSecond = 500;
	UtilVoice utilVoice = null;
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccOutputIdCardPanel.class);
	private Component comp; 
	private Map params;
	private boolean on_off=true;

	public AccOutputIdCardPanel(final Map map) {
		logger.info("进入退出身份证页面");
		// 将当前页面传入流程控制进行操作
		comp = this;
		params = map;
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
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
		if(delayTimer!=null){
			clearTimeText();
		}
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000,
				new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("退出身份证页面倒计时结束 ");
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
		t.setBounds(0, 129, GlobalParameter.TRANS_WIDTH - 50, 40);
		this.showJpanel.add(t);
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();
		billImage.setIcon(new ImageIcon("pic/output_idCard.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(324, 236, 362, 320);
		this.showJpanel.add(billImage);

		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 处理上一页 */
				preStep(map);
			}
		});
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(1075, 770);
		add(submitBtn);

		// 确认
		JLabel confirm = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		confirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击确认按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 处理下一页 */
				nextStep(comp, map);
			}
		});
		confirm.setSize( 150, 50);
		confirm.setLocation(890, 770);
		add(confirm);
		
		// 返回
		JLabel backButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
				clearTimeText();
				openPanel(new OutputBankCardPanel());
			}
		});
		add(backButton);
	}

	/**
	 * 下一步
	 */
	public void nextStep(final Component comp, final Map map) {
		logger.info("进入确认方法");
		// 关闭语音
		try {
			closeLed("1");
		} catch (Exception e1) {
			logger.error("LED灯关闭失败" + e1);
		}
		if (checkIdCard(map)) {
			on_off=true;
			return;
		}
		
		closeVoice();
		clearTimeText();
		stopTimer(voiceTimer);
		AccountTransFlow.startTransFlow(comp, map);
	
			
	}

	/**
	 * 上一步
	 */
	public void preStep(Map map) {
		logger.info("进入上一步方法");
		// 关闭语音
		closeVoice();
		clearTimeText();
		stopTimer(voiceTimer);
		openPanel(new AccDeputySelectionPanel(map));
	}

	/**
	 * 检查身份证是否合格
	 * 
	 * @return
	 */
	private boolean checkIdCard(Map map) {
		logger.info("进入检查身份证方法");
		boolean b = false;
		try {
			IdCardReadBean bean = (IdCardReadBean) map.get("cardInfo");

			if ("Y".equals(map.get("isMyself"))) {
				String idCardNo = (String) map.get("idCardNo");
				if (idCardNo == null
						|| bean.getIdCode() != null// 判断卡证件号和身份证，若不传卡身份证则不校验
						&& (bean.getIdCode().contains(idCardNo) || idCardNo
								.contains(bean.getIdCode()))) {
					
				} else {
					choiceBack();
					return true;
				}
			}
			String bir = bean.getBirthday();
			String endDate = bean.getEndDate();
			// 判断证件是否长期
			if (endDate.contains("长期")) {
				return false;
			}
			Date bir1 = new SimpleDateFormat("yyyy-MM-dd").parse(bir);
			Date endDate1 = new SimpleDateFormat("yyyy.MM.dd").parse(endDate);
			Date date = new Date();
			int age = date.getYear() - bir1.getYear();
			if (endDate1.before(date)) {
				openMistakeDialog("此证件已过有效期");
				b = true;
			}
		} catch (Exception e) {
			logger.error(e);
			mistakeDialog.showDialog("身份证信息获取失败");
			b = true;
		}
		return b;
	}
	
	public void choiceBack(){
		openConfirmDialog("银行卡号对应的证件信息和此证件不符,是否继续。是：重新插入身份证，否：重新选择是否代理。");
		confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				clearTimeText();
				confirmDialog.disposeDialog();
				if("0008".equals(params.get("transCode"))){
					openPanel(new AccInputIdCardPanel(params));
				}else{
					openPanel(new AccInputAgentIdCardPanel(params));
				}
			}
		});
		confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				clearTimeText();
				confirmDialog.disposeDialog();
				openPanel(new AccDeputySelectionPanel(params));
			}
		});
	}

}
