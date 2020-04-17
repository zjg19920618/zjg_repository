package com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseLoginFrame;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.FileUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.peripheral.action.PaperCutter;
import com.boomhope.Bill.peripheral.bean.PaperCutterOpenBean;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
/**
 * 第二次插入存单
 * @author zjg
 *
 */
public class BillChangeOpenInputDepositPanels extends BaseTransPanelNew {
	
	private static final long serialVersionUID = 1L;
	private boolean cundan = false;
	static Logger logger = Logger.getLogger(BillChangeOpenInputDepositPanels.class);
	private String billImgPath;//扫描图片路径
	
	public BillChangeOpenInputDepositPanels() {
		
		// 生成流程序号
		bcOpenBean.setFid(DateUtil.getNowDate("yyyyMMddhhmmss"));
		/* 显示时间倒计时 */
		this.showTimeText(BaseTransPanelNew.delaySecondthirtyTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondthirtyTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
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
		
		open();//打开回单模块
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			logger.error("线程睡眠调用异常："+e1);
		}
		readIdCard();//打开裁纸器
		
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
		            	
		            	Map<String, String> check = jwCheck();
						if("999999".equals(check.get("resCode"))){//存单扫描失败
							
							clearTimeText();//清空倒计时
							stopTimer(voiceTimer);//关闭语音
			            	closeVoice();//关闭语音流
							serverStop("存单扫描失败","", check.get("errMsg"));
							return;
							
						}
						
						openPanel(new BillChangeOpenCheckBillPanel());
						
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
	
	/**
	 * 扫描存单
	 */
	public Map<String,String> jwCheck(){
		Map<String,String> map = new HashMap<String,String>();
		String fc = null;
		String rc = null;
		String ir = null;
		String uv  = null;
		//扫描存单
		try{
			GlobalParameter.ACC_STATUS = true;   //设为有存单标识
			
			logger.debug("开始扫描存单：BH_Scan,扫描前先睡100ms");
			sleep(100);
			
			if(Dispatch.call(BaseLoginFrame.dispath, "BH_Scan",new Variant("0")).getInt() == 0){
				logger.debug("扫描完毕睡100ms");
				Thread.sleep(100);
				logger.debug("开始获取存单信息：BH_Scan");
				
				//获取扫描存单路径
				billImgPath = Dispatch.call(BaseLoginFrame.dispath, "ScanInfo").getString();
				logger.info("存单路径："+billImgPath);
				
				//扫描存单路径格式不符，报错
				if(billImgPath.indexOf("<Results>") == -1){
					logger.error("扫描存单返回格式异常");
					map.put("resCode", "999999");
					map.put("errMsg", "扫描存单返回格式异常");
					return map;
				}
			}
			
			billImgPath = billImgPath.substring(billImgPath.indexOf("<Results>"));
			
			fc = billImgPath.substring(billImgPath.indexOf("<FColor>")+8, billImgPath.indexOf("</FColor>"));
			rc = billImgPath.substring(billImgPath.indexOf("<RColor>")+8, billImgPath.indexOf("</RColor>"));
			ir = billImgPath.substring(billImgPath.indexOf("<IR>")+4, billImgPath.indexOf("</IR>"));
			uv = billImgPath.substring(billImgPath.indexOf("<UV>")+4, billImgPath.indexOf("</UV>"));
			
			logger.debug("正面彩图:"+fc);
			logger.debug("反面彩图:"+rc);
			logger.debug("红外图:"+ir);
			logger.debug("紫外图:"+uv);
			logger.debug("等待100ms然后开始复制图片");
			sleep(100);
			
			if(fc == null || "".equals(fc)){
				logger.error("存单扫描失败，未能生成正面彩图");
				map.put("resCode", "999999");
				map.put("errMsg", "存单扫描失败，未能生成正面彩图");
				return map;
			}else{
				String tmp = Property.BH_TMP + "\\"+DateUtil.getNowDate("yyyyMMdd")+"\\" +bcOpenBean.getFid()+"\\";
				// --------------------------fc上传----------------
				// 源目录
				File from_fc = new File(fc);
				// 目标目录
				File to_fc = new File(tmp+from_fc.getName());
				// 拷贝文件
				FileUtil.copyFileUsingJava7Files(from_fc,to_fc);
				logger.info("完成正面彩图fc拷贝");
				
				// --------------------------rc上传----------------
				// 源目录
				File from_rc = new File(rc);
				// 目标目录
				File to_rc = new File(tmp+from_rc.getName());
				// 拷贝文件
				FileUtil.copyFileUsingJava7Files(from_rc,to_rc);
				logger.info("完成反面彩图rc拷贝");
				
				// --------------------------ir上传----------------
				// 源目录
				File from_ir = new File(ir);
				// 目标目录
				File to_ir = new File(tmp+from_ir.getName());
				// 拷贝文件
				FileUtil.copyFileUsingJava7Files(from_ir,to_ir);
				logger.info("完成红外ir拷贝");
				
				// --------------------------uv上传----------------
				// 源目录
				File from_uv = new File(uv);
				// 目标目录
				File to_uv = new File(tmp+from_uv.getName());
				// 拷贝文件
				FileUtil.copyFileUsingJava7Files(from_uv,to_uv);
				logger.info("完成紫外uv拷贝");
				
				bcOpenBean.setBillPath_fc(tmp+from_fc.getName());
				bcOpenBean.setBillPath_rc(tmp+from_rc.getName());	
			}
			
		}catch(Exception e){
			logger.error("扫描存单图片失败"+e);
			map.put("resCode", "999999");
			map.put("errMsg", "扫描存单图片失败");
			return map;
		}
		
		//回单模块取消等待
		try {
			logger.debug("回单模块取消等待：BH_CancelWaitInsert");
			Dispatch.call(BaseLoginFrame.dispath, "BH_CancelWaitInsert");
		} catch (Exception e) {
			logger.error("回单模块取消等待失败"+e);
		}
		
		map.put("resCode", "000000");
		return map;
	}
}