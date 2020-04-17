package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.Util.AmplificationImage;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.FileUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.peripheral.action.IdCard;
import com.boomhope.Bill.peripheral.bean.IdCardReadBean;

/**
 * 请插入身份证
 * 
 * @author gyw
 *
 */
public class AccInputAgentIdCardPanel extends BaseTransPanelNew {
	Thread thread = null;
	static Logger logger = Logger.getLogger(AccInputAgentIdCardPanel.class);
	IdCardReadBean bean = null;
	SourceDataLine line = null;
	JButton button = null;
	private static final long serialVersionUID = 1L;

	public AccInputAgentIdCardPanel(final Map map) {
		logger.info("进入插入代理人身份证页面");
		
		// 倒计时打开语音
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
				try {
					openLed("1");
				} catch (Exception e1) {
					logger.error("led灯打开失败" + e);
				}
				excuteVoice("voice/agentid.wav");
				readIdCard(map);
			}
		});
		voiceTimer.start();

		/* 显示时间倒计时 */
		if(delayTimer!=null){
			clearTimeText();
		}
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000,
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						logger.info("插入代理人身份证页面倒计时结束 ");
						/* 倒计时结束退出交易 */
						clearTimeText();//清空倒计时
						try {
							closeLed("1");
						} catch (Exception e1) {
							logger.error("led灯关闭失败" + e);
						}
						serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！", "","");
					}
				});
		delayTimer.start();
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请插入代理人的身份证");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setBounds(0, 129, 1009, 40);
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
	public void readIdCard(final Map map) {
		logger.info("进入读身份证方法");
		thread = new Thread("readIdCard") {
			@Override
			public void run() {
				IdCard card = new IdCard();
        		// 打开机器
        		try {
        			bean = card.IdCardRead("2");//打开读取身份证的读卡器
        		} catch (Exception e) {
        			logger.error("读卡器打开异常");
        			serverStop("身份证读卡器调用失败,请联系大堂经量","","身份证打开读卡器异常");
        			try {
                		closeLed("1");
    				} catch (Exception e1) {
    					logger.error("led灯关闭失败"+e);
    				}
        			return;
        		}
        		
        		String status = bean.getStatus();//读卡器状态
        		logger.info("status=" + status);            	
        		if(status == null){//状态为空，则说明未成功起动
    				delayTimer.stop();
    				logger.error("读卡器打开异常");
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
        			map.put("cardInfo", bean);
        			map.put("isMyself", "N");//判断本人进行身份证与银行卡帐户比对
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
	 * 处理成功
	 */
	private void success(final Map map) {
		closeVoice();
		clearTimeText();
		stopTimer(voiceTimer);
		map.put("transCode", "0009");
		openPanel(new AccOutputIdCardPanel(map));
		
	}

}
