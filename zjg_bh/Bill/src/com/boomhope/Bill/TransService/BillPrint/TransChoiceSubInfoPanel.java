package com.boomhope.Bill.TransService.BillPrint;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintSubAccInfoBean;

/**
 * 
 * 选择打印协议的子账户页面
 * @author Administrator
 *
 */
public class TransChoiceSubInfoPanel extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(TransPassingPanel.class);
	
	private JPanel bigPanel;//基础面板
	private JLabel left;//上一页按钮
	private JLabel right;//下一页按钮
	private int nowPage=1;//当前页
	private int maxPage;//最大页数
	private int Nums;//子账户列表的数量
	private List<BillPrintSubAccInfoBean> list;//子账户信息集合
	private boolean on_off=true;//用于控制页面跳转的开关
	
	public TransChoiceSubInfoPanel(){
		logger.info("进入打印协议书子账户选择页面");
		
		this.showTimeText(delaySecondMaxTime);
		delayTimer = new Timer(delaySecondMaxTime * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		delayTimer.start();
		
		//标题提示语
		JLabel labelHead = new JLabel("请选择要打印协议的子账户信息");
		labelHead.setFont(new Font("微软雅黑",Font.BOLD,34));
		labelHead.setBounds(0, 40, 1009, 40);
		labelHead.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(labelHead);
		
		//上一步
		JLabel label = new JLabel(new ImageIcon("pic/preStep.png"));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");				
				backTrans();
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
				returnHome();
			}

		});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);
		//确认
		JLabel lblNewLabel1 = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		lblNewLabel1.setBounds(890, 770, 150, 50);
		lblNewLabel1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				/* 处理下一页 */
				logger.info("点击确认按钮");	
				confirm();
			}

		});
		this.add(lblNewLabel1);
		/*添加修饰样式*/
		//向左
		left=new JLabel();
		left.setIcon(new ImageIcon("pic/newPic/left.png"));
		left.setBounds(15,305 ,57, 98);
		left.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击向左按钮");
				upPage();
				showUpDown();
			}
		});
		this.showJpanel.add(left);
	   
		//向右
		right=new JLabel();
		right.setIcon(new ImageIcon("pic/newPic/right.png"));
		right.setBounds(937,305,57, 98);
		right.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击向右按钮");
				downPage();
				showUpDown();
			}
		});
		this.showJpanel.add(right);
		
		//基础面板
		bigPanel = new JPanel();
		bigPanel.setBounds(120, 110, 790, 485);
		bigPanel.setLayout(null);
		bigPanel.setOpaque(false);
		this.showJpanel.add(bigPanel);
		
		getMaxPage();
		showUpDown();
		showSubInfos();
	}
	
	//显示翻页按钮
	public void showUpDown(){
		if(nowPage==1 && maxPage>1){
			left.setVisible(false);
			right.setVisible(true);
		}else if(maxPage==1){
			left.setVisible(false);
			right.setVisible(false);
		}else if(nowPage>1 && nowPage<maxPage){
			left.setVisible(true);
			right.setVisible(true);
		}else if(nowPage==maxPage && maxPage>1){
			left.setVisible(true);
			right.setVisible(false);
		}
	}
	
	//计算最大页数
	public void getMaxPage(){
		Nums=(Integer)billPrintBean.getSubInfo().get("SUB_ACC_NUMS");
		list = (List<BillPrintSubAccInfoBean>)billPrintBean.getSubInfo().get("SUB_ACC_MSG");
		if(Nums%3==0){
			maxPage=Nums/3;
		}else{
			maxPage=Nums/3+1;
		}
	}
	
	//展示子账户页面
	public void showSubInfos(){
		int nowNum;//当前页面的子账户列表数
		if(nowPage*3>Nums){
			nowNum = Nums;
		}else{
			nowNum=nowPage*3;
		}
		for(int i =(nowPage-1)*3;i<nowNum;i++){
			final BillPrintSubAccInfoBean nowBean=list.get(i);
			final int ind=i;
			
			int height = 0;
			if(i%3==0){
				height=0;
			}else if(i%3==1){
				height=165;
			}else if(i%3==2){
				height=330;
			}
			JPanel panel = new JPanel();
			panel.setSize(790, 155);
			panel.setLocation(0,height);
			panel.setLayout(null);
			panel.setOpaque(false);
			panel.setBorder(BorderFactory.createLineBorder(Color.getColor("#2254ff")));
			bigPanel.add(panel);
			
			//账号
			String accNo=null;
			if(nowBean.getSubAccNo()!=null && !"".equals(nowBean.getSubAccNo())){
				accNo=nowBean.getAccNo()+"-"+nowBean.getSubAccNo();
			}else{
				accNo=nowBean.getAccNo();
			}
			JLabel label1=new JLabel("账号："+accNo);
			label1.setBounds(60, 10, 750, 30);
			label1.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label1.setHorizontalAlignment(SwingUtilities.LEFT);
			panel.add(label1);
			
			//产品名称
			JLabel label2 = new JLabel("产品名称："+nowBean.getProductName());
			label2.setBounds(60, 45, 375, 30);
			label2.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label2.setHorizontalAlignment(SwingUtilities.LEFT);
			panel.add(label2);
			
			//产品金额
			JLabel label3 = new JLabel("产品金额："+nowBean.getOpenATM());
			label3.setBounds(435, 45, 375, 30);
			label3.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label3.setHorizontalAlignment(SwingUtilities.LEFT);
			panel.add(label3);
			
			//开户日
			JLabel label4 = new JLabel("开户日："+nowBean.getOpenDate());
			label4.setBounds(60, 80, 375, 30);
			label4.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label4.setHorizontalAlignment(SwingUtilities.LEFT);
			panel.add(label4);
			
			//到期日
			JLabel label5 = new JLabel("到期日："+nowBean.getEndIntDate());
			label5.setBounds(435, 80, 375, 30);
			label5.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label5.setHorizontalAlignment(SwingUtilities.LEFT);
			panel.add(label5);
			
			String dep=nowBean.getDepTerm().trim();
			String depterm=null;
			if(dep.contains("D")){
				depterm=dep.replace("D", "天");
			}else if(dep.contains("M")){
				depterm = dep.replace("M", "月");
			}else if(dep.contains("Y")){
				depterm = dep.replace("Y", "年");
			}else if("366".equals(dep)){
				depterm=dep+"天";
			}else{
				depterm=dep;
			}
			list.get(i).setDepTerm(depterm);
			//存期
			JLabel label6 = new JLabel("存期："+depterm);
			label6.setBounds(60, 115, 375, 30);
			label6.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label6.setHorizontalAlignment(SwingUtilities.LEFT);
			panel.add(label6);
			
			//开户渠道
			JLabel label7 = new JLabel("开户渠道："+billPrintBean.getChannelMap().get(nowBean.getChannel()));
			label7.setBounds(435, 115, 375, 30);
			label7.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label7.setHorizontalAlignment(SwingUtilities.LEFT);
			panel.add(label7);
			
			//选择框(未选中)
			final JLabel label8 = new JLabel(new ImageIcon("pic/wxz.png"));
			label8.setBounds(10, 63, 30, 30);
			panel.add(label8);
			
			//选择框(选中)
			final JLabel label9 = new JLabel(new ImageIcon("pic/selected.png"));
			label9.setBounds(10, 63, 30, 30);
			label9.setVisible(false);
			panel.add(label9);
			
			//点击选择框处理
			label8.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					label8.setVisible(false);
					label9.setVisible(true);
					nowBean.setChoice(true);
					list.set(ind, nowBean);
				}
			});
			label9.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					label9.setVisible(false);
					label8.setVisible(true);
					nowBean.setChoice(false);
					list.set(ind, nowBean);
				}
			});
			
			//显示选择框
			if(nowBean.isChoice()){
				label9.setVisible(true);
				label8.setVisible(false);
			}else{
				label8.setVisible(true);
				label9.setVisible(false);
			}
		}
	}
	
	//上一页按钮
	public void upPage(){
		nowPage -= 1;
		bigPanel.removeAll();
		bigPanel.repaint();
		showSubInfos();
	}
	
	//下一页按钮
	public void downPage(){
		nowPage += 1;
		bigPanel.removeAll();
		bigPanel.repaint();
		showSubInfos();
	}
	
	
	/**
	 * 确认
	 */
	public void confirm(){
		if(!on_off){
			logger.info("开关当前状态"+on_off);
			return;
		}
		logger.info("开关当前状态"+on_off);
		on_off=false;
		
		String a= "";
		for (BillPrintSubAccInfoBean infoBean : list) {
			if(infoBean.isChoice()){
				a = "1";
				break;
			}
		}
		if(!"1".equals(a)){//未选中
			openMistakeDialog("请选择要打印协议的子账户");
			mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					closeDialog(mistakeDialog);
					on_off=true;
				}
			});
			return;
		}
		billPrintBean.getSubInfo().put("SUB_ACC_MSG", list);
		clearTimeText();
		openPanel(new TransPrintAgreementsPanel());
	}
	
	/**
	 * 上一步
	 */
	public void backTrans(){
		clearTimeText();
		if("idCard".equals(billPrintBean.getImportMap().get("idCardPath"))){
			openPanel(new TransPrintAgreementShowCardsPanel());
		}else{
			openPanel(new TransPrintCheckTransPathPanel(billPrintBean));
		}
	}
}
