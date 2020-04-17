package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.boomhope.Bill.Comm.CDJPrintBill;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.AccZYDRulePanel;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.Bill.peripheral.action.MachineLed;
import com.boomhope.Bill.peripheral.bean.LedStateBean;

/**
 * 存单开户成功页面
 * @author gyw
 *
 */
public class MoneyBoxSuccessDepPanel extends BaseTransPanelNew{
	

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxSuccessDepPanel.class);
	
	private JLabel billImage;//动画
	private JLabel promptLabel;//存入成功提示
	private JLabel svrNoLabel;//流水号
	private JLabel tangDSvrLabel;//唐豆兑付流水号
	private JLabel label;//唐豆提示
	private JLabel labe2;//唐豆提示
	private JLabel labe3;//唐豆提示
	
	private AccZYDRulePanel zydrp = null;//增益豆规则
	private JLabel zydrComfirm;//增益豆规则显示确认按钮
	private JLabel tdTotalCount;//增益豆赠送数量
	private JLabel tdinfoCount;//唐豆明细
	private JLabel rlueLabel;//规则提示
	private JLabel rlueButton;//规则按钮
	private JLabel labelTitle;//扣回规则标题
	private int y=180;//各个组件位置高度
	
	
	public MoneyBoxSuccessDepPanel(final PublicCashOpenBean transBean){
		this.cashBean = transBean;
		java.text.DecimalFormat  df1= new java.text.DecimalFormat("0.0"); 
		//java.text.DecimalFormat  df2= new java.text.DecimalFormat("0.00"); 
//		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);
				openLed(transBean);
				excuteVoice("voice/successdep.wav");

			}
		});
		voiceTimer.start();
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* 倒计时结束退出交易 */
				clearTimeText();
				closeLed(transBean);
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		delayTimer.start();
		
		/* 加载凭证动画 */
		billImage = new JLabel("");   
		billImage.setIcon(new ImageIcon("pic/accomplish.png"));
		billImage.setIconTextGap(6);
		billImage.setBounds(385, 120, 50, 50);
		this.showJpanel.add(billImage);
		
		/* 提示信息 */
		promptLabel = new JLabel("存入成功");
		promptLabel.setHorizontalAlignment(JLabel.CENTER);
		promptLabel.setForeground(Color.decode("#412174"));
		promptLabel.setFont(new Font("微软雅黑", Font.PLAIN, 36));
		promptLabel.setBounds(10, 121, 1059, 49);
		this.showJpanel.add(promptLabel);	
		
		if(("0010".equals(transBean.getProductCode()) || transBean.getProductCode().startsWith("XF")) ){
			if(transBean.isTangDouReturn() != false && transBean.getTangDouExchangeAmt()!=null && transBean.getTangDouExchangeAmt()!=""){
			
				label = new JLabel("本次存款所得价值");
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setFont(new Font("微软雅黑", Font.PLAIN, 30));
				label.setBounds(87, y+=50, 676, 30);
				this.showJpanel.add(label);
				labe2 = new JLabel(transBean.getTangDouExchangeAmt()+"元");
				labe2.setForeground(Color.RED);
				labe2.setHorizontalAlignment(JLabel.CENTER);
				labe2.setFont(new Font("微软雅黑", Font.PLAIN, 30));
				labe2.setBounds(169, y, 880, 30);
				this.showJpanel.add(labe2);							
				labe3 = new JLabel("唐豆");
				labe3.setHorizontalAlignment(JLabel.CENTER);
				labe3.setFont(new Font("微软雅黑", Font.PLAIN, 30));
				labe3.setBounds(272, y, 880, 30);
				this.showJpanel.add(labe3);
			}else if("0".equals(transBean.getImportMap().get("TdChaXun"))){//唐豆查询失败
				label = new JLabel(transBean.getErrmsg());
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setFont(new Font("微软雅黑", Font.PLAIN, 28));
				label.setBounds(102, y+=50, 880, 30);
				this.showJpanel.add(label);
			}else if("0".equals(transBean.getImportMap().get("TdChongZhi"))){//唐豆充值失败
				label = new JLabel(transBean.getErrmsg());
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setFont(new Font("微软雅黑", Font.PLAIN, 28));
				label.setBounds(102, y+=50, 880, 30);
				this.showJpanel.add(label);
			}
		}
		
		//若有唐豆，则显示唐豆相关信息
		if(transBean.getTdTotalCount()!=null && !"".equals(transBean.getTdTotalCount())){
			if(Double.valueOf(transBean.getTdTotalCount())==0){
				logger.info("唐豆数量为0");
			}else{
				
				//加载唐豆规则
				zydrp = new AccZYDRulePanel(showJpanel,Property.acc_zyd_rules_path);
				zydrp.getJsp().setVisible(false);
				
				tdTotalCount = new JLabel("<HTML><font color='#333333'>您购买此产品共获赠</font><font color='#ff0000'>"
						//+ transBean.getTdTotalCount()
						+ df1.format(Double.valueOf(transBean.getTdTotalCount()))
						+ "</font><font color='#333333'>" + "个唐豆,"
						+ "</font></HTML>");
				tdTotalCount.setHorizontalAlignment(SwingConstants.CENTER);
				tdTotalCount.setFont(new Font("微软雅黑", Font.PLAIN, 30));
				tdTotalCount.setForeground(Color.decode("#333333"));
				tdTotalCount.setBounds(0, y+=50, 1009, 30);
				this.showJpanel.add(tdTotalCount);
				
				StringBuffer tdStr =new StringBuffer("含");
				if(transBean.getInterestCount()!=null && !"".equals(transBean.getInterestCount()) && Double.valueOf(transBean.getInterestCount())>0){
					//tdStr.append("幸运豆"+transBean.getInterestCount().trim()+"元、");
					tdStr.append("幸运豆"+df1.format(Double.valueOf(transBean.getInterestCount()))+"元、");
				}
				if(transBean.getZydCount()!=null && !"".equals(transBean.getZydCount()) && Double.valueOf(transBean.getZydCount())>0){
					//tdStr.append("增益豆"+transBean.getZydCount().trim()+"元、");
					tdStr.append("增益豆"+df1.format(Double.valueOf(transBean.getZydCount()))+"元、");
				}
				if(transBean.getXfdCount()!=null && !"".equals(transBean.getXfdCount()) && Double.valueOf(transBean.getXfdCount())>0){
					//tdStr.append("消费豆"+transBean.getXfdCount()+"个、");
					tdStr.append("消费豆"+df1.format(Double.valueOf(transBean.getXfdCount()))+"个、");
				}
				tdStr.deleteCharAt(tdStr.length()-1);
				tdStr.append(",");
				
				tdinfoCount =new JLabel(tdStr.toString());
				tdinfoCount.setBounds(0, y+=50, 1009, 30);
				tdinfoCount.setFont(new Font("微软雅黑",Font.PLAIN,30));
				tdinfoCount.setHorizontalAlignment(SwingUtilities.CENTER);
				this.showJpanel.add(tdinfoCount);
				
				labelTitle = new JLabel("");
				labelTitle.setBounds((showJpanel.getWidth()-250)/2, 20, 250, 40);
				labelTitle.setFont(new Font("微软雅黑",Font.BOLD,30));
				labelTitle.setHorizontalAlignment(SwingUtilities.CENTER);
				labelTitle.setVisible(false);
				this.showJpanel.add(labelTitle);
				
				rlueLabel =new JLabel("若该笔存款提前支取将扣回唐豆，具体标准见  ");
				rlueLabel.setBounds(0, y+=50, 690, 30);
				rlueLabel.setFont(new Font("微软雅黑",Font.PLAIN,30));
				rlueLabel.setHorizontalAlignment(SwingUtilities.RIGHT);
				this.showJpanel.add(rlueLabel);
				
				rlueButton = new JLabel("  唐豆扣回规则(链接)");
				rlueButton.setBounds(690, y, 300, 30);
				rlueButton.setFont(new Font("微软雅黑",Font.BOLD,30));
				rlueButton.setHorizontalAlignment(SwingUtilities.LEFT);
				rlueButton.setForeground(Color.red);
				rlueButton.setBackground(Color.BLUE);
				this.showJpanel.add(rlueButton);
				
				rlueButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						logger.info("点击扣回规则按钮");
						zydrp.getJsp().setVisible(true);
						showRule();
					};
				});
				
				/* 确认 */
				zydrComfirm = new JLabel(new ImageIcon("pic/affirm.png"));
				zydrComfirm.setBounds((showJpanel.getWidth()-200)/2, showJpanel.getHeight()-70, 200, 50);
				zydrComfirm.setVisible(false);
				this.showJpanel.add(zydrComfirm);
				zydrComfirm.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						logger.info("点击规则确认按钮");
						zydrp.getJsp().setVisible(false);
						notShowRule();
					}
				});
			}
		}

		/*开户流水号*/
		svrNoLabel = new JLabel("开户流水号："+transBean.getSvrJrnlNo());
		svrNoLabel.setBounds(0, y+=50, 1059, 30);
		svrNoLabel.setHorizontalAlignment(SwingUtilities.CENTER);
		svrNoLabel.setFont(new Font("微软雅黑",Font.BOLD,20));
		this.showJpanel.add(svrNoLabel);
		
		/*唐豆兑付流水号*/
		if(transBean.getTangDSvrJrnlNo()!=null && !"".equals(transBean.getTangDSvrJrnlNo())){
			tangDSvrLabel = new JLabel("唐豆兑付流水号："+transBean.getTangDSvrJrnlNo());
			tangDSvrLabel.setBounds(0, y+=50, 1059, 30);
			tangDSvrLabel.setHorizontalAlignment(SwingUtilities.CENTER);
			tangDSvrLabel.setFont(new Font("微软雅黑",Font.BOLD,20));
			this.showJpanel.add(tangDSvrLabel);
		}
		
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");	
				GlobalParameter.OFF_ON=true;
				returnHome();
			}

		});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);
		
		
		//打印存单
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				CDJPrintBill cdjPrintBill = new CDJPrintBill();
				boolean printBills = cdjPrintBill.PrintBills(transBean);
				if(printBills){
					transBean.getReqMCM001().setTransresult("0");
				}else{
					transBean.getReqMCM001().setTransresult("2");
				}
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//业务办理完成，上送流水信息
				logger.info(printBills);
			}
		}).start();
	}
	/**打开led灯*/
	public void openLed(PublicCashOpenBean transBean){
		LedStateBean openLed = new LedStateBean();
		try {
			openLed = MachineLed.openLed("3");
			logger.info("打印机Led灯打开返回值："+openLed);
		} catch (Exception e) {
			logger.error("打印机led灯通讯异常");
			transBean.setErrmsg("未找到打印机Led灯设备");
			serverStop("请联系大堂经理","", "打印机led灯通讯异常");
			return;
		}
		logger.info("打印机Led灯打开："+openLed.getStatus());
	}
	
	/**关闭led灯*/
	public void closeLed(PublicCashOpenBean transBean){
		LedStateBean closeLed = new LedStateBean();
		try {
			closeLed = MachineLed.closeLed("3");
			logger.info("打印机Led灯关闭返回值："+closeLed);
		} catch (Exception e) {
			logger.error("打印机led灯通讯异常");
			transBean.setErrmsg("未找到打印机Led灯设备");
			serverStop("请联系大堂经理","", "打印机led灯通讯异常");
			return;
		}
		logger.info("打印机Led灯关闭："+closeLed.getStatus());
	}

	/**
	 * 继续交易
	 * */
	public void backTrans(PublicCashOpenBean transBean) {
		clearTimeText();
		transBean.setJijvOrPuhui("");//清空普惠标示
		openPanel(new MoneyBoxPageSelectlPanel(transBean));
			
	}
	
	//点击扣回规则显示规则明细
	public void showRule(){
		zydrComfirm.setVisible(true);
		labelTitle.setVisible(true);
		billImage.setVisible(false);
		promptLabel.setVisible(false);
		svrNoLabel.setVisible(false);
		tdTotalCount.setVisible(false);
		rlueLabel.setVisible(false);
		rlueButton.setVisible(false);
		tdinfoCount.setVisible(false);
		if(tangDSvrLabel!=null){
			tangDSvrLabel.setVisible(false);
		}
		if(label!=null){
			label.setVisible(false);
		}
		if(labe2!=null){
			labe2.setVisible(false);
		}
		if(labe3!=null){
			labe3.setVisible(false);
		}
	}
	
	//点击规则确认按钮退出规则明细
	public void notShowRule(){
		zydrComfirm.setVisible(false);
		labelTitle.setVisible(false);
		billImage.setVisible(true);
		promptLabel.setVisible(true);
		svrNoLabel.setVisible(true);
		tdTotalCount.setVisible(true);
		rlueLabel.setVisible(true);
		rlueButton.setVisible(true);
		tdinfoCount.setVisible(true);
		if(tangDSvrLabel!=null){
			tangDSvrLabel.setVisible(true);
		}
		if(label!=null){
			label.setVisible(true);
		}
		if(labe2!=null){
			labe2.setVisible(true);
		}
		if(labe3!=null){
			labe3.setVisible(true);
		}
	}
	
}
