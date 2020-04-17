package com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.TransferCancel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccTransfer.Action.TransferAction;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.Bean.TransferSelectBean;
import com.boomhope.Bill.Util.DateUtil;
/***
 * 撤销信息展示界面
 * @author hao
 *
 */
public class TransferCancelInfo extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransferCancelInfo.class);
	private static final long serialVersionUID = 1L;
	private JLabel submitBtn;
	private JLabel backBtn;
	private JLabel utilButton;
	private PublicAccTransferBean bean;
	JPanel panel;
	JLabel[] labels=null;
	Set<String> sets=null;//放标记选中的数字
	int page;//第一页
	private JLabel nextBtn;
	List<TransferSelectBean> infos=null;
	private JPanel[] panels=new JPanel[3];
	private boolean on_off=true;
	
	public TransferCancelInfo(final PublicAccTransferBean transferBean) {
		logger.info("进入撤销信息展示页面...");
		// 设置倒计时
		this.showTimeText(300);
		delayTimer = new Timer(300 * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* 倒计时结束退出交易 */
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！", "","");
			}
		});
		delayTimer.start();
		
		bean=transferBean;
		this.page=bean.getPage();
		this.sets=bean.getSetFlag();
		infos = (List<TransferSelectBean>)bean.getParams().get("CancelMsgList");
		if(infos!=null){
			initPanel1();
			showInfos(page, infos);
		}
	}
	private void initPanel1() {
		labels = new JLabel[infos.size()];
		panel=new JPanel();
		panel.setBounds(10,140,989,450);
		panel.setBackground(Color.decode("#f7f2ff"));
		panel.setLayout(null);
		this.showJpanel.add(panel);
		//可撤销汇款信息
		JLabel label = new JLabel("可撤销汇款信息");
		label.setFont(new Font("微软雅黑",Font.BOLD,40));
		label.setHorizontalAlignment(0);
		label.setBounds(0,60,989,40);
		this.showJpanel.add(label);
		JLabel labelLine_1=new JLabel("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		labelLine_1.setBounds(0,143,989,10);
		labelLine_1.setFont(new Font("", Font.PLAIN, 20));
		panel.add(labelLine_1);
		
		JLabel label_10 = new JLabel("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -- - - --");
		label_10.setFont(new Font("Dialog", Font.PLAIN, 20));
		label_10.setBounds(0, 296, 989, 10);
		panel.add(label_10);
		
		// 上一页
		submitBtn = new JLabel(new ImageIcon("pic/back_page.png"));
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(890, 770);
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一页按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
					upStep();
					showInfos(page, infos);
					
				}
		});
		add(submitBtn);
		// 下一页
		nextBtn= new JLabel(new ImageIcon("pic/next_page.png"));
		nextBtn.setSize(150, 50);
		nextBtn.setLocation(1075, 770);
		nextBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击下一页按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				nextStep();
				showInfos(page, infos);
			}	
		});
		add(nextBtn);

		// 确认
		backBtn = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击确认按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				// 清空倒计时
				clearTimeText();
				confim();
			}
		});
		backBtn.setSize(150, 50);
		backBtn.setLocation(705, 770);
		add(backBtn);
		
		// 返回
		utilButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		utilButton.setBounds(1258, 770, 150, 50);
		utilButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				// 清空倒计时
				clearTimeText();
				openPanel(new OutputBankCardPanel());
			}
		});
		add(utilButton);
	}
	public void showInfos(final int page,final List<TransferSelectBean> infos) {
		for (int i = 0; i < panels.length; i++) {
			if (panels[i] != null) {
				panel.remove(panels[i]);
			}
		}
		panel.repaint();
		for (int i = (page-1)*3; i <infos.size()&&i<page*3 ; i++) {
			
			final int a=i;
			int x=0;
			int y=153*i;
			if(i-(page-1)*3<1){
				x=0;
				y=0;
			}else if(i-(page-1)*3<2){
				x=0;
				y=153;
			}else{
				x=0;
				y=153*2;
			}
			
			JPanel panel1=new JPanel();
			panel1.setBounds(x, y, 989, 143);
			panel1.setLayout(null);
			panel1.setBackground(Color.decode("#f7f2ff"));
			panels[i-(page-1)*3]=panel1;
			panel.add(panel1);
			JLabel lblNewLabel = new JLabel("转 入 账 号：");
			lblNewLabel.setBounds(80, 39, 130, 30);
			lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
			panel1.add(lblNewLabel);

			JLabel label_2 = new JLabel("转 入 户 名：");
			label_2.setHorizontalAlignment(SwingConstants.RIGHT);
			label_2.setFont(new Font("微软雅黑", Font.BOLD, 20));
			label_2.setBounds(80, 69, 130, 30);
			panel1.add(label_2);

			JLabel label_3 = new JLabel("转入开户行：");
			label_3.setHorizontalAlignment(SwingConstants.RIGHT);
			label_3.setFont(new Font("微软雅黑", Font.BOLD, 20));
			label_3.setBounds(80, 99, 130, 30);
			panel1.add(label_3);
			
			JLabel label_1 = new JLabel("操 作 日 期：");
			label_1.setHorizontalAlignment(SwingConstants.RIGHT);
			label_1.setFont(new Font("微软雅黑", Font.BOLD, 20));
			label_1.setBounds(80, 10, 130, 30);
			panel1.add(label_1);
			
			JLabel label_4 = new JLabel("转 入 金 额：");
			label_4.setHorizontalAlignment(SwingConstants.RIGHT);
			label_4.setFont(new Font("微软雅黑", Font.BOLD, 20));
			label_4.setBounds(520, 10, 130, 30);
			panel1.add(label_4);
			// 转入卡号
			JLabel lblNewLabel_1 = new JLabel(infos.get(i).getZrNo());
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			lblNewLabel_1.setBounds(209, 39, 840, 30);
			panel1.add(lblNewLabel_1);

			// 转入户名
			JLabel lblsadad = new JLabel(infos.get(i).getZrName());
			lblsadad.setHorizontalAlignment(SwingConstants.LEFT);
			lblsadad.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			lblsadad.setBounds(209, 69, 840, 30);
			panel1.add(lblsadad);
			
			// 转入开户行
			JLabel label_8 = new JLabel(infos.get(i).getPayeeHbrName());
			label_8.setHorizontalAlignment(SwingConstants.LEFT);
			label_8.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			label_8.setBounds(209, 99, 840, 30);
			panel1.add(label_8);
			//操作日期
			String date=infos.get(i).getWtDate();
			if(date!=null&&!"".equals(date)){
				try {
					date=DateUtil.getDate(date, "yyyyMMdd");
				} catch (ParseException e1) {
					logger.error(e1);
				}
			}else{
				date="";
			}
			JLabel lblNewLabel_2 = new JLabel(date);
			lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			lblNewLabel_2.setBounds(209, 10, 301, 30);
			panel1.add(lblNewLabel_2);
			//转入金额
			JLabel label_18 = new JLabel(infos.get(i).getTransMoney());
			label_18.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			label_18.setBounds(648, 10, 282, 30);
			panel1.add(label_18);
			
			labels[i]=new JLabel();
			labels[i].setBounds(30, 60, 30,30);
			if(sets.size()==0){
				labels[i].setIcon(new ImageIcon("pic/wxz.png"));
				
			}else{
				for(String string :sets){
					
					if(string!=null&&string.equals(infos.get(i).getSelectFlag())){
							labels[i].setIcon(new ImageIcon("pic/selected.png"));
							break;
						}else{
							labels[i].setIcon(new ImageIcon("pic/wxz.png"));
						}
					}
			}
			labels[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					for (int j = 0; j < labels.length; j++) {
						
						if (labels[a].equals(labels[j])) {
							if(!infos.get(a).isFlag()){//改为选中
								if(bean.getSetFlag().size()==3){
									openMistakeDialog("您最多只能选择三条可撤销记录!");
									return;
								}
								infos.get(a).setFlag(true);
								bean.getSetFlag().add(infos.get(a).getSelectFlag());
								logger.info("选中的记录："+bean.getSetFlag().toString());
								labels[j].setIcon(new ImageIcon("pic/selected.png"));
							}else {//取消选中
								infos.get(a).setFlag(false);
								bean.getSetFlag().remove(infos.get(a).getSelectFlag());
								logger.info("取消选中后的记录："+bean.getSetFlag().toString());
								labels[j].setIcon(new ImageIcon("pic/wxz.png"));
							}
						} 
					}
					
				}
			});
			panel1.add(labels[i]);
		
		}
	}
	/**
	 * 上一页
	 * @return 
	 */
	public void  upStep(){
//		clearTimeText();//清空倒计时
		if(page<=1){
			//跳到插卡页面
			bean.setPage(1);
			on_off=true;
			openMistakeDialog("已经没有更多的信息了");
			return;
		}
		page--;
		bean.setPage(page);
		openPanel(new TransferCancelInfo(bean));		
	}
	
	/**
	 * 下一页
	 */
	public void nextStep(){
//		clearTimeText();//清空倒计时
		int pages=infos.size()%3==0?infos.size()/3:infos.size()/3+1;
		if(page>=pages){
			on_off=true;
			openMistakeDialog("已经没有更多信息了");
			return;
		}
		page++;
		bean.setPage(page);
		openPanel(new TransferCancelInfo(bean));
	}
	/**
	 * 确定
	 */
	public void confim(){
//		clearTimeText();//清空倒计时
		bean.setPage(1);
		if(bean.getSetFlag().size()==0){
			on_off=true;
			openMistakeDialog("请选择至少一条可撤销转账记录");
			return;
		}
		logger.info("确定选中的记录："+bean.getSetFlag().toString());
		openPanel(new TransferCancelSignPanel(bean));
	}
}

