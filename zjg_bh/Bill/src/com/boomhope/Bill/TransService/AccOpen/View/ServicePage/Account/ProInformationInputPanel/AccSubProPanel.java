package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.Bean.AccPublicBean;
import com.boomhope.Bill.TransService.AccOpen.Bean.ProductRateInfo1;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.PrivateLine.AccPrivateLinePanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProtocolDeposit.AccProtocolDepPanel;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTransFlow;

/***
 * 协议或私行系列产品页面
 * @author zjg
 *
 */
public class AccSubProPanel extends BaseTransPanelNew{
	
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccSubProPanel.class);
	
	
	JLabel lblNewLabel_1 ;
	JLabel lblNewLabel_3;
	JLabel lblNewLabel_5;
	JLabel lblNewLabel_7;
	
	String background =""; //产品背景图
	private int nowPage; 
	List<ProductRateInfo1> list;
	List<ProductRateInfo1> list1;
	private AccPublicBean accBean;
	private Map<Object,Object> parames;
	Component comp =null;
	private boolean on_off=true;
	
	public AccSubProPanel(Map<Object,Object> parames) {
		comp = this;
		this.parames = parames;
		accBean = AccountTradeCodeAction.transBean;
		this.nowPage = accBean.getQxn();
		logger.info("进入子产品页面");
		
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	logger.info("子产品页面倒计时结束 ");
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
		//背景色
		if("QX".equals(parames.get("PRODUCT_CODE"))){//协议千禧
			if("1".equals(parames.get("CHL_ID")) || "5".equals(parames.get("CHL_ID"))){
				background="pic/newPic/QXS.png";
				
			}else if("2".equals(parames.get("CHL_ID"))){//私行千禧
				background="pic/newPic/SHQXS.png";
			}
		}else if("XF".equals(parames.get("PRODUCT_CODE"))){//幸福
			background="pic/newPic/XFS.png";
		}else if("RY".equals(parames.get("PRODUCT_CODE"))){//如意
			background="pic/newPic/RYCS.png";
		}else if("RJ".equals(parames.get("PRODUCT_CODE"))){//如意+
			background="pic/newPic/RYCJS.png";
		}else if("JX".equals(parames.get("PRODUCT_CODE"))){//积享
			background="pic/newPic/JXCS.png";
		}else if("AX".equals(parames.get("PRODUCT_CODE"))){//安享
			background="pic/newPic/AXCS.png";
		}else if("LT".equals(parames.get("PRODUCT_CODE")) || "LZ".equals(parames.get("PRODUCT_CODE"))){//立得自享、他享
			background="pic/newPic/LDCS.png";
		}else if("YA".equals(parames.get("PRODUCT_CODE")) || "YB".equals(parames.get("PRODUCT_CODE"))||"YC".equals(parames.get("PRODUCT_CODE"))){//约享A系列、B系列、C系列
			background="pic/newPic/YXCS.png";
		}
		list=(List<ProductRateInfo1>) parames.get("02867List");
		if(list!=null){
			initPanel1();
			if("QX".equals(parames.get("PRODUCT_CODE"))){
				if("2".equals(parames.get("CHL_ID"))&&("1".equals(accBean.getCustLevel().trim())||
													   "2".equals(accBean.getCustLevel().trim())||
													   "3".equals(accBean.getCustLevel().trim()))){
					list1 = qxSubProduct(list);
				}else if("5".equals(parames.get("CHL_ID"))){
					list1 = qxSubProduct(list);
				}else{
					list1 =list;
				}
			}else{
				list1 =list;
			}
			showProducts(list1, nowPage);
		}
	}
	/**
	 * 初始化面板
	 */
	private void initPanel1(){
		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 处理上一页 */
				backTrans();
			}
		});
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(1075, 770);
		add(submitBtn);

		// 返回
		JLabel backButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				clearTimeText();
				openPanel(new OutputBankCardPanel());
			}
		});
		add(backButton);
		
		/*添加修饰样式*/
		//向左
		JLabel left=new JLabel();
		left.setIcon(new ImageIcon("pic/newPic/left.png"));
		left.setBounds(30,265 ,57, 98);
		left.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击向左按钮");
				upStep();
			}
		});
		this.showJpanel.add(left);
	   
		//向右
		JLabel right=new JLabel();
		right.setIcon(new ImageIcon("pic/newPic/right.png"));
		right.setBounds(922,265,57, 98);
		right.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击向右按钮");
				nextStep();		
			}
		});
		this.showJpanel.add(right);
	}
	
	/**
	 * 显示产品
	 * @param list
	 * @param nowPage
	 */
	private void showProducts(final List<ProductRateInfo1> list,int nowPage){
		logger.info("进入显示产品的方法");
		
		for (int i=(nowPage-1)*4;i<list.size()&&i<(nowPage*4);i++) {
			final int a = i;
			int x=117;
			int y=50;
			if(i-((nowPage-1)*4)<2){
				x=117+(i-((nowPage-1)*4))*(350+GlobalParameter.TRANS_WIDTH-980);
				y=70;
			}else{
				x=117+(i-((nowPage-1)*4)-2)*(350+GlobalParameter.TRANS_WIDTH-980);
				y=320;
			}
			JPanel panel_1 = new JPanel();
			panel_1.setBounds(x, y, 350, 220);
			panel_1.setLayout(null);
			this.showJpanel.add(panel_1);
			final String code=list.get(i).getProductCode();
			panel_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					accBean.setQxn(1);
					accBean.setMonthsUpperStr(list.get(a).getSavingCountMinStr());//存期  (天，月，年)
					accBean.setMonthsUpper(list.get(a).getSavingCountMin());//存期(D,M,Y)
					accBean.setMonthsUpperXySh(list.get(a).getSavingCountMin());//存期(D,M,Y)
					accBean.setProductName(list.get(a).getProductCodeName());//产品名称
					accBean.setProductTotalName(list.get(a).getProductCodeName());//产品系列名称
					accBean.setProductCode(list.get(a).getProductCode());//产品代码
					next();
				}

			});
			JLabel lblNewLabel = new JLabel("产品名称 : ");
			lblNewLabel.setForeground(Color.decode("#ffffff"));
			lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel.setBounds(10, 21, 100, 20);
			lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
			panel_1.add(lblNewLabel);
			lblNewLabel_1 = new JLabel(list.get(i).getProductCodeName());
			lblNewLabel_1.setForeground(Color.decode("#ffffff"));
			lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_1.setBounds(108, 21, 215, 20);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
			panel_1.add(lblNewLabel_1);
			
			JLabel lblNewLabel_2 = new JLabel("存期 : ");
			lblNewLabel_2.setForeground(Color.decode("#ffffff"));
			lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_2.setBounds(10, 66, 100, 20);
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
			panel_1.add(lblNewLabel_2);
			lblNewLabel_3 = new JLabel(list.get(i).getSavingCountMinStr());
			lblNewLabel_3.setForeground(Color.decode("#ffffff"));
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_3.setBounds(108,66, 215, 20);
			panel_1.add(lblNewLabel_3);
			
			
			JLabel lblNewLabel_4 = new JLabel("起存金额 : ");
			lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_4.setForeground(Color.decode("#ffffff"));
			lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_4.setBounds(10, 66+45, 100, 20);
			panel_1.add(lblNewLabel_4);
			lblNewLabel_5 = new JLabel(list.get(i).getStartMoneyStr()+"元");
			lblNewLabel_5.setForeground(Color.decode("#ffffff"));
			lblNewLabel_5.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_5.setBounds(108, 66+45, 215, 20);
			panel_1.add(lblNewLabel_5);
			
			
			JLabel lblNewLabel_6 = new JLabel("到期利率 : ");
			lblNewLabel_6.setForeground(Color.decode("#ffffff"));
			lblNewLabel_6.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_6.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_6.setBounds(10, 66+90, 100, 20);
			panel_1.add(lblNewLabel_6);
			lblNewLabel_7 = new JLabel(list.get(i).getInterestRateRangeStr());
			lblNewLabel_7.setForeground(Color.decode("#ffffff"));
			lblNewLabel_7.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_7.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_7.setBounds(108, 66+90, 215, 20);
			panel_1.add(lblNewLabel_7);
			JLabel jlb = new JLabel();
			jlb.setLocation(0, 0);
			ImageIcon image = new ImageIcon(background);
			image.setImage(image.getImage().getScaledInstance(350, 220,Image.SCALE_DEFAULT ));
			jlb.setIcon(image);
			jlb.setSize(350, 220);
			jlb.setVisible(true);
			panel_1.add(jlb);
			this.showJpanel.add(panel_1);
		}
	}
	
	/**选择对应账号跳转页面*/
	public void next() {
		logger.info("进入跳转至相应子页面的方法");
    	clearTimeText();//清空倒计时
    	accBean.setQxn(1);//还原页码
		parames.put("transCode", "0020");//下页标识
		AccountTransFlow.startTransFlow(comp, parames);
	}	
	
	/**
	 * 上一页
	 * @return 
	 */
	public void  upStep(){
		logger.info("进入到上一页的方法");
		if(nowPage<=1){
			return;
		}
		clearTimeText();//清空倒计时
		nowPage--;
		accBean.setQxn(nowPage);
		parames.put("accBean", accBean);
		openPanel(new AccSubProPanel(parames));	
	}
	
	/**
	 * 下一页
	 */
	public void  nextStep(){
		logger.info("进入到下一页的方法");
		int pages=list1.size()%4==0?list1.size()/4:list1.size()/4+1;
		if(nowPage>=pages){
			return;
		}
		clearTimeText();//清空倒计时
		nowPage++;
		accBean.setQxn(nowPage);
		parames.put("accBean", accBean);
		openPanel(new AccSubProPanel(parames));
	}
	
	/**
	 * 返回上一步
	 */
	public void backTrans() {
		logger.info("进入返回上一步的方法");
		clearTimeText();//清空倒计时
		accBean.setQxn(1);//还原页码
		//判断协议还是私行  ，判断哪款产品查询的
		if("LZ".equals(parames.get("PRODUCT_CODE")) || "LT".equals(parames.get("PRODUCT_CODE"))){
			openPanel(new AccLdcxlProPanel(parames));
		}else if("YA".equals(parames.get("PRODUCT_CODE")) || "YB".equals(parames.get("PRODUCT_CODE"))||"YC".equals(parames.get("PRODUCT_CODE"))){
			openPanel(new AccYxcxlProPanel(parames));
		}else if("AX".equals(parames.get("PRODUCT_CODE"))){
			openPanel(new AccAxcxlProPanel(parames));
		}else if("1".equals(parames.get("CHL_ID"))){
			openPanel(new AccProtocolDepPanel(parames));
		}else{
			openPanel(new AccPrivateLinePanel(parames));
		}		
	}
	
	/**
	 * 千禧产品合并
	 */
	public List<ProductRateInfo1> qxSubProduct(final List<ProductRateInfo1> list){
		List<ProductRateInfo1> listnew = new LinkedList<>();
		listnew.add(0,null);
		listnew.add(1,null);
		listnew.add(2,null);
		listnew.add(3,null);
		listnew.add(4,null);
		listnew.add(5,null);
		for(ProductRateInfo1 a:list){
			//喜迎
			if("QXK".equals(a.getProductCode().trim())){
				if(listnew.get(2)==null){
					listnew.set(2, a);
					continue;
				}else{
					listnew.get(2).setStartMoney(a.getStartMoney());
					String minRate = a.getInterestRateRange().split("-")[0];
					String maxRate = listnew.get(2).getInterestRateRange().split("-")[1];
					listnew.get(2).setInterestRateRange(minRate+"-"+maxRate);
				}
			//福临
			}else if("QXN".equals(a.getProductCode().trim())){
				if(listnew.get(5)==null){
					listnew.set(5, a);
					continue;
				}else{
					listnew.get(5).setStartMoney(a.getStartMoney());
					String minRate = a.getInterestRateRange().split("-")[0];
					String maxRate = listnew.get(5).getInterestRateRange().split("-")[1];
					listnew.get(5).setInterestRateRange(minRate+"-"+maxRate);
				}
			//溢彩	
			}else if("QXJ".equals(a.getProductCode().trim())){
				if(listnew.get(1)==null){
					listnew.set(1, a);
					continue;
				}else{
					listnew.get(1).setStartMoney(a.getStartMoney());
					String minRate = a.getInterestRateRange().split("-")[0];
					String maxRate = listnew.get(1).getInterestRateRange().split("-")[1];
					listnew.get(1).setInterestRateRange(minRate+"-"+maxRate);
				}
			//合瀛	
			}else if("QXI".equals(a.getProductCode().trim())){
				if(listnew.get(0)==null){
					listnew.set(0, a);
					continue;
				}else{
					listnew.get(0).setStartMoney(a.getStartMoney());
					String minRate = a.getInterestRateRange().split("-")[0];
					String maxRate = listnew.get(0).getInterestRateRange().split("-")[1];
					listnew.get(0).setInterestRateRange(minRate+"-"+maxRate);
				}
			//惠德	
			}else if("QXL".equals(a.getProductCode().trim())){
				if(listnew.get(3)==null){
					listnew.set(3, a);
					continue;
				}else{
					listnew.get(3).setStartMoney(a.getStartMoney());
					String minRate = a.getInterestRateRange().split("-")[0];
					String maxRate = listnew.get(3).getInterestRateRange().split("-")[1];
					listnew.get(3).setInterestRateRange(minRate+"-"+maxRate);
				}
			//祥瑞	
			}else if("QXM".equals(a.getProductCode().trim())){
				if(listnew.get(4)==null){
					listnew.set(4, a);
					continue;
				}else{
					listnew.get(4).setStartMoney(a.getStartMoney());
					String minRate = a.getInterestRateRange().split("-")[0];
					String maxRate = listnew.get(4).getInterestRateRange().split("-")[1];
					listnew.get(4).setInterestRateRange(minRate+"-"+maxRate);
				}
			//喜迎500000
			}else if("QXC".equals(a.getProductCode().trim())){
				if(listnew.get(2)==null){
					listnew.set(2, a);
					continue;
				}else{
					String maxRate = a.getInterestRateRange().split("-")[1];
					String minRate = listnew.get(2).getInterestRateRange().split("-")[0];
					listnew.get(2).setInterestRateRange(minRate+"-"+maxRate);
				}
			//福临500000
			}else if("QXD".equals(a.getProductCode().trim())){
				if(listnew.get(5)==null){
					listnew.set(5, a);
					continue;
				}else{
					String maxRate = a.getInterestRateRange().split("-")[1];
					String minRate = listnew.get(5).getInterestRateRange().split("-")[0];
					listnew.get(5).setInterestRateRange(minRate+"-"+maxRate);
				}
			//溢彩500000
			}else if("QXE".equals(a.getProductCode().trim())){
				if(listnew.get(1)==null){
					listnew.set(1, a);
					continue;
				}else{
					String maxRate = a.getInterestRateRange().split("-")[1];
					String minRate = listnew.get(1).getInterestRateRange().split("-")[0];
					listnew.get(1).setInterestRateRange(minRate+"-"+maxRate);
				}
			//合瀛500000
			}else if("QXF".equals(a.getProductCode().trim())){
				if(listnew.get(0)==null){
					listnew.set(0, a);
					continue;
				}else{
					String maxRate = a.getInterestRateRange().split("-")[1];
					String minRate = listnew.get(0).getInterestRateRange().split("-")[0];
					listnew.get(0).setInterestRateRange(minRate+"-"+maxRate);
				}
			//惠德500000
			}else if("QXG".equals(a.getProductCode().trim())){
				if(listnew.get(3)==null){
					listnew.set(3, a);
					continue;
				}else{
					String maxRate = a.getInterestRateRange().split("-")[1];
					String minRate = listnew.get(3).getInterestRateRange().split("-")[0];
					listnew.get(3).setInterestRateRange(minRate+"-"+maxRate);
				}
			//祥瑞500000
			}else if("QXH".equals(a.getProductCode().trim())){
				if(listnew.get(4)==null){
					listnew.set(4, a);
					continue;
				}else{
					String maxRate = a.getInterestRateRange().split("-")[1];
					String minRate = listnew.get(4).getInterestRateRange().split("-")[0];
					listnew.get(4).setInterestRateRange(minRate+"-"+maxRate);
				}
			}
		}
		return listnew;
	}
	
	
	
}
