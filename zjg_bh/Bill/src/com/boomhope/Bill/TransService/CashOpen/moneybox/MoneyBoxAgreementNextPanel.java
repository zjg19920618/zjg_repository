package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.TransService.CashOpen.Interface.AccountDepositApi;
import com.boomhope.Bill.TransService.CashOpen.Interface.ProductRateInfo1;
import com.boomhope.Bill.TransService.CashOpen.Interface.SearchRYCDetail;
import com.boomhope.Bill.Util.DateTool;
import com.boomhope.Bill.Util.MoneyUtils;
import com.boomhope.Bill.Util.UtilButton;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.message.account.in.BCK02867ResBean;
import com.boomhope.tms.message.account.in.BCK03512ResBean;
/***
 * 协议存款产品第2页面
 * @author gyw
 *
 */
public class MoneyBoxAgreementNextPanel extends BaseTransPanelNew{

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxAgreementNextPanel.class);
	private boolean on_off=true;//用于控制页面跳转的开关
	
	public MoneyBoxAgreementNextPanel(final PublicCashOpenBean transBean){
		this.cashBean = transBean;
		
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
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
		msg.setHorizontalAlignment(0);
		msg.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(msg);
		// 协议千禧
		UtilButton qxButton=null;
		if("1".equals(transBean.getJijvOrPuhui())){//普惠版
			 qxButton = new UtilButton("pic/newPic/PHQX.png",
						"pic/newPic/PHQX.png");
		}else{
			 qxButton = new UtilButton("pic/newPic/QX.png",
						"pic/newPic/QX.png");
		}
		
		qxButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				qx(transBean);
			}

		});
		qxButton.setSize(200, 300);
//		qxButton.setLocation(127, 174);//三个
		qxButton.setLocation(203, 174);
		qxButton.setIcon(new ImageIcon("pic/newPic/QX.png"));
		this.showJpanel.add(qxButton);


		// 立得存
		UtilButton ldButton=null;
		if("1".equals(transBean.getJijvOrPuhui())){//普惠版
			ldButton = new UtilButton("pic/newPic/PHLDC.png",
					"pic/newPic/PHLDC.png");
		}else{
			ldButton = new UtilButton("pic/newPic/LDC.png",
					"pic/newPic/LDC.png");
		}
		
		ldButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				nextPage4(transBean);
			}

		});
		ldButton.setSize(200, 300);
		ldButton.setLocation(603, 174);
		ldButton.setIcon(new ImageIcon("pic/newPic/LDC.png"));
		this.showJpanel.add(ldButton);
		// 下方圆点
		JLabel points = new JLabel(
				"<HTML><font color='#ffffff'>.</font>          <font color='#412174'>.</font>          "
						);
		points.setBounds(465, 503, 100, 60);
		points.setFont(new Font("微软雅黑", Font.BOLD, 60));
		this.showJpanel.add(points);


		// 上一页
		UtilButton backButton1 = new UtilButton("pic/newPic/left.png",
				"pic/newPic/left.png");
		backButton1.addMouseListener(new MouseAdapter() {
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
		//上一步
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
		backButton1.setBounds(30,265 ,57, 98);
		backButton1.setIcon(new ImageIcon("pic/newPic/left.png"));
		this.showJpanel.add(backButton1);
		
		
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
	 * 如意存+
	 * */
	public void nextPage1(PublicCashOpenBean transBean) {
		
			//查询该用户的所有如意产品
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("CustNo", transBean.getCustNo());
			AccountDepositApi accountDepositApi = new AccountDepositApi();
			
			transBean.getReqMCM001().setReqBefor("03512");
			BCK03512ResBean searchProduct = accountDepositApi.searchRYCDetail(map);
			List<SearchRYCDetail> list = (List<SearchRYCDetail>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
			if (null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode())||"44444".equals(searchProduct.getHeadBean().getResCode())) {
				if(null == searchProduct){
					transBean.getReqMCM001().setIntereturnmsg("查询产品失败：调用03512接口异常。");
				}else{
					transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
				}
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop("请联系大堂经理","利率查询失败:" + searchProduct.getHeadBean().getResMsg(), "");
				return;
			}else {
				transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
				List<SearchRYCDetail> RYCDetail = new ArrayList<SearchRYCDetail>();
				DateTool tool = new DateTool();
				for(SearchRYCDetail  searchRYCDetail : list){
					Double balance = Double.parseDouble(searchRYCDetail.getBalance());
					
					if(!judgeDate(searchRYCDetail.getEndDate(),"yyyy/MM/dd")){
						continue;
					}
					if(balance < 50000.00 ){
						continue;
					}
					if(balance > 500000.00 ){
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
				
				transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES, RYCDetail);
				//校验是否有如意存
				if(RYCDetail.size() == 0){
					transBean.setPromptPages("2");
					clearTimeText();
					openPanel(new MoneyBoxPromptPagesPanel(transBean));
					
				}
			}
		
		clearTimeText();
		openPanel(new MoneyBoxCatalogRYCJFYPanel(transBean));
			
	}
	
	/**
	 * 上一页分页
	 * */
	public void backTrans1(PublicCashOpenBean transBean) {
		clearTimeText();
		openPanel(new MoneyBoxAgreementPanel(transBean));
		
	}
	
	/**
	 * 下一页分页
	 * */
	public void okTrans1(PublicCashOpenBean transBean) {
		clearTimeText();
		openPanel(new MoneyBoxAgreementThirdPanel(transBean));
		
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
	 * 立得存
	 * */
	public void nextPage4(PublicCashOpenBean transBean) {
		clearTimeText();
		openPanel(new MoneyBoxCatalogLDCTPPanel(transBean));
		
	}
	/**
	 * 千禧
	 * */
	public void qx(PublicCashOpenBean transBean) {

		// 查询利率接口
		
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("CUST_NO", transBean.getCustNo());
			map.put("PRODUCT_CODE", "QX");
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
			AccountDepositApi accountDepositApi = new AccountDepositApi();
			
			transBean.getReqMCM001().setReqBefor("02867");
			BCK02867ResBean searchProduct = accountDepositApi
					.searchProduct(map);
			List<ProductRateInfo1> list = (List<ProductRateInfo1>) map
					.get(AccountDepositApi.KEY_PRODUCT_RATES);
			if (null == searchProduct
					|| !"000000".equals(searchProduct.getHeadBean()
							.getResCode()) || list.size() == 0
					|| "44444".equals(searchProduct.getHeadBean().getResCode())) {
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
			List<ProductRateInfo1> list1 = new ArrayList<ProductRateInfo1>();
			for (ProductRateInfo1 productRateInfo1 : list) {
				if (!"QXA".equals(productRateInfo1.getProductCode())) {
					list1.add(productRateInfo1);
				}
			}
			transBean.setKhqxn(0);
			transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES,list1);
			clearTimeText();
			openPanel(new MoneyBoxQXXLPanel(transBean));
			
		
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
		
		transBean.getReqMCM001().setReqBefor("02867");//记录接口请求前流水信息
		AccountDepositApi accountDepositApi = new AccountDepositApi();
		BCK02867ResBean searchProduct = accountDepositApi.searchProduct(map);
		List<ProductRateInfo1> list = (List<ProductRateInfo1>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
		if (null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode()) || list.size() == 0 ||"44444".equals(searchProduct.getHeadBean().getResCode())) {
			//记录接口返回后流水信息
			if(null == searchProduct){
				transBean.getReqMCM001().setIntereturnmsg("查询产品利率失败：调用02867接口异常");
			}else{
				transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
			}
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//查询失败，上送接口流水信息
			serverStop("请联系大堂经理","利率查询失败:" + searchProduct.getHeadBean().getResMsg(), "");
			return;
		} else {
			//记录接口返回后流水信息
			transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
			transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES, list);
		}
		clearTimeText();
		openPanel(new MoneyBoxRYCXLPanel(transBean));
		
	}
}
