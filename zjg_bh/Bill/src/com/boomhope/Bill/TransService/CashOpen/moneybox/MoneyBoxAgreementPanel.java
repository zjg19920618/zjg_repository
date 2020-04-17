package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.TransService.CashOpen.Interface.AccountDepositApi;
import com.boomhope.Bill.TransService.CashOpen.Interface.ProductRateInfo;
import com.boomhope.Bill.TransService.CashOpen.Interface.ProductRateInfo1;
import com.boomhope.Bill.TransService.CashOpen.Interface.SearchRYCDetail;
import com.boomhope.Bill.Util.DateTool;
import com.boomhope.Bill.Util.MoneyUtils;
import com.boomhope.Bill.Util.UtilButton;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.message.account.in.BCK02867ResBean;
import com.boomhope.tms.message.account.in.BCK03512ResBean;

/***
 * 协议存款产品页面
 * @author gyw
 *
 */
public class MoneyBoxAgreementPanel extends BaseTransPanelNew{
	

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxAgreementPanel.class);
	
	private boolean on_off=true;//用于控制页面跳转的开关
	
	public MoneyBoxAgreementPanel(final PublicCashOpenBean transBean){
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
            	excuteVoice("voice/select.wav");
            	
            }      
        });            
		voiceTimer.start(); 
		//请选择您要办理的业务
		JLabel msg=new JLabel("请选择您要办理的业务");
		msg.setFont(new Font("微软雅黑", Font.BOLD, 40));
		msg.setForeground(Color.decode("#412174"));
		msg.setHorizontalAlignment(0);
		msg.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(msg);
		//安享存（机具版）
//		UtilButton anButton = new UtilButton("pic/newPic/AXC.png","pic/newPic/AXC.png");
//		anButton.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				ax(transBean);
//			}
//			
//		});
//		anButton.setSize(200, 300);
//		anButton.setLocation(127,174);
//		anButton.setIcon(new ImageIcon("pic/newPic/AXC.png"));
//		this.showJpanel.add(anButton);
		// 如意存
		UtilButton ryButton=null;
		if("1".equals(transBean.getJijvOrPuhui())){//普惠版
			
			 ryButton = new UtilButton("pic/newPic/PHRYC.png",
						"pic/newPic/PHRYC.png");
		}else{
			
			 ryButton = new UtilButton("pic/newPic/RYC.png",
						"pic/newPic/RYC.png");
		}
		
		ryButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				nextPage5(transBean);
			}

		});
		ryButton.setSize(200, 300);
		ryButton.setLocation(124, 174);
		ryButton.setIcon(new ImageIcon("pic/newPic/RYC.png"));
		this.showJpanel.add(ryButton);
		//约享存
		UtilButton yxButton=null;
		if("1".equals(transBean.getJijvOrPuhui())){//普惠版
			
			yxButton = new UtilButton("pic/newPic/PHYXC.png","pic/newPic/PHYXC.png");
		}else{
			
			yxButton = new UtilButton("pic/newPic/YXC.png","pic/newPic/YXC.png");
		}
		yxButton = new UtilButton("pic/newPic/YXC.png","pic/newPic/YXC.png");
		yxButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				nextPage3(transBean);
			}

		});
		yxButton.setSize(200, 300);
		yxButton.setLocation(405, 174);//安享存存在时
		yxButton.setIcon(new ImageIcon("pic/newPic/YXC.png"));
		this.showJpanel.add(yxButton);
		
		//幸福1+1
		UtilButton xfButton=null;
		if("1".equals(transBean.getJijvOrPuhui())){//普惠版
			xfButton = new UtilButton("pic/newPic/PHXF.png","pic/newPic/PHXF.png");
		}else{
			xfButton = new UtilButton("pic/newPic/XF.png","pic/newPic/XF.png");
		}
		
		xfButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				nextPage2(transBean);
			}

		});
		xfButton.setSize(200, 300);
		xfButton.setLocation(683,174);//安享存存在时
		xfButton.setIcon(new ImageIcon("pic/newPic/XF.png"));
		this.showJpanel.add(xfButton);
		
		//下方圆点
		JLabel points=new JLabel("<HTML><font color='#412174'>.</font>         <font color='#ffffff'>.</font>          "
				);
		points.setBounds(465, 503, 100, 60);
		points.setFont(new Font("微软雅黑", Font.BOLD, 60));
		this.showJpanel.add(points);
		// 下一页
		UtilButton backButton = new UtilButton("pic/newPic/right.png", "pic/newPic/right.png");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				okTrans1(transBean);
			}

		});
		backButton.setBounds(922,265 ,57, 98);
		backButton.setIcon(new ImageIcon("pic/newPic/right.png"));
		this.showJpanel.add(backButton);
		
		JLabel label = new JLabel(new ImageIcon("pic/preStep.png"));
		label.setBounds(1075, 770, 150, 50);
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
		this.add(label);
		
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
		clearTimeText();
		transBean.setJijvOrPuhui("");//清空普惠标示
		openPanel(new MoneyBoxPageSelectlPanel(transBean));
		
	}
	
	
	/**
	 * 1+1
	 * */
	public void nextPage2(PublicCashOpenBean transBean) {
		
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("CUST_NO", transBean.getCustNo());
			map.put("PRODUCT_CODE", "XF");
			map.put("QRY_TYPE", "1");
			if(transBean.getMoney()<500000){
				map.put("DEP_AMT", "0");// 最低起存
				map.put("MAX_AMT", "500000");// 最高起存
				
			}else{
				map.put("DEP_AMT", "500000");// 最低起存
				map.put("MAX_AMT", "");// 最高起存
				map.put("CHL_ID", "3");// 私行版:渠道模块标识为3
			}
			if("1".equals(transBean.getJijvOrPuhui())){
				map.put("CHL_ID", "5");// 普惠版:渠道模块标识为5
			}
			
			transBean.getReqMCM001().setReqBefor("02867");
			AccountDepositApi accountDepositApi = new AccountDepositApi();
			BCK02867ResBean searchProduct = accountDepositApi
					.searchProduct(map);
			List<ProductRateInfo> list = (List<ProductRateInfo>) map
					.get(AccountDepositApi.KEY_PRODUCT_RATES);
			if (null == searchProduct
					|| !"000000".equals(searchProduct.getHeadBean()
							.getResCode()) || list.size() == 0) {
				if(null == searchProduct){
					transBean.getReqMCM001().setIntereturnmsg("查询产品利率失败：调用02867接口异常");
				}else{
					transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
				}
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop("请联系大堂经理","利率查询失败:" + searchProduct.getHeadBean().getResMsg(), "");
				return;
			}
			transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
			transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES,
					list);
			clearTimeText();
			openPanel(new MoneyBoxCatalogXFYJYXLPanel(transBean));
			
		
	}
	//安享存
	public void ax(PublicCashOpenBean transBean) {
		//调用查询子产品信息
		
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("CUST_NO", transBean.getCustNo());
			map.put("PRODUCT_CODE","JJ");
			map.put("QRY_TYPE", "1");
			map.put("DEP_AMT","0");//最低起存
			map.put("MAX_AMT","500000");//最高起存
			transBean.getReqMCM001().setReqBefor("02867");
			AccountDepositApi accountDepositApi = new AccountDepositApi();
			BCK02867ResBean searchProduct=accountDepositApi.searchProduct(map);
			List<ProductRateInfo1> list = (List<ProductRateInfo1>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
			if(null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode()) || list.size() == 0 ||"44444".equals(searchProduct.getHeadBean().getResCode())){
				if(null == searchProduct){
					transBean.getReqMCM001().setIntereturnmsg("查询产品利率失败：调用02867接口异常");
				}else{
					transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
				}
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop("请联系大堂经理","利率查询失败:子产品查询，"+searchProduct.getHeadBean().getResMsg(), "");
				return;
			}
			transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
			transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES, list);
			clearTimeText();
			openPanel(new MoneyBoxCatalogAXCXLPanel(transBean));
		
	}
	/**
	 * 约享存
	 * */
	public void nextPage3(PublicCashOpenBean transBean) {
		clearTimeText();
		openPanel(new MoneyBoxCatalogYXCTPPanel(transBean));
		
	}
	
	
	/**
	 * 如意存
	 * */
	public void nextPage5(PublicCashOpenBean transBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CUST_NO", transBean.getCustNo());
		map.put("PRODUCT_CODE", "RY");
		map.put("QRY_TYPE", "1");
		if(transBean.getMoney()<500000){
			map.put("DEP_AMT", "0");// 最低起存
			map.put("MAX_AMT", "500000");// 最高起存
			
		}else{
			map.put("DEP_AMT", "500000");// 最低起存
			map.put("MAX_AMT", "");// 最高起存
			map.put("CHL_ID", "3");// 私行版:渠道模块标识为3
		}
		if("1".equals(transBean.getJijvOrPuhui())){
			map.put("CHL_ID", "5");// 普惠版:渠道模块标识为5
		}
		transBean.getReqMCM001().setReqBefor("02867");
		AccountDepositApi accountDepositApi = new AccountDepositApi();
		BCK02867ResBean searchProduct = accountDepositApi.searchProduct(map);
		List<ProductRateInfo1> list = (List<ProductRateInfo1>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
		if (null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode()) || list.size() == 0 ||"44444".equals(searchProduct.getHeadBean().getResCode())) {
			if(null == searchProduct){
				transBean.getReqMCM001().setIntereturnmsg("查询产品利率失败：调用02867接口异常");
			}else{
				transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
			}
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
			serverStop("请联系大堂经理","利率查询失败:" + searchProduct.getHeadBean().getResMsg(), "");
			return;
		} else {
			transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES, list);
		}
		transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
		transBean.setKhryn(0);
		clearTimeText();
		openPanel(new MoneyBoxRYCXLPanel(transBean));
			
	}
	
	
	
	
	/**
	 * 判断是否大于3个月
	 * @param endDate 到期日期
	 * @param strFormat 日期格式
	 * @return
	 */
	private boolean judgeDate(String endDate,String strFormat){
		try {
			Date enday = stringToUtilDate(endDate, strFormat);
			Date today=Calendar.getInstance().getTime();
			today=nMonthsAfterOneDate(today, 3);
			if(enday.getTime()> today.getTime()){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	/**
	 * 将指定格式的日期/时间字符串转换成Date格式
	 * 
	 * @param strDate
	 *            String类型，日期字符
	 * @param strFormat
	 *            String类型，格式
	 * @return Date类型
	 */
	public static Date stringToUtilDate(String strDate, String strFormat) {
		try {
			if (strDate == null || strDate.equals("")) {
				return null;
			} else {
				SimpleDateFormat sf = new SimpleDateFormat(strFormat,
						Locale.getDefault());
				return sf.parse(strDate);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	// 当前日期加减n个月后的日期，返回Date
	private Date nMonthsAfterOneDate(Date basicDate, int n) {
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(basicDate);
		rightNow.add(Calendar.MONTH, +n);
		return rightNow.getTime();
	}
	
	
	
	
	/**
	 * 下一页分页
	 * */
	public void okTrans1(PublicCashOpenBean transBean) {
		clearTimeText();
		openPanel(new MoneyBoxAgreementNextPanel(transBean));
		
	}
	

}
