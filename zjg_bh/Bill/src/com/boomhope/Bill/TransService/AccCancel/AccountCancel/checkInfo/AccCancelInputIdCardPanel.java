package com.boomhope.Bill.TransService.AccCancel.AccountCancel.checkInfo;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.Util.AmplificationImage;
import com.boomhope.Bill.Util.FileUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.peripheral.action.IdCard;
import com.boomhope.Bill.peripheral.bean.IdCardReadBean;
/**
 * 请插入身份证
 * @author hao
 *
 */
public class AccCancelInputIdCardPanel extends BaseTransPanelNew{
	Thread thread = null;
	static Logger logger = Logger.getLogger(AccCancelInputIdCardPanel.class);
	IdCardReadBean bean = null;
	private static final long serialVersionUID = 1L;
	public AccCancelInputIdCardPanel() {
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
		//倒计时打开语音 
		voiceTimer = new Timer(BaseTransPanelNew.voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
            	try {
					openLed("1");
				} catch (Exception e1) {
					logger.error("led灯打开失败"+e);
				}
            	excuteVoice("voice/corresponding.wav");
            	readIdCard();
            }      
        });            
		voiceTimer.start(); 
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请插入本账户对应的身份证");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 60, GlobalParameter.TRANS_WIDTH-50, 40);
		this.showJpanel.add(titleLabel);
		
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/input_idCard.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(325, 170, 362, 320);
		this.showJpanel.add(billImage);
		
	}
	
	/**
	 * 读卡信息
	 */
	public void readIdCard(){
		thread  = new Thread("readIdCard"){
            @Override
            public void run(){
            	Map map = accCancelBean.getMap();
            	IdCard card = new IdCard();
        		// 打开机器
        		try {
        			bean = card.IdCardRead("2");
        		} catch (Exception e) {
        			logger.error("身份证读卡器打开异常"+e);
        			serverStop("身份证读卡器调用失败,请联系大堂经量","","身份证打开读卡器异常");
        			try {
                		closeLed("1");
    				} catch (Exception e1) {
    					logger.error("led灯关闭失败"+e);
    				}
        			return;
        		}
        		map.put("cardInfo", bean);
        		
        		String status = bean.getStatus();//读卡器状态
        		logger.info("status=" + status);       
        		
        		if(status == null){//状态为空，则说明未成功起动
    				delayTimer.stop();
    				logger.error("身份证读卡器打开异常");
    				serverStop("身份证读卡器调用失败,请联系大堂经量","","身份证读卡器打开异常");
    				try {
                		closeLed("1");
    				} catch (Exception e1) {
    					logger.error("led灯关闭失败"+e1);
    				}
    				return;
    			}
        		if("0".equals(status)){
        			// 成功
        			//获取读取的值
        			getMyselfInfo();
        			
        			String form1 = Property.BILL_ID_SELF_JUST;
        			String form2 = Property.BILL_ID_SELF_AGAINST;
        			String form3 = Property.BILL_ID_SELF_FACE;
        			try {
	       				FileUtil.copy(new File(bean.getFrontFace()), new File(form1));
	        			FileUtil.copy(new File(bean.getBackFace()), new File(form2));
	        			AmplificationImage image = new AmplificationImage();
	        			boolean flag = image.zoomPicture(bean.getPhotoPath(), form3, 132, 163, true);
	        			if(!flag){
	        				logger.error("获取身份证头像图片失败");
	        				serverStop("读取身份证信息失败，身份证可能损坏，弯曲", "","");
	        				try {
	                    		closeLed("1");
	        				} catch (Exception e1) {
	        					logger.error("led灯关闭失败"+e1);
	        				}
	        				return;
	        			}
        			} catch (Exception e) {
        				logger.error("身份证拷贝时失败！"+e);
						serverStop("获取身份证信息失败,请联系大堂经量", "","获取身份证信息时异常");
						try {
		            		closeLed("1");
						} catch (Exception e1) {
							logger.error("led灯关闭失败"+e);
						}
						return;
        			}
        			new File(bean.getFrontFace()).delete();
        			new File(bean.getBackFace()).delete();
        			new File(bean.getPhotoPath()).delete();
        			success(map);
        		}else if("3".equals(status)){
        			// 超时
        			logger.error("读取身份证失败:"+bean.getStatusDesc());
        			return;
        		}else{
        			// 其它错误
        			logger.error("读取身份证失败:"+bean.getStatusDesc());
        			serverStop("读取身份证失败", bean.getStatusDesc(),"");
        			try {
                		closeLed("1");
    				} catch (Exception e1) {
    					logger.error("led灯关闭失败"+e1);
    				}
        		}
            }
        };
        thread.start();
	}
	
	/*
	 * 读取本人身份证信息
	 */
	public void getMyselfInfo(){
		accCancelBean.setReadIdNo(bean.getIdCode());//身份证号
		accCancelBean.setReadIdName(bean.getName());//姓名
		accCancelBean.setSex(bean.getSex());//性别
		accCancelBean.setBirthday(bean.getBirthday());//生日
		accCancelBean.setAddress(bean.getAddress());//地址
		accCancelBean.setQfjg(bean.getIssuingUnit());//签发机关
		accCancelBean.setEndDate(bean.getEndDate());//有效结束日期
	}
	
	
	/**
	 * 处理成功
	 */
	private void success(final Map map){
		closeVoice();
		clearTimeText();
		stopTimer(voiceTimer);
		openPanel(new AccCancelOutputIdCardPanel(map));
	}
}


