package com.boomhope.Bill.TransService.BillPrint;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.Util.AmplificationImage;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.FileUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.Bill.peripheral.action.IdCard;
import com.boomhope.Bill.peripheral.action.MachineLed;
import com.boomhope.Bill.peripheral.bean.IdCardReadBean;
import com.boomhope.Bill.peripheral.bean.LedStateBean;

/**
 * 请插入被代理人的身份证
 * @author zy
 *
 */
public class BackTransInputIdcard1Panel extends BaseTransPanelNew{
	
	Thread thread = null;
	static Logger logger = Logger.getLogger(BackTransInputIdcard1Panel.class);
	IdCardReadBean bean = null;
	private static final long serialVersionUID = 1L;
	public BackTransInputIdcard1Panel(final BillPrintBean transBean) {
		logger.info("进入插入本人身份证页面");
		this.billPrintBean = transBean;

		//设置开关
		GlobalParameter.OFF_ON = false;
		//倒计时打开语音 
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);
            	openLed(transBean);//开led灯    
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
            	closeLed(transBean);//关led灯
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
		billImage.setBounds(315, 236, 460, 360);
		this.showJpanel.add(billImage);

	}
	
	
	
	
	/**
	 * 下一步
	 */
	public void nextStep(BillPrintBean transBean){
		closeLed(transBean);
 		clearTimeText();
		openPanel(new TransPrintOutputIdcardPanel(transBean));
		
	}
	
	
	/**
	 * 读卡信息
	 */
	public void readIdCard(final BillPrintBean transBean){
		thread  = new Thread("readIdCard"){
            @Override
            public void run(){
            	      
            	IdCard card = new IdCard();
        		// 打开机器
        		try {
        			bean = card.IdCardRead("2");
        		} catch (Exception e) {
        			logger.error("读卡器打开异常"+e);
        			transBean.getReqMCM001().setIntereturnmsg("读卡器打开异常");
    				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
        			transBean.setErrmsg("未找到身份证读取设备");
        			fail(transBean);
        			return;
        		}
        		String status = bean.getStatus();
        		logger.info("status=" + status);            	
        		if(status != null){
    				delayTimer.stop();
    			}
        		if("0".equals(status)){
        			// 成功
        			logger.debug("身份证读取成功！");            	
        			transBean.setQfjg(bean.getIssuingUnit());// 发证机关
        			transBean.setSex(bean.getSex());// 性别
        			transBean.setReadIdcard(bean.getIdCode());// 证件号
        			transBean.setIdCardName(bean.getName());// 姓名
        			transBean.setIdCardtitle(bean.getPhotoPath());//头像照片
        			transBean.setIdCardface(bean.getFrontFace());//正面
        			transBean.setIdCardback(bean.getBackFace());// 背面
        			transBean.setEndDate(bean.getEndDate());//有效期结束
        			transBean.setBirthday(bean.getBirthday());//生日
        			String form1 = Property.BILL_ID_SELF_JUST;
        			String form2 = Property.BILL_ID_SELF_AGAINST;
        			String form3 = Property.BILL_ID_SELF_FACE;
        			try {
	       				FileUtil.copy(new File(bean.getFrontFace()), new File(form1));
	        			FileUtil.copy(new File(bean.getBackFace()), new File(form2));
	        			AmplificationImage image = new AmplificationImage();
	        			boolean flag = image.zoomPicture(bean.getPhotoPath(), form3, 132, 163, true);
	        			if(!flag){
	        				transBean.getReqMCM001().setIntereturnmsg("读取身份证信息失败，身份证可能损坏，弯曲");
	        				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
	        				transBean.setErrmsg("读取身份证信息失败，身份证可能损坏，弯曲");
	        				fail(transBean);
	        				return;
	        			}
        			} catch (Exception e) {
        				logger.error("身份证拷贝时失败！！！"+e);
        				transBean.getReqMCM001().setIntereturnmsg("读取身份证异常");
        				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
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
					File to_f = new File(tmp+bean.getIdCode()+"_meIdf.bmp");
        			try {
						FileUtil.copyFileUsingJava7Files(from_f, to_f);
					} catch (IOException e) {
						logger.error("拷贝临时图片异常"+e);
						transBean.getReqMCM001().setIntereturnmsg("读取身份证异常");
        				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
        				transBean.setErrmsg("拷贝临时图片异常");
        				fail(transBean);
        				return;
					}
        			
        			// 拷贝临时图片-------反面-------------
					File from_m = new File(form2);
					// 目标目录
					File to_m = new File(tmp+bean.getIdCode()+"_meIdm.bmp");
        			try {
						FileUtil.copyFileUsingJava7Files(from_m, to_m);
					} catch (IOException e) {
						logger.error("拷贝临时图片异常"+e);
						transBean.getReqMCM001().setIntereturnmsg("读取身份证异常");
        				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
        				transBean.setErrmsg("拷贝临时图片异常");
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
        			transBean.setErrmsg("设备出现未知异常");
        			logger.error("读卡失败原因:"+bean.getStatusDesc());
        			fail(transBean);
        		}
            }
        };
        thread.start();
	}
	/**打开led灯*/
	public void openLed(BillPrintBean transBean){
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
	public void closeLed(BillPrintBean transBean){
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
	private void fail(BillPrintBean transBean){
		closeLed(transBean);
		transBean.getImportMap().put("backStep", GlobalPanelFlag.INPUT_IDCARD+"");
		clearTimeText();
		openPanel(new TransErrorPrintMsgPanel(transBean));
			
	}
}
