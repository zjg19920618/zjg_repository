package com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.IdCardMsg;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;

import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.AccTransfer.Action.TransferAction;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.Interface.InterfaceSendMsg;
import com.boomhope.Bill.Util.AmplificationImage;
import com.boomhope.Bill.Util.FileUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.Bill.peripheral.action.IdCard;
import com.boomhope.Bill.peripheral.bean.IdCardReadBean;
/**
 * 请插入身份证
 * @author hk
 *
 */
public class TransferAccInputIdCardPanel extends BaseTransPanelNew{
	Thread thread = null;
	static Logger logger = Logger.getLogger(TransferAccInputIdCardPanel.class);
	IdCardReadBean bean = null;
	final int voiceSecond = 500;
	SourceDataLine line = null;
	JButton button  = null;
	private static final long serialVersionUID = 1L;
	private PublicAccTransferBean transferMoneyBean;
	
	public TransferAccInputIdCardPanel(final PublicAccTransferBean transferBean) {
		logger.info("进行插入身份证步骤：");
		transferMoneyBean = transferBean;
		
		//倒计时打开语音 
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);
            	closeVoice();
            	try {
					openLed("1");
				} catch (Exception e1) {
					logger.error("led灯打开失败"+e);
				}
            	excuteVoice("voice/corresponding.wav");
            	readIdCard(transferMoneyBean);
            }      
        });            
		voiceTimer.start(); 

		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
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
		JLabel titleLabel = new JLabel("请插入本账户对应的身份证");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setBounds(0, 100, GlobalParameter.TRANS_WIDTH-50, 42);
		this.showJpanel.add(titleLabel);
		
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/input_idCard.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(324, 190, 362, 360);
		this.showJpanel.add(billImage);
		
	}
	
	/**
	 * 
	 * 读卡信息
	 */
	public void readIdCard(final PublicAccTransferBean transferMoneyBean){
		thread  = new Thread("readIdCard"){
            @Override
            public void run(){
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
        			//将读取到的身份证信息添加到公共bean中
        			transferMoneyBean.setIdCardNo(bean.getIdCode());
        			transferMoneyBean.setIdCardName(bean.getName());
        			transferMoneyBean.setQfjg(bean.getIssuingUnit());
        			transferMoneyBean.setAddress(bean.getAddress());
        			transferMoneyBean.setSex(bean.getSex());
        			transferMoneyBean.setIdCardtitle(bean.getPhotoPath());
        			transferMoneyBean.setIdCardback(bean.getBackFace());
        			transferMoneyBean.setIdCardface(bean.getFrontFace());
        			transferMoneyBean.setBirthday(bean.getBirthday());
        			transferMoneyBean.setEndDate(bean.getEndDate());
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
        			success(transferMoneyBean);
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
	private void success(final PublicAccTransferBean transferMoneyBean){
		//清空倒计时
		clearTimeText();
		closeVoice();
		stopTimer(voiceTimer);
		try {
    		closeLed("1");
		} catch (Exception e1) {
			logger.error("led灯关闭失败"+e1);
		}
		//调用联网核查接口
		if(checkIdCardInfo()==false){
			try {
        		closeLed("1");
			} catch (Exception e1) {
				logger.error("led灯关闭失败"+e1);
			}
			return;
		}
		//跳转页面
		openPanel(new AccOutputIdCardPanel(transferMoneyBean)); 
	}
	
	/**
	 * 身份联网核查
	 */
	public boolean checkIdCardInfo(){
//		Thread thread = new Thread("执行回单加载线程...") {
//			@Override
//			public void run() {
				try {
					logger.info("调用联网核查接口：");
					transferBean.getReqMCM001().setReqBefor("07670");
					Map result07670 = InterfaceSendMsg.inter07670(transferMoneyBean);
					transferBean.getReqMCM001().setReqAfter((String)result07670.get("resCode"), (String)result07670.get("errMsg"));
					logger.info("返回信息resCode:"+result07670.get("resCode")+"---返回联网核查结果："+transferMoneyBean.getResultCheckIdCard());
					//调用联网核查接口失败
					if("4444".equals(result07670.get("resCode"))){
						logger.error(result07670.get("errMsg"));
						MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
						serverStop("抱歉！联网核查失败,请联系大堂经量", (String)result07670.get("errMsg"),"");
						return false;
					}
					if(!"000000".equals(result07670.get("resCode"))){
						logger.error(result07670.get("errMsg"));
						MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
						serverStop("抱歉！联网核查失败,请联系大堂经量", (String)result07670.get("errMsg"),"");
						return false;
					}
				} catch (Exception e) {
					logger.error("联网核查失败！"+e);
					transferBean.getReqMCM001().setIntereturnmsg("调用07670接口异常");
					MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
					serverStop("抱歉！联网核查失败,请联系大堂经量", "","调用联网核查接口异常");
					return false;
				}
				return true;
//			}
//		};
//		thread.start();
	}	
}


