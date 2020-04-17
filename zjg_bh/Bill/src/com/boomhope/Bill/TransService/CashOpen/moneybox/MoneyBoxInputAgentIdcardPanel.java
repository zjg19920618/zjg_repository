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
import com.boomhope.Bill.Util.UtilVoice;
import com.boomhope.Bill.peripheral.action.IdCard;
import com.boomhope.Bill.peripheral.action.MachineLed;
import com.boomhope.Bill.peripheral.bean.IdCardReadBean;
import com.boomhope.Bill.peripheral.bean.LedStateBean;

/**
 * 请插入代理人身份证
 * @author zy
 *
 */
public class MoneyBoxInputAgentIdcardPanel extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(MoneyBoxInputAgentIdcardPanel.class);
	String res_status = null;
	UtilVoice utilVoice = null;
	SourceDataLine line = null;
	private static final long serialVersionUID = 1L;
	public MoneyBoxInputAgentIdcardPanel(final PublicCashOpenBean transBean) {
		this.cashBean = transBean;
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);
            	openLed(transBean);
            	excuteVoice("voice/agentid.wav");
            	readIdCard(transBean);
            }      
        });            
		voiceTimer.start(); 
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请插入代理人的身份证");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(titleLabel);
		
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/input_idCard.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(325, 236, 460, 360);
		this.showJpanel.add(billImage);
		
		
		
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	
            	closeLed(transBean);
            	clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start();
		
		
	}
	
	
	/**
	 * 下一步
	 */
	public void nextStep(PublicCashOpenBean transBean){
		clearTimeText();
		openPanel(new MoneyBoxOutputAgentIdcardPanel(transBean));
	}
	
	
	
	/**
	 * 读卡信息
	 */
	public void readIdCard(final PublicCashOpenBean transBean){
		Thread thread  = new Thread("读取代理人身份证线程"){
            @Override
            public void run(){
            	logger.info("开始读取....");
            	IdCard card = new IdCard();
        		// 打开机器
            	IdCardReadBean bean = null;
        		try {
        			 bean = card.IdCardRead("2");
        		} catch (Exception e) {
        			res_status = "Y";
        			logger.error("读卡器打开异常"+e);
        			transBean.setErrmsg("未找到身份证读取设备");
        			fail(transBean);
        			return;
        		}
        		String status = bean.getStatus();
        		logger.debug("status=" + status);    
        		if(status != null){
    				delayTimer.stop();
    			}
        		if("0".equals(status)){
        			// 成功
        			transBean.setAgentqfjg(bean.getIssuingUnit());// 代理人发证机关
        			transBean.setAgentsex(bean.getSex());// 性别
        			transBean.setAgentIdCardNo(bean.getIdCode());// 证件号
        			transBean.setAgentIdCardName(bean.getName());// 姓名
        			transBean.setAgentIdCardtitle(bean.getPhotoPath());// 姓名
        			transBean.setAgentIdCardface(bean.getFrontFace());//正面
        			transBean.setAgentIdCardback(bean.getBackFace());// 背面
        			transBean.setAgentBirthday(bean.getBirthday());//生日
        			transBean.setAgentEndDate(bean.getEndDate());//有效日期
        			String form1 = Property.BILL_ID_AGENT_JUST;
        			String form2 = Property.BILL_ID_AGENT_AGAINST;
        			try {
        				FileUtil.copy(new File(bean.getFrontFace()), new File(form1));
        				FileUtil.copy(new File(bean.getBackFace()), new File(form2));
        			} catch (Exception e) {
        				logger.error("身份证拷贝时失败！！！"+e);
        				transBean.setErrmsg("读取身份证异常");
        				fail(transBean);
        				return;
        			}
        			new File(bean.getFrontFace()).delete();
        			new File(bean.getBackFace()).delete();
        			new File(bean.getPhotoPath()).delete();
        			// 拷贝临时图片--------------------
        			String tmp = Property.BH_TMP + "\\"+DateUtil.getNowDate("yyyyMMdd")+"\\" +transBean.getFid()+"\\";
        			// 拷贝临时图片-------正面-------------
					File from_f = new File(form1);
					// 目标目录
					File to_f = new File(tmp+bean.getIdCode()+"_agentIdf.bmp");
        			try {
						FileUtil.copyFileUsingJava7Files(from_f, to_f);
					} catch (IOException e) {
						logger.error("拷贝临时图片异常"+e);
        				fail(transBean);
        				return;
					}
        			
        			// 拷贝临时图片-------反面-------------
					File from_m = new File(form2);
					// 目标目录
					File to_m = new File(tmp+bean.getIdCode()+"_agentIdm.bmp");
        			try {
						FileUtil.copyFileUsingJava7Files(from_m, to_m);
					} catch (IOException e) {
						logger.error("拷贝临时图片异常"+e);
        				fail(transBean);
        				return;
					}
        			nextStep(transBean);
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
        			transBean.setErrmsg("未知异常");
        			logger.error("读卡失败原因:"+bean.getStatusDesc());
        			fail(transBean);
        		}
        		logger.info("结束读取....");
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
		transBean.getImportMap().put("CashProcurator", GlobalPanelFlag.DEPUTY_SELECTION+"");
		clearTimeText();
		openPanel(new MoneyBoxMistakePanel(transBean));
		
	}
	
	/**
	 * 上一步
	 */
	public void nextStep1(PublicCashOpenBean transBean){
		closeLed(transBean);
		line.close();
		clearTimeText();
		openPanel(new MoneyBoxDeputySelectionPanel(transBean));
		
		
		
	}
}
