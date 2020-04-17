package com.boomhope.Bill.TransService.AccTransfer.View.ServicePage;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor.GOLD;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.AccTransfer.Action.TransferAction;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.Common.SlipPrintPt;
/**
 * 打印提示
 * @author hao
 *
 */
public class TransferBusinessSuccessPanel extends BaseTransPanelNew {
	
	
	static Logger logger = Logger.getLogger(TransferBusinessSuccessPanel.class);
	private static final long serialVersionUID = 1L;
	/**
	 * 初始化
	 */
	public TransferBusinessSuccessPanel(final PublicAccTransferBean transferBean) {
		logger.info("进入打印提示页面");
		
		//设置倒计时
		this.showTimeText(delaySecondShortTime );
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start();
		
		JLabel lblNewLabel = new JLabel("汇款成功！");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 230, 1009, 40);
		this.showJpanel.add(lblNewLabel);
		
		JLabel svrLabel = new JLabel("转账流水号："+transferBean.getSvrJrnlNo());
		svrLabel.setFont(new Font("微软雅黑",Font.PLAIN,30));
		svrLabel.setHorizontalAlignment(SwingUtilities.CENTER);
		svrLabel.setBounds(0, 300, 1009, 40);
		this.showJpanel.add(svrLabel);
		
		JLabel lblNewLabel1 = new JLabel("正在为您打印业务受理凭条，请稍候...");
		lblNewLabel1.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		lblNewLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel1.setBounds(0, 360, 1009, 40);
		this.showJpanel.add(lblNewLabel1);
	
		//打印凭条
		successPrintBill(transferBean);
		
		
	}
	/**
	 * 打印汇款凭条
	 */
	public void successPrintBill(final PublicAccTransferBean transferBean){
		//打印凭条
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					openProssDialog();
					SlipPrintPt print = new SlipPrintPt(); 
					if(!print.print(transferBean)){
						clearTimeText();
						closeDialog(prossDialog);
						serverStop(transferBean.getBillMsg(), "","");
						return;
					}
					closeDialog(prossDialog);
					Thread.sleep(3000);
				} catch (Exception e) {
					logger.error("调用凭条打印失败："+e);
					closeDialog(prossDialog);
					return;
				}
				//主要业务已经完成，可以向其它子业务跳转
				GlobalParameter.OFF_ON=true;
				clearTimeText();
            	openPanel(new TransferBusinessFinishPanel(transferBean));
				return;
			}
		}).start();
	}
}
