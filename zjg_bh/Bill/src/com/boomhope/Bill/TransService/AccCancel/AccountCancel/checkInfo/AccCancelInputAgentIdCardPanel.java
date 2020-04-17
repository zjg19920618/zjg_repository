package com.boomhope.Bill.TransService.AccCancel.AccountCancel.checkInfo;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;

import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
 * @author gyw
 *
 */
public class AccCancelInputAgentIdCardPanel extends BaseTransPanelNew{
	Thread thread = null;
	static Logger logger = Logger.getLogger(AccCancelInputAgentIdCardPanel.class);
	IdCardReadBean bean = null;
	SourceDataLine line = null;
	JButton button  = null;
	private static final long serialVersionUID = 1L;
	public AccCancelInputAgentIdCardPanel() {
		logger.info("进入插入身份证页面");

		//倒计时打开语音 
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
            	try {
					openLed("1");
				} catch (Exception e1) {
					logger.error("led灯打开失败"+e);
				}
            	excuteVoice("voice/agentid.wav");
            	readIdCard();
            }      
        });            
		voiceTimer.start(); 
		if(delayTimer!=null){
			clearTimeText();
		}
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	logger.info("插入身份证页面倒计时结束 ");
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();//清空倒计时
            	try {
            		closeLed("1");
				} catch (Exception e1) {
					logger.error("led灯关闭失败"+e);
				}
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        }); 
		delayTimer.start(); 
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请插入代理人对应的身份证");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));
		titleLabel.setBounds(10, 129, GlobalParameter.TRANS_WIDTH-50, 40);
		this.showJpanel.add(titleLabel);
		
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/input_idCard.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(324, 236, 362, 320);
		this.showJpanel.add(billImage);
		
	}
	
	/**
	 * 读卡信息
	 */
	public void readIdCard(){
		logger.info("进入插入身份证方法");
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
        			payInfoAgent();
        			
        			String form1 = Property.BILL_ID_AGENT_JUST;
					String form2 = Property.BILL_ID_AGENT_AGAINST;
					String form3 = Property.BILL_ID_AGENT_FACE;
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
	
	/**
	 * 赋值给代理人字段
	 */
	public void payInfoAgent(){
		accCancelBean.setReadAgentIdNo(bean.getIdCode());//身份证号
		accCancelBean.setReadAgentIdName(bean.getName());//姓名
		accCancelBean.setAgentsex(bean.getSex());//性别
		accCancelBean.setAgentBirthday(bean.getBirthday());//生日
		accCancelBean.setAgentaddress(bean.getAddress());//地址
		accCancelBean.setAgentqfjg(bean.getIssuingUnit());//签发机关
		accCancelBean.setAgentEndDate(bean.getEndDate());//有效结束日期
	}
	
	/**
	 * 处理成功
	 */
	private void success(final Map map){
		logger.info("处理成功，进入下一步");
		closeVoice();
		clearTimeText();
		stopTimer(voiceTimer);
		openPanel(new AccCancelOutputAgentIdCardPanel(map));
	}
}


