package com.boomhope.Bill.TransService.CashOpen.moneybox;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.TransService.CashOpen.Interface.AccountDepositApi;
import com.boomhope.Bill.TransService.CashOpen.Interface.ProductRateInfo1;
import com.boomhope.Bill.Util.UtilImages1;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.message.account.in.BCK02867ResBean;

/***
 * 立得存图片页面
 * @author gyw
 *
 */
public class MoneyBoxCatalogSHLDCTPPanel extends BaseTransPanelNew{

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxCatalogSHLDCTPPanel.class);
	
	private boolean on_off=true;//开关控制
	public MoneyBoxCatalogSHLDCTPPanel(final PublicCashOpenBean transBean){
		this.cashBean= transBean;
		
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
		
		//判断是否为私行快线
		if(transBean.getPrivateLine().equals("1")){
			//立得存1
			UtilImages1 image = new UtilImages1("pic/ldcA.png");
			image.setSize(250, 210);
			image.setLocation(204, 257);
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
//					openPanel(new MoneyBoxCatalogSHLDCXLZXTXPanel(transBean));
					
				}
			});
			this.showJpanel.add(image);
			
			//立得存2
			UtilImages1 image1 = new UtilImages1("pic/LDCB.png");
			image1.setSize(250, 210);
			image1.setLocation(606, 257);
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
		}else{
			//立得存1
			UtilImages1 image = new UtilImages1("pic/LDC1.png");
			image.setSize(250, 210);
			image.setLocation(204, 257);
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
				}
			});
			this.showJpanel.add(image);
			
			//立得存2
			UtilImages1 image1 = new UtilImages1("pic/LDC2.png");
			image1.setSize(250, 210);
			image1.setLocation(606, 257);
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

	}
	
	
	
	/**
	 * 返回
	 * */
	public void backTrans(PublicCashOpenBean transBean) {
//		delayTimer.stop();
//		if("1".equals(transBean.getPrivateLine())){
			clearTimeText();
			openPanel(new MoneyBoxPrivateLineProductsNextPage(transBean));
			
//		}
//		if("2".equals(transBean.getPrivateLine())){
//			SwingUtilities.invokeLater(new Runnable(){
//				@Override
//				public void run() {
//					openPanel(GlobalPanelFlag.MONEYBOX_AGREEMENT_PANEL);
//				}
//				
//			});
//		}
	}
	
	/**
	 * 确认
	 * */
	public void okTrans() {
		delayTimer.stop();
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
//				openPanel(GlobalPanelFlag.INPUT_DEPINFO_PANEL);
			}
			
		});
	}
	
	/**
	 * 立得存1
	 * */
	public void nextPage1(PublicCashOpenBean transBean) {
		//调用查询子产品信息
		try{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CUST_NO", transBean.getCustNo());
		map.put("PRODUCT_CODE","LZ");
		map.put("QRY_TYPE", "1");
		if("2".equals(transBean.getPrivateLine())){
			map.put("DEP_AMT","0");//最低起存
			map.put("MAX_AMT","500000");//最高起存
		}
		
		transBean.getReqMCM001().setReqBefor("02867");//接口调用前流水信息记录
		
		AccountDepositApi accountDepositApi = new AccountDepositApi();
		BCK02867ResBean searchProduct=accountDepositApi.searchProduct(map);
		List<ProductRateInfo1> list = (List<ProductRateInfo1>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
		if(null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode()) || list.size() == 0 ||"44444".equals(searchProduct.getHeadBean().getResCode())){
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
		transBean.setLdcType("0");
		}catch(Exception e) {
			logger.error("##########ERROR_INTERFACE:" + "SearchProduct" + "#########", e);
			transBean.getReqMCM001().setIntereturnmsg("查询子产品失败：调用02867接口异常");
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
			serverStop("请联系大堂经理","errorMsg", "未知异常:"+e.getMessage());
			return;
		}	
		clearTimeText();
		openPanel(new MoneyBoxCatalogLDCXLZXTXPanel(transBean));
			
	}
	
	/**
	 * 立得存2
	 * */
	public void nextPage2(PublicCashOpenBean transBean) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("CUST_NO", transBean.getCustNo());
			map.put("PRODUCT_CODE","LT");
			map.put("QRY_TYPE", "1");
			if("2".equals(transBean.getPrivateLine())){
				map.put("DEP_AMT","0");//最低起存
				map.put("MAX_AMT","500000");//最高起存
			}
			transBean.getReqMCM001().setReqBefor("02867");//接口调用前流水信息记录
			
			AccountDepositApi accountDepositApi = new AccountDepositApi();
			BCK02867ResBean searchProduct=accountDepositApi.searchProduct(map);
			List<ProductRateInfo1> list = (List<ProductRateInfo1>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
			if(null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode()) || "44444".equals(searchProduct.getHeadBean().getResCode())){
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
			transBean.setLdcType("1");
			
		clearTimeText();
		openPanel(new MoneyBoxCatalogLDCXLZXTXPanel(transBean));
		
	}
	
}
