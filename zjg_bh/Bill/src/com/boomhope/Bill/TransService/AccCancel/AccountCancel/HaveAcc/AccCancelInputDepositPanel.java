package com.boomhope.Bill.TransService.AccCancel.AccountCancel.HaveAcc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor.GOLD;

import com.boomhope.Bill.Framework.BaseLoginFrame;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.peripheral.action.PaperCutter;
import com.boomhope.Bill.peripheral.bean.PaperCutterOpenBean;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
/**
 * 第一次插入存单
 * @author hao
 *
 */
public class AccCancelInputDepositPanel extends BaseTransPanelNew {
	private static final long serialVersionUID = 1L;
	private boolean cundan = false;
	static Logger logger = Logger.getLogger(AccCancelInputDepositPanel.class);
	public AccCancelInputDepositPanel() {
		
		//设置开关
		GlobalParameter.OFF_ON = false;
		
		// 生成流程序号
		accCancelBean.setFid(DateUtil.getNowDate("yyyyMMddhhmmss"));
		/* 显示时间倒计时 */
		this.showTimeText(BaseTransPanelNew.delaySecondthirtyTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondthirtyTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	cundan = true;
            	//回单模块取消等待
				try {
					logger.debug("回单模块取消等待：BH_CancelWaitInsert");
					Dispatch.call(BaseLoginFrame.dispath, "BH_CancelWaitInsert");
				} catch (Exception e1) {
					logger.error("回单模块取消等待失败"+e1);
				}
				try {
					closeLed("4");
				} catch (Exception e1) {
					logger.error("裁纸器Led灯关闭失败"+e1);
				}
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		/* 倒计时打开语音 */
		voiceTimer = new Timer(BaseTransPanelNew.voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) { 
            	stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	excuteVoice("voice/zmcs.wav");
            	open();//打开回单模块
        		try {
        			Thread.sleep(1000);
        		} catch (InterruptedException e1) {
        			logger.error("线程睡眠调用异常："+e1);
        		}
        		readIdCard();//打开裁纸器
            }      
        });            
		voiceTimer.start();
		// 标题
		JLabel depoLum = new JLabel("请插入您的存单");
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(depoLum);
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/input_bill.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(325,200, 340, 350);
		this.showJpanel.add(billImage);
		
	}
	/**
	 * 打开回单模块等待存单插入
	 * 
	 */
	public void open(){
		Thread thread = new Thread("执行回单加载线程...") {
			@Override
			public void run() {
				//调用回单模块
				try {
					if (index == 1) {
						logger.debug("初始化调用回单模块：BH_Open");//程序启动后该方法只执行一次
						Dispatch.call(BaseLoginFrame.dispath, "BH_Open");// 打开
						index++;
					}
					// 清理资源
					logger.debug("清理资源：BH_CleanResource");
					Dispatch.call(BaseLoginFrame.dispath, "BH_CleanResource");
					
				} catch (Exception e) {
					logger.error("回单模块调用异常:"+e);
					serverStop("回单模块调用异常", "", "");
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关闭语音
	            	closeVoice();//关闭语音流
	            	try {
						closeLed("4");
					} catch (Exception e1) {
						logger.error("裁纸器Led灯关闭失败"+e1);
					}
					return;
				}
				//睡眠几秒
				try {
					logger.debug("睡眠100ms等待外设反应");
					sleep(100);
				} catch (InterruptedException e1) {
					logger.error(e1);
				}
				//打开舱门
				try {
					logger.debug("开启回单舱门,等待放入存单：BH_WaitInsert");
					if (Dispatch.call(BaseLoginFrame.dispath, "BH_WaitInsert",new Variant("0"), new Variant("0")).getInt() == 0) {
						logger.debug("开启回单舱门成功");
						clearTimeText();//清空倒计时
						stopTimer(voiceTimer);//关闭语音
		            	closeVoice();//关闭语音流
		            	try {
							closeLed("4");
						} catch (Exception e1) {
							logger.error("裁纸器Led灯关闭失败"+e1);
						}
		            	
		            	//跳转存单鉴伪处理页
		            	openPanel(new AccCancelCheckTFPanel());
						
					} else {
						if(!cundan){
							
							logger.error(cundan);
							logger.error("开启回单舱门失败");
							serverStop("开启回单舱门失败", "", "");
							clearTimeText();//清空倒计时
							stopTimer(voiceTimer);//关闭语音
							closeVoice();//关闭语音流
							try {
								closeLed("4");
							} catch (Exception e1) {
								logger.error("裁纸器Led灯关闭失败"+e1);
							}
						}
						return;
					}
				} catch (Exception e) {
					logger.error("开启回单舱门异常:"+e);
					serverStop("开启回单舱门异常", "", "");
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关闭语音
	            	closeVoice();//关闭语音流
	            	try {
						closeLed("4");
					} catch (Exception e1) {
						logger.error("裁纸器Led灯关闭失败"+e1);
					}
					return;
				}
			}
		};
		thread.start();
	} 
	
	/**
	 * 打开裁纸器
	 */
	public void readIdCard() {
		Thread thread = new Thread("打开裁纸器...") {
			@Override
			public void run() {
				try {
					PaperCutterOpenBean paperCutterOpenBean = PaperCutter.openPaperCutter("5");
					if (!"0".equals(paperCutterOpenBean.getId())) {
						logger.error("裁纸器打开失败");
						
						//回单模块取消等待
						try {
							logger.debug("回单模块取消等待：BH_CancelWaitInsert");
							Dispatch.call(BaseLoginFrame.dispath, "BH_CancelWaitInsert");
						} catch (Exception e) {
							logger.error("回单模块取消等待失败"+e);
						}
						serverStop("裁纸器打开失败", paperCutterOpenBean.getRemark(),"");
						clearTimeText();//清空倒计时
						stopTimer(voiceTimer);//关闭语音
		            	closeVoice();//关闭语音流
						return;
					}
				} catch (Exception e) {
					logger.error("裁纸器打开失败"+e);
					
					//回单模块取消等待
					try {
						logger.debug("回单模块取消等待：BH_CancelWaitInsert");
						Dispatch.call(BaseLoginFrame.dispath, "BH_CancelWaitInsert");
					} catch (Exception e1) {
						logger.error("回单模块取消等待失败"+e1);
					}
					serverStop("裁纸器打开失败", "", "");
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关闭语音
	            	closeVoice();//关闭语音流
					return;
				}
				try {
					openLed("4");
				} catch (Exception e) {
					logger.error("裁纸器Led灯打开失败"+e);
				}
			}
		};
		thread.start();
	}
}