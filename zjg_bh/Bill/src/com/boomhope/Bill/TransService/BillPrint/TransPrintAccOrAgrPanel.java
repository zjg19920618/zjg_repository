package com.boomhope.Bill.TransService.BillPrint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.Util.OnOffSetUp;
import com.boomhope.Bill.Util.UtilButton;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;

/**
 * 
 * @Description:选择打印存单或者是打印协议书
 * @author hk
 * 
 */
public class TransPrintAccOrAgrPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransPrintAccOrAgrPanel.class);
	private static final long serialVersionUID = 1L;
	
	private UtilButton printAcc;//打印存单
	private UtilButton printAgreement;//打印协议书
	
	//初始化
	public TransPrintAccOrAgrPanel(final BillPrintBean transBean) {
		this.billPrintBean = transBean;
		logger.info("进入选择打印存单或者打印协议书页面");

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
		/* 打印存单 */
		printAcc = new UtilButton("pic/print.png","pic/print.png");
		printAcc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击打印存单");
				Acc();
			}
		});
		printAcc.setIconTextGap(6);
		printAcc.setBounds(192, 174, 200, 300);
		this.showJpanel.add(printAcc);

		/* 打印协议书*/
		printAgreement = new UtilButton("pic/printAgr.png","pic/printAgr.png");
		printAgreement.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击打印协议书");
				Agreement();
			}
		});
		printAgreement.setIconTextGap(6);
		printAgreement.setBounds(585, 174, 200,300);
		this.showJpanel.add(printAgreement);
		
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
		
		//根据打印机状况显示按钮
		initShow();
	}
	
	//页面显示图片根据打印机情况而定
	public void initShow(){
		if(!"0".equals(GlobalParameter.printStatus)){
			printAcc.setVisible(false);
			printAgreement.setLocation(430, 174);
		}
	}

	//选择打印存单
	public void Acc(){
		billPrintBean.setPrintChoice("0");
		billPrintBean.getReqMCM001().setTranscode("11035");
		if(!getPrintRateStatus()){
			clearTimeText();
			billPrintBean.getReqMCM001().setIntereturnmsg("查询存单利率打印状态失败");
			MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
			serverStop("查询存单利率打印状态失败，请联系大堂经理。", "", "");
			return;
		}
		clearTimeText();
		openPanel(new TransPrintCheckTransPathPanel(billPrintBean));
	}
	
	//选择打印协议书
	public void Agreement(){
		billPrintBean.setPrintChoice("1");
		billPrintBean.getReqMCM001().setTranscode("11036");
		clearTimeText();
		openPanel(new TransPrintCheckTransPathPanel(billPrintBean));
	}
	
	//查询打印存单利率打印开关
	public boolean getPrintRateStatus(){
		try {
			OnOffSetUp oosu=new OnOffSetUp();
			Map map=oosu.checkOnOffState("rate");
			if(!"000000".equals(map.get("resCode"))){
				logger.info("查询存单利率打印状态失败："+map.get("errMsg"));
				return false;
			}
			GlobalParameter.printRateStatus=(String)map.get("STATE");
			return true;
		} catch (Exception e) {
			logger.error("查询存单利率打印状态失败："+e);
			return false;
		}
	}
}