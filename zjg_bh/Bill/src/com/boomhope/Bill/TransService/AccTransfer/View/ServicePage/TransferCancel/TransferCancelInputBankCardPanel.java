package com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.TransferCancel;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.AccTransfer.Action.TransferAction;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.TransferUtil.SAXUnionFiled55Utils;
import com.boomhope.Bill.TransService.AccTransfer.TransferUtil.TLV;
import com.boomhope.Bill.peripheral.action.ICBank;
import com.boomhope.Bill.peripheral.bean.ICBankReadBean55;
/**
 * 插入银行卡页面
 * @author hao 
 *
 */
public class TransferCancelInputBankCardPanel extends BaseTransPanelNew{
	
	static Logger logger = Logger.getLogger(TransferCancelInputBankCardPanel.class);
	private static final long serialVersionUID = 1L;
	private ICBank bank = new ICBank();
	private PublicAccTransferBean bean;
	/***
	 * 初始化
	 */
	public TransferCancelInputBankCardPanel(final Map params,final PublicAccTransferBean transferBean){
		logger.info("进入插入银行卡页面");
		//将当前页面传入流程控制进行操作
		final Component comp=this;
		bean = transferBean;
		
		//插入银行卡页面，不能进行其它子业务页面跳转，关闭跳转开关
		GlobalParameter.OFF_ON=false;
		
		if(delayTimer!=null){
			clearTimeText();
		}
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
		titleLabel.setBounds(0, 100, GlobalParameter.TRANS_WIDTH-50, 40);
		this.showJpanel.add(titleLabel);
		
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/input_bank.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(325, 210, 360, 390);
		this.showJpanel.add(billImage);
		
		getBankInfo(comp,params);
		
	}
	
	
	/**
	 * 错误提示
	 */
	private void errDialog(String errMsg,final Component comp,final Map params){
		try {
			closeVoice();
			openMistakeDialog(errMsg);
			mistakeDialog.ExitButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mistakeDialog.disposeDialog();
					getBankInfo(comp, params);
				}
			});
		} catch (Exception e) {
			logger.error("错误提示方法异常："+e);
		}
		
	}
	
	/**
	 * 读卡操作
	 */
	public void getBankInfo(final Component comp,final Map params){
		Thread thread  = new Thread("读取银行卡线程"){
            @Override
            public void run(){
            	try {
            		logger.info("读取银行卡信息");
            		//插入银行卡，将状态值 变更为true
            		GlobalParameter.CARD_STATUS=true;
            		
        			ICBankReadBean55 read = bank.ICBankRead55();
        			String status = read.getStatus();
        			if("0".equals(status)){
        				logger.info("银行卡读取状态："+status);
        				//查看是否是唐山银行卡
            			if(!CheckAccountCode(read.getIcBankCode())){
            				logger.error("非唐山银行卡");
            				Thread.sleep(1000);
            				closeICBank();
            				Thread.sleep(1000);
            				//退卡
            				quitBankCard();
            				Thread.sleep(1000);
            				errDialog("您插入的卡不是唐山银行卡", comp, params);
            				return;
            			}
            			
            			clearTimeText();//清空倒计时
            			stopTimer(voiceTimer);//关闭语音
            	    	closeVoice();
        				try {
        					closeLed("2");
        				} catch (Exception e1) {
        					logger.error("led灯打开失败"+e1);
        				}

        				// 成功
        				String bankCode = read.getIcBankCode();
        				params.put("cardNo", bankCode);
        				
        				//调用赋值方法
        				payForBean(read);
        				
        				//进入输入密码页
        				openPanel(new TransferCancelInputBankCardPassword(bean));
        			}else{
        				if("3".equals(status)){
        					//超时
        					//未插入银行卡，将状态值 变更为false
                    		GlobalParameter.CARD_STATUS=false;
        					logger.info("插入银行卡超时"+status);
        					return;
        				}else{
        					stopTimer(delayTimer);//关闭定时器
        					clearTimeText();//清空倒计时
        					closeVoice();
        					try {
        						closeLed("2");
        					} catch (Exception e1) {
        						logger.error("led灯打开失败"+e1);
        					}
        					logger.error("读卡器读卡失败状态："+status);
        					String statusDesc = read.getStatusDesc();//读卡失败状态描述
        					logger.error("读卡器读卡失败信息："+statusDesc);
        					Thread.sleep(1000);
        					// 其它
        					closeICBank();
        					//退出错误页
        					serverStop("读卡器读卡失败,，请联系大堂经理","读卡器读卡"+statusDesc,"");
        					
        				}
        			}
        		} catch (Exception e) {
        			stopTimer(delayTimer);//关闭定时器
        	    	clearTimeText();//清空倒计时
        	    	closeVoice();
        	    	logger.error("读卡器调用失败");
    				try {
    					closeLed("2");
    				} catch (Exception e1) {
    					logger.error("led灯打开失败"+e);
    				}

    				try {
						Thread.sleep(1000);
						// 其它
	    				closeICBank();
	    				//退出错误页
	    				serverStop("抱歉，读卡器读取信息失败，请联系大堂经理","","读卡器异常");
					} catch (Exception e1) {
						logger.error(e1);
					}
    				
        		}
            }
        };
        thread.start();
	}
	
	/**
	 * 检验银行卡是否为唐山银行的卡
	 * hao
	 * @return
	 */
	public boolean CheckAccountCode(String bankCode){
		try {
			if(bankCode==null){
				return false;         
			}else if(bankCode.startsWith("622167")||bankCode.startsWith("623193") ||bankCode.startsWith("62326558")){
				return true ;
			}else{
				return false;
			}
		} catch (Exception e) {
			logger.error("检验银行卡异常："+e);
			return false;
		}
	}
	
	/**
	 * 银行卡信息赋值
	 */
	public void payForBean(ICBankReadBean55 read){
		logger.info("将读取到的值赋给bean");
		try {
			String data55=read.getData55().substring(6);
			List<TLV> list55 = SAXUnionFiled55Utils.saxUnionField55_2List(data55);
			Map<String,String> map = new HashMap<String, String>();
			for(TLV tlv:list55){
				map.put(tlv.getTag(), tlv.getValue());
				logger.info(tlv);
			}
			
			bean.setChuZhangCardNo(read.getIcBankCode());
			bean.setErCiInfo(read.getErCiData());
			bean.setAppAcctSeq(read.getCardNo());
			bean.setCardPov(read.getCardValidity());
			bean.setArqc(map.get("9F26"));
			String termChkValue = map.get("95").substring(0,7)+"800";
			bean.setTermChkValue(termChkValue);
			bean.setIssAppData(map.get("9F10"));
			
			String srcData = map.get("9F02")+map.get("9F03")+map.get("9F1A")+termChkValue+map.get("5F2A")+map.get("9A") 
					+map.get("9C")+map.get("9F37")+map.get("82")+map.get("9F36")+map.get("9F10").substring(6,14);
			bean.setArqcSrcData(srcData);
			
		} catch (Exception e) {
			logger.error("赋值失败"+e);
			serverStop("获取银行卡信息失败，请核对银行卡是否正常", "","无法获取银行卡55域值信息");
		}
	}
	
}
