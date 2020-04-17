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
import com.boomhope.Bill.TransService.BillPrint.Agent.TransPrintInputAgentIdcardPanel;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.Util.UtilButton;

/**
 * @Description: TODO 选择凭证打印方式
 * @author zjg
 * @date 2016年11月4日 下午5:04:01
 */
public class TransPrintCheckProxyPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransPrintCheckProxyPanel.class);
	private static final long serialVersionUID = 1L;

	public TransPrintCheckProxyPanel(final BillPrintBean transBean) {
		this.billPrintBean = transBean;
		logger.info("选择办理人页面（本人或代理人）");
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
				excuteVoice("voice/checkProxy.wav");

			}
		});
		voiceTimer.start();
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请选择办理人");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(titleLabel);
		/* 本人办理 */
		UtilButton myselfTransact = new UtilButton("pic/me.png",
				"pic/me.png");
		myselfTransact.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				me(transBean);
			}
		});
		myselfTransact.setIconTextGap(6);
		myselfTransact.setBounds(192, 174, 200, 300);
		this.showJpanel.add(myselfTransact);

		/* 代理人办理 */
		UtilButton proxyTransact = new UtilButton("pic/agent.png",
				"pic/agent.png");
		proxyTransact.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				agent(transBean);
			}
		});
		proxyTransact.setIconTextGap(6);
		proxyTransact.setBounds(585, 174, 200,300);
		this.showJpanel.add(proxyTransact);
		
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
	/**
	 * 代理人
	 */
	public void agent(BillPrintBean transBean){
		transBean.getImportMap().put("agent_persion", "yes");
		clearTimeText();
		openPanel(new TransPrintInputAgentIdcardPanel(transBean));
		
	}
	
	/**
	 * 本人
	 */
	public void me(BillPrintBean transBean){
		transBean.getImportMap().put("agent_persion", "no");
		clearTimeText();
		openPanel(new BackTransInputIdcard1Panel(transBean));
			
	}

	/**上一步*/
	private void backStep(BillPrintBean transBean){
		clearTimeText();
		openPanel(new TransPrintCheckTransPathPanel(transBean));
	}
	
	

}
