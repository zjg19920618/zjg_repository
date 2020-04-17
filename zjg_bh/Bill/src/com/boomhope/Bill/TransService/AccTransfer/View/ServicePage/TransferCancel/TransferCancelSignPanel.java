package com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.TransferCancel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
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
import com.boomhope.Bill.Util.Property;
/***
 * 撤销信息签字界面
 * @author hao
 *
 */
public class TransferCancelSignPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransferCancelSignPanel.class);
	private static final long serialVersionUID = 1L;
	private JLabel submitBtn;
	private JLabel backBtn;
	private JLabel utilButton;
	private JLabel lblNewLabel_3;//客户签字区
	private ImageIcon image;//签名图片
	private JPanel panelNew;//签名图片面板
	private PublicAccTransferBean bean;
	JPanel panel;//大背景
	JLabel label;
	int page=1;//第一页
	List<TransferSelectBean> infos=null;
	List<TransferSelectBean> infos1=null;
	Component comp ;
	private boolean on_off=true;
	
	public TransferCancelSignPanel(final PublicAccTransferBean transferBean) {
		logger.info("进入撤销信息签字页面...");
		// 设置倒计时
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* 倒计时结束退出交易 */
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！", "","");
			}
		});
		delayTimer.start();
		
		comp = this;
		bean=transferBean;
		infos1 = (List<TransferSelectBean>)bean.getParams().get("CancelMsgList");
		infos = new ArrayList<TransferSelectBean>();
		Set<String> setFlag = bean.getSetFlag();
		
		//获取选中的撤销数据
		for (int i = 0; i < infos1.size(); i++) {
			for (String string : setFlag) {
				if(string.equals(infos1.get(i).getSelectFlag())){
					infos.add(infos1.get(i));
				}
			}
		}
		bean.getParams().put("chooseCancelList",infos);
		
		if(infos!=null){
			initPanel1();
			showInfos(page, infos);
		}
	}
	private void initPanel1() {
		panel=new JPanel();
		panel.setBounds(10,120,989,369);
		panel.setBackground(Color.decode("#f7f2ff"));
		panel.setLayout(null);
		this.showJpanel.add(panel);
		//可撤销汇款信息
		JLabel label = new JLabel("已选择撤销汇款信息");
		label.setFont(new Font("微软雅黑",Font.BOLD,40));
		label.setHorizontalAlignment(0);
		label.setBounds(0,60,989,40);
		this.showJpanel.add(label);
		JLabel labelLine_1=new JLabel("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		labelLine_1.setBounds(0,123,989,10);
		labelLine_1.setFont(new Font("", Font.PLAIN, 20));
		panel.add(labelLine_1);
		
		JLabel label_10 = new JLabel("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -- - - --");
		label_10.setFont(new Font("Dialog", Font.PLAIN, 20));
		label_10.setBounds(0, 246, 989, 10);
		panel.add(label_10);
		//加载签名的图片
		lblNewLabel_3=new JLabel();
		image = new ImageIcon(Property.SIGNATURE_PATH_CANCEL);
		if(image != null){
			image.getImage().flush();
			image = new ImageIcon(Property.SIGNATURE_PATH_CANCEL);
		}
		if("".equals(bean.getIsSign())||bean.getIsSign()==null){
			lblNewLabel_3.setText("客户签字区");
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 30));
			lblNewLabel_3.setForeground(Color.blue);
		}else{
			//使图片自适应窗格大小
			image.setImage(image.getImage().getScaledInstance(989, 90, Image.SCALE_DEFAULT));
			lblNewLabel_3.setIcon(image);
		}
		lblNewLabel_3.setBounds(10, 489, 989, 90);
		lblNewLabel_3.setBorder(BorderFactory.createLineBorder(Color.blue));
		this.showJpanel.add(lblNewLabel_3);
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if("".equals(bean.getIsSign())||bean.getIsSign()==null){
					logger.info("点击签字面板进行签字");
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					signView();
				}else{
					askLook(bean);
				}
				
			}
			
		});
		// 上一步
		submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(1075, 770);
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
				upStep();
				showInfos(page, infos);
				}
		});
		add(submitBtn);

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
		backBtn.setLocation(890, 770);
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
	public void showInfos(int page,List<TransferSelectBean> infos) {
		for (int i = (page-1)*3; i <infos.size()&&i<page*3 ; i++) {
			int x=0;
			int y=153*i;
			if(i-(page-1)*3<1){
				x=0;
				y=0;
			}else if(i-(page-1)*3<2){
				x=0;
				y=123;
			}else{
				x=0;
				y=123*2;
			}
			
			JPanel panel1=new JPanel();
			panel1.setBounds(x, y, 989, 123);
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
			JLabel label_18 = new JLabel(infos.get(i).getTransMoney());
			label_18.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			label_18.setBounds(648, 10, 282, 30);
			panel1.add(label_18);
		}
	}
	
	//显示签字区
	public void signView(){
		//清空倒计时
		clearTimeText();
		//跳转到签字页面
		openPanel(new TransferCancelSignaturePanel(bean));
	}
	/**
	 * 上一步
	 * @return 
	 */
	public void  upStep(){
		clearTimeText();//清空倒计时
		openPanel(new TransferCancelInfo(bean));		
	}
	
	/**
	 * 下一步
	 * @return 
	 */
	public void  confim(){
		clearTimeText();//清空倒计时
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if("".equals(bean.getIsSign())||bean.getIsSign()==null){
					lblNewLabel_3.setText("请点击签字区进行签字");
					on_off=true;
					return;
				}
				transferAction.CanaclHuiHua(comp,bean);	
			}
		}).start();
	}

	
	//点击查看签名图片
	public void askLook(final PublicAccTransferBean transferMoneyBean){
		logger.info("进入查看签名图片方法");
		//设置信息内容消失
		panel.setVisible(false);
		lblNewLabel_3.setVisible(false);
		//创建显示签名图片的面板
		panelNew = new JPanel();
		panelNew.setBounds(113, 120, 783, 420);
		panelNew.setOpaque(false);
		panelNew.setLayout(null);
		panelNew.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		
		this.showJpanel.add(panelNew);
		panelNew.setVisible(true);
		
		//使图片自适应窗格大小
		image.setImage(image.getImage().getScaledInstance(780, 100, Image.SCALE_DEFAULT));
		//签名的图片
		JLabel imageLabel = new JLabel(image);
		imageLabel.setBounds(2, 1, 780, 350);
		panelNew.add(imageLabel);
		
		//重新签名的按钮
		JLabel reSignButton = new JLabel(new ImageIcon("pic/reSign.png"));
		panelNew.add(reSignButton);
		reSignButton.setBounds(140, 360, 200, 50);
		reSignButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击重新签名按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				transferMoneyBean.setIsSign("");
				signView();
			}
		});
		
		//取消按钮
		JLabel lookButton = new JLabel(new ImageIcon("pic/cancel_icon.png"));
		panelNew.add(lookButton);
		lookButton.setBounds(440, 360, 200, 50);
		lookButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				panelNew.setVisible(false);
				panel.setVisible(true);
				//使图片自适应窗格大小
				image.setImage(image.getImage().getScaledInstance(780, 100, Image.SCALE_DEFAULT));
				lblNewLabel_3.setVisible(true);
			}
		});
		
	}
}

