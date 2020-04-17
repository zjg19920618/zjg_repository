package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import com.boomhope.Bill.TransService.AccOpen.Bean.SearchRYCDetail;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.PrivateLine.AccPrivateLinePanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProtocolDeposit.AccProtocolDepPanel;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTransFlow;
import com.boomhope.Bill.Util.DateTool;

/***
 * 关联如意存产品
 * @author zjg
 *
 */
public class AccGuanLianRycProPanel extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccGuanLianRycProPanel.class);

	JLabel lblNewLabel_1 ;
	JLabel lblNewLabel_3;
	JLabel lblNewLabel_5;
	JLabel lblNewLabel_7;
	
	String background =""; //产品背景图
	private int nowPage; 
	List<SearchRYCDetail> list;
	private AccPublicBean accBean;
	private Map<Object,Object> parames;
	Component comp =null;
	private boolean on_off=true;
	
	public AccGuanLianRycProPanel(final Map<Object,Object> params) {

		comp = this;
		this.parames = params;
		accBean = AccountTradeCodeAction.transBean;
		this.nowPage = accBean.getQxn();
		logger.info("进入关联如意存产品页面");
		
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	logger.info("关联如意存产品倒计时结束 ");
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();//清空倒计时
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
		//背景色
		if("RJ".equals(parames.get("PRODUCT_CODE"))){//如意+
			background="pic/ryc+Xl.png";
		}else if("JX".equals(parames.get("PRODUCT_CODE"))){//积享
			background="pic/jxcXl.png";
		}
		list=(List<SearchRYCDetail>) parames.get("03512List");
		if(list!=null){
			initPanel1();
			showProducts(list, nowPage);
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
				stopTimer(voiceTimer);//关闭语音
		    	closeVoice();
				openPanel(new OutputBankCardPanel());
			}
		});
		add(backButton);
		
//		/*添加修饰样式*/
		
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
	private void showProducts(final List<SearchRYCDetail> list,int nowPage){
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
					accBean.setProductName(list.get(a).getProductCodeName());//关联产品名称
					accBean.setSubProductName(list.get(a).getProductCodeName());//关联产品名称
					accBean.setProductCode(list.get(a).getProductCode());//产品代码
					accBean.setMonthsUpperStr(list.get(a).getDepTermStr());//存期(天，月，年)
					accBean.setMonthsUpper(list.get(a).getDepTerm());//存期(D,M,Y)
					accBean.setMonthsUpperXySh(list.get(a).getDepTerm());//存期(D,M,Y)
					accBean.setEndTime(list.get(a).getEndDate());//结束日期
					accBean.setRelaAcctNo(list.get(a).getAcctNo().trim());//关联账号、收益账号
					accBean.setSubRelaAcctNo(list.get(a).getSubAcctNo());//关联子账号
					try {
						int nowDate = Integer.parseInt(accBean.getSvrDate());//当前核心日
						int endDate = Integer.parseInt(accBean.getEndTime().replace("/", ""));//关联如意存到期日
						
						if(nowDate>=endDate){//关联如意存已到期
							logger.info("关联如意存接近到期日的，不符合开立");
							openMistakeDialog("该关联如意存产品已到期，不符合开立条件");
							on_off=true;
							mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseReleased(MouseEvent e) {
									closeDialog(mistakeDialog);
									
								}
							});
							return;
							
						}else{
							
							String jiajDate = DateTool.jiajDate(2, -3, accBean.getEndTime().replace("/", ""), "yyyyMMdd");
							if(!"".equals(jiajDate)){
								logger.info("关联如意存到期日前三个月时间"+jiajDate);
								int date = Integer.parseInt(jiajDate);
								if(nowDate>date){
									logger.info("关联如意存接近到期日三个月之内，不符合开立");
									openMistakeDialog("该关联如意存产品即将到期，不符合开立条件");
									on_off=true;
									mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
										@Override
										public void mouseReleased(MouseEvent e) {
											closeDialog(mistakeDialog);
											
										}
									});
									return;
								}
							}
						}
					} catch (Exception e2) {
						logger.info("关联如意存到期日时间计算失败"+e2);
					}
					next();
				}

			});
			JLabel lblNewLabel = new JLabel("产品名称：");
			lblNewLabel.setForeground(Color.decode("#ffffff"));
			lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel.setBounds(10, 21, 100, 20);
			lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
			panel_1.add(lblNewLabel);
			lblNewLabel_1 = new JLabel(list.get(i).getProductCodeName().trim());
			lblNewLabel_1.setForeground(Color.decode("#ffffff"));
			lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_1.setBounds(108, 21, 215, 20);
			panel_1.add(lblNewLabel_1);
			
			JLabel lblNewLabel_2 = new JLabel("开户日期：");
			lblNewLabel_2.setForeground(Color.decode("#ffffff"));
			lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_2.setBounds(10, 66, 100, 20);
			panel_1.add(lblNewLabel_2);
			lblNewLabel_3 = new JLabel(list.get(i).getOpenDate());
			lblNewLabel_3.setForeground(Color.decode("#ffffff"));
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_3.setBounds(108, 66, 215, 20);
			panel_1.add(lblNewLabel_3);
			
			
			JLabel lblNewLabel_4 = new JLabel("存    期：");
			lblNewLabel_4.setForeground(Color.decode("#ffffff"));
			lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_4.setBounds(10, 66+45, 100, 20);
			panel_1.add(lblNewLabel_4);
			lblNewLabel_5 = new JLabel(list.get(i).getDepTermStr());
			lblNewLabel_5.setForeground(Color.decode("#ffffff"));
			lblNewLabel_5.setFont(new Font("微软雅黑", Font.BOLD, 18));
			lblNewLabel_5.setBounds(108, 66+45, 215, 20);
			panel_1.add(lblNewLabel_5);
			
			
			JLabel lblNewLabel_6 = new JLabel("金    额：");
			lblNewLabel_6.setForeground(Color.decode("#ffffff"));
			lblNewLabel_6.setFont(new Font("微软雅黑", Font.BOLD, 18));
			lblNewLabel_6.setBounds(10, 66+90, 100, 20);
			panel_1.add(lblNewLabel_6);
			lblNewLabel_7 = new JLabel(list.get(i).getBalance()+"元");
			lblNewLabel_7.setForeground(Color.decode("#ffffff"));
			lblNewLabel_7.setFont(new Font("微软雅黑", Font.BOLD, 18));
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
	
	/*选择对应账号跳转页面*/
	public void next() {
		logger.info("进入跳转到相应页面的方法");
    	clearTimeText();//清空倒计时
    	accBean.setQxn(1);//还原页码
    	parames.put("transCode", "0020");//下页标识
		AccountTransFlow.startTransFlow(comp, parames);
		if("1".equals(accBean.getHaveRelaAcc())){
			on_off=true;
		}
	}	
	
	/**
	 * 上一页
	 * @return 
	 */
	public void  upStep(){
		logger.info("进入到上一页的方法");
		clearTimeText();//清空倒计时
		if(nowPage<=1){
			return;
		}
		nowPage--;
		accBean.setQxn(nowPage);
		parames.put("accBean", accBean);
		openPanel(new AccGuanLianRycProPanel(parames));		
	}
	
	/**
	 * 下一页
	 */
	public void  nextStep(){
		logger.info("进入到下一页的方法");
		clearTimeText();//清空倒计时
		int pages=list.size()%4==0?list.size()/4:list.size()/4+1;
		if(nowPage>=pages){
			return;
		}
		nowPage++;
		accBean.setQxn(nowPage);
		parames.put("accBean", accBean);
		openPanel(new AccGuanLianRycProPanel(parames));
	}
	
	/**
	 * 返回上一步
	 */
	public void backTrans() {
		logger.info("进入到返回上一步的方法");
		clearTimeText();//清空倒计时
		accBean.setQxn(1);//还原页码
		//跳转积享、如意+
		if("1".equals(parames.get("CHL_ID")) || "5".equals(parames.get("CHL_ID"))){
			logger.info(parames.get("CHL_ID"));
			openPanel(new AccProtocolDepPanel(parames));
		}else{
			logger.info(parames.get("CHL_ID"));
			openPanel(new AccPrivateLinePanel(parames));
		}		
	}
	public static void main(String[] args) {
		
		java.util.Date stringToUtilDate = DateTool.stringToUtilDate("20170809","yyyymmdd");
		System.out.println(stringToUtilDate.toString());
		String nMonthsAfterOneDateString = DateTool.nMonthsAfterOneDateString(stringToUtilDate,4);
		System.out.println(nMonthsAfterOneDateString.replace("-", ""));
	}
}
