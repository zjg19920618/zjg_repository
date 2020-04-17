package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.Socket.SocketClient;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.UtilVoice;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.bck.BCK0010ResBean;
import com.boomhope.tms.peripheral.action.FingerPrint;
import com.boomhope.tms.peripheral.bean.FingerPrintGet;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 指纹识别
 * @author gyw
 *
 */
public class MoneyBoxAdditionFingerprintPanel extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(MoneyBoxAdditionFingerprintPanel.class);
	private static final long serialVersionUID = 1L;
	JLabel fingerprint = null;
	SourceDataLine line = null;
	String zwValue = null;
	/***
	 * 初始化
	 */
	public MoneyBoxAdditionFingerprintPanel(final PublicCashOpenBean transBean){
		
		this.cashBean = transBean;
		GlobalPanelFlag.CurrentFlag = GlobalPanelFlag.MONEYBOX_ADDITION_FINGERPRINT;
		
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);
            	excuteVoice("voice/fingerprint.wav");
            	
            }      
        });            
		voiceTimer.start(); 
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
//		/* 提示信息 */
//		JLabel promptLabel = new JLabel("温馨提示：请录入四次指纹");
//		promptLabel.setHorizontalAlignment(JLabel.CENTER);
//		promptLabel.setFont(new Font("微软雅黑", Font.PLAIN, 25));
//		promptLabel.setBounds(0, 573, 1059, 30);
//		this.add(promptLabel);
		
		/* 标题信息 */
		JLabel t = new JLabel("请录入指纹:");
		t.setHorizontalAlignment(JLabel.CENTER);
		t.setForeground(Color.decode("#412174"));
		t.setFont(new Font("微软雅黑", Font.BOLD, 40));
		t.setBounds(0, 60,1009, 60);
		this.showJpanel.add(t);
		
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/fingerprint.png"));
		billImage.setIconTextGap(6);
		billImage.setBounds(395, 320, 220, 200);
		this.showJpanel.add(billImage);
		
		
		JButton btnNewButton = new JButton("测试按钮-成功");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				openPanel(new MoneyBoxOkInputDepinfoPanel(transBean));
			}

		});
		btnNewButton.setBounds(299, 500, 156, 34);
		Property property = new Property();
		if(property.getProperties().getProperty("push_button").equals("false")){
			btnNewButton.setVisible(false);
		}else if (property.getProperties().getProperty("push_button").equals("true")){
			btnNewButton.setVisible(true);
		}
		this.showJpanel.add(btnNewButton);
		
		JButton button = new JButton("测试按钮-失败");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				fail(); 
			}

		});
		button.setBounds(484, 500, 156, 34);
		if(property.getProperties().getProperty("push_button").equals("false")){
			button.setVisible(false);
		}else if (property.getProperties().getProperty("push_button").equals("true")){
			button.setVisible(true);
		}
		this.showJpanel.add(button);
		
		fingerprint = new JLabel("指纹验证失败,请重新录入");
		fingerprint.setVisible(false);;
		fingerprint.setFont(new Font("微软雅黑", Font.BOLD, 15));
		fingerprint.setHorizontalAlignment(SwingConstants.CENTER);
		fingerprint.setForeground(Color.RED);
		fingerprint.setBounds(394, 557, 276, 15);
		this.showJpanel.add(fingerprint);
		
		openZw(transBean);
	}
	
	
	
	/**
	 * 成功处理
	 */
	private void success(PublicCashOpenBean transBean){
		transBean.getImportMap().put("identifying", "8");
		clearTimeText();
		openPanel(new MoneyBoxSystemProcessingPanel(transBean));
	}
	
	/**
	 * 失败处理
	 */
	private void fail(){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				fingerprint.setVisible(true);
			}
			
		});
		
	}
	/**
	 * 失败处理(当柜员号角色权限不足时，采用此方法)
	 */
	private void fails(PublicCashOpenBean transBean,String errmsg){
		transBean.setErrmsg(errmsg);
		transBean.getImportMap().put("CashJurisdiction", GlobalPanelFlag.MONEYBOX_ADDITION_FINGERPRINT+"");
		clearTimeText();
		serverStop("请重新授权",errmsg, "");
		
		
	}
	/**
	 * 前置校验指纹值
	 */
	public void getZw(PublicCashOpenBean transBean){
		
		transBean.getReqMCM001().setIntecode("07660");
		BCK0010ResBean resBean = checkZW(transBean);
		if(resBean == null){
			transBean.getReqMCM001().setIntereturnmsg("调用07660接口异常，授权失败。");
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
			serverStop("请重新操作","","系统异常，存单已退出");
			return;
		}
		String resCode = resBean.getHeadBean().getResCode();
		String resMsg = resBean.getHeadBean().getResMsg();
		transBean.getReqMCM001().setReqAfter(resCode, resMsg);
		logger.info("resCode:"+resCode);
		logger.info("resMsg:"+resMsg);
		if(!"000000".equals(resCode)){
			// 指纹错误
			if("710099".equals(resCode)){
				fail();
				openZw(transBean);
			}else if("700018".equals(resCode)){
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				//700018：柜员角色权限不够
				fails(transBean,resMsg);
			}else if("700066".equals(resCode)){
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				//700066：指纹录入错误
				fails(transBean,resMsg);
			}else{
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop("","调用前置接口异常","");
			}
			return;
		}
		success(transBean);
	}
	
	/**
	 * 校验授指纹
	 */
	public BCK0010ResBean checkZW(PublicCashOpenBean transBean){
		Map<String,String> map = new HashMap();
//		if(transBean.getPolicyIdentify()=="1"){
//			String tllerNo = this.transBean.getHandSupTellerNo();
//			map.put("tllerNo", tllerNo);
//		}else{
			String tllerNo = transBean.getSupTellerNo();
			map.put("tllerNo", tllerNo);
//		}
		map.put("zw", zwValue);
		SocketClient sc = new SocketClient();
		Socket socket=null;
		InputStream is=null;
		OutputStream os=null;
		try {
			 socket = sc.createSocket();
            //构建IO  
             is = socket.getInputStream();  
             os = socket.getOutputStream();  
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
            //向服务器端发送一条消息  
            bw.write(sc.BCK_0010(map) + "\n");  

            bw.flush();  

            //读取服务器返回的消息  
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
			
			String retMsg = "";
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)){
					break;
				}
			}
			logger.info(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("Root", BCK0010ResBean.class);
			reqXs.alias("Head", InReqHeadBean.class);
			
			BCK0010ResBean bck0010ResBean = (BCK0010ResBean)reqXs.fromXML(retMsg);
			logger.info(bck0010ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
            return bck0010ResBean;
	}catch (UnknownHostException e) {
		logger.error(e);
	} catch (IOException e) {
		logger.error(e);
	}finally{
		try {
			if(os!=null){
				os.close();
			}
			if(is!=null){
				is.close();
			}
			if(socket!=null){
				socket.close();
			}
		} catch (Exception e2) {
			logger.info(e2);
		}
	}
		return null;
	}
	
	/**
	 * 打开指纹
	 */
	public void openZw(final PublicCashOpenBean transBean){
		Thread thread  = new Thread("退卡线程"){
            @Override
            public void run(){
            	try {
            		logger.info("等待指纹录入.............");
            		FingerPrint fp = new FingerPrint();
            		FingerPrintGet fpg = fp.getFingerPrint("99");
            		String status = fpg.getStatus();
            		if("0".equals(status)){
            			//成功指纹值
            			zwValue = fpg.getFingerPrint();
            		}else if("3".equals(status)){
            			// 超时
            			serverStop("指纹授权超时","","录入授权指纹退出");
            		}else{
            			// 其它错误
            			serverStop("请联系维修人员","","指纹外设故障");
            		}
            		logger.info("指纹录入完成.............");
				} catch (Exception e) {
					logger.error(e);
				}
            	getZw(transBean);
            }
        };
        thread.start();
	}
	
	
	
	

}
