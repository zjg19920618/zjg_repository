package com.boomhope.Bill.TransService.AccCancel.AccountCancel.checkInfo;

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
import com.boomhope.Bill.Util.UtilVoice;
import com.boomhope.Bill.peripheral.bean.IdCardReadBean;

/**
 * 证件退出
 * 
 * @author gyw
 * 
 */
public class AccCancelOutputAgentIdCardPanel extends BaseTransPanelNew {
	final int voiceSecond = 500;
	UtilVoice utilVoice = null;
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccCancelOutputAgentIdCardPanel.class);
	private Component comp; 
	private boolean on_off=true;//用于控制页面跳转的开关

	public AccCancelOutputAgentIdCardPanel(final Map map) {
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondShortTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondShortTime*1000,new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
			
		});
		delayTimer.start();
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

		//确认
		JLabel lblNewLabel = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		lblNewLabel.setBounds(890, 770, 150, 50);
		add(lblNewLabel);
		lblNewLabel.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				nextStep(map);
			}
		});
		
		//上一步
		JLabel label = new JLabel(new ImageIcon("pic/newPic/preStep.png"));
		label.setBounds(1075, 770, 150, 50);
		add(label);
		label.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				closeVoice();
				clearTimeText();
				stopTimer(voiceTimer);
				openPanel(new AccCancelDeputySelectionPanel());
			}
		});
		
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.setBounds(1258, 770, 150, 50);
		add(label_1);
		label_1.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				closeVoice();
				clearTimeText();
				stopTimer(voiceTimer);
				accCancelExit();
			}
		});
	}

	/**
	 * 下一步
	 */
	public void nextStep(final Map map) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					closeLed("1");
				} catch (Exception e1) {
					logger.error("LED灯关闭失败" + e1);
				}
		
				openProssDialog();
				if (checkIdCard(map)) {
					prossDialog.disposeDialog();
					closeVoice();
					clearTimeText();
					stopTimer(voiceTimer);
					return;
				}
		
				prossDialog.disposeDialog();
				closeVoice();
				clearTimeText();
				stopTimer(voiceTimer);
				openPanel(new AccCancelAgentPoliceCheckPanel());
			}
		}).start();
	}

	/**
	 * 检查代理人身份证是否合格
	 * 
	 * @return
	 */
	private boolean checkIdCard(Map map) {
		boolean b = false;
		try {
			IdCardReadBean bean = (IdCardReadBean) map.get("cardInfo");

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
			openMistakeDialog("身份证信息获取失败");
			b = true;
		}
		return b;
	}

}
