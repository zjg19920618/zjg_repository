package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.UtilVoice;
import com.boomhope.tms.peripheral.action.Dimension;
import com.boomhope.tms.peripheral.bean.DimensionReadBean;
import com.boomhope.tms.peripheral.bean.IdCardReadBean;

/**
 * 扫描二维码
 * @author gyw
 *
 */
public class MoneyBoxScanningPanel extends BaseTransPanelNew{
	java.util.Timer timer = null;
	Thread thread = null;
	static Logger logger = Logger.getLogger(MoneyBoxScanningPanel.class);
	IdCardReadBean bean = null;
	UtilVoice utilVoice = null;
	SourceDataLine line = null;
	JButton button  = null;
	private static final long serialVersionUID = 1L;
	public MoneyBoxScanningPanel(final PublicCashOpenBean transBean) {
		this.cashBean= transBean;

		//设置开关
		GlobalParameter.OFF_ON = false;
		//倒计时打开语音 
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);
            	excuteVoice("voice/maxcard.wav");
            	readDimensionInfo(transBean);
            }      
        });            
		voiceTimer.start(); 


		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	transBean.setErrmsg("二维码扫描超时!");
            	transBean.getImportMap().put("CashQRCode", GlobalPanelFlag.ENTRY_MODE+"");
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        }); 
		delayTimer.start(); 
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请对准二维码扫描区，进行扫描");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 60, GlobalParameter.TRANS_WIDTH, 60);
		this.showJpanel.add(titleLabel);
		
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/QRCode.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(275, 203, 460, 360);
		this.showJpanel.add(billImage);
		

		
	
	}
	
	/**
	 * 处理成功
	 * 
	 */
	private void success(PublicCashOpenBean transBean ){
		transBean.setQrCode("1");
		clearTimeText();
		openPanel(new MoneyBoxPasswordPanel(transBean));
		
	}
	
	/**
	 * 失败
	 * @param fail
	 */
	public void fail(PublicCashOpenBean transBean,String fail){
		transBean.setErrmsg(fail);
		transBean.getImportMap().put("Maxcard", GlobalPanelFlag.ENTRY_MODE+"");
		clearTimeText();
		openPanel(new MoneyBoxMistakePanel(transBean));
			
		
	}

	/**
	 * 获取二维码信息
	 */
	private void readDimensionInfo(final PublicCashOpenBean transBean){
		Thread thread  = new Thread("读取代理人身份证线程"){
            @Override
            public void run(){
            	try {
        			DimensionReadBean bean=Dimension.dimensionRead("2");
        			if(!bean.getStatus().equals("0")){
        				fail(transBean, bean.getStatusDesc());
        				return;
        			}
        			String info=bean.getInfo();
        			//000000001_053600150_20170214000014_320882199011271214
        			if(info.contains("_")){
        				String infos[]=info.split("_");
        				if(infos.length>=4){
        					transBean.setOrderNo(infos[2]);
        					success(transBean);
        				}else{
        					fail(transBean, "二维码信息格式有误");
        				}
        			}else{
        				fail(transBean, "二维码信息格式有误");
        			}
        		} catch (Exception e) {
        			logger.error("调用二维码扫描仪失败"+e);
        			fail(transBean, "调用二维码扫描仪失败");
        		}
            }
        };
        thread.start();
	}
	public static void main(String[] args) {
		try {
			DimensionReadBean bean=Dimension.dimensionRead("2");
			if(!bean.getStatus().equals("0")){
				System.out.println(bean.getStatusDesc());
				return;
			}
			String info=bean.getInfo();
			//000000001_053600150_20170214000014_320882199011271214
			if(info.contains("_")){
				String infos[]=info.split("_");
				if(infos.length>=4){
//					transBean.setOrderNo(infos[2]);
					System.out.println(infos[2]);
				}else{
					System.out.println("二维码信息格式有误："+info);
				}
			}else{
				System.out.println("二维码信息格式有误："+info);
			}
		} catch (Exception e) {
			logger.error("调用二维码扫描仪失败"+e);
			System.out.println("调用二维码扫描仪失败");
		}
	}
}
