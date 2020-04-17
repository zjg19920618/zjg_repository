package com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc;

import java.awt.Color;
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
import com.boomhope.Bill.TransService.AccTransfer.TransferUtil.SAXUnionFiled55Utils;
import com.boomhope.Bill.TransService.AccTransfer.TransferUtil.TLV;
import com.boomhope.Bill.TransService.BillChOpen.Bean.PublicBillChangeOpenBean;
import com.boomhope.Bill.peripheral.action.ICBank;
import com.boomhope.Bill.peripheral.bean.ICBankReadBean55;
/**
 * 插入银行卡页面
 * @author hao
 *
 */
public class BillChangeOpenInputBankCardPanel extends BaseTransPanelNew{
	
	static Logger logger = Logger.getLogger(BillChangeOpenInputBankCardPanel.class);
	private static final long serialVersionUID = 1L;
	private Component comp;
	
	/***
	 * 初始化
	 */
	public BillChangeOpenInputBankCardPanel(){
		
		this.comp=this;
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
		/* 倒计时打开语音 */
		voiceTimer = new Timer(BaseTransPanelNew.voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) { 
            	stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	excuteVoice("voice/interposition.wav");
            	getBankInfo();
            	
            }      
        });            
		voiceTimer.start();
		
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请插入您的银行卡");
		titleLabel.setHorizontalAlignment(0);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 60, 1009,60);
		this.showJpanel.add(titleLabel);
		
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/input_bank.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(325,150, 360, 390);
		this.showJpanel.add(billImage);

	}
	
	/**
	 * 读卡操作
	 */
	public void getBankInfo(){
		Thread thread  = new Thread("读取银行卡线程"){
            @Override
            public void run(){
            	try {
            		logger.info("读取银行卡信息");
            		//插入银行卡，将状态值 变更为true
            		ICBank bank = new ICBank();
            		GlobalParameter.CARD_STATUS=true;
        			ICBankReadBean55 read = bank.ICBankRead55();
        			String status = read.getStatus();
        			if("0".equals(status)){
        				logger.info("银行卡读取状态："+status);
        				//查看是否是唐山银行卡
            			if(!CheckAccountCode(read.getIcBankCode())){
            				logger.error("非唐山银行卡");
            				serverStop("非本行银行卡，请插入唐山银行卡", "", "");
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

        				//调用赋值方法
        				payForBean(read,bcOpenBean);
        				
        				//调用检验IC卡信息的接口
        				if(!bcOpenAction.checkICInfo(comp)){
        					logger.info("检验IC卡信息失败");
        					return;
        				}
        				
        				//调用查询黑灰名单接口
        				if(!bcOpenAction.meCheckTelephoneFraud(comp)){
        					logger.info("查询黑灰名单失败");
        					return;
        				}
        				
        				//进入银行卡信息查询
        				openPanel(new BillChangeOpenInputBankCardPasswordPanel());
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
        	    	logger.error("读卡器调用失败"+e);
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
	    				serverStop("抱歉，读卡器读取信息失败，请联系大堂经理","","调用读卡器异常");
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
		logger.info("判断银行卡是否为唐山银行卡");
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
	public void payForBean(ICBankReadBean55 read,final PublicBillChangeOpenBean bcOpenBean){
		logger.info("将读取到的值赋给bean");
		try {
			String data55=read.getData55().substring(6);
			List<TLV> list55 = SAXUnionFiled55Utils.saxUnionField55_2List(data55);
			Map<String,String> map = new HashMap<String, String>();
			for(TLV tlv:list55){
				map.put(tlv.getTag(), tlv.getValue());
				logger.info(tlv);
			}
			
			bcOpenBean.setCardNo(read.getIcBankCode());
			bcOpenBean.setErCiInfo(read.getErCiData());
			bcOpenBean.setAppAcctSeq(read.getCardNo());
			bcOpenBean.setCardPov(read.getCardValidity());
			bcOpenBean.setArqc(map.get("9F26"));
			String termChkValue = map.get("95").substring(0,7)+"800";
			bcOpenBean.setTermChkValue(termChkValue);
			bcOpenBean.setIssAppData(map.get("9F10"));
			
			String srcData = map.get("9F02")+map.get("9F03")+map.get("9F1A")+termChkValue+map.get("5F2A")+map.get("9A") 
					+map.get("9C")+map.get("9F37")+map.get("82")+map.get("9F36")+map.get("9F10").substring(6,14);
			bcOpenBean.setArqcSrcData(srcData);
			
		} catch (Exception e) {
			logger.error("赋值失败"+e);
			serverStop("获取银行卡值失败，请核对卡是否正常后再试", "","获取银行卡55域值信息失败");
		}
	}
}
