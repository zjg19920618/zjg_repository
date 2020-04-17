package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
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
import com.boomhope.Bill.Util.UtilButton;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.message.account.in.BCK02867ResBean;
/***
 * 业务选择页(整存整取 协议存款 私行快线)
 * @author gyw
 *
 */
public class MoneyBoxPageSelectlPanel  extends BaseTransPanelNew{

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxPageSelectlPanel.class);
	JLabel titleLabel1;
	private boolean on_off=true;//开关控制
	public MoneyBoxPageSelectlPanel(final PublicCashOpenBean transBean){
		this.cashBean = transBean;
		/* 显示时间倒计时 */
		showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	on_off=false;
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
		
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请选择您要办理的业务");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(titleLabel);
		
		/* 标题信息 */
		titleLabel1 = new JLabel("<html>您的存款金额不满足该系列产品购买条件，"
				+ "请联系大堂经理</html>");
		titleLabel1.setHorizontalAlignment(JLabel.CENTER);
		titleLabel1.setFont(new Font("隶书", Font.BOLD, 20));
		titleLabel1.setForeground(Color.red);
		titleLabel1.setBounds(0, 545, 1009, 80);
		this.showJpanel.add(titleLabel1);
		titleLabel1.setVisible(false);

		/* 整存整取*/
		UtilButton openButton = new UtilButton("pic/wholeupdated.png","pic/wholeupdated.png");
		openButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 整存整取*/
				doOpenTrans(transBean);
			}

		});
		openButton.setSize(227,337);
		openButton.setLocation(151, 174);//三个同时存在
		openButton.setIcon(new ImageIcon("pic/wholeupdated.png"));
		this.showJpanel.add(openButton);
		
		
		
		/* 协议存款*/
		UtilButton backButton = new UtilButton("pic/agreementupdated.png","pic/agreementupdated.png");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 协议存款*/
				int[] nums={10000,20000,30000,40000,50000,150000,300000,500000,1000000,5000000,10000000};
				boolean b=true;
				for (int i : nums) {
					if(transBean.getMoney()==i){
						b=false;
					}
				}
				if(b){
					titleLabel1.setVisible(true);
					on_off=true;
					return;
				}
				for (int i : nums) {
					if(i>=500000){
						transBean.setJijvOrPuhui("2");//私行标识
					}
				}
				doBackTrans(transBean);

			}
		});
		backButton.setSize(227,337);
		backButton.setLocation(528, 174);//三个同时存在
		backButton.setIcon(new ImageIcon("pic/agreementupdated.png"));
		this.showJpanel.add(backButton);
		
		
//		/* 普惠产品*/(暂时去除该产品)
//		UtilButton puhui = new UtilButton("pic/PHCP.png","pic/PHCP.png");
//		puhui.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				/* 协议存款*/
//				int[] nums={10000,20000,30000,40000,50000,150000,300000,500000,1000000,5000000,10000000};
//				boolean b=true;
//				for (int i : nums) {
//					if(transBean.getMoney()==i){
//						b=false;
//					}
//				}
//				if(b){
//					titleLabel1.setVisible(true);
//					return;
//				}
//				transBean.setJijvOrPuhui("1");//普惠标识
//				doBackTrans(transBean);
//
//			}
//		});
//		
//		
//		puhui.setSize(227,337);
//		puhui.setLocation(700, 174);
//		puhui.setIcon(new ImageIcon("pic/PHCP.png"));
//		this.showJpanel.add(puhui);
		
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
	
	/* 整存整取*/
	private void doOpenTrans(PublicCashOpenBean transBean){	
	
		Map<String, Object> map = new HashMap<String, Object>();
		//查询利率接口
		map.put("CUST_NO", transBean.getCustNo());
		map.put("PRODUCT_CODE", "0010");
		map.put("QRY_TYPE", "4");
		
		transBean.getReqMCM001().setTranscode("11021");//设置交易代码
		transBean.getReqMCM001().setReqBefor("02867");//接口调用前流水信息记录
		
		AccountDepositApi accountDepositApi = new AccountDepositApi();
		BCK02867ResBean searchProduct = accountDepositApi.searchProduct(map);
		List<ProductRateInfo> list = (List<ProductRateInfo>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
		if(null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode()) || "44444".equals(searchProduct.getHeadBean().getResCode()) || list.size() == 0){
			//接口调用后流水信息记录
			if(null == searchProduct){
				transBean.getReqMCM001().setIntereturnmsg("查询子产品失败：调用02867接口异常");
			}else{
				transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
			}
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
			
			serverStop("请联系大堂经理","利率查询失败:"+searchProduct.getHeadBean().getResMsg(), "");
			return;
		}else{
			
			//接口调用成功后记录流水信息
			transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
			
			transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES, list);
			transBean.setProductCode("0010");
			transBean.setProductName("整存整取");
			clearTimeText();
			openPanel(new MoneyBoxInPutdepInfoPanel(transBean));
			
		}
		
	}
	/* 协议存款（普惠产品）*/
	private void doBackTrans(PublicCashOpenBean transBean){
		
		clearTimeText();
		transBean.setPrivateLine("2");
		transBean.getReqMCM001().setTranscode("11022");//设置交易代码
		openPanel(new MoneyBoxAgreementPanel(transBean));
		
	}
	/* 私行快线*/
	private void doBackTrans11(PublicCashOpenBean transBean){
		
		clearTimeText();
		transBean.setPrivateLine("1");
		transBean.getReqMCM001().setTranscode("11023");//设置交易代码
		openPanel(new MoneyBoxPrivateLineProductsPanel(transBean));
		
	}
	
	
	



}
