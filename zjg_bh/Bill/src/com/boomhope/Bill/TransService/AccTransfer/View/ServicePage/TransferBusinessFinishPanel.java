package com.boomhope.Bill.TransService.AccTransfer.View.ServicePage;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.CardConfirm.TransferInputBankCardPassword;
/**
 * 业务完成
 * @author hao
 *
 */
public class TransferBusinessFinishPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransferBusinessFinishPanel.class);
	private static final long serialVersionUID = 1L;
	private PublicAccTransferBean transferMoneyBean;
	private boolean on_off=true;
	/**
	 * 初始化
	 */
	public TransferBusinessFinishPanel(final PublicAccTransferBean transferBean) {
		logger.info("进入业务完成页面");
		
		transferMoneyBean = transferBean;
		
		//设置倒计时
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start();
				
		JLabel lblNewLabel = new JLabel("交易完成！是否继续？");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		lblNewLabel.setBounds(0, 80, 1009, 60);
		this.showJpanel.add(lblNewLabel);
		
		//是，继续交易
		JLabel lblNewLabel_1 = new JLabel(new ImageIcon("pic/true_icon.png"));
		lblNewLabel_1.setBounds(92, 200, 366, 286);
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击继续交易按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				//清空倒计时
				clearTimeText();
				restartTrans();
			}
		});
		this.showJpanel.add(lblNewLabel_1);
		
		//否，退出交易
		JLabel label = new JLabel(new ImageIcon("pic/false_icon.png"));
		label.setBounds(550, 200, 366, 286);
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//清空倒计时
				clearTimeText();
				openPanel(new OutputBankCardPanel());
			}
		});
		this.showJpanel.add(label);
		
	}
	
	public void restartTrans(){
		//需要保留的字段
		String isCardBin = transferMoneyBean.getIsCardBin();//单位还是个人
		String cardNo = transferMoneyBean.getChuZhangCardNo();//卡号
		String resultCheckIdCard = transferMoneyBean.getResultCheckIdCard();//联网核查结果 
		String faceCompareVal = transferMoneyBean.getFaceCompareVal();// 人脸识别结果
		String idCardNo = transferMoneyBean.getIdCardNo();//身份证号
		String idCardName = transferMoneyBean.getIdCardName();//身份证姓名
		String qfjg = transferMoneyBean.getQfjg();//签发机关
		String erCiInfo =transferMoneyBean.getErCiInfo();//二磁信息
		String appAccSeq = transferMoneyBean.getAppAcctSeq();//应用主账户序列号
		String cardPov = transferMoneyBean.getCardPov();//卡片有效期
		String arqc = transferMoneyBean.getArqc();//ARQC
		String termChkValue = transferMoneyBean.getTermChkValue();//终端验证结果
		String issAppData = transferMoneyBean.getIssAppData();//发卡行应用数据
		String arqcSrcData = transferMoneyBean.getArqcSrcData();//ARQC生成数据
		String transCode = transferMoneyBean.getReqMCM001().getTranscode();//上送流水交易代码
		
		//更新交易的笔数 
		String transNo=String.valueOf(Integer.parseInt(transferMoneyBean.getTransNo())+1);
		
		//重新创建交易对象
		transferMoneyBean=null;
		transferMoneyBean=new PublicAccTransferBean();
		
		//设置新的的交易笔数
		transferMoneyBean.setTransNo(transNo);
		
		//赋值保留字段
		transferMoneyBean.setIsCardBin(isCardBin);
		transferMoneyBean.setChuZhangCardNo(cardNo);
		transferMoneyBean.setResultCheckIdCard(resultCheckIdCard);
		transferMoneyBean.setFaceCompareVal(faceCompareVal);
		transferMoneyBean.setIdCardNo(idCardNo);
		transferMoneyBean.setIdCardName(idCardName);
		transferMoneyBean.setQfjg(qfjg);
		transferMoneyBean.setErCiInfo(erCiInfo);
		transferMoneyBean.setAppAcctSeq(appAccSeq);
		transferMoneyBean.setCardPov(cardPov);
		transferMoneyBean.setArqc(arqc);
		transferMoneyBean.setTermChkValue(termChkValue);
		transferMoneyBean.setIssAppData(issAppData);
		transferMoneyBean.setArqcSrcData(arqcSrcData);
		transferMoneyBean.getReqMCM001().setTranscode(transCode);
		transferBean = transferMoneyBean;
		
		//跳转至输入密码页面
		openPanel(new TransferInputBankCardPassword(transferBean));
	}
	
}
