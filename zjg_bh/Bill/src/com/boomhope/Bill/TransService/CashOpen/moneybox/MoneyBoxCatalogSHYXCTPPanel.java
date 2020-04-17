package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.TransService.CashOpen.Interface.AccountDepositApi;
import com.boomhope.Bill.TransService.CashOpen.Interface.ProductRateInfo1;
import com.boomhope.Bill.Util.UtilImages1;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.message.account.in.BCK02867ResBean;

/***
 * 私行约享存图片页面
 * 
 * @author gyw
 *
 */
public class MoneyBoxCatalogSHYXCTPPanel extends BaseTransPanelNew{


	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxCatalogSHYXCTPPanel.class);
	private boolean on_off=true;//开关控制
	public MoneyBoxCatalogSHYXCTPPanel(final PublicCashOpenBean transBean) {
		this.cashBean = transBean;

		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				on_off=false;
				/* 倒计时结束退出交易 */
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		delayTimer.start();
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);
				excuteVoice();

			}
		});
		voiceTimer.start();
		//判断是否为私行快线
		if(transBean.getPrivateLine().equals("1")){
			// 约享存1
						UtilImages1 image = new UtilImages1("pic/shyxca.png");
						image.setSize(250, 210);
						image.setLocation(78, 257);
						image.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								if(!on_off){
									logger.info("开关当前状态"+on_off);
									return;
								}
								logger.info("开关当前状态"+on_off);
								on_off=false;
								/* 处理上一页 */
								nextPage1(transBean);
//								SwingUtilities.invokeLater(new Runnable(){
//								@Override
//								public void run() {
//									openPanel(GlobalPanelFlag.MONEYBOX_CATALOG_SHYXCFY_PANEL);
//								}
//								
//							});
							}
						});
						this.showJpanel.add(image);

						// 约享存2
						UtilImages1 image1 = new UtilImages1("pic/shyxcb.png");
						image1.setSize(250, 210);
						image1.setLocation(407, 257);
						image1.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								if(!on_off){
									logger.info("开关当前状态"+on_off);
									return;
								}
								logger.info("开关当前状态"+on_off);
								on_off=false;
								/* 处理上一页 */
								nextPage2(transBean);
							}
						});
						this.showJpanel.add(image1);
						
						//约享存3
						UtilImages1 image2 = new UtilImages1("pic/shyxcc.png");
						image2.setSize(250, 210);
						image2.setLocation(730, 257);
						image2.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								if(!on_off){
									logger.info("开关当前状态"+on_off);
									return;
								}
								logger.info("开关当前状态"+on_off);
								on_off=false;
								/* 处理上一页 */
								nextPage3(transBean);
							}
						});
						this.showJpanel.add(image2);
		}else{
			// 约享存1
			UtilImages1 image = new UtilImages1("pic/YXC1.png");
			image.setSize(250, 210);
			image.setLocation(78, 257);
			image.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					/* 处理上一页 */
					nextPage1(transBean);
//					SwingUtilities.invokeLater(new Runnable(){
//						@Override
//						public void run() {
//							openPanel(GlobalPanelFlag.MONEYBOX_CATALOG_SHYXCFY_PANEL);
//						}
//						
//					});
				}
			});
			this.showJpanel.add(image);

			// 约享存2
			UtilImages1 image1 = new UtilImages1("pic/YXC2.png");
			image1.setSize(250, 210);
			image1.setLocation(407, 257);
			image1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					/* 处理上一页 */
					nextPage2(transBean);
				}
			});
			this.showJpanel.add(image1);
			
			//约享存3
			UtilImages1 image2 = new UtilImages1("pic/YXC3.png");
			image2.setSize(250, 210);
			image2.setLocation(730, 257);
			image2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					/* 处理上一页 */
					nextPage3(transBean);
				}
			});
			this.showJpanel.add(image2);
		}
		
		//上一步
		JLabel label = new JLabel(new ImageIcon("pic/preStep.png"));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				logger.info("点击上一步按钮");				
				backTrans(transBean);
			}

		});
		label.setBounds(1075, 770, 150, 50);
		this.add(label);
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				logger.info("点击退出按钮");				
				returnHome();
			}

		});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);
//		/* 下一页 */
//		JButton okButton = new JButton(new ImageIcon("pic/tk.png"));
//
//		okButton.setHorizontalTextPosition(SwingConstants.CENTER);
//		okButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
//		okButton.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
//		okButton.setContentAreaFilled(false);// 设置图片填满按钮所在的区域
//		// okButton.setMargin(new Insets(0, 0, 0, 0));//设置按钮边框和标签文字之间的距离
//		// okButton.setIcon(new ImageIcon("pic/inputinfo_1.png"));
//		okButton.setFocusPainted(true);// 设置这个按钮是不是获得焦点
//		okButton.setBorderPainted(false);// 设置是否绘制边框
//		okButton.setBorder(null);// 设置边框
//		okButton.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				//退卡
//				delayTimer.stop();
//				transBean.setErrmsg("");
//			}
//
//		});
//		okButton.setSize(200, 50);
//		okButton.setLocation(780, 638);
//		this.add(okButton);
	}
	/**
	 * 执行语音
	 */
	private void excuteVoice(){
		try{
			FileInputStream fileau = new FileInputStream("voice/select.wav");  
	        as = new AudioStream(fileau);  
	        AudioPlayer.player.start(as); 
		}catch(Exception e){
			logger.error(e);
		}
	}
	
	/**
	 * 返回
	 * */
	public void backTrans(PublicCashOpenBean transBean) {
	
//		if("1".equals(transBean.getPrivateLine())){
		clearTimeText();
		openPanel(new MoneyBoxPrivateLineProductsPanel(transBean));
			
//		}
//		if("2".equals(transBean.getPrivateLine())){
//			SwingUtilities.invokeLater(new Runnable(){
//				@Override
//				public void run() {
//					openPanel(GlobalPanelFlag.MONEYBOX_PRIVATE_LINE_PRODUCTS);
//				}
//				
//			});
		}
		
//	}
	
	
	/**
	 * 约享存1
	 * */
	public void nextPage1(PublicCashOpenBean transBean) {
		//调接口查询所有约享存A
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CUST_NO", transBean.getCustNo());
		map.put("PRODUCT_CODE","YA");
		map.put("QRY_TYPE", "1");
		if("2".equals(transBean.getPrivateLine())){
			map.put("DEP_AMT","0");//最低起存
			map.put("MAX_AMT","500000");//最高起存
		}
		if("1".equals(transBean.getPrivateLine())){
			map.put("CHL_ID", "3");
			map.put("DEP_AMT", "500000");
			map.put("MAX_AMT", "");
		}
		
		transBean.getReqMCM001().setReqBefor("02867");//接口调用前流水信息记录
		
		AccountDepositApi accountDepositApi = new AccountDepositApi();
		BCK02867ResBean searchProduct=accountDepositApi.searchProduct(map);
		List<ProductRateInfo1> list = (List<ProductRateInfo1>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
		if(null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode()) || list.size() == 0){
			//接口调用后流水信息记录
			if(null == searchProduct){
				transBean.getReqMCM001().setIntereturnmsg("查询子产品失败：调用02867接口异常");
			}else{
				transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
			}
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
			serverStop("请联系大堂经理","利率查询失败:子产品查询，"+searchProduct.getHeadBean().getResMsg(), "");
			return;
		}
		//接口调用成功后记录流水信息
		transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
		transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES, list);
		
		if("1".equals(transBean.getPrivateLine())){//私行快线
			clearTimeText();
			openPanel(new MoneyBoxCatalogSHYXCFYPanel(transBean));
			
		}
		if("2".equals(transBean.getPrivateLine())){//协议存款
			clearTimeText();
			openPanel(new MoneyBoxCatalogYXCFYPanel(transBean));
			
		}
		
	}
	
	/**
	 *约享存2
	 * */
	public void nextPage2(PublicCashOpenBean transBean) {
		//调接口查询所有约享存B
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("CUST_NO", transBean.getCustNo());
				map.put("PRODUCT_CODE","YB");
				map.put("QRY_TYPE", "1");
				if("2".equals(transBean.getPrivateLine())){
					map.put("DEP_AMT","0");//最低起存
					map.put("MAX_AMT","500000");//最高起存
				}
				
				if("1".equals(transBean.getPrivateLine())){
					map.put("CHL_ID", "3");
					map.put("DEP_AMT", "500000");
					map.put("MAX_AMT", "");
				}
				
				transBean.getReqMCM001().setReqBefor("02867");//接口调用前流水信息记录
				
				AccountDepositApi accountDepositApi = new AccountDepositApi();
				BCK02867ResBean searchProduct=accountDepositApi.searchProduct(map);
				List<ProductRateInfo1> list = (List<ProductRateInfo1>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
				if(null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode()) || list.size() == 0){
					//接口调用后流水信息记录
					if(null == searchProduct){
						transBean.getReqMCM001().setIntereturnmsg("查询子产品失败：调用02867接口异常");
					}else{
						transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
					}
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
					serverStop("请联系大堂经理","利率查询失败:子产品查询，"+searchProduct.getHeadBean().getResMsg(), "");
					return;
				}
				//接口调用成功后记录流水信息
				transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
				
				transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES, list);
				
				if("1".equals(transBean.getPrivateLine())){//私行快线
					clearTimeText();
					openPanel(new MoneyBoxCatalogSHYXCFYPanel(transBean));
						
				}
				if("2".equals(transBean.getPrivateLine())){//协议存款
					clearTimeText();
					openPanel(new MoneyBoxCatalogYXCFYPanel(transBean));
					
				}
	}
	/**
	 * 约享存3
	 * */
	public void nextPage3(PublicCashOpenBean transBean) {
		//调接口查询所有约享存C
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("CUST_NO", transBean.getCustNo());
				map.put("PRODUCT_CODE","YC");
				map.put("QRY_TYPE", "1");
				if("2".equals(transBean.getPrivateLine())){
					map.put("DEP_AMT","0");//最低起存
					map.put("MAX_AMT","500000");//最高起存
				}
				if("1".equals(transBean.getPrivateLine())){
					map.put("CHL_ID", "3");
					map.put("DEP_AMT", "500000");
					map.put("MAX_AMT", "");
				}
				
				transBean.getReqMCM001().setReqBefor("02867");//接口调用前流水信息记录
				
				AccountDepositApi accountDepositApi = new AccountDepositApi();
				BCK02867ResBean searchProduct=accountDepositApi.searchProduct(map);
				List<ProductRateInfo1> list = (List<ProductRateInfo1>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
				if(null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode()) || list.size() == 0){
					//接口调用后流水信息记录
					if(null == searchProduct){
						transBean.getReqMCM001().setIntereturnmsg("查询子产品失败：调用02867接口异常");
					}else{
						transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
					}
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
					serverStop("请联系大堂经理","利率查询失败:子产品查询，"+searchProduct.getHeadBean().getResMsg(), "");
					return;
				}
				
				//接口调用成功后记录流水信息
				transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
				
				transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES, list);
				
				if("1".equals(transBean.getPrivateLine())){//私行快线
					clearTimeText();
					openPanel(new MoneyBoxCatalogSHYXCFYPanel(transBean));
					
				}
				if("2".equals(transBean.getPrivateLine())){//协议存款
					clearTimeText();
					openPanel(new MoneyBoxCatalogYXCFYPanel(transBean));
					
				}
	}
	



}
