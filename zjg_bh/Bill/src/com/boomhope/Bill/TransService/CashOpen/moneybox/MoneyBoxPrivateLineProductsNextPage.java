package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
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
import com.boomhope.Bill.TransService.CashOpen.Interface.ProductRateInfo1;
import com.boomhope.Bill.Util.UtilButton;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.message.account.in.BCK02867ResBean;
/***
 * 私行协议存款产品第2页面
 * @author gyw
 *
 */
public class MoneyBoxPrivateLineProductsNextPage extends BaseTransPanelNew{

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxPrivateLineProductsNextPage.class);
	
	private boolean on_off=true;//开关控制
	public MoneyBoxPrivateLineProductsNextPage(final PublicCashOpenBean transBean){
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
		msg.setBounds(376, 41, 405, 60);
		this.showJpanel.add(msg);
		// 私行千禧
		UtilButton qxButton = new UtilButton("pic/newPic/SHQX.png",
				"pic/newPic/SHQX.png");
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
		qxButton.setLocation(127, 174);
		qxButton.setIcon(new ImageIcon("pic/newPic/SHQX.png"));
		this.showJpanel.add(qxButton);

		// 如意存
		UtilButton ryButton = new UtilButton("pic/newPic/RYC.png",
				"pic/newPic/RYC.png");
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
		ryButton.setLocation(405, 174);
		ryButton.setIcon(new ImageIcon("pic/newPic/RYC.png"));
		this.showJpanel.add(ryButton);

		// 立得存
		UtilButton ldButton = new UtilButton("pic/newPic/LDC.png",
				"pic/newPic/LDC.png");
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
		ldButton.setLocation(683, 174);
		ldButton.setIcon(new ImageIcon("pic/newPic/LDC.png"));
		this.showJpanel.add(ldButton);
		// 下方圆点
		JLabel points = new JLabel(
				"<HTML><font color='#ffffff'>.</font>          <font color='#412174'>.</font>          "
						+ "<font color='#ffffff'>.</font></HTML>");
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

		// 下一页
		UtilButton backButton1 = new UtilButton("pic/newPic/right.png", "pic/newPic/right.png");
		backButton1.addMouseListener(new MouseAdapter() {
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
		backButton1.setBounds(922,265 ,57, 98);
		backButton1.setIcon(new ImageIcon("pic/newPic/right.png"));
		this.showJpanel.add(backButton1);
		//上一步
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
	
	

	/**
	 * 如意存
	 * @author GTH
	 */
	public void nextPage5(PublicCashOpenBean transBean) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CUST_NO", transBean.getCustNo());
		map.put("PRODUCT_CODE", "RY");
		map.put("QRY_TYPE", "1");
		if("1".equals(transBean.getPrivateLine())){
			map.put("DEP_AMT","500000");
			map.put("MAX_AMT","");
			map.put("CHL_ID", "3");
		}
		
		transBean.getReqMCM001().setReqBefor("02867");//接口调用前流水信息记录
		
		AccountDepositApi accountDepositApi = new AccountDepositApi();
		BCK02867ResBean searchProduct = accountDepositApi.searchProduct(map);
		List<ProductRateInfo1> list = (List<ProductRateInfo1>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
		if (null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode()) || list.size() == 0 || "44444".equals(searchProduct.getHeadBean().getResCode())) {
			//接口调用后流水信息记录
			if(null == searchProduct){
				transBean.getReqMCM001().setIntereturnmsg("查询子产品失败：调用02867接口异常");
			}else{
				transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
			}
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
			serverStop("请联系大堂经理","利率查询失败:" + searchProduct.getHeadBean().getResMsg(), "");
			return;
		} else {
			//接口调用成功后记录流水信息
			transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
			transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES, list);
		}
		clearTimeText();
		openPanel(new MoneyBoxSHRYCXLPanel(transBean));
		
	}
	
	
	/**
	 * 上一页分页
	 * */
	public void backTrans1(PublicCashOpenBean transBean) {
		clearTimeText();
		openPanel(new MoneyBoxPrivateLineProductsNextPage(transBean));
			
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
	//安享存
	public void nextPage2(PublicCashOpenBean transBean) {
		clearTimeText();
		openPanel(new MoneyBoxCatalogSHAXCXLPanel(transBean));
		
	}
	//千禧
	public void qx(PublicCashOpenBean transBean) {		
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
			return;
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
		openPanel(new MoneyBoxQXXLPanel(transBean));
		//私行千禧
//		openPanel(GlobalPanelFlag.PRIVATE_CATALOG_QXXLPANEL);
		
	
	
	}
	//立得存
	public void nextPage4(PublicCashOpenBean transBean) {

		clearTimeText();
		openPanel(new MoneyBoxCatalogSHLDCTPPanel(transBean));
		
	
	}
	/**
	 * 下一页分页
	 * */
	public void okTrans1(PublicCashOpenBean transBean) {
		clearTimeText();
		openPanel(new MoneyBoxPrivateLineProductsThirdPanel(transBean));
			
	}
}
