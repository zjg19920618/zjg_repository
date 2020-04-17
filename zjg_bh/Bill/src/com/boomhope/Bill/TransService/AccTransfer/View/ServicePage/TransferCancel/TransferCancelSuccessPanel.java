package com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.TransferCancel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.List;

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
 * 撤销最终界面
 * @author hao
 *
 */
public class TransferCancelSuccessPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransferCancelSuccessPanel.class);
	private static final long serialVersionUID = 1L;
	private JLabel submitBtn;
	private Component utilButton;
	private PublicAccTransferBean bean;
	JPanel panel;//大背景
	JLabel label;
	int page=1;//第一页
	List<TransferSelectBean> infos=null;
	private boolean on_off=true;
	public TransferCancelSuccessPanel(final PublicAccTransferBean transferBean) {
		logger.info("进入撤销最终页面...");
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
		infos = (List<TransferSelectBean>)bean.getParams().get("chooseCancelList");
	
		if(infos!=null){
			initPanel1();
			showInfos(page, infos);
		}
	}
	private void initPanel1() {
		panel=new JPanel();
		panel.setBounds(10,140,989,450);
		panel.setBackground(Color.decode("#f7f2ff"));
		panel.setLayout(null);
		this.showJpanel.add(panel);
		//可撤销汇款信息
		JLabel label = new JLabel("汇款撤销结果");
		label.setFont(new Font("微软雅黑",Font.BOLD,40));
		label.setHorizontalAlignment(0);
		label.setBounds(0,60,989,40);
		this.showJpanel.add(label);
		JLabel labelLine_1=new JLabel("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		labelLine_1.setBounds(0,150,989,10);
		labelLine_1.setFont(new Font("", Font.PLAIN, 20));
		panel.add(labelLine_1);
		
		JLabel label_10 = new JLabel("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -- - - --");
		label_10.setFont(new Font("Dialog", Font.PLAIN, 20));
		label_10.setBounds(0, 300, 989, 10);
		panel.add(label_10);
		
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
	public void showInfos(int page,List<TransferSelectBean> infos) {
		for (int i = (page-1)*3; i <infos.size()&&i<page*3 ; i++) {
			int x=0;
			int y=0;
			if(i-(page-1)*3<1){
				x=0;
				y=0;
			}else if(i-(page-1)*3<2){
				x=0;
				y=150;
			}else{
				x=0;
				y=150*2;
			}
			
			JPanel panel1=new JPanel();
			panel1.setBounds(x, y, 989, 150);
			panel1.setLayout(null);
			panel1.setBackground(Color.decode("#f7f2ff"));
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
			label_3.setBounds(80, 96, 130, 30);
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
			JLabel label_5 = new JLabel("撤 销 状 态：");
			label_5.setHorizontalAlignment(SwingConstants.RIGHT);
			label_5.setFont(new Font("微软雅黑", Font.BOLD, 20));
			label_5.setBounds(80, 123, 130, 30);
			panel1.add(label_5);
			JLabel label_6 = new JLabel("失 败 原 因：");
			label_6.setHorizontalAlignment(SwingConstants.RIGHT);
			label_6.setFont(new Font("微软雅黑", Font.BOLD, 20));
			label_6.setBounds(520, 123, 130, 30);
			panel1.add(label_6);
			// 转入卡号
			String no=infos.get(i).getZrNo();
			if(no!=null&&!"".equals(no)){
				
			}else{
				no="";
			}
			JLabel lblNewLabel_1 = new JLabel(no);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			lblNewLabel_1.setBounds(209, 39, 840, 30);
			panel1.add(lblNewLabel_1);

			// 转入户名
			String name=infos.get(i).getZrName();
			if(name!=null&&!"".equals(name)){
				
			}else{
				name="";
			}
			JLabel lblsadad = new JLabel(name);
			lblsadad.setHorizontalAlignment(SwingConstants.LEFT);
			lblsadad.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			lblsadad.setBounds(209, 69, 840, 30);
			panel1.add(lblsadad);
			
			// 转入开户行
			String bank=infos.get(i).getPayeeHbrName();
			if(bank!=null&&!"".equals(bank)){
				
			}else{
				bank="";
			}
			JLabel label_8 = new JLabel(infos.get(i).getPayeeHbrName());
			label_8.setHorizontalAlignment(SwingConstants.LEFT);
			label_8.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			label_8.setBounds(209, 96, 840, 30);
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
			String money=infos.get(i).getTransMoney();
			if(money!=null&&!"".equals(money)){
				
			}else{
				money="";
			}
			JLabel label_18 = new JLabel(money);
			label_18.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			label_18.setBounds(648, 10, 282, 30);
			panel1.add(label_18);
			//失败原因
			String cause=infos.get(i).getFailCause();
			if(cause!=null&&!"".equals(cause)){
				
			}else{
				cause="无";
			}
			JLabel label_19 = new JLabel(cause);
			label_19.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			label_19.setBounds(648, 123, 282, 30);
			panel1.add(label_19);
			//撤销状态
			String state=infos.get(i).getCancelResult();
			if(state!=null&&!"".equals(state)){
				
			}else{
				state="";
			}
			JLabel label_20 = new JLabel(state);
			label_20.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			label_20.setBounds(209, 123, 282, 30);
			panel1.add(label_20);

		}
		
	}
	
}

