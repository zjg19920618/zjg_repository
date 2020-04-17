package com.boomhope.Bill.TransService.BillPrint;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.peripheral.action.ICBank;
import com.boomhope.Bill.peripheral.action.MachineLed;
import com.boomhope.Bill.peripheral.bean.ICBankCheckStatusBean;
import com.boomhope.Bill.peripheral.bean.ICBankReadBean;
import com.boomhope.Bill.peripheral.bean.LedStateBean;

/***
 * 插入银行卡界面Panel
 * @author zy
 *
 */
public class TransPrintBackInputBankCardPanel extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(TransPrintBackInputBankCardPanel.class);
	private static final long serialVersionUID = 1L;
	ExecutorService executorService = Executors.newCachedThreadPool();
	/***
	 * 初始化
	 */
	public TransPrintBackInputBankCardPanel(BillPrintBean transBean){
		
		this.billPrintBean = transBean;
		logger.info("进入插入银行卡页面");
		
		//设置开关
		GlobalParameter.OFF_ON = false;
		
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	try {
            		closeLed("2");
				} catch (Exception e1) {
					logger.error("led灯关闭失败"+e);
				}
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) { 
            	stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	excuteVoice("voice/interposition.wav");
            	
            }      
        });            
		voiceTimer.start();
		try {
			openLed("2");
		} catch (Exception e1) {
			logger.error("led灯打开失败"+e1);
		}
		
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请插入您的银行卡");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 100, GlobalParameter.TRANS_WIDTH-50, 40);
		this.showJpanel.add(titleLabel);
		
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/input_bank.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(325, 210, 360, 390);
		this.showJpanel.add(billImage);
		
		getBankInfo(transBean);
		
	}
	
	
	/**
	 * 读卡操作
	 */
	public void getBankInfo(final BillPrintBean transBean){
		Thread thread  = new Thread("读卡银行卡线程"){
            @Override
            public void run(){
            	try {
            		ICBank bank = new ICBank();
        			ICBankReadBean read = bank.ICBankRead("50");
        			String status = read.getStatus();
        			if("0".equals(status)){
        				// 成功
        				String bankCode = read.getIcBankCode();
        				transBean.setAccNo(bankCode);//卡号
        				closeLed(transBean);
        				clearTimeText();
        				//退卡
        				ICBankCheckStatusBean bean;
        				try {
        					bean = ICBank.checkStatus("2");
        					if(!"0".equals(bean.getStatus())){
        						logger.error(bean.getStatusDesc());
        						return;
        					}
        					//判断读卡器里是否有卡
        					if(bean.getTouchStatus().equals("1")){
        						//若有卡则退卡
        						ICBank.ICBankQuit("2", "60");
        					}
        				} catch (Exception e) {
        					logger.error("读卡器异常："+e);
        				}
        				openPanel(new OutputBankCardPanel(transBean,bankBeans));
        			}else if("3".equals(status)){
        				// 超时
        				logger.error("读取身份证超时");
        				return;
        			}else if("4".equals(status)){
        				// 读卡失败
        				transBean.setErrmsg("银行卡读卡器超时");
        				fail(transBean, "读卡失败,请重新插入");
        			}else{
        				// 其它
        				transBean.setErrmsg("银行卡读卡器未知异常");
        				
        			}
        			
        		} catch (Exception e) {
        			logger.error(e);
        		}
            }
        };
        thread.start();
	}
	public void fail(BillPrintBean transBean,String fail){
		transBean.setErrmsg(fail);
		transBean.getImportMap().put("backStep", GlobalPanelFlag.INPUT_BANK_CARD+"");
		closeLed(transBean);
		clearTimeText();
		openPanel(new TransErrorPrintMsgPanel(transBean));
		
		
	}
	/**关闭led灯*/
	public void closeLed(BillPrintBean transBean){
		LedStateBean closeLed = new LedStateBean();
		try {
			closeLed = MachineLed.closeLed("2");
			logger.info("银行卡Led灯关闭返回值："+closeLed);
		} catch (Exception e) {
			logger.error("银行卡led灯通讯异常");
			transBean.setErrmsg("未找到银行卡Led灯设备");
			return;
		}
		logger.info("银行卡Led灯关闭："+closeLed.getStatus());
	}
	
	
}
