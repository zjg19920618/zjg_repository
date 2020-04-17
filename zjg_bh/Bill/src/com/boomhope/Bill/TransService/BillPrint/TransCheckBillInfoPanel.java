package com.boomhope.Bill.TransService.BillPrint;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintSubAccInfoBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.InterfaceSendMsg;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;

/**
 * 查询存单信息
 * @author hk
 *
 */
public class TransCheckBillInfoPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransCheckBillInfoPanel.class);
	private static final long serialVersionUID = 1L;
	
	public TransCheckBillInfoPanel(BillPrintBean bean) {
		billPrintBean=bean;
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
		voiceTimer = new Timer(BaseTransPanelNew.voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) { 
            	stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	excuteVoice("voice/checktf.wav");
            	//查询存单信息
            	checkBill();
            }      
        });            
		voiceTimer.start();
		// 标题
		JLabel depoLum = new JLabel("查询存单信息中，请稍候......");
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(depoLum);
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/checking.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(404,170, 340, 253);
		this.showJpanel.add(billImage);
		
	}
	
	/**
	 * 查询存单信息
	 */
	public void checkBill(){
		new Thread(){
			public void run(){
				
				//查询存单信息
				if(!checkBillMsg()){
					stopTimer(voiceTimer);//关闭语音
	            	closeVoice();//关闭语音流
					return;
				}
				
				//全部成功，进入存单展现页
				clearTimeText();//清空倒计时
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	openPanel(new TransChoiceSubInfoPanel());
				
			}
		}.start();
	}
	
	
	/**
	 * 调用存单信息查询接口
	 */
	public boolean checkBillMsg(){
		try {
			billPrintBean.setIsCheckPass("1");//验密
			billPrintBean.getReqMCM001().setReqBefor("07601");
			Map inter07601 = InterfaceSendMsg.inter07601(billPrintBean);
			billPrintBean.getReqMCM001().setReqAfter((String)inter07601.get("resCode"),(String)inter07601.get("errMsg"));
			if(!"000000".equals(inter07601.get("resCode"))){
				logger.info((String)inter07601.get("errMsg"));
				if(((String)inter07601.get("errMsg")).startsWith("A102")){
					openConfirmDialog("密码错误，是否重新输入密码。是：重新收入密码；否：退出。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							logger.info("进入重新手输页面");
							closeDialog(confirmDialog);
							clearTimeText();
							//重新手输
							openPanel(new TransInputBankCardPassPanel(billPrintBean,bankBeans));
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							logger.info("返回首页");
							closeDialog(confirmDialog);
							clearTimeText();
							returnHome();
						}
					});
				}else{
					MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
					clearTimeText();
					serverStop("存单验密失败，请联系大堂经理。", (String)inter07601.get("errMsg"), "");
				}
				return false;
			}
		} catch (Exception e) {
			logger.error("调用07601存单账号信息查询失败"+e);
			billPrintBean.getReqMCM001().setIntereturnmsg("调用07601存单账号信息查询失败");
			MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
			serverStop("存单信息查询失败","调用07601存单账号信息查询失败","");
			return false;
		}
		
		return true;
	}
}
