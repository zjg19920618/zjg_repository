package com.boomhope.Bill.TransService.CashOpen.moneybox;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
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
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.FileUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.UtilButton;
import com.boomhope.Bill.Util.UtilVoice;
import com.boomhope.Bill.peripheral.action.IdCard;
import com.boomhope.Bill.peripheral.action.MachineLed;
import com.boomhope.Bill.peripheral.bean.IdCardReadBean;
import com.boomhope.Bill.peripheral.bean.LedStateBean;
/**
 * 请插入身份证
 * @author gyw
 *
 */
public class MoneyBoxIdentificationCardPanel extends BaseTransPanelNew{
	java.util.Timer timer = null;
	Thread thread = null;
	static Logger logger = Logger.getLogger(MoneyBoxIdentificationCardPanel.class);
	IdCardReadBean bean = null;
	JButton button  = null;
	private static final long serialVersionUID = 1L;
	public MoneyBoxIdentificationCardPanel(final PublicCashOpenBean transBean) {


		this.cashBean = transBean;

		//倒计时打开语音 
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	voiceTimer.stop();
            	openLed(transBean);
            	excuteVoice("voice/corresponding.wav");
            	readIdCard(transBean);
            }      
        });            
		voiceTimer.start(); 


		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	closeLed(transBean);
            	transBean.getImportMap().put("CashCard", GlobalPanelFlag.DEPUTY_SELECTION+"");
            	transBean.setErrmsg("身份证读取超时，请重新插入身份证");
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        }); 
		delayTimer.start(); 
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请插入本账户对应的身份证");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(titleLabel);
		
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/input_idCard.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(320, 236, 460, 360);
		this.showJpanel.add(billImage);
		
		
		
	}
	
	/**
	 * 读卡信息
	 */
	public void readIdCard(final PublicCashOpenBean transBean){
		thread  = new Thread("readIdCard"){
            @Override
            public void run(){
            	IdCard card = new IdCard();
        		// 打开机器
        		try {
        			bean = card.IdCardRead("2");
        		} catch (Exception e) {
        			logger.error("读卡器打开异常");
        			transBean.setErrmsg("未找到身份证读取设备");
        			serverStop("请联系大堂经理!", "","未找到身份证读取设备");
        			return;
        		}
        		String status = bean.getStatus();
        		logger.info("status=" + status);            	
        		if("0".equals(status)){
        			// 成功
        			transBean.setQfjg(bean.getIssuingUnit());// 发证机关
        			transBean.setSex(bean.getSex());// 性别
        			transBean.setReadIdcard(bean.getIdCode());// 证件号
        			transBean.setIdCardName(bean.getName());// 姓名
        			transBean.setIdCardtitle(bean.getPhotoPath());//头像照片
        			transBean.setIdCardface(bean.getFrontFace());//正面
        			transBean.setIdCardback(bean.getBackFace());// 背面
        			transBean.setAddress(bean.getAddress());// 地址
        			transBean.setBirthday(bean.getBirthday());//生日
        			transBean.setEndDate(bean.getEndDate());//有效日期
        			String form1 = Property.BILL_ID_SELF_JUST;
        			String form2 = Property.BILL_ID_SELF_AGAINST;
        			//String form3 = Property.BILL_ID_SELF_FACE;
        			try {
	       				FileUtil.copy(new File(bean.getFrontFace()), new File(form1));
	        			FileUtil.copy(new File(bean.getBackFace()), new File(form2));
	        			/*AmplificationImage image = new AmplificationImage();
	        			boolean flag = image.zoomPicture(bean.getPhotoPath(), form3, 132, 163, true);
	        			if(!flag){
	        				transBean.setErrmsg("读取身份证异常");
	        				serStop();
	        			}*/
	        			//FileUtil.copy(new File(bean.getPhotoPath()), new File(form3));
        			} catch (Exception e) {
        				logger.error("身份证拷贝时失败！！！");
        				logger.error(e);
        				transBean.setErrmsg("读取身份证异常");
        				serverStop("请联系大堂经理!", "","读取身份证异常");
        			}
        			new File(bean.getFrontFace()).delete();
        			new File(bean.getBackFace()).delete();
        			new File(bean.getPhotoPath()).delete();
        			// 拷贝临时图片--------------------
        			String tmp = Property.BH_TMP + "\\"+DateUtil.getNowDate("yyyyMMdd")+"\\" +transBean.getFid()+"\\";
        			// 拷贝临时图片-------正面-------------
					File from_f = new File(form1);
					// 目标目录
					File to_f = new File(tmp+bean.getIdCode()+"_meIdf.bmp");
        			try {
						FileUtil.copyFileUsingJava7Files(from_f, to_f);
					} catch (IOException e) {
						logger.error("拷贝临时图片异常");
        				logger.error(e);
        				transBean.setErrmsg("拷贝临时图片异常");
        				serverStop("请联系大堂经理!", "","拷贝临时图片异常");
					}
        			
        			// 拷贝临时图片-------反面-------------
					File from_m = new File(form2);
					// 目标目录
					File to_m = new File(tmp+bean.getIdCode()+"_meIdm.bmp");
        			try {
						FileUtil.copyFileUsingJava7Files(from_m, to_m);
					} catch (IOException e) {
						logger.error("拷贝临时图片异常");
        				logger.error(e);
        				transBean.setErrmsg("拷贝临时图片异常");
        				serverStop("请联系大堂经理!", "","拷贝临时图片异常");
					}
        			success(transBean);
        		}else if("3".equals(status)){
        			// 超时
        			logger.error("读卡失败原因:"+bean.getStatusDesc());
        			return;
        		}else if("4".equals(status)){
        			// 失败
        			logger.error("读卡失败原因:"+bean.getStatusDesc());
        			transBean.setErrmsg("身份证读取失败，请重新插入身份证");
                	fail(transBean); 
        		}else{
        			// 其它错误
        			transBean.setErrmsg("设备出现未知异常");
        			logger.error("读卡失败原因:"+bean.getStatusDesc());
        			serverStop("请联系大堂经理!", "","读卡失败原因:"+bean.getStatusDesc());
        		}
            }
        };
        thread.start();
	}
	/**打开led灯*/
	public void openLed(PublicCashOpenBean transBean){
		LedStateBean openLed = new LedStateBean();
		try {
			openLed = MachineLed.openLed("1");
			logger.info("身份证Led灯打开返回值："+openLed);
		} catch (Exception e) {
			logger.error("身份证led灯通讯异常");
			transBean.setErrmsg("未找到身份证Led灯设备");
			return;
		}
		logger.info("身份证Led灯打开："+openLed.getStatus());
	}
	
	/**关闭led灯*/
	public void closeLed(PublicCashOpenBean transBean){
		LedStateBean closeLed = new LedStateBean();
		try {
			closeLed = MachineLed.closeLed("1");
			logger.info("身份证Led灯关闭返回值："+closeLed);
		} catch (Exception e) {
			logger.error("身份证led灯通讯异常");
			transBean.setErrmsg("未找到身份证Led灯设备");
			return;
		}
		logger.info("身份证Led灯关闭："+closeLed.getStatus());
	}
	
	
	
	
	/**
	 * 失败处理
	 * 
	 */
	private void fail(PublicCashOpenBean transBean){
		closeLed(transBean);
		transBean.getImportMap().put("CashCard", GlobalPanelFlag.DEPUTY_SELECTION+"");
		clearTimeText();
		openPanel(new MoneyBoxMistakePanel(transBean));
		
	}
 	
	/**
	 * 处理成功
	 * 
	 */
	private void success(PublicCashOpenBean transBean){
//		transBean.getImportMap().put("backStep", GlobalPanelFlag.INPUT_IDCARD+"");
		clearTimeText();
		openPanel(new MoneyBoxOutputIdCardPanel(transBean));
		
	}
	
	/**
	 * 上一步
	 */
	public void nextStep1(PublicCashOpenBean transBean){
		
		clearTimeText();
		openPanel(new MoneyBoxDeputySelectionPanel(transBean));
		
		
		
	}
	}


