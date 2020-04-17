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
 * 私行快线产品第三页面
 * @author gyw
 *
 */
public class MoneyBoxPrivateLineProductsThirdPanel extends BaseTransPanelNew{

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxPrivateLineProductsThirdPanel.class);
	private boolean on_off=true;//开关控制
	public MoneyBoxPrivateLineProductsThirdPanel(final PublicCashOpenBean transBean){
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
		
		// 请选择您要办理的业务
		JLabel msg = new JLabel("请选择您要办理的业务");
		msg.setFont(new Font("微软雅黑", Font.BOLD, 40));
		msg.setForeground(Color.decode("#412174"));
		msg.setBounds(376, 41, 464, 60);
		this.showJpanel.add(msg);
		// 积享存
		UtilButton jxButton = new UtilButton("pic/newPic/JXC.png",
				"pic/newPic/JXC.png");
		jxButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				jx(transBean);

			}

		});
		jxButton.setSize(200, 300);
		jxButton.setLocation(127, 174);
		jxButton.setIcon(new ImageIcon("pic/newPic/JXC.png"));
		this.showJpanel.add(jxButton);

		// 如意存+
		UtilButton rycjiaButton = new UtilButton("pic/newPic/RYC+.png",
				"pic/newPic/RYC+.png");
		rycjiaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				rycj(transBean);

			}

		});
		rycjiaButton.setSize(200, 300);
		rycjiaButton.setLocation(405, 174);
		rycjiaButton.setIcon(new ImageIcon("pic/newPic/RYC+.png"));
		this.showJpanel.add(rycjiaButton);

		// 下方圆点
		JLabel points = new JLabel(
				"<HTML><font color='#ffffff'>.</font>          "
						+ "<font color='#ffffff'>.</font>         <font color='#412174'>.</font></HTML>");
		points.setBounds(454, 503, 100, 60);
		points.setFont(new Font("微软雅黑", Font.BOLD, 60));
		this.showJpanel.add(points);
		// 上一页
		UtilButton backButton = new UtilButton("pic/newPic/left.png",
				"pic/newPic/left.png");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				backTrans1(transBean);
			}

		});
		backButton.setBounds(30,265 ,57, 98);
		backButton.setIcon(new ImageIcon("pic/newPic/left.png"));
		this.showJpanel.add(backButton);

//		//上一步
		JLabel label = new JLabel(new ImageIcon("pic/preStep.png"));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");	
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
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
				logger.info("点击退出按钮");	
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				returnHome();
			}

		});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);
		}
	//千禧
	public void nextPage1(PublicCashOpenBean transBean) {		
		//查询利率接口
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CUST_NO", transBean.getCustNo());
		map.put("PRODUCT_CODE","QX");
		map.put("QRY_TYPE", "1");
		map.put("DEP_AMT","500000");
		map.put("MAX_AMT","");
		map.put("CHL_ID", "3");
		
		transBean.getReqMCM001().setReqBefor("02867");//接口调用前流水信息记录
		
		AccountDepositApi accountDepositApi = new AccountDepositApi();
		BCK02867ResBean searchProduct=accountDepositApi.searchProduct(map);
		List<ProductRateInfo1> list = (List<ProductRateInfo1>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
		if(null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode()) || list.size() == 0|| "44444".equals(searchProduct.getHeadBean().getResCode())){
			//接口调用后流水信息记录
			if(null == searchProduct){
				transBean.getReqMCM001().setIntereturnmsg("查询子产品失败：调用02867接口异常");
			}else{
				transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
			}
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
			serverStop("请联系大堂经理","利率查询失败:"+searchProduct.getHeadBean().getResMsg(), "");
		}
		//接口调用成功后记录流水信息
		transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
		/*List<ProductRateInfo1>  RateInfo1  = new ArrayList<ProductRateInfo1>();
		for(ProductRateInfo1 productRateInfo1:list){
			float startMoney = Float.parseFloat(productRateInfo1.getStartMoney());
			String productCode = productRateInfo1.getProductCode();
			if (startMoney >= 500000.00f && !productCode.equals("QXA")) {
				//list.add(productRateInfo1);
				RateInfo1.add(productRateInfo1);		
			}			
		}*/
		transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES, list);
			
		clearTimeText();
		openPanel(new MoneyBoxSHQXXLpanel(transBean));
				//私行千禧
//				openPanel(GlobalPanelFlag.PRIVATE_CATALOG_QXXLPANEL);
		
	
	}
	//约享存
//	
//	/**
//	 * 幸福1+1
//	 * @author GTH
//	 */
//	public void nextPage3(PublicCashOpenBean transBean) {
//
//		try {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("CUST_NO", transBean.getCustNo());
//			map.put("PRODUCT_CODE", "XF");
//			map.put("QRY_TYPE", "1");
//			map.put("DEP_AMT","500000");//最低起存
//			map.put("MAX_AMT","");//最高起存
//			AccountDepositApi accountDepositApi = new AccountDepositApi();
//			BCK02867ResBean searchProduct = accountDepositApi.searchProduct(map);
//			List<ProductRateInfo> list = (List<ProductRateInfo>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
//			if (null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode())
//					|| list.size() == 0) {
//				serStop("利率查询失败:" + searchProduct.getHeadBean().getResMsg(), "");
//				return;
//			}
//			transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES, list);
//			clearTimeText();
//			openPanel(new MoneyBoxCatalogSHXFYJYXLPanel(transBean));
//			
//		} catch (Exception e) {
//			logger.error("##########ERROR_INTERFACE:" + "SearchProduct" + "#########", e);
//			serStop("errorMsg", "未知异常:"+e.getMessage());
//			return;
//		}
//	
//	}
	//立得存
//	public void nextPage4() {
//
//		clearTimeText();
//				openPanel(GlobalPanelFlag.CATALOG_LDCXL_PANEL);
//			}			
//		});
//	
//	}
	/**
	 * 如意存
	 * @author GTH
	 */
//	public void nextPage5() {
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("CUST_NO", transBean.getCustNo());
//		map.put("PRODUCT_CODE", "RY");
//		map.put("QRY_TYPE", "1");
//		if("1".equals(transBean.getPrivateLine())){
//			map.put("DEP_AMT","500000");
//			map.put("MAX_AMT","");
//			map.put("CHL_ID", "3");
//		}
//		AccountDepositApi accountDepositApi = new AccountDepositApi();
//		BCK02867ResBean searchProduct = accountDepositApi.searchProduct(map);
//		List<ProductRateInfo1> list = (List<ProductRateInfo1>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
//		if (null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode()) || list.size() == 0 || "44444".equals(searchProduct.getHeadBean().getResCode())) {
//			serStop("利率查询失败:" + searchProduct.getHeadBean().getResMsg(), "");
//		} else {
//			transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES, list);
//		}
//		delayTimer.stop();
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				openPanel(GlobalPanelFlag.MONEYBOX_SHRYCXL_PANEL);
//			}
//		});
//	
//	}
	//积享存
//	public void nextPage6() {
//
//		//调用接口
//		try {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("CustNo", transBean.getCustNo());
//			AccountDepositApi accountDepositApi = new AccountDepositApi();
//			BCK03512ResBean searchryc = accountDepositApi.searchRYCDetail(map);
//			List<SearchRYCDetail> list = (List<SearchRYCDetail>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
//			if(null == searchryc || !"000000".equals(searchryc.getHeadBean().getResCode()) || "44444".equals(searchryc.getHeadBean().getResCode())){
//				serStop("利率查询失败:子产品查询，"+searchryc.getHeadBean().getResMsg(), "");
//			}
//			
//			List<SearchRYCDetail> details = new ArrayList<SearchRYCDetail>();
//			DateTool tool = new DateTool();
//			for (SearchRYCDetail searchRYCDetail : list) {
//				
//				if(!tool.judgeDate(searchRYCDetail.getEndDate(), "yyyy/MM/dd")){
//					continue;
//				}
//				//转换存期
//				String depTerm = searchRYCDetail.getDepTerm();
//				String xyck = MoneyUtils.intUppercaseXYCK(depTerm);
//				searchRYCDetail.setDepTerm(xyck);
//				//转换时间
//				Date date =DateTool.stringToUtilDate(searchRYCDetail.getOpenDate(), "yyyy/MM/dd");
//				searchRYCDetail.setOpenDate(DateTool.DateTimeToString(date, "yyyy-MM-dd"));
//
//				Date endDate =DateTool.stringToUtilDate(searchRYCDetail.getEndDate(), "yyyy/MM/dd");
//				searchRYCDetail.setEndDate(DateTool.DateTimeToString(endDate, "yyyyMMdd"));	
//				//transBean.setEndTime(DateTool.DateTimeToString(endDate, "yyyyMMdd"));
//				details.add(searchRYCDetail);
//			}
//			transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES, details);	
//			//校验是否有如意存
//			if(details.size() == 0){
//				transBean.setPromptPages("1");
//				delayTimer.stop();
//				SwingUtilities.invokeLater(new Runnable() {
//					@Override
//					public void run() {
//						openPanel(GlobalPanelFlag.PROMPT_PAGES_PANEL);
//					}
//				});
//			}
//			
//		} catch (Exception e) {
//			logger.error("##########ERROR_INTERFACE:" + "SearchProduct" + "#########", e);
//			serStop("errorMsg", "未知异常:"+e.getMessage());
//		}	
//		delayTimer.stop();
//		SwingUtilities.invokeLater(new Runnable(){
//			@Override
//			public void run() {
//				openPanel(GlobalPanelFlag.CATALOG_JXCRYCXL_PANEL);
//			}			
//		});
//	
//	}
	
	/**
	 * 返回
	 * */
	public void backTrans(PublicCashOpenBean transBean) {
		clearTimeText();
		transBean.setJijvOrPuhui("");//清空普惠标示
		openPanel(new MoneyBoxPageSelectlPanel(transBean));
			
	}
//	/**
//	 * 退卡
//	 * */
//	public void okTrans() {
//		delayTimer.stop();
//		SwingUtilities.invokeLater(new Runnable(){
//			@Override
//			public void run() {
//				openPanel(GlobalPanelFlag.MONEYBOX_TERMINATION_OF_SERVICE);
//			}
//			
//		});
//	}
	
	
	/**
	 * 上一页分页
	 * */
	public void backTrans1(PublicCashOpenBean transBean) {
		clearTimeText();
		openPanel(new MoneyBoxPrivateLineProductsNextPage(transBean));
		
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
	//积享存
		public void jx(PublicCashOpenBean transBean) {

			//调用接口
			
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("CustNo", transBean.getCustNo());
				
				transBean.getReqMCM001().setReqBefor("03512");//接口调用前流水信息记录
				
				AccountDepositApi accountDepositApi = new AccountDepositApi();
				BCK03512ResBean searchryc = accountDepositApi.searchRYCDetail(map);
				List<SearchRYCDetail> list = (List<SearchRYCDetail>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
				if(null == searchryc || !"000000".equals(searchryc.getHeadBean().getResCode()) || "44444".equals(searchryc.getHeadBean().getResCode())){
					//接口调用后流水信息记录
					if(null == searchryc){
						transBean.getReqMCM001().setIntereturnmsg("查询利率失败：调用03512接口异常");
					}else{
						transBean.getReqMCM001().setReqAfter(searchryc.getHeadBean().getResCode(), searchryc.getHeadBean().getResMsg());
					}
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
					serverStop("请联系大堂经理","利率查询失败:子产品查询，"+searchryc.getHeadBean().getResMsg(), "");
					return;
				}
				
				//接口调用成功后记录流水信息
				transBean.getReqMCM001().setReqAfter(searchryc.getHeadBean().getResCode(), searchryc.getHeadBean().getResMsg());
				
				List<SearchRYCDetail> details = new ArrayList<SearchRYCDetail>();
				DateTool tool = new DateTool();
				for (SearchRYCDetail searchRYCDetail : list) {
					
					if(!tool.judgeDate(searchRYCDetail.getEndDate(), "yyyy/MM/dd")){
						continue;
					}
					//转换存期
					String depTerm = searchRYCDetail.getDepTerm();
					String xyck = MoneyUtils.intUppercaseXYCK(depTerm);
					searchRYCDetail.setDepTerm(xyck);
					//转换时间
					Date date =DateTool.stringToUtilDate(searchRYCDetail.getOpenDate(), "yyyy/MM/dd");
					searchRYCDetail.setOpenDate(DateTool.DateTimeToString(date, "yyyy-MM-dd"));

					Date endDate =DateTool.stringToUtilDate(searchRYCDetail.getEndDate(), "yyyy/MM/dd");
					searchRYCDetail.setEndDate(DateTool.DateTimeToString(endDate, "yyyyMMdd"));	
					//transBean.setEndTime(DateTool.DateTimeToString(endDate, "yyyyMMdd"));
					details.add(searchRYCDetail);
				}
				transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES, details);	
				//校验是否有如意存
				if(details.size() == 0){
					transBean.setPromptPages("1");
					clearTimeText();
					openPanel(new MoneyBoxPromptPagesPanel(transBean));
					return;
				}
				
			
			clearTimeText();
			openPanel(new MoneyBoxCatalogJXCXLPanel(transBean));
				
		
		}
		/**
		 * 如意存+
		 * */
		public void rycj(PublicCashOpenBean transBean) {
				//查询该用户的所有如意产品
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("CustNo", transBean.getCustNo());
				
				transBean.getReqMCM001().setReqBefor("03512");//接口调用前流水信息记录
				
				AccountDepositApi accountDepositApi = new AccountDepositApi();
				BCK03512ResBean searchProduct = accountDepositApi.searchRYCDetail(map);
				List<SearchRYCDetail> list = (List<SearchRYCDetail>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
				if (null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode())||"44444".equals(searchProduct.getHeadBean().getResCode())) {
					//接口调用后流水信息记录
					if(null == searchProduct){
						transBean.getReqMCM001().setIntereturnmsg("查询利率失败：调用03512接口异常");
					}else{
						transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
					}
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
					serverStop("请联系大堂经理","利率查询失败:" + searchProduct.getHeadBean().getResMsg(), "");
					return;
				}else {
					//接口调用成功后记录流水信息
					transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
					List<SearchRYCDetail> RYCDetail = new ArrayList<SearchRYCDetail>();
					DateTool tool = new DateTool();
					for(SearchRYCDetail  searchRYCDetail : list){
						float startMoney = Float.parseFloat(searchRYCDetail.getBalance());

						if(startMoney>300000){
						Double balance = Double.parseDouble(searchRYCDetail.getBalance());
						
						if(!judgeDate(searchRYCDetail.getEndDate(),"yyyy/MM/dd")){
							continue;
						}
						if(balance == 50000.00 ){
							continue;
						}
						//转换存期
						String depTerm = searchRYCDetail.getDepTerm();
						String xyck = MoneyUtils.intUppercaseXYCK(depTerm);
						searchRYCDetail.setDepTermStr(xyck);
						//转换时间
						Date date =DateTool.stringToUtilDate(searchRYCDetail.getOpenDate(), "yyyy/MM/dd");
						searchRYCDetail.setOpenDate(DateTool.DateTimeToString(date, "yyyy-MM-dd"));

						Date endDate =DateTool.stringToUtilDate(searchRYCDetail.getEndDate(), "yyyy/MM/dd");
						searchRYCDetail.setEndDate(DateTool.DateTimeToString(endDate, "yyyyMMdd"));	
						//transBean.setEndTime(DateTool.DateTimeToString(endDate, "yyyyMMdd"));
						RYCDetail.add(searchRYCDetail);
					}
					}
					
					transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES, RYCDetail);
					//校验是否有如意存
					if(RYCDetail.size() == 0){
						transBean.setPromptPages("2");
						clearTimeText();
						openPanel(new MoneyBoxPromptPagesPanel(transBean));
						return;
					}
				}
			
			clearTimeText();
			openPanel(new MoneyBoxCatalogRYCJFYPanel(transBean));
			
		}
}
